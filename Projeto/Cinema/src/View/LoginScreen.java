package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {

    public LoginScreen() 
    {
        setTitle("Cinema - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(false);
        setLayout(null);

   
        JPanel panel = new JPanel();
        panel.setBackground(new Color(10, 10, 30)); 
        panel.setLayout(null);
        setContentPane(panel);

        JLabel titleLabel = new JLabel("🎬 Cinema");
        titleLabel.setForeground(new Color(180, 100, 255));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 64));
        titleLabel.setBounds(720, 120, 500, 80);
        panel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Bem-vindo ao universo do cinema!");
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        subtitleLabel.setBounds(680, 200, 600, 40);
        panel.add(subtitleLabel);

    
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        userLabel.setBounds(740, 300, 400, 30);
        panel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        userField.setBounds(740, 340, 440, 40);
        userField.setBackground(new Color(30, 30, 60));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);
        panel.add(userField);


        JLabel passLabel = new JLabel("Senha:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passLabel.setBounds(740, 400, 400, 30);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passField.setBounds(740, 440, 440, 40);
        passField.setBackground(new Color(30, 30, 60));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);
        panel.add(passField);


        JButton loginButton = new JButton("Entrar");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginButton.setBackground(new Color(180, 100, 255));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBounds(740, 520, 440, 50);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(140, 60, 220), 2));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setOpaque(true);


        loginButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() 
        {
            @Override
            public void installUI(JComponent c) 
            {
                super.installUI(c);
                c.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                c.setBackground(new Color(180, 100, 255));
            }
        });

        panel.add(loginButton);


        loginButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                String user = userField.getText();
                String password = new String(passField.getPassword());

                if (user.equals("admin") && password.equals("1234")) 
                {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido! 🎬");

                } 
                else 
                {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.");
                }
            }
        });


        JLabel footer = new JLabel("©");
        footer.setForeground(new Color(120, 120, 120));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setBounds(720, 1000, 500, 30);
        panel.add(footer);

        setVisible(true);
    }

    public static void main(String[] args)
     {
        new LoginScreen();
    }
}
