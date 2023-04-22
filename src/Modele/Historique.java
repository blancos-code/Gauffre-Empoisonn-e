package Modele;

import java.util.LinkedList;

public class Historique {
    LinkedList<ListeCoups> passe;
    LinkedList<ListeCoups> futur;

    public Historique(){
        passe = new LinkedList<>();
        futur = new LinkedList<>();
    }

    public void afficheFutur(){
        System.out.println("Affiche futur :");
        for(int k=0; k<futur.size(); k++){
            ListeCoups listeCoups = futur.get(k);
            for(int l=0; l<listeCoups.taille(); l++){
                Coup h = listeCoups.getListe_coups().get(l);
                h.affiche();
            }
        }
    }

    public void affichePasse(){
        System.out.println("Affiche passe :");
        for(int k=0; k<passe.size(); k++){
            ListeCoups listeCoups = passe.get(k);
            for(int l=0; l<listeCoups.taille(); l++){
                Coup h = listeCoups.getListe_coups().get(l);
                h.affiche();
            }
        }
    }

    public void ajoute(ListeCoups lc) {
        passe.addFirst(lc);
        futur.clear();
    }

    public ListeCoups annuler(){
        //System.out.println("On annule, taille de passe : " + passe.size());
        ListeCoups tete = passe.removeFirst();
        futur.addFirst(tete);
        //System.out.println("On retire un élément de passe, taille de futur : " + futur.size());
        //System.out.println("tete vide ? " + tete.estVide());
        //System.out.println("taille de passe : " + passe.size());
        return tete;
    }

    public ListeCoups refaire(){
        //System.out.println("On refait, taille de futur : " + futur.size());
        ListeCoups tete = futur.removeFirst();
        passe.addFirst(tete);
        //System.out.println("tete vide ? " + tete.estVide());
        //System.out.println("taille de futur : " + futur.size());
        return tete;
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }

}
