import include.java.web.*; //package
import include.java.config.*;
import include.java.gui.color.*;
import include.java.gui.popupMenu.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream; //io

import javax.swing.*; //gui
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.kwhat.jnativehook.*; //keybinds
import com.github.kwhat.jnativehook.dispatcher.*;
import com.github.kwhat.jnativehook.keyboard.*;

public final class Main extends JFrame implements NativeKeyListener, WindowListener {
  public ServerConfig config;

  public static Font poppins;

  private JPopupMenu popupMenu;

  private JLabel countLabel;
  private SettingsButton settingsButton = new SettingsButton();

  private int xOffset;
  private int yOffset;

  private static boolean upMask = false;
  private static boolean downMask = false;
  private static boolean altMask = false;

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

    countLabel = new JLabel(config.runCount + "");
    countLabel.setFont(poppins.deriveFont(50f));
    countLabel.setForeground(ServerConfig.hextoColor(config.textColor));

    add(countLabel);

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

  public void nativeKeyPressed(NativeKeyEvent e) {
    switch (e.getKeyCode()) {
      case NativeKeyEvent.VC_ALT:
        altMask = true;
        break;
      case NativeKeyEvent.VC_UP:
        if(altMask && !upMask) {
          countLabel.setText(++config.runCount + "");
          config.writeConfigs();
        }
        upMask = true; break;
      case NativeKeyEvent.VC_DOWN:
        if(altMask && !downMask) {
          countLabel.setText(--config.runCount + "");
          config.writeConfigs();
        }
        downMask = true; break;
      default:
        break;
    }
  }

  public void nativeKeyReleased(NativeKeyEvent e) {
    switch (e.getKeyCode()) {
      case NativeKeyEvent.VC_ALT:
        altMask = false;
        break;
      case NativeKeyEvent.VC_UP:
        upMask = false;
        break;
      case NativeKeyEvent.VC_DOWN:
        downMask = false;
        break;
      default:
        break;
    }
  }

  //unimplemented Window methods
  public void windowClosing(WindowEvent e) { /* Unimplemented */ }
  public void windowIconified(WindowEvent e) { /* Unimplemented */ }
  public void windowDeiconified(WindowEvent e) { /* Unimplemented */ }
  public void windowActivated(WindowEvent e) { /* Unimplemented */ }
  public void windowDeactivated(WindowEvent e) { /* Unimplemented */ }

  //action classes
  static class ColorPickerChanged implements ChangeListener {
    public Main parent;

    public ColorPickerChanged(Main parent) {
      this.parent = parent;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
      JColorPicker cp = ((ValuedSelectionModel) e.getSource()).parent;
      String actionID = cp.actionID;

      if(actionID.equals("textColor")) {
        cp.updateColor();
        parent.config.textColor = cp.toHex();
        parent.config.writeConfigs();
      } else {
        cp.updateColor();
        parent.config.backgroundColor = cp.toHex();
        parent.config.writeConfigs();
      }
    }
  }
}