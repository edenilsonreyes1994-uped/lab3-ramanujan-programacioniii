package hilos;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class SincronizacionInventarioTarea implements Runnable {
    private JLabel lblEstadoHilo;
    private JButton btnSincronizar;

    public SincronizacionInventarioTarea(JLabel lblEstadoHilo, JButton btnSincronizar) {
        this.lblEstadoHilo = lblEstadoHilo;
        this.btnSincronizar = btnSincronizar;
    }

    @Override
    public void run() {
        try {
            SwingUtilities.invokeLater(() -> btnSincronizar.setEnabled(false));

            String[] pasos = {
                "Conectando con servidor central del Minimarket...",
                "Verificando lotes perecederos y no perecederos...",
                "Guardando respaldo de inventario en la nube..."
            };

            for (int i = 0; i < pasos.length; i++) {
                final String mensaje = pasos[i];
                SwingUtilities.invokeLater(() -> lblEstadoHilo.setText("Estado Hilo: " + mensaje));
                Thread.sleep(1200); // Simula latencia de red sin congelar la GUI
            }

            SwingUtilities.invokeLater(() -> {
                lblEstadoHilo.setText("Estado Hilo: ¡Inventario sincronizado correctamente!");
                btnSincronizar.setEnabled(true);
            });

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            SwingUtilities.invokeLater(() -> {
                lblEstadoHilo.setText("Estado Hilo: Sincronización interrumpida.");
                btnSincronizar.setEnabled(true);
            });
        }
    }
}