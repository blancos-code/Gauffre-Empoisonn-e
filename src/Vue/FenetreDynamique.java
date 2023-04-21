package Vue;

import Modele.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import javax.swing.Box.Filler;


public class FenetreDynamique {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FenetreDynamique::createAndShowGUI);
    }

    private static Font loadCustomFont(String fontPath, float fontSize) {
        try {
            InputStream is = FenetreDynamique.class.getResourceAsStream(fontPath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(fontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Fen�tre Dynamique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Taille dynamique en fonction de la taille de l'�cran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setLocationRelativeTo(null);

        // Panneau avec un fond sombre
        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40)); // Fond sombre
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 60, 60, 60); // Ajout des insets pour �loigner les boutons des bords

        // Ajout d'un espace vertical avant le titre
        Dimension verticalMargin = new Dimension(0, 50); // 50 pixels d'espace vertical
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(new Filler(verticalMargin, verticalMargin, verticalMargin), gbc);


        // Titre "Gaufre Empoisonn�e"
        Font titleFont = new Font("Roboto", Font.BOLD, 48);
        JLabel titleLabel = new JLabel("La Gaufre Empoisonn�e");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1; // R�initialise la largeur de la grille

        // Panneau pour les boutons "Jouer" et "Param�tres"
        JPanel jouerParametresPanel = new JPanel();
        jouerParametresPanel.setOpaque(false);
        jouerParametresPanel.setLayout(new BoxLayout(jouerParametresPanel, BoxLayout.X_AXIS));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        panel.add(jouerParametresPanel, gbc);


        // Bouton "Jouer"
        JButton jouerButton = createButton("Jouer", new Color(76, 175, 80), new Font("Roboto", Font.BOLD, 32), e -> System.out.println("Jouer"));
        jouerParametresPanel.add(jouerButton);

        // Espace fixe entre les boutons "Jouer" et "Param�tres"
        jouerParametresPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Bouton "Param�tres"
        URL iconParametresUrl = FenetreDynamique.class.getResource("/settings.png");
        ImageIcon iconParametres = new ImageIcon(iconParametresUrl);
        JButton parametresButton = createIconButton(iconParametres, e -> System.out.println("Param�tres"));
        parametresButton.setPreferredSize(new Dimension(jouerButton.getPreferredSize().height, jouerButton.getPreferredSize().height));
        jouerParametresPanel.add(parametresButton);

        // Bouton "Quitter"
        JButton quitterButton = createButton("Quitter", new Color(244, 67, 54), new Font("Roboto", Font.BOLD, 28), e -> System.exit(0));
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        panel.add(quitterButton, gbc);


        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateInsets(frame, panel, jouerParametresPanel, quitterButton);
            }
        });

        // Affiche la fen�tre
        updateInsets(frame, panel, jouerParametresPanel, quitterButton);
        frame.setVisible(true);
    }

    private static JButton createButton(String text, Color color, Font font, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.addActionListener(actionListener);

        return button;
    }

    private static JButton createIconButton(ImageIcon icon, ActionListener actionListener) {
        JButton button = new JButton(icon);
        button.setFocusable(false);
        button.addActionListener(actionListener);

        return button;
    }

    private static void updateInsets(JFrame frame, JPanel panel, JPanel jouerParametresPanel, JButton quitterButton) {
        int width = frame.getWidth();
        int height = frame.getHeight();
        int insetSize = Math.min(width, height) / 10;

        GridBagConstraints gbc = ((GridBagLayout) panel.getLayout()).getConstraints(jouerParametresPanel);
        gbc.insets = new Insets(0, insetSize, insetSize, 0);
        panel.remove(jouerParametresPanel);
        panel.add(jouerParametresPanel, gbc);

        gbc = ((GridBagLayout) panel.getLayout()).getConstraints(quitterButton);
        gbc.insets = new Insets(0, insetSize, insetSize, insetSize);
        panel.remove(quitterButton);
        panel.add(quitterButton, gbc);

        panel.revalidate();
        panel.repaint();
    }

}

