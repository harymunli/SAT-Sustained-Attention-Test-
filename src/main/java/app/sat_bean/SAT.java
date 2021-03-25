package app.sat_bean;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class SAT extends JFrame implements ActionListener {
  private String responderName;

  private int testDuration;

  private Timer timer;

  private int numberDuration = 1000;

  private int blankDuration = 2000;

  private Random random = new Random();

  private Container home;

  private Thankyou thankyou = new Thankyou(this);

  private Result result;

  private ResultStorage storage;

  private static boolean ongoing;

  private static boolean blank;

  private int randomize;

  private int count;

  private int countStimulus;

  private int current;

  private int iter;

  private static boolean isStimulus;

  private long startTime;

  private static long occurTime;

  private static boolean clicked;

  private static ArrayList<ArrayList<Integer>> listAns;

  private JSpinner duration;

  private JLabel contentTitle;

  private JLabel nameLabel;

  private JLabel durationLabel;

  private JPanel titlePanel;

  private JPanel durationPanel;

  private JPanel namePanel;

  private JPanel formPanel;

  private JTextField name;

  private JButton start;

  private void initComponents() {

    this.contentTitle = new JLabel();
    this.start = new JButton();
    this.titlePanel = new JPanel();
    this.formPanel = new JPanel();
    this.namePanel = new JPanel();
    this.nameLabel = new JLabel();
    this.name = new JTextField();
    this.durationPanel = new JPanel();
    this.durationLabel = new JLabel();
    this.duration = new JSpinner();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(800, 600));

    getContentPane().setLayout(new GridBagLayout());
    this.titlePanel.setLayout(new GridLayout(2, 1));

    
    URL resource = SAT.class.getResource("rske-itb.png");
    Image image = new ImageIcon(resource).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    ImageIcon imageIcon = new ImageIcon(image);
    JLabel imageLabel = new JLabel(imageIcon);
    getContentPane().add(imageLabel);

    this.contentTitle.setBackground(new Color(0, 0, 0));
    this.contentTitle.setFont(new Font("Tahoma", 0, 48));
    this.contentTitle.setText("Sustained Attention Test");

    this.titlePanel.add(imageLabel);
    this.titlePanel.add(contentTitle);
    getContentPane().add(this.titlePanel, new GridBagConstraints());

    this.start.setFont(new Font("Tahoma", 0, 14));
    this.start.setText("Mulai");
    this.start.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        SAT.this.startActionPerformed(evt);
        }
    });

    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new Insets(20, 0, 0, 0);
    getContentPane().add(this.start, gridBagConstraints);

    this.formPanel.setLayout(new GridLayout(2, 0));

    this.namePanel.setLayout(new GridLayout());
    this.nameLabel.setFont(new Font("Tahoma", 0, 14));
    this.nameLabel.setText("Nama");
    this.namePanel.add(this.nameLabel);
    this.name.setColumns(15);
    this.name.setFont(new Font("Tahoma", 0, 14));
    this.namePanel.add(this.name);

    this.durationPanel.setLayout(new GridLayout());
    this.durationLabel.setFont(new Font("Tahoma", 0, 14));
    this.durationLabel.setText("Durasi (menit)");
    this.durationPanel.add(this.durationLabel);
    this.duration.setFont(new Font("Tahoma", 0, 14));
    this.duration.setModel(new SpinnerNumberModel(Integer.valueOf(20), Integer.valueOf(0), null, Integer.valueOf(1)));
    this.durationPanel.add(this.duration);

    this.formPanel.add(this.namePanel);
    this.formPanel.add(this.durationPanel);
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new Insets(18, 0, 0, 0);
    getContentPane().add(this.formPanel, gridBagConstraints);

    pack();

    this.setTitle("Sustained Attention Test");
    this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
  }

  private void startActionPerformed(ActionEvent evt) {
    this.listAns = new ArrayList<>();
    this.responderName = this.name.getText();
    this.testDuration = ((Integer)this.duration.getValue()).intValue() * 60 * 1000;
    this.ongoing = true;
    this.randomize = randomize();
    View newPanel = new View();
    setContentPane(newPanel);
    ((View)getContentPane()).setLabel(Integer.toString(0));
    newPanel.requestFocus();
    validate();
    this.blank = true;
    this.count = 0;
    this.countStimulus = 0;
    this.iter = 0;
    this.isStimulus = false;
    this.timer = new Timer(this.blankDuration, this);
    this.timer.setActionCommand("timed");
    this.timer.start();
    this.startTime = System.currentTimeMillis();
    this.occurTime = System.currentTimeMillis();
  }

  public SAT() throws IOException {
    this.ongoing = false;
    this.blank = true;
    this.count = 0;
    this.countStimulus = 0;
    this.current = 0;
    this.iter = 0;
    this.isStimulus = false;
    this.clicked = false;
    this.storage = new ResultStorage("Result.csv");
    initComponents();
    this.home = getContentPane();
  }

  public int randomize() {
    return this.random.nextInt(9) + 1;
  }

  public static void main(String[] args) {
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(SAT.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(SAT.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(SAT.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(SAT.class.getName()).log(Level.SEVERE, (String)null, ex);
    }
    EventQueue.invokeLater(new Runnable() {
          public void run() {
            try {
              (new SAT()).setVisible(true);
            } catch (IOException ex) {
              Logger.getLogger(SAT.class.getName()).log(Level.SEVERE, (String)null, ex);
              JOptionPane.showMessageDialog(new JFrame(), "Silahkan tutup file hasil tes!", "Error", 0);
              System.exit(0);
            }
          }
        });
  }

  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    if (command.equals("toHome")) {
      setContentPane(this.home);
      validate();
    }
    if (command.equals("toThankyou")) {
        setContentPane(this.thankyou);
        validate();
    }
    if (command.equals("timed"))
      if (System.currentTimeMillis() - this.startTime <= this.testDuration) {
        if (this.blank) {
          this.blank = false;
          if (this.isStimulus && !this.clicked) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(Integer.valueOf(0));
            temp.add(Integer.valueOf(0));
            System.out.println(temp.toString());
            this.listAns.add(temp);
          }
          ((View)getContentPane()).setLabel(" ");
          this.timer.setDelay(this.numberDuration);
        } else {
          this.clicked = false;
          this.blank = true;
          this.current++;
          this.count++;
          this.iter++;
          if (this.iter == this.randomize) {
            this.iter = 0;
            this.randomize = randomize();
            this.current++;
            this.countStimulus++;
            this.isStimulus = true;
          } else {
            this.isStimulus = false;
          }
          ((View)getContentPane()).setLabel(Integer.toString(this.current % 10));
          this.timer.setDelay(this.blankDuration);
        }
        this.occurTime = System.currentTimeMillis();
      } else {
        this.ongoing = false;
        this.timer.stop();
        TestResult result = new TestResult(this.responderName, this.testDuration, this.count, this.countStimulus, this.listAns);
        try {
            this.storage.addRecord(result);
        } catch (IOException ex) {
          Logger.getLogger(SAT.class.getName()).log(Level.SEVERE, (String)null, ex);
        }
        this.result = new Result(this, result);
        setContentPane(this.result);
        validate();
      }
    }

    public static void keyPressed(KeyEvent e) {
        if (SAT.ongoing && SAT.blank && !SAT.clicked) {
            SAT.clicked = true;
            ArrayList<Integer> temp = new ArrayList<>();
            long delay = System.currentTimeMillis() - SAT.occurTime;
            temp.add(Integer.valueOf((int)delay));
            if (SAT.isStimulus) {
              temp.add(Integer.valueOf(1));
            } else {
              temp.add(Integer.valueOf(-1));
            }
            System.out.println(temp.toString());

            SAT.listAns.add(temp);
        }
    }
}	
