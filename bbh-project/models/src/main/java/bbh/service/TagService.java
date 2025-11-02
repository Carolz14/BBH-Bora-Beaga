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
    public List<Tag> listarTagsDoUsuario(Long idUsuario) throws PersistenciaException{
        if(idUsuario == null){
            throw new PersistenciaException("Erro na inserção do id do usuário");
        }
        return tagCorrespondenciaDAO.listarTagsDoEstabelecimento(idUsuario);
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
