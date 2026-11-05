import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class DataSetGUI extends JFrame implements ActionListener {
    private JComboBox<String> cbType;
    private JTextField tfName, tfValue;
    private JLabel lblName, lblValue;
    private JButton btnAdd, btnResult, btnReset;
    private JTextArea area;

    private DataSet bankData = new DataSet();
    private DataSet coinData = new DataSet();
    private DataSet studentData = new DataSet();
    private DecimalFormat df = new DecimalFormat("0.00");

    public DataSetGUI() {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout(5, 5));

        // input panel
        JPanel input = new JPanel(new GridLayout(3, 2, 5, 5));
        cbType = new JComboBox<String>(new String[]{"Bank Account", "Coin", "Student"});
        lblName = new JLabel("Name :");
        tfName = new JTextField(10);
        lblValue = new JLabel("Balance :");
        tfValue = new JTextField(10);
        input.add(new JLabel("Data type :"));
        input.add(cbType);
        input.add(lblName);
        input.add(tfName);
        input.add(lblValue);
        input.add(tfValue);

        // buttons
        JPanel buttons = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add");
        btnResult = new JButton("Show Average & Max");
        btnReset = new JButton("Reset");
        buttons.add(btnAdd);
        buttons.add(btnResult);
        buttons.add(btnReset);

        // output
        area = new JTextArea(10, 30);
        area.setEditable(false);

        JPanel north = new JPanel(new BorderLayout());
        north.add(input, BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);
        pane.add(north, BorderLayout.NORTH);
        pane.add(new JScrollPane(area), BorderLayout.CENTER);

        // register listeners
        cbType.addActionListener(this);
        btnAdd.addActionListener(this);
        btnResult.addActionListener(this);
        btnReset.addActionListener(this);

        updateFields();
    }

    private void updateFields() {
        String type = (String) cbType.getSelectedItem();
        if (type.equals("Bank Account")) {
            tfName.setEnabled(false);
            tfName.setText("");
            lblValue.setText("Balance :");
        } else if (type.equals("Coin")) {
            tfName.setEnabled(true);
            lblValue.setText("Value :");
        } else {
            tfName.setEnabled(true);
            lblValue.setText("Height :");
        }
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        String type = (String) cbType.getSelectedItem();

        if (src == cbType) {
            updateFields();
        } else if (src == btnAdd) {
            try {
                double v = Double.parseDouble(tfValue.getText().trim());
                String name = tfName.getText().trim();
                if (type.equals("Bank Account")) {
                    bankData.add(new BankAccount(v));
                    area.append("Added Bank Account, balance " + v + "\n");
                } else {
                    if (name.length() == 0) {
                        JOptionPane.showMessageDialog(this, "Please enter a name.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (type.equals("Coin")) {
                        coinData.add(new Coin(v, name));
                        area.append("Added Coin " + name + ", value " + v + "\n");
                    } else {
                        studentData.add(new Student(name, v));
                        area.append("Added Student " + name + ", height " + v + "\n");
                    }
                }
                tfName.setText("");
                tfValue.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (src == btnResult) {
            DataSet ds;
            if (type.equals("Bank Account")) ds = bankData;
            else if (type.equals("Coin")) ds = coinData;
            else ds = studentData;

            Measurable max = ds.getMax();
            if (max == null) {
                area.append("No " + type + " data added yet.\n");
            } else {
                area.append("--- " + type + " result ---\n");
                area.append("Average : " + df.format(ds.getAverage()) + "\n");
                area.append("Highest : " + df.format(max.getMeasure()) + "\n");
                area.append("Name    : " + max.getName() + "\n");
            }
        } else if (src == btnReset) {
            bankData = new DataSet();
            coinData = new DataSet();
            studentData = new DataSet();
            area.setText("");
            tfName.setText("");
            tfValue.setText("");
        }
    }

    public static void main(String[] args) {
        DataSetGUI frame = new DataSetGUI();
        frame.setTitle("DataSet GUI");
        frame.setSize(430, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}