package Modele;

import javax.swing.*;
import java.net.URL;

public class Gif {
    public static void main() {
        // Créez un JFrame
        JFrame frame = new JFrame("GIF Animé");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Créez un JLabel pour afficher l'image GIF
        JLabel gifLabel = new JLabel();
        frame.add(gifLabel);

        // Chargez l'image GIF à partir de votre fichier ou d'une URL
        // Si le fichier est stocké localement, utilisez : new ImageIcon("chemin/vers/le/gif.gif");
        URL gifURL = null;
        try {
            gifURL = new URL("https://tenor.com/fr/view/clearing-gif-25544135");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gifURL != null) {
            // Définissez l'icône du JLabel comme l'image GIF chargée
            gifLabel.setIcon(new ImageIcon(gifURL));
        } else {
            System.out.println("URL du GIF non valide");
        }

        // Rendez le JFrame visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}