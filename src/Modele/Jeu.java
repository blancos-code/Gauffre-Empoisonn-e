package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Gaufre g;


    public Jeu(){
        int longueur = 5;
        int largeur = 5;
        g = new Gaufre(longueur, largeur);
    }

    public void joue(Coup c){
        g.joue(c);
        metAJour();
    }

    public Coup annuler() {
        Coup cp = g.annuler();
        metAJour();
        return cp;
    }

    public Coup refaire() {
        Coup cp = g.refaire();
        metAJour();
        return cp;
    }

    public void reinitialiseGaufre() {
        g.reinitialise();
        metAJour();
    }
}
