package Modele;

import java.util.HashMap;
import java.util.LinkedList;

public class IAResolveur extends IA{

    //this.jeu.gaufre() est déjà disponible puisque on extends IA


    public IAResolveur() {
        super();
    }

    public Coup joue() {
        Arbre2 configuration = new Arbre2(convertit(this.jeu.gaufre().getCases()));
        int valeur = calcul_Joueur_A(configuration, 1);
        return jouerCoup(configuration, valeur);
    }

    public Coup jouerCoup(Arbre2 configuration, int valeur){
        return null;
    }

    public int evaluation(Arbre2 a){
        boolean[][] config = a.getConfig();
        int nb_cases = 0;
        for(int i = 0; i < config.length; i++){
            for(int j = 0; j < config[0].length; j++){
                if(config[i][j] == false){
                    nb_cases++;
                }
            }
        }
        return nb_cases;
    }

    public LinkedList<Coup> coupsJouables(Arbre2 configuration){
        boolean[][] config = configuration.getConfig();
        LinkedList<Coup> coups = new LinkedList<>();
        for(int i = 0; i < config.length; i++){
            for(int j = 0; j < config[0].length; j++){
                if(config[i][j] == false && !(i == 0 && j == 0)){
                    coups.add(new Coup(i, j, 0, 1));
                }
            }
        }
        return coups;
    }

    public boolean[][] convertit(int[][] configuration){//convertit un tableau d'entiers contenant des 0 et des  en tableau de booleens
        boolean[][] resultat = new boolean[configuration.length][configuration[0].length];
        for (int i = 0; i < configuration.length; i++) {
            for (int j = 0; j < configuration[0].length; j++) {
                if(configuration[i][j] == 0){
                    resultat[i][j] = false;
                }else{
                    resultat[i][j] = true;
                }
            }
        }
        return resultat;
    }

    public Arbre2 joueUnCoup(Arbre2 configuration, Coup c){
        boolean[][] config = configuration.getConfig();
        for (int i = c.getI(); i<= jeu.gaufre().lignes()-1; i++) {
            for (int j = c.getJ(); j <= jeu.gaufre().colonnes() - 1; j++) {
                if (config[i][j] == false) {
                    config[i][j] = true;
                }
            }
        }
        configuration.setConfig(config);
        return configuration;
    }

    public int calcul_Joueur_A(Arbre2 configuration, int horizon){
        HashMap<boolean[][],Integer> h = new HashMap<>();
        if(configuration.estFeuille() || horizon == 0){
            int eval = evaluation(configuration);
            h.put(configuration.getConfig(), eval);
            return eval;
        }else {//le joueur A doit jouer
            LinkedList<Coup> coups = coupsJouables(configuration);
            int valeur = Integer.MIN_VALUE;

            while (!coups.isEmpty()) {
                Coup c = coups.removeFirst();
                Arbre2 newConfig = joueUnCoup(configuration, c);
                int valeur_B = calcul_Joueur_B(newConfig, horizon - 1);
                if(!h.containsKey(newConfig.getConfig())){
                    h.put(newConfig.getConfig(), valeur_B);
                }
                valeur = Math.max(valeur, valeur_B);
            }
            h.put(configuration.getConfig(), valeur);
            return valeur;
        }
    }

    public int calcul_Joueur_B(Arbre2 configuration, int horizon){
        HashMap<boolean[][],Integer> h = new HashMap<>();
        if(configuration.estFeuille() || horizon == 0){
            int eval = evaluation(configuration);
            h.put(configuration.getConfig(), eval);
            return eval;
        }else {//le joueur A doit jouer
            LinkedList<Coup> coups = coupsJouables(configuration);
            int valeur = Integer.MAX_VALUE;

            while (!coups.isEmpty()) {
                Coup c = coups.removeFirst();
                Arbre2 newConfig = joueUnCoup(configuration, c);
                int valeur_B = calcul_Joueur_B(newConfig, horizon - 1);
                if(!h.containsKey(newConfig.getConfig())){
                    h.put(newConfig.getConfig(), valeur_B);
                }
                valeur = Math.min(valeur, valeur_B);
            }
            h.put(configuration.getConfig(), valeur);
            return valeur;
        }
    }
}
