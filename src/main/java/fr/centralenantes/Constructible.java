package fr.centralenantes;
/**
 * Représente une case constructible du plateau (type rue/terrain) qu’un joueur peut acheter et sur laquelle
 * il est possible de construire des maisons et des hôtels. Le loyer dû par un visiteur dépend du
 * nombre de constructions via la formule: loyer = A × nbMaisons + B × nbHotels.
 */
public class Constructible extends Achetable {

    /** Nombre de maisons construites sur la propriété. */
    private int nbMaisons;
    /** Nombre d'hôtels construits sur la propriété. */
    private int nbHotels;

    /**
     * Coefficient utilisé pour le calcul du loyer par maison.
     * Valeur supposée connue dans l’énoncé.
     */
    public static final int A = 1000;  // maison
    /**
     * Coefficient utilisé pour le calcul du loyer par hôtel.
     * Valeur supposée connue dans l’énoncé.
     */
    public static final int B = 5000;  // hôtel

    /**
     * Crée une case constructible avec un nom et un prix d'achat.
     * @param nom  le nom de la propriété
     * @param prix le prix d’achat de la propriété
     */
    public Constructible(String nom, int prix) {
        super(nom, prix);
        this.nbMaisons = 0;
        this.nbHotels = 0;
    }

    /**
     * Retourne le nombre de maisons construites.
     * @return le nombre de maisons
     */
    public int getNbMaisons() {
        return nbMaisons;
    }

    /**
     * Retourne le nombre d'hôtels construits.
     * @return le nombre d'hôtels
     */
    public int getNbHotels() {
        return nbHotels;
    }

    /**
     * Ajoute une maison sur la propriété.
     */
    public void ajouterMaison() {
        nbMaisons++;
    }

    /**
     * Ajoute un hôtel sur la propriété.
     */
    public void ajouterHotel() {
        nbHotels++;
    }

    @Override
    /**
     * Calcule le loyer dû par le joueur visiteur en fonction du nombre de maisons et d'hôtels.
     * Si la case n'a pas de propriétaire ou que le visiteur en est le propriétaire, le loyer est 0.
     * Formule: {@code loyer = A * nbMaisons + B * nbHotels}.
     *
     * @param p        le plateau (non utilisé ici)
     * @param visiteur le joueur qui visite la case
     * @return le montant du loyer à payer
     */
    public int calculLoyer(Plateau p, Joueur visiteur) {
        if (proprietaire == null || proprietaire == visiteur) {
            return 0;
        }
        // formule du diagramme : loyer = A * nbMaisons + B * nbHotels
        return A * nbMaisons + B * nbHotels;
    }

    @Override
    /**
     * Représentation textuelle incluant le nombre de constructions et le loyer calculé.
     * @return une chaîne descriptive de la propriété
     */
    public String toString() {
        return super.toString()
                + ", " + nbMaisons + " maisons, "
                + nbHotels + " hôtels, loyer = " + calculLoyer(null, null) + "€";
    }
}
