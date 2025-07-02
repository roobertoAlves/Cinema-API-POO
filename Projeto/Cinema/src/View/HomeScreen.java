package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeScreen extends JFrame 
{

    public HomeScreen(String usuarioLogado) 
    {
        setTitle("CineRoxo - Início");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JPanel background = new JPanel();
        background.setBackground(new Color(10, 10, 30));
        background.setLayout(null);
        setContentPane(background);

        JPanel menu = new JPanel();
        menu.setBackground(new Color(30, 0, 60));
        menu.setBounds(0, 0, 200, 1080);
        menu.setLayout(new GridLayout(5, 1, 0, 10));

        String[] opcoes = {"🎞 Filmes", "📺 Séries", "⭐ Favoritos", "🔍 Buscar", "👤 Conta"};
        for (String opcao : opcoes) {
            JLabel item = new JLabel(opcao, SwingConstants.CENTER);
            item.setForeground(Color.WHITE);
            item.setFont(new Font("Segoe UI", Font.BOLD, 18));
            item.setOpaque(true);
            item.setBackground(new Color(40, 0, 80));
            item.setCursor(new Cursor(Cursor.HAND_CURSOR));
            item.addMouseListener(new MouseAdapter() 
            {
                public void mouseEntered(MouseEvent e) 
                {
                    item.setBackground(new Color(70, 0, 120));
                }

                public void mouseExited(MouseEvent e) 
                {
                    item.setBackground(new Color(40, 0, 80));
                }

                public void mouseClicked(MouseEvent e) 
                {
                    JOptionPane.showMessageDialog(null, "Clicou em: " + opcao);
                }
            });
            menu.add(item);
        }

        background.add(menu);


        JLabel banner = new JLabel("🎬 Bem-vindo, " + usuarioLogado + "!", SwingConstants.LEFT);
        banner.setForeground(new Color(180, 100, 255));
        banner.setFont(new Font("Segoe UI", Font.BOLD, 36));
        banner.setBounds(220, 30, 1000, 50);
        background.add(banner);

        JPanel filmesPanel = new JPanel();
        filmesPanel.setBounds(220, 100, 1600, 900);
        filmesPanel.setBackground(new Color(10, 10, 30));
        filmesPanel.setLayout(new GridLayout(2, 4, 30, 30));
        background.add(filmesPanel);


        for (int i = 1; i <= 8; i++) 
        {
            JPanel card = criarCardFilme("Filme " + i, "Descrição curta do filme " + i);
            filmesPanel.add(card);
        }

        setVisible(true);
    }


    private JPanel criarCardFilme(String titulo, String descricao) 
    {
        JPanel card = new JPanel();
        card.setBackground(new Color(30, 30, 60));
        card.setLayout(null);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel imagem = new JLabel("🎬", SwingConstants.CENTER);
        imagem.setFont(new Font("Segoe UI", Font.PLAIN, 50));
        imagem.setBounds(0, 10, 200, 60);
        imagem.setForeground(new Color(180, 100, 255));
        card.add(imagem);

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setForeground(Color.WHITE);
        tituloLabel.setBounds(0, 80, 200, 30);
        card.add(tituloLabel);

        JLabel descricaoLabel = new JLabel("<html><div style='text-align: center;'>" + descricao + "</div></html>");
        descricaoLabel.setForeground(Color.LIGHT_GRAY);
        descricaoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descricaoLabel.setBounds(10, 110, 180, 40);
        card.add(descricaoLabel);

        card.setPreferredSize(new Dimension(200, 160));
        card.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                JOptionPane.showMessageDialog(null, "Abrir detalhes de: " + titulo);
            }
        });

        return card;
    }

    public static void main(String[] args) 
    {
        new HomeScreen("admin");
    }
}
