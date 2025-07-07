package View;

import Model.BomboniereProducts;
import Model.BomboniereProductsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BomboniereManagerScreen extends JFrame {
    private JTextField nameField, priceField, stockField;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private BomboniereProductsDAO productDAO = new BomboniereProductsDAO();
    private int selectedProductId = 0;
    private JFrame previousScreen;

    public BomboniereManagerScreen() {
        this(null);
    }

    public BomboniereManagerScreen(JFrame previousScreen) {
        this.previousScreen = previousScreen;
        setTitle("Gerenciar Bomboniere - CinePlay");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));

        // Botão Voltar no canto superior direito
        JButton backButton = createButton("Voltar", 870, 10);
        backButton.setSize(100, 30);
        backButton.addActionListener(e -> {
            if (previousScreen != null) {
                previousScreen.setVisible(true);
            } else {
                new AdminHomeScreen();
            }
            dispose();
        });
        add(backButton);

        // Título
        JLabel title = new JLabel("🍿 Gerenciamento de Bomboniere");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds(280, 20, 500, 40);
        add(title);

        // Campos de entrada
        nameField = createField("Nome do Produto:", 50, 90);
        priceField = createField("Preço (R$):", 50, 140);
        stockField = createField("Status do Estoque:", 50, 190);

        // Botões de ação
        JButton addButton = createButton("Adicionar", 300, 250);
        JButton updateButton = createButton("Atualizar", 470, 250);
        JButton deleteButton = createButton("Excluir", 640, 250);
        JButton clearButton = createButton("Limpar", 810, 250);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        // Tabela
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "ID", "Nome", "Preço", "Estoque"
        });

        productTable = new JTable(tableModel);
        productTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        productTable.setBackground(new Color(40, 40, 60));
        productTable.setForeground(Color.WHITE);
        productTable.setSelectionBackground(new Color(130, 30, 200));
        productTable.getTableHeader().setBackground(new Color(70, 70, 90));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(productTable);
        scroll.setBounds(50, 310, 880, 350);
        scroll.getViewport().setBackground(new Color(40, 40, 60));
        add(scroll);

        // Event listeners
        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = productTable.getSelectedRow();
                if (row >= 0) {
                    selectedProductId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    priceField.setText(tableModel.getValueAt(row, 2).toString());
                    stockField.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });

        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        clearButton.addActionListener(e -> clearFields());

        loadProducts();
        setVisible(true);
    }

    private JTextField createField(String label, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 200, 25);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lbl);

        JTextField field = new JTextField();
        field.setBounds(x + 220, y, 250, 25);
        field.setBackground(new Color(40, 40, 60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 90), 1));
        add(field);

        return field;
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 150, 35);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(160, 64, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(180, 90, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(160, 64, 255));
            }
        });

        return btn;
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        try {
            ResultSet rs = productDAO.list("");
            while (rs.next()) {
                int id = rs.getInt("id_produto");
                String name = rs.getString("nome");
                double price = rs.getDouble("preco");
                String stock = rs.getString("estoqueDisponivel");

                tableModel.addRow(new Object[]{id, name, String.format("R$ %.2f", price), stock});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addProduct() {
        if (nameField.getText().trim().isEmpty() || priceField.getText().trim().isEmpty() || 
            stockField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        try {
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            String stock = stockField.getText().trim();

            BomboniereProducts product = new BomboniereProducts(name, price, stock);
            int result = productDAO.insert(product);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!");
                clearFields();
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar produto!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void updateProduct() {
        if (selectedProductId == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar!");
            return;
        }

        try {
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            String stock = stockField.getText().trim();

            BomboniereProducts product = new BomboniereProducts(selectedProductId, name, price, stock);
            int result = productDAO.update(product);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                clearFields();
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        if (selectedProductId == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este produto?", 
            "Confirmar exclusão", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            BomboniereProducts product = new BomboniereProducts();
            product.setIdProduct(selectedProductId);
            int result = productDAO.delete(product);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                clearFields();
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir produto!");
            }
        }
    }

    private void clearFields() {
        selectedProductId = 0;
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
        productTable.clearSelection();
    }
}
