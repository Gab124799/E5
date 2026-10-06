import java.util.List;

public class Partida {
    public static final int META_CREDITOS = 25;
    public static final int MAX_TURNOS = 12;
    public static final int CARTAS_MANO_INICIAL = 3;

    private final Jugador jugador;
    private final Tablero tablero;
    private final Mazo mazo;
    private boolean tomoCartaEsteTurno;

    public Partida(String nombreJugador, List<Carta> catalogo) {
        this.jugador = new Jugador(nombreJugador);
        this.tablero = new Tablero();
        this.mazo = new Mazo();
        this.mazo.agregarTodas(catalogo);
        this.tomoCartaEsteTurno = false;
        for (int i = 0; i < CARTAS_MANO_INICIAL; i++) {
            this.mazo.robarCarta();
        }
    }

    public ResultadoAccion usarCarta(int idCarta) {
        if (getEstado() != EstadoPartida.EN_CURSO) {
            return new ResultadoAccion(false, "La partida ya terminó.");
        }
        Carta carta = mazo.buscar(idCarta);
        if (carta == null) {
            return new ResultadoAccion(false, "No existe ninguna carta con ID " + idCarta + ".");
        }
        if (carta.getUbicacion() != UbicacionCarta.MANO) {
            return new ResultadoAccion(false, "La carta \"" + carta.getNombre() + "\" no está en tu mano.");
        }
        if (!jugador.puedePagar(carta.getCostoEnergia())) {
            return new ResultadoAccion(false, String.format(
                    "Energía insuficiente: \"%s\" cuesta %d y tienes %d.",
                    carta.getNombre(), carta.getCostoEnergia(), jugador.getEnergia()));
        }
        jugador.gastarEnergia(carta.getCostoEnergia());

        String efecto = carta.jugarCarta(jugador, tablero);
        carta.setUbicacion(UbicacionCarta.DESCARTE);
        return new ResultadoAccion(true, "Jugaste \"" + carta.getNombre() + "\": " + efecto);
    }

    public ResultadoAccion tomarCarta() {
        if (getEstado() != EstadoPartida.EN_CURSO) {
            return new ResultadoAccion(false, "La partida ya terminó.");
        }
        if (tomoCartaEsteTurno) {
            return new ResultadoAccion(false, "Ya tomaste una carta en este turno.");
        }
        Carta carta = mazo.robarCarta();
        if (carta == null) {
            return new ResultadoAccion(false, "No quedan cartas para tomar: todas están en tu mano.");
        }
        tomoCartaEsteTurno = true;
        return new ResultadoAccion(true, "Tomaste: " + carta);
    }

    public ResultadoAccion pasarTurno() {
        if (getEstado() != EstadoPartida.EN_CURSO) {
            return new ResultadoAccion(false, "La partida ya terminó.");
        }
        String avisoEvento = tablero.avanzarTurno();
        tomoCartaEsteTurno = false;

        StringBuilder mensaje = new StringBuilder();
        if (getEstado() == EstadoPartida.EN_CURSO) {
            int energiaPorTurno = Jugador.ENERGIA_POR_TURNO + tablero.getBonoEnergia();
            int ganada = jugador.ganarEnergia(energiaPorTurno);
            mensaje.append(String.format("Comienza el turno %d: recibes %d de energía.",
                    tablero.getTurno(), ganada));
        } else {
            mensaje.append("Se terminó el último turno del semestre.");
        }
        if (!avisoEvento.isEmpty()) {
            mensaje.append(" ").append(avisoEvento);
        }
        return new ResultadoAccion(true, mensaje.toString());
    }

    public EstadoPartida getEstado() {
        if (jugador.getCreditos() >= META_CREDITOS) {
            return EstadoPartida.VICTORIA;
        }
        if (jugador.estaSuspendido() || tablero.getTurno() > MAX_TURNOS) {
            return EstadoPartida.DERROTA;
        }
        return EstadoPartida.EN_CURSO;
    }

    public String getMotivoFin() {
        switch (getEstado()) {
            case VICTORIA:
                return "¡Alcanzaste " + META_CREDITOS + " créditos y apruebas el semestre!";
            case DERROTA:
                if (jugador.estaSuspendido()) {
                    return "Acumulaste " + Jugador.LIMITE_LLAMADAS
                            + " llamadas de atención: quedas suspendido.";
                }
                return "Se acabó el semestre y no llegaste a " + META_CREDITOS + " créditos.";
            default:
                return "";
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Mazo getMazo() {
        return mazo;
    }

    public boolean haTomadoCartaEsteTurno() {
        return tomoCartaEsteTurno;
    }
}
