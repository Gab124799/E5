import java.util.List;
import java.util.NoSuchElementException;

public class JuegoController {
    private final Partida partida;
    private final ConsolaView vista;

    public JuegoController(Partida partida, ConsolaView vista) {
        this.partida = partida;
        this.vista = vista;
    }

    public void iniciar() {
        vista.mostrarBienvenida();
        try {
            String nombre = vista.pedirTexto("Nombre del jugador: ");
            if (!nombre.isEmpty()) {
                partida.getJugador().setNombre(nombre);
            }
            vista.mostrarCartas("Tu mano inicial", partida.getMazo().getCartasEn(UbicacionCarta.MANO));

            boolean salir = false;
            while (!salir && partida.getEstado() == EstadoPartida.EN_CURSO) {
                vista.mostrarEstado(partida.getJugador(), partida.getTablero());
                vista.mostrarMenu();
                int opcion = vista.pedirEntero("Elige una opción: ");
                salir = procesarOpcion(opcion);
            }

            if (partida.getEstado() != EstadoPartida.EN_CURSO) {
                vista.mostrarFin(partida.getEstado(), partida.getMotivoFin(), partida.getJugador());
            } else {
                vista.mostrarMensaje("¡Hasta luego!");
            }
        } catch (NoSuchElementException e) {
            vista.mostrarMensaje("\nEntrada finalizada. ¡Hasta luego!");
        }
    }

    private boolean procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                vista.mostrarCartas("Cartas del mazo", partida.getMazo().getCartas());
                break;
            case 2:
                buscarPorId();
                break;
            case 3:
                buscarPorNombre();
                break;
            case 4:
                ordenarPorEnergia();
                break;
            case 5:
                vista.mostrarCartas("Tu mano", partida.getMazo().getCartasEn(UbicacionCarta.MANO));
                break;
            case 6:
                usarCarta();
                break;
            case 7:
                mostrarResultado(partida.tomarCarta());
                break;
            case 8:
                mostrarResultado(partida.pasarTurno());
                break;
            case 0:
                return true;
            default:
                vista.mostrarError("Opción no válida.");
        }
        return false;
    }

    private void buscarPorId() {
        int id = vista.pedirEntero("ID de la carta: ");
        Carta carta = partida.getMazo().buscar(id);
        if (carta == null) {
            vista.mostrarError("No existe ninguna carta con ID " + id + ".");
        } else {
            vista.mostrarCarta(carta);
        }
    }

    private void buscarPorNombre() {
        String texto = vista.pedirTexto("Nombre (o parte del nombre): ");
        if (texto.isEmpty()) {
            vista.mostrarError("Escribe al menos una letra.");
            return;
        }
        List<Carta> resultado = partida.getMazo().buscar(texto);
        if (resultado.isEmpty()) {
            vista.mostrarError("No se encontraron cartas con \"" + texto + "\".");
        } else {
            vista.mostrarCartas("Resultados para \"" + texto + "\"", resultado);
        }
    }

    private void ordenarPorEnergia() {
        boolean ascendente = vista.confirmar("¿Ordenar de menor a mayor costo?");
        partida.getMazo().ordenarPorEnergia(ascendente);
        vista.mostrarCartas("Mazo ordenado por costo de energía ("
                + (ascendente ? "menor a mayor" : "mayor a menor") + ")", partida.getMazo().getCartas());
    }

    private void usarCarta() {
        List<Carta> mano = partida.getMazo().getCartasEn(UbicacionCarta.MANO);
        if (mano.isEmpty()) {
            vista.mostrarError("No tienes cartas en la mano. Toma una con la opción 7.");
            return;
        }
        vista.mostrarCartas("Tu mano", mano);
        int id = vista.pedirEntero("ID de la carta a usar (0 para cancelar): ");
        if (id == 0) {
            return;
        }
        mostrarResultado(partida.usarCarta(id));
    }

    private void mostrarResultado(ResultadoAccion resultado) {
        if (resultado.isExito()) {
            vista.mostrarMensaje(resultado.getMensaje());
        } else {
            vista.mostrarError(resultado.getMensaje());
        }
    }
}
