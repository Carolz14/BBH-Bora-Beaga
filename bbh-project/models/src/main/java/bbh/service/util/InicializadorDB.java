package bbh.service.util; // Adapte o pacote conforme a localização da classe

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class InicializadorDB implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            CriarTabelas.criarTodasAsTabelas();
            System.out.println("Tabelas verificadas/criadas com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao criar as tabelas do DB na inicialização");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicação encerrada.");
    }
}
