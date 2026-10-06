public class Jugador {
    public static final int ENERGIA_INICIAL = 5;
    public static final int ENERGIA_MAXIMA = 10;
    public static final int ENERGIA_POR_TURNO = 2;
    public static final int LIMITE_LLAMADAS = 6;

    private String nombre;
    private int energia;
    private int creditos;
    private int llamadasAtencion;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.energia = ENERGIA_INICIAL;
        this.creditos = 0;
        this.llamadasAtencion = 0;
    }

    public boolean puedePagar(int costo) {
        return energia >= costo;
    }

    public boolean gastarEnergia(int costo) {
        if (!puedePagar(costo)) {
            return false;
        }
        energia -= costo;
        return true;
    }

    public int ganarEnergia(int cantidad) {
        int antes = energia;
        energia = Math.min(ENERGIA_MAXIMA, energia + cantidad);
        return energia - antes;
    }

    public void ganarCreditos(int cantidad) {
        creditos += cantidad;
    }

    public void recibirLlamadas(int cantidad) {
        llamadasAtencion += cantidad;
    }

    public boolean estaSuspendido() {
        return llamadasAtencion >= LIMITE_LLAMADAS;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEnergia() {
        return energia;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getLlamadasAtencion() {
        return llamadasAtencion;
    }

    @Override
    public String toString() {
        return String.format("%s | Energía: %d/%d | Créditos: %d | Llamadas: %d/%d",
                nombre, energia, ENERGIA_MAXIMA, creditos, llamadasAtencion, LIMITE_LLAMADAS);
    }
}
