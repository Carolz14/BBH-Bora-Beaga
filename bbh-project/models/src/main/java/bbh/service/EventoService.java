package bbh.service;

import bbh.common.PersistenciaException;
import bbh.domain.Evento;
import bbh.dao.EventoDAO;

import java.time.LocalDate;
import java.util.List;

public class EventoService {

    private final EventoDAO dao = EventoDAO.getInstance();

    public Long criarEvento(Evento evento) throws PersistenciaException {
        validarEventoBasico(evento, true);
        return dao.inserir(evento);
    }

    public void atualizarEvento(Evento evento) throws PersistenciaException {
        validarEventoBasico(evento, false);
        dao.atualizar(evento);
    }

    public void excluirEvento(Long idEvento, Long estabelecimentoId) throws PersistenciaException {
        if (idEvento == null) {
            throw new PersistenciaException("Id do evento não informado.");
        }
        if (estabelecimentoId == null) {
            throw new PersistenciaException("Id do estabelecimento não informado.");
        }
        dao.excluirLogico(idEvento, estabelecimentoId);
    }

    public Evento buscarPorId(Long id) throws PersistenciaException {
        return dao.buscarPorId(id);
    }

    public List<Evento> buscarProximos4() throws PersistenciaException {
        return dao.buscarProximos4();
    }

    public List<Evento> listarEventosFuturos() throws PersistenciaException {
        return dao.listarEventosFuturos();
    }

    public List<Evento> listarMeusEventos(Long estabelecimentoId) throws PersistenciaException {
        if (estabelecimentoId == null) {
            throw new PersistenciaException("Id do estabelecimento não informado.");
        }
        return dao.listarMeusEventos(estabelecimentoId);
    }

    private void validarEventoBasico(Evento evento, boolean novo) throws PersistenciaException {
        if (evento == null) {
            throw new PersistenciaException("Evento inválido.");
        }
        if (!novo && evento.getId() == null) {
            throw new PersistenciaException("Id do evento é obrigatório para atualização.");
        }
        if (evento.getNome() == null || evento.getNome().trim().isEmpty()) {
            throw new PersistenciaException("Nome do evento é obrigatório.");
        }
        if (evento.getData() == null) {
            throw new PersistenciaException("Data do evento é obrigatória.");
        }
        LocalDate hoje = LocalDate.now();
        if (evento.getData().isBefore(hoje)) {
            throw new PersistenciaException("A data do evento deve ser hoje ou futura.");
        }
        if (evento.getEstabelecimentoId() == null) {
            throw new PersistenciaException("Evento deve estar associado a um estabelecimento.");
        }
    }
}
