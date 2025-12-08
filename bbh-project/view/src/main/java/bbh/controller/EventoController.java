package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Evento;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.EventoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/evento")
public class EventoController extends HttpServlet {

    private final EventoService service = new EventoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {

            if (action.equals("detalhe")) {

                Long id = parseLong(req.getParameter("id"));

                if (id == null) {
                    req.setAttribute("erro", "Evento inválido.");
                    req.getRequestDispatcher("/jsps/turista/eventos.jsp").forward(req, resp);
                    return;
                }

                Evento evento = service.buscarPorId(id);
                req.setAttribute("evento", evento);

                req.getRequestDispatcher("/jsps/turista/detalhe-evento.jsp").forward(req, resp);
                return;
            }

            if (action.equals("gerenciar")) {

                if (usuario == null || !UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendRedirect(req.getContextPath() + "/evento");
                    return;
                }

                Long estabId = usuario.getId();

                req.setAttribute("meusEventos", service.listarMeusEventos(estabId));
                req.getRequestDispatcher("/jsps/estabelecimento/eventos.jsp").forward(req, resp);
                return;
            }

            if (action.equals("carregarEdicao")) {

                if (usuario == null || !UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendError(403);
                    return;
                }

                Long id = parseLong(req.getParameter("id"));

                Evento e = service.buscarPorId(id);
                req.setAttribute("eventoEdit", e);
                req.setAttribute("meusEventos", service.listarMeusEventos(usuario.getId()));

                req.getRequestDispatcher("/jsps/estabelecimento/eventos.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("proximos4", service.buscarProximos4());
            req.setAttribute("eventosFuturos", service.listarEventosFuturos());

            req.getRequestDispatcher("/jsps/turista/eventos.jsp").forward(req, resp);

        } catch (PersistenciaException e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/jsps/turista/eventos.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String action = req.getParameter("action");

        try {

            if ("add".equals(action)) {

                if (usuario == null || !UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendError(403);
                    return;
                }

                Evento e = new Evento();
                e.setNome(req.getParameter("nome"));
                e.setDescricao(req.getParameter("descricao"));
                e.setData(LocalDate.parse(req.getParameter("dataEvento")));
                e.setHorario(LocalTime.parse(req.getParameter("horarioEvento")));
                e.setEstabelecimentoId(usuario.getId());

                service.criarEvento(e);

                resp.sendRedirect("evento?action=gerenciar");
                return;
            }

            if ("update".equals(action)) {

                if (usuario == null || !UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendError(403);
                    return;
                }

                Long id = parseLong(req.getParameter("id"));

                Evento e = new Evento();
                e.setId(id);
                e.setNome(req.getParameter("nome"));
                e.setDescricao(req.getParameter("descricao"));
                e.setData(LocalDate.parse(req.getParameter("dataEvento")));
                e.setHorario(LocalTime.parse(req.getParameter("horarioEvento")));
                e.setEstabelecimentoId(usuario.getId());

                service.atualizarEvento(e);

                resp.sendRedirect("evento?action=gerenciar");
                return;
            }

            if ("delete".equals(action)) {

                if (usuario == null || !UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendError(403);
                    return;
                }

                Long id = parseLong(req.getParameter("id"));
                service.excluirEvento(id, usuario.getId());

                resp.sendRedirect("evento?action=gerenciar");
            }

        } catch (PersistenciaException e) {
            req.setAttribute("erro", e.getMessage());
            req.getRequestDispatcher("/jsps/estabelecimento/eventos.jsp").forward(req, resp);
        }
    }

    private Long parseLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            return null;
        }
    }
}