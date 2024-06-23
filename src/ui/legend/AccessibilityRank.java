package ui.legend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Constants.UIConstants;
import core.managers.amenity.AmenityStatisticsManager;
import core.models.AccessibilityMeasure;

public class AccessibilityRank extends JPanel {
    public AccessibilityRank() {
        super(new BorderLayout());
        setPreferredSize(new Dimension(550,150));
        
        add(createBottom5Panel(), BorderLayout.EAST);
        add(createTop5Panel(), BorderLayout.WEST);
    }

    private JPanel createBottom5Panel() {
        JPanel bottomRankedPanel = new JPanel();
        bottomRankedPanel.setLayout(new BoxLayout(bottomRankedPanel, BoxLayout.Y_AXIS));

        var bottom5 = AmenityStatisticsManager.getBottom5();
        for (int index = 0; index < bottom5.size(); index++) {
            JPanel legendItem = new JPanel();
            AccessibilityMeasure accessibilityMeasure = bottom5.get(index);
            String string = "Bottom %s at %s with accessibility %f".formatted(index + 1, accessibilityMeasure.getPostalCode(),
            accessibilityMeasure.getAccessibility());
            JLabel pp = new JLabel(string);
            legendItem.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
            legendItem.setAlignmentX(Component.LEFT_ALIGNMENT);
            legendItem.add(pp);
            bottomRankedPanel.add(legendItem);
        }

        return bottomRankedPanel;
    }

    private JPanel createTop5Panel() {
        JPanel topRankedPanel = new JPanel();
        topRankedPanel.setLayout(new BoxLayout(topRankedPanel, BoxLayout.Y_AXIS));

        var top5 = AmenityStatisticsManager.getTop5();
        for (int index = 0; index < top5.size(); index++) {
            JPanel legendItem = new JPanel();
            AccessibilityMeasure accessibilityMeasure = top5.get(index);
            String string = "Top %s at %s with accessibility %f".formatted(index + 1, accessibilityMeasure.getPostalCode(),
            accessibilityMeasure.getAccessibility());
            JLabel pp = new JLabel(string);
            legendItem.setBackground(UIConstants.GUI_LEGENDITEM_COLOR);
            legendItem.setAlignmentX(Component.LEFT_ALIGNMENT);
            legendItem.add(pp);
            topRankedPanel.add(legendItem);
        }

        return topRankedPanel;
    }
}
