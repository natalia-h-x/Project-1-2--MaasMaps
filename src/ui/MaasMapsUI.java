package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
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

import core.Constants.Paths;
import core.Constants.UIConstants;
import core.Context;
import core.algorithms.AStarAlgorithm;
import core.algorithms.DijkstraAlgorithm;
import core.algorithms.PathStrategy;
import core.managers.amenity.AmenityIconManager;
import core.managers.map.MapManager;
import core.models.Location;
import tools.generator.AccessibilityImageGenerator;
import ui.legend.AccessibilityLegend;
import ui.legend.BusNetworkLegend;
import ui.map.ProxyMap;
import ui.map.geometry.AbstractedBusNetwork;
import ui.map.geometry.ImageGraphics;
import ui.map.geometry.ImageMarker;
import ui.map.geometry.MapBackground;
import ui.map.geometry.MapGraphicsGroup;
import ui.map.geometry.interfaces.MapGraphics;
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
    private ui.map.Map map;
    private JPanel buttonPanel;
    private JButton mainButton;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton legend;
    @SuppressWarnings("rawtypes")
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
        map = new ui.map.Map(new MapBackground());
        map.setMinimumSize(new Dimension(800, 150));
        map.setPreferredSize(new Dimension(800, 500));
        map.setSize(new Dimension(800, 500));

        ProxyMap proxyMap = new ProxyMap(map);
        Context.getContext().setMap(proxyMap);

        proxyMap.linkMapGraphics("AbstractedBusMap", new AbstractedBusNetwork(MapManager.getBusGraph()));
        proxyMap.linkMapGraphics("Accessibility", createAccessibilityMap());
        proxyMap.linkMapGraphics("Icons", new MapGraphicsGroup(createIcons()));
        proxyMap.hideMapGraphics("Icons");
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
            @SuppressWarnings("unchecked")
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
            proxyMap.toggleMapGraphics("Icons");
        });

        boolean[] pressed2 = {false};
        boolean[] pressed3 = {false};
        ActionListener action2 = f -> {
            if (pressed2[0]) new BusNetworkLegend();
        };

        button2.addActionListener(e -> {
            proxyMap.toggleMapGraphics("AbstractedBusMap");
            // Add action listener to the legend button
            if (!pressed2[0]) legend.addActionListener(action2);
            else legend.removeActionListener(action2);
            pressed2[0] = !pressed2[0];
        });

        ActionListener action3 = f -> {
            if (pressed3[0]) new AccessibilityLegend();
        };

        button3.addActionListener(e -> {
            proxyMap.toggleMapGraphics("Accessibility");
            if (!pressed3[0]) legend.addActionListener(action3);
            else legend.removeActionListener(action3);
            pressed3[0] = !pressed3[0];
        });

        // Add button panel to the frame
        resultsContainer.add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
        revalidate();
    }

    public static void removeActionListeners(AbstractButton button) {
        if (button == null) {
            return;
        }
        ActionListener[] listeners = button.getActionListeners();
        if (listeners == null) {
            return;
        }
        for (ActionListener listener : listeners) {
            button.removeActionListener(listener);
        }
    }

    private MapBackground createAccessibilityMap() {
        try {
            File file = new File("resources/accessibilityMap.png");

            if (!file.exists())
                AccessibilityImageGenerator.generateImage();

            BufferedImage image = ImageIO.read(file);

            MapBackground top = new MapBackground(image);
            top.setAlpha(0.5f);
            return top;
        }
        catch (IOException e1) {
            throw new IllegalArgumentException(e1);
        }
    }

    private List<MapGraphics> createIcons() {
        List<MapGraphics> mapGraphics = new ArrayList<>();

        for (Map.Entry<BufferedImage, List<Location>> icon : AmenityIconManager.getLocationsOfIcons().entrySet()) {
            for (Location loc : icon.getValue())
                mapGraphics.add(new ImageGraphics(loc, icon.getKey(), 0.01));
        }

        return mapGraphics;
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
