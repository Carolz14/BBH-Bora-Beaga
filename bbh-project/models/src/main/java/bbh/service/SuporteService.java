package bbh.service;

import bbh.common.PersistenciaException;
import bbh.dao.TicketDAO;
import bbh.dao.TicketMensagemDAO;
import bbh.domain.Ticket;
import bbh.domain.TicketMensagem;

import java.util.List;

public class SuporteService {

    private final TicketDAO ticketDAO = TicketDAO.getInstance();
    private final TicketMensagemDAO mensagemDAO = TicketMensagemDAO.getInstance();

    public Long criarTicket(Ticket t) throws PersistenciaException {
        if (t == null) {
            throw new PersistenciaException("Ticket inválido.");
        }
        if (t.getUsuarioId() == null) {
            throw new PersistenciaException("Usuário não informado.");
        }
        if (t.getAssunto() == null || t.getAssunto().trim().isEmpty()) {
            throw new PersistenciaException("Assunto obrigatório.");
        }
        if (t.getMensagem() == null || t.getMensagem().trim().isEmpty()) {
            throw new PersistenciaException("Mensagem obrigatória.");
        }
        return ticketDAO.inserir(t);
    }

    public List<Ticket> listarTodos() throws PersistenciaException {
        return ticketDAO.listarTodos();
    }

    public List<Ticket> listarPorUsuario(Long usuarioId) throws PersistenciaException {
        if (usuarioId == null) {
            throw new PersistenciaException("Usuário não informado.");
        }
        return ticketDAO.listarPorUsuario(usuarioId);
    }

    public Ticket buscarPorId(Long id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("Id não informado.");
        }
        return ticketDAO.buscarPorId(id);
    }

    public void concluir(Long id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("Id não informado.");
        }
        ticketDAO.marcarConcluido(id);
    }

    public void enviarMensagem(Long ticketId, Long autorId, String autorTipo, String mensagem) throws PersistenciaException {
        if (ticketId == null) {
            throw new PersistenciaException("Ticket inválido.");
        }
        if (mensagem == null || mensagem.isBlank()) {
            throw new PersistenciaException("Mensagem vazia.");
        }

        TicketMensagem tm = new TicketMensagem();
        tm.setTicketId(ticketId);
        tm.setAutorId(autorId);
        tm.setAutorTipo(autorTipo);
        tm.setMensagem(mensagem);

        mensagemDAO.inserir(tm);

        Ticket t = ticketDAO.buscarPorId(ticketId);
        if (t != null && !"CONCLUIDO".equalsIgnoreCase(t.getStatus())) {
            if ("ADMIN".equalsIgnoreCase(autorTipo)) {
                ticketDAO.atualizarStatus(ticketId, "EM_ANDAMENTO");
            }
        }
    }

    public List<TicketMensagem> listarMensagens(Long ticketId) throws PersistenciaException {
        if (ticketId == null) {
            throw new PersistenciaException("Ticket inválido.");
        }
        return mensagemDAO.listarPorTicket(ticketId);
    }
}