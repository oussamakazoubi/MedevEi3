package fr.centralenantes;
/**
 * Classe abstraite Case
 * ---------------------
 * Représente une case générique du plateau de Monopoly.
 * Chaque case possède un nom et redéfinit toString() pour l’affichage.
 */
public abstract class Case {

    // ===================== ATTRIBUTS =====================
    protected String nom; // Nom de la case (ex: "Gare du Nord", "Rue Crébillon", "Prison")

    // ===================== CONSTRUCTEUR =====================
    /**
     * Constructeur d'une case générique.
     * @param nom Nom de la case.
     */
    public Case(String nom) {
        this.nom = nom;
    }

    // ===================== GETTER =====================
    /**
     * Renvoie le nom de la case.
     * @return nom de la case
     */
    public String getNom() {
        return nom;
    }

    // ===================== MÉTHODES ABSTRAITES =====================
    /**
     * Chaque sous-classe doit fournir sa propre représentation textuelle.
     * Exemple :
     *   - Gare de Lyon (coût : 20000 €) - sans propriétaire
     *   - Rue Crébillon (coût : 45000€) – propriétaire : Bidule, 2 maisons, loyer = 1500€
     *   - Départ
     */
    @Override
    public abstract String toString();
}