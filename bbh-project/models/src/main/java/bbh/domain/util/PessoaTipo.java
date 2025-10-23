package bbh.domain.util;

public enum PessoaTipo {
   ADMINISTRADOR,
   TURISTA,
   GUIA,
   ESTABELECIMENTO;


   public static PessoaTipo strTo(String strTipo) {
      switch (strTipo) {
         case "ADMINISTRADOR" :
            return ADMINISTRADOR;
         case "TURISTA" :
            return TURISTA;
         case "GUIA" :
            return GUIA;
         case "ESTABELECIMENTO" :
            return ESTABELECIMENTO;
      }

      throw new RuntimeException("O tipo:" + strTipo + " nao e valido");
   }


}
