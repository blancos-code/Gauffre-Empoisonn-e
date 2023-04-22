package Controleur;

import Modele.Coup;
import Modele.Jeu;
import Vue.CollecteurEvenements;
import Vue.GaufreGraphique;

public class ControleurMediateur implements CollecteurEvenements {
    Jeu jeu;
    GaufreGraphique vue;

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

    public void clicAnnuler(){
        jeu.annuler();
    }

    @Override
    public void clicRefaire() {

    }

    @Override
    public void ajouteInterfaceUtilisateur(GaufreGraphique v) {
        vue = v;
    }

}
