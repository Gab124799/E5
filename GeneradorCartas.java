import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneradorCartas {
    public static final int MINIMO_CARTAS = 10;

    private static final String[] NOMBRES_CATEDRATICOS = {
            "Ing. Morales", "Dra. López", "Lic. Castillo", "Dr. Herrera",
            "Dra. Méndez", "Ing. Pineda", "Lic. Barrios", "Dr. Estrada"
    };
    private static final String[] DEPARTAMENTOS = {
            "Computación", "Matemática", "Física", "Química", "Biología", "Ingeniería"
    };
    private static final String[] NOMBRES_CURSOS = {
            "Programación Orientada a Objetos", "Cálculo II", "Álgebra Lineal",
            "Física 1", "Química General", "Estructuras de Datos",
            "Estadística", "Bases de Datos", "Algoritmos", "Redes de Computadoras"
    };

    private static final int TIPO_CURSO = 0;
    private static final int TIPO_CATEDRATICO = 1;
    private static final int TIPO_EVENTO = 2;

    private final Random random;
    private int siguienteId = 1;

    public GeneradorCartas() {
        this(new Random());
    }

    public GeneradorCartas(Random random) {
        this.random = random;
    }

    public List<Carta> generar(int cantidad) {
        int total = Math.max(cantidad, MINIMO_CARTAS);

        List<Integer> tipos = new ArrayList<>();
        tipos.add(TIPO_CURSO);
        tipos.add(TIPO_CATEDRATICO);
        tipos.add(TIPO_EVENTO);
        while (tipos.size() < total) {
            tipos.add(tipoAleatorio());
        }
        Collections.shuffle(tipos, random);

        List<Carta> cartas = new ArrayList<>();
        for (int tipo : tipos) {
            switch (tipo) {
                case TIPO_CURSO:
                    cartas.add(crearCurso());
                    break;
                case TIPO_CATEDRATICO:
                    cartas.add(crearCatedratico());
                    break;
                default:
                    cartas.add(crearEvento());
                    break;
            }
        }
        return cartas;
    }

    private int tipoAleatorio() {
        int tirada = random.nextInt(10);
        if (tirada < 5) {
            return TIPO_CURSO;
        } else if (tirada < 8) {
            return TIPO_CATEDRATICO;
        }
        return TIPO_EVENTO;
    }

    private CartaCurso crearCurso() {
        String nombre = elegir(NOMBRES_CURSOS);
        int dificultad = 1 + random.nextInt(5);
        int creditos = dificultad + random.nextInt(2);
        int costo = dificultad;
        String descripcion = String.format("Materia de dificultad %d que aporta %d crédito(s).",
                dificultad, creditos);
        return new CartaCurso(siguienteId++, nombre, costo, descripcion, creditos, dificultad);
    }

    private CartaCatedratico crearCatedratico() {
        String nombre = elegir(NOMBRES_CATEDRATICOS);
        String departamento = elegir(DEPARTAMENTOS);
        int llamadas = 1 + random.nextInt(2);
        int tiempo = 15 * (1 + random.nextInt(4));
        int costo = 1 + random.nextInt(2);
        String descripcion = "Catedrático del departamento de " + departamento
                + ": te da energía a cambio de llamadas de atención.";
        return new CartaCatedratico(siguienteId++, nombre, costo, descripcion,
                departamento, llamadas, tiempo);
    }

    private CartaEventoCampus crearEvento() {
        switch (random.nextInt(3)) {
            case 0:
                return new SemanaParciales(siguienteId++);
            case 1:
                return new FeriaClubes(siguienteId++);
            default:
                return new RecesoAcademico(siguienteId++);
        }
    }

    private String elegir(String[] opciones) {
        return opciones[random.nextInt(opciones.length)];
    }
}
