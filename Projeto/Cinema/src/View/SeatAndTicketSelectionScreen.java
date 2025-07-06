package View;

import Model.MovieSessionsDAO;
import Model.MovieTickets;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SeatAndTicketSelectionScreen extends JFrame {
    private int movieId;
    private int sessionId;
    private String selectedSeat = "";
    private double selectedPrice = 20.00;
    private JLabel priceLabel, sessionInfoLabel;
    private JComboBox<String> ticketTypeCombo;
    private JPanel seatPanel;

    private final MovieSessionsDAO sessionDAO = new MovieSessionsDAO();

    public SeatAndTicketSelectionScreen(int movieId) {
        this.movieId = movieId;
        this.sessionId = getFirstSessionIdByMovie(movieId);

        if (sessionId == 0) {
            JOptionPane.showMessageDialog(this, "Nenhuma sessão disponível para este filme.");
            dispose();
            return;
        }

        setTitle("Selecionar Assento e Ingresso");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));

        JLabel header = new JLabel("🎟️ Selecione seu tipo de ingresso e assento");
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setForeground(new Color(160, 64, 255));
        header.setBounds(200, 20, 600, 30);
        add(header);

        sessionInfoLabel = new JLabel();
        sessionInfoLabel.setForeground(Color.WHITE);
        sessionInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sessionInfoLabel.setBounds(50, 60, 500, 70);
        add(sessionInfoLabel);

        ticketTypeCombo = new JComboBox<>(new String[]{
            "Comum - R$20", "3D - R$30", "IMAX - R$40", "VIP - R$50"
        });
        ticketTypeCombo.setBounds(350, 80, 200, 30);
        ticketTypeCombo.setBackground(new Color(40, 40, 60));
        ticketTypeCombo.setForeground(Color.WHITE);
        ticketTypeCombo.addActionListener(e -> updatePrice());
        add(ticketTypeCombo);

        priceLabel = new JLabel("Valor: R$ 20.00");
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        priceLabel.setBounds(580, 80, 200, 30);
        add(priceLabel);

        seatPanel = new JPanel(new GridLayout(5, 10, 8, 8));
        seatPanel.setBounds(100, 160, 700, 300);
        seatPanel.setBackground(new Color(18, 18, 30));
        add(seatPanel);

        renderSessionDetails();
        renderSeats();

        JButton nextButton = new JButton("Próximo");
        nextButton.setBounds(380, 500, 120, 40);
        nextButton.setFocusPainted(false);
        nextButton.setBackground(new Color(160, 64, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(nextButton);

        nextButton.addActionListener(e -> {
            if (selectedSeat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um assento.");
                return;
            }
            // Aqui você pode registrar ou passar dados para próxima tela
            new ProductSelectionScreen();
            dispose();
        });

        setVisible(true);
    }

    private void renderSessionDetails() {
        try {
            ResultSet rs = sessionDAO.list("filme_id = " + movieId + " AND id_sessao = " + sessionId);
            if (rs.next()) {
                String info = "<html><div style='text-align:left;'>"
                        + "Filme ID: " + movieId + "<br>"
                        + "Sala: " + rs.getInt("sala_id") + "<br>"
                        + "Horário: " + rs.getString("horarioInicio") + "<br>"
                        + "Duração: " + rs.getString("duracaoFilme") + "</div></html>";
                sessionInfoLabel.setText(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void renderSeats() {
        seatPanel.removeAll();
        Set<String> occupiedSeats = getOccupiedSeats();

        for (int i = 1; i <= 50; i++) {
            String seatNum = String.valueOf(i);
            JButton seatBtn = new JButton(seatNum);
            seatBtn.setFocusPainted(false);
            seatBtn.setFont(new Font("SansSerif", Font.BOLD, 12));

            if (occupiedSeats.contains(seatNum)) {
                seatBtn.setEnabled(false);
                seatBtn.setBackground(Color.DARK_GRAY);
                seatBtn.setForeground(Color.LIGHT_GRAY);
            } else {
                seatBtn.setBackground(new Color(100, 200, 100));
                seatBtn.setForeground(Color.BLACK);
                seatBtn.addActionListener(e -> {
                    selectedSeat = seatNum;
                    highlightSelectedSeat(seatBtn);
                });
            }

            seatPanel.add(seatBtn);
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private Set<String> getOccupiedSeats() {
        Set<String> set = new HashSet<>();
        try {
            ResultSet rs = new MovieTickets().listAll();
            while (rs.next()) {
                if (rs.getInt("sessao_id") == sessionId) {
                    set.add(String.valueOf(rs.getInt("poltrona")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    private int getFirstSessionIdByMovie(int movieId) {
        try {
            ResultSet rs = sessionDAO.list("filme_id = " + movieId + " LIMIT 1");
            if (rs.next()) return rs.getInt("id_sessao");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void updatePrice() {
        String selected = ticketTypeCombo.getSelectedItem().toString();
        if (selected.contains("Comum")) selectedPrice = 20;
        else if (selected.contains("3D")) selectedPrice = 30;
        else if (selected.contains("IMAX")) selectedPrice = 40;
        else selectedPrice = 50;
        priceLabel.setText("Valor: R$ " + String.format("%.2f", selectedPrice));
    }

    private void highlightSelectedSeat(JButton selectedButton) {
        for (Component comp : seatPanel.getComponents()) {
            if (comp instanceof JButton btn && btn.isEnabled()) {
                btn.setBackground(new Color(100, 200, 100));
            }
        }
        selectedButton.setBackground(new Color(255, 200, 50));
    }
}
