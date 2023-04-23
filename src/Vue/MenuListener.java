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
        m.metAJour();
    }

    public boolean estCurseurSurBouton_JcJ(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_jcj;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            m.select_jcj = true;
            this.type_jeu = "JcJ";
            return true;
        }
        m.select_jcj = false;
        return false;
    }

    public boolean estCurseurSurBouton_JcIA(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_jcia;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            m.select_jcia = true;
            this.type_jeu = "JcAI";
            return true;
        }
        m.select_jcia = false;
        return false;
    }

    public boolean estCurseurSurBouton_IA(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_ia;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            m.select_ia = true;
            this.type_jeu = "AIcAI";
            return true;
        }
        m.select_ia = false;
        return false;
    }

    public boolean estCurseurSurBouton_Quitter(MouseEvent e){
        int startx = m.posX_boutons;
        int starty = m.posY_quitter;
        if(e.getX() >= startx && e.getX() <= startx+m.largeur_bouton && e.getY() >= starty && e.getY() <= starty+m.hauteur_bouton) {
            m.select_quitter = true;
            return true;
        }
        m.select_quitter = false;
        return false;
    }

    public void verif(MouseEvent e){
        if(estCurseurSurBouton_JcJ(e) || estCurseurSurBouton_JcIA(e)){
            //efface tout le contenu de la frame
            m.frame.getContentPane().removeAll();
            //ajoute une gaufregraphique � la frame
            Parametres p;
            try {
                p = new Parametres();
                p.setType_jeu(this.type_jeu);
                p.sauvegarderParametres();
                p.lireFichierParametres();
                Jeu jeu = new Jeu(p);
                CollecteurEvenements collecteur = new ControleurMediateur(jeu);
                GaufreGraphique vue = new GaufreGraphique(m.frame, jeu, collecteur);
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
        m.metAJour();
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
            if (estCurseurSurBouton_JcJ(e)||estCurseurSurBouton_JcIA(e)||estCurseurSurBouton_IA(e)||estCurseurSurBouton_Quitter(e)) {
                m.setCursor(new Cursor(Cursor.HAND_CURSOR));
                m.metAJour();
            }else{
                m.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }
}
