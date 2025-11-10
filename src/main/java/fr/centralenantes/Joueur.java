/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package fr.centralenantes;

/**
 *
 * @author yasminebouhadji
 */
public class Joueur {
    private String nom; // nom du joueur
    private int fortune; // fortune du joueur
    private int position; // position actuelle du joueur sur le plateau
    private Plateau plateau; // référence au plateau de jeu




    public Joueur(String nom, Plateau plateau) {
        this.nom = nom;
        this.plateau = plateau;
    }
    public String getNom() {
        return nom;
    }
    public int getFortune() {
        return fortune;
        }
    public int getPosition() {
        return position;}
    public void setPosition(int position) {
        this.position = position;}
        
    public Plateau getPlateau() {
        return plateau;
    }

    public void crediter(int montant) {
        fortune += montant;
}

    public void debiter(int montant) throws NoMoreMoney {
        fortune -= montant;
        if (fortune < 0) {
            throw new NoMoreMoney("Le joueur " + nom + " n'a plus d'argent !");
        }
    }
    
    public void payer (Joueur j, int montant) throws NoMoreMoney {
        this.debiter(montant);
        j.crediter(montant);
    }
    public int nbGaresPossedees() {
        int count = 0;
        for (Case p : plateau.getCases()) {
            if (p instanceof Gare && p.getCases() == this) {
                count++;
            }
        }
        return count;}

    public static int lanceLeDe() {
        return ((int) Math.floor(Math.random()*6))+1;
}

    public void tourDeJeu() {

        int de=lanceLeDe();
        System.out.println(nom + " a lancé le dé : " + de);

        int new_position = this.Plateau.avance(position, de);
        this.setPosition(new_position);
        System.out.println("Le joueur " + nom + " est en  " + position + " : " + caseAt(new_position).getNom());
        if (caseAt(new_position) instanceof Achetable) {

            Achetable prop = (Achetable) caseAt(new_position);

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
