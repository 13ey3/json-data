package id.mitraprimautama.smsgateway.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {

  public String getTanggal() {  
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");  
    Date date = new Date();  
    return dateFormat.format(date);  
  }  
  
  public String getWaktu() {  
      DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
      Date date = new Date();  
      return dateFormat.format(date);  
  }
}