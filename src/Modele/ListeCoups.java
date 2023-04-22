package Modele;

import java.util.LinkedList;
import java.io.Serializable;


public class ListeCoups implements Serializable {
    LinkedList<Coup> liste_coups;

    public ListeCoups(){
        liste_coups = new LinkedList<>();
    }

    public String toString(){
        String s = "";
        for(int k=0; k<liste_coups.size(); k++){
            s += liste_coups.get(k).toString();
            s += "/";
        }
        return s;
    }

    public void ajoute(Coup h) {
        liste_coups.addFirst(h);
    }

    public boolean estVide() {
        return liste_coups.isEmpty();
    }

    public LinkedList<Coup> getListe_coups() {
        return liste_coups;
    }

    public int taille() {
        return liste_coups.size();
    }
}
