package modelo;

public class ProductoNoPerecedero extends Producto {
    private int mesesGarantia;

    public ProductoNoPerecedero(String codigo, String nombre, double precioBase, int stock, int mesesGarantia) {
        super(codigo, nombre, precioBase, stock);
        this.mesesGarantia = mesesGarantia;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("[No Perecedero] " + nombre + " - Garantía: " + mesesGarantia + " meses - Stock: " + stock);
    }

    @Override
    public double calcularPrecioVenta() {
        return this.precioBase * 1.13;
    }

    @Override
    public String obtenerDescripcionFactura() {
        return nombre + " (Garantía: " + mesesGarantia + " meses)";
    }
}