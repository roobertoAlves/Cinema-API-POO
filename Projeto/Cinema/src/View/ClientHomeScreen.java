package View;

import Model.MoviesDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHomeScreen extends JFrame 
{
    private MoviesDAO movieDAO = new MoviesDAO();
    public ClientHomeScreen() {
        this(null);
    }

    public ClientHomeScreen(JFrame previousScreen) 
    {
        setTitle("Escolha seu Filme");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 30));


        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(18, 18, 30));
        

        if (previousScreen != null) 
        {
            JButton backButton = new JButton("← Voltar");
            backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
            backButton.setBackground(new Color(70, 70, 90));
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);

            backButton.addActionListener(e -> {
                previousScreen.setVisible(true);
                dispose();
            });

            topPanel.add(backButton, BorderLayout.WEST);
        }

        JLabel titleLabel = new JLabel("🎬 Selecione um Filme", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(160, 64, 255));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        logoutButton.setBackground(new Color(220, 60, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) 
            {
                logoutButton.setBackground(new Color(255, 80, 80));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) 
            {
                logoutButton.setBackground(new Color(220, 60, 60));
            }
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente fazer logout?", 
                "Confirmar Logout", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) 
            {
                new LoginScreen();
                dispose();
            }
        });

        topPanel.add(logoutButton, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);

        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        moviePanel.setBackground(new Color(18, 18, 30));
        JScrollPane scroll = new JScrollPane(moviePanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        try 
        {
            ResultSet rs = movieDAO.list("");

            while (rs.next()) 
            {
                int movieId = rs.getInt("id_filme");
                String movieTitle = rs.getString("titulo");


                JPanel movieCard = createMovieCard(movieTitle, movieId);
                moviePanel.add(movieCard);
            }

            rs.close();

        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar filmes.");
            e.printStackTrace();
        }

        setVisible(true);
    }

    private JPanel createMovieCard(String movieTitle, int movieId) 
    {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(45, 45, 65));
        card.setPreferredSize(new Dimension(150, 180));
        card.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 90), 2));
        

        JLabel iconLabel = new JLabel("🎬", SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        iconLabel.setForeground(new Color(160, 64, 255));
        iconLabel.setOpaque(false);
        

        JLabel titleLabel = new JLabel("<html><center>" + movieTitle + "</center></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        
        card.add(iconLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);
        

        card.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) 
            {
                card.setBackground(new Color(60, 60, 85));
                card.setBorder(BorderFactory.createLineBorder(new Color(160, 64, 255), 2));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) 
            {
                card.setBackground(new Color(45, 45, 65));
                card.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 90), 2));
            }
            
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) 
            {
                new SessionSelectionScreen(movieId, ClientHomeScreen.this);
                setVisible(false);
            }
        });
        
        return card;
    }
}
