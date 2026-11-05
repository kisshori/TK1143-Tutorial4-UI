import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PriceGUI extends JFrame implements ActionListener {
    private JComboBox<String> cbItem;
    private JTextField tfItemName, tfPrice, tfCustomer, tfSalary;
    private JLabel lblItemName;
    private JButton btnCheck, btnClear;
    private JTextArea area;

    public PriceGUI() {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout(5, 5));

        JPanel input = new JPanel(new GridLayout(5, 2, 5, 5));
        cbItem = new JComboBox<String>(new String[]{"SmartPhone", "Laptop", "Car"});
        lblItemName = new JLabel("Product name :");
        tfItemName = new JTextField(10);
        tfPrice = new JTextField(10);
        tfCustomer = new JTextField(10);
        tfSalary = new JTextField(10);
        input.add(new JLabel("Item :"));
        input.add(cbItem);
        input.add(lblItemName);
        input.add(tfItemName);
        input.add(new JLabel("Price (RM) :"));
        input.add(tfPrice);
        input.add(new JLabel("Customer name :"));
        input.add(tfCustomer);
        input.add(new JLabel("Salary (RM) :"));
        input.add(tfSalary);

        JPanel buttons = new JPanel(new FlowLayout());
        btnCheck = new JButton("Check Price");
        btnClear = new JButton("Clear");
        buttons.add(btnCheck);
        buttons.add(btnClear);

        area = new JTextArea(9, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JPanel north = new JPanel(new BorderLayout());
        north.add(input, BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);
        pane.add(north, BorderLayout.NORTH);
        pane.add(new JScrollPane(area), BorderLayout.CENTER);

        cbItem.addActionListener(this);
        btnCheck.addActionListener(this);
        btnClear.addActionListener(this);
    }

    // getPrice() prints to the console, so we capture that text to show it in the GUI
    private String capture(Price item, Customer c) {
        PrintStream old = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            item.getPrice(c);
        } finally {
            System.setOut(old);
        }
        return buffer.toString();
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbItem) {
            if (cbItem.getSelectedItem().equals("Car")) lblItemName.setText("Car brand :");
            else lblItemName.setText("Product name :");
        } else if (src == btnCheck) {
            try {
                String itemName = tfItemName.getText().trim();
                String custName = tfCustomer.getText().trim();
                double price = Double.parseDouble(tfPrice.getText().trim());
                double salary = Double.parseDouble(tfSalary.getText().trim());
                if (itemName.length() == 0 || custName.length() == 0) {
                    JOptionPane.showMessageDialog(this, "Please fill in all the fields.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Customer customer = new Customer(custName, salary);
                String choice = (String) cbItem.getSelectedItem();
                Price item;
                if (choice.equals("SmartPhone")) item = new SmartPhone("SmartPhone", itemName, price);
                else if (choice.equals("Laptop")) item = new Laptop("Laptop", itemName, price);
                else item = new Car(itemName, price);

                area.append(capture(item, customer) + "\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Price and salary must be numbers.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (src == btnClear) {
            area.setText("");
            tfItemName.setText("");
            tfPrice.setText("");
            tfCustomer.setText("");
            tfSalary.setText("");
        }
    }

    public static void main(String[] args) {
        PriceGUI frame = new PriceGUI();
        frame.setTitle("Price GUI");
        frame.setSize(450, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}