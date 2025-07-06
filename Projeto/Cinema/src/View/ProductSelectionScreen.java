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
    private double totalPrice = 0.0;
    private Map<JCheckBox, Double> checkBoxes = new LinkedHashMap<>();

    public ProductSelectionScreen() {
        setTitle("Selecionar Produtos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 30));

        JLabel label = new JLabel("🍿 Escolha seus produtos", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setForeground(new Color(160, 64, 255));
        add(label, BorderLayout.NORTH);

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

        totalLabel = new JLabel("Total: R$ 0,00", SwingConstants.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setForeground(Color.WHITE);
        bottomPanel.add(totalLabel, BorderLayout.NORTH);

        JButton finalizeButton = new JButton("Finalizar Compra");
        finalizeButton.setBackground(new Color(160, 64, 255));
        finalizeButton.setForeground(Color.WHITE);
        finalizeButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        finalizeButton.setFocusPainted(false);
        finalizeButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Compra finalizada com total de R$ " + String.format("%.2f", totalPrice));
            dispose();
        });

        bottomPanel.add(finalizeButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void updateTotal() {
        totalPrice = 0.0;
        for (Map.Entry<JCheckBox, Double> entry : checkBoxes.entrySet()) {
            if (entry.getKey().isSelected()) {
                totalPrice += entry.getValue();
            }
        }
        totalLabel.setText("Total: R$ " + String.format("%.2f", totalPrice));
    }
}
