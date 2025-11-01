package bbh.service;

public class teste {
    public static void main(String[] args) {
        String stringTeste = "Leandro";
        System.out.println(stringTeste);
        System.out.println(TagService.gerarSlug(stringTeste));
    }
}
