import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;  
public class Mian1 {
	private static final String DIR_PERFILES = "perfiles/";
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    PerfilJugador jugador = null;
	    boolean salir = false;
	    while (!salir) {
	    	System.out.println("   ===================================================================");
	    	System.out.println("  |              BIENVENIDO AL JUEGO MENU DE JUEGOS                   |");
	    	System.out.println("  |                                                                   |");
	    	System.out.println("  ====================================================================");
	    	System.out.println("  |                                                                   |");
	    	System.out.println("  |                       🕹️MENÚ PRINCIPAL:                           |");
	    	System.out.println("  |                                                                   |");
	    	System.out.println("  |🎮1. CREAR PERFIL             (si eres nuevo jugador)              |");
	    	System.out.println("  |🎮2. CARGAR PERFIL DE JUGADOR (para empezar a jugar)               |");
	    	System.out.println("  |🎮3. VER PERFIL DE TU AVATAR  (busca tu nombre de jugador)         |");
	    	System.out.println("  |🏆4. VER PUNTAJES GENERALES   (Mira a quien debes vencer)          |");
	    	System.out.println("  |                                                                   |");
	    	System.out.println("  |                 ELIJE QUE JUEGO QUIERES JUGAR                     |");
	    	System.out.println("  |                                                                   |");
	    	System.out.println("  |🕵️‍♂️5. JUEGO ADIVINA QUIEN?     (despues de crear o cargar tu perfil)|");
	    	System.out.println("  |💀6. JUGAR AL AHORCADO        (después de crear o cargar tu perfil)|");
	    	System.out.println("  |                                                                   |");
	    	System.out.println("  |🚪7. SALIR DEL MENU                                                 |");
	    	System.out.println("  |                                                                   |");
	    	System.out.println("  |             (CADA VEZ QUE GANAS ACUMULAS 💯 PUNTOS)               |");
	    	System.out.println("  ====================================================================");
	    	System.out.print("Seleccione una opción (1-7): ");
	        int opcion = sc.nextInt();
	        sc.nextLine(); // Consume el salto de línea
	        switch (opcion) {
	            case 1:
	            	System.out.println();
	                System.out.print("Ingrese su nombre de jugador: ");
	                String nombre = sc.nextLine();
	                jugador = new PerfilJugador(nombre);
	                guardarPerfil(jugador);
	                break;
	            case 2:
	            	System.out.println();
	                System.out.print("Ingrese su nombre del jugador existente: ");
	                nombre = sc.nextLine();
	                jugador = cargarPerfil(nombre);
	                break;
	            case 3:
	            	System.out.println();
	                System.out.print("Ingresa tu nombre de jugador: ");
	                nombre = sc.nextLine();
	                jugador = cargarPerfil(nombre);
	                if (jugador != null) {
	                    System.out.println(jugador);
	                }
	                break;
	            case 4:
	                List<PerfilJugador> perfiles = cargarPerfiles();
	                perfiles.sort(Comparator.comparing(PerfilJugador::getSumaPuntos).reversed());
	                System.out.println();
	                System.out.println("LOS MEJORES JUGADORES:");
	                System.out.printf("%-15s %15s%n", "Nombres:", "Puntos:"); // Encabezado de columnas
	                for (PerfilJugador perfil : perfiles) {
	                    System.out.printf("%-15s %15d%n", perfil.getNombre(), perfil.getSumaPuntos());
	                }
	                break;
	            case 5:
	                if (jugador != null) {
	                    jugar(jugador);
	                } else {
	                    System.out.println("Debe cargar o crear un perfil de jugador antes de iniciar el juego.");
	                }
	                break;
	            case 6: // Añade el caso para jugar al Ahorcado
                    if (jugador != null) {
                        jugarAhorcado(jugador);
                    } else {
                        System.out.println("Debe cargar o crear un perfil de jugador antes de iniciar el juego.");
                    }
                    break;
	            case 7:
	                salir = true;
	                break;
	            default:
	                System.out.println("Opción no válida.");
	                break;
	        }
	    }
	}
        	    private static void guardarPerfil(PerfilJugador jugador) {
        	        try {
        	            File file = new File(DIR_PERFILES + jugador.getNombre() + "jugadores.dat");
        	            if (!file.getParentFile().exists()) {
        	                file.getParentFile().mkdirs();
        	            }
        	            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        	            oos.writeObject(jugador);
        	            oos.close();
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
        	    }
        	    private static PerfilJugador cargarPerfil(String nombre) {
        	        try {
        	            File file = new File(DIR_PERFILES + nombre + "jugadores.dat");
        	            if (file.exists()) {
        	                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        	                PerfilJugador jugador = (PerfilJugador) ois.readObject();
        	                ois.close();
        	                return jugador;
        	            } else {
        	                System.out.println("El perfil del jugador no existe, cree un perfil nuevo.");
        	            }
        	        } catch (IOException | ClassNotFoundException e) {
        	            e.printStackTrace();
        	        }
        	        return null;
        	    }
        	    public static List<PerfilJugador> cargarPerfiles() {
        	        List<PerfilJugador> perfiles = new ArrayList<>();
        	        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(DIR_PERFILES))) {
        	            for (Path path : directoryStream) {
        	                if (path.getFileName().toString().endsWith("jugadores.dat")) {
        	                    try {
        	                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()));
        	                        PerfilJugador jugador = (PerfilJugador) ois.readObject();
        	                        ois.close();
        	                        perfiles.add(jugador);
        	                    } catch (IOException | ClassNotFoundException e) {
        	                        e.printStackTrace();
        	                    }
        	                }
        	            }
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
        	        return perfiles;
        	    }
        	    public static void jugar(PerfilJugador jugador) {       	    	 
        	    	Scanner sc = new Scanner(System.in);
        	        Personas[] persona = new Personas[10];
        	        for (int i = 0; i < 10; i++) {
        	            persona[i] = new Personas() {};
        	        }
        	        // contadores de los atributos
        	        int espada = 0;
        	        int millonario = 0;
        	        int mamado = 0;
        	        int feo = 0;
        	        int sabeProgramar = 0;
        	        int estaCansado = 0;
        	        int estaEnfermo = 0;
        	        int estaTriste = 0;
        	        int estaFeliz = 0;        	        
        	        System.out.println("JUEGO ADIVINA QUIEN? (JUGADORES)");
        	        System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", "Nombre", "Caracteristica 1", "Caracteristica 2", "Caracteristica 3", "Caracteristica 4");
        	        for (Personas personas : persona) {
        	            System.out.printf("%-15s", personas.getNombre());
        	            List<String> atributos = personas.getAtributos();
        	            for (int i = 0; i < 4; i++) {
        	                if (i < atributos.size()) {
        	                    System.out.printf("%-15s", atributos.get(i));
        	                } else {
        	                    System.out.printf("%-18s", "");
        	                }
        	            }
        	            System.out.println();
        	            for (String atributo : atributos) {
        	                switch (atributo) {
        	                    case "Tiene Espada":
        	                        espada++;
        	                        break;
        	                    case "Es Millonario":
        	                        millonario++;
        	                        break;
        	                    case "Está Mamado":
        	                        mamado++;
        	                        break;
        	                    case "Está Feo":
        	                        feo++;
        	                        break;
        	                    case "Sabe Programar":
        	                        sabeProgramar++;
        	                        break;
        	                    case "Está Cansado":
        	                        estaCansado++;
        	                        break;
        	                    case "Está Enfermo":
        	                        estaEnfermo++;
        	                        break;
        	                    case "Está Triste":
        	                        estaTriste++;
        	                        break;
        	                    case "Está Feliz":
        	                        estaFeliz++;
        	                        break;
        	                }
        	            }
        	        }
        	        System.out.println();
        	        System.out.println("REPETICION DE ATRIBUTOS");
        	        System.out.println("El Atributo Tiene Espada se repite: " + espada);
        	        System.out.println("El Atributo es Millonario se repite: " + millonario);
        	        System.out.println("El Atributo está Mamado se repite: " + mamado);
        	        System.out.println("El Atributo está Feo se repite: " + feo);
        	        System.out.println("El Atributo Sabe programar se repite: " + sabeProgramar);
        	        System.out.println("El Atributo está cansado se repite: " + estaCansado);
        	        System.out.println("El Atributo está enfermo se repite: " + estaEnfermo);
        	        System.out.println("El Atributo está triste se repite: " + estaTriste);
        	        System.out.println("El Atributo está feliz se repite: " + estaFeliz);
        	        System.out.println();    	    
        	        int personaSeleccionada = new Random().nextInt(persona.length);
        	        for (int i = 0; i < 3; i++) {
        	            System.out.println("Pregunta " + (i + 1) + ":");
        	            while (true) {
        	                System.out.println("Seleccione un atributo para eliminar personas:");
        	                for (int j = 0; j < Personas.getCaracteristicasImportantes().size(); j++) {
        	                    System.out.println((j + 1) + ". " + Personas.getCaracteristicasImportantes().get(j));
        	                }
        	                int atributoElegido = sc.nextInt() - 1;
        	                String atributo = Personas.getCaracteristicasImportantes().get(atributoElegido);
        	                List<Personas> personasRestantes = new ArrayList<>();
        	                for (Personas p : persona) {
        	                    if (!p.tieneAtributo(atributo)) {
        	                        personasRestantes.add(p);
        	                    }
        	                }
        	                if (personasRestantes.size() > 0) {
        	                    persona = personasRestantes.toArray(new Personas[0]);
        	                    personaSeleccionada = new Random().nextInt(persona.length); // Actualizar la persona seleccionada

        	                    System.out.println("Personas restantes:");
        	                    for (Personas p : persona) {
        	                        StringBuilder atributosConcatenados = new StringBuilder();
        	                        for (String atributoIndividual : p.getAtributos()) {
        	                            atributosConcatenados.append(atributoIndividual).append(", ");
        	                        }
        	                        atributosConcatenados.setLength(atributosConcatenados.length() - 2); // Eliminar la última coma y espacio
        	                        System.out.println(p.getNombre() + ": " + atributosConcatenados);
        	                    }
        	                    System.out.println();
        	                    break;
        	                } else {
        	                    System.out.println("Si eliges este atributo se descartan todos los jugadores, por favor elije otro.");
        	                }
        	            }
        	        }
        	        System.out.println("Ingrese el índice de la persona que cree que es la seleccionada (1-" + persona.length + "):");
        	        int indiceElegido = sc.nextInt() - 1;
        	        if (indiceElegido >= 0 && indiceElegido < persona.length && persona[indiceElegido].getNombre().equals(persona[personaSeleccionada].getNombre())) {
        	            System.out.println("¡Felicidades! Ha adivinado correctamente. La persona seleccionada es " + persona[indiceElegido].getNombre());
        	            jugador.incrementarJuegosJugados();
        	            jugador.incrementarJuegosGanados();
        	        } else {
        	            System.out.println("Lo siento, no ha adivinado correctamente. La persona seleccionada era " + persona[personaSeleccionada].getNombre());
        	            jugador.incrementarJuegosJugados();
        	            jugador.incrementarJuegosPerdidos();
        	        }
        	        guardarPerfil(jugador);
        	    }
        	    
        	    public static void jugarAhorcado(PerfilJugador jugador) {
        	        Scanner scanner = new Scanner(System.in);
        	        String[] palabras = {"JAVA", "PROGRAMACION", "COMPUTADORA", "PROGRAMADOR", "CODIGO", "DATOS"};
        	        String palabra = palabras[(int) (Math.random() * palabras.length)];
        	        char[] letras = new char[palabra.length()];
        	        boolean[] aciertos = new boolean[palabra.length()];
        	        int intentos = 0;
        	        boolean completado = false;
        	        while (intentos < 6 && !completado) {
        	            System.out.println("Adivina la palabra con tematica de informatica:");
        	            for (int i = 0; i < palabra.length(); i++) {
        	                if (aciertos[i]) {
        	                    System.out.print(palabra.charAt(i));
        	                } else {
        	                    System.out.print("-");
        	                }
        	            }
        	            System.out.println();
        	            System.out.print("Letra: ");
        	            char letra = scanner.nextLine().toUpperCase().charAt(0);
        	            boolean acierto = false;
        	            for (int i = 0; i < palabra.length(); i++) {
        	                if (palabra.charAt(i) == letra) {
        	                    aciertos[i] = true;
        	                    acierto = true;
        	                }
        	            }
        	            if (!acierto) {
        	                intentos++;
        	                System.out.println("Incorrecto. Te quedan " + (6 - intentos) + " intentos.");
        	            }
        	            completado = true;
        	            for (boolean aciertoActual : aciertos) {
        	                if (!aciertoActual) {
        	                    completado = false;
        	                    break;
        	                }
        	            }
        	        }
        	        jugador.incrementarJuegosJugados();
        	        if (completado) {
        	            System.out.println("¡Felicidades, has adivinado la palabra!");
        	            jugador.incrementarJuegosGanados();
        	        } else {
        	            System.out.println("Lo siento, has perdido. La palabra era " + palabra + ".");
        	            jugador.incrementarJuegosPerdidos();
        	        }
        	        guardarPerfil(jugador);
        	    }
        	    }
