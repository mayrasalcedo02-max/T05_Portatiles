package co.edu.uptc.sala.modelo;

public class EstadoInvalidoException extends Exception {
    public EstadoInvalidoException(String codigo, EstadoPortatil estado, String accion) {
        super("No se puede "+accion+" el portatil "+codigo+": esta "+estado);
    }
}
