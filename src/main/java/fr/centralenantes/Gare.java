package fr.centralenantes;

/**
 * Classe représentant une Gare sur le plateau du Monopoly.
 * Une Gare est une Case Achetable : elle a un prix et un propriétaire.
 * Le loyer dépend du nombre de gares possédées par le propriétaire.
 */
public class Gare extends Achetable {

    /** Loyer de base par gare possédée */
    public static final int LOYER_PAR_GARE = 2500;

    /**
     * Constructeur d'une gare.
     * 
     * @param nom  nom de la gare
     * @param prix prix d’achat de la gare
     */
    public Gare(String nom, int prix) {
        super(nom, prix);
    }

    /**
     * Calcule le loyer que le visiteur doit payer lorsqu’il s’arrête sur la gare.
     * 
     * Règle :
     *  - Si la gare n’a pas de propriétaire → loyer = 0
     *  - Si le propriétaire est le visiteur → loyer = 0
     *  - Sinon → loyer = LOYER_PAR_GARE × (nombre de gares possédées par le propriétaire)
     * 
     * @param plateau  le plateau de jeu (permet de connaître toutes les cases)
     * @param visiteur le joueur qui vient de tomber sur cette case
     * @return le montant du loyer à payer
     */
    public int calculLoyer(Plateau plateau, Joueur visiteur) {
        if (proprietaire == null || proprietaire == visiteur) {
            return 0; // Cas d'erreur
        }
        int nbGares = plateau.nbGares(proprietaire);
        return LOYER_PAR_GARE * nbGares;
    }

    /**
     * Fournit une représentation textuelle de la gare.
     */
    @Override
    public String toString() {
        String texte = nom + " (coût : " + prix + "€) - ";
        if (proprietaire == null) {
            texte += "sans propriétaire";
        } else {
            texte += "propriétaire : " + proprietaire.getNom();
        }
        return texte;
    }
}
