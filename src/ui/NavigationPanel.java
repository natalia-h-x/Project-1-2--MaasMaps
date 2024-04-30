package ui;

import javax.swing.*;

import core.Context;
import core.Constants.UIConstants;
import core.database.ZipCodeDatabase;
import core.managers.ExceptionManager;
import core.models.Location;
import core.models.transport.Biking;
import core.models.transport.TransportMode;
import core.models.transport.Walking;
import ui.map.geometry.Line;
import ui.map.geometry.Marker;
import ui.map.geometry.MarkerFactory;

import java.awt.*;

/**
 * This class represents the side navigation panel in the UI
 *
 * @author Natalia Hadjisoteriou
 */
public class NavigationPanel extends JPanel {
    private JLabel timeLabel;

    public NavigationPanel() {
        initialiseNavigationUI();
    }

    private void initialiseNavigationUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createMatteBorder(UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE,
                                                  UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BACKGROUND_COLOR));
        setForeground(UIConstants.GUI_BACKGROUND_COLOR);
        setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        // create navigation side JPanel
        JPanel navigationButtons = new JPanel();
        navigationButtons.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        navigationButtons.setLayout(new GridLayout(2, 0, 0, UIConstants.GUI_BORDER_SIZE));

        // create postal codes' fields
        JLabel location1 = new JLabel("From: ");
        location1.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));

        JTextField textField1 = new JTextField("Enter postal code", 8);
        JLabel location2 = new JLabel("To: ");
        location2.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));

        JTextField textField2 = new JTextField("Enter postal code", 8);
        JButton calculate = new JButton("Calculate");
        calculate.setBackground(UIConstants.GUI_HIGHLIGHT_BACKGROUND_COLOR);
        calculate.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);

        //arrange text fields to jpanels
        JPanel panel1= new JPanel();
        JPanel panel2= new JPanel();
        panel1.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        panel2.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        panel1.setLayout(new GridLayout(2, 0, 0, UIConstants.GUI_BORDER_SIZE / 2));
        panel2.setLayout(new GridLayout(2, 0, 0, UIConstants.GUI_BORDER_SIZE / 2));
        panel1.add(location1);
        panel1.add(location2);
        panel2.add(textField1);
        panel2.add(textField2);

        JLabel title = new JLabel("Maas maps");
        title.setForeground(UIConstants.GUI_TITLE_COLOR);
        title.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TITLE_FONT_SIZE));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel(UIConstants.GUI_TIME_LABEL_TEXT);
        timeLabel.setFont(new Font(" ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));
        // for later : JLabel resultLabel = new JLabel()
        // calls calculator methods or whatever to get time result

        // Create Combo Box header
        JLabel transportType = new JLabel("Select means of transport: ");
        transportType.setFont(new Font("Select means of transport: ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // create combo box
        String[] options = {"Walking", "Biking"};
        JComboBox<String> selection = new JComboBox<>(options);
        selection.setBackground(UIConstants.GUI_ACCENT_COLOR);
        selection.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);

        // create bottomPanel to hold label3, selection, and calculate button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel selectionPanel = new JPanel(new BorderLayout());
        JButton clearButton = new JButton("Clear Map");
        clearButton.setBackground(UIConstants.GUI_TITLE_COLOR);
        clearButton.setForeground(Color.WHITE);
        selectionPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        bottomPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        selectionPanel.add(transportType, BorderLayout.WEST);
        selectionPanel.add(selection, BorderLayout.CENTER);
        selectionPanel.add(calculate, BorderLayout.EAST);
        bottomPanel.add(timeLabel, BorderLayout.CENTER);
        bottomPanel.add(clearButton, BorderLayout.SOUTH);
        bottomPanel.add(selectionPanel, BorderLayout.NORTH);

        JPanel zipCodeSelectionPanel = new JPanel(new BorderLayout());
        zipCodeSelectionPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        zipCodeSelectionPanel.add(panel1, BorderLayout.WEST);
        zipCodeSelectionPanel.add(panel2, BorderLayout.CENTER);

        // add components to the navigation panel
        navigationButtons.add(title);
        navigationButtons.add(zipCodeSelectionPanel);

        // add split pane to the frame
        add(navigationButtons, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        addActionListeners(textField1, textField2, calculate, selection);
        addClearActionListener(clearButton);
    }

    private void addActionListeners(JTextField textField1, JTextField textField2, JButton calculate,
            JComboBox<String> selection) {

        ZipCodeDatabase db = Context.getContext().getZipCodeDatabase();

        calculate.addActionListener(e -> {
            TransportMode transportMode;
            Line line;
            Marker startPoint;
            Marker endPoint;

            switch ((String) selection.getSelectedItem()) {
                case "Walking": transportMode = new Walking(); break;
                case "Biking": transportMode = new Biking(); break;
                default: throw new IllegalArgumentException("The Transport Mode %s is not supported!".formatted(selection.getSelectedItem()));
            }

            Context.getContext().getMap().clearIcons();

            try {
                Location locationA = db.getLocation(textField1.getText());
                Location locationB = db.getLocation(textField2.getText());

                line = new Line(
                        locationA,
                        locationB
                    );

                startPoint = MarkerFactory.createAImageMarker(locationA);
                endPoint = MarkerFactory.createBImageMarker(locationB);

                Context.getContext().getMap().addMapIcon(line, startPoint, endPoint);

                double time = transportMode.calculateTravelTime(locationA, locationB);
                double seconds = (time - (int) (time))*60;
                timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT + (int) (time) + " min " + Math.round(seconds) + " seconds");
            }
            catch (Exception ex) {
                ExceptionManager.handle(this, ex);
            }
        });
    }

    private void addClearActionListener(JButton clearButton){
        clearButton.addActionListener(e -> {
            Context.getContext().getMap().clearIcons();
            timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT);
        });
    }
}
