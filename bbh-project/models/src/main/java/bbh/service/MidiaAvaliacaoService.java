package bbh.service;

import bbh.common.PersistenciaException;
import bbh.dao.MidiaAvaliacaoDAO;
import bbh.domain.MidiaAvaliacao;
import bbh.service.util.MidiaAvaliacaoUtil;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.ImageReader;
import java.util.Iterator;

public class MidiaAvaliacaoService {

    private final MidiaAvaliacaoDAO dao;
    private final Path raizUpload;
    private final long tamanhoMaximoImagem;
    private final int larguraMaximaPx = 4000;
    private final int alturaMaximaPx = 4000;

    public MidiaAvaliacaoService() {
        this(
                new MidiaAvaliacaoDAO(),
                detectDefaultUploadDir(),
                10L * 1024L * 1024L);
    }

    private static Path detectDefaultUploadDir() {
        Path caminhoFixo = Paths.get("C:/Projetos/BBH-Bora-Beaga/uploads");
        caminhoFixo = caminhoFixo.toAbsolutePath().normalize();
        return caminhoFixo;
    }

    public MidiaAvaliacaoService(MidiaAvaliacaoDAO dao, Path raizUpload, long tamanhoMaximoImagem) {
        if (dao == null) {
            throw new IllegalArgumentException("DAO não pode ser nulo.");
        }
        if (raizUpload == null) {
            throw new IllegalArgumentException("Raiz de upload não pode ser nula.");
        }

        this.dao = dao;
        this.raizUpload = raizUpload;
        this.tamanhoMaximoImagem = tamanhoMaximoImagem;

        System.out.println("java.io.tmpdir = " + System.getProperty("java.io.tmpdir"));
        System.out.println("Tentando criar a pasta de upload: " + this.raizUpload.toAbsolutePath());

        ImageIO.setUseCache(false);
        System.out.println("Tentando criar a pasta de upload: " + this.raizUpload.toAbsolutePath());

        try {
            Files.createDirectories(this.raizUpload);
            System.out.println("Diretório criado ou já existente: " + this.raizUpload.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("ERRO ao criar diretório de upload!");
            e.printStackTrace();
            throw new IllegalStateException("Não foi possível criar diretório de upload: " + this.raizUpload, e);
        }
    }

    public MidiaAvaliacao salvarMidia(Part part, long idAvaliacao)
            throws PersistenciaException, IOException {

        if (part == null) {
            throw new PersistenciaException("Parte do upload é nula");
        }

        String nomeArquivoEnviado;
        if (part.getSubmittedFileName() == null) {
            nomeArquivoEnviado = "";
        } else {
            nomeArquivoEnviado = Path.of(part.getSubmittedFileName()).getFileName().toString();
        }

        String mime = part.getContentType();
        long tamanho = part.getSize();

        try (InputStream in = part.getInputStream()) {
            return salvarMidiaEmBytes(in, nomeArquivoEnviado, mime, tamanho, idAvaliacao);
        }
    }

    public MidiaAvaliacao salvarMidiaEmBytes(InputStream in, String nomeArquivoEnviado, String mime, long tamanho,
            long idAvaliacao) throws PersistenciaException, IOException {

        if (mime == null) {
            throw new PersistenciaException("Erro: arquivo sem MIME type.");
        }

        String mimeLower = mime.toLowerCase();
        if (!mimeLower.startsWith("image/")) {
            throw new PersistenciaException("Erro: apenas imagens são permitidas.");
        }

        if (tamanho <= 0) {
            throw new PersistenciaException("Erro: arquivo vazio.");
        }

        if (tamanho > tamanhoMaximoImagem) {
            throw new PersistenciaException("Erro: tamanho do arquivo supera o permitido.");
        }

        Path imagensDir = raizUpload.resolve("imagens");
        Files.createDirectories(imagensDir);

        String extensao = MidiaAvaliacaoUtil.extrairExtensao(nomeArquivoEnviado);
        if (extensao == null) {
            extensao = "";
        }
        if (extensao.isEmpty()) {
            String extFromMime = MidiaAvaliacaoUtil.extrairDeMime(mime);
            if (extFromMime == null) {
                extFromMime = "";
            }
            if (!extFromMime.isEmpty()) {
                extensao = "." + extFromMime;
            }
        }

        String tempSuffix = extensao.isEmpty() ? ".tmp" : extensao;

        String nomeArmazenadoBase = UUID.randomUUID().toString();
        String nomeArmazenado = extensao.isEmpty() ? nomeArmazenadoBase : nomeArmazenadoBase + extensao;

        Path destino = imagensDir.resolve(nomeArmazenado).normalize();
        if (!destino.startsWith(imagensDir)) {
            throw new PersistenciaException("Erro: caminho de destino inválido.");
        }

        Path temp = Files.createTempFile(imagensDir, "midia-upload-", tempSuffix);

        try {
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);

            long sizeReal = Files.size(temp);
            if (sizeReal <= 0) {
                throw new PersistenciaException("Erro: arquivo temporário está vazio.");
            }
            if (sizeReal > tamanhoMaximoImagem) {
                throw new PersistenciaException("Erro: arquivo gravado excede tamanho máximo permitido.");
            }
            try (ImageInputStream iis = ImageIO.createImageInputStream(Files.newInputStream(temp))) {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
                if (readers == null || !readers.hasNext()) {
                    throw new PersistenciaException("Formato de imagem não suportado pelo servidor.");
                }
            }

            BufferedImage img = ImageIO.read(temp.toFile());
            if (img == null) {
                throw new PersistenciaException("O arquivo enviado não é uma imagem válida.");
            }

            int largura = img.getWidth();
            int altura = img.getHeight();

            if (largura <= 0 || altura <= 0) {
                throw new PersistenciaException("Imagem com dimensões inválidas.");
            }

            if (largura > larguraMaximaPx || altura > alturaMaximaPx) {
                throw new PersistenciaException(
                        "Imagem muito grande. Máximo permitido: " + larguraMaximaPx + "x" + alturaMaximaPx + " px.");
            }

            try {
                Files.move(temp, destino, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException amnse) {
                Files.move(temp, destino, StandardCopyOption.REPLACE_EXISTING);
            }

            String caminhoRelativo = "imagens/" + nomeArmazenado;

            MidiaAvaliacao midia = new MidiaAvaliacao(idAvaliacao, nomeArquivoEnviado, nomeArmazenado, caminhoRelativo,
                    mime,
                    sizeReal);
            MidiaAvaliacao midiaSalva = dao.inserir(midia);
            return midiaSalva;

        } catch (PersistenciaException pe) {
            try {
                Files.deleteIfExists(destino);
            } catch (IOException ignored) {
            }
            throw pe;
        } catch (IOException ioe) {
            try {
                Files.deleteIfExists(temp);
            } catch (IOException ignored) {
            }
            try {
                Files.deleteIfExists(destino);
            } catch (IOException ignored) {
            }
            throw ioe;
        }
    }

    public MidiaAvaliacao buscarPorId(long idMidia) throws PersistenciaException {
        return dao.buscarPorId(idMidia);
    }

    public List<MidiaAvaliacao> listarMidiasPorAvaliacao(long idAvaliacao)
            throws PersistenciaException {
        return dao.listarMidiasPorAvaliacao(idAvaliacao);
    }

    public void removerMidia(long idMidia) throws PersistenciaException {

        MidiaAvaliacao midiaAvaliacao;
        try {
            midiaAvaliacao = dao.buscarPorId(idMidia);
        } catch (PersistenciaException e) {
            throw new PersistenciaException("Erro ao buscar mídia antes de remover: " + e.getMessage(), e);
        }

        if (midiaAvaliacao == null) {
            System.out
                    .println("Aviso: tentativa de remoção de mídia id=" + idMidia + " mas registro não existe no BD.");
            return;
        }

        Path caminho = raizUpload.resolve(midiaAvaliacao.getCaminho()).normalize();
        if (!caminho.startsWith(raizUpload)) {
            throw new PersistenciaException("Caminho inválido para remoção: " + caminho);
        }
        boolean removedFromDb;
        try {
            removedFromDb = dao.removerPorId(idMidia);
            if (!removedFromDb) {
                System.out.println("Aviso: remoção do metadado retornou 0 linhas afetadas para id=" + idMidia);
            } else {
                System.out.println("Metadado removido do BD: id=" + idMidia);
            }
        } catch (PersistenciaException e) {
            throw new PersistenciaException("Erro ao remover metadado da mídia: " + e.getMessage(), e);
        }
        try {
            boolean apagou = Files.deleteIfExists(caminho);
            if (apagou) {
                System.out.println("Arquivo físico apagado: " + caminho);
            } else {
                System.out.println("Aviso: arquivo físico não encontrado ao tentar apagar: " + caminho);
            }
        } catch (IOException ioe) {
            throw new PersistenciaException("Erro ao apagar arquivo físico: " + ioe.getMessage(), ioe);
        }
    }

    public MidiaAvaliacao atualizarMidia(long idMidia, InputStream in, String nomeArquivoEnviado, String mime,
            long tamanho) throws PersistenciaException, IOException {

        if (in == null) {
            throw new PersistenciaException("InputStream nulo para atualização de mídia.");
        }
        if (mime == null) {
            throw new PersistenciaException("Erro: arquivo sem MIME type.");
        }

        // busca metadado existente
        MidiaAvaliacao existente = dao.buscarPorId(idMidia);
        if (existente == null) {
            throw new PersistenciaException("Mídia com id " + idMidia + " não encontrada para atualização.");
        }

        // validações simples
        if (!mime.toLowerCase().startsWith("image/")) {
            throw new PersistenciaException("Erro: apenas imagens são permitidas.");
        }
        if (tamanho <= 0) {
            throw new PersistenciaException("Erro: arquivo vazio.");
        }
        if (tamanho > tamanhoMaximoImagem) {
            throw new PersistenciaException("Erro: tamanho do arquivo supera o permitido.");
        }

        // prepara diretório de imagens
        Path imagensDir = raizUpload.resolve("imagens");
        Files.createDirectories(imagensDir);

        // decide extensão
        String extensao = MidiaAvaliacaoUtil.extrairExtensao(nomeArquivoEnviado);
        if (extensao == null)
            extensao = "";
        if (extensao.isEmpty()) {
            String extFromMime = MidiaAvaliacaoUtil.extrairDeMime(mime);
            if (extFromMime == null)
                extFromMime = "";
            if (!extFromMime.isEmpty())
                extensao = "." + extFromMime;
        }
        String tempSuffix = extensao.isEmpty() ? ".tmp" : extensao;
        String nomeArmazenadoBase = UUID.randomUUID().toString();
        String nomeArmazenado = extensao.isEmpty() ? nomeArmazenadoBase : nomeArmazenadoBase + extensao;

        Path destino = imagensDir.resolve(nomeArmazenado).normalize();
        if (!destino.startsWith(imagensDir)) {
            throw new PersistenciaException("Erro: caminho de destino inválido.");
        }

        Path temp = Files.createTempFile(imagensDir, "midia-update-", tempSuffix);
        try {
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);

            long sizeReal = Files.size(temp);
            if (sizeReal <= 0) {
                throw new PersistenciaException("Erro: arquivo temporário está vazio.");
            }
            if (sizeReal > tamanhoMaximoImagem) {
                throw new PersistenciaException("Erro: arquivo gravado excede tamanho máximo permitido.");
            }

            try (ImageInputStream iis = ImageIO.createImageInputStream(Files.newInputStream(temp))) {
                Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
                if (readers == null || !readers.hasNext()) {
                    throw new PersistenciaException("Formato de imagem não suportado pelo servidor.");
                }
            }
            BufferedImage img = ImageIO.read(temp.toFile());
            if (img == null) {
                throw new PersistenciaException("O arquivo enviado não é uma imagem válida.");
            }
            int largura = img.getWidth();
            int altura = img.getHeight();
            if (largura <= 0 || altura <= 0) {
                throw new PersistenciaException("Imagem com dimensões inválidas.");
            }
            if (largura > larguraMaximaPx || altura > alturaMaximaPx) {
                throw new PersistenciaException(
                        "Imagem muito grande. Máximo permitido: " + larguraMaximaPx + "x" + alturaMaximaPx + " px.");
            }

            try {
                Files.move(temp, destino, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException amnse) {
                Files.move(temp, destino, StandardCopyOption.REPLACE_EXISTING);
            }

            String caminhoRelativo = "imagens/" + nomeArmazenado;
            MidiaAvaliacao novaMidia = new MidiaAvaliacao(idMidia, existente.getIdAvaliacao(), nomeArquivoEnviado,
                    nomeArmazenado,
                    caminhoRelativo,
                    mime,
                    sizeReal,
                    null);

            boolean atualizado = dao.atualizarPorId(novaMidia);
            if (!atualizado) {
                try {
                    Files.deleteIfExists(destino);
                } catch (IOException ignored) {
                }
                throw new PersistenciaException("Falha ao atualizar metadado da mídia no BD (0 linhas afetadas).");
            }

            Path antigo = raizUpload.resolve(existente.getCaminho()).normalize();
            try {
                if (Files.exists(antigo) && antigo.startsWith(raizUpload)) {
                    Files.deleteIfExists(antigo);
                }
            } catch (IOException ioe) {
                System.err.println("Aviso: não foi possível apagar arquivo antigo:" + ioe.getMessage());
            }
            return dao.buscarPorId(idMidia);

        } catch (PersistenciaException pe) {
            try {
                Files.deleteIfExists(destino);
            } catch (IOException ignored) {
            }
            throw pe;
        } catch (IOException ioe) {
            try {
                Files.deleteIfExists(temp);
            } catch (IOException ignored) {
            }
            try {
                Files.deleteIfExists(destino);
            } catch (IOException ignored) {
            }
            throw ioe;
        }
    }

    public Path getRaizUpload() {
        return this.raizUpload;
    }
}
