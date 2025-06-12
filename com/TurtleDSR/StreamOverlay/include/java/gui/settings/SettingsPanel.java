package com.TurtleDSR.StreamOverlay.include.java.gui.settings;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.TurtleDSR.StreamOverlay.Main;
import com.TurtleDSR.StreamOverlay.include.java.config.ServerConfig;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.config.ConfigPanel;

public class SettingsPanel extends JTabbedPane {
  public static JTabbedPane widgetsPane;
  public static JPanel systemPanel;

  private ServerConfig config;

  public SettingsPanel(ServerConfig config) {
    super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

    this.config = config;

    widgetsPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
    systemPanel = new JPanel();
    updateWidgetsPane();

    addTab("Widgets", widgetsPane);
    addTab("System", systemPanel);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.isPopupTrigger()) {
          Main.main.showMenu(e);
        }
      }
    });
  }


  private void updateWidgetsPane() {
    widgetsPane.removeAll();
    for (ConfigPanel i : config.configPanelMap.values()) {
      widgetsPane.addTab(i.getBoundWidget().getId(), i);
      i.setSize(400, 400);
    }
    widgetsPane.setMinimumSize(new Dimension(300, 300));
  }

  public void update() {
    Main.main.setSize(280, 200);
  }
}
