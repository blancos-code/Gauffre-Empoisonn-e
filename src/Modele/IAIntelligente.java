package Modele;

import Structures.Sequence;
import Structures.Maillon;
import Structures.SequenceListe;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IAIntelligente extends IA{

    private SequenceListe<Coup> coupsPossibles(int A,int[][] instance){
        SequenceListe<Coup> coupsPossibles = new SequenceListe();
        for(int i=1;i< instance.length;i++){
            for(int j=1;j<instance[0].length;j++){
                if(!this.jeu.gaufre().estMangee(i,j)){
                    coupsPossibles.insereTete(new Coup(i,j,0,1));
                }
            }
        }
        return coupsPossibles;
    }

    boolean estFeuille(int[][] instance){
        return (instance[1][0]==1&&instance[0][1]==0&&instance[0][2]==1)||(instance[0][1]==1&&instance[1][0]==0&&instance[2][0]==1);
    }
    int[][] copieInstance(int[][] instance){
        int[][] instanceCopie = new int[instance.length][instance[0].length];
        for(int i=0;i<instance.length;i++){
            for(int j=0;j<instance[0].length;j++){
                instanceCopie[i][j] = instance[i][j];
            }
        }
        return instanceCopie;
    }
    boolean calculJoueurA(int[][] instance,Arbre pere){
        // C'est au joueur A de jouer, renvoie vrai si la configuration est gagnante
        if(estFeuille(instance)) return false;
        else{
            // Le joueur A doit jouer
            SequenceListe<Coup> C = coupsPossibles(1,instance); // Ensemble des coups jouables par A
            boolean val = false;
            //SequenceListe<Coup> coupsGagnants = new SequenceListe<>();
            for(int i=0;i<C.taille();i++){
                Coup coupCourant = C.extraitTete();
                int[][] instanceCourante = copieInstance(instance);
                instanceCourante[coupCourant.getI()][coupCourant.getJ()] = coupCourant.getNewValeur();
                Arbre fils = new Arbre(instanceCourante,coupCourant,null,null,val);

                if(calculJoueurB(instanceCourante,fils)){
                    val = true;
                    //coupsGagnants.insereQueue(coupCourant);
                    fils.setCoupGagnant(val);
                }
                if(pere.getFilsGauche()==null){
                    pere.setFilsGauche(fils);
                }else{
                    pere = pere.getFilsGauche();
                    while(pere.getFilsDroit()!=null){
                        pere = pere.getFilsDroit();
                    }
                    pere.setFilsDroit(fils);
                }
            }
            return val;
        }
    }

    boolean calculJoueurB(int[][] instance,Arbre pere){
        // C'est au joueur B de jouer, renvoie vrai si la configuration est gagnante pour A
        if(estFeuille(instance)) return false;
        else{
            // Le joueur B doit jouer
            SequenceListe<Coup> C = coupsPossibles(0,instance); // Ensemble des coups jouables par B
            boolean val = true;
            for(int i=0;i<C.taille();i++){
                Coup coupCourant = C.extraitTete();
                int[][] instanceCourante = copieInstance(instance);
                instanceCourante[coupCourant.getI()][coupCourant.getJ()] = coupCourant.getNewValeur();
                val = val&&calculJoueurA(instanceCourante,pere);
            }
            return val;
        }
    }

    private void affiche(int[][] niveau){
        for(int i=0;i< niveau.length;i++){
            for(int j=0;j<niveau[0].length;j++){
                System.out.println(niveau[i][j]);
            }
            System.out.println('\n');
        }
    }

    public Coup joue() {
        int [][] instance = this.jeu.gaufre().cases;
        Arbre pere = new Arbre(instance,null,null,null,true);
        if(calculJoueurA(instance,pere)){
            affiche(pere.getFilsGauche().getCourant());
            return null;
        }else{ // on choisit aléatoirement
            boolean estJouable = false;
            int i = 0, j = 0;
            while (!estJouable) {
                Random r = new Random();

                i = r.nextInt(this.jeu.gaufre().lignes());
                j = r.nextInt(this.jeu.gaufre().colonnes());

                if (!this.jeu.gaufre().estMangee(i, j) && !(i == 0 && j == 0)) {
                    estJouable = true;
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new Coup(i, j, 1, 0);
        }
    }
}
