package ui;

import static org.junit.Assert.assertEquals;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.models.gtfs.Time;
import core.models.transport.Bus;
import core.models.transport.Transport;

public class NavigationPanelTest {
    private NavigationPanel navigationPanel;
    private JPanel testPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox<Transport> selection;
    private JTextField departureField;
    private JTextField radiusField;
    private JButton calculate;
    private JButton busRandom;
    private JCheckBox checkBox;
    private JButton clearButton;

    @BeforeEach
    public void setUp() {
        // Initialize the context and necessary components
        navigationPanel = new NavigationPanel(new MaasMapsUI());

        // Extract components from the navigation panel for testing
        textField1 = (JTextField) getComponentByName(navigationPanel, "textField1");
        textField2 = (JTextField) getComponentByName(navigationPanel, "textField2");
        selection = (JComboBox<Transport>) getComponentByName(navigationPanel, "selection");
        departureField = (JTextField) getComponentByName(navigationPanel, "departureField");
        radiusField = (JTextField) getComponentByName(navigationPanel, "radiusField");
        calculate = (JButton) getComponentByName(navigationPanel, "calculate");
        busRandom = (JButton) getComponentByName(navigationPanel, "busRandom");
        checkBox = (JCheckBox) getComponentByName(navigationPanel, "checkBox");
        clearButton = (JButton) getComponentByName(navigationPanel, "clearButton");

        // Create a test panel and add the navigation panel to it
        testPanel = new JPanel(new BorderLayout());
        testPanel.add(navigationPanel, BorderLayout.CENTER);
    }

    @Test
    public void testNavigationPanelComponents() {
        // Set text fields with valid data
        textField1.setText("90210");
        textField2.setText("10001");

        // Simulate combo box selection
        selection.setSelectedIndex(0); // Assuming Walking is at index 0
        selection.setSelectedIndex(2); // Assuming Bus is at index 2

        // Simulate departure field input
        departureField.setText("08:30:00");

        // Simulate radius field input
        radiusField.setText("5");

        // Simulate randomize bus stops button click
        busRandom.doClick();

        // Simulate calculate button click
        calculate.doClick();

        // Simulate check box selection
        checkBox.setSelected(true);
        checkBox.setSelected(false);

        // Simulate clear button click
        clearButton.doClick();

        // Verify the bus component behaviors
        Bus bus = (Bus) selection.getSelectedItem();
        assertEquals(Time.of("08:30:00"), bus.getDepartingTime());

        // Verify the clear button functionality
        assertEquals("", textField1.getText());
        assertEquals("", textField2.getText());
    }

    // Helper method to get a component by its name
    private static java.awt.Component getComponentByName(java.awt.Container container, String name) {
        for (java.awt.Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            } else if (component instanceof java.awt.Container) {
                java.awt.Component found = getComponentByName((java.awt.Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
