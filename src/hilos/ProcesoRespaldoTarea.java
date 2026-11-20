package hilos;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

// Tarea concurrente ejecutada con Runnable
public class ProcesoRespaldoTarea implements Runnable {
    private JLabel etiquetaEstado;

    public ProcesoRespaldoTarea(JLabel etiquetaEstado) {
        this.etiquetaEstado = etiquetaEstado;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 3; i++) {
                final int paso = i;
                // Actualización segura de Swing desde un hilo secundario
                SwingUtilities.invokeLater(() -> {
                    if (etiquetaEstado != null) {
                        etiquetaEstado.setText("Respaldo en segundo plano... Paso " + paso + "/3");
                    }
                });
                Thread.sleep(1000); // Simula proceso pesado de guardado
            }
            SwingUtilities.invokeLater(() -> {
                if (etiquetaEstado != null) {
                    etiquetaEstado.setText("¡Respaldo automático completado con éxito!");
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}