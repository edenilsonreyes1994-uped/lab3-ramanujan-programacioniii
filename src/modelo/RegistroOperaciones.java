package modelo;

// Recurso personalizado que implementa AutoCloseable para try-with-resources
public class RegistroOperaciones implements AutoCloseable {
    
    public RegistroOperaciones() {
        System.out.println("[LOG]: Recurso de registro abierto correctamente.");
    }

    public void registrar(String operacion) {
        System.out.println("[REGISTRANDO]: " + operacion);
    }

    @Override
    public void close() {
        System.out.println("[LOG]: Recurso de registro cerrado automáticamente (AutoCloseable).");
    }
}