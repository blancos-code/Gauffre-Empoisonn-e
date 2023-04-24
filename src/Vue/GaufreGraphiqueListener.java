package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class GaufreGraphiqueListener implements MouseListener {

    GaufreGraphique g;
    JLabel gifLabel;

    Cursor idle_cursor;
    Cursor gaufre_cursor;

    public GaufreGraphiqueListener(GaufreGraphique gaufre) {
        super();
        g = gaufre;
        DetectionSurvol survol = new DetectionSurvol();
        g.addMouseMotionListener(survol);

        Image cursorImage = Toolkit.getDefaultToolkit().getImage("ressources/normal_cursor.png");
        Image cursorImage2 = Toolkit.getDefaultToolkit().getImage("ressources/gaufre_cursor.png");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotspot = new Point(0, 0); // Définissez les coordonnées du point d'ancrage du curseur si nécessaire
        idle_cursor = toolkit.createCustomCursor(cursorImage, hotspot, "Custom Cursor");
        gaufre_cursor = toolkit.createCustomCursor(cursorImage2, hotspot, "Custom Cursor");
    }

    public boolean estCurseurSurBouton_Save(MouseEvent e){
        if(g.finPartie) return false;
        int startx = g.posX_save;
        int starty = g.posY_save_load;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_load_save && e.getY() >= starty && e.getY() <= starty+g.largeur_load_save) {
            g.select_save = true;
            g.setToolTipText("Sauvegarder la partie");
            return true;
        }
        g.select_save = false;
        return false;
    }

    public boolean estCurseurSurBouton_Load(MouseEvent e){
        int startx = g.posX_load;
        int starty = g.posY_save_load;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_load_save && e.getY() >= starty && e.getY() <= starty+g.largeur_load_save) {
            g.select_load = true;
            g.setToolTipText("Charger une partie");
            return true;
        }
        g.select_load = false;
        return false;
    }

    public boolean estCurseurSurBouton_Quitter(MouseEvent e){
        int startx = g.posX_boutons;
        int starty = g.posY_bouton_quitter;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_bouton && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton && g.boutonVoler[0]==0) {
            g.select_quitter = true;
            g.setToolTipText("Quitter la partie et revenir au menu du jeu");
            return true;
        }
        g.select_quitter = false;
        return false;
    }

    public boolean estCurseurSurBouton_Annuler(MouseEvent e){
        if(g.finPartie) return false;
        int startx = g.posX_boutons;
        int starty = g.posY_bouton_annuler;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_bouton && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton && g.boutonVoler[1]==0) {
            g.select_annuler = true;
            g.setToolTipText("Annuler le coup");
            return true;
        }
        g.select_annuler = false;
        return false;
    }

    public boolean estCurseurSurBouton_Refaire(MouseEvent e){
        if(g.finPartie) return false;
        int startx = g.posX_boutons;
        int starty = g.posY_bouton_refaire;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_bouton && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton && g.boutonVoler[2]==0) {
            g.select_refaire = true;
            g.setToolTipText("Refaire le coup");
            return true;
        }
        g.select_refaire = false;
        return false;
    }

    public boolean estCurseurSurBouton_Reset(MouseEvent e){
        int startx = g.posX_boutons;
        int starty = g.posY_reset;
        if(e.getX() >= startx && e.getX() <= startx+g.largeur_bouton && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton && g.boutonVoler[3]==0) {
            g.select_reset = true;
            g.setToolTipText("Recommencer la partie");
            return true;
        }
        g.select_reset = false;
        return false;
    }

    public boolean estCurseurSurGaufre(MouseEvent e){
        if(estCurseurSurSinge(e)) return false;
        if(g.largeurCase == 0 || g.hauteurCase == 0) return false;
        int startx = 0;
        int starty = 0;
        int c = e.getX()/g.largeurCase;
        int l = e.getY()/g.hauteurCase;
        if(e.getX() >= startx && e.getX() <= startx+g.largeurCase*g.colonnes() && e.getY() >= starty && e.getY() <= starty+g.hauteurCase*g.lignes() && !estCurseurSurSinge(e)){
            if(e.getX()<=startx+g.largeurCase && e.getY()<=starty+g.hauteurCase){
                //g.miseAJour();
                return false;
            }else{
                if(l<g.j.gaufre().lignes() && c<g.j.gaufre().colonnes() && !g.j.gaufre().estMangee(l,c) && !estCurseurSurSinge(e)) {
                    g.l=l;
                    g.c=c;
                    g.setToolTipText("Manger ce morceau");
                    //.miseAJour();
                    return true;
                }else{
                    g.l=g.lignes();
                    g.c=g.colonnes();
                    //g.miseAJour();
                    return false;
                }
            }
        }
        g.l=g.lignes();
        g.c=g.colonnes();
        //g.miseAJour();
        return false;
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

    public boolean estCurseurSurSinge(MouseEvent e){
        int startx = g.getSingePosition();
        int starty = g.getPosYBoutonAvoler();
        if(e.getX() >= startx && e.getX() <= startx+(g.largeur_bouton/2) && e.getY() >= starty && e.getY() <= starty+g.hauteur_bouton) {
            g.select_reset = true;
            g.setToolTipText("Recommencer la partie");
            return true;
        }
        return false;
    }

    public void verif(MouseEvent e){
        if(estCurseurSurBouton_Quitter(e)) {
            g.f.getContentPane().removeAll();
            Menu menu = null;
            try {
                menu = new Menu(g.f);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            g.f.add(menu);
            g.f.revalidate();//redessine la fenetre
        }
        if(estCurseurSurBouton_Annuler(e)) {
            g.collecteur.clicAnnuler();
            //g.miseAJour();
        }
        if(estCurseurSurBouton_Refaire(e)) {
            g.collecteur.clicRefaire();
            //g.miseAJour();
        }
        if(estCurseurSurBouton_Save(e)) {
            g.collecteur.clicSauvegarder();
        }
        if(estCurseurSurBouton_Load(e)) {
            g.collecteur.clicCharger();
        }
        if(estCurseurSurBouton_Reset(e)) {
            g.collecteur.clicRecommencer();
            //g.miseAJour();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        verif(e);
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
            if(estCurseurSurSinge(e)) if(!g.SingePeur && !g.arriveSinge) g.tuerSinge();

            if (estCurseurSurBouton_Quitter(e)||estCurseurSurBouton_Annuler(e)||estCurseurSurBouton_Refaire(e)||
                    estCurseurSurBouton_Save(e)||estCurseurSurBouton_Load(e)||estCurseurSurGaufre(e)||estCurseurSurBouton_Reset(e)) {
                g.setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (estCurseurSurGaufre(e)) {
                    g.setCursor(gaufre_cursor);
                    //g.miseAJour();
                } else {
                    if (!estCurseurSur_Poison(e)) {
                        g.setToolTipText(null);
                    }
                    g.setCursor(idle_cursor);
                }
            }
        }
    }
}
