package Vue;

import Controleur.ControleurMediateur;
import Modele.Jeu;
import Modele.Parametres;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class MenuListener implements MouseListener {

    public Menu m;
    public String type_jeu = "JcJ";

    public MenuListener(Menu menu) {
        super();
        m = menu;
        DetectionSurvol survol = new DetectionSurvol();
        m.addMouseMotionListener(survol);
        m.frame.repaint();
    }

    public boolean estCurseurSurBouton_JcJ(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_jcj;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            this.type_jeu = "JCJ";
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_JcIA(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_jcia;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            this.type_jeu = "JCAI";
            return true;
        }else return false;
    }

    public boolean estCurseurSurBouton_IA(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_ia;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            this.type_jeu = "AICAI";
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
            //efface tout le contenu de la frame
            m.frame.getContentPane().removeAll();
            //ajoute une gaufregraphique � la frame
            Parametres p;
            try {
                p = new Parametres();
                p.setType_jeu(this.type_jeu);
                p.lireFichierParametres();
                Jeu jeu = new Jeu(p);
                CollecteurEvenements collecteur = new ControleurMediateur(jeu);
                GaufreGraphique vue = new GaufreGraphique(jeu, collecteur);
                m.frame.add(vue);
                collecteur.ajouteInterfaceUtilisateur(vue);
                vue.addMouseListener(new AdaptateurSouris(vue, collecteur));
                m.frame.revalidate();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(estCurseurSurBouton_Quitter(e)){
            System.exit(0);
        }
        m.repaint();
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
