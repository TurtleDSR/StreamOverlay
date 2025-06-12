package com.TurtleDSR.StreamOverlay.include.java.gui.settings;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.ConfigPanel;

public class SettingsPanel extends JTabbedPane implements ChangeListener {
  public static JTabbedPane widgetsPane;
  public static JPanel systemPanel;

  private ServerConfig config;

  private ConfigPanel oldSelection;

  public SettingsPanel(ServerConfig config) {
    super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    this.config = config;

    widgetsPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
    updateWidgetsPane();
    widgetsPane.addChangeListener(this);

    systemPanel = new JPanel();

    addTab("Widgets", widgetsPane);
    addTab("System", systemPanel);

    setForeground(ServerConfig.hextoColor(config.foregroundColor));
    setBackground(ServerConfig.hextoColor(config.backgroundColor));
  }

  private void updateWidgetsPane() {
    widgetsPane.removeAll();
    for (ConfigPanel i : config.configPanelMap.values()) {
      widgetsPane.addTab(i.getBoundWidget().getId(), i);
    }

    oldSelection = (ConfigPanel) widgetsPane.getSelectedComponent();
    oldSelection.setDisplayed(true);

    widgetsPane.setForeground(ServerConfig.hextoColor(config.foregroundColor));
    widgetsPane.setBackground(ServerConfig.hextoColor(config.backgroundColor));
  }

  public void update() {
    Main.main.setSize(280, 200);
    ((ConfigPanel) widgetsPane.getSelectedComponent()).update();
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    oldSelection.setDisplayed(false);

    oldSelection = (ConfigPanel) widgetsPane.getSelectedComponent();

    oldSelection.setDisplayed(true);
  }
}
