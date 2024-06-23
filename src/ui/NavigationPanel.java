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
import core.Constants.UIConstants;
import core.Constants.Map;
import core.Context;
import core.managers.ExceptionManager;
import core.managers.map.PostalCodeManager;
import core.models.gtfs.Time;
import core.models.transport.Biking;
import core.models.transport.Bus;
import core.models.transport.Transport;
import core.models.transport.Walking;
import core.zipcode.ZipCodeDatabase;

/**
 * This class represents the side navigation panel in the UI
 *
 * @author Natalia Hadjisoteriou
 */
public class NavigationPanel extends JPanel {
    private MaasMapsUI parentUI;
    private JLabel timeLabel;
    private transient Transport[] options = {
            new Walking(),
            new Biking(),
            new Bus()
    };

    public NavigationPanel(MaasMapsUI parentUI) {
        this.parentUI = parentUI;
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

        JTextField textField1 = new JTextField(PostalCodeManager.getRandomPostalCode(), 8);
        JLabel location2 = new JLabel("To: ");
        location2.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));

        JTextField textField2 = new JTextField(PostalCodeManager.getRandomPostalCode(), 8);
        JButton calculate = new JButton("Calculate");
        calculate.setBackground(UIConstants.GUI_HIGHLIGHT_BACKGROUND_COLOR);
        calculate.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);

        // create departure time label and field
        JLabel departure = new JLabel("Departure time: ");
        departure.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));
        JTextField departureField = new JTextField("07:00:00");

        // create an empty panel for spacing
        JPanel spacerPanel = new JPanel();
        spacerPanel.setPreferredSize(new Dimension(0, 10));
        spacerPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);


        // create search radius label and field
        JLabel search = new JLabel("Search radius (m): ");
        search.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));
        JTextField radiusField = new JTextField("" + Map.POSTAL_CODE_MAX_SEARCH_RADIUS);

        // randomize bus stops button
        JButton busRandom = new JButton("Randomize bus stops");
        busRandom.setPreferredSize(new Dimension(10, 25));
        busRandom.setBackground(UIConstants.GUI_BUTTON_COLOR);
        busRandom.setForeground(Color.WHITE);
        busRandom.addActionListener(e -> {
            textField1.setText(PostalCodeManager.getRandomPostalCode());
            textField2.setText(PostalCodeManager.getRandomPostalCode());
        });

        // arrange text fields to jpanels
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        panel2.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        panel1.setLayout(new GridLayout(7, 0, 0, UIConstants.GUI_BORDER_SIZE / 2));
        panel2.setLayout(new GridLayout(7, 0, 0, UIConstants.GUI_BORDER_SIZE / 2));
        departure.setVisible(false);
        departureField.setVisible(false);
        search.setVisible(false);
        radiusField.setVisible(false);
        busRandom.setVisible(false);

        // Add the new "Maximal walking time desired" label and field above the "Number of transfers"
        JLabel walkingTime = new JLabel("Maximal walking time (min): ");
        walkingTime.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        JTextField walkingField = new JTextField("" + Map.WALKING_MAX_TIME );

        walkingTime.setVisible(false);
        walkingField.setVisible(false);

        JLabel checkTransfer = new JLabel("Number of transfers: ");
        checkTransfer.setFont(new Font("Number of transfers: ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // transfer number label
        JLabel transferCount = new JLabel("");

        checkTransfer.setVisible(false);
        transferCount.setVisible(false);

        // Adding new components to the panels
        panel1.add(location1);
        panel1.add(location2);
        panel1.add(departure);
        panel1.add(spacerPanel);
        panel1.add(search);
        panel1.add(walkingTime);
        panel1.add(checkTransfer);

        panel2.add(textField1);
        panel2.add(textField2);
        panel2.add(departureField);
        panel2.add(busRandom);
        panel2.add(radiusField);
        panel2.add(walkingField);
        panel2.add(transferCount);

        JLabel title = new JLabel("Maas maps");
        title.setForeground(UIConstants.GUI_TITLE_COLOR);
        title.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TITLE_FONT_SIZE));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel(UIConstants.GUI_TIME_LABEL_TEXT);
        timeLabel.setFont(new Font(" ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // Create Combo Box header
        JLabel transportType = new JLabel("Select means of transport: ");
        transportType.setFont(new Font("Select means of transport: ", Font.BOLD, UIConstants.GUI_INFO_FONT_SIZE));

        // create combo box
        JComboBox<Transport> selection = new JComboBox<>(options);
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

        // Call the addActionListeners method and pass the array of components
        addActionListeners(textField1, textField2, calculate, selection, departure, departureField,
                           search, radiusField, busRandom, checkTransfer, transferCount, walkingTime,
                           walkingField, clearButton);
        addClearActionListener(clearButton);
    }

    private void addActionListeners(JComponent... components) {
        final ZipCodeDatabase db = Context.getContext().getZipCodeDatabase();

        JTextField textField1 = (JTextField) components[0];
        JTextField textField2 = (JTextField) components[1];
        JButton calculate = (JButton) components[2];
        JComboBox<Transport> selection = (JComboBox<Transport>) components[3];
        JLabel departure = (JLabel) components[4];
        JTextField departureField = (JTextField) components[5];
        JLabel search = (JLabel) components[6];
        JTextField radiusField = (JTextField) components[7];
        JButton busRandom = (JButton) components[8];
        JLabel checkTransfer = (JLabel) components[9];
        JLabel transferCount = (JLabel) components[10];
        JLabel walkingTime = (JLabel) components[11];
        JTextField walkingField = (JTextField) components[12];

        calculate.addActionListener(e -> {
            try {
                Transport transportMode = (Transport) selection.getSelectedItem();
                transportMode.dispose();
                transportMode.setStart(db.getLocation(textField1.getText()));
                transportMode.setDestination(db.getLocation(textField2.getText()));

                if (transportMode instanceof Bus)
                    for (Transport option : options) {
                        if (option instanceof Bus b) {
                            b.setDepartingTime(Time.of(departureField.getText()));
                            b.setPathStrategy(parentUI.getSelectedAlgorithm());
                            if (radiusField.getText().length() > 0)
                                b.setRadius(Integer.parseInt(radiusField.getText()));
                        }
                    }

                Context.getContext().getMap().clearIcons();

                if (walkingField.getText().length() > 0)
                    System.out.println("max walking: " + walkingField.getText()); //temporary

                if (transportMode instanceof Bus b)
                    transferCount.setText(String.valueOf(b.getTransfers()));

                Context.getContext().getMap().addMapGraphics(transportMode.getGraphics());
                Context.getContext().getResultsPanel().setRoute(transportMode.getRoute());

                timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT + transportMode.getTravelTime().toString());
            }
            catch (Exception ex) {
                ExceptionManager.handle(this, ex);
            }
        });

        // Add ItemListener to the selection JComboBox
        selection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Transport selectedMode = (Transport) selection.getSelectedItem();
                if (selectedMode instanceof Bus) {
                    // Show components related to bus stops
                    departure.setVisible(true);
                    departureField.setVisible(true);
                    search.setVisible(true);
                    radiusField.setVisible(true);
                    busRandom.setVisible(true);
                    checkTransfer.setVisible(true);
                    transferCount.setVisible(true);
                    walkingTime.setVisible(true);
                    walkingField.setVisible(true);
                }
                else {
                    // Hide components related to bus stops
                    departure.setVisible(false);
                    departureField.setVisible(false);
                    search.setVisible(false);
                    radiusField.setVisible(false);
                    busRandom.setVisible(false);
                    checkTransfer.setVisible(false);
                    transferCount.setVisible(false);
                    walkingTime.setVisible(false);
                    walkingField.setVisible(false);
                }
            }
        });

        addClearActionListener((JButton) components[13]);
    }

    private void addClearActionListener(JButton clearButton) {
        clearButton.addActionListener(e -> {
            Context.getContext().getMap().clearIcons();
            timeLabel.setText(UIConstants.GUI_TIME_LABEL_TEXT);
        });
    }
}
