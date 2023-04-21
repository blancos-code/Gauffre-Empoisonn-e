package Vue;

import Modele.Jeu;
import Patterns.Observateur;

import javax.swing.*;
import java.awt.*;

public class InterfaceGraphique implements Runnable, InterfaceUtilisateur, Observateur {
    Jeu j;
    JFrame frame;
    boolean maximized;
    JLabel titre, progression;
    JToggleButton IA, animation;

    InterfaceGraphique(Jeu jeu) {
        j = jeu;
    }

    public static void demarrer(Jeu j) {
        InterfaceGraphique vue = new InterfaceGraphique(j);
        SwingUtilities.invokeLater(vue);
    }

    @Override
    public void miseAJour() {

    }

    @Override
    public void toggleFullscreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        if (maximized) {
            device.setFullScreenWindow(null);
            maximized = false;
        } else {
            device.setFullScreenWindow(frame);
            maximized = true;
        }
    }

    @Override
    public void changeEtatIA(boolean b) {

    }

    @Override
    public void changeEtatAnimations(boolean b) {

    }

    @Override
    public void run() {
        // Eléments de l'interface
        frame = new JFrame("Gaufre pleine de foutre");

        // Mise en place de l'interface

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        //centrer la fenetre
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
