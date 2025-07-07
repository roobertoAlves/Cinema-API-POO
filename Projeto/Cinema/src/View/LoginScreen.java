package View;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame 
{
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginScreen() 
    {
        setTitle("Login - Sistema de Cinema");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/Assets/cineplay.png"));

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(18, 18, 30));

        int formWidth = 350;
        int formHeight = 300;
        int startX = (800 - formWidth) / 2;
        int startY = (600 - formHeight) / 2;

        JLabel title = new JLabel("🎬 CinePlay");
        title.setFont(new Font("SansSerif", Font.BOLD, 48));
        title.setForeground(new Color(160, 64, 255));
        title.setBounds((800 - 300) / 2, startY - 120, 300, 60);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setBounds(startX, startY, 100, 30);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel);

        userField = new JTextField();
        userField.setBounds(startX + 110, startY, 220, 35);
        userField.setBackground(new Color(40, 40, 60));
        userField.setForeground(Color.WHITE);
        userField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userField.setCaretColor(Color.WHITE);
        userField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(userField);

        JLabel passLabel = new JLabel("Senha:");
        passLabel.setBounds(startX, startY + 60, 100, 30);
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(startX + 110, startY + 60, 220, 35);
        passField.setBackground(new Color(40, 40, 60));
        passField.setForeground(Color.WHITE);
        passField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passField.setCaretColor(Color.WHITE);
        passField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(passField);

        loginButton = new JButton("Entrar");
        loginButton.setBounds(startX + 110, startY + 120, 120, 40);
        loginButton.setFocusPainted(false);
        loginButton.setBackground(new Color(160, 64, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(130, 30, 200), 2, true));
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(true);
        loginButton.setBorder(new RoundedBorder(20));
        panel.add(loginButton);


        loginButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                loginButton.setBackground(new Color(180, 90, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) 
            {
                loginButton.setBackground(new Color(160, 64, 255));
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
                loginButton.setBackground(new Color(120, 30, 200));
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
                loginButton.setBackground(new Color(180, 90, 255));
            }
        });

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) 
            {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
            } 
            else if (username.equals("admin") && password.equals("admin")) 
            {
                JOptionPane.showMessageDialog(null, "Login de administrador bem-sucedido!");
                dispose();
                new AdminHomeScreen();
            } 
            else if (username.equals("cli") && password.equals("cli")) 
            {
                JOptionPane.showMessageDialog(null, "Login de cliente bem-sucedido!");
                dispose();
                new ClientHomeScreen();
            } 
            else 
            {
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.");
            }
        });

        add(panel);
        setVisible(true);
    }


    class RoundedBorder implements Border 
    {
        private int radius;

        RoundedBorder(int radius) 
        {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) 
        {
            return new Insets(radius, radius, radius, radius);
        }

        public boolean isBorderOpaque() 
        {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) 
        {
            g.setColor(new Color(130, 30, 200));
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
