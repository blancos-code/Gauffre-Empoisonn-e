package Modele;

import Patterns.Observable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Jeu extends Observable {
    Gaufre g;
    Joueur joueur1, joueur2;
    IA IA1, IA2;
    int joueurCourant;

    Object[] joueurs = new Object[2];
    Parametres p;

    int[]score =new int[2];

    public Jeu(Parametres p){
        this.p = p;
        int lignes = p.getLignes();
        int colonnes = p.getColonnes();
        score[0]= 0;
        score[1]=0;
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

    public Parametres getParametres(){
        return p;
    }

    public void lancePartie(){
        //random entre 0 et 1 pour choisir le joueur qui joue en premier
        joueurCourant = (int) (Math.random() * 2);

        if (estJoueurCourantUneIA()) {
            // Pour pas que l'IA joue directement
            // Attendez un certain temps avant d'exécuter l'action finale
            int delai = 1000; // delai en millisecondes (1000 ms = 1 s)
            Timer timer = new Timer(delai, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    joueIA();
                }
            });

            timer.setRepeats(false); // Ne répétez pas l'action finale, exécutez-là une seule fois
            timer.start(); // Démarrez le timer
        }
    }

    public String gagnant(){
        if(g.estFinit()){
            if(joueurCourant == 0){
                if(g.fini){
                    score[0]++;
                    g.fini = false;
                }
                return joueur1.prenom;
            }else{
                if(g.fini) {
                    score[1]++;
                    g.fini = false;
                }
                return joueur2.prenom;
            }
        }
        return null;
    }

    public int joueurCourant(){
        return joueurCourant;
    }

    public String getJoueur1(){
        return joueur1.prenom;
    }

    public String getJoueur2(){
        return joueur2.prenom;
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
        System.out.println("IA ???? : " + p.getTypeJeu());
        if (!estJoueurCourantUneIA()) {
            return;
        }
        Coup c = ((IA)joueurs[joueurCourant]).joue();
        if (gaufre().estMangee(c.i, c.j)) {
            return;
        }
        joue(c);

        if (p.getTypeJeu().compareTo("AIcAI") == 0) {
            System.out.println("Oui");
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
        }
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
        SaveScore();
        metAJour();
    }

    public void charger() {
        g.charger();
        LoadScore();
        metAJour();
    }

    public void reinitialiseGaufre() {
        g.reinitialise();
        lancePartie();
        metAJour();
    }

    public int getScorej1() {return score[0];}
    public int getScorej2() {return score[1];}

    public void SaveScore (){
        try {
            FileOutputStream  f = new FileOutputStream("sauvegarde1.txt");
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeInt(score[0]);
            out.writeInt(score[1]);
            out.close();
        }catch (Exception e){
            throw new RuntimeException();
        }

        }
    public void LoadScore (){
        try {
            FileInputStream fichier = new FileInputStream("sauvegarde1.txt");
            ObjectInputStream in = new ObjectInputStream(fichier);
            score[0]=in.readInt();
            score[1]=in.readInt();
            in.close();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
