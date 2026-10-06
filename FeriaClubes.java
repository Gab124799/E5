public class FeriaClubes extends CartaEventoCampus {
    public FeriaClubes(int id) {
        super(id, "Feria de Clubes", 2,
                "Ganas +2 de energía extra al inicio de cada turno mientras dure.", 3);
    }

    @Override
    public String jugarCarta(Jugador jugador, Tablero tablero) {
        tablero.activarEvento(getNombre(), getDuracionTurnos(), 0, 2, 0);
        return String.format("Se abre la %s: durante %d turno(s) recibes +2 de energía extra al iniciar el turno.",
                getNombre(), getDuracionTurnos());
    }
}
