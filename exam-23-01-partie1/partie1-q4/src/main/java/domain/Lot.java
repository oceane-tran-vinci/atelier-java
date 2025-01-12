package domain;

import java.util.List;

public interface Lot {
    /**
     * Calculer le chiffre d'affaire associé à un lot, c'est à dire la somme des
     * ventes d'oeufs associées à ce lot.
     *
     * @return le chiffre d'affaire calculé.
     */
    double calculerChiffreDAffaire();

    /**
     * Ajouter une vente d'oeufs si celle-ci n'existe pas déjà.
     *
     * @param vente
     * @return le succès (ou non) de l'opération.
     */
    boolean enregistrerVente(VenteOeufs vente);

    /**
     * Initialiser toutes les ventes associées à un lot en passant autant d'arguments que souhaité.
     *
     * @param nouvellesVentes
     * @throws IllegalStateException s'il existe déjà des ventes associées à ce lot.
     */

    void initialiserVentes(VenteOeufs... nouvellesVentes);

    /**
     * Permettre d'augmenter le cout associé à un lot. L'opération échoue si le lot est déjà sorti du poulailler.
     *
     * @param montant
     * @return le succès de l'opération (ou non).
     */
    boolean augmenterCout(double montant);

    /**
     * Signaler une affectation d'un lot à un poulailler.
     * Il ne peut y avoir qu'un seul lot affecté à un poulailler.
     * On ne peut pas affecter un lot qui est déjà affecté à un poulailler.
     *
     * @return le succès de l'opération (ou non).
     */
    boolean signalerAffectation();

    /**
     * Signaler qu'un lot est sorti d'un poulailler.
     * On ne peut pas sortir un lot d'un poulailler si celui-ci n'est pas encore affecté ou est
     * déjà sorti.
     *
     * @return le succès de l'opération (ou non).
     */
    boolean signalerSorti();

    boolean isSorti();

    /**
     * Calculer le résultat associé à un lot, c'est à dire les bénéfices ou pertes associé à un lot, sur base
     * du chiffre d'affaire et du coût associé à l'achat et la maintenance du lot de poules.
     *
     * @return les bénéfices ou pertes associées à un lot.
     */
    double calculerResultat();

    List<VenteOeufs> getVentes();
}
