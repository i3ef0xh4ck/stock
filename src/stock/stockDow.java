package stock;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class stockDow {
  
  private static Integer stockNum = 9000001;   //0閿熸枻鎷峰ご閿熸枻鎷疯閿熸枻鎷�9閿熸枻鎷�
  private static String flag = "1";  //1 鍏ㄩ敓鏂ゆ嫹   2 閿熸枻鎷蜂笟  3閿熸枻鎷烽敓鏂ゆ嫹 

  @SuppressWarnings("unused")
  public static void main(String [] args) throws Exception {
        //鏀敓琛楄鎷烽敓鎻粨褝鎷烽敓鎻紮鎷烽敓锟�
        
   
        List<String> listAllYearDates = Arrays.asList(
                                                "2016-12-31");
        
        List<String> listYearDates = Arrays.asList("2017-03-31",
                                                   "2017-06-30",
                                                   "2017-09-30",
                                                   "2017-12-31"
                                                    );
                                                    
        
        List<Integer> prepareData = stockDow.prepareData();
        
        
        
        
        List<Map<String,Object>> list閿熸枻鎷蜂笟 =new ArrayList<Map<String,Object>>();

        String text閿熸枻鎷蜂笟 = "";
        //璇佸埜涔嬮敓鏂ゆ嫹
        //String filePath ="http://stock.quote.stockstar.com/stockinfo_finance/profit.aspx?code=600819&dt=2009-12-31";
        //閿熸枻鎷烽敓鏂ゆ嫹
        String urlPath = "http://stock.quote.stockstar.com/stockinfo_finance/profit.aspx?";
        
        
        //String urlPath = "http://stock.quote.stockstar.com/stockinfo_finance/summary.aspx?code=601088&dt=2004-12-31";
        //鎸囬敓鏂ゆ嫹
      //  String urlPath = "http://stock.quote.stockstar.com/stockinfo_finance/summary.aspx?";
        
        for(int i = 0 ; i < prepareData.size();i++){
          Integer integerCode = prepareData.get(i);
          String strCode = "";
          if(integerCode.toString().startsWith("9")){
        	  strCode = integerCode.toString().substring(1, integerCode.toString().length());
          }else{
        	  strCode = integerCode.toString();
          }
          
          String urlPath2 = urlPath+"";
          urlPath2 += "code="+strCode;
          //urlPath.append("code="+integerCode);
          Map<String,Object> map = new TreeMap<String,Object>();
          for(int j = 0 ; j  < listAllYearDates.size();j++){
            String allPath = urlPath2.toString()+"";  //閿熸枻鎷穉llPath浣块敓鏂ゆ嫹閿熼摪纰夋嫹閿熸枻鎷峰潃閿熸枻鎷峰潃
            String year = listAllYearDates.get(j);
            
            
            allPath += "&dt="+year; //涓�閿熸枻鎷烽敓鏂ゆ嫹绁ㄩ敓鏂ゆ嫹搴旀瘡閿熸枻鎷烽敓锟�12閿熸枻鎷�31閿熺Ц鎾呮嫹涓�閿熸枻鎷穐tml
            
            
            
            System.out.println("閿熸枻鎷烽敓鏂ゆ嫹閿熻锟�:"+allPath);
            System.out.println("閿熶茎纭锋嫹閿熸枻鎷�:"+strCode+"_"+year+".html");
            String fileName = strCode+"_"+year+".html";
            File downFile = stockDow.downFile(allPath.toString(),fileName);
            
            String readFile = stockDow.readFile(downFile,"UTF-8");
            
     
            //閿熸枻鎷烽敓鏂ゆ嫹
            stockDow.lirun(readFile, map, fileName);
            
            //roe
            //stockDow.zb(readFile, map, fileName);
            
            //閿熸枻鎷烽敓鏂ゆ嫹閿熼樁顏庢嫹閿燂拷
           // stockDow.zb2(readFile, map, fileName);
            
           
            System.out.println();
            
          }
          
          list閿熸枻鎷蜂笟.add(map);
          
          String text = "";
          for (String key : map.keySet()) {
              //map.keySet()閿熸枻鎷烽敓鎴鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹key閿熸枻鎷峰��
              Object value = map.get(key);//閿熺煫纰夋嫹姣忛敓鏂ゆ嫹key閿熸枻鎷烽敓鏂ゆ嫹閿熺淡alue閿熸枻鎷峰��
              
              //System.out.println(key+"\t"+value);
              
              text += key+"\t"+value + "\r\n";
              
              //鍐欓敓鏂ゆ嫹excel
              
          }
          
          System.out.println(text);
          	text閿熸枻鎷蜂笟 += text + "\r\n"+"\r\n"+"\r\n";
          
          
/*          for (String key : map.keySet()) {
            //map.keySet()閿熸枻鎷烽敓鎴鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹key閿熸枻鎷峰��
            Object value = map.get(key);//閿熺煫纰夋嫹姣忛敓鏂ゆ嫹key閿熸枻鎷烽敓鏂ゆ嫹閿熺淡alue閿熸枻鎷峰��
            
            System.out.print(key+"\t");
        }
          
          System.out.println();
          
          for (String key : map.keySet()) {
            //map.keySet()閿熸枻鎷烽敓鎴鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹key閿熸枻鎷峰��
            Object value = map.get(key);//閿熺煫纰夋嫹姣忛敓鏂ゆ嫹key閿熸枻鎷烽敓鏂ゆ嫹閿熺淡alue閿熸枻鎷峰��
            System.out.print(value.toString()+"\t"+"\t");
            
        }
          System.out.println();*/
          
          
          
          

          //閿熸枻鎷烽敓渚ョ》鎷峰垹閿熸枻鎷烽敓鏂ゆ嫹
          
          
          
        }
        
        System.out.println("===============================================");
        System.out.println(text閿熸枻鎷蜂笟);
        System.out.println("===============================================");
       // stockDow.writeFile(text閿熸枻鎷蜂笟, "all"+".txt");;
        
        stockDow.Optexcel(list閿熸枻鎷蜂笟);
          
    
  

 
       // return file;  
  }
  
  
  /**
   * roe
   * 
   * */
  public static void zb(String readFile,Map<String,Object> map,String fileName){
    int local = readFile.indexOf("閿熸枻鎷烽敓缁炶鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹(%)</td><td>");//閿熸枻鎷烽敓缁炶鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹(%)</td><td>29.76</td>
    String substring = "";
    
    
    if(local < 0){
      //System.out.println(integerCode+"_"+year+":"+"閿熸枻鎷烽敓鏂ゆ嫹娌￠敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹" );
      //map.put(fileName, "閿熸枻鎷烽敓鏂ゆ嫹娌￠敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹");
      map.put(fileName, "0.00");
    }else{
      
      
      substring = readFile.substring(local, local+35);
      
      
      
      
      
        String substring2 = substring.substring(17, substring.length());
        
        
        int start = substring2.indexOf(">");// >116.41</td></t
        int end = substring2.indexOf("<");
        
        String substring3 = substring2.substring(start+1, end);
        
        
        
        String substring4 = substring3.replace("--", "0.00");
        
        
        Double d = Double.parseDouble(substring4);

        
        
        map.put(fileName, d);
      
    }
    
  }
  
  
  /**
   * 閿熸枻鎷烽敓鏂ゆ嫹閿熼樁顏庢嫹閿燂拷
   * 
   * */
  public static void zb2(String readFile,Map<String,Object> map,String fileName){
    int local = readFile.indexOf("閿熸枻鎷烽敓鏂ゆ嫹閿熼樁顏庢嫹閿燂拷(%)</td><td>");//閿熸枻鎷烽敓鏂ゆ嫹閿熼樁顏庢嫹閿燂拷(%)</td><td>5.33</td>
    String substring = "";
    
    
    if(local < 0){
      //System.out.println(integerCode+"_"+year+":"+"閿熸枻鎷烽敓鏂ゆ嫹娌￠敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹" );
      //map.put(fileName, "閿熸枻鎷烽敓鏂ゆ嫹娌￠敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹");
      map.put(fileName, "0.00");
    }else{
      
      
      substring = readFile.substring(local, local+35);
      
      
      
      
      
        String substring2 = substring.substring(16, substring.length());
        
        
        int start = substring2.indexOf(">");// >116.41</td></t
        int end = substring2.indexOf("<");
        
        String substring3 = substring2.substring(start+1, end);
        
        
        
        String substring4 = substring3.replace("--", "0.00");
        
        
        Double d = Double.parseDouble(substring4);

        
        
        map.put(fileName, d);
      
    }
    
  }
  
  
  
  
  public static void lirun(String readFile,Map<String,Object> map,String fileName){
    int local = readFile.indexOf("閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�(閿熸枻鎷烽敓鏂ゆ嫹鍏�)</b></td><td>");
    String substring = "";
    
    
    if(local < 0){
      //System.out.println(integerCode+"_"+year+":"+"閿熸枻鎷烽敓鏂ゆ嫹娌￠敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹" );
      //map.put(fileName, "閿熸枻鎷烽敓鏂ゆ嫹娌￠敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹");
      map.put(fileName, "0.00");
    }else{
      
      
      substring = readFile.substring(local, local+35);
      
      
      
      
      
        String substring2 = substring.substring(20, substring.length());
        
        
        int start = substring2.indexOf(">");// >116.41</td></t
        int end = substring2.indexOf("<");
        
        String substring3 = substring2.substring(start+1, end);
        
        String substring4 = substring3.replace(",", "");
        
        
        Double d = Double.parseDouble(substring4);
        long l = (long) (d*1000000);
        double l2 = l/100000000d;
        
        
        map.put(fileName, l2);
      
    }
    
  }
  
  
  public static String outputFile = "C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\test.xls";
  public static void Optexcel(List<Map<String,Object>> list閿熸枻鎷蜂笟) throws Exception{
	  
	  // 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪纰夋嫹Excel 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  
      HSSFWorkbook workbook = new HSSFWorkbook();  

      // 閿熸枻鎷稥xcel閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鍙枻鎷蜂竴閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂负缂虹渷鍊�  
      // 閿熸枻鎷疯閿熼摪鏂ゆ嫹涓�閿熸枻鎷蜂负"鏁堥敓鏂ゆ嫹鎸囬敓鏂ゆ嫹"閿熶茎鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熻娇顏庢嫹閿燂拷  
      // HSSFSheet sheet = workbook.createSheet("鏁堥敓鏂ゆ嫹鎸囬敓鏂ゆ嫹");  
      HSSFSheet sheet = workbook.createSheet();  
      
      
      // 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�0閿熸枻鎷蜂綅閿熺煫杈炬嫹閿熸枻鎷烽敓鍙綇鎷烽敓绛嬮《閿熷壙纰夋嫹閿熷彨锝忔嫹  
      HSSFRow row = sheet.createRow((short) 0);  

      HSSFCell empCodeCell = row.createCell((short) 1);  
      empCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      empCodeCell.setCellValue("2004");  
        
      HSSFCell empNameCell = row.createCell((short) 2);  
      empNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      empNameCell.setCellValue("2005");  

      HSSFCell sexCell = row.createCell((short) 3);  
      sexCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      sexCell.setCellValue("2006");  
        
      HSSFCell birthdayCell = row.createCell((short) 4);  
      birthdayCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      birthdayCell.setCellValue("2007");  

      HSSFCell orgCodeCell = row.createCell((short) 5);  
      orgCodeCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      orgCodeCell.setCellValue("2008");  

      HSSFCell orgNameCell = row.createCell((short) 6);  
      orgNameCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      orgNameCell.setCellValue("2009");  
        
      HSSFCell contactTelCell = row.createCell((short) 7);  
      contactTelCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      contactTelCell.setCellValue("2010");  

      HSSFCell zjmCell = row.createCell((short) 8);  
      zjmCell.setCellType(HSSFCell.CELL_TYPE_STRING);  
      zjmCell.setCellValue("2011");  
      
      HSSFCell zjmCell2 = row.createCell((short) 9);  
      zjmCell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
      zjmCell2.setCellValue("2012");  
      
      
      HSSFCell zjmCell3 = row.createCell((short) 10);  
      zjmCell3.setCellType(HSSFCell.CELL_TYPE_STRING);  
      zjmCell3.setCellValue("2013");  
      
      HSSFCell zjmCell4 = row.createCell((short) 11);  
      zjmCell4.setCellType(HSSFCell.CELL_TYPE_STRING);  
      zjmCell4.setCellValue("2014"); 
      
      
      HSSFCell zjmCell5 = row.createCell((short) 12);  
      zjmCell5.setCellType(HSSFCell.CELL_TYPE_STRING);  
      zjmCell5.setCellValue("2015"); 
      
      
      for(int i = 0 ; i < list閿熸枻鎷蜂笟.size();i++){
    	  Map<String, Object> map = list閿熸枻鎷蜂笟.get(i);
          HSSFRow ro = sheet.createRow((short) 1+i);
          int j = 0 ;
          
          
          HSSFCell ce = ro.createCell((short) 0);
          ce.setCellType(HSSFCell.CELL_TYPE_STRING);
          
          for (String key : map.keySet()) {
              //map.keySet()閿熸枻鎷烽敓鎴鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹key閿熸枻鎷峰��
              Object value = map.get(key);//閿熺煫纰夋嫹姣忛敓鏂ゆ嫹key閿熸枻鎷烽敓鏂ゆ嫹閿熺淡alue閿熸枻鎷峰��
              
              
              if(j == 0){
            	  ce.setCellValue(key);
              }
              

              HSSFCell ce2 = ro.createCell((short) 1+j);
              ce2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
              ce2.setCellValue(Double.parseDouble(value.toString())); 
              
              
              j++;
              //鍐欓敓鏂ゆ嫹excel
              
          }
          
    	  
      }
      

      
      
 
      // 閿熼摪鏂ゆ嫹涓�閿熸枻鎷烽敓鏂ゆ嫹鍕熼敓鏂ゆ嫹閿燂拷  
      FileOutputStream fOut = new FileOutputStream(outputFile);  
      // 閿熸枻鎷烽敓鏂ゆ嫹搴旈敓鏂ゆ嫹Excel 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  
      workbook.write(fOut);  
      fOut.flush();  
      // 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鎴唻鎷烽敓渚ョ》鎷�  
      fOut.close();  
      System.out.println("閿熶茎纭锋嫹閿熸枻鎷烽敓鏂ゆ嫹...");  

  }
  
  
  

  
  
  public static String readFile(File file,String uincode) throws Exception{
    
    StringBuffer strb = new StringBuffer();  
    FileInputStream fs = new FileInputStream(file);  
    InputStreamReader isr = new InputStreamReader(fs,uincode);  //"UTF-8"
    BufferedReader br = new BufferedReader(isr);  
    String data = "";  
    while((data = br.readLine()) != null){  
        strb.append(data + "\n");  
    }  
    br.close();  
    fs.close();  
    isr.close();  
   // System.out.println(strb.toString()); 
    return strb.toString();  
      
  }
  
  public static void writeFile(String str,String fileName) throws Exception{
       
      String directory = "C:\\Users\\kikili\\Desktop\\test2";
      File file = new File(directory,fileName);
     OutputStream oputstream = new FileOutputStream(file);  
     
     
     
     InputStream in=new ByteArrayInputStream(str.getBytes());
     
     byte[] buffer = new byte[4*1024];  
     int byteRead = -1;     
     while((byteRead=(in.read(buffer)))!= -1){  
         oputstream.write(buffer, 0, byteRead);  
     }  
     oputstream.flush();    
     in.close();  
     oputstream.close();  
     
     
  }
  
  
  
  
  public static File downFile(String urlPath,String fileName){
    File file = null;
    try {  
      
        String directory = "f:\\test";
    	// String directory = "C:\\Users\\kikili\\Desktop\\test";
       // String fileName = "myFile.html";
        
        
          file = new File(directory,fileName); 
      
        OutputStream oputstream = new FileOutputStream(file);  
        URL url = new URL(urlPath.toString());  
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
        uc.setDoInput(true);//閿熸枻鎷烽敓鏂ゆ嫹閿熻鍑ゆ嫹瑕侀敓鏂ゆ嫹 URL 閿熸枻鎷烽敓鎺ヨ鎷峰彇閿熸枻鎷烽敓鏂ゆ嫹,榛橀敓鏂ゆ嫹涓簍rue  
        uc.connect();  
        InputStream iputstream = uc.getInputStream();  
 //       System.out.println("file size is:"+uc.getContentLength());//閿熸枻鎷峰嵃閿熶茎纭锋嫹閿熸枻鎷烽敓鏂ゆ嫹  
        byte[] buffer = new byte[4*1024];  
        int byteRead = -1;     
        while((byteRead=(iputstream.read(buffer)))!= -1){  
            oputstream.write(buffer, 0, byteRead);  
        }  
        oputstream.flush();    
        iputstream.close();  
        oputstream.close();  
    //  System.out.println(file.getAbsolutePath());
        return file;
          
 } catch (Exception e) {  
     System.out.println("路径不存在");  
     e.printStackTrace();  
 }  
    
    return file;
  }
  
  
  
  public static List<Integer> prepareData(){
    //1.閿熸枻鎷烽敓鏂ゆ嫹
    List<Integer> listCode_1_hq = new ArrayList<Integer>(Arrays.asList(
                                                  9000420  ,// 閿熸枻鎷烽敓琛椾紮鎷烽敓鏂ゆ嫹
                                                  9000584  ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹
                                                  9000615  ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹
                                                  9000677   ,//閿熸枻鎷烽敓灞婃捣閿熸枻鎷�
                                                  9000703   ,//閿熸枻鎷烽敓鏂ゆ嫹鐭抽敓鏂ゆ嫹
                                                  9000782   ,//閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�
                                                  9000936  ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹
                                                  9000949  ,// 閿熸枻鎷烽敓鐣屽寲閿熸枻鎷�
                                                  9000976  ,// 閿熸枻鎷烽敓閰佃偂鍑ゆ嫹
                                                  9002015  ,// 闇為敓閰典紮鎷烽敓鏂ゆ嫹
                                                  9002064  ,// 閿熸枻鎷烽敓钘夋皑閿熸枻鎷�
                                                  9002080  ,// 閿熷彨鏉愮纭锋嫹
                                                  9002172  ,// 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�
                                                  9002206   ,//閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷�
                                                  9002254  ,// 娉伴敓鏂ゆ嫹閿熼摪璇ф嫹
                                                  9002427  ,// 閿熼ズ鍑ゆ嫹鐓為敓锟�
                                                  9002493   ,//閿熸枻鎷风洓鐭抽敓鏂ゆ嫹
                                                  300180  ,// 閿熸枻鎷烽敓钘夎秴閿熸枻鎷�
                                                  600063  ,// 閿熸枻鎷风淮閿熸枻鎷烽敓鏂ゆ嫹
                                                  600346  ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹
                                                  600527  ,// 閿熸枻鎷烽敓杈冮潻鎷烽敓鏂ゆ嫹
                                                  600810  ,// 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�
                                                  600889  ,// 閿熻緝鎾呮嫹閿熸枻鎷烽敓鏂ゆ嫹
                                                  601113  ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹
                                                  601233  // 妗愰敓鏂ゆ嫹閿熺即鍑ゆ嫹

                                               )); 

    
    //閿熸枻鎷风焊
    List<Integer> listCode_2_zz = new ArrayList<Integer>(Arrays.asList(
                                                 9000488 ,// 閿熸枻鎷烽敓鏂ゆ嫹绾镐笟
                                                9000576 ,// 閿熷涓滈敓缁炰紮鎷�
                                                9000815 ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                                9000833 ,// 閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹
                                                9002012 ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹
                                                9002067 ,// 閿熸枻鎷烽敓鏂ゆ嫹绾镐笟
                                                9002078 ,// 澶敓鏂ゆ嫹绾镐笟
                                                9002235 ,// 閿熸枻鎷烽敓鎹疯偂鍑ゆ嫹
                                                9002303 ,// 閿熸枻鎷风泩妫�
                                                9002511 ,// 閿熸枻鎷烽『閿熸枻鎷烽敓鏂ゆ嫹
                                                9002521 ,// 閿熸枻鎷烽敓鏂ゆ嫹铏忛敓锟�
                                                9002565 ,// 椤虹亸鑲″嚖鎷�
                                                600069  ,// 閿熸枻鎷烽敓鏂ゆ嫹鎶曢敓鏂ゆ嫹
                                                600103  ,// 閿熸枻鎷峰北绾镐笟
                                                600235  ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熻锟�
                                                600308  ,// 閿熸枻鎷锋嘲閿熺即鍑ゆ嫹
                                                600356  ,// 閿熸枻鎷烽敓琛楁彮锟�
                                                600433  ,// 閿熻妭鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹
                                                600567  ,// 灞遍拱绾镐笟
                                                600793  ,// 閿熷壙鎲嬫嫹绾镐笟
                                                600963  ,// 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷风焊
                                                600966  ,// 閿熸枻鎷烽敓鏂ゆ嫹绾镐笟
                                                603165  // 閿熸枻鎷烽敓缂翠紮鎷烽敓鏂ゆ嫹
                                  ));
    //閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰搧
    List<Integer> listCode_3_kwzp = new ArrayList<Integer>(Arrays.asList(
                                                  9000511,//1.00 *ST鐑⒊
                                                  9000519,//2.00 閿熸枻鎷烽敓杈冪尨鎷烽敓锟�
                                                  9000795,//3.00 鑻遍敓钘夊崕
                                                  9002088,//4.00 椴侀敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                                  9002297,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹
                                                  300064,//6.00 璞敓鏂ゆ嫹閿熺粸锟�
                                                  300073,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹
                                                  300179,//8.00 閿熶茎鍑ゆ嫹閿熸枻鎷�
                                                  600172,//9.00 閿熺嫛鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹
                                                  600516,//10.00  閿熸枻鎷烽敓鏂ゆ嫹鐐敓鏂ゆ嫹
                                                  600783,//11.00  椴侀敓鑴氳揪鎷锋姇
                                                  603663,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹
                                                  603688//13.00  鐭宠嫳閿熺即鍑ゆ嫹
    
    ));
    
    //閿熸枻鎷烽敓鐭紮鎷烽敓鏂ゆ嫹
    List<Integer> listCode_4_ryhg = new ArrayList<Integer>(Arrays.asList(
                                                9000523,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                                9000737,//2.00  閿熻緝椋庡寲閿熸枻鎷�
                                                9002094,//3.00  閿熸磥宀涢敓鏂ゆ嫹閿熸枻鎷�
                                                9002637,//4.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�
                                                600249,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                                600315//6.00 閿熻緝鐚存嫹閿熸彮浼欐嫹
    
    
    ));
    
    
    List<Integer> listCode_5_jydq = new ArrayList<Integer>(Arrays.asList(
                                              9000016,//1.00  閿熺瓔搴烽敓绐栵綇鎷�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000100,//2.00  TCL 閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000333,//3.00  閿熸枻鎷烽敓渚ョ》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000418,//4.00  灏忛敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000521,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000533,//6.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000541,//7.00  閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000651,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9000921,//9.00  閿熸枻鎷烽敓鑴氬尅鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002005,//10.00 閿熼摪鐚存嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002032,//11.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002035,//12.00 閿熸枻鎷烽敓妗旇偂鍑ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002076,//13.00 闆� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002242,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002290,//15.00 閿熸枻鎷风洓閿熼摪璇ф嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002403,//16.00 閿熸枻鎷烽敓鍓胯揪鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002429,//17.00 閿熼樁椹拌偂鍑ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002473,//18.00 鍦ｉ敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002508,//19.00 閿熻緝甯嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002543,//20.00 閿熸枻鎷峰伔閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002668,//21.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002677,//22.00 閿熷姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002681,//23.00 閿熸澃杈炬嫹钀嶉敓锟�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002705,//24.00 閿熼摪鎲嬫嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002723,//25.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              9002759,//26.00 閿熸枻鎷蜂娇鐓為敓锟�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600060,//27.00  閿熸枻鎷烽敓鑴氱鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600261,//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600336,//29.00  閿熶茎鍖℃嫹閿熸枻鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600690,//30.00  閿熸磥宀涢敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600839,//31.00  閿熶茎杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600854,//32.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600870,//33.00  閿熺煫浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              600983,//34.00  閿熸嵎璁规嫹閿熸枻鎷� 閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              603366,//35.00  閿熺Ц绛规嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              603519,//36.00  閿熸枻鎷烽敓鐨嗚偂鍑ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹
                                              603868//37.00  閿熺即绉戠鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鐭鎷烽敓鏂ゆ嫹

    
    ));
    
    List<Integer> listCode_6_ylbj = new ArrayList<Integer>(Arrays.asList(
                                                9000150,//1.00  閿熷壙浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9000502,//2.00  閿熸暀鎾呮嫹閿熸埅鐧告嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9000503,//3.00  閿熸枻鎷烽敓鏂ゆ嫹姣撻敓锟�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002022,//4.00  閿熺嫛浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002044,//5.00  閿熸枻鎷烽敓鐤ュ仴閿熸枻鎷�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002162,//6.00  閿熸枻鎷烽敓渚ユ枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002223,//7.00  閿熸枻鎷疯穬鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002382,//8.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002432,//9.00  閿熻剼甯嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002551,//10.00 閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                9002614,//11.00 閿熺即鍑ゆ嫹閿熸枻鎷� 鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300003,//12.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300015,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸鍖℃嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300030,//14.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300061,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300171,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300206,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300216,//18.00  鍗冨北鑽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300238,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300244,//20.00  閿熻緝甯嫹閿熸枻鎷烽敓锟�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300246,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300247,//22.00  閿熻閲戝仴鍖℃嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300273,//23.00  閿熼叺浣宠偂鍑ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300298,//24.00  閿熸枻鎷疯閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300314,//25.00  閿熸枻鎷风淮鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300318,//26.00  閿熸枻鎷烽敓閰佃揪鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300326,//27.00  閿熸枻鎷烽敓鏂ゆ嫹娉� 鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300347,//28.00  娉伴敓鏂ゆ嫹鍖昏嵂  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300358,//29.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300396,//30.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300404,//31.00  閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300412,//32.00  閿熸枻鎷烽敓杈冪纭锋嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300439,//33.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300453,//34.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300463,//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300529,//36.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                300562,//37.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                600055,//38.00  閿熸枻鎷峰尰閿熸枻鎷�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                600381,//39.00  閿熸磥娴烽敓鏂ゆ嫹閿熸枻鎷�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                600530,//40.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                600587,//41.00  閿熼摪浼欐嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                600763,//42.00  閫氶敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                600767,//43.00  閿熸枻鎷风洓鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                603309,//44.00  缁撮敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                603658,//45.00  閿熸枻鎷峰浘閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                603987,//46.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹
                                                603579//47.00  閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  鍖婚敓鐙℃唻鎷烽敓鏂ゆ嫹

    
    ));
    
    
    List<Integer> listCode_7_jjyp = new ArrayList<Integer>(Arrays.asList(
                                                9000910,//1.00  閿熸枻鎷烽敓鏂ゆ嫹鍦ｉ敓鏂ゆ嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002084,//2.00  閿熸枻鎷烽弗閿熸枻鎷锋荡  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002120,//3.00  閿熼摪鐚存嫹閿熺即鍑ゆ嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002240,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002259,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002489,//6.00  閿熷姹熼敓鏂ゆ嫹寮�  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002572,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002615,//8.00  閿熸枻鎷烽敓鏂ゆ嫹鏂� 閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002631,//9.00  閿熼摪璁规嫹鏈敓鏂ゆ嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002718,//10.00 閿熺獤甯嫹閿熸枻鎷烽敓锟�  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002751,//11.00 閿熸枻鎷烽敓鏂ゆ嫹灞曠ず  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002757,//12.00 閿熸枻鎷烽敓鏂ゆ嫹瑁呴敓鏂ゆ嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002790,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                9002798,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                600337,//15.00  閿熸枻鎷烽敓鍓垮鎾呮嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                600978,//16.00  閿熷壙浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                603008,//17.00  鍠滈敓鏂ゆ嫹閿熸枻鎷� 閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                603313,//18.00  閿熷搴烽敓鎻拝鎷�  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                603389,//19.00  閿熸枻鎷烽敓鏂ゆ嫹鎻栭敓锟�  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                603600,//20.00  閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                603816,//21.00  閿熷壙瀹跺鎾呮嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                603818,//22.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸彮鎾呮嫹  閿熸彮鎾呮嫹閿熸枻鎷峰搧
                                                603898//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸彮鎾呮嫹閿熸枻鎷峰搧

    
    ));
    
    
    List<Integer> listCode_8_smdl = new ArrayList<Integer>(Arrays.asList(
                                                9000062,//1.00  閿熸枻鎷烽敓鑺備紮鎷峰己  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                9000151,//2.00  閿熷彨鎴愯偂鍑ゆ嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                9000408,//3.00  *ST閿熸枻鎷锋簮 閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                9000626,//4.00  杩滈敓鏂ゆ嫹姣撻敓锟�  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                9002091,//5.00  閿熸枻鎷烽敓绉哥櫢鎷锋嘲  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600058,//6.00 閿熸枻鎷烽敓绉革拷  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600120,//7.00 閿熷姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600128,//8.00 閿熸枻鎷蜂笟閿熺即鍑ゆ嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600153,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600241,//10.00  鏃堕敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600247,//11.00  ST閿熺即绛规嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600250,//12.00  閿熻緝绾鸿偂鍑ゆ嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600278,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600287,//14.00  閿熸枻鎷烽敓鏂ゆ嫹鑸滈敓鏂ゆ嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600382,//15.00  閿熷涓滈敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600500,//16.00  閿熷彨浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600605,//17.00  閿熸枻鎷烽�氶敓鏂ゆ嫹婧�  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600704,//18.00  閿熸枻鎷烽敓鏂ゆ嫹鍐欓敓锟�  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600735,//19.00  閿熼摪浼欐嫹閿熸枻鎷� 閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600739,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即杈炬嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600755,//21.00  閿熸枻鎷烽敓鑴氱櫢鎷疯锤  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600822,//22.00  閿熻緝鐚存嫹閿熸枻鎷疯锤  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600826,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
                                                600981//24.00  閿熸枻鎷锋潛顖ゆ嫹閿燂拷  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_9_ggbz = new ArrayList<Integer>(Arrays.asList(
                                                  9000038,//1.00  閿熸枻鎷烽敓閰碉拷 閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9000607,//2.00  閿熸枻鎷峰獟閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9000659,//3.00  閿熶粙娴烽敓鍙潻鎷�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9000812,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰彾  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002117,//5.00  閿熸枻鎷烽敓妗旇偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002188,//6.00  閿熸枻鎷峰＋閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002191,//7.00  閿熸枻鎷烽敓杞胯偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002228,//8.00  閿熸枻鎷烽敓鍓垮府鎷疯  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002229,//9.00  閿熷�熷崥閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002400,//10.00 鐪侀敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002599,//11.00 鐩涢�氶敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002701,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002712,//13.00 鎬濋敓鏂ゆ嫹閿熸枻鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002752,//14.00 閿熺祬閿熷壙鑲″嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002787,//15.00 閿熸枻鎷锋簮閿熸枻鎷疯  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002799,//16.00 閿熸枻鎷烽敓鏂ゆ嫹鍗伴敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002803,//17.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002812,//18.00 閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002831,//19.00 瑁曞悓閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  9002836,//20.00 閿熼摪鐚存嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  300057,//21.00  閿熸枻鎷烽『閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  300058,//22.00  閿熸枻鎷疯壊閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  300071,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  300501,//24.00  閿熸枻鎷烽『閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  600210,//25.00  閿熻緝鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  600836,//26.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  600880,//27.00  閿熸枻鎷烽敓閲戜紶璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  601515,//28.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  601968,//29.00  閿熸枻鎷烽敓琛楀府鎷疯  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  603022,//30.00  閿熸枻鎷烽�氶敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  603058,//31.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
                                                  603729//32.00  閿熸枻鎷烽敓杈冭偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹瑁�
    
    ));
    
    
    List<Integer> listCode_10_wjxx = new ArrayList<Integer>(Arrays.asList(
                                        9000017,//1.00  閿熸枻鎷烽敓鍙紮鎷稟  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9000526,//2.00  閿熻緝鐧告嫹瀛﹂敓鏂ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9000558,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9002103,//4.00  閿熷鍗氶敓缂村嚖鎷�  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9002105,//5.00  閿熸枻鎷烽殕閿熸枻鎷烽敓鏂ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9002301,//6.00  閿熸枻鎷烽敓渚ョ》鎷烽敓鏂ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9002348,//7.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9002575,//8.00  缇ら敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9002605,//9.00  濮氶敓鏂ゆ嫹閿熷壙鍖℃嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        9002678,//10.00 閿熶粙姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        300043,//11.00  閿熻浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        300329,//12.00  閿熸枻鎷烽敓闃堕潻鎷烽敓鏂ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        300359,//13.00  鍏ㄩ�氶敓鏂ゆ嫹閿熸枻鎷�  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        600158,//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        600234,//15.00  *ST灞辨按 閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        600661,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        600679,//17.00  閿熻緝鐚存嫹閿熸枻鎷烽敓锟�  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        600818,//18.00  閿熸枻鎷疯矾閿熺即鍑ゆ嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        603398,//19.00  閿熺瓔瀹濋敓鏂ゆ嫹閿熸枻鎷�  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                        603899//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熶茎鎾呮嫹  閿熶茎鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_11_jdcy = new ArrayList<Integer>(Arrays.asList(

                                      9000007,//1.00  鍏ㄩ敓閾扮尨鎷� 閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      9000428,//2.00  閿熸枻鎷烽敓鏂ゆ嫹棰戦敓锟�  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      9000524,//3.00  閿熸枻鎷烽敓杈冩帶鐧告嫹  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      9000721,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      9002186,//5.00  鍏� 閿熸枻鎷� 閿熸枻鎷� 閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      9002306,//6.00  閿熷彨鍖℃嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      9000033,//7.00  *ST閿熼摪璁规嫹 閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      600258,//8.00 閿熸枻鎷烽敓鐭厭纰夋嫹  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      600640,//9.00 閿熻剼鐧炬帶鐧告嫹  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      600754,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
                                      601007//11.00  閿熸枻鎷烽敓鐤ラキ閿熸枻鎷�  閿熺嫛纰夋嫹閿熸枻鎷烽敓锟�
    ));
    
    List<Integer> listCode_12_hk = new ArrayList<Integer>(Arrays.asList(
                                    9000738,//1.00  閿熷彨鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    9000768,//2.00  閿熷彨鐚存嫹閿熺即浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    9000901,//3.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
                                    9002013,//4.00  閿熷彨鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    9002023,//5.00  閿熸枻鎷烽敓鎴潻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    9002111,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋嘲  閿熸枻鎷烽敓鏂ゆ嫹
                                    9002260,//7.00  閿熼摪甯嫹閫氶敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    300424,//8.00 閿熸枻鎷烽敓閾扮纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    300581,//9.00 閿熸枻鎷烽敓鎴尨鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    600038,//10.00  閿熸枻鎷风洿閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    600118,//11.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    600316,//12.00  閿熶粙閮介敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
                                    600343,//13.00  閿熸枻鎷烽敓灞婂姩閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
                                    600372,//14.00  閿熷彨鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    600391,//15.00  閿熺即鍑ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    600862,//16.00  閿熷彨鐚存嫹閿熺鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                    600879,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
                                    600893//18.00  閿熷彨鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_13_cb = new ArrayList<Integer>(Arrays.asList(
                                  9002608,//1.00  *ST鑸滈敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹
                                  300008,//2.00 閿熷眾娴烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
                                  300123,//3.00 澶敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
                                  300589,//4.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯墖  閿熸枻鎷烽敓鏂ゆ嫹
                                  600072,//5.00 閿熻鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                  600150,//6.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                  600685,//7.00 閿熷彨杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                  601890,//8.00 閿熸枻鎷烽敓鏂ゆ嫹閿氶敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                  601989//9.00 閿熷彨鐧告嫹閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_14_yssb = new ArrayList<Integer>(Arrays.asList(
                                9000008,//1.00  閿熸枻鎷烽敓鎹烽潻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                9002367,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                9002689,//3.00  杩滈敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                300011,//4.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                300455,//5.00 閿熸枻鎷烽敓鎴尨鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                600495,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                600835,//7.00 閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                600894,//8.00 閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                600967,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                601313,//10.00  閿熸枻鎷烽敓杈冨槈鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                601766,//11.00  閿熷彨鐧告嫹閿熷彨绛规嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                603111,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                603611//13.00  璇洪敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
    
    ));
    
    
    List<Integer> listCode_15_dqsb = new ArrayList<Integer>(Arrays.asList(
                                      9000049,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000400,//2.00  閿熸枻鎷风棸閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000585,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000682,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000922,//5.00  閿熺獤纰夋嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000967,//6.00  鐩堥敓钘夌幆閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002028,//7.00  鎬濇簮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002074,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002112,//9.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002130,//10.00 閿熻璁规嫹閿熷壙璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002164,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002168,//12.00 閿熸枻鎷烽敓鑺傛儬绛规嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002169,//13.00 閿熻鐧告嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002176,//14.00 閿熸枻鎷烽敓鎴鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002202,//15.00 閿熸枻鎷烽敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002212,//16.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002227,//17.00 閿熸枻鎷� 閿熸枻鎷� 杩� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002249,//18.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002266,//19.00 閿熷瀵岄敓鎴櫢鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002270,//20.00 閿熸枻鎷烽敓鏂ゆ嫹瑁呴敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002276,//21.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002300,//22.00 澶敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002309,//23.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002334,//24.00 鑻遍敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002335,//25.00 閿熺嫛浼欐嫹閿熸枻鎷风洓  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002339,//26.00 閿熸枻鎷烽敓缂寸鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002346,//27.00 閿熸枻鎷烽敓鍙偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002350,//28.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002358,//29.00 妫簮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002364,//30.00 閿熷彨鐚存嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002380,//31.00 閿熸枻鎷疯繙閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002451,//32.00 鎽╅敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002452,//33.00 閿熸枻鎷烽敓绔》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002471,//34.00 閿熷彨绛规嫹閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002498,//35.00 閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002531,//36.00 閿熸枻鎷烽『閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002533,//37.00 閿熼噾鏉數宸�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002546,//38.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002560,//39.00 閫氶敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002576,//40.00 閫氶敓鏂ゅ姩閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002580,//41.00 鍦ｉ敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002606,//42.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002610,//43.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002617,//44.00 闇茬瑧閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002622,//45.00 閿熸枻鎷烽敓鑺傜》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002647,//46.00 閿熸枻鎷烽敓鑺傝偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002660,//47.00 鑼傜閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002665,//48.00 閿熼樁鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002684,//49.00 閿熸枻鎷风嫯閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002692,//50.00 杩滈敓鏁欑鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002706,//51.00 閿熸枻鎷烽敓鑴氱鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002730,//52.00 閿熸枻鎷烽敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002733,//53.00 閿熸枻鎷烽煬鑲″嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002782,//54.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002801,//55.00 寰敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300001,//56.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300004,//57.00  閿熻緝鍑ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300018,//58.00  閿熸枻鎷峰厓閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300040,//59.00  閿熸枻鎷烽敓鐫鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300048,//60.00  閿熻緝鍖℃嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300062,//61.00  閿熸枻鎷烽敓鏉扮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300068,//62.00  閿熻緝璁规嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300069,//63.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300120,//64.00  閿熸枻鎷风含閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300125,//65.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300129,//66.00  娉拌儨閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300140,//67.00  閿熸枻鎷锋簮瑁呴敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300141,//68.00  閿熸枻鎷烽『閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300153,//69.00  閿熸枻鎷锋嘲閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300208,//70.00  閿熸枻鎷烽『閿熻妭鏄�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300215,//71.00  閿熸枻鎷烽敓鐨嗭拷 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300222,//72.00  閿熺嫛杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300252,//73.00  閿熸枻鎷烽敓鏂ゆ嫹璇� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300265,//74.00  閫氶敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300274,//75.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺殕锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300283,//76.00  閿熸枻鎷烽敓鎹风尨鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300341,//77.00  閿熸枻鎷风郴閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300356,//78.00  閿熸枻鎷蜂竴閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300376,//79.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300403,//80.00  閿熸埅璁规嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300407,//81.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300423,//82.00  椴侀敓鏂ゆ嫹閫� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300427,//83.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300438,//84.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300444,//85.00  鍙岄敓鏉扮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300447,//86.00  鍏ㄩ敓鑴氳偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300477,//87.00  閿熸枻鎷烽敓鎹风纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300484,//88.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300490,//89.00  閿熸枻鎷烽敓鐨嗙纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300491,//90.00  閫氶敓杈冪纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300510,//91.00  閿熸枻鎷疯瘶閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300543,//92.00  閿熺粸鍖℃嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300372,//93.00  閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300593,//94.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600089,//95.00  閿熸埅鎲嬫嫹缁伙拷  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600110,//96.00  璇洪敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600112,//97.00  閿熸枻鎷风吔姣撻敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600192,//98.00  閿熸枻鎷烽敓瑙掔數宸�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600202,//99.00  閿熸枻鎷烽敓绉哥鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600268,//100.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600290,//101.00 閿熸枻鎷烽敓瑙掔鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600312,//102.00 骞抽敓绔鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600379,//103.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600405,//104.00 閿熸枻鎷烽敓鏂ゆ嫹婧� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600416,//105.00 閿熸枻鎷烽敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600468,//106.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600482,//107.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600517,//108.00 閿熸枻鎷烽敓鑴氱鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600525,//109.00 閿熸枻鎷峰洯閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600550,//110.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600560,//111.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600577,//112.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600580,//113.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600590,//114.00 娉伴敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600847,//115.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600869,//116.00 閿熻浼欐嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600875,//117.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600885,//118.00 閿熺枼鍙戦敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600973,//119.00 閿熸枻鎷疯儨閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601126,//120.00 閿熶茎鍑ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601179,//121.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601311,//122.00 閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601558,//123.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601616,//124.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601700,//125.00 閿熺晫鑼冮敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601727,//126.00 閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      601877,//127.00 閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603015,//128.00 閿熸枻鎷疯閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603016,//129.00 閿熼摪鐚存嫹娉� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603333,//130.00 閿熸枻鎷烽敓瑙掔鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603355,//131.00 閿熸枻鎷烽敓鍓跨鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603416,//132.00 閿熻剼鎹风鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603515,//133.00 娆ч敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603606,//134.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603618,//135.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603703,//136.00 鐩涢敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603819,//137.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603861,//138.00 閿熸枻鎷烽敓鐙＄鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603988,//139.00 閿熷彨纰夋嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603628//140.00 閿熸枻鎷锋簮閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      
    ));
    
    
    List<Integer> listCode_16_gcjx = new ArrayList<Integer>(Arrays.asList(
                                        9000157,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鍖℃嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9000425,//2.00  閿熷眾宸ラ敓鏂ゆ嫹姊�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9000528,//3.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9000680,//4.00  灞遍敓鐙¤偂鍑ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9000811,//5.00  閿熸枻鎷峰彴閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9000923,//6.00  閿熸帴鎲嬫嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002009,//7.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002011,//8.00  閿熸澃甯嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002097,//9.00  灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002158,//10.00 閿熸枻鎷烽敓鎺ユ拝鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002459,//11.00 閿熸枻鎷蜂笟閫氶敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002483,//12.00 閿熸枻鎷烽敓缂村嚖鎷�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002523,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002526,//14.00 灞遍敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002535,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸埅浼欐嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002667,//16.00 閿熸枻鎷烽敓鎴偂鍑ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        9002685,//17.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸埅浼欐嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        300035,//18.00  閿熷彨绉戠鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        300103,//19.00  閿熸枻鎷烽敓閾板嚖鎷烽敓锟�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        300185,//20.00  閫氳閿熸埅鐧告嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        300308,//21.00  閿熷彨纭锋嫹瑁呴敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600031,//22.00  閿熸枻鎷蜂竴閿熸埅鐧告嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600169,//23.00  澶師閿熸埅鐧告嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600320,//24.00  閿熸枻鎷烽敓鎴櫢鎷�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600582,//25.00  閿熸枻鎷蜂箿钀嶉敓锟�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600761,//26.00  閿熸枻鎷烽敓绉哥尨鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600815,//27.00  閿熺煫鐧告嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600984,//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷彨锟�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        601100,//29.00  閿熸枻鎷烽敓鏂ゆ嫹娑插帇  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        601106,//30.00  閿熷彨鐧告嫹涓�閿熸枻鎷�  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        601717,//31.00  閮戠叅閿熸枻鎷� 閿熸枻鎷烽敓鏁欎紮鎷锋
                                        603218,//32.00  閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏁欎紮鎷锋
                                        600710//33.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏁欎紮鎷锋
    ));
    
    
    List<Integer> listCode_17_dqyb = new ArrayList<Integer>(Arrays.asList(
                                        9000988,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002008,//2.00  閿熸枻鎷烽敓钘夋縺閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002058,//3.00  閿熸枻鎷� 閿熸枻鎷� 娉� 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002121,//4.00  閿熸枻鎷烽檰閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002175,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002197,//6.00  璇侀�氶敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002214,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002236,//8.00  閿熻鍗庤偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002338,//9.00  閿熸枻鎷烽敓绉哥櫢鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002414,//10.00 閿熺寰风尨鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002415,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002518,//12.00 閿熸枻鎷峰＋閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002527,//13.00 閿熸枻鎷锋椂閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002767,//14.00 閿熼ズ鍑ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        9002819,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熷彨鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300007,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300012,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300066,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300097,//19.00  閿熸枻鎷烽敓鐙¤偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300099,//20.00  閿熸枻鎷烽敓钘夊崱 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300105,//21.00  閿熸枻鎷锋簮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300112,//22.00  閿熸枻鎷疯閿熺殕鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300124,//23.00  閿熷宸濋敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300165,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300217,//25.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300259,//26.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300286,//27.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300306,//28.00  杩滈敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300338,//29.00  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300349,//30.00  閿熼噾鍗¤偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300354,//31.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300360,//32.00  閿熻姤鍗庨敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300370,//33.00  閿熸枻鎷烽敓鎴纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300371,//34.00  閿熸枻鎷烽敓鍙偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300410,//35.00  閿熸枻鎷蜂笟閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300416,//36.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300417,//37.00  閿熻緝浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300430,//38.00  閿熸枻鎷烽敓鏂ゆ嫹閫� 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300445,//39.00  閿熸枻鎷锋柉閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300466,//40.00  閿熸枻鎷锋懇閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300480,//41.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300515,//42.00  閿熸枻鎷烽敓閾扮纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300516,//43.00  閿熸枻鎷蜂箣閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300553,//44.00  閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300557,//45.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300567,//46.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        300572,//47.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        600366,//48.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        600651,//49.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        601222,//50.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        601567,//51.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        603100,//52.00  閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
                                        603556//53.00  閿熸枻鎷烽敓鍓跨鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹
    ));
    
    List<Integer> listCode_18_dxyy = new ArrayList<Integer>(Arrays.asList(
                                      9002093,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯惀
                                      9002467,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯惀
                                      300017,//3.00 閿熸枻鎷烽敓鐫纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯惀
                                      300383,//4.00 閿熻В鐜敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯惀
                                      600050,//5.00 閿熷彨鐧告嫹閿熸枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯惀
                                      600804//6.00 閿熸枻鎷烽敓鏂ゆ嫹澹� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯惀
    ));
    
    List<Integer> listCode_19_ggjt = new ArrayList<Integer>(Arrays.asList(
                                      600386,//1.00 閿熸枻鎷烽敓閰佃揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��
                                      600611,//2.00 閿熸枻鎷烽敓鑺傛枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��
                                      600650,//3.00 閿熸枻鎷烽敓鏂ゆ嫹鎶曢敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��
                                      600662,//4.00 寮洪敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��
                                      600676,//5.00 閿熸枻鎷烽敓鍓胯偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��
                                      600834//6.00 閿熸枻鎷烽�氶敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��
    ));
    
    List<Integer> listCode_20_sw = new ArrayList<Integer>(Arrays.asList(
                                    9000598,//1.00  閿熸枻鎷烽敓鎴紮鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
                                    9000605,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  姘撮敓鏂ゆ嫹
                                    9000685,//3.00  閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
                                    600168,//4.00 閿熸垝姹夐敓鎴櫢鎷�  姘撮敓鏂ゆ嫹
                                    600187,//5.00 閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
                                    600283,//6.00 閽遍敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
                                    600323,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  姘撮敓鏂ゆ嫹
                                    600461,//8.00 閿熸枻鎷烽敓鍓款啯锟�  姘撮敓鏂ゆ嫹
                                    601158,//9.00 閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
                                    601199,//10.00  閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
                                    601368//11.00  閿熸暀绛规嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_21_gsgr = new ArrayList<Integer>(Arrays.asList(
                                  9000407,//1.00  鑳滈敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9000421,//2.00  閿熻緝鎾呮嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9000593,//3.00  閿熸枻鎷烽�氱噧閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9000669,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺殕锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9000692,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼ズ纰夋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9000695,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9002267,//7.00  閿熸枻鎷烽敓鏂ゆ嫹鐒堕敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9002524,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  9002700,//9.00  閿熼摪鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  300335,//10.00  閿熸枻鎷锋．閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600167,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600333,//12.00  閿熸枻鎷烽敓鏂ゆ嫹鐕冮敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600617,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600635,//14.00  閿熸枻鎷烽敓鑺傜櫢鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600681,//15.00  閿熷姭杈炬嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600719,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼ズ纰夋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600856,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600917,//18.00  閿熸枻鎷烽敓鏂ゆ嫹鐕冮敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  600982,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼ズ纰夋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  601139,//20.00  閿熸枻鎷烽敓鏂ゆ嫹鐕冮敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  603393,//21.00  閿熸枻鎷烽敓鏂ゆ嫹鐒堕敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                  603689//22.00  閿熸枻鎷烽敓鏂ゆ嫹鐒堕敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_22_hjbh = new ArrayList<Integer>(Arrays.asList(
                                9000035,//1.00  閿熷彨鐧告嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9000544,//2.00  閿熸枻鎷峰師閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9000820,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9000826,//4.00  閿熸枻鎷烽敓鏂ゆ嫹妗戦敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9000920,//5.00  閿熻緝鍑ゆ嫹閿熸枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002200,//6.00  閿熸枻鎷锋姇閿熸枻鎷锋��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002322,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002499,//8.00  閿熸枻鎷烽敓琛椾紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002549,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002573,//10.00 閿熸枻鎷烽敓閾颁紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002616,//11.00 閿熸枻鎷烽敓娲侀泦閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002658,//12.00 闆敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002672,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300055,//14.00  閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300056,//15.00  閿熸枻鎷风淮涓� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300070,//16.00  閿熸枻鎷锋按婧� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300072,//17.00  閿熸枻鎷烽敓妗斾紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300090,//18.00  鐩涢敓鍓夸紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300137,//19.00  閿熼ズ娌充紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300152,//20.00  閿熸枻鎷烽敓鑺備紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300156,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300172,//22.00  閿熷彨鐢电幆閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300187,//23.00  閿熸枻鎷烽敓钘夌幆閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300190,//24.00  缁撮敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300197,//25.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300203,//26.00  閿熸鐧告嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300262,//27.00  閿熼叺甯嫹姘撮敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300272,//28.00  閿熸枻鎷烽敓鏉颁紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300332,//29.00  閿熷眾澹曢敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300355,//30.00  閿熺即璇ф嫹閿熸枻鎷锋��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300362,//31.00  閿熸枻鎷烽敓鍊熺幆閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300385,//32.00  闆敓鍓夸紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300388,//33.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300422,//34.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300425,//35.00  閿熸枻鎷烽敓鏉扮纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600008,//36.00  閿熼樁杈炬嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600217,//37.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺粸浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600292,//38.00  杩滈敓鏂ょ幆閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600388,//39.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600481,//40.00  鍙岄敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600526,//41.00  閿熺嫛杈剧幆閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600874,//42.00  閿熸枻鎷蜂笟閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                603126,//43.00  閿熷彨鏉愭枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                603568,//44.00  浼熼敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                603588,//45.00  閿熸枻鎷烽敓鏉颁紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                603822//46.00  閿熻娇婢充紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_23_ccwl = new ArrayList<Integer>(Arrays.asList(
                                9002183,//1.00  閿熸枻鎷� 閿熸枻鎷� 閫� 閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002210,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002245,//3.00  閿熸枻鎷烽敓鏂ゆ嫹椤洪敓鏂ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002468,//4.00  閿熸枻鎷烽�氶敓鏂ゆ嫹閿燂拷  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002492,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002711,//6.00  娆ч敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002769,//7.00  閿熸枻鎷疯矾閫� 閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002800,//8.00  閿熸枻鎷烽『閿熺即鍑ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300013,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300240,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                300350,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600057,//12.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600119,//13.00  閿熸枻鎷烽敓鏂ゆ嫹鎶曢敓鏂ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600179,//14.00  閿熸枻鎷烽�氶敓鎴櫢鎷�  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600180,//15.00  閿熸枻鎷疯寕閫� 閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600233,//16.00  鍦嗛�氶敓鍔鎷�  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600270,//17.00  閿熸枻鎷烽敓鍓垮嚖鎷峰睍  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600787,//18.00  閿熷彨杈炬嫹閿熺即鍑ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600794,//19.00  閿熸枻鎷风◣閿熺嫛纭锋嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                603117,//20.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                603128,//21.00  閿熸枻鎷疯锤閿熸枻鎷烽敓鏂ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
                                603569//22.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_24_yh = new ArrayList<Integer>(Arrays.asList(
                                        9000001,//1.00  骞抽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        9002142,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        9002807,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        9002839,//4.00  閿熻剼瀹堕潻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        600000,//5.00 閿熻鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        600015,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        600016,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        600036,//8.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        600908,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        600919,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        600926,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601009,//12.00  閿熻緝鎾呮嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601128,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601166,//14.00  閿熸枻鎷蜂笟閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601169,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601229,//16.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601288,//17.00  鍐滀笟閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601328,//18.00  閿熸枻鎷烽�氶敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
                                        601398,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601818,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
                                        601939,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601988,//22.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601997,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        601998,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                        603323//25.00  閿熻В姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
    ));
    List<Integer> listCode_25_zq = new ArrayList<Integer>(Arrays.asList(
                                      9000166,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺殕锟�  璇佸埜
                                      9000686,//2.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      9000728,//3.00  閿熸枻鎷峰厓璇佸埜  璇佸埜
                                      9000750,//4.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      9000776,//5.00  閿熷鍙戣瘉鍒�  璇佸埜
                                      9000783,//6.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      9002500,//7.00  灞遍敓鏂ゆ嫹璇佸埜  璇佸埜
                                      9002673,//8.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      9002736,//9.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      9002797,//10.00 閿熸枻鎷蜂竴閿熸枻鎷蜂笟  璇佸埜
                                      600030,//11.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      600061,//12.00  閿熸枻鎷锋姇閿熸枻鎷烽敓鏂ゆ嫹  璇佸埜
                                      600109,//13.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      600369,//14.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      600837,//15.00  閿熸枻鎷烽�氳瘉鍒�  璇佸埜
                                      600909,//16.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      600958,//17.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      600999,//18.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      601099,//19.00  澶钩閿熸枻鎷� 璇佸埜
                                      601198,//20.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      601211,//21.00  閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  璇佸埜
                                      601377,//22.00  閿熸枻鎷蜂笟璇佸埜  璇佸埜
                                      601555,//23.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      601688,//24.00  閿熸枻鎷锋嘲璇佸埜  璇佸埜
                                      601788,//25.00  閿熸枻鎷烽敓琛椼仺锟�  璇佸埜
                                      601901,//26.00  閿熸枻鎷烽敓鏂ゆ嫹璇佸埜  璇佸埜
                                      601375,//27.00  閿熸枻鎷峰師璇佸埜  璇佸埜
                                      601881//28.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  璇佸埜
    ));
    
    List<Integer> listCode_26_bx = new ArrayList<Integer>(Arrays.asList(
                                      9000627,//1.00  閿熸枻鎷疯寕閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                      600291,//2.00 閿熸枻鎷锋按閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                      601318,//3.00 閿熷彨鐧告嫹骞抽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                      601336,//4.00 閿熼摪浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                      601601,//5.00 閿熷彨鐧告嫹澶敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
                                      601628//6.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_27_dyjr = new ArrayList<Integer>(Arrays.asList(
                                      9000415,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      9000416,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      9000563,//3.00  閿熼摪鐧告嫹鎶曢敓鏂ゆ嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      9000712,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      9000987,//5.00  瓒婇敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      9002670,//6.00  閿熸枻鎷风洓閿熸枻鎷烽敓锟�  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600318,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600599,//8.00 閿熸枻鎷风尗閿熸枻鎷烽敓锟�  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600643,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600695,//10.00  閿熸枻鎷峰涵鎶曢敓鏂ゆ嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600705,//11.00  閿熷彨鐚存嫹閿熺粸鎲嬫嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600747,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600816,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
                                      600830//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_28_dlsb = new ArrayList<Integer>(Arrays.asList(
                                      9000021,//1.00  閿熸枻鎷疯悕閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000066,//2.00  閿熸枻鎷烽敓瑙掔鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000748,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9000977,//4.00  閿熷壙绛规嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002152,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼叺锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002177,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002180,//7.00  閿熸枻鎷烽敓缂村尅鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002308,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002312,//9.00  閿熸枻鎷锋嘲閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002351,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002362,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002376,//12.00 閿熼摪鎲嬫嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002528,//13.00 鑻遍敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002577,//14.00 閿熼樁鏌忕纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      9002635,//15.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300042,//16.00  閿熺粸绉戠纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300045,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300076,//18.00  GQY閿熸枻鎷疯 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300130,//19.00  閿熼摪鐧告嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300282,//20.00  閿熸枻鎷疯癄鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300367,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300390,//22.00  閿熷眾鍗庨敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      300449,//23.00  閿熸枻鎷烽敓鏂ゆ嫹鍛栭敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600074,//24.00  閿熸枻鎷峰崈閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600100,//25.00  鍚岄敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600271,//26.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600601,//27.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      600734,//28.00  瀹為敓鏂ら泦閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603019,//29.00  閿熷彨鍖℃嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603025,//30.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
                                      603996//31.00  閿熸枻鎷烽敓閾扮纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熷�熷
    
    ));
    
    List<Integer> listCode_29_txsb = new ArrayList<Integer>(Arrays.asList(
                                      9000063,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閫氳  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000070,//2.00  閿熸埅鍑ゆ嫹閿熸枻鎷锋伅  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000547,//3.00  閿熸枻鎷烽敓灞婂彂灞�  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000561,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000586,//5.00  閿熸枻鎷锋簮閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000687,//6.00  閿熸枻鎷疯閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000801,//7.00  閿熶茎杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000836,//8.00  閿熸枻鎷疯寕閿熺嫛纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000889,//9.00  鑼備笟閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9000892,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002017,//11.00 閿熸枻鎷烽敓鑴氱尨鎷峰钩  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002052,//12.00 鍚岄敓鐫鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002089,//13.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閫氶敓鏂ゆ嫹閿熷�熷
                                      9002115,//14.00 閿熸枻鎷风淮閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002151,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002161,//16.00 杩� 閿熸枻鎷� 閿熸枻鎷� 閫氶敓鏂ゆ嫹閿熷�熷
                                      9002194,//17.00 閿熸垝姹夐敓鏂ゆ嫹閿熸枻鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002231,//18.00 閿熸枻鎷风淮閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002281,//19.00 閿熸枻鎷疯繀閿熺嫛纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002296,//20.00 閿熺殕鐓岀纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002313,//21.00 閿熺Ц鐚存嫹閫氳  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002369,//22.00 鍗撻敓鏂ゆ嫹钀嶉敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002383,//23.00 閿熸枻鎷烽敓鏂ゆ嫹鎬濆．  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002384,//24.00 閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002396,//25.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002413,//26.00 閿熼樁绉戝嚖鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002417,//27.00 閿熸枻鎷峰厓閿熸枻鎷� 閫氶敓鏂ゆ嫹閿熷�熷
                                      9002446,//28.00 鐩涜矾閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002465,//29.00 閿熸枻鎷烽敓鏂ゆ嫹閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002491,//30.00 閫氶敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002519,//31.00 閿熸枻鎷烽敓鎺ョ鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002547,//32.00 閿熸枻鎷烽敓鍓挎拝鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002583,//33.00 閿熸枻鎷烽敓鏉拌揪鎷� 閫氶敓鏂ゆ嫹閿熷�熷
                                      9002792,//34.00 閫氶敓鏂ゆ嫹閫氳  閫氶敓鏂ゆ嫹閿熷�熷
                                      9002829,//35.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      300025,//36.00  閿熸枻鎷烽敓瑙掕揪鎷蜂笟  閫氶敓鏂ゆ嫹閿熷�熷
                                      300028,//37.00  閿熸枻鎷烽敓瑙掔纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300038,//38.00  姊呮嘲璇� 閫氶敓鏂ゆ嫹閿熷�熷
                                      300074,//39.00  閿熸枻鎷峰钩閿熺即鍑ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300079,//40.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯  閫氶敓鏂ゆ嫹閿熷�熷
                                      300081,//41.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛璁规嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300098,//42.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閫氶敓鏂ゆ嫹閿熷�熷
                                      300101,//43.00  閿熸枻鎷疯姱閿熺嫛纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300134,//44.00  閿熻瀵岀纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300136,//45.00  閿熸枻鎷风淮閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300167,//46.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯  閫氶敓鏂ゆ嫹閿熷�熷
                                      300177,//47.00  閿熷彨鐚存嫹閿熸枻鎷� 閫氶敓鏂ゆ嫹閿熷�熷
                                      300211,//48.00  閿熸枻鎷烽�氶敓鐙＄》鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      300213,//49.00  閿熸枻鎷疯閿熺即鐚存嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300250,//50.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閫氶敓鏂ゆ嫹閿熷�熷
                                      300264,//51.00  閿熺獤杈炬嫹閿熸枻鎷疯  閫氶敓鏂ゆ嫹閿熷�熷
                                      300270,//52.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300292,//53.00  閿熸枻鎷烽�氶敓鎴櫢鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      300299,//54.00  閿熸枻鎷烽敓鏂ゆ嫹閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300310,//55.00  閿熸枻鎷烽�氶敓鏂ゆ嫹閿熸枻鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      300312,//56.00  閿熸枻鎷疯閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300322,//57.00  纭曢敓鏂ゆ嫹閿熸枻鎷� 閫氶敓鏂ゆ嫹閿熷�熷
                                      300353,//58.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300394,//59.00  閿熸枻鎷烽敓鏂ゆ嫹閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300397,//60.00  閿熸枻鎷峰ご閿熸枻鎷烽敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      300493,//61.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300502,//62.00  閿熸枻鎷烽敓鏂ゆ嫹鐩� 閫氶敓鏂ゆ嫹閿熷�熷
                                      300555,//63.00  璺�氶敓鏂ゆ嫹閿熸枻鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      300560,//64.00  閿熷彨闈╂嫹閫� 閫氶敓鏂ゆ嫹閿熷�熷
                                      300563,//65.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      300565,//66.00  閿熸枻鎷烽敓鑴氱》鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      300590,//67.00  閿熸枻鎷蜂负閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600105,//68.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600130,//69.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600198,//70.00  閿熸枻鎷烽敓鐙＄鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600260,//71.00  閿熸枻鎷烽敓琛楃纭锋嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600345,//72.00  閿熸枻鎷烽敓鏂ゆ嫹閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600485,//73.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600487,//74.00  閿熸枻鎷烽�氶敓鏂ゆ嫹閿燂拷  閫氶敓鏂ゆ嫹閿熷�熷
                                      600498,//75.00  閿熸枻鎷烽敓閰殿煉鎷烽敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      600522,//76.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      600562,//77.00  閿熸枻鎷风澘绉戠》鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      600677,//78.00  閿熸枻鎷烽敓鏂ゆ嫹閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600680,//79.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600745,//80.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      600764,//81.00  閿熷彨纰夋嫹閿熼叺锟�  閫氶敓鏂ゆ嫹閿熷�熷
                                      600775,//82.00  閿熻緝鎾呮嫹閿熸枻鎷风尗  閫氶敓鏂ゆ嫹閿熷�熷
                                      600776,//83.00  閿熸枻鎷烽敓鏂ゆ嫹閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      600990,//84.00  閿熶茎杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      603118,//85.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      603322,//86.00  閿熸枻鎷疯閫氶敓鏂ゆ嫹  閫氶敓鏂ゆ嫹閿熷�熷
                                      603421,//87.00  閿熸枻鎷烽敓鏂ゆ嫹閫氳  閫氶敓鏂ゆ嫹閿熷�熷
                                      603559,//88.00  閿熸枻鎷烽�氶敓鏂ゆ嫹閿熸枻鎷�  閫氶敓鏂ゆ嫹閿熷�熷
                                      603660//89.00  閿熸枻鎷烽敓鎹风杈炬嫹  閫氶敓鏂ゆ嫹閿熷�熷
    ));
    
    List<Integer> listCode_30_bdt = new ArrayList<Integer>(Arrays.asList(
                                      9002079,//1.00  閿熸枻鎷烽敓鎹风櫢鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      9002119,//2.00  閿熸枻鎷峰己閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      9002129,//3.00  閿熷彨浼欐嫹閿熺即鍑ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      9002156,//4.00  閫氶敓鏂ゆ嫹寰敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      9002185,//5.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      9002218,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      9002371,//7.00  閿熸枻鎷烽敓瑙掔鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      9002449,//8.00  閿熸枻鎷烽敓瑙掔櫢鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      9002506,//9.00  鍗忛敓杞跨》鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      9002638,//10.00 閿熸枻鎷烽敓杈冪櫢鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      9002654,//11.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      9002724,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熻瀵奸敓鏂ゆ嫹
                                      9002745,//13.00 鏈ㄩ敓鏂ゆ嫹妫� 閿熻瀵奸敓鏂ゆ嫹
                                      9002815,//14.00 閿熸枻鎷烽敍纭锋嫹閿燂拷  閿熻瀵奸敓鏂ゆ嫹
                                      300046,//15.00  鍙伴敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300053,//16.00  娆ч敓鏂ゆ嫹閿熸枻鎷� 閿熻瀵奸敓鏂ゆ嫹
                                      300077,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熻瀵奸敓鏂ゆ嫹
                                      300080,//18.00  閿熼樁绛规嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300102,//19.00  涔鹃敓绉哥櫢鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      300111,//20.00  閿熸枻鎷烽敓绉稿尅鎷� 閿熻瀵奸敓鏂ゆ嫹
                                      300118,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300223,//22.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300232,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300241,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷  閿熻瀵奸敓鏂ゆ嫹
                                      300269,//25.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      300296,//26.00  閿熸枻鎷烽敓瑙掔鎷� 閿熻瀵奸敓鏂ゆ嫹
                                      300301,//27.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300303,//28.00  閿熸椋炵櫢鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      300317,//29.00  閿熸枻鎷蜂紵閿熺即鍑ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300323,//30.00  閿熸枻鎷烽敓鎺ョ櫢鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      300327,//31.00  閿熸枻鎷烽閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      300373,//32.00  閿熸枻鎷疯姼钀嶉敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      300389,//33.00  閿熸枻鎷烽敓鏂ゆ嫹妫� 閿熻瀵奸敓鏂ゆ嫹
                                      300582,//34.00  鑻遍敓鏂ゆ嫹閿熸枻鎷� 閿熻瀵奸敓鏂ゆ嫹
                                      600151,//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      600171,//36.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      600206,//37.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熻瀵奸敓鏂ゆ嫹
                                      600360,//38.00  閿熸枻鎷峰井閿熸枻鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      600401,//39.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熻瀵奸敓鏂ゆ嫹
                                      600460,//40.00  澹敓鏂ゆ嫹寰� 閿熻瀵奸敓鏂ゆ嫹
                                      600537,//41.00  閿熻妭鎾呮嫹閿熸枻鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      600584,//42.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      600667,//43.00  澶敓鏂ゆ嫹瀹炰笟  閿熻瀵奸敓鏂ゆ嫹
                                      600703,//44.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熻瀵奸敓鏂ゆ嫹
                                      600817,//45.00  *ST閿熸枻鎷风洓 閿熻瀵奸敓鏂ゆ嫹
                                      601012,//46.00  闅嗛敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
                                      601908,//47.00  閿熸枻鎷烽敓鏂ゆ嫹閫� 閿熻瀵奸敓鏂ゆ嫹
                                      603005,//48.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熻瀵奸敓鏂ゆ嫹
                                      603986//49.00  閿熸枻鎷烽敓闃惰揪鎷烽敓鏂ゆ嫹  閿熻瀵奸敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_31_yqj = new ArrayList<Integer>(Arrays.asList(
                                    9000020,//1.00  閿熺瓔鍗庨敓鏂ゆ嫹閿熸枻鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000032,//2.00  閿熸枻鎷锋閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000050,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000058,//4.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000068,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000413,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000532,//7.00  閿熸枻鎷烽敓杈冭偂鍑ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000536,//8.00  閿熸枻鎷锋槧閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000636,//9.00  閿熺晫鍗庨敓绔尅鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000670,//10.00 *ST鐩堥敓鏂ゆ嫹 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000725,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000727,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000733,//13.00 閿熶粖鍗庣纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000810,//14.00 閿熸枻鎷风淮閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000823,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9000970,//16.00 閿熷彨鍖℃嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002025,//17.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002036,//18.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002045,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002049,//20.00 閿熻緝鐧告嫹閿熷彨锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002055,//21.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002056,//22.00 閿熸枻鎷风敁顐嫹閿燂拷  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002057,//23.00 閿熷彨闈╂嫹閿熸枻鎷锋簮  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002104,//24.00 閿熷瀹濋敓缂村嚖鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002106,//25.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺鍖℃嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002134,//26.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002137,//27.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002138,//28.00 椤洪敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002139,//29.00 閿熸埅甯嫹鐓為敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002141,//30.00 閿熸枻鎷疯儨閿熸枻鎷峰井  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002179,//31.00 閿熷彨鐚存嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002189,//32.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002199,//33.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002217,//34.00 閿熸枻鎷烽敓鏂ゆ嫹娉� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002222,//35.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002241,//36.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002273,//37.00 姘撮敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002288,//38.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002289,//39.00 *ST閿熸枻鎷烽『 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002388,//40.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛绛规嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002389,//41.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002402,//42.00 閿熼叺璁规嫹娉� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002426,//43.00 鑳滈敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002436,//44.00 閿熸枻鎷锋．閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002456,//45.00 娆ч敓鐙＄櫢鎷� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002463,//46.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002475,//47.00 閿熸枻鎷疯閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002484,//48.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002512,//49.00 閿熸枻鍗庨敓鏂ゆ嫹閿熸枻鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002579,//50.00 閿熷彨鎾呮嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002587,//51.00 閿熸枻鎷烽敓鎴鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002600,//52.00 閿熸枻鎷烽敓妗旂璇ф嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002618,//53.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002636,//54.00 閿熼噾瀹夌櫢鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002655,//55.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002729,//56.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002806,//57.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002808,//58.00 閿熸枻鎷烽敓鎹风尨鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002835,//59.00 鍚屼负閿熺即鍑ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300014,//60.00  閿熸枻鎷风含閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300032,//61.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300078,//62.00  鎬濋敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300083,//63.00  閿熸枻鎷疯儨閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300088,//64.00  閿熸枻鎷烽敓鑴氱纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300114,//65.00  閿熷彨鐚存嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300115,//66.00  閿熸枻鎷风泩閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300127,//67.00  閿熸枻鎷烽敓鎺ヨ揪鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300128,//68.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300131,//69.00  鑻遍敓鏂ゆ嫹閿熻鍖℃嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300139,//70.00  閿熸枻鎷烽敓鏁欑纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300154,//71.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300155,//72.00  閿熸枻鎷烽敓鎺ユ唻鎷� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300162,//73.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300184,//74.00  閿熸枻鎷锋簮閿熸枻鎷锋伅  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300205,//75.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300207,//76.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300219,//77.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻浼欐嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300220,//78.00  閿熸枻鎷烽敓鍓跨》鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300224,//79.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻剼璇ф嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300227,//80.00  閿熸枻鎷烽敓杈冭揪鎷� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300256,//81.00  閿熸枻鎷烽敓瑙掔纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300279,//82.00  閿熼叺鎾呮嫹閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300319,//83.00  閿熸枻鎷疯彉钀嶉敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300331,//84.00  閿熺Ц杈炬嫹缁撮敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300333,//85.00  閿熸枻鎷烽敓绉哥纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300340,//86.00  閿熺嫛鐚存嫹鐓為敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300342,//87.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300346,//88.00  閿熻緝杈炬嫹閿熸枻鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300351,//89.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300408,//90.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300414,//91.00  閿熷彨鐧告嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300433,//92.00  閿熸枻鎷锋�濋敓鐙＄》鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300456,//93.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300458,//94.00  鍏ㄥ織閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300460,//95.00  閿熸枻鎷烽敓闃舵拝鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300474,//96.00  閿熸枻鎷烽敓鏂ゆ嫹寰� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300476,//97.00  鑳滈敓鏂ゆ嫹钀嶉敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300546,//98.00  閿熸甯濈纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300548,//99.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300566,//100.00 閿熸枻鎷烽敓瑙掔纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    300570,//101.00 澶敓鏂ゆ嫹閿熸枻鎷� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    9002841,//102.00 閿熸枻鎷锋簮閿熺即鍑ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600071,//103.00 閿熸枻鎷锋柉閿熺獤锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600183,//104.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600203,//105.00 閿熸枻鎷烽敓绉哥鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600207,//106.00 閿熸枻鎷烽敓缁為珮鍖℃嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600237,//107.00 閾滈敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600330,//108.00 閿熸枻鎷烽�氶敓缂村嚖鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600353,//109.00 閿熸枻鎷烽敓缂村嚖鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600355,//110.00 閿熸枻鎷烽敓闃剁鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600363,//111.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600462,//112.00 閿熸枻鎷烽敓鍙偂鍑ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600478,//113.00 閿熸枻鎷烽敓鏂ゆ嫹杩� 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600563,//114.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600666,//115.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600707,//116.00 閿熺粸鐚存嫹鐓為敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    600980,//117.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    601231,//118.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603160,//119.00 閿熷椤堕敓鐙＄》鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603328,//120.00 閿熸枻鎷烽敓鍔鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603633,//121.00 閿熸枻鎷锋湪閿熺即鍑ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603678,//122.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603738,//123.00 娉伴敓鏂ゆ嫹閿熺嫛纭锋嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603936,//124.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603989,//125.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603186,//126.00 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
                                    603228//127.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏冮敓鏂ゆ嫹閿熸枻鎷�
    
    ));
    
    List<Integer> listCode_32_rjfw = new ArrayList<Integer>(Arrays.asList(
                                    9000555,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9000662,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9000711,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9000851,//4.00  閿熺鐚存嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9000938,//5.00  閿熻緝鐧告嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9000948,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9000997,//7.00  閿熸枻鎷� 閿熸枻鎷� 闄� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002027,//8.00  閿熸枻鎷烽敓鑺傝揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002063,//9.00  杩滈敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002065,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002073,//11.00 閿熸枻鎷锋瘬鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002090,//12.00 閿熸枻鎷烽敓瑙掔纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002153,//13.00 鐭抽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002184,//14.00 閿熸枻鎷烽敓鐭尅鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002195,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002230,//16.00 閿熺嫛杈炬嫹璁敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002232,//17.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002253,//18.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯儨  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002268,//19.00 閿熸枻鎷� 澹� 閫� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002279,//20.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002280,//21.00 閿熸枻鎷烽敓鐣屼簰閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002298,//22.00 閿熷彨纰夋嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002316,//23.00 閿熸枻鎷烽敓鏂ゆ嫹閫氳  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002331,//24.00 閿熸枻鎷烽�氶敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002368,//25.00 澶敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002373,//26.00 鍗冮敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002401,//27.00 閿熷彨鐚存嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002405,//28.00 閿熸枻鎷风淮鍥鹃敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002410,//29.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002421,//30.00 閿熸枻鎷峰疄閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002439,//31.00 閿熸枻鎷烽敓鏂ゆ嫹閿熻绛规嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002474,//32.00 閿熻剼浼欐嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002544,//33.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002609,//34.00 閿熸枻鎷烽『閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002642,//35.00 閿熸枻鎷蜂箣閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002649,//36.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002657,//37.00 閿熷彨绉戞枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002771,//38.00 閿熸枻鎷烽敓鏂ゆ嫹閫� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    9002777,//39.00 閿熸枻鎷疯繙閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300002,//40.00  閿熸枻鎷烽敓鏂ゆ嫹娉伴敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300010,//41.00  閿熸枻鎷锋�濋敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300020,//42.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300033,//43.00  鍚岄敓鏂ゆ嫹椤� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300036,//44.00  閿熸枻鎷峰浘閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300044,//45.00  閿熸枻鎷蜂负閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300047,//46.00  閿熸枻鎷锋簮閿熻緝鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300050,//47.00  閿熸枻鎷烽敓閰佃鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300065,//48.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300075,//49.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300085,//50.00  閿熸枻鎷蜂箣閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300096,//51.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300150,//52.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300166,//53.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300168,//54.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻緝锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300170,//55.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300182,//56.00  閿熸嵎鎴愯偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300183,//57.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300188,//58.00  閿熸枻鎷烽敓瑙掓煆鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300209,//59.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300212,//60.00  閿熼樁浼欐嫹褰� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300229,//61.00  閿熸埅璁规嫹鎬� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300231,//62.00  閿熸枻鎷烽敓鑴氱纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300235,//63.00  閿熸枻鎷风洿閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300245,//64.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300248,//65.00  閿熼摪鍖℃嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300253,//66.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300271,//67.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300275,//68.00  姊呴敓鏂ゆ嫹妫� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300277,//69.00  閿熸枻鎷烽敓鏂ゆ嫹璁� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300287,//70.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300288,//71.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300290,//72.00  閿熷姭绉戠纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300297,//73.00  閿熸枻鎷烽敓鏉拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300300,//74.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300302,//75.00  鍚岄敓鍙纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300311,//76.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300324,//77.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300330,//78.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼叺锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300339,//79.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300348,//80.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300352,//81.00  閿熸枻鎷烽敓鏂ゆ嫹婧� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300365,//82.00  閿熷鍗庨敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300366,//83.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300369,//84.00  閿熸枻鎷烽敓鍓跨纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300377,//85.00  璧㈡椂鑳� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300378,//86.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300379,//87.00  閿熸枻鎷烽敓鏂ゆ嫹閫� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300380,//88.00  閿熸枻鎷风閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300386,//89.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300419,//90.00  閿熺嫛鍑ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300440,//91.00  閿熷壙杈炬嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300448,//92.00  閿熸枻鎷烽敓鐙＄纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300451,//93.00  閿熸枻鎷蜂笟閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300465,//94.00  閿熸枻鎷蜂紵閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300468,//95.00  閿熶茎鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300469,//96.00  閿熸枻鎷锋伅閿熸枻鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300479,//97.00  閿熸枻鎷锋�濋敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300496,//98.00  閿熷彨绉戣揪鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300508,//99.00  缁撮敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300513,//100.00 閿熸枻鎷锋嘲瀹為敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300518,//101.00 鐩涜閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300520,//102.00 閿熺嫛杈炬嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300523,//103.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300525,//104.00 閿熸枻鎷锋�濋敓鏂ゆ嫹閿燂拷  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300531,//105.00 閿熻剼璇ф嫹璁� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300532,//106.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300533,//107.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300541,//108.00 閿熼ズ鏂ゆ嫹閿熸枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300542,//109.00 閿熼摪绛规嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300550,//110.00 閿熸枻鎷烽敓缁炵纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300552,//111.00 閿熸触闆嗙纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300556,//112.00 涓濊矾閿熸帴鎾呮嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300559,//113.00 閿熺獤鍑ゆ嫹閿熸枻鎷锋嘲  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300561,//114.00 閿熸枻鎷烽敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300579,//115.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯瘉  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300588,//116.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    300592,//117.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600288,//118.00 閿熸枻鎷烽敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600289,//119.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600406,//120.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600410,//121.00 閿熸枻鎷疯儨閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600446,//122.00 閿熸枻鎷疯瘉閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600455,//123.00 閿熸枻鎷烽�氶敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600476,//124.00 閿熸枻鎷烽敓缁炵纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600536,//125.00 閿熷彨鐧告嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600556,//126.00 ST閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600570,//127.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600571,//128.00 閿熸枻鎷烽敓鑴氳揪鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600588,//129.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600602,//130.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600654,//131.00 閿熷彨甯嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600718,//132.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600728,//133.00 閿熺獤璁规嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600756,//134.00 閿熷壙绛规嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600797,//135.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600845,//136.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    600850,//137.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    601519,//138.00 閿熸枻鎷烽敓瑙掍紮鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603189,//139.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603508,//140.00 鎬濈淮閿熷彨鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603528,//141.00 閿熸枻鎷烽敓闃剁纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603636,//142.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603859,//143.00 閿熸澃绉戣偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603918,//144.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603990,//145.00 閿熸枻鎷峰场钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
                                    603039//146.00 閿熸枻鎷峰井閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
    
    ));
    
    List<Integer> listCode_33_hlw = new ArrayList<Integer>(Arrays.asList(
                                    9000676,//1.00  閿熻搴﹁偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9000681,//2.00  閿熸帴鎾呮嫹閿熷彨鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9000971,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002095,//4.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002113,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002123,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002127,//7.00  閿熻緝纭锋嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002131,//8.00  閿熸枻鎷锋閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002148,//9.00  閿熸枻鎷风含閫氶敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002174,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002261,//11.00 閿熸枻鎷风淮閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002315,//12.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002354,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002464,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002517,//15.00 閿熸枻鎷疯嫳閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    9002555,//16.00 閿熸枻鎷烽敓绔紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300051,//17.00  閿熸枻鎷烽敓钘変簰閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300052,//18.00  閿熸枻鎷烽敓娲佸疂 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300059,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛闈╂嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300104,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300113,//21.00  椤洪敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300226,//22.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300242,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300295,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300315,//25.00  閿熸枻鎷疯叮閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300343,//26.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300392,//27.00  閿熸枻鎷烽敓鑴氳偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300399,//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300418,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷风淮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300431,//30.00  閿熸枻鎷烽敓鐣岄泦閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300467,//31.00  杩呴敓杞跨纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300494,//32.00  鐩涢敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    300571,//33.00  骞抽敓鏂ゆ嫹閿熸枻鎷锋伅  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    600652,//34.00  閿熻娇鎾呮嫹閿熸枻鎷锋垙  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    600986,//35.00  閿熺嫛杈炬嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    603000,//36.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    603258,//37.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    603888,//38.00  閿熼摪浼欐嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
                                    603444//39.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
    
    ));
    
    List<Integer> listCode_34_zhl = new ArrayList<Integer>(Arrays.asList(
                                  9000009,//1.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000034,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000301,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷彨绛规嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000409,//4.00  灞遍敓鏂ゆ嫹閿熸埅鍖℃嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000632,//5.00  閿熸枻鎷锋湪閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000633,//6.00  *ST閿熻緝鏂ゆ嫹 閿熸鐚存嫹閿熸枻鎷�
                                  9000701,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻剼杈炬嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000839,//8.00  閿熸枻鎷烽敓鑴氱櫢鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000881,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  9000909,//10.00 閿熸枻鎷锋簮閿熺嫛纭锋嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600051,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600149,//12.00  閿熼ズ鍑ゆ嫹閿熸枻鎷峰睍  閿熸鐚存嫹閿熸枻鎷�
                                  600175,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸鐚存嫹閿熸枻鎷�
                                  600200,//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600209,//15.00  閿熺潾椤垮嚖鎷峰睍  閿熸鐚存嫹閿熸枻鎷�
                                  600212,//16.00  *ST閿熸枻鎷锋硥 閿熸鐚存嫹閿熸枻鎷�
                                  600256,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺殕锟�  閿熸鐚存嫹閿熸枻鎷�
                                  600603,//18.00  *ST閿熸枻鎷蜂笟 閿熸鐚存嫹閿熸枻鎷�
                                  600614,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600620,//20.00  閿熸枻鎷峰鑲″嚖鎷�  閿熸鐚存嫹閿熸枻鎷�
                                  600624,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600647,//22.00  鍚岄敓鏂ゅ垱涓�  閿熸鐚存嫹閿熸枻鎷�
                                  600701,//23.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸鐚存嫹閿熸枻鎷�
                                  600730,//24.00  閿熷彨鐧告嫹閿熺鍖℃嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600770,//25.00  閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600777,//26.00  閿熼摪绛规嫹閿熸枻鎷锋簮  閿熸鐚存嫹閿熸枻鎷�
                                  600800,//27.00  閿熸枻鎷烽敓鑴氬尅鎷�  閿熸鐚存嫹閿熸枻鎷�
                                  600805,//28.00  閿熺煫杈炬嫹鎶曢敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600811,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600846,//30.00  鍚岄敓鐭纭锋嫹  閿熸鐚存嫹閿熸枻鎷�
                                  600892//31.00  閿熸枻鎷烽敓鏂ゆ嫹閿熶茎浼欐嫹  閿熸鐚存嫹閿熸枻鎷�
    
    ));
    
    List<Integer> listCode_35_mtkc = new ArrayList<Integer>(Arrays.asList(
                                  9000552,//1.00  閿熸枻鎷疯繙鐓ら敓鏂ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  9000571,//2.00  閿熼摪杈炬嫹閿熺潾锝忔嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  9000780,//3.00  骞冲簞閿熸枻鎷锋簮  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  9000933,//4.00  *ST閿熸枻鎷烽敓锟� 鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  9000937,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  9000968,//6.00  *ST鐓ら敓鏂ゆ嫹 鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  9000983,//7.00  閿熸枻鎷峰北鐓ら敓鏂ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  9002128,//8.00  闇查敓鏂ゆ嫹鐓や笟  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600121,//9.00 閮戦敓鏂ゆ嫹鐓ら敓鏂ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600123,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛杈炬嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600157,//11.00  閿熸枻鎷锋嘲閿熸枻鎷锋簮  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600188,//12.00  閿熸枻鎷烽敓鏂ゆ嫹鐓や笟  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600348,//13.00  閿熸枻鎷锋硥鐓や笟  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600395,//14.00  閿熸暀鏂ゆ嫹閿熺即鍑ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600397,//15.00  閿熸枻鎷锋簮鐓や笟  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600403,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600508,//17.00  閿熻緝鐚存嫹閿熸枻鎷锋簮  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600546,//18.00  *ST灞辩叅 鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600714,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600758,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600971,//21.00  閿熸枻鎷锋簮鐓ら敓鏂ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  600997,//22.00  閿熸枻鎷烽敓鍙偂鍑ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601001,//23.00  閿熸枻鎷峰悓鐓や笟  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601088,//24.00  閿熷彨鐧告嫹閿熸枻鎷�  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601101,//25.00  鏄婁紮鎷烽敓鏂ゆ嫹婧�  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601225,//26.00  閿熸枻鎷烽敓鏂ゆ嫹鐓や笟  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601666,//27.00  骞崇叅閿熺即鍑ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601699,//28.00  娼為敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601898,//29.00  閿熸枻鎷风叅閿熸枻鎷锋簮  鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  601918//30.00  *ST閿熼摪纭锋嫹 鐓ょ偔閿熸枻鎷烽敓鏂ゆ嫹
                                  
    ));
    
    List<Integer> listCode_36_jtjg = new ArrayList<Integer>(Arrays.asList(
                                  9000723,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷风偔閿熸帴鐧告嫹
                                  600408,//2.00 閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风偔閿熸帴鐧告嫹
                                  600721,//3.00 *ST閿熷姭浼欐嫹 閿熸枻鎷风偔閿熸帴鐧告嫹
                                  600725,//4.00 *ST閿熸枻鎷风淮 閿熸枻鎷风偔閿熸帴鐧告嫹
                                  600740,//5.00 灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风偔閿熸帴鐧告嫹
                                  600792,//6.00 閿熸枻鎷风叅閿熸枻鎷锋簮  閿熸枻鎷风偔閿熸帴鐧告嫹
                                  601011,//7.00 閿熸枻鎷锋嘲闅� 閿熸枻鎷风偔閿熸帴鐧告嫹
                                  601015//8.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷风尗  閿熸枻鎷风偔閿熸帴鐧告嫹
    
    ));
    
    List<Integer> listCode_37_slfd = new ArrayList<Integer>(Arrays.asList(
                                9000601,//1.00  閿熸枻鎷烽敓鏉拌偂鍑ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9000722,//2.00  閿熸枻鎷烽敓杈冨嚖鎷峰睍  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9000791,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼叺锟�  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9000993,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                9002039,//5.00  榛旀簮閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600101,//6.00 閿熸枻鎷烽敓瑙掔鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600116,//7.00 閿熸枻鎷峰场姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600131,//8.00 宀锋枻鎷锋按閿熸枻鎷�  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600236,//9.00 閿熸枻鎷疯瘶閿熸枻鎷烽敓锟�  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600310,//10.00  閿熼噾涓滅鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600452,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600505,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600644,//13.00  閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600674,//14.00  閿熸枻鎷锋姇閿熸枻鎷锋簮  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600868,//15.00  姊呴敓濮愬悏閿熸枻鎷�  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600886,//16.00  閿熸枻鎷锋姇閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600900,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600969,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600979,//19.00  閿熷瀹夐敓鏂ゆ嫹閿熸枻鎷�  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                                600995//20.00  閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_38_hldf = new ArrayList<Integer>(Arrays.asList(
                              9000027,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000037,//2.00  *ST閿熻緝纰夋嫹A  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000531,//3.00  閿熸枻鎷烽敓鏂ゆ嫹鑰嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000539,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000543,//5.00  閿熸枻鎷烽敓鏉扮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000600,//6.00  閿熸枻鎷锋姇閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000690,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000720,//8.00  閿熸枻鎷烽敓鏂ゆ嫹娉板北  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000767,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000875,//10.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000883,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000899,//12.00 閿熸枻鎷烽敓鏉拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000958,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9000966,//14.00 閿熸枻鎷锋簮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9001896,//15.00 璞敓鏉版帶鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              9002479,//16.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600011,//17.00  閿熸枻鎷烽敓鏉扮櫢鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600021,//18.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600023,//19.00  閿熸枻鎷烽敓鏉扮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600027,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600098,//21.00  閿熸枻鎷烽敓鎹峰嚖鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600396,//22.00  閿熸枻鎷峰北閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600483,//23.00  閿熸枻鎷烽敓鏉拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600509,//24.00  閿熷眾瀵岄敓鏂ゆ嫹婧�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600578,//25.00  閿熸枻鎷烽敓鏉扮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600642,//26.00  閿熸枻鎷烽敓鏉拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600726,//27.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600744,//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600780,//29.00  閫氶敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600795,//30.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600863,//31.00  閿熸枻鎷烽敓缂翠紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              600864,//32.00  閿熸枻鎷锋姇閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
                              601991//33.00  閿熸枻鎷烽敓鐙″嚖鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_39_xxdl = new ArrayList<Integer>(Arrays.asList(
                            9000591,//1.00  澶敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓閰电鎷烽敓鏂ゆ嫹
                            9000862,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓閰电鎷烽敓鏂ゆ嫹
                            9000939,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋��  閿熸枻鎷烽敓閰电鎷烽敓鏂ゆ嫹
                            600163,//4.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓閰电鎷烽敓鏂ゆ嫹
                            600277,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓閰电鎷烽敓鏂ゆ嫹
                            601016,//6.00 閿熸枻鎷烽敓鏉板嚖鎷烽敓锟�  閿熸枻鎷烽敓閰电鎷烽敓鏂ゆ嫹
                            601985//7.00 閿熷彨鐧告嫹閿熷壙纰夋嫹  閿熸枻鎷烽敓閰电鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_40_sykc = new ArrayList<Integer>(Arrays.asList(
                        9002207,//1.00  鍑嗛敓閰佃偂鍑ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        9002554,//2.00  閿熸嵎璇ф嫹閿熸枻鎷� 鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        9002629,//3.00  閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        9002828,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        300084,//5.00 閿熸枻鎷烽粯閿熺嫛纭锋嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        300157,//6.00 閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        300164,//7.00 閫氭簮鐭抽敓鏂ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        300191,//8.00 娼滈敓鏉扮尨鎷烽敓鏂ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        600583,//9.00 閿熸枻鎷烽敓閰电櫢鎷烽敓鏂ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        600759,//10.00  閿熺潾纭锋嫹閿熸枻鎷烽敓鏂ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        600871,//11.00  鐭抽敓鏂ゆ嫹閿熼叺鍑ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        601808,//12.00  閿熷彨鐚存嫹閿熼叺鍑ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        601857,//13.00  閿熷彨鐧告嫹鐭抽敓鏂ゆ嫹  鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
                        603727//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鐭抽敓閰靛尅鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_41_syjg = new ArrayList<Integer>(Arrays.asList(
                      9000059,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鐭抽敓閰靛姞鐧告嫹
                      9000637,//2.00  鑼傞敓鏂ゆ嫹瀹為敓鏂ゆ嫹  鐭抽敓閰靛姞鐧告嫹
                      9000819,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷壙绛规嫹  鐭抽敓閰靛姞鐧告嫹
                      9002377,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鐭抽敓閰靛姞鐧告嫹
                      9002778,//5.00  閿熺鍖℃嫹鐭抽敓鏂ゆ嫹  鐭抽敓閰靛姞鐧告嫹
                      600028,//6.00 閿熷彨鐧告嫹鐭抽敓鏂ゆ嫹  鐭抽敓閰靛姞鐧告嫹
                      600339,//7.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 鐭抽敓閰靛姞鐧告嫹
                      600688,//8.00 閿熻緝鐚存嫹鐭抽敓鏂ゆ嫹  鐭抽敓閰靛姞鐧告嫹
                      603798//9.00 閿熸枻鎷烽敓绉歌鎷� 鐭抽敓閰靛姞鐧告嫹
    ));
    List<Integer> listCode_42_symy = new ArrayList<Integer>(Arrays.asList(
                      9000096,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺殕锟�  鐭抽敓鏂ゆ嫹璐搁敓鏂ゆ嫹
                      9000159,//2.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  鐭抽敓鏂ゆ嫹璐搁敓鏂ゆ嫹
                      9000554,//3.00  娉板北鐭抽敓鏂ゆ嫹  鐭抽敓鏂ゆ嫹璐搁敓鏂ゆ嫹
                      9002221,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  鐭抽敓鏂ゆ嫹璐搁敓鏂ゆ嫹
                      600387,//5.00 閿熸枻鎷疯秺閿熺即鍑ゆ嫹  鐭抽敓鏂ゆ嫹璐搁敓鏂ゆ嫹
                      603003//6.00 閿熸枻鎷烽敓鏂ゆ嫹鐕冮敓鏂ゆ嫹  鐭抽敓鏂ゆ嫹璐搁敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_43_pg = new ArrayList<Integer>(Arrays.asList(
                      9000629,//1.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熺Ц闈╂嫹
                      9000655,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  閿熺Ц闈╂嫹
                      9000709,//3.00  閿熸帴閽㈣偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      9000898,//4.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      9000932,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熺Ц闈╂嫹
                      9000959,//6.00  閿熼樁閽㈣偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      9002110,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熺Ц闈╂嫹
                      9002478,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熺Ц闈╂嫹
                      600005,//9.00 閿熸枻鎷锋鐓為敓锟�  閿熺Ц闈╂嫹
                      600010,//10.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      600019,//11.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      600022,//12.00  灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熺Ц闈╂嫹
                      600126,//13.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      600231,//14.00  閿熸枻鎷锋鐓為敓锟�  閿熺Ц闈╂嫹
                      600282,//15.00  閿熻緝閽㈣偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      600307,//16.00  閿熺嫛閽㈢尨鎷烽敓鏂ゆ嫹  閿熺Ц闈╂嫹
                      600532,//17.00  閿熸枻鎷烽敓鏂ゆ嫹涓�  閿熺Ц闈╂嫹
                      600569,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熺Ц闈╂嫹
                      600581,//19.00  *ST閿熷壙闈╂嫹 閿熺Ц闈╂嫹
                      600608,//20.00  閿熻緝鐚存嫹閿熺嫛纭锋嫹  閿熺Ц闈╂嫹
                      600784,//21.00  椴侀敓鏂ゆ嫹鎶曢敓鏂ゆ嫹  閿熺Ц闈╂嫹
                      600808,//22.00  閿熸枻鎷锋鐓為敓锟�  閿熺Ц闈╂嫹
                      601003,//23.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熺Ц闈╂嫹
                      601005,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熺Ц闈╂嫹
                      601969//25.00  閿熸枻鎷烽敓杈冨尅鎷蜂笟  閿熺Ц闈╂嫹
    
    ));
    
    List<Integer> listCode_44_tzg = new ArrayList<Integer>(Arrays.asList(
                    9000708,//1.00  閿熸枻鎷峰喍閿熸埅闈╂嫹  閿熸枻鎷烽敓琛楅潻鎷�
                    9000825,//2.00  澶敓琛楄鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓琛楅潻鎷�
                    9002075,//3.00  娌欓敓琛楄偂鍑ゆ嫹  閿熸枻鎷烽敓琛楅潻鎷�
                    9002318,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  閿熸枻鎷烽敓琛楅潻鎷�
                    9002423,//5.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓琛楅潻鎷�
                    9002756,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅闈╂嫹  閿熸枻鎷烽敓琛楅潻鎷�
                    600117,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸埅闈╂嫹  閿熸枻鎷烽敓琛楅潻鎷�
                    600399,//8.00 閿熸枻鎷烽『閿熸埅闈╂嫹  閿熸枻鎷烽敓琛楅潻鎷�
                    600507//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸埅闈╂嫹  閿熸枻鎷烽敓琛楅潻鎷�
    ));
    
    List<Integer> listCode_45_gjg = new ArrayList<Integer>(Arrays.asList(
                    9000717,//1.00  *ST閿熸埅闈╂嫹 閿熻鍔犵櫢鎷�
                    9000761,//2.00  閿熸枻鎷烽敓琛楀府鎷烽敓锟�  閿熻鍔犵櫢鎷�
                    9000778,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻鍔犵櫢鎷�
                    9000890,//4.00  閿熸枻鎷� 閿熸枻鎷� 鑳� 閿熻鍔犵櫢鎷�
                    9000969,//5.00  閿熸枻鎷锋嘲閿熺嫛纭锋嫹  閿熻鍔犵櫢鎷�
                    9002132,//6.00  閿熸枻鎷烽敓瑙掔纭锋嫹  閿熻鍔犵櫢鎷�
                    9002352,//7.00  閿熸枻鎷锋嘲閿熼摪璇ф嫹  閿熻鍔犵櫢鎷�
                    9002359,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻鍔犵櫢鎷�
                    9002443,//9.00  閿熸枻鎷烽敓鐫纰夋嫹  閿熻鍔犵櫢鎷�
                    9002487,//10.00 閿熸枻鎷烽敓鏂ゆ嫹姣撻敓锟�  閿熻鍔犵櫢鎷�
                    9002541,//11.00 閿熸枻鎷疯矾閿熻鐧告嫹  閿熻鍔犵櫢鎷�
                    9002545,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熻鍔犵櫢鎷�
                    9002743,//13.00 閿熸枻鎷烽敓閰甸挗鐧告嫹  閿熻鍔犵櫢鎷�
                    300345,//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熻鍔犵櫢鎷�
                    9002843,//15.00 娉伴敓杞胯偂鍑ゆ嫹  閿熻鍔犵櫢鎷�
                    600165,//16.00  閿熸枻鎷烽敓绉哥尨鎷烽敓鏂ゆ嫹  閿熻鍔犵櫢鎷�
                    600477,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻鐧告嫹  閿熻鍔犵櫢鎷�
                    600496,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻鐧告嫹  閿熻鍔犵櫢鎷�
                    600558,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熻鍔犵櫢鎷�
                    600782,//20.00  閿熼摪閽㈣偂鍑ゆ嫹  閿熻鍔犵櫢鎷�
                    600992,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熻鍔犵櫢鎷�
                    601028,//22.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熻鍔犵櫢鎷�
                    603028,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熻鍔犵櫢鎷�
                    603300,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熻鍔犵櫢鎷�
                    603577,//25.00  閿熸枻鎷烽敓閰碉拷 閿熻鍔犵櫢鎷�
                    603878//26.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熻鍔犵櫢鎷�
    
    ));
    
    List<Integer> listCode_46_t =new ArrayList<Integer>( Arrays.asList(
                          9000630,//1.00  閾滈敓鏂ゆ嫹閿熸枻鎷疯壊  閾�
                          9000878,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閾滀笟  閾�
                          9002171,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閾�
                          9002203,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閾�
                          9002295,//5.00  閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  閾�
                          600139,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閾�
                          600255,//7.00 閿熻娇绉戣鎷烽敓鏂ゆ嫹  閾�
                          600362,//8.00 閿熸枻鎷烽敓鏂ゆ嫹閾滀笟  閾�
                          600490,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閾�
                          601137,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻緝鏂ゆ嫹  閾�
                          601168//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閾�
    ));
    List<Integer> listCode_47_l = new ArrayList<Integer>(Arrays.asList(
                    9000612,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷�
                    9000807,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷�
                    9002082,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷�
                    9002160,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷�
                    9002333,//5.00  閿熸枻鎷烽敓鏂ゆ嫹鏂敓鏂ゆ嫹  閿熸枻鎷�
                    9002379,//6.00  *ST椴侀敓鏂ゆ嫹 閿熸枻鎷�
                    9002501,//7.00  閿熸枻鎷锋簮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷�
                    9002540,//8.00  閿熸枻鎷峰お閿熺嫛纭锋嫹  閿熸枻鎷�
                    9002578,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷�
                    300328,//10.00  閿熷壙甯嫹閿熺嫛纭锋嫹  閿熸枻鎷�
                    300337,//11.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷�
                    300428,//12.00  閿熸枻鎷烽�氶敓閾拌鎷�  閿熸枻鎷�
                    300489,//13.00  閿熷彨椋炶偂鍑ゆ嫹  閿熸枻鎷�
                    9002824,//14.00  閿熸枻鎷疯儨閿熺即鍑ゆ嫹  閿熸枻鎷�
                    600219,//15.00  閿熸枻鎷峰北閿熸枻鎷蜂笟  閿熸枻鎷�
                    600595,//16.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  閿熸枻鎷�
                    600673,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷�
                    600768,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷�
                    600888,//19.00  閿熼摪鏂ゆ嫹閿熻妭鐚存嫹  閿熸枻鎷�
                    601388,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷�
                    601600,//21.00  閿熷彨鐧告嫹閿熸枻鎷蜂笟  閿熸枻鎷�
                    601677//22.00  閿熸枻鎷锋嘲閿熸枻鎷蜂笟  閿熸枻鎷�
    ));
    List<Integer> listCode_47_yx = new ArrayList<Integer>(Arrays.asList(
                9000060,//1.00  閿熷彨鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閾呴攲
                9000426,//2.00  閿熸枻鎷蜂笟閿熸枻鎷蜂笟  閾呴攲
                9000603,//3.00  鐩涢敓鏂ゆ嫹閿熸彮锟�  閾呴攲
                9000688,//4.00  閿熸枻鎷烽敓閾板尅鎷蜂笟  閾呴攲
                9000751,//5.00  閿屼笟閿熺即鍑ゆ嫹  閾呴攲
                9000758,//6.00  閿熸枻鎷疯壊閿熺即鍑ゆ嫹  閾呴攲
                9000975,//7.00  閿熸枻鎷锋嘲閿熸枻鎷锋簮  閾呴攲
                9002114,//8.00  閿熸枻鎷峰钩閿岄敓鏂ゆ嫹  閾呴攲
                600331,//9.00 閿熸枻鎷烽敓缂村嚖鎷�  閾呴攲
                600338,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閾呴攲
                600497,//11.00  閿熸鐚存嫹閿岄敓鏂ゆ嫹  閾呴攲
                600531,//12.00  璞敓鏂ゆ嫹閿熻锟�  閾呴攲
                600961,//13.00  閿熸枻鎷峰喍閿熸枻鎷烽敓鏂ゆ嫹  閾呴攲
                601020//14.00  閿熸枻鎷烽敓鑺傚尅鎷蜂笟  閾呴攲
    
    ));
    
    List<Integer> listCode_48_hj = new ArrayList<Integer>(Arrays.asList(
                9002155,//1.00  閿熸枻鎷烽敓杈冮粍鏂ゆ嫹  閿熺嫛鏂ゆ嫹
                9002237,//2.00  閿熸枻鎷烽敓缂村嚖鎷�  閿熺嫛鏂ゆ嫹
                600311,//3.00 閿熷姭浼欐嫹瀹炰笟  閿熺嫛鏂ゆ嫹
                600385,//4.00 灞遍敓鏂ゆ嫹閿熸枻鎷锋嘲  閿熺嫛鏂ゆ嫹
                600489,//5.00 閿熷彨鏂ゆ嫹骞抽敓锟�  閿熺嫛鏂ゆ嫹
                600547,//6.00 灞遍敓鏂ゆ嫹閿熺嫛鏂ゆ嫹  閿熺嫛鏂ゆ嫹
                600687,//7.00 閿熸枻鎷锋嘲閿熸埅鐧告嫹  閿熺嫛鏂ゆ嫹
                600766,//8.00 鍥敓瑙掗粍鏂ゆ嫹  閿熺嫛鏂ゆ嫹
                600988,//9.00 閿熸枻鎷烽敓鐙℃枻鎷�  閿熺嫛鏂ゆ嫹
                601069,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛鏂ゆ嫹  閿熺嫛鏂ゆ嫹
                601899//11.00  閿熻緝鏂ゆ嫹閿熸彮锟�  閿熺嫛鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_49_xjs = new ArrayList<Integer>(Arrays.asList(
                  9000657,//1.00  閿熸枻鎷烽敓鍔潻鎷烽敓鏂ゆ嫹  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9000693,//2.00  ST閿熸枻鎷烽敓鏂ゆ嫹  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9000697,//3.00  閿熸枻鎷风煶閿熸枻鎷疯壊  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9000762,//4.00  閿熸枻鎷烽敓鎴尅鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9000831,//5.00  *ST閿熸枻鎷风█ 灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9000960,//6.00  閿熸枻鎷蜂笟閿熺即鍑ゆ嫹  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9000962,//7.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002149,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002167,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002182,//10.00 閿熺嫛鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002340,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002378,//12.00 閿熸枻鎷锋簮閿熸枻鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002428,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002460,//14.00 閿熸帴鍑ゆ嫹閿熸彮锟�  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002466,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002716,//16.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  300034,//17.00  閿熸枻鎷烽敓鍙潻鎷烽敓鏂ゆ嫹  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  9002842,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600111,//19.00  閿熸枻鎷烽敓鏂ゆ嫹绋�閿熸枻鎷�  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600259,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯壊  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600390,//21.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600392,//22.00  鐩涢敓鏂ゆ嫹閿熸枻鎷锋簮  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600432,//23.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600456,//24.00  閿熸枻鎷烽敓绐栬偂鍑ゆ嫹  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600459,//25.00  閿熸枻鎷烽敓鍙鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600549,//26.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600615,//27.00  閿熺粨鍗庨敓缂村嚖鎷�  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  600711,//28.00  鐩涢敓閰靛尅鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  601958,//29.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  603399,//30.00  閿熼摪浼欐嫹閿熸枻鎷� 灏忛敓鏂ゆ嫹閿熸枻鎷�
                  603799,//31.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  603993,//32.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  灏忛敓鏂ゆ嫹閿熸枻鎷�
                  601212//33.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯壊  灏忛敓鏂ゆ嫹閿熸枻鎷�
    ));
    
    List<Integer> listCode_50_hgyl = new ArrayList<Integer>(Arrays.asList(
                9000510,//1.00  閿熸枻鎷疯矾閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000545,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000635,//3.00  鑻� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000683,//4.00  杩滈敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000698,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000707,//6.00  鍙岄敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000755,//7.00  灞遍敓鏂ゆ嫹閿熸枻鎷风淮  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000818,//8.00  閿熸枻鎷烽敓瑗熷寲鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000822,//9.00  灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000985,//10.00 閿熸枻鎷烽敓灞婂崕閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9000990,//11.00 閿熸枻鎷峰織閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002002,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002037,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002054,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002061,//15.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002068,//16.00 閿熸枻鎷风尗閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002092,//17.00 閿熸枻鎷锋嘲閿熸枻鎷峰  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002096,//18.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002109,//19.00 *ST閿熷壙浼欐嫹 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002125,//20.00 閿熸枻鎷锋江閿熺晫鍖�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002136,//21.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002145,//22.00 閿熷彨鐚存嫹閿熺獤甯嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002165,//23.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002226,//24.00 閿熸枻鎷烽敓杈冧紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002246,//25.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002250,//26.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002326,//27.00 閿熸枻鎷峰お閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002341,//28.00 閿熸枻鎷烽敓鑺傜纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002360,//29.00 鍚岄敓閾颁紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002361,//30.00 閿熶粖鍓戣偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002386,//31.00 閿熸枻鎷峰師閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002407,//32.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002408,//33.00 閿熸枻鎷烽敓鏂ゆ嫹閿熻妭杈炬嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002409,//34.00 閿熻剼鍏嬬纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002442,//35.00 閿熸枻鎷烽敓瑙掍紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002450,//36.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002453,//37.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002455,//38.00 閿熷姭杈炬嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002476,//39.00 閿熸枻鎷疯帿閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002497,//40.00 閿熻剼浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002562,//41.00 閿熻寮熺纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002584,//42.00 閿熸枻鎷烽檱閿熸枻鎷峰  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002591,//43.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002597,//44.00 閿熸枻鎷烽敓缁炵媱锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002601,//45.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002632,//46.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002643,//47.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002648,//48.00 閿熸枻鎷烽敓鏂ゆ嫹鐭抽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002666,//49.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002669,//50.00 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002683,//51.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002709,//52.00 閿熸枻鎷风瓛閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002741,//53.00 閿熻В鍗庨敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002748,//54.00 閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002753,//55.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002783,//56.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002802,//57.00 閿熸枻鎷烽敓鏂ゆ嫹铏忛敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002805,//58.00 閿熸枻鎷峰厓閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002809,//59.00 閿熸枻鎷峰閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002810,//60.00 灞遍敓鏂ゆ嫹閿熺Ц杈炬嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002825,//61.00 閿熺即璁规嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                9002827,//62.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300019,//63.00  閿熷�熷疂閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300037,//64.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300041,//65.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300054,//66.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300082,//67.00  閿熼摪鍏嬭偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300107,//68.00  閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300109,//69.00  閿熼摪鍖℃嫹婧� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300121,//70.00  閿熸枻鎷烽敓楗轰紮鎷锋嘲  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300132,//71.00  閿熸枻鎷烽敓缂磋偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300135,//72.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300174,//73.00  鍏冮敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300200,//74.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300214,//75.00  閿熺Ц绉戜紮鎷峰  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300243,//76.00  閿熸枻鎷烽敓绔鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300387,//77.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300405,//78.00  閿熸枻鎷烽殕閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300429,//79.00  寮洪敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300437,//80.00  閿熸枻鎷锋按婧� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300446,//81.00  閿熻鍖℃嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300459,//82.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300481,//83.00  閿熸枻鎷烽敓鏂ゆ嫹鑾╅敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300487,//84.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300505,//85.00  閿熸枻鎷烽敓鏂ゆ嫹璇� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300530,//86.00  閿熸枻鎷峰織閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300535,//87.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300568,//88.00  閿熸枻鎷锋簮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300586,//89.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                300596,//90.00  閿熸枻鎷烽敓鏂ゆ嫹闅� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600075,//91.00  閿熼摪鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600078,//92.00  閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600091,//93.00  ST閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600135,//94.00  閿熻鍖℃嫹閿熸枻鎷风墖  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600141,//95.00  閿熷壙鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600160,//96.00  閿熺潾浼欐嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600228,//97.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600273,//98.00  閿熻娇浼欐嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600281,//99.00  澶敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600301,//100.00 *ST閿熻緝浼欐嫹 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600309,//101.00 閿熸触鍗庝紮鎷峰  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600319,//102.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600328,//103.00 閿熸枻鎷峰お瀹炰笟  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600367,//104.00 閿熸枻鎷烽敓瑙掑嚖鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600378,//105.00 閿熸枻鎷蜂箳鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600409,//106.00 閿熸枻鎷烽敓绐栦紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600618,//107.00 閿熼ズ纰卞寲閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600636,//108.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600722,//109.00 閿熸枻鎷风墰閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600746,//110.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600844,//111.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                600985,//112.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                601208,//113.00 閿熸枻鎷烽敓渚ョ纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                601216,//114.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                601678,//115.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603002,//116.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603010,//117.00 閿熸枻鎷风洓閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603026,//118.00 鐭抽敓鏂ゆ嫹鑳滈敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603067,//119.00 閿熶粖鍗庤偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603077,//120.00 閿熼叺甯嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603227,//121.00 闆敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603299,//122.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603585,//123.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603928,//124.00 閿熸枻鎷蜂笟閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603968,//125.00 閿熼樁浼欐嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
                603977//126.00 閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍘熼敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_51_lyhf = new ArrayList<Integer>(Arrays.asList(
                9000422,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷壙浼欐嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000525,//2.00  閿熸枻鎷� 澶� 閿熸枻鎷� 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000553,//3.00  娌欓殕閿熸枻鎷烽敓锟�  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000731,//4.00  閿熶茎杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000792,//5.00  閿熻娇鐚存嫹閿熺即鍑ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000830,//6.00  椴侀敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000902,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000912,//8.00  閿熸枻鎷烽敓灞婂寲 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000950,//9.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000953,//10.00 閿熸帴姹犱紮鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002018,//11.00 閿熸枻鎷烽敓鑴氱櫢鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002170,//12.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002215,//13.00 璇� 閿熸枻鎷� 閿熸枻鎷� 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002258,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002274,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002391,//16.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002470,//17.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002496,//18.00 閿熺殕鍑ゆ嫹鐓為敓锟�  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002513,//19.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002538,//20.00 鍙搁敓鏂ゆ嫹閿熸枻鎷� 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002539,//21.00 閿熸枻鎷峰浘閿熸埅鐧告嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002588,//22.00 鍙查敓鏂ゆ嫹閿熸枻鎷� 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002734,//23.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9002749,//24.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                300261,//25.00  閿熻剼鎲嬫嫹閿熸枻鎷峰  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                300575,//26.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                9000155,//27.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600096,//28.00  閿熸枻鎷烽敓灞婂寲 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600226,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸嵎鍖℃嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600227,//30.00  閿熸枻鎷烽敓灞婂寲 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600230,//31.00  *ST閿熼樁杈炬嫹 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600389,//32.00  閿熸枻鎷峰北閿熺即鍑ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600423,//33.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600426,//34.00  閿熸枻鎷烽瞾閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600470,//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600486,//36.00  閿熸枻鎷峰啘閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600538,//37.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600596,//38.00  閿熼摪甯嫹閿熺即鍑ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600691,//39.00  閿熸枻鎷风叅閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600727,//40.00  椴侀敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600731,//41.00  閿熸枻鎷烽敓杈冪尨鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600796,//42.00  閽遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                600803,//43.00  閿熼摪濂ヨ偂鍑ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                603599,//44.00  閿熸枻鎷烽敓鑴氳偂鍑ゆ嫹  鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
                603639//45.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍐滆嵂閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_52_sl = new ArrayList<Integer>(Arrays.asList(
              9000859,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹
              9000973,//2.00  閿熸枻鎷烽敓鏉扮纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002014,//3.00  閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002108,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002243,//5.00  閫氶敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002263,//6.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
              9002324,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
              9002395,//8.00  鍙岄敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
              9002420,//9.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
              9002457,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹
              9002522,//11.00 閿熷姹熼敓鑺傜鎷�  閿熸枻鎷烽敓鏂ゆ嫹
              9002585,//12.00 鍙岄敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002641,//13.00 閿熸枻鎷烽敓绔偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002676,//14.00 椤洪敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002694,//15.00 閿熷壙鍦扮纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002735,//16.00 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹
              9002768,//17.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300169,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300198,//19.00  閿熺即杈炬嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300218,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300221,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300230,//22.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300305,//23.00  瑁曢敓鍓胯偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300321,//24.00  鍚岄敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
              300325,//25.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300393,//26.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              300539,//27.00  閿熸枻鎷烽敓渚ワ綇鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
              9002838,//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              600143,//29.00  閿熼噾鍙戠纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
              600146,//30.00  閿熸枻鎷疯耽閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              600444,//31.00  閿熸枻鎷烽敓鏂ゆ嫹閫氶敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              600458,//32.00  鏃堕敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹
              600589,//33.00  閿熷涓滈敓鏂ゆ嫹娉�  閿熸枻鎷烽敓鏂ゆ嫹
              603806,//34.00  閿熸枻鎷锋柉閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
              603266//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
              
    ));
    
    List<Integer> listCode_53_xj = new ArrayList<Integer>(Arrays.asList(
            9000887,//1.00  閿熷彨璁规嫹閿熺即鍑ゆ嫹  閿熸枻鎷�
            9002211,//2.00  閿熸枻鎷烽敓鏂ゆ嫹铏忛敓锟�  閿熸枻鎷�
            9002224,//3.00  閿熸枻鎷� 閿熸枻鎷� 澹� 閿熸枻鎷�
            9002381,//4.00  鍙岄敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷�
            300031,//5.00 閿熸枻鎷烽�氶敓鐙＄》鎷�  閿熸枻鎷�
            300320,//6.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷�
            300478,//7.00 閿熸枻鎷烽敓鎹烽潻鎷烽敓鏂ゆ嫹  閿熸枻鎷�
            300547,//8.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷�
            300587,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷�
            601118,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷�
            603033//11.00  閿熸枻鎷风淮閿熺即鍑ゆ嫹  閿熸枻鎷�
    ));
    
    List<Integer> listCode_54_yltl = new ArrayList<Integer>(Arrays.asList(
              
              9000565,//1.00  閿熸枻鎷烽敓鏂ゆ嫹宄￠敓鏂ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              9002010,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              9002256,//3.00  閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              9002319,//4.00  閿熸枻鎷烽�氶敓缂村嚖鎷�  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              9002440,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300063,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300067,//7.00 閿熸枻鎷疯閿熸枻鎷� 鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300192,//8.00 閿熸枻鎷锋柉閿熸枻鎷烽敓锟�  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300225,//9.00 閿熸枻鎷烽敓鏂ゆ嫹娉� 鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300236,//10.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300398,//11.00  閿熺即鍖℃嫹閿熸枻鎷烽敓鏂ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300522,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300537,//13.00  閿熸枻鎷烽敓鑴氳鎷烽敓鏂ゆ嫹  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              300576,//14.00  閿熸嵎杈炬嫹娉勯敓锟�  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              600352,//15.00  閿熷姹熼敓鏂ゆ嫹鐩�  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              603188,//16.00  閿熻甯嫹鐓為敓锟�  鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              603737,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
              603823//18.00  閿熷姭鍚堜紮鎷� 鏌撻敓鏂ゆ嫹娑傞敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_55_tc = new ArrayList<Integer>(Arrays.asList(
            300089,//1.00 閿熶茎浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熺Ц杈炬嫹
            300234,//2.00 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熺Ц杈炬嫹
            300285,//3.00 閿熸枻鎷烽敓缂磋鎷烽敓鏂ゆ嫹  閿熺Ц杈炬嫹
            300409,//4.00 閿熸枻鎷烽敓杈冪》鎷烽敓鏂ゆ嫹  閿熺Ц杈炬嫹
            600145,//5.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熺Ц杈炬嫹
            603268,//6.00 閿熺即鍑ゆ嫹閿熺即鍑ゆ嫹  閿熺Ц杈炬嫹
            603838//7.00 閿熸枻鎷烽�氶敓缂村嚖鎷�  閿熺Ц杈炬嫹

    ));
    
    
    List<Integer> listCode_56_sn = new ArrayList<Integer>(Arrays.asList(
              9000401,//1.00  閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              9000546,//2.00  閿熸枻鎷峰渾閿熺即鍑ゆ嫹  姘撮敓鏂ゆ嫹
              9000672,//3.00  閿熻緝鍑ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              9000789,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 姘撮敓鏂ゆ嫹
              9000877,//5.00  閿熸枻鎷峰北閿熺即鍑ゆ嫹  姘撮敓鏂ゆ嫹
              9000885,//6.00  鍚岄敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              9000935,//7.00  閿熶茎杈炬嫹鍙岄敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              9002233,//8.00  閿熸枻鎷烽敓鐙＄》鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              9002302,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              9002619,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  姘撮敓鏂ゆ嫹
              600425,//11.00  閿熸枻鎷烽敓缂存枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              600449,//12.00  閿熸枻鎷烽敓渚ユ枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              600539,//13.00  ST鐙ご  姘撮敓鏂ゆ嫹
              600585,//14.00  閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              600668,//15.00  閿熸枻鎷峰顖ゆ嫹閿燂拷  姘撮敓鏂ゆ嫹
              600678,//16.00  閿熶茎杈炬嫹閿熸枻鎷�  姘撮敓鏂ゆ嫹
              600720,//17.00  閿熸枻鎷烽敓鏂ゆ嫹灞� 姘撮敓鏂ゆ嫹
              600801,//18.00  閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              600802,//19.00  閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              600881,//20.00  閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
              600883,//21.00  閿熸枻鎷烽敓鑴氱纭锋嫹  姘撮敓鏂ゆ嫹
              601992//22.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  姘撮敓鏂ゆ嫹

    ));
    
    List<Integer> listCode_57_bl = new ArrayList<Integer>(Arrays.asList(
    9000012,//1.00  閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    9002201,//2.00  閿熻剼璁规嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹
    9002571,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    9002623,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹
    300093,//5.00 閿熸枻鎷疯┕閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
    300160,//6.00 閿熸枻鎷峰己閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    300196,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    300395,//8.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
    600176,//9.00 閿熷彨鐧告嫹閿熸枻鎷风煶  閿熸枻鎷烽敓鏂ゆ嫹
    600293,//10.00  閿熸枻鎷峰场閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600529,//11.00  灞遍敓鏂ゆ嫹鑽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600552,//12.00  閿熸枻鎷风洓閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600586,//13.00  閿熼噾鏅剁纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600819,//14.00  鑰�鐨敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
    600876,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    601636,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
    603021,//17.00  灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    603601//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_58_qtjc = new ArrayList<Integer>(Arrays.asList(

      9000023,//1.00  閿熸枻鎷烽敓鏂ゆ嫹鍏�閿燂拷  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9000055,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9000509,//3.00  閿熸枻鎷烽敓鏉版帶鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9000619,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼叺璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9000786,//5.00  閿熸枻鎷烽敓閾版枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002043,//6.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002066,//7.00  閿熸枻鎷锋嘲閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002205,//8.00  閿熸枻鎷风粺閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002225,//9.00  閿熸枻鎷峰嚫鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002271,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002372,//11.00 浼熼敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002392,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002398,//13.00 閿熸枻鎷烽敓鍙》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002596,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002652,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002671,//16.00 閿熸枻鎷锋硥閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002742,//17.00 閿熸枻鎷峰湥閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002785,//18.00 閿熸枻鎷烽敓鏂ゆ嫹鐭� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      9002791,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      300163,//20.00  閿熼ズ鍑ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      300344,//21.00  澶敓绉稿府鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      300374,//22.00  閿熸枻鎷烽�氶敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      600076,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      600155,//24.00  閿熸枻鎷风閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      600321,//25.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      600634,//26.00  閿熷彨纭锋嫹閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      603616,//27.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰北  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
      603969//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹

    ));
    
    List<Integer> listCode_59_zzy = new ArrayList<Integer>(Arrays.asList(

        9000713,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷锋涓�
        9000998,//2.00  闅嗗钩閿熺鍖℃嫹  閿熸枻鎷锋涓�
        9002041,//3.00  閿熻鐚存嫹閿熸枻鎷蜂笟  閿熸枻鎷锋涓�
        9002772,//4.00  閿熸枻鎷烽敓鍓挎拝鎷蜂笟  閿熸枻鎷锋涓�
        300087,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺鍖℃嫹  閿熸枻鎷锋涓�
        300143,//6.00 閿熻鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷锋涓�
        300189,//7.00 閿熸枻鎷峰啘閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷锋涓�
        300511,//8.00 闆敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷锋涓�
        600313,//9.00 鍐滈敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷锋涓�
        600354,//10.00  閿熸埅浼欐嫹閿熸枻鎷蜂笟  閿熸枻鎷锋涓�
        600371,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻剼锟�  閿熸枻鎷锋涓�
        600506,//12.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷锋涓�
        600540,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷锋涓�
        600598//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷锋涓�
    ));
    
    List<Integer> listCode_60_yy = new ArrayList<Integer>(Arrays.asList(
          9000798,//1.00  閿熸枻鎷锋按閿熸枻鎷蜂笟  閿熸枻鎷蜂笟
          9002069,//2.00  *ST鐛愮鎷� 閿熸枻鎷蜂笟
          9002086,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷蜂笟
          9002447,//4.00  澹归敓鑴氳偂鍑ゆ嫹  閿熸枻鎷蜂笟
          9002696,//5.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷蜂笟
          300094,//6.00 閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  閿熸枻鎷蜂笟
          600097,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷蜂笟
          600257,//8.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷蜂笟
          600467//9.00 閿熺煫纰夋嫹閿熸枻鎷� 閿熸枻鎷蜂笟
    ));
    List<Integer> listCode_61_ly = new ArrayList<Integer>(Arrays.asList(
      9000592,//1.00  骞虫江閿熸枻鎷峰睍  閿熸枻鎷蜂笟
      9000663,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷蜂笟
      9002679,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋．  閿熸枻鎷蜂笟
      600189,//4.00 閿熸枻鎷烽敓鏂ゆ嫹妫敓鏂ゆ嫹  閿熸枻鎷蜂笟
      600265,//5.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷蜂笟
      601996//6.00 閿熸枻鎷烽敓琛楃》鎷烽敓鏂ゆ嫹  閿熸枻鎷蜂笟
    ));
    List<Integer> listCode_62_sl = new ArrayList<Integer>(Arrays.asList(
        9000048,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹
        9000702,//2.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
        9000876,//3.00  閿熸枻鎷� 甯� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002100,//4.00  閿熷眾搴烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
        9002124,//5.00  閿熸枻鎷烽敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹
        9002157,//6.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
        9002311,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
        9002385,//8.00  閿熸枻鎷峰啘 閿熸枻鎷烽敓鏂ゆ嫹
        9002548,//9.00  閿熸枻鎷烽敓鏂ゆ嫹鍐� 閿熸枻鎷烽敓鏂ゆ嫹
        9002567,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        300381,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹
        600195,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        600438,//13.00  閫氶敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        603609//14.00  閿熸暀鍑ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    List<Integer> listCode_63_lyzh = new ArrayList<Integer>(Arrays.asList(
    9000061,//1.00  鍐� 閿熸枻鎷� 鍝� 鍐滀笟閿熸鐚存嫹
    9000735,//2.00  閿熸枻鎷� 鐗� 灞� 鍐滀笟閿熸鐚存嫹
    9000930,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍐滀笟閿熸鐚存嫹
    9002173,//4.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 鍐滀笟閿熸鐚存嫹
    9002234,//5.00  閿熸枻鎷峰嚫鐓為敓锟�  鍐滀笟閿熸鐚存嫹
    9002299,//6.00  鍦ｅ啘閿熸枻鎷峰睍  鍐滀笟閿熸鐚存嫹
    9002321,//7.00  閿熸枻鎷疯嫳鍐滀笟  鍐滀笟閿熸鐚存嫹
    9002458,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鍐滀笟閿熸鐚存嫹
    9002477,//9.00  閿熸枻鎷烽拱鍐滈敓鏂ゆ嫹  鍐滀笟閿熸鐚存嫹
    9002505,//10.00 閿熸枻鎷峰啘涓�  鍐滀笟閿熸鐚存嫹
    9002714,//11.00 閿熸枻鎷峰師閿熺即鍑ゆ嫹  鍐滀笟閿熸鐚存嫹
    9002746,//12.00 閿熸枻鎷峰潧閿熺即鍑ゆ嫹  鍐滀笟閿熸鐚存嫹
    300021,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷壙锟�  鍐滀笟閿熸鐚存嫹
    300106,//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  鍐滀笟閿熸鐚存嫹
    300268,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  鍐滀笟閿熸鐚存嫹
    300313,//16.00  閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  鍐滀笟閿熸鐚存嫹
    300498,//17.00  閿熸枻鎷烽敓杈冭偂鍑ゆ嫹  鍐滀笟閿熸鐚存嫹
    600108,//18.00  閿熸枻鎷风洓閿熸枻鎷烽敓鏂ゆ嫹  鍐滀笟閿熸鐚存嫹
    600127,//19.00  閿熸枻鎷烽敓鏂ゆ嫹涓�  鍐滀笟閿熸鐚存嫹
    600251,//20.00  閿熸枻鎷峰啘閿熺即鍑ゆ嫹  鍐滀笟閿熸鐚存嫹
    600275,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 鍐滀笟閿熸鐚存嫹
    600359,//22.00  閿熸枻鎷峰啘閿熸枻鎷烽敓鏂ゆ嫹  鍐滀笟閿熸鐚存嫹
    600965,//23.00  閿熸枻鎷烽敓缂磋偂鍑ゆ嫹  鍐滀笟閿熸鐚存嫹
    600975,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 鍐滀笟閿熸鐚存嫹
    603336,//25.00  閿熸枻鎷锋�ㄩ敓鏂ゆ嫹閿燂拷  鍐滀笟閿熸鐚存嫹
    603668//26.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  鍐滀笟閿熸鐚存嫹
    ));
    
    List<Integer> listCode_64_fz = new ArrayList<Integer>(Arrays.asList(
    9000045,//1.00  閿熸枻鎷烽敓琛楊垽鎷烽敓锟�  閿熸枻鎷风粐
    9000158,//2.00  閿熸枻鎷峰北閿熺即鍑ゆ嫹  閿熸枻鎷风粐
    9000611,//3.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷风粐
    9000726,//4.00  椴� 娉伴敓鏂ゆ嫹  閿熸枻鎷风粐
    9000779,//5.00  閿熸枻鎷锋瘺閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐
    9000803,//6.00  閿熸枻鎷烽敓绛嬭溅閿熸枻鎷�  閿熸枻鎷风粐
    9000850,//7.00  閿熸枻鎷疯寕閿熺即鍑ゆ嫹  閿熸枻鎷风粐
    9000955,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷风粐
    9000982,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷风粐
    9002034,//10.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷风粐
    9002042,//11.00 閿熸枻鎷烽敓鏂ゆ嫹鑹查敓鏂ゆ嫹  閿熸枻鎷风粐
    9002070,//12.00 閿熻妭鍜岃偂鍑ゆ嫹  閿熸枻鎷风粐
    9002072,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷风粐
    9002083,//14.00 閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  閿熸枻鎷风粐
    9002087,//15.00 閿熸枻鎷烽噹閿熸枻鎷风粐  閿熸枻鎷风粐
    9002144,//16.00 閿熸枻鎷烽敓绔尅鎷�  閿熸枻鎷风粐
    9002193,//17.00 灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐
    9002293,//18.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐
    9002327,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷风粐
    9002394,//20.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷风粐
    9002397,//21.00 閿熻娇鏂ゆ嫹鐓為敓锟�  閿熸枻鎷风粐
    9002404,//22.00 閿熸枻鎷烽敓鏂ゆ嫹涓濋敓鏂ゆ嫹  閿熸枻鎷风粐
    9002516,//23.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷风粐
    9002674,//24.00 閿熸枻鎷蜂笟閿熺嫛纭锋嫹  閿熸枻鎷风粐
    9002761,//25.00 閿熸枻鎷峰枩閿熸枻鎷� 閿熸枻鎷风粐
    300577,//26.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷风粐
    600070,//27.00  閿熷姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷风粐
    600152,//28.00  缁撮敓鐙℃拝鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐
    600156,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷风粐
    600220,//30.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐
    600232,//31.00  閿熸枻鎷烽拱閿熺即鍑ゆ嫹  閿熸枻鎷风粐
    600370,//32.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷风粐
    600448,//33.00  閿熸枻鎷烽敓渚ヨ偂鍑ゆ嫹  閿熸枻鎷风粐
    600493,//34.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻锟�  閿熸枻鎷风粐
    600626,//35.00  閿熸枻鎷烽敓缂村嚖鎷�  閿熸枻鎷风粐
    600630,//36.00  閿熸枻鎷峰ご閿熺即鍑ゆ嫹  閿熸枻鎷风粐
    600689,//37.00  閿熻緝鐚存嫹閿熸枻鎷锋瘺  閿熸枻鎷风粐
    600851,//38.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷风粐
    600987,//39.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷风粐
    601339,//40.00  閿熸枻鎷烽殕閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐
    603558,//41.00  閿熸枻鎷风洓閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐
    603889//42.00  閿熼摪婢宠偂鍑ゆ嫹  閿熸枻鎷风粐
    ));
    
    List<Integer> listCode_65_fs = new ArrayList<Integer>(Arrays.asList(

        9002003,//1.00  浼熼敓瑙掕偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002029,//2.00  閿熸枻鎷� 鍖� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002098,//3.00  閿熸枻鎷锋柉鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
        9002154,//4.00  閿熸枻鎷� 鍠� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002269,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
        9002291,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002345,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹
        9002356,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002425,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熶茎浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002485,//10.00 甯屽姫閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002486,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹
        9002494,//12.00 閿熸枻鎷锋柉閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002503,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002563,//14.00 妫敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
        9002569,//15.00 閿熸枻鎷锋．閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002574,//16.00 閿熸枻鎷烽敓鏂ゆ嫹閿熶粙瀹�  閿熸枻鎷烽敓鏂ゆ嫹
        9002612,//17.00 閿熸枻鎷烽敓鍓胯偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002634,//18.00 閿熸枻鎷烽敓鏉拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002640,//19.00 閿熺晫澧冮�� 閿熸枻鎷烽敓鏂ゆ嫹
        9002656,//20.00 鎽╅敓瑙掕揪鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹
        9002687,//21.00 閿熸枻鎷烽敓杞垮府鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002699,//22.00 閿熸枻鎷风洓閿熶茎浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002721,//23.00 閿熸枻鎷蜂竴閿熶茎浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹
        9002731,//24.00 閿熼叺浼欐嫹閿熶粙瀹�  閿熸枻鎷烽敓鏂ゆ嫹
        9002740,//25.00 閿熸枻鎷烽敓杈冭鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002762,//26.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
        9002763,//27.00 閿熸枻鎷烽敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹
        9002776,//28.00 閿熸埅鎲嬫嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        9002832,//29.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺Ц鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        300005,//30.00  鎺㈣矾閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        300591,//31.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        600086,//32.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        600107,//33.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        600137,//34.00  閿熸枻鎷疯帋閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        600177,//35.00  閿熻剼闈╂嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹
        600272,//36.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  閿熸枻鎷烽敓鏂ゆ嫹
        600295,//37.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋柉  閿熸枻鎷烽敓鏂ゆ嫹
        600398,//38.00  閿熸枻鎷烽敓鏂ゆ嫹涔嬮敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        600400,//39.00  閿熷眾璞嗛敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹
        600439,//40.00  閿熼噾璐濆尅鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        600612,//41.00  閿熻緝鍑ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        600884,//42.00  鏉夋潐閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        601566,//43.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        601718,//44.00  閿熺粸浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        603001,//45.00  閿熼摪鍖℃嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        603116,//46.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        603518,//47.00  缁撮敓鏂ゆ嫹閿熸枻鎷蜂笣  閿熸枻鎷烽敓鏂ゆ嫹
        603555,//48.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
        603608,//49.00  閿熷眾鍒涙椂閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
        603808,//50.00  閿熸枻鎷烽敓鏂ゆ嫹鎬� 閿熸枻鎷烽敓鏂ゆ嫹
        603900,//51.00  閫氶敓鏂ゆ嫹閿熶粙瀹�  閿熸枻鎷烽敓鏂ゆ嫹
        603958,//52.00  閿熸枻鎷锋．閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
        603877//53.00  澶钩閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_66_rzp = new ArrayList<Integer>(Arrays.asList(
        9002329,//1.00  閿熸枻鎷烽敓杈冪》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍝�
        9002570,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍝�
        9002719,//3.00  閿熸枻鎷疯叮閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鍝�
        9002732,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹鍝�
        9002770,//5.00  閿熺嫛纰夋嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹鍝�
        600419,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹鍝�
        600429,//7.00 閿熸枻鎷峰厓閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍝�
        600597,//8.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹鍝�
        600882,//9.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鍝�
        600887//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鍝�
    ));
    
    List<Integer> listCode_67_ryl =new ArrayList<Integer>( Arrays.asList(
        9000019,//1.00  閿熸枻鎷烽敓绛嬪疂閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
        9000848,//2.00  閿熷彨纰夋嫹闇查湶  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
        9002387,//3.00  閿熸枻鎷风墰椋熷搧  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
        600300,//4.00 缁寸淮閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
        600962//5.00 閿熸枻鎷锋姇閿熸枻鎷烽瞾  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�
    ));
    List<Integer> listCode_68_sp = new ArrayList<Integer>(Arrays.asList(
        9000529,//1.00  閿熸枻鎷烽敓鎴櫢鎷�  椋熷搧
        9000639,//2.00  閿熸枻鎷烽敓鏂ゆ嫹椋熷搧  椋熷搧
        9000716,//3.00  閿熸枻鎷疯姖閿熸枻鎷� 椋熷搧
        9000893,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  椋熷搧
        9000895,//5.00  鍙岄敓濮愬彂灞�  椋熷搧
        9000911,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  椋熷搧
        9000972,//7.00  閿熷彨浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  椋熷搧
        9002053,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋姇  椋熷搧
        9002216,//9.00  閿熸枻鎷峰叏椋熷搧  椋熷搧
        9002220,//10.00 閿熷眾瀹濋敓缂村嚖鎷�  椋熷搧
        9002286,//11.00 閿熸枻鎷烽敓鎴掑疂 椋熷搧
        9002330,//12.00 閿熸枻鎷烽敓鏂ゆ嫹鏂� 椋熷搧
        9002481,//13.00 鍙岄敓鏂ゆ嫹椋熷搧  椋熷搧
        9002495,//14.00 閿熸枻鎷烽殕閿熺即鍑ゆ嫹  椋熷搧
        9002507,//15.00 閿熸枻鎷烽敓鏂ゆ嫹姒ㄩ敓鏂ゆ嫹  椋熷搧
        9002515,//16.00 閿熸枻鎷烽敓琛椾紮鎷烽敓鏂ゆ嫹  椋熷搧
        9002557,//17.00 娲芥唇椋熷搧  椋熷搧
        9002582,//18.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 椋熷搧
        9002604,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  椋熷搧
        9002626,//20.00 閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 椋熷搧
        9002650,//21.00 閿熸帴纭锋嫹椋熷搧  椋熷搧
        9002661,//22.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  椋熷搧
        9002695,//23.00 閿熸枻鎷烽敓杈冧紮鎷� 椋熷搧
        9002702,//24.00 閿熸枻鎷烽敓鏂ゆ嫹椋熷搧  椋熷搧
        9002726,//25.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽  椋熷搧
        9002820,//26.00 閿熸枻鎷烽敓鏂ゆ嫹 椋熷搧
        300138,//27.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  椋熷搧
        300146,//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  椋熷搧
        300149,//29.00  閿熸枻鎷烽敓鎺ラ珮鍖℃嫹  椋熷搧
        300175,//30.00  閿熸枻鎷锋簮閿熺即鍑ゆ嫹  椋熷搧
        300401,//31.00  閿熸枻鎷峰洯閿熸枻鎷烽敓鏂ゆ嫹  椋熷搧
        9002840,//32.00 閿熸枻鎷风粺閿熺即鍑ゆ嫹  椋熷搧
        600073,//33.00  閿熻緝鐚存嫹姊呴敓鏂ゆ嫹  椋熷搧
        600186,//34.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  椋熷搧
        600191,//35.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  椋熷搧
        600298,//36.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋瘝  椋熷搧
        600305,//37.00  閿熸枻鎷烽『閿熸枻鎷蜂笟  椋熷搧
        600737,//38.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼叺鐚存嫹  椋熷搧
        600866,//39.00  *ST閿熻鐚存嫹 椋熷搧
        600872,//40.00  閿熷彨鎾呮嫹閿熸枻鎷烽敓锟�  椋熷搧
        600873,//41.00  姊呴敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  椋熷搧
        603020,//42.00  閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  椋熷搧
        603027,//43.00  鍗冮敓鏂ゆ嫹鍛充笟  椋熷搧
        603288,//44.00  閿熸枻鎷烽敓鏂ゆ嫹鍛充笟  椋熷搧
        603696,//45.00  閿熸枻鎷烽敓鏂ゆ嫹椋熷搧  椋熷搧
        603866,//46.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  椋熷搧
        603886//47.00  鍏冮敓鏂ゆ嫹鐓為敓锟�  椋熷搧
    ));
    
    List<Integer> listCode_69_bj = new ArrayList<Integer>(Arrays.asList(
    
    9000568,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻緝鏂ゆ嫹  閿熼樁鎾呮嫹
    9000596,//2.00  閿熻剼鎾呮嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熼樁鎾呮嫹
    9000799,//3.00  閿熺嫛鐧告嫹閿燂拷 閿熼樁鎾呮嫹
    9000858,//4.00  閿熸枻鎷� 閿熸枻鎷� 娑� 閿熼樁鎾呮嫹
    9000860,//5.00  椤洪敓鏂ゆ嫹鍐滀笟  閿熼樁鎾呮嫹
    9000995,//6.00  *ST閿熸枻鎷峰彴 閿熼樁鎾呮嫹
    9002304,//7.00  閿熸枻鎷峰焊鐓為敓锟�  閿熼樁鎾呮嫹
    9002646,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熼樁鎾呮嫹
    600197,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熼樁鎾呮嫹
    600199,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸帴鎾呮嫹  閿熼樁鎾呮嫹
    600519,//11.00  閿熸枻鎷烽敓鏂ゆ嫹鑼呭彴  閿熼樁鎾呮嫹
    600559,//12.00  閿熻緝鐧藉共鎾呮嫹  閿熼樁鎾呮嫹
    600702,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熼樁鎾呮嫹
    600779,//14.00  姘撮敓鏂ゆ嫹閿熸枻鎷� 閿熼樁鎾呮嫹
    600809,//15.00  灞遍敓鏂ゆ嫹閿熻妭鎾呮嫹  閿熼樁鎾呮嫹
    603198,//16.00  杩庨敓鎹风櫢鎷烽敓鏂ゆ嫹  閿熼樁鎾呮嫹
    603369,//17.00  閿熸枻鎷烽敓鏂ゆ嫹缂� 閿熼樁鎾呮嫹
    603589,//18.00  閿熸枻鎷烽敓鎺ユ枻鎷� 閿熼樁鎾呮嫹
    603919//19.00  閿熸枻鎷风珯閿燂拷 閿熼樁鎾呮嫹
    
    ));
    List<Integer> listCode_70_pj = new ArrayList<Integer>(Arrays.asList(

        9000729,//1.00  閿熸磥浜暏閿熸枻鎷�  鍟ら敓鏂ゆ嫹
        9000752,//2.00  閿熸枻鎷烽敓鎴嚖鎷峰睍  鍟ら敓鏂ゆ嫹
        9000929,//3.00  閿熸枻鎷烽敓鎹烽粍鐚存嫹  鍟ら敓鏂ゆ嫹
        9002461,//4.00  閿熶粙姹熷暏閿熸枻鎷�  鍟ら敓鏂ゆ嫹
        600132,//5.00 閿熸枻鎷烽敓鏂ゆ嫹鍟ら敓鏂ゆ嫹  鍟ら敓鏂ゆ嫹
        600573,//6.00 閿熸枻鎷锋硥鍟ら敓鏂ゆ嫹  鍟ら敓鏂ゆ嫹
        600600//7.00 閿熸磥宀涘暏閿熸枻鎷�  鍟ら敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_71_hhyj =new ArrayList<Integer>( Arrays.asList(
    9000557,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    9000869,//2.00  閿熸枻鎷� 瑁曢敓鏂ゆ嫹  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    9002568,//3.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    600059,//4.00 閿熸枻鎷疯秺閿熸枻鎷峰北  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    600084,//5.00 閿熸枻鎷烽敓杈冭偂鍑ゆ嫹  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    600238,//6.00 閿熸枻鎷烽敓鏂ゆ嫹妞伴敓鏂ゆ嫹  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    600365,//7.00 閫氶敓杈冭偂鍑ゆ嫹  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    600543,//8.00 鑾敓绔偂鍑ゆ嫹  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    600616,//9.00 閿熸枻鎷烽敓鏂ゆ嫹涓�  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    601579,//10.00  閿熸枻鎷烽敓缂达拷 閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    603779//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鎻晪鎷烽敓锟�
    
    ));
    List<Integer> listCode_72_qczc = new ArrayList<Integer>(Arrays.asList(
    9000550,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000572,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000625,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000800,//4.00  涓�閿熸枻鎷烽敓杞跨鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000868,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼叺绛规嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000927,//6.00  涓�閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000951,//7.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000957,//8.00  閿熸枻鎷烽�氶敓閰电鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002537,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002594,//10.00 閿熸枻鎷烽敓瑙掔鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600006,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600066,//12.00  閿熸枻鎷烽�氶敓閰电鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600104,//13.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600166,//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600213,//15.00  閿熸枻鎷烽敓瑙掑绛规嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600262,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600303,//17.00  閿熸枻鎷烽敓缂村嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600375,//18.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600418,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600609,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600686,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600760,//22.00  *ST閿熻妭鎲嬫嫹 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601238,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601633//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    
    List<Integer> listCode_73_qcpj =new ArrayList<Integer>( Arrays.asList(
        9000030,//1.00  閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000338,//2.00  娼嶉敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000559,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閽遍敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000581,//4.00  閿熸枻鎷烽敓鑺傞珮鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000589,//5.00  榛旈敓鏂ゆ嫹鑳庨敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000599,//6.00  閿熸磥宀涘弻閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000622,//7.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000700,//8.00  妯￠敓鏉扮纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000710,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻鎲嬫嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000757,//10.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000760,//11.00 鏂お閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9000980,//12.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002031,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002048,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002085,//15.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002126,//16.00 閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002190,//17.00 閿熺即椋炵》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002213,//18.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002239,//19.00 閿熸枻鎷烽敓鎴》鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002265,//20.00 閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002283,//21.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002284,//22.00 閿熸枻鎷峰お閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002328,//23.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002355,//24.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002363,//25.00 闅嗛敓鏂ゆ嫹閿熸枻鎷锋  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002406,//26.00 杩滈敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002434,//27.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002448,//28.00 閿熸枻鎷峰師閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002454,//29.00 閿熸枻鎷疯姖閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002488,//30.00 閿熸枻鎷峰潶鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002510,//31.00 閿熸枻鎷烽敓鏂ゆ嫹妯� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002536,//32.00 閿熸枻鎷烽敓鐭偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002553,//33.00 閿熻緝鍑ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002590,//34.00 閿熸触瀹夌纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002592,//35.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002593,//36.00 閿熸枻鎷烽敓杈冪》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002602,//37.00 閿熸枻鎷烽敓閰典紮鎷烽��  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002625,//38.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002662,//39.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002664,//40.00 閿熸枻鎷烽敓缁炵鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002703,//41.00 閿熷姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002708,//42.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002715,//43.00 閿熸枻鎷烽敓鐙¤偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002725,//44.00 璺冮敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002765,//45.00 閿熸枻鎷烽敓灞婁紶閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002766,//46.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        9002813,//47.00 璺敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300176,//48.00  閿熸枻鎷烽敓鎴拝鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300258,//49.00  閿熸枻鎷烽敓閰电纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300304,//50.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300375,//51.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300432,//52.00  閿熸枻鎷烽敓鍔拝鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300473,//53.00  閿熼摪璁规嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300507,//54.00  閿熺Ц濂ヨ揪鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300580,//55.00  閿熸枻鎷锋柉閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        300585,//56.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600081,//57.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600093,//58.00  閿熸暀鍢夎偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600148,//59.00  閿熸枻鎷烽敓鏂ゆ嫹涓�閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600178,//60.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600182,//61.00  S閿熸枻鎷烽�� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600335,//62.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600469,//63.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600480,//64.00  閿熸枻鎷烽敓鐙¤偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600501,//65.00  閿熸枻鎷烽敓灞婃櫒閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600523,//66.00  閿熻鑸偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600623,//67.00  閿熸枻鎷烽敓鐤ラ泦閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600660,//68.00  閿熸枻鎷疯��閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600698,//69.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600699,//70.00  閿熸枻鎷疯儨閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600741,//71.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600742,//72.00  涓�閿熸枻鎷烽敓鏂ゆ嫹缁�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        600960,//73.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        601058,//74.00  閿熸枻鎷烽敓琛楁枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        601127,//75.00  灏忛敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        601163,//76.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯儙  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        601500,//77.00  閫氶敓鐭偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        601689,//78.00  閿熸枻鎷烽敓绉哥》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        601799,//79.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        601966,//80.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯儙  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603006,//81.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603009,//82.00  閿熸枻鎷烽敓鎴纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603023,//83.00  閿熸枻鎷烽敓妗旇偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603158,//84.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603166,//85.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603239,//86.00  N閿熸枻鎷烽�� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603306,//87.00  閿熸枻鎷烽敓鐙＄》鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603319,//88.00  閿熸枻鎷烽敓閰垫唻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603701,//89.00  閿熼摪鐚存嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603788,//90.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603997,//91.00  閿熸暀鍑ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603035,//92.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
        603037//93.00  閿熸枻鎷烽敓鑺傝偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�
    
    ));
    
    List<Integer> listCode_74_qcfw =new ArrayList<Integer>( Arrays.asList(
    9000025,//1.00  閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000753,//2.00  閿熸枻鎷烽敓鎹峰嚖鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002607,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300100,//4.00 鍙岄敓琛楄偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600297,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600653,//6.00 閿熺枼鍗庨敓鎴櫢鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601258,//7.00 閿熸帴杈炬嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601965,//8.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    603377//9.00 閿熸枻鎷烽敓鏂ゆ嫹鏃堕敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_75_mtc = new ArrayList<Integer>(Arrays.asList(
          9000913,//1.00  *ST閽遍敓鏂ゆ嫹 鎽╅敓鍙鎷�
          9001696,//2.00  閿熸枻鎷烽敓鐤ュ姩閿熸枻鎷�  鎽╅敓鍙鎷�
          600099,//3.00 閿熻鐚存嫹閿熺即鍑ゆ嫹  鎽╅敓鍙鎷�
          600877,//4.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  鎽╅敓鍙鎷�
          601777,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  鎽╅敓鍙鎷�
          603766//6.00 闅嗛敓鏂ゆ嫹閫氶敓鏂ゆ嫹  鎽╅敓鍙鎷�
    
    ));
    
    List<Integer> listCode_76_hxzy = new ArrayList<Integer>(Arrays.asList(
                  9000153,//1.00  閿熸枻鎷峰師鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000566,//2.00  閿熸枻鎷烽敓杈冪尨鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000597,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000606,//4.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000739,//5.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000756,//6.00  閿熼摪浼欐嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000788,//7.00  閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000813,//8.00  閿熸枻鎷峰睍閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000908,//9.00  閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000915,//10.00 灞遍敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000919,//11.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000952,//12.00 閿熸枻鎷烽敓鎻敭锟�  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9000963,//13.00 閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002001,//14.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002004,//15.00 閿熸枻鎷烽敓绛嬪仴閿熸枻鎷�  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002019,//16.00 閿熻妭鍑ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002020,//17.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002099,//18.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002102,//19.00 閿熻妭闈╂嫹閿熺即鍑ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002262,//20.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002294,//21.00 閿熸枻鎷烽敓鏂ゆ嫹娉� 閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002365,//22.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002370,//23.00 閿熸枻鎷峰お鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002393,//24.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002399,//25.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002411,//26.00 閿熸埅鍖℃嫹閿熺即鍑ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002422,//27.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002437,//28.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002653,//29.00 閿熸枻鎷锋�濋敓鏂ゆ嫹 閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002675,//30.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002688,//31.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002693,//32.00 鍙岄敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002817,//33.00 閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  9002826,//34.00 閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300006,//35.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300086,//36.00  閿熸枻鎷疯姖鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300110,//37.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300194,//38.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300199,//39.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300233,//40.00  閿熸枻鎷烽敓鎻彮锟�  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300254,//41.00  浠熸簮鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300267,//42.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300363,//43.00  閿熸枻鎷烽敓鑺傝偂鍑ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300436,//44.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷峰閿熸枻鎷疯嵂
                  300452,//45.00  灞遍敓鏂ゆ嫹鑽敓鏂ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300497,//46.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300558,//47.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300573,//48.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  300584,//49.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600062,//50.00  閿熸枻鎷烽敓鏂ゆ嫹鍙岄敓鏂ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600079,//51.00  閿熷壙闈╂嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600196,//52.00  閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600216,//53.00  閿熷姹熷尰鑽�  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600267,//54.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600276,//55.00  閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600299,//56.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷峰閿熸枻鎷疯嵂
                  600380,//57.00  閿熸枻鎷烽敓鏂ゆ嫹鍏� 閿熸枻鎷峰閿熸枻鎷疯嵂
                  600420,//58.00  閿熻杈炬嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600488,//59.00  閿熸枻鎷疯嵂閿熺即鍑ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600513,//60.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600521,//61.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600664,//62.00  閿熸枻鎷疯嵂閿熺即鍑ゆ嫹  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600789,//63.00  椴侀敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  600812,//64.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  603168,//65.00  鑾庨敓绉稿府鎷锋��  閿熸枻鎷峰閿熸枻鎷疯嵂
                  603222,//66.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熸枻鎷峰閿熸枻鎷疯嵂
                  603456,//67.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷峰閿熸枻鎷疯嵂
                  603520,//68.00  鍙稿お閿熸枻鎷� 閿熸枻鎷峰閿熸枻鎷疯嵂
                  603669//69.00  閿熶粙搴疯嵂涓�  閿熸枻鎷峰閿熸枻鎷疯嵂
    
    ));
    
    List<Integer> listCode_77_swzy = new ArrayList<Integer>(Arrays.asList(
    9000004,//1.00  閿熸枻鎷峰啘閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9000078,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9000403,//3.00  ST閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9000518,//4.00  閿熶茎浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9000661,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9000806,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002007,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002030,//8.00  閿熸枻瀹夐敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002038,//9.00  鍙岄敓鏂ゆ嫹鑽笟  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002252,//10.00 閿熻緝鐚存嫹閿熸枻鎷峰＋  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002332,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002550,//12.00 鍗冮敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002581,//13.00 鏈敓鏂ゆ嫹鍖昏嵂  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002680,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    9002821,//15.00 閿熸枻鎷烽敓鏂ゆ嫹鑻� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300009,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300119,//17.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300122,//18.00  閿熻鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300142,//19.00  閿熸枻鎷锋．閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300204,//20.00  閿熸枻鎷锋嘲閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300239,//21.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300255,//22.00  閿熸枻鎷峰北鑽笟  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300289,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300294,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300357,//25.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300406,//26.00  閿熸枻鎷峰己閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300482,//27.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300485,//28.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    300583,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    600161,//30.00  閿熸枻鎷峰潧閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    600201,//31.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    600645,//32.00  閿熸枻鎷锋簮鍗忛敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    600867,//33.00  閫氶敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    603566,//34.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    603718//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂
    
    ));
    
    
    List<Integer> listCode_78_zcy = new ArrayList<Integer>(Arrays.asList(
    9000423,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9000513,//2.00  閿熸枻鎷烽敓浠嬮泦閿熸枻鎷�  閿熷彨绛规嫹鑽�
    9000538,//3.00  閿熸枻鎷烽敓杈冨府鎷疯嵂  閿熷彨绛规嫹鑽�
    9000590,//4.00  閿熸枻鎷烽敓杈冨彜鐚存嫹  閿熷彨绛规嫹鑽�
    9000623,//5.00  閿熸枻鎷烽敓琛楀府鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9000650,//6.00  閿熺粸鐚存嫹鑽笟  閿熷彨绛规嫹鑽�
    9000766,//7.00  閫氶敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9000790,//8.00  娉伴敓杈冩枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9000989,//9.00  閿熸枻鎷� 鑺� 閿熸枻鎷� 閿熷彨绛规嫹鑽�
    9000999,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9002107,//11.00 閿熻浼欐嫹鍖昏嵂  閿熷彨绛规嫹鑽�
    9002118,//12.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    9002166,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9002198,//14.00 閿熸枻鎷峰簲閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    9002219,//15.00 閿熷搴峰尰閿熸枻鎷�  閿熷彨绛规嫹鑽�
    9002275,//16.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9002287,//17.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    9002317,//18.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    9002349,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    9002390,//20.00 閿熻剼甯嫹閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    9002412,//21.00 閿熸枻鎷锋．閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    9002424,//22.00 閿熸枻鎷烽敓鎹峰府鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    9002433,//23.00 澶敓鏂ゆ嫹閿熸枻鎷� 閿熷彨绛规嫹鑽�
    9002566,//24.00 閿熸枻鎷风洓鑽笟  閿熷彨绛规嫹鑽�
    9002603,//25.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    9002644,//26.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  閿熷彨绛规嫹鑽�
    9002728,//27.00 閿熸枻鎷蜂竴鑽笟  閿熷彨绛规嫹鑽�
    9002737,//28.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    9002750,//29.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    9002773,//30.00 閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    300016,//31.00  閿熸枻鎷烽檰鑽笟  閿熷彨绛规嫹鑽�
    300026,//32.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    300039,//33.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    300049,//34.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熷彨绛规嫹鑽�
    300108,//35.00  鍙岄敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熷彨绛规嫹鑽�
    300147,//36.00  閿熸枻鎷烽洩閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    300158,//37.00  閿熸枻鎷烽敓鏂ゆ嫹鑽�  閿熷彨绛规嫹鑽�
    300181,//38.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    300519,//39.00  閿熼摪鐧告嫹鑽笟  閿熷彨绛规嫹鑽�
    300534,//40.00  闄囬敓鏂ゆ嫹閿熻鍑ゆ嫹  閿熷彨绛规嫹鑽�
    600080,//41.00  閿熼噾鑺辫偂鍑ゆ嫹  閿熷彨绛规嫹鑽�
    600085,//42.00  鍚岄敓鏂ゆ嫹閿熸枻鎷� 閿熷彨绛规嫹鑽�
    600129,//43.00  澶敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    600211,//44.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    600222,//45.00  澶敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    600252,//46.00  閿熷彨鎭掗泦閿熸枻鎷�  閿熷彨绛规嫹鑽�
    600285,//47.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    600329,//48.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    600332,//49.00  閿熸枻鎷烽敓鏂ゆ嫹灞� 閿熷彨绛规嫹鑽�
    600351,//50.00  閿熻鎲嬫嫹鑽笟  閿熷彨绛规嫹鑽�
    600422,//51.00  閿熸枻鎷疯嵂閿熸枻鎷烽敓鏂ゆ嫹  閿熷彨绛规嫹鑽�
    600436,//52.00  鐗囬敓鏂ゆ嫹閿燂拷 閿熷彨绛规嫹鑽�
    600479,//53.00  鍗冮敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    600518,//54.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    600535,//55.00  閿熸枻鎷峰＋閿熸枻鎷� 閿熷彨绛规嫹鑽�
    600557,//56.00  閿熸枻鎷风紭鑽笟  閿熷彨绛规嫹鑽�
    600566,//57.00  閿熺煫杈炬嫹鑽笟  閿熷彨绛规嫹鑽�
    600572,//58.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熷彨绛规嫹鑽�
    600594,//59.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�  閿熷彨绛规嫹鑽�
    600613,//60.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    600671,//61.00  閿熸枻鎷风洰鑽笟  閿熷彨绛规嫹鑽�
    600750,//62.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    600771,//63.00  閿熸枻鎷烽敓鏂ゆ嫹杩� 閿熷彨绛规嫹鑽�
    600781,//64.00  閿熸枻鎷烽敓鏂ゆ嫹鑽笟  閿熷彨绛规嫹鑽�
    600976,//65.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熷彨绛规嫹鑽�
    600993,//66.00  閿熸枻鎷峰簲閿熸枻鎷� 閿熷彨绛规嫹鑽�
    603567,//67.00  閿熸垝瀹濋敓鏂ゆ嫹 閿熷彨绛规嫹鑽�
    603858,//68.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    603998//69.00  閿熸枻鎷风洓閿熸枻鎷疯嵂  閿熷彨绛规嫹鑽�
    ));
    
    List<Integer> listCode_79_bh = new ArrayList<Integer>(Arrays.asList(

          9000417,//1.00  閿熻緝鑲ョ櫨浼欐嫹  閿熷姭浼欐嫹
          9000419,//2.00  閫氶敓鏁欐帶鐧告嫹  閿熷姭浼欐嫹
          9000501,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸暀锝忔嫹  閿熷姭浼欐嫹
          9000516,//4.00  閿熸枻鎷烽敓鏂ゆ嫹鍖诲  閿熷姭浼欐嫹
          9000560,//5.00  閿熸枻鎷烽敓鍔揪鎷烽敓锟�  閿熷姭浼欐嫹
          9000564,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          9000679,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          9000715,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熷姭浼欐嫹
          9000785,//9.00  閿熸垝姹夐敓鏂ゆ嫹閿熸枻鎷�  閿熷姭浼欐嫹
          9000882,//10.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熷姭浼欐嫹
          9002187,//11.00 閿熸枻鎷蜂繜鐓為敓锟�  閿熷姭浼欐嫹
          9002277,//12.00 閿熺獤甯嫹閿熺即鍑ゆ嫹  閿熷姭浼欐嫹
          9002419,//13.00 閿熸枻鎷烽敓鏂ゆ嫹鍧涢敓锟�  閿熷姭浼欐嫹
          9002561,//14.00 閿熸枻鎷蜂竴閿燂拷 閿熷姭浼欐嫹
          300413,//15.00  閿熸枻鎷烽敓琛楃櫢鎷� 閿熷姭浼欐嫹
          600280,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸暀绛规嫹  閿熷姭浼欐嫹
          600306,//17.00  *ST閿熸暀绛规嫹 閿熷姭浼欐嫹
          600327,//18.00  閿熻涓滃嚖鎷� 閿熷姭浼欐嫹
          600515,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          600628,//20.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熷姭浼欐嫹
          600655,//21.00  璞洯閿熸暀绛规嫹  閿熷姭浼欐嫹
          600682,//22.00  閿熻緝鎾呮嫹閿熼摪甯嫹  閿熷姭浼欐嫹
          600693,//23.00  閿熸枻鎷烽敓鍔》鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          600694,//24.00  閿熸枻鎷烽敓鏁欒偂鍑ゆ嫹  閿熷姭浼欐嫹
          600697,//25.00  娆ч敓瑙掔》鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          600712,//26.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷姭浼欐嫹  閿熷姭浼欐嫹
          600723,//27.00  閿熸枻鎷烽敓鏁欒偂鍑ゆ嫹  閿熷姭浼欐嫹
          600729,//28.00  閿熸枻鎷烽敓鏂ゆ嫹鍊╅敓锟�  閿熷姭浼欐嫹
          600738,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熷姭浼欐嫹
          600774,//30.00  閿熸枻鎷烽敓鏁欑》鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          600778,//31.00  閿熺獤濂界》鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          600785,//32.00  閿熼摪浼欐嫹閿熷姭浼欐嫹  閿熷姭浼欐嫹
          600814,//33.00  閿熸枻鎷烽敓鎹锋枻鎷烽敓锟�  閿熷姭浼欐嫹
          600821,//34.00  閿熸枻鎷峰姖涓� 閿熷姭浼欐嫹
          600824,//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熷姭浼欐嫹
          600828,//36.00  鑼備笟閿熸枻鎷蜂笟  閿熷姭浼欐嫹
          600838,//37.00  閿熻緝鐚存嫹閿熻剼甯嫹  閿熷姭浼欐嫹
          600857,//38.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷彨甯嫹  閿熷姭浼欐嫹
          600858,//39.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熷姭浼欐嫹
          600859,//40.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熷姭浼欐嫹
          600861,//41.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          600865,//42.00  閿熷姭杈炬嫹閿熸枻鎷�  閿熷姭浼欐嫹
          600891,//43.00  閿熸枻鎷烽敓琛楃》鎷烽敓鏂ゆ嫹  閿熷姭浼欐嫹
          603031,//44.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熷姭浼欐嫹
          603101,//45.00  閿熸枻鎷烽敓缁炴唻鎷烽敓锟�  閿熷姭浼欐嫹
          603123//46.00  閿熸枻鎷峰井閿熺即鍑ゆ嫹  閿熷姭浼欐嫹
    ));
    
    
    List<Integer> listCode_80_csls = new ArrayList<Integer>(Arrays.asList(
    9000759,//1.00  閿熷彨鐧剧》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002251,//2.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002264,//3.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002336,//4.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002697,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600361,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸绛规嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600827,//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601010,//8.00 閿熶茎鍑ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601116,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601933,//10.00  閿熸枻鎷烽敓鐨嗙鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    603708//11.00  閿熸彮纭锋嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_81_dqls = new ArrayList<Integer>(Arrays.asList(
        9002024,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
        9002416,//2.00  閿熸枻鎷锋柦閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
        600898//3.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    ));
    List<Integer> listCode_82_yysy = new ArrayList<Integer>(Arrays.asList(

      9000028,//1.00  閿熸枻鎷疯嵂涓�閿熸枻鎷�  鍖昏嵂閿熸枻鎷蜂笟
      9000411,//2.00  鑻遍敓鎴》鎷烽敓鏂ゆ嫹  鍖昏嵂閿熸枻鎷蜂笟
      9000705,//3.00  閿熷姹熼敓鏂ゆ嫹鍏�  鍖昏嵂閿熸枻鎷蜂笟
      9002462,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 鍖昏嵂閿熸枻鎷蜂笟
      9002589,//5.00  閿熸枻鎷峰尰鑽�  鍖昏嵂閿熸枻鎷蜂笟
      9002727,//6.00  涓�閿熸枻鎷烽敓鏂ゆ嫹 鍖昏嵂閿熸枻鎷蜂笟
      9002758,//7.00  閿熸枻鎷烽�氬尰鑽�  鍖昏嵂閿熸枻鎷蜂笟
      9002788,//8.00  閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  鍖昏嵂閿熸枻鎷蜂笟
      600056,//9.00 閿熷彨鐧告嫹鍖昏嵂  鍖昏嵂閿熸枻鎷蜂笟
      600090,//10.00  鍚岄敓鏂ゆ嫹閿熸枻鎷� 鍖昏嵂閿熸枻鎷蜂笟
      600511,//11.00  閿熸枻鎷疯嵂閿熺即鍑ゆ嫹  鍖昏嵂閿熸枻鎷蜂笟
      600713,//12.00  閿熻緝鎾呮嫹鍖昏嵂  鍖昏嵂閿熸枻鎷蜂笟
      600829,//13.00  閿熸枻鎷烽敓鏂ゆ嫹鍚屾嘲  鍖昏嵂閿熸枻鎷蜂笟
      600833,//14.00  閿熸枻鎷蜂竴鍖昏嵂  鍖昏嵂閿熸枻鎷蜂笟
      600998,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閫� 鍖昏嵂閿熸枻鎷蜂笟
      601607,//16.00  閿熻緝鐚存嫹鍖昏嵂  鍖昏嵂閿熸枻鎷蜂笟
      603108,//17.00  閿熸枻鎷烽敓鎻枻鎷烽敓锟�  鍖昏嵂閿熸枻鎷蜂笟
      603368,//18.00  閿熸枻鎷烽敓鏂ゆ嫹鍖昏嵂  鍖昏嵂閿熸枻鎷蜂笟
      603716,//19.00  閿熸枻鎷烽敓鏂ゆ嫹鏂� 鍖昏嵂閿熸枻鎷蜂笟
      603883,//20.00  閿熻緝甯嫹閿熸枻鎷� 鍖昏嵂閿熸枻鎷蜂笟
      603939//21.00  閿熸枻鎷烽敓鎻晪鎷烽敓锟�  鍖昏嵂閿熸枻鎷蜂笟
    ));
    
    List<Integer> listCode_83_qtsy = new ArrayList<Integer>(Arrays.asList(
        9000026,//1.00  閿熸枻鎷烽敓瑙掕揪鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟
        9000829,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟
        9002556,//3.00  閿熸枻鎷烽殕閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟
        9002780,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟
        300022,//5.00 閿熸枻鎷烽敓鏂ゆ嫹鍐滈敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟
        600122,//6.00 閿熸枻鎷峰浘閿熺鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟
        603777//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟
    
    ));
    
    List<Integer> listCode_84_spc = new ArrayList<Integer>(Arrays.asList(
    9002344,//1.00  閿熸枻鎷烽敓鏂ゆ嫹鐨敓鏂ゆ嫹  閿熸枻鎷峰搧閿熸枻鎷�
    600415,//2.00 灏忛敓鏂ゆ嫹鍝侀敓鏂ゆ嫹  閿熸枻鎷峰搧閿熸枻鎷�
    600790//3.00 閿熸枻鎷锋煇閿燂拷 閿熸枻鎷峰搧閿熸枻鎷�
    ));
    
    List<Integer> listCode_85_pfy = new ArrayList<Integer>(Arrays.asList(
          9000587,//1.00  閿熸枻鎷烽敓鐫厛鐚存嫹  閿熸枻鎷烽敓鏂ゆ嫹涓�
          9000638,//2.00  閿熸触鏂瑰嚖鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹涓�
          9000652,//3.00  娉伴敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹涓�
          9000906,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹涓�
          9002441,//5.00  閿熸枻鎷蜂笟閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹涓�
          300538,//6.00 鍚岄敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹涓�
          600753//7.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹涓�
    
    ));
    
    List<Integer> listCode_86_cpy = new ArrayList<Integer>(Arrays.asList(
          9000504,//1.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹涓�
          9000719,//2.00  閿熸枻鎷峰嵁閿熺煫锟�  閿熸枻鎷烽敓鏂ゆ嫹涓�
          9000793,//3.00  閿熸枻鎷烽敓鑴氳揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          9002181,//4.00  閿熸枻鎷� 閿熸枻鎷� 濯� 閿熸枻鎷烽敓鏂ゆ嫹涓�
          300148,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熶茎浼欐嫹  閿熸枻鎷烽敓鏂ゆ嫹涓�
          300364,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹涓�
          600229,//7.00 閿熸枻鎷烽敓鍙揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          600373,//8.00 閿熸枻鎷烽敓渚ヨ揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          600551,//9.00 鏃堕敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹涓�
          600633,//10.00  閿熷鎶ラ敓鏂ゆ嫹濯�  閿熸枻鎷烽敓鏂ゆ嫹涓�
          600757,//11.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          600825,//12.00  閿熼摪浼欐嫹閿熸枻鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          601098,//13.00  閿熸枻鎷烽敓杈冭揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          601801,//14.00  閿熸枻鎷烽敓閾拌揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          601811,//15.00  閿熼摪浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹涓�
          601900,//16.00  閿熻緝鍑ゆ嫹閿熸枻鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          601928,//17.00  閿熸枻鎷疯垳閿熺煫锟�  閿熸枻鎷烽敓鏂ゆ嫹涓�
          601999,//18.00  閿熸枻鎷烽敓鑺ヤ紶濯�  閿熸枻鎷烽敓鏂ゆ嫹涓�
          603999,//19.00  閿熸枻鎷烽敓绔揪鎷峰獟  閿熸枻鎷烽敓鏂ゆ嫹涓�
          601858//20.00  閿熷彨鐧告嫹閿熺嫛杈炬嫹  閿熸枻鎷烽敓鏂ゆ嫹涓�
    
    ));
    
    List<Integer> listCode_87_ysyx = new ArrayList<Integer>(Arrays.asList(
    9000156,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰獟  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000665,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000673,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000835,//4.00  閿熸枻鎷烽敓瑙掕鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9000917,//5.00  閿熸枻鎷锋劍顐�锟�  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002071,//6.00  閿熸枻鎷烽敓鏂ゆ嫹褰遍敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002143,//7.00  鍗伴敓閰佃揪鎷峰獟  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002238,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002292,//9.00  閿熼摪鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002343,//10.00 閿熸枻鎷烽敓渚ヨ揪鎷峰獟  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002445,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熶茎浼欐嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002502,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熶茎浼欐嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002624,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    9002739,//14.00 閿熸枻鎷烽敓鐨嗙尨鎷烽敓锟�  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300027,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻纰夋嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300133,//16.00  閿熸枻鎷烽敓鏂ゆ嫹褰遍敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300251,//17.00  閿熸枻鎷烽敓绔揪鎷峰獟  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300291,//18.00  閿熸枻鎷峰綍閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300336,//19.00  閿熸枻鎷烽敓渚ヤ紮鎷� 褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300426,//20.00  閿熺嫛纰夋嫹褰遍敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    300528,//21.00  閿熸彮闈╂嫹閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600037,//22.00  閿熷�熷崕閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600088,//23.00  閿熸枻鎷烽敓鎺ヨ揪鎷峰獟  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600136,//24.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600576,//25.00  閿熸枻鎷烽敓鏂ゆ嫹骞曢敓锟�  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600637,//26.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600715,//27.00  閿熸枻鎷锋姇閿熸埅鐧告嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600831,//28.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600936,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600959,//30.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600977,//31.00  閿熷彨鐧告嫹閿熸枻鎷峰奖  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600996,//32.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601595,//33.00  閿熻緝鐚存嫹閿熸枻鎷峰奖  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601599,//34.00  楣块敓鏂ゆ嫹閿熶茎浼欐嫹  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    601929,//35.00  閿熸枻鎷烽敓鎺ヨ揪鎷峰獟  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    603598//36.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰獟  褰遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_88_lyfw = new ArrayList<Integer>(Arrays.asList(
        9000610,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        9000613,//2.00  閿熻涓滅尨鎷稟  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        9000796,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        9000802,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熶茎浼欐嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        9002558,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        9002707,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        300178,//7.00 閿熻妭甯嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        600138,//8.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        600358,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        600706,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        601888,//11.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        603099,//12.00  閿熸枻鎷烽敓鏂ゆ嫹灞� 閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        603199,//13.00  閿熻剼浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
        603869//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞垮嚖鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_89_lyjd = new ArrayList<Integer>(Arrays.asList(
        9000069,//1.00  閿熸枻鎷烽敓楗哄煄锝忔嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        9000430,//2.00  閿熻剼瀹舵枻鎷� 閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        9000888,//3.00  閿熸枻鎷风湁灞遍敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        9000978,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        9002033,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        9002059,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        9002159,//7.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        300144,//8.00 閿熻娇绛规嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        600054,//9.00 閿熸枻鎷峰北閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        600555,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        600593,//11.00  閿熸枻鎷烽敓鏂ゆ嫹鍦ｉ敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
        600749//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓杞挎拝鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_90_jczz = new ArrayList<Integer>(Arrays.asList(
    
          9000410,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          9000837,//2.00  閿熸埅杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          9002248,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          9002520,//4.00  閿熺Ц鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          9002559,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          300161,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          300441,//7.00 閿熸枻鎷锋柉閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          600243,//8.00 閿熸磥娴烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          600806,//9.00 *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          601882,//10.00  閿熸枻鎷烽敓灞婄簿閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
          603011//11.00  閿熻緝璁规嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    List<Integer> listCode_91_jxjj = new ArrayList<Integer>(Arrays.asList(
          9000530,//1.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000570,//2.00  閿熺Ц绛规嫹閿熸枻鎷烽敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000595,//3.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000617,//4.00  *ST閿熺煫璇ф嫹 閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000678,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000777,//6.00  閿熷彨鏍哥纭锋嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000816,//7.00  閿熻浼欐嫹鍐滀笟  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000856,//8.00  *ST閿熸枻鎷疯 閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000880,//9.00  娼嶉敓鏂ゆ嫹閿熸埅浼欐嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9000903,//10.00 閿熸枻鎷烽敓鑺傝鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002026,//11.00 灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002046,//12.00 閿熸枻鎷烽敓鍙纭锋嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002050,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熻鍖℃嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002101,//14.00 閿熷涓滈敓鏂ゆ嫹鍥�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002122,//15.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002147,//16.00 閿熼摪鐧告嫹鍦嗛敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002150,//17.00 閫氶敓鏂ゆ嫹瑁呴敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002272,//18.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002342,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002347,//20.00 娉伴敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002418,//21.00 閿熸枻鎷风洓閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002435,//22.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002438,//23.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002472,//24.00 鍙岄敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002480,//25.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002514,//26.00 閿熸枻鎷烽Θ閿熺嫛纭锋嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002552,//27.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002598,//28.00 灞遍敓鏂ゆ嫹閿熼摪鐧告嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002633,//29.00 閿熸枻鎷蜂箳鐓為敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002747,//30.00 閿熸枻鎷锋柉閿熸枻鎷� 閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002760,//31.00 閿熸枻鎷烽敓杞胯偂鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002795,//32.00 閿熸枻鎷烽敓鏂ゆ嫹閿熻鍖℃嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          9002823,//33.00 閿熸枻鎷烽敓鍙拝鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300091,//34.00  閿熸枻鎷烽�氶敓鏂ゆ嫹 閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300095,//35.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300151,//36.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300257,//37.00  閿熸枻鎷峰北閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300260,//38.00  閿熸枻鎷烽敓鏂ゆ嫹搴旈敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300266,//39.00  閿熸枻鎷锋簮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300391,//40.00  閿熸枻鎷疯穬閿熺嫛纭锋嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300420,//41.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300421,//42.00  閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300435,//43.00  閿熸枻鎷锋嘲閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300464,//44.00  閿熻寰芥拝鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300470,//45.00  閿熺Ц浼欐嫹閿熸澃鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300488,//46.00  閿熸枻鎷峰﹢銈忔嫹閿燂拷  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          300503,//47.00  閿熻鎾呮嫹閿熸枻鎷烽敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          600114,//48.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          600421,//49.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          600520,//50.00  *ST閿熷彨鍑ゆ嫹 閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          600592,//51.00  閿熸枻鎷锋邯閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          600619,//52.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          600765,//53.00  閿熷彨鐚存嫹閿熸埅浼欐嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          600841,//54.00  閿熻緝璇ф嫹鐓為敓锟�  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          601002,//55.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          601177,//56.00  閿熸枻鎷烽敓鏂ゆ嫹鍓嶉敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          601218,//57.00  閿熸枻鎷烽敓杞跨纭锋嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          601369,//58.00  閿熼摪榧撹鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          603315,//59.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          603667,//60.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼摪杈炬嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
          603726//61.00  閿熺粸杩》鎷烽敓鏂ゆ嫹  閿熸枻鎷锋閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_92_hgjx = new ArrayList<Integer>(Arrays.asList(
      9000852,//1.00  鐭抽敓鏂ゆ嫹閿熸枻鎷锋  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      9002278,//2.00  閿熶粖寮�鑲″嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      9002337,//3.00  閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      9002353,//4.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      9002430,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      9002490,//6.00  灞遍敓鏂ゆ嫹澧ㄩ敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      9002564,//7.00  閿熸枻鎷烽敓琛楃纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      9002698,//8.00  閿熸枻鎷峰疄閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      300228,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      600579,//10.00  閿熷眾鍗庨櫌 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
      601798//11.00  閿熸枻鎷烽敓鐙￠潻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋
    ));
    
    List<Integer> listCode_93_qgjx = new ArrayList<Integer>(Arrays.asList(
      9000039,//1.00  閿熷彨纭锋嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熺粨宸ラ敓鏂ゆ嫹姊�
      9000821,//2.00  閿熸枻鎷峰北閿熸枻鎷烽敓锟�  閿熺粨宸ラ敓鏂ゆ嫹姊�
      9002209,//3.00  閿熸枻鎷� 閿熸枻鎷� 闅� 閿熺粨宸ラ敓鏂ゆ嫹姊�
      9002282,//4.00  閿熸枻鎷烽敓绛嬪伐閿熸枻鎷�  閿熺粨宸ラ敓鏂ゆ嫹姊�
      9002444,//5.00  閿熸枻鎷烽敓瑙掔纭锋嫹  閿熺粨宸ラ敓鏂ゆ嫹姊�
      9002611,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熺粨宸ラ敓鏂ゆ嫹姊�
      300126,//7.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熺粨宸ラ敓鏂ゆ嫹姊�
      300173,//8.00 閿熻浼欐嫹閿熺即纰夋嫹  閿熺粨宸ラ敓鏂ゆ嫹姊�
      300195,//9.00 閿熸枻鎷烽敓鍔偂鍑ゆ嫹  閿熺粨宸ラ敓鏂ゆ嫹姊�
      300442//10.00  閿熸枻鎷烽敓鏂ゆ嫹鐩� 閿熺粨宸ラ敓鏂ゆ嫹姊�
    
    ));
    List<Integer> listCode_94_fzjx = new ArrayList<Integer>(Arrays.asList(
    9000666,//1.00  閿熸枻鎷风含閿熶茎浼欐嫹  閿熸枻鎷风粐閿熸枻鎷锋
    9002021,//2.00  閿熷彨鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷风粐閿熸枻鎷锋
    9002196,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷风粐閿熸枻鎷锋
    9002722,//4.00  閿熸枻鎷烽敓琛楄偂鍑ゆ嫹  閿熸枻鎷风粐閿熸枻鎷锋
    300307,//5.00 閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  閿熸枻鎷风粐閿熸枻鎷锋
    300384,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷风粐閿熸枻鎷锋
    600302,//7.00 閿熸枻鎷峰噯閿熺即鍑ゆ嫹  閿熸枻鎷风粐閿熸枻鎷锋
    600843,//8.00 閿熻緝鐧告嫹閿熺枼璐�  閿熸枻鎷风粐閿熸枻鎷锋
    603337//9.00 閿熸澃鍏嬭偂鍑ゆ嫹  閿熸枻鎷风粐閿熸枻鎷锋
    ));
    
    List<Integer> listCode_95_lyjx = new ArrayList<Integer>(Arrays.asList(
    9002532,//1.00  閿熼摪鏂ゆ嫹閿熸彮锟�  鍐滈敓鐭紮鎷锋
    9002779,//2.00  閿熷彨纭锋嫹钀嶉敓锟�  鍐滈敓鐭紮鎷锋
    300159,//3.00 閿熸枻鎷烽敓鍙偂鍑ゆ嫹  鍐滈敓鐭紮鎷锋
    600218,//4.00 鍏ㄩ敓鏂ゆ嫹閿熸枻鎷�  鍐滈敓鐭紮鎷锋
    601038,//5.00 涓�閿熻緝鑲″嚖鎷�  鍐滈敓鐭紮鎷锋
    603789//6.00 閿熻鐧告嫹鍐滈敓鏂ゆ嫹  鍐滈敓鐭紮鎷锋
    ));
    List<Integer> listCode_96_zyjx = new ArrayList<Integer>(Arrays.asList(
    9000404,//1.00  閿熸枻鎷烽敓鏂ゆ嫹鍘嬮敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    9000551,//2.00  閿熸枻鎷峰厓閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    9000925,//3.00  閿熻妭鍚堢纭锋嫹  涓撻敓鐭紮鎷锋
    9002006,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    9002192,//5.00  閿熻妭鎹疯偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002204,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  涓撻敓鐭紮鎷锋
    9002255,//7.00  閿熸枻鎷烽檰閿熸埅鐧告嫹  涓撻敓鐭紮鎷锋
    9002366,//8.00  鍙伴敓鏂ゆ嫹閿熷壙纰夋嫹  涓撻敓鐭紮鎷锋
    9002509,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺煫锟�  涓撻敓鐭紮鎷锋
    9002529,//10.00 閿熸枻鎷锋簮閿熸枻鎷锋  涓撻敓鐭紮鎷锋
    9002530,//11.00 閿熺粨涓滈敓缂村嚖鎷�  涓撻敓鐭紮鎷锋
    9002534,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002595,//13.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    9002613,//14.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002621,//15.00 閿熸枻鎷烽敓鎹疯偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002630,//16.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  涓撻敓鐭紮鎷锋
    9002639,//17.00 闆敓鍓胯偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002645,//18.00 閿熸枻鎷烽敓鏂ゆ嫹钀嶉敓锟�  涓撻敓鐭紮鎷锋
    9002651,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002686,//20.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 涓撻敓鐭紮鎷锋
    9002690,//21.00 閿熸枻鎷烽敓瑙掔櫢鎷烽敓锟�  涓撻敓鐭紮鎷锋
    9002691,//22.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002786,//23.00 閿熸枻鎷烽敓鏂ゆ嫹灞遍敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    9002793,//24.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    9002796,//25.00 閿熸枻鎷烽敓杞跨纭锋嫹  涓撻敓鐭紮鎷锋
    9002816,//26.00 閿熼叺绉戣揪鎷� 涓撻敓鐭紮鎷锋
    9002833,//27.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    9002837,//28.00 鑻辩淮閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300023,//29.00  閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300024,//30.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300029,//31.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  涓撻敓鐭紮鎷锋
    300092,//32.00  閿熸枻鎷烽敓閾颁紮鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300116,//33.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300145,//34.00  閿熷彨閲戠幆鎾呮嫹  涓撻敓鐭紮鎷锋
    300193,//35.00  閿熸枻鎷峰＋閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    300201,//36.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300202,//37.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300210,//38.00  妫繙閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300249,//39.00  閿熸枻鎷烽敓闃跺尅鎷� 涓撻敓鐭紮鎷锋
    300263,//40.00  闅嗛敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300276,//41.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300278,//42.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300280,//43.00  閿熸枻鎷烽�氶敓鏂ゆ嫹鍘�  涓撻敓鐭紮鎷锋
    300281,//44.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300293,//45.00  閿熸枻鎷疯嫳瑁呴敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300309,//46.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    300316,//47.00  閿熸枻鎷风洓閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300334,//48.00  閿熸枻鎷疯啘閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    300368,//49.00  閿熸枻鎷烽敓缂村嚖鎷�  涓撻敓鐭紮鎷锋
    300382,//50.00  鏂敓鏂ゆ嫹閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300400,//51.00  閿熸枻鎷烽敓鎴偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300402,//52.00  閿熸枻鎷疯壊閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300411,//53.00  閿熸枻鎷疯妱鐓為敓锟�  涓撻敓鐭紮鎷锋
    300415,//54.00  閿熸枻鎷蜂箣閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300434,//55.00  閿熸枻鎷风煶閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300443,//56.00  閿熸枻鎷烽敓闃跺嚖鎷烽敓锟�  涓撻敓鐭紮鎷锋
    300450,//57.00  閿熼ズ纰夋嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300457,//58.00  璧㈤敓杈冪纭锋嫹  涓撻敓鐭紮鎷锋
    300461,//59.00  閿熸枻鎷烽敓鍙拝鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300462,//60.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300471,//61.00  閿熸枻鎷烽敓绉歌偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300472,//62.00  閿熸枻鎷峰厓閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    300475,//63.00  閿熸枻鎷烽殕閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    300483,//64.00  閿熸枻鎷锋柦閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300486,//65.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300499,//66.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300509,//67.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300512,//68.00  閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300521,//69.00  閿熸枻鎷峰徃閿熸枻鎷� 涓撻敓鐭紮鎷锋
    300526,//70.00  閿熸枻鎷锋綔閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    300527,//71.00  閿熸枻鎷烽敓鏂ゆ嫹搴旈敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300540,//72.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  涓撻敓鐭紮鎷锋
    300545,//73.00  閿熸枻鎷烽敓鏂ゆ嫹瑁呴敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300549,//74.00  閿熻剼寰锋拝鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    300551,//75.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    300569,//76.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  涓撻敓鐭紮鎷锋
    600184,//77.00  閿熸枻鎷烽敓缂村嚖鎷�  涓撻敓鐭紮鎷锋
    600435,//78.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    600475,//79.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  涓撻敓鐭紮鎷锋
    600499,//80.00  閿熺嫛杈炬嫹閿熸枻鎷烽敓锟�  涓撻敓鐭紮鎷锋
    600855,//81.00  閿熸枻鎷烽敓灞婇暱閿熸枻鎷�  涓撻敓鐭紮鎷锋
    600860,//82.00  閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  涓撻敓鐭紮鎷锋
    601226,//83.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  涓撻敓鐭紮鎷锋
    601608,//84.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  涓撻敓鐭紮鎷锋
    603012,//85.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    603029,//86.00  閿熸枻鎷烽敓缂村嚖鎷�  涓撻敓鐭紮鎷锋
    603036,//87.00  閿熸枻鎷烽�氶敓缂村嚖鎷�  涓撻敓鐭紮鎷锋
    603066,//88.00  閿熸枻鎷烽敓缂磋揪鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    603085,//89.00  閿熸枻鎷烽敓鏂ゆ嫹閽ラ敓锟�  涓撻敓鐭紮鎷锋
    603088,//90.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    603090,//91.00  閿熸枻鎷风洓閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    603131,//92.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    603159,//93.00  閿熻緝鐚存嫹閿熻鐚存嫹  涓撻敓鐭紮鎷锋
    603169,//94.00  閿熸枻鎷风煶閿熸枻鎷疯  涓撻敓鐭紮鎷锋
    603203,//95.00  閿熸枻鎷锋柉鐓為敓锟�  涓撻敓鐭紮鎷锋
    603298,//96.00  閿熸枻鎷烽敓鑺ラ泦閿熸枻鎷�  涓撻敓鐭紮鎷锋
    603308,//97.00  搴旈敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    603311,//98.00  閿熼噾娴蜂紮鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    603318,//99.00  閿熸枻鎷锋�濋敓缂村嚖鎷�  涓撻敓鐭紮鎷锋
    603338,//100.00 閿熷姹熼敓鏂ゆ嫹閿熸枻鎷�  涓撻敓鐭紮鎷锋
    603339,//101.00 閿熶茎鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    603686,//102.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  涓撻敓鐭紮鎷锋
    603698,//103.00 閿熸枻鎷烽敓灞婂伐閿熸枻鎷�  涓撻敓鐭紮鎷锋
    603699,//104.00 绾介敓鏂ゆ嫹閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    603800,//105.00 閿熸枻鎷锋．閿熺即鍑ゆ嫹  涓撻敓鐭紮鎷锋
    603901,//106.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  涓撻敓鐭紮鎷锋
    603690//107.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  涓撻敓鐭紮鎷锋
    ));
    
    List<Integer> listCode_97_tl = new ArrayList<Integer>(Arrays.asList(
    600125,//1.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷疯矾
    601006,//2.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯矾  閿熸枻鎷疯矾
    601333//3.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯矾  閿熸枻鎷疯矾
    ));
    List<Integer> listCode_98_sy = new ArrayList<Integer>(Arrays.asList(
    9000520,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  姘撮敓鏂ゆ嫹
    9002320,//2.00  閿熸枻鎷峰场閿熺即鍑ゆ嫹  姘撮敓鏂ゆ嫹
    600026,//3.00 閿熸枻鎷疯繙閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    600242,//4.00 閿熷彨璇ф嫹閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    600428,//5.00 閿熸枻鎷疯繙閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    600575,//6.00 閿熺瓔姹熼敓鏂ゆ嫹閿熸枻鎷�  姘撮敓鏂ゆ嫹
    600692,//7.00 閿熸枻鎷烽�氶敓缂村嚖鎷�  姘撮敓鏂ゆ嫹
    600751,//8.00 閿熷眾娴锋姇閿熸枻鎷�  姘撮敓鏂ゆ嫹
    600798,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    600896,//10.00  閿熸枻鎷烽敓鏂ゆ嫹鎶曢敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    601866,//11.00  閿熸枻鎷疯繙閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    601872,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻杈炬嫹  姘撮敓鏂ゆ嫹
    601919,//13.00  閿熸枻鎷疯繙閿熸枻鎷烽敓鏂ゆ嫹  姘撮敓鏂ゆ嫹
    603167//14.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻璁规嫹  姘撮敓鏂ゆ嫹
    ));
    List<Integer> listCode_99_ky = new ArrayList<Integer>(Arrays.asList(
    9000099,//1.00  閿熸枻鎷烽敓鑴氱尨鎷风洿  閿熸枻鎷烽敓鏂ゆ嫹
    600029,//2.00 閿熻緝鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600115,//3.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600221,//4.00 閿熸枻鎷烽敓杈冪尨鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    601021,//5.00 閿熸枻鎷烽敓鏂よ埅閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
    601111,//6.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    603885//7.00 閿熸枻鎷烽敓浠嬭埅閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_100_gl = new ArrayList<Integer>(Arrays.asList(
    9000996,//1.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷疯矾
    9002357,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷疯矾
    9002627,//3.00  閿熷壙璇ф嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷疯矾
    9002682,//4.00  閿熸枻鎷烽敓鐫偂鍑ゆ嫹  閿熸枻鎷疯矾
    600561,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷疯矾
    603069,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷疯矾
    603223,//7.00 閿熸枻鎷烽�氶敓缂村嚖鎷�  閿熸枻鎷疯矾
    603032//8.00 閿熸枻鎷烽敓閾版枻鎷烽敓鏂ゆ嫹  閿熸枻鎷疯矾
    ));
    
    List<Integer> listCode_101_lq = new ArrayList<Integer>(Arrays.asList(
    9000429,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熷姭锝忔嫹  璺敓鏂ゆ嫹
    9000548,//2.00  閿熸枻鎷烽敓鏂ゆ嫹鎶曢敓鏂ゆ嫹  璺敓鏂ゆ嫹
    9000828,//3.00  閿熸枻鎷疯帪閿熸埅鐧告嫹  璺敓鏂ゆ嫹
    9000886,//4.00  閿熸枻鎷烽敓杈冮潻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    9000900,//5.00  閿熻杈炬嫹鎶曢敓鏂ゆ嫹  璺敓鏂ゆ嫹
    9000916,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    600012,//7.00 閿熸枻鎷烽�氶敓鏂ゆ嫹閿熸枻鎷�  璺敓鏂ゆ嫹
    600020,//8.00 閿熸枻鎷峰師閿熸枻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    600033,//9.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    600035,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  璺敓鏂ゆ嫹
    600106,//11.00  閿熸枻鎷烽敓鏂ゆ嫹璺敓鏂ゆ嫹  璺敓鏂ゆ嫹
    600269,//12.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    600350,//13.00  灞遍敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    600368,//14.00  閿熸枻鎷烽敓鐫枻鎷烽��  璺敓鏂ゆ嫹
    600377,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    600548,//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 璺敓鏂ゆ嫹
    601107,//17.00  閿熶茎杈炬嫹閿熸枻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    601188,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽��  璺敓鏂ゆ嫹
    601518//19.00  閿熸枻鎷烽敓琛楅潻鎷烽敓鏂ゆ嫹  璺敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_102_jc = new ArrayList<Integer>(Arrays.asList(
    9000089,//1.00  閿熸枻鎷烽敓鑺備紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600004,//2.00 閿熸枻鎷烽敓鐙′紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600009,//3.00 閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹
    600897//4.00 閿熸枻鎷烽敓鑴氱┖闈╂嫹  閿熸枻鎷烽敓鏂ゆ嫹
    ));
    List<Integer> listCode_103_gk = new ArrayList<Integer>(Arrays.asList(
    9000022,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸鍖℃嫹
    9000088,//2.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸鍖℃嫹
    9000507,//3.00  閿熶粙娴烽敓鏂ゆ嫹 閿熸鍖℃嫹
    9000582,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸鍖℃嫹
    9000905,//5.00  閿熸枻鎷烽敓鑴氶潻鎷烽敓鏂ゆ嫹  閿熸鍖℃嫹
    9002040,//6.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸鍖℃嫹
    600017,//7.00 閿熸枻鎷烽敓绉搁潻鎷� 閿熸鍖℃嫹
    600018,//8.00 閿熻緝娓》鎷烽敓鏂ゆ嫹  閿熸鍖℃嫹
    600190,//9.00 閿熸枻鎷烽敓鎹烽潻鎷� 閿熸鍖℃嫹
    600279,//10.00  閿熸枻鎷烽敓鏂ゆ嫹鍔敓锟�  閿熸鍖℃嫹
    600317,//11.00  钀ラ敓鑺傞潻鎷� 閿熸鍖℃嫹
    600717,//12.00  閿熸枻鎷烽敓鏂ゆ嫹 閿熸鍖℃嫹
    601000,//13.00  閿熸枻鎷峰北閿熸枻鎷� 閿熸鍖℃嫹
    601008,//14.00  閿熸枻鎷烽敓鐙￠潻鎷� 閿熸鍖℃嫹
    601018,//15.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸鍖℃嫹
    601880//16.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸鍖℃嫹
    ));
    List<Integer> listCode_104_jzsg = new ArrayList<Integer>(Arrays.asList(
    9000010,//1.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋��  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9000065,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9000090,//3.00  閿熷眾鍋ラ敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9000498,//4.00  灞遍敓鏂ゆ嫹璺敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9000928,//5.00  閿熷彨閽㈢櫢鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9000961,//6.00  閿熸枻鎷烽敓杈冩枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002051,//7.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002060,//8.00  閿熸枻鎷� 姘� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002062,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002116,//10.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002135,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002140,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002178,//13.00 閿熸帴浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002307,//14.00 閿熸枻鎷烽敓鏂ゆ嫹璺敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002310,//15.00 閿熸枻鎷烽敓鏂ゆ嫹鍥敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002323,//16.00 閿熻剼甯嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002374,//17.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002431,//18.00 閿熸枻鎷锋鑲″嚖鎷�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002469,//19.00 閿熸枻鎷风淮閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002542,//20.00 閿熷彨浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002586,//21.00 鍥撮敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002628,//22.00 閿熺即璁规嫹璺敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002659,//23.00 閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002663,//24.00 閿熺Ц甯嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002717,//25.00 閿熸枻鎷烽敓鏂ゆ嫹鍥敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002738,//26.00 閿熷彨鍖℃嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002755,//27.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    9002775,//28.00 閿熶茎鍖℃嫹鍥敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300237,//29.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300284,//30.00  閿熺Ц鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300492,//31.00  灞遍敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300495,//32.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋��  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300500,//33.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300506,//34.00  閿熸枻鎷烽敓鎻紮鎷� 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300517,//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鍖℃嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    300536,//36.00  鍐滈敓鍙紮鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600039,//37.00  閿熶茎杈炬嫹璺敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600068,//38.00  閿熸枻鎷烽敓鐫府鎷� 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600083,//39.00  閿熸枻鎷烽敓鑴氳偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600170,//40.00  閿熻緝鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600248,//41.00  閿熸帴绛规嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600284,//42.00  閿熻璁规嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600326,//43.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷疯矾  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600491,//44.00  閿熸枻鎷峰厓閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600502,//45.00  閿熸枻鎷烽敓鏂ゆ嫹姘撮敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600512,//46.00  閿熻妭杈惧缓閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600528,//47.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600545,//48.00  閿熼摪鏂ゆ嫹閿熻鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600610,//49.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600629,//50.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600769,//51.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600820,//52.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600853,//53.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    600970,//54.00  閿熷彨鏉愮櫢鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601117,//55.00  閿熷彨鐧告嫹閿熸枻鎷峰  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601186,//56.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601390,//57.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601611,//58.00  閿熷彨鐧告嫹閿熷壙鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601618,//59.00  閿熷彨鐧告嫹閿熸枻鎷峰喍  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601668,//60.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601669,//61.00  閿熷彨鐧告嫹閿熺晫寤�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601789,//62.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    601800,//63.00  閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603007,//64.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603017,//65.00  閿熷彨鐚存嫹閿熸枻鎷烽敓锟�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603018,//66.00  閿熸枻鎷烽敓鍊熼泦閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603060,//67.00  閿熸枻鎷烽敓灞婇泦閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603778,//68.00  涔鹃敓鏂ゆ嫹鍥敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603843,//69.00  XD閿熸枻鎷峰钩閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603887,//70.00  閿熻鍦拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603909,//71.00  閿熻緝璇氳偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603959,//72.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺嫛纭锋嫹  閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    603979//73.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 閿熸枻鎷烽敓鏂ゆ嫹鏂介敓鏂ゆ嫹
    ));
    
    List<Integer> listCode_105_jzzs = new ArrayList<Integer>(Arrays.asList(
    9000018,//1.00  閿熸枻鎷烽敓鎹风鎷烽敓鏂ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002047,//2.00  閿熸枻鎷烽拱閿熺即鍑ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002081,//3.00  閿熸枻鎷� 閿燂拷 閿熸枻鎷� 瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002163,//4.00  閿熷彨鐚存嫹閿熸枻鎷烽敓鏂ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002247,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熶茎浼欐嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002325,//6.00  閿熸枻鎷烽敓杞胯偂鍑ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002375,//7.00  閿熸枻鎷烽敓鐭偂鍑ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002482,//8.00  閿熸枻鎷烽敓鏂ら泦閿熸枻鎷�  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002504,//9.00  閿熸枻鎷峰徑閿熸枻鎷烽敓锟�  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002620,//10.00 閿熸枻鎷峰嚫鐓為敓锟�  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002713,//11.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷风洓  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002781,//12.00 閿熸枻鎷烽敓鑴氳偂鍑ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002789,//13.00 閿熸枻鎷烽敓绉哥》鎷烽敓鏂ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002811,//14.00 閿熸枻鎷锋嘲閿熸枻鎷烽敓鏂ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002822,//15.00 閿熸枻鎷疯閿熸枻鎷烽敓鏂ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    9002830,//16.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    300117,//17.00  閿熸枻鎷峰瘬閿熺即鍑ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    600193,//18.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    601886,//19.00  閿熸枻鎷烽敓鎺ョ》鎷烽敓鏂ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    603030,//20.00  鍏ㄩ敓鏂ゆ嫹閿熺即鍑ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    603098,//21.00  妫敓鎴偂鍑ゆ嫹  瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    603828,//22.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    603929//23.00  N閿熸枻鎷烽敓鏂ゆ嫹 瑁呴敓鏂ゆ嫹瑁呴敓鏂ゆ嫹
    ));
    List<Integer> listCode_106_qgdc = new ArrayList<Integer>(Arrays.asList(
    9000002,//1.00  閿熸枻鎷� 閿熺嫛锝忔嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000014,//2.00  娌欓敓鎺ヨ偂鍑ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000031,//3.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000036,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000040,//5.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000042,//6.00  閿熸枻鎷烽敓鐫帶鐧告嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000043,//7.00  閿熷彨鐚存嫹閿熸埅璇ф嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000046,//8.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅鐧告嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000402,//9.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000616,//10.00 閿熸枻鎷烽敓鏂ゆ嫹鎶曢敓鏂ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000620,//11.00 閿熼摪浼欐嫹閿熸枻鎷� 鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000667,//12.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000736,//13.00 閿熷彨鍑ゆ嫹閿熸埅璇ф嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000797,//14.00 閿熷彨鐧告嫹閿熸枻鎷烽敓鏂ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9000918,//15.00 閿熻娇鍖℃嫹閿熸枻鎷� 鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9001979,//16.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺鍖℃嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9002133,//17.00 閿熸枻鎷烽敓绛嬮泦閿熸枻鎷�  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    9002146,//18.00 閿熸枻鎷风洓閿熸枻鎷峰睍  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600048,//19.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600067,//20.00  閿熻妭鍩庤揪鎷烽��  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600077,//21.00  閿熻娇璁规嫹閿熺即鍑ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600162,//22.00  閿熷姹熼敓鎴櫢鎷�  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600173,//23.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600208,//24.00  閿熼摪鐚存嫹閿熷彨鎲嬫嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600240,//25.00  閿熸枻鎷蜂笟閿熺粸鎲嬫嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600383,//26.00  閿熸枻鎷蜂付閿熸枻鎷烽敓锟�  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600393,//27.00  閿熸枻鎷锋嘲閿熺即鍑ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600510,//28.00  閿熸枻鎷风墶閿熸枻鎷� 鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600565,//29.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600606,//30.00  閿熸暀鍦版帶鐧告嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600621,//31.00  閿熸枻鎷烽敓杞胯偂鍑ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600657,//32.00  閿熻剼杈炬嫹澶敓锟�  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600665,//33.00  閿熸枻鎷烽敓鐨嗭拷 鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600684,//34.00  閿熶粙姹熷疄涓�  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600708,//35.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600748,//36.00  閿熸枻鎷峰疄閿熸枻鎷峰睍  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600791,//37.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    600823//38.00  閿熸枻鎷疯寕閿熺即鍑ゆ嫹  鍏ㄩ敓鏂ゆ嫹閿熸埅璇ф嫹
    ));
    List<Integer> listCode_107_qydc = new ArrayList<Integer>(Arrays.asList(
    9000006,//1.00  閿熸枻鎷烽敓鏂ゆ嫹涓氶敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000011,//2.00  閿熸枻鎷烽敓鏂ゆ嫹涓欰  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000029,//3.00  閿熸枻鎷烽敓绛嬫埧閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000506,//4.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000514,//5.00  閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000517,//6.00  閿熷姭甯嫹閿熸埅璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000534,//7.00  閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000537,//8.00  閿熸枻鎷烽敓绛嬪彂灞�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000540,//9.00  閿熸枻鎷烽敓鏂ゆ嫹閿熼叺锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000567,//10.00 閿熸枻鎷烽敓閾拌偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000573,//11.00 閿熸枻鎷烽敓鏂ゆ嫹杩滈敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000608,//12.00 閿熸枻鎷烽敓鏂ゆ嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000609,//13.00 閿熸枻鎷风煶鎶曢敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000631,//14.00 椤洪敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000656,//15.00 閿熸枻鎷蜂箳鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000668,//16.00 閿熷姭鍑ゆ嫹姣撻敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000671,//17.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000691,//18.00 ST閿熸枻鎷峰お  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000718,//19.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000732,//20.00 娉伴敓鏁欑》鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000809,//21.00 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪绛规嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000838,//22.00 閿熸枻鎷烽敓鑴氬嚖鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000863,//23.00 閿熸枻鎷烽敓鏂ゆ嫹鍗伴敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000897,//24.00 閿熸枻鎷烽敓鏂ゆ嫹閿熺Ц锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000926,//25.00 閿熸枻鎷烽敓瑙掕偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000931,//26.00 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000965,//27.00 閿熷眾淇濋敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000979,//28.00 閿熷彨鐚存嫹鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9000981,//29.00 閿熸枻鎷烽敓鑺傝偂鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9002016,//30.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9002077,//31.00 閿熸枻鎷烽厓鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9002208,//32.00 閿熻緝鑲ュ煄鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9002244,//33.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9002305,//34.00 閿熻緝鐧告嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    9002314,//35.00 閿熸枻鎷峰北閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600052,//36.00  閿熷姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600053,//37.00  閿熻剼璁规嫹鎶曢敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600094,//38.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600095,//39.00  閿熸枻鎷烽敓绔尅鎷� 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600113,//40.00  閿熷姹熼敓鏂ゆ嫹閿熸枻鎷�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600159,//41.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600185,//42.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸埅璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600223,//43.00  椴侀敓鏂ゆ嫹閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600225,//44.00  閿熸枻鎷烽敓鏂ゆ嫹灞遍敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600239,//45.00  閿熸枻鎷烽敓杈冪鎷锋姇  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600246,//46.00  閿熸枻鎷烽�氶敓鎴鎷�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600266,//47.00  閿熸枻鎷烽敓鏂ゆ嫹閿熻鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600322,//48.00  閿熷眾鎴块敓鏂ゆ嫹灞�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600325,//49.00  閿熸枻鎷烽敓鏂ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600340,//50.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸彮闈╂嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600376,//51.00  閿熼樁鍖℃嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600466,//52.00  閿熸枻鎷烽敓瑙ｅ彂灞�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600503,//53.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600533,//54.00  閿熸枻鎷烽湠閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600568,//55.00  閿熸枻鎷烽敓鏂ゆ嫹鍖婚敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600622,//56.00  閿熻娇鎲嬫嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600638,//57.00  閿熼摪浼欐嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600641,//58.00  閿熸枻鎷蜂笟閿熸枻鎷蜂笟  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600649,//59.00  閿熸枻鎷锋姇閿熸埅鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600675,//60.00  *ST閿熸枻鎷烽敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600683,//61.00  閿熸枻鎷锋姇閿熸枻鎷峰睍  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600696,//62.00  鍖瑰嚫鍖� 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600716,//63.00  閿熸枻鎷锋柉鐓為敓锟�  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600724,//64.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600733,//65.00  S鍓嶉敓鏂ゆ嫹 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600743,//66.00  閿熸枻鎷疯繙閿熸埅璇ф嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600773,//67.00  閿熸枻鎷烽敓鎴鎷锋姇  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600807,//68.00  閿熸枻鎷蜂笟閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600890,//69.00  閿熷彨鍑ゆ嫹閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    601155,//70.00  閿熼摪鍩庢帶鐧告嫹  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    601588,//71.00  閿熸枻鎷烽敓鏂ゆ嫹瀹炰笟  閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�
    600732//72.00  *ST閿熸枻鎷锋 閿熸枻鎷烽敓鏂ゆ嫹澶敓锟�

    ));
    List<Integer> listCode_108_yqkf = new ArrayList<Integer>(Arrays.asList(
    9000628,//1.00  閿熸枻鎷烽敓閾板嚖鎷峰睍  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600007,//2.00 閿熷彨鐧告嫹閿熸枻鎷疯锤  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600064,//3.00 閿熻緝鎾呮嫹閿熺鍖℃嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600082,//4.00 閿熸枻鎷锋嘲閿熸枻鎷峰睍  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600133,//5.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600215,//6.00 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600463,//7.00 閿熺Ц娓偂鍑ゆ嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600604,//8.00 閿熷彨鎲嬫嫹閿熸枻鎷烽敓鏂ゆ嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600639,//9.00 閿熻璁规嫹閿熸枻鎷烽敓鏂ゆ嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600648,//10.00  閿熸枻鎷烽敓鏂ゆ嫹閿燂拷 鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600658,//11.00  閿熸枻鎷烽敓鎺ョ鎷� 鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600663,//12.00  闄嗛敓鏂ゆ嫹閿熸枻鎷� 鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600736,//13.00  閿熸枻鎷烽敓鎹烽潻鎷烽敓鏂ゆ嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600848,//14.00  閿熻緝鐚存嫹閿熷姭闈╂嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    600895//1.00 閿熻剼鏂ゆ嫹閿熺鍖℃嫹  鍥敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    
    ));
    
    List<Integer> listCode_109_fcfw = new ArrayList<Integer>(Arrays.asList(
        9000005,//2.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷锋簮  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
        9000056,//3.00  閿熸枻鎷峰涵閿熸枻鎷烽敓鏂ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
        9000505,//4.00  *ST閿熶粙姹� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
        9000861,//5.00  閿熸枻鎷峰嵃閿熺即鍑ゆ嫹  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
        9002285,//6.00  閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
        9002818//7.00  閿熸枻鎷锋．閿熸枻鎷� 閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
    ));
    
    
    List<Integer> listAll = new ArrayList<Integer>();

    
    

    //閿熸枻鎷峰叏閿熸枻鎷�
    if(flag.equals("1")){
      listAll.addAll(listCode_1_hq); listAll.addAll(listCode_2_zz); listAll.addAll(listCode_3_kwzp); listAll.addAll(listCode_4_ryhg);
      listAll.addAll(listCode_5_jydq); listAll.addAll(listCode_6_ylbj);listAll.addAll(listCode_7_jjyp);listAll.addAll(listCode_8_smdl);
      listAll.addAll(listCode_9_ggbz); listAll.addAll(listCode_10_wjxx); listAll.addAll(listCode_11_jdcy);
      listAll.addAll(listCode_12_hk);
      listAll.addAll(listCode_13_cb);
      listAll.addAll(listCode_14_yssb);
      listAll.addAll(listCode_15_dqsb);
      listAll.addAll(listCode_16_gcjx);
      listAll.addAll(listCode_17_dqyb);
      listAll.addAll(listCode_18_dxyy);
      listAll.addAll(listCode_19_ggjt);
      listAll.addAll(listCode_20_sw);
      listAll.addAll(listCode_21_gsgr);
      listAll.addAll(listCode_22_hjbh);
      listAll.addAll(listCode_23_ccwl);
      listAll.addAll(listCode_24_yh);
      listAll.addAll(listCode_25_zq);
      listAll.addAll(listCode_26_bx);
      listAll.addAll(listCode_27_dyjr);
      listAll.addAll(listCode_28_dlsb);
      listAll.addAll(listCode_29_txsb);
      listAll.addAll(listCode_30_bdt);
      listAll.addAll(listCode_31_yqj);
      listAll.addAll(listCode_32_rjfw);
      listAll.addAll(listCode_33_hlw);
      listAll.addAll(listCode_34_zhl);
      listAll.addAll(listCode_35_mtkc);
      listAll.addAll(listCode_36_jtjg);
      listAll.addAll(listCode_37_slfd);
      listAll.addAll(listCode_38_hldf);
      listAll.addAll(listCode_39_xxdl);
      listAll.addAll(listCode_40_sykc);
      listAll.addAll(listCode_41_syjg);
      listAll.addAll(listCode_42_symy);
      listAll.addAll(listCode_43_pg);
      listAll.addAll(listCode_44_tzg);
      listAll.addAll(listCode_45_gjg);
      listAll.addAll(listCode_46_t);
      listAll.addAll(listCode_47_l);
      listAll.addAll(listCode_47_yx);
      listAll.addAll(listCode_48_hj);
      listAll.addAll(listCode_49_xjs);
      listAll.addAll(listCode_50_hgyl);
      listAll.addAll(listCode_51_lyhf);
      listAll.addAll(listCode_52_sl);
      listAll.addAll(listCode_53_xj);
      listAll.addAll(listCode_54_yltl);
      listAll.addAll(listCode_55_tc);
      listAll.addAll(listCode_56_sn);
      listAll.addAll(listCode_57_bl);
      listAll.addAll(listCode_58_qtjc);
      listAll.addAll(listCode_59_zzy);
      listAll.addAll(listCode_60_yy);
      listAll.addAll(listCode_61_ly);
      listAll.addAll(listCode_62_sl);
      listAll.addAll(listCode_63_lyzh);
      listAll.addAll(listCode_64_fz);
      listAll.addAll(listCode_65_fs);
      listAll.addAll(listCode_66_rzp);
      listAll.addAll(listCode_67_ryl);
      listAll.addAll(listCode_68_sp);
      listAll.addAll(listCode_69_bj);
      listAll.addAll(listCode_70_pj);
      listAll.addAll(listCode_71_hhyj);
      listAll.addAll(listCode_72_qczc);
      listAll.addAll(listCode_73_qcpj);
      listAll.addAll(listCode_74_qcfw);
      listAll.addAll(listCode_75_mtc);
      listAll.addAll(listCode_76_hxzy);
      listAll.addAll(listCode_77_swzy);
      listAll.addAll(listCode_78_zcy);
      listAll.addAll(listCode_79_bh);
      listAll.addAll(listCode_80_csls);
      listAll.addAll(listCode_81_dqls);
      listAll.addAll(listCode_82_yysy);
      listAll.addAll(listCode_83_qtsy);
      listAll.addAll(listCode_84_spc);
      listAll.addAll(listCode_85_pfy);
      listAll.addAll(listCode_86_cpy);
      listAll.addAll(listCode_87_ysyx);
      listAll.addAll(listCode_88_lyfw);
      listAll.addAll(listCode_89_lyjd);
      listAll.addAll(listCode_90_jczz);
      listAll.addAll(listCode_91_jxjj);
      listAll.addAll(listCode_92_hgjx);
      listAll.addAll(listCode_93_qgjx);
      listAll.addAll(listCode_94_fzjx);
      listAll.addAll(listCode_95_lyjx);
      listAll.addAll(listCode_96_zyjx);
      listAll.addAll(listCode_97_tl);
      listAll.addAll(listCode_98_sy);
      listAll.addAll(listCode_99_ky);
      listAll.addAll(listCode_100_gl);
      listAll.addAll(listCode_101_lq);
      listAll.addAll(listCode_102_jc);listAll.addAll(listCode_103_gk);listAll.addAll(listCode_104_jzsg);listAll.addAll(listCode_105_jzzs);
      listAll.addAll(listCode_106_qgdc);listAll.addAll(listCode_107_qydc);listAll.addAll(listCode_108_yqkf); listAll.addAll(listCode_109_fcfw);
      
    }else if(flag.equals("2")){
      //閿熸枻鎷烽敓鏂ゆ嫹閿熸彮锟�
      listAll.addAll(listCode_109_fcfw);
      
      
    }else if(flag.equals("3")){
      //閿熸枻鎷锋煇閿熸枻鎷烽敓鏂ゆ嫹绁� 
      listAll.add(stockNum);  // 9000001   瑕侀敓鏂ゆ嫹9閿熸枻鎷峰墠閿熸枻鎷�
      
    }
    
    
    

    return listAll;
    
    
  }
  
  
  //閿熸枻鎷烽敓鏂ゆ嫹  閿熸鍑ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹   http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code=60051901&num=9&code1=sh600519&spstr=&n=1&timetip=1487063111207
}
