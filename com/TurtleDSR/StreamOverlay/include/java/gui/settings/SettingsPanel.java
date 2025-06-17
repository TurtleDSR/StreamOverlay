package com.TurtleDSR.StreamOverlay.include.java.gui.settings;

import javax.swing.JTabbedPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.gui.settings.panels.SystemPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.ConfigPanel;

public class SettingsPanel extends JTabbedPane implements ChangeListener {
  public static JTabbedPane widgetsPane;
  public static SystemPanel systemPanel;

  private ServerConfig config;

  private ConfigPanel oldSelection;

  public SettingsPanel(ServerConfig config) {
    super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    this.config = config;

    widgetsPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);

    updateWidgetsPane();
    widgetsPane.addChangeListener(this);

    systemPanel = new SystemPanel();

    addTab("Widgets", widgetsPane);
    addTab("System", systemPanel);
  }

  public void updateWidgetsPane() {
    widgetsPane.removeAll();
    for (ConfigPanel i : config.configPanelMap.values()) {
      widgetsPane.addTab(i.getBoundWidget().getId(), i);
    }

    if(oldSelection == null) {
      oldSelection = (ConfigPanel) widgetsPane.getSelectedComponent();
    } else {
      widgetsPane.setSelectedComponent(oldSelection);
    }
    oldSelection.setDisplayed(true);
  }

  public void update() {
    Main.main.pack();
    ((ConfigPanel) widgetsPane.getSelectedComponent()).update();
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if((ConfigPanel) widgetsPane.getSelectedComponent() != null) {
      oldSelection.setDisplayed(false);

      oldSelection = (ConfigPanel) widgetsPane.getSelectedComponent();

      oldSelection.setDisplayed(true);
    }
  }
}
