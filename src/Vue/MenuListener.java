package Vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class MenuListener implements MouseListener {

    public Menu m;

    public MenuListener(Menu menu) {
        super();
        m = menu;
        DetectionSurvol survol = new DetectionSurvol();
        m.addMouseMotionListener(survol);
    }

    public boolean estCurseurSurBouton_JcJ(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_jcj;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_JcIA(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_jcia;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_IA(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_ia;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_Quitter(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_quitter;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            return true;
        }else return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(estCurseurSurBouton_JcJ(e)){
            System.out.println("JcJ");
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
            if (estCurseurSurBouton_JcJ(e)||estCurseurSurBouton_JcIA(e)||estCurseurSurBouton_IA(e)||estCurseurSurBouton_Quitter(e)) {
                m.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }else{
                m.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}
