/*
Programm: Die erste GUI
Autoren: Philipp Riefer, Domenic Heidemann
Datum: 14.01.2021
*/

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ResistanceCalculator extends JFrame implements ActionListener{

    private JRadioButton serial;
    private JRadioButton parallel;
    private ButtonGroup serialOrParallelButtons;
    private JButton okButton;
    private JComboBox prefixBoxOne;
    private JComboBox prefixBoxTwo;
    private JTextField firstResistorValue;
    private JTextField secondResistorValue;
    private JLabel simpleResult;
    private JLabel prefixResult;

    public ResistanceCalculator() {
        super("Resistance Calculator"); // title of the window

        // creating GUI elements
        serial = new JRadioButton("Serie", true);
        parallel = new JRadioButton("Parallel");

        serialOrParallelButtons = new ButtonGroup();
        serialOrParallelButtons.add(serial);
        serialOrParallelButtons.add(parallel);

        serial.addActionListener(this);
        parallel.addActionListener(this);

        String prefixString[] = {"nano", "mikro", "milli", "", "Kilo", "Mega", "Giga"};
        prefixBoxOne = new JComboBox(prefixString);
        prefixBoxTwo = new JComboBox(prefixString);

        firstResistorValue = new JTextField(50);
        secondResistorValue = new JTextField(50);

        simpleResult = new JLabel("");
        prefixResult = new JLabel("");

        // construction of the window
        setLayout(new GridLayout(0, 2, 10, 10));

        // create the button
        okButton = new JButton("Berechne!");

        // listen to the button
        okButton.addActionListener(this);

        // now put all elements into the window panel
        // the order is from left to right - from top to bottom

        // first row
        add(serial);
        add(parallel);

        // second row
        add(prefixBoxOne);
        add(firstResistorValue);

        // third row
        add(prefixBoxTwo);
        add(secondResistorValue);

        // fourth row
        add(new JLabel(""));
        add(okButton);

        // fifth row
        add(new JLabel("Ergebnis:"));
        add(simpleResult);

        // sixth row
        add(new JLabel("entspricht"));
        add(prefixResult);

        // make the window beautiful
        setDefaultCloseOperation(EXIT_ON_CLOSE); // close-button behaviour
        setSize(500, 300); // start with this
        setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = screenSize.getWidth()/2-250;
        Double y = screenSize.getHeight()/2-150;
        setLocation(x.intValue(), y.intValue());

        // showtime!
        setVisible(true);
    }

    public static void main(String[] args) {
        new ResistanceCalculator();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == okButton) {
            
            Double firstResistorValueDouble = Double.parseDouble(firstResistorValue.getText());
            if (prefixBoxOne.getSelectedItem() == "Giga") {
                firstResistorValueDouble = firstResistorValueDouble*1000000000;
            }else if(prefixBoxOne.getSelectedItem() == "Mega") {
                firstResistorValueDouble = firstResistorValueDouble*1000000;
            }else if(prefixBoxOne.getSelectedItem() == "Kilo") {
                firstResistorValueDouble = firstResistorValueDouble*1000;
            }else if(prefixBoxOne.getSelectedItem() == "") {
                firstResistorValueDouble = firstResistorValueDouble*1;
            }else if(prefixBoxOne.getSelectedItem() == "milli") {
                firstResistorValueDouble = firstResistorValueDouble/1000;
            }else if(prefixBoxOne.getSelectedItem() == "mikro") {
                firstResistorValueDouble = firstResistorValueDouble/1000000;
            }else if(prefixBoxOne.getSelectedItem() == "nano") {
                firstResistorValueDouble = firstResistorValueDouble/1000000000;
            }

            Double secondResistorValueDouble = Double.parseDouble(secondResistorValue.getText());
            if (prefixBoxTwo.getSelectedItem() == "Giga") {
                secondResistorValueDouble = secondResistorValueDouble*1000000000;
            }else if(prefixBoxTwo.getSelectedItem() == "Mega") {
                secondResistorValueDouble = secondResistorValueDouble*1000000;
            }else if(prefixBoxTwo.getSelectedItem() == "Kilo") {
                secondResistorValueDouble = secondResistorValueDouble*1000;
            }else if(prefixBoxTwo.getSelectedItem() == "") {
                secondResistorValueDouble = secondResistorValueDouble*1;
            }else if(prefixBoxTwo.getSelectedItem() == "milli") {
                secondResistorValueDouble = secondResistorValueDouble/1000;
            }else if(prefixBoxTwo.getSelectedItem() == "mikro") {
                secondResistorValueDouble = secondResistorValueDouble/1000000;
            }else if(prefixBoxTwo.getSelectedItem() == "nano") {
                secondResistorValueDouble = secondResistorValueDouble/1000000000;
            }
            
            if (serial.isSelected() == true) {
                Double totalResistorValueDouble = firstResistorValueDouble + secondResistorValueDouble;

                simpleResult.setText(totalResistorValueDouble.toString()+" Ω");

                if (totalResistorValueDouble >= 1000000000) { // GIGA and everything above
                    totalResistorValueDouble = totalResistorValueDouble/1000000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" GΩ");
                }else if(totalResistorValueDouble >= 1000000){ // MEGA
                    totalResistorValueDouble = totalResistorValueDouble/1000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" MΩ");
                }else if(totalResistorValueDouble >= 1000){ // KILO
                    totalResistorValueDouble = totalResistorValueDouble/1000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" kΩ");
                }else if(totalResistorValueDouble >= 1){ // NONE
                    totalResistorValueDouble = totalResistorValueDouble*1;
                    prefixResult.setText(totalResistorValueDouble.toString()+" Ω");
                }else if(totalResistorValueDouble >= 0.001){ // MILLI
                    totalResistorValueDouble = totalResistorValueDouble*1000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" mΩ");
                }else if(totalResistorValueDouble >= 0.000001){ // MIKRO
                    totalResistorValueDouble = totalResistorValueDouble*1000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" µΩ");
                }else if(totalResistorValueDouble < 0.000001){ // NANO and everything below
                    totalResistorValueDouble = totalResistorValueDouble*1000000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" nΩ");
                }

            } else if(parallel.isSelected() == true){
                Double totalResistorValueDouble = (firstResistorValueDouble*secondResistorValueDouble)/(firstResistorValueDouble+secondResistorValueDouble);

                simpleResult.setText(totalResistorValueDouble.toString()+" Ω");
                
                if (totalResistorValueDouble >= 1000000000) { // GIGA and everything above
                    totalResistorValueDouble = totalResistorValueDouble/1000000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" GΩ");
                }else if(totalResistorValueDouble >= 1000000){ // MEGA
                    totalResistorValueDouble = totalResistorValueDouble/1000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" MΩ");
                }else if(totalResistorValueDouble >= 1000){ // KILO
                    totalResistorValueDouble = totalResistorValueDouble/1000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" kΩ");
                }else if(totalResistorValueDouble >= 1){ // NONE
                    totalResistorValueDouble = totalResistorValueDouble*1;
                    prefixResult.setText(totalResistorValueDouble.toString()+" Ω");
                }else if(totalResistorValueDouble >= 0.001){ // MILLI
                    totalResistorValueDouble = totalResistorValueDouble*1000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" mΩ");
                }else if(totalResistorValueDouble >= 0.000001){ // MIKRO
                    totalResistorValueDouble = totalResistorValueDouble*1000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" µΩ");
                }else if(totalResistorValueDouble < 0.000001){ // NANO and everything below
                    totalResistorValueDouble = totalResistorValueDouble*1000000000;
                    prefixResult.setText(totalResistorValueDouble.toString()+" nΩ");
                }
            } else {
                System.out.println("error: neither serial nor parallel is selected");
            }
        }
    }
}