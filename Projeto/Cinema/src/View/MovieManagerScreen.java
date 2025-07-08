package View;

import Model.Movies;
import Model.MoviesDAO;
import Model.MovieGenderDAO;
import Model.MovieSessions;
import Model.MovieSessionsDAO;
import Model.MovieTickets;
import Model.MovieTicketsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieManagerScreen extends JFrame 
{
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, ratingField, durationField, languageField, distributorField;
    private JComboBox<String> typeCombo, dubbingCombo, subtitleCombo, genreCombo;

    private MoviesDAO movieDAO = new MoviesDAO();
    private MovieGenderDAO genreDAO = new MovieGenderDAO();

    private int selectedMovieId = 0;

    public MovieManagerScreen(JFrame previousScreen) 
    {
        setTitle("Gerenciar Filmes - CinePlay");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/Assets/cineplay.png"));


        JButton backButton = createButton("Voltar", 870, 10);
        backButton.setSize(100, 30);

        backButton.addActionListener(e -> {
            if (previousScreen != null) 
            {
                previousScreen.setVisible(true);
            }
            
            dispose();
        });

        add(backButton);


        JLabel screenTitle = new JLabel("🎬 Gerenciador de Filmes");
        screenTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        screenTitle.setForeground(new Color(160, 64, 255));
        screenTitle.setBounds(300, 10, 500, 40);
        add(screenTitle);

        int y = 60;
        titleField = createField("Título", 50, y); y += 40;
        ratingField = createField("Classificação", 50, y); y += 40;
        durationField = createField("Duração", 50, y); y += 40;

        typeCombo = createCombo("Tipo", new String[]{"Nacional", "Estrangeiro"}, 50, y); y += 40;
        languageField = createField("Idioma", 50, y); y += 40;

        dubbingCombo = createCombo("Dublagem", new String[]{"Sim", "Não"}, 50, y); y += 40;
        subtitleCombo = createCombo("Legenda", new String[]{"Sim", "Não"}, 50, y); y += 40;
        distributorField = createField("Distribuidor", 50, y); y += 40;

        genreCombo = createGenreCombo("Gênero", 50, y); y += 40;

        JButton addButton = createButton("Adicionar", 300, 570);
        JButton updateButton = createButton("Atualizar", 420, 570);
        JButton deleteButton = createButton("Excluir", 540, 570);
        JButton clearButton = createButton("Limpar", 660, 570);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]
        {
            "ID", "Título", "Classificação", "Duração", "Tipo", "Idioma", "Dublagem", "Legenda", "Distribuidor"
        });

        movieTable = new JTable(tableModel);
        movieTable.setBackground(new Color(40, 40, 60));
        movieTable.setForeground(Color.WHITE);
        movieTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        movieTable.setSelectionBackground(new Color(130, 30, 200));

        JScrollPane scroll = new JScrollPane(movieTable);
        scroll.setBounds(50, 620, 880, 80);
        add(scroll);

        addButton.addActionListener(e -> addMovie());
        updateButton.addActionListener(e -> updateMovie());
        deleteButton.addActionListener(e -> deleteMovie());
        clearButton.addActionListener(e -> clearFields());

        movieTable.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int row = movieTable.getSelectedRow();
                selectedMovieId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                titleField.setText(tableModel.getValueAt(row, 1).toString());
                ratingField.setText(tableModel.getValueAt(row, 2).toString());
                durationField.setText(tableModel.getValueAt(row, 3).toString());
                typeCombo.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                languageField.setText(tableModel.getValueAt(row, 5).toString());
                dubbingCombo.setSelectedItem(tableModel.getValueAt(row, 6).toString());
                subtitleCombo.setSelectedItem(tableModel.getValueAt(row, 7).toString());
                distributorField.setText(tableModel.getValueAt(row, 8).toString());
            }
        });

        loadMovies();
        setVisible(true);
    }

    private JTextField createField(String label, int x, int y) 
    {
        JLabel lbl = new JLabel(label + ":");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(x, y, 120, 25);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lbl);

        JTextField txt = new JTextField();
        txt.setBounds(x + 130, y, 200, 25);
        txt.setBackground(new Color(40, 40, 60));
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(Color.WHITE);
        add(txt);
        return txt;
    }

    private JComboBox<String> createCombo(String label, String[] options, int x, int y) 
    {
        JLabel lbl = new JLabel(label + ":");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(x, y, 120, 25);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lbl);

        JComboBox<String> combo = new JComboBox<>(options);
        combo.setBounds(x + 130, y, 200, 25);
        combo.setBackground(new Color(40, 40, 60));
        combo.setForeground(Color.WHITE);
        add(combo);
        return combo;
    }

    private JComboBox<String> createGenreCombo(String label, int x, int y) 
    {
        JLabel lbl = new JLabel(label + ":");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(x, y, 120, 25);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lbl);

        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(x + 130, y, 200, 25);
        combo.setBackground(new Color(40, 40, 60));
        combo.setForeground(Color.WHITE);

        try 
        {
            ResultSet rs = genreDAO.list("");

            while (rs.next()) 
            {
                combo.addItem(rs.getString("nomeGenero"));
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        add(combo);
        return combo;
    }

    private JButton createButton(String text, int x, int y) 
    {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 100, 35);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(160, 64, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2, true));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadMovies() 
    {
        tableModel.setRowCount(0);
        try 
        {
            ResultSet rs = movieDAO.list("");

            while (rs.next()) 
            {
                tableModel.addRow(new Object[]{
                    rs.getInt("id_filme"),
                    rs.getString("titulo"),
                    rs.getString("classificacao"),
                    rs.getString("duracao"),
                    rs.getString("tipoFilme"),
                    rs.getString("idiomaFilme"),
                    rs.getString("dublagem"),
                    rs.getString("legenda"),
                    rs.getString("distribuidor")
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
        return !(titleField.getText().isEmpty() || ratingField.getText().isEmpty()
                || durationField.getText().isEmpty() || languageField.getText().isEmpty()
                || distributorField.getText().isEmpty());
    }

    private void addMovie() 
    {
        if (!validateFields()) 
        {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
            return;
        }

        Movies movie = new Movies(
            titleField.getText(), ratingField.getText(), durationField.getText(),
            typeCombo.getSelectedItem().toString(), languageField.getText(),
            dubbingCombo.getSelectedItem().toString(), subtitleCombo.getSelectedItem().toString(),
            distributorField.getText()
        );

        movieDAO.insert(movie);

        loadMovies();
        clearFields();
    }

    private void updateMovie() 
    {
        if (!validateFields() || selectedMovieId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione um filme para atualizar.");
            return;
        }

        Movies movie = new Movies(
            selectedMovieId, titleField.getText(), ratingField.getText(), durationField.getText(),
            typeCombo.getSelectedItem().toString(), languageField.getText(),
            dubbingCombo.getSelectedItem().toString(), subtitleCombo.getSelectedItem().toString(),
            distributorField.getText()
        );

        movieDAO.update(movie);
        loadMovies();
        clearFields();
    }

    private void deleteMovie() 
    {
        if (selectedMovieId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione um filme para excluir.");
            return;
        }

        try 
        {
            MovieSessionsDAO sessionDAO = new MovieSessionsDAO();
            ResultSet sessionsRs = sessionDAO.list("filme_id = " + selectedMovieId);
            
            java.util.List<Integer> sessionIds = new java.util.ArrayList<>();
            
            while (sessionsRs.next()) 
            {
                sessionIds.add(sessionsRs.getInt("id_sessao"));
            }
            sessionsRs.close();
            
            if (!sessionIds.isEmpty()) 
            {
                String message = "Este filme possui " + sessionIds.size() + " sessão(ões) registrada(s).\n" +
                               "Para excluir o filme, é necessário excluir as sessões primeiro.\n\n" +
                               "Deseja excluir TODAS as sessões e o filme?";
                
                int confirm = JOptionPane.showConfirmDialog(this, 
                    message, 
                    "Atenção - Filme com Sessões", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    MovieTicketsDAO ticketsDAO = new MovieTicketsDAO();
                    
                    for (Integer sessionId : sessionIds) 
                    {
                        ResultSet ticketsRs = ticketsDAO.list("sessao_id = " + sessionId);
                        
                        while (ticketsRs.next()) 
                        {
                            MovieTickets ticket = new MovieTickets();
                            ticket.setIdTicket(ticketsRs.getInt("id_ingresso"));
                            ticketsDAO.delete(ticket);
                        }
                        ticketsRs.close();
                        
                        MovieSessions session = new MovieSessions();
                        session.setIdSession(sessionId);
                        int deleteResult = sessionDAO.delete(session);
                        if (deleteResult <= 0) 
                        {
                            JOptionPane.showMessageDialog(this, 
                                "Erro ao excluir sessão ID: " + sessionId);
                            return;
                        }
                    }
                    
                    Movies movie = new Movies();
                    movie.setMovieId(selectedMovieId);
                    int result = movieDAO.delete(movie);
                    
                    if (result > 0) 
                    {
                        JOptionPane.showMessageDialog(this, 
                            "Filme e todas as sessões foram excluídos com sucesso.");
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(this, 
                            "Erro ao excluir o filme.");
                    }
                }
            } 
            else 
            {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Deseja realmente excluir este filme?", 
                    "Confirmar exclusão", 
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) 
                {
                    Movies movie = new Movies();
                    movie.setMovieId(selectedMovieId);
                    int result = movieDAO.delete(movie);

                    if (result > 0) 
                    {
                        JOptionPane.showMessageDialog(this, "Filme excluído com sucesso!");
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir filme!");
                    }
                }
            }
            
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, 
                "Erro ao verificar sessões do filme: " + e.getMessage(),
                "Erro de Banco de Dados",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        clearFields();
        loadMovies();
    }

    private void clearFields() 
    {
        selectedMovieId = 0;
        titleField.setText("");
        ratingField.setText("");
        durationField.setText("");
        languageField.setText("");
        distributorField.setText("");
        if (typeCombo.getItemCount() > 0) typeCombo.setSelectedIndex(0);
        if (dubbingCombo.getItemCount() > 0) dubbingCombo.setSelectedIndex(0);
        if (subtitleCombo.getItemCount() > 0) subtitleCombo.setSelectedIndex(0);
        if (genreCombo.getItemCount() > 0) genreCombo.setSelectedIndex(0);
    }
}
