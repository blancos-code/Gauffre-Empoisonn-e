import Modele.Jeu;
import Modele.Parametres;
import Vue.InterfaceGraphique;

import java.io.IOException;

public class MainGaufre {

    public static void main(String[] args) throws IOException {
        Parametres p = new Parametres();
        Jeu j = new Jeu(p);
        InterfaceGraphique.demarrer(j);
    }
}
