package co.edu.uptc.sala.negocio;

import co.edu.uptc.sala.modelo.DatoInvalidoException;
import co.edu.uptc.sala.modelo.EstadoPortatil;
import co.edu.uptc.sala.modelo.Portatil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Guarda y lee los portatiles en datos/portatiles.txt. Formato: P-01;Lenovo;DISPONIBLE */
public class ArchivoSala {

    private static final String SEP = ";";

    private Path carpeta;
    private Path archivo;

    public ArchivoSala(String carpeta) {
        this.carpeta = Path.of(carpeta);
        this.archivo = this.carpeta.resolve("portatiles.txt");
    }

    public boolean existe() {
        return Files.exists(archivo);
    }

    public void guardar(List<Portatil> portatiles) throws IOException {
        List<String> lineas = new ArrayList<>();
        for (Portatil p : portatiles) {
            lineas.add(p.getCodigo() + SEP + p.getMarca() + SEP + p.getEstado().name());
        }

        Files.createDirectories(carpeta);
        Files.write(archivo, lineas);
    }

    /**
     * PUNTO 3 · Lee el archivo y devuelve la lista de portatiles.
     */
    public List<Portatil> cargar() throws IOException {
        List<Portatil> portatiles = new ArrayList<>();
        List<String> lineas = Files.readAllLines(archivo);

        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);

            if (linea.isBlank()) {
                continue;
            }

            String[] d = linea.split(SEP);

            if (d.length != 3) {
                throw new DatoInvalidoException("Datos invalidos");
            }

            try {
                portatiles.add(new Portatil(d[0],d[1],EstadoPortatil.valueOf(d[2]) ));
            }catch (IllegalArgumentException e){
                throw new DatoInvalidoException("Datos invalidos");
            }


            // PUNTO 3 · Escriba aqui la lectura del archivo.
        }
        return portatiles;
    }
}
