package bbh.service.util;

import bbh.common.PersistenciaException;
import bbh.dao.MidiaAvaliacaoDAO;
import bbh.service.MidiaAvaliacaoService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.lang.reflect.Method;

@WebListener
public class InicializadorDB implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(">>> Iniciando Aplicação BBH...");
        //Inicializa o Banco de Dados
        try {
            CriarTabelas.criarTodasAsTabelas();
            System.out.println("Banco de dados verificado/criado com sucesso.");
        } catch (SQLException e) {
            System.err.println("CRITICO: Erro SQL na inicialização do DB: " + e.getMessage());
            e.printStackTrace();
        } catch (PersistenciaException ex) {
            System.err.println("CRITICO: Erro de Persistência na inicialização do DB: " + ex.getMessage());
            ex.printStackTrace();
        }
        // Serviço de Mídia
        try {
            MidiaAvaliacaoService midiaService = new MidiaAvaliacaoService(
                new MidiaAvaliacaoDAO(),
                Paths.get("C:/Projetos/BBH-Bora-Beaga/uploads"), 
                10L * 1024L * 1024L 
            );
            
            sce.getServletContext().setAttribute("midiaService", midiaService);
            System.out.println("Service de Mídia configurado com sucesso.");
            
        } catch (Exception e) {
            System.err.println("Erro ao configurar serviço de mídia: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(">>> Encerrando Aplicação BBH...");
        try {
            // "Desregistra" drivers manualmente
            Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                java.sql.Driver driver = drivers.nextElement();
                if (driver.getClass().getName().contains("mysql")) {
                    DriverManager.deregisterDriver(driver);
                    System.out.println("Driver MySQL desregistrado.");
                }
            }
            try {
                Class<?> cls = Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread");
                Method method = cls.getMethod("checkedShutdown");
                method.invoke(null);
                System.out.println("Thread de limpeza MySQL encerrada (via Reflection).");
            } catch (ClassNotFoundException e) {
                System.out.println("CleanupThread do MySQL não encontrada ou não necessária.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao desregistrar driver MySQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao parar thread de limpeza: " + e.getMessage());
        }
    }
}