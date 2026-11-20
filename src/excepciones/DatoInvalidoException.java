package excepciones;

// Excepción Unchecked para validación de entradas de usuario
public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}