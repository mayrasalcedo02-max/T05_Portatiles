package co.edu.uptc.sala.negocio;

public class PortatilNoEncontradoException extends Exception {
    public PortatilNoEncontradoException(String codigo) {

        super("No hay ningun portatil con el codigo "+codigo);
    }
}
