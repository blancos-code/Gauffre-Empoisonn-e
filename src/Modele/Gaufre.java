package Modele;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.util.LinkedList;
import java.io.ObjectOutputStream;
public class Gaufre {
    protected int[][] cases;
    protected int lignes, colonnes;
    private int nb_cases_pleines;
    private int nb_cases;
    public Historique historique;

    public Gaufre(int i, int j) {
        cases = new int[i][j];
        lignes = i;
        colonnes = j;
        nb_cases = i * j;
        nb_cases_pleines = nb_cases-1;
        historique = new Historique();
    }

    public boolean estFinit() {
        return nb_cases_pleines == 0;
    }

    public boolean estMangee(int i, int j) {
        return cases[i][j] == 1;
    }


    public void joue(Coup c) {
        ListeCoups lc = new ListeCoups();
        for (int i = c.getI(); i<= lignes-1; i++) {
            for (int j = c.getJ(); j <= colonnes - 1; j++) {
                if (cases[i][j] == 0) {
                    lc.ajoute(new Coup(i, j, 0, 1));
                    fixeValeurCase(1, i, j);
                    nb_cases_pleines--;
                }
            }
        }
        historique.ajoute(lc);
    }

    public void resetHistorique(){
        historique = new Historique();
    }

    public double progression() { return ((double)nb_cases_pleines / ((double)nb_cases-1))*100;
    }

    public int colonnes() {
        return colonnes;
    }

    public int lignes() {
        return lignes;
    }

    public boolean peutAnnuler() {
        return historique.peutAnnuler();
    }

    public boolean peutRefaire() {
        return historique.peutRefaire();
    }

    public boolean annuler() {
        if(peutAnnuler()) {
            LinkedList<Coup> liste_coups = historique.annuler().getListe_coups();
            LinkedList<Coup> liste_coups_copie = new LinkedList<>();
            liste_coups_copie.addAll(liste_coups);
            while(!liste_coups_copie.isEmpty()){
                Coup h = liste_coups_copie.removeFirst();
                int i = h.getI();
                int j = h.getJ();
                int oldValeur = h.getOldValeur();
                fixeValeurCase(oldValeur, i, j);
                nb_cases_pleines++;
            }
            return true;
        }
        return false;
    }


    public boolean refaire() {
        if(peutRefaire()) {
            LinkedList<Coup> liste_coups = historique.refaire().getListe_coups();
            LinkedList<Coup> liste_coups_copie = new LinkedList<>();
            liste_coups_copie.addAll(liste_coups);
            while (!liste_coups_copie.isEmpty()) {
                Coup h = liste_coups_copie.removeFirst();
                int i = h.getI();
                int j = h.getJ();
                int oldValeur = h.getNewValeur();
                fixeValeurCase(oldValeur, i, j);
                nb_cases_pleines--;
            }
            return true;
        }
        return false;
    }

    public void fixeValeurCase(int v, int i, int j) {
        cases[i][j] = v;
    }

    public void sauvegarder() {
        try {

            FileOutputStream  f = new FileOutputStream("sauvegarde.txt");
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeInt(lignes);
            out.writeInt(colonnes);
            for (int i = 0 ; i<lignes ; i++){
                for (int j = 0 ; j<colonnes ; j++){
                    out.writeInt(cases[i][j]);
                }
            }
            out.writeObject(historique);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void charger() {
        try{
            FileInputStream fichier = new FileInputStream("sauvegarde.txt");
            ObjectInputStream in = new ObjectInputStream(fichier);
            lignes= in.readInt();
            colonnes= in.readInt();
            nb_cases = lignes * colonnes;
            nb_cases_pleines = nb_cases-1;
            for (int i = 0 ; i<lignes ; i++){
                for (int j = 0 ; j<colonnes ; j++){
                    int valeur = in.readInt();
                    if(valeur==1){
                        nb_cases_pleines--;
                    }
                    cases[i][j]=valeur;

                }
            }
            historique = (Historique)in.readObject();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public void reinitialise() {
        cases = new int[lignes][colonnes];
        nb_cases = lignes * colonnes;
        nb_cases_pleines = nb_cases-1;
        historique = new Historique();
    }

    public void affiche(){
        for (int i = 0; i<= lignes -1; i++) {
            for (int j = 0; j<= colonnes -1; j++)
                System.out.print(cases[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
