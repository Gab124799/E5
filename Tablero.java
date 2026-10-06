public class Tablero {
    private int turno;
    private String eventoActivo;
    private int turnosRestantesEvento;
    private int bonoCreditos;
    private int bonoEnergia;
    private int recargoLlamadas;

    public Tablero() {
        this.turno = 1;
        limpiarEvento();
    }

    public void activarEvento(String nombre, int duracionTurnos,
                              int bonoCreditos, int bonoEnergia, int recargoLlamadas) {
        this.eventoActivo = nombre;
        this.turnosRestantesEvento = duracionTurnos;
        this.bonoCreditos = bonoCreditos;
        this.bonoEnergia = bonoEnergia;
        this.recargoLlamadas = recargoLlamadas;
    }

    public void activarEvento(String nombre, int duracionTurnos) {
        activarEvento(nombre, duracionTurnos, 0, 0, 0);
    }

    public String avanzarTurno() {
        turno++;
        if (eventoActivo == null) {
            return "";
        }
        turnosRestantesEvento--;
        if (turnosRestantesEvento <= 0) {
            String aviso = "Terminó el evento: " + eventoActivo + ".";
            limpiarEvento();
            return aviso;
        }
        return "";
    }

    private void limpiarEvento() {
        eventoActivo = null;
        turnosRestantesEvento = 0;
        bonoCreditos = 0;
        bonoEnergia = 0;
        recargoLlamadas = 0;
    }

    public boolean hayEventoActivo() {
        return eventoActivo != null;
    }

    public String resumenEvento() {
        if (eventoActivo == null) {
            return "Ninguno";
        }
        return String.format("%s (%d turno(s) restante(s)) [créditos +%d, energía/turno +%d, llamadas +%d]",
                eventoActivo, turnosRestantesEvento, bonoCreditos, bonoEnergia, recargoLlamadas);
    }

    public int getTurno() {
        return turno;
    }

    public String getEventoActivo() {
        return eventoActivo;
    }

    public int getTurnosRestantesEvento() {
        return turnosRestantesEvento;
    }

    public int getBonoCreditos() {
        return bonoCreditos;
    }

    public int getBonoEnergia() {
        return bonoEnergia;
    }

    public int getRecargoLlamadas() {
        return recargoLlamadas;
    }

    @Override
    public String toString() {
        return "Turno " + turno + " | Evento: " + resumenEvento();
    }
}
