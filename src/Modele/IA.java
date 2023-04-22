package Modele;

import Structures.Sequence;

public abstract class IA {
    protected Jeu jeu;

    public static IA nouvelle(Jeu j, Parametres p) {
        IA resultat = null;

        String type = p.getType_IA();
        System.out.println(type);
        switch (type) {
            case "Aléatoire":
                resultat = new IAAleatoire();
                break;
            case "tropSmart":
                resultat = new IAIntelligente();
                break;
            default:
                System.out.println("IA non supportée.");
        }
        if (resultat != null) {
            resultat.jeu = j;
        }
        return resultat;
    }

    public Coup joue() {
        return null;
    }
}
