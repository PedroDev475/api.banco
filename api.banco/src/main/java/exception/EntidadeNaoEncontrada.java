package exception;

public class EntidadeNaoEncontrada extends RuntimeException {
    public EntidadeNaoEncontrada(String message) {
        super(message);
    }
}
