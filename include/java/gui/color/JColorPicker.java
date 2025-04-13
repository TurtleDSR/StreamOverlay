package include.java.gui.color;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import java.awt.*;

public class JColorPicker extends JColorChooser{
  public String actionID;
  public Color color;

  public JColorPicker(Color initialColor, String actionID) {
    super();

    this.actionID = actionID;
    this.color = initialColor;

    setPreviewPanel(new JPanel(new BorderLayout()));

    AbstractColorChooserPanel[] panels = getChooserPanels(); //remove all annoying panels
    for (AbstractColorChooserPanel accp : panels) {
      if(!accp.getDisplayName().equals("HSV")) {
        removeChooserPanel(accp);
      } 
    }

    JComponent current = (JComponent) getComponents()[0]; //remove all annoying panels 
    while(!current.getClass().toString().equals("class javax.swing.colorchooser.ColorChooserPanel") ) {
      current = (JComponent) current.getComponents()[0]; 
    }
    for(Component jc : current.getComponents()){
      String componentName = jc.getClass().toString();
      if(!componentName.equals("class javax.swing.colorchooser.DiagramComponent")) {
        jc.setVisible(false);;
      }
    }

    this.setSelectionModel(new ValuedSelectionModel(this));
    this.getSelectionModel().setSelectedColor(initialColor);
  }

  public Color updateColor() {
    color = this.getColor();
    return color;
  }

  public String toHex() {
    return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
  }
}
