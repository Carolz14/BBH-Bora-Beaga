package bbh.service;

import bbh.common.PersistenciaException;
import bbh.domain.Comentario;
import bbh.dao.ComentarioRoteiroDAO;
import java.util.List;

public class GestaoComentarioRoteiroService {
 private final ComentarioRoteiroDAO comentarioDAO;

    public GestaoComentarioRoteiroService() {
        this.comentarioDAO = ComentarioRoteiroDAO.getInstance();
      
    }

    public void inserirComentario(Comentario comentario) throws PersistenciaException {
        comentarioDAO.inserir(comentario);
    }

    public void excluirComentario(Long comentarioId, Long usuarioId) throws PersistenciaException {
        comentarioDAO.excluir(comentarioId, usuarioId);
    }

    
    public Comentario pesquisarComentario(Long id) throws PersistenciaException {
        return comentarioDAO.pesquisar(id); 
    }
     public List<Comentario> listarComentarios(Long roteiroId) throws PersistenciaException {
        return comentarioDAO.listarComentarios(roteiroId); 
    }
}
