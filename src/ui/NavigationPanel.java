package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NavigationPanel extends JFrame{
   
    public NavigationPanel() {
        super("MaasMaps"); // set the title of the JFrame
        initialiseNavigationUI();
    }

    private void initialiseNavigationUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set the default close operation
        setSize(1000, 600); // set the size of the JFrame

        
        // create navigation side JPanel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(201, 202, 217));
        JPanel navigationButtons = new JPanel();

        navigationButtons.setLayout(new BoxLayout(navigationButtons, BoxLayout.Y_AXIS));

        // create postal codes' fields
        JLabel label1 = new JLabel("From: ");
        label1.setFont(new Font("From: ", Font.BOLD, 15));
        JTextField textField1 = new JTextField("Enter postal code",8);
        JLabel label2 = new JLabel("To: ");
        label2.setFont(new Font("To: ", Font.BOLD, 15));
        JTextField textField2 = new JTextField("Enter postal code",8);
        JButton calculate = new JButton("Calculate");
        calculate.setBackground(new Color(119, 150, 203));
        calculate.setForeground(new Color(237, 242, 244));

        JLabel label4 = new JLabel("Maas maps"); 
        label4.setFont(new Font("Maas Maps", Font.BOLD, 40));
        label4.setForeground(new Color(87, 100, 144));
        JLabel label5 = new JLabel("Average time needed for this distance:");
        label5.setFont(new Font(" ",Font.BOLD, 13));
        //for later : JLabel resultLabel = new JLabel();
//calls calculator methods or whatever to get time result


        // create combo box
        JLabel label3 = new JLabel("Select means of transport: ");
        label3.setFont(new Font("Select means of transport: ", Font.BOLD, 13));
        String[] options = {"Walking", "Biking"};
        JComboBox<String> selection = new JComboBox<>(options);
        selection.setBackground(new Color(53, 80, 112));
        selection.setForeground(new Color(237, 242, 244));
        
        // add components to the left panel
        //set title
        leftPanel.add(label4);
        // create middlePanel to hold label1 and textField1
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(label1);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(textField1);
        navigationButtons.add(topPanel);
        navigationButtons.add(Box.createVerticalGlue());
        
        // create middlePanel to hold label2 and textField2
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.add(label2);
        middlePanel.add(Box.createHorizontalGlue());
        middlePanel.add(textField2);
        navigationButtons.add(middlePanel);
        navigationButtons.add(Box.createVerticalGlue());
        
        // create bottomPanel to hold label3, selection, and calculate button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(label3);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(selection);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(calculate);
        navigationButtons.add(bottomPanel);

        leftPanel.add(navigationButtons);
        
        leftPanel.add(label5);
        // create right panel (empty for now)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(216, 226, 220));

        // create split pane with left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.35); // adjust this value to set left side size 
        splitPane.setDividerLocation(350);

        // add split pane to the frame
        add(splitPane);
        
        //actions
        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //call method that calculates the distance
                System.out.print("calculation made");
            }
        });

        selection.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object choice =selection.getSelectedItem();
                //get choice
                System.out.println(choice);
            }
        });
        
        textField1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
              String point1 = textField1.getText();
              System.out.println(point1);
            }
        });

        textField2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
              String point2 = textField2.getText();
              System.out.println(point2);
            }
        });

        //set visible
        setVisible(true);
    }

   
}
