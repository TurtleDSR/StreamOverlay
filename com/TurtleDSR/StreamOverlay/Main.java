package com.TurtleDSR.StreamOverlay;

import com.TurtleDSR.StreamOverlay.include.java.web.*; //package
import com.TurtleDSR.StreamOverlay.include.java.config.*;
import com.TurtleDSR.StreamOverlay.include.java.gui.popupMenu.*;
import com.TurtleDSR.StreamOverlay.include.java.gui.settings.SettingsPanel;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;
import com.TurtleDSR.StreamOverlay.include.java.keybinds.Keybind;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream; //io
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.swing.*; //gui

import com.github.kwhat.jnativehook.*; //keybinds
import com.github.kwhat.jnativehook.dispatcher.*;
import com.github.kwhat.jnativehook.keyboard.*;

public final class Main extends JFrame implements NativeKeyListener, WindowListener {
  public static Set<Integer> keyMasks = new HashSet<>(); //initialize keymask set
  public static Queue<Keybind> keybinds = new LinkedList<>();
  
  public static Font poppins;
  public static Main main;

  public boolean draggable = true;

  public WidgetPanel displayed;

  public SettingsPanel settingsPanel;

  public ServerConfig config;

  private SystemTray tray;
  private JPopupMenu popupMenu;
  private SettingsButton settingsButton = new SettingsButton();

  private int xOffset;
  private int yOffset;

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    super();
    addWindowListener(this);

    GlobalScreen.setEventDispatcher(new SwingDispatchService());

    main = this;

    tray = SystemTray.getSystemTray();

    poppins = loadPoppins();

    config = new ServerConfig(ServerConfig.DONOTRESETCONFIGS);

    settingsPanel = new SettingsPanel(config);

    popupMenu = new JPopupMenu();
    popupMenu.add(settingsButton);
    popupMenu.add(new TrayButton(tray, this));
    popupMenu.add(new ExitButton());
    
    setLayout(new FlowLayout(FlowLayout.LEFT));

    removeNotify();
    setUndecorated(true);
    getContentPane().setBackground(ServerConfig.hextoColor(config.backgroundColor));

    displayed = config.panelMap.values().iterator().next();
    displayed.setDisplayed(true);

    add(displayed);

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger()) {
          showMenu(e);
        } else if(e.getButton() == MouseEvent.BUTTON1) {
          yOffset = e.getY();
          xOffset = e.getX();
        }
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.isPopupTrigger()) {
          showMenu(e);
        }
      }
    });

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        if(draggable && (e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) setLocation(e.getXOnScreen() - xOffset, e.getYOnScreen() - yOffset);
      }
    });

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setAlwaysOnTop(true);
    setVisible(true);

    new ServerSocket(config);
  }

  public static Font loadPoppins() {
    InputStream is = Main.class.getResourceAsStream("include/web/static/font/Poppins-Regular.ttf");

    try {return Font.createFont(Font.TRUETYPE_FONT, is);} catch(FontFormatException e) {
      System.err.println("There was a problem formatting the font.");
			System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println("There was a problem reading the font file.");
			System.err.println(e.getMessage());
    }

    System.exit(1);
    return null;
  }

  public void windowOpened(WindowEvent e) {
		// Initialze native hook.
		try {GlobalScreen.registerNativeHook();} catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();

			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(this);
	}

	public void windowClosed(WindowEvent e) {
		//Clean up the native hook.
    try {GlobalScreen.unregisterNativeHook();} catch (NativeHookException ex) {
      System.err.println("There was a problem unregistering the native hook.");
			System.err.println(ex.getMessage());
			ex.printStackTrace();

			System.exit(1);
    }
		System.exit(0);
	}

  //unimplemented Window methods
  public void windowClosing(WindowEvent e) { /* Unimplemented */ }
  public void windowIconified(WindowEvent e) { /* Unimplemented */ }
  public void windowDeiconified(WindowEvent e) { /* Unimplemented */ }
  public void windowActivated(WindowEvent e) { /* Unimplemented */ }
  public void windowDeactivated(WindowEvent e) { /* Unimplemented */ }

  @Override
  public void nativeKeyPressed(NativeKeyEvent e) {
    if(!keyMasks.contains(e.getKeyCode())) {
      keyMasks.add(e.getKeyCode());
      updateBinds(true);
    }
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent e) {
    keyMasks.remove(e.getKeyCode());
    updateBinds(false);
  }

  public void openSettings() {
    displayed.setDisplayed(false);
    getContentPane().remove(displayed);
    add(settingsPanel);

    settingsPanel.update();
    
    removeNotify();
    setUndecorated(false);

    draggable = false;

    pack();
  }

  public void closeSettings() {
    getContentPane().remove(settingsPanel);
    add(displayed);

    removeNotify();
    setUndecorated(true);

    displayed.setDisplayed(true);
    setVisible(true);
    draggable = true;
  }

  public void showMenu(MouseEvent e) {
    Main.main.popupMenu.show(e.getComponent(), e.getX(), e.getY());
  }

  private static void updateBinds(boolean pressed) {
    for (Keybind bind : keybinds) {
      bind.checkBind(pressed);
    }
  }
}