package Vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class GaufreGraphiqueListener implements MouseListener {

    GaufreGraphique g;


    public GaufreGraphiqueListener(GaufreGraphique gaufre) {
        super();
        g = gaufre;
        DetectionSurvol survol = new DetectionSurvol();
        g.addMouseMotionListener(survol);
    }

    public boolean estCurseurSurBouton_Annuler(MouseEvent e){
        int startx = g.posX_boutons;
        int starty = g.posY_bouton_annuler;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_bouton && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton) {
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_Refaire(MouseEvent e){
        int startx = g.posX_boutons;
        int starty = g.posY_bouton_refaire;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_bouton && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton) {
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_Save(MouseEvent e){
        int startx = g.posX_save;
        int starty = g.posY_save_load;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_load_save && e.getY() >= starty && e.getY() <= starty+g.largeur_load_save) {
            return true;
        }else return false;
    }

    public boolean estCurseurSurGaufre(MouseEvent e){
        int startx = 0;
        int starty = 0;
        if(e.getX() >= startx && e.getX() <= startx+g.largeurCase*g.colonnes() && e.getY() >= starty && e.getY() <= starty+g.hauteurCase*g.lignes()) {
            if(e.getX()<=startx+g.largeurCase && e.getY()<=starty+g.hauteurCase){
                return false;
            }else{
                return true;
            }
        }else return false;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(estCurseurSurBouton_Annuler(e)) {
            g.collecteur.clicAnnuler();
            g.miseAJour();
        }
        if(estCurseurSurBouton_Refaire(e)) {
            g.collecteur.clicRefaire();
            g.miseAJour();
        }
        if(estCurseurSurBouton_Save(e)) {
            g.collecteur.clicSauvegarder();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public class DetectionSurvol extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (estCurseurSurBouton_Annuler(e)||estCurseurSurBouton_Refaire(e)||estCurseurSurBouton_Save(e)||estCurseurSurGaufre(e)) {
                g.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
                g.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}
