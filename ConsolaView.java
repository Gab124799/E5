import java.util.List;
import java.util.Scanner;

public class ConsolaView {
    private final Scanner scanner;

    public ConsolaView() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarBienvenida() {
        System.out.println("==========================================");
        System.out.println("          UVG CARD BATTLE");
        System.out.println("==========================================");
        System.out.println("Meta: juntar " + Partida.META_CREDITOS + " créditos en "
                + Partida.MAX_TURNOS + " turnos.");
        System.out.println("Cuidado: " + Jugador.LIMITE_LLAMADAS + " llamadas de atención y quedas suspendido.");
        System.out.println();
    }

    public void mostrarMenu() {
        System.out.println();
        System.out.println("1. Listar todas las cartas del mazo");
        System.out.println("2. Buscar carta por ID");
        System.out.println("3. Buscar carta por nombre");
        System.out.println("4. Ordenar el mazo por costo de energía");
        System.out.println("5. Ver mi mano");
        System.out.println("6. Usar una carta");
        System.out.println("7. Tomar una carta");
        System.out.println("8. Pasar el turno");
        System.out.println("0. Salir");
    }

    public void mostrarEstado(Jugador jugador, Tablero tablero) {
        System.out.println();
        System.out.println("=============== TURNO " + tablero.getTurno() + "/" + Partida.MAX_TURNOS
                + " ===============");
        System.out.println(jugador);
        System.out.println("Meta de créditos: " + jugador.getCreditos() + "/" + Partida.META_CREDITOS);
        System.out.println("Evento activo: " + tablero.resumenEvento());
    }

    public void mostrarCartas(String titulo, List<Carta> cartas) {
        System.out.println();
        System.out.println("--- " + titulo + " (" + cartas.size() + ") ---");
        if (cartas.isEmpty()) {
            System.out.println("(no hay cartas)");
            return;
        }
        for (Carta carta : cartas) {
            System.out.println(formatear(carta));
        }
    }

    public void mostrarCarta(Carta carta) {
        System.out.println();
        System.out.println("--- Detalle de la carta ---");
        System.out.println(formatear(carta));
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[!] " + mensaje);
    }

    public void mostrarFin(EstadoPartida estado, String motivo, Jugador jugador) {
        System.out.println();
        System.out.println("==========================================");
        System.out.println(estado == EstadoPartida.VICTORIA ? "          ¡VICTORIA!" : "          FIN DEL JUEGO");
        System.out.println("==========================================");
        System.out.println(motivo);
        System.out.println("Resultado final: " + jugador);
    }

    public String pedirTexto(String pregunta) {
        System.out.print(pregunta);
        return scanner.nextLine().trim();
    }

    public int pedirEntero(String pregunta) {
        while (true) {
            String texto = pedirTexto(pregunta);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                mostrarError("Escribe un número entero válido.");
            }
        }
    }

    public boolean confirmar(String pregunta) {
        return pedirTexto(pregunta + " (s/n): ").toLowerCase().startsWith("s");
    }

    private String formatear(Carta carta) {
        return carta + "  <" + carta.getUbicacion() + ">";
    }
}
