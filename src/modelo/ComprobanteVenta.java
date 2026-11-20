package modelo;

// Implementa AutoCloseable para cerrar automáticamente el flujo de impresión/registro
public class ComprobanteVenta implements AutoCloseable {

    public ComprobanteVenta() {
        System.out.println("[TICKET]: Iniciando generación de comprobante de venta...");
    }

    public void imprimirDetalle(String detalle, int cantidad, double total) {
        System.out.println("=========================================");
        System.out.println("MINIMARKET - COMPROBANTE DE VENTA");
        System.out.println("Item: " + detalle);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Total (con IVA/recargo): $" + String.format("%.2f", total));
        System.out.println("=========================================");
    }

    @Override
    public void close() {
        System.out.println("[TICKET]: Impresora de comprobantes liberada y cerrada (AutoCloseable).");
    }
}