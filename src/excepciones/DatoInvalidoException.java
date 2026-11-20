package excepciones;

// Excepción Unchecked (hereda de RuntimeException) para reglas de entrada
public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}