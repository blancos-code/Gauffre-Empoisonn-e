package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JPanel{
    boolean maximized;
    JLabel titre, progression;
    JToggleButton IA, animation;
    public Image bouton_jCj, bouton_jCIA, bouton_IACIA, bouton_quitter, background, bouton_jCj_select,
            bouton_jCIA_select, bouton_IACIA_select, bouton_quitter_select;
    public int posX_boutons, posY_jcj, posY_jcia, posY_ia, posY_quitter, posX_background, posY_background;
    Dimension tailleEcran, tailleFenetre;
    int screenWidth, screenHeight, frameWidth, frameHeight, largeur_background, hauteur_background, largeur_bouton, hauteur_bouton;
    public JFrame frame;
    public boolean select_jcj, select_jcia, select_ia, select_quitter;

    public Menu(JFrame f) throws IOException {
        //Chargement des images
        String CHEMIN = "ressources/";
        background = lisImage("background");
        bouton_jCj = lisImage("Jcj");
        bouton_jCIA = lisImage("JcIA");
        bouton_IACIA = lisImage("IAcIA");
        bouton_quitter = lisImage("quitter");
        bouton_jCj_select = lisImage("Jcj_select");
        bouton_jCIA_select = lisImage("JcIA_select");
        bouton_IACIA_select = lisImage("IAcIA_select");
        bouton_quitter_select = lisImage("quitter_select");
        //booléens
        select_jcj = false;
        select_jcia = false;
        select_ia = false;
        select_quitter = false;
        // Eléments de l'interface
        frame = f;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        //centrer la fenetre
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //Définition des dimensions de la fenêtre
        tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth=tailleEcran.width;
        screenHeight=tailleEcran.height;
        tailleFenetre=frame.getSize();
        frameWidth=tailleFenetre.width;
        frameHeight=tailleFenetre.width;
        //Ajout d'une interaction avec les boutons
        addMouseListener(new MenuListener(this));
    }

    private Image lisImage(String nom) throws IOException {
        String CHEMIN = "ressources/";
        return ImageIO.read(new File(CHEMIN + nom + ".png"));
    }

    public void afficheBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, frameWidth, frameHeight);
        double rapport = 0.5625;// rapport de 2160/3840
        double rapport_actuel = (double)frameHeight/(double)frameWidth;
        if(rapport_actuel>rapport) {// si la fenêtre est plus haute que large
            largeur_background=frameWidth;
            hauteur_background=(int)(largeur_background*rapport);
            posX_background=0;
            posY_background=(frameHeight-hauteur_background)/2;
        }
        else {
            hauteur_background=frameHeight;
            largeur_background=(int)(hauteur_background/rapport);
            posX_background=(frameWidth-largeur_background)/2;
            posY_background=0;
        }
        g.drawImage(background, posX_background, posY_background, largeur_background, hauteur_background, null);
    }

    public void afficheBoutonJoueurContreJoueur(Graphics g) {
        if(select_jcj)
            g.drawImage(bouton_jCj_select, posX_boutons, posY_jcj, largeur_bouton, hauteur_bouton, null);
        else
            g.drawImage(bouton_jCj, posX_boutons, posY_jcj, largeur_bouton, hauteur_bouton, null);
    }

    public void afficheBoutonJoueurContreIA(Graphics g) {
        if(select_jcia)
            g.drawImage(bouton_jCIA_select, posX_boutons, posY_jcia, largeur_bouton, hauteur_bouton,null);
        else
            g.drawImage(bouton_jCIA, posX_boutons, posY_jcia, largeur_bouton, hauteur_bouton,null);
    }

    public void afficheBoutonIAContreIA(Graphics g) {
        if(select_ia)
            g.drawImage(bouton_IACIA_select, posX_boutons, posY_ia, largeur_bouton, hauteur_bouton,null);
        else
            g.drawImage(bouton_IACIA, posX_boutons, posY_ia, largeur_bouton, hauteur_bouton,null);
    }

    public void afficheBoutonQuitter(Graphics g) {
        if(select_quitter)
            g.drawImage(bouton_quitter_select, posX_boutons, posY_quitter, largeur_bouton, hauteur_bouton,null);
        else
            g.drawImage(bouton_quitter, posX_boutons, posY_quitter, largeur_bouton, hauteur_bouton,null);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        frameWidth=frame.getWidth();
        frameHeight=frame.getHeight();
        double rapport_bouton=0.16475095785440613026819923371648;//rapport de 86/522
        largeur_bouton=Math.min(largeur_background/4, frameWidth/4);
        hauteur_bouton=(int)(largeur_bouton*rapport_bouton);
        posX_boutons=posX_background+largeur_background/2-largeur_bouton/2;
        posY_jcj=posY_background+hauteur_background/6-hauteur_bouton/2;
        posY_jcia=posY_jcj+hauteur_background/6;
        posY_ia=posY_jcia+hauteur_background/6;
        posY_quitter=posY_ia+hauteur_background/6;
        afficheBackground(g2d);
        afficheBoutonJoueurContreJoueur(g2d);
        afficheBoutonJoueurContreIA(g2d);
        afficheBoutonIAContreIA(g2d);
        afficheBoutonQuitter(g2d);
    }

    public void metAJour() {
        repaint();
    }
}
