package View;

import Model.Rooms;
import Model.RoomsDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomManagerScreen extends JFrame 
{
    private JTextField numberField, maxCapacityField, currentCapacityField, seatsField;
    private JComboBox<String> statusCombo, typeCombo;
    private JTable roomTable;
    private DefaultTableModel tableModel;

    private RoomsDAO roomDAO = new RoomsDAO();
    private int selectedRoomId = 0;

    public RoomManagerScreen(JFrame previousScreen) 
    {
        setTitle("Gerenciar Salas - CinePlay");
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
            dispose();
        });

        add(backButton);

        JLabel title = new JLabel("🏢 Gerenciamento de Salas");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds(520, 20, 400, 40);
        add(title);

        numberField = createField("Número da Sala:", 50, 90);
        maxCapacityField = createField("Capacidade Máxima:", 350, 90);
        currentCapacityField = createField("Capacidade Atual:", 650, 90);

        seatsField = createField("Número de Poltronas:", 50, 150);
        
        statusCombo = createCombo("Status da Sala:", new String[]
        {
            "liberada", "limpeza", "manutenção", "em sessão", "interditada"
        }, 350, 150);

        typeCombo = createCombo("Tipo da Sala:", new String[]
        {
            "comum", "3d", "imax", "vip"
        }, 650, 150);

        JButton addButton = createButton("Adicionar", 300, 230);
        JButton updateButton = createButton("Atualizar", 470, 230);
        JButton deleteButton = createButton("Excluir", 640, 230);
        JButton clearButton = createButton("Limpar", 810, 230);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        createTable();

        addButton.addActionListener(e -> addRoom());
        updateButton.addActionListener(e -> updateRoom());
        deleteButton.addActionListener(e -> deleteRoom());
        clearButton.addActionListener(e -> clearFields());

        loadRooms();
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

    private JComboBox<String> createCombo(String label, String[] items, int x, int y) 
    {
        JLabel comboLabel = new JLabel(label);
        comboLabel.setBounds(x, y, 200, 25);
        comboLabel.setForeground(Color.WHITE);
        comboLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(comboLabel);

        JComboBox<String> combo = new JComboBox<>(items);
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
            "ID", "Número", "Cap. Máx.", "Cap. Atual", "Status", "Tipo", "Poltronas"
        });

        roomTable = new JTable(tableModel);
        roomTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roomTable.setBackground(new Color(40, 40, 60));
        roomTable.setForeground(Color.WHITE);
        roomTable.setSelectionBackground(new Color(130, 30, 200));
        roomTable.setSelectionForeground(Color.WHITE);
        roomTable.setGridColor(new Color(70, 70, 90));
        roomTable.setRowHeight(30);
        roomTable.getTableHeader().setBackground(new Color(70, 70, 90));
        roomTable.getTableHeader().setForeground(Color.WHITE);
        roomTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        roomTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        roomTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        roomTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        roomTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        roomTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        roomTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        roomTable.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scroll = new JScrollPane(roomTable);
        scroll.setBounds(50, 300, 1300, 500);
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

        roomTable.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int row = roomTable.getSelectedRow();
                if (row >= 0) 
                {
                    selectedRoomId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
                    numberField.setText(tableModel.getValueAt(row, 1).toString());
                    maxCapacityField.setText(tableModel.getValueAt(row, 2).toString());
                    currentCapacityField.setText(tableModel.getValueAt(row, 3).toString());
                    statusCombo.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                    typeCombo.setSelectedItem(tableModel.getValueAt(row, 5).toString());
                    seatsField.setText(tableModel.getValueAt(row, 6).toString());
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

    private void loadRooms() 
    {
        tableModel.setRowCount(0);
        try 
        {
            ResultSet rs = roomDAO.list("");

            while (rs.next()) 
            {
                Object[] row = 
                {
                    rs.getInt("id_sala"),
                    rs.getInt("numeroSala"),
                    rs.getInt("capacidadeMaxima"),
                    rs.getInt("capacidadeAtual"),
                    rs.getString("statusSala"),
                    rs.getString("tipoSala"),
                    rs.getInt("poltronas")
                };

                tableModel.addRow(row);
            }
            
            rs.close();
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar salas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean validateFields() 
    {
        try 
        {
            Integer.parseInt(numberField.getText().trim());
            Integer.parseInt(maxCapacityField.getText().trim());
            Integer.parseInt(currentCapacityField.getText().trim());
            Integer.parseInt(seatsField.getText().trim());
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Todos os campos numéricos devem conter valores válidos!");
            return false;
        }
        
        return true;
    }

    private void addRoom() 
    {
        if (!validateFields()) return;

        try 
        {
            Rooms room = new Rooms();
            room.setRoomNumber(Integer.parseInt(numberField.getText().trim()));
            room.setRoomMaxCapacity(Integer.parseInt(maxCapacityField.getText().trim()));
            room.setRoomCurrentCapacity(Integer.parseInt(currentCapacityField.getText().trim()));
            room.setRoomStatus(statusCombo.getSelectedItem().toString());
            room.setRoomType(typeCombo.getSelectedItem().toString());
            room.setSeats(Integer.parseInt(seatsField.getText().trim()));

            int result = room.save();

            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, "Sala adicionada com sucesso!");
                loadRooms();
                clearFields();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar sala.");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void updateRoom() 
    {
        if (selectedRoomId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione uma sala para atualizar.");
            return;
        }

        if (!validateFields()) return;

        try 
        {
            Rooms room = new Rooms();
            room.setRoomId(selectedRoomId);
            room.setRoomNumber(Integer.parseInt(numberField.getText().trim()));
            room.setRoomMaxCapacity(Integer.parseInt(maxCapacityField.getText().trim()));
            room.setRoomCurrentCapacity(Integer.parseInt(currentCapacityField.getText().trim()));
            room.setRoomStatus(statusCombo.getSelectedItem().toString());
            room.setRoomType(typeCombo.getSelectedItem().toString());
            room.setSeats(Integer.parseInt(seatsField.getText().trim()));

            int result = room.save();

            if (result > 0) 
            {
                JOptionPane.showMessageDialog(this, "Sala atualizada com sucesso!");
                loadRooms();
                clearFields();
                selectedRoomId = 0;
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar sala.");
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    private void deleteRoom() 
    {
        if (selectedRoomId == 0) 
        {
            JOptionPane.showMessageDialog(this, "Selecione uma sala para excluir.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir esta sala?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) 
        {
            try 
            {
                Rooms room = new Rooms();
                room.setRoomId(selectedRoomId);

                int result = roomDAO.delete(room);
                if (result > 0) 
                {
                    JOptionPane.showMessageDialog(this, "Sala excluída com sucesso!");
                    loadRooms();
                    clearFields();
                    selectedRoomId = 0;
                } 
                else 
                {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir sala.");
                }
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
            }
        }
    }

    private void clearFields() 
    {
        selectedRoomId = 0;
        numberField.setText("");
        maxCapacityField.setText("");
        currentCapacityField.setText("");
        seatsField.setText("");
        
        if (statusCombo.getItemCount() > 0) statusCombo.setSelectedIndex(0);
        if (typeCombo.getItemCount() > 0) typeCombo.setSelectedIndex(0);
    }
}
