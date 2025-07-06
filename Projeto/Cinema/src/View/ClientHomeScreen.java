package View;

import Model.MoviesDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHomeScreen extends JFrame {
    private MoviesDAO movieDAO = new MoviesDAO();

    public ClientHomeScreen() {
        setTitle("Escolha seu Filme");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 30));

        JLabel titleLabel = new JLabel("🎬 Selecione um Filme", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(160, 64, 255));
        add(titleLabel, BorderLayout.NORTH);

        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new GridLayout(0, 2, 20, 20));
        moviePanel.setBackground(new Color(18, 18, 30));
        JScrollPane scroll = new JScrollPane(moviePanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        try {
            ResultSet rs = movieDAO.list("");
            while (rs.next()) {
                int movieId = rs.getInt("id_filme");
                String movieTitle = rs.getString("titulo");

                JButton movieBtn = new JButton(movieTitle);
                movieBtn.setBackground(new Color(130, 30, 200));
                movieBtn.setForeground(Color.WHITE);
                movieBtn.setFocusPainted(false);
                movieBtn.setFont(new Font("SansSerif", Font.BOLD, 16));

                movieBtn.addActionListener(e -> 
                {
                    new SessionSelectionScreen(movieId);
                    dispose();
                });

                moviePanel.add(movieBtn);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar filmes.");
            e.printStackTrace();
        }

        setVisible(true);
    }
}
