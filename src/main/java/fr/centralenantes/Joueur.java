/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package fr.centralenantes;

/**
 * Représente un joueur dans la partie de Monopoly.
 * Gère sa fortune, sa position sur le plateau et ses actions de jeu.
 *
 * @author  
 */
public class Joueur {
    private String nom; // nom du joueur
    private int fortune; // fortune du joueur
    private int position; // position actuelle du joueur sur le plateau
    private Plateau plateau; // référence au plateau de jeu


   /**
     * Construit un nouveau joueur.
     * La fortune est initialisée à 100000€ et la position à 0 (Case Départ).
     *
     * @param nom Le nom du joueur.
     * @param plateau La référence au plateau de jeu sur lequel le joueur évolue.
     */

    public Joueur(String nom, Plateau plateau) {
        this.nom = nom;
        this.plateau = plateau;
        this.fortune = 100000; // fortune initiale
    }
    /**
     * @return Le nom du joueur.
     */
    public String getNom() {
        return nom;
    }
    /**
     * @return La fortune actuelle du joueur.
     */
    public int getFortune() {
        return fortune;
        }
    /**
     * @return L'index de la position du joueur sur le plateau.
     */
    public int getPosition() {
        return position;}
        /**
     * Met à jour la position du joueur.
     * (Logique de passage par la case départ non implémentée ici,
     * [cite_start]gérée par setPosition(int pos) dans le diagramme [cite: 71] si besoin)
     *
     * @param position Le nouvel index (0-39) de la position.
     */
    public void setPosition(int position) {
        this.position = position;}
    /**
     * @return La référence à l'objet Plateau.
     */
    public Plateau getPlateau() {
        return plateau;
    }
    /**
     * Ajoute un montant à la fortune du joueur.
     *
     * @param montant Le montant à créditer.
     */
    public void crediter(int montant) {
        fortune += montant;
}
    /**
     * Retire un montant de la fortune du joueur.
     * Lève une exception si le joueur ne peut pas payer.
     *
     * @param montant Le montant à débiter.
     * [cite_start]@throws NoMoreMoney Si la fortune est insuffisante[cite: 157].
     */
    public void debiter(int montant) throws NoMoreMoney {
        fortune -= montant;
        if (fortune < 0) {
            throw new NoMoreMoney("Le joueur " + nom + " n'a plus d'argent !");
        }
    }
    /**
     * Transfère un montant de ce joueur à un autre.
     *
     * @param j Le joueur bénéficiaire.
     * @param montant Le montant à payer.
     * [cite_start]@throws NoMoreMoney Si le joueur payeur n'a pas les fonds suffisants[cite: 157].
     */
    
    public void payer (Joueur j, int montant) throws NoMoreMoney {
        this.debiter(montant);
        j.crediter(montant);
    }
    /**
     * Calcule le nombre de gares possédées par ce joueur.
     *
     * @return Le nombre total de gares dont ce joueur est propriétaire.
     */
    public int nbGaresPossedees() {
        int count = 0;
        for (Case p : this.plateau.getCases()) {
            if (p instanceof Gare && p.getCases() == this) {
                count++;
            }
        }
        return count;}
    /**
     * Simule un lancer de dé à 6 faces.
     *
     * @return Un entier aléatoire entre 1 et 6.
     */
    public static int lanceLeDe() {
        return ((int) Math.floor(Math.random()*6))+1;
}
/**
     * Exécute les actions d'un tour de jeu pour ce joueur.
     * 1. Lance le dé.
     * 2. Avance sur le plateau.
     * 3. Effectue une action (acheter ou payer) selon la case d'arrivée.
     */

    public void tourDeJeu() {

        int de=lanceLeDe();
        System.out.println(nom + " a lancé le dé : " + de);

        int new_position = this.plateau.avance(position, de);
        this.setPosition(new_position);
        Case current_case = this.plateau.caseAt(new_position);
        System.out.println("Le joueur " + nom + " est en  " + position + " : " + current_case.getNom());
        if (this.plateau.caseAt(new_position) instanceof Achetable) {

            Achetable prop = (Achetable) this.plateau.caseAt(new_position);

            if ((de % 2 != 0) && (prop.estLibre()))  {

                if ((this.getFortune()>= prop.getPrix())){

                    prop.acheter(this);
                    System.out.println(nom + " a acheté la propriété " + prop.getNom() + " pour " + prop.getPrix() + "€.");
                }else{
                    System.out.println(nom + " n'a pas assez d'argent pour acheter " + prop.getNom() + ".");
                }

            }else{
                System.out.println("la case est déjà acquise par " + prop.getNom() + ". Veuillez payer le loyer.");
                int loyer = prop.calculerLoyer(this.plateau, this);
                this.payer(prop.getProprietaire(), loyer);
                System.out.println(nom + " a payé un loyer de " + loyer + "€ à " + prop.getProprietaire().getNom() + ".");
            }
        }
   
    }
    
    @Override
    public String toString() {
        return "Joueur: " + nom + ", Fortune: " + fortune + "€, Position: " + position ;
    
    }}
