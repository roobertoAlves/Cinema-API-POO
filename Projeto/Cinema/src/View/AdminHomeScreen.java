package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminHomeScreen extends JFrame {

    public AdminHomeScreen() {
        setTitle("Painel do Administrador - CineRoxo");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(18, 18, 30));

        JLabel titleLabel = new JLabel("🎬 Painel do Administrador");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(new Color(160, 64, 255));
        titleLabel.setBounds(200, 30, 500, 40);
        mainPanel.add(titleLabel);

        // Painel de botões em grid
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        buttonPanel.setBounds(180, 100, 440, 240);
        buttonPanel.setBackground(new Color(18, 18, 30));

        JButton btnMovies = createButton("Gerenciar Filmes");
        JButton btnConcession = createButton("Gerenciar Bomboniere");
        JButton btnSessions = createButton("Sessões e Poltronas");
        JButton btnGenres = createButton("Gerenciar Gêneros");

        buttonPanel.add(btnMovies);
        buttonPanel.add(btnConcession);
        buttonPanel.add(btnSessions);
        buttonPanel.add(btnGenres);

        JButton btnLogout = createButton("Sair");
        btnLogout.setBounds(280, 400, 240, 40);

        btnMovies.addActionListener((ActionEvent e) -> {
            new MovieManagerScreen(this);
            setVisible(false);
        });

        btnGenres.addActionListener((ActionEvent e) -> {
            new MovieGenderManagerScreen(this);
            setVisible(false);
        });

        btnConcession.addActionListener(e -> {
            new BomboniereManagerScreen(this);
            setVisible(false);
        });
        
        btnSessions.addActionListener(e -> { 
			new MovieSessionManagerScreen(this);
			setVisible(false);
		});
        
        btnLogout.addActionListener(e -> {
            new LoginScreen();
            dispose();
        });

        mainPanel.add(buttonPanel);
        mainPanel.add(btnLogout);
        add(mainPanel);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(160, 64, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2, true));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}
