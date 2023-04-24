package Vue;

import Controleur.ControleurMediateur;
import Modele.Gaufre;
import Modele.Jeu;
import Modele.Parametres;
import Patterns.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class GaufreGraphique extends JComponent implements Observateur {
    Image case_saine, case_poison, quitter, annuler, refaire, save, load, reset, case_saine_select, quitter_select,
            annuler_select, refaire_select, save_select, load_select, reset_select, victoire, annuler_lock, refaire_lock, save_lock, miettes, singe;
    Jeu j;

    Image[] singes = new Image[8];
    Image[] balayeurs = new Image[13];


    int largeurCase, hauteurCase, largeur_bouton, hauteur_bouton, posX_boutons, posY_bouton_annuler, posY_bouton_refaire, posX_save, posX_load, posY_save_load, singe_index, balayeur_index, singepos, boutonAvoler,
            largeur_load_save, posY_bouton_quitter, posY_reset;
    JProgressBar progressBar;
    CollecteurEvenements collecteur;
    Graphics2D drawable;
    JFrame f;
    public int l, c;

    boolean clean = true;

    ArrayList<Coords> balayees = new ArrayList<>();
    boolean select_quitter, select_annuler, select_refaire, select_save, select_load, select_reset, finPartie, unefoisSinge;

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
        for(int i=1;i<9;i++){
            singes[i-1] = lisImage("Singe/monkey_run_"+i);
        }
        for(int i=1;i<13;i++){
            balayeurs[i-1] = lisImage("Balayeur/balayeur_frame_"+i);
        }
        balayeur_index = 0;
        singepos = -50;
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
        boucle();



    }

    public void videGaufre(Graphics g) {
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
        if (j.gaufre().estFinit()) {
            finPartie = true;
        } else {
            finPartie = false;
        }
        int largeur = getSize().width;
        int hauteur = getSize().height;
        largeurCase = largeur / gaufre.colonnes();
        hauteurCase = (int) (hauteur * .8) / gaufre.lignes();
        // On prend des cases carrées
        largeurCase = Math.min(largeurCase, hauteurCase);
        hauteurCase = largeurCase;
        posX_boutons = (int) (gaufre.colonnes() * largeurCase + largeurCase * 0.2);
        //dessine un rectangle qui fait toute la frame
        g.setColor(new Color(69, 69, 69));
        g.fillRect(0, 0, largeur, hauteur);

        // Tracé de la gaufre
        for (int i = 0; i < gaufre.lignes(); i++) {
            for (int j = 0; j < gaufre.colonnes(); j++) {
                if (i == 0 && j == 0) {
                    tracer(drawable, case_poison, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                } else {
                    if (!gaufre.estMangee(i, j)) {
                        if (i >= l && j >= c)
                            tracer(drawable, case_saine_select, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                        else
                            tracer(drawable, case_saine, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                    } else {
                        boolean afficher = true;
                        for (int k = 0; k < balayees.size(); k++) {
                            if (balayees.get(k).i == i && balayees.get(k).j == j) {
                                afficher = false;
                            }
                        }
                        if (afficher) {
                            tracer(drawable, miettes, j * largeurCase, i * hauteurCase, largeurCase, hauteurCase);
                        }
                    }
                }
            }
        }

        for (int i = gaufre.lignes() - 1; i > 0; i--) {
            for (int j = gaufre.colonnes() - 1; j > 0; j--) {
                if (gaufre.estMangee(i, j)) {
                    boolean afficher = true;
                    for (int k = 0; k < balayees.size(); k++) {
                        if (balayees.get(k).i == i && balayees.get(k).j == j) {
                            afficher = false;
                        }
                    }
                    if (afficher) {
                        tracer(drawable, balayeurs[balayeur_index], j * largeurCase - largeurCase / 3, i * hauteurCase - hauteurCase / 3, largeurCase, hauteurCase);
                        i = 0;
                        j = 0;
                    }
                }
            }
        }


        Calendar calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.MILLISECOND);
        if (seconds % 15 == 0) {
            if (clean) {
                for (int i = gaufre.lignes() - 1; i >= 0; i--) {
                    for (int j = gaufre.colonnes() - 1; j >= 0; j--) {
                        if (gaufre.estMangee(i, j)) {
                            boolean dejabalayee = false;
                            for (int k = 0; k < balayees.size(); k++) {
                                if (balayees.get(k).i == i && balayees.get(k).j == j) {
                                    dejabalayee = true;
                                }
                            }
                            if (!dejabalayee) {
                                balayees.add(new Coords(i, j));
                                i = 0;
                                j = 0;
                                clean = false;
                            }
                        }
                    }
                }
            }
        } else {
            clean = true;
        }

        //affiche le bouton quitter la partie
        double rapport_bouton = (double) 207 / 603;
        largeur_bouton = (int) Math.min(largeur * .29, hauteur * .29);
        hauteur_bouton = (int) (largeur_bouton * rapport_bouton);
        posY_bouton_quitter = (int) (hauteur * .10);
        if (select_quitter) {
            tracer(drawable, quitter_select, posX_boutons, posY_bouton_quitter, largeur_bouton, hauteur_bouton);
        } else
            tracer(drawable, quitter, posX_boutons, posY_bouton_quitter, largeur_bouton, hauteur_bouton);
        //affiche les boutons annuler et refaire
        posY_bouton_annuler = (int) (hauteur * .22);
        posY_bouton_refaire = (int) (hauteur * .34);
        if (!finPartie) {
            if (select_annuler)
                tracer(drawable, annuler_select, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
            else
                tracer(drawable, annuler, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
            if (select_refaire)
                tracer(drawable, refaire_select, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
            else
                tracer(drawable, refaire, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
        } else {
            tracer(drawable, annuler_lock, posX_boutons, posY_bouton_annuler, largeur_bouton, hauteur_bouton);
            tracer(drawable, refaire_lock, posX_boutons, posY_bouton_refaire, largeur_bouton, hauteur_bouton);
        }
        //affiche le bouton reset
        posY_reset = (int) (hauteur * .46);
        if (select_reset)
            tracer(drawable, reset_select, posX_boutons, posY_reset, largeur_bouton, hauteur_bouton);
        else
            tracer(drawable, reset, posX_boutons, posY_reset, largeur_bouton, hauteur_bouton);
        //affiche les boutons save et load
        double rapport_bouton_save_load = 1.0;
        largeur_load_save = (int) (hauteur_bouton * 0.8);
        posY_save_load = 0;
        posX_save = posX_boutons + (int) (largeur_bouton * 0.12);
        posX_load = posX_save + (int) (largeur_bouton * 0.45);
        if (select_load)
            tracer(drawable, load_select, posX_load, posY_save_load, largeur_load_save, (int) (largeur_load_save * rapport_bouton_save_load));
        else
            tracer(drawable, load, posX_load, posY_save_load, largeur_load_save, (int) (largeur_load_save * rapport_bouton_save_load));
        if (!finPartie) {
            if (select_save)
                tracer(drawable, save_select, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save * rapport_bouton_save_load));
            else
                tracer(drawable, save, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save * rapport_bouton_save_load));
        } else {
            tracer(drawable, save_lock, posX_save, posY_save_load, largeur_load_save, (int) (largeur_load_save * rapport_bouton_save_load));
        }
        //affiche un texte "joueur 1" ou "joueur 2" en fonction du joueur courant




        Font font2 = new Font("Roboto",Font.BOLD,(int)(hauteur_bouton*0.4));
        drawable.setFont(font2);
        drawable.setColor(Color.green);
        drawable.drawString("Score ",(int)(posX_boutons*1.32),(int) (hauteur*0.3));
        drawable.setColor(Color.ORANGE);
        drawable.drawString(j.getJoueur1()+" : "+j.getScorej1(),(int)(posX_boutons*1.30),(int) (hauteur*0.4));
        drawable.drawString(j.getJoueur2()+" : "+j.getScorej2(),(int)(posX_boutons*1.30),(int) (hauteur*0.5));
        Font font = new Font("Roboto", Font.BOLD, (int)(hauteur_bouton*0.6));
        drawable.setFont(font);
        if (j.joueurCourant() == 0) {
            drawable.setColor(Color.RED);
            drawable.drawString(j.getJoueur1(), (int) (posX_boutons * 1.015), (int) (hauteur * .70));
        } else {
            drawable.setColor(Color.ORANGE);
            drawable.drawString(j.getJoueur2(), (int) (posX_boutons * 1.015), (int) (hauteur * .70));
        }
        //affichage si victoire
        affichevictoire(drawable);
        //créer une barre de progression
        int progress = (int) (gaufre.progression());
        progressBar.setValue(progress);
        progressBar.setStringPainted(true);
        progressBar.setBounds(0, (int) (hauteur * .85), largeur, (int) (hauteur_bouton * 0.5));
        progressBar.setForeground(new Color(58, 170, 53));
        progressBar.setString("Progression : " + progress + "%");
        Font progressFont = new Font("Roboto", Font.BOLD, hauteur_bouton / 4);
        progressBar.setFont(progressFont);
        add(progressBar);
        appelSinge(g);
        if(unefoisSinge){
            boutonAvoler = choisirBoutonVoler();
            unefoisSinge=true;
        }
    }

    public void affichevictoire(Graphics g) {
        int hauteur = getSize().height;
        if (j.gaufre().estFinit()) {
            videGaufre(g);
            tracer(drawable, victoire, (int) (posX_boutons / 2.475), 0, (int) (getHeight() * 0.30), (int) (getHeight() * 0.30));
            drawable.setColor(Color.ORANGE);
            Font font = new Font("Roboto", Font.BOLD, (int) (getHeight() * 0.060));
            drawable.setFont(font);
            drawable.drawString("Partie terminée", (int) (posX_boutons / 2.475) - ("Partie terminée".length() * font.getSize() / 12), (int) (hauteur * .40));
            font = new Font("Roboto", Font.BOLD, (int) (getHeight() * 0.044));
            drawable.setFont(font);
            Color color = new Color(255, 144, 0);
            drawable.setColor(color);
            String phraseVictoire = j.gagnant() + " a gagné la partie";
            drawable.drawString(phraseVictoire, (int) (posX_boutons / 2.5) - ((phraseVictoire.length() * font.getSize()) / 11), (int) (hauteur * .48));
        }
    }

    private int choisirBoutonVoler(){
        int valeur;
        Random r = new Random();
        int boutton = r.nextInt(3);
        // on choisit aleatoirement un bouton à voler
        if(boutton==0){
            valeur = (int) (getSize().height*.10);
        } else if (boutton==1) {
            valeur = (int) (getSize().height*.22);
        } else if (boutton==1) {
            valeur = (int) (getSize().height*.34);
        } else{
            valeur = (int) (getSize().height*.46);
        }

        return valeur;
    }
    public void appelSinge(Graphics g){
        singe = singes[singe_index];
        //System.out.println("bouton a voler : "+boutonAvoler);
        g.drawImage(singe, (int)(posX_boutons/2.475)+singepos, boutonAvoler, hauteur_bouton, hauteur_bouton,this);
    }

    private void tracer(Graphics2D g, Image i, int x, int y, int l, int h) {
        g.drawImage(i, x, y, l, h, null);
    }

    public int largeurCase() {
        return largeurCase;
    }

    public int hauteurCase() {
        return hauteurCase;
    }

    public int colonnes() {
        return j.gaufre().colonnes();
    }

    public int lignes() {
        return j.gaufre().lignes();
    }

    private Image lisImage(String nom) {
        String CHEMIN = "ressources/";
        Image img = null;
        try {
            img = ImageIO.read(new File(CHEMIN + nom + ".png"));
        } catch (IOException e) {
            System.err.println("Impossible de charger l'image " + nom);
        }
        return img;
    }

    public void boucle(){
        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                miseAJour();
                miseAJourBalayeur();
            }
        });
        timer.start();
    }


    @Override
    public void miseAJour() {
        if(singe_index==7) singe_index=0;
        singe_index++;
        singepos+=5;
        repaint();
    }

    public void miseAJourBalayeur() {
        if(balayeur_index==12) balayeur_index=0;
        balayeur_index++;
        repaint();
    }

    public class Coords {
        public int i;
        public int j;

        public Coords(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

}
