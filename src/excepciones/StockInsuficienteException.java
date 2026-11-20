package excepciones;

// Excepción Checked (hereda de Exception) con dato extra y getter
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