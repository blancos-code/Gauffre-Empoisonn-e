package Modele;

import java.util.LinkedList;

public class Arbre2 {
    private Arbre2 pere;
    private LinkedList<Arbre2> fils;
    private boolean[][] config;


    public Arbre2(boolean[][] configuration) {
        this.pere = null;
        this.config = configuration;
        this.fils = new LinkedList<Arbre2>();
    }

    public boolean estFeuille(){
        return this.fils.isEmpty();
    }

    public boolean[][] getConfig() {
        return config;
    }

    public void setConfig(boolean[][] config) {
        this.config = config;
    }
}
