package bbh.service;

import bbh.dao.TagCorrespondenciaDAO;
import bbh.dao.TagDAO;
import bbh.domain.Tag;
import java.util.List;
import bbh.common.PersistenciaException;

public class TagService {

    private final TagDAO tagDAO = new TagDAO();
    private final TagCorrespondenciaDAO tagCorrespondenciaDAO = new TagCorrespondenciaDAO();

    public List<Tag> listarTodasAsTags() throws PersistenciaException {
        return tagDAO.listarTodasAsTags();
    }

    public List<Tag> listarTagsDoUsuario(Long idUsuario) throws PersistenciaException {
        if (idUsuario == null) {
            throw new PersistenciaException("Erro na inserção do id do usuário");
        }
        return tagCorrespondenciaDAO.listarTagsDoEstabelecimento(idUsuario);
    }

    public void removerTagsEstabelecimento(Long idUsuario, List<Long> idTags) throws PersistenciaException {
        if (idUsuario == null)
            throw new IllegalArgumentException("idUsuario não pode ser null");
        if (idTags == null || idTags.isEmpty())
            return;

        try {
            tagCorrespondenciaDAO.removerTagEstabelecimento(idUsuario, idTags);
        } catch (NoSuchMethodError | AbstractMethodError e) {
            for (Long idTag : idTags) {
                if (idTag == null)
                    continue;
                tagCorrespondenciaDAO.removerTagEstabelecimento(idUsuario, idTags);
            }
        }
    }
    public void adicionarTagsEstabelecimento(Long idUsuario, List<Long> idsTags) throws PersistenciaException {
        if (idUsuario == null)
            throw new IllegalArgumentException("idUsuario não pode ser null");
        if (idsTags == null || idsTags.isEmpty())
            return;

        try {
            tagCorrespondenciaDAO.associarTagsAoEstabelecimento(idUsuario, idsTags);
        } catch (NoSuchMethodError | AbstractMethodError e) {
            // fallback: itera chamando o método single
            for (Long idTag : idsTags) {
                if (idTag == null)
                    continue;
                tagCorrespondenciaDAO.associarTagsAoEstabelecimento(idUsuario, idsTags);
            }
        }
    }

    public static String gerarSlug(String nome) {
        if (nome == null || nome.isEmpty()) {
            return "";
        }
        String slug = nome.toLowerCase();
        slug = java.text.Normalizer.normalize(slug, java.text.Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        slug = slug.replaceAll("[^a-z0-9]+", "-");
        slug = slug.replaceAll("(^-|-$)", "");
        return slug;
    }

}
