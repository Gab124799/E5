public class RecesoAcademico extends CartaEventoCampus {
    private static final int ENERGIA_RECUPERADA = 3;

    public RecesoAcademico(int id) {
        super(id, "Receso Académico", 1,
                "Recuperas " + ENERGIA_RECUPERADA + " de energía al instante.", 1);
    }

    @Override
    public String jugarCarta(Jugador jugador, Tablero tablero) {
        tablero.activarEvento(getNombre(), getDuracionTurnos());
        int ganada = jugador.ganarEnergia(ENERGIA_RECUPERADA);
        return String.format("Receso Académico: descansas y recuperas %d de energía.", ganada);
    }
}
