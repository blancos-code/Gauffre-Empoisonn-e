package Modele;

import Structures.Sequence;

import java.util.Random;

class IAAleatoire extends IA {
    public IAAleatoire() {
    }


    public Coup joue() {
        boolean estJouable = false;
        int i = 0, j = 0;
        while (!estJouable) {
            Random r = new Random();

            i = r.nextInt() % this.jeu.gaufre().lignes();
            j = r.nextInt() % this.jeu.gaufre().colonnes();

            if (!this.jeu.gaufre().estMangee(i, j)) {
                estJouable = true;
            }
        }
        return new Coup(i, j, 1, 0);
    }
}