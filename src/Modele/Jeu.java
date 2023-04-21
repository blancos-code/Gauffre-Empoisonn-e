package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Gaufre g;
    Joueur joueur1, joueur2;
    Parametres p;


    public Jeu(Parametres p) {
        this.p = p;
        int lignes = p.getLignes();
        int colonnes = p.getColonnes();
        joueur1 = new Joueur(p.getPrenom1());
        joueur2 = new Joueur(p.getPrenom2());
        g = new Gaufre(lignes, colonnes);
        lancePartie();
    }

    public void lancePartie(){
        g.affiche();
        joue(new Coup(3, 5, 0, 1));
        g.affiche();
        joue(new Coup(2, 4, 0, 1));
    }

    public void joue(Coup c){
        g.joue(c);
        metAJour();
        //commentaire
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
