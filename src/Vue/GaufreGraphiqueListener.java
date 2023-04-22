package Vue;

import javax.swing.*;
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
            g.setToolTipText("Annuler le coup");
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_Refaire(MouseEvent e){
        int startx = g.posX_boutons;
        int starty = g.posY_bouton_refaire;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_bouton && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton) {
            g.setToolTipText("Refaire le coup");
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_Save(MouseEvent e){
        int startx = g.posX_save;
        int starty = g.posY_save_load;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_load_save && e.getY() >= starty && e.getY() <= starty+g.largeur_load_save) {
            g.setToolTipText("Sauvegarder la partie");
            //modifie la couleur du tooltiptext en BLUE
            //g.toolTip.setBackground(Color.BLUE);

            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_Load(MouseEvent e){
        int startx = g.posX_load;
        int starty = g.posY_save_load;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_load_save && e.getY() >= starty && e.getY() <= starty+g.largeur_load_save) {
            g.setToolTipText("Charger une partie");
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
                g.setToolTipText("Manger ce morceau");
                return true;
            }
        }else return false;
    }

    public boolean estCurseurSur_Poison(MouseEvent e){
        int startx = 0;
        int starty = 0;
        if(e.getX()<=startx+g.largeurCase && e.getY()<=starty+g.hauteurCase){
            g.setToolTipText("Vous ne pouvez pas manger ce morceau !");
            return true;
        }else{
            return false;
        }
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
        if(estCurseurSurBouton_Load(e)) {
            g.collecteur.clicCharger();
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
            if (estCurseurSurBouton_Annuler(e)||estCurseurSurBouton_Refaire(e)||estCurseurSurBouton_Save(e)||estCurseurSurBouton_Load(e)||estCurseurSurGaufre(e)) {
                g.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
                if(!estCurseurSur_Poison(e)) {
                    g.setToolTipText(null);
                }
                g.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}
