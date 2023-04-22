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
    public boolean clicSouris(int l, int c) {
        Coup cp = new Coup(l, c, 1, 0);
        return jeu.joueJoueur(cp);
    }

    @Override
    public void toucheClavier(String t) {
    }

    public void clicAnnuler(){
        jeu.annuler();
    }

    @Override
    public void clicRefaire() {
        jeu.refaire();
    }

    @Override
    public void clicSauvegarder() {
        jeu.sauvegarder();
    }

    @Override
    public void clicCharger() {
        jeu.charger();
    }

    @Override
    public void clicRecommencer() {
        jeu.reinitialiseGaufre();
    }

    @Override
    public void ajouteInterfaceUtilisateur(GaufreGraphique v) {
        vue = v;
    }

}
