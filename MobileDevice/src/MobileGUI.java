import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MobileGUI extends JFrame implements ActionListener {
    private JComboBox<String> cbType;
    private JTextField tfManufacturer, tfModel, tfPhoneType, tfStation, tfFrequency;
    private JLabel lblPhoneType;
    private JButton btnShow, btnClear;
    private JTextArea area;

    public MobileGUI() {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout(5, 5));

        JPanel input = new JPanel(new GridLayout(6, 2, 5, 5));
        cbType = new JComboBox<String>(new String[]{"SmartPhone", "BasicPhone"});
        tfManufacturer = new JTextField(10);
        tfModel = new JTextField(10);
        lblPhoneType = new JLabel("Type :");
        tfPhoneType = new JTextField(10);
        tfStation = new JTextField(10);
        tfFrequency = new JTextField(10);
        input.add(new JLabel("Device :"));
        input.add(cbType);
        input.add(new JLabel("Manufacturer :"));
        input.add(tfManufacturer);
        input.add(new JLabel("Model :"));
        input.add(tfModel);
        input.add(lblPhoneType);
        input.add(tfPhoneType);
        input.add(new JLabel("Radio station :"));
        input.add(tfStation);
        input.add(new JLabel("Frequency :"));
        input.add(tfFrequency);

        JPanel buttons = new JPanel(new FlowLayout());
        btnShow = new JButton("Show Details");
        btnClear = new JButton("Clear");
        buttons.add(btnShow);
        buttons.add(btnClear);

        area = new JTextArea(8, 30);
        area.setEditable(false);

        JPanel north = new JPanel(new BorderLayout());
        north.add(input, BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);
        pane.add(north, BorderLayout.NORTH);
        pane.add(new JScrollPane(area), BorderLayout.CENTER);

        cbType.addActionListener(this);
        btnShow.addActionListener(this);
        btnClear.addActionListener(this);
    }

    // printDetails() prints to the console, so we capture that text to show it in the GUI
    private String capture(MobileDevice m) {
        PrintStream old = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            m.printDetails();
        } finally {
            System.setOut(old);
        }
        return buffer.toString();
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == cbType) {
            boolean smart = cbType.getSelectedItem().equals("SmartPhone");
            tfPhoneType.setEnabled(smart);
            lblPhoneType.setEnabled(smart);
            if (!smart) tfPhoneType.setText("");
        } else if (src == btnShow) {
            try {
                String manu = tfManufacturer.getText().trim();
                String model = tfModel.getText().trim();
                String station = tfStation.getText().trim();
                double freq = Double.parseDouble(tfFrequency.getText().trim());
                if (manu.length() == 0 || model.length() == 0) {
                    JOptionPane.showMessageDialog(this, "Please enter manufacturer and model.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                MobileDevice m;
                if (cbType.getSelectedItem().equals("SmartPhone")) {
                    m = new SmartPhone(manu, model, tfPhoneType.getText().trim());
                } else {
                    m = new BasicPhone(manu, model);
                }
                m.setStation(station);
                m.setFrequency(freq);

                area.append(capture(m));
                area.append("Station: " + m.station + "\n");
                area.append("Frequency: " + m.frequency + "\n\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Frequency must be a number.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (src == btnClear) {
            area.setText("");
            tfManufacturer.setText("");
            tfModel.setText("");
            tfPhoneType.setText("");
            tfStation.setText("");
            tfFrequency.setText("");
        }
    }

    public static void main(String[] args) {
        MobileGUI frame = new MobileGUI();
        frame.setTitle("Mobile Device GUI");
        frame.setSize(430, 470);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}