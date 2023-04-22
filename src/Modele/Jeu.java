package Modele;

import Patterns.Observable;

public class Jeu extends Observable {
    Gaufre g;
    Joueur joueur1, joueur2;
    int joueurCourant;
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
        //random entre 0 et 1 pr choisir joueur courant
        joueurCourant = (int) (Math.random() * 2);
        System.out.println("Joueur courant : " + joueurCourant);

    }

    public int joueurCourant(){
        return joueurCourant;
    }

    public void changeJoueurCourant(){
        if(joueurCourant == 0){
            joueurCourant = 1;
        }else{
            joueurCourant = 0;
        }
    }

    public void joue(Coup c){
        g.joue(c);
        g.affiche();
        metAJour();
        changeJoueurCourant();
    }

    public Gaufre gaufre() {
        return g;
    }

    public Coup annuler() {
        System.out.println("Annuler");
        Coup cp = g.annuler();
        metAJour();
        return cp;
    }

    public Coup refaire() {
        System.out.println("Refaire");
        Coup cp = g.refaire();
        metAJour();
        return cp;
    }

    public void reinitialiseGaufre() {
        g.reinitialise();
        metAJour();
    }
}
