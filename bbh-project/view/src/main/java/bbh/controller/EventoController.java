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

        try {

            if (usuario != null && 
                UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo()) &&
                (action == null || action.isEmpty())) 
            {

                resp.sendRedirect(req.getContextPath() + "/evento?action=gerenciar");
                return;
            }

            if ("detalhe".equals(action)) {

                Long id = parseLong(req.getParameter("id"));
                if (id == null) {
                    req.setAttribute("erro", "Id do evento inválido.");
                    req.getRequestDispatcher("/jsps/turista/eventos.jsp").forward(req, resp);
                    return;
                }

                Evento evento = service.buscarPorId(id);
                req.setAttribute("evento", evento);
                req.getRequestDispatcher("/jsps/turista/detalhe-evento.jsp").forward(req, resp);
                return;
            }

            if ("gerenciar".equals(action)) {

                if (!UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendRedirect(req.getContextPath() + "/evento");
                    return;
                }

                Long estabId = usuario.getId();
                req.setAttribute("meusEventos", service.listarMeusEventos(estabId));

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

                if (!UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendError(403);
                    return;
                }

                Evento evento = new Evento();
                evento.setNome(req.getParameter("nome"));
                evento.setDescricao(req.getParameter("descricao"));
                evento.setData(LocalDate.parse(req.getParameter("dataEvento")));
                evento.setHorario(LocalTime.parse(req.getParameter("horarioEvento")));
                evento.setEstabelecimentoId(usuario.getId());

                service.criarEvento(evento);

                resp.sendRedirect("evento?action=gerenciar");
                return;
            }

            if ("update".equals(action)) {

                if (!UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
                    resp.sendError(403);
                    return;
                }

                Long id = parseLong(req.getParameter("id"));
                if (id == null) {
                    req.setAttribute("erro", "Id inválido.");
                    req.getRequestDispatcher("/jsps/estabelecimento/eventos.jsp").forward(req, resp);
                    return;
                }

                Evento evento = new Evento();
                evento.setId(id);
                evento.setNome(req.getParameter("nome"));
                evento.setDescricao(req.getParameter("descricao"));
                evento.setData(LocalDate.parse(req.getParameter("dataEvento")));
                evento.setHorario(LocalTime.parse(req.getParameter("horarioEvento")));
                evento.setEstabelecimentoId(usuario.getId());

                service.atualizarEvento(evento);

                resp.sendRedirect("evento?action=gerenciar");
                return;
            }

            if ("delete".equals(action)) {

                if (!UsuarioTipo.ESTABELECIMENTO.equals(usuario.getUsuarioTipo())) {
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