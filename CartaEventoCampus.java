public abstract class CartaEventoCampus extends Carta {
    private int duracionTurnos;

    public CartaEventoCampus(int id, String nombre, int costoEnergia, String descripcion,
                             int duracionTurnos) {
        super(id, nombre, costoEnergia, descripcion);
        this.duracionTurnos = duracionTurnos;
    }

    @Override
    public String getTipo() {
        return "Evento Campus";
    }

    public int getDuracionTurnos() {
        return duracionTurnos;
    }

    public void setDuracionTurnos(int duracionTurnos) {
        this.duracionTurnos = duracionTurnos;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Duración: %d turno(s)", duracionTurnos);
    }
}
