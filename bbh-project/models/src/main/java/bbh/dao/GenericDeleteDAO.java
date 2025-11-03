package bbh.dao;

import bbh.common.PersistenciaException;


public interface GenericDeleteDAO<T, K> extends GenericDAO<T, K>{
    void delete(K chave) throws PersistenciaException;
}
