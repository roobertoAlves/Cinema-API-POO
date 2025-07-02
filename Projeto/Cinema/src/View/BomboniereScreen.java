package View;

import Model.BomboniereProducts;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BomboniereScreen extends JFrame {

    private JTextField txtNome, txtPreco, txtEstoque;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSalvar, btnEditar, btnExcluir, btnLimpar;

    private int idSelecionado = -1;

    public BomboniereScreen() {
        super("Produtos da Bomboniere");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));

        txtNome = new JTextField();
        txtPreco = new JTextField();
        txtEstoque = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("Preço:"));
        formPanel.add(txtPreco);
        formPanel.add(new JLabel("Estoque Disponível:"));
        formPanel.add(txtEstoque);

        // Botões
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnLimpar);

        // Tabela
        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "Preço", "Estoque"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Adicionar à janela
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Carrega dados
        carregarProdutos();

        // Listeners
        btnSalvar.addActionListener(e -> salvarProduto());
        btnEditar.addActionListener(e -> carregarSelecionado());
        btnExcluir.addActionListener(e -> excluirProduto());
        btnLimpar.addActionListener(e -> limparCampos());
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                carregarSelecionado();
            }
        });

        setVisible(true);
    }

    private void carregarProdutos() {
        tableModel.setRowCount(0);
        
        try {
            BomboniereProducts produto = new BomboniereProducts();
            ResultSet rs = produto.listAll();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("estoqueDisponivel")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro de conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void salvarProduto() {
        try {
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            String estoque = txtEstoque.getText();

            BomboniereProducts produto;
            if (idSelecionado == -1) {
                produto = new BomboniereProducts(nome, preco, estoque);
            } else {
                produto = new BomboniereProducts(idSelecionado, nome, preco, estoque);
            }

            int resultado = produto.save();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
                carregarProdutos();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar produto!");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void carregarSelecionado() {
        int row = table.getSelectedRow();
        if (row != -1) {
            idSelecionado = (int) table.getValueAt(row, 0);
            txtNome.setText(table.getValueAt(row, 1).toString());
            txtPreco.setText(table.getValueAt(row, 2).toString());
            txtEstoque.setText(table.getValueAt(row, 3).toString());
        }
    }

    private void excluirProduto() {
        if (idSelecionado != -1) {
            int confirmacao = JOptionPane.showConfirmDialog(
                this, 
                "Tem certeza que deseja excluir este produto?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                BomboniereProducts produto = new BomboniereProducts();
                produto.setIdProduct(idSelecionado);
                int resultado = produto.delete();
                
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                    carregarProdutos();
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir produto!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir!");
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtPreco.setText("");
        txtEstoque.setText("");
        idSelecionado = -1;
        table.clearSelection();
    }

    public static void main(String[] args) {
    
        
        SwingUtilities.invokeLater(() -> new BomboniereScreen());
    }
}
