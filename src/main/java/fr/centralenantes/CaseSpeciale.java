package fr.centralenantes;

/**
 * Représente une case spéciale du plateau de jeu Monopoly simplifié.
 * <p>
 * Une {@code CaseSpeciale} est une case qui ne peut pas être achetée
 * (exemples : "Départ", "Prison", "Chance", etc.).
 * Elle hérite de la classe abstraite {@link Case}.
 * </p>
 *
 * @author
 * @version 1.0
 */
public class CaseSpeciale extends Case {

    /** Type de la case spéciale (ex. : "Prison", "Départ", "Chance"). */
    private String type;

    /**
     * Constructeur de la case spéciale.
     *
     * @param nom  le nom de la case (affiché sur le plateau)
     * @param type le type de la case (ex. : "Départ", "Prison", "Chance")
     */
    public CaseSpeciale(String nom, String type) {
        super(nom);
        this.type = type;
    }

    /**
     * Retourne le type de la case spéciale.
     *
     * @return le type de la case (ex. : "Chance", "Prison", etc.)
     */
    public String getType() {
        return type;
    }

    /**
     * Retourne une représentation textuelle de la case spéciale.
     * <p>
     * Par exemple : "Départ" ou "Prison".
     * </p>
     *
     * @return le nom de la case
     */
    @Override
    public String toString() {
        return nom; // Exemple : "Départ" ou "Prison"
    }
}