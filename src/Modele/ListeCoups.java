package Modele;

import java.util.LinkedList;

public class ListeCoups {
    LinkedList<Coup> liste_coups;

    public ListeCoups(){
        liste_coups = new LinkedList<>();
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
