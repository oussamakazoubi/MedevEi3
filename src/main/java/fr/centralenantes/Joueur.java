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
    public void getNom() {
        return nom;
    }
    

}
