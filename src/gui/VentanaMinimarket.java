package gui;

import excepciones.DatoInvalidoException;
import excepciones.StockInsuficienteException;
import hilos.SincronizacionInventarioTarea;
import modelo.ComprobanteVenta;
import modelo.Producto;
import modelo.ProductoNoPerecedero;
import modelo.ProductoPerecedero;

import javax.swing.*;
import java.awt.*;

public class VentanaMinimarket extends JFrame {
    // Objetos reales de tu dominio (Labs I y II)
    private ProductoPerecedero leche;
    private ProductoNoPerecedero arroz;

    // Componentes Swing
    private JComboBox<String> cmbProductos;
    private JTextField txtCantidad;
    private JLabel lblStockActual;
    private JLabel lblEstadoHilo;
    private JTextArea txtAreaFactura;
    private JButton btnVender;
    private JButton btnAgregarStock;
    private JButton btnSincronizar;

    public VentanaMinimarket() {
        // Inicializamos los productos del minimarket
        leche = new ProductoPerecedero("P001", "Leche Entera", 1.20, 10, "20-10-2026");
        arroz = new ProductoNoPerecedero("N001", "Arroz San Pedro", 0.90, 15, 12);

        configurarVentana();
        inicializarComponentes();
        actualizarEtiquetaStock();
    }

    private void configurarVentana() {
        setTitle("Sistema de Minimarket - Laboratorio III (Swing, Excepciones e Hilos)");
        setSize(580, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // PANEL SUPERIOR: Formulario con GridLayout
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Ventas e Inventario"));

        panelFormulario.add(new JLabel("Seleccione Producto:"));
        cmbProductos = new JComboBox<>(new String[]{
            "P001 - Leche Entera (Perecedero)",
            "N001 - Arroz San Pedro (No Perecedero)"
        });
        panelFormulario.add(cmbProductos);

        panelFormulario.add(new JLabel("Disponibilidad en tienda:"));
        lblStockActual = new JLabel();
        lblStockActual.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelFormulario.add(lblStockActual);

        panelFormulario.add(new JLabel("Cantidad (Unidades):"));
        txtCantidad = new JTextField();
        panelFormulario.add(txtCantidad);

        btnVender = new JButton("Realizar Venta");
        btnAgregarStock = new JButton("Agregar Stock");
        panelFormulario.add(btnVender);
        panelFormulario.add(btnAgregarStock);

        add(panelFormulario, BorderLayout.NORTH);

        // PANEL CENTRAL: Área de visualización polimórfica (Facturable)
        txtAreaFactura = new JTextArea();
        txtAreaFactura.setEditable(false);
        txtAreaFactura.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtAreaFactura);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Detalle de Facturación Polimórfica"));
        add(scrollPane, BorderLayout.CENTER);

        // PANEL INFERIOR: Control de Hilos (FlowLayout / BorderLayout)
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        btnSincronizar = new JButton("Sincronizar Inventario (Hilo)");
        lblEstadoHilo = new JLabel("Estado Hilo: En espera...");

        panelInferior.add(btnSincronizar, BorderLayout.WEST);
        panelInferior.add(lblEstadoHilo, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);

        // EVENTOS CON EXPRESIONES LAMBDA
        cmbProductos.addActionListener(e -> actualizarEtiquetaStock());
        btnVender.addActionListener(e -> procesarVenta());
        btnAgregarStock.addActionListener(e -> procesarIngresoStock());
        btnSincronizar.addActionListener(e -> ejecutarHiloSincronizacion());
    }

    private Producto obtenerProductoSeleccionado() {
        return cmbProductos.getSelectedIndex() == 0 ? leche : arroz;
    }

    private void actualizarEtiquetaStock() {
        Producto seleccionado = obtenerProductoSeleccionado();
        lblStockActual.setText(seleccionado.getStock() + " unidades | Precio Unit. c/IVA: $" 
                + String.format("%.2f", seleccionado.calcularPrecioVenta()));
    }

    private void procesarVenta() {
        Producto producto = obtenerProductoSeleccionado();

        // Uso de try-with-resources con ComprobanteVenta (AutoCloseable)
        try (ComprobanteVenta comprobante = new ComprobanteVenta()) {
            String texto = txtCantidad.getText().trim();

            if (texto.isEmpty()) {
                throw new DatoInvalidoException("Debe ingresar una cantidad antes de procesar la venta.");
            }

            int cantidad = Integer.parseInt(texto); // Puede lanzar NumberFormatException

            // Llamada al método que declara throws StockInsuficienteException
            producto.vender(cantidad);

            double totalVenta = producto.calcularPrecioVenta() * cantidad;
            comprobante.imprimirDetalle(producto.obtenerDescripcionFactura(), cantidad, totalVenta);

            txtAreaFactura.append("VENTA: " + cantidad + "x " + producto.obtenerDescripcionFactura() 
                    + " -> Total: $" + String.format("%.2f", totalVenta) + "\n");

            actualizarEtiquetaStock();
            txtCantidad.setText("");

            JOptionPane.showMessageDialog(this, 
                "Venta realizada con éxito.\nTotal cobrado: $" + String.format("%.2f", totalVenta), 
                "Venta Completada", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            // 1. Excepción más específica (formato numérico)
            JOptionPane.showMessageDialog(this, 
                "Error de formato: Ingrese únicamente números enteros válidos.", 
                "Dato No Numérico", 
                JOptionPane.WARNING_MESSAGE);
        } catch (DatoInvalidoException ex) {
            // 2. Excepción Unchecked personalizada
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Validación de Datos", 
                JOptionPane.WARNING_MESSAGE);
        } catch (StockInsuficienteException ex) {
            // 3. Excepción Checked personalizada (usa el getter del dato extra)
            JOptionPane.showMessageDialog(this, 
                ex.getMessage() + "\nUnidades disponibles actualmente: " + ex.getStockDisponible(), 
                "Stock Insuficiente", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            // 4. Excepción general al final (orden correcto de catch)
            JOptionPane.showMessageDialog(this, 
                "Error inesperado en el sistema: " + ex.getMessage(), 
                "Error Crítico", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            System.out.println("[FINALLY]: Operación de venta finalizada y verificada.");
        }
    }

    private void procesarIngresoStock() {
        Producto producto = obtenerProductoSeleccionado();
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            producto.agregarStock(cantidad, "Proveedor Local");
            actualizarEtiquetaStock();
            txtCantidad.setText("");
            JOptionPane.showMessageDialog(this, "Stock actualizado correctamente.", "Inventario", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número entero válido para agregar stock.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        } catch (DatoInvalidoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Dato Inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void ejecutarHiloSincronizacion() {
        SincronizacionInventarioTarea tarea = new SincronizacionInventarioTarea(lblEstadoHilo, btnSincronizar);
        Thread hilo = new Thread(tarea);
        hilo.start();
    }
}