public class Main {
    private static final int CARTAS_INICIALES = 12;

    public static void main(String[] args) {
        GeneradorCartas generador = new GeneradorCartas();
        Partida partida = new Partida("Jugador", generador.generar(CARTAS_INICIALES));

        ConsolaView vista = new ConsolaView();
        JuegoController controlador = new JuegoController(partida, vista);
        controlador.iniciar();
    }
}
