package co.edu.uptc.sala.presentacion;

import co.edu.uptc.sala.negocio.ArchivoSala;
import co.edu.uptc.sala.negocio.Sala;

/** Arranca la aplicacion: ni un ciclo, ni un println. */
public class Main {

    public static void main(String[] args) {
        Sala sala = new Sala(new ArchivoSala("datos"));
        MenuSala menu = new MenuSala(sala);
        menu.iniciar();
    }
}
