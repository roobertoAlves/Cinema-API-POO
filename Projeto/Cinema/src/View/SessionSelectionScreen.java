package View;

import Model.MovieSessionsDAO;
import Model.RoomsDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionSelectionScreen extends JFrame {
    private MovieSessionsDAO sessionDAO = new MovieSessionsDAO();
    private RoomsDAO roomDAO = new RoomsDAO();


    public SessionSelectionScreen(int movieId, JFrame previousScreen) 
    {
        setTitle("Escolha uma Sessão");
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(18, 18, 30));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🕒 Selecione a Sessão", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(160, 64, 255));
        add(title, BorderLayout.NORTH);

        JPanel sessionPanel = new JPanel();
        sessionPanel.setLayout(new BoxLayout(sessionPanel, BoxLayout.Y_AXIS));
        sessionPanel.setBackground(new Color(18, 18, 30));
        JScrollPane scroll = new JScrollPane(sessionPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(18, 18, 30));

        JButton backButton = new JButton("← Voltar");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(70, 70, 90));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

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
                int roomId = rs.getInt("sala_id");
                String roomLabel = getRoomNumberById(roomId);
                String capacityInfo = getAvailableCapacity(roomId);

                JButton btn = new JButton("Sala: " + roomLabel + " | Horário: " + time + " | Duração: " + duration + " | " + capacityInfo);
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                btn.setBackground(new Color(130, 30, 200));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                btn.setFont(new Font("SansSerif", Font.BOLD, 16));

                btn.addActionListener(e -> {
                    
                    new SeatAndTicketSelectionScreen(sessionId, this); 
                    setVisible(false);

                });

                sessionPanel.add(Box.createVerticalStrut(10));
                sessionPanel.add(btn);
            }
            
            rs.close();

            if (!hasSessions) 
            {
                JLabel noSessions = new JLabel("⚠️ Nenhuma sessão disponível para este filme.", SwingConstants.CENTER);
                noSessions.setFont(new Font("SansSerif", Font.PLAIN, 18));
                noSessions.setForeground(Color.LIGHT_GRAY);
                add(noSessions, BorderLayout.CENTER);
            }

        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar sessões.");
            e.printStackTrace();
        }

        setVisible(true);
    }

    private String getRoomNumberById(int roomId) 
    {
        try 
        {
            ResultSet rs = roomDAO.list("id_sala = " + roomId);
            if (rs.next()) 
            {
                String roomNumber = "Sala " + rs.getString("numeroSala");
                rs.close();
                return roomNumber;
            }
            rs.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return "Sala " + roomId;
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
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        return "Capacidade: N/A";
    }
}
