package bbh.common;

public class NaoEncontradoException extends Exception {
    public NaoEncontradoException(String msg, Exception exception) {
        super(msg, exception);
    }

    public NaoEncontradoException(String msg) {
        super(msg);
    }
}
