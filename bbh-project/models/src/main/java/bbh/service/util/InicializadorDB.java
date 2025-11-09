package bbh.service.util;

import bbh.common.PersistenciaException;
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
            System.out.println("Erro ao criar as tabelas do DB na inicialização" + e.getMessage() + e);
        } catch (PersistenciaException ex) {
            System.out.println("Erro ao criar tabelas do DB na inicialização" + ex.getMessage() + ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicação encerrada.");
    }
}
