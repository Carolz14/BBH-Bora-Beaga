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
            throw new RuntimeException("Falha ao criar tabelas do DB na inicialização" + e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicação encerrada.");
    }
}
