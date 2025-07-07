package View;

import Model.BomboniereProducts;
import Model.BomboniereProductsDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductSelectionScreen extends JFrame {

    private JLabel totalLabel;
    private double ticketPrice = 0.0;
    private double productsPrice = 0.0;
    private double totalPrice = 0.0;
    private JFrame previousScreen;
    private Map<JCheckBox, Double> checkBoxes = new LinkedHashMap<>();

    public ProductSelectionScreen() {
        this(0.0, null);
    }

    public ProductSelectionScreen(double ticketPrice, JFrame previousScreen) {
        this.ticketPrice = ticketPrice;
        this.previousScreen = previousScreen;
        this.totalPrice = ticketPrice;
        setTitle("Selecionar Produtos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 30));

        // Painel superior com botão voltar e título
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(18, 18, 30));
        
        // Botão Voltar
        JButton backButton = new JButton("← Voltar");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        backButton.setBackground(new Color(70, 70, 90));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            if (previousScreen != null) {
                previousScreen.setVisible(true);
            }
            dispose();
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel label = new JLabel("🍿 Escolha seus produtos", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setForeground(new Color(160, 64, 255));
        topPanel.add(label, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        JPanel productPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        productPanel.setBackground(new Color(18, 18, 30));
        JScrollPane scroll = new JScrollPane(productPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        try {
            ResultSet rs = new BomboniereProductsDAO().list("estoqueDisponivel = 'disponivel'");
            while (rs.next()) {
                String name = rs.getString("nome");
                double price = rs.getDouble("preco");

                JCheckBox check = new JCheckBox(name + " - R$" + String.format("%.2f", price));
                check.setForeground(Color.WHITE);
                check.setBackground(new Color(18, 18, 30));
                check.setFont(new Font("SansSerif", Font.PLAIN, 14));
                checkBoxes.put(check, price);

                check.addActionListener(e -> updateTotal());
                productPanel.add(check);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos.");
            e.printStackTrace();
        }

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(18, 18, 30));

        // Label mostrando detalhes do total
        JPanel totalDetailsPanel = new JPanel(new GridLayout(3, 1));
        totalDetailsPanel.setBackground(new Color(18, 18, 30));
        
        JLabel ticketLabel = new JLabel("Ingresso: R$ " + String.format("%.2f", ticketPrice), SwingConstants.CENTER);
        ticketLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ticketLabel.setForeground(Color.LIGHT_GRAY);
        totalDetailsPanel.add(ticketLabel);
        
        JLabel productsLabel = new JLabel("Produtos: R$ 0,00", SwingConstants.CENTER);
        productsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        productsLabel.setForeground(Color.LIGHT_GRAY);
        totalDetailsPanel.add(productsLabel);

        totalLabel = new JLabel("Total: R$ " + String.format("%.2f", totalPrice), SwingConstants.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setForeground(Color.WHITE);
        totalDetailsPanel.add(totalLabel);
        
        bottomPanel.add(totalDetailsPanel, BorderLayout.NORTH);

        JButton finalizeButton = new JButton("Finalizar Compra");
        finalizeButton.setBackground(new Color(160, 64, 255));
        finalizeButton.setForeground(Color.WHITE);
        finalizeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        finalizeButton.setFocusPainted(false);
        finalizeButton.addActionListener(e -> {
            // Se a tela anterior for SeatAndTicketSelectionScreen, integrar a compra
            if (previousScreen instanceof SeatAndTicketSelectionScreen) {
                SeatAndTicketSelectionScreen seatScreen = (SeatAndTicketSelectionScreen) previousScreen;
                
                // Por simplicidade, vamos usar um ID de cliente padrão (1)
                // Em um sistema real, você obteria isso do login
                int clientId = 1;
                
                // Registrar compra de produtos (implementar conforme necessário)
                registerProductsPurchase(clientId);
                
                // Finalizar compra através da tela de assentos
                seatScreen.finalizePurchase(clientId, totalPrice);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Compra finalizada com total de R$ " + String.format("%.2f", totalPrice));
                dispose();
            }
        });

        bottomPanel.add(finalizeButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateTotal() {
        productsPrice = 0.0;
        for (Map.Entry<JCheckBox, Double> entry : checkBoxes.entrySet()) {
            if (entry.getKey().isSelected()) {
                productsPrice += entry.getValue();
            }
        }
        totalPrice = ticketPrice + productsPrice;
        
        // Atualizar os labels de detalhes
        Component[] components = ((JPanel)((JPanel)getContentPane().getComponent(2)).getComponent(0)).getComponents();
        if (components.length >= 3) {
            ((JLabel)components[1]).setText("Produtos: R$ " + String.format("%.2f", productsPrice));
            ((JLabel)components[2]).setText("Total: R$ " + String.format("%.2f", totalPrice));
        }
    }

    private void registerProductsPurchase(int clientId) {
        // Este método registraria a compra dos produtos selecionados
        // Implementar conforme a estrutura do seu DAO de Purchase/PurchaseItems
        try {
            for (Map.Entry<JCheckBox, Double> entry : checkBoxes.entrySet()) {
                if (entry.getKey().isSelected()) {
                    String productName = entry.getKey().getText().split(" - ")[0];
                    double productPrice = entry.getValue();
                    
                    // Aqui você registraria cada produto comprado
                    // Exemplo: purchaseDAO.insert(new Purchase(clientId, productName, productPrice));
                    System.out.println("Produto comprado: " + productName + " - R$ " + productPrice);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao registrar compra de produtos: " + e.getMessage());
        }
    }

    // Getter para o total de produtos
    public double getProductsPrice() {
        return productsPrice;
    }

    // Getter para o preço total
    public double getTotalPrice() {
        return totalPrice;
    }
}
