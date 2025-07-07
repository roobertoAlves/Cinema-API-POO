package View;

import Model.MovieGender;
import Model.MovieGenderDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieGenderManagerScreen extends JFrame 
{
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTable genreTable;
    private DefaultTableModel tableModel;

    private MovieGenderDAO genreDAO = new MovieGenderDAO();
    private int selectedGenreId = 0;

    public MovieGenderManagerScreen(JFrame previousScreen) 
    {
        setTitle("Gerenciar Gêneros - CinePlay");
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

        JLabel title = new JLabel("🎭 Gerenciamento de Gêneros");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds(420, 20, 400, 40);
        add(title);

        nameField = createField("Nome do Gênero:", 50, 90);
        createDescriptionArea("Descrição:", 400, 90);

        JButton addButton = createButton("Adicionar", 300, 220);
        JButton updateButton = createButton("Atualizar", 470, 220);
        JButton deleteButton = createButton("Excluir", 640, 220);
        JButton clearButton = createButton("Limpar", 810, 220);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        createTable();

        addButton.addActionListener(e -> addGenre());
        updateButton.addActionListener(e -> updateGenre());
        deleteButton.addActionListener(e -> deleteGenre());
        clearButton.addActionListener(e -> clearFields());

        loadGenres();
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
        field.setBounds(x, y + 25, 300, 35);
        field.setBackground(new Color(40, 40, 60));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        field.setCaretColor(Color.WHITE);
        add(field);

        return field;
    }

    private void createDescriptionArea(String label, int x, int y) 
    {
        JLabel areaLabel = new JLabel(label);
        areaLabel.setBounds(x, y, 200, 25);
        areaLabel.setForeground(Color.WHITE);
        areaLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(areaLabel);

        descriptionArea = new JTextArea();
        descriptionArea.setBackground(new Color(40, 40, 60));
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descriptionArea.setCaretColor(Color.WHITE);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBounds(x, y + 25, 600, 100);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        scrollPane.getViewport().setBackground(new Color(40, 40, 60));
        scrollPane.setBackground(new Color(40, 40, 60));
        
        scrollPane.getVerticalScrollBar().setBackground(new Color(40, 40, 60));
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() 
        {
            protected void configureScrollBarColors() 
            {
                this.thumbColor = new Color(130, 30, 200);
                this.trackColor = new Color(60, 60, 80);
            }
        });
        
        add(scrollPane);
    }

    private void createTable() 
    {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Nome", "Descrição"});

        genreTable = new JTable(tableModel);
        genreTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        genreTable.setBackground(new Color(40, 40, 60));
        genreTable.setForeground(Color.WHITE);
        genreTable.setSelectionBackground(new Color(130, 30, 200));
        genreTable.setSelectionForeground(Color.WHITE);
        genreTable.setGridColor(new Color(70, 70, 90));
        genreTable.setRowHeight(30);
        genreTable.getTableHeader().setBackground(new Color(70, 70, 90));
        genreTable.getTableHeader().setForeground(Color.WHITE);
        genreTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        genreTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        genreTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        genreTable.getColumnModel().getColumn(2).setPreferredWidth(500);

        JScrollPane scroll = new JScrollPane(genreTable);
        scroll.setBounds(50, 290, 1100, 450);
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

        genreTable.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int row = genreTable.getSelectedRow();
                if (row >= 0) 
                {
                    selectedGenreId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    descriptionArea.setText(tableModel.getValueAt(row, 2).toString());
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

    private void loadGenres() 
    {
        tableModel.setRowCount(0);
        try 
        {
            ResultSet rs = genreDAO.list("");
            if (rs != null) 
            {
                while (rs.next()) 
                {
                    tableModel.addRow(new Object[]
                    {
                        rs.getInt("id_genero"),
                        rs.getString("nomeGenero"),
                        rs.getString("descricaoGenero")
                    });
                }
                rs.close();
            }
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar gêneros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validateFields() 
    {
        if (nameField.getText().trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "O nome do gênero é obrigatório!");
            return false;
        }
        return true;
    }

    private void addGenre() 
    {
        if (!validateFields()) return;

        MovieGender genre = new MovieGender(
            nameField.getText(),
            descriptionArea.getText()
        );

        int result = genreDAO.insert(genre);
        if (result > 0) 
        {
            JOptionPane.showMessageDialog(this, "Gênero adicionado com sucesso!");
            clearFields();
            loadGenres();
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar gênero!");
        }
    }

    private void updateGenre() 
    {
        if (!validateFields() || selectedGenreId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione um gênero para atualizar.");
            return;
        }

        MovieGender genre = new MovieGender(
            selectedGenreId,
            nameField.getText(),
            descriptionArea.getText()
        );

        int result = genreDAO.update(genre);
        if (result > 0) 
        {
            JOptionPane.showMessageDialog(this, "Gênero atualizado com sucesso!");
            clearFields();
            loadGenres();
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar gênero!");
        }
    }

    private void deleteGenre() 
    {
        if (selectedGenreId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione um gênero para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir este gênero?", 
            "Confirmar exclusão", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) 
        {
            MovieGender genre = new MovieGender();
            genre.setIdGender(selectedGenreId);
            int result = genreDAO.delete(genre);
            
            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, "Gênero excluído com sucesso!");
                clearFields();
                loadGenres();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao excluir gênero!");
            }
        }
    }

    private void clearFields() 
    {
        selectedGenreId = 0;
        nameField.setText("");
        descriptionArea.setText("");
    }
}
