package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import core.Constants.BusColors;
import core.Constants.Paths;
import core.Constants.UIConstants;
import core.algorithms.AStarAlgorithm;
import core.algorithms.DijkstraAlgorithm;
import core.algorithms.PathStrategy;
import core.Context;
import core.datastructures.graph.Graph;
import core.managers.map.MapManager;
import core.models.Location;
import ui.map.Map;
import ui.map.ProxyMap;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.MapBackground;
import ui.map.geometry.Network;
import ui.route.ResultsProxy;
import ui.route.RouteUI;

/**
 * This class represents the app UI showing the map, the navigation panel and
 * the map option buttons
 *
 * @author Sheena Gallagher
 * @author Natalia Hadjisoteriou
 */
public class MaasMapsUI extends JFrame {
    private Map map;
    private JPanel buttonPanel;
    private JButton mainButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JComboBox<PathStrategy> changeAlgorithmBox;
    private Timer timer;
    private boolean expanded = false;
    private int animationStep = 0;
    private final int ANIMATION_STEPS = 30;

    @SuppressWarnings("rawtypes")
    private final PathStrategy[] algoOptions = {
            new DijkstraAlgorithm<Location>(),
            new AStarAlgorithm<Location>()
    };

    private PathStrategy<Location> selectedAlgorithm;

    public MaasMapsUI() {
        super("Maas Maps");
        initialiseUI();
    }

    @SuppressWarnings("rawtypes")
    private void initialiseUI() {
        setSize(800, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Creating all components
        map = new Map(new MapBackground());
        map.setMinimumSize(new Dimension(800, 150));
        map.setPreferredSize(new Dimension(800, 500));
        map.setSize(new Dimension(800, 500));

        ProxyMap proxyMap = new ProxyMap(map);
        Context.getContext().setMap(proxyMap);

        proxyMap.linkMapGraphics("AbstractedBusMap", new AbstractedBusNetwork(MapManager.getBusGraph()));
        proxyMap.linkMapGraphics("Accessibility", createAccessibilityMap());
        proxyMap.hideMapGraphics("AbstractedBusMap");
        proxyMap.hideMapGraphics("Accessibility");

        // Create split pane with left and right panels
        JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setDividerLocation(10000);

        JSplitPane horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        setContentPane(horizontalSplitPane);

        // Create split pane with top and bottom panels
        JPanel resultsContainer = new JPanel(new BorderLayout());
        resultsContainer.setBorder(BorderFactory.createMatteBorder(UIConstants.GUI_BORDER_SIZE,
                UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE, UIConstants.GUI_BORDER_SIZE,
                UIConstants.GUI_BACKGROUND_COLOR));
        resultsContainer.setMinimumSize(new Dimension(800, 600));
        resultsContainer.setPreferredSize(new Dimension(800, 600));

        // Adding custom panels
        NavigationPanel navigationPanel = new NavigationPanel(this);
        navigationPanel.setMinimumSize(new Dimension(450, 600));
        navigationPanel.setPreferredSize(new Dimension(500, 600));

        // Create and register the RouteUI to the context
        RouteUI resultsPanel = new RouteUI();
        Context.getContext().setResultsPanel(new ResultsProxy(resultsPanel));

        JPanel resultsContainerSouth = new JPanel(new GridLayout(1, 2, 900, UIConstants.GUI_BORDER_SIZE / 2));
        resultsContainerSouth.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        JPanel changeAlgoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        changeAlgoPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        // add combo box to change algorithms
        changeAlgorithmBox = new JComboBox<>(algoOptions);
        changeAlgorithmBox.setBackground(UIConstants.GUI_ACCENT_COLOR);
        changeAlgorithmBox.setForeground(UIConstants.GUI_HIGHLIGHT_COLOR);

        changeAlgorithmBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the selected algorithm when selection changes
                selectedAlgorithm = (PathStrategy) changeAlgorithmBox.getSelectedItem();
            }
        });

        JPanel legendButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        legendButtonPanel.setBackground(UIConstants.GUI_BACKGROUND_COLOR);

        // add legend button
        legend = new JButton("LEGEND");
        legend.setPreferredSize(new Dimension(100, 25));
        legend.setBackground(UIConstants.GUI_TITLE_COLOR);
        legend.setForeground(Color.WHITE);
        legendButtonPanel.add(legend);
        changeAlgoPanel.add(changeAlgorithmBox); // needs action listener

        legendButtonPanel.setVisible(true);
        changeAlgoPanel.setVisible(true);

        resultsContainer.add(resultsContainerSouth, BorderLayout.SOUTH);

        resultsContainerSouth.add(changeAlgoPanel);
        resultsContainerSouth.add(legendButtonPanel);

        // Add action listener to the legend button
        legend.addActionListener(e -> createLegendPanel());

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

        mainButton.addActionListener(e -> toggleMenu());

        button1.addActionListener(e -> {
            proxyMap.hideMapGraphics("Accessibility");
            proxyMap.hideMapGraphics("AbstractedBusMap");
        });
        button2.addActionListener(e -> proxyMap.toggleMapGraphics("AbstractedBusMap"));
        button3.addActionListener(e -> proxyMap.toggleMapGraphics("Accessibility"));

        // Add button panel to the frame
        resultsContainer.add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
        revalidate();
    }

    private MapBackground createAccessibilityMap() {
        try {
            BufferedImage image = ImageIO.read(new File("resources/accessibilityMap.png"));

            MapBackground top = new MapBackground(image);
            top.setAlpha(0.5f);
            return top;
        }
        catch (IOException e1) {
            throw new IllegalArgumentException(e1);
        }
    }

    private void createLegendPanel() {
        JFrame legendWindow = new JFrame("Legend");
        legendWindow.setSize(250, 400);
        legendWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        legendWindow.setLocationRelativeTo(this);

        JLabel heading = new JLabel("Bus Lines");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(UIConstants.GUI_TITLE_COLOR);
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        legendPanel.setBackground(UIConstants.GUI_LEGEND_COLOR);
        legendPanel.add(heading);

        // Map to store bus colors and their corresponding counts
        java.util.Map<Color, Integer> busData = new HashMap<>();
        busData.put(BusColors.BUS_30, 30);
        busData.put(BusColors.BUS_6, 6);
        busData.put(BusColors.BUS_1, 1);
        busData.put(BusColors.BUS_10, 10);
        busData.put(BusColors.BUS_7, 7);
        busData.put(BusColors.BUS_4, 4);
        busData.put(BusColors.BUS_2, 2);
        busData.put(BusColors.BUS_350, 350);
        busData.put(BusColors.BUS_15, 15);

        // Create legend items
        for (java.util.Map.Entry<Color, Integer> entry : busData.entrySet()) {
            JPanel legendItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            legendItem.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
            legendItem.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel colorLabel = new JLabel("●");
            colorLabel.setForeground(entry.getKey()); // Set color
            colorLabel.setFont(new Font("●", 3, 20)); // Set font size
            legendItem.add(colorLabel);

            JLabel numberLabel = new JLabel(" - No. " + entry.getValue());
            legendItem.add(numberLabel);

            legendPanel.add(legendItem);
        }

        legendWindow.add(legendPanel);
        legendWindow.setAlwaysOnTop(true);
        legendWindow.setVisible(true);
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

    public PathStrategy getSelectedAlgorithm() {
        return selectedAlgorithm;
    }
}
