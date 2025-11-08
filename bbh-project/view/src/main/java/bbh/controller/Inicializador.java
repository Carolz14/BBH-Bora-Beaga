package bbh.controller;
import bbh.service.util.InitDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1, urlPatterns = "/init")
public class Inicializador extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("Inicializando banco de dados BBH");
        InitDB.inicializar();
        System.out.println("Banco de dados inicializado com sucesso.");
    }
}
