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

        MidiaAvaliacao midiaAvaliacao = dao.buscarPorId(idMidia);
        if (midiaAvaliacao == null) {
            throw new PersistenciaException("Midia com id " + idMidia + " não encontrada");
        }

        Path caminho = raizUpload.resolve(midiaAvaliacao.getCaminho()).normalize();

        if (!caminho.startsWith(raizUpload)) {
            throw new PersistenciaException("Caminho inválido para remoção");
        }

        try {
            dao.removerPorId(idMidia);
        } catch (PersistenciaException e) {
            throw new PersistenciaException("Erro ao remover o metadado do BD: " + e.getMessage(), e);
        }

        try {
            Files.deleteIfExists(caminho);
        } catch (IOException e) {
            throw new PersistenciaException("Mídia removida do banco, mas erro ao apagar arquivo: " + e.getMessage(),
                    e);
        }
    }

    public Path getRaizUpload() {
        return this.raizUpload;
    }
}
