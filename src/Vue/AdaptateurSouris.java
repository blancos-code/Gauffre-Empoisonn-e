package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdaptateurSouris extends MouseAdapter {

    GaufreGraphique g;
    CollecteurEvenements collecteur;

    AdaptateurSouris(GaufreGraphique gg, CollecteurEvenements c) {
        g = gg;
        collecteur = c;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        int l = e.getY() / g.largeurCase();
        int c = e.getX() / g.hauteurCase();
        if(l<g.lignes() && c<g.colonnes() && !(l==0 && c==0)){
            collecteur.clicSouris(l, c);
        }
    }
}
