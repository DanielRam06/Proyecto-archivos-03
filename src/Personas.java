import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Personas {
    private static final String[] nombres = { "La lupita", "Donatello", "Leonardo", "Michelangelo", "Raphael", "Naruto", "Helen Chufe", "Alba Sura", "Goku", "Hinata" };
    private static final List<String> nombresAleatorios = new ArrayList<String>();
    private static final List<String> caracteristicasImportantes = new ArrayList<String>();

    static {
        for (String nombre : nombres) {
            nombresAleatorios.add(nombre);
            
        }
        Collections.shuffle(nombresAleatorios, new Random());
      
        caracteristicasImportantes.add("Tiene Espada");
        caracteristicasImportantes.add("Es Millonario");
        caracteristicasImportantes.add("Está Mamado");
        caracteristicasImportantes.add("Está Feo");
        caracteristicasImportantes.add("Sabe Programar");
        caracteristicasImportantes.add("Está Cansado");
        caracteristicasImportantes.add("Está Enfermo");
        caracteristicasImportantes.add("Está Triste");
        caracteristicasImportantes.add("Está Feliz");
    }

    private String nombre;
    private List<String> atributos;

    public Personas() {
        if (nombresAleatorios.isEmpty()) {
            for (String nombre : nombres) {
                nombresAleatorios.add(nombre);
            }
            Collections.shuffle(nombresAleatorios, new Random());
        }
        this.nombre = nombresAleatorios.remove(0);

        Collections.shuffle(caracteristicasImportantes, new Random());
        this.atributos = new ArrayList<>(caracteristicasImportantes.subList(0, 4));
    }


    public String getNombre() {
        return nombre;
    }

    public List<String> getAtributos() {
        return atributos;
    }
    public boolean tieneAtributo(String atributo) {
        return atributos.contains(atributo);
    }
    public static List<String> getCaracteristicasImportantes() {
        return caracteristicasImportantes;
    }
}
