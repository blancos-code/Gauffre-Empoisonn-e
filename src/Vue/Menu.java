package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Menu extends JPanel{
    boolean maximized;
    JLabel titre, progression;
    JToggleButton IA, animation;
    public Image bouton_joueurContreJoueur, bouton_joueurContreIA, bouton_IAContreIA, bouton_quitter, background;
    public int posX_boutons, posY_jcj, posY_jcia, posY_ia, posY_quitter, posX_background, posY_background;
    Dimension tailleEcran, tailleFenetre;
    int screenWidth, screenHeight, frameWidth, frameHeight, largeur_background, hauteur_background, largeur_bouton, hauteur_bouton;
    public JFrame frame;

    public Menu(JFrame f) throws IOException {
        //Chargement des images
        String CHEMIN = "ressources/";
        background = lisImage("background");
        bouton_joueurContreJoueur = lisImage("Jcj");
        bouton_joueurContreIA = lisImage("JcIA");
        bouton_IAContreIA = lisImage("IAcIA");
        bouton_quitter = lisImage("Quitter");
        // El�ments de l'interface
        frame = f;

        // Mise en place de l'interface
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        //centrer la fenetre
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //D�finition des dimensions de la fen�tre
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
        if(rapport_actuel>rapport) {// si la fen�tre est plus haute que large
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
        g.drawImage(bouton_joueurContreJoueur, posX_boutons, posY_jcj, largeur_bouton, hauteur_bouton, null);
    }

    public void afficheBoutonJoueurContreIA(Graphics g) {
        g.drawImage(bouton_joueurContreIA, posX_boutons, posY_jcia, largeur_bouton, hauteur_bouton,null);
    }

    public void afficheBoutonIAContreIA(Graphics g) {
        g.drawImage(bouton_IAContreIA, posX_boutons, posY_ia, largeur_bouton, hauteur_bouton,null);
    }

    public void afficheBoutonQuitter(Graphics g) {
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
}
