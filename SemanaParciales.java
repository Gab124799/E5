public class SemanaParciales extends CartaEventoCampus {
    public SemanaParciales(int id) {
        super(id, "Semana de Parciales", 2,
                "Los cursos dan +1 crédito, pero cada catedrático suma +1 llamada de atención.", 2);
    }

    @Override
    public String jugarCarta(Jugador jugador, Tablero tablero) {
        tablero.activarEvento(getNombre(), getDuracionTurnos(), 1, 0, 1);
        return String.format("Comienza la %s: durante %d turno(s) los cursos dan +1 crédito y los catedráticos +1 llamada.",
                getNombre(), getDuracionTurnos());
    }
}
