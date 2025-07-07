package View;

import Model.MoviesDAO;
import Model.RoomsDAO;
import Model.MovieSessions;
import Model.MovieSessionsDAO;
import Model.MovieTickets;
import Model.MovieTicketsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MovieSessionManagerScreen extends JFrame 
{
    private JComboBox<String> movieCombo, roomCombo;
    private JFormattedTextField dateField, timeField;
    private JTextField durationField;
    private JTextField priceField;
    private JTextField sessionTypeField;
    private JTable sessionTable;
    private DefaultTableModel tableModel;

    private MovieSessionsDAO sessionDAO = new MovieSessionsDAO();
    private MoviesDAO movieDAO = new MoviesDAO();
    private RoomsDAO roomDAO = new RoomsDAO();

    private int selectedSessionId = 0;

    public MovieSessionManagerScreen(JFrame previousScreen) 
    {
        setTitle("Gerenciar Sessões - CinePlay");
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 30));

        JButton backButton = createButton("Voltar", 1270, 10);
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

        JLabel title = new JLabel("🎥 Gerenciamento de Sessões");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds(480, 20, 500, 40);
        add(title);

        movieCombo = createCombo("Filme:", 50, 80);
        roomCombo = createCombo("Sala:", 350, 80);

        dateField = createDateField("Data (DD/MM/AAAA):", 650, 80);
        timeField = createTimeField("Horário (HH:MM:SS):", 950, 80);

        durationField = createField("Duração (HH:MM:SS):", 50, 140);
        priceField = createField("Preço do Ingresso (R$):", 350, 140);
        sessionTypeField = createField("Tipo da Sessão:", 650, 140);

        JButton addButton = createButton("Adicionar", 200, 300);
        JButton updateButton = createButton("Atualizar", 370, 300);
        JButton deleteButton = createButton("Excluir", 540, 300);
        JButton clearButton = createButton("Limpar", 710, 300);
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        createTable();

        addButton.addActionListener(e -> addSession());
        updateButton.addActionListener(e -> updateSession());
        deleteButton.addActionListener(e -> deleteSession());
        clearButton.addActionListener(e -> clearFields());

        loadMovies();
        loadRooms();
        loadSessions("");
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

    private JComboBox<String> createCombo(String label, int x, int y) 
    {
        JLabel comboLabel = new JLabel(label);
        comboLabel.setBounds(x, y, 200, 25);
        comboLabel.setForeground(Color.WHITE);
        comboLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(comboLabel);

        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(x, y + 25, 250, 35);
        combo.setBackground(new Color(40, 40, 60));
        combo.setForeground(Color.WHITE);
        combo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        combo.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        add(combo);

        return combo;
    }

    private JFormattedTextField createDateField(String label, int x, int y) 
    {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setBounds(x, y, 200, 25);
        fieldLabel.setForeground(Color.WHITE);
        fieldLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(fieldLabel);

        JFormattedTextField field = null;
        try 
        {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.setPlaceholderCharacter('_');
            field = new JFormattedTextField(dateMask);
        } 
        catch (ParseException e) 
        {
            field = new JFormattedTextField();
        }

        field.setBounds(x, y + 25, 250, 35);
        field.setBackground(new Color(40, 40, 60));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        field.setCaretColor(Color.WHITE);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        field.setText(sdf.format(new java.util.Date()));
        
        add(field);
        return field;
    }

    private JFormattedTextField createTimeField(String label, int x, int y) 
    {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setBounds(x, y, 200, 25);
        fieldLabel.setForeground(Color.WHITE);
        fieldLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(fieldLabel);

        JFormattedTextField field = null;
        try 
        {
            MaskFormatter timeMask = new MaskFormatter("##:##:##");
            timeMask.setPlaceholderCharacter('_');
            field = new JFormattedTextField(timeMask);
        } 
        catch (ParseException e) 
        {
            field = new JFormattedTextField();
        }

        field.setBounds(x, y + 25, 250, 35);
        field.setBackground(new Color(40, 40, 60));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2));
        field.setCaretColor(Color.WHITE);
        field.setText("20:00:00");
        
        add(field);
        return field;
    }

    private void createTable() 
    {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]
        {
            "ID", "Filme", "Sala", "Data", "Horário", "Duração", "Tipo", "Preço"
        });

        sessionTable = new JTable(tableModel);
        sessionTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sessionTable.setBackground(new Color(40, 40, 60));
        sessionTable.setForeground(Color.WHITE);
        sessionTable.setSelectionBackground(new Color(130, 30, 200));
        sessionTable.setSelectionForeground(Color.WHITE);
        sessionTable.setGridColor(new Color(70, 70, 90));
        sessionTable.setRowHeight(30);
        sessionTable.getTableHeader().setBackground(new Color(70, 70, 90));
        sessionTable.getTableHeader().setForeground(Color.WHITE);
        sessionTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        sessionTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        sessionTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        sessionTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        sessionTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        sessionTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        sessionTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        sessionTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        sessionTable.getColumnModel().getColumn(7).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(sessionTable);
        scroll.setBounds(50, 360, 1300, 450);
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

        sessionTable.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int row = sessionTable.getSelectedRow();
                if (row >= 0) 
                {
                    selectedSessionId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    
                    String movieInfo = tableModel.getValueAt(row, 1).toString();
                    setComboSelection(movieCombo, movieInfo);
                    
                    String roomInfo = tableModel.getValueAt(row, 2).toString();
                    setComboSelection(roomCombo, roomInfo);
                    
                    dateField.setText(tableModel.getValueAt(row, 3).toString());
                    timeField.setText(tableModel.getValueAt(row, 4).toString());
                    durationField.setText(tableModel.getValueAt(row, 5).toString());
                    sessionTypeField.setText(tableModel.getValueAt(row, 6).toString());
                    
                    String priceText = tableModel.getValueAt(row, 7).toString();
                    priceText = priceText.replace("R$ ", "");
                    priceField.setText(priceText);
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

    private void loadMovies() 
    {
        try 
        {
            movieCombo.removeAllItems();
            movieCombo.addItem("Selecione um filme...");
            
            ResultSet rs = movieDAO.list("");
            if (rs != null) 
            {
                while (rs.next()) 
                {
                    String movieInfo = rs.getInt("id_filme") + " - " + rs.getString("titulo");
                    movieCombo.addItem(movieInfo);
                }
                rs.close();
            }
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar filmes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadRooms() 
    {
        try 
        {
            roomCombo.removeAllItems();
            roomCombo.addItem("Selecione uma sala...");
            
            ResultSet rs = roomDAO.list("");
            if (rs != null) 
            {
                while (rs.next()) 
                {
                    String roomInfo = rs.getInt("id_sala") + " - Sala " + 
                                    rs.getInt("numeroSala") + " (" + 
                                    rs.getString("tipoSala").toUpperCase() + " - " + 
                                    rs.getInt("capacidadeMaxima") + " lugares)";
                    roomCombo.addItem(roomInfo);
                }
                rs.close();
            }
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar salas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadSessions(String filter) 
    {
        tableModel.setRowCount(0);
        try 
        {
            ResultSet rs = sessionDAO.list(filter);

            if (rs != null) 
            {
                while (rs.next()) 
                {
                    String movieTitle = getMovieTitleById(rs.getInt("filme_id"));
                    String roomInfo = "Sala " + getRoomNumberById(rs.getInt("sala_id"));
                    String datetime = rs.getString("horarioInicio");
                    String[] dateTimeParts = datetime.split(" ");
                    String datePart = dateTimeParts[0];
                    String timePart = dateTimeParts[1];
                    
                    tableModel.addRow(new Object[]
                    {
                        rs.getInt("id_sessao"),
                        movieTitle,
                        roomInfo,
                        datePart,
                        timePart,
                        rs.getString("duracaoFilme"),
                        rs.getString("tipoSessao"),
                        "R$ " + String.format("%.2f", rs.getDouble("precoIngresso"))
                    });
                }
                rs.close();
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    private String formatDateTime() 
    {
        String date = dateField.getText().trim();
        String time = timeField.getText().trim();
        
        if (date.equals("__/__/____") || time.equals("__:__:__")) 
        {
            return "";
        }
        
        try 
        {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateObj = inputFormat.parse(date);
            String formattedDate = outputFormat.format(dateObj);
            
            return formattedDate + " " + time;
        } 
        catch (Exception e) 
        {
            return "";
        }
    }

    private void addSession() 
    {
        if (movieCombo.getSelectedItem() == null || 
            movieCombo.getSelectedIndex() == 0 ||
            roomCombo.getSelectedItem() == null ||
            roomCombo.getSelectedIndex() == 0) 
        {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um filme e uma sala.");
            return;
        }

        String duration = durationField.getText().trim();
        if (!duration.matches("^([0-9]{2}):([0-5][0-9]):([0-5][0-9])$")) 
        {
            JOptionPane.showMessageDialog(this, "Formato de duração inválido. Use HH:MM:SS");
            return;
        }

        String datetime = formatDateTime();
        if (datetime.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Data ou horário inválidos.");
            return;
        }

        try 
        {
            String movieText = movieCombo.getSelectedItem().toString();
            int movieId = Integer.parseInt(movieText.split(" - ")[0]);
            
            String roomText = roomCombo.getSelectedItem().toString();
            int roomId = Integer.parseInt(roomText.split(" - ")[0]);
            
            if (hasConflict(roomId, datetime)) 
            {
                JOptionPane.showMessageDialog(this, "Já existe uma sessão nesta sala no horário especificado!");
                return;
            }
            
            double price = Double.parseDouble(priceField.getText().replace(",", "."));
            String sessionType = sessionTypeField.getText().trim();
            
            if (sessionType.isEmpty()) 
            {
                sessionType = "comum";
            }

            MovieSessions session = new MovieSessions(
                movieId, roomId, datetime, duration, sessionType, price
            );

            int result = sessionDAO.insert(session);
            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, 
                    "Sessão adicionada com sucesso!\n" +
                    "Filme: " + getMovieTitleById(movieId) + "\n" +
                    "Sala: " + getRoomNumberById(roomId) + "\n" +
                    "Data/Hora: " + datetime + "\n" +
                    "Duração: " + duration + "\n" +
                    "Tipo: " + sessionType.toUpperCase() + "\n" +
                    "Preço: R$ " + String.format("%.2f", price));
                clearFields();
                loadSessions("");
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar sessão!");
            }
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro nos dados numéricos. Verifique os campos.");
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar sessão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateSession() 
    {
        if (selectedSessionId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione uma sessão para atualizar.");
            return;
        }

        if (movieCombo.getSelectedItem() == null || 
            movieCombo.getSelectedIndex() == 0 ||
            roomCombo.getSelectedItem() == null ||
            roomCombo.getSelectedIndex() == 0) 
        {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um filme e uma sala.");
            return;
        }

        String duration = durationField.getText().trim();
        if (!duration.matches("^([0-9]{2}):([0-5][0-9]):([0-5][0-9])$")) 
        {
            JOptionPane.showMessageDialog(this, "Formato de duração inválido. Use HH:MM:SS");
            return;
        }

        String datetime = formatDateTime();
        if (datetime.isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Data ou horário inválidos.");
            return;
        }

        try 
        {
            String movieText = movieCombo.getSelectedItem().toString();
            int movieId = Integer.parseInt(movieText.split(" - ")[0]);
            
            String roomText = roomCombo.getSelectedItem().toString();
            int roomId = Integer.parseInt(roomText.split(" - ")[0]);
            
            double price = Double.parseDouble(priceField.getText().replace(",", "."));
            String sessionType = sessionTypeField.getText().trim();
            
            if (sessionType.isEmpty()) 
            {
                sessionType = "comum";
            }

            MovieSessions session = new MovieSessions(
                selectedSessionId, movieId, roomId, datetime, duration, sessionType, price
            );

            int result = sessionDAO.update(session);
            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, "Sessão atualizada com sucesso!");
                clearFields();
                loadSessions("");
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar sessão!");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar sessão: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void deleteSession() 
    {
        if (selectedSessionId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione uma sessão para excluir.");
            return;
        }

        try 
        {
            MovieTicketsDAO ticketsDAO = new MovieTicketsDAO();
            ResultSet ticketsRs = ticketsDAO.list("sessao_id = " + selectedSessionId);
            
            java.util.List<Integer> ticketIds = new java.util.ArrayList<>();
            
            while (ticketsRs.next()) 
            {
                ticketIds.add(ticketsRs.getInt("id_ingresso"));
            }
            
            if (!ticketIds.isEmpty()) 
            {
                String message = "Esta sessão possui " + ticketIds.size() + " ingresso(s) vendido(s).\n" +
                               "Para excluir a sessão, é necessário excluir os ingressos primeiro.\n\n" +
                               "Deseja excluir TODOS os ingressos e a sessão?";
                
                int confirm = JOptionPane.showConfirmDialog(this, 
                    message, 
                    "Atenção - Ingressos Vendidos", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    for (Integer ticketId : ticketIds) 
                    {
                        MovieTickets ticket = new MovieTickets();
                        ticket.setIdTicket(ticketId);
                        ticketsDAO.delete(ticket);
                    }
                    
                    MovieSessions session = new MovieSessions();
                    session.setIdSession(selectedSessionId);
                    int result = sessionDAO.delete(session);
                    
                    if (result > 0) 
                    {
                        JOptionPane.showMessageDialog(this, 
                            "Sessão e todos os ingressos foram excluídos com sucesso.");
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(this, 
                            "Erro ao excluir a sessão.");
                    }
                }
            } 
            else 
            {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Deseja realmente excluir esta sessão?", 
                    "Confirmar exclusão", 
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) 
                {
                    MovieSessions session = new MovieSessions();
                    session.setIdSession(selectedSessionId);
                    int result = sessionDAO.delete(session);
                    
                    if (result > 0) 
                    {
                        JOptionPane.showMessageDialog(this, "Sessão excluída com sucesso.");
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir a sessão.");
                    }
                }
            }
            
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, 
                "Erro ao verificar ingressos: " + e.getMessage(),
                "Erro de Banco de Dados",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        clearFields();
        loadSessions("");
    }

    private void clearFields() 
    {
        selectedSessionId = 0;
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateField.setText(sdf.format(new java.util.Date()));
        
        timeField.setText("20:00:00");
        durationField.setText("01:30:00");
        priceField.setText("20.00");
        sessionTypeField.setText("");
        
        if (movieCombo.getItemCount() > 0) movieCombo.setSelectedIndex(0);
        if (roomCombo.getItemCount() > 0) roomCombo.setSelectedIndex(0);
    }

    private String getMovieTitleById(int movieId) 
    {
        try 
        {
            ResultSet rs = movieDAO.list("id_filme = " + movieId);
            String title = "Filme " + movieId;

            if (rs.next()) 
            {
                title = rs.getString("titulo");
            }
            rs.close();
            return title;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return "Filme " + movieId;
    }

    private String getRoomNumberById(int roomId) 
    {
        try 
        {
            ResultSet rs = roomDAO.list("id_sala = " + roomId);
            String roomNumber = String.valueOf(roomId);

            if (rs.next()) 
            {
                roomNumber = rs.getString("numeroSala");
            }
            rs.close();
            return roomNumber;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        return String.valueOf(roomId);
    }

    private boolean hasConflict(int roomId, String datetime) 
    {
        try 
        {
            String filter = "sala_id = " + roomId + " AND horarioInicio = '" + datetime + "'";
            ResultSet rs = sessionDAO.list(filter);
            
            boolean hasConflict = rs.next();
            rs.close();
            return hasConflict;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    private void setComboSelection(JComboBox<String> combo, String searchText) 
    {
        for (int i = 0; i < combo.getItemCount(); i++) 
        {
            String item = combo.getItemAt(i);
            if (item.contains(searchText)) 
            {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
}
