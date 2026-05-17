package model;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class AddExpense extends JFrame {

    /*
     * Stage 2: Facade Pattern
     * The GUI uses ExpenseTrackerFacade instead of writing SQL directly.
     */
    private ExpenseTrackerFacade facade = new ExpenseTrackerFacade();

    /*
     * Stage 3: Command Pattern
     * ButtonHandler executes command objects instead of direct button logic.
     */
    private ButtonHandler buttonHandler = new ButtonHandler();

    /*
     * Stage 3: Observer Pattern
     * TransactionManager notifies observers when transaction data changes.
     */
    private TransactionManager transactionManager = new TransactionManager();

    AddExpense(String username) {

        Font f1 = new Font("Futura", Font.BOLD, 40);
        Font f2 = new Font("Segeo UI Emoji", Font.PLAIN, 22);

        JLabel title = new JLabel("Add Transactions", JLabel.CENTER);
        title.setForeground(new Color(0, 255, 180));

        JLabel l3 = new JLabel("Category:");
        JComboBox<CategoryItem> cbCategory = new JComboBox<>();

        JLabel l4 = new JLabel("Type:");
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Expense", "Income"});

        JLabel l1 = new JLabel("Enter Amount:");
        JTextField t1 = new JTextField(10);

        JLabel l2 = new JLabel("Description:");
        JTextField t2 = new JTextField(15);

        JButton subBtn = new JButton("Submit");
        subBtn.setBackground(new Color(0, 153, 102));
        subBtn.setForeground(Color.WHITE);
        subBtn.setFocusPainted(false);
        subBtn.setBorderPainted(false);
        subBtn.setOpaque(true);

        JButton backBtn = new JButton("Back");
        backBtn.setBackground(new Color(70, 70, 70));
        backBtn.setForeground(new Color(200, 200, 200));
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);

        title.setFont(f1);
        l3.setFont(f2);
        cbCategory.setFont(f2);
        l4.setFont(f2);
        cbType.setFont(f2);
        l1.setFont(f2);
        t1.setFont(f2);
        l2.setFont(f2);
        t2.setFont(f2);
        subBtn.setFont(f2);
        backBtn.setFont(f2);

        title.setBounds(240, 20, 330, 40);
        l3.setBounds(200, 100, 200, 30);
        cbCategory.setBounds(400, 100, 200, 30);
        l4.setBounds(200, 160, 200, 30);
        cbType.setBounds(400, 160, 200, 30);
        l1.setBounds(200, 220, 200, 30);
        t1.setBounds(400, 220, 200, 30);
        l2.setBounds(200, 280, 300, 30);
        t2.setBounds(400, 280, 200, 30);
        subBtn.setBounds(300, 380, 200, 40);
        backBtn.setBounds(300, 440, 200, 40);

        Container c = getContentPane();
        c.setLayout(null);
        c.add(title);
        c.add(l3);
        c.add(cbCategory);
        c.add(l4);
        c.add(cbType);
        c.add(l1);
        c.add(t1);
        c.add(l2);
        c.add(t2);
        c.add(subBtn);
        c.add(backBtn);

        cbCategory.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus
            ) {
                Component component = super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                if (value instanceof CategoryItem) {
                    CategoryItem item = (CategoryItem) value;

                    try {
                        component.setForeground(Color.decode(item.colorHex));
                    } catch (Exception e) {
                        component.setForeground(Color.BLACK);
                    }
                }

                return component;
            }
        });

        cbType.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus
            ) {
                Component component = super.getListCellRendererComponent(
                        list,
                        value,
                        index,
                        isSelected,
                        cellHasFocus
                );

                try {
                    if (value.equals("Income")) {
                        component.setForeground(new Color(50, 225, 50));
                    } else {
                        component.setForeground(Color.RED);
                    }
                } catch (Exception e) {
                    component.setForeground(Color.BLACK);
                }

                return component;
            }
        });

        loadCategories(username, cbCategory);

        /*
         * Stage 3: Observer Pattern
         * Register observers after loading the page.
         */
        transactionManager.addObserver(new BalanceObserver(transactionManager));
        transactionManager.addObserver(new SummaryObserver());

        subBtn.addActionListener(a -> {
            try {
                String type = cbType.getSelectedItem().toString();
                String amountText = t1.getText().trim();
                String description = t2.getText().trim();

                if (amountText.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Fields cannot be empty.");
                    return;
                }

                double amount = Double.parseDouble(amountText);

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Amount must be positive.");
                    return;
                }

                CategoryItem selectedCategory = (CategoryItem) cbCategory.getSelectedItem();

                if (selectedCategory == null) {
                    JOptionPane.showMessageDialog(null, "Please select a category.");
                    return;
                }

                String categoryName = selectedCategory.name;

                String date = new java.text.SimpleDateFormat(
                        "yyyy-MM-dd",
                        java.util.Locale.US
                ).format(new java.util.Date());

                /*
                 * Stage 1: Factory Method Pattern
                 * The GUI does not create ExpenseTransaction or IncomeTransaction directly.
                 * It asks TransactionFactory to create the correct object.
                 */
                Transaction transaction = TransactionFactory.createTransaction(
                        type,
                        amount,
                        categoryName,
                        description,
                        date
                );

                System.out.println("Transaction created using Factory Method Pattern.");
                System.out.println("Transaction Type: " + transaction.getType());

                /*
                 * Stage 3: Command Pattern
                 * The Submit button does not save directly.
                 * It creates AddTransactionCommand and executes it using ButtonHandler.
                 *
                 * Important:
                 * AddTransactionCommand internally calls ExpenseTrackerFacade.
                 */
                Command addCommand = new AddTransactionCommand(facade, username, transaction);
                buttonHandler.setCommand(addCommand);
                buttonHandler.clickButton();

                /*
                 * Stage 3: Observer Pattern
                 * After the transaction is added, observers are notified.
                 */
                transactionManager.addTransaction(transaction);

                JOptionPane.showMessageDialog(
                        null,
                        "Transaction added successfully using Factory Method, Facade, Command, and Observer Patterns."
                );

                t1.setText("");
                t2.setText("");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                e.printStackTrace();
            }
        });

        backBtn.addActionListener(a -> {
            JOptionPane.showMessageDialog(null, "Redirecting to Home page...");
            new Home(username);
            dispose();
        });

        setVisible(true);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Add Expense Page");
    }

    private void loadCategories(String username, JComboBox<CategoryItem> cbCategory) {
        Connection con = DBConnection.getInstance().getConnection();

        try {
            cbCategory.removeAllItems();

            String sql = "SELECT name, color FROM categories WHERE user_id = " +
                    "(SELECT id FROM users WHERE username = ?)";

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setString(1, username);

                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    String name = rs.getString("name");
                    String color = rs.getString("color");
                    cbCategory.addItem(new CategoryItem(name, color));
                }
            }

            if (cbCategory.getItemCount() == 0) {
                cbCategory.addItem(new CategoryItem("General", "#FFFFFF"));
                cbCategory.addItem(new CategoryItem("Food", "#FF0000"));
                cbCategory.addItem(new CategoryItem("Transport", "#00AAFF"));

                JOptionPane.showMessageDialog(
                        null,
                        "No saved categories were found for this user. Default categories were loaded."
                );
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Category loading error: " + ex.getMessage());

            cbCategory.addItem(new CategoryItem("General", "#FFFFFF"));
            cbCategory.addItem(new CategoryItem("Food", "#FF0000"));
            cbCategory.addItem(new CategoryItem("Transport", "#00AAFF"));
        }
    }

    public static void main(String[] args) {
        new AddExpense("A");
    }
}

class CategoryItem {

    String name;
    String colorHex;

    CategoryItem(String name, String colorHex) {
        this.name = name;
        this.colorHex = colorHex;
    }

    @Override
    public String toString() {
        return name;
    }
}