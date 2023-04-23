package Modele;

import java.util.LinkedList;
import java.lang.Integer;

public class TabConverter {
    /**
     * afficher_tab : affiche le tableau
     *  @param P : la plateau contenant le tableau a afficher
     *
     */
    public static void afficher_tab(Plateau P) {
        int [][] tab = P.getTab();
        int N = P.hauteur();
        int M = P.largeur();
        for(int p = 0; p < N; p ++) {
            for(int po = 0; po < M; po ++) {
                System.out.print(tab[p][po] + " , ");
            }
            System.out.println(" ");
        }
    }
    /**
     * afficher_tab : ajoute 1 ou 0 à la representation binaire de l'int donne
     *  @param res : l'int à modifier
     *  @param val :  0 ou 1
     *  @return l'entier modifié
     */
    public static int addbin(int res, int val) {
        if(val == 1) {
            res = res << 1;
            res++;
            //System.out.println("add 1");
        }else {
            res = res << 1;
            //System.out.println("add 0");
        }
        return res;
    }

    /**
     * ToInt : traduit un configuration de tableau en Int via le vecteur de bit de partie mangee ( O(N+M) )
     *  @param P : le plateau contenant le tableau a ToInter
     *  @return : L'integer correspondant au vecteur de bit du tableau
     */

    public static int ToInt(Plateau P) {
        int[][] tableau = P.getTab();
        int M = P.largeur();
        int N = P.hauteur();


        boolean pass = true;
        int curr = 0;
        int res = 1; // ajout d'un 1 pour ne pas perdre d'informations
        if(M == 0 || N == 0) return 0;


        if(tableau[N-1][M-1] == 0) {// cas trivial
            for(int i = 0; i < M; i++) {
                res = addbin(res, 0);
            }
            for(int i = 0; i < N; i++) {
                res = addbin(res, 1);
            }
            res = addbin(res,1);
            return res;
        }


        for(int i = 0; i < M && pass; i++) { // traitement de la premiere ligne
            if(tableau[N-1][i] == 0) {
                res = addbin(res, 0);
            }
            else { // sauvegarde de la position du premier 1 de la ligne
                curr = i;
                pass = false;
            }
        }

        int i = N-1; // calcul de "l'escalier"
        boolean doubles = true;
        boolean pasde1 = true;
        while( i >= 0 ) {
            doubles = true;
            pasde1 = true;
            for(int j = curr; j < M && doubles ; j++) { // a partir du premier 1 de la ligne precedente
                if(j != M-1 && tableau[i][j] == 0 && doubles) {
                    res = addbin(res, 0);
                }else if(doubles && tableau[i][j] > 0) { // un 1 : ligne suivante into add 1
                    doubles = false;
                    pasde1 = false;
                    res = addbin(res, 1);
                    curr = j;
                    i --;
                }
            }
            if(pasde1) { // aucun 1 dans la ligne : break
                i = -1;
            }
        }

        doubles = false;
        for(int k = N-1; k >= 0; k--) { // calcul de la paroie droite + cas batard
            if(k != N-1 && (tableau[k][M-1] == 0 && tableau[k+1][M-1] != 0)) {
                res=addbin(res, 0);
                doubles = true;
            }
            if(doubles)
                res=addbin(res,1);
        }

        for(int k = 0; k < M; k++) { // on ajoute les 0 manquants sur la ligne 0
            if(tableau[0][k] != 0) {
                res = addbin(res,0);
            }
        }
        res = addbin(res,1); // ajout d'un 1 pour ne pas perdre d'informations
        return res;
    }


    /**
     *  Honnetement, je sais pas pourquoi la partie en haut a gauche du tableau est initialisée à 0, mais ca marche mais
     *  ToTab : Traduit un int en une configuration du tableau ( O(n²) )
     *  @param base : l'int à traduire
     *  @return : un plateau contenant le tableau créé, et sa taille
     */

