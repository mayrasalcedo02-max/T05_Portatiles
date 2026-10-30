package co.edu.uptc.sala.presentacion;

import co.edu.uptc.sala.modelo.DatoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoPortatil;
import co.edu.uptc.sala.modelo.Portatil;
import co.edu.uptc.sala.negocio.PortatilNoEncontradoException;
import co.edu.uptc.sala.negocio.Sala;
import java.io.IOException;
import javax.swing.JOptionPane;

/** Todo lo que ve el usuario, con JOptionPane. */
public class MenuSala {

    private static final String TITULO = "Sala de sistemas";

    private Sala sala;
    private Portatil portatil;

    public MenuSala(Sala sala) {
        this.sala = sala;
    }

    public void iniciar() {
        try {
            sala.cargar();
        } catch (IOException | DatoInvalidoException e) {
            avisar("No se pudieron cargar los datos: " + e.getMessage());
        }
        String opcion = "";
        while (!opcion.equals("-1")) {
            opcion = mostrarMenu();
            if (opcion.equals("1")) { verPortatiles(); }
            else if (opcion.equals("2")) { prestar(); }
            else if (opcion.equals("3")) { devolver(); }
            else if (opcion.equals("4")) { verResumen(); }
            else if (opcion.equals("0")) {

                guardar();

                int confirmacion = JOptionPane.showConfirmDialog(null, "Seguro que desea salir?", "Verificacion", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION){
                    JOptionPane.showMessageDialog(null,"Saliendo del programa, adios",TITULO,JOptionPane.INFORMATION_MESSAGE);
                    opcion="-1";
                }else {
                    JOptionPane.showMessageDialog(null, "Regresando al menu", "Regresar", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else { avisar("Esa opcion no existe."); }
        }
    }

    private String mostrarMenu() {
        String menu = "1. Ver portatiles\n"
                + "2. Prestar\n"
                + "3. Devolver\n"
                + "4. Resumen por estado\n"
                + "0. Salir\n\n"
                + "Escriba el numero de la opcion:";
        String opcion = JOptionPane.showInputDialog(null, menu, TITULO, JOptionPane.PLAIN_MESSAGE);
        if (opcion == null) {
            return "0";
        }
        return opcion.trim();
    }

    private String preguntar(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje, TITULO, JOptionPane.QUESTION_MESSAGE);
        if (texto == null) {
            return "";
        }
        return texto.trim();
    }

    private void avisar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, TITULO, JOptionPane.ERROR_MESSAGE);
    }

    private void verPortatiles() {

        String portatiles="";

        for (Portatil p : sala.getPortatiles()){
            portatiles+=" . "+p.getMarca()+" | "+p.getCodigo()+
                    " | "+p.getEstado()+"\n";
        }

        JOptionPane.showMessageDialog(null,portatiles,"VER PORTATILES", JOptionPane.PLAIN_MESSAGE);


        // PUNTO 4 · Todos los portatiles en UNA ventana PLAIN_MESSAGE.
    }

    private void prestar() {
        String codigoBuscado=JOptionPane.showInputDialog(null,"Cual es el codigo del portatil que desea?","PRESTAR",JOptionPane.INFORMATION_MESSAGE);
        if (codigoBuscado == null || codigoBuscado.trim().isEmpty()) {
            return;
        }

        try {
            portatil = sala.buscar(codigoBuscado);
        }catch (PortatilNoEncontradoException e){
            avisar(e.getMessage());
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(null,"confirma el prestamo", "CONFIRMACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (confirmacion == JOptionPane.YES_OPTION){

            try {
                portatil.prestar();
                JOptionPane.showMessageDialog(null,"Portatil prestado","CONFIRMACION YES",JOptionPane.INFORMATION_MESSAGE);
            } catch (EstadoInvalidoException e) {
                avisar(e.getMessage());
            }


        }else {
            JOptionPane.showMessageDialog(null, "Regresando al menu", "Regresar", JOptionPane.INFORMATION_MESSAGE);
        }


        // PUNTO 4 · Preguntar el codigo, buscar, confirmar con Si/No, prestar y avisar.
    }

    private void devolver() {
        // PUNTO 4 · Preguntar el codigo, buscar, devolver y avisar.

        String codigoBuscado=JOptionPane.showInputDialog(null,"Cual es el codigo del portatil que desea devolver?","DEVOLVER",JOptionPane.INFORMATION_MESSAGE);

        if (codigoBuscado == null || codigoBuscado.trim().isEmpty()) {
            return;
        }

        try {
            portatil=sala.buscar(codigoBuscado);
        }catch (PortatilNoEncontradoException e){
            avisar(e.getMessage());
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(null,"confirma la devolucion", "CONFIRMACION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (confirmacion == JOptionPane.YES_OPTION){
            try {
                portatil.devolver();
                JOptionPane.showMessageDialog(null,"Portatil devuelto","CONFIRMACION YES",JOptionPane.INFORMATION_MESSAGE);

            } catch (EstadoInvalidoException e) {
                avisar(e.getMessage());
            }

        }else {
            JOptionPane.showMessageDialog(null, "Regresando al menu", "Regresar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void verResumen() {
        String verResum ="";

        for (EstadoPortatil e : EstadoPortatil.values()) {
            verResum+="  " + e.getTexto() + ": " + sala.contarPorEstado(e)+"\n";
        }

        JOptionPane.showMessageDialog(null,verResum);


        // PUNTO 4 · Cuantos hay en cada estado, recorriendo EstadoPortatil.values().
    }

    private void guardar() {
        try {
            sala.guardar();
        } catch (IOException e) {
            avisar("No se pudo guardar: " + e.getMessage());
        }
    }
}
