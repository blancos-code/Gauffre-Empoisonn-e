package Modele;

public class Gaufre {
    protected int[][] cases;
    protected int lignes, colonnes;
    private int nb_cases_pleines;
    private int nb_cases;

    public Gaufre(int i, int j) {
        cases = new int[i][j];
        lignes = i;
        colonnes = j;
        nb_cases = i * j;
        nb_cases_pleines = nb_cases-1;
    }


    public void joue(Coup c) {
        for (int i = c.getI(); i<= lignes -1; i++)
            for (int j = c.getJ(); j<= colonnes -1; j++) {
                fixeValeurCase(1, i, j);
                nb_cases_pleines--;
            }
    }

    public double progression() {
        return (double) (nb_cases_pleines / (nb_cases-1))/100;
    }

    public int colonnes() {
        return colonnes;
    }

    public int lignes() {
        return lignes;
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
        System.out.println();
    }
}
