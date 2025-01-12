package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class VenteOeufs {

  private LocalDate date;
  private int nombreOeufsVendus;
  private double prixPiece;

  private String typeAcheteur;

  private String nomAcheteur;

  private String venteId;

  private final static String NOM_ACHETEUR_PAR_DEFAUT = "Particulier";

  public VenteOeufs(LocalDate date, int nombreOeufsVendus, double prixPiece, String typeAcheteur,
      String nomAcheteur) {
    this.date = date;
    this.nombreOeufsVendus = nombreOeufsVendus;
    this.prixPiece = prixPiece;
    this.typeAcheteur = typeAcheteur;
    this.nomAcheteur = nomAcheteur;
    venteId = UUID.randomUUID().toString();
  }

  public VenteOeufs(LocalDate date, int nombreOeufsVendus, double prixPiece, String typeAcheteur) {
    this(date, nombreOeufsVendus, prixPiece, typeAcheteur, NOM_ACHETEUR_PAR_DEFAUT);
  }

  public LocalDate getDate() {
    return date;
  }

  public double calculerPrixVente() {
    return this.nombreOeufsVendus * this.prixPiece;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    VenteOeufs that = (VenteOeufs) o;

    return venteId.equals(that.venteId);
  }

  @Override
  public int hashCode() {
    return venteId.hashCode();
  }

  public String getTypeAcheteur() {
    return typeAcheteur;
  }

  public String getNomAcheteur() {
    return nomAcheteur;
  }

  public double getPrixPiece() {
    return prixPiece;
  }

  public int getNombreOeufsVendus() {
    return nombreOeufsVendus;
  }

  public String getVenteId() {
    return venteId;
  }

  @Override
  public String toString() {
    return
        "date=" + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            +
            ", nombreOeufsVendus=" + nombreOeufsVendus +
            ", prixPiece=" + prixPiece +
            ", nomAcheteur=" + nomAcheteur;
  }
}
