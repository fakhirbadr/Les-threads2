import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        int[] tableau = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int nbThreads = 4;
        int tailleSousTableau = tableau.length / nbThreads;

        // Créer un pool de threads
        ExecutorService pool = Executors.newFixedThreadPool(nbThreads);

        // Collecter les résultats de chaque tâche
        List<Future<Integer>> futures = new ArrayList<>();
        int debut = 0;
        for (int i = 0; i < nbThreads; i++) {
            int fin = debut + tailleSousTableau - 1;
            if (i == nbThreads - 1) {
                fin = tableau.length - 1;
            }
            Sommeur sommeur = new Sommeur(tableau, debut, fin);
            Future<Integer> future = pool.submit(sommeur, 0); // Passer 0 comme valeur par défaut
            futures.add(future);
            debut = fin + 1;
        }

        // Arrêter le pool de threads
        pool.shutdown();

        try {
            // Attendre que toutes les tâches se terminent ou jusqu'à un délai maximal de 10 secondes
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Calculer la somme totale
        int sommeTotale = 0;
        for (Future<Integer> future : futures) {
            try {
                sommeTotale += future.get(); // Ajouter la somme de la tâche à la somme totale
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("La somme totale est : " + sommeTotale);
    }
}
