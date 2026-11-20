package excepciones;

// Excepción Checked con dato extra para el dominio del proyecto
public class StockInsuficienteException extends Exception {
    private int stockDisponible;

    public StockInsuficienteException(String mensaje, int stockDisponible) {
        super(mensaje);
        this.stockDisponible = stockDisponible;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }
}