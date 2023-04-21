package Structures;

/*
 * Sokoban - Encore une nouvelle version (� but p�dagogique) du c�l�bre jeu
 * Copyright (C) 2018 Guillaume Huard
 *
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique G�n�rale GNU publi�e par la
 * Free Software Foundation (version 2 ou bien toute autre version ult�rieure
 * choisie par vous).
 *
 * Ce programme est distribu� car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but sp�cifique. Reportez-vous � la
 * Licence Publique G�n�rale GNU pour plus de d�tails.
 *
 * Vous devez avoir re�u une copie de la Licence Publique G�n�rale
 * GNU en m�me temps que ce programme ; si ce n'est pas le cas, �crivez � la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * �tats-Unis.
 *
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'H�res
 */

public class SequenceListe<E> implements Sequence<E> {
    Maillon<E> tete, queue;
    private int taille = 0;

    @Override
    public void insereQueue(E element) {
        Maillon<E> m = new Maillon<>(element, null);
        if (tete == null) {
            tete = queue = m;
        } else {
            queue.suivant = m;
            queue = queue.suivant;
        }
        taille++;
    }

    public void incrementeTaille(){
        taille++;
    }

    @Override
    public void insereTete(E element) {
        Maillon<E> m = new Maillon<>(element, tete);
        if (tete == null) {
            tete = queue = m;
        } else {
            tete = m;
        }
        taille++;
    }

    @Override
    public E extraitTete() {
        E resultat;
        // Exception si tete == null (sequence vide)
        resultat = tete.element;
        tete = tete.suivant;
        if (tete == null) {
            queue = null;
        }
        taille--;
        return resultat;
    }

    public E extraitQueue(){
        if(queue == null){
            return null;
        }else if(tete == queue){// il y a un seul �l�ment dans la liste
            E element = queue.element;
            tete = queue = null;
            taille--;
            return element;
        }else{
            // il y a plusieurs �l�ments dans la liste
            Maillon<E> precedent = tete;
            while(precedent.suivant != queue){
                precedent = precedent.suivant;
            }
            E element = queue.element;
            queue = precedent;
            queue.suivant = null;
            taille--;
            return element;
        }
    }

    public E getTete(){
        return tete.element;
    }

    public E getQueue(){
        return queue.element;
    }

    @Override
    public boolean estVide() {
        return tete == null;
    }

    public int taille() {
        return taille;
    }

    @Override
    public String toString() {
        String resultat = "SequenceListe [ ";
        boolean premier = true;
        Maillon<E> m = tete;
        while (m != null) {
            if (!premier)
                resultat += ", ";
            resultat += m.element;
            m = m.suivant;
            premier = false;
        }
        resultat += " ]";
        return resultat;
    }
}