    public static Plateau ToTab(int base) {
        LinkedList<Integer> binary = new LinkedList<>();
        binary = intToBinary(base); // representation binaire de l'int

        if(binary.size() >= 2) {
            binary.removeFirst(); // on enleve le premier et dernier 1 de la liste (warning normal)
            binary.removeLast();
        }
        int N=0, M=0; // on reconstruit la taille du tableau
        for(int i = 0; i < binary.size(); i++) {
            if(binary.get(i) == 1)
                N++;
            else
                M++;
        }

        int [][] res = new int[N][M];
        int curr;
        int temp = 0;
        boolean pass = true ; // represente le faire de ne rencontrer qu'un un par ligne

        int i = N-1; // on remplit depuis la derniere ligne
        temp = 0;
        //System.out.println(binary);
        while( i >= 0 ){ // on recalcule le tableau a partir du vecteur
            pass = true;
            for(int j = temp ; j < M && pass; j++) {
                if((curr = binary.pollFirst()) == 0 && j == M-1) {// on recupere le bit à traite0
                    return new Plateau(N,M,res);
                }
                if(curr == 1 && pass) { // la ligne entiere est à remplir de 1, on passe a la suivante
                    //System.out.println(i+" ; "+j);
                    pass = false;
                    temp = j;
                    while(j < M ) { // remplissage
                        res[i][j] = 1;
                        j++;
                    }
                    i--; // ligne suivante
                }
                else { // on ajoute des 0 au cas par cas
                    res[i][j] = 0;

                }
            }
            if(pass) // au cas il n'y ait aucun 1 dans la ligne
                i--;
        }

        return new Plateau(N,M,res);
    }

    /**
     * IntToBinary : traduit un int en une linkedlist
     *  @param base : l'int à traduire
     *  @return : une linked list contenant le binaire de l'int donne
     */
    public static LinkedList<Integer> intToBinary(int base){
        LinkedList<Integer> res = new LinkedList<>();
        while (base > 0)
        {
            res.addFirst((base % 2 ) == 0 ? 0 : 1);
            base = base / 2;
        }

        return res;
    }

    /**
     * binaryToInt : traduit un int en une linkedlist
     *  @param bin : la linked list a traduire
     *  @return : le int correspondant
     */
    public static int binaryToInt(LinkedList<Integer> bin) {
        int res = 0;
        for( int i = 0; i < bin.size() ; i++){
            if(bin.get(i) == 0) {
                res = res << 1;
            }
            else {
                res = res << 1;
                res++;
            }
        }
        return res;
    }
    /**
     * nbunfun :  nombre de 1 dans une linked list
     *  @param bin : la linked list
     *  @return : le int correspondant
     */
    public static int nbunfun(LinkedList<Integer> bin) {
        int M = 0;
        for( int i = 0; i < bin.size() ; i++){
            if(bin.get(i) == 1) {
                M++;
            }
        }
        return M;
    }
    /**
     * nbzerofun :  nombre de 0 dans une linked list
     *  @param bin : la linked list
     *  @return : le int correspondant
     */
    public static  int nbzerofun(LinkedList<Integer> bin) {
        int M = 0;
        for (int i = 0; i < bin.size() ; i++){
            if(bin.get(i) == 0) {
                M++;
            }
        }
        return M;
    }

