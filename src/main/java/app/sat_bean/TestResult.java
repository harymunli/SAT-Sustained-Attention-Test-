package app.sat_bean;

import java.util.ArrayList;

public class TestResult {
  private ArrayList<ArrayList<Integer>> listAns = new ArrayList<>();

  private String nama;

  private int durasi;

  private int nAngka;

  private int nStimulus;

  public TestResult() {
    this.nama = "nama";
    this.durasi = 0;
    this.nAngka = 0;
    this.nStimulus = 0;
  }

  public TestResult(String nama, int durasi, int nAngka, int nStimulus, ArrayList<ArrayList<Integer>> list) {
    this.nama = nama;
    this.durasi = durasi;
    this.nAngka = nAngka;
    this.nStimulus = nStimulus;
    this.listAns = list;
  }

  public int hitungBenar() {
    int temp = 0;
    for (int i = 0; i < this.listAns.size(); i++) {
      if (((Integer)((ArrayList<Integer>)this.listAns.get(i)).get(1)).intValue() == 1)
        temp++;
    }
    return temp;
  }

  public int hitungSalah() {
    int temp = 0;
    for (int i = 0; i < this.listAns.size(); i++) {
      if (((Integer)((ArrayList<Integer>)this.listAns.get(i)).get(1)).intValue() == -1)
        temp++;
    }
    return temp;
  }

  public int hitungMiss() {
    int temp = 0;
    for (int i = 0; i < this.listAns.size(); i++) {
      if (((Integer)((ArrayList<Integer>)this.listAns.get(i)).get(1)).intValue() == 0)
        temp++;
    }
    return temp;
  }

  public int hitungResponseMoreThan850() {
      int temp = 0;
      for (int i = 0; i < this.listAns.size(); i++) {
        if (((Integer)((ArrayList<Integer>)this.listAns.get(i)).get(0)).intValue() > 850)
            temp++;
      }
      return temp;
  }
  
  public float persentase(char ch){
    if(this.nStimulus!=0){
        switch (ch) {
            case 'b':
                return ((hitungBenar()/this.nStimulus)*100);
            case 's':
                return ((hitungSalah()/this.nStimulus)*100);
            default:
                return ((hitungMiss()/this.nStimulus)*100);
        }
    }
    return 0;
  }
  
  public ArrayList<String> getRecord() {
    ArrayList<String> record = new ArrayList<>();
    record.add(this.nama);
    record.add(Integer.toString(this.durasi/1000));
    record.add(Integer.toString(this.nAngka));
    record.add(Integer.toString(this.nStimulus));
    int b = hitungBenar();
    int s = hitungSalah();
    int m = hitungMiss();
    float pb,ps,pm;
    if(this.nStimulus != 0){
        pb = ((float)b/this.nStimulus)*100;
        ps = ((float)s/this.nStimulus)*100;
        pm = ((float)m/this.nStimulus)*100;
    }else{
        pb = ps = pm = 0;
    }
    record.add(Integer.toString(b));
    record.add(Integer.toString(s));
    record.add(Integer.toString(m));
    record.add(String.format("%,.2f", pb));
    record.add(String.format("%,.2f", ps));
    record.add(String.format("%,.2f", pm));
    for (int i = 0; i < this.listAns.size(); i++) {
      record.add(Double.toString(((Integer)((ArrayList<Integer>)this.listAns.get(i)).get(0)).intValue() / 1000.0D));
      if (((Integer)((ArrayList<Integer>)this.listAns.get(i)).get(1)).intValue() == 1) {
        record.add("Hit");
      } else if (((Integer)((ArrayList<Integer>)this.listAns.get(i)).get(1)).intValue() == -1) {
        record.add("Wrong");
      } else {
        record.add("Miss");
      }
    }
    return record;
  }
}
