package bbh.controller;

import bbh.common.PersistenciaException;
import bbh.domain.Ticket;
import bbh.domain.TicketMensagem;
import bbh.domain.Usuario;
import bbh.domain.util.UsuarioTipo;
import bbh.service.SuporteService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/bbh/suporte")
public class SuporteController extends HttpServlet {

    private final SuporteService service = new SuporteService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Usuario usuario = session != null ? (Usuario) session.getAttribute("usuario") : null;
        String acao = req.getParameter("acao");
        Long id = parseLong(req.getParameter("id"));

        try {
            if (usuario == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            if ("mensagens".equals(acao)) {
                if (id == null) {
                    resp.sendError(400);
                    return;
                }
                List<TicketMensagem> msgs = service.listarMensagens(id);
                resp.setContentType("application/json;charset=UTF-8");
                try (PrintWriter out = resp.getWriter()) {
                    out.print(buildJsonFromMensagens(msgs));
                }
                return;
            }

            if ("ver".equals(acao) || id != null) {
                if (id == null) {
                    resp.sendRedirect(req.getContextPath() + "/bbh/suporte");
                    return;
                }
                Ticket ticket = service.buscarPorId(id);
                if (ticket == null) {
                    resp.sendRedirect(req.getContextPath() + "/bbh/suporte");
                    return;
                }
                req.setAttribute("ticket", ticket);
                if (UsuarioTipo.ADMINISTRADOR.equals(usuario.getUsuarioTipo())) {
                    req.getRequestDispatcher("/jsps/admin/detalhe-ticket-admin.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/jsps/estabelecimento/detalhe-ticket.jsp").forward(req, resp);
                }
                return;
            }

            if (UsuarioTipo.ADMINISTRADOR.equals(usuario.getUsuarioTipo())) {
                req.setAttribute("tickets", service.listarTodos());
                req.getRequestDispatcher("/jsps/admin/suporte-admin.jsp").forward(req, resp);
                return;
            } else {
                req.setAttribute("meusTickets", service.listarPorUsuario(usuario.getId()));
                req.getRequestDispatcher("/jsps/estabelecimento/suporte.jsp").forward(req, resp);
                return;
            }

        } catch (PersistenciaException e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Usuario usuario = session != null ? (Usuario) session.getAttribute("usuario") : null;
        String acao = req.getParameter("acao");

        try {
            if (usuario == null) {
                resp.sendError(403);
                return;
            }

            if ("abrir".equals(acao) || "create".equals(acao)) {
                Ticket t = new Ticket();
                t.setUsuarioId(usuario.getId());
                t.setUsuarioEmail(usuario.getEmail());
                t.setAssunto(req.getParameter("titulo") != null ? req.getParameter("titulo") : req.getParameter("assunto"));
                t.setMensagem(req.getParameter("mensagem"));
                service.criarTicket(t);
                resp.sendRedirect(req.getContextPath() + "/bbh/suporte");
                return;
            }

            if ("mensagem".equals(acao)) {
                Long ticketId = parseLong(req.getParameter("ticketId"));
                String texto = req.getParameter("mensagem");
                if (ticketId == null || texto == null || texto.isBlank()) {
                    resp.sendError(400);
                    return;
                }
                String autorTipo = UsuarioTipo.ADMINISTRADOR.equals(usuario.getUsuarioTipo()) ? "ADMIN" : "USUARIO";
                service.enviarMensagem(ticketId, usuario.getId(), autorTipo, texto);
                resp.sendRedirect(req.getContextPath() + "/bbh/suporte?acao=ver&id=" + ticketId);
                return;
            }

            if ("concluir".equals(acao) && UsuarioTipo.ADMINISTRADOR.equals(usuario.getUsuarioTipo())) {
                Long ticketId = parseLong(req.getParameter("id"));
                if (ticketId != null) {
                    service.concluir(ticketId);
                }
                resp.sendRedirect(req.getContextPath() + "/bbh/suporte");
                return;
            }

            resp.sendError(400);

        } catch (PersistenciaException e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        }
    }

    private Long parseLong(String s) {
        try {
            return s == null ? null : Long.parseLong(s);
        } catch (Exception ex) {
            return null;
        }
    }

    private String buildJsonFromMensagens(List<TicketMensagem> msgs) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (msgs != null) {
            boolean first = true;
            for (TicketMensagem m : msgs) {
                if (!first) {
                    sb.append(",");
                }
                first = false;
                sb.append("{");
                sb.append("\"id\":").append(m.getId() == null ? "null" : m.getId()).append(",");
                sb.append("\"autorId\":").append(m.getAutorId() == null ? "null" : m.getAutorId()).append(",");
                sb.append("\"autorTipo\":\"").append(escapeJson(m.getAutorTipo())).append("\",");
                sb.append("\"mensagem\":\"").append(escapeJson(m.getMensagem())).append("\",");
                sb.append("\"dataFormatada\":\"").append(escapeJson(m.getDataFormatada())).append("\"");
                sb.append("}");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String escapeJson(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}