package Vue;

import Controleur.ControleurMediateur;
import Modele.Gaufre;
import Modele.Jeu;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GaufreGraphique extends JComponent implements Observateur {
    Image case_saine, case_poison, quitter, annuler, refaire, save, load, reset, case_saine_select, quitter_select,
            annuler_select, refaire_select, save_select, load_select, reset_select;
    Jeu j;
    int largeurCase, hauteurCase, largeur_bouton, hauteur_bouton, posX_boutons, posY_bouton_annuler, posY_bouton_refaire, posX_save, posX_load, posY_save_load,
    largeur_load_save, posY_bouton_quitter, posY_reset;
    JProgressBar progressBar;
    CollecteurEvenements collecteur;
    Graphics2D drawable;
    JFrame f;
    public int l, c;
    boolean select_quitter, select_annuler, select_refaire, select_save, select_load, select_reset;

    public GaufreGraphique(JFrame frame, Jeu jeu, CollecteurEvenements col) throws IOException {
        f = frame;
        j = jeu;
        collecteur = col;
        j.ajouteObservateur(this);
        case_saine = lisImage("case_saine");
        case_poison = lisImage("case_poison");
        quitter = lisImage("quitter_partie");
        annuler = lisImage("annuler");
        refaire = lisImage("refaire");
        save = lisImage("save");
        load = lisImage("load");
        reset = lisImage("reinitialiser");
        case_saine_select = lisImage("case_saine_select");
        quitter_select = lisImage("quitter_partie_select");
        annuler_select = lisImage("annuler_select");
        refaire_select = lisImage("refaire_select");
        save_select = lisImage("save_select");
        load_select = lisImage("load_select");
        reset_select = lisImage("reinitialiser_select");
        progressBar = new JProgressBar(0, 100);
        addMouseListener(new GaufreGraphiqueListener(this));
        l = j.gaufre().lignes();
        c = j.gaufre().colonnes();
        select_quitter = false;
        select_annuler = false;
        select_refaire = false;
        select_save = false;
        select_load = false;
        select_reset = false;
    }

    public void paintComponent(Graphics g) {
        drawable = (Graphics2D) g;
        drawable.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Gaufre gaufre = j.gaufre();
        int largeur = getSize().width;
        int hauteur = getSize().height;
        largeurCase = largeur / gaufre.colonnes();
        hauteurCase = (int) (hauteur*.8) / gaufre.lignes();
        // On prend des cases carr?es
        largeurCase = Math.min(largeurCase, hauteurCase);
        hauteurCase = largeurCase;
        //dessine un rectangle qui fait toute la frame
        g.setColor(new Color(69, 69, 69));
        g.fillRect(0, 0, largeur, hauteur);

        // Tracé de la gaufre
        for (int i = 0; i < gaufre.lignes(); i++) {
            for (int j = 0; j < gaufre.colonnes(); j++) {
                if (i ==0 && j == 0) {
                    tracer(drawable, case_poison, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                } else {
                    if(!gaufre.estMangee(i,j)) {
                        if(i>=l && j>=c)
                            tracer(drawable, case_saine_select, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                        else
                            tracer(drawable, case_saine, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                    }
                }
            }
        }
        //affiche un texte "joueur 1" ou "joueur 2" en fonction du joueur courant
        Font font = new Font("Roboto", Font.BOLD, (int)(hauteurCase*0.4));
        drawable.setFont(font);
        posX_boutons = (int) (gaufre.colonnes()*largeurCase+largeurCase*0.2);
        if (j.joueurCourant() == 1) {
            drawable.setColor(Color.RED);
            drawable.drawString("Joueur 1", (int)(posX_boutons*1.015), (int) (hauteur*.70));
        } else {
            drawable.setColor(Color.ORANGE);
            drawable.drawString("Joueur 2", (int)(posX_boutons*1.015), (int) (hauteur*.70));
        }
        //affiche le bouton quitter la partie
        double rapport_bouton = (double) 207/603;
        largeur_bouton= (int) (largeurCase*1.8);
        hauteur_bouton= (int) (largeur_bouton*rapport_bouton);
        posY_bouton_quitter = (int) (hauteur*.10);
        if(select_quitter) {
            System.out.println("select_quitter");
            tracer(drawable, quitter_select, posX_boutons, posY_bouton_quitter, largeur_bouton, hauteur_bouton);
        }else
            tracer(drawable, quitter, posX_boutons, posY_bouton_quitter, largeur_bouton, hauteur_bouton);
        //affiche les boutons annuler et refaire
        posY_bouton_annuler = (int) (hauteur*.22);
        posY_bouton_refaire = (int) (hauteur*.34);
        if(select_annuler)
            tracer(drawable, annuler_select, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
        else
            tracer(drawable, annuler, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
        if(select_refaire)
            tracer(drawable, refaire_select, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
        else
            tracer(drawable, refaire, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
        //affiche le bouton reset
        posY_reset = (int) (hauteur*.46);
        if(select_reset)
            tracer(drawable, reset_select, posX_boutons, posY_reset, largeur_bouton, hauteur_bouton);
        else
            tracer(drawable, reset, posX_boutons, posY_reset, largeur_bouton, hauteur_bouton);
        //affiche les boutons save et load
        double rapport_bouton_save_load = 1.0;
        largeur_load_save = (int) (largeurCase*0.55);
        posY_save_load = 0;
        posX_save = posX_boutons + (int) (largeur_bouton*0.12);
        posX_load = posX_save + (int) (largeur_bouton*0.45);
        if(select_save)
            tracer(drawable, save_select, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
        else
            tracer(drawable, save, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
        if(select_load)
            tracer(drawable, load_select, posX_load, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
        else
            tracer(drawable, load, posX_load, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));

        //créer une barre de progression
        int progress = (int) (gaufre.progression());
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        progressBar.setBounds(0, (int) (hauteur*.85), largeur, (int) (hauteurCase*0.3));
        progressBar.setForeground(new Color(58, 170, 53));
        progressBar.setString("Progression : " + progress + "%");
        Font progressFont = new Font("Roboto", Font.BOLD, largeurCase/5);
        progressBar.setFont(progressFont);
        add(progressBar);
    }

    private void tracer(Graphics2D g, Image i, int x, int y, int l, int h) {
        g.drawImage(i, x, y, l, h, null);
    }

    public int largeurCase(){
        return largeurCase;
    }

    public int hauteurCase(){
        return hauteurCase;
    }

    public int colonnes(){
        return j.gaufre().colonnes();
    }

    public int lignes(){
        return j.gaufre().lignes();
    }

    private Image lisImage(String nom) {
        String CHEMIN = "ressources/";
        Image img = null;
        try{
            img = ImageIO.read(new File(CHEMIN + nom + ".png"));
        } catch (IOException e) {
            System.err.println("Impossible de charger l'image " + nom);
        }
        return img;
    }
    @Override
    public void miseAJour() {
        repaint();
    }
}
