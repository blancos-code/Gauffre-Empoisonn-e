package Vue;

public interface CollecteurEvenements {
    void clicSouris(int l, int c);
    void toucheClavier(String t);
    void ajouteInterfaceUtilisateur(InterfaceUtilisateur vue);
    void tictac();
}
