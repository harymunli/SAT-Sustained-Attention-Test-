package app.sat_bean;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class Result extends JPanel {
  private ActionListener listener;

  private ArrayList<String> result;
  private int resMoreThan850;
  private JButton finishButton;
  private JLabel contentTitle;
  private JLabel nameLabel;
  private JLabel durationLabel;
  private JLabel stimulusLabel;
  private JLabel correctLabel;
  private JLabel wrongLabel;
  private JLabel missLabel;
  private JLabel responseLabel;
  private JPanel resultPanel;
  public Result(ActionListener listener, TestResult result) {
    this.listener = listener;
    this.result = result.getRecord();
    this.resMoreThan850 = result.hitungResponseMoreThan850();
    initComponents();
  }
  private void initComponents() {
    this.resultPanel = new JPanel();
    this.contentTitle = new JLabel();
    this.nameLabel = new JLabel();
    this.durationLabel = new JLabel();
    this.stimulusLabel = new JLabel();
    this.correctLabel = new JLabel();
    this.wrongLabel = new JLabel();
    this.missLabel = new JLabel();
    this.responseLabel = new JLabel();

    setLayout(new GridBagLayout());
    this.contentTitle.setFont(new Font("Tahoma", 0, 32));
    this.contentTitle.setText("Hasil Eksperimen");
    add(this.contentTitle, new GridBagConstraints());

    this.finishButton = new JButton();
    this.finishButton.setText("Selesai");
    this.finishButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            Result.this.finishButtonActionPerformed(evt);
        }
    });
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.insets = new Insets(45, 0, 0, 0);
    add(this.finishButton, gridBagConstraints);

    GridLayout panelLayout = new GridLayout(7,0);
    panelLayout.setVgap(20);
    this.resultPanel.setLayout(panelLayout);
    this.nameLabel.setFont(new Font("Tahoma", 0, 18));
    this.nameLabel.setText("<html> nama: <b>" + this.result.get(0) + "</b></html>");
    this.durationLabel.setFont(new Font("Tahoma", 0, 18));
    this.durationLabel.setText("<html> Durasi: <b>" + Integer.valueOf(this.result.get(1)) / 60 + " menit</b></html>");
    this.stimulusLabel.setFont(new Font("Tahoma", 0, 18));
    this.stimulusLabel.setText("<html>Jumlah Stimulus: <b>" + this.result.get(3) + "</b></html>");
    this.correctLabel.setFont(new Font("Tahoma", 0, 18));
    this.correctLabel.setText("<html>Jumlah Benar: <b>" + this.result.get(4) + "</b> (<b>" + this.result.get(7) + "</b>%)</html>");
    this.wrongLabel.setFont(new Font("Tahoma", 0, 18));
    this.wrongLabel.setText("<html>Jumlah Salah: <b>" + this.result.get(5) + "</b> (<b>" + this.result.get(8) + "</b>%)</html>");
    this.missLabel.setFont(new Font("Tahoma", 0, 18));
    this.missLabel.setText("<html>Jumlah Miss: <b>" + this.result.get(6) + "</b> (<b>" + this.result.get(9) + "</b>%)</html>");
    this.responseLabel.setFont(new Font("Tahoma", 0, 18));
    this.responseLabel.setText("<html>Jumlah Response >850ms: <b>" + this.resMoreThan850 + "</b></html>");

    this.resultPanel.add(nameLabel);
    this.resultPanel.add(durationLabel);
    this.resultPanel.add(stimulusLabel);
    this.resultPanel.add(correctLabel);
    this.resultPanel.add(wrongLabel);
    this.resultPanel.add(missLabel);
    this.resultPanel.add(responseLabel);

    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new Insets(80, 0, 0, 0);
    add(this.resultPanel, gridBagConstraints);
    validate();
  }

  private void finishButtonActionPerformed(ActionEvent evt) {
    this.listener.actionPerformed(new ActionEvent(this, 0, "toThankyou"));
  }
}
