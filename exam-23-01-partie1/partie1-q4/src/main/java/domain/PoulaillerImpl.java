package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class PoulaillerImpl implements Poulailler {

  private String identifiant;
  private int surface;

  private List<Lot> tousLesLots = new ArrayList<>();

  public PoulaillerImpl(String identifiant, int surface) {
    this.identifiant = identifiant;
    this.surface = surface;
  }

  /**
   * Ajouter un lot au poulailler si le lot n'est pas déjà affecté.
   * Cette méthode signale l'affectation du lot au poulailler.
   * @param lot
   * @return le succès de l'opération (ou non).
   */
  @Override
  public boolean ajouterLot(Lot lot) {
    if (verifierLotActuelDejaAffecteAuPoulailler() || !lot.signalerAffectation()) {
      return false;
    }

    tousLesLots.add(lot);
    return true;
  }

  /**
   * Signaler qu'un lot est sorti d'un poulailler.
   * On ne peut pas sortir un lot d'un poulailler si celui-ci n'est pas encore affecté ou est
   * déjà sorti.
   * @return le succès de l'opération (ou non).
   */

  @Override
  public boolean signalerSortieLot() {
    var lotActuel = obtenirLotActuelAffecteAuPoulailler();
    if (lotActuel == null || !lotActuel.signalerSorti()) {
      return false;
    }
    return true;
  }

  @Override
  public Iterator<Lot> iterator() {
    return tousLesLots.iterator();
  }

  /**
   * Obtenir le lot qui est actuellement affecté au poulaillé.
   * @return lot qui est affecté au poulaillé, ou null si aucune affectation.
   */

  @Override
  public Lot obtenirLotActuelAffecteAuPoulailler() {
    if (tousLesLots.isEmpty()) {
      return null;
    }
    var lotActuelPotentiel = tousLesLots.get(tousLesLots.size() - 1);
    if (lotActuelPotentiel.isSorti() == true) {
      return null;
    }
    return lotActuelPotentiel;
  }

  /**
   * Vérifier si un lot est déjà affecté au poulailler.
   * @return le statut d'affectation d'un lot au poulailler.
   */
  @Override
  public boolean verifierLotActuelDejaAffecteAuPoulailler() {
    if (tousLesLots.isEmpty()) {
      return false;
    }
    var lotActuelPotentiel = obtenirLotActuelAffecteAuPoulailler();
    return lotActuelPotentiel != null;
  }

  @Override
  public String getIdentifiant() {
    return identifiant;
  }

  @Override
  public List<Lot> getTousLesLots() {
    return Collections.unmodifiableList(tousLesLots);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PoulaillerImpl that = (PoulaillerImpl) o;

    return Objects.equals(identifiant, that.identifiant);
  }

  @Override
  public int hashCode() {
    return identifiant != null ? identifiant.hashCode() : 0;
  }
}
