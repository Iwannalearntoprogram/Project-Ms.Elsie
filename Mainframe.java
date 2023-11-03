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

        // Set the background color of the content pane to dark gray
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
        BubbleButton homeButton = new BubbleButton("Home");
        BubbleButton receiptButton = new BubbleButton("Check Receipts");
        BubbleButton checkoutButton = new BubbleButton("Check Out");
        

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
        logoutButton.setBackground(null); // No button background
        logoutButton.setFocusPainted(false);
        logoutButton.setFocusable(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(150, 40)); // Adjust the button size
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(255, 215, 0)); // Change background color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(null); // No background color when not hovering
            }
        });

        // Add an action listener for the "Logout" button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the logout functionality here
                // For example, close the application or navigate to a logout page
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
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space
        leftPanel.add(homeButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Add space
        leftPanel.add(receiptButton);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Add space
        leftPanel.add(checkoutButton);

        // Add the "Logout" button to the bottom of the left panel
        leftPanel.add(Box.createRigidArea(new Dimension(0, 350))); // Add space
        leftPanel.add(logoutButton);

        frame.setVisible(true);
    }
}
