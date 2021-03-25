package app.sat_bean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class ResultStorage {
  private CSVPrinter printer;

  private CSVFormat format = CSVFormat.DEFAULT;

  public ResultStorage(String path) throws IOException {
    File file = new File(path);
    boolean empty = (!file.exists() || file.length() == 0L);
    FileWriter fileWriter = new FileWriter(file, true);
    this.printer = new CSVPrinter(fileWriter, this.format);
    if (empty)
      this.printer.printRecord(getHeader());
    this.printer.flush();
  }

  public ArrayList<String> getHeader() {
    ArrayList<String> header = new ArrayList<>();
    header.add("Nama");
    header.add("Durasi (detik)");
    header.add("Jumlah Angka");
    header.add("Jumlah Stimulus");
    header.add("Jumlah Benar");
    header.add("Jumlah Salah");
    header.add("Jumlah Miss");
    for (int i = 1; i <= 100; i++) {
      header.add("Response Time-" + i);
      header.add("Status-" + i);
    }
    return header;
  }

  public void addRecord(TestResult result) throws IOException {
    this.printer.printRecord(result.getRecord());
    this.printer.flush();
    System.out.println("Record Stored.");
  }

  public static void main(String[] args) throws IOException {
    new ResultStorage("Tes.csv");
  }
}
