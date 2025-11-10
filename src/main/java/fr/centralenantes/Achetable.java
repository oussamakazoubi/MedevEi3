package fr.centralenantes;

/**
 * La classe Achetable représente une case du plateau de jeu qui peut être achetée par un joueur.
 * Elle hérite de la classe Case.
 *
 * Chaque case achetable a un prix et peut avoir un propriétaire.
 * Cette classe fournit des méthodes pour gérer l'achat, vérifier la disponibilité et calculer le loyer.
 *
 * @author user
 */
public class Achetable extends Case {

    /** Prix d'achat de la case */
    protected int prix;

    /** Joueur propriétaire de la case ; null si la case est libre */
    protected Joueur proprietaire;

    // --- Constructeur ---

    /**
     * Crée une case achetable avec un nom, un prix et un propriétaire initial.
     * Par défaut, le propriétaire est null (case libre).
     *
     * @param nom Nom de la case
     * @param prix Prix d'achat de la case
     * @param proprietaire Joueur propriétaire initial (null si libre)
     */
    public Achetable(String nom, int prix, Joueur proprietaire) {
        super(nom);
        this.prix = prix;
        this.proprietaire = proprietaire;
    }

    public Achetable(String nom, int prix) {
        super(nom);
        this.prix = prix;
        this.proprietaire = null;
    }
    // --- Getters ---

    /**
     * Retourne le prix d'achat de la case.
     *
     * @return prix de la case
     */
    public int getPrix() {
        return prix;
    }

    /**
     * Retourne le joueur propriétaire de la case.
     *
     * @return propriétaire actuel ou null si libre
     */
    public Joueur getProprietaire() {
        return this.proprietaire;
    }

    // --- Setters ---

    /**
     * Définit le propriétaire de la case.
     *
     * @param j Joueur qui devient le propriétaire
     */
    public void setProprietaire(Joueur j) {
        this.proprietaire = j;
    }

    // --- Méthodes utilitaires ---

    /**
     * Indique si la case est libre (non possédée).
     *
     * @return true si la case n'a pas de propriétaire, false sinon
     */
    public boolean estLibre() {
        return proprietaire == null;
    }

    /**
     * Permet à un joueur d'acheter la case.
     * Vérifie si la case est libre et si le joueur a assez d'argent.
     * Met à jour le propriétaire si l'achat est réussi.
     *
     * @param acheteur Joueur qui souhaite acheter la case
     */
    public void acheter(Joueur acheteur) {
        if (!estLibre()) {
            System.out.println("La case " + nom + " appartient déjà à " + proprietaire.getNom() + ".");
            return;
        }

        if (acheteur.getFortune() < prix) {
            System.out.println(acheteur.getNom() + " n’a pas assez d’argent pour acheter " + nom + ".");
            return;
        }

        try {
            acheteur.debiter(prix);
            this.proprietaire = acheteur;
            System.out.println(acheteur.getNom() + " a acheté " + nom + " pour " + prix + " € !");
        } catch (NoMoreMoney e) {
            System.out.println("Achat impossible : " + e.getMessage());
        }
    }

    /**
     * Calcule le loyer dû par un visiteur.
     * Par défaut, le loyer est de 0, mais cette méthode peut être redéfinie par les sous-classes
     * (par exemple pour les cases Gare ou Constructible).
     *
     * @param p Plateau de jeu (peut être utilisé pour le calcul du loyer)
     * @param visiteur Joueur qui visite la case
     * @return montant du loyer à payer
     */
    public int calculLoyer(Plateau p, Joueur visiteur) {
        if (estLibre() || proprietaire == visiteur) {
            return 0;
        }
        return 0;
    }

    // --- Représentation textuelle ---

    /**
     * Retourne une représentation textuelle de la case.
     * Affiche le nom, le prix et le propriétaire si la case est possédée.
     *
     * @return description textuelle de la case
     */
    @Override
    public String toString() {
        if (proprietaire == null) {
            return nom + " (coût : " + prix + " €) - sans propriétaire";
        } else {
            return nom + " (coût : " + prix + " €) - propriétaire : " + proprietaire.getNom();
        }
    }
}
