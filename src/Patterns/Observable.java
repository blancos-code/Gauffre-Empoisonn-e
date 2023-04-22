package Patterns;
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

/*
 * Pattern Observateur tel que pr�sent� dans le livre de Gamma et Al.
 * Ce pattern existe d�j� dans la biblioth�que standard de Java sous une forme
 * l�g�rement diff�rente. Il est r�impl�ment� ici � des fins p�dagogiques
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Observable {
    List<Observateur> observateurs;

    public Observable() {
        observateurs = new ArrayList<>();
    }

    public void ajouteObservateur(Observateur o) {
        observateurs.add(o);
    }

    public void metAJour() {
        Iterator<Observateur> it;

        it = observateurs.iterator();
        while (it.hasNext()) {
            Observateur o = it.next();
            o.miseAJour();
        }
    }
}
