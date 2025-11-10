public class Constructible extends Achetable {

    private int nbMaisons;
    private int nbHotels;

    // coefficients supposés connus dans l’énoncé
    public static final int A = 1000;  // maison
    public static final int B = 5000;  // hôtel

    public Constructible(String nom, int prix) {
        super(nom, prix);
        this.nbMaisons = 0;
        this.nbHotels = 0;
    }

    public int getNbMaisons() {
        return nbMaisons;
    }

    public int getNbHotels() {
        return nbHotels;
    }

    public void ajouterMaison() {
        nbMaisons++;
    }

    public void ajouterHotel() {
        nbHotels++;
    }

    @Override
    public int calculLoyer(Plateau p, Joueur visiteur) {
        if (proprietaire == null || proprietaire == visiteur) {
            return 0;
        }
        // formule du diagramme : loyer = A * nbMaisons + B * nbHotels
        return A * nbMaisons + B * nbHotels;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", " + nbMaisons + " maisons, "
                + nbHotels + " hôtels, loyer = " + calculLoyer(null, null) + "€";
    }
}