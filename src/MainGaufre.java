import Modele.Jeu;
import Modele.Parametres;
import Vue.FenetreDynamique;
import Vue.InterfaceGraphique;
import Vue.Menu;

import javax.swing.*;
import java.io.IOException;

public class MainGaufre {

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("La gaugaufre mmh");
        ImageIcon icon = new ImageIcon("ressources/icon.png");
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1380, 720);
        Menu menu = new Menu(frame);
        frame.add(menu);
        frame.setVisible(true);
    }
}
