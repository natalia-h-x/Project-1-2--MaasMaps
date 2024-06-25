package ui.legend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import core.Constants.UIConstants;
import ui.accessibility.AccessibilityRank;

public class AccessibilityLegend extends Legend {
    public AccessibilityLegend() {
        initlialiseUI();
    }

    public void initlialiseUI() {
        setTitle("Legend");
        setSize(600, 450);
        setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
        setLayout(new BorderLayout());
        setResizable(false);

        JLabel heading = new JLabel("Accessibility");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(UIConstants.GUI_TITLE_COLOR);
        heading.setAlignmentX(CENTER_ALIGNMENT);
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel legendPanel = new JPanel(new BorderLayout());
        legendPanel.add(heading, BorderLayout.NORTH);
        legendPanel.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);

        JPanel gradientContainer = new JPanel();
        gradientContainer.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        gradientContainer.add(new AccessibilityGradientLegend());
        legendPanel.add(gradientContainer, BorderLayout.CENTER);

        JPanel rankContainer = new JPanel();
        rankContainer.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
        rankContainer.add(new AccessibilityRank());
        legendPanel.add(rankContainer, BorderLayout.SOUTH);

        add(legendPanel);
        setAlwaysOnTop(true);
        setVisible(true);
    }
}
