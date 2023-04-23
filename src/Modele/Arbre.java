package Modele;

import java.util.LinkedList;

public class Arbre {

    private Noeud racine;

    // Constructeurs

    public Arbre() {
        this.racine = new Noeud();
    }

    /**
     * Constructeur pour une feuille
     *
     * @param  v  	Valeur entiere
     */
    public Arbre(int v) {
        this.racine = new Noeud(v);
    }

    /**
     * Constructeur pour un arbre
     *
     * @param v valeur du noeud a la racine
     * @param l liste des fils de la racine
     */
    public Arbre(int v, LinkedList<Noeud> l) {
        this.racine = new Noeud(v);
        racine.setFils(l);
    }

    /**
     * Getteur pour l'etiquette de la racine de l'arbre
     *
     * @return valeur de l'etiquette de la racine
     */
    public Noeud racine() {
        return this.racine;
    }
}