package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Ferme {

  List<Poulailler> poulaillers = new ArrayList<>();

  public enum Acheteur {
    DELHAIZE("Supermarché", "Delhaize"),
    CARREFOUR("Supermarché", "Carrefour"),
    JETJ("Epicerie", "JetJ"),
    ROSEMARIE("Epicerie", "Rosemarie"),
    PARTICULIER("Particulier", "Particulier");

    private String type, nom;

    Acheteur(String type, String nom) {
      this.type = type;
      this.nom = nom;
    }

    public String getType() {
      return type;
    }

    public String getNom() {
      return nom;
    }
  }

  /**
   * Ajouter un poulailler à la ferme.
   *
   * @param poulailler
   * @throws PoulaillerDejaExistantException si l'on tente d'ajouter un poulailler déjà ajouté à la
   *                                         ferme.
   */
  public void ajouterPoulailler(Poulailler poulailler) {
    if (poulaillers.contains(poulailler)) {
      throw new PoulaillerDejaExistantException();
    }

    poulaillers.add(poulailler);
  }

  public List<Poulailler> getPoulaillers() {
    return Collections.unmodifiableList(poulaillers);
  }

  /**
   * Grouper les ventes par nom d'acheteur en triant ces groupes par ordre des ventes les plus
   * récentes aux plus anciennes ; lorsque plusieurs ventes sont faites le même jour, trié les
   * ventes par ordre des nombres d'oeufs vendus les plus grands au plus petits  ;
   *
   * @return une map de liste de vente d'oeufs triées
   */
  public Map<String, List<VenteOeufs>> obtenirVentesGroupeesParAcheteurTrieesParDate() {
    Map<String, List<VenteOeufs>> ventesGroupeesParAcheteurTrieesParDate = new TreeMap<>();

    for (Poulailler poulailler : poulaillers) {
      Lot lotActuelDuPoulailler = poulailler.obtenirLotActuelAffecteAuPoulailler();
      if (lotActuelDuPoulailler == null) {
        continue;
      }

      List<VenteOeufs> ventesOeufsPourLotActuel = lotActuelDuPoulailler.getVentes();

      for (VenteOeufs vente : ventesOeufsPourLotActuel) {
        List<VenteOeufs> ventesPourAcheteur = ventesGroupeesParAcheteurTrieesParDate.get(
            vente.getAcheteur().getNom());
        if (ventesPourAcheteur == null) {
          ventesPourAcheteur = new ArrayList<>();
        }

        ventesPourAcheteur = ajouterVenteTrieePourAcheteur(ventesPourAcheteur, vente);
        ventesGroupeesParAcheteurTrieesParDate.put(vente.getAcheteur().getNom(), ventesPourAcheteur);

      }
    }
    return ventesGroupeesParAcheteurTrieesParDate;
  }

  /**
   * Ajouter une vente à une liste associée à un acheteur, afin d'assurer le tri des ventes selon
   * l'ordre suivant : par ordre des ventes des plus récentes au plus anciennes ; puis, en cas de
   * ventes faites le même jour, par ordre des nombres d'oeufs vendus des plus grands au plus
   * petits.
   *
   * @param listePourAcheteur
   * @param venteAajouter
   * @return une liste de ventes d'oeufs triée
   */
  private static List<VenteOeufs> ajouterVenteTrieePourAcheteur(List<VenteOeufs> listePourAcheteur,
      VenteOeufs venteAajouter) {
    List<VenteOeufs> nouvelleListePourAcheteurTriee =
        listePourAcheteur == null ? new ArrayList<>() : new ArrayList<>(listePourAcheteur);
    if (nouvelleListePourAcheteurTriee.isEmpty()) {
      nouvelleListePourAcheteurTriee.add(venteAajouter);
      return nouvelleListePourAcheteurTriee;
    }

    for (int i = 0; i < listePourAcheteur.size(); i++) {
      LocalDate dateVenteActuel = nouvelleListePourAcheteurTriee.get(i).getDate();
      if (venteAajouter.getDate().isAfter(dateVenteActuel)) {
        nouvelleListePourAcheteurTriee.add(i, venteAajouter);
        break;
      } else if (venteAajouter.getDate().equals(dateVenteActuel)) {
        if (venteAajouter.getNombreOeufsVendus() > nouvelleListePourAcheteurTriee.get(i)
            .getNombreOeufsVendus()) {
          nouvelleListePourAcheteurTriee.add(i, venteAajouter);
          break;
        }
      }

      if (i == listePourAcheteur.size() - 1) {
        nouvelleListePourAcheteurTriee.add(venteAajouter);
      }
    }

    return nouvelleListePourAcheteurTriee;
  }

  /**
   * Obtenir toutes les ventes d'une ferme, c'est à dire toutes les ventes associées à tous les
   * poulailler de la ferme, pour tous les lots.
   *
   * @return toutes les ventes de la ferme.
   */
  public List<VenteOeufs> obtenirToutesLesVentes() {
    List<VenteOeufs> toutesLesVentes = new ArrayList<>();

    for (Poulailler poulailler : poulaillers) {
      var toutesLesVentesDunPoulailler = poulailler.getTousLesLots();
      for (Lot lot : toutesLesVentesDunPoulailler) {
        toutesLesVentes.addAll(lot.getVentes());
      }
    }
    return toutesLesVentes;
  }


}
