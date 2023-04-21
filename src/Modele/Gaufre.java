package Modele;

public class Gaufre {
    protected int[][] cases;
    protected int longueur, largeur;

    public Gaufre(int i, int j) {
        cases = new int[i][j];
        longueur = i;
        largeur = j;
    }


    public void joue(Coup c) {
        for (int i=c.getI(); i<=longueur-1; i++)
            for (int j=c.getJ(); j<=largeur-1; j++)
                fixeValeurCase(1, i, j);
    }

    public Coup annuler() {
        return null;
    }


    public Coup refaire() {
        return null;
    }

    public void fixeValeurCase(int v, int i, int j) {
        cases[i][j] = v;
    }

    public void reinitialise() {
        for (int i=0; i<=longueur-1; i++)
            for (int j=0; j<=largeur-1; j++)
                fixeValeurCase(0, i, j);
    }

    public void affiche(){
        for (int i=0; i<=longueur-1; i++) {
            for (int j=0; j<=largeur-1; j++)
                System.out.print(cases[i][j] + " ");
            System.out.println();
        }
    }
}
