package com.TurtleDSR.StreamOverlay;

import com.TurtleDSR.StreamOverlay.include.java.web.*; //package
import com.TurtleDSR.StreamOverlay.include.java.config.*;
import com.TurtleDSR.StreamOverlay.include.java.gui.popupMenu.*;
import com.TurtleDSR.StreamOverlay.include.java.gui.widgets.WidgetPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream; //io

import javax.swing.*; //gui

import com.github.kwhat.jnativehook.*; //keybinds
import com.github.kwhat.jnativehook.dispatcher.*;
import com.github.kwhat.jnativehook.keyboard.*;

public final class Main extends JFrame implements NativeKeyListener, WindowListener {
  public static boolean[] keyMasks = new boolean[255]; //initialize keymask arr
  public static Font poppins;
  public static Main main;

  private ServerConfig config;

  private SystemTray tray;
  private JPopupMenu popupMenu;
  private SettingsButton settingsButton = new SettingsButton();

  private int xOffset;
  private int yOffset;

  private WidgetPanel displayed;

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

    popupMenu = new JPopupMenu();
    popupMenu.add(settingsButton);
    popupMenu.add(new TrayButton(tray, this));
    popupMenu.add(new ExitButton());
    
    setLayout(new FlowLayout(FlowLayout.LEFT));

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
      private void showMenu(MouseEvent e) {
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
      }
    });

    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseDragged(MouseEvent e) {
        if((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) setLocation(e.getXOnScreen() - xOffset, e.getYOnScreen() - yOffset);
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
    if(!keyMasks[e.getRawCode()]) {
      keyMasks[e.getRawCode()] = true;
    }
  }

  @Override
  public void nativeKeyReleased(NativeKeyEvent e) {
    keyMasks[e.getRawCode()] = false;
  }
}