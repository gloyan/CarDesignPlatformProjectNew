import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class CarSelectionPage extends JFrame {
    GeneralTreeNode root = new GeneralTreeNode();

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


        add(new JLabel("Select a car brand"));
        add(comboBoxBrands);
        add(brandButton);

        brandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLayout(new GridLayout(2, 3));
                add(new JLabel("Select a car model"));
                CarDesignPage.selectedBrand = comboBoxBrands.getSelectedItem().toString();

                if (comboBoxBrands.getSelectedItem().equals(brands[0])) {
                    comboBoxModels.addItem(models[0]);
                    comboBoxModels.addItem(models[1]);
                    brandButton.setEnabled(false);
                    comboBoxBrands.setEnabled(false);
                    add(comboBoxModels);
                    add(modelButton);
                } else if (comboBoxBrands.getSelectedItem().equals(brands[1])) {
                    comboBoxModels.addItem(models[2]);
                    comboBoxModels.addItem(models[3]);
                    brandButton.setEnabled(false);
                    comboBoxBrands.setEnabled(false);
                    add(comboBoxModels);
                    add(modelButton);
                } else {
                    comboBoxModels.addItem(models[4]);
                    comboBoxModels.addItem(models[5]);
                    add(comboBoxModels);
                    brandButton.setEnabled(false);
                    comboBoxBrands.setEnabled(false);
                    add(modelButton);
                }
                setSize((getWidth() * 5) / 4, (getHeight() * 5) / 4);
                setLocationRelativeTo(null);
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
