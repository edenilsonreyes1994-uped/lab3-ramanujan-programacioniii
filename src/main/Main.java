package main;

import gui.VentanaMinimarket;
import interfaces.Facturable;
import modelo.ProductoNoPerecedero;
import modelo.ProductoPerecedero;
import modelo.ServicioDelivery;

import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- SISTEMA DE MINIMARKET (VERIFICACIÓN LABS I Y II) ---");

        ProductoPerecedero leche = new ProductoPerecedero("P001", "Leche Entera", 1.20, 50, "20-10-2026");
        ProductoNoPerecedero arroz = new ProductoNoPerecedero("N001", "Arroz San Pedro", 0.90, 100, 12);
        ServicioDelivery envio = new ServicioDelivery(5.0);

        // Polimorfismo por sobrecarga
        arroz.agregarStock(20);
        leche.agregarStock(10, "Lácteos La Salud");
        leche.aplicarDescuento(10);
        arroz.aplicarDescuento(15, "PROMO-FIN-AÑO");

        // Polimorfismo por sobrescritura
        leche.mostrarInformacion();
        arroz.mostrarInformacion();

        // Interfaces y colecciones polimórficas
        List<Facturable> carrito = new ArrayList<>();
        carrito.add(leche);
        carrito.add(arroz);
        carrito.add(envio);

        double totalPagar = 0;
        for (Facturable item : carrito) {
            System.out.println(item.obtenerDescripcionFactura() + " -> $" + String.format("%.2f", item.calcularPrecioVenta()));
            totalPagar += item.calcularPrecioVenta();
        }
        System.out.println("TOTAL A PAGAR EN CONSOLA: $" + String.format("%.2f", totalPagar));

        // --- INICIO DE INTERFAZ GRÁFICA SWING (LABORATORIO III) ---
        SwingUtilities.invokeLater(() -> {
            VentanaMinimarket ventana = new VentanaMinimarket();
            ventana.setVisible(true);
        });
    }
}