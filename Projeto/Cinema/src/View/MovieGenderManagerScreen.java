package View;

import Model.MovieGender;
import Model.MovieGenderDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieGenderManagerScreen extends JFrame 
{

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JTable genreTable;
    private DefaultTableModel tableModel;

    private MovieGenderDAO genderDAO = new MovieGenderDAO();
    private MovieGender selectedGenre = new MovieGender();
    private int selectedGenreId = 0;
    public MovieGenderManagerScreen(JFrame previousScreen) 
    {
        setTitle("Gerenciar Gêneros - CinePlay");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/Assets/cineplay.png"));


        JButton backButton = createButton("Voltar", 670, 10);
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

        JLabel title = new JLabel("🎬 Gerenciar Gêneros");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds(250, 20, 400, 30);
        add(title);

        JLabel nameLabel = new JLabel("Nome do Gênero:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setBounds(50, 80, 150, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 80, 300, 25);
        nameField.setBackground(new Color(40, 40, 60));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(nameField);

        JLabel descLabel = new JLabel("Descrição:");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        descLabel.setBounds(50, 120, 150, 25);
        add(descLabel);

        descriptionArea = new JTextArea();
        descriptionArea.setBounds(200, 120, 300, 80);
        descriptionArea.setBackground(new Color(40, 40, 60));
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        add(descriptionArea);

        JButton addButton = createButton("Adicionar", 530, 80);
        JButton updateButton = createButton("Atualizar", 530, 120);
        JButton deleteButton = createButton("Excluir", 530, 160);
        JButton clearButton = createButton("Limpar", 530, 200);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Nome", "Descrição"});

        genreTable = new JTable(tableModel);
        genreTable.setBackground(new Color(40, 40, 60));
        genreTable.setForeground(Color.WHITE);
        genreTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        genreTable.setSelectionBackground(new Color(130, 30, 200));

        JScrollPane scroll = new JScrollPane(genreTable);
        scroll.setBounds(50, 320, 680, 200);
        add(scroll);


        addButton.addActionListener(e -> addGenre());
        updateButton.addActionListener(e -> updateGenre());
        deleteButton.addActionListener(e -> deleteGenre());
        clearButton.addActionListener(e -> clearFields());

        genreTable.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int row = genreTable.getSelectedRow();
                selectedGenreId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                nameField.setText(tableModel.getValueAt(row, 1).toString());
                descriptionArea.setText(tableModel.getValueAt(row, 2).toString());
            }
        });

        loadGenres();
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 120, 30);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(160, 64, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadGenres() {
        tableModel.setRowCount(0);
        try 
        {
            ResultSet rs = genderDAO.list("");

            while (rs.next()) 
            {
                tableModel.addRow(new Object[]
                {
                    rs.getInt("id_genero"),
                    rs.getString("nomeGenero"),
                    rs.getString("descricaoGenero")
                });
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private boolean validateFields() 
    {
        if (nameField.getText().trim().isEmpty() || descriptionArea.getText().trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return false;
        }
        return true;
    }

    private void addGenre() {
        if (!validateFields()) return;

        MovieGender genre = new MovieGender(
            nameField.getText().trim(),
            descriptionArea.getText().trim()
        );
        
        genderDAO.insert(genre);
        loadGenres();
        clearFields();
    }

    private void updateGenre() 
    {
        if (!validateFields() || selectedGenreId == 0) return;

        	selectedGenre.setIdGender(selectedGenreId);
        	selectedGenre.setGenderName(nameField.getText().trim());
        	selectedGenre.setGenderDescription(descriptionArea.getText().trim()
        		
        );
        	
        genderDAO.update(selectedGenre);
        loadGenres();
        clearFields();
    }

    private void deleteGenre() 
    {
        if (selectedGenreId == 0) return;

        selectedGenre.setIdGender(selectedGenreId);
        genderDAO.delete(selectedGenre);
        loadGenres();
        clearFields();
    }

    private void clearFields() 
    {
        selectedGenreId = 0;
        nameField.setText("");
        descriptionArea.setText("");
    }
}
