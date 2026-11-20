package gui;

import excepciones.DatoInvalidoException;
import excepciones.StockInsuficienteException;
import hilos.ProcesoRespaldoTarea;
import modelo.RegistroOperaciones;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JTextField txtCantidad;
    private JButton btnProcesar;
    private JButton btnHilo;
    private JLabel lblEstado;

    public VentanaPrincipal() {
        setTitle("Sistema Modular - Laboratorio 3");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel central con GridLayout organizado
        JPanel panelCentral = new JPanel(new GridLayout(3, 2, 5, 5));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelCentral.add(new JLabel("Ingrese cantidad de producto:"));
        txtCantidad = new JTextField();
        panelCentral.add(txtCantidad);

        btnProcesar = new JButton("Procesar Venta / Validar");
        panelCentral.add(btnProcesar);

        btnHilo = new JButton("Ejecutar Respaldo (Hilo)");
        panelCentral.add(btnHilo);

        lblEstado = new JLabel("Estado del sistema: En espera");
        panelCentral.add(lblEstado);

        add(panelCentral, BorderLayout.CENTER);

        // Eventos con expresiones Lambda
        btnProcesar.addActionListener(e -> validarDatos());
        btnHilo.addActionListener(e -> iniciarHiloRespaldo());
    }

    private void validarDatos() {
        // Uso de try-with-resources, try-catch múltiple y excepciones personalizadas
        try (RegistroOperaciones registro = new RegistroOperaciones()) {
            String texto = txtCantidad.getText().trim();
            
            if (texto.isEmpty()) {
                throw new DatoInvalidoException("El campo de cantidad no puede estar vacío.");
            }
            
            int cantidad;
            try {
                cantidad = Integer.parseInt(texto);
            } catch (NumberFormatException ex) {
                throw new DatoInvalidoException("Debe ingresar un valor numérico entero válido.");
            }

            if (cantidad <= 0) {
                throw new DatoInvalidoException("La cantidad debe ser mayor a cero.");
            }

            int stockActual = 5;
            if (cantidad > stockActual) {
                throw new StockInsuficienteException("No hay suficiente stock disponible.", stockActual);
            }

            registro.registrar("Operación exitosa con cantidad: " + cantidad);
            JOptionPane.showMessageDialog(this, "¡Operación procesada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (DatoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Advertencia de Datos", JOptionPane.WARNING_MESSAGE);
        } catch (StockInsuficienteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage() + "\nStock actual en tienda: " + ex.getStockDisponible(), "Error de Stock", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error inesperado: " + ex.getMessage(), "Error Crítico", JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println("[FINALLY]: Bloque finally ejecutado correctamente.");
        }
    }

    private void iniciarHiloRespaldo() {
        lblEstado.setText("Iniciando tarea en hilo...");
        ProcesoRespaldoTarea tarea = new ProcesoRespaldoTarea(lblEstado);
        Thread hilo = new Thread(tarea);
        hilo.start();
    }
}