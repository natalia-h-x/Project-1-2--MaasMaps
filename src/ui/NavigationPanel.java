package ui;

import java.awt.Dimension;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ItemEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.JCheckBox;

import core.Constants.UIConstants;
import core.Constants.Map;
import core.Context;
import core.managers.ExceptionManager;
import core.managers.MapManager;
import core.models.Time;
import core.models.transport.Biking;
import core.models.transport.Bus;
import core.models.transport.TransportMode;
import core.models.transport.Walking;
import core.zipcode.ZipCodeDatabase;
import ui.map.geometry.Radius;

/**
 * This class represents the side navigation panel in the UI
 *
 * @author Natalia Hadjisoteriou
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
        JTextField departureField = new JTextField("07:00:00");

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
        JTextField radiusField = new JTextField("" + Map.POSTAL_CODE_MAX_SEARCH_RADIUS);

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
        departure.setVisible(false);
        departureField.setVisible(false);
        search.setVisible(false);
        radiusField.setVisible(false);
        busRandom.setVisible(false);
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
        JLabel checkTransfer = new JLabel("The count when you need bus transfers: ");
        checkTransfer.setFont(
                new Font("The count when you need bus transfers: ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // create check boxes
        JLabel transferCount = new JLabel("");

        checkTransfer.setVisible(false);
        transferCount.setVisible(false);

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
        transferCount.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        bottomPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        selectionPanel.add(transportType, BorderLayout.WEST);
        selectionPanel.add(selection, BorderLayout.CENTER);
        selectionPanel.add(calculate, BorderLayout.EAST);
        selectionPanel2.add(checkTransfer, BorderLayout.WEST);
        selectionPanel2.add(transferCount, BorderLayout.CENTER);
        bottomPanel.add(clearButton, BorderLayout.SOUTH);
        bottomPanel.add(selectionPanel, BorderLayout.CENTER);
        bottomPanel.add(timeLabel, BorderLayout.NORTH);

        JPanel zipCodeSelectionPanel = new JPanel(new BorderLayout());
        zipCodeSelectionPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        zipCodeSelectionPanel.add(panel1, BorderLayout.WEST);
        zipCodeSelectionPanel.add(panel2, BorderLayout.CENTER);
        zipCodeSelectionPanel.add(selectionPanel2, BorderLayout.SOUTH);

        // add components to the navigation panel
        navigationButtons.add(title);
        navigationButtons.add(zipCodeSelectionPanel);

        // add split pane to the frame
        add(navigationButtons, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Create an array of JComponent to hold the components
        JComponent[] components = new JComponent[] {
                textField1, textField2, calculate, selection, departure, departureField,
                search, radiusField, busRandom, checkTransfer, transferCount, clearButton
        };

        // Call the addActionListeners method and pass the array of components
        addActionListeners(components);

        addClearActionListener(clearButton);
    }

    private void addActionListeners(JComponent[] components) {
        final ZipCodeDatabase db = Context.getContext().getZipCodeDatabase();

        JTextField textField1 = (JTextField) components[0];
        JTextField textField2 = (JTextField) components[1];
        JButton calculate = (JButton) components[2];
        JComboBox<TransportMode> selection = (JComboBox<TransportMode>) components[3];
        JLabel departure = (JLabel) components[4];
        JTextField departureField = (JTextField) components[5];
        JLabel search = (JLabel) components[6];
        JTextField radiusField = (JTextField) components[7];
        JButton busRandom = (JButton) components[8];
        JLabel checkTransfer = (JLabel) components[9];
        JLabel transferCount = (JLabel) components[10];

        calculate.addActionListener(e -> {
            try {
                TransportMode transportMode = (TransportMode) selection.getSelectedItem();
                transportMode.dispose();
                transportMode.setStart(db.getLocation(textField1.getText()));
                transportMode.setDestination(db.getLocation(textField2.getText()));

                if (transportMode instanceof Bus)
                    for (TransportMode option : options) {
                        if (option instanceof Bus b) {
                            b.setDepartingTime(Time.of(departureField.getText()));
                        }
                    }

                Context.getContext().getMap().clearIcons();

                if (radiusField.getText().length() > 0)
                    Context.getContext().getMap().setRadius(Integer.parseInt(radiusField.getText()));

                if (transportMode instanceof Bus b)
                    transferCount.setText(String.valueOf(b.getTransfers()));

                Context.getContext().getMap().addMapGraphics(transportMode.getGraphics());

                timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT + transportMode.getTravelTime().toString());
            }
            catch (Exception ex) {
                ExceptionManager.handle(this, ex);
            }
        });

        // Add ItemListener to the selection JComboBox
        selection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                TransportMode selectedMode = (TransportMode) selection.getSelectedItem();
                if (selectedMode instanceof Bus) {
                    // Show components related to bus stops
                    departure.setVisible(true);
                    departureField.setVisible(true);
                    search.setVisible(true);
                    radiusField.setVisible(true);
                    busRandom.setVisible(true);
                    checkTransfer.setVisible(true);
                    transferCount.setVisible(true);
                } else {
                    // Hide components related to bus stops
                    departure.setVisible(false);
                    departureField.setVisible(false);
                    search.setVisible(false);
                    radiusField.setVisible(false);
                    busRandom.setVisible(false);
                    checkTransfer.setVisible(false);
                    transferCount.setVisible(false);
                }
            }
        });

        addClearActionListener((JButton) components[11]);
    }

    private void addClearActionListener(JButton clearButton) {
        clearButton.addActionListener(e -> {
            Context.getContext().getMap().clearIcons();
            timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT);
        });
    }
}
