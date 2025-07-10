
package Observador;

import java.util.ArrayList;
import java.util.List;


public class Subject {
    

    private static List<Observer> observadores = new ArrayList<>();

    public static void agregarObservador(Observer o) {
        observadores.add(o);
    }

    public static void notificar(String mensaje) {
        for (Observer o : observadores) {
            o.actualizar(mensaje);
        }
    }
}

    

