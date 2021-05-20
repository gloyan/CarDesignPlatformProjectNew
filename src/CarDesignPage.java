import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarDesignPage extends JFrame implements ActionListener {
    static String selectedBrand;
    static String selectedModel;

    private JButton lastColorButtonPressed;
    private JButton lastRimButtonPressed;

    ImageIcon carIcon1 = new ImageIcon("src/SEN2212_Images (3)/SEN2212_Images/" + selectedBrand + "/" + selectedModel + "/" + selectedBrand + selectedModel + "BlackRim1.jpg");
    ImageIcon carIcon2 = new ImageIcon("src/SEN2212_Images (3)/SEN2212_Images/" + selectedBrand + "/" + selectedModel + "/" + selectedBrand + selectedModel + "BlackRim2.jpg");
    ImageIcon carIcon3 = new ImageIcon("src/SEN2212_Images (3)/SEN2212_Images/" + selectedBrand + "/" + selectedModel + "/" + selectedBrand + selectedModel + "BlueRim1.jpg");
    ImageIcon carIcon4 = new ImageIcon("src/SEN2212_Images (3)/SEN2212_Images/" + selectedBrand + "/" + selectedModel + "/" + selectedBrand + selectedModel + "BlueRim2.jpg");
    ImageIcon carIcon5 = new ImageIcon("src/SEN2212_Images (3)/SEN2212_Images/" + selectedBrand + "/" + selectedModel + "/" + selectedBrand + selectedModel + "GreyRim1.jpg");
    ImageIcon carIcon6 = new ImageIcon("src/SEN2212_Images (3)/SEN2212_Images/" + selectedBrand + "/" + selectedModel + "/" + selectedBrand + selectedModel + "GreyRim2.jpg");

    JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel pnlWest = new JPanel(new FlowLayout(FlowLayout.LEFT));

    JLabel carImage = new JLabel(carIcon1);
    JLabel carImage2 = new JLabel(carIcon2);
    JLabel carImage3 = new JLabel(carIcon3);
    JLabel carImage4 = new JLabel(carIcon4);
    JLabel carImage5 = new JLabel(carIcon5);
    JLabel carImage6 = new JLabel(carIcon6);

    JButton blackButton = new JButton();
    JButton blueButton = new JButton();
    JButton greyButton = new JButton();
    JButton rimButtonType1 = new JButton("Rim1");
    JButton rimButtonType2 = new JButton("Rim2");
    JLabel colorLabel = new JLabel("Colors");
    JLabel rimLabel = new JLabel("Rims");


    public CarDesignPage() {

        super("Car Design Page");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(1500, 750);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        carImage.setPreferredSize(new Dimension(1400, 700));
        carImage2.setPreferredSize(new Dimension(1400, 700));
        carImage3.setPreferredSize(new Dimension(1400, 700));
        carImage4.setPreferredSize(new Dimension(1400, 700));
        carImage5.setPreferredSize(new Dimension(1400, 700));
        carImage6.setPreferredSize(new Dimension(1400, 700));

        pnlCenter.setBackground(Color.WHITE);
        add(pnlCenter, BorderLayout.CENTER);

        colorLabel.setFont(new Font("Serif", Font.BOLD, 25));
        pnlNorth.add(colorLabel);
        blackButton.setBackground(Color.black);
        blackButton.setPreferredSize(new Dimension(50, 25));
        pnlNorth.add(blackButton);
        blueButton.setBackground(Color.blue);
        blueButton.setPreferredSize(new Dimension(50, 25));
        pnlNorth.add(blueButton);
        greyButton.setBackground(Color.gray);
        greyButton.setPreferredSize(new Dimension(50, 25));
        pnlNorth.add(greyButton);
        pnlNorth.add(new JLabel(""));
        pnlNorth.add(new JLabel(""));
        rimLabel.setFont(new Font("Serif", Font.BOLD, 25));
        pnlNorth.add(rimLabel);
        pnlNorth.add(rimButtonType1);
        pnlNorth.add(rimButtonType2);
        pnlNorth.setBackground(Color.WHITE);
        add(pnlNorth, BorderLayout.NORTH);

        pnlWest.setBackground(Color.WHITE);

        add(pnlWest, BorderLayout.WEST);

        pnlSouth.setBackground(Color.WHITE);
        lastColorButtonPressed = blackButton;
        lastRimButtonPressed = rimButtonType1;
        pnlSouth.add(carImage);
        add(pnlSouth, BorderLayout.SOUTH);


        blackButton.addActionListener(this);
        blueButton.addActionListener(this);
        greyButton.addActionListener(this);
        rimButtonType1.addActionListener(this);
        rimButtonType2.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        pnlSouth.removeAll();

        if (e.getSource() == blackButton && lastRimButtonPressed == rimButtonType1) {
            pnlSouth.add(carImage);
            lastColorButtonPressed = blackButton;
            lastRimButtonPressed = rimButtonType1;
        }
        if (e.getSource() == blackButton && lastRimButtonPressed == rimButtonType2) {
            pnlSouth.add(carImage2);
            lastColorButtonPressed = blackButton;
            lastRimButtonPressed = rimButtonType2;
        }
        if (e.getSource() == blueButton && lastRimButtonPressed == rimButtonType1) {
            pnlSouth.add(carImage3);
            lastColorButtonPressed = blueButton;
            lastRimButtonPressed = rimButtonType1;
        }
        if (e.getSource() == blueButton && lastRimButtonPressed == rimButtonType2) {
            pnlSouth.add(carImage4);
            lastColorButtonPressed = blueButton;
            lastRimButtonPressed = rimButtonType2;
        }
        if (e.getSource() == greyButton && lastRimButtonPressed == rimButtonType1) {
            pnlSouth.add(carImage5);
            lastColorButtonPressed = greyButton;
            lastRimButtonPressed = rimButtonType1;
        }
        if (e.getSource() == greyButton && lastRimButtonPressed == rimButtonType2) {
            pnlSouth.add(carImage6);
            lastColorButtonPressed = greyButton;
            lastRimButtonPressed = rimButtonType2;
        }
        if (e.getSource() == rimButtonType1 && lastColorButtonPressed == blackButton) {
            pnlSouth.add(carImage);
            lastColorButtonPressed = blackButton;
            lastRimButtonPressed = rimButtonType1;
        }
        if (e.getSource() == rimButtonType1 && lastColorButtonPressed == blueButton) {
            pnlSouth.add(carImage3);
            lastColorButtonPressed = blueButton;
            lastRimButtonPressed = rimButtonType1;
        }
        if (e.getSource() == rimButtonType1 && lastColorButtonPressed == greyButton) {
            pnlSouth.add(carImage5);
            lastColorButtonPressed = greyButton;
            lastRimButtonPressed = rimButtonType1;
        }
        if (e.getSource() == rimButtonType2 && lastColorButtonPressed == blackButton) {
            pnlSouth.add(carImage2);
            add(pnlSouth, BorderLayout.SOUTH);
            lastColorButtonPressed = blackButton;
            lastRimButtonPressed = rimButtonType2;
        }
        if (e.getSource() == rimButtonType2 && lastColorButtonPressed == blueButton) {
            pnlSouth.add(carImage4);
            add(pnlSouth, BorderLayout.SOUTH);
            lastColorButtonPressed = blueButton;
            lastRimButtonPressed = rimButtonType2;
        }
        if (e.getSource() == rimButtonType2 && lastColorButtonPressed == greyButton) {
            pnlSouth.add(carImage6);
            add(pnlSouth, BorderLayout.SOUTH);
            lastColorButtonPressed = greyButton;
            lastRimButtonPressed = rimButtonType2;
        }
        if (getSize().getWidth() >= 1509 && getSize().getHeight() >= 756)
            setSize(1500, 750);
        else
            setSize((getWidth() * 500) / 499, (getHeight() * 500) / 499);
        add(pnlSouth, BorderLayout.SOUTH);
    }
}

