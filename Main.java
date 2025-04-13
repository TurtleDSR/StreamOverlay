import include.java.web.*; //package
import include.java.config.*;
import include.java.gui.*;

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

public final class Main implements NativeKeyListener, WindowListener {
  public ServerConfig config;

  public JFrame frame;

  public JTabbedPane tabbedPane;

  public JPanel countPanel;
  public JPanel colorPanel;

  public JLabel countLabel;
  public JColorChooser textChooser;
  public JColorChooser backgroundChooser;

  public static Font poppins;

  private static boolean upMask = false;
  private static boolean downMask = false;
  private static boolean altMask = false;

  public static void main(String[] args) {
    new Main();
  }

  public Main() {
    GlobalScreen.setEventDispatcher(new SwingDispatchService());

    poppins = loadPoppins();

    config = new ServerConfig(ServerConfig.DONOTRESETCONFIGS);
    
    frame = new JFrame("Counter");
    frame.addWindowListener(this);

    tabbedPane = new JTabbedPane(JTabbedPane.LEFT);

    countPanel = new JPanel(new BorderLayout(10, 0));
    colorPanel = new JPanel(new GridLayout(2, 1));

    JLabel keybindUpLabel = new JLabel("Alt+Up: Increment");
    keybindUpLabel.setFont(poppins.deriveFont(25f));

    JLabel keybindDownLabel = new JLabel("Alt+Down: Decrement");
    keybindDownLabel.setFont(poppins.deriveFont(25f));

    countLabel = new JLabel("" + config.runCount);
    countLabel.setFont(poppins.deriveFont(50f));
    
    countPanel.add(keybindUpLabel, BorderLayout.NORTH);
    countPanel.add(keybindDownLabel, BorderLayout.SOUTH);
    countPanel.add(countLabel, BorderLayout.CENTER); 

    tabbedPane.addTab("Counter", countPanel);

    textChooser = new JColorPicker(ServerConfig.hextoColor(config.textColor), "textColor");
    textChooser.getSelectionModel().addChangeListener(new ColorPickerChanged(this));

    keybindUpLabel.setForeground(textChooser.getColor());
    keybindDownLabel.setForeground(textChooser.getColor());

    backgroundChooser = new JColorPicker(ServerConfig.hextoColor(config.backgroundColor), "backgroundColor");
    backgroundChooser.getSelectionModel().addChangeListener(new ColorPickerChanged(this));

    tabbedPane.addTab("Text Color", textChooser);
    tabbedPane.addTab("Background Color", backgroundChooser);

    frame.add(tabbedPane);

    frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setAlwaysOnTop(true);

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