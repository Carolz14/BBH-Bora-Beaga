package bbh.service;

public class TagService {
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
