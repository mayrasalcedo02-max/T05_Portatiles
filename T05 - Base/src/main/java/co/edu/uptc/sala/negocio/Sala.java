package co.edu.uptc.sala.negocio;

import co.edu.uptc.sala.modelo.EstadoPortatil;
import co.edu.uptc.sala.modelo.Portatil;
import java.io.IOException;
import java.util.ArrayList;

/** Administra todos los portatiles de la sala. */
public class Sala {

    private ArrayList<Portatil> portatiles;
    private ArchivoSala archivo;

    public Sala(ArchivoSala archivo) {
        this.portatiles = new ArrayList<>();
        this.archivo = archivo;
    }

    public ArrayList<Portatil> getPortatiles() {
        return portatiles;
    }

    /** La primera vez (sin archivo) arranca con cuatro portatiles de ejemplo. */
    public void cargar() throws IOException {
        if (!archivo.existe()) {
            portatiles.add(new Portatil("P-01", "Lenovo"));
            portatiles.add(new Portatil("P-02", "HP"));
            portatiles.add(new Portatil("P-03", "Dell"));
            portatiles.add(new Portatil("P-04", "Asus", EstadoPortatil.REPARACION));
            return;
        }
        portatiles = new ArrayList<>(archivo.cargar());
    }

    public void guardar() throws IOException {
        archivo.guardar(portatiles);
    }

    /** Devuelve el portatil con ese codigo. */
    public Portatil buscar(String codigo) throws PortatilNoEncontradoException {
        for (Portatil p : portatiles) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        throw new PortatilNoEncontradoException(codigo);
    }

    public int contarPorEstado(EstadoPortatil estado) {
        int cuenta = 0;
        for (Portatil p : portatiles) {
            if (p.getEstado() == estado) {
                cuenta++;
            }
        }
        return cuenta;
    }
}
