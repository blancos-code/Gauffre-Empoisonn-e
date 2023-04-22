package Modele;

import Structures.SequenceListe;

public class Arbre {

    private int[][] courant;
    private Arbre filsGauche;

    private Arbre filsDroit;
    private Coup coup;

    private boolean coupGagnant;

    public Arbre(int[][] instanceCourante, Coup coupCourant, Arbre filsGauche, Arbre filsDroit, boolean coupGagnant){
        this.courant = instanceCourante;
        this.coup = coupCourant;
        this.filsGauche = filsGauche;
        this.filsDroit = filsDroit;
        this.coupGagnant = coupGagnant;
    }
    public int[][] getCourant(){
        return courant;
    }
    public Coup getCoupGagnat(){
        return coup;
    }
    public Arbre getFilsGauche(){
        return filsGauche;
    }
    public Arbre getFilsDroit(){
        return filsDroit;
    }
    public boolean getCoupGagnant(){ return coupGagnant; }
    public void setCoupGagnant(boolean estGagnant){
        this.coupGagnant = estGagnant;
    }
    public void setFilsGauche(Arbre filsGauche){
        this.filsGauche = filsGauche;
    }
    public void setFilsDroit(Arbre filsDroit){
        this.filsDroit = filsDroit;
    }


}
