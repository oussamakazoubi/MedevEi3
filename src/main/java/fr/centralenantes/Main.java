package fr.centralenantes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main extends JFrame {
    private Plateau plateau;
    private JTextArea plateauArea;
    private JTextArea logArea;
    private JButton btnTourJoueur;
    private JButton btnTourComplet;
    private JButton btnReset;
    private JLabel lblJoueurActuel;
    private JPanel panelJoueurs;
    private int joueurActuelIndex = 0;
    private Joueur joueurHumain; // Le joueur contrôlé par l'utilisateur
    
    public Main() {
        setTitle("Monopoly Simplifié - TP1 MEDEV (Mode Interactif)");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Initialisation du plateau
        plateau = new Plateau();
        plateau.initPlateau();
        
        // Demander le nom du joueur humain
        String nomJoueur = JOptionPane.showInputDialog(
            this,
            "Entrez votre nom de joueur :",
            "Bienvenue au Monopoly !",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (nomJoueur == null || nomJoueur.trim().isEmpty()) {
            nomJoueur = "Vous";
        }
        
        // Ajout de joueurs par défaut
        plateau.getJoueurs().add(new Joueur("Alice", plateau));
        plateau.getJoueurs().add(new Joueur("Bob", plateau));
        plateau.getJoueurs().add(new Joueur("Charlie", plateau));
        joueurHumain = new Joueur(nomJoueur, plateau);
        plateau.getJoueurs().add(joueurHumain);
        
        // Panel principal avec le plateau
        JPanel mainPanel = createPlateauPanel();
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de droite avec les infos
        JPanel rightPanel = createInfoPanel();
        add(rightPanel, BorderLayout.EAST);
        
        // Panel du bas avec les contrôles
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        
        // Panel du haut avec les joueurs
        JPanel topPanel = createJoueursPanel();
        add(topPanel, BorderLayout.NORTH);
        
        log("🎮 Bienvenue " + joueurHumain.getNom() + " !");
        log("Vous jouez avec Alice, Bob et Charlie.");
        log("Lorsque c'est votre tour, vous pourrez choisir d'acheter ou non les propriétés.\n");
        
        updateDisplay();
        setVisible(true);
    }
    
    private JPanel createPlateauPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Plateau de Jeu"));
        
        plateauArea = new JTextArea();
        plateauArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        plateauArea.setEditable(false);
        plateauArea.setBackground(new Color(240, 255, 240));
        
        JScrollPane scrollPane = new JScrollPane(plateauArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Légende
        JPanel legendePanel = createLegendePanel();
        panel.add(legendePanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createLegendePanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Légende"));
        panel.setBackground(new Color(255, 255, 220));
        
        String[] legendes = {
            "[ n ]", "Case numéro n",
            "[D]", "Départ",
            "[P]", "Prison",
            "[C]", "Chance/Caisse",
            "[G]", "Gare (achetable)",
            "[R]", "Rue constructible (achetable)",
            "©", "Propriété avec propriétaire",
            "♦", "Maison(s) construite(s)",
            "♦♦", "Hôtel construit",
            "→J", "Joueur présent sur la case"
        };
        
        for (int i = 0; i < legendes.length; i += 2) {
            JLabel symbole = new JLabel(legendes[i]);
            symbole.setFont(new Font("Courier New", Font.BOLD, 12));
            symbole.setForeground(new Color(0, 100, 0));
            
            JLabel description = new JLabel(legendes[i + 1]);
            description.setFont(new Font("Arial", Font.PLAIN, 11));
            
            panel.add(symbole);
            panel.add(description);
        }
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Journal d'événements"));
        panel.setPreferredSize(new Dimension(350, 0));
        
        logArea = new JTextArea();
        logArea.setFont(new Font("Arial", Font.PLAIN, 12));
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        lblJoueurActuel = new JLabel("Tour de : " + plateau.getJoueurs().get(0).getNom());
        lblJoueurActuel.setFont(new Font("Arial", Font.BOLD, 14));
        lblJoueurActuel.setForeground(new Color(0, 0, 150));
        
        btnTourJoueur = new JButton("▶ Tour du joueur actuel");
        btnTourJoueur.setFont(new Font("Arial", Font.BOLD, 12));
        btnTourJoueur.addActionListener(e -> executerTourJoueur());
        
        btnTourComplet = new JButton("▶▶ Tour complet (tous les joueurs)");
        btnTourComplet.setFont(new Font("Arial", Font.PLAIN, 12));
        btnTourComplet.addActionListener(e -> executerTourComplet());
        
        btnReset = new JButton("↻ Nouvelle partie");
        btnReset.setFont(new Font("Arial", Font.PLAIN, 12));
        btnReset.addActionListener(e -> resetJeu());
        
        panel.add(lblJoueurActuel);
        panel.add(btnTourJoueur);
        panel.add(btnTourComplet);
        panel.add(btnReset);
        
        return panel;
    }
    
    private JPanel createJoueursPanel() {
        panelJoueurs = new JPanel(new GridLayout(1, 0, 10, 0));
        panelJoueurs.setBorder(BorderFactory.createTitledBorder("Joueurs"));
        updateJoueursPanel();
        return panelJoueurs;
    }
    
    private void updateJoueursPanel() {
        panelJoueurs.removeAll();
        
        for (Joueur j : plateau.getJoueurs()) {
            JPanel joueurCard = new JPanel();
            joueurCard.setLayout(new BoxLayout(joueurCard, BoxLayout.Y_AXIS));
            joueurCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            
            // Mettre en surbrillance le joueur humain
            if (j == joueurHumain) {
                joueurCard.setBackground(new Color(173, 216, 230)); // Bleu clair
            } else {
                joueurCard.setBackground(Color.WHITE);
            }
            
            JLabel nom = new JLabel(j.getNom() + (j == joueurHumain ? " (VOUS)" : ""));
            nom.setFont(new Font("Arial", Font.BOLD, 14));
            nom.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel fortune = new JLabel("Fortune : " + j.getFortune() + " €");
            fortune.setFont(new Font("Arial", Font.PLAIN, 12));
            fortune.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel position = new JLabel("Case : " + j.getPosition());
            position.setFont(new Font("Arial", Font.PLAIN, 11));
            position.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            JLabel gares = new JLabel("Gares : " + j.nbGaresPossedees());
            gares.setFont(new Font("Arial", Font.PLAIN, 11));
            gares.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            joueurCard.add(Box.createVerticalStrut(5));
            joueurCard.add(nom);
            joueurCard.add(Box.createVerticalStrut(5));
            joueurCard.add(fortune);
            joueurCard.add(position);
            joueurCard.add(gares);
            joueurCard.add(Box.createVerticalStrut(5));
            
            panelJoueurs.add(joueurCard);
        }
        
        panelJoueurs.revalidate();
        panelJoueurs.repaint();
    }
    
    private void executerTourJoueur() {
        if (plateau.findePartie()) {
            afficherFinPartie();
            return;
        }
        
        if (plateau.getJoueurs().isEmpty()) {
            log("Aucun joueur dans la partie !");
            return;
        }
        
        Joueur joueur = plateau.getJoueurs().get(joueurActuelIndex % plateau.getJoueurs().size());
        
        log("\n═══════════════════════════════════");
        log("🎲 Tour de " + joueur.getNom());
        log("═══════════════════════════════════");
        
        try {
            if (joueur == joueurHumain) {
                // Tour interactif pour le joueur humain
                executerTourInteractif(joueur);
            } else {
                // Tour automatique pour l'IA
                executerTourIA(joueur);
            }
            
        } catch (NoMoreMoney e) {
            log("❌ " + joueur.getNom() + " est éliminé : " + e.getMessage());
            plateau.getJoueurs().remove(joueur);
            
            if (joueur == joueurHumain) {
                JOptionPane.showMessageDialog(
                    this,
                    "Vous êtes ruiné ! Vous avez perdu la partie.",
                    "Game Over",
                    JOptionPane.ERROR_MESSAGE
                );
            }
            
            if (joueurActuelIndex >= plateau.getJoueurs().size() && !plateau.getJoueurs().isEmpty()) {
                joueurActuelIndex = 0;
            }
        }
        
        joueurActuelIndex = (joueurActuelIndex + 1) % Math.max(1, plateau.getJoueurs().size());
        
        updateDisplay();
        
        if (plateau.findePartie()) {
            afficherFinPartie();
        }
    }
    
    private void executerTourInteractif(Joueur joueur) throws NoMoreMoney {
        int de = Joueur.lanceLeDe();
        log("🎲 Vous avez lancé le dé : " + de);
        
        int posAvant = joueur.getPosition();
        int posApres = plateau.avance(posAvant, de);
        joueur.setPosition(posApres);
        
        Case caseActuelle = plateau.caseAt(posApres);
        log("📍 Position : " + posAvant + " → " + posApres);
        log("📌 Case : " + caseActuelle.getNom());
        
        if (caseActuelle instanceof Achetable) {
            Achetable prop = (Achetable) caseActuelle;
            
            if (de % 2 != 0 && prop.estLibre()) {
                // Proposer l'achat
                if (joueur.getFortune() >= prop.getPrix()) {
                    int choix = JOptionPane.showConfirmDialog(
                        this,
                        "La propriété " + prop.getNom() + " est disponible !\n" +
                        "Prix : " + prop.getPrix() + " €\n" +
                        "Votre fortune : " + joueur.getFortune() + " €\n\n" +
                        "Voulez-vous l'acheter ?",
                        "Opportunité d'achat",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (choix == JOptionPane.YES_OPTION) {
                        prop.acheter(joueur);
                        log("✅ Vous avez acheté " + prop.getNom() + " pour " + prop.getPrix() + " €");
                    } else {
                        log("⏭️ Vous avez refusé d'acheter " + prop.getNom());
                    }
                } else {
                    log("❌ Vous n'avez pas assez d'argent pour acheter " + prop.getNom() + " (" + prop.getPrix() + " €)");
                }
            } else if (!prop.estLibre() && prop.getProprietaire() != joueur) {
                // Payer le loyer
                int loyer = prop.calculLoyer(plateau, joueur);
                log("💸 Vous devez payer un loyer de " + loyer + " € à " + prop.getProprietaire().getNom());
                
                JOptionPane.showMessageDialog(
                    this,
                    "Vous êtes sur la propriété de " + prop.getProprietaire().getNom() + " !\n" +
                    "Loyer à payer : " + loyer + " €",
                    "Paiement de loyer",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                joueur.payer(prop.getProprietaire(), loyer);
                log("✔️ Loyer payé : " + loyer + " € à " + prop.getProprietaire().getNom());
            } else if (prop.getProprietaire() == joueur) {
                log("🏠 Cette propriété vous appartient déjà !");
            }
        }
        
        updateDisplay();
    }
    
    private void executerTourIA(Joueur joueur) throws NoMoreMoney {
        int posAvant = joueur.getPosition();
        joueur.tourDeJeu();
        int posApres = joueur.getPosition();
        
        Case caseActuelle = plateau.caseAt(posApres);
        log("📍 Position : " + posAvant + " → " + posApres);
        log("📌 Case : " + caseActuelle.getNom());
    }
    
    private void executerTourComplet() {
        if (plateau.findePartie()) {
            afficherFinPartie();
            return;
        }
        
        log("\n╔══════════════════════════════════════╗");
        log("║     TOUR COMPLET - TOUS LES JOUEURS   ║");
        log("╚══════════════════════════════════════╝\n");
        
        int nbJoueurs = plateau.getJoueurs().size();
        for (int i = 0; i < nbJoueurs && !plateau.findePartie(); i++) {
            executerTourJoueur();
            try {
                Thread.sleep(500); // Petite pause pour suivre l'action
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void resetJeu() {
        plateau = new Plateau();
        plateau.initPlateau();
        
        // Demander le nom du joueur humain
        String nomJoueur = JOptionPane.showInputDialog(
            this,
            "Entrez votre nom de joueur :",
            "Nouvelle partie",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (nomJoueur == null || nomJoueur.trim().isEmpty()) {
            nomJoueur = "Vous";
        }
        
        // Demander le nombre de joueurs IA
        String[] options = {"1", "2", "3"};
        String choix = (String) JOptionPane.showInputDialog(
            this,
            "Nombre de joueurs IA :",
            "Nouvelle partie",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            "2"
        );
        
        int nbIA = choix != null ? Integer.parseInt(choix) : 2;
        
        String[] noms = {"Alice", "Bob", "Charlie"};
        for (int i = 0; i < nbIA; i++) {
            plateau.getJoueurs().add(new Joueur(noms[i], plateau));
        }
        
        joueurHumain = new Joueur(nomJoueur, plateau);
        plateau.getJoueurs().add(joueurHumain);
        
        joueurActuelIndex = 0;
        logArea.setText("");
        log("🎮 Nouvelle partie démarrée avec " + (nbIA + 1) + " joueurs !");
        log("Vous jouez contre " + nbIA + " adversaire(s) IA.");
        log("Bonne chance " + joueurHumain.getNom() + " !\n");
        
        updateDisplay();
    }
    
    private void afficherFinPartie() {
        log("\n╔══════════════════════════════════════╗");
        log("║          FIN DE LA PARTIE !           ║");
        log("╚══════════════════════════════════════╝");
        
        if (plateau.getJoueurs().size() == 1) {
            Joueur gagnant = plateau.getJoueurs().get(0);
            log("\n🏆 VAINQUEUR : " + gagnant.getNom());
            log("💰 Fortune finale : " + gagnant.getFortune() + " €");
            
            String message;
            if (gagnant == joueurHumain) {
                message = "🎉 FÉLICITATIONS ! VOUS AVEZ GAGNÉ ! 🎉\n" +
                         "Fortune finale : " + gagnant.getFortune() + " €";
            } else {
                message = "😢 " + gagnant.getNom() + " remporte la partie.\n" +
                         "Fortune finale : " + gagnant.getFortune() + " €\n\n" +
                         "Meilleure chance la prochaine fois !";
            }
            
            JOptionPane.showMessageDialog(
                this,
                message,
                "Fin de partie",
                JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            log("\n⚠️ Partie terminée sans vainqueur clair");
        }
        
        btnTourJoueur.setEnabled(false);
        btnTourComplet.setEnabled(false);
    }
    
    private void updateDisplay() {
        // Mise à jour du plateau textuel
        plateauArea.setText(genererPlateauTextuel());
        
        // Mise à jour du panel des joueurs
        updateJoueursPanel();
        
        // Mise à jour du label joueur actuel
        if (!plateau.getJoueurs().isEmpty()) {
            Joueur joueur = plateau.getJoueurs().get(joueurActuelIndex % plateau.getJoueurs().size());
            lblJoueurActuel.setText("Tour de : " + joueur.getNom() + (joueur == joueurHumain ? " (VOUS)" : ""));
        }
        
        // Activer/désactiver les boutons
        boolean partieEnCours = !plateau.findePartie();
        btnTourJoueur.setEnabled(partieEnCours);
        btnTourComplet.setEnabled(partieEnCours);
    }
    
    private String genererPlateauTextuel() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔════════════════════════════════════════════════════════════════════════════════╗\n");
        sb.append("║                          PLATEAU DE JEU MONOPOLY                               ║\n");
        sb.append("╚════════════════════════════════════════════════════════════════════════════════╝\n\n");
        
        // Affichage case par case avec formatage
        for (int i = 0; i < plateau.getCases().size(); i++) {
            Case c = plateau.caseAt(i);
            sb.append(String.format("[%2d] ", i));
            
            // Symbole selon le type de case
            if (c instanceof CaseSpeciale) {
                CaseSpeciale cs = (CaseSpeciale) c;
                if (cs.getType().equals("DEPART") || cs.getType().equals("Depart")) {
                    sb.append("[D] ");
                } else if (cs.getType().equals("PRISON") || cs.getType().equals("Prison")) {
                    sb.append("[P] ");
                } else {
                    sb.append("[C] ");
                }
            } else if (c instanceof Gare) {
                sb.append("[G] ");
            } else if (c instanceof Constructible) {
                sb.append("[R] ");
            } else {
                sb.append("[ ] ");
            }
            
            // Nom de la case
            sb.append(String.format("%-30s", c.getNom()));
            
            // Informations supplémentaires pour les cases achetables
            if (c instanceof Achetable) {
                Achetable a = (Achetable) c;
                sb.append(String.format(" | %6d€ ", a.getPrix()));
                
                if (!a.estLibre()) {
                    sb.append("© " + String.format("%-10s", a.getProprietaire().getNom()));
                    
                    if (c instanceof Constructible) {
                        Constructible con = (Constructible) c;
                        if (con.getNbMaisons() > 0) {
                            sb.append(" | ");
                            for (int m = 0; m < con.getNbMaisons(); m++) {
                                sb.append("♦");
                            }
                        }
                        if (con.getNbHotels() > 0) {
                            sb.append(" | ♦♦(hôtel)");
                        }
                    }
                } else {
                    sb.append("   (libre)      ");
                }
            }
            
            // Afficher les joueurs présents sur cette case
            StringBuilder joueursSurCase = new StringBuilder();
            for (Joueur j : plateau.getJoueurs()) {
                if (j.getPosition() == i) {
                    if (joueursSurCase.length() > 0) {
                        joueursSurCase.append(", ");
                    }
                    joueursSurCase.append(j.getNom());
                }
            }
            
            if (joueursSurCase.length() > 0) {
                sb.append(" → " + joueursSurCase.toString());
            }
            
            sb.append("\n");
            
            // Séparateur tous les 10 cases
            if ((i + 1) % 10 == 0 && i < plateau.getCases().size() - 1) {
                sb.append("--------------------------------------------------------------------------------\n");
            }
        }
        
        return sb.toString();
    }
    
    private void log(String message) {
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Main();
        });
    }
}