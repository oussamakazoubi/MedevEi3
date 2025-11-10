/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centralenantes;

/**
 *
 * @author srodr
 */
public abstract class Case {
    
    public String nom; 

    public Case(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
    
}
