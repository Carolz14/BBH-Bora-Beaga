package bbh.dao;

import bbh.common.PersistenciaException;

public interface GenericDAO<T, K> {
    void inserir(T entidade) throws PersistenciaException;
    T pesquisar(K chave) throws PersistenciaException;
}
