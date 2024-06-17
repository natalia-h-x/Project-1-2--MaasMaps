package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import core.Constants.Paths;
import core.Constants.UIConstants;
import java.awt.geom.Point2D;
import core.Context;
import core.algorithms.datastructures.Graph;
import core.managers.MapManager;
import ui.map.Map;
import ui.map.ProxyMap;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.Network;

/**
 * This class represents the app UI showing the map, the navigation panel and the map option buttons
 *
 * @author Sheena Gallagher
 * @author Natalia Hadjisoteriou
 *
 */

public class MaasMapsUI extends JFrame {
    private Map map;
    private JPanel buttonPanel;
    private JButton mainButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton legend;
    private Timer timer;
    private boolean expanded = false;
    private int animationStep = 0;
    private final int ANIMATION_STEPS = 30;
    private JFrame legendWindow = new JFrame("Legend");

    public MaasMapsUI() {
        super("Maas Maps");
        initialiseUI();
    }

    private void initialiseUI() {
        setSize(800, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Creating all components
        map = new Map();
        map.addMapBackground();
        map.setMinimumSize(new Dimension(800, 150));
        map.setPreferredSize(new Dimension(800, 500));
        map.setSize(new Dimension(800, 500));

        ProxyMap proxyMap = new ProxyMap(map);
        Context.getContext().setMap(proxyMap);

        // Create split pane with left and right panels
        JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setDividerLocation(10000);

        JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        setContentPane(horizontalSplitPane);

        // Create split pane with top and bottom panels
        JPanel resultsContainer = new JPanel(new BorderLayout());
        resultsContainer.setBorder(BorderFactory.createMatteBorder(UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE,
                UIConstants.GUI_BACKGROUND_COLOR));
        resultsContainer.setMinimumSize(new Dimension(800, 600));
        resultsContainer.setPreferredSize(new Dimension(800, 600));

        // Adding custom panels
        NavigationPanel navigationPanel = new NavigationPanel();
        navigationPanel.setMinimumSize(new Dimension(450, 600));
        navigationPanel.setPreferredSize(new Dimension(500, 600));

        Map resultsPanel = new Map();
        resultsPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);
        resultsPanel.setPreferredSize(new Dimension(500, 150));
        resultsPanel.setSize(new Dimension(500, 150));

        Graph<Point2D> graph = MapManager.getBusGraph();
        Network abstractedBusNetwork = new AbstractedBusNetwork(graph);

        resultsPanel.addMapGraphics(abstractedBusNetwork);
        resultsPanel.repaint();

        JPanel legendPanel = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        legendPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        //add legend button
        legend = new JButton("LEGEND");
        legend.setPreferredSize(new Dimension(100,25));
        legend.setBackground(UIConstants.GUI_TITLE_COLOR);
        legend.setForeground(Color.WHITE);
        legendPanel.add(legend);

        legendPanel.setVisible(true);
        resultsContainer.add(legendPanel,BorderLayout.SOUTH);

        // Add action listener to the button
        legend.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create a new window
            legendWindow.setSize(250, 350);
            legendWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // Set the new window to be visible
            legendWindow.setVisible(true);
            legendWindow.setLocation(1400, 600); 
        }
    });

    


        // Adding components to the split panes
        verticalSplitPane.add(map, JSplitPane.TOP);
        verticalSplitPane.add(resultsPanel, JSplitPane.BOTTOM);

        resultsContainer.add(verticalSplitPane);

        horizontalSplitPane.add(navigationPanel, JSplitPane.LEFT);
        horizontalSplitPane.add(resultsContainer, JSplitPane.RIGHT);

        // Add button panel to the top right corner of the map
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        // Create label and add to button panel
        JLabel buttonsLabel = new JLabel("Map Options:  ");
        buttonsLabel.setFont(new Font(UIConstants.GUI_FONT_FAMILY, Font.BOLD, UIConstants.GUI_TEXT_FIELD_FONT_SIZE));
        buttonPanel.add(buttonsLabel);

        mainButton = new JButton("Menu ", Paths.menuIcon);
        mainButton.setVerticalTextPosition(SwingConstants.CENTER);
        mainButton.setHorizontalTextPosition(SwingConstants.CENTER);
        mainButton.setPreferredSize(new Dimension(UIConstants.BUTTON_SIZE, UIConstants.BUTTON_SIZE));
        mainButton.setFont(new Font("Arial", Font.BOLD, 10));
        buttonPanel.add(mainButton);

        button1 = new JButton(Paths.buttonMenu1);
        button2 = new JButton(Paths.buttonMenu2);
        button3 = new JButton(Paths.buttonMenu3);
        button1.setPreferredSize(new Dimension(UIConstants.BUTTON_SIZE, UIConstants.BUTTON_SIZE));
        button2.setPreferredSize(new Dimension(UIConstants.BUTTON_SIZE, UIConstants.BUTTON_SIZE));
        button3.setPreferredSize(new Dimension(UIConstants.BUTTON_SIZE, UIConstants.BUTTON_SIZE));
        button1.setBackground(Color.WHITE);
        button2.setBackground(Color.WHITE);
        button3.setBackground(Color.WHITE);

        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        mainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenu();
            }
        });

        // Add button panel to the frame
        resultsContainer.add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
        revalidate();
    }

    private void toggleMenu() {
        if (expanded) {
            shrinkMenu();
        } else {
            expandMenu();
        }
    }

    private void expandMenu() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animationStep < ANIMATION_STEPS) {
                    animationStep++;
                    updateButtonSizes();
                } else {
                    timer.stop();
                    expanded = true;
                }
            }
        });
        animationStep = 0;
        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        timer.start();
    }

    private void shrinkMenu() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animationStep > 0) {
                    animationStep--;
                    updateButtonSizes();
                } else {
                    timer.stop();
                    expanded = false;
                    button1.setVisible(false);
                    button2.setVisible(false);
                    button3.setVisible(false);
                }
            }
        });
        animationStep = ANIMATION_STEPS;
        timer.start();
    }

    private void updateButtonSizes() {
        int size = animationStep * UIConstants.BUTTON_SIZE / ANIMATION_STEPS;
        button1.setPreferredSize(new Dimension(size, size));
        button2.setPreferredSize(new Dimension(size, size));
        button3.setPreferredSize(new Dimension(size, size));
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MaasMapsUI();
            }
        });
    }
}
