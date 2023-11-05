import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Mainframe {
    public static boolean confirm = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createMenuWindow();
        });
    }

    private static void createMenuWindow() {
        JFrame frame = new JFrame("Menu Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 943);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.DARK_GRAY);


        // Create a custom header panel with a background image
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)) {
            private BufferedImage background;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (background != null) {
                    Graphics2D g2d = (Graphics2D) g;

                    // Draw the background image with opacity
                    float opacity = 0.9f; // Adjust the opacity (0.0f to 1.0f)
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                    g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);

                    // Draw a dark overlay to make it darker
                    g2d.setColor(new Color(0, 0, 0, 100)); // Adjust the color and alpha value
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }

            // Load the background image
            {
                try {
                    background = ImageIO.read(new File("bg.jpg")); // Replace with the actual file path
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Set the size of the header panel
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 300));

        // Create a panel for the left side with a dark gray background
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.DARK_GRAY);
        leftPanel.setPreferredSize(new Dimension(200, frame.getHeight() - 100)); // Adjust the width and height as needed

        // Create a panel for the right side with a dark gray background
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setPreferredSize(new Dimension(200, frame.getHeight() - 100)); // Adjust the width and height as needed

        // Create a custom JPanel for the center with rounded edges
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2d = (Graphics2D) g;
                    int arcWidth = 25; // Adjust the arc width to change the roundness
                    int arcHeight = 25; // Adjust the arc height to change the roundness
                    Shape roundedRect = new RoundRectangle2D.Double(0, 15, getWidth(), getHeight(), arcWidth, arcHeight);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(getBackground());
                    g2d.fill(roundedRect);
                } else {
                    super.paintComponent(g);
                }
            }
        };

        // Set the background of the center panel to be transparent
        centerPanel.setOpaque(false);

        // Set the size of the center panel
        centerPanel.setPreferredSize(new Dimension(frame.getWidth() - 200, 100));

        // Create buttons for the images with additional info
JButton jwButton = createImageButton("jw.jpg", "John Wick Chapter 4");
JButton ohButton = createImageButton("oh.jpg", "Oppenheimer");

// Create a "Buy Ticket" button
BubbleButton buyTicketButton = new BubbleButton("Buy Ticket");


  // Set the same button style as other buttons (you can create a separate method for this)
        buyTicketButton.setFont(new Font("Sans-serif", Font.BOLD, 16));
        buyTicketButton.setForeground(Color.BLACK);
        buyTicketButton.setBackground(null);
        buyTicketButton.setFocusPainted(false);
        buyTicketButton.setFocusable(false);
        buyTicketButton.setBorderPainted(false);
        buyTicketButton.setPreferredSize(new Dimension(150, 38));
        buyTicketButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buyTicketButton.setBackground(new Color(255, 215, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buyTicketButton.setBackground(null);
            }
        });

        // Add an action listener for the "Logout" button
        buyTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new MovieTicketBookingSystem();
            
            }
        });

        // Create a "Buy Ticket" button
