package domain;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

public class Ferme {

  List<Poulailler> poulaillers = new ArrayList<>();

  /**
   * Ajouter un poulailler à la ferme.
   * @throws PoulaillerDejaExistantException si l'on tente d'ajouter un poulailler déjà ajouté à la ferme.
   * @param poulailler
   */
  public void ajouterPoulailler(Poulailler poulailler) {
    if(poulaillers.contains(poulailler))
           throw new PoulaillerDejaExistantException();

    poulaillers.add(poulailler);
  }

  public List<Poulailler> getPoulaillers() {
    return Collections.unmodifiableList(poulaillers);
  }

  /**
   * Trouver la vente la plus grande pour un lot en particulier (il n'est pas nécessaire de
   * vérifier que le lot appartienne à un poulailler de la ferme).
   *
   * @param lot
   * @return la vente trouvée, ou une seule des ventes en cas d'ex aequo (peu importe laquelle),
   * ou null si aucune vente ne peut être trouvée.
   */
  public VenteOeufs trouverVenteLaPlusGrande(Lot lot) {
    // TODO Q3.1
    return lot.getVentes().stream()
            .max(Comparator.comparing(VenteOeufs::getNombreOeufsVendus))
            .orElse(null);
  }

  /**
   * Trouver toutes les ventes pour un nomAcheteur donné, listées dans l’ordre chronologique (du
   * plus ancien au plus récent) , sur l'ensemble des ventes faites dans une ferme.
   *
   * @param nomAcheteur
   * @return les ventes triées trouvées, ou null si aucune vente n'est trouvée.
   */
  public List<VenteOeufs> trouverVentesPourNomAcheteur(String nomAcheteur) {
    // TODO Q3.2 : n'hésitez pas à partir du retour de la fonction obtenirToutesLesVentes()
    return obtenirToutesLesVentes().stream()
            .filter(venteOeufs -> venteOeufs.getNomAcheteur().equals(nomAcheteur))
            .sorted(Comparator.comparing(VenteOeufs::getDate))
            .toList();
  }

  ;

  /**
   * Calculer la somme des oeufs vendus par nom d'acheteur sur l'ensemble des ventes faites au
   * niveau d'une ferme.
   *
   * @return une map reprenant par nom d'acheteur, le nombre d'oeufs vendus.
   */
  public Map<String, Long> calculerOeufsVendusParNomAcheteur() {
    // TODO Q3.3 : n'hésitez pas à partir du retour de la fonction obtenirToutesLesVentes()
    return obtenirToutesLesVentes().stream()
            .collect(groupingBy(VenteOeufs::getNomAcheteur, summingLong(VenteOeufs::getNombreOeufsVendus)));
  }

  /**
   * Grouper les ventes par nom d'acheteur en triant ces groupes par
   * ordre des ventes les plus récentes aux plus anciennes ; lorsque plusieurs ventes sont faites le même jour,
   * trié les ventes par ordre des nombres d'oeufs vendus les plus grands au plus petits  ;
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
            vente.getNomAcheteur());
        if (ventesPourAcheteur == null) {
          ventesPourAcheteur = new ArrayList<>();
        }

        ventesPourAcheteur = ajouterVenteTrieePourAcheteur(ventesPourAcheteur, vente);
        ventesGroupeesParAcheteurTrieesParDate.put(vente.getNomAcheteur(), ventesPourAcheteur);

      }
    }
    return ventesGroupeesParAcheteurTrieesParDate;
  }

  /**
   * Ajouter une vente à une liste associée à un acheteur, afin d'assurer le tri des ventes selon
   * l'ordre suivant : par ordre des ventes des plus récentes au plus anciennes ;
   * puis, en cas de ventes faites le même jour, par ordre
   * des nombres d'oeufs vendus des plus grands au plus petits.
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
