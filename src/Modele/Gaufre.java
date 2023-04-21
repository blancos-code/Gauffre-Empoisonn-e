package Modele;

public class Gaufre {
    protected int[][] cases;
    protected int lignes, colonnes;

    public Gaufre(int i, int j) {
        cases = new int[i][j];
        lignes = i;
        colonnes = j;
    }


    public void joue(Coup c) {
        for (int i = c.getI(); i<= lignes -1; i++)
            for (int j = c.getJ(); j<= colonnes -1; j++)
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
        for (int i = 0; i<= lignes -1; i++)
            for (int j = 0; j<= colonnes -1; j++)
                fixeValeurCase(0, i, j);
    }

    public void affiche(){
        for (int i = 0; i<= lignes -1; i++) {
            for (int j = 0; j<= colonnes -1; j++)
                System.out.print(cases[i][j] + " ");
            System.out.println();
        }
    }
}
