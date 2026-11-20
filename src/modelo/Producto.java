package modelo;

import excepciones.DatoInvalidoException;
import excepciones.StockInsuficienteException;
import interfaces.Facturable;

public abstract class Producto implements Facturable {
    protected String codigo;
    protected String nombre;
    protected double precioBase;
    protected int stock;

    public Producto(String codigo, String nombre, double precioBase, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
    }

    public boolean verificarStock(int cantidad) {
        return this.stock >= cantidad;
    }

    // NUEVO LAB III: Regla de negocio con throw y throws (Excepción Checked)
    public void vender(int cantidad) throws StockInsuficienteException {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad a vender debe ser mayor a 0.");
        }
        if (!verificarStock(cantidad)) {
            throw new StockInsuficienteException(
                "Stock insuficiente para el producto: " + this.nombre, 
                this.stock
            );
        }
        this.stock -= cantidad;
    }

    // --- POLIMORFISMO POR SOBRECARGA (OVERLOADING) ---
    public void agregarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad a agregar debe ser positiva.");
        }
        this.stock += cantidad;
        System.out.println("Se agregaron " + cantidad + " unidades a " + nombre);
    }

    public void agregarStock(int cantidad, String proveedor) {
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad a agregar debe ser positiva.");
        }
        this.stock += cantidad;
        System.out.println("Se agregaron " + cantidad + " unidades a " + nombre + " desde el proveedor: " + proveedor);
    }

    public void aplicarDescuento(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new DatoInvalidoException("El porcentaje de descuento debe estar entre 0 y 100.");
        }
        this.precioBase -= (this.precioBase * (porcentaje / 100));
    }

    public void aplicarDescuento(double porcentaje, String codigoPromo) {
        System.out.println("Aplicando promo " + codigoPromo);
        aplicarDescuento(porcentaje);
    }

    public abstract void mostrarInformacion();

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public String getCodigo() {
        return codigo;
    }
}