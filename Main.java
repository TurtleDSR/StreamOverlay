import include.java.web.*; //package
import include.java.config.*;
import include.java.gui.popupMenu.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream; //io

import javax.swing.*; //gui

import com.github.kwhat.jnativehook.*; //keybinds
import com.github.kwhat.jnativehook.dispatcher.*;
import com.github.kwhat.jnativehook.keyboard.*;

public final class Main extends JFrame implements NativeKeyListener, WindowListener {
  public ServerConfig config;

  public static Font poppins;

  private JPopupMenu popupMenu;

  private SettingsButton settingsButton = new SettingsButton();

  private int xOffset;
  private int yOffset;

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    super("Run Count Overlay");
    addWindowListener(this);

    GlobalScreen.setEventDispatcher(new SwingDispatchService());

    poppins = loadPoppins();

    config = new ServerConfig(ServerConfig.DONOTRESETCONFIGS);

    popupMenu = new JPopupMenu();
    popupMenu.add(settingsButton);
    popupMenu.add(new ExitButton());

    addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger()) {
          showMenu(e);
        } else if(e.getButton() == MouseEvent.BUTTON1) {
          xOffset = e.getX();
          yOffset = e.getY();
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

    setUndecorated(true);

    setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
    getContentPane().setBackground(ServerConfig.hextoColor(config.backgroundColor));
    setSize(275, 75);

    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setAlwaysOnTop(true);

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
}