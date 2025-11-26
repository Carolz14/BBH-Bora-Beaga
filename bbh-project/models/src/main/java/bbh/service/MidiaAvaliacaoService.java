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

public class MidiaAvaliacaoService {

    private final MidiaAvaliacaoDAO dao;
    private final Path raizUpload;
    private final long tamanhoMaximoImagem;
    private final int larguraMaximaPx = 4000; // 4K de largura
    private final int alturaMaximaPx = 4000; // 4K de altura

    public MidiaAvaliacaoService() {
        this(
                new MidiaAvaliacaoDAO(),
                Paths.get(System.getProperty("bbh.upload.dir",
                        System.getProperty("user.home")
                                + System.getProperty("file.separator")
                                + "bbh-uploads")),
                10L * 1024L * 1024L);
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

        // prepara diretório de imagens
        Path imagensDir = raizUpload.resolve("imagens");
        Files.createDirectories(imagensDir);

        // decide extensão (do nome ou do mime)
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

        // sufixo para arquivo temporário
        String tempSuffix;
        if (extensao.isEmpty()) {
            tempSuffix = ".tmp";
        } else {
            tempSuffix = extensao;
        }

        // nome armazenado único
        String nomeArmazenadoBase = UUID.randomUUID().toString();
        String nomeArmazenado;
        if (extensao.isEmpty()) {
            nomeArmazenado = nomeArmazenadoBase;
        } else {
            nomeArmazenado = nomeArmazenadoBase + extensao;
        }

        Path destino = imagensDir.resolve(nomeArmazenado).normalize();
        if (!destino.startsWith(imagensDir)) {
            throw new PersistenciaException("Erro: caminho de destino inválido.");
        }

        Path temp = Files.createTempFile(imagensDir,"midia-upload-", tempSuffix);

        try {
            // grava stream em temp
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);

            // --- valida dimensões em pixels (carrega imagem do temp) ---
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
                        "Imagem muito grande. Máximo permitido:" + alturaMaximaPx + "x" + larguraMaximaPx);

            }
            // ------------------------------------------------------------

            // move para destino final (tenta atômico, senão replace)
            try {
                Files.move(temp, destino, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException amnse) {
                Files.move(temp, destino, StandardCopyOption.REPLACE_EXISTING);
            }

            String caminhoRelativo = "imagens/" + nomeArmazenado;

            MidiaAvaliacao midia = new MidiaAvaliacao(idAvaliacao, nomeArquivoEnviado, nomeArmazenado, caminhoRelativo,
                    mime,
                    tamanho);
            MidiaAvaliacao midiaSalva = dao.inserir(midia);
            return midiaSalva;

        } catch (PersistenciaException pe) {
            // se falhar ao persistir, tenta apagar o destino para não deixar órfãos
            try {
                Files.deleteIfExists(destino);
            } catch (IOException ignored) {
            }
            throw pe;
        } catch (IOException ioe) {
            // limpeza: apagar temp e destino parcial
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

        // caminho físico
        Path caminho = raizUpload.resolve(midiaAvaliacao.getCaminho()).normalize();

        if (!caminho.startsWith(raizUpload)) {
            throw new PersistenciaException("Caminho inválido para remoção");
        }

        try {
            Files.deleteIfExists(caminho);
        } catch (IOException e) {
            throw new PersistenciaException("Erro ao apagar arquivo físico: " + e.getMessage(), e);
        }

        dao.removerPorId(idMidia);
    }
}
