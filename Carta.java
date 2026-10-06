public abstract class Carta implements Comparable<Carta> {
    private final int id;
    private String nombre;
    private int costoEnergia;
    private String descripcion;
    private UbicacionCarta ubicacion;

    public Carta(int id, String nombre, int costoEnergia, String descripcion) {
        if (costoEnergia < 0) {
            throw new IllegalArgumentException("El costo de energía no puede ser negativo");
        }
        this.id = id;
        this.nombre = nombre;
        this.costoEnergia = costoEnergia;
        this.descripcion = descripcion;
        this.ubicacion = UbicacionCarta.MAZO;
    }

    public Carta(int id, String nombre, int costoEnergia) {
        this(id, nombre, costoEnergia, "Sin descripción");
    }

    public abstract String getTipo();

    public abstract String jugarCarta(Jugador jugador, Tablero tablero);

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCostoEnergia() {
        return costoEnergia;
    }

    public void setCostoEnergia(int costoEnergia) {
        if (costoEnergia < 0) {
            throw new IllegalArgumentException("El costo de energía no puede ser negativo");
        }
        this.costoEnergia = costoEnergia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UbicacionCarta getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(UbicacionCarta ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public int compareTo(Carta otra) {
        int porCosto = Integer.compare(this.costoEnergia, otra.costoEnergia);
        return (porCosto != 0) ? porCosto : Integer.compare(this.id, otra.id);
    }

    @Override
    public String toString() {
        return String.format("#%d %s [%s] | Energía: %d | %s",
                id, nombre, getTipo(), costoEnergia, descripcion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carta)) {
            return false;
        }
        return id == ((Carta) o).id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
