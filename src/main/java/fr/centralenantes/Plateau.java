package fr.centralenantes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Classe Plateau.
 * <p>
 * Represente le plateau du jeu Monopoly simplifie.
 * Ce plateau contient 40 cases et une liste de joueurs.
 * Il est genere de maniere aleatoire avec des types de cases selon les probabilites suivantes :
 * </p>
 * <ul>
 *   <li>20% de gares</li>
 *   <li>60% de cases constructibles</li>
 *   <li>20% de cases speciales (Chance, Prison, etc.)</li>
 * </ul>
 *
 * <p>La case numero 0 correspond toujours au "Depart".</p>
 *
 * @author srodr
 * @version 1.0
 * @since 2025-11
 */
public class Plateau {

    // ===================== ATTRIBUTS =====================

    /** Liste ordonnee des 40 cases du plateau. */
    private ArrayList<Case> plateau;

    /** Liste des joueurs actuellement en jeu. */
    private LinkedList<Joueur> joueurs;

    /** Generateur aleatoire utilise pour la creation des cases. */
    private Random random;

    // ===================== CONSTRUCTEUR =====================

    /**
     * Constructeur du plateau de jeu.
     * Initialise les structures de donnees et le generateur aleatoire.
     */
    public Plateau() {
        this.plateau = new ArrayList<>();
        this.joueurs = new LinkedList<>();
        this.random = new Random();
    }

    // ===================== GETTERS =====================

    /**
     * Retourne la liste complete des cases du plateau.
     * @return liste de toutes les cases
     */
    public ArrayList<Case> getCases() {
        return plateau;
    }

    /**
     * Retourne la liste des joueurs actuellement sur le plateau.
     * @return liste des joueurs
     */
    public LinkedList<Joueur> getJoueurs() {
        return joueurs;
    }

    // ===================== GENERATION DU PLATEAU =====================

    /**
     * Initialise le plateau de maniere aleatoire selon les probabilites definies :
     * <ul>
     *   <li>20% de gares</li>
     *   <li>60% de constructibles</li>
     *   <li>20% de cases speciales</li>
     * </ul>
     * <p>La premiere case (indice 0) est toujours "Depart".</p>
     */
    public void initPlateau() {
        plateau.clear();
        plateau.add(new CaseSpeciale("Depart", "Depart")); // Case de depart

        for (int i = 1; i < 40; i++) {
            plateau.add(generateRandomCase(i));
        }

        System.out.println("Plateau genere aleatoirement avec " + plateau.size() + " cases.\n");
    }

    /**
     * Genere une case aleatoire selon une probabilite donnee.
     * @param index index de la case sur le plateau (1 a 39)
     * @return une instance de la classe Case (Gare, Constructible ou CaseSpeciale)
     */
    private Case generateRandomCase(int index) {
        double p = random.nextDouble(); // Valeur entre 0.0 et 1.0

        if (p < 0.20) {
            return generateRandomGare(index);
        } else if (p < 0.80) { // Entre 0.20 et 0.80 -> 60%
            return generateRandomConstructible(index);
        } else {
            return generateRandomCaseSpeciale(index);
        }
    }

    /**
     * Cree une gare aleatoire avec un nom et un prix variables.
     * @param index position de la case
     * @return une instance de Gare
     */
    private Gare generateRandomGare(int index) {
        String[] nomsGares = {"Gare du Nord", "Gare de Lyon", "Gare Montparnasse", "Gare Saint-Lazare"};
        String nom = nomsGares[random.nextInt(nomsGares.length)] + " " + index;
        int prix = 15000 + random.nextInt(10000); // entre 15 000 et 25 000
        return new Gare(nom, prix);
    }

