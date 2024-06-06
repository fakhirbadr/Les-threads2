public class Sommeur implements Runnable {
    private int[] tableau;
    private int debut;
    private int fin;
    private int somme;

    public Sommeur(int[] tableau, int debut, int fin) {
        this.tableau = tableau;
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public void run() {
        for (int i = debut; i <= fin; i++) {
            somme += tableau[i];
        }
    }

    public int getSomme() {
        return somme;
    }
}
