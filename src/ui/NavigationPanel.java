package ui;
import javax.swing.*;

import core.Context;
import database.ZipCodeDatabaseInteractor;
import models.Location;
import transport.Biking;
import transport.TransportMode;
import transport.Walking;
import ui.map.ProxyMap;
import ui.map.geometry.Line;

import java.awt.*;
import java.awt.event.*;

public class NavigationPanel extends JPanel{
    public NavigationPanel() {
        initialiseNavigationUI();
    }

    JLabel timeLabel;

    private void initialiseNavigationUI(){
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setForeground(new Color(201, 202, 217));

        // create navigation side JPanel
        JPanel navigationButtons = new JPanel();
        navigationButtons.setBackground(new Color(201, 202, 217));
        navigationButtons.setLayout(new GridLayout(5, 0));

        // create postal codes' fields
        JLabel location1 = new JLabel("From: ");
        location1.setFont(new Font("From: ", Font.BOLD, 15));

        JTextField textField1 = new JTextField("Enter postal code",8);
        JLabel location2 = new JLabel("To: ");
        location2.setFont(new Font("To: ", Font.BOLD, 15));

        JTextField textField2 = new JTextField("Enter postal code",8);
        JButton calculate = new JButton("Calculate");
        calculate.setBackground(new Color(119, 150, 203));
        calculate.setForeground(new Color(237, 242, 244));

        JLabel title = new JLabel("Maas maps");
        title.setFont(new Font("Maas Maps", Font.BOLD, 40));
        title.setForeground(new Color(87, 100, 144));

        timeLabel = new JLabel("Average time needed for this distance:");
        timeLabel.setFont(new Font(" ",Font.BOLD, 13));
        //for later : JLabel resultLabel = new JLabel();
        //calls calculator methods or whatever to get time result

        // create combo box
        JLabel transportType = new JLabel("Select means of transport: ");
        transportType.setFont(new Font("Select means of transport: ", Font.BOLD, 13));
        String[] options = {"Walking", "Biking"};
        JComboBox<String> selection = new JComboBox<>(options);
        selection.setBackground(new Color(53, 80, 112));
        selection.setForeground(new Color(237, 242, 244));

        // add components to the left panel
        // set title
        navigationButtons.add(title);

        // create middlePanel to hold label1 and textField1
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(location1);
        topPanel.add(textField1);
        navigationButtons.add(topPanel);

        // create middlePanel to hold label2 and textField2
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.add(location2);
        middlePanel.add(textField2);
        navigationButtons.add(middlePanel);

        // create bottomPanel to hold label3, selection, and calculate button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(transportType);
        bottomPanel.add(selection);
        bottomPanel.add(calculate);
        navigationButtons.add(bottomPanel);
        navigationButtons.add(timeLabel);

        // add split pane to the frame
        contentPanel.add(navigationButtons, BorderLayout.NORTH);
        add(contentPanel);

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
            timeLabel.setText(String.valueOf(time));
        });
    }
}