    /**
     * Cree une case constructible aleatoire avec un nom et un prix variables.
     * @param index position de la case
     * @return une instance de Constructible
     */
    private Constructible generateRandomConstructible(int index) {
        String[] nomsRues = {"Rue Crebillon", "Rue Lafayette", "Avenue Foch", "Boulevard Voltaire", "Rue de la Paix"};
        String nom = nomsRues[random.nextInt(nomsRues.length)] + " " + index;
        int prix = 30000 + random.nextInt(40000); // entre 30 000 et 70 000
        return new Constructible(nom, prix);
    }

    /**
     * Cree une case speciale aleatoire (Prison, Chance, Impots, etc.).
     * @param index position de la case
     * @return une instance de CaseSpeciale
     */
    private CaseSpeciale generateRandomCaseSpeciale(int index) {
        String[] types = {"Prison", "Chance", "Impots", "Caisse de communaute", "Visite simple"};
        String type = types[random.nextInt(types.length)];
        return new CaseSpeciale(type + " " + index, type);
    }

    // ===================== METHODES DE LOGIQUE =====================

    /**
     * Calcule le nombre de gares possedees par un joueur.
     * @param j joueur concerne
     * @return nombre total de gares possedees
     */
    public int nbGares(Joueur j) {
        int nb = 0;
        for (Case c : plateau) {
            if (c instanceof Gare) {
                Gare g = (Gare) c;
                if (g.getProprietaire() != null && g.getProprietaire().equals(j)) {
                    nb++;
                }
            }
        }
        return nb;
    }

    /**
     * Retourne la case situee a un indice donne (modulo la taille du plateau).
     * @param index position souhaitee
     * @return case correspondante
     */
    public Case caseAt(int index) {
        return plateau.get(index % plateau.size());
    }

    /**
     * Retourne l'indice d'une case donnee dans la liste.
     * @param c case recherchee
     * @return indice de la case ou -1 si non trouvee
     */
    public int indexOf(Case c) {
        return plateau.indexOf(c);
    }

    /**
     * Calcule la nouvelle position d'un joueur apres un deplacement.
     * @param position position actuelle
     * @param d valeur du de ou du deplacement
     * @return nouvelle position (modulo 40)
     */
    public int avance(int position, int d) {
        return (position + d) % plateau.size();
    }

    // ===================== AFFICHAGE =====================

    /**
     * Affiche dans la console l'etat complet du plateau :
     * <ul>
     *   <li>La liste des cases avec leurs informations (via toString)</li>
     *   <li>La liste des joueurs avec leur fortune et position</li>
     * </ul>
     */
    public void affiche() {
        System.out.println("===== Etat du Plateau =====");
        for (int i = 0; i < plateau.size(); i++) {
            System.out.printf("%02d. %s%n", i, plateau.get(i).toString());
        }

        System.out.println("\n===== Joueurs =====");
        for (Joueur j : joueurs) {
            System.out.println(j.toString());
        }
        System.out.println("===========================\n");
    }

    // ===================== GESTION DE LA PARTIE =====================

    /**
     * Indique si la partie est terminee (un seul joueur restant ou aucun).
     * @return true si la partie est finie, false sinon
     */
    public boolean findePartie() {
        return joueurs.size() <= 1;
    }

    /**
     * Fait jouer tous les joueurs jusqu'a la fin de la partie.
     * Supprime les joueurs elimines en cours de route.
     */
    public void tourDeJeuComplet() {
        System.out.println("Debut de la partie");
        while (!findePartie()) {
            LinkedList<Joueur> elimines = new LinkedList<>();

            for (Joueur j : joueurs) {
                try {
                    j.tourDeJeu();
                } catch (NoMoreMoney e) {
                    System.out.println(j.getNom() + " est elimine : " + e.getMessage());
                    elimines.add(j);
                }
            }

            joueurs.removeAll(elimines);
            affiche();
        }

        if (!joueurs.isEmpty()) {
            System.out.println("Le gagnant est : " + joueurs.getFirst().getNom());
        } else {
            System.out.println("Aucun gagnant, tous les joueurs sont ruines !");
        }
    }
}