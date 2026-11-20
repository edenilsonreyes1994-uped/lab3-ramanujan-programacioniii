package modelo;

public class ProductoPerecedero extends Producto {
    private String fechaVencimiento;

    public ProductoPerecedero(String codigo, String nombre, double precioBase, int stock, String fechaVencimiento) {
        super(codigo, nombre, precioBase, stock);
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("[Perecedero] " + nombre + " - Vence: " + fechaVencimiento + " - Stock: " + stock);
    }

    @Override
    public double calcularPrecioVenta() {
        return this.precioBase * 1.05;
    }

    @Override
    public String obtenerDescripcionFactura() {
        return nombre + " (Vence: " + fechaVencimiento + ")";
    }
}