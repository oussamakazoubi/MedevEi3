package fr.centralenantes;

public class TestMedevEi3 {
    
    private static int testsReussis = 0;
    private static int testsTotaux = 0;
    
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  TESTS DU JEU MONOPOLY SIMPLIFIÉ");
        System.out.println("==============================================\n");
        
        testCaseSpeciale();
        testConstructible();
        testGare();
        testPlateau();
        testJoueur();
        testExceptions();
        testIntegration();
        
        System.out.println("\n==============================================");
        System.out.println("  RÉSULTATS FINAUX");
        System.out.println("==============================================");
        System.out.println("Tests réussis : " + testsReussis + "/" + testsTotaux);
        System.out.println("Taux de réussite : " + (testsReussis * 100 / testsTotaux) + "%");
    }
    
    // ========== TESTS CASESPECIALE ==========
    private static void testCaseSpeciale() {
        System.out.println("\n--- Tests CaseSpeciale ---");
        
        // Test 1 : Création d'une case spéciale
        try {
            CaseSpeciale depart = new CaseSpeciale("Départ", "DEPART");
            verifier("CaseSpeciale - Création", 
                    depart.getNom().equals("Départ") && depart.getType().equals("DEPART"));
        } catch (Exception e) {
            verifier("CaseSpeciale - Création", false);
        }
        
        // Test 2 : toString() d'une case spéciale
        try {
            CaseSpeciale prison = new CaseSpeciale("Prison", "PRISON");
            verifier("CaseSpeciale - toString()", 
                    prison.toString().contains("Prison"));
        } catch (Exception e) {
            verifier("CaseSpeciale - toString()", false);
        }
    }
    
    // ========== TESTS CONSTRUCTIBLE ==========
    private static void testConstructible() {
        System.out.println("\n--- Tests Constructible ---");
        
        Plateau p = new Plateau();
        p.initPlateau();
        
        // Test 1 : Création d'une case constructible
        try {
            Constructible rue = new Constructible("Rue de la Paix", 40000);
            verifier("Constructible - Création", 
                    rue.getNom().equals("Rue de la Paix") && rue.getPrix() == 40000);
        } catch (Exception e) {
            verifier("Constructible - Création", false);
        }
        
        // Test 2 : Case libre sans propriétaire
        try {
            Constructible rue = new Constructible("Boulevard", 30000);
            verifier("Constructible - estLibre()", rue.estLibre());
        } catch (Exception e) {
            verifier("Constructible - estLibre()", false);
        }
        
        // Test 3 : Ajout de maisons
        try {
            Constructible rue = new Constructible("Avenue", 25000);
            rue.ajouterMaison();
            rue.ajouterMaison();
            verifier("Constructible - ajouterMaison()", rue.getNbMaisons() == 2);
        } catch (Exception e) {
            verifier("Constructible - ajouterMaison()", false);
        }
        
        // Test 4 : Ajout d'hôtel
        try {
            Constructible rue = new Constructible("Place", 35000);
            rue.ajouterHotel();
            verifier("Constructible - ajouterHotel()", rue.getNbHotels() == 1);
        } catch (Exception e) {
            verifier("Constructible - ajouterHotel()", false);
        }
        
        // Test 5 : Calcul du loyer sans propriétaire
        try {
            Constructible rue = new Constructible("Impasse", 20000);
            Joueur j = new Joueur("Test", p);
            verifier("Constructible - Loyer sans propriétaire", 
                    rue.calculLoyer(p, j) == 0);
        } catch (Exception e) {
            verifier("Constructible - Loyer sans propriétaire", false);
        }
        
        // Test 6 : Calcul du loyer avec maisons
        try {
            Constructible rue = new Constructible("Allée", 30000);
            Joueur proprio = new Joueur("Proprio", p);
            Joueur visiteur = new Joueur("Visiteur", p);
            rue.setProprietaire(proprio);
            rue.ajouterMaison();
            rue.ajouterMaison();
            int loyerAttendu = Constructible.A * 2; // 2 maisons
            verifier("Constructible - Loyer avec maisons", 
                    rue.calculLoyer(p, visiteur) == loyerAttendu);
        } catch (Exception e) {
            verifier("Constructible - Loyer avec maisons", false);
        }
        
        // Test 7 : toString() avec propriétaire et maisons
        try {
            Constructible rue = new Constructible("Rue Crébillon", 45000);
            Joueur proprio = new Joueur("Bidule", p);
            rue.setProprietaire(proprio);
            rue.ajouterMaison();
            rue.ajouterMaison();
            String str = rue.toString();
            verifier("Constructible - toString() complet", 
                    str.contains("Rue Crébillon") && str.contains("Bidule") && str.contains("2 maisons"));
        } catch (Exception e) {
            verifier("Constructible - toString() complet", false);
        }
    }
    
    // ========== TESTS GARE ==========
    private static void testGare() {
        System.out.println("\n--- Tests Gare ---");
        
        Plateau p = new Plateau();
        p.initPlateau();
        
        // Test 1 : Création d'une gare
        try {
            Gare gare = new Gare("Gare de Lyon", 20000);
            verifier("Gare - Création", 
                    gare.getNom().equals("Gare de Lyon") && gare.getPrix() == 20000);
        } catch (Exception e) {
            verifier("Gare - Création", false);
        }
        
        // Test 2 : Constante LOYER_PAR_GARE
        try {
            verifier("Gare - LOYER_PAR_GARE", Gare.LOYER_PAR_GARE == 2500);
        } catch (Exception e) {
            verifier("Gare - LOYER_PAR_GARE", false);
        }
        
        // Test 3 : Calcul du loyer sans propriétaire
        try {
            Gare gare = new Gare("Gare Montparnasse", 20000);
            Joueur j = new Joueur("Test", p);
            verifier("Gare - Loyer sans propriétaire", gare.calculLoyer(p, j) == 0);
        } catch (Exception e) {
            verifier("Gare - Loyer sans propriétaire", false);
        }
        
        // Test 4 : toString() sans propriétaire
        try {
            Gare gare = new Gare("Gare du Nord", 20000);
            String str = gare.toString();
            verifier("Gare - toString() sans propriétaire", 
                    str.contains("Gare du Nord") && str.contains("sans propriétaire"));
        } catch (Exception e) {
            verifier("Gare - toString() sans propriétaire", false);
        }
    }
    
    // ========== TESTS PLATEAU ==========
    private static void testPlateau() {
        System.out.println("\n--- Tests Plateau ---");
        
        // Test 1 : Création du plateau
        try {
            Plateau p = new Plateau();
            verifier("Plateau - Création", p != null);
        } catch (Exception e) {
            verifier("Plateau - Création", false);
        }
        
        // Test 2 : Initialisation du plateau (40 cases)
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            verifier("Plateau - initPlateau() 40 cases", p.getCases().size() == 40);
        } catch (Exception e) {
            verifier("Plateau - initPlateau() 40 cases", false);
        }
        
        // Test 3 : caseAt()
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            Case c = p.caseAt(0);
            verifier("Plateau - caseAt()", c != null);
        } catch (Exception e) {
            verifier("Plateau - caseAt()", false);
        }
        
        // Test 4 : avance() - cas normal
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            int nouvellePos = p.avance(5, 3);
            verifier("Plateau - avance() normal", nouvellePos == 8);
        } catch (Exception e) {
            verifier("Plateau - avance() normal", false);
        }
        
        // Test 5 : avance() - tour complet
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            int nouvellePos = p.avance(38, 5);
            verifier("Plateau - avance() tour complet", nouvellePos == 3);
        } catch (Exception e) {
            verifier("Plateau - avance() tour complet", false);
        }
        
        // Test 6 : nbGares() - joueur sans gare
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            Joueur j = new Joueur("Sans Gare", p);
            verifier("Plateau - nbGares() aucune", p.nbGares(j) == 0);
        } catch (Exception e) {
            verifier("Plateau - nbGares() aucune", false);
        }
        
        // Test 7 : affiche()
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            p.affiche();
            verifier("Plateau - affiche()", true);
        } catch (Exception e) {
            verifier("Plateau - affiche()", false);
        }
        
        // Test 8 : findePartie() - début de partie
        
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            p.getJoueurs().add(new Joueur("Alice", p));
            p.getJoueurs().add(new Joueur("Bob", p));
            verifier("Plateau - findePartie() début", !p.findePartie());
        } catch (Exception e) {
            verifier("Plateau - findePartie() début", false);
        }
    }
    
    // ========== TESTS JOUEUR ==========
    private static void testJoueur() {
        System.out.println("\n--- Tests Joueur ---");
        
        Plateau p = new Plateau();
        p.initPlateau();
        
        // Test 1 : Création d'un joueur
        try {
            Joueur j = new Joueur("Alice", p);
            verifier("Joueur - Création", 
                    j.getNom().equals("Alice") && j.getFortune() == 100000);
        } catch (Exception e) {
            verifier("Joueur - Création", false);
        }
        
        // Test 2 : Position initiale
        try {
            Joueur j = new Joueur("Bob", p);
            verifier("Joueur - Position initiale", j.getPosition() == 0);
        } catch (Exception e) {
            verifier("Joueur - Position initiale", false);
        }
        
        // Test 3 : setPosition()
        try {
            Joueur j = new Joueur("Charlie", p);
            j.setPosition(10);
            verifier("Joueur - setPosition()", j.getPosition() == 10);
        } catch (Exception e) {
            verifier("Joueur - setPosition()", false);
        }
        
        // Test 4 : crediter()
        try {
            Joueur j = new Joueur("David", p);
            j.crediter(5000);
            verifier("Joueur - crediter()", j.getFortune() == 105000);
        } catch (Exception e) {
            verifier("Joueur - crediter()", false);
        }
        
        // Test 5 : debiter() - montant valide
        try {
            Joueur j = new Joueur("Emma", p);
            j.debiter(30000);
            verifier("Joueur - debiter() valide", j.getFortune() == 70000);
        } catch (Exception e) {
            verifier("Joueur - debiter() valide", false);
        }
        
        // Test 6 : lanceLeDe()
        try {
            int de = Joueur.lanceLeDe();
            verifier("Joueur - lanceLeDe()", de >= 1 && de <= 6);
        } catch (Exception e) {
            verifier("Joueur - lanceLeDe()", false);
        }
        
        // Test 7 : nbGaresPossedees() - aucune gare
        try {
            Joueur j = new Joueur("Frank", p);
            verifier("Joueur - nbGaresPossedees() aucune", j.nbGaresPossedees() == 0);
        } catch (Exception e) {
            verifier("Joueur - nbGaresPossedees() aucune", false);
        }
        
        // Test 8 : toString()
        try {
            Joueur j = new Joueur("Grace", p);
            String str = j.toString();
            verifier("Joueur - toString()", str.contains("Grace") && str.contains("100000"));
        } catch (Exception e) {
            verifier("Joueur - toString()", false);
        }
    }
    
    // ========== TESTS EXCEPTIONS ==========
    private static void testExceptions() {
        System.out.println("\n--- Tests Exceptions ---");
        
        Plateau p = new Plateau();
        p.initPlateau();
        
        // Test 1 : debiter() - montant insuffisant
        try {
            Joueur j = new Joueur("Pauvre", p);
            j.debiter(150000); // Plus que la fortune initiale
            verifier("Exception - debiter() insuffisant", false);
        } catch (NoMoreMoney e) {
            verifier("Exception - debiter() insuffisant", true);
        } catch (Exception e) {
            verifier("Exception - debiter() insuffisant", false);
        }
        
        // Test 2 : payer() - montant insuffisant
        try {
            Joueur j1 = new Joueur("Débiteur", p);
            Joueur j2 = new Joueur("Créancier", p);
            j1.payer(j2, 150000);
            verifier("Exception - payer() insuffisant", false);
        } catch (NoMoreMoney e) {
            verifier("Exception - payer() insuffisant", true);
        } catch (Exception e) {
            verifier("Exception - payer() insuffisant", false);
        }
        
        // Test 3 : payer() - montant valide
        try {
            Joueur j1 = new Joueur("Payeur", p);
            Joueur j2 = new Joueur("Receveur", p);
            int fortuneInitJ1 = j1.getFortune();
            int fortuneInitJ2 = j2.getFortune();
            j1.payer(j2, 10000);
            verifier("Exception - payer() valide", 
                    j1.getFortune() == fortuneInitJ1 - 10000 && 
                    j2.getFortune() == fortuneInitJ2 + 10000);
        } catch (Exception e) {
            verifier("Exception - payer() valide", false);
        }
        
        // Test 4 : Message de NoMoreMoney
        try {
            throw new NoMoreMoney("Fonds insuffisants");
        } catch (NoMoreMoney e) {
            verifier("Exception - Message NoMoreMoney", 
                    e.getMessage().equals("Fonds insuffisants"));
        } catch (Exception e) {
            verifier("Exception - Message NoMoreMoney", false);
        }
    }
    
    // ========== TESTS D'INTÉGRATION ==========
    private static void testIntegration() {
        System.out.println("\n--- Tests d'Intégration ---");
        
        // Test 1 : Achat d'une propriété
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            Joueur j = new Joueur("Acheteur", p);
            
            // Trouver une case achetable dans le plateau
            Achetable prop = null;
            for (Case c : p.getCases()) {
                if (c instanceof Achetable) {
                    prop = (Achetable) c;
                    break;
                }
            }
            
            if (prop != null) {
                int fortuneAvant = j.getFortune();
                prop.acheter(j);
                verifier("Integration - Achat propriété", 
                        prop.getProprietaire() == j && 
                        j.getFortune() == fortuneAvant - prop.getPrix());
            } else {
                verifier("Integration - Achat propriété", false);
            }
        } catch (Exception e) {
            verifier("Integration - Achat propriété", false);
        }
        
        // Test 2 : Tour de jeu complet
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            Joueur j1 = new Joueur("J1", p);
            Joueur j2 = new Joueur("J2", p);
            p.getJoueurs().add(j1);
            p.getJoueurs().add(j2);
            
            int posAvant = j1.getPosition();
            j1.tourDeJeu();
            verifier("Integration - Tour de jeu", j1.getPosition() != posAvant);
        } catch (Exception e) {
            verifier("Integration - Tour de jeu", false);
        }
        
        // Test 3 : Propriétaire avec plusieurs gares
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            Joueur j = new Joueur("CollectionneurGares", p);
            
            int nbGaresAchetees = 0;
            for (Case c : p.getCases()) {
                if (c instanceof Gare && nbGaresAchetees < 3) {
                    Gare g = (Gare) c;
                    g.acheter(j);
                    nbGaresAchetees++;
                }
            }
            
            verifier("Integration - Plusieurs gares", 
                    p.nbGares(j) == 3 && j.nbGaresPossedees() == 3);
        } catch (Exception e) {
            verifier("Integration - Plusieurs gares", false);
        }
        
        // Test 4 : Calcul loyer avec plusieurs gares
        try {
            Plateau p = new Plateau();
            p.initPlateau();
            Joueur proprio = new Joueur("PropriétaireGares", p);
            Joueur visiteur = new Joueur("Visiteur", p);
            
            // Acheter 2 gares
            int nbGares = 0;
            Gare derniereGare = null;
            for (Case c : p.getCases()) {
                if (c instanceof Gare && nbGares < 2) {
                    Gare g = (Gare) c;
                    g.acheter(proprio);
                    derniereGare = g;
                    nbGares++;
                }
            }
            
            if (derniereGare != null) {
                int loyerAttendu = 2 * Gare.LOYER_PAR_GARE;
                int loyerCalcule = derniereGare.calculLoyer(p, visiteur);
                verifier("Integration - Loyer gares", loyerCalcule == loyerAttendu);
            } else {
                verifier("Integration - Loyer gares", false);
            }
        } catch (Exception e) {
            verifier("Integration - Loyer gares", false);
        }
    }
    
    // ========== MÉTHODE UTILITAIRE ==========
    private static void verifier(String nomTest, boolean condition) {
        testsTotaux++;
        if (condition) {
            testsReussis++;
            System.out.println("✓ " + nomTest);
        } else {
            System.out.println("✗ " + nomTest + " - ÉCHEC");
        }
    }
}