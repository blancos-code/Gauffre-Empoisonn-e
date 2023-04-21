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
import java.io.InputStream;

public class GaufreGraphique extends JComponent implements Observateur {
    Image case_saine, case_poison, annuler, refaire;
    Jeu j;
    int largeurCase, hauteurCase, largeur_bouton, hauteur_bouton, posX_boutons;
    JProgressBar progressBar;

    public GaufreGraphique(Jeu jeu) throws IOException {
        j = jeu;
        j.ajouteObservateur(this);
        case_saine = lisImage("case_saine");
        case_poison = lisImage("case_poison");
        annuler = lisImage("annuler");
        refaire = lisImage("refaire");
        progressBar = new JProgressBar(0, 100);
        CollecteurEvenements c = new ControleurMediateur(j);
        addMouseListener(new AdaptateurSouris(this, c));
    }

    public void paintComponent(Graphics g) {
        Graphics2D drawable = (Graphics2D) g;
        drawable.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Gaufre gaufre = j.gaufre();
        int largeur = getSize().width;
        int hauteur = getSize().height;
        largeurCase = largeur / gaufre.colonnes();
        hauteurCase = (int) (hauteur*.8) / gaufre.lignes();
        // On prend des cases carrées
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
                    tracer(drawable, case_saine, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                }
            }
        }
        //affiche un texte "joueur 1" ou "joueur 2" en fonction du joueur courant
        Font font = new Font("Roboto", Font.BOLD, 40);
        drawable.setFont(font);
        posX_boutons = (int) (gaufre.colonnes()*largeurCase+largeurCase*0.2);
        if (j.joueurCourant() == 1) {
            drawable.setColor(Color.RED);
            drawable.drawString("Joueur 1", posX_boutons, (int) (hauteur*.60));
        } else {
            drawable.setColor(Color.ORANGE);
            drawable.drawString("Joueur 2", posX_boutons, (int) (hauteur*.60));
        }
        double rapport_bouton = (double) 207/603;
        largeur_bouton=(int) (largeurCase*1.8);
        hauteur_bouton=(int) (largeur_bouton*rapport_bouton);
        tracer(drawable, annuler, posX_boutons, (int) (hauteur*.10), largeur_bouton, hauteur_bouton);
        tracer(drawable, refaire, posX_boutons, (int) (hauteur*.30), largeur_bouton, hauteur_bouton);

        //créer une barre de progression
        int progress = (int) (gaufre.progression());
        System.out.println("gaufre.progression() : " + gaufre.progression());
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
