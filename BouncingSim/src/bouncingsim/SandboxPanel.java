
package bouncingsim;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SandboxPanel extends JPanel implements ActionListener{
    private BouncingSim frame;
    
    private FieldPanel fieldPanel;
    private JPanel controlPanel;
    private JPanel selectionPanel;
    private JPanel attributesPanel;
    private JPanel resetPanel;
    private JPanel gridPanel;
    private JButton resetButton;
    private String[] bouncableObjectTypes = {"Controllable Ball","Ball","Point","Line","Box","Circle","Ring","Arc"};
    private String[] attributeTypes = {"Bouncy","Mud","Deadly"};
    private JComboBox selectionComboBox;
    private JComboBox attributeComboBox;
    private JTextField densityTextField;
    private JTextField dragMultiplierTextField;
    private JTextField dragSubtracterTextField;
    private JLabel densityLabel;
    private JLabel dragMultiplierLabel;
    private JLabel dragSubtracterLabel;
    
    
    private final int xSize = 1100;
    private final int ySize = 900;
    private final int controlWidth = 250;
    
    
    
    public SandboxPanel(BouncingSim frame){
        this.frame = frame;
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        
        fieldPanel = new FieldPanel(frame,this,xSize,ySize);
        controlPanel = new JPanel();
        attributesPanel = new JPanel();
        selectionPanel = new JPanel();
        resetPanel = new JPanel();
        selectionComboBox = new JComboBox(bouncableObjectTypes);
        attributeComboBox = new JComboBox(attributeTypes);
        resetButton = new JButton("Reset");
        resetButton.setActionCommand("Reset");
        resetButton.addActionListener(this);
        densityLabel = new JLabel("Density");
        dragMultiplierLabel = new JLabel("Drag Mult");
        dragSubtracterLabel = new JLabel("Drag Sub");
        densityTextField = new JTextField(6);
        dragMultiplierTextField = new JTextField(6);
        dragSubtracterTextField = new JTextField(6);
        gridPanel = new JPanel(new GridLayout(2,3));
        
        fieldPanel.setPreferredSize(new Dimension(xSize,ySize));
        controlPanel.setPreferredSize(new Dimension(controlWidth,ySize));
        
        gridPanel.add(densityLabel);
        gridPanel.add(dragMultiplierLabel);
        gridPanel.add(dragSubtracterLabel);
        gridPanel.add(densityTextField);
        gridPanel.add(dragMultiplierTextField);
        gridPanel.add(dragSubtracterTextField);
        attributesPanel.add(gridPanel);
        
        selectionPanel.add(selectionComboBox);
        resetPanel.add(resetButton);
        controlPanel.add(selectionPanel);
        controlPanel.add(attributesPanel);
        controlPanel.add(attributeComboBox);
        controlPanel.add(resetPanel);
        add(fieldPanel);
        add(controlPanel);
        
        
        selectionComboBox.addActionListener(this);
        selectionComboBox.setActionCommand("ComboBox");
        densityTextField.addActionListener(this);
        densityTextField.setActionCommand("Density");
        dragMultiplierTextField.addActionListener(this);
        dragMultiplierTextField.setActionCommand("DragM");
        dragSubtracterTextField.addActionListener(this);
        dragSubtracterTextField.setActionCommand("DragS");
        attributeComboBox.addActionListener(this);
        attributeComboBox.setActionCommand("Attribute");
        
        densityTextField.setText("1");
        dragMultiplierTextField.setText("1");
        dragSubtracterTextField.setText("0");
        
        
        setVisible(true);
    }
    
    public JComboBox getComboBox(){
        return  selectionComboBox;
    }
    
    public void setDensity(){
        double d = 1;
        try{
            d = Double.parseDouble(densityTextField.getText());
        }
        catch (Exception g){
            d = 1;
        }
        fieldPanel.setDensity(d);
    }
    
    public void setDragM(){
        double d = 1;
        try{
            d = Double.parseDouble(dragMultiplierTextField.getText());
        }
        catch (Exception g){
            d = 1;
        }
        fieldPanel.setDragM(d);
    }
    
    public void setDragS(){
        double d = 0;
        try{
            d = Double.parseDouble(dragSubtracterTextField.getText());
        }
        catch (Exception g){
            d = 0;
            
        }
        fieldPanel.setDragS(d);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(xSize + controlWidth,ySize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ComboBox")){
            fieldPanel.setSelection((String) selectionComboBox.getSelectedItem());
            fieldPanel.setNewColors((String) selectionComboBox.getSelectedItem(),(String) attributeComboBox.getSelectedItem());
            fieldPanel.resetNewObjectData();
        }
        if (e.getActionCommand().equals("Reset")){
            fieldPanel.getBallList().clear();
        }
        if (e.getActionCommand().equals("Density")){
            try{
                double d = Double.parseDouble(densityTextField.getText());
                if (d < 0.1)
                    densityTextField.setText("0.1");
                if (d > 100)
                    densityTextField.setText("100"); 
            }
            catch(Exception g){
                densityTextField.setText("1");
            }
            setDensity();
        }
        if (e.getActionCommand().equals("DragM")){
            try{
                double d = Double.parseDouble(dragMultiplierTextField.getText());
                if (d < 0.5)
                    dragMultiplierTextField.setText("0.5");
                if (d > 1)
                    dragMultiplierTextField.setText("1"); 
            }
            catch(Exception g){
                dragMultiplierTextField.setText("1");
            }
            setDragM();
        }
        if (e.getActionCommand().equals("DragS")){
            try{
                double d = Double.parseDouble(dragSubtracterTextField.getText());
                if (d < 0)
                    dragSubtracterTextField.setText("0");
                if (d > 3)
                    dragSubtracterTextField.setText("3"); 
            }
            catch(Exception g){
                dragSubtracterTextField.setText("0");
            }
            setDragS();
        }
        if (e.getActionCommand().equals("Attribute")){
            fieldPanel.setNewColors((String) selectionComboBox.getSelectedItem(),(String) attributeComboBox.getSelectedItem());
        }
    }
    
    
    
}
