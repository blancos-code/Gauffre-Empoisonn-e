package Vue;

import Modele.Parametres;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Menu extends JPanel{
    boolean maximized;
    JLabel titre, progression;
    JToggleButton IA, animation;

    int nombreGaufre = 50;
    public Image bouton_jCj, bouton_jCIA, bouton_IACIA, bouton_options, bouton_quitter, background, bouton_jCj_select,
            bouton_jCIA_select, bouton_IACIA_select, bouton_quitter_select, bouton_options_select, menu_options;

    public int posX_boutons, posY_jcj, posY_jcia, posY_ia, posY_options, posY_quitter, posX_background, posY_background,posWaffleY,
            posWaffleX, posX_menu_options, posY_menu_options;

    public Image[] waffles = new Image[9];
    private int[][] specGaufre = new int[nombreGaufre][5];
    Dimension tailleEcran, tailleFenetre;
    int screenWidth, screenHeight, frameWidth, frameHeight, largeur_background, hauteur_background, largeur_bouton, hauteur_bouton,
            largeur_menu_options, hauteur_menu_options;
    public JFrame frame;
    private JTextField field_joueur1, field_joueur2, field_lignes, field_colonnes;
    private JSlider slider_lignes, slider_colonnes;
    public boolean select_jcj, select_jcia, select_ia, select_options, select_quitter, clicOptions;

    public Menu(JFrame f) throws IOException {
        //Chargement des images
        String CHEMIN = "ressources/";
        background = lisImage("background");
        bouton_jCj = lisImage("Jcj");
        bouton_jCIA = lisImage("JcIA");
        bouton_IACIA = lisImage("IAcIA");
        bouton_options = lisImage("options");
        bouton_quitter = lisImage("quitter");
        menu_options = lisImage("menu_options");
        bouton_jCj_select = lisImage("Jcj_select");
        bouton_jCIA_select = lisImage("JcIA_select");
        bouton_IACIA_select = lisImage("IAcIA_select");
        bouton_options_select = lisImage("options_select");
        bouton_quitter_select = lisImage("quitter_select");
        Parametres p = new Parametres();
        field_joueur1 = new JTextField(p.getPrenom1());
        field_joueur2 = new JTextField(p.getPrenom2());
        field_lignes = new JTextField(p.getLignes());
        field_colonnes = new JTextField(p.getColonnes());
        field_joueur1.setBounds(100, 100, 100, 100);
        field_joueur1.setVisible(false);
        field_joueur2.setVisible(true);
        field_lignes.setVisible(true);
        field_colonnes.setVisible(true);

        for(int i=0;i<waffles.length;i++){
            waffles[i] = lisImage("Gaufres/gaufre_"+i);
        }
        //booléens
        select_jcj = false;
        select_jcia = false;
        select_ia = false;
        select_options = false;
        select_quitter = false;
        clicOptions = false;
        // Eléments de l'interface
        frame = f;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        //centrer la fenetre
        frame.setLocationRelativeTo(null);
        //ajout des éléments à la frame
        frame.add(field_joueur1);
        frame.setVisible(true);
        //Définition des dimensions de la fenêtre
        tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth=tailleEcran.width;
        screenHeight=tailleEcran.height;
        tailleFenetre=frame.getSize();
        frameWidth=tailleFenetre.width;
        frameHeight=tailleFenetre.width;
        posX_menu_options = frameWidth;

        for(int i=0;i<nombreGaufre;i++){
            specGaufre[i] = specificiteGaufre();
        }

        //Ajout d'une interaction avec les boutons
        addMouseListener(new MenuListener(this));
        boucle();
    }

    public void afficheMenuParametres(Graphics g) throws IOException {
        int posX_menu_options_arrivee = posX_boutons+(int)(largeur_bouton*1.07);
        if(clicOptions){
            if(posX_menu_options > posX_menu_options_arrivee){
                posX_menu_options-=10;
            }
            if(posX_menu_options < posX_menu_options_arrivee){
                posX_menu_options = posX_menu_options_arrivee;
            }
            g.drawImage(menu_options, posX_menu_options, posY_menu_options, largeur_menu_options, hauteur_menu_options, null);
            //affiche un texte
            int posX_textes = posX_menu_options+(int)(largeur_menu_options*0.25);
            int posX_chiffres = posX_menu_options+(int)(largeur_menu_options*0.45);
            int posY_joueur1 = posY_menu_options+(int)(hauteur_menu_options*0.34);
            int posY_joueur2 = posY_joueur1+(int)(hauteur_menu_options*0.2);
            int posY_lignes = posY_joueur2+(int)(hauteur_menu_options*0.2);
            int posY_colonnes = posY_lignes+(int)(hauteur_menu_options*0.2);
            int posY_slider1 = posY_joueur2+(int)(hauteur_menu_options*0.15);
            int posY_slider2 = posY_slider1+(int)(hauteur_menu_options*0.2);
            Font font = new Font("Roboto", Font.BOLD, (int)(hauteur_bouton*0.4));
            g.setFont(font);
            g.setColor(Color.WHITE);
            Parametres p = new Parametres();
            g.drawString(p.getPrenom1(), posX_textes, posY_joueur1);
            g.drawString(p.getPrenom2(), posX_textes, posY_joueur2);
            g.drawString(String.valueOf(p.getLignes()), posX_chiffres, posY_lignes);
            g.drawString(String.valueOf(p.getColonnes()), posX_chiffres, posY_colonnes);
            //affiche les slider
            slider_lignes = new JSlider(JSlider.HORIZONTAL, 3, 20, 5);
            //définit le placement du slider sur la fenêtre
            Color couleur = new Color(135,135,134);
            slider_lignes.setBounds((int) (posX_menu_options*1.01), posY_slider1, (int)(largeur_menu_options*0.95), (int)(hauteur_bouton*0.8));
            slider_lignes.setMajorTickSpacing(5);
            slider_lignes.setPaintTicks(true);// affiche les traits
            slider_lignes.setPaintLabels(true);// affiche les chiffres
            slider_lignes.setBackground(couleur);
            slider_lignes.setForeground(Color.WHITE);
            slider_lignes.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    System.out.println(slider_lignes.getValue());
                }
            });
            slider_colonnes = new JSlider(JSlider.HORIZONTAL, 3, 30, 8);
            slider_colonnes.setMajorTickSpacing(1);
            slider_colonnes.setPaintTicks(true);// affiche les traits
            slider_colonnes.setPaintLabels(true);// affiche les chiffres
            //add(slider_lignes);
        }else{
            posX_menu_options = frameWidth;
        }
        posY_menu_options = posY_jcj;
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

    public void afficheBoutonOptions(Graphics g) {
        if(select_options)
            g.drawImage(bouton_options_select, posX_boutons, posY_options, largeur_bouton, hauteur_bouton,null);
        else
            g.drawImage(bouton_options, posX_boutons, posY_options, largeur_bouton, hauteur_bouton,null);
    }
    public void afficheBoutonQuitter(Graphics g) {
        if(select_quitter)
            g.drawImage(bouton_quitter_select, posX_boutons, posY_quitter, largeur_bouton, hauteur_bouton,null);
        else
            g.drawImage(bouton_quitter, posX_boutons, posY_quitter, largeur_bouton, hauteur_bouton,null);
    }

    public int[] specificiteGaufre(){
        int[] spec = new int[4];
        Random r = new Random();
        int taille_max = Math.min(frameWidth/4, frameHeight/4);
        int rand = r.nextInt(taille_max);
        while(rand==0) rand = r.nextInt(taille_max);
        int value = r.nextInt(8);
        while(value==0) value = r.nextInt(8);
        spec[0] = value;
        spec[1] = Math.max(40,rand);
        spec[2] = r.nextInt(screenWidth);
        spec[3] = -r.nextInt(1200);
        return spec;
    }
    public void afficheWaffle(Graphics g){
        for(int i=0;i<nombreGaufre;i++){
            g.drawImage(waffles[specGaufre[i][0]], specGaufre[i][2], posWaffleY+specGaufre[i][3], specGaufre[i][1], specGaufre[i][1],null);
            if(posWaffleY==0){
                specGaufre[i] = specificiteGaufre();
            }
        }
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
        posY_jcj=posY_background+hauteur_background/8-hauteur_bouton/2;
        posY_jcia=posY_jcj+hauteur_background/8;
        posY_ia=posY_jcia+hauteur_background/8;
        posY_options=posY_ia+hauteur_background/8;
        posY_quitter=posY_options+hauteur_background/8;
        double rapport_menu_options = 1.0980140935297885970531710442024;//rapport de 1714/1561
        largeur_menu_options = hauteur_background/2;
        hauteur_menu_options = (int)(largeur_menu_options*rapport_menu_options);
        afficheBackground(g2d);
        afficheWaffle(g2d);
        afficheBoutonJoueurContreJoueur(g2d);
        afficheBoutonJoueurContreIA(g2d);
        afficheBoutonIAContreIA(g2d);
        afficheBoutonOptions(g2d);
        afficheBoutonQuitter(g2d);
        try {
            afficheMenuParametres(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void boucle(){
        Timer timer = new Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                metAJour();
            }
        });
        timer.start();
    }

    public void metAJour() {
        posWaffleY++;
        if(posWaffleY>screenHeight*1.35) posWaffleY = 0;
        repaint();
    }
}
