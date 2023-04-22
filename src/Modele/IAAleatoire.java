package Modele;

import Structures.Sequence;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class IAAleatoire extends IA {
    public IAAleatoire() {
    }


    public Coup joue() {
        boolean estJouable = false;
        int i = 0, j = 0;
        while (!estJouable) {
            Random r = new Random();

            i = r.nextInt(this.jeu.gaufre().lignes());
            j = r.nextInt(this.jeu.gaufre().colonnes());

            if (!this.jeu.gaufre().estMangee(i, j) && !(i == 0 && j == 0)) {
                estJouable = true;
            }
        }

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Coup(i, j, 1, 0);
    }
}