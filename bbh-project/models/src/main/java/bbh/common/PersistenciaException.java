package bbh.common;

public class PersistenciaException extends Exception {

    public PersistenciaException(String msg, Exception exception) {
        super(msg, exception);
    }

    public PersistenciaException(String msg) {
        super(msg);
    }
}
