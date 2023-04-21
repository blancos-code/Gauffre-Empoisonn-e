package Controleur;

import Modele.Coup;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.InterfaceUtilisateur;

public class ControleurMediateur implements CollecteurEvenements {
    Jeu jeu;
    InterfaceUtilisateur vue;


    public ControleurMediateur(Jeu j) {
        jeu = j;
    }


    @Override
    public void clicSouris(int l, int c) {
        Coup cp = new Coup(l, c, 1, 0);
        jeu.joue(cp);
    }

    @Override
    public void toucheClavier(String t) {

    }

    @Override
    public void ajouteInterfaceUtilisateur(InterfaceUtilisateur vue) {

    }

    @Override
    public void tictac() {

    }
}
