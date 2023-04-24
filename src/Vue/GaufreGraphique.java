package Vue;

import Controleur.ControleurMediateur;
import Modele.Gaufre;
import Modele.Jeu;
import Modele.Parametres;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GaufreGraphique extends JComponent implements Observateur {
    Image case_saine, case_poison, quitter, annuler, refaire, save, load, reset, case_saine_select, quitter_select,
            annuler_select, refaire_select, save_select, load_select, reset_select, victoire, annuler_lock, refaire_lock, save_lock, miettes;
    Jeu j;
    int largeurCase, hauteurCase, largeur_bouton, hauteur_bouton, posX_boutons, posY_bouton_annuler, posY_bouton_refaire, posX_save, posX_load, posY_save_load,
    largeur_load_save, posY_bouton_quitter, posY_reset;
    JProgressBar progressBar;
    CollecteurEvenements collecteur;
    Graphics2D drawable;
    JFrame f;
    public int l, c;
    boolean select_quitter, select_annuler, select_refaire, select_save, select_load, select_reset, finPartie;

    public GaufreGraphique(JFrame frame, Jeu jeu, CollecteurEvenements col) throws IOException {
        f = frame;
        j = jeu;
        collecteur = col;
        j.ajouteObservateur(this);
        case_saine = lisImage("case_saine");
        case_saine_select = lisImage("case_saine_select");
        case_poison = lisImage("case_poison");
        quitter = lisImage("quitter_partie");
        quitter_select = lisImage("quitter_partie_select");
        annuler = lisImage("annuler");
        annuler_select = lisImage("annuler_select");
        refaire_select = lisImage("refaire_select");
        annuler_lock = lisImage("annuler_lock");
        refaire = lisImage("refaire");
        refaire_lock = lisImage("refaire_lock");
        save = lisImage("save");
        save_lock = lisImage("save_lock");
        save_select = lisImage("save_select");
        load = lisImage("load");
        load_select = lisImage("load_select");
        reset = lisImage("reinitialiser");
        reset_select = lisImage("reinitialiser_select");
        victoire = lisImage("victoire");
        miettes = lisImage("Gaufres/miettes");
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
        finPartie = false;
    }

    public void videGaufre(Graphics g){
        Gaufre gaufre = j.gaufre();
        for (int i = 0; i < gaufre.lignes(); i++) {
            for (int j = 0; j < gaufre.colonnes(); j++) {
                tracer(drawable, lisImage("Gaufres/fond"), j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
            }
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        drawable = (Graphics2D) g;
        drawable.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Gaufre gaufre = j.gaufre();
        if(j.gaufre().estFinit()){
            finPartie = true;
        }else{
            finPartie = false;
        }
        int largeur = getSize().width;
        int hauteur = getSize().height;
        largeurCase = largeur / gaufre.colonnes();
        hauteurCase = (int) (hauteur*.8) / gaufre.lignes();
        // On prend des cases carrées
        largeurCase = Math.min(largeurCase, hauteurCase);
        hauteurCase = largeurCase;
        posX_boutons = (int) (gaufre.colonnes()*largeurCase+largeurCase*0.2);
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
                        if (i >= l && j >= c)
                            tracer(drawable, case_saine_select, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                        else
                            tracer(drawable, case_saine, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                    }else{
                        tracer(drawable, miettes, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                    }
                }
            }
        }
        //affiche le bouton quitter la partie
        double rapport_bouton = (double) 207/603;
        largeur_bouton= (int) Math.min(largeur*.29, hauteur*.29);
        hauteur_bouton= (int) (largeur_bouton*rapport_bouton);
        posY_bouton_quitter = (int) (hauteur*.10);
        if(select_quitter) {
            tracer(drawable, quitter_select, posX_boutons, posY_bouton_quitter, largeur_bouton, hauteur_bouton);
        }else
            tracer(drawable, quitter, posX_boutons, posY_bouton_quitter, largeur_bouton, hauteur_bouton);
        //affiche les boutons annuler et refaire
        posY_bouton_annuler = (int) (hauteur*.22);
        posY_bouton_refaire = (int) (hauteur*.34);
        if(!finPartie) {
            if (select_annuler)
                tracer(drawable, annuler_select, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
            else
                tracer(drawable, annuler, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
            if (select_refaire)
                tracer(drawable, refaire_select, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
            else
                tracer(drawable, refaire, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
        }else {
            tracer(drawable, annuler_lock, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
            tracer(drawable, refaire_lock, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
        }
        //affiche le bouton reset
        posY_reset = (int) (hauteur*.46);
        if(select_reset)
            tracer(drawable, reset_select, posX_boutons, posY_reset, largeur_bouton, hauteur_bouton);
        else
            tracer(drawable, reset, posX_boutons, posY_reset, largeur_bouton, hauteur_bouton);
        //affiche les boutons save et load
        double rapport_bouton_save_load = 1.0;
        largeur_load_save = (int) (hauteur_bouton*0.8);
        posY_save_load = 0;
        posX_save = posX_boutons + (int) (largeur_bouton*0.12);
        posX_load = posX_save + (int) (largeur_bouton*0.45);
        if(select_load)
            tracer(drawable, load_select, posX_load, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
        else
            tracer(drawable, load, posX_load, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
        if(!finPartie) {
            if(select_save)
                tracer(drawable, save_select, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
            else
                tracer(drawable, save, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
        }else {
            tracer(drawable, save_lock, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save*rapport_bouton_save_load));
        }
        //affiche un texte "joueur 1" ou "joueur 2" en fonction du joueur courant
        Font font = new Font("Roboto", Font.BOLD, (int)(hauteur_bouton*0.6));
        drawable.setFont(font);
        if(!j.gaufre().estFinit()) {
            if (j.joueurCourant() == 1) {
                drawable.setColor(Color.RED);
                drawable.drawString(j.getJoueur1(), (int) (posX_boutons * 1.015), (int) (hauteur * .70));
            } else {
                drawable.setColor(Color.ORANGE);
                drawable.drawString(j.getJoueur2(), (int) (posX_boutons * 1.015), (int) (hauteur * .70));
            }
        }
        //affichage si victoire
        affichevictoire(drawable);
        //créer une barre de progression
        int progress = (int) (gaufre.progression());
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        progressBar.setBounds(0, (int) (hauteur*.85), largeur, (int) (hauteur_bouton*0.5));
        progressBar.setForeground(new Color(58, 170, 53));
        progressBar.setString("Progression : " + progress + "%");
        Font progressFont = new Font("Roboto", Font.BOLD, hauteur_bouton/4);
        progressBar.setFont(progressFont);
        add(progressBar);
    }

    public void affichevictoire(Graphics g){
        int hauteur = getSize().height;
        if(j.gaufre().estFinit()){
            videGaufre(g);
            tracer(drawable, victoire, (int)(posX_boutons/2.1), 0, (int)(largeur_bouton*0.8), (int)(largeur_bouton*0.8));
            drawable.setColor(Color.ORANGE);
            Font font = new Font("Roboto", Font.BOLD, (int)(largeur_bouton*0.2));
            drawable.setFont(font);
            drawable.drawString("Partie terminée", (int)(posX_boutons/2.5), (int) (hauteur*.30));
            font = new Font("Roboto", Font.BOLD, (int)(largeur_bouton*0.1));
            drawable.setFont(font);
            Color color = new Color(255, 144, 0);
            drawable.setColor(color);
            drawable.drawString(j.gagnant()+" a gagné la partie", (int)(largeur_bouton*1.5), (int) (hauteur*.40));
            progressBar.setVisible(false);
        }else{
            progressBar.setVisible(true);
        }
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
