package View;

import Model.MoviesDAO;
import Model.RoomsDAO;
import Model.MovieSessions;
import Model.MovieSessionsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MovieSessionManagerScreen extends JFrame {
    private JComboBox<String> movieCombo, roomCombo;
    private JFormattedTextField timeField, durationField;
    private JTable sessionTable;
    private DefaultTableModel tableModel;
    private MovieSessionsDAO sessionDAO = new MovieSessionsDAO();
    private MoviesDAO movieDAO = new MoviesDAO();
    private RoomsDAO roomDAO = new RoomsDAO();
    private int selectedSessionId = 0;

    public MovieSessionManagerScreen() {
        setTitle("Gerenciar Sessões - CinePlay");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));

        JButton backButton = createButton("Voltar", 870, 10);
        backButton.setSize(100, 30);
        backButton.addActionListener(e -> {
            new AdminHomeScreen();
            dispose();
        });
        add(backButton);

        JLabel title = new JLabel("🎥 Gerenciamento de Sessões");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds(280, 20, 500, 40);
        add(title);

        movieCombo = createCombo("Filme:", 50, 90);
        roomCombo = createCombo("Sala:", 50, 140);
        timeField = createField("Horário (aaaa-MM-dd HH:mm:ss):", 50, 190, "####-##-## ##:##:##");
        durationField = createField("Duração (HH:mm:ss):", 50, 240, "##:##:##");

        JButton addButton = createButton("Adicionar", 300, 300);
        JButton updateButton = createButton("Atualizar", 470, 300);
        JButton deleteButton = createButton("Excluir", 640, 300);
        add(addButton);
        add(updateButton);
        add(deleteButton);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "ID", "Filme", "Sala", "Horário", "Duração"
        });

        sessionTable = new JTable(tableModel);
        sessionTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sessionTable.setBackground(new Color(40, 40, 60));
        sessionTable.setForeground(Color.WHITE);
        sessionTable.setSelectionBackground(new Color(130, 30, 200));

        JScrollPane scroll = new JScrollPane(sessionTable);
        scroll.setBounds(50, 360, 880, 300);
        add(scroll);

        sessionTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = sessionTable.getSelectedRow();
                selectedSessionId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                timeField.setText(tableModel.getValueAt(row, 3).toString());
                durationField.setText(tableModel.getValueAt(row, 4).toString());
                movieCombo.setSelectedItem(getItemByName(movieCombo, tableModel.getValueAt(row, 1).toString()));
                roomCombo.setSelectedItem(getItemByName(roomCombo, tableModel.getValueAt(row, 2).toString()));
            }
        });

        addButton.addActionListener(e -> addSession());
        updateButton.addActionListener(e -> updateSession());
        deleteButton.addActionListener(e -> deleteSession());

        loadMovies();
        loadRooms();
        loadSessions("");
        setVisible(true);
    }

    private JFormattedTextField createField(String label, int x, int y, String pattern) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 230, 25);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lbl);

        try {
            MaskFormatter formatter = new MaskFormatter(pattern);
            formatter.setPlaceholderCharacter('_');
            JFormattedTextField field = new JFormattedTextField(formatter);
            field.setBounds(x + 250, y, 200, 25);
            field.setBackground(new Color(40, 40, 60));
            field.setForeground(Color.WHITE);
            field.setCaretColor(Color.WHITE);
            field.setFont(new Font("SansSerif", Font.PLAIN, 14));
            add(field);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    private JComboBox<String> createCombo(String label, int x, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 140, 25);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(lbl);

        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(x + 150, y, 300, 25);
        combo.setBackground(new Color(40, 40, 60));
        combo.setForeground(Color.WHITE);
        add(combo);

        return combo;
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

    private void loadMovies() {
        try {
            ResultSet rs = movieDAO.list("");
            while (rs.next()) {
                String item = rs.getInt("id_filme") + " - " + rs.getString("titulo");
                movieCombo.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadRooms() {
        try {
            ResultSet rs = roomDAO.list("");
            while (rs.next()) {
                String item = rs.getInt("id_sala") + " - Sala " + rs.getInt("numeroSala");
                roomCombo.addItem(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getRoomLabelById(int roomId) {
        try {
            ResultSet rs = roomDAO.list("id_sala = " + roomId);
            if (rs.next()) {
                return "Sala " + rs.getInt("numeroSala");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Sala " + roomId;
    }

    private void loadSessions(String where) {
        tableModel.setRowCount(0);
        try {
            ResultSet rs = sessionDAO.list(where);
            while (rs.next()) {
                int id = rs.getInt("id_sessao");
                String movieTitle = getMovieTitleById(rs.getInt("filme_id"));
                String roomLabel = getRoomLabelById(rs.getInt("sala_id"));
                String time = rs.getString("horarioInicio");
                String duration = rs.getString("duracaoFilme");

                tableModel.addRow(new Object[]{id, movieTitle, roomLabel, time, duration});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addSession() {
        if (movieCombo.getSelectedItem() == null || roomCombo.getSelectedItem() == null ||
                timeField.getText().contains("_") || durationField.getText().contains("_")) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente.");
            return;
        }

        int movieId = Integer.parseInt(movieCombo.getSelectedItem().toString().split(" - ")[0]);
        int roomId = Integer.parseInt(roomCombo.getSelectedItem().toString().split(" - ")[0]);
        String time = timeField.getText();
        String duration = durationField.getText();

        if (hasConflict(roomId, time)) {
            JOptionPane.showMessageDialog(this, "Já existe sessão nesta sala e horário.");
            return;
        }

        MovieSessions session = new MovieSessions(movieId, roomId, time, duration);
        sessionDAO.insert(session);
        JOptionPane.showMessageDialog(this, "Sessão adicionada com sucesso.");
        clearFields();
        loadSessions("");
    }

    private void updateSession() {
        if (selectedSessionId == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma sessão para atualizar.");
            return;
        }

        int movieId = Integer.parseInt(movieCombo.getSelectedItem().toString().split(" - ")[0]);
        int roomId = Integer.parseInt(roomCombo.getSelectedItem().toString().split(" - ")[0]);
        String time = timeField.getText();
        String duration = durationField.getText();

        MovieSessions session = new MovieSessions(selectedSessionId, movieId, roomId, time, duration);
        sessionDAO.update(session);
        JOptionPane.showMessageDialog(this, "Sessão atualizada com sucesso.");
        clearFields();
        loadSessions("");
    }

    private void deleteSession() {
        if (selectedSessionId == 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma sessão para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta sessão?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            MovieSessions session = new MovieSessions();
            session.setIdSession(selectedSessionId);
            sessionDAO.delete(session);
            JOptionPane.showMessageDialog(this, "Sessão excluída.");
            clearFields();
            loadSessions("");
        }
    }

    private void clearFields() {
        selectedSessionId = 0;
        timeField.setText("");
        durationField.setText("");
        movieCombo.setSelectedIndex(0);
        roomCombo.setSelectedIndex(0);
    }

    private String getMovieTitleById(int movieId) {
        try {
            ResultSet rs = movieDAO.list("id_filme = " + movieId);
            if (rs.next()) return rs.getString("titulo");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Desconhecido";
    }

    private boolean hasConflict(int roomId, String time) {
        try {
            ResultSet rs = sessionDAO.list("sala_id = " + roomId + " AND horarioInicio = '" + time + "'");
            return rs != null && rs.next();
        } catch (SQLException e) {
            return true;
        }
    }

    private String getItemByName(JComboBox<String> combo, String name) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).contains(name)) return combo.getItemAt(i);
        }
        return null;
    }
}
