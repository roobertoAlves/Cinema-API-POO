package View;

import Model.BomboniereProductsDAO;
import Model.Purchase;
import Model.PurchaseDAO;
import Model.PurchaseItems;
import Model.PurchaseItemsDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductSelectionScreen extends JFrame 
{
    
    private JLabel totalLabel, productsLabel, ticketLabel;
    private double ticketPrice = 0.0;
    private double productsPrice = 0.0;
    private double totalPrice = 0.0;
    private JFrame previousScreen;
    private Map<ProductCard, ProductInfo> productCards = new LinkedHashMap<>();
    

    private static class ProductInfo 
    {
        String name;
        double price;
        int quantity;
        
        ProductInfo(String name, double price) 
        {
            this.name = name;
            this.price = price;
            this.quantity = 0;
        }
    }
    

    private class ProductCard extends JPanel 
    {
        private ProductInfo productInfo;
        private JLabel nameLabel, priceLabel, quantityLabel;
        private JButton minusButton, plusButton;
        private boolean isSelected = false;
        
        public ProductCard(ProductInfo productInfo) 
        {
            this.productInfo = productInfo;
            setupCard();
        }
        
        private void setupCard() 
        {
            setLayout(new BorderLayout());
            setBackground(new Color(45, 45, 65));
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 90), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));

            setPreferredSize(new Dimension(250, 120));
            

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setOpaque(false);
            

            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 5));
            infoPanel.setOpaque(false);
            
            nameLabel = new JLabel(productInfo.name);
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            
            priceLabel = new JLabel("R$ " + String.format("%.2f", productInfo.price));
            priceLabel.setForeground(new Color(160, 64, 255));
            priceLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            
            infoPanel.add(nameLabel);
            infoPanel.add(priceLabel);
            

            JPanel controlPanel = new JPanel(new BorderLayout());
            controlPanel.setOpaque(false);
            
            JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            quantityPanel.setOpaque(false);
            
            minusButton = createControlButton("-");
            quantityLabel = new JLabel("0");
            quantityLabel.setForeground(Color.WHITE);
            quantityLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
            quantityLabel.setPreferredSize(new Dimension(30, 25));
            
            plusButton = createControlButton("+");
            
            quantityPanel.add(minusButton);
            quantityPanel.add(quantityLabel);
            quantityPanel.add(plusButton);
            
            controlPanel.add(quantityPanel, BorderLayout.CENTER);
            
            mainPanel.add(infoPanel, BorderLayout.CENTER);
            mainPanel.add(controlPanel, BorderLayout.EAST);
            
            add(mainPanel, BorderLayout.CENTER);
            

            minusButton.addActionListener(e -> decreaseQuantity());
            plusButton.addActionListener(e -> increaseQuantity());
            

            addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseEntered(MouseEvent e) 
                {
                    if (!isSelected) 
                    {
                        setBackground(new Color(55, 55, 75));
                        setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(100, 100, 120), 2),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                    }
                }
                
                @Override
                public void mouseExited(MouseEvent e) 
                {
                    if (!isSelected) 
                    {
                        setBackground(new Color(45, 45, 65));
                        setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(70, 70, 90), 2),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                    }
                }
            });
        }
        
        private JButton createControlButton(String text) 
        {
            JButton button = new JButton(text);
            button.setPreferredSize(new Dimension(30, 25));
            button.setBackground(new Color(160, 64, 255));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("SansSerif", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder());
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            button.addMouseListener(new MouseAdapter() 
            {
                @Override
                public void mouseEntered(MouseEvent e) 
                {
                    button.setBackground(new Color(180, 90, 255));
                }
                
                @Override
                public void mouseExited(MouseEvent e) 
                {
                    button.setBackground(new Color(160, 64, 255));
                }
            });
            
            return button;
        }
        
        private void increaseQuantity() 
        {
            productInfo.quantity++;
            quantityLabel.setText(String.valueOf(productInfo.quantity));
            updateCardAppearance();
            updateTotal();
        }
        
        private void decreaseQuantity() 
        {
            if (productInfo.quantity > 0) 
            {
                productInfo.quantity--;
                quantityLabel.setText(String.valueOf(productInfo.quantity));
                updateCardAppearance();
                updateTotal();
            }
        }
        
        private void updateCardAppearance() 
        {
            isSelected = productInfo.quantity > 0;
            if (isSelected) 
            {
                setBackground(new Color(60, 60, 85));
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(160, 64, 255), 2),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            } 
            else 
            {
                setBackground(new Color(45, 45, 65));
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(70, 70, 90), 2),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
        }
    }

    public ProductSelectionScreen() 
    {
        this(0.0, null);
    }

    public ProductSelectionScreen(double ticketPrice, JFrame previousScreen) 
    {
        this.ticketPrice = ticketPrice;
        this.previousScreen = previousScreen;
        this.totalPrice = ticketPrice;
        setupUI();
        loadProducts();
        setVisible(true);
    }
    
    private void setupUI() 
    {
        setTitle("Selecionar Produtos - CinePlay");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 30));
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/Assets/cineplay.png"));


        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);


        JPanel productsPanel = createProductsPanel();
        add(productsPanel, BorderLayout.CENTER);


        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTopPanel() 
    {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(18, 18, 30));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        

        JButton backButton = new JButton("← Voltar");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(70, 70, 90));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> {
            if (previousScreen != null) 
            {
                previousScreen.setVisible(true);
            }
            dispose();
        });
        
        backButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                backButton.setBackground(new Color(90, 90, 110));
            }
            @Override
            public void mouseExited(MouseEvent e) 
            {
                backButton.setBackground(new Color(70, 70, 90));
            }
        });
        
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("🍿 Escolha seus Produtos", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(160, 64, 255));
        topPanel.add(titleLabel, BorderLayout.CENTER);
        
        return topPanel;
    }
    
    private JPanel createProductsPanel() 
    {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(18, 18, 30));
        
        JLabel subtitleLabel = new JLabel("Selecione a quantidade desejada de cada produto:", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(subtitleLabel, BorderLayout.NORTH);
        
        JPanel productsContainer = new JPanel(new GridLayout(0, 2, 20, 20));
        productsContainer.setBackground(new Color(18, 18, 30));
        productsContainer.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
        
        JScrollPane scrollPane = new JScrollPane(productsContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(18, 18, 30));
        scrollPane.getViewport().setBackground(new Color(18, 18, 30));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private JPanel createBottomPanel() 
    {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(25, 25, 40));
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(70, 70, 90)),
            BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));


        JPanel totalDetailsPanel = new JPanel(new GridLayout(3, 1, 0, 8));
        totalDetailsPanel.setOpaque(false);
        
        ticketLabel = new JLabel("Ingresso: R$ " + String.format("%.2f", ticketPrice), SwingConstants.CENTER);
        ticketLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        ticketLabel.setForeground(Color.LIGHT_GRAY);
        
        productsLabel = new JLabel("Produtos: R$ 0,00", SwingConstants.CENTER);
        productsLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        productsLabel.setForeground(Color.LIGHT_GRAY);

        totalLabel = new JLabel("Total: R$ " + String.format("%.2f", totalPrice), SwingConstants.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalLabel.setForeground(Color.WHITE);
        
        totalDetailsPanel.add(ticketLabel);
        totalDetailsPanel.add(productsLabel);
        totalDetailsPanel.add(totalLabel);
        
        bottomPanel.add(totalDetailsPanel, BorderLayout.CENTER);


        JButton finalizeButton = new JButton("Finalizar Compra");
        finalizeButton.setBackground(new Color(160, 64, 255));
        finalizeButton.setForeground(Color.WHITE);
        finalizeButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        finalizeButton.setFocusPainted(false);
        finalizeButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        finalizeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        finalizeButton.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                finalizeButton.setBackground(new Color(180, 90, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) 
            {
                finalizeButton.setBackground(new Color(160, 64, 255));
            }
        });
        
        finalizeButton.addActionListener(e -> finalizePurchase());

        bottomPanel.add(finalizeButton, BorderLayout.SOUTH);
        
        return bottomPanel;
    }
    
    private void loadProducts() 
    {
        try 
        {
            ResultSet rs = new BomboniereProductsDAO().list("estoqueDisponivel = 'disponivel'");
            JPanel productsContainer = (JPanel) ((JScrollPane) ((JPanel) getContentPane().getComponent(1)).getComponent(1)).getViewport().getView();
            
            while (rs.next()) 
            {
                String name = rs.getString("nome");
                double price = rs.getDouble("preco");
                
                ProductInfo productInfo = new ProductInfo(name, price);
                ProductCard productCard = new ProductCard(productInfo);
                
                productCards.put(productCard, productInfo);
                productsContainer.add(productCard);
            }

            rs.close();
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void updateTotal() 
    {
        productsPrice = 0.0;
        for (ProductInfo productInfo : productCards.values()) 
        {
            productsPrice += productInfo.price * productInfo.quantity;
        }
        totalPrice = ticketPrice + productsPrice;
        

        productsLabel.setText("Produtos: R$ " + String.format("%.2f", productsPrice));
        totalLabel.setText("Total: R$ " + String.format("%.2f", totalPrice));
    }
    
    private void finalizePurchase() 
    {

        if (previousScreen instanceof SeatAndTicketSelectionScreen) 
        {
            SeatAndTicketSelectionScreen seatScreen = (SeatAndTicketSelectionScreen) previousScreen;
        
            int clientId = 1;

            registerProductsPurchase(clientId);
            
            seatScreen.finalizePurchase(clientId, totalPrice);
            dispose();
        } 
        else 
        {

            StringBuilder summary = new StringBuilder();
            summary.append("Resumo da Compra:\n\n");
            
            if (ticketPrice > 0) 
            {
                summary.append("Ingresso: R$ ").append(String.format("%.2f", ticketPrice)).append("\n");
            }
            
            summary.append("Produtos:\n");
            for (ProductInfo productInfo : productCards.values()) 
            {
                if (productInfo.quantity > 0) 
                {
                    summary.append("• ").append(productInfo.name)
                           .append(" (").append(productInfo.quantity).append("x)")
                           .append(" - R$ ").append(String.format("%.2f", productInfo.price * productInfo.quantity))
                           .append("\n");
                }
            }
            
            summary.append("\nTotal: R$ ").append(String.format("%.2f", totalPrice));
            
            JOptionPane.showMessageDialog(this, summary.toString(), "Compra Finalizada", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }
    
    private void registerProductsPurchase(int clientId) 
    {
        try 
        {
            boolean hasProducts = false;
            for (ProductInfo productInfo : productCards.values()) 
            {
                if (productInfo.quantity > 0) 
                {
                    hasProducts = true;
                    break;
                }
            }
            
            if (!hasProducts) 
            {
                return;
            }
            

            Purchase purchase = new Purchase();
            purchase.setClientId(clientId);
            purchase.setEmployeeId(0);
            purchase.setPurchaseDate(new java.sql.Date(System.currentTimeMillis()).toString());
            purchase.setTotalValue(productsPrice);
            
            PurchaseDAO purchaseDAO = new PurchaseDAO();
            int purchaseId = purchaseDAO.insertAndGetId(purchase);
            
            if (purchaseId > 0) 
            {
                PurchaseItemsDAO itemsDAO = new PurchaseItemsDAO();
                BomboniereProductsDAO productsDAO = new BomboniereProductsDAO();
                
                for (ProductInfo productInfo : productCards.values()) 
                {
                    if (productInfo.quantity > 0) 
                    {

                        ResultSet productRs = productsDAO.list("nome = '" + productInfo.name + "'");

                        if (productRs.next()) 
                        {
                            int productId = productRs.getInt("id_produto");
                            
                            PurchaseItems item = new PurchaseItems();
                            item.setPurchaseId(purchaseId);
                            item.setProductId(productId);
                            item.setQuantity(productInfo.quantity);
                            item.setUnitPrice(productInfo.price);
                            
                            int itemResult = itemsDAO.insert(item);
                            if (itemResult > 0) 
                            {
                                System.out.println("Item salvo: " + productInfo.name + 
                                                 " - Quantidade: " + productInfo.quantity);
                            }
                        }
                        productRs.close();
                    }
                }
                
                System.out.println("Compra registrada com sucesso! ID: " + purchaseId);
                JOptionPane.showMessageDialog(this, "Compra registrada no banco de dados!");
            }
            else 
            {
                System.err.println("Erro ao registrar compra principal");
                JOptionPane.showMessageDialog(this, "Erro ao registrar compra");
            }
            
        } 
        catch (Exception e) 
        {
            System.err.println("Erro ao registrar compra de produtos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public double getProductsPrice() 
    {
        return productsPrice;
    }

    public double getTotalPrice() 
    {
        return totalPrice;
    }
}
