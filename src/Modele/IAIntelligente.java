package Modele;

import Structures.Sequence;
import Structures.Maillon;
import Structures.SequenceListe;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class IAIntelligente extends IA{

    /*private SequenceListe<Coup>  coupsPossibles(int A,int[][] instance){
        SequenceListe<Coup> coupPossible = new SequenceListe();
        for(int i=0;i< instance.length;i++){
            for(int j=0;j<instance[0].length;j++){
                if(instance[i][j]==0){
                    if(i==0&&j==0) continue;
                    coupPossible.insereTete(new Coup(i,j,0,1));
                }
            }
        }
        return coupPossible;
    }

    boolean estFeuille(int[][] instance){
        //return (instance[1][0]==1&&instance[0][1]==0&&instance[0][2]==1)||(instance[0][1]==1&&instance[1][0]==0&&instance[2][0]==1);
        return (instance[0][1]==1 && instance[1][0]==1);
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

    Gaufre copieGaufre(Gaufre gaufre){
        Gaufre copGaufre = new Gaufre(gaufre.lignes, gaufre.colonnes);
        copGaufre.cases = gaufre.cases;
        copGaufre.historique = gaufre.historique;
        return copGaufre;
    }
    boolean calculJoueurA(Gaufre instance,Arbre pere){
        System.out.println("A");
        // C'est au joueur A de jouer, renvoie vrai si la configuration est gagnante
        if(estFeuille(instance.cases)) return false;
        else{
            // Le joueur A doit jouer
            SequenceListe<Coup> C = coupsPossibles(1,instance.cases); // Ensemble des coups jouables par A
            boolean val = false;
            //SequenceListe<Coup> coupsGagnants = new SequenceListe<>();
            for(int i=0;i<C.taille();i++){
                Coup coupCourant = C.extraitTete();
                Gaufre instanceCourante = copieGaufre(instance);
                instanceCourante.joue(coupCourant);
                affiche(instanceCourante.cases);
                //instanceCourante[coupCourant.getI()][coupCourant.getJ()] = 1;
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

    boolean calculJoueurB(Gaufre instance,Arbre pere){
        System.out.println("B");
        // C'est au joueur B de jouer, renvoie vrai si la configuration est gagnante pour A
        if(estFeuille(instance.cases)) return false;
        else{
            // Le joueur B doit jouer
            SequenceListe<Coup> C = coupsPossibles(0,instance.cases); // Ensemble des coups jouables par B
            boolean val = true;
            for(int i=0;i<C.taille();i++){
                Coup coupCourant = C.extraitTete();
                Gaufre instanceCourante = copieGaufre(instance);
                instanceCourante.joue(coupCourant);
                if(estFeuille(instanceCourante.cases)) return true;
                affiche(instanceCourante.cases);
                val = val&&calculJoueurA(instanceCourante,pere);
            }
            return val;
        }
    }

    private void affiche(int[][] niveau){
        System.out.println("AFFICHAGE");
        String niv = "";
        for(int i=0;i< niveau.length;i++){
            for(int j=0;j<niveau[0].length;j++){
                niv = niv+(String.valueOf(niveau[i][j]));
            }
            niv = niv+("\n");
        }
        System.out.println(niv);
    }

    public Coup joue() {
        //int [][] instance = this.jeu.gaufre().cases;
        Gaufre gaufreCourante = copieGaufre(this.jeu.gaufre());


        Arbre pere = new Arbre(gaufreCourante,null,null,null,true);
        if(calculJoueurA(gaufreCourante,pere)){
            System.out.println("pas aleatoire");
            //affiche(pere.getFilsGauche().getCourant().cases);
            return null;
        }else{ // on choisit aléatoirement
            System.out.println("aleatoire");
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
    */

    int peutFairePerdre(int[][] instance){
        if(instance[1][0]==1) return 1;
        else if(instance[0][1]==1) return 0;
        else return -1;
    }

    public Coup joue() {
        int valeur=peutFairePerdre(this.jeu.gaufre().cases);
        if(valeur!=-1){
            if(valeur==1) return new Coup(0,1,0,1);
            else return new Coup(1,0,0,1);
        }else{ // on choisit aléatoirement
            System.out.println("aleatoire");
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
