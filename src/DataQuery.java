/*
Programm: Die erste GUI
Autoren: Philipp Riefer, Domenic Heidemann
Datum: 12.01.2021
*/

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DataQuery extends JFrame implements ActionListener {

    private JComboBox honorificsBox;
    private JTextField name;
    private JTextField surname;
    private JRadioButton Mr;
    private JRadioButton Mrs;
    private JRadioButton none;
    private ButtonGroup salutationButtons;
    private JComboBox dayBox;
    private JComboBox monthBox;
    private JList yearsList;
    private JButton okButton;
    private JPanel salutationPanel;
    private JLabel resultLabel;
    private SimpleDateFormat format;
    private String honorificsList[] = { "", "Dr.", "Prof. Dr." };
    private Integer daysInteger[];
    private String monthsString[] = { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember" };
    private Integer yearsInteger[];

    public DataQuery() {
        super("Die erste GUI");

        // format for the date that is used later
        format= new SimpleDateFormat("EEEE, dd. MMMM yyyy");

        // creating GUI elements
        resultLabel = new JLabel("");

        Mr = new JRadioButton("Herr");
        Mrs = new JRadioButton("Frau");
        none = new JRadioButton("Keine Anrede", true);

        salutationButtons = new ButtonGroup();
        salutationButtons.add(Mr);
        salutationButtons.add(Mrs);
        salutationButtons.add(none);

        Mr.addActionListener(this);
        Mrs.addActionListener(this);
        none.addActionListener(this);

        // put the button group into one panel
        salutationPanel = new JPanel();
        salutationPanel.add(Mr);
        salutationPanel.add(Mrs);
        salutationPanel.add(none);
        
        honorificsBox = new JComboBox(honorificsList);

        name = new JTextField(50);
        surname = new JTextField(50);

        daysInteger = new Integer[31];
        for (int i = 1; i <= 31; i++) daysInteger[i-1] = i;
        dayBox = new JComboBox(daysInteger);

        monthBox = new JComboBox(monthsString);

        yearsInteger = new Integer[200];
        for (int i = 1910; i <= 2020; i++) yearsInteger[i - 1910] = i;
        yearsList = new JList(yearsInteger);

        // create the button
        okButton = new JButton("OK");

        // listen to the button
        okButton.addActionListener(this);

        // construction of the window
        setLayout(new GridLayout(0, 2, 10, 10));

        // now put all elements into the window panel
        // the order is from left to right - from top to bottom

        // first row
        add(new JLabel("Anrede:"));
        add(salutationPanel);

        // second row
        add(new JLabel("Titel:"));
        add(honorificsBox);

        // third row
        add(new JLabel("Vorname:"));
        add(name);

        // fourth row
        add(new JLabel("Nachname:"));
        add(surname);

        // fifth row
        add(new JLabel("Geburtsdatum: Tag:"));
        add(dayBox);

        // sixth row
        add(new JLabel("Geburtsdatum: Monat:"));
        add(monthBox);

        // seventh row
        add(new JLabel("Geburtsdatum: Jahr:"));
        add(new JScrollPane(yearsList));

        // eigth row
        add(new JLabel(""));
        add(okButton);

        // ninth row
        add(resultLabel);

        // make the window beautiful
        setDefaultCloseOperation(EXIT_ON_CLOSE); // close-button behaviour
        setSize(800, 400); // start with this
        setResizable(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double x = screenSize.getWidth()/2-300;
        Double y = screenSize.getHeight()/2-300;
        setLocation(x.intValue(), y.intValue());

        // showtime!
        setVisible(true);
    }

    public static void main(String[] args){
        new DataQuery();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == okButton) {

            // no name
            if (name.getText().isEmpty() == true) {
                System.out.println("error: vorname fehlt");
                return;
            }

            // no surname
            if (surname.getText().isEmpty() == true) {
                System.out.println("error: nachname fehlt");
                return;
            }
            
            Integer yearInteger = Integer.parseInt(yearsList.getSelectedValue().toString());
            GregorianCalendar cal = new GregorianCalendar(); // GregorianCalendar(year, month-1, day, hour, minute, second);

            // getSelectedIndex() starts at 0 of course
            if (dayBox.getSelectedIndex() == 29-1 && monthBox.getSelectedIndex() == 2-1 && cal.isLeapYear(yearInteger) == false|| // 29th feb without leap year
                dayBox.getSelectedIndex() == 30-1 && monthBox.getSelectedIndex() == 2-1 || // 30th feb
                dayBox.getSelectedIndex() == 31-1 && monthBox.getSelectedIndex() == 2-1 || // 31st feb
                dayBox.getSelectedIndex() == 31-1 && monthBox.getSelectedIndex() == 4-1 || // 31st apr
                dayBox.getSelectedIndex() == 31-1 && monthBox.getSelectedIndex() == 6-1 || // 31st jun
                dayBox.getSelectedIndex() == 31-1 && monthBox.getSelectedIndex() == 9-1 || // 31st sep
                dayBox.getSelectedIndex() == 31-1 && monthBox.getSelectedIndex() == 11-1) // 31st nov
            {
                System.out.println("error: ungültiges Datum");
                return;
            }

            // everything is correct

            // correct date formatting
            cal.set(yearsList.getSelectedIndex()+1910, monthBox.getSelectedIndex(), daysInteger[dayBox.getSelectedIndex()]);
            String calString = format.format(cal.getTime());

            // get radio button text
            String salutationSelection = "";
			if (Mr.isSelected()) {
				salutationSelection="Herr ";
			} else if (Mrs.isSelected()) {
				salutationSelection="Frau ";
			} else if (none.isSelected()) {
				salutationSelection="";
			} else {
				System.out.println("error: radiobuttons");
			}
            // display results
            resultLabel.setText(salutationSelection + " " + honorificsBox.getSelectedItem().toString() + " " + name.getText().toString() + " " + surname.getText().toString() + ", geboren am " + calString);
        }
    }
}
