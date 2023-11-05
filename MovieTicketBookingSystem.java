import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MovieTicketBookingSystem extends Mainframe {
    private JFrame frame;
    private JButton[] seatButtons;
    private JLabel statusLabel;
    private int totalSeats = 40;
    private List<Integer> selectedSeatIndices = new ArrayList<>();
    private JButton proceedButton;
    private JButton undoButton;
    private JButton redoButton;
    private Stack<List<Integer>> undoHistory = new Stack<>();
    private Stack<List<Integer>> redoHistory = new Stack<>();
    private double seatPrice = 350.0;
    private double totalCost = 0.0;
    private PaymentSystem paymentSystem;
    private List<Receipt> receipts = new ArrayList<>();

    public MovieTicketBookingSystem() {
        // Initialize the main application window
        frame = new JFrame("Movie Ticket Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an array of buttons to represent available seats
        seatButtons = new JButton[totalSeats];
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(4, 10));

        // Create and set up seat buttons, and add them to the panel
        for (int i = 0; i < totalSeats; i++) {
            seatButtons[i] = new JButton("Seat " + (i + 1));
            seatButtons[i].addActionListener(new SeatButtonListener(i));
            seatPanel.add(seatButtons[i]);
        }

        // Create labels and buttons for UI
        statusLabel = new JLabel("Select seats to book");
        proceedButton = new JButton("Proceed to Payment");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");
        proceedButton.setEnabled(false);
        undoButton.setEnabled(false);
        redoButton.setEnabled(false);

        // Register action listeners for buttons
        proceedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a PaymentSystem instance and open the payment dialog
                paymentSystem = new PaymentSystem(totalCost, selectedSeatIndices);
                paymentSystem.openPaymentDialog();
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Undo the seat selection
                undoSelection();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Redo the seat selection
                redoSelection();
            }
        });

        JButton viewReceiptsButton = new JButton("View Receipts");
        viewReceiptsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewReceipts(); // Call the method to display receipts
            }
        });

        // Set up the layout of the main frame
        frame.add(seatPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(proceedButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);
        buttonPanel.add(viewReceiptsButton); // Add the "View Receipts" button
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setSize(450,430);
        frame.setVisible(true);
    }

    // Inner class for handling seat button actions
    private class SeatButtonListener implements ActionListener {
        private int seatIndex;

        public SeatButtonListener(int seatIndex) {
            this.seatIndex = seatIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (seatButtons[seatIndex].isEnabled()) {
                // Select a seat
                seatButtons[seatIndex].setEnabled(false);
                selectedSeatIndices.add(seatIndex);
                totalCost += seatPrice;
                undoHistory.push(new ArrayList<>(selectedSeatIndices));
                redoHistory.clear();
                updateUndoRedoButtons();
            } else {
                // Deselect a seat
                seatButtons[seatIndex].setEnabled(true);
                selectedSeatIndices.remove(Integer.valueOf(seatIndex));
                totalCost -= seatPrice;
                undoHistory.push(new ArrayList<>(selectedSeatIndices));
                redoHistory.clear();
                updateUndoRedoButtons();
            }
            updateStatusLabel();
        }
    }

    // Update the status label to display the number of selected seats and the total cost
    private void updateStatusLabel() {
        statusLabel.setText("Selected " + selectedSeatIndices.size() + " seats. Total Cost: $" + totalCost);
    }

    // Enable or disable undo, redo, and proceed buttons
    private void updateUndoRedoButtons() {
        undoButton.setEnabled(!undoHistory.isEmpty());
        redoButton.setEnabled(!redoHistory.isEmpty());
        proceedButton.setEnabled(!selectedSeatIndices.isEmpty());
    }

    // Undo the seat selection
    private void undoSelection() {
        if (!undoHistory.isEmpty()) {
            redoHistory.push(new ArrayList<>(selectedSeatIndices));
            selectedSeatIndices = undoHistory.pop();
            totalCost = selectedSeatIndices.size() * seatPrice;
            updateSeatButtons(selectedSeatIndices);
            updateUndoRedoButtons();
            updateStatusLabel();
        }
    }

    // Redo the seat selection
    private void redoSelection() {
        if (!redoHistory.isEmpty()) {
            undoHistory.push(new ArrayList<>(selectedSeatIndices));
            selectedSeatIndices = redoHistory.pop();
            totalCost = selectedSeatIndices.size() * seatPrice;
            updateSeatButtons(selectedSeatIndices);
            updateUndoRedoButtons();
            updateStatusLabel();
        }
    }

    // Update the seat buttons based on the selection
    private void updateSeatButtons(List<Integer> selection) {
        for (int i = 0; i < totalSeats; i++) {
            seatButtons[i].setEnabled(!selection.contains(i));
        }
    }

    // PaymentSystem class for managing payment processing
    class PaymentSystem {
        private double totalCost;
        private List<Integer> selectedSeatIndices;

        public PaymentSystem(double totalCost, List<Integer> selectedSeatIndices) {
            this.totalCost = totalCost;
            this.selectedSeatIndices = selectedSeatIndices;
        }

        public void openPaymentDialog() {
            // Create and display the payment dialog
            PaymentDialog dialog = new PaymentDialog(totalCost);
            dialog.showDialog();
        }
    }

    // PaymentDialog class for displaying payment information
    class PaymentDialog {
        private double totalCost;
        private JFrame frame;
        private JPanel panel;
        private JTextField creditCardField;
        private JTextField expirationField;
        private JTextField paypalUsernameField;
        private JPasswordField paypalPasswordField;

        public PaymentDialog(double totalCost) {
            this.totalCost = totalCost;
            frame = new JFrame("Payment Information");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            panel = new JPanel();
            panel.setLayout(new GridLayout(0, 2));

            creditCardField = new JTextField();
            expirationField = new JTextField();
            paypalUsernameField = new JTextField();
            paypalPasswordField = new JPasswordField();

            frame.add(panel);
        }

        // Display the payment dialog with options for credit card or PayPal
        public void showDialog() {
            String[] options = {"Credit Card", "PayPal"};
            int choice = JOptionPane.showOptionDialog(null, "Select payment method:", "Payment Method", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == 0) { // Credit Card
                panel.removeAll();
                panel.add(new JLabel("Total Cost: $" + totalCost));
                panel.add(new JLabel("Credit Card Number:"));
                panel.add(creditCardField);
                panel.add(new JLabel("Expiration Date:"));
                panel.add(expirationField);
            } else if (choice == 1) { // PayPal
                panel.removeAll();
                panel.add(new JLabel("Total Cost: $" + totalCost));
                panel.add(new JLabel("PayPal Username:"));
                panel.add(paypalUsernameField);
                panel.add(new JLabel("PayPal Password:"));
                panel.add(paypalPasswordField);
            }

            panel.revalidate();
            panel.repaint();
            frame.pack();
            frame.setVisible(true);

            // After the payment is processed, display the receipt
            JButton payButton = new JButton("Pay");
            payButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    ReceiptDialog receiptDialog = new ReceiptDialog(totalCost, selectedSeatIndices);
                    receiptDialog.showReceiptDialog();

                    // Reset the total cost after payment
                    resetTotalCost();

                    // Store the receipt
                    Receipt receipt = new Receipt(totalCost, selectedSeatIndices);
                    receipts.add(receipt);
                }
            });

            panel.add(payButton);
        }
    }

    // Receipt class to represent receipt information
    class Receipt {
        private double totalCost;
        private List<Integer> selectedSeatIndices;

        public Receipt(double totalCost, List<Integer> selectedSeatIndices) {
            this.totalCost = totalCost;
            this.selectedSeatIndices = selectedSeatIndices;
        }

        // Getters for total cost and selected seat indices

        public double getTotalCost() {
            return totalCost;
        }

        public List<Integer> getSelectedSeatIndices() {
            return selectedSeatIndices;
        }
    }

    // ReceiptDialog class for displaying the receipt
    class ReceiptDialog {
        private double totalCost;
        private List<Integer> selectedSeatIndices;

        public ReceiptDialog(double totalCost, List<Integer> selectedSeatIndices) {
            this.totalCost = totalCost;
            this.selectedSeatIndices = selectedSeatIndices;
        }

        public void showReceiptDialog() {
            JFrame receiptFrame = new JFrame("Receipt");
            receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel receiptPanel = new JPanel();
            receiptPanel.setLayout(new GridLayout(0, 1));
            receiptPanel.add(new JLabel("Receipt Details:"));
            receiptPanel.add(new JLabel("Amount Paid: $" + totalCost));
            receiptPanel.add(new JLabel("Number of Seats Taken: " + selectedSeatIndices.size()));

            // Adjust seat numbers by adding 1 to each index
            List<Integer> seatNumbers = new ArrayList<>();
            for (Integer index : selectedSeatIndices) {
                seatNumbers.add(index + 1);
            }

            receiptPanel.add(new JLabel("Seats Taken: " + seatNumbers.toString()));

            receiptFrame.add(receiptPanel);
            receiptFrame.pack();
            receiptFrame.setVisible(true);
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    receiptFrame.dispose();
                }
            });
            receiptPanel.add(closeButton);
        }
    }

    // Method to access all past receipts
    public List<Receipt> getAllReceipts() {
        return receipts;
    }

    public void viewReceipts() {
        List<Receipt> allReceipts = getAllReceipts();

        if (allReceipts.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No past receipts available.", "Past Receipts", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JFrame receiptListFrame = new JFrame("Past Receipts");
            receiptListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel receiptListPanel = new JPanel();
            receiptListPanel.setLayout(new GridLayout(0, 1));
            receiptListPanel.add(new JLabel("Past Receipts:"));

            for (Receipt receipt : allReceipts) {
                receiptListPanel.add(new JLabel("Amount Paid: $" + receipt.getTotalCost()));
                receiptListPanel.add(new JLabel("Number of Seats Taken: " + receipt.getSelectedSeatIndices().size()));

                // Adjust seat numbers by adding 1 to each index
                List<Integer> seatNumbers = new ArrayList<>();
                for (Integer index : receipt.getSelectedSeatIndices()) {
                    seatNumbers.add(index + 1);
                }
                receiptListPanel.add(new JLabel("Seats Taken: " + seatNumbers.toString()));
                receiptListPanel.add(new JLabel("-----"));
            }

            JScrollPane scrollPane = new JScrollPane(receiptListPanel);
            receiptListFrame.add(scrollPane);
            receiptListFrame.pack();
            receiptListFrame.setVisible(true);
        }
    }

    public void resetTotalCost() {
        totalCost = 0.0;
    }

   
    }

