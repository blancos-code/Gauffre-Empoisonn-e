package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Gaufre g;
    Joueur joueur1, joueur2;


    public Jeu(){
        int lignes = 5;
        int colonnes = 8;
        joueur1 = new Joueur("Joueur 1");
        joueur2 = new Joueur("Joueur 2");
        g = new Gaufre(lignes, colonnes);
        lancePartie();
    }

    public void lancePartie(){
        g.affiche();
        joue(new Coup(3, 5, 0, 1));
        g.affiche();
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
