package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Evento;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.EventoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet(name = "EventoController", urlPatterns = {"/evento"})
public class EventoController extends HttpServlet {

    private final EventoService service = new EventoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processar(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processar(req, resp);
    }

    private void processar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession(false);
        Usuario usuarioLogado = null;
        UsuarioTipo tipo = null;
        if (session != null) {
            usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
            if (usuarioLogado != null) {
                tipo = usuarioLogado.getUsuarioTipo();
            }
        }

        try {
            if ("add".equals(action)) {
                // só estabelecimento pode criar eventos
                requireTipo(tipo, UsuarioTipo.ESTABELECIMENTO, resp);
                Evento e = montarEventoParaCriar(req, usuarioLogado);
                service.criarEvento(e);
                req.setAttribute("msg", "Evento criado com sucesso.");
                // após criar tenho que mostrar gestão do estabelecimento
                List<Evento> meus = service.listarMeusEventos(usuarioLogado.getId());
                req.setAttribute("meusEventos", meus);
                req.getRequestDispatcher("/jsps/eventos/eventos.jsp").forward(req, resp);
                return;

            } else if ("update".equals(action)) {
                requireTipo(tipo, UsuarioTipo.ESTABELECIMENTO, resp);
                Evento e = montarEventoParaAtualizar(req, usuarioLogado);
                service.atualizarEvento(e);
                req.setAttribute("msg", "Evento atualizado.");
                List<Evento> meus = service.listarMeusEventos(usuarioLogado.getId());
                req.setAttribute("meusEventos", meus);
                req.getRequestDispatcher("/jsps/eventos/eventos.jsp").forward(req, resp);
                return;

            } else if ("delete".equals(action)) {
                requireTipo(tipo, UsuarioTipo.ESTABELECIMENTO, resp);
                Long id = parseLong(req.getParameter("id"));
                service.excluirEvento(id, usuarioLogado.getId());
                req.setAttribute("msg", "Evento removido.");
                List<Evento> meus = service.listarMeusEventos(usuarioLogado.getId());
                req.setAttribute("meusEventos", meus);
                req.getRequestDispatcher("/jsps/eventos/eventos.jsp").forward(req, resp);
                return;
                
            } else {
                // listagem — comportamento depende do tipo do usuário
                if (tipo == UsuarioTipo.TURISTA) {
                    List<Evento> proximos4 = service.buscarProximos4();
                    List<Evento> todos = service.listarEventosFuturos();
                    req.setAttribute("proximos4", proximos4);
                    req.setAttribute("eventosFuturos", todos);
                } else if (tipo == UsuarioTipo.ESTABELECIMENTO) {
                    // estabelecimento gerencia seus eventos
                    if (usuarioLogado == null) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não autenticado.");
                        return;
                    }
                    List<Evento> meus = service.listarMeusEventos(usuarioLogado.getId());
                    req.setAttribute("meusEventos", meus);
                } else {
                    // ADMIN ou outros: não expõe eventos públicos
                    req.setAttribute("proximos4", null);
                    req.setAttribute("eventosFuturos", null);
                }
                req.getRequestDispatcher("/jsps/eventos/eventos.jsp").forward(req, resp);
                return;
            }
        } catch (PersistenciaException pe) {
            req.setAttribute("erro", pe.getMessage());
            try {
                req.getRequestDispatcher("/jsps/eventos/eventos.jsp").forward(req, resp);
            } catch (Exception ex) {
                throw new ServletException(ex);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void requireTipo(UsuarioTipo tipo, UsuarioTipo esperado, HttpServletResponse resp) throws IOException {
        if (tipo == null || tipo != esperado) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Você não tem permissão para executar essa ação.");
        }
    }

    private Evento montarEventoParaCriar(HttpServletRequest req, Usuario usuarioLogado) throws PersistenciaException {
        Evento e = new Evento();
        e.setEstabelecimentoId(usuarioLogado.getId());
        e.setNome(req.getParameter("nome"));
        e.setDescricao(req.getParameter("descricao"));
        String dataStr = req.getParameter("dataEvento");
        String horarioStr = req.getParameter("horarioEvento");

        if (dataStr != null && !dataStr.isBlank())
            e.setData(LocalDate.parse(dataStr));
        if (horarioStr != null && !horarioStr.isBlank())
            e.setHorario(LocalTime.parse(horarioStr));

        return e;
    }

    private Evento montarEventoParaAtualizar(HttpServletRequest req, Usuario usuarioLogado) throws PersistenciaException {
        Evento e = new Evento();
        e.setId(parseLong(req.getParameter("id")));
        e.setEstabelecimentoId(usuarioLogado.getId());
        e.setNome(req.getParameter("nome"));
        e.setDescricao(req.getParameter("descricao"));

        String dataStr = req.getParameter("dataEvento");
        String horarioStr = req.getParameter("horarioEvento");

        if (dataStr != null && !dataStr.isBlank())
            e.setData(LocalDate.parse(dataStr));
        if (horarioStr != null && !horarioStr.isBlank())
            e.setHorario(LocalTime.parse(horarioStr));

        return e;
    }

    private Long parseLong(String s) {
        if (s == null) return null;
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
