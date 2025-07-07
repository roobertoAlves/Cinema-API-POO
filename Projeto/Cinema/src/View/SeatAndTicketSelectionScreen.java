package View;

import Model.MovieSessionsDAO;
import Model.MovieTickets;
import Model.MovieTicketsDAO;
import Model.RoomsDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatAndTicketSelectionScreen extends JFrame 
{
    private int sessionId;
    private JFrame previousScreen;
    private List<String> selectedSeats = new ArrayList<>();
    private double selectedPrice = 20.00;
    private JLabel priceLabel, sessionInfoLabel, selectedSeatsLabel;
    private JComboBox<String> ticketTypeCombo;
    private JPanel seatPanel;

    private final MovieSessionsDAO sessionDAO = new MovieSessionsDAO();
    private final MovieTicketsDAO ticketDAO = new MovieTicketsDAO();
    private final RoomsDAO roomDAO = new RoomsDAO();

    public SeatAndTicketSelectionScreen(int sessionId) 
    {
        this(sessionId, null);
    }

    public SeatAndTicketSelectionScreen(int sessionId, JFrame previousScreen) 
    {
        this.sessionId = sessionId;
        this.previousScreen = previousScreen;

        if (sessionId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Sessão inválida.");
            dispose();
            return;
        }

        setTitle("Selecionar Assentos e Ingresso");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));

        JButton backButton = new JButton("← Voltar");
        backButton.setBounds(20, 20, 100, 30);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
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

        add(backButton);

        JLabel header = new JLabel("🎟️ Selecione seus assentos e tipo de ingresso");
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setForeground(new Color(160, 64, 255));
        header.setBounds(200, 20, 600, 30);
        add(header);

        sessionInfoLabel = new JLabel();
        sessionInfoLabel.setForeground(Color.WHITE);
        sessionInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sessionInfoLabel.setBounds(50, 60, 500, 70);
        add(sessionInfoLabel);

        ticketTypeCombo = new JComboBox<>(new String[]
        {
            "Comum - R$20", "3D - R$30", "IMAX - R$40", "VIP - R$50"
        });

        ticketTypeCombo.setBounds(350, 80, 200, 30);
        ticketTypeCombo.setBackground(new Color(40, 40, 60));
        ticketTypeCombo.setForeground(Color.WHITE);
        ticketTypeCombo.addActionListener(e -> updatePrice());
        add(ticketTypeCombo);

        priceLabel = new JLabel("Valor unitário: R$ 20.00");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLabel.setBounds(580, 80, 200, 30);
        add(priceLabel);

        selectedSeatsLabel = new JLabel("Assentos selecionados: Nenhum");
        selectedSeatsLabel.setForeground(Color.YELLOW);
        selectedSeatsLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        selectedSeatsLabel.setBounds(50, 120, 600, 30);
        add(selectedSeatsLabel);

        seatPanel = new JPanel(new GridLayout(5, 10, 8, 8));
        seatPanel.setBounds(100, 160, 700, 300);
        seatPanel.setBackground(new Color(18, 18, 30));
        add(seatPanel);

        renderSessionDetails();
        renderSeats();

        JButton nextButton = new JButton("Próximo");
        nextButton.setBounds(300, 500, 120, 40);
        nextButton.setFocusPainted(false);
        nextButton.setBackground(new Color(160, 64, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(nextButton);

        JButton clearButton = new JButton("Limpar");
        clearButton.setBounds(450, 500, 120, 40);
        clearButton.setFocusPainted(false);
        clearButton.setBackground(new Color(200, 70, 70));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(clearButton);

        nextButton.addActionListener(e -> {
            if (selectedSeats.isEmpty()) 
            {
                JOptionPane.showMessageDialog(this, "Por favor, selecione pelo menos um assento.");
                return;
            }
          
            double totalPrice = selectedSeats.size() * selectedPrice;
            new ProductSelectionScreen(totalPrice, this);
            setVisible(false);
        });

        clearButton.addActionListener(e -> {
            selectedSeats.clear();
            updateSelectedSeatsLabel();
            renderSeats(); 
        });

        setVisible(true);
    }

    private void renderSessionDetails() 
    {
        try 
        {
            ResultSet rs = sessionDAO.list("id_sessao = " + sessionId);
            
            if (rs.next()) 
            {
                int movieId = rs.getInt("filme_id");
                int roomId = rs.getInt("sala_id");
                
                String roomInfo = "Sala " + roomId;
                try 
                {
                    ResultSet roomRs = roomDAO.list("id_sala = " + roomId);

                    if (roomRs.next()) 
                    {
                        roomInfo = "Sala " + roomRs.getString("numeroSala");
                    }
                } 
                catch (SQLException e) 
                {
                    System.err.println("Erro ao buscar informações da sala: " + e.getMessage());
                }

                String info = "<html><div style='text-align:left;'>"
                        + "Filme ID: " + movieId + "<br>"
                        + "Sala: " + roomInfo + "<br>"
                        + "Horário: " + rs.getString("horarioInicio") + "<br>"
                        + "Duração: " + rs.getString("duracaoFilme") + "</div></html>";

                sessionInfoLabel.setText(info);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private int roomCapacity( int roomMaxCapacity ) 
    {
        try 
        {
            ResultSet sessionRs = sessionDAO.list("id_sessao = " + sessionId);
            
            if (sessionRs.next()) 
            {
                int roomId = sessionRs.getInt("sala_id");
                
                ResultSet roomRs = roomDAO.list("id_sala = " + roomId);

                if (roomRs.next()) 
                {
                    roomMaxCapacity = roomRs.getInt("capacidadeMaxima");
                }

                roomRs.close();
            }
            sessionRs.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return roomMaxCapacity;
    }
    private void renderSeats() 
    {
        seatPanel.removeAll();
        Set<String> occupiedSeats = getOccupiedSeats();
        
        int maxCapacity = roomCapacity(0);

        for (int i = 1; i <= maxCapacity; i++) 
        {
            String seatNum = String.valueOf(i);
            JButton seatBtn = new JButton(seatNum);
            seatBtn.setFocusPainted(false);
            seatBtn.setFont(new Font("SansSerif", Font.BOLD, 12));

            if (occupiedSeats.contains(seatNum)) 
            {
                seatBtn.setEnabled(false);
                seatBtn.setBackground(Color.DARK_GRAY);
                seatBtn.setForeground(Color.LIGHT_GRAY);
            } 
            else if (selectedSeats.contains(seatNum))
            {
                seatBtn.setBackground(new Color(255, 200, 50));
                seatBtn.setForeground(Color.BLACK);
                seatBtn.addActionListener(e -> toggleSeatSelection(seatNum));
            }
            else 
            {
                seatBtn.setBackground(new Color(100, 200, 100));
                seatBtn.setForeground(Color.BLACK);
                seatBtn.addActionListener(e -> toggleSeatSelection(seatNum));
            }

            seatPanel.add(seatBtn);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }
    
    private void toggleSeatSelection(String seatNum) 
    {
        if (selectedSeats.contains(seatNum)) 
        {
            selectedSeats.remove(seatNum);
        } 
        else 
        {
            selectedSeats.add(seatNum);
        }
        
        updateSelectedSeatsLabel();
        renderSeats();
    }
    
    private void updateSelectedSeatsLabel() 
    {
        if (selectedSeats.isEmpty()) 
        {
            selectedSeatsLabel.setText("Assentos selecionados: Nenhum");
        } 
        else 
        {
            double totalPrice = selectedSeats.size() * selectedPrice;
            selectedSeatsLabel.setText("Assentos selecionados: " + selectedSeats + 
                                     " | Total: R$ " + String.format("%.2f", totalPrice));
        }
    }

    private Set<String> getOccupiedSeats() 
    {
        Set<String> set = new HashSet<>();
        try 
        {
            ResultSet rs = ticketDAO.list("sessao_id = " + sessionId);

            while (rs.next()) 
            {
                set.add(String.valueOf(rs.getInt("poltrona")));
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return set;
    }

    public boolean purchaseTickets(int clientId) 
    {
        if (selectedSeats.isEmpty()) 
        {
            return false;
        }
        
        try 
        {
            String ticketType = ticketTypeCombo.getSelectedItem().toString().split(" - ")[0];

            for (String seatNum : selectedSeats) 
            {
                MovieTickets ticket = new MovieTickets();
                ticket.setSessionId(sessionId);
                ticket.setClientId(clientId);
                ticket.setSeatNumber(Integer.parseInt(seatNum));
                ticket.setPrice(selectedPrice);
                ticket.setTicketType(ticketType);
                
                int result = ticket.save();
                if (result <= 0) 
                {
                    JOptionPane.showMessageDialog(this, "Erro ao registrar ingresso para assento " + seatNum);
                    return false;
                }
            }
            
            updateRoomCapacity();
            return true;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    private void updateRoomCapacity() 
    {
        try 
        {
            ResultSet sessionRs = sessionDAO.list("id_sessao = " + sessionId);
            if (sessionRs.next()) 
            {
                int roomId = sessionRs.getInt("sala_id");
                
                ResultSet roomRs = roomDAO.list("id_sala = " + roomId);

                if (roomRs.next()) 
                {
                    int currentOccupied = roomRs.getInt("capacidadeAtual");
                    int maxCapacity = roomRs.getInt("capacidadeMaxima");

                    int newOccupied = currentOccupied + selectedSeats.size();
                    
                    if (newOccupied <= maxCapacity) 
                    {
                        roomDAO.updateCurrentCapacity(roomId, newOccupied);
                        System.out.println("Capacidade atualizada. Sala " + roomId + ": " + newOccupied + "/" + maxCapacity + 
                                         " (+" + selectedSeats.size() + " assentos)");
                    } 
                    else 
                    {
                        System.out.println("ERRO: Tentativa de exceder capacidade da sala " + roomId + "!");
                    }
                }
                roomRs.close();
            }
            sessionRs.close();
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public void finalizePurchase(int clientId, double totalAmount) 
    {
        try 
         {
            if (!purchaseTickets(clientId)) 
            {
                JOptionPane.showMessageDialog(this, "Erro ao registrar ingressos!");
                return;
            }

            String seatsText = selectedSeats.size() == 1 ? 
                "Assento: " + selectedSeats.get(0) : 
                "Assentos: " + String.join(", ", selectedSeats);

            JOptionPane.showMessageDialog(this, 
                "Compra finalizada com sucesso!\n" +
                seatsText + "\n" +
                "Quantidade: " + selectedSeats.size() + " ingresso(s)\n" +
                "Valor total: R$ " + String.format("%.2f", totalAmount),
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);

            dispose();

            if (previousScreen != null) 
            {
                previousScreen.setVisible(true);
            }

        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar compra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double getSelectedPrice() 
    {
        return selectedPrice;
    }

    private void updatePrice() 
    {
        String selected = ticketTypeCombo.getSelectedItem().toString();
        if (selected.contains("Comum")) selectedPrice = 20;
        else if (selected.contains("3D")) selectedPrice = 30;
        else if (selected.contains("IMAX")) selectedPrice = 40;
        else selectedPrice = 50;
        
        priceLabel.setText("Valor unitário: R$ " + String.format("%.2f", selectedPrice));
        updateSelectedSeatsLabel();
    }
}
