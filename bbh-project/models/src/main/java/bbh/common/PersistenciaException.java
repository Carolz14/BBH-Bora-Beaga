package bbh.common;

public class PersistenciaException extends Exception {
    public PersistenciaException(String msg) {
        super(msg); 
    }
    public PersistenciaException(String msg, Throwable cause) {
        super(msg, cause); 
    }
}
