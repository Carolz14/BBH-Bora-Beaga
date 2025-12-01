package bbh.controller;

import bbh.dao.MidiaAvaliacaoDAO;
import bbh.service.MidiaAvaliacaoService;
import bbh.service.util.InitDB;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class InicializadorListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Inicializando banco de dados BBH");
        InitDB.inicializar();
        System.out.println("Banco de dados inicializado com sucesso.");
        MidiaAvaliacaoService service = new MidiaAvaliacaoService(new MidiaAvaliacaoDAO(),
                Paths.get("C:/Projetos/BBH-Bora-Beaga/uploads"),
                10L * 1024L * 1024L);
        sce.getServletContext().setAttribute("midiaService", service);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicação encerrada. Desregistrando driver MySQL...");
        try {
            java.util.Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                java.sql.Driver driver = drivers.nextElement();
                if (driver.getClass().getName().equals("com.mysql.cj.jdbc.Driver")) {
                    DriverManager.deregisterDriver(driver);
                    break;
                }
            }
            AbandonedConnectionCleanupThread.checkedShutdown();

            System.out.println("Recursos MySQL liberados com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao desregistrar driver MySQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao parar a thread de limpeza: " + e.getMessage());
        }
    }
}