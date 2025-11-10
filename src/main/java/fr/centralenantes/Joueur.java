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
        
    }
