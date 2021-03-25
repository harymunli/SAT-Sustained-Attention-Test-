package app.sat_bean;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.*;

public class View extends JTextField {
    
  private JLabel label;

  private Clip clip;

  private AudioInputStream audioInputStream;

  private URL resource;

   private ImageIcon image;

  @Override
  public boolean isOptimizedDrawingEnabled() {
      return false;
  }

  public View() { 
    this.resource = SAT.class.getResource("check.png");
    this.image = new ImageIcon(new ImageIcon(resource).getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));

    try {
      InputStream in = SAT.class.getResourceAsStream("button.wav");
      audioInputStream =  AudioSystem.getAudioInputStream(new BufferedInputStream(in));
      clip = AudioSystem.getClip();
      clip.open(audioInputStream);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    initComponents();
  }

  public void setLabel(String label) {
    this.label.setText(label);
  }

  private void initComponents() {
    this.label = new JLabel();
    setBackground(new Color(0, 0, 0));
    addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent evt) {
            View.this.formKeyTyped(evt);
            SAT.keyPressed(evt);
        }
    });
    setLayout(new GridBagLayout());
    this.label.setBackground(new Color(255, 255, 255));
    this.label.setFont(new Font("Tahoma", 0, 500));
    this.label.setForeground(new Color(255, 255, 255));
    
    add(this.label, new GridBagConstraints());
  }
  
  private void formKeyTyped(KeyEvent evt){
    JFrame frame = new JFrame();

    final JDialog dialog = new JDialog(frame, "Test", true);
    JLabel imageLabel = new JLabel(image);
    dialog.setSize(40,40);
    dialog.add(imageLabel);

    clip.setFramePosition(0);
    clip.start();

    dialog.setUndecorated(true);
    dialog.getRootPane().setOpaque(false);
    dialog.setBackground(new Color (0, 0, 0, 0));
    dialog.setLocation(100, 100);

    ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
    s.schedule(new Runnable() {
        public void run() {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }, 200, TimeUnit.MILLISECONDS);
    
    dialog.setVisible(true);
    validate();
  }
  
  private void formMouseClicked(MouseEvent evt) {
    JFrame f = new JFrame();

    final JDialog dialog = new JDialog(f, "Test", true);
    JLabel imageLabel = new JLabel(image);
    dialog.setSize(40,40);
    dialog.add(imageLabel);

    clip.setFramePosition(0);
    clip.start();

    Point location = MouseInfo.getPointerInfo().getLocation();
    int x = (int) location.getX();
    int y = (int) location.getY();
    dialog.setUndecorated(true);
    dialog.getRootPane().setOpaque(false);
    dialog.setBackground(new Color (0, 0, 0, 0));
    dialog.setLocation(x+10, y+10);

    ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
    s.schedule(new Runnable() {
        public void run() {
            dialog.setVisible(false);
            dialog.dispose();
        }
    }, 200, TimeUnit.MILLISECONDS);

    dialog.setVisible(true);
    validate();
  }
}