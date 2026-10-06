public class CartaCurso extends Carta {
    private static final int DIFICULTAD_CON_BONO = 4;

    private int creditos;
    private int dificultad;

    public CartaCurso(int id, String nombre, int costoEnergia, String descripcion,
                      int creditos, int dificultad) {
        super(id, nombre, costoEnergia, descripcion);
        this.creditos = creditos;
        this.dificultad = dificultad;
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String jugarCarta(Jugador jugador, Tablero tablero) {
        int extraPorDificultad = (dificultad >= DIFICULTAD_CON_BONO) ? 1 : 0;
        int total = creditos + extraPorDificultad + tablero.getBonoCreditos();
        jugador.ganarCreditos(total);
        return String.format("Apruebas %s y ganas %d crédito(s) (base %d, esfuerzo +%d, evento +%d).",
                getNombre(), total, creditos, extraPorDificultad, tablero.getBonoCreditos());
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Créditos: %d | Dificultad: %d/5", creditos, dificultad);
    }
}
