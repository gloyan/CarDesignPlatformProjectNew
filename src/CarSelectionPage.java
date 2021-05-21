import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CarSelectionPage extends JFrame {

    GeneralTreeNode root = new GeneralTreeNode();

    JLabel brandLabel = new JLabel("Brands");
    JLabel modelLabel = new JLabel("Models");
    JButton brandButton = new JButton("Select Brand");
    JButton modelButton = new JButton("Select Model");

    public CarSelectionPage() {

        super("Car Selection Page");
        setSize(1000, 600);
        setLayout(new GridLayout(1, 3));
        setLocationRelativeTo(null);
        setBackground(Color.BLUE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        root.key = "Car";
        (root.child).add(GeneralTreeNode.newNode("BMW"));
        (root.child).add(GeneralTreeNode.newNode("Audi"));
        (root.child).add(GeneralTreeNode.newNode("Mercedes"));
        (root.child.get(0).child).add(GeneralTreeNode.newNode("X6"));
        (root.child.get(0).child).add(GeneralTreeNode.newNode("M8"));
        (root.child.get(1).child).add(GeneralTreeNode.newNode("TT"));
        (root.child.get(1).child).add(GeneralTreeNode.newNode("Q8"));
        (root.child.get(2).child).add(GeneralTreeNode.newNode("CLA"));
        (root.child.get(2).child).add(GeneralTreeNode.newNode("GSeries"));

        String[] brands = {root.child.get(0).key, root.child.get(1).key, root.child.get(2).key};
        String[] models = {root.child.get(0).child.get(0).key, root.child.get(0).child.get(1).key,
                root.child.get(1).child.get(0).key, root.child.get(1).child.get(1).key,
                root.child.get(2).child.get(0).key, root.child.get(2).child.get(1).key};

        JComboBox comboBoxBrands = new JComboBox(brands);
        JComboBox comboBoxModels = new JComboBox();

        comboBoxBrands.setFont(new Font("Calibri", Font.BOLD, 25));
        comboBoxModels.setFont(new Font("Calibri", Font.BOLD, 25));
        brandLabel.setFont(new Font("Calibri", Font.BOLD, 25));
        modelLabel.setFont(new Font("Calibri", Font.BOLD, 25));
        brandButton.setFont(new Font("Calibri", Font.BOLD, 25));
        modelButton.setFont(new Font("Calibri", Font.BOLD, 25));

        add(brandLabel);
        add(comboBoxBrands);
        add(brandButton);

        brandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLayout(new GridLayout(2, 3));
                comboBoxModels.removeAllItems();
                add(modelLabel);
                CarDesignPage.selectedBrand = comboBoxBrands.getSelectedItem().toString();

                if (comboBoxBrands.getSelectedItem().equals(brands[0])) {
                    comboBoxModels.addItem(models[0]);
                    comboBoxModels.addItem(models[1]);
                }
                else if (comboBoxBrands.getSelectedItem().equals(brands[1])) {
                    comboBoxModels.addItem(models[2]);
                    comboBoxModels.addItem(models[3]);

                }
                else {
                    comboBoxModels.addItem(models[4]);
                    comboBoxModels.addItem(models[5]);
                }

                add(comboBoxModels);
                add(modelButton);
                setSize((getWidth() * 500) / 499, (getHeight() * 500) / 499);
                setLocationRelativeTo(null);
                JOptionPane.showMessageDialog(null, "First select car model from combobox then click to Select Model button");

            }
        });

        modelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (comboBoxModels.getSelectedItem() != null) {
                    CarDesignPage.selectedModel = comboBoxModels.getSelectedItem().toString();
                }
                new CarDesignPage().setVisible(true);
            }
        });

    }

    public static void main(String[] args) {
        new CarSelectionPage().setVisible(true);

    }
}
