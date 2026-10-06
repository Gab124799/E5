import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mazo {
    private final List<Carta> cartas;
    private final Random random;

    public Mazo() {
        this(new Random());
    }

    public Mazo(Random random) {
        this.cartas = new ArrayList<>();
        this.random = random;
    }

    public void agregar(Carta carta) {
        if (carta != null && !cartas.contains(carta)) {
            cartas.add(carta);
        }
    }

    public void agregarTodas(List<Carta> nuevas) {
        for (Carta carta : nuevas) {
            agregar(carta);
        }
    }

    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

    public int tamano() {
        return cartas.size();
    }

    public Carta buscar(int id) {
        for (Carta carta : cartas) {
            if (carta.getId() == id) {
                return carta;
            }
        }
        return null;
    }

    public List<Carta> buscar(String texto) {
        List<Carta> resultado = new ArrayList<>();
        String buscado = normalizar(texto);
        for (Carta carta : cartas) {
            if (normalizar(carta.getNombre()).contains(buscado)) {
                resultado.add(carta);
            }
        }
        return resultado;
    }

    public void ordenarPorEnergia() {
        ordenarPorEnergia(true);
    }

    public void ordenarPorEnergia(boolean ascendente) {
        if (ascendente) {
            Collections.sort(cartas);
        } else {
            Collections.sort(cartas, Collections.reverseOrder());
        }
    }

    public List<Carta> getCartasEn(UbicacionCarta ubicacion) {
        List<Carta> resultado = new ArrayList<>();
        for (Carta carta : cartas) {
            if (carta.getUbicacion() == ubicacion) {
                resultado.add(carta);
            }
        }
        return resultado;
    }

    public Carta robarCarta() {
        List<Carta> disponibles = getCartasEn(UbicacionCarta.MAZO);
        if (disponibles.isEmpty()) {
            reciclarDescarte();
            disponibles = getCartasEn(UbicacionCarta.MAZO);
        }
        if (disponibles.isEmpty()) {
            return null;
        }
        Carta elegida = disponibles.get(random.nextInt(disponibles.size()));
        elegida.setUbicacion(UbicacionCarta.MANO);
        return elegida;
    }

    private void reciclarDescarte() {
        for (Carta carta : cartas) {
            if (carta.getUbicacion() == UbicacionCarta.DESCARTE) {
                carta.setUbicacion(UbicacionCarta.MAZO);
            }
        }
    }

    private String normalizar(String texto) {
        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinTildes.toLowerCase().trim();
    }
}
