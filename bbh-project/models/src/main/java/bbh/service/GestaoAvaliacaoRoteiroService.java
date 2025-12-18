package bbh.service;

import bbh.common.PersistenciaException;
import bbh.dao.AvaliacaoRoteiroDAO;


public class GestaoAvaliacaoRoteiroService {
    private final AvaliacaoRoteiroDAO avaliacaoDAO;

    public GestaoAvaliacaoRoteiroService() {
        this.avaliacaoDAO = AvaliacaoRoteiroDAO.getInstance();
      
    }

    public void avaliarRoteiro(Long roteiroId, Long usuarioId, int nota) throws PersistenciaException {
        avaliacaoDAO.avaliar(roteiroId, usuarioId, nota);
    }

    public double mediaRoteiro (Long roteiroId) throws PersistenciaException  {
       return avaliacaoDAO.media(roteiroId); 
    }
    public int pesquisarNota (Long roteiroId, Long usuarioId) throws  PersistenciaException  {
       return avaliacaoDAO.pesquisarNota(roteiroId, usuarioId); 
    }
}
