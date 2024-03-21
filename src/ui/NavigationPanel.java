package ui;

import javax.swing.*;

import core.Context;
import database.ZipCodeDatabaseInteractor;
import transport.Biking;
import transport.TransportMode;
import transport.Walking;
import ui.map.geometry.Line;

import java.awt.*;

public class NavigationPanel extends JPanel {
    private static final int GUI_BORDER_SIZE = 25;
    private static final String GUI_FONT_FAMILY = "Arial";
    private static final String GUI_TIME_LABEL_TEXT = "Average time needed for this distance: ";
    private static final int GUI_TITLE_FONT_SIZE      = 40;
    private static final int GUI_TEXT_FIELD_FONT_SIZE = 15;
    private static final int GUI_INFO_FONT_SIZE       = 13;
    private static final Color GUI_TITLE_COLOR                = new Color(87 , 100 , 144);
    private static final Color GUI_ACCENT_COLOR               = new Color(53 , 80  , 112);
    private static final Color GUI_HIGHLIGHT_COLOR            = new Color(237, 242 , 244);
    private static final Color GUI_HIGHLIGHT_BACKGROUND_COLOR = new Color(119, 150 , 203);
    private static final Color GUI_BACKGROUND_COLOR           = new Color(201, 202 , 217);
    private JLabel timeLabel;

    public NavigationPanel() {
        initialiseNavigationUI();
    }

    private void initialiseNavigationUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(GUI_BORDER_SIZE, GUI_BORDER_SIZE, GUI_BORDER_SIZE, GUI_BORDER_SIZE, GUI_BACKGROUND_COLOR));
        setForeground(GUI_BACKGROUND_COLOR);
        setBackground(GUI_BACKGROUND_COLOR);

        // create navigation side JPanel
        JPanel navigationButtons = new JPanel();
        navigationButtons.setBackground(GUI_BACKGROUND_COLOR);
        navigationButtons.setLayout(new GridLayout(5, 0, 0, GUI_BORDER_SIZE));

        // create postal codes' fields
        JLabel location1 = new JLabel("From: ");
        location1.setFont(new Font(GUI_FONT_FAMILY, Font.BOLD, GUI_TEXT_FIELD_FONT_SIZE));

        JTextField textField1 = new JTextField("Enter postal code", 8);
        JLabel location2 = new JLabel("To: ");
        location2.setFont(new Font(GUI_FONT_FAMILY, Font.BOLD, GUI_TEXT_FIELD_FONT_SIZE));

        JTextField textField2 = new JTextField("Enter postal code", 8);
        JButton calculate = new JButton("Calculate");
        calculate.setBackground(GUI_HIGHLIGHT_BACKGROUND_COLOR);
        calculate.setForeground(GUI_HIGHLIGHT_COLOR);

        JLabel title = new JLabel("Maas maps");
        title.setForeground(GUI_TITLE_COLOR);
        title.setFont(new Font(GUI_FONT_FAMILY, Font.BOLD, GUI_TITLE_FONT_SIZE));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel(GUI_TIME_LABEL_TEXT);
        timeLabel.setFont(new Font(" ", Font.BOLD, GUI_INFO_FONT_SIZE));
        // for later : JLabel resultLabel = new JLabel()
        // calls calculator methods or whatever to get time result

        // Create Combo Box header
        JLabel transportType = new JLabel("Select means of transport: ");
        transportType.setFont(new Font("Select means of transport: ", Font.BOLD, GUI_INFO_FONT_SIZE));

        // create combo box
        String[] options = {"Walking", "Biking"};
        JComboBox<String> selection = new JComboBox<>(options);
        selection.setBackground(GUI_ACCENT_COLOR);
        selection.setForeground(GUI_HIGHLIGHT_COLOR);

        // create middlePanel to hold label1 and textField1
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(GUI_BACKGROUND_COLOR);
        topPanel.add(location1, BorderLayout.WEST);
        topPanel.add(textField1, BorderLayout.CENTER);

        // create middlePanel to hold label2 and textField2
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(GUI_BACKGROUND_COLOR);
        middlePanel.add(location2, BorderLayout.WEST);
        middlePanel.add(textField2, BorderLayout.CENTER);

        // create bottomPanel to hold label3, selection, and calculate button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(GUI_BACKGROUND_COLOR);
        bottomPanel.add(transportType, BorderLayout.WEST);
        bottomPanel.add(selection, BorderLayout.CENTER);
        bottomPanel.add(calculate, BorderLayout.EAST);
        bottomPanel.add(timeLabel, BorderLayout.SOUTH);

        JPanel zipCodeSelectionPanel = new JPanel(new GridLayout(2, 0, 0, GUI_BORDER_SIZE / 2));
        zipCodeSelectionPanel.setBackground(GUI_BACKGROUND_COLOR);
        zipCodeSelectionPanel.add(topPanel);
        zipCodeSelectionPanel.add(middlePanel);

        // add components to the navigation panel
        navigationButtons.add(title);
        navigationButtons.add(zipCodeSelectionPanel);


        // add split pane to the frame
        add(navigationButtons, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        addActionListeners(textField1, textField2, calculate, selection);

        //set visible
        setVisible(true);
    }

    private void addActionListeners(JTextField textField1, JTextField textField2, JButton calculate,
            JComboBox<String> selection) {

        ZipCodeDatabaseInteractor db = ZipCodeDatabaseInteractor.getZipCodeDatabaseInteractor();

        calculate.addActionListener(e -> {
            TransportMode transportMode;
            Line line;

            switch ((String) selection.getSelectedItem()) {
                case "Walking": transportMode = new Walking(); break;
                case "Biking": transportMode = new Biking(); break;
                default: throw new IllegalArgumentException("The Transport Mode %s is not valid!".formatted(selection.getSelectedItem()));
            }

            Context.getContext().getMap().addMapIcon(
                line = new Line(
                    db.getLocation(textField1.getText()),
                    db.getLocation(textField2.getText())
                )
            );

            double time = transportMode.getAverageSpeed() * line.getTotalDistance();
            timeLabel.setText(GUI_TIME_LABEL_TEXT + String.valueOf(time));
        });
    }
}
