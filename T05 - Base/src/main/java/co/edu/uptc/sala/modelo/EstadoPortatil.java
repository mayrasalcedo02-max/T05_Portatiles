package co.edu.uptc.sala.modelo;

public enum EstadoPortatil {
    DISPONIBLE("Disponible"),
    PRESTADO("Prestado"),
    REPARACION("En reparacion");

    private final String texto;

    EstadoPortatil(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
