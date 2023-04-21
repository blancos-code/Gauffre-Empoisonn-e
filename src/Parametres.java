import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Parametres {
    private int largeur;
    private int longueur;
    private String prenom;
    private String nom;
    private String nomFichier = "ressources/parametres.txt";

    public Parametres() {
        lireFichierParametres();
    }

    public void lireFichierParametres() {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            largeur = Integer.parseInt(br.readLine());
            longueur = Integer.parseInt(br.readLine());
            prenom = br.readLine();
            nom = br.readLine();
        } catch (IOException e) {
            System.err.println("Le fichier '" + nomFichier + "' est introuvable.");
        } catch (NumberFormatException e) {
            System.err.println("Le fichier parametres.txt doit contenir un nombre entier pour la largeur et la longueur.");
        }
    }

    public int getLargeur() {
        return largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void sauvegarderParametres() {
        try (FileWriter writer = new FileWriter(nomFichier)) {
            writer.write(Integer.toString(largeur) + "\n");
            writer.write(Integer.toString(longueur) + "\n");
            writer.write(prenom + "\n");
            writer.write(nom + "\n");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'enregistrement des paramètres.");
        }
    }

    public static void main(String[] args) {
        Parametres parametres = new Parametres();

        System.out.println("Largeur: " + parametres.getLargeur());
        System.out.println("Longueur: " + parametres.getLongueur());
        System.out.println("Prénom: " + parametres.getPrenom());
        System.out.println("Nom: " + parametres.getNom());
    }
}
