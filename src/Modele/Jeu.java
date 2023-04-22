package Modele;

import Patterns.Observable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Jeu extends Observable {
    Gaufre g;
    Joueur joueur1, joueur2;
    IA IA1, IA2;
    int joueurCourant;

    Object[] joueurs = new Object[2];
    Parametres p;


    public Jeu(Parametres p){
        this.p = p;
        int lignes = p.getLignes();
        int colonnes = p.getColonnes();

        String type_jeu = p.getTypeJeu();
        switch (type_jeu) {
            case "JcJ":
                joueur1 = new Joueur(p.getPrenom1());
                joueur2 = new Joueur(p.getPrenom2());
                joueurs[0] = joueur1;
                joueurs[1] = joueur2;
                break;
            case "JcAI":
                joueur1 = new Joueur(p.getPrenom1());
                joueur2 = new Joueur(p.getType_IA());
                IA1 = IA.nouvelle(this, this.p);
                joueurs[0] = joueur1;
                joueurs[1] = IA1;
                break;
            case "AIcAI":
                joueur1 = new Joueur(p.getType_IA() + " 1");
                joueur2 = new Joueur(p.getType_IA() + " 2");
                IA1 = IA.nouvelle(this, this.p);
                IA2 = IA.nouvelle(this, this.p);
                joueurs[0] = IA1;
                joueurs[1] = IA2;
                break;
        }
        g = new Gaufre(lignes, colonnes);
        lancePartie();
    }

    public void lancePartie(){
        //random entre 0 et 1 pour choisir le joueur qui joue en premier
        joueurCourant = (int) (Math.random() * 2);

        if (estJoueurCourantUneIA()) {
            // Pour pas que l'IA joue directement
            // Attendez un certain temps avant d'exécuter l'action finale
            int delai = 1000; // delai en millisecondes (1à00 ms = 1 s)
            Timer timer = new Timer(delai, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    joueIA();
                }
            });

            timer.setRepeats(false); // Ne répétez pas l'action finale, exécutez-la une seule fois
            timer.start(); // Démarrez le timer
        }
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


    public boolean joueJoueur(Coup c) {

        if (estJoueurCourantUneIA()) {
            return false;
        }
        if (gaufre().estMangee(c.i, c.j)) {
            return false;
        }

        if(joue(c)){
            return true;
        }

        // Attendez un certain temps avant d'exécuter l'action finale
        int delai = 500; // delai en millisecondes (500 ms = 0.5 s)
        Timer timer = new Timer(delai, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joueIA();
            }
        });

        timer.setRepeats(false); // Ne répétez pas l'action finale, exécutez-la une seule fois
        timer.start(); // Démarrez le timer
        return false;
    }

    public void joueIA() {
        if (!estJoueurCourantUneIA()) {
            return;
        }
        Coup c = ((IA)joueurs[joueurCourant]).joue();
        if (gaufre().estMangee(c.i, c.j)) {
            return;
        }
        joue(c);
    }

    public boolean joue(Coup c){
        g.joue(c);
        if(g.estFinit()){
            g.resetHistorique();
            metAJour();
            return true;
        }
        changeJoueurCourant();
        metAJour();
        return false;
    }

    public boolean estJoueurCourantUneIA() {
        return this.joueurs[joueurCourant] instanceof IA;
    }

    public Gaufre gaufre() {
        return g;
    }

    public void annuler() {
        g.annuler();
        metAJour();
    }

    public void refaire() {
        g.refaire();
        metAJour();
    }

    public void sauvegarder() {
        g.sauvegarder();
        metAJour();
    }

    public void charger() {
        g.charger();
        metAJour();
    }

    public void reinitialiseGaufre() {
        g.reinitialise();
        lancePartie();
        metAJour();
    }
}