    public static int[][] deepcpy(int[][] tableau, int M, int N) {
        int[][] copied = new int[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                copied[i][j] = tableau[i][j];
            }
        }
        return copied;
    }
    /**
     * simule_coup : simule un coup en coordonnee x y
     *  @param tableau : tableau dans lequel simuler le coup
     *  @param x : coordonnee x du coup a simuler
     *  @param y : coordonee y du coup a simuler
     *  @param M : hauteur
     *  @param N : hauteur
     *  @return : le tableau deepcopie
     */
    public static int[][] simule_coup(int[][] tableau, int x, int y,int M,int N) {
        int[][] simule = deepcpy(tableau,M,N);
        for(int i = x; i < N; i++) {
            for(int j = y; j < M; j++) {
                simule[i][j] = 1;
            }
        }
        return simule;
    }
    /**
     * FilsNoeud : rempli la table des fils d'un noeud donne par effet de bord
     *  @param N : le noeud
     */
    public static void FilsNoeud (Noeud N) {
        //version vecteur de bit qui marche moyen (d'un point de vu algo pas implementation, je la laisse parceque c'est faisable)
			/*
			LinkedList<Noeud> filsN = new LinkedList<Noeud>(); // la linked list de fils a integrer
			LinkedList<Integer> vector = new LinkedList<Integer>(); // la linked list de la representation binaire de la valeur du noeud
			LinkedList<Integer> max = new LinkedList<Integer>(); // la linked list de la representation binaire du vecteur de bit maximal
			LinkedList<Integer> current = new LinkedList<Integer>(); // la linked list de la representation binaire de l'entier traité
			LinkedList<Integer> tmp = new LinkedList<Integer>();
			int nbzero = 0; // nombre de 0 de la representation binaire de la valeur de N
			int nbun = 0; // nombre de 1 de la representation binaire de la valeur de N

			vector = intToBinary(N.valeur()); // valeur binaire de la valeur du noeud N

			if(vector.size() >= 2) {
				vector.removeFirst(); // on enleve le premier et dernier 1 de la liste (warning normal)
				vector.removeLast();
			}

			nbun = nbunfun(vector);
			nbzero = nbzerofun(vector);

			int newNval = binaryToInt(vector);
			System.out.println("le vecteur initial : "+ vector +" son int = "+ newNval);

			for(int i = 0; i < nbun; i++) { // calcul du vecteur binaire maximal
				max.addLast(1);
			}
			for(int i = 0; i < nbzero; i++) {
				max.addLast(0);
			}
			int intmax = binaryToInt(max); // et de son INT correspondant

			System.out.println("le vecteur maximal : "+ max +" et son int : "+ intmax);
			System.out.println("fils : ");
			int sorti = 0;
			for(int i = 0; i < vector.size(); i++) {
				sorti = vector.get(i);
				if(sorti == 1) {
					tmp = (LinkedList<Integer>) vector.clone(); // je sais pas enlever le warning
					tmp.set(i, 0);
					for(int j = 0; j < i; j++) {
						if(j != i && tmp.get(j) == 0) {
							current = (LinkedList<Integer>) tmp.clone();// je sais pas enlever le warning
							current.set(j, 1);
							System.out.println("un vecteur pris en compte : "+ tmp );
							if( binaryToInt(current) <= intmax && binaryToInt(current) > newNval ) {
								System.out.println("le vecteur courant : "+ current +" et son int : "+ binaryToInt(current));
								current.addFirst(1); // parceque le premier et le dernier 1 sont depop par ToTab
								current.addLast(1);
								filsN.add(new Noeud(binaryToInt(current), N));
							}
						}
					}
				}
			}
			N.setFils(filsN); // assignation des fils
			*/

        // super version nulle en O(n puissance infini) mais qui marche

        LinkedList<Noeud> filsN = new LinkedList<Noeud>(); // la linked list de fils a integrer
        LinkedList<Integer> vector = new LinkedList<Integer>(); // la linked list de la representation binaire de la valeur du noeud
        vector = intToBinary(N.valeur()); // valeur binaire de la valeur du noeud N

        Plateau P = ToTab(binaryToInt(vector));
        int [][] tableau = P.getTab();
        int largeur = P.largeur();
        int hauteur = P.hauteur();
        for(int i = 0; i < hauteur; i++) {
            for(int j = 0; j < largeur; j++) {
                if(tableau[i][j] == 0) {
                    int[][] simule = simule_coup(tableau,i,j,largeur,hauteur);
                    int curr = ToInt(new Plateau(hauteur,largeur,simule));
                    Noeud Aadd = new Noeud(curr,N);
                    filsN.add(Aadd);
                }
            }
        }
        N.setFils(filsN); // assignation des fils
    }
}