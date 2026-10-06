    
public class CartaCatedratico extends Carta {

    private static final int MINUTOS_POR_PUNTO_DE_ENERGIA = 15;

    private String departamento;
    private int llamadasAtencion;
    private int tiempoAtencion;

    public CartaCatedratico(int id, String nombre, int costoEnergia, String descripcion,
                            String departamento, int llamadasAtencion, int tiempoAtencion) {
        super(id, nombre, costoEnergia, descripcion);
        this.departamento = departamento;
        this.llamadasAtencion = llamadasAtencion;
        this.tiempoAtencion = tiempoAtencion;
    }

    @Override
    public String getTipo() {
        return "Catedrático";
    }

    @Override
    public String jugarCarta(Jugador jugador, Tablero tablero) {
        int energiaOfrecida = Math.max(1, tiempoAtencion / MINUTOS_POR_PUNTO_DE_ENERGIA);
        int energiaGanada = jugador.ganarEnergia(energiaOfrecida);
        int llamadas = llamadasAtencion + tablero.getRecargoLlamadas();
        jugador.recibirLlamadas(llamadas);
        return String.format("%s (%s) te atiende %d min: ganas %d de energía, pero recibes %d llamada(s) de atención.",
                getNombre(), departamento, tiempoAtencion, energiaGanada, llamadas);
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getLlamadasAtencion() {
        return llamadasAtencion;
    }

    public void setLlamadasAtencion(int llamadasAtencion) {
        this.llamadasAtencion = llamadasAtencion;
    }

    public int getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(int tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Depto: %s | Llamadas de atención: %d | Tiempo de atención: %d min",
                departamento, llamadasAtencion, tiempoAtencion);
    }
}
