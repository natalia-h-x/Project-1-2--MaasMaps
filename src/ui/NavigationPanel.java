package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.Constants;
import core.Constants.UIConstants;
import core.Context;
import core.managers.ExceptionManager;
import core.managers.MapManager;
import core.models.Time;
import core.models.transport.Biking;
import core.models.transport.Bus;
import core.models.transport.TransportMode;
import core.models.transport.Walking;
import core.zipcode.ZipCodeDatabase;

/**
 * This class represents the side navigation panel in the UI
 */
public class NavigationPanel extends JPanel {
    private JLabel timeLabel;
    private transient TransportMode[] options = {
        new Walking(),
        new Biking(),
        new Bus()
    };

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

        JTextField textField1 = new JTextField(MapManager.getRandomPostalCode(), 8);
        JLabel location2 = new JLabel("To: ");
        location2.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));

        JTextField textField2 = new JTextField(MapManager.getRandomPostalCode(), 8);
        JButton calculate = new JButton("Calculate");
        calculate.setBackground(UIConstants.GUI_HIGHLIGHT_BACKGROUND_COLOR);
        calculate.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);

        // create departure time label and field
        JLabel departure = new JLabel("Departure time: ");
        departure.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));
        JTextField departureField = new JTextField();
        departureField.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);
        departureField.addActionListener(e -> {
            for (TransportMode option : options) {
                if (option instanceof Bus b)
                    b.setDepartingTime(Time.of(departureField.getText()));
            }
        });

        // create an empty panel for spacing
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new java.awt.Dimension(0, 10));
        spacerPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        // create an empty panel 2 for spacing
        JPanel spacerPanel2 = new JPanel();
        spacerPanel2.setPreferredSize(new java.awt.Dimension(0, 10));
        spacerPanel2.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        // create search radius label and field
        JLabel search = new JLabel("Search radius: ");
        search.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));
        JTextField radiusField = new JTextField(Constants.Map.POSTAL_CODE_MAX_SEARCH_RADIUS);
        radiusField.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);
        radiusField.addActionListener(e -> Integer.parseInt(radiusField.getText()));

        // randomize bus stops button
        JButton busRandom = new JButton("Randomize bus stops");
        busRandom.setPreferredSize(new Dimension(10, 25));
        busRandom.setBackground(UIConstants.GUI_BUTTON_COLOR);
        busRandom.setForeground(Color.WHITE);
        busRandom.addActionListener(e -> {
            textField1.setText(MapManager.getRandomPostalCode());
            textField2.setText(MapManager.getRandomPostalCode());
        });

        // arrange text fields to jpanels
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        panel2.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        panel1.setLayout(new GridLayout(6, 0, 0, UIConstants.GUI_BORDER_SIZE / 2));
        panel2.setLayout(new GridLayout(6, 0, 0, UIConstants.GUI_BORDER_SIZE / 2));
        panel1.add(location1);
        panel1.add(location2);
        panel1.add(departure);
        panel1.add(spacerPanel);
        panel1.add(search);
        panel2.add(textField1);
        panel2.add(textField2);
        panel2.add(departureField);
        panel2.add(spacerPanel2);
        panel2.add(radiusField);
        panel2.add(busRandom, BorderLayout.CENTER);

        JLabel title = new JLabel("Maas maps");
        title.setForeground(UIConstants.GUI_TITLE_COLOR);
        title.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TITLE_FONT_SIZE));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel(UIConstants.GUI_TIME_LABEL_TEXT);
        timeLabel.setFont(new Font(" ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // Create Check Box header
        JLabel checkTransfer = new JLabel("Select if you want to include bus transfers: ");
        checkTransfer.setFont(new Font("Select if you want to include bus transfers: ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // create check boxes
        JCheckBox checkBox = new JCheckBox("Yes");

        // Create Combo Box header
        JLabel transportType = new JLabel("Select means of transport: ");
        transportType.setFont(new Font("Select means of transport: ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // create combo box
        JComboBox<TransportMode> selection = new JComboBox<>(options);
        selection.setBackground(UIConstants.GUI_ACCENT_COLOR);
        selection.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);

        // create bottomPanel to hold label3, selection, and calculate button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel selectionPanel = new JPanel(new BorderLayout());
        JPanel selectionPanel2 = new JPanel(new BorderLayout());
        JButton clearButton = new JButton("Clear Map");
        clearButton.setBackground(UIConstants.GUI_TITLE_COLOR);
        clearButton.setForeground(Color.WHITE);
        selectionPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        selectionPanel2.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        checkBox.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        bottomPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        selectionPanel.add(transportType, BorderLayout.WEST);
        selectionPanel.add(selection, BorderLayout.CENTER);
        selectionPanel.add(calculate, BorderLayout.EAST);
        selectionPanel2.add(checkTransfer, BorderLayout.WEST);
        selectionPanel2.add(checkBox, BorderLayout.CENTER);
        bottomPanel.add(timeLabel, BorderLayout.CENTER);
        bottomPanel.add(clearButton, BorderLayout.SOUTH);
        bottomPanel.add(selectionPanel, BorderLayout.NORTH);
        bottomPanel.add(selectionPanel2);

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
        addBoxActionListener(checkBox);
    }

    private void addActionListeners(JTextField textField1, JTextField textField2, JButton calculate,
            JComboBox<TransportMode> selection) {

        final ZipCodeDatabase db = Context.getContext().getZipCodeDatabase();

        calculate.addActionListener(e -> {
            try {
                TransportMode transportMode = (TransportMode) selection.getSelectedItem();
                transportMode.dispose();
                transportMode.setStart(db.getLocation(textField1.getText()));
                transportMode.setDestination(db.getLocation(textField2.getText()));

                Context.getContext().getMap().clearIcons();
                Context.getContext().getMap().addMapGraphics(transportMode.getGraphics());

                timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT + transportMode.getTravelTime().toString());
            }
            catch (Exception ex) {
                ExceptionManager.handle(this, ex);
            }
        });
    }

    private void addClearActionListener(JButton clearButton) {
        clearButton.addActionListener(e -> {
            Context.getContext().getMap().clearIcons();
            timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT);
        });
    }

    // Add temporary action listener to the checkbox
    private void addBoxActionListener(JCheckBox checkBox) {
        checkBox.addActionListener(e -> {
            for (TransportMode option : options) {
                if (option instanceof Bus b)
                    b.setAllowTransfers(checkBox.isSelected());
            }
        });
    }
}
