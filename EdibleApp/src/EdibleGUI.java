import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EdibleGUI extends JFrame implements ActionListener {
    private JTextField tfPerson, tfFood;
    private JComboBox<String> cbType;
    private JButton btnEat, btnClear;
    private JTextArea area;

    public EdibleGUI() {
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout(5, 5));

        JPanel input = new JPanel(new GridLayout(3, 2, 5, 5));
        tfPerson = new JTextField(10);
        cbType = new JComboBox<String>(new String[]{"Animal", "Apple", "Orange"});
        tfFood = new JTextField(10);
        input.add(new JLabel("Person's name :"));
        input.add(tfPerson);
        input.add(new JLabel("Edible type :"));
        input.add(cbType);
        input.add(new JLabel("Food name :"));
        input.add(tfFood);

        JPanel buttons = new JPanel(new FlowLayout());
        btnEat = new JButton("Eat");
        btnClear = new JButton("Clear");
        buttons.add(btnEat);
        buttons.add(btnClear);

        area = new JTextArea(8, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JPanel north = new JPanel(new BorderLayout());
        north.add(input, BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);
        pane.add(north, BorderLayout.NORTH);
        pane.add(new JScrollPane(area), BorderLayout.CENTER);

        btnEat.addActionListener(this);
        btnClear.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnEat) {
            String person = tfPerson.getText().trim();
            String food = tfFood.getText().trim();
            if (person.length() == 0 || food.length() == 0) {
                JOptionPane.showMessageDialog(this, "Please enter the person's name and the food name.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String type = (String) cbType.getSelectedItem();
            Edible edible;
            if (type.equals("Animal")) edible = new Animal(food);
            else if (type.equals("Apple")) edible = new Apple(food);
            else edible = new Orange(food);

            Person p = new Person(person);
            area.append(p.eating(edible) + "\n");   // polymorphism: howToEat() depends on the object
        } else if (src == btnClear) {
            area.setText("");
            tfPerson.setText("");
            tfFood.setText("");
        }
    }

    public static void main(String[] args) {
        EdibleGUI frame = new EdibleGUI();
        frame.setTitle("Edible GUI");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}