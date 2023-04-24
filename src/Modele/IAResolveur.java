package Modele;

import java.util.HashMap;

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

    public Coup coupsJouables(Arbre2 configuration){

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

    public int calcul_Joueur_A(Arbre2 configuration, int horizon){
        if(configuration.estFeuille() || horizon == 0){
            return evaluation(configuration);
        }else {//le joueur A doit jouer
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < configuration.length; i++) {
                for (int j = 0; j < configuration[0].length; j++) {
                    if(configuration[i][j] == 0){
                        configuration[i][j] = 1;
                        int min = calcul_Joueur_B(configuration,horizon-1);
                        if(min > max){
                            max = min;
                        }
                        configuration[i][j] = 0;
                    }
                }
            }
            return max;
        }
        return 0;
    }
}
