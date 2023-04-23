package Modele;

import java.util.LinkedList;

public class Noeud {
    private int valeur;
    private int heuristic;
    private Noeud pere ;
    private LinkedList<Noeud> fils;

    public Noeud() {
        this.valeur = 0;
        this.heuristic = 0;
        this.pere = null;
        this.fils = new LinkedList<Noeud>();
    }

    public Noeud(int v) {
        this.valeur = v;
        this.pere = null;
        this.fils = new LinkedList<Noeud>();
    }

    public Noeud(int v, Noeud p) {
        this.valeur = v;
        this.heuristic = 0;
        this.pere = p;
        this.fils = new LinkedList<Noeud>();
    }

    /**
     * Test pour savoir si le noeud est une feuille
     *
     * @return true si le noeud est une feuille false sinon
     */
    public boolean estFeuille() {
        return this.fils.isEmpty();
    }

    /**
     * retourne la valeur heuristique du noeud
     *
     * @return le champ heuristic du noeud
     */
    public int heuristic() {
        return this.heuristic;
    }

    /**
     *
     * @return valeur du noeud
     */
    public int valeur() {
        return this.valeur;
    }

    /**
     *
     * @return pere du noeud
     */
    public Noeud pere() {
        return this.pere;
    }

    /**
     *
     * @return liste des fils du noeud
     */
    public LinkedList<Noeud> fils(){
        return this.fils;
    }

    /**
     *
     * @param i indice du fils a recupere
     * @return fils i du noeud
     */
    public Noeud fils(int i) {
        return this.fils.get(i);
    }

    public LinkedList<Noeud> filsTaggue(){
        LinkedList<Noeud> res = new LinkedList<Noeud>();
        for(Noeud n : this.fils) {
            if (n.heuristic() == this.heuristic()) {
                res.add(n);
            }
        }
        return res;
    }

    /**
     *
     * @param v nouvelle valeur pour le noeud
     */
    public void setValeur(int v) {
        this.valeur = v;
    }

    /**
     * Change la valeur de l'heuristique
     *
     * @param h nouvelle heuristique
     */
    public void setHeuristic(int h) {
        this.heuristic = h;
    }

    /**
     *
     * @param p nouveau pere pour le noeud
     */
    public void setPere(Noeud p) {
        this.pere = p;
    }

    /**
     * Remplace la liste des fils
     *
     * @param l nouvelle liste des noeud
     */
    public void setFils(LinkedList<Noeud> l) {
        this.fils = l;
    }

    /**
     *
     * @param n Noeud a ajouter a la liste des fils
     */
    public void addFils(Noeud n) {
        this.fils.add(n);
    }

    /**
     *
     * @param i indice du noeud a supprimer de la liste des fils
     */
    public void removeFils(int i) {
        this.fils.remove(i);
    }

}