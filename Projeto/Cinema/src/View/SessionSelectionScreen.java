package View;

import Model.MovieSessionsDAO;
import Model.RoomsDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionSelectionScreen extends JFrame 
{

    private MovieSessionsDAO sessionDAO = new MovieSessionsDAO();
    private RoomsDAO roomDAO = new RoomsDAO();

    public SessionSelectionScreen(int movieId, JFrame previousScreen) 
    {
        setTitle("Escolha uma Sessão");
        setSize(800, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 18, 30));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🕒 Selecione a Sessão", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(160, 64, 255));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel sessionPanel = new JPanel();
        sessionPanel.setLayout(new BoxLayout(sessionPanel, BoxLayout.Y_AXIS));
        sessionPanel.setBackground(new Color(18, 18, 30));
        sessionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JScrollPane scroll = new JScrollPane(sessionPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(new Color(18, 18, 30));
        add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(18, 18, 30));

        JButton backButton = new JButton("← Voltar");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(70, 70, 90));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        backButton.addActionListener(e -> {
            if (previousScreen != null) 
            {
                previousScreen.setVisible(true);
            }
            dispose();
        });

        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        try 
        {
            ResultSet rs = sessionDAO.list("filme_id = " + movieId);
            boolean hasSessions = false;

            while (rs.next()) 
            {
                hasSessions = true;
                int sessionId = rs.getInt("id_sessao");
                String time = rs.getString("horarioInicio");
                String duration = rs.getString("duracaoFilme");
                String sessionType = rs.getString("tipoSessao");
                double sessionPrice = rs.getDouble("precoIngresso");
                int roomId = rs.getInt("sala_id");
                
                String roomLabel = getRoomNumberById(roomId);
                String capacityInfo = getAvailableCapacity(roomId);

                JPanel sessionItemPanel = new JPanel(new BorderLayout());
                sessionItemPanel.setBackground(new Color(40, 40, 60));
                sessionItemPanel.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
                sessionItemPanel.setMaximumSize(new Dimension(750, 80));
                
                JLabel mainInfo = new JLabel("<html><div style='padding: 5px;'>" +
                    "<b>Sala " + roomLabel + " (" + sessionType.toUpperCase() + ")</b><br>" +
                    "Horário: " + time + " | Duração: " + duration +
                    "</div></html>");
                mainInfo.setForeground(Color.WHITE);
                mainInfo.setFont(new Font("SansSerif", Font.PLAIN, 14));
                
                JPanel rightPanel = new JPanel(new GridLayout(2, 1));
                rightPanel.setBackground(new Color(40, 40, 60));
                
                JLabel priceLabel = new JLabel("R$ " + String.format("%.2f", sessionPrice));
                priceLabel.setForeground(new Color(255, 215, 0)); // Dourado
                priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
                priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
                JLabel capacityLabel = new JLabel(capacityInfo);
                capacityLabel.setForeground(Color.LIGHT_GRAY);
                capacityLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                capacityLabel.setHorizontalAlignment(SwingConstants.CENTER);
                
                rightPanel.add(priceLabel);
                rightPanel.add(capacityLabel);
                
                sessionItemPanel.add(mainInfo, BorderLayout.CENTER);
                sessionItemPanel.add(rightPanel, BorderLayout.EAST);
                
                sessionItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                sessionItemPanel.addMouseListener(new java.awt.event.MouseAdapter() 
                {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) 
                    {
                        new SeatAndTicketSelectionScreen(sessionId, SessionSelectionScreen.this);
                        setVisible(false);
                    }
                    
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) 
                    {
                        sessionItemPanel.setBackground(new Color(60, 60, 80));
                        rightPanel.setBackground(new Color(60, 60, 80));
                    }
                    
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) 
                    {
                        sessionItemPanel.setBackground(new Color(40, 40, 60));
                        rightPanel.setBackground(new Color(40, 40, 60));
                    }
                });

                sessionPanel.add(sessionItemPanel);
                sessionPanel.add(Box.createVerticalStrut(10));
            }

            rs.close();

            if (!hasSessions) 
            {
                JLabel noSessions = new JLabel("⚠️ Nenhuma sessão disponível para este filme.", SwingConstants.CENTER);
                noSessions.setFont(new Font("SansSerif", Font.PLAIN, 18));
                noSessions.setForeground(Color.LIGHT_GRAY);
                sessionPanel.add(noSessions);
            }

        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar sessões: " + e.getMessage());
            e.printStackTrace();
        }

        setVisible(true);
    }

    private String getRoomNumberById(int roomId) 
    {
        try 
        {
            ResultSet rs = roomDAO.list("id_sala = " + roomId);
            String roomNumber = String.valueOf(roomId);

            if (rs.next()) 
            {
                roomNumber = rs.getString("numeroSala");
            }
            rs.close();
            return roomNumber;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return String.valueOf(roomId);
    }

    private String getAvailableCapacity(int roomId) 
    {
        try 
        {
            ResultSet rs = roomDAO.list("id_sala = " + roomId);
            if (rs.next()) 
            {
                int maxCapacity = rs.getInt("capacidadeMaxima");
                int currentCapacity = rs.getInt("capacidadeAtual");
                int availableSeats = maxCapacity - currentCapacity;
                
                if (currentCapacity < 0) 
                {
                    currentCapacity = 0; 
                }
                
                if (availableSeats < 0) 
                {
                    availableSeats = 0;
                }
                
                rs.close();
                return "Disponível: " + availableSeats + "/" + maxCapacity;
            }
            rs.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        return "Capacidade: N/A";
    }
}
