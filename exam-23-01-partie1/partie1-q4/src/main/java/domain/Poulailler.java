package domain;

import java.util.Iterator;
import java.util.List;

public interface Poulailler extends Iterable<Lot> {
    /**
     * Ajouter un lot au poulailler si le lot n'est pas déjà affecté.
     * Cette méthode signale l'affectation du lot au poulailler.
     *
     * @param lot
     * @return le succès de l'opération (ou non).
     */
    boolean ajouterLot(Lot lot);

    /**
     * Signaler qu'un lot est sorti d'un poulailler.
     * On ne peut pas sortir un lot d'un poulailler si celui-ci n'est pas encore affecté ou est
     * déjà sorti.
     *
     * @return le succès de l'opération (ou non).
     */

    boolean signalerSortieLot();

    @Override
    Iterator<Lot> iterator();

    /**
     * Obtenir le lot qui est actuellement affecté au poulaillé.
     *
     * @return lot qui est affecté au poulaillé, ou null si aucune affectation.
     */

    Lot obtenirLotActuelAffecteAuPoulailler();

    /**
     * Vérifier si un lot est déjà affecté au poulailler.
     *
     * @return le statut d'affectation d'un lot au poulailler.
     */
    boolean verifierLotActuelDejaAffecteAuPoulailler();

    String getIdentifiant();

    List<Lot> getTousLesLots();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
