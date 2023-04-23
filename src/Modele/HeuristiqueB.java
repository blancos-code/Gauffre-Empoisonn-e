package Modele;

public class HeuristiqueB {
    //le nom parle de lui meme
    public static int nbzeroligne(int[][] tab, int i, int M) {
        int res = 0;
        for(int j = 0; j < M; j++) {
            if(tab[i][j] == 0) {
                res++;
            }
        }
        return res;
    }
    //le nom parle de lui meme
    public static int nbzerocolonne(int[][] tab, int j, int N) {
        int res = 0;
        for(int i = 0; i < N; i++) {
            if(tab[i][j] == 0) {
                res++;
            }
        }
        return res;
    }
    /**
     * calcule_heuristique : modifie par effet de bord l'heuristique d'un noeud donné pour le joueur B (inverse du joueur A)
     *  @param N : le noeud duquel il faut calculer l'heuristique
     */
    public static int calcule_heuristique(Noeud N) {
        //on recupere le tableau du pere

        //on recupere le tableau du noeud courant
        Plateau Pcurrent = TabConverter.ToTab(N.valeur());
        int[][] tabcurrent = Pcurrent.getTab();

        //on recupere les dimensions
        int hauteur = Pcurrent.hauteur();
        int largeur = Pcurrent.largeur();

        //traitement (on detecte des configurations gagnantes ou perdantes afin de les appliquer, ou pas.)


        if(tabcurrent[0][0] > 0) {
            return 0;

        }

        if((tabcurrent[0][1] == 0 && tabcurrent[0][1] > 0 ) || (tabcurrent[0][1] == 0 && tabcurrent[0][1] > 0)) {
            return 0;

        }
        if(nbzeroligne(tabcurrent,0,largeur) == nbzerocolonne(tabcurrent,0,hauteur) && tabcurrent[1][1]  == 0 && tabcurrent[0][0] == 0) {
            return 0;
        }
        if(nbzeroligne(tabcurrent,0,largeur) != nbzerocolonne(tabcurrent,0,hauteur) && tabcurrent[1][1] > 0) {
            return 0;
        }

        if((tabcurrent[0][1] > 0 && tabcurrent[1][0] > 0) && tabcurrent[0][0] == 0) {
            return 1000;
        }

        if(nbzeroligne(tabcurrent,0,largeur) == nbzerocolonne(tabcurrent,0,hauteur) && tabcurrent[1][1] > 0 && tabcurrent[0][0] == 0) {
            return 1000;
        }

        return 10;
    }
}
