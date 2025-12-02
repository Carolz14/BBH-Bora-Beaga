package bbh.service.util;

public class MidiaAvaliacaoUtil {
    public static String extrairExtensao(String nomeArquivo) {
        if (nomeArquivo == null) {
            return "";
        }
        int ponto = nomeArquivo.lastIndexOf(".");
        if (ponto >= 0 && ponto < nomeArquivo.length() - 1) {
            return nomeArquivo.substring(ponto).toLowerCase();
        }
        return "";
    }

    public static String extrairDeMime(String mime){
        if (mime == null)
            return "";
        switch (mime.toLowerCase()) {
            case "image/jpeg":
                return "jpg";
            case "image/pjpeg":
                return "jpg";
            case "image/png":
                return "png";
            case "image/gif":
                return "gif";
            case "image/webp":
                return "webp";
            default:
                int i = mime.indexOf('/');
                if (i >= 0 && i < mime.length() - 1)
                    return mime.substring(i + 1);
                return "";
        }
    }
}
