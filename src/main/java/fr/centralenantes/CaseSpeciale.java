package fr.centralenantes;

public class CaseSpeciale extends Case {
    private String type;

    public CaseSpeciale(String nom, String type) {
        super(nom);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return nom; // Exemple : "Départ" ou "Prison"
    }
}