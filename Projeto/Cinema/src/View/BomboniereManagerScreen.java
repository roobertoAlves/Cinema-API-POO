package View;

import Model.BomboniereProducts;
import Model.BomboniereProductsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BomboniereManagerScreen extends JFrame 
{
    private JTextField nameField, priceField;
    private JComboBox<String> stockCombo;
    private JTable productTable;
    private DefaultTableModel tableModel;

    private BomboniereProductsDAO productDAO = new BomboniereProductsDAO();
    private int selectedProductId = 0;

    public BomboniereManagerScreen(JFrame previousScreen) 
    {
        setTitle("Gerenciar Bomboniere - CinePlay");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));

        JButton backButton = createButton("Voltar", 1070, 10);
        backButton.setSize(100, 30);

        backButton.addActionListener(e -> {
            if (previousScreen != null) 
            {
                previousScreen.setVisible(true);
            } 
            else 
            {
                new AdminHomeScreen();
            }
            dispose();
        });

        add(backButton);

        JLabel title = new JLabel("🍿 Gerenciamento de Bomboniere");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds(390, 20, 500, 40);
        add(title);

        nameField = createField("Nome do Produto:", 50, 90);
        priceField = createField("Preço (R$):", 350, 90);
        stockCombo = createStockCombo("Status do Estoque:", 650, 90);

        JButton addButton = createButton("Adicionar", 300, 180);
        JButton updateButton = createButton("Atualizar", 470, 180);
        JButton deleteButton = createButton("Excluir", 640, 180);
        JButton clearButton = createButton("Limpar", 810, 180);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        createTable();

        addButton.addActionListener(e -> addProduct());
        updateButton.addActionListener(e -> updateProduct());
        deleteButton.addActionListener(e -> deleteProduct());
        clearButton.addActionListener(e -> clearFields());

        loadProducts();
        setVisible(true);
    }

    private JTextField createField(String label, int x, int y) 
    {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setBounds(x, y, 200, 25);
        fieldLabel.setForeground(Color.WHITE);
        fieldLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(fieldLabel);

        JTextField field = new JTextField();
        field.setBounds(x, y + 25, 250, 35);
        field.setBackground(new Color(40, 40, 60));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        field.setCaretColor(Color.WHITE);
        add(field);

        return field;
    }

    private JComboBox<String> createStockCombo(String label, int x, int y) 
    {
        JLabel comboLabel = new JLabel(label);
        comboLabel.setBounds(x, y, 200, 25);
        comboLabel.setForeground(Color.WHITE);
        comboLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(comboLabel);

        JComboBox<String> combo = new JComboBox<>(new String[]{"disponivel", "indisponivel"});
        combo.setBounds(x, y + 25, 250, 35);
        combo.setBackground(new Color(40, 40, 60));
        combo.setForeground(Color.WHITE);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        combo.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        add(combo);

        return combo;
    }

    private void createTable() 
    {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]
        {
            "ID", "Nome", "Preço", "Estoque"
        });

        productTable = new JTable(tableModel);
        productTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        productTable.setBackground(new Color(40, 40, 60));
        productTable.setForeground(Color.WHITE);
        productTable.setSelectionBackground(new Color(130, 30, 200));
        productTable.setSelectionForeground(Color.WHITE);
        productTable.setGridColor(new Color(70, 70, 90));
        productTable.setRowHeight(30);
        productTable.getTableHeader().setBackground(new Color(70, 70, 90));
        productTable.getTableHeader().setForeground(Color.WHITE);
        productTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        productTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scroll = new JScrollPane(productTable);
        scroll.setBounds(50, 250, 1100, 450);
        scroll.getViewport().setBackground(new Color(40, 40, 60));
        scroll.setBackground(new Color(40, 40, 60));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        
        scroll.getVerticalScrollBar().setBackground(new Color(40, 40, 60));
        
        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() 
        {
            protected void configureScrollBarColors() 
            {
                this.thumbColor = new Color(130, 30, 200);
                this.trackColor = new Color(60, 60, 80);
            }

            protected JButton createDecreaseButton(int orientation) 
            {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(70, 70, 90));
                button.setBorder(null);
                return button;
            }

            protected JButton createIncreaseButton(int orientation) 
            {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(new Color(70, 70, 90));
                button.setBorder(null);
                return button;
            }
        });
        
        add(scroll);

        productTable.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int row = productTable.getSelectedRow();
                if (row >= 0) 
                {
                    selectedProductId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    String priceStr = tableModel.getValueAt(row, 2).toString().replace("R$ ", "");
                    priceField.setText(priceStr);
                    stockCombo.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                }
            }
        });
    }

    private JButton createButton(String text, int x, int y) 
    {
        JButton button = new JButton(text);
        button.setBounds(x, y, 150, 40);
        button.setBackground(new Color(130, 30, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e) 
            {
                button.setBackground(new Color(160, 64, 255));
            }

            public void mouseExited(MouseEvent e) 
            {
                button.setBackground(new Color(130, 30, 200));
            }
        });

        return button;
    }

    private void loadProducts() 
    {
        tableModel.setRowCount(0);
        try 
        {
            ResultSet rs = productDAO.list("");
            while (rs.next())
            {
                int id = rs.getInt("id_produto");
                String name = rs.getString("nome");
                double price = rs.getDouble("preco");
                String stock = rs.getString("estoqueDisponivel");

                tableModel.addRow(new Object[]{id, name, String.format("R$ %.2f", price), stock});
            }
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validateFields() 
    {
        if (nameField.getText().trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "O nome do produto é obrigatório!");
            return false;
        }
        
        try 
        {
            Double.parseDouble(priceField.getText().replace(",", "."));
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Preço inválido!");
            return false;
        }
        
        return true;
    }

    private void addProduct() 
    {
        if (!validateFields()) return;

        try 
        {
            BomboniereProducts product = new BomboniereProducts(
                nameField.getText(),
                Double.parseDouble(priceField.getText().replace(",", ".")),
                stockCombo.getSelectedItem().toString()
            );

            int result = productDAO.insert(product);
            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!");
                clearFields();
                loadProducts();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar produto!");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void updateProduct() 
    {
        if (!validateFields() || selectedProductId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar!");
            return;
        }

        try 
        {
            BomboniereProducts product = new BomboniereProducts(
                selectedProductId,
                nameField.getText(),
                Double.parseDouble(priceField.getText().replace(",", ".")),
                stockCombo.getSelectedItem().toString()
            );

            int result = productDAO.update(product);
            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");
                clearFields();
                loadProducts();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto!");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void deleteProduct() 
    {
        if (selectedProductId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este produto?", 
            "Confirmar exclusão", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) 
        {
            BomboniereProducts product = new BomboniereProducts();
            product.setIdProduct(selectedProductId);
            int result = productDAO.delete(product);

            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                clearFields();
                loadProducts();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao excluir produto!");
            }
        }
    }

    private void clearFields() 
    {
        selectedProductId = 0;
        nameField.setText("");
        priceField.setText("");
        if (stockCombo.getItemCount() > 0) stockCombo.setSelectedIndex(0);
    }
}
