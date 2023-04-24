package Modele;

import java.util.*;

public class IAResolveur extends IA{

    //this.jeu.gaufre() est déjà disponible puisque on extends IA


    public IAResolveur() {
        super();
    }

    ArrayList<Coup> meilleurs_coups = new ArrayList<>();
    int HORIZON = 6;

    public Coup joue() {
        meilleurs_coups.clear();
        Arbre2 configuration = new Arbre2(convertit(this.jeu.gaufre().getCases()));

        boolean[][] config = configuration.getConfig();
        if ((!config[0][1] && config[1][0])) {
            return new Coup(0, 1, 0, 1);

        }
        if ((config[0][1] && !config[1][0])) {
            return new Coup(1, 0, 0, 1);
        }

        int val = Integer.MIN_VALUE;

        for (int i = 0; i < this.jeu.gaufre().getCases().length; i++) {
            for (int j = 0; j < this.jeu.gaufre().getCases()[0].length; j++) {
                if (!this.jeu.gaufre().estMangee(i, j) && !(i == 0 && j == 0)) {
                    Coup coup = new Coup(i, j, 0, 1);
                    Arbre2 newConfig = joueUnCoup(configuration, coup);

                    int current_val = calcul_Joueur_A(newConfig, HORIZON);
                    if (current_val == val) {
                        meilleurs_coups.add(coup);
                    }
                    if (current_val > val) {
                        val = current_val;
                        meilleurs_coups.clear();
                        meilleurs_coups.add(coup);
                    }
                }
            }
        }

        Collections.shuffle(meilleurs_coups);
        System.out.println("Joue " + meilleurs_coups.get(0));
        return meilleurs_coups.get(0);
    }

    public Coup jouerCoup(Arbre2 configuration){
        int valeur = calcul_Joueur_A(configuration, 10);
        return null;
    }

    public int evaluation(Arbre2 a){
        boolean[][] config = a.getConfig();

        // Heuristique nb case restante
        int nb_cases = 0;
        for(int i = 0; i < config.length; i++){
            for(int j = 0; j < config[0].length; j++){
                if(config[i][j] == true){
                    nb_cases++;
                }
            }
        }

        // Favorise état pair pour l'adversaire
        return nb_cases + ((nb_cases + 1)%2) * 6;
    }

    public void afficheConfig(boolean[][] config) {
        for(int i = 0; i < config.length; i++){
            for(int j = 0; j < config[0].length; j++){
                System.out.print(config[i][j]);
            }
            System.out.println();
        }
    }

    public LinkedList<Coup> coupsJouables(Arbre2 configuration){
        boolean[][] config = configuration.getConfig();
        LinkedList<Coup> coups = new LinkedList<>();
        for(int i = 0; i < config.length; i++){
            for(int j = 0; j < config[0].length; j++){
                if(config[i][j] == false && !(i == 0 && j == 0)){
                    Coup coup = new Coup(i, j, 0, 1);
                    coups.add(coup);
                    //System.out.println(coup);
                }
            }
        }
        if (!coups.isEmpty()) {
            //System.out.println("========================================================================");
            //afficheConfig(config);
            //System.out.println("========================================================================");
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

    public boolean[][] copyConfig(boolean[][] config) {
        boolean[][] newconfig = new boolean[config.length][config[0].length];
        for (int i = 0; i < config.length; i++) {
            for (int j = 0; j < config[0].length; j++) {
                newconfig[i][j] = config[i][j];
            }
        }
        return newconfig;
    }

    public int calcul_Joueur_A(Arbre2 configuration, int horizon){
        //System.out.println("B: " + horizon);

        HashMap<boolean[][],Integer> h = new HashMap<>();

        if(horizon == 0){
            int eval = evaluation(configuration);
            h.put(configuration.getConfig(), eval);
            return eval;
        }else {//le joueur A doit jouer
            //System.out.println("A");
            LinkedList<Coup> coups = coupsJouables(configuration);
            if (coups.isEmpty()) {
                int eval = 0;
                h.put(configuration.getConfig(), eval);
                return eval;
            }
            int valeur = Integer.MIN_VALUE;
            while (!coups.isEmpty()) {
                Coup c = coups.removeFirst();
                //System.out.println("A, " + c);
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
        //System.out.println(horizon);

        //System.out.println("B: " + horizon);
        HashMap<boolean[][],Integer> h = new HashMap<>();
        if(horizon == 0){
            int eval = evaluation(configuration);
            h.put(configuration.getConfig(), eval);
            return eval;
        }else {//le joueur A doit jouer
            //System.out.println("B");
            LinkedList<Coup> coups = coupsJouables(configuration);
            if (coups.isEmpty()) {
                int eval = 0;
                h.put(configuration.getConfig(), eval);
                return eval;
            }
            int valeur = Integer.MAX_VALUE;

            while (!coups.isEmpty()) {
                Coup c = coups.removeFirst();
                //System.out.println("B, " + c);
                Arbre2 newConfig = joueUnCoup(configuration, c);

                int valeur_A = calcul_Joueur_A(newConfig, horizon - 1);
                if(!h.containsKey(newConfig.getConfig())){
                    h.put(newConfig.getConfig(), valeur_A);
                }
                valeur = Math.min(valeur, valeur_A);
            }
            h.put(configuration.getConfig(), valeur);
            return valeur;
        }
    }
}
