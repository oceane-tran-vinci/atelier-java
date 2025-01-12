package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lot {

  private int nbrVolailles;
  private double cout;
  private boolean affecte;
  private boolean sorti;

  private String race;
  private List<VenteOeufs> ventes = new ArrayList<>();


  public Lot(int nbrVolailles, double cout, String race) {
    if (nbrVolailles < 1 || cout <= 0 || race == null) {
      throw new IllegalArgumentException();
    }
    this.nbrVolailles = nbrVolailles;
    this.cout = cout;
    this.race = race;
  }

  /**
   * Calculer le chiffre d'affaire associé à un lot, c'est à dire la somme des
   * ventes d'oeufs associées à ce lot.
   * @return le chiffre d'affaire calculé.
   */
  public double calculerChiffreDAffaire() {
    double somme = 0;

    for (VenteOeufs vente : ventes) {
      somme += vente.calculerPrixVente();
    }

    return somme;
  }

  /**
   * Ajouter une vente d'oeufs si celle-ci n'existe pas déjà.
   * @param vente
   * @return le succès (ou non) de l'opération.
   */
  public boolean enregistrerVente(VenteOeufs vente) {

    if (ventes.contains(vente)) {
      return false;
    }

    ventes.add(vente);

    return true;
  }

  /**
   * Initialiser toutes les ventes associées à un lot en passant autant d'arguments que souhaité.
   * @throws  IllegalStateException s'il existe déjà des ventes associées à ce lot.
   * @param nouvellesVentes
   */

  public void initialiserVentes(VenteOeufs... nouvellesVentes) {
    if (!ventes.isEmpty()) {
      throw new IllegalStateException("La liste ne peut pas contenir de données !");
    }

    for (int i = 0; i < nouvellesVentes.length; i++) {
      VenteOeufs potentielleNouvelleVente = nouvellesVentes[i];
      if (ventes.contains(potentielleNouvelleVente)) {
        ventes = new ArrayList<>();
        throw new VenteDejaPresenteException();
      }
      ventes.add(potentielleNouvelleVente);
    }

  }

  /**
   * Permettre d'augmenter le cout associé à un lot. L'opération échoue si le lot est déjà sorti du poulailler.
   * @param montant
   * @return le succès de l'opération (ou non).
   */
  public boolean augmenterCout(double montant) {
    if (sorti) {
      return false;
    }

    cout += montant;

    return true;
  }

  /**
   * Signaler une affectation d'un lot à un poulailler.
   * Il ne peut y avoir qu'un seul lot affecté à un poulailler.
   * On ne peut pas affecter un lot qui est déjà affecté à un poulailler.
   * @return le succès de l'opération (ou non).
   */
  public boolean signalerAffectation() {
    if (affecte) {
      return false;
    }

    affecte = true;

    return true;
  }

  /**
   * Signaler qu'un lot est sorti d'un poulailler.
   * On ne peut pas sortir un lot d'un poulailler si celui-ci n'est pas encore affecté ou est
   * déjà sorti.
   * @return le succès de l'opération (ou non).
   */
  public boolean signalerSorti() {
    if (!affecte || sorti) {
      return false;
    }

    sorti = true;

    return true;
  }

  public boolean isSorti() {
    return sorti;
  }

  /**
   * Calculer le résultat associé à un lot, c'est à dire les bénéfices ou pertes associé à un lot, sur base
   * du chiffre d'affaire et du coût associé à l'achat et la maintenance du lot de poules.
   * @return les bénéfices ou pertes associées à un lot.
   */
  public double calculerResultat() {
    return calculerChiffreDAffaire() - cout;
  }

  public List<VenteOeufs> getVentes() {
    return Collections.unmodifiableList(ventes);
  }
}