BubbleButton buyTicketButton1 = new BubbleButton("Buy Ticket");


  // Set the same button style as other buttons (you can create a separate method for this)
        buyTicketButton1.setFont(new Font("Sans-serif", Font.BOLD, 16));
        buyTicketButton1.setForeground(Color.BLACK);
        buyTicketButton1.setBackground(null);
        buyTicketButton1.setFocusPainted(false);
        buyTicketButton1.setFocusable(false);
        buyTicketButton1.setBorderPainted(false);
        buyTicketButton1.setPreferredSize(new Dimension(150, 38));
        buyTicketButton1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buyTicketButton1.setBackground(new Color(255, 215, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buyTicketButton1.setBackground(null);
            }
        });

        // Add an action listener for the "Logout" button
        buyTicketButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            

                new MovieTicketBookingSystem();
                
            
            }
        });
        

    // Create a panel for the images using GridBagLayout to center them
    JPanel imagePanel = new JPanel(new GridBagLayout());
    imagePanel.setOpaque(false);

    // Add the image buttons to the imagePanel
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.insets = new Insets(30, 50, 0, 50);
    imagePanel.add(jwButton, gbc);

    gbc.gridx = 1;
    imagePanel.add(ohButton, gbc);

    // Add the imagePanel to the centerPanel
    centerPanel.add(imagePanel);

    // Add a mouse listener for hover effect (optional)
    buyTicketButton.addMouseListener(new MouseAdapter() {
    private Component bubbleButton;

    @Override
    public void mouseEntered(MouseEvent e) {
        // Customize the button's appearance on hover
        buyTicketButton.setForeground(Color.BLACK);
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
});

// Add the "Buy Ticket" button to the centerPanel
centerPanel.add(buyTicketButton);
centerPanel.add(buyTicketButton1);

// Create a panel for the "jw.jpg" image and its "Buy Ticket" button
JPanel jwImagePanel = new JPanel(new GridBagLayout());
jwImagePanel.setOpaque(false);

// Add the "jw.jpg" image button to the panel
gbc.gridx = 0;
gbc.gridy = 0;
gbc.insets = new Insets(30, 50, 10, 50);
jwImagePanel.add(jwButton, gbc);

// Add the "Buy Ticket" button to the panel
gbc.gridy = 1;
gbc.insets = new Insets(0, 50, 30, 50);
jwImagePanel.add(buyTicketButton, gbc);

// Add the "jw.jpg" image panel to the centerPanel
centerPanel.add(jwImagePanel);

// Create a panel for the "oh.jpg" image and its "Buy Ticket" button
JPanel ohImagePanel = new JPanel(new GridBagLayout());
ohImagePanel.setOpaque(false);

// Add the "oh.jpg" image button to the panel
gbc.gridx = 0;
gbc.gridy = 0;
gbc.insets = new Insets(30, 50, 10, 50);
ohImagePanel.add(ohButton, gbc);

// Add the "Buy Ticket" button to the panel
gbc.gridy = 1;
gbc.insets = new Insets(0, 50, 30, 50);
ohImagePanel.add(buyTicketButton1, gbc);

// Add the "oh.jpg" image panel to the centerPanel
centerPanel.add(ohImagePanel);

        // Add a logo image to the left side of the header panel
        try {
            Image logoImage = ImageIO.read(new File("Logo.png.png")); // Replace with the actual file path
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            headerPanel.add(logoLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add the "Tickets N Cheap" text with Poppins font
        JLabel textLabel = new JLabel("Tickets N Cheap");
        Font poppinsFont = new Font("Poppins", Font.BOLD, 50);
        textLabel.setFont(poppinsFont);
        textLabel.setForeground(Color.ORANGE);
        headerPanel.add(textLabel);

        // Create buttons with bubble coloring effect using BubbleButton class
        BubbleButton profileButton = new BubbleButton("Profile");
        BubbleButton receiptButton = new BubbleButton("Check Receipts");

        receiptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){

                MovieTicketBookingSystem mtbs = new MovieTicketBookingSystem();

                mtbs.viewReceipts();
            }
        });

        
      


        // Create a "Logout" button with the logout.png image
        JButton logoutButton = new JButton();
        try {
            Image logoutImage = ImageIO.read(new File("logout.png")); // Replace with the actual file path
            ImageIcon logoutIcon = new ImageIcon(logoutImage);
            logoutButton.setIcon(logoutIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the same button style as other buttons (you can create a separate method for this)
        logoutButton.setFont(new Font("Sans-serif", Font.BOLD, 16));
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBackground(null);
        logoutButton.setFocusPainted(false);
        logoutButton.setFocusable(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(150, 40));
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(255, 215, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(null);
            }
        });

        // Add an action listener for the "Logout" button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                new Logout();

                if (confirm){
                    frame.dispose();
                    
                }
                
            }
        });

        // Use BorderLayout to arrange the components
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        frame.getContentPane().add(leftPanel, BorderLayout.WEST);
        frame.getContentPane().add(rightPanel, BorderLayout.EAST);
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

        // Add buttons to leftPanel using BoxLayout
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(profileButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        leftPanel.add(receiptButton);


        // Add the "Logout" button to the bottom of the left panel
        leftPanel.add(Box.createRigidArea(new Dimension(0, 350)));
        leftPanel.add(logoutButton);

        frame.setVisible(true);
    }

    // Create a method to generate image buttons with additional info and hover effect
    private static JButton createImageButton(String imagePath, String info) {
        JButton imageButton = new JButton();

        try {
            Image image = ImageIO.read(new File(imagePath));
            ImageIcon icon = new ImageIcon(image);
            imageButton.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Customize the image button
        imageButton.setPreferredSize(new Dimension(200, 200));
        imageButton.setBorderPainted(false);
        imageButton.setFocusPainted(false);
        imageButton.setContentAreaFilled(false);

        // Add a mouse listener for hover effect
        imageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                imageButton.setToolTipText(info);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                imageButton.setToolTipText(null);
            }
        });

        return imageButton;
    }
}
