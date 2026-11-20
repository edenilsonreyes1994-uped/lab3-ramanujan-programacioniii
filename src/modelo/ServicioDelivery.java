package modelo;

import excepciones.DatoInvalidoException;
import interfaces.Facturable;

public class ServicioDelivery implements Facturable {
    private double distanciaKm;
    private double tarifaPorKm = 1.50;

    public ServicioDelivery(double distanciaKm) {
        if (distanciaKm <= 0) {
            throw new DatoInvalidoException("La distancia de delivery debe ser mayor a 0 Km.");
        }
        this.distanciaKm = distanciaKm;
    }

    @Override
    public double calcularPrecioVenta() {
        return distanciaKm * tarifaPorKm;
    }

    @Override
    public String obtenerDescripcionFactura() {
        return "Servicio de Delivery (" + distanciaKm + " Km)";
    }
}