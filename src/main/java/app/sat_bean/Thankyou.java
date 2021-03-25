package app.sat_bean;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Thankyou extends JPanel {
  private ActionListener listener;

  private JButton jButton1;

  private JLabel jLabel1;

  private JLabel jLabel2;

  public Thankyou(ActionListener listener) {
    this.listener = listener;
    initComponents();
  }

  private void initComponents() {
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.jButton1 = new JButton();
    setLayout(new GridBagLayout());
    this.jLabel1.setFont(new Font("Tahoma", 0, 48));
    this.jLabel1.setText("Terima Kasih!");
    add(this.jLabel1, new GridBagConstraints());
    this.jLabel2.setFont(new Font("Tahoma", 0, 18));
    this.jLabel2.setText("Eksperimen telah selesai dilakukan.");
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new Insets(10, 0, 0, 0);
    add(this.jLabel2, gridBagConstraints);
    this.jButton1.setText("Kembali");
    this.jButton1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            Thankyou.this.jButton1ActionPerformed(evt);
          }
        });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new Insets(25, 0, 0, 0);
    add(this.jButton1, gridBagConstraints);
  }

  private void jButton1ActionPerformed(ActionEvent evt) {
    this.listener.actionPerformed(new ActionEvent(this, 0, "toHome"));
  }
}
