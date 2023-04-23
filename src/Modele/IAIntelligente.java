package Modele;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IAIntelligente extends IA {

    Random r;

    public static final int MAX_PROFONDEUR = 100; // pronfondeur max apres laquel on ce dit que meme avec un facteur de branchement faible ca fait trop.
    public static final long CALCUL_LIMIT = 30000000000L; // limite de caclul a partir de laquelle le pc ram. A modifier tu des petits pc !(3E10)


    /**
     * Trouve le coup pour passer d'un Plateau à un autre
     * retourne (0,0) si le couple n'as pas ete trouve
     *
     * @param origin Plateau avant le coup joue
     * @param nouveau Plateau après le coup joué
     * @return un Couple c la position a cliquer pour passer de origin a nouveau
     */
    private Couple reconstruireCoup(Plateau origin, Plateau nouveau) {
        Couple c;
        for(int i=0;i < origin.largeur();i++) {
            for(int j=0;j < origin.hauteur();j++) {
                c = new Couple(j,i);
                if(nouveau.estMangee(c) && !origin.estMangee(c)) {
                    return c;
                }
            }
        }
        return new Couple(0,0);
    }

    /**
     * Renvois le nombre de cases mangeable de p
     *
     * @param p
     * @return
     */
    private int nbCaseMangeable(Plateau p) {
        int res = 0;
        for(int i=0;i<p.hauteur();i++) {
            for(int j=0;j<p.largeur();j++) {
                if(p.getTab()[i][j] == 0) {
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * retourne la profondeur de l'arbre des configuration pour le plateau p
     *
     * @param p
     * @return
     */
    private int evaluerProfondeur(Plateau p) {
        int n = nbCaseMangeable(p);
        if(n <= 13) {
            return 1000;
        } else {
            return (7 - (n-13))>0?(6 - (n-13)):1;
        }
    }

    private int evaluerA(Noeud N) {
        return Heuristique.calcule_heuristique(N);



    }

    private int evaluerB(Noeud N) {
        return HeuristiqueB.calcule_heuristique(N);



    }

    /**
     * Calcule si la configuration a la racine de l'arbre des configurations
     * est gagnante pour le joueur A en sachant que c'est au joeur A de jouer
     *
     * @param n racine de l'arbre des configurations
     * @return true si la configuration est gagnante pour le joueur A false sinon
     */
    private int minimaxA(Noeud n, HashMap<Integer,Integer> r, int profondeur) {
        TabConverter.FilsNoeud(n);	//calcul des fils
        int heuristique;
        if (n.estFeuille() || profondeur == 0) {
            // la configuration ne permet pas de jouer,
            // le joueur B gagne
            heuristique = evaluerA(n);
            r.put(n.valeur(), heuristique);
            n.setHeuristic(heuristique);
            return heuristique;
        } else {
            // Le joueur A doit jouer
            heuristique = 0;
            // On parcours l'ensemble des coups jouables par A
            for(Noeud fils : n.fils()) {
                int curr = minimaxB(fils, r, profondeur-1);
                // Si fils n'as pas encore ete calcule, le faire et mettre a jour r
                if(!r.containsKey(fils.valeur())) {
                    r.put(fils.valeur(), curr);
                }
                heuristique = Math.max(heuristique,r.get(fils.valeur()));
            }
            r.put(n.valeur(), heuristique);
            n.setHeuristic(heuristique);
            return heuristique;
        }
    }

    /**
     * Calcule si la configuration a la racine de l'arbre des configurations
     * est gagnante pour le joueur A en sachant que c'est au joeur B de jouer
     *
     * @param n racine de l'arbre des configurations
     * @return true si la configuration est gagnante pour le joueur A false sinon
     */
    private int minimaxB(Noeud n,HashMap<Integer,Integer> r, int profondeur) {
        TabConverter.FilsNoeud (n);	//calcul des fils
        int heuristique;
        if (n.estFeuille() || profondeur == 0) {
            // la configuration ne permet pas de jouer
            // le joueur A gagne
            heuristique = evaluerB(n);
            r.put(n.valeur(), heuristique);
            n.setHeuristic(heuristique);
            return heuristique;
        } else {
            // Le joueur B doit jouer
            heuristique = 1000; // + infini
            // On parcours l'ensemble des coups jouables par B
            for(Noeud fils : n.fils()) {
                int curr = minimaxA(fils, r, profondeur-1);
                // Si fils n'as pas encore ete calcule , le faire et mettre a jour r
                if(! r.containsKey(fils.valeur())) {
                    r.put(fils.valeur(), curr);
                }
                heuristique = Math.min(heuristique,r.get(fils.valeur()));
            }
            r.put(n.valeur(), heuristique);
            n.setHeuristic(heuristique);
            return heuristique;
        }
    }

    /**
     * Joue un coup sur le plateau avec les regles d'IA faciles
     * l'IA facile est un choix aleatoire du coup a jouer
     *
     * @return True si le coup a bien ete joue, False sinon
     */
    Couple jouerCoupFacile(Plateau plateau) {
        int i, j;
        int count = 0;
        i = r.nextInt(plateau.hauteur());
        j = r.nextInt(plateau.largeur());
        // l'IA essaye de ne pas se suicider et de manger quelque chose
        while (!plateau.estMangeable(new Couple(i,j)) && count < 100 || (i<=1 && j<=1)) {
            i = r.nextInt(plateau.hauteur());
            j = r.nextInt(plateau.largeur());
            count++;
        }
        if (count == 100) {
            return new Couple(0,0); //si elle n'as rien trouvée elle se suicide
        }
        //plateau.manger(new Couple(i,j));
        if(plateau.getTab()[i][j] == 0)
            return new Couple(i,j);
        else
            return new Couple(0,0);

    }

    /**
     * Joue un coup sur le plateau avec les regles d'IA moyennes (minimax avec un horizon de 1 equiveau a appliquer les regles heuristiques)
     *
     * @return True si le coup a bien ete joue, False sinon
     */
    Couple jouerCoupMoyen(Plateau plateau) {
        //ajoute de ca pour tester un cas ou ca plante si on le laisse tourner
        int[][] test = plateau.getTab();
        int htest = plateau.hauteur();
        int ltest = plateau.largeur();
        if(ltest == 2 && htest == 2 && test[1][1] > 0 && test[0][1] == 0 && test[1][0] == 0) {
            int testrand = r.nextInt(2);
            if(testrand == 1)
                return new Couple(1,0);
            else
                return new Couple(0,1);
        }else if (test[0][1] > 0 && test[1][0] > 0)
            return new Couple(0,0);

        Arbre a = new Arbre(TabConverter.ToInt(plateau)); // construction de l'arbre des configurations
        HashMap<Integer,Integer> memo = new HashMap<Integer,Integer>();
        int profondeur = 1;
        if(minimaxA(a.racine(),memo,profondeur) > 0) {
            LinkedList<Noeud> cp;
            if(( a.racine().filsTaggue().size()) != 0) {
                cp = a.racine().filsTaggue(); //recuperations des solutions
            }
            else {
                return jouerCoupFacile(plateau);
            }
            int rand = r.nextInt(cp.size()); //choix d'une solution admissible aleatoire
            Plateau nouveau = TabConverter.ToTab(cp.get(rand).valeur()); //traduction de la solution en Plateau
            Couple res = reconstruireCoup(plateau , nouveau); //traduction de la solution en Couple
            //plateau.manger(res); //Appliquer solution
            return res;
        } else {
            return jouerCoupFacile(plateau);
        }
    }

    /**
     * Joue un coup sur le plateau avec les regles d'IA difficiles
     *
     * @return True si le coup a bien ete joue, False sinon
     */
    Couple jouerCoupDifficile(Gaufre plateau) {
        //ajoute de ca pour tester un cas ou ca plante si on le laisse tourner
        int[][] test = plateau.cases;
        int htest = plateau.lignes;
        int ltest = plateau.colonnes;

        if(ltest == 2 && htest == 2 && test[1][1] > 0 && test[0][1] == 0 && test[1][0] == 0) {
            int testrand = r.nextInt(2);
            if(testrand == 1)
                return new Couple(1,0);
            else
                return new Couple(0,1);
        }else if (test[0][1] > 0 && test[1][0] > 0)
            return new Couple(0,0);

        Arbre a = new Arbre(TabConverter.ToInt(plateau)); // construction de l'arbre des configurations
        HashMap<Integer,Integer> memo = new HashMap<Integer,Integer>();
        int profondeur = evaluerProfondeur(plateau);
        if(minimaxA(a.racine(),memo,profondeur) > 0) {
            LinkedList<Noeud> cp;
            if(( a.racine().filsTaggue().size()) != 0) {
                cp = a.racine().filsTaggue(); //recuperations des solutions
            }
            else {
                return jouerCoupFacile(plateau);
            }
            int rand = r.nextInt(cp.size()); //choix d'une solution admissible aleatoire
            Plateau nouveau = TabConverter.ToTab(cp.get(rand).valeur()); //traduction de la solution en Plateau
            Couple res = reconstruireCoup(plateau , nouveau); //traduction de la solution en Couple
            //plateau.manger(res); //Appliquer solution
            return res;
        } else {
            return jouerCoupFacile(plateau);
        }
    }

    public int delai() {
        return 0;
    }

    public Couple prochainCoup(Plateau p) {
        return jouerCoupDifficile(p);
    }

    public Coup joue() {
        Plateau p = new Plateau(this.jeu.gaufre().lignes,this.jeu.gaufre().colonnes,this.jeu.gaufre().cases);
        Couple coup = jouerCoupDifficile(p);

        return new Coup(coup.getI(), coup.getJ(), 1,0);

    }

}