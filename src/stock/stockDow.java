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
  
  private static Integer stockNum = 9000001;   //0��ͷ��Ҫ��9��
  private static String flag = "1";  //1 ȫ��   2 ��ҵ  3���� 

  @SuppressWarnings("unused")
  public static void main(String [] args) throws Exception {
        //֧�ֶ��Ҳ֧��һ��
        
   
        List<String> listAllYearDates = Arrays.asList(
                                                "2016-12-31");
        
        List<String> listYearDates = Arrays.asList("2017-03-31",
                                                   "2017-06-30",
                                                   "2017-09-30",
                                                   "2017-12-31"
                                                    );
                                                    
        
        List<Integer> prepareData = stockDow.prepareData();
        
        
        
        
        List<Map<String,Object>> list��ҵ =new ArrayList<Map<String,Object>>();

        String text��ҵ = "";
        //֤ȯ֮��
        //String filePath ="http://stock.quote.stockstar.com/stockinfo_finance/profit.aspx?code=600819&dt=2009-12-31";
        //����
        String urlPath = "http://stock.quote.stockstar.com/stockinfo_finance/profit.aspx?";
        
        
        //String urlPath = "http://stock.quote.stockstar.com/stockinfo_finance/summary.aspx?code=601088&dt=2004-12-31";
        //ָ��
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
            String allPath = urlPath2.toString()+"";  //��allPathʹ���µ���ַ��ַ
            String year = listAllYearDates.get(j);
            
            
            allPath += "&dt="+year; //һ����Ʊ��Ӧÿ���12��31�վ�һ��html
            
            
            
            System.out.println("�����ַ:"+allPath);
            System.out.println("�ļ���:"+strCode+"_"+year+".html");
            String fileName = strCode+"_"+year+".html";
            File downFile = stockDow.downFile(allPath.toString(),fileName);
            
            String readFile = stockDow.readFile(downFile,"UTF-8");
            
     
            //����
            stockDow.lirun(readFile, map, fileName);
            
            //roe
            //stockDow.zb(readFile, map, fileName);
            
            //�����ת��
           // stockDow.zb2(readFile, map, fileName);
            
           
            System.out.println();
            
          }
          
          list��ҵ.add(map);
          
          String text = "";
          for (String key : map.keySet()) {
              //map.keySet()���ص�������key��ֵ
              Object value = map.get(key);//�õ�ÿ��key�����value��ֵ
              
              //System.out.println(key+"\t"+value);
              
              text += key+"\t"+value + "\r\n";
              
              //д��excel
              
          }
          
          System.out.println(text);
          	text��ҵ += text + "\r\n"+"\r\n"+"\r\n";
          
          
/*          for (String key : map.keySet()) {
            //map.keySet()���ص�������key��ֵ
            Object value = map.get(key);//�õ�ÿ��key�����value��ֵ
            
            System.out.print(key+"\t");
        }
          
          System.out.println();
          
          for (String key : map.keySet()) {
            //map.keySet()���ص�������key��ֵ
            Object value = map.get(key);//�õ�ÿ��key�����value��ֵ
            System.out.print(value.toString()+"\t"+"\t");
            
        }
          System.out.println();*/
          
          
          
          

          //���ļ�ɾ����
          
          
          
        }
        
        System.out.println("===============================================");
        System.out.println(text��ҵ);
        System.out.println("===============================================");
       // stockDow.writeFile(text��ҵ, "all"+".txt");;
        
        stockDow.Optexcel(list��ҵ);
          
    
  

 
       // return file;  
  }
  
  
  /**
   * roe
   * 
   * */
  public static void zb(String readFile,Map<String,Object> map,String fileName){
    int local = readFile.indexOf("���ʲ�������(%)</td><td>");//���ʲ�������(%)</td><td>29.76</td>
    String substring = "";
    
    
    if(local < 0){
      //System.out.println(integerCode+"_"+year+":"+"����û������" );
      //map.put(fileName, "����û������");
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
   * �����ת��
   * 
   * */
  public static void zb2(String readFile,Map<String,Object> map,String fileName){
    int local = readFile.indexOf("�����ת��(%)</td><td>");//�����ת��(%)</td><td>5.33</td>
    String substring = "";
    
    
    if(local < 0){
      //System.out.println(integerCode+"_"+year+":"+"����û������" );
      //map.put(fileName, "����û������");
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
    int local = readFile.indexOf("������(����Ԫ)</b></td><td>");
    String substring = "";
    
    
    if(local < 0){
      //System.out.println(integerCode+"_"+year+":"+"����û������" );
      //map.put(fileName, "����û������");
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
  public static void Optexcel(List<Map<String,Object>> list��ҵ) throws Exception{
	  
	  // �����µ�Excel ������  
      HSSFWorkbook workbook = new HSSFWorkbook();  

      // ��Excel�������н�һ����������Ϊȱʡֵ  
      // ��Ҫ�½�һ��Ϊ"Ч��ָ��"�Ĺ����������Ϊ��  
      // HSSFSheet sheet = workbook.createSheet("Ч��ָ��");  
      HSSFSheet sheet = workbook.createSheet();  
      
      
      // ������0��λ�ô����У���˵��У�  
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
      
      
      for(int i = 0 ; i < list��ҵ.size();i++){
    	  Map<String, Object> map = list��ҵ.get(i);
          HSSFRow ro = sheet.createRow((short) 1+i);
          int j = 0 ;
          
          
          HSSFCell ce = ro.createCell((short) 0);
          ce.setCellType(HSSFCell.CELL_TYPE_STRING);
            
          
          for (String key : map.keySet()) {
              //map.keySet()���ص�������key��ֵ
              Object value = map.get(key);//�õ�ÿ��key�����value��ֵ
              
              
              if(j == 0){
            	  ce.setCellValue(key);
              }
              

              HSSFCell ce2 = ro.createCell((short) 1+j);
              ce2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
              ce2.setCellValue(Double.parseDouble(value.toString())); 
              
              
              j++;
              //д��excel
              
          }
          
    	  
      }
      

      
      
 
      // �½�һ����ļ���  
      FileOutputStream fOut = new FileOutputStream(outputFile);  
      // ����Ӧ��Excel ����������  
      workbook.write(fOut);  
      fOut.flush();  
      // �����������ر��ļ�  
      fOut.close();  
      System.out.println("�ļ�����...");  

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
      
        String directory = "H:\\test";
    	// String directory = "C:\\Users\\kikili\\Desktop\\test";
       // String fileName = "myFile.html";
        
        
          file = new File(directory,fileName); 
      
        OutputStream oputstream = new FileOutputStream(file);  
        URL url = new URL(urlPath.toString());  
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();  
        uc.setDoInput(true);//�����Ƿ�Ҫ�� URL ���Ӷ�ȡ����,Ĭ��Ϊtrue  
        uc.connect();  
        InputStream iputstream = uc.getInputStream();  
 //       System.out.println("file size is:"+uc.getContentLength());//��ӡ�ļ�����  
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
     System.out.println("��ȡʧ�ܣ�");  
     e.printStackTrace();  
 }  
    
    return file;
  }
  
  
  
  public static List<Integer> prepareData(){
    //1.����
    List<Integer> listCode_1_hq = new ArrayList<Integer>(Arrays.asList(
                                                  9000420  ,// ���ֻ���
                                                  9000584  ,// �����ع�
                                                  9000615  ,// �����ɷ�
                                                  9000677   ,//���캣��
                                                  9000703   ,//����ʯ��
                                                  9000782   ,//����ɷ�
                                                  9000936  ,// �����ɷ�
                                                  9000949  ,// ���绯��
                                                  9000976  ,// ���͹ɷ�
                                                  9002015  ,// ϼ�ͻ���
                                                  9002064  ,// ���就��
                                                  9002080  ,// �вĿƼ�
                                                  9002172  ,// ����Ƽ�
                                                  9002206   ,//�� �� ��
                                                  9002254  ,// ̩���²�
                                                  9002427  ,// �ȷ�ɷ�
                                                  9002493   ,//��ʢʯ��
                                                  300180  ,// ���峬��
                                                  600063  ,// ��ά����
                                                  600346  ,// �����ɷ�
                                                  600527  ,// ���ϸ���
                                                  600810  ,// ����ɷ�
                                                  600889  ,// �Ͼ�����
                                                  601113  ,// �����ɷ�
                                                  601233  // ͩ���ɷ�

                                               )); 

    
    //��ֽ
    List<Integer> listCode_2_zz = new ArrayList<Integer>(Arrays.asList(
                                                 9000488 ,// ����ֽҵ
                                                9000576 ,// �㶫�ʻ�
                                                9000815 ,// ������
                                                9000833 ,// ���ǹɷ�
                                                9002012 ,// �����ɷ�
                                                9002067 ,// ����ֽҵ
                                                9002078 ,// ̫��ֽҵ
                                                9002235 ,// ���ݹɷ�
                                                9002303 ,// ��ӯɭ
                                                9002511 ,// ��˳����
                                                9002521 ,// ����²�
                                                9002565 ,// ˳尹ɷ�
                                                600069  ,// ����Ͷ��
                                                600103  ,// ��ɽֽҵ
                                                600235  ,// �����ֽ
                                                600308  ,// ��̩�ɷ�
                                                600356  ,// ���ֽҵ
                                                600433  ,// �ں�����
                                                600567  ,// ɽӥֽҵ
                                                600793  ,// �˱�ֽҵ
                                                600963  ,// ������ֽ
                                                600966  ,// ����ֽҵ
                                                603165  // ���ɻ���
                                  ));
    //������Ʒ
    List<Integer> listCode_3_kwzp = new ArrayList<Integer>(Arrays.asList(
                                                  9000511,//1.00 *STϩ̼
                                                  9000519,//2.00 ���Ϻ��
                                                  9000795,//3.00 Ӣ�廪
                                                  9002088,//4.00 ³������
                                                  9002297,//5.00 �����²�
                                                  300064,//6.00 ԥ���ʯ
                                                  300073,//7.00 �����Ƽ�
                                                  300179,//8.00 �ķ���
                                                  600172,//9.00 �ƺ�����
                                                  600516,//10.00  ����̿��
                                                  600783,//11.00  ³�Ŵ�Ͷ
                                                  603663,//12.00  �����²�
                                                  603688//13.00  ʯӢ�ɷ�
    
    ));
    
    //���û���
    List<Integer> listCode_4_ryhg = new ArrayList<Integer>(Arrays.asList(
                                                9000523,//1.00  ��������
                                                9000737,//2.00  �Ϸ绯��
                                                9002094,//3.00  �ൺ����
                                                9002637,//4.00  ����Ƽ�
                                                600249,//5.00 ������
                                                600315//6.00 �Ϻ��һ�
    
    
    ));
    
    
    List<Integer> listCode_5_jydq = new ArrayList<Integer>(Arrays.asList(
                                              9000016,//1.00  ��ѣ�  ���õ���
                                              9000100,//2.00  TCL ����  ���õ���
                                              9000333,//3.00  ���ļ���  ���õ���
                                              9000418,//4.00  С����  ���õ���
                                              9000521,//5.00  �������  ���õ���
                                              9000533,//6.00  �� �� �� ���õ���
                                              9000541,//7.00  ��ɽ����  ���õ���
                                              9000651,//8.00  ��������  ���õ���
                                              9000921,//9.00  ���ſ���  ���õ���
                                              9002005,//10.00 �º����  ���õ���
                                              9002032,//11.00 �� �� �� ���õ���
                                              9002035,//12.00 ���۹ɷ�  ���õ���
                                              9002076,//13.00 ѩ �� �� ���õ���
                                              9002242,//14.00 �����ɷ�  ���õ���
                                              9002290,//15.00 ��ʢ�²�  ���õ���
                                              9002403,//16.00 ���˴� ���õ���
                                              9002429,//17.00 �׳۹ɷ�  ���õ���
                                              9002473,//18.00 ʥ���� ���õ���
                                              9002508,//19.00 �ϰ����  ���õ���
                                              9002543,//20.00 ��͵���  ���õ���
                                              9002668,//21.00 �������  ���õ���
                                              9002677,//22.00 �㽭����  ���õ���
                                              9002681,//23.00 �ܴ�Ƽ�  ���õ���
                                              9002705,//24.00 �±��ɷ�  ���õ���
                                              9002723,//25.00 ������ ���õ���
                                              9002759,//26.00 ��ʹɷ�  ���õ���
                                              600060,//27.00  ���ŵ���  ���õ���
                                              600261,//28.00  ��������  ���õ���
                                              600336,//29.00  �Ŀ��� ���õ���
                                              600690,//30.00  �ൺ����  ���õ���
                                              600839,//31.00  �Ĵ�����  ���õ���
                                              600854,//32.00  �����ɷ�  ���õ���
                                              600870,//33.00  �û�����  ���õ���
                                              600983,//34.00  �ݶ��� ���õ���
                                              603366,//35.00  �ճ�����  ���õ���
                                              603519,//36.00  ���Թɷ�  ���õ���
                                              603868//37.00  �ɿƵ���  ���õ���

    
    ));
    
    List<Integer> listCode_6_ylbj = new ArrayList<Integer>(Arrays.asList(
                                                9000150,//1.00  �˻�����  ҽ�Ʊ���
                                                9000502,//2.00  �̾��ع�  ҽ�Ʊ���
                                                9000503,//3.00  ����ع�  ҽ�Ʊ���
                                                9002022,//4.00  �ƻ�����  ҽ�Ʊ���
                                                9002044,//5.00  ���꽡��  ҽ�Ʊ���
                                                9002162,//6.00  ���Ľ���  ҽ�Ʊ���
                                                9002223,//7.00  ��Ծҽ��  ҽ�Ʊ���
                                                9002382,//8.00  ����ҽ��  ҽ�Ʊ���
                                                9002432,//9.00  �Ű�ҽ��  ҽ�Ʊ���
                                                9002551,//10.00 ����ҽ��  ҽ�Ʊ���
                                                9002614,//11.00 �ɷ��� ҽ�Ʊ���
                                                300003,//12.00  ����ҽ��  ҽ�Ʊ���
                                                300015,//13.00  �����ۿ�  ҽ�Ʊ���
                                                300030,//14.00  ����ҽ��  ҽ�Ʊ���
                                                300061,//15.00  ������ ҽ�Ʊ���
                                                300171,//16.00  ������ ҽ�Ʊ���
                                                300206,//17.00  �������  ҽ�Ʊ���
                                                300216,//18.00  ǧɽҩ��  ҽ�Ʊ���
                                                300238,//19.00  �������  ҽ�Ʊ���
                                                300244,//20.00  �ϰ����  ҽ�Ʊ���
                                                300246,//21.00  ������ ҽ�Ʊ���
                                                300247,//22.00  �ֽ𽡿�  ҽ�Ʊ���
                                                300273,//23.00  �ͼѹɷ�  ҽ�Ʊ���
                                                300298,//24.00  ��ŵ����  ҽ�Ʊ���
                                                300314,//25.00  ��άҽ��  ҽ�Ʊ���
                                                300318,//26.00  ���ʹ���  ҽ�Ʊ���
                                                300326,//27.00  ����̩ ҽ�Ʊ���
                                                300347,//28.00  ̩��ҽҩ  ҽ�Ʊ���
                                                300358,//29.00  ����Ƽ�  ҽ�Ʊ���
                                                300396,//30.00  ����ҽ��  ҽ�Ʊ���
                                                300404,//31.00  ����ҽҩ  ҽ�Ʊ���
                                                300412,//32.00  ���ϿƼ�  ҽ�Ʊ���
                                                300439,//33.00  ��������  ҽ�Ʊ���
                                                300453,//34.00  ����ҽ��  ҽ�Ʊ���
                                                300463,//35.00  ��������  ҽ�Ʊ���
                                                300529,//36.00  ��������  ҽ�Ʊ���
                                                300562,//37.00  ����ҽ��  ҽ�Ʊ���
                                                600055,//38.00  ��ҽ��  ҽ�Ʊ���
                                                600381,//39.00  �ຣ����  ҽ�Ʊ���
                                                600530,//40.00  ������  ҽ�Ʊ���
                                                600587,//41.00  �»�ҽ��  ҽ�Ʊ���
                                                600763,//42.00  ͨ��ҽ��  ҽ�Ʊ���
                                                600767,//43.00  ��ʢҽ��  ҽ�Ʊ���
                                                603309,//44.00  ά��ҽ��  ҽ�Ʊ���
                                                603658,//45.00  ��ͼ����  ҽ�Ʊ���
                                                603987,//46.00  ������ ҽ�Ʊ���
                                                603579//47.00  ��̩����  ҽ�Ʊ���

    
    ));
    
    
    List<Integer> listCode_7_jjyp = new ArrayList<Integer>(Arrays.asList(
                                                9000910,//1.00  ����ʥ��  �Ҿ���Ʒ
                                                9002084,//2.00  ��Ÿ��ԡ  �Ҿ���Ʒ
                                                9002120,//3.00  �º��ɷ�  �Ҿ���Ʒ
                                                9002240,//4.00  �����ɷ�  �Ҿ���Ʒ
                                                9002259,//5.00  ������ҵ  �Ҿ���Ʒ
                                                9002489,//6.00  �㽭��ǿ  �Ҿ���Ʒ
                                                9002572,//7.00  ������ �Ҿ���Ʒ
                                                9002615,//8.00  ����˹ �Ҿ���Ʒ
                                                9002631,//9.00  �¶�δ��  �Ҿ���Ʒ
                                                9002718,//10.00 �Ѱ����  �Ҿ���Ʒ
                                                9002751,//11.00 ����չʾ  �Ҿ���Ʒ
                                                9002757,//12.00 ����װ��  �Ҿ���Ʒ
                                                9002790,//13.00 ����� �Ҿ���Ʒ
                                                9002798,//14.00 �������  �Ҿ���Ʒ
                                                600337,//15.00  ���˼Ҿ�  �Ҿ���Ʒ
                                                600978,//16.00  �˻�����  �Ҿ���Ʒ
                                                603008,//17.00  ϲ���� �Ҿ���Ʒ
                                                603313,//18.00  �㿵�Ҿ�  �Ҿ���Ʒ
                                                603389,//19.00  ����Ҿ�  �Ҿ���Ʒ
                                                603600,//20.00  ���չɷ�  �Ҿ���Ʒ
                                                603816,//21.00  �˼ҼҾ�  �Ҿ���Ʒ
                                                603818,//22.00  �����Ҿ�  �Ҿ���Ʒ
                                                603898//23.00  ������ �Ҿ���Ʒ

    
    ));
    
    
    List<Integer> listCode_8_smdl = new ArrayList<Integer>(Arrays.asList(
                                                9000062,//1.00  ���ڻ�ǿ  ��ó����
                                                9000151,//2.00  �гɹɷ�  ��ó����
                                                9000408,//3.00  *ST��Դ ��ó����
                                                9000626,//4.00  Զ��ع�  ��ó����
                                                9002091,//5.00  ���չ�̩  ��ó����
                                                600058,//6.00 ���չ  ��ó����
                                                600120,//7.00 �㽭����  ��ó����
                                                600128,//8.00 ��ҵ�ɷ�  ��ó����
                                                600153,//9.00 �����ɷ�  ��ó����
                                                600241,//10.00  ʱ�����  ��ó����
                                                600247,//11.00  ST�ɳ�  ��ó����
                                                600250,//12.00  �ϷĹɷ�  ��ó����
                                                600278,//13.00  ������ҵ  ��ó����
                                                600287,//14.00  ����˴��  ��ó����
                                                600382,//15.00  �㶫����  ��ó����
                                                600500,//16.00  �л�����  ��ó����
                                                600605,//17.00  ��ͨ��Դ  ��ó����
                                                600704,//18.00  ����д�  ��ó����
                                                600735,//19.00  �»��� ��ó����
                                                600739,//20.00  �����ɴ�  ��ó����
                                                600755,//21.00  ���Ź�ó  ��ó����
                                                600822,//22.00  �Ϻ���ó  ��ó����
                                                600826,//23.00  �����ɷ�  ��ó����
                                                600981//24.00  ��輯��  ��ó����
    
    ));
    
    List<Integer> listCode_9_ggbz = new ArrayList<Integer>(Arrays.asList(
                                                  9000038,//1.00  ���ͨ ����װ
                                                  9000607,//2.00  ��ý�ع�  ����װ
                                                  9000659,//3.00  �麣�и�  ����װ
                                                  9000812,//4.00  ������Ҷ  ����װ
                                                  9002117,//5.00  ���۹ɷ�  ����װ
                                                  9002188,//6.00  ��ʿ����  ����װ
                                                  9002191,//7.00  ���ιɷ�  ����װ
                                                  9002228,//8.00  ���˰�װ  ����װ
                                                  9002229,//9.00  �販�ɷ�  ����װ
                                                  9002400,//10.00 ʡ��ɷ�  ����װ
                                                  9002599,//11.00 ʢͨ�ɷ�  ����װ
                                                  9002701,//12.00 ����� ����װ
                                                  9002712,//13.00 ˼����ý  ����װ
                                                  9002752,//14.00 �N�˹ɷ�  ����װ
                                                  9002787,//15.00 ��Դ��װ  ����װ
                                                  9002799,//16.00 ����ӡ��  ����װ
                                                  9002803,//17.00 ����ɷ�  ����װ
                                                  9002812,//18.00 ���¹ɷ�  ����װ
                                                  9002831,//19.00 ԣͬ�Ƽ�  ����װ
                                                  9002836,//20.00 �º��� ����װ
                                                  300057,//21.00  ��˳�ɷ�  ����װ
                                                  300058,//22.00  ��ɫ���  ����װ
                                                  300071,//23.00  �������  ����װ
                                                  300501,//24.00  ��˳�²�  ����װ
                                                  600210,//25.00  �Ͻ���ҵ  ����װ
                                                  600836,//26.00  ����ʵҵ  ����װ
                                                  600880,//27.00  ���𴫲�  ����װ
                                                  601515,//28.00  ����ɷ�  ����װ
                                                  601968,//29.00  ���ְ�װ  ����װ
                                                  603022,//30.00  ��ͨ�� ����װ
                                                  603058,//31.00  �����ɷ�  ����װ
                                                  603729//32.00  ���Ϲɷ�  ����װ
    
    ));
    
    
    List<Integer> listCode_10_wjxx = new ArrayList<Integer>(Arrays.asList(
                                        9000017,//1.00  ���л�A  �Ľ�����
                                        9000526,//2.00  �Ϲ�ѧ��  �Ľ�����
                                        9000558,//3.00  ��������  �Ľ�����
                                        9002103,//4.00  �㲩�ɷ�  �Ľ�����
                                        9002105,//5.00  ��¡����  �Ľ�����
                                        9002301,//6.00  ���ļ���  �Ľ�����
                                        9002348,//7.00  ���ֹɷ�  �Ľ�����
                                        9002575,//8.00  Ⱥ�����  �Ľ�����
                                        9002605,//9.00  Ҧ���˿�  �Ľ�����
                                        9002678,//10.00 �齭����  �Ľ�����
                                        300043,//11.00  �ǻ�����  �Ľ�����
                                        300329,//12.00  ���׸���  �Ľ�����
                                        300359,//13.00  ȫͨ����  �Ľ�����
                                        600158,//14.00  �����ҵ  �Ľ�����
                                        600234,//15.00  *STɽˮ �Ľ�����
                                        600661,//16.00  ������ �Ľ�����
                                        600679,//17.00  �Ϻ����  �Ľ�����
                                        600818,//18.00  ��·�ɷ�  �Ľ�����
                                        603398,//19.00  �����  �Ľ�����
                                        603899//20.00  �����ľ�  �Ľ�����
    ));
    
    List<Integer> listCode_11_jdcy = new ArrayList<Integer>(Arrays.asList(

                                      9000007,//1.00  ȫ�º� �Ƶ����
                                      9000428,//2.00  ����Ƶ�  �Ƶ����
                                      9000524,//3.00  ���Ͽع�  �Ƶ����
                                      9000721,//4.00  ������ʳ  �Ƶ����
                                      9002186,//5.00  ȫ �� �� �Ƶ����
                                      9002306,//6.00  �п�����  �Ƶ����
                                      9000033,//7.00  *ST�¶� �Ƶ����
                                      600258,//8.00 ���þƵ�  �Ƶ����
                                      600640,//9.00 �Űٿع�  �Ƶ����
                                      600754,//10.00  �����ɷ�  �Ƶ����
                                      601007//11.00  ���극��  �Ƶ����
    ));
    
    List<Integer> listCode_12_hk = new ArrayList<Integer>(Arrays.asList(
                                    9000738,//1.00  �к�����  ����
                                    9000768,//2.00  �к��ɻ�  ����
                                    9000901,//3.00  ����Ƽ�  ����
                                    9002013,//4.00  �к�����  ����
                                    9002023,//5.00  ���ظ���  ����
                                    9002111,//6.00  ������̩  ����
                                    9002260,//7.00  �°�ͨ��  ����
                                    300424,//8.00 ���¿Ƽ�  ����
                                    300581,//9.00 ���غ���  ����
                                    600038,//10.00  ��ֱ�ɷ�  ����
                                    600118,//11.00  �й�����  ����
                                    600316,//12.00  �鶼����  ����
                                    600343,//13.00  ���춯��  ����
                                    600372,//14.00  �к�����  ����
                                    600391,//15.00  �ɷ��Ƽ�  ����
                                    600862,//16.00  �к��߿�  ����
                                    600879,//17.00  �������  ����
                                    600893//18.00  �к�����  ����
    ));
    
    List<Integer> listCode_13_cb = new ArrayList<Integer>(Arrays.asList(
                                  9002608,//1.00  *ST˴�� ����
                                  300008,//2.00 �캣����  ����
                                  300123,//3.00 ̫���� ����
                                  300589,//4.00 ������ͧ  ����
                                  600072,//5.00 �ֹ�����  ����
                                  600150,//6.00 �й�����  ����
                                  600685,//7.00 �д�����  ����
                                  601890,//8.00 ����ê��  ����
                                  601989//9.00 �й��ع�  ����
    
    ));
    
    List<Integer> listCode_14_yssb = new ArrayList<Integer>(Arrays.asList(
                                9000008,//1.00  ���ݸ���  �����豸
                                9002367,//2.00  ��������  �����豸
                                9002689,//3.00  Զ������  �����豸
                                300011,//4.00 ��������  �����豸
                                300455,//5.00 ���غ���  �����豸
                                600495,//6.00 ��������  �����豸
                                600835,//7.00 �Ϻ�����  �����豸
                                600894,//8.00 ���չɷ�  �����豸
                                600967,//9.00 ������ҵ  �����豸
                                601313,//10.00  ���ϼν�  �����豸
                                601766,//11.00  �й��г�  �����豸
                                603111,//12.00  �������  �����豸
                                603611//13.00  ŵ���ɷ�  �����豸
    
    ));
    
    
    List<Integer> listCode_15_dqsb = new ArrayList<Integer>(Arrays.asList(
                                      9000049,//1.00  �������  �����豸
                                      9000400,//2.00  ��̵���  �����豸
                                      9000585,//3.00  ��������  �����豸
                                      9000682,//4.00  ��������  �����豸
                                      9000922,//5.00  �ѵ�ɷ�  �����豸
                                      9000967,//6.00  ӯ�廷��  �����豸
                                      9002028,//7.00  ˼Դ����  �����豸
                                      9002074,//8.00  �����߿�  �����豸
                                      9002112,//9.00  ����Ƽ�  �����豸
                                      9002130,//10.00 �ֶ��˲�  �����豸
                                      9002164,//11.00 ��������  �����豸
                                      9002168,//12.00 ���ڻݳ�  �����豸
                                      9002169,//13.00 �ǹ����  �����豸
                                      9002176,//14.00 ���ص��  �����豸
                                      9002202,//15.00 ���Ƽ�  �����豸
                                      9002212,//16.00 ����ɷ�  �����豸
                                      9002227,//17.00 �� �� Ѹ �����豸
                                      9002249,//18.00 ������  �����豸
                                      9002266,//19.00 �㸻�ع�  �����豸
                                      9002270,//20.00 ����װ��  �����豸
                                      9002276,//21.00 ����ɷ�  �����豸
                                      9002300,//22.00 ̫������  �����豸
                                      9002309,//23.00 �����Ƽ�  �����豸
                                      9002334,//24.00 Ӣ���� �����豸
                                      9002335,//25.00 �ƻ���ʢ  �����豸
                                      9002339,//26.00 ���ɵ���  �����豸
                                      9002346,//27.00 ���йɷ�  �����豸
                                      9002350,//28.00 ��������  �����豸
                                      9002358,//29.00 ɭԴ����  �����豸
                                      9002364,//30.00 �к����  �����豸
                                      9002380,//31.00 ��Զ�ɷ�  �����豸
                                      9002451,//32.00 Ħ������  �����豸
                                      9002452,//33.00 ���߼���  �����豸
                                      9002471,//34.00 �г��ع�  �����豸
                                      9002498,//35.00 ���¹ɷ�  �����豸
                                      9002531,//36.00 ��˳����  �����豸
                                      9002533,//37.00 �𱭵繤  �����豸
                                      9002546,//38.00 ��������  �����豸
                                      9002560,//39.00 ͨ��ɷ�  �����豸
                                      9002576,//40.00 ͨ�ﶯ��  �����豸
                                      9002580,//41.00 ʥ���ɷ�  �����豸
                                      9002606,//42.00 �������  �����豸
                                      9002610,//43.00 �����Ƽ�  �����豸
                                      9002617,//44.00 ¶Ц�Ƽ�  �����豸
                                      9002622,//45.00 ���ڼ���  �����豸
                                      9002647,//46.00 ���ڹɷ�  �����豸
                                      9002660,//47.00 ï˶��Դ  �����豸
                                      9002665,//48.00 �׺�����  �����豸
                                      9002684,//49.00 ��ʨ�Ƽ�  �����豸
                                      9002692,//50.00 Զ�̵���  �����豸
                                      9002706,//51.00 ���ŵ���  �����豸
                                      9002730,//52.00 ���Ƽ�  �����豸
                                      9002733,//53.00 ��躹ɷ�  �����豸
                                      9002782,//54.00 ������ �����豸
                                      9002801,//55.00 ΢��ɷ�  �����豸
                                      300001,//56.00  ����� �����豸
                                      300004,//57.00  �Ϸ�ɷ�  �����豸
                                      300018,//58.00  ��Ԫ�ɷ�  �����豸
                                      300040,//59.00  ���޵���  �����豸
                                      300048,//60.00  �Ͽ�����  �����豸
                                      300062,//61.00  ���ܵ���  �����豸
                                      300068,//62.00  �϶���Դ  �����豸
                                      300069,//63.00  ��������  �����豸
                                      300120,//64.00  ��γ���  �����豸
                                      300125,//65.00  ������ �����豸
                                      300129,//66.00  ̩ʤ����  �����豸
                                      300140,//67.00  ��Դװ��  �����豸
                                      300141,//68.00  ��˳����  �����豸
                                      300153,//69.00  ��̩��Դ  �����豸
                                      300208,//70.00  ��˳�ڕN  �����豸
                                      300215,//71.00  ���Ժ �����豸
                                      300222,//72.00  �ƴ�����  �����豸
                                      300252,//73.00  ����ŵ �����豸
                                      300265,//74.00  ͨ������  �����豸
                                      300274,//75.00  �����Դ  �����豸
                                      300283,//76.00  ���ݺ��  �����豸
                                      300341,//77.00  ��ϵ���  �����豸
                                      300356,//78.00  ��һ�Ƽ�  �����豸
                                      300376,//79.00  ������ �����豸
                                      300403,//80.00  �ض�����  �����豸
                                      300407,//81.00  ��������  �����豸
                                      300423,//82.00  ³��ͨ �����豸
                                      300427,//83.00  �������  �����豸
                                      300438,//84.00  ������Դ  �����豸
                                      300444,//85.00  ˫�ܵ���  �����豸
                                      300447,//86.00  ȫ�Źɷ�  �����豸
                                      300477,//87.00  ���ݿƼ�  �����豸
                                      300484,//88.00  ��������  �����豸
                                      300490,//89.00  ���ԿƼ�  �����豸
                                      300491,//90.00  ͨ�ϿƼ�  �����豸
                                      300510,//91.00  ��ڵ���  �����豸
                                      300543,//92.00  �ʿ�����  �����豸
                                      300372,//93.00  ��̩����  �����豸
                                      300593,//94.00  ������ �����豸
                                      600089,//95.00  �ر�繤  �����豸
                                      600110,//96.00  ŵ�¹ɷ�  �����豸
                                      600112,//97.00  ��ɿع�  �����豸
                                      600192,//98.00  ���ǵ繤  �����豸
                                      600202,//99.00  ���յ� �����豸
                                      600268,//100.00 ��������  �����豸
                                      600290,//101.00 ���ǵ���  �����豸
                                      600312,//102.00 ƽ�ߵ���  �����豸
                                      600379,//103.00 ����ɷ�  �����豸
                                      600405,//104.00 ����Դ �����豸
                                      600416,//105.00 ���ɷ�  �����豸
                                      600468,//106.00 ��������  �����豸
                                      600482,//107.00 �й�����  �����豸
                                      600517,//108.00 ���ŵ���  �����豸
                                      600525,//109.00 ��԰����  �����豸
                                      600550,//110.00 �������  �����豸
                                      600560,//111.00 ��������  �����豸
                                      600577,//112.00 ����ɷ�  �����豸
                                      600580,//113.00 ��������  �����豸
                                      600590,//114.00 ̩���Ƽ�  �����豸
                                      600847,//115.00 ����ɷ�  �����豸
                                      600869,//116.00 �ǻ���Դ  �����豸
                                      600875,//117.00 ��������  �����豸
                                      600885,//118.00 �귢�ɷ�  �����豸
                                      600973,//119.00 ��ʤ�ɷ�  �����豸
                                      601126,//120.00 �ķ��ɷ�  �����豸
                                      601179,//121.00 �й�����  �����豸
                                      601311,//122.00 ���չɷ�  �����豸
                                      601558,//123.00 ������  �����豸
                                      601616,//124.00 ������  �����豸
                                      601700,//125.00 �緶�ɷ�  �����豸
                                      601727,//126.00 �Ϻ�����  �����豸
                                      601877,//127.00 ��̩����  �����豸
                                      603015,//128.00 ��Ѷ�Ƽ�  �����豸
                                      603016,//129.00 �º�̩ �����豸
                                      603333,//130.00 ���ǵ���  �����豸
                                      603355,//131.00 ���˵���  �����豸
                                      603416,//132.00 �Žݵ���  �����豸
                                      603515,//133.00 ŷ������  �����豸
                                      603606,//134.00 ��������  �����豸
                                      603618,//135.00 ����ɷ�  �����豸
                                      603703,//136.00 ʢ��Ƽ�  �����豸
                                      603819,//137.00 �����ɷ�  �����豸
                                      603861,//138.00 ���Ƶ���  �����豸
                                      603988,//139.00 �е���  �����豸
                                      603628//140.00 ��Դ�ɷ�  �����豸
                                      
    ));
    
    
    List<Integer> listCode_16_gcjx = new ArrayList<Integer>(Arrays.asList(
                                        9000157,//1.00  �����ؿ�  ���̻�е
                                        9000425,//2.00  �칤��е  ���̻�е
                                        9000528,//3.00  �� �� ���̻�е
                                        9000680,//4.00  ɽ�ƹɷ�  ���̻�е
                                        9000811,//5.00  ��̨����  ���̻�е
                                        9000923,//6.00  �ӱ�����  ���̻�е
                                        9002009,//7.00  ����ɷ�  ���̻�е
                                        9002011,//8.00  �ܰ�����  ���̻�е
                                        9002097,//9.00  ɽ������  ���̻�е
                                        9002158,//10.00 ���Ӿ���  ���̻�е
                                        9002459,//11.00 ��ҵͨ��  ���̻�е
                                        9002483,//12.00 ���ɷ�  ���̻�е
                                        9002523,//13.00 ��������  ���̻�е
                                        9002526,//14.00 ɽ�����  ���̻�е
                                        9002535,//15.00 �����ػ�  ���̻�е
                                        9002667,//16.00 ���عɷ�  ���̻�е
                                        9002685,//17.00 �����ػ�  ���̻�е
                                        300035,//18.00  �пƵ���  ���̻�е
                                        300103,//19.00  ���·��  ���̻�е
                                        300185,//20.00  ͨԣ�ع�  ���̻�е
                                        300308,//21.00  �м�װ��  ���̻�е
                                        600031,//22.00  ��һ�ع�  ���̻�е
                                        600169,//23.00  ̫ԭ�ع�  ���̻�е
                                        600320,//24.00  ���ع�  ���̻�е
                                        600582,//25.00  ��ؿƼ�  ���̻�е
                                        600761,//26.00  ���պ���  ���̻�е
                                        600815,//27.00  �ù��ɷ�  ���̻�е
                                        600984,//28.00  �����е  ���̻�е
                                        601100,//29.00  ����Һѹ  ���̻�е
                                        601106,//30.00  �й�һ��  ���̻�е
                                        601717,//31.00  ֣ú�� ���̻�е
                                        603218,//32.00  ���¹ɷ�  ���̻�е
                                        600710//33.00  *ST���� ���̻�е
    ));
    
    
    List<Integer> listCode_17_dqyb = new ArrayList<Integer>(Arrays.asList(
                                        9000988,//1.00  �����Ƽ�  �����Ǳ�
                                        9002008,//2.00  ���弤��  �����Ǳ�
                                        9002058,//3.00  �� �� ̩ �����Ǳ�
                                        9002121,//4.00  ��½����  �����Ǳ�
                                        9002175,//5.00  ��������  �����Ǳ�
                                        9002197,//6.00  ֤ͨ����  �����Ǳ�
                                        9002214,//7.00  �����Ƽ�  �����Ǳ�
                                        9002236,//8.00  �󻪹ɷ�  �����Ǳ�
                                        9002338,//9.00  ���չ��  �����Ǳ�
                                        9002414,//10.00 �ߵº���  �����Ǳ�
                                        9002415,//11.00 ��������  �����Ǳ�
                                        9002518,//12.00 ��ʿ�� �����Ǳ�
                                        9002527,//13.00 ��ʱ�� �����Ǳ�
                                        9002767,//14.00 �ȷ����  �����Ǳ�
                                        9002819,//15.00 �����п�  �����Ǳ�
                                        300007,//16.00  ��������  �����Ǳ�
                                        300012,//17.00  ������  �����Ǳ�
                                        300066,//18.00  �����ǻ�  �����Ǳ�
                                        300097,//19.00  ���ƹɷ�  �����Ǳ�
                                        300099,//20.00  ���忨 �����Ǳ�
                                        300105,//21.00  ��Դ����  �����Ǳ�
                                        300112,//22.00  ��Ѷ�Կ�  �����Ǳ�
                                        300124,//23.00  �㴨����  �����Ǳ�
                                        300165,//24.00  ��������  �����Ǳ�
                                        300217,//25.00  ��������  �����Ǳ�
                                        300259,//26.00  ����Ƽ�  �����Ǳ�
                                        300286,//27.00  ������ �����Ǳ�
                                        300306,//28.00  Զ�����  �����Ǳ�
                                        300338,//29.00  ��Ԫ����  �����Ǳ�
                                        300349,//30.00  �𿨹ɷ�  �����Ǳ�
                                        300354,//31.00  ��������  �����Ǳ�
                                        300360,//32.00  �滪�Ƽ�  �����Ǳ�
                                        300370,//33.00  ���ؿƼ�  �����Ǳ�
                                        300371,//34.00  ���йɷ�  �����Ǳ�
                                        300410,//35.00  ��ҵ�Ƽ�  �����Ǳ�
                                        300416,//36.00  ��������  �����Ǳ�
                                        300417,//37.00  �ϻ�����  �����Ǳ�
                                        300430,//38.00  ����ͨ �����Ǳ�
                                        300445,//39.00  ��˹�� �����Ǳ�
                                        300466,//40.00  ��Ħ����  �����Ǳ�
                                        300480,//41.00  �����Ƽ�  �����Ǳ�
                                        300515,//42.00  ���¿Ƽ�  �����Ǳ�
                                        300516,//43.00  ��֮�� �����Ǳ�
                                        300553,//44.00  ���ǹɷ�  �����Ǳ�
                                        300557,//45.00  �����  �����Ǳ�
                                        300567,//46.00  �������  �����Ǳ�
                                        300572,//47.00  �������  �����Ǳ�
                                        600366,//48.00  ��������  �����Ǳ�
                                        600651,//49.00  ��������  �����Ǳ�
                                        601222,//50.00  ������Դ  �����Ǳ�
                                        601567,//51.00  ����ҽ��  �����Ǳ�
                                        603100,//52.00  ���ǹɷ�  �����Ǳ�
                                        603556//53.00  ���˵���  �����Ǳ�
    ));
    
    List<Integer> listCode_18_dxyy = new ArrayList<Integer>(Arrays.asList(
                                      9002093,//1.00  �����Ƽ�  ������Ӫ
                                      9002467,//2.00  ������ ������Ӫ
                                      300017,//3.00 ���޿Ƽ�  ������Ӫ
                                      300383,//4.00 �⻷����  ������Ӫ
                                      600050,//5.00 �й���ͨ  ������Ӫ
                                      600804//6.00 ����ʿ ������Ӫ
    ));
    
    List<Integer> listCode_19_ggjt = new ArrayList<Integer>(Arrays.asList(
                                      600386,//1.00 ���ʹ�ý  ������ͨ
                                      600611,//2.00 ���ڽ�ͨ  ������ͨ
                                      600650,//3.00 ����Ͷ��  ������ͨ
                                      600662,//4.00 ǿ���ع�  ������ͨ
                                      600676,//5.00 ���˹ɷ�  ������ͨ
                                      600834//6.00 ��ͨ����  ������ͨ
    ));
    
    List<Integer> listCode_20_sw = new ArrayList<Integer>(Arrays.asList(
                                    9000598,//1.00  ���ػ���  ˮ��
                                    9000605,//2.00  �����ɷ�  ˮ��
                                    9000685,//3.00  ��ɽ����  ˮ��
                                    600168,//4.00 �人�ع�  ˮ��
                                    600187,//5.00 ����ˮ��  ˮ��
                                    600283,//6.00 Ǯ��ˮ��  ˮ��
                                    600323,//7.00 �������  ˮ��
                                    600461,//8.00 ���ˮҵ  ˮ��
                                    601158,//9.00 ����ˮ��  ˮ��
                                    601199,//10.00  ����ˮ��  ˮ��
                                    601368//11.00  �̳�ˮ��  ˮ��
    ));
    
    List<Integer> listCode_21_gsgr = new ArrayList<Integer>(Arrays.asList(
                                  9000407,//1.00  ʤ���ɷ�  ��������
                                  9000421,//2.00  �Ͼ�����  ��������
                                  9000593,//3.00  ��ͨȼ��  ��������
                                  9000669,//4.00  �����Դ  ��������
                                  9000692,//5.00  �����ȵ�  ��������
                                  9000695,//6.00  ������Դ  ��������
                                  9002267,//7.00  ����Ȼ��  ��������
                                  9002524,//8.00  ��������  ��������
                                  9002700,//9.00  �½���Դ  ��������
                                  300335,//10.00  ��ɭ�ɷ�  ��������
                                  600167,//11.00  �����ع�  ��������
                                  600333,//12.00  ����ȼ��  ��������
                                  600617,//13.00  ������Դ  ��������
                                  600635,//14.00  ���ڹ���  ��������
                                  600681,//15.00  �ٴ���Դ  ��������
                                  600719,//16.00  �����ȵ�  ��������
                                  600856,//17.00  ������Դ  ��������
                                  600917,//18.00  ����ȼ��  ��������
                                  600982,//19.00  �����ȵ�  ��������
                                  601139,//20.00  ����ȼ��  ��������
                                  603393,//21.00  ����Ȼ��  ��������
                                  603689//22.00  ����Ȼ��  ��������
    ));
    
    List<Integer> listCode_22_hjbh = new ArrayList<Integer>(Arrays.asList(
                                9000035,//1.00  �й����  ��������
                                9000544,//2.00  ��ԭ����  ��������
                                9000820,//3.00  �������  ��������
                                9000826,//4.00  ����ɣ��  ��������
                                9000920,//5.00  �Ϸ���ͨ  ��������
                                9002200,//6.00  ��Ͷ��̬  ��������
                                9002322,//7.00  ������  ��������
                                9002499,//8.00  ���ֻ���  ��������
                                9002549,//9.00  ��������  ��������
                                9002573,//10.00 ���»���  ��������
                                9002616,//11.00 ���༯��  ��������
                                9002658,//12.00 ѩ���� ��������
                                9002672,//13.00 ��������  ��������
                                300055,//14.00  ���� ��������
                                300056,//15.00  ��ά˿ ��������
                                300070,//16.00  ��ˮԴ ��������
                                300072,//17.00  ���ۻ���  ��������
                                300090,//18.00  ʢ�˻���  ��������
                                300137,//19.00  �Ⱥӻ���  ��������
                                300152,//20.00  ���ڻ���  ��������
                                300156,//21.00  ������  ��������
                                300172,//22.00  �е绷��  ��������
                                300187,//23.00  ���廷��  ��������
                                300190,//24.00  ά���� ��������
                                300197,//25.00  ������̬  ��������
                                300203,//26.00  �۹�Ƽ�  ��������
                                300262,//27.00  �Ͱ�ˮ��  ��������
                                300272,//28.00  ���ܻ���  ��������
                                300332,//29.00  �캾����  ��������
                                300355,//30.00  �ɲ���̬  ��������
                                300362,//31.00  ���軷��  ��������
                                300385,//32.00  ѩ�˻���  ��������
                                300388,//33.00  ��������  ��������
                                300422,//34.00  ������ ��������
                                300425,//35.00  ���ܿƼ�  ��������
                                600008,//36.00  �״��ɷ�  ��������
                                600217,//37.00  �����ʻ�  ��������
                                600292,//38.00  Զ�ﻷ��  ��������
                                600388,//39.00  ��������  ��������
                                600481,//40.00  ˫������  ��������
                                600526,//41.00  �ƴﻷ��  ��������
                                600874,//42.00  ��ҵ����  ��������
                                603126,//43.00  �вĽ���  ��������
                                603568,//44.00  ΰ������  ��������
                                603588,//45.00  ���ܻ���  ��������
                                603822//46.00  �ΰĻ���  ��������
    ));
    
    List<Integer> listCode_23_ccwl = new ArrayList<Integer>(Arrays.asList(
                                9002183,//1.00  �� �� ͨ �ִ�����
                                9002210,//2.00  �������  �ִ�����
                                9002245,//3.00  ����˳��  �ִ�����
                                9002468,//4.00  ��ͨ���  �ִ�����
                                9002492,//5.00  �������  �ִ�����
                                9002711,//6.00  ŷ������  �ִ�����
                                9002769,//7.00  ��·ͨ �ִ�����
                                9002800,//8.00  ��˳�ɷ�  �ִ�����
                                300013,//9.00 ��������  �ִ�����
                                300240,//10.00  ������ �ִ�����
                                300350,//11.00  ������ �ִ�����
                                600057,//12.00  ����ɷ�  �ִ�����
                                600119,//13.00  ����Ͷ��  �ִ�����
                                600179,//14.00  ��ͨ�ع�  �ִ�����
                                600180,//15.00  ��ïͨ �ִ�����
                                600233,//16.00  Բͨ�ٵ�  �ִ�����
                                600270,//17.00  ���˷�չ  �ִ�����
                                600787,//18.00  �д��ɷ�  �ִ�����
                                600794,//19.00  ��˰�Ƽ�  �ִ�����
                                603117,//20.00  ���ֹɷ�  �ִ�����
                                603128,//21.00  ��ó����  �ִ�����
                                603569//22.00  ��������  �ִ�����
    
    ));
    
    List<Integer> listCode_24_yh = new ArrayList<Integer>(Arrays.asList(
                                        9000001,//1.00  ƽ������  ����
                                        9002142,//2.00  ��������  ����
                                        9002807,//3.00  ��������  ����
                                        9002839,//4.00  �żҸ���  ����
                                        600000,//5.00 �ַ�����  ����
                                        600015,//6.00 ��������  ����
                                        600016,//7.00 ��������  ����
                                        600036,//8.00 ��������  ����
                                        600908,//9.00 ��������  ����
                                        600919,//10.00  ��������  ����
                                        600926,//11.00  ��������  ����
                                        601009,//12.00  �Ͼ�����  ����
                                        601128,//13.00  ��������  ����
                                        601166,//14.00  ��ҵ����  ����
                                        601169,//15.00  ��������  ����
                                        601229,//16.00  �Ϻ�����  ����
                                        601288,//17.00  ũҵ����  ����
                                        601328,//18.00  ��ͨ����  ����
                                        601398,//19.00  ��������  ����
                                        601818,//20.00  �������  ����
                                        601939,//21.00  ��������  ����
                                        601988,//22.00  �й�����  ����
                                        601997,//23.00  ��������  ����
                                        601998,//24.00  ��������  ����
                                        603323//25.00  �⽭����  ����
    ));
    List<Integer> listCode_25_zq = new ArrayList<Integer>(Arrays.asList(
                                      9000166,//1.00  �����Դ  ֤ȯ
                                      9000686,//2.00  ����֤ȯ  ֤ȯ
                                      9000728,//3.00  ��Ԫ֤ȯ  ֤ȯ
                                      9000750,//4.00  ����֤ȯ  ֤ȯ
                                      9000776,//5.00  �㷢֤ȯ  ֤ȯ
                                      9000783,//6.00  ����֤ȯ  ֤ȯ
                                      9002500,//7.00  ɽ��֤ȯ  ֤ȯ
                                      9002673,//8.00  ����֤ȯ  ֤ȯ
                                      9002736,//9.00  ����֤ȯ  ֤ȯ
                                      9002797,//10.00 ��һ��ҵ  ֤ȯ
                                      600030,//11.00  ����֤ȯ  ֤ȯ
                                      600061,//12.00  ��Ͷ����  ֤ȯ
                                      600109,//13.00  ����֤ȯ  ֤ȯ
                                      600369,//14.00  ����֤ȯ  ֤ȯ
                                      600837,//15.00  ��֤ͨȯ  ֤ȯ
                                      600909,//16.00  ����֤ȯ  ֤ȯ
                                      600958,//17.00  ����֤ȯ  ֤ȯ
                                      600999,//18.00  ����֤ȯ  ֤ȯ
                                      601099,//19.00  ̫ƽ�� ֤ȯ
                                      601198,//20.00  ����֤ȯ  ֤ȯ
                                      601211,//21.00  ��̩����  ֤ȯ
                                      601377,//22.00  ��ҵ֤ȯ  ֤ȯ
                                      601555,//23.00  ����֤ȯ  ֤ȯ
                                      601688,//24.00  ��̩֤ȯ  ֤ȯ
                                      601788,//25.00  ���֤ȯ  ֤ȯ
                                      601901,//26.00  ����֤ȯ  ֤ȯ
                                      601375,//27.00  ��ԭ֤ȯ  ֤ȯ
                                      601881//28.00  �й�����  ֤ȯ
    ));
    
    List<Integer> listCode_26_bx = new ArrayList<Integer>(Arrays.asList(
                                      9000627,//1.00  ��ï����  ����
                                      600291,//2.00 ��ˮ�ɷ�  ����
                                      601318,//3.00 �й�ƽ��  ����
                                      601336,//4.00 �»�����  ����
                                      601601,//5.00 �й�̫��  ����
                                      601628//6.00 �й�����  ����
    ));
    
    List<Integer> listCode_27_dyjr = new ArrayList<Integer>(Arrays.asList(
                                      9000415,//1.00  �������  ��Ԫ����
                                      9000416,//2.00  �����ع�  ��Ԫ����
                                      9000563,//3.00  �¹�Ͷ��  ��Ԫ����
                                      9000712,//4.00  �����ɷ�  ��Ԫ����
                                      9000987,//5.00  Խ����  ��Ԫ����
                                      9002670,//6.00  ��ʢ���  ��Ԫ����
                                      600318,//7.00 ��������  ��Ԫ����
                                      600599,//8.00 ��è���  ��Ԫ����
                                      600643,//9.00 ��������  ��Ԫ����
                                      600695,//10.00  ��ͥͶ��  ��Ԫ����
                                      600705,//11.00  �к��ʱ�  ��Ԫ����
                                      600747,//12.00  �����ع�  ��Ԫ����
                                      600816,//13.00  ��������  ��Ԫ����
                                      600830//14.00  ������ͨ  ��Ԫ����
    
    ));
    
    List<Integer> listCode_28_dlsb = new ArrayList<Integer>(Arrays.asList(
                                      9000021,//1.00  ��Ƽ� �����豸
                                      9000066,//2.00  ���ǵ���  �����豸
                                      9000748,//3.00  ������Ϣ  �����豸
                                      9000977,//4.00  �˳���Ϣ  �����豸
                                      9002152,//5.00  �����ͨ  �����豸
                                      9002177,//6.00  �����ɷ�  �����豸
                                      9002180,//7.00  ���ɿ� �����豸
                                      9002308,//8.00  �����ɷ�  �����豸
                                      9002312,//9.00  ��̩�ع�  �����豸
                                      9002351,//10.00 ������ �����豸
                                      9002362,//11.00 �����Ƽ�  �����豸
                                      9002376,//12.00 �±��� �����豸
                                      9002528,//13.00 Ӣ���� �����豸
                                      9002577,//14.00 �װؿƼ�  �����豸
                                      9002635,//15.00 ����Ƽ�  �����豸
                                      300042,//16.00  �ʿƿƼ�  �����豸
                                      300045,//17.00  ������ͨ  �����豸
                                      300076,//18.00  GQY��Ѷ �����豸
                                      300130,//19.00  �¹��� �����豸
                                      300282,//20.00  ��ڹɷ�  �����豸
                                      300367,//21.00  ��������  �����豸
                                      300390,//22.00  �컪����  �����豸
                                      300449,//23.00  ����߿�  �����豸
                                      600074,//24.00  ��ǧ�� �����豸
                                      600100,//25.00  ͬ���ɷ�  �����豸
                                      600271,//26.00  ������Ϣ  �����豸
                                      600601,//27.00  �����Ƽ�  �����豸
                                      600734,//28.00  ʵ�Ｏ��  �����豸
                                      603019,//29.00  �п����  �����豸
                                      603025,//30.00  ����Ƽ�  �����豸
                                      603996//31.00  ���¿Ƽ�  �����豸
    
    ));
    
    List<Integer> listCode_29_txsb = new ArrayList<Integer>(Arrays.asList(
                                      9000063,//1.00  ����ͨѶ  ͨ���豸
                                      9000070,//2.00  �ط���Ϣ  ͨ���豸
                                      9000547,//3.00  ���췢չ  ͨ���豸
                                      9000561,//4.00  ������  ͨ���豸
                                      9000586,//5.00  ��Դͨ��  ͨ���豸
                                      9000687,//6.00  ��Ѷ����  ͨ���豸
                                      9000801,//7.00  �Ĵ�����  ͨ���豸
                                      9000836,//8.00  ��ï�Ƽ�  ͨ���豸
                                      9000889,//9.00  ïҵͨ��  ͨ���豸
                                      9000892,//10.00 ��������  ͨ���豸
                                      9002017,//11.00 ���ź�ƽ  ͨ���豸
                                      9002052,//12.00 ͬ�޵���  ͨ���豸
                                      9002089,//13.00 �� �� �� ͨ���豸
                                      9002115,//14.00 ��άͨ��  ͨ���豸
                                      9002151,//15.00 ������ͨ  ͨ���豸
                                      9002161,//16.00 Զ �� �� ͨ���豸
                                      9002194,//17.00 �人����  ͨ���豸
                                      9002231,//18.00 ��άͨ��  ͨ���豸
                                      9002281,//19.00 ��Ѹ�Ƽ�  ͨ���豸
                                      9002296,//20.00 �ԻͿƼ�  ͨ���豸
                                      9002313,//21.00 �պ�ͨѶ  ͨ���豸
                                      9002369,//22.00 ׿��Ƽ�  ͨ���豸
                                      9002383,//23.00 ����˼׳  ͨ���豸
                                      9002384,//24.00 ��ɽ����  ͨ���豸
                                      9002396,//25.00 �������  ͨ���豸
                                      9002413,//26.00 �׿Ʒ���  ͨ���豸
                                      9002417,//27.00 ��Ԫ�� ͨ���豸
                                      9002446,//28.00 ʢ·ͨ��  ͨ���豸
                                      9002465,//29.00 ����ͨ��  ͨ���豸
                                      9002491,//30.00 ͨ������  ͨ���豸
                                      9002519,//31.00 ���ӵ���  ͨ���豸
                                      9002547,//32.00 ���˾���  ͨ���豸
                                      9002583,//33.00 ���ܴ� ͨ���豸
                                      9002792,//34.00 ͨ��ͨѶ  ͨ���豸
                                      9002829,//35.00 �������  ͨ���豸
                                      300025,//36.00  ���Ǵ�ҵ  ͨ���豸
                                      300028,//37.00  ���ǿƼ�  ͨ���豸
                                      300038,//38.00  ÷̩ŵ ͨ���豸
                                      300074,//39.00  ��ƽ�ɷ�  ͨ���豸
                                      300079,//40.00  ������Ѷ  ͨ���豸
                                      300081,//41.00  �����ƶ�  ͨ���豸
                                      300098,//42.00  ������ ͨ���豸
                                      300101,//43.00  ��о�Ƽ�  ͨ���豸
                                      300134,//44.00  �󸻿Ƽ�  ͨ���豸
                                      300136,//45.00  ��άͨ��  ͨ���豸
                                      300167,//46.00  ������Ѷ  ͨ���豸
                                      300177,//47.00  �к��� ͨ���豸
                                      300211,//48.00  ��ͨ�Ƽ�  ͨ���豸
                                      300213,//49.00  ��Ѷ�ɺ�  ͨ���豸
                                      300250,//50.00  ������Ϣ  ͨ���豸
                                      300264,//51.00  �Ѵ���Ѷ  ͨ���豸
                                      300270,//52.00  ��������  ͨ���豸
                                      300292,//53.00  ��ͨ�ع�  ͨ���豸
                                      300299,//54.00  ����ͨ��  ͨ���豸
                                      300310,//55.00  ��ͨ����  ͨ���豸
                                      300312,//56.00  ��Ѷ����  ͨ���豸
                                      300322,//57.00  ˶���� ͨ���豸
                                      300353,//58.00  �����Ƽ�  ͨ���豸
                                      300394,//59.00  ����ͨ��  ͨ���豸
                                      300397,//60.00  ��ͷ���  ͨ���豸
                                      300493,//61.00  �����Ƽ�  ͨ���豸
                                      300502,//62.00  ����ʢ ͨ���豸
                                      300555,//63.00  ·ͨ����  ͨ���豸
                                      300560,//64.00  �и�ͨ ͨ���豸
                                      300563,//65.00  ����ɷ�  ͨ���豸
                                      300565,//66.00  ���ż���  ͨ���豸
                                      300590,//67.00  ��Ϊͨ��  ͨ���豸
                                      600105,//68.00  �����ɷ�  ͨ���豸
                                      600130,//69.00  �����ɷ�  ͨ���豸
                                      600198,//70.00  ���Ƶ���  ͨ���豸
                                      600260,//71.00  ���ֿƼ�  ͨ���豸
                                      600345,//72.00  ����ͨ��  ͨ���豸
                                      600485,//73.00  ��������  ͨ���豸
                                      600487,//74.00  ��ͨ���  ͨ���豸
                                      600498,//75.00  ���ͨ��  ͨ���豸
                                      600522,//76.00  ����Ƽ�  ͨ���豸
                                      600562,//77.00  ��Ƽ�  ͨ���豸
                                      600677,//78.00  ����ͨ��  ͨ���豸
                                      600680,//79.00  �Ϻ�����  ͨ���豸
                                      600745,//80.00  ����ɷ�  ͨ���豸
                                      600764,//81.00  �е��ͨ  ͨ���豸
                                      600775,//82.00  �Ͼ���è  ͨ���豸
                                      600776,//83.00  ����ͨ��  ͨ���豸
                                      600990,//84.00  �Ĵ�����  ͨ���豸
                                      603118,//85.00  �����ɷ�  ͨ���豸
                                      603322,//86.00  ��Ѷͨ��  ͨ���豸
                                      603421,//87.00  ����ͨѶ  ͨ���豸
                                      603559,//88.00  ��ͨ����  ͨ���豸
                                      603660//89.00  ���ݿƴ�  ͨ���豸
    ));
    
    List<Integer> listCode_30_bdt = new ArrayList<Integer>(Arrays.asList(
                                      9002079,//1.00  ���ݹ��  �뵼��
                                      9002119,//2.00  ��ǿ����  �뵼��
                                      9002129,//3.00  �л��ɷ�  �뵼��
                                      9002156,//4.00  ͨ��΢��  �뵼��
                                      9002185,//5.00  ����Ƽ�  �뵼��
                                      9002218,//6.00  ��������  �뵼��
                                      9002371,//7.00  ���ǵ���  �뵼��
                                      9002449,//8.00  ���ǹ��  �뵼��
                                      9002506,//9.00  Э�μ���  �뵼��
                                      9002638,//10.00 ���Ϲ��  �뵼��
                                      9002654,//11.00 ����Ƽ�  �뵼��
                                      9002724,//12.00 ������ �뵼��
                                      9002745,//13.00 ľ��ɭ �뵼��
                                      9002815,//14.00 ��＼��  �뵼��
                                      300046,//15.00  ̨���ɷ�  �뵼��
                                      300053,//16.00  ŷ���� �뵼��
                                      300077,//17.00  ������  �뵼��
                                      300080,//18.00  �׳�����  �뵼��
                                      300102,//19.00  Ǭ�չ��  �뵼��
                                      300111,//20.00  ���տ� �뵼��
                                      300118,//21.00  ��������  �뵼��
                                      300223,//22.00  ��������  �뵼��
                                      300232,//23.00  �����Ƽ�  �뵼��
                                      300241,//24.00  �����  �뵼��
                                      300269,//25.00  �������  �뵼��
                                      300296,//26.00  ���ǵ� �뵼��
                                      300301,//27.00  ��������  �뵼��
                                      300303,//28.00  �۷ɹ��  �뵼��
                                      300317,//29.00  ��ΰ�ɷ�  �뵼��
                                      300323,//30.00  ���ӹ��  �뵼��
                                      300327,//31.00  ��ӱ����  �뵼��
                                      300373,//32.00  ��ܿƼ�  �뵼��
                                      300389,//33.00  ����ɭ �뵼��
                                      300582,//34.00  Ӣ���� �뵼��
                                      600151,//35.00  �������  �뵼��
                                      600171,//36.00  �Ϻ�����  �뵼��
                                      600206,//37.00  �����²�  �뵼��
                                      600360,//38.00  ��΢����  �뵼��
                                      600401,//39.00  ������  �뵼��
                                      600460,//40.00  ʿ��΢ �뵼��
                                      600537,//41.00  �ھ����  �뵼��
                                      600584,//42.00  ����Ƽ�  �뵼��
                                      600667,//43.00  ̫��ʵҵ  �뵼��
                                      600703,//44.00  �������  �뵼��
                                      600817,//45.00  *ST��ʢ �뵼��
                                      601012,//46.00  ¡���ɷ�  �뵼��
                                      601908,//47.00  ����ͨ �뵼��
                                      603005,//48.00  �����Ƽ�  �뵼��
                                      603986//49.00  ���״���  �뵼��
    
    ));
    
    List<Integer> listCode_31_yqj = new ArrayList<Integer>(Arrays.asList(
                                    9000020,//1.00  �����  Ԫ����
                                    9000032,//2.00  ��ɣ���  Ԫ����
                                    9000050,//3.00  �������  Ԫ����
                                    9000058,//4.00  �� �� �� Ԫ����
                                    9000068,//5.00  ��������  Ԫ����
                                    9000413,//6.00  ������  Ԫ����
                                    9000532,//7.00  ���Ϲɷ�  Ԫ����
                                    9000536,//8.00  ��ӳ�Ƽ�  Ԫ����
                                    9000636,//9.00  �绪�߿�  Ԫ����
                                    9000670,//10.00 *STӯ�� Ԫ����
                                    9000725,//11.00 ��������  Ԫ����
                                    9000727,//12.00 �����Ƽ�  Ԫ����
                                    9000733,//13.00 �񻪿Ƽ�  Ԫ����
                                    9000810,//14.00 ��ά����  Ԫ����
                                    9000823,//15.00 ��������  Ԫ����
                                    9000970,//16.00 �п�����  Ԫ����
                                    9002025,//17.00 �������  Ԫ����
                                    9002036,//18.00 ��������  Ԫ����
                                    9002045,//19.00 �������  Ԫ����
                                    9002049,//20.00 �Ϲ��о  Ԫ����
                                    9002055,//21.00 �������  Ԫ����
                                    9002056,//22.00 ��궫��  Ԫ����
                                    9002057,//23.00 �и���Դ  Ԫ����
                                    9002104,//24.00 �㱦�ɷ�  Ԫ����
                                    9002106,//25.00 �����߿�  Ԫ����
                                    9002134,//26.00 �������  Ԫ����
                                    9002137,//27.00 �������  Ԫ����
                                    9002138,//28.00 ˳�����  Ԫ����
                                    9002139,//29.00 �ذ�ɷ�  Ԫ����
                                    9002141,//30.00 ��ʤ��΢  Ԫ����
                                    9002179,//31.00 �к����  Ԫ����
                                    9002189,//32.00 ������  Ԫ����
                                    9002199,//33.00 *ST���� Ԫ����
                                    9002217,//34.00 ����̩ Ԫ����
                                    9002222,//35.00 �����Ƽ�  Ԫ����
                                    9002241,//36.00 ����ɷ�  Ԫ����
                                    9002273,//37.00 ˮ�����  Ԫ����
                                    9002288,//38.00 �����Ƽ�  Ԫ����
                                    9002289,//39.00 *ST��˳ Ԫ����
                                    9002388,//40.00 �����Ƴ�  Ԫ����
                                    9002389,//41.00 ����Ƽ�  Ԫ����
                                    9002402,//42.00 �Ͷ�̩ Ԫ����
                                    9002426,//43.00 ʤ������  Ԫ����
                                    9002436,//44.00 ��ɭ�Ƽ�  Ԫ����
                                    9002456,//45.00 ŷ�ƹ� Ԫ����
                                    9002463,//46.00 ����ɷ�  Ԫ����
                                    9002475,//47.00 ��Ѷ����  Ԫ����
                                    9002484,//48.00 �����ɷ�  Ԫ����
                                    9002512,//49.00 �ﻪ����  Ԫ����
                                    9002579,//50.00 �о�����  Ԫ����
                                    9002587,//51.00 ���ص���  Ԫ����
                                    9002600,//52.00 ���۴Ų�  Ԫ����
                                    9002618,//53.00 ����Ƽ�  Ԫ����
                                    9002636,//54.00 �𰲹���  Ԫ����
                                    9002655,//55.00 �������  Ԫ����
                                    9002729,//56.00 ������ Ԫ����
                                    9002806,//57.00 ����ɷ�  Ԫ����
                                    9002808,//58.00 ���ݺ��  Ԫ����
                                    9002835,//59.00 ͬΪ�ɷ�  Ԫ����
                                    300014,//60.00  ��γ���  Ԫ����
                                    300032,//61.00  ��������  Ԫ����
                                    300078,//62.00  ˼��ҽ��  Ԫ����
                                    300083,//63.00  ��ʤ����  Ԫ����
                                    300088,//64.00  ���ſƼ�  Ԫ����
                                    300114,//65.00  �к����  Ԫ����
                                    300115,//66.00  ��ӯ����  Ԫ����
                                    300127,//67.00  ���Ӵ���  Ԫ����
                                    300128,//68.00  �����²�  Ԫ����
                                    300131,//69.00  Ӣ���ǿ�  Ԫ����
                                    300139,//70.00  ���̿Ƽ�  Ԫ����
                                    300154,//71.00  ����ɷ�  Ԫ����
                                    300155,//72.00  ���ӱ� Ԫ����
                                    300162,//73.00  �����ɷ�  Ԫ����
                                    300184,//74.00  ��Դ��Ϣ  Ԫ����
                                    300205,//75.00  ������Ϣ  Ԫ����
                                    300207,//76.00  ������ Ԫ����
                                    300219,//77.00  �����ǻ�  Ԫ����
                                    300220,//78.00  ���˼���  Ԫ����
                                    300224,//79.00  �����Ų�  Ԫ����
                                    300227,//80.00  ���ϴ� Ԫ����
                                    300256,//81.00  ���ǿƼ�  Ԫ����
                                    300279,//82.00  �;��Ƽ�  Ԫ����
                                    300319,//83.00  ��ݿƼ�  Ԫ����
                                    300331,//84.00  �մ�ά��  Ԫ����
                                    300333,//85.00  ���տƼ�  Ԫ����
                                    300340,//86.00  �ƺ�ɷ�  Ԫ����
                                    300342,//87.00  ��������  Ԫ����
                                    300346,//88.00  �ϴ���  Ԫ����
                                    300351,//89.00  �������  Ԫ����
                                    300408,//90.00  ��������  Ԫ����
                                    300414,//91.00  �й����  Ԫ����
                                    300433,//92.00  ��˼�Ƽ�  Ԫ����
                                    300456,//93.00  �����Ƽ�  Ԫ����
                                    300458,//94.00  ȫ־�Ƽ�  Ԫ����
                                    300460,//95.00  ���׾���  Ԫ����
                                    300474,//96.00  ����΢ Ԫ����
                                    300476,//97.00  ʤ��Ƽ�  Ԫ����
                                    300546,//98.00  �۵ۿƼ�  Ԫ����
                                    300548,//99.00  �����Ƽ�  Ԫ����
                                    300566,//100.00 ���ǿƼ�  Ԫ����
                                    300570,//101.00 ̫���� Ԫ����
                                    9002841,//102.00 ��Դ�ɷ�  Ԫ����
                                    600071,//103.00 ��˹�ѧ  Ԫ����
                                    600183,//104.00 ����Ƽ�  Ԫ����
                                    600203,//105.00 ���յ���  Ԫ����
                                    600207,//106.00 ���ʸ߿�  Ԫ����
                                    600237,//107.00 ͭ�����  Ԫ����
                                    600330,//108.00 ��ͨ�ɷ�  Ԫ����
                                    600353,//109.00 ���ɷ�  Ԫ����
                                    600355,//110.00 ���׵���  Ԫ����
                                    600363,//111.00 �������  Ԫ����
                                    600462,//112.00 ���йɷ�  Ԫ����
                                    600478,//113.00 ����Զ Ԫ����
                                    600563,//114.00 ��������  Ԫ����
                                    600666,//115.00 ����� Ԫ����
                                    600707,//116.00 �ʺ�ɷ�  Ԫ����
                                    600980,//117.00 ����Ƽ�  Ԫ����
                                    601231,//118.00 �������  Ԫ����
                                    603160,//119.00 �㶥�Ƽ�  Ԫ����
                                    603328,//120.00 ���ٵ���  Ԫ����
                                    603633,//121.00 ��ľ�ɷ�  Ԫ����
                                    603678,//122.00 ������  Ԫ����
                                    603738,//123.00 ̩���Ƽ�  Ԫ����
                                    603936,//124.00 ��������  Ԫ����
                                    603989,//125.00 ��������  Ԫ����
                                    603186,//126.00 �����²�  Ԫ����
                                    603228//127.00 ��������  Ԫ����
    
    ));
    
    List<Integer> listCode_32_rjfw = new ArrayList<Integer>(Arrays.asList(
                                    9000555,//1.00  ������Ϣ  �������
                                    9000662,//2.00  �����ǻ�  �������
                                    9000711,//3.00  �����Ƽ�  �������
                                    9000851,//4.00  �ߺ�ɷ�  �������
                                    9000938,//5.00  �Ϲ�ɷ�  �������
                                    9000948,//6.00  ������Ϣ  �������
                                    9000997,//7.00  �� �� ½ �������
                                    9002027,//8.00  ���ڴ�ý  �������
                                    9002063,//9.00  Զ�����  �������
                                    9002065,//10.00 �������  �������
                                    9002073,//11.00 ��عɷ�  �������
                                    9002090,//12.00 ���ǿƼ�  �������
                                    9002153,//13.00 ʯ����Ϣ  �������
                                    9002184,//14.00 ���ÿ���  �������
                                    9002195,//15.00 ��������  �������
                                    9002230,//16.00 �ƴ�Ѷ��  �������
                                    9002232,//17.00 ������Ϣ  �������
                                    9002253,//18.00 ������ʤ  �������
                                    9002268,//19.00 �� ʿ ͨ �������
                                    9002279,//20.00 �������  �������
                                    9002280,//21.00 ���绥��  �������
                                    9002298,//22.00 �е�����  �������
                                    9002316,//23.00 ����ͨѶ  �������
                                    9002331,//24.00 ��ͨ�Ƽ�  �������
                                    9002368,//25.00 ̫���ɷ�  �������
                                    9002373,//26.00 ǧ���Ƽ�  �������
                                    9002401,//27.00 �к��Ƽ�  �������
                                    9002405,//28.00 ��άͼ��  �������
                                    9002410,//29.00 ������ �������
                                    9002421,//30.00 ��ʵ����  �������
                                    9002439,//31.00 �����ǳ�  �������
                                    9002474,//32.00 �Ż����  �������
                                    9002544,//33.00 �����Ƽ�  �������
                                    9002609,//34.00 ��˳�Ƽ�  �������
                                    9002642,//35.00 ��֮�� �������
                                    9002649,//36.00 ����Ƽ�  �������
                                    9002657,//37.00 �пƽ��  �������
                                    9002771,//38.00 ����ͨ �������
                                    9002777,//39.00 ��Զ����  �������
                                    300002,//40.00  ����̩��  �������
                                    300010,//41.00  ��˼�� �������
                                    300020,//42.00  �����ɷ�  �������
                                    300033,//43.00  ͬ��˳ �������
                                    300036,//44.00  ��ͼ���  �������
                                    300044,//45.00  ��Ϊ����  �������
                                    300047,//46.00  ��Դ�Ͽ�  �������
                                    300050,//47.00  ���Ͷ���  �������
                                    300065,//48.00  ������ �������
                                    300075,//49.00  ������ͨ  �������
                                    300085,//50.00  ��֮�� �������
                                    300096,//51.00  ������ �������
                                    300150,//52.00  �������  �������
                                    300166,//53.00  ��������  �������
                                    300168,//54.00  �����Ϣ  �������
                                    300170,//55.00  ������Ϣ  �������
                                    300182,//56.00  �ݳɹɷ�  �������
                                    300183,//57.00  �����ز�  �������
                                    300188,//58.00  ���ǰؿ�  �������
                                    300209,//59.00  ������Ϣ  �������
                                    300212,//60.00  �׻�¼ �������
                                    300229,//61.00  �ض�˼ �������
                                    300231,//62.00  ���ſƼ�  �������
                                    300235,//63.00  ��ֱ�Ƽ�  �������
                                    300245,//64.00  ����Ƽ�  �������
                                    300248,//65.00  �¿��� �������
                                    300253,//66.00  ��������  �������
                                    300271,//67.00  �������  �������
                                    300275,//68.00  ÷��ɭ �������
                                    300277,//69.00  ����Ѷ �������
                                    300287,//70.00  ������ �������
                                    300288,//71.00  ������Ϣ  �������
                                    300290,//72.00  �ٿƿƼ�  �������
                                    300297,//73.00  ���ܹɷ�  �������
                                    300300,//74.00  ��������  �������
                                    300302,//75.00  ͬ�пƼ�  �������
                                    300311,//76.00  ������ �������
                                    300324,//77.00  ������Ϣ  �������
                                    300330,//78.00  �����ͨ  �������
                                    300339,//79.00  ������  �������
                                    300348,//80.00  �����Ƽ�  �������
                                    300352,//81.00  ����Դ �������
                                    300365,//82.00  �㻪�Ƽ�  �������
                                    300366,//83.00  ������Ϣ  �������
                                    300369,//84.00  ���˿Ƽ�  �������
                                    300377,//85.00  Ӯʱʤ �������
                                    300378,//86.00  �������  �������
                                    300379,//87.00  ����ͨ �������
                                    300380,//88.00  ��˶��Ϣ  �������
                                    300386,//89.00  �������  �������
                                    300419,//90.00  �Ʒ�Ƽ�  �������
                                    300440,//91.00  �˴�Ƽ�  �������
                                    300448,//92.00  ���ƿƼ�  �������
                                    300451,//93.00  ��ҵ���  �������
                                    300465,//94.00  ��ΰ�� �������
                                    300468,//95.00  �ķ�����  �������
                                    300469,//96.00  ��Ϣ��չ  �������
                                    300479,//97.00  ��˼����  �������
                                    300496,//98.00  �пƴ���  �������
                                    300508,//99.00  ά��ɷ�  �������
                                    300513,//100.00 ��̩ʵ��  �������
                                    300518,//101.00 ʢѶ�� �������
                                    300520,//102.00 �ƴ����  �������
                                    300523,//103.00 �����Ƽ�  �������
                                    300525,//104.00 ��˼���  �������
                                    300531,//105.00 �Ų�Ѷ �������
                                    300532,//106.00 �������  �������
                                    300533,//107.00 ��������  �������
                                    300541,//108.00 �Ƚ���ͨ  �������
                                    300542,//109.00 �³��Ƽ�  �������
                                    300550,//110.00 ���ʿƼ�  �������
                                    300552,//111.00 �򼯿Ƽ�  �������
                                    300556,//112.00 ˿·�Ӿ�  �������
                                    300559,//113.00 �ѷ���̩  �������
                                    300561,//114.00 ���Ƽ�  �������
                                    300579,//115.00 ������֤  �������
                                    300588,//116.00 ������Ϣ  �������
                                    300592,//117.00 ��������  �������
                                    600288,//118.00 ���Ƽ�  �������
                                    600289,//119.00 ������ͨ  �������
                                    600406,//120.00 ��������  �������
                                    600410,//121.00 ��ʤ���  �������
                                    600446,//122.00 ��֤�ɷ�  �������
                                    600455,//123.00 ��ͨ�ɷ�  �������
                                    600476,//124.00 ���ʿƼ�  �������
                                    600536,//125.00 �й����  �������
                                    600556,//126.00 ST����  �������
                                    600570,//127.00 ��������  �������
                                    600571,//128.00 ���Ŵ� �������
                                    600588,//129.00 ��������  �������
                                    600602,//130.00 ��������  �������
                                    600654,//131.00 �а��� �������
                                    600718,//132.00 ������  �������
                                    600728,//133.00 �Ѷ��Ƽ�  �������
                                    600756,//134.00 �˳����  �������
                                    600797,//135.00 �������  �������
                                    600845,//136.00 �������  �������
                                    600850,//137.00 ��������  �������
                                    601519,//138.00 ���ǻ� �������
                                    603189,//139.00 �������  �������
                                    603508,//140.00 ˼ά�п�  �������
                                    603528,//141.00 ���׿Ƽ�  �������
                                    603636,//142.00 �������  �������
                                    603859,//143.00 �ܿƹɷ�  �������
                                    603918,//144.00 ������Ϣ  �������
                                    603990,//145.00 ��ϿƼ�  �������
                                    603039//146.00 ��΢����  �������
    
    ));
    
    List<Integer> listCode_33_hlw = new ArrayList<Integer>(Arrays.asList(
                                    9000676,//1.00  �Ƕȹɷ�  ������
                                    9000681,//2.00  �Ӿ��й�  ������
                                    9000971,//3.00  �����ع�  ������
                                    9002095,//4.00  �� �� �� ������
                                    9002113,//5.00  ��������  ������
                                    9002123,//6.00  ��������  ������
                                    9002127,//7.00  �ϼ�����  ������
                                    9002131,//8.00  ��ŷ�ɷ�  ������
                                    9002148,//9.00  ��γͨ��  ������
                                    9002174,//10.00 ��������  ������
                                    9002261,//11.00 ��ά��Ϣ  ������
                                    9002315,//12.00 ����Ƽ�  ������
                                    9002354,//13.00 ��������  ������
                                    9002464,//14.00 �����Ƽ�  ������
                                    9002517,//15.00 ��Ӣ����  ������
                                    9002555,//16.00 ���߻���  ������
                                    300051,//17.00  ���廥��  ������
                                    300052,//18.00  ���౦ ������
                                    300059,//19.00  �����Ƹ�  ������
                                    300104,//20.00  ������ ������
                                    300113,//21.00  ˳���Ƽ�  ������
                                    300226,//22.00  �Ϻ�����  ������
                                    300242,//23.00  ��������  ������
                                    300295,//24.00  ��������  ������
                                    300315,//25.00  ��Ȥ�Ƽ�  ������
                                    300343,//26.00  ��������  ������
                                    300392,//27.00  ���Źɷ�  ������
                                    300399,//28.00  ������ ������
                                    300418,//29.00  ������ά  ������
                                    300431,//30.00  ���缯��  ������
                                    300467,//31.00  Ѹ�οƼ�  ������
                                    300494,//32.00  ʢ������  ������
                                    300571,//33.00  ƽ����Ϣ  ������
                                    600652,//34.00  �ξ���Ϸ  ������
                                    600986,//35.00  �ƴ�ɷ�  ������
                                    603000,//36.00  ������ ������
                                    603258,//37.00  �������  ������
                                    603888,//38.00  �»��� ������
                                    603444//39.00  ������ ������
    
    ));
    
    List<Integer> listCode_34_zhl = new ArrayList<Integer>(Arrays.asList(
                                  9000009,//1.00  �й�����  �ۺ���
                                  9000034,//2.00  ��������  �ۺ���
                                  9000301,//3.00  �����г�  �ۺ���
                                  9000409,//4.00  ɽ���ؿ�  �ۺ���
                                  9000632,//5.00  ��ľ����  �ۺ���
                                  9000633,//6.00  *ST�Ͻ� �ۺ���
                                  9000701,//7.00  �����Ŵ�  �ۺ���
                                  9000839,//8.00  ���Ź���  �ۺ���
                                  9000881,//9.00  ��������  �ۺ���
                                  9000909,//10.00 ��Դ�Ƽ�  �ۺ���
                                  600051,//11.00  ��������  �ۺ���
                                  600149,//12.00  �ȷ���չ  �ۺ���
                                  600175,//13.00  ������Դ  �ۺ���
                                  600200,//14.00  ��������  �ۺ���
                                  600209,//15.00  �޶ٷ�չ  �ۺ���
                                  600212,//16.00  *ST��Ȫ �ۺ���
                                  600256,//17.00  �����Դ  �ۺ���
                                  600603,//18.00  *ST��ҵ �ۺ���
                                  600614,//19.00  �����ɷ�  �ۺ���
                                  600620,//20.00  ��巹ɷ�  �ۺ���
                                  600624,//21.00  ��������  �ۺ���
                                  600647,//22.00  ͬ�ﴴҵ  �ۺ���
                                  600701,//23.00  *ST���� �ۺ���
                                  600730,//24.00  �й��߿�  �ۺ���
                                  600770,//25.00  ���չɷ�  �ۺ���
                                  600777,//26.00  �³���Դ  �ۺ���
                                  600800,//27.00  ���ſ�  �ۺ���
                                  600805,//28.00  �ô�Ͷ��  �ۺ���
                                  600811,//29.00  ��������  �ۺ���
                                  600846,//30.00  ͬ�ÿƼ�  �ۺ���
                                  600892//31.00  �����Ļ�  �ۺ���
    
    ));
    
    List<Integer> listCode_35_mtkc = new ArrayList<Integer>(Arrays.asList(
                                  9000552,//1.00  ��Զú��  ú̿����
                                  9000571,//2.00  �´��ޣ�  ú̿����
                                  9000780,//3.00  ƽׯ��Դ  ú̿����
                                  9000933,//4.00  *ST��� ú̿����
                                  9000937,//5.00  ������Դ  ú̿����
                                  9000968,//6.00  *STú�� ú̿����
                                  9000983,//7.00  ��ɽú��  ú̿����
                                  9002128,//8.00  ¶��úҵ  ú̿����
                                  600121,//9.00 ֣��ú��  ú̿����
                                  600123,//10.00  �����ƴ�  ú̿����
                                  600157,//11.00  ��̩��Դ  ú̿����
                                  600188,//12.00  ����úҵ  ú̿����
                                  600348,//13.00  ��Ȫúҵ  ú̿����
                                  600395,//14.00  �̽��ɷ�  ú̿����
                                  600397,//15.00  ��Դúҵ  ú̿����
                                  600403,//16.00  ������Դ  ú̿����
                                  600508,//17.00  �Ϻ���Դ  ú̿����
                                  600546,//18.00  *STɽú ú̿����
                                  600714,//19.00  �����ҵ  ú̿����
                                  600758,//20.00  ������Դ  ú̿����
                                  600971,//21.00  ��Դú��  ú̿����
                                  600997,//22.00  ���йɷ�  ú̿����
                                  601001,//23.00  ��ͬúҵ  ú̿����
                                  601088,//24.00  �й���  ú̿����
                                  601101,//25.00  껻���Դ  ú̿����
                                  601225,//26.00  ����úҵ  ú̿����
                                  601666,//27.00  ƽú�ɷ�  ú̿����
                                  601699,//28.00  º������  ú̿����
                                  601898,//29.00  ��ú��Դ  ú̿����
                                  601918//30.00  *ST�¼� ú̿����
                                  
    ));
    
    List<Integer> listCode_36_jtjg = new ArrayList<Integer>(Arrays.asList(
                                  9000723,//1.00  ������Դ  ��̿�ӹ�
                                  600408,//2.00 ��̩����  ��̿�ӹ�
                                  600721,//3.00 *ST�ٻ� ��̿�ӹ�
                                  600725,//4.00 *ST��ά ��̿�ӹ�
                                  600740,//5.00 ɽ������  ��̿�ӹ�
                                  600792,//6.00 ��ú��Դ  ��̿�ӹ�
                                  601011,//7.00 ��̩¡ ��̿�ӹ�
                                  601015//8.00 ������è  ��̿�ӹ�
    
    ));
    
    List<Integer> listCode_37_slfd = new ArrayList<Integer>(Arrays.asList(
                                9000601,//1.00  ���ܹɷ�  ˮ������
                                9000722,//2.00  ���Ϸ�չ  ˮ������
                                9000791,//3.00  �����Ͷ  ˮ������
                                9000993,//4.00  ��������  ˮ������
                                9002039,//5.00  ǭԴ����  ˮ������
                                600101,//6.00 ���ǵ���  ˮ������
                                600116,//7.00 ��Ͽˮ��  ˮ������
                                600131,//8.00 ẽ�ˮ��  ˮ������
                                600236,//9.00 ��ڵ���  ˮ������
                                600310,//10.00  �𶫵���  ˮ������
                                600452,//11.00  �������  ˮ������
                                600505,//12.00  ��������  ˮ������
                                600644,//13.00  ��ɽ����  ˮ������
                                600674,//14.00  ��Ͷ��Դ  ˮ������
                                600868,//15.00  ÷�㼪��  ˮ������
                                600886,//16.00  ��Ͷ����  ˮ������
                                600900,//17.00  ��������  ˮ������
                                600969,//18.00  �������  ˮ������
                                600979,//19.00  �㰲����  ˮ������
                                600995//20.00  ��ɽ����  ˮ������
    
    ));
    
    List<Integer> listCode_38_hldf = new ArrayList<Integer>(Arrays.asList(
                              9000027,//1.00  ������Դ  ��������
                              9000037,//2.00  *ST�ϵ�A  ��������
                              9000531,//3.00  ����ˣ�  ��������
                              9000539,//4.00  ��������  ��������
                              9000543,//5.00  ���ܵ���  ��������
                              9000600,//6.00  ��Ͷ��Դ  ��������
                              9000690,//7.00  ������Դ  ��������
                              9000720,//8.00  ����̩ɽ  ��������
                              9000767,//9.00  �������  ��������
                              9000875,//10.00 ����ɷ�  ��������
                              9000883,//11.00 ������Դ  ��������
                              9000899,//12.00 ���ܹɷ�  ��������
                              9000958,//13.00 ������Դ  ��������
                              9000966,//14.00 ��Դ����  ��������
                              9001896,//15.00 ԥ�ܿع�  ��������
                              9002479,//16.00 ��������  ��������
                              600011,//17.00  ���ܹ���  ��������
                              600021,//18.00  �Ϻ�����  ��������
                              600023,//19.00  ���ܵ���  ��������
                              600027,//20.00  �������  ��������
                              600098,//21.00  ���ݷ�չ  ��������
                              600396,//22.00  ��ɽ�ɷ�  ��������
                              600483,//23.00  ���ܹɷ�  ��������
                              600509,//24.00  �츻��Դ  ��������
                              600578,//25.00  ���ܵ���  ��������
                              600642,//26.00  ���ܹɷ�  ��������
                              600726,//27.00  ������Դ  ��������
                              600744,//28.00  ��������  ��������
                              600780,//29.00  ͨ����Դ  ��������
                              600795,//30.00  �������  ��������
                              600863,//31.00  ���ɻ���  ��������
                              600864,//32.00  ��Ͷ�ɷ�  ��������
                              601991//33.00  ���Ʒ���  ��������
    ));
    
    List<Integer> listCode_39_xxdl = new ArrayList<Integer>(Arrays.asList(
                            9000591,//1.00  ̫���� ���͵���
                            9000862,//2.00  ������Դ  ���͵���
                            9000939,//3.00  ������̬  ���͵���
                            600163,//4.00 ������Դ  ���͵���
                            600277,//5.00 ��������  ���͵���
                            601016,//6.00 ���ܷ��  ���͵���
                            601985//7.00 �й��˵�  ���͵���
    ));
    
    List<Integer> listCode_40_sykc = new ArrayList<Integer>(Arrays.asList(
                        9002207,//1.00  ׼�͹ɷ�  ʯ�Ϳ���
                        9002554,//2.00  �ݲ��� ʯ�Ϳ���
                        9002629,//3.00  ���ǹɷ�  ʯ�Ϳ���
                        9002828,//4.00  ������Դ  ʯ�Ϳ���
                        300084,//5.00 ��Ĭ�Ƽ�  ʯ�Ϳ���
                        300157,//6.00 ��̩����  ʯ�Ϳ���
                        300164,//7.00 ͨԴʯ��  ʯ�Ϳ���
                        300191,//8.00 Ǳ�ܺ���  ʯ�Ϳ���
                        600583,//9.00 ���͹���  ʯ�Ϳ���
                        600759,//10.00  �޼�����  ʯ�Ϳ���
                        600871,//11.00  ʯ���ͷ�  ʯ�Ϳ���
                        601808,//12.00  �к��ͷ�  ʯ�Ϳ���
                        601857,//13.00  �й�ʯ��  ʯ�Ϳ���
                        603727//14.00  ������ ʯ�Ϳ���
    ));
    
    List<Integer> listCode_41_syjg = new ArrayList<Integer>(Arrays.asList(
                      9000059,//1.00  �����ɷ�  ʯ�ͼӹ�
                      9000637,//2.00  ï��ʵ��  ʯ�ͼӹ�
                      9000819,//3.00  �����˳�  ʯ�ͼӹ�
                      9002377,//4.00  ��������  ʯ�ͼӹ�
                      9002778,//5.00  �߿�ʯ��  ʯ�ͼӹ�
                      600028,//6.00 �й�ʯ��  ʯ�ͼӹ�
                      600339,//7.00 *ST���� ʯ�ͼӹ�
                      600688,//8.00 �Ϻ�ʯ��  ʯ�ͼӹ�
                      603798//9.00 ���ն� ʯ�ͼӹ�
    ));
    List<Integer> listCode_42_symy = new ArrayList<Integer>(Arrays.asList(
                      9000096,//1.00  �����Դ  ʯ��ó��
                      9000159,//2.00  ����ʵҵ  ʯ��ó��
                      9000554,//3.00  ̩ɽʯ��  ʯ��ó��
                      9002221,//4.00  ������Դ  ʯ��ó��
                      600387,//5.00 ��Խ�ɷ�  ʯ��ó��
                      603003//6.00 ����ȼ��  ʯ��ó��
    ));
    
    List<Integer> listCode_43_pg = new ArrayList<Integer>(Arrays.asList(
                      9000629,//1.00  *ST���� �ո�
                      9000655,//2.00  �����ҵ  �ո�
                      9000709,//3.00  �Ӹֹɷ�  �ո�
                      9000898,//4.00  ���ֹɷ�  �ո�
                      9000932,//5.00  �������  �ո�
                      9000959,//6.00  �׸ֹɷ�  �ո�
                      9002110,//7.00  ��������  �ո�
                      9002478,//8.00  �����ɷ�  �ո�
                      600005,//9.00 ��ֹɷ�  �ո�
                      600010,//10.00  ���ֹɷ�  �ո�
                      600019,//11.00  ���ֹɷ�  �ո�
                      600022,//12.00  ɽ������  �ո�
                      600126,//13.00  ���ֹɷ�  �ո�
                      600231,//14.00  ��ֹɷ�  �ո�
                      600282,//15.00  �ϸֹɷ�  �ո�
                      600307,//16.00  �Ƹֺ���  �ո�
                      600532,//17.00  ����ҵ  �ո�
                      600569,//18.00  ��������  �ո�
                      600581,//19.00  *ST�˸� �ո�
                      600608,//20.00  �Ϻ��Ƽ�  �ո�
                      600784,//21.00  ³��Ͷ��  �ո�
                      600808,//22.00  ��ֹɷ�  �ո�
                      601003,//23.00  ���ֹɷ�  �ո�
                      601005,//24.00  �������  �ո�
                      601969//25.00  ���Ͽ�ҵ  �ո�
    
    ));
    
    List<Integer> listCode_44_tzg = new ArrayList<Integer>(Arrays.asList(
                    9000708,//1.00  ��ұ�ظ�  ���ָ�
                    9000825,//2.00  ̫�ֲ���  ���ָ�
                    9002075,//3.00  ɳ�ֹɷ�  ���ָ�
                    9002318,//4.00  �����ز�  ���ָ�
                    9002423,//5.00  *ST���� ���ָ�
                    9002756,//6.00  �����ظ�  ���ָ�
                    600117,//7.00 �����ظ�  ���ָ�
                    600399,//8.00 ��˳�ظ�  ���ָ�
                    600507//9.00 �����ظ�  ���ָ�
    ));
    
    List<Integer> listCode_45_gjg = new ArrayList<Integer>(Arrays.asList(
                    9000717,//1.00  *ST�ظ� �ּӹ�
                    9000761,//2.00  ���ְ��  �ּӹ�
                    9000778,//3.00  ��������  �ּӹ�
                    9000890,//4.00  �� �� ʤ �ּӹ�
                    9000969,//5.00  ��̩�Ƽ�  �ּӹ�
                    9002132,//6.00  ���ǿƼ�  �ּӹ�
                    9002352,//7.00  ��̩�²�  �ּӹ�
                    9002359,//8.00  ��������  �ּӹ�
                    9002443,//9.00  ���޹ܵ�  �ּӹ�
                    9002487,//10.00 ����ع�  �ּӹ�
                    9002541,//11.00 ��·�ֹ�  �ּӹ�
                    9002545,//12.00 ��������  �ּӹ�
                    9002743,//13.00 ���͸ֹ�  �ּӹ�
                    300345,//14.00  �����²�  �ּӹ�
                    9002843,//15.00 ̩�ιɷ�  �ּӹ�
                    600165,//16.00  ���պ���  �ּӹ�
                    600477,//17.00  �����ֹ�  �ּӹ�
                    600496,//18.00  �����ֹ�  �ּӹ�
                    600558,//19.00  ������ �ּӹ�
                    600782,//20.00  �¸ֹɷ�  �ּӹ�
                    600992,//21.00  �����ɷ�  �ּӹ�
                    601028,//22.00  �����ɷ�  �ּӹ�
                    603028,//23.00  ������ �ּӹ�
                    603300,//24.00  �����Ƽ�  �ּӹ�
                    603577,//25.00  ���ͨ �ּӹ�
                    603878//26.00  �������  �ּӹ�
    
    ));
    
    List<Integer> listCode_46_t =new ArrayList<Integer>( Arrays.asList(
                          9000630,//1.00  ͭ����ɫ  ͭ
                          9000878,//2.00  ����ͭҵ  ͭ
                          9002171,//3.00  �����²�  ͭ
                          9002203,//4.00  �����ɷ�  ͭ
                          9002295,//5.00  ���չɷ�  ͭ
                          600139,//6.00 ������Դ  ͭ
                          600255,//7.00 �οƲ���  ͭ
                          600362,//8.00 ����ͭҵ  ͭ
                          600490,//9.00 ������Դ  ͭ
                          601137,//10.00  �����Ͻ�  ͭ
                          601168//11.00  ������ҵ  ͭ
    ));
    List<Integer> listCode_47_l = new ArrayList<Integer>(Arrays.asList(
                    9000612,//1.00  ������  ��
                    9000807,//2.00  �����ɷ�  ��
                    9002082,//3.00  �����²�  ��
                    9002160,//4.00  �����ɷ�  ��
                    9002333,//5.00  ����˹��  ��
                    9002379,//6.00  *ST³�� ��
                    9002501,//7.00  ��Դ����  ��
                    9002540,//8.00  ��̫�Ƽ�  ��
                    9002578,//9.00  ������ҵ  ��
                    300328,//10.00  �˰��Ƽ�  ��
                    300337,//11.00  ����ɷ�  ��
                    300428,//12.00  ��ͨ�²�  ��
                    300489,//13.00  �зɹɷ�  ��
                    9002824,//14.00  ��ʤ�ɷ�  ��
                    600219,//15.00  ��ɽ��ҵ  ��
                    600595,//16.00  ����ʵҵ  ��
                    600673,//17.00  �������  ��
                    600768,//18.00  ��������  ��
                    600888,//19.00  �½��ں�  ��
                    601388,//20.00  ������Դ  ��
                    601600,//21.00  �й���ҵ  ��
                    601677//22.00  ��̩��ҵ  ��
    ));
    List<Integer> listCode_47_yx = new ArrayList<Integer>(Arrays.asList(
                9000060,//1.00  �н�����  Ǧп
                9000426,//2.00  ��ҵ��ҵ  Ǧп
                9000603,//3.00  ʢ���ҵ  Ǧп
                9000688,//4.00  ���¿�ҵ  Ǧп
                9000751,//5.00  пҵ�ɷ�  Ǧп
                9000758,//6.00  ��ɫ�ɷ�  Ǧп
                9000975,//7.00  ��̩��Դ  Ǧп
                9002114,//8.00  ��ƽп��  Ǧп
                600331,//9.00 ���ɷ�  Ǧп
                600338,//10.00  �������  Ǧп
                600497,//11.00  �ۺ�п��  Ǧп
                600531,//12.00  ԥ���Ǧ  Ǧп
                600961,//13.00  ��ұ����  Ǧп
                601020//14.00  ���ڿ�ҵ  Ǧп
    
    ));
    
    List<Integer> listCode_48_hj = new ArrayList<Integer>(Arrays.asList(
                9002155,//1.00  ���ϻƽ�  �ƽ�
                9002237,//2.00  ���ɷ�  �ƽ�
                600311,//3.00 �ٻ�ʵҵ  �ƽ�
                600385,//4.00 ɽ����̩  �ƽ�
                600489,//5.00 �н�ƽ�  �ƽ�
                600547,//6.00 ɽ���ƽ�  �ƽ�
                600687,//7.00 ��̩�ع�  �ƽ�
                600766,//8.00 ԰�ǻƽ�  �ƽ�
                600988,//9.00 ���ƽ�  �ƽ�
                601069,//10.00  �����ƽ�  �ƽ�
                601899//11.00  �Ͻ��ҵ  �ƽ�
    
    ));
    
    List<Integer> listCode_49_xjs = new ArrayList<Integer>(Arrays.asList(
                  9000657,//1.00  ���ٸ���  С����
                  9000693,//2.00  ST����  С����
                  9000697,//3.00  ��ʯ��ɫ  С����
                  9000762,//4.00  ���ؿ�ҵ  С����
                  9000831,//5.00  *ST��ϡ С����
                  9000960,//6.00  ��ҵ�ɷ�  С����
                  9000962,//7.00  *ST���� С����
                  9002149,//8.00  ��������  С����
                  9002167,//9.00  �����ҵ  С����
                  9002182,//10.00 �ƺ�����  С����
                  9002340,//11.00 ������ С����
                  9002378,//12.00 ��Դ��ҵ  С����
                  9002428,//13.00 ������ҵ  С����
                  9002460,//14.00 �ӷ��ҵ  С����
                  9002466,//15.00 �����ҵ  С����
                  9002716,//16.00 �����ҵ  С����
                  300034,//17.00  ���и���  С����
                  9002842,//18.00  ������ҵ  С����
                  600111,//19.00  ����ϡ��  С����
                  600259,//20.00  ������ɫ  С����
                  600390,//21.00  *ST���� С����
                  600392,//22.00  ʢ����Դ  С����
                  600432,//23.00  *ST���� С����
                  600456,//24.00  ���ѹɷ�  С����
                  600459,//25.00  ���в�ҵ  С����
                  600549,//26.00  ������ҵ  С����
                  600615,//27.00  �Ừ�ɷ�  С����
                  600711,//28.00  ʢ�Ϳ�ҵ  С����
                  601958,//29.00  ����ɷ�  С����
                  603399,//30.00  �»��� С����
                  603799,//31.00  ������ҵ  С����
                  603993,//32.00  ������ҵ  С����
                  601212//33.00  ������ɫ  С����
    ));
    
    List<Integer> listCode_50_hgyl = new ArrayList<Integer>(Arrays.asList(
                9000510,//1.00  ��·����  ����ԭ��
                9000545,//2.00  ������ҵ  ����ԭ��
                9000635,//3.00  Ӣ �� �� ����ԭ��
                9000683,//4.00  Զ����Դ  ����ԭ��
                9000698,//5.00  ��������  ����ԭ��
                9000707,//6.00  ˫���Ƽ�  ����ԭ��
                9000755,//7.00  ɽ����ά  ����ԭ��
                9000818,//8.00  ���󻯹�  ����ԭ��
                9000822,//9.00  ɽ������  ����ԭ��
                9000985,//10.00 ���컪��  ����ԭ��
                9000990,//11.00 ��־�ɷ�  ����ԭ��
                9002002,//12.00 �����ҵ  ����ԭ��
                9002037,//13.00 ������չ  ����ԭ��
                9002054,//14.00 ��������  ����ԭ��
                9002061,//15.00 *ST���� ����ԭ��
                9002068,//16.00 ��è�ɷ�  ����ԭ��
                9002092,//17.00 ��̩��ѧ  ����ԭ��
                9002096,//18.00 ������  ����ԭ��
                9002109,//19.00 *ST�˻� ����ԭ��
                9002125,//20.00 ��̶�绯  ����ԭ��
                9002136,//21.00 �� �� �� ����ԭ��
                9002145,//22.00 �к��Ѱ�  ����ԭ��
                9002165,//23.00 �� �� �� ����ԭ��
                9002226,//24.00 ���ϻ���  ����ԭ��
                9002246,//25.00 �����ɷ�  ����ԭ��
                9002250,//26.00 �����Ƽ�  ����ԭ��
                9002326,//27.00 ��̫�Ƽ�  ����ԭ��
                9002341,//28.00 ���ڿƼ�  ����ԭ��
                9002360,//29.00 ͬ�»���  ����ԭ��
                9002361,//30.00 �񽣹ɷ�  ����ԭ��
                9002386,//31.00 ��ԭ����  ����ԭ��
                9002407,//32.00 ����� ����ԭ��
                9002408,//33.00 �����ڴ�  ����ԭ��
                9002409,//34.00 �ſ˿Ƽ�  ����ԭ��
                9002442,//35.00 ���ǻ���  ����ԭ��
                9002450,//36.00 ������ ����ԭ��
                9002453,//37.00 ������  ����ԭ��
                9002455,//38.00 �ٴ��ɷ�  ����ԭ��
                9002476,//39.00 ��Ī�ɷ�  ����ԭ��
                9002497,//40.00 �Ż�����  ����ԭ��
                9002562,//41.00 �ֵܿƼ�  ����ԭ��
                9002584,//42.00 ��¤��ѧ  ����ԭ��
                9002591,//43.00 ������  ����ԭ��
                9002597,//44.00 ���ʵҵ  ����ԭ��
                9002601,//45.00 ������ ����ԭ��
                9002632,//46.00 ������ѧ  ����ԭ��
                9002643,//47.00 ����ɷ�  ����ԭ��
                9002648,//48.00 ����ʯ��  ����ԭ��
                9002666,//49.00 ��������  ����ԭ��
                9002669,//50.00 �����²�  ����ԭ��
                9002683,//51.00 �����  ����ԭ��
                9002709,//52.00 ��Ͳ���  ����ԭ��
                9002741,//53.00 �⻪�Ƽ�  ����ԭ��
                9002748,//54.00 ����ʵҵ  ����ԭ��
                9002753,//55.00 �����ɷ�  ����ԭ��
                9002783,//56.00 �����ɷ�  ����ԭ��
                9002802,//57.00 ����²�  ����ԭ��
                9002805,//58.00 ��Ԫ�ɷ�  ����ԭ��
                9002809,//59.00 ��ǽ�ɷ�  ����ԭ��
                9002810,//60.00 ɽ���մ�  ����ԭ��
                9002825,//61.00 �ɶ��ɷ�  ����ԭ��
                9002827,//62.00 ������  ����ԭ��
                300019,//63.00  �豦�Ƽ�  ����ԭ��
                300037,//64.00  ����� ����ԭ��
                300041,//65.00  �����²�  ����ԭ��
                300054,//66.00  �����ɷ�  ����ԭ��
                300082,//67.00  �¿˹ɷ�  ����ԭ��
                300107,//68.00  ���¹ɷ�  ����ԭ��
                300109,//69.00  �¿�Դ ����ԭ��
                300121,//70.00  ���Ȼ�̩  ����ԭ��
                300132,//71.00  ���ɹɷ�  ����ԭ��
                300135,//72.00  ��������  ����ԭ��
                300174,//73.00  Ԫ���ɷ�  ����ԭ��
                300200,//74.00  �����²�  ����ԭ��
                300214,//75.00  �տƻ�ѧ  ����ԭ��
                300243,//76.00  ���߲�  ����ԭ��
                300387,//77.00  ����ɷ�  ����ԭ��
                300405,//78.00  ��¡����  ����ԭ��
                300429,//79.00  ǿ���²�  ����ԭ��
                300437,//80.00  ��ˮԴ ����ԭ��
                300446,//81.00  �ֿ��²�  ����ԭ��
                300459,//82.00  �������  ����ԭ��
                300481,//83.00  ����ݳ�  ����ԭ��
                300487,//84.00  �����Ƽ�  ����ԭ��
                300505,//85.00  ����ŵ ����ԭ��
                300530,//86.00  ��־�Ƽ�  ����ԭ��
                300535,//87.00  �����ɷ�  ����ԭ��
                300568,//88.00  ��Դ����  ����ԭ��
                300586,//89.00  �����²�  ����ԭ��
                300596,//90.00  ����¡ ����ԭ��
                600075,//91.00  �½���ҵ  ����ԭ��
                600078,//92.00  ���ǹɷ�  ����ԭ��
                600091,//93.00  ST����  ����ԭ��
                600135,//94.00  �ֿ���Ƭ  ����ԭ��
                600141,//95.00  �˷�����  ����ԭ��
                600160,//96.00  �޻��ɷ�  ����ԭ��
                600228,//97.00  ��������  ����ԭ��
                600273,//98.00  �λ���Դ  ����ԭ��
                600281,//99.00  ̫���ɷ�  ����ԭ��
                600301,//100.00 *ST�ϻ� ����ԭ��
                600309,//101.00 �򻪻�ѧ  ����ԭ��
                600319,//102.00 *ST���� ����ԭ��
                600328,//103.00 ��̫ʵҵ  ����ԭ��
                600367,//104.00 ���Ƿ�չ  ����ԭ��
                600378,//105.00 ��ƹɷ�  ����ԭ��
                600409,//106.00 ���ѻ���  ����ԭ��
                600618,//107.00 �ȼ��  ����ԭ��
                600636,//108.00 ������ ����ԭ��
                600722,//109.00 ��ţ����  ����ԭ��
                600746,//110.00 ��������  ����ԭ��
                600844,//111.00 �����Ƽ�  ����ԭ��
                600985,//112.00 �����ƻ�  ����ԭ��
                601208,//113.00 ���ĿƼ�  ����ԭ��
                601216,//114.00 ��������  ����ԭ��
                601678,//115.00 �����ɷ�  ����ԭ��
                603002,//116.00 �������  ����ԭ��
                603010,//117.00 ��ʢ�ɷ�  ����ԭ��
                603026,//118.00 ʯ��ʤ��  ����ԭ��
                603067,//119.00 �񻪹ɷ�  ����ԭ��
                603077,//120.00 �Ͱ�����  ����ԭ��
                603227,//121.00 ѩ��Ƽ�  ����ԭ��
                603299,//122.00 ����ɷ�  ����ԭ��
                603585,//123.00 �����ɷ�  ����ԭ��
                603928,//124.00 ��ҵ�ɷ�  ����ԭ��
                603968,//125.00 �׻��ɷ�  ����ԭ��
                603977//126.00 ��̩����  ����ԭ��
    
    ));
    
    List<Integer> listCode_51_lyhf = new ArrayList<Integer>(Arrays.asList(
                9000422,//1.00  �����˻�  ũҩ����
                9000525,//2.00  �� ̫ �� ũҩ����
                9000553,//3.00  ɳ¡���  ũҩ����
                9000731,//4.00  �Ĵ�����  ũҩ����
                9000792,//5.00  �κ��ɷ�  ũҩ����
                9000830,//6.00  ³������  ũҩ����
                9000902,//7.00  ����� ũҩ����
                9000912,//8.00  ���컯 ũҩ����
                9000950,//9.00  *ST���� ũҩ����
                9000953,//10.00 �ӳػ���  ũҩ����
                9002018,//11.00 ���Ź���  ũҩ����
                9002170,//12.00 ����ɷ�  ũҩ����
                9002215,//13.00 ŵ �� �� ũҩ����
                9002258,//14.00 ������ѧ  ũҩ����
                9002274,//15.00 ��������  ũҩ����
                9002391,//16.00 ����ɷ�  ũҩ����
                9002470,//17.00 ������ ũҩ����
                9002496,//18.00 �Է�ɷ�  ũҩ����
                9002513,//19.00 *ST���� ũҩ����
                9002538,//20.00 ˾���� ũҩ����
                9002539,//21.00 ��ͼ�ع�  ũҩ����
                9002588,//22.00 ʷ���� ũҩ����
                9002734,//23.00 ����ɷ�  ũҩ����
                9002749,//24.00 ����ɷ�  ũҩ����
                300261,//25.00  �ű���ѧ  ũҩ����
                300575,//26.00  ����ɷ�  ũҩ����
                9000155,//27.00 *ST���� ũҩ����
                600096,//28.00  ���컯 ũҩ����
                600226,//29.00  �����ݿ�  ũҩ����
                600227,//30.00  ���컯 ũҩ����
                600230,//31.00  *ST�״� ũҩ����
                600389,//32.00  ��ɽ�ɷ�  ũҩ����
                600423,//33.00  �����ɷ�  ũҩ����
                600426,//34.00  ��³����  ũҩ����
                600470,//35.00  ��������  ũҩ����
                600486,//36.00  ��ũ����  ũҩ����
                600538,//37.00  �����ɷ�  ũҩ����
                600596,//38.00  �°��ɷ�  ũҩ����
                600691,//39.00  ��ú����  ũҩ����
                600727,//40.00  ³������  ũҩ����
                600731,//41.00  ���Ϻ���  ũҩ����
                600796,//42.00  Ǯ������  ũҩ����
                600803,//43.00  �°¹ɷ�  ũҩ����
                603599,//44.00  ���Źɷ�  ũҩ����
                603639//45.00  ������ ũҩ����
    ));
    
    List<Integer> listCode_52_sl = new ArrayList<Integer>(Arrays.asList(
              9000859,//1.00  ������ҵ  ����
              9000973,//2.00  ���ܿƼ�  ����
              9002014,//3.00  ���¹ɷ�  ����
              9002108,//4.00  ��������  ����
              9002243,//5.00  ͨ������  ����
              9002263,//6.00  �� �� �� ����
              9002324,//7.00  ������ ����
              9002395,//8.00  ˫��ɷ�  ����
              9002420,//9.00  ����ɷ�  ����
              9002457,//10.00 ������ҵ  ����
              9002522,//11.00 �㽭�ڳ�  ����
              9002585,//12.00 ˫���²�  ����
              9002641,//13.00 ���߹ɷ�  ����
              9002676,//14.00 ˳���ɷ�  ����
              9002694,//15.00 �˵ؿƼ�  ����
              9002735,//16.00 �����²�  ����
              9002768,//17.00 �����ɷ�  ����
              300169,//18.00  �����²�  ����
              300198,//19.00  �ɴ��ɷ�  ����
              300218,//20.00  �����ɷ�  ����
              300221,//21.00  �����Ƽ�  ����
              300230,//22.00  �����ɷ�  ����
              300305,//23.00  ԣ�˹ɷ�  ����
              300321,//24.00  ͬ��ɷ�  ����
              300325,//25.00  �����²�  ����
              300393,//26.00  �����ɷ�  ����
              300539,//27.00  ���ģ��  ����
              9002838,//28.00  �����ɷ�  ����
              600143,//29.00  �𷢿Ƽ�  ����
              600146,//30.00  ��Ӯ����  ����
              600444,//31.00  ����ͨ��  ����
              600458,//32.00  ʱ���²�  ����
              600589,//33.00  �㶫��̩  ����
              603806,//34.00  ��˹�� ����
              603266//35.00  �����ɷ�  ����
              
    ));
    
    List<Integer> listCode_53_xj = new ArrayList<Integer>(Arrays.asList(
            9000887,//1.00  �ж��ɷ�  ��
            9002211,//2.00  ����²�  ��
            9002224,//3.00  �� �� ʿ ��
            9002381,//4.00  ˫���ɷ�  ��
            300031,//5.00 ��ͨ�Ƽ�  ��
            300320,//6.00 ����ɷ�  ��
            300478,//7.00 ���ݸ���  ��
            300547,//8.00 �����Ƽ�  ��
            300587,//9.00 �����ɷ�  ��
            601118,//10.00  ������  ��
            603033//11.00  ��ά�ɷ�  ��
    ));
    
    List<Integer> listCode_54_yltl = new ArrayList<Integer>(Arrays.asList(
              
              9000565,//1.00  ����Ͽ��  Ⱦ��Ϳ��
              9002010,//2.00  ��������  Ⱦ��Ϳ��
              9002256,//3.00  ���¹ɷ�  Ⱦ��Ϳ��
              9002319,//4.00  ��ͨ�ɷ�  Ⱦ��Ϳ��
              9002440,//5.00  �����ɷ�  Ⱦ��Ϳ��
              300063,//6.00 ��������  Ⱦ��Ϳ��
              300067,//7.00 ��ŵ�� Ⱦ��Ϳ��
              300192,//8.00 ��˹���  Ⱦ��Ϳ��
              300225,//9.00 ����̩ Ⱦ��Ϳ��
              300236,//10.00  �Ϻ�����  Ⱦ��Ϳ��
              300398,//11.00  �ɿ�����  Ⱦ��Ϳ��
              300522,//12.00  �����Ƽ�  Ⱦ��Ϳ��
              300537,//13.00  ���Ų���  Ⱦ��Ϳ��
              300576,//14.00  �ݴ�й�  Ⱦ��Ϳ��
              600352,//15.00  �㽭��ʢ  Ⱦ��Ϳ��
              603188,//16.00  �ǰ�ɷ�  Ⱦ��Ϳ��
              603737,//17.00  ������ Ⱦ��Ϳ��
              603823//18.00  �ٺϻ� Ⱦ��Ϳ��
    
    ));
    
    List<Integer> listCode_55_tc = new ArrayList<Integer>(Arrays.asList(
            300089,//1.00 �Ļ�����  �մ�
            300234,//2.00 �����²�  �մ�
            300285,//3.00 ���ɲ���  �մ�
            300409,//4.00 ���ϼ���  �մ�
            600145,//5.00 *ST���� �մ�
            603268,//6.00 �ɷ��ɷ�  �մ�
            603838//7.00 ��ͨ�ɷ�  �մ�

    ));
    
    
    List<Integer> listCode_56_sn = new ArrayList<Integer>(Arrays.asList(
              9000401,//1.00  ����ˮ��  ˮ��
              9000546,//2.00  ��Բ�ɷ�  ˮ��
              9000672,//3.00  �Ϸ�ˮ��  ˮ��
              9000789,//4.00  ������ ˮ��
              9000877,//5.00  ��ɽ�ɷ�  ˮ��
              9000885,//6.00  ͬ��ˮ��  ˮ��
              9000935,//7.00  �Ĵ�˫��  ˮ��
              9002233,//8.00  ���Ƽ���  ˮ��
              9002302,//9.00  ��������  ˮ��
              9002619,//10.00 ������ҵ  ˮ��
              600425,//11.00  ���ɽ���  ˮ��
              600449,//12.00  ���Ľ���  ˮ��
              600539,//13.00  STʨͷ  ˮ��
              600585,//14.00  ����ˮ��  ˮ��
              600668,//15.00  ��弯��  ˮ��
              600678,//16.00  �Ĵ���  ˮ��
              600720,//17.00  ����ɽ ˮ��
              600801,//18.00  ����ˮ��  ˮ��
              600802,//19.00  ����ˮ��  ˮ��
              600881,//20.00  ��̩����  ˮ��
              600883,//21.00  ���ſƼ�  ˮ��
              601992//22.00  ����ɷ�  ˮ��

    ));
    
    List<Integer> listCode_57_bl = new ArrayList<Integer>(Arrays.asList(
    9000012,//1.00  �� ����  ����
    9002201,//2.00  �Ŷ��²�  ����
    9002571,//3.00  �����ɷ�  ����
    9002623,//4.00  ����� ����
    300093,//5.00 ��ղ���  ����
    300160,//6.00 ��ǿ�ɷ�  ����
    300196,//7.00 �����ɷ�  ����
    300395,//8.00 ������ ����
    600176,//9.00 �й���ʯ  ����
    600293,//10.00  ��Ͽ�²�  ����
    600529,//11.00  ɽ��ҩ��  ����
    600552,//12.00  ��ʢ�Ƽ�  ����
    600586,//13.00  �𾧿Ƽ�  ����
    600819,//14.00  ҫƤ����  ����
    600876,//15.00  ��������  ����
    601636,//16.00  �������  ����
    603021,//17.00  ɽ������  ����
    603601//18.00  �����Ƽ�  ����
    ));
    
    List<Integer> listCode_58_qtjc = new ArrayList<Integer>(Arrays.asList(

      9000023,//1.00  ����أ�  ��������
      9000055,//2.00  ������  ��������
      9000509,//3.00  ���ܿع�  ��������
      9000619,//4.00  �����Ͳ�  ��������
      9000786,//5.00  ���½���  ��������
      9002043,//6.00  �� �� �� ��������
      9002066,//7.00  ��̩�Ƽ�  ��������
      9002205,//8.00  ��ͳ�ɷ�  ��������
      9002225,//9.00  ��͹ɷ�  ��������
      9002271,//10.00 �������  ��������
      9002372,//11.00 ΰ���²�  ��������
      9002392,//12.00 ��������  ��������
      9002398,//13.00 ���м���  ��������
      9002596,//14.00 ��������  ��������
      9002652,//15.00 �����²�  ��������
      9002671,//16.00 ��Ȫ�ɷ�  ��������
      9002742,//17.00 ��ʥ�ɷ�  ��������
      9002785,//18.00 ����ʯ ��������
      9002791,//19.00 �������  ��������
      300163,//20.00  �ȷ��²�  ��������
      300344,//21.00  ̫�հ�ҵ  ��������
      300374,//22.00  ��ͨ�Ƽ�  ��������
      600076,//23.00  �����²�  ��������
      600155,//24.00  ��˶�ɷ�  ��������
      600321,//25.00  ��������  ��������
      600634,//26.00  �м��ع�  ��������
      603616,//27.00  ������ɽ  ��������
      603969//28.00  �����ɷ�  ��������

    ));
    
    List<Integer> listCode_59_zzy = new ArrayList<Integer>(Arrays.asList(

        9000713,//1.00  ������ҵ  ��ֲҵ
        9000998,//2.00  ¡ƽ�߿�  ��ֲҵ
        9002041,//3.00  �Ǻ���ҵ  ��ֲҵ
        9002772,//4.00  ���˾�ҵ  ��ֲҵ
        300087,//5.00 �����߿�  ��ֲҵ
        300143,//6.00 �Ǻ�����  ��ֲҵ
        300189,//7.00 ��ũ����  ��ֲҵ
        300511,//8.00 ѩ������  ��ֲҵ
        600313,//9.00 ũ����ҵ  ��ֲҵ
        600354,//10.00  �ػ���ҵ  ��ֲҵ
        600371,//11.00  �����ũ  ��ֲҵ
        600506,//12.00  ����ɷ�  ��ֲҵ
        600540,//13.00  �����ɷ�  ��ֲҵ
        600598//14.00  ����� ��ֲҵ
    ));
    
    List<Integer> listCode_60_yy = new ArrayList<Integer>(Arrays.asList(
          9000798,//1.00  ��ˮ��ҵ  ��ҵ
          9002069,//2.00  *ST⯵� ��ҵ
          9002086,//3.00  ��������  ��ҵ
          9002447,//4.00  Ҽ�Źɷ�  ��ҵ
          9002696,//5.00  ����ɷ�  ��ҵ
          300094,//6.00 ����ˮ��  ��ҵ
          600097,//7.00 ��������  ��ҵ
          600257,//8.00 ����ɷ�  ��ҵ
          600467//9.00 �õ��� ��ҵ
    ));
    List<Integer> listCode_61_ly = new ArrayList<Integer>(Arrays.asList(
      9000592,//1.00  ƽ̶��չ  ��ҵ
      9000663,//2.00  ������ҵ  ��ҵ
      9002679,//3.00  ������ɭ  ��ҵ
      600189,//4.00 ����ɭ��  ��ҵ
      600265,//5.00 *ST���� ��ҵ
      601996//6.00 ���ּ���  ��ҵ
    ));
    List<Integer> listCode_62_sl = new ArrayList<Integer>(Arrays.asList(
        9000048,//1.00  ����� ����
        9000702,//2.00  ����Ƽ�  ����
        9000876,//3.00  �� ϣ �� ����
        9002100,//4.00  �쿵����  ����
        9002124,//5.00  ���ɷ�  ����
        9002157,//6.00  ����Ƽ�  ����
        9002311,//7.00  ������  ����
        9002385,//8.00  ��ũ ����
        9002548,//9.00  ����ũ ����
        9002567,//10.00 ������ ����
        300381,//11.00  ����� ����
        600195,//12.00  �����ɷ�  ����
        600438,//13.00  ͨ���ɷ�  ����
        603609//14.00  �̷���ҵ  ����
    
    ));
    List<Integer> listCode_63_lyzh = new ArrayList<Integer>(Arrays.asList(
    9000061,//1.00  ũ �� Ʒ ũҵ�ۺ�
    9000735,//2.00  �� ţ ɽ ũҵ�ۺ�
    9000930,//3.00  ��������  ũҵ�ۺ�
    9002173,//4.00  *ST���� ũҵ�ۺ�
    9002234,//5.00  ��͹ɷ�  ũҵ�ۺ�
    9002299,//6.00  ʥũ��չ  ũҵ�ۺ�
    9002321,//7.00  ��Ӣũҵ  ũҵ�ۺ�
    9002458,//8.00  �����ɷ�  ũҵ�ۺ�
    9002477,//9.00  ��ӥũ��  ũҵ�ۺ�
    9002505,//10.00 ��ũҵ  ũҵ�ۺ�
    9002714,//11.00 ��ԭ�ɷ�  ũҵ�ۺ�
    9002746,//12.00 ��̳�ɷ�  ũҵ�ۺ�
    300021,//13.00  �����ˮ  ũҵ�ۺ�
    300106,//14.00  ������ҵ  ũҵ�ۺ�
    300268,//15.00  ������  ũҵ�ۺ�
    300313,//16.00  ��ɽ����  ũҵ�ۺ�
    300498,//17.00  ���Ϲɷ�  ũҵ�ۺ�
    600108,//18.00  ��ʢ����  ũҵ�ۺ�
    600127,//19.00  ����ҵ  ũҵ�ۺ�
    600251,//20.00  ��ũ�ɷ�  ũҵ�ۺ�
    600275,//21.00  ����� ũҵ�ۺ�
    600359,//22.00  ��ũ����  ũҵ�ۺ�
    600965,//23.00  ���ɹɷ�  ũҵ�ۺ�
    600975,//24.00  ����� ũҵ�ۺ�
    603336,//25.00  ��Թ���  ũҵ�ۺ�
    603668//26.00  ����Ƽ�  ũҵ�ۺ�
    ));
    
    List<Integer> listCode_64_fz = new ArrayList<Integer>(Arrays.asList(
    9000045,//1.00  ���֯��  ��֯
    9000158,//2.00  ��ɽ�ɷ�  ��֯
    9000611,//3.00  *ST���� ��֯
    9000726,//4.00  ³ ̩��  ��֯
    9000779,//5.00  ��ë����  ��֯
    9000803,//6.00  �����  ��֯
    9000850,//7.00  ��ï�ɷ�  ��֯
    9000955,//8.00  �����ع�  ��֯
    9000982,//9.00  ������ҵ  ��֯
    9002034,//10.00 �� �� �� ��֯
    9002042,//11.00 ����ɫ��  ��֯
    9002070,//12.00 �ں͹ɷ�  ��֯
    9002072,//13.00 ����� ��֯
    9002083,//14.00 ���չɷ�  ��֯
    9002087,//15.00 ��Ұ��֯  ��֯
    9002144,//16.00 ���߿�  ��֯
    9002193,//17.00 ɽ������  ��֯
    9002293,//18.00 ��������  ��֯
    9002327,//19.00 ������ ��֯
    9002394,//20.00 �����ɷ�  ��֯
    9002397,//21.00 �ν�ɷ�  ��֯
    9002404,//22.00 ����˿��  ��֯
    9002516,//23.00 ����Ƽ�  ��֯
    9002674,//24.00 ��ҵ�Ƽ�  ��֯
    9002761,//25.00 ��ϲ�� ��֯
    300577,//26.00  ����ɷ�  ��֯
    600070,//27.00  �㽭����  ��֯
    600152,//28.00  ά�ƾ���  ��֯
    600156,//29.00  �����ɷ�  ��֯
    600220,//30.00  ��������  ��֯
    600232,//31.00  ��ӥ�ɷ�  ��֯
    600370,//32.00  ������ ��֯
    600448,//33.00  ���Ĺɷ�  ��֯
    600493,//34.00  �����֯  ��֯
    600626,//35.00  ���ɷ�  ��֯
    600630,//36.00  ��ͷ�ɷ�  ��֯
    600689,//37.00  �Ϻ���ë  ��֯
    600851,//38.00  �����ɷ�  ��֯
    600987,//39.00  ����ɷ�  ��֯
    601339,//40.00  ��¡����  ��֯
    603558,//41.00  ��ʢ����  ��֯
    603889//42.00  �°Ĺɷ�  ��֯
    ));
    
    List<Integer> listCode_65_fs = new ArrayList<Integer>(Arrays.asList(

        9002003,//1.00  ΰ�ǹɷ�  ����
        9002029,//2.00  �� ƥ �� ����
        9002098,//3.00  ��˹ɷ�  ����
        9002154,//4.00  �� ϲ �� ����
        9002269,//5.00  �������  ����
        9002291,//6.00  ������ ����
        9002345,//7.00  ����� ����
        9002356,//8.00  ��������  ����
        9002425,//9.00  �����Ļ�  ����
        9002485,//10.00 ϣŬ�� ����
        9002486,//11.00 ����� ����
        9002494,//12.00 ��˹�ɷ�  ����
        9002503,//13.00 ������ ����
        9002563,//14.00 ɭ�����  ����
        9002569,//15.00 ��ɭ�ɷ�  ����
        9002574,//16.00 �����鱦  ����
        9002612,//17.00 ���˹ɷ�  ����
        9002634,//18.00 ���ܹɷ�  ����
        9002640,//19.00 �羳ͨ ����
        9002656,//20.00 Ħ�Ǵ��  ����
        9002687,//21.00 ���ΰ� ����
        9002699,//22.00 ��ʢ�Ļ�  ����
        9002721,//23.00 ��һ�Ļ�  ����
        9002731,//24.00 �ͻ��鱦  ����
        9002740,//25.00 ���϶� ����
        9002762,//26.00 ������  ����
        9002763,//27.00 ���ɷ�  ����
        9002776,//28.00 �ر��� ����
        9002832,//29.00 �����շ�  ����
        300005,//30.00  ̽·�� ����
        300591,//31.00  ������ ����
        600086,//32.00  ��������  ����
        600107,//33.00  ������ ����
        600137,//34.00  ��ɯ�ɷ�  ����
        600177,//35.00  �Ÿ�� ����
        600272,//36.00  ����ʵҵ  ����
        600295,//37.00  ������˹  ����
        600398,//38.00  ����֮��  ����
        600400,//39.00  �춹�ɷ�  ����
        600439,//40.00  �𱴿� ����
        600612,//41.00  �Ϸ��� ����
        600884,//42.00  ɼɼ�ɷ�  ����
        601566,//43.00  ������ ����
        601718,//44.00  �ʻ�����  ����
        603001,//45.00  �¿�����  ����
        603116,//46.00  ������ ����
        603518,//47.00  ά����˿  ����
        603555,//48.00  ������ ����
        603608,//49.00  �촴ʱ��  ����
        603808,//50.00  ����˼ ����
        603900,//51.00  ͨ���鱦  ����
        603958,//52.00  ��ɭ�ɷ�  ����
        603877//53.00  ̫ƽ�� ����
    ));
    
    List<Integer> listCode_66_rzp = new ArrayList<Integer>(Arrays.asList(
        9002329,//1.00  ���ϼ���  ����Ʒ
        9002570,//2.00  ������ ����Ʒ
        9002719,//3.00  ��Ȥ�� ����Ʒ
        9002732,//4.00  ������ҵ  ����Ʒ
        9002770,//5.00  �Ƶ���ҵ  ����Ʒ
        600419,//6.00 ������ҵ  ����Ʒ
        600429,//7.00 ��Ԫ�ɷ�  ����Ʒ
        600597,//8.00 ������ҵ  ����Ʒ
        600882,//9.00 ����ɷ�  ����Ʒ
        600887//10.00  �����ɷ�  ����Ʒ
    ));
    
    List<Integer> listCode_67_ryl =new ArrayList<Integer>( Arrays.asList(
        9000019,//1.00  �����  ������
        9000848,//2.00  �е�¶¶  ������
        9002387,//3.00  ��ţʳƷ  ������
        600300,//4.00 άά�ɷ�  ������
        600962//5.00 ��Ͷ��³  ������
    ));
    List<Integer> listCode_68_sp = new ArrayList<Integer>(Arrays.asList(
        9000529,//1.00  ���ع�  ʳƷ
        9000639,//2.00  ����ʳƷ  ʳƷ
        9000716,//3.00  ��֥�� ʳƷ
        9000893,//4.00  �������  ʳƷ
        9000895,//5.00  ˫�㷢չ  ʳƷ
        9000911,//6.00  ������ҵ  ʳƷ
        9000972,//7.00  �л�����  ʳƷ
        9002053,//8.00  ������Ͷ  ʳƷ
        9002216,//9.00  ��ȫʳƷ  ʳƷ
        9002220,//10.00 �챦�ɷ�  ʳƷ
        9002286,//11.00 ���䱦 ʳƷ
        9002330,//12.00 ����˹ ʳƷ
        9002481,//13.00 ˫��ʳƷ  ʳƷ
        9002495,//14.00 ��¡�ɷ�  ʳƷ
        9002507,//15.00 ����ե��  ʳƷ
        9002515,//16.00 ���ֻ���  ʳƷ
        9002557,//17.00 ǢǢʳƷ  ʳƷ
        9002582,//18.00 ������ ʳƷ
        9002604,//19.00 ��������  ʳƷ
        9002626,//20.00 ����� ʳƷ
        9002650,//21.00 �Ӽ�ʳƷ  ʳƷ
        9002661,//22.00 ������ҵ  ʳƷ
        9002695,//23.00 ���ϻ� ʳƷ
        9002702,//24.00 ����ʳƷ  ʳƷ
        9002726,//25.00 ������ʳ  ʳƷ
        9002820,//26.00 ���� ʳƷ
        300138,//27.00  ��������  ʳƷ
        300146,//28.00  ��������  ʳƷ
        300149,//29.00  ���Ӹ߿�  ʳƷ
        300175,//30.00  ��Դ�ɷ�  ʳƷ
        300401,//31.00  ��԰����  ʳƷ
        9002840,//32.00 ��ͳ�ɷ�  ʳƷ
        600073,//33.00  �Ϻ�÷��  ʳƷ
        600186,//34.00  ��������  ʳƷ
        600191,//35.00  ����ʵҵ  ʳƷ
        600298,//36.00  ������ĸ  ʳƷ
        600305,//37.00  ��˳��ҵ  ʳƷ
        600737,//38.00  �����ͺ�  ʳƷ
        600866,//39.00  *ST�Ǻ� ʳƷ
        600872,//40.00  �о����  ʳƷ
        600873,//41.00  ÷������  ʳƷ
        603020,//42.00  ���չɷ�  ʳƷ
        603027,//43.00  ǧ��ζҵ  ʳƷ
        603288,//44.00  ����ζҵ  ʳƷ
        603696,//45.00  ����ʳƷ  ʳƷ
        603866,//46.00  �������  ʳƷ
        603886//47.00  Ԫ��ɷ�  ʳƷ
    ));
    
    List<Integer> listCode_69_bj = new ArrayList<Integer>(Arrays.asList(
    
    9000568,//1.00  �����Ͻ�  �׾�
    9000596,//2.00  �ž�����  �׾�
    9000799,//3.00  �ƹ�� �׾�
    9000858,//4.00  �� �� Һ �׾�
    9000860,//5.00  ˳��ũҵ  �׾�
    9000995,//6.00  *ST��̨ �׾�
    9002304,//7.00  ��ӹɷ�  �׾�
    9002646,//8.00  ��������  �׾�
    600197,//9.00 ������ �׾�
    600199,//10.00  �����Ӿ�  �׾�
    600519,//11.00  ����ę́  �׾�
    600559,//12.00  �ϰ׸ɾ�  �׾�
    600702,//13.00  �������  �׾�
    600779,//14.00  ˮ���� �׾�
    600809,//15.00  ɽ���ھ�  �׾�
    603198,//16.00  ӭ�ݹ���  �׾�
    603369,//17.00  ����Ե �׾�
    603589,//18.00  ���ӽ� �׾�
    603919//19.00  ��վ� �׾�
    
    ));
    List<Integer> listCode_70_pj = new ArrayList<Integer>(Arrays.asList(

        9000729,//1.00  �ྩơ��  ơ��
        9000752,//2.00  ���ط�չ  ơ��
        9000929,//3.00  ���ݻƺ�  ơ��
        9002461,//4.00  �齭ơ��  ơ��
        600132,//5.00 ����ơ��  ơ��
        600573,//6.00 ��Ȫơ��  ơ��
        600600//7.00 �ൺơ��  ơ��
    ));
    
    List<Integer> listCode_71_hhyj =new ArrayList<Integer>( Arrays.asList(
    9000557,//1.00  ������ҵ  ���ҩ��
    9000869,//2.00  �� ԣ��  ���ҩ��
    9002568,//3.00  ����ɷ�  ���ҩ��
    600059,//4.00 ��Խ��ɽ  ���ҩ��
    600084,//5.00 ���Ϲɷ�  ���ҩ��
    600238,//6.00 ����Ҭ��  ���ҩ��
    600365,//7.00 ͨ�Ϲɷ�  ���ҩ��
    600543,//8.00 Ī�߹ɷ�  ���ҩ��
    600616,//9.00 ����ҵ  ���ҩ��
    601579,//10.00  ���ɽ ���ҩ��
    603779//11.00  �����ɷ�  ���ҩ��
    
    ));
    List<Integer> listCode_72_qczc = new ArrayList<Integer>(Arrays.asList(
    9000550,//1.00  ��������  ��������
    9000572,//2.00  ��������  ��������
    9000625,//3.00  ��������  ��������
    9000800,//4.00  һ���γ�  ��������
    9000868,//5.00  �����ͳ�  ��������
    9000927,//6.00  һ������  ��������
    9000951,//7.00  �й�����  ��������
    9000957,//8.00  ��ͨ�ͳ�  ��������
    9002537,//9.00  ��������  ��������
    9002594,//10.00 ���ǵ� ��������
    600006,//11.00  ��������  ��������
    600066,//12.00  ��ͨ�ͳ�  ��������
    600104,//13.00  ��������  ��������
    600166,//14.00  ��������  ��������
    600213,//15.00  ���ǿͳ�  ��������
    600262,//16.00  �����ɷ�  ��������
    600303,//17.00  ���ɷ�  ��������
    600375,//18.00  *ST���� ��������
    600418,//19.00  ��������  ��������
    600609,//20.00  ������  ��������
    600686,//21.00  ��������  ��������
    600760,//22.00  *ST�ڱ� ��������
    601238,//23.00  ��������  ��������
    601633//24.00  ��������  ��������
    ));
    
    
    List<Integer> listCode_73_qcpj =new ArrayList<Integer>( Arrays.asList(
        9000030,//1.00  ���¹ɷ�  �������
        9000338,//2.00  Ϋ����  �������
        9000559,//3.00  ����Ǯ��  �������
        9000581,//4.00  ���ڸ߿�  �������
        9000589,//5.00  ǭ��̥��  �������
        9000599,//6.00  �ൺ˫��  �������
        9000622,//7.00  *ST���� �������
        9000700,//8.00  ģ�ܿƼ�  �������
        9000710,//9.00  �����Ǳ�  �������
        9000757,//10.00 ����ɷ�  �������
        9000760,//11.00 ˹̫�� �������
        9000980,//12.00 ����ɷ�  �������
        9002031,//13.00 ��������  �������
        9002048,//14.00 ��������  �������
        9002085,//15.00 ������  �������
        9002126,//16.00 ���ֹɷ�  �������
        9002190,//17.00 �ɷɼ���  �������
        9002213,//18.00 �� �� �� �������
        9002239,//19.00 ���ؼ� �������
        9002265,//20.00 ���ǹɷ�  �������
        9002283,//21.00 ��������  �������
        9002284,//22.00 ��̫�ɷ�  �������
        9002328,//23.00 ����ɷ�  �������
        9002355,//24.00 ������ͨ  �������
        9002363,//25.00 ¡����е  �������
        9002406,//26.00 Զ������  �������
        9002434,//27.00 ������ �������
        9002448,//28.00 ��ԭ����  �������
        9002454,//29.00 ��֥�ɷ�  �������
        9002488,//30.00 ��̹ɷ�  �������
        9002510,//31.00 ����ģ �������
        9002536,//32.00 ���ùɷ�  �������
        9002553,//33.00 �Ϸ����  �������
        9002590,//34.00 �򰲿Ƽ�  �������
        9002592,//35.00 ����Ƽ�  �������
        9002593,//36.00 ���ϼ���  �������
        9002602,//37.00 ���ͻ�ͨ  �������
        9002625,//38.00 �����ɷ�  �������
        9002662,//39.00 �����ɷ�  �������
        9002664,//40.00 ���ʵ��  �������
        9002703,//41.00 �㽭����  �������
        9002708,//42.00 ����ɷ�  �������
        9002715,//43.00 ���ƹɷ�  �������
        9002725,//44.00 Ծ��ɷ�  �������
        9002765,//45.00 ���촫��  �������
        9002766,//46.00 ����ɷ�  �������
        9002813,//47.00 ·���Ƽ�  �������
        300176,//48.00  ���ؾ���  �������
        300258,//49.00  ���ͿƼ�  �������
        300304,//50.00  �������  �������
        300375,//51.00  ����ɷ�  �������
        300432,//52.00  ���پ���  �������
        300473,//53.00  �¶��ɷ�  �������
        300507,//54.00  �հ´���  �������
        300580,//55.00  ��˹�� �������
        300585,//56.00  ��������  �������
        600081,//57.00  ����Ƽ�  �������
        600093,//58.00  �̼ιɷ�  �������
        600148,//59.00  ����һ��  �������
        600178,//60.00  ��������  �������
        600182,//61.00  S��ͨ �������
        600335,//62.00  ��������  �������
        600469,//63.00  ����ɷ�  �������
        600480,//64.00  ���ƹɷ�  �������
        600501,//65.00  ���쳿��  �������
        600523,//66.00  �󺽹ɷ�  �������
        600623,//67.00  ���꼯��  �������
        600660,//68.00  ��ҫ����  �������
        600698,//69.00  ��������  �������
        600699,//70.00  ��ʤ����  �������
        600741,//71.00  ��������  �������
        600742,//72.00  һ����ά  �������
        600960,//73.00  ��������  �������
        601058,//74.00  ���ֽ���  �������
        601127,//75.00  С���ɷ�  �������
        601163,//76.00  ������̥  �������
        601500,//77.00  ͨ�ùɷ�  �������
        601689,//78.00  ���ռ���  �������
        601799,//79.00  ����ɷ�  �������
        601966,//80.00  ������̥  �������
        603006,//81.00  �����ɷ�  �������
        603009,//82.00  ���ؿƼ�  �������
        603023,//83.00  ���۹ɷ�  �������
        603158,//84.00  �����ɷ�  �������
        603166,//85.00  ����ɷ�  �������
        603239,//86.00  N��ͨ �������
        603306,//87.00  ���Ƽ�  �������
        603319,//88.00  ���ͱ� �������
        603701,//89.00  �º�ɷ�  �������
        603788,//90.00  �����߷�  �������
        603997,//91.00  �̷�ɷ�  �������
        603035,//92.00  ��������  �������
        603037//93.00  ���ڹɷ�  �������
    
    ));
    
    List<Integer> listCode_74_qcfw =new ArrayList<Integer>( Arrays.asList(
    9000025,//1.00  �� ����  ��������
    9000753,//2.00  ���ݷ�չ  ��������
    9002607,//3.00  ��������  ��������
    300100,//4.00 ˫�ֹɷ�  ��������
    600297,//5.00 �������  ��������
    600653,//6.00 �껪�ع�  ��������
    601258,//7.00 �Ӵ���  ��������
    601965,//8.00 �й�����  ��������
    603377//9.00 ����ʱ��  ��������
    
    ));
    
    List<Integer> listCode_75_mtc = new ArrayList<Integer>(Arrays.asList(
          9000913,//1.00  *STǮ�� Ħ�г�
          9001696,//2.00  ���궯��  Ħ�г�
          600099,//3.00 �ֺ��ɷ�  Ħ�г�
          600877,//4.00 �й�����  Ħ�г�
          601777,//5.00 �����ɷ�  Ħ�г�
          603766//6.00 ¡��ͨ��  Ħ�г�
    
    ));
    
    List<Integer> listCode_76_hxzy = new ArrayList<Integer>(Arrays.asList(
                  9000153,//1.00  ��ԭҩҵ  ��ѧ��ҩ
                  9000566,//2.00  ���Ϻ�ҩ  ��ѧ��ҩ
                  9000597,//3.00  ������ҩ  ��ѧ��ҩ
                  9000606,//4.00  *ST���� ��ѧ��ҩ
                  9000739,//5.00  ����ҩҵ  ��ѧ��ҩ
                  9000756,//6.00  �»���ҩ  ��ѧ��ҩ
                  9000788,//7.00  ����ҽҩ  ��ѧ��ҩ
                  9000813,//8.00  ��չ����  ��ѧ��ҩ
                  9000908,//9.00  ����ҽҩ  ��ѧ��ҩ
                  9000915,//10.00 ɽ����  ��ѧ��ҩ
                  9000919,//11.00 ����ҩҵ  ��ѧ��ҩ
                  9000952,//12.00 ���ҩҵ  ��ѧ��ҩ
                  9000963,//13.00 ����ҽҩ  ��ѧ��ҩ
                  9002001,//14.00 �� �� �� ��ѧ��ҩ
                  9002004,//15.00 �����  ��ѧ��ҩ
                  9002019,//16.00 �ڷ�ҽҩ  ��ѧ��ҩ
                  9002020,//17.00 ����ҩҵ  ��ѧ��ҩ
                  9002099,//18.00 ����ҩҵ  ��ѧ��ҩ
                  9002102,//19.00 �ڸ��ɷ�  ��ѧ��ҩ
                  9002262,//20.00 ����ҩҵ  ��ѧ��ҩ
                  9002294,//21.00 ����̩ ��ѧ��ҩ
                  9002365,//22.00 ����ҩҵ  ��ѧ��ҩ
                  9002370,//23.00 ��̫ҩҵ  ��ѧ��ҩ
                  9002393,//24.00 ������ҩ  ��ѧ��ҩ
                  9002399,//25.00 ������ ��ѧ��ҩ
                  9002411,//26.00 �ؿ��ɷ�  ��ѧ��ҩ
                  9002422,//27.00 ����ҩҵ  ��ѧ��ҩ
                  9002437,//28.00 ����ҩҵ  ��ѧ��ҩ
                  9002653,//29.00 ��˼�� ��ѧ��ҩ
                  9002675,//30.00 ����ҩҵ  ��ѧ��ҩ
                  9002688,//31.00 �������  ��ѧ��ҩ
                  9002693,//32.00 ˫��ҩҵ  ��ѧ��ҩ
                  9002817,//33.00 ��ɽ����  ��ѧ��ҩ
                  9002826,//34.00 ����ҽҩ  ��ѧ��ҩ
                  300006,//35.00  ����ҩҵ  ��ѧ��ҩ
                  300086,//36.00  ��֥ҩҵ  ��ѧ��ҩ
                  300110,//37.00  ����ҩҵ  ��ѧ��ҩ
                  300194,//38.00  ����ҩҵ  ��ѧ��ҩ
                  300199,//39.00  ����ҩҵ  ��ѧ��ҩ
                  300233,//40.00  ���ҽҩ  ��ѧ��ҩ
                  300254,//41.00  ǪԴҽҩ  ��ѧ��ҩ
                  300267,//42.00  ������ҩ  ��ѧ��ҩ
                  300363,//43.00  ���ڹɷ�  ��ѧ��ҩ
                  300436,//44.00  ������ ��ѧ��ҩ
                  300452,//45.00  ɽ��ҩ��  ��ѧ��ҩ
                  300497,//46.00  ����ɷ�  ��ѧ��ҩ
                  300558,//47.00  ����ҩҵ  ��ѧ��ҩ
                  300573,//48.00  ������ҩ  ��ѧ��ҩ
                  300584,//49.00  ����ҩҵ  ��ѧ��ҩ
                  600062,//50.00  ����˫��  ��ѧ��ҩ
                  600079,//51.00  �˸�ҽҩ  ��ѧ��ҩ
                  600196,//52.00  ����ҽҩ  ��ѧ��ҩ
                  600216,//53.00  �㽭ҽҩ  ��ѧ��ҩ
                  600267,//54.00  ����ҩҵ  ��ѧ��ҩ
                  600276,//55.00  ����ҽҩ  ��ѧ��ҩ
                  600299,//56.00  ������ ��ѧ��ҩ
                  600380,//57.00  ����Ԫ ��ѧ��ҩ
                  600420,//58.00  �ִ���ҩ  ��ѧ��ҩ
                  600488,//59.00  ��ҩ�ɷ�  ��ѧ��ҩ
                  600513,//60.00  ����ҩҵ  ��ѧ��ҩ
                  600521,//61.00  ����ҩҵ  ��ѧ��ҩ
                  600664,//62.00  ��ҩ�ɷ�  ��ѧ��ҩ
                  600789,//63.00  ³��ҽҩ  ��ѧ��ҩ
                  600812,//64.00  ������ҩ  ��ѧ��ҩ
                  603168,//65.00  ɯ�հ�˼  ��ѧ��ҩ
                  603222,//66.00  ������ҩ  ��ѧ��ҩ
                  603456,//67.00  ����ҩҵ  ��ѧ��ҩ
                  603520,//68.00  ˾̫�� ��ѧ��ҩ
                  603669//69.00  �鿵ҩҵ  ��ѧ��ҩ
    
    ));
    
    List<Integer> listCode_77_swzy = new ArrayList<Integer>(Arrays.asList(
    9000004,//1.00  ��ũ�Ƽ�  ������ҩ
    9000078,//2.00  ��������  ������ҩ
    9000403,//3.00  ST����  ������ҩ
    9000518,//4.00  �Ļ�����  ������ҩ
    9000661,//5.00  ��������  ������ҩ
    9000806,//6.00  ��������  ������ҩ
    9002007,//7.00  ��������  ������ҩ
    9002030,//8.00  �ﰲ����  ������ҩ
    9002038,//9.00  ˫��ҩҵ  ������ҩ
    9002252,//10.00 �Ϻ���ʿ  ������ҩ
    9002332,//11.00 �����ҩ  ������ҩ
    9002550,//12.00 ǧ����ҩ  ������ҩ
    9002581,//13.00 δ��ҽҩ  ������ҩ
    9002680,//14.00 ��������  ������ҩ
    9002821,//15.00 ����Ӣ ������ҩ
    300009,//16.00  ��������  ������ҩ
    300119,//17.00  ��������  ������ҩ
    300122,//18.00  �Ƿ�����  ������ҩ
    300142,//19.00  ��ɭ����  ������ҩ
    300204,//20.00  ��̩�� ������ҩ
    300239,//21.00  ��������  ������ҩ
    300255,//22.00  ��ɽҩҵ  ������ҩ
    300289,//23.00  ������ ������ҩ
    300294,//24.00  ��������  ������ҩ
    300357,//25.00  ��������  ������ҩ
    300406,//26.00  ��ǿ����  ������ҩ
    300482,//27.00  ��������  ������ҩ
    300485,//28.00  ����ҩҵ  ������ҩ
    300583,//29.00  ��������  ������ҩ
    600161,//30.00  ��̳����  ������ҩ
    600201,//31.00  ����ɷ�  ������ҩ
    600645,//32.00  ��ԴЭ��  ������ҩ
    600867,//33.00  ͨ������  ������ҩ
    603566,//34.00  ������ ������ҩ
    603718//35.00  ��������  ������ҩ
    
    ));
    
    
    List<Integer> listCode_78_zcy = new ArrayList<Integer>(Arrays.asList(
    9000423,//1.00  ��������  �г�ҩ
    9000513,//2.00  ���鼯��  �г�ҩ
    9000538,//3.00  ���ϰ�ҩ  �г�ҩ
    9000590,//4.00  ���Ϲź�  �г�ҩ
    9000623,//5.00  ���ְ���  �г�ҩ
    9000650,//6.00  �ʺ�ҩҵ  �г�ҩ
    9000766,//7.00  ͨ������  �г�ҩ
    9000790,//8.00  ̩�Ͻ���  �г�ҩ
    9000989,//9.00  �� ֥ �� �г�ҩ
    9000999,//10.00 ��������  �г�ҩ
    9002107,//11.00 �ֻ�ҽҩ  �г�ҩ
    9002118,//12.00 ����ҩҵ  �г�ҩ
    9002166,//13.00 ��������  �г�ҩ
    9002198,//14.00 ��Ӧ��ҩ  �г�ҩ
    9002219,//15.00 �㿵ҽ��  �г�ҩ
    9002275,//16.00 ��������  �г�ҩ
    9002287,//17.00 ������ҩ  �г�ҩ
    9002317,//18.00 ����ҩҵ  �г�ҩ
    9002349,//19.00 ������ҩ  �г�ҩ
    9002390,//20.00 �Ű���ҩ  �г�ҩ
    9002412,//21.00 ��ɭ��ҩ  �г�ҩ
    9002424,//22.00 ���ݰ���  �г�ҩ
    9002433,//23.00 ̫���� �г�ҩ
    9002566,//24.00 ��ʢҩҵ  �г�ҩ
    9002603,//25.00 ����ҩҵ  �г�ҩ
    9002644,//26.00 �����ҩ  �г�ҩ
    9002728,//27.00 ��һҩҵ  �г�ҩ
    9002737,//28.00 ����ҩҵ  �г�ҩ
    9002750,//29.00 ����ҩҵ  �г�ҩ
    9002773,//30.00 ����ҩҵ  �г�ҩ
    300016,//31.00  ��½ҩҵ  �г�ҩ
    300026,//32.00  ����ҩҵ  �г�ҩ
    300039,//33.00  �Ϻ�����  �г�ҩ
    300049,//34.00  ����ɷ�  �г�ҩ
    300108,//35.00  ˫���ɷ�  �г�ҩ
    300147,//36.00  ��ѩ��ҩ  �г�ҩ
    300158,//37.00  ����ҩ  �г�ҩ
    300181,//38.00  ����ҩҵ  �г�ҩ
    300519,//39.00  �¹�ҩҵ  �г�ҩ
    300534,//40.00  ¤���ַ�  �г�ҩ
    600080,//41.00  �𻨹ɷ�  �г�ҩ
    600085,//42.00  ͬ���� �г�ҩ
    600129,//43.00  ̫������  �г�ҩ
    600211,//44.00  ����ҩҵ  �г�ҩ
    600222,//45.00  ̫��ҩҵ  �г�ҩ
    600252,//46.00  �к㼯��  �г�ҩ
    600285,//47.00  ������ҩ  �г�ҩ
    600329,//48.00  ����ҩҵ  �г�ҩ
    600332,//49.00  ����ɽ �г�ҩ
    600351,//50.00  �Ǳ�ҩҵ  �г�ҩ
    600422,//51.00  ��ҩ����  �г�ҩ
    600436,//52.00  Ƭ��� �г�ҩ
    600479,//53.00  ǧ��ҩҵ  �г�ҩ
    600518,//54.00  ����ҩҵ  �г�ҩ
    600535,//55.00  ��ʿ�� �г�ҩ
    600557,//56.00  ��Եҩҵ  �г�ҩ
    600566,//57.00  �ô�ҩҵ  �г�ҩ
    600572,//58.00  ������ �г�ҩ
    600594,//59.00  �����ҩ  �г�ҩ
    600613,//60.00  ������ҩ  �г�ҩ
    600671,//61.00  ��Ŀҩҵ  �г�ҩ
    600750,//62.00  ����ҩҵ  �г�ҩ
    600771,//63.00  ����Զ �г�ҩ
    600781,//64.00  ����ҩҵ  �г�ҩ
    600976,//65.00  ������  �г�ҩ
    600993,//66.00  ��Ӧ�� �г�ҩ
    603567,//67.00  �䱦�� �г�ҩ
    603858,//68.00  ������ҩ  �г�ҩ
    603998//69.00  ��ʢ��ҩ  �г�ҩ
    ));
    
    List<Integer> listCode_79_bh = new ArrayList<Integer>(Arrays.asList(

          9000417,//1.00  �Ϸʰٻ�  �ٻ�
          9000419,//2.00  ͨ�̿ع�  �ٻ�
          9000501,//3.00  �����̣�  �ٻ�
          9000516,//4.00  ����ҽѧ  �ٻ�
          9000560,//5.00  ���ٴ��  �ٻ�
          9000564,//6.00  ��������  �ٻ�
          9000679,//7.00  ��������  �ٻ�
          9000715,//8.00  ������ҵ  �ٻ�
          9000785,//9.00  �人����  �ٻ�
          9000882,//10.00 �����ɷ�  �ٻ�
          9002187,//11.00 ��ٹɷ�  �ٻ�
          9002277,//12.00 �Ѱ��ɷ�  �ٻ�
          9002419,//13.00 ����̳�  �ٻ�
          9002561,//14.00 ��һ� �ٻ�
          300413,//15.00  ���ֹ� �ٻ�
          600280,//16.00  �����̳�  �ٻ�
          600306,//17.00  *ST�̳� �ٻ�
          600327,//18.00  �󶫷� �ٻ�
          600515,//19.00  ��������  �ٻ�
          600628,//20.00  ������ �ٻ�
          600655,//21.00  ԥ԰�̳�  �ٻ�
          600682,//22.00  �Ͼ��°�  �ٻ�
          600693,//23.00  ���ټ���  �ٻ�
          600694,//24.00  ���̹ɷ�  �ٻ�
          600697,//25.00  ŷ�Ǽ���  �ٻ�
          600712,//26.00  �����ٻ�  �ٻ�
          600723,//27.00  ���̹ɷ�  �ٻ�
          600729,//28.00  ����ٻ�  �ٻ�
          600738,//29.00  �������  �ٻ�
          600774,//30.00  ���̼���  �ٻ�
          600778,//31.00  �Ѻü���  �ٻ�
          600785,//32.00  �»��ٻ�  �ٻ�
          600814,//33.00  ���ݽ��  �ٻ�
          600821,//34.00  ��Ȱҵ �ٻ�
          600824,//35.00  ������  �ٻ�
          600828,//36.00  ïҵ��ҵ  �ٻ�
          600838,//37.00  �Ϻ��Ű�  �ٻ�
          600857,//38.00  �����а�  �ٻ�
          600858,//39.00  �����ɷ�  �ٻ�
          600859,//40.00  ������ �ٻ�
          600861,//41.00  ��������  �ٻ�
          600865,//42.00  �ٴ���  �ٻ�
          600891,//43.00  ���ּ���  �ٻ�
          603031,//44.00  ������ �ٻ�
          603101,//45.00  ���ʱ��  �ٻ�
          603123//46.00  ��΢�ɷ�  �ٻ�
    ));
    
    
    List<Integer> listCode_80_csls = new ArrayList<Integer>(Arrays.asList(
    9000759,//1.00  �аټ���  ��������
    9002251,//2.00  �� �� �� ��������
    9002264,//3.00  �� �� �� ��������
    9002336,//4.00  *ST���� ��������
    9002697,//5.00  ��������  ��������
    600361,//6.00 �����۳�  ��������
    600827,//7.00 �����ɷ�  ��������
    601010,//8.00 �ķ�ɷ�  ��������
    601116,//9.00 ��������  ��������
    601933,//10.00  ���Գ���  ��������
    603708//11.00  �Ҽ��� ��������
    
    ));
    
    List<Integer> listCode_81_dqls = new ArrayList<Integer>(Arrays.asList(
        9002024,//1.00  ��������  ��������
        9002416,//2.00  ��ʩ�� ��������
        600898//3.00 ��������  ��������
    ));
    List<Integer> listCode_82_yysy = new ArrayList<Integer>(Arrays.asList(

      9000028,//1.00  ��ҩһ��  ҽҩ��ҵ
      9000411,//2.00  Ӣ�ؼ���  ҽҩ��ҵ
      9000705,//3.00  �㽭��Ԫ  ҽҩ��ҵ
      9002462,//4.00  ������ ҽҩ��ҵ
      9002589,//5.00  ��ҽҩ  ҽҩ��ҵ
      9002727,//6.00  һ���� ҽҩ��ҵ
      9002758,//7.00  ��ͨҽҩ  ҽҩ��ҵ
      9002788,//8.00  ����ҽҩ  ҽҩ��ҵ
      600056,//9.00 �й�ҽҩ  ҽҩ��ҵ
      600090,//10.00  ͬ���� ҽҩ��ҵ
      600511,//11.00  ��ҩ�ɷ�  ҽҩ��ҵ
      600713,//12.00  �Ͼ�ҽҩ  ҽҩ��ҵ
      600829,//13.00  ����̩ͬ  ҽҩ��ҵ
      600833,//14.00  ��һҽҩ  ҽҩ��ҵ
      600998,//15.00  ����ͨ ҽҩ��ҵ
      601607,//16.00  �Ϻ�ҽҩ  ҽҩ��ҵ
      603108,//17.00  ���ҽ��  ҽҩ��ҵ
      603368,//18.00  ����ҽҩ  ҽҩ��ҵ
      603716,//19.00  ����˹ ҽҩ��ҵ
      603883,//20.00  �ϰ��� ҽҩ��ҵ
      603939//21.00  ���ҩ��  ҽҩ��ҵ
    ));
    
    List<Integer> listCode_83_qtsy = new ArrayList<Integer>(Arrays.asList(
        9000026,//1.00  ���Ǵ��  ������ҵ
        9000829,//2.00  �����ع�  ������ҵ
        9002556,//3.00  ��¡�ɷ�  ������ҵ
        9002780,//4.00  ������  ������ҵ
        300022,//5.00 ����ũ��  ������ҵ
        600122,//6.00 ��ͼ�߿�  ������ҵ
        603777//7.00 ������ ������ҵ
    
    ));
    
    List<Integer> listCode_84_spc = new ArrayList<Integer>(Arrays.asList(
    9002344,//1.00  ����Ƥ��  ��Ʒ��
    600415,//2.00 С��Ʒ��  ��Ʒ��
    600790//3.00 ��ĳ� ��Ʒ��
    ));
    
    List<Integer> listCode_85_pfy = new ArrayList<Integer>(Arrays.asList(
          9000587,//1.00  ���޴Ⱥ�  ����ҵ
          9000638,//2.00  �򷽷�չ  ����ҵ
          9000652,//3.00  ̩��ɷ�  ����ҵ
          9000906,//4.00  �������  ����ҵ
          9002441,//5.00  ��ҵ�� ����ҵ
          300538,//6.00 ͬ��ɷ�  ����ҵ
          600753//7.00 ��������  ����ҵ
    
    ));
    
    List<Integer> listCode_86_cpy = new ArrayList<Integer>(Arrays.asList(
          9000504,//1.00  *ST���� ����ҵ
          9000719,//2.00  ��ش�ý  ����ҵ
          9000793,//3.00  ���Ŵ�ý  ����ҵ
          9002181,//4.00  �� �� ý ����ҵ
          300148,//5.00 �����Ļ�  ����ҵ
          300364,//6.00 ��������  ����ҵ
          600229,//7.00 ���д�ý  ����ҵ
          600373,//8.00 ���Ĵ�ý  ����ҵ
          600551,//9.00 ʱ������  ����ҵ
          600633,//10.00  �㱨��ý  ����ҵ
          600757,//11.00  ������ý  ����ҵ
          600825,//12.00  �»���ý  ����ҵ
          601098,//13.00  ���ϴ�ý  ����ҵ
          601801,//14.00  ���´�ý  ����ҵ
          601811,//15.00  �»�����  ����ҵ
          601900,//16.00  �Ϸ���ý  ����ҵ
          601928,//17.00  ��˴�ý  ����ҵ
          601999,//18.00  ���洫ý  ����ҵ
          603999,//19.00  ���ߴ�ý  ����ҵ
          601858//20.00  �й��ƴ�  ����ҵ
    
    ));
    
    List<Integer> listCode_87_ysyx = new ArrayList<Integer>(Arrays.asList(
    9000156,//1.00  ������ý  Ӱ������
    9000665,//2.00  �������  Ӱ������
    9000673,//3.00  ��������  Ӱ������
    9000835,//4.00  ���Ƕ���  Ӱ������
    9000917,//5.00  ��㴫ý  Ӱ������
    9002071,//6.00  ����Ӱ��  Ӱ������
    9002143,//7.00  ӡ�ʹ�ý  Ӱ������
    9002238,//8.00  ������Ѷ  Ӱ������
    9002292,//9.00  �·�����  Ӱ������
    9002343,//10.00 ���Ĵ�ý  Ӱ������
    9002445,//11.00 �����Ļ�  Ӱ������
    9002502,//12.00 �����Ļ�  Ӱ������
    9002624,//13.00 ��������  Ӱ������
    9002739,//14.00 ���Ժ��  Ӱ������
    300027,//15.00  �����ֵ�  Ӱ������
    300133,//16.00  ����Ӱ��  Ӱ������
    300251,//17.00  ���ߴ�ý  Ӱ������
    300291,//18.00  ��¼����  Ӱ������
    300336,//19.00  ���Ļ� Ӱ������
    300426,//20.00  �Ƶ�Ӱ��  Ӱ������
    300528,//21.00  �Ҹ�����  Ӱ������
    600037,//22.00  �軪����  Ӱ������
    600088,//23.00  ���Ӵ�ý  Ӱ������
    600136,//24.00  ��������  Ӱ������
    600576,//25.00  ����Ļ�  Ӱ������
    600637,//26.00  ��������  Ӱ������
    600715,//27.00  ��Ͷ�ع�  Ӱ������
    600831,//28.00  �������  Ӱ������
    600936,//29.00  �������  Ӱ������
    600959,//30.00  ��������  Ӱ������
    600977,//31.00  �й���Ӱ  Ӱ������
    600996,//32.00  �������  Ӱ������
    601595,//33.00  �Ϻ���Ӱ  Ӱ������
    601599,//34.00  ¹���Ļ�  Ӱ������
    601929,//35.00  ���Ӵ�ý  Ӱ������
    603598//36.00  ������ý  Ӱ������
    
    ));
    
    List<Integer> listCode_88_lyfw = new ArrayList<Integer>(Arrays.asList(
        9000610,//1.00  ��������  ���η���
        9000613,//2.00  �󶫺�A  ���η���
        9000796,//3.00  ��������  ���η���
        9000802,//4.00  �����Ļ�  ���η���
        9002558,//5.00  ��������  ���η���
        9002707,//6.00  ��������  ���η���
        300178,//7.00 �ڰ����  ���η���
        600138,//8.00 ������ ���η���
        600358,//9.00 ��������  ���η���
        600706,//10.00  ��������  ���η���
        601888,//11.00  �й�����  ���η���
        603099,//12.00  ����ɽ ���η���
        603199,//13.00  �Ż�����  ���η���
        603869//14.00  ��������  ���η���
    ));
    
    List<Integer> listCode_89_lyjd = new ArrayList<Integer>(Arrays.asList(
        9000069,//1.00  ���ȳǣ�  ���ξ���
        9000430,//2.00  �żҽ� ���ξ���
        9000888,//3.00  ��üɽ��  ���ξ���
        9000978,//4.00  ��������  ���ξ���
        9002033,//5.00  ��������  ���ξ���
        9002059,//6.00  ��������  ���ξ���
        9002159,//7.00  ��������  ���ξ���
        300144,//8.00 �γ�����  ���ξ���
        600054,//9.00 ��ɽ����  ���ξ���
        600555,//10.00  ��������  ���ξ���
        600593,//11.00  ����ʥ��  ���ξ���
        600749//12.00  ��������  ���ξ���
    ));
    
    List<Integer> listCode_90_jczz = new ArrayList<Integer>(Arrays.asList(
    
          9000410,//1.00  ��������  ��������
          9000837,//2.00  �ش�����  ��������
          9002248,//3.00  ��������  ��������
          9002520,//4.00  �շ�����  ��������
          9002559,//5.00  �����ɷ�  ��������
          300161,//6.00 ��������  ��������
          300441,//7.00 ��˹�ɷ�  ��������
          600243,//8.00 �ຣ����  ��������
          600806,//9.00 *ST���� ��������
          601882,//10.00  ���쾫��  ��������
          603011//11.00  �϶�����  ��������
    
    ));
    List<Integer> listCode_91_jxjj = new ArrayList<Integer>(Arrays.asList(
          9000530,//1.00  ����ɷ�  ��е����
          9000570,//2.00  �ճ����  ��е����
          9000595,//3.00  ����ʵҵ  ��е����
          9000617,//4.00  *ST�ò� ��е����
          9000678,//5.00  �������  ��е����
          9000777,//6.00  �к˿Ƽ�  ��е����
          9000816,//7.00  �ǻ�ũҵ  ��е����
          9000856,//8.00  *ST��װ ��е����
          9000880,//9.00  Ϋ���ػ�  ��е����
          9000903,//10.00 ���ڶ���  ��е����
          9002026,//11.00 ɽ������  ��е����
          9002046,//12.00 ���пƼ�  ��е����
          9002050,//13.00 �����ǿ�  ��е����
          9002101,//14.00 �㶫��ͼ  ��е����
          9002122,//15.00 ����ɷ�  ��е����
          9002147,//16.00 �¹�Բ��  ��е����
          9002150,//17.00 ͨ��װ��  ��е����
          9002272,//18.00 ����ɷ�  ��е����
          9002342,//19.00 ��������  ��е����
          9002347,//20.00 ̩���ɷ�  ��е����
          9002418,//21.00 ��ʢ�ɷ�  ��е����
          9002435,//22.00 ������  ��е����
          9002438,//23.00 ������ͨ  ��е����
          9002472,//24.00 ˫������  ��е����
          9002480,//25.00 �����ɷ�  ��е����
          9002514,//26.00 ��ܰ�Ƽ�  ��е����
          9002552,//27.00 �����Ƽ�  ��е����
          9002598,//28.00 ɽ���¹�  ��е����
          9002633,//29.00 ��ƹɷ�  ��е����
          9002747,//30.00 ��˹�� ��е����
          9002760,//31.00 ���ιɷ�  ��е����
          9002795,//32.00 �����ǿ�  ��е����
          9002823,//33.00 ���о���  ��е����
          300091,//34.00  ��ͨ�� ��е����
          300095,//35.00  ����ɷ�  ��е����
          300151,//36.00  ����Ƽ�  ��е����
          300257,//37.00  ��ɽ�ɷ�  ��е����
          300260,//38.00  ����Ӧ��  ��е����
          300266,//39.00  ��Դ����  ��е����
          300391,//40.00  ��Ծ�Ƽ�  ��е����
          300420,//41.00  ����Ƽ�  ��е����
          300421,//42.00  ���ǹɷ�  ��е����
          300435,//43.00  ��̩�ɷ�  ��е����
          300464,//44.00  �ǻվ���  ��е����
          300470,//45.00  �ջ��ܷ�  ��е����
          300488,//46.00  ��湤��  ��е����
          300503,//47.00  �־����  ��е����
          600114,//48.00  �����ɷ�  ��е����
          600421,//49.00  �����ع�  ��е����
          600520,//50.00  *ST�з� ��е����
          600592,//51.00  ��Ϫ�ɷ�  ��е����
          600619,//52.00  �����ɷ�  ��е����
          600765,//53.00  �к��ػ�  ��е����
          600841,//54.00  �ϲ�ɷ�  ��е����
          601002,//55.00  ����ʵҵ  ��е����
          601177,//56.00  ����ǰ��  ��е����
          601218,//57.00  ���οƼ�  ��е����
          601369,//58.00  �¹Ķ���  ��е����
          603315,//59.00  �����ɷ�  ��е����
          603667,//60.00  �����´�  ��е����
          603726//61.00  �ʵϼ���  ��е����
    
    ));
    
    List<Integer> listCode_92_hgjx = new ArrayList<Integer>(Arrays.asList(
      9000852,//1.00  ʯ����е  ������е
      9002278,//2.00  �񿪹ɷ�  ������е
      9002337,//3.00  ����Ƽ�  ������е
      9002353,//4.00  ����ɷ�  ������е
      9002430,//5.00  �����ɷ�  ������е
      9002490,//6.00  ɽ��ī��  ������е
      9002564,//7.00  ���ֿƼ�  ������е
      9002698,//8.00  ��ʵ�ɷ�  ������е
      300228,//9.00 ������װ  ������е
      600579,//10.00  �컪Ժ ������е
      601798//11.00  ���Ƹ���  ������е
    ));
    
    List<Integer> listCode_93_qgjx = new ArrayList<Integer>(Arrays.asList(
      9000039,//1.00  �м�����  �Ṥ��е
      9000821,//2.00  ��ɽ���  �Ṥ��е
      9002209,//3.00  �� �� ¡ �Ṥ��е
      9002282,//4.00  �����  �Ṥ��е
      9002444,//5.00  ���ǿƼ�  �Ṥ��е
      9002611,//6.00  ��������  �Ṥ��е
      300126,//7.00 ����ɷ�  �Ṥ��е
      300173,//8.00 �ǻ��ɵ�  �Ṥ��е
      300195,//9.00 ���ٹɷ�  �Ṥ��е
      300442//10.00  ����ʢ �Ṥ��е
    
    ));
    List<Integer> listCode_94_fzjx = new ArrayList<Integer>(Arrays.asList(
    9000666,//1.00  ��γ�Ļ�  ��֯��е
    9002021,//2.00  �н���Դ  ��֯��е
    9002196,//3.00  �������  ��֯��е
    9002722,//4.00  ���ֹɷ�  ��֯��е
    300307,//5.00 ���ǹɷ�  ��֯��е
    300384,//6.00 ��������  ��֯��е
    600302,//7.00 ��׼�ɷ�  ��֯��е
    600843,//8.00 �Ϲ��건  ��֯��е
    603337//9.00 �ܿ˹ɷ�  ��֯��е
    ));
    
    List<Integer> listCode_95_lyjx = new ArrayList<Integer>(Arrays.asList(
    9002532,//1.00  �½��ҵ  ũ�û�е
    9002779,//2.00  �м�Ƽ�  ũ�û�е
    300159,//3.00 ���йɷ�  ũ�û�е
    600218,//4.00 ȫ����  ũ�û�е
    601038,//5.00 һ�Ϲɷ�  ũ�û�е
    603789//6.00 �ǹ�ũ��  ũ�û�е
    ));
    List<Integer> listCode_96_zyjx = new ArrayList<Integer>(Arrays.asList(
    9000404,//1.00  ����ѹ��  ר�û�е
    9000551,//2.00  ��Ԫ�Ƽ�  ר�û�е
    9000925,//3.00  �ںϿƼ�  ר�û�е
    9002006,//4.00  �����Ƽ�  ר�û�е
    9002192,//5.00  �ڽݹɷ�  ר�û�е
    9002204,//6.00  �����ع�  ר�û�е
    9002255,//7.00  ��½�ع�  ר�û�е
    9002366,//8.00  ̨���˵�  ר�û�е
    9002509,//9.00  �����ï  ר�û�е
    9002529,//10.00 ��Դ��е  ר�û�е
    9002530,//11.00 �ᶫ�ɷ�  ר�û�е
    9002534,//12.00 �����ɷ�  ר�û�е
    9002595,//13.00 �����Ƽ�  ר�û�е
    9002613,//14.00 �����ɷ�  ר�û�е
    9002621,//15.00 ���ݹɷ�  ר�û�е
    9002630,//16.00 ������Դ  ר�û�е
    9002639,//17.00 ѩ�˹ɷ�  ר�û�е
    9002645,//18.00 ����Ƽ�  ר�û�е
    9002651,//19.00 �����ɷ�  ר�û�е
    9002686,//20.00 ������ ר�û�е
    9002690,//21.00 ���ǹ��  ר�û�е
    9002691,//22.00 �����ɷ�  ר�û�е
    9002786,//23.00 ����ɽ��  ר�û�е
    9002793,//24.00 �����ɷ�  ר�û�е
    9002796,//25.00 ���οƼ�  ר�û�е
    9002816,//26.00 �Ϳƴ� ר�û�е
    9002833,//27.00 ��������  ר�û�е
    9002837,//28.00 Ӣά�� ר�û�е
    300023,//29.00  ���¹ɷ�  ר�û�е
    300024,//30.00  ������ ר�û�е
    300029,//31.00  �������  ר�û�е
    300092,//32.00  ���»���  ר�û�е
    300116,//33.00  ��������  ר�û�е
    300145,//34.00  �н𻷾�  ר�û�е
    300193,//35.00  ��ʿ�Ƽ�  ר�û�е
    300201,//36.00  ������ ר�û�е
    300202,//37.00  �����ɷ�  ר�û�е
    300210,//38.00  ɭԶ�ɷ�  ר�û�е
    300249,//39.00  ���׿� ר�û�е
    300263,//40.00  ¡������  ר�û�е
    300276,//41.00  ��������  ר�û�е
    300278,//42.00  ������ ר�û�е
    300280,//43.00  ��ͨ��ѹ  ר�û�е
    300281,//44.00  ��������  ר�û�е
    300293,//45.00  ��Ӣװ��  ר�û�е
    300309,//46.00  �����Ƽ�  ר�û�е
    300316,//47.00  ��ʢ����  ר�û�е
    300334,//48.00  ��Ĥ�Ƽ�  ר�û�е
    300368,//49.00  ���ɷ�  ר�û�е
    300382,//50.00  ˹���� ר�û�е
    300400,//51.00  ���عɷ�  ר�û�е
    300402,//52.00  ��ɫ�ɷ�  ר�û�е
    300411,//53.00  ��ܹɷ�  ר�û�е
    300415,//54.00  ��֮�� ר�û�е
    300434,//55.00  ��ʯ����  ר�û�е
    300443,//56.00  ���׷��  ר�û�е
    300450,//57.00  �ȵ�����  ר�û�е
    300457,//58.00  Ӯ�ϿƼ�  ר�û�е
    300461,//59.00  ���о���  ר�û�е
    300462,//60.00  ��������  ר�û�е
    300471,//61.00  ���չɷ�  ר�û�е
    300472,//62.00  ��Ԫ�Ƽ�  ר�û�е
    300475,//63.00  ��¡�Ƽ�  ר�û�е
    300483,//64.00  ��ʩ�ɷ�  ר�û�е
    300486,//65.00  ��������  ר�û�е
    300499,//66.00  �����ɷ�  ר�û�е
    300509,//67.00  ������ ר�û�е
    300512,//68.00  ���ǹɷ�  ר�û�е
    300521,//69.00  ��˾�� ר�û�е
    300526,//70.00  ��Ǳ�ɷ�  ר�û�е
    300527,//71.00  ����Ӧ��  ר�û�е
    300540,//72.00  ����ɷ�  ר�û�е
    300545,//73.00  ����װ��  ר�û�е
    300549,//74.00  �ŵ¾���  ר�û�е
    300551,//75.00  �����Ƽ�  ר�û�е
    300569,//76.00  �����ع�  ר�û�е
    600184,//77.00  ���ɷ�  ר�û�е
    600435,//78.00  ��������  ר�û�е
    600475,//79.00  ����ɷ�  ר�û�е
    600499,//80.00  �ƴ����  ר�û�е
    600855,//81.00  ���쳤��  ר�û�е
    600860,//82.00  ���ǹɷ�  ר�û�е
    601226,//83.00  �����ع�  ר�û�е
    601608,//84.00  �����ع�  ר�û�е
    603012,//85.00  ��������  ר�û�е
    603029,//86.00  ���ɷ�  ר�û�е
    603036,//87.00  ��ͨ�ɷ�  ר�û�е
    603066,//88.00  ���ɴ���  ר�û�е
    603085,//89.00  ����Կ�  ר�û�е
    603088,//90.00  ��������  ר�û�е
    603090,//91.00  ��ʢ�ɷ�  ר�û�е
    603131,//92.00  �Ϻ�����  ר�û�е
    603159,//93.00  �Ϻ��Ǻ�  ר�û�е
    603169,//94.00  ��ʯ��װ  ר�û�е
    603203,//95.00  ��˹ɷ�  ר�û�е
    603298,//96.00  ���漯��  ר�û�е
    603308,//97.00  Ӧ���ɷ�  ר�û�е
    603311,//98.00  �𺣻���  ר�û�е
    603318,//99.00  ��˼�ɷ�  ר�û�е
    603338,//100.00 �㽭����  ר�û�е
    603339,//101.00 �ķ�����  ר�û�е
    603686,//102.00 ������  ר�û�е
    603698,//103.00 ���칤��  ר�û�е
    603699,//104.00 Ŧ���ɷ�  ר�û�е
    603800,//105.00 ��ɭ�ɷ�  ר�û�е
    603901,//106.00 ��������  ר�û�е
    603690//107.00 �����Ƽ�  ר�û�е
    ));
    
    List<Integer> listCode_97_tl = new ArrayList<Integer>(Arrays.asList(
    600125,//1.00 ��������  ��·
    601006,//2.00 ������·  ��·
    601333//3.00 ������·  ��·
    ));
    List<Integer> listCode_98_sy = new ArrayList<Integer>(Arrays.asList(
    9000520,//1.00  �������  ˮ��
    9002320,//2.00  ��Ͽ�ɷ�  ˮ��
    600026,//3.00 ��Զ����  ˮ��
    600242,//4.00 �в�����  ˮ��
    600428,//5.00 ��Զ����  ˮ��
    600575,//6.00 �����  ˮ��
    600692,//7.00 ��ͨ�ɷ�  ˮ��
    600751,//8.00 �캣Ͷ��  ˮ��
    600798,//9.00 ��������  ˮ��
    600896,//10.00  ����Ͷ��  ˮ��
    601866,//11.00  ��Զ����  ˮ��
    601872,//12.00  �����ִ�  ˮ��
    601919,//13.00  ��Զ����  ˮ��
    603167//14.00  �����ֶ�  ˮ��
    ));
    List<Integer> listCode_99_ky = new ArrayList<Integer>(Arrays.asList(
    9000099,//1.00  ���ź�ֱ  ����
    600029,//2.00 �Ϸ�����  ����
    600115,//3.00 ��������  ����
    600221,//4.00 ���Ϻ���  ����
    601021,//5.00 ���ﺽ��  ����
    601111,//6.00 �й�����  ����
    603885//7.00 ���麽��  ����
    ));
    
    List<Integer> listCode_100_gl = new ArrayList<Integer>(Arrays.asList(
    9000996,//1.00  �й�����  ��·
    9002357,//2.00  ������ҵ  ��·
    9002627,//3.00  �˲�����  ��·
    9002682,//4.00  ���޹ɷ�  ��·
    600561,//5.00 ��������  ��·
    603069,//6.00 ��������  ��·
    603223,//7.00 ��ͨ�ɷ�  ��·
    603032//8.00 ���½���  ��·
    ));
    
    List<Integer> listCode_101_lq = new ArrayList<Integer>(Arrays.asList(
    9000429,//1.00  �����٣�  ·��
    9000548,//2.00  ����Ͷ��  ·��
    9000828,//3.00  ��ݸ�ع�  ·��
    9000886,//4.00  ���ϸ���  ·��
    9000900,//5.00  �ִ�Ͷ��  ·��
    9000916,//6.00  ��������  ·��
    600012,//7.00 ��ͨ����  ·��
    600020,//8.00 ��ԭ����  ·��
    600033,//9.00 ��������  ·��
    600035,//10.00  �������  ·��
    600106,//11.00  ����·��  ·��
    600269,//12.00  ��������  ·��
    600350,//13.00  ɽ������  ·��
    600368,//14.00  ���޽�ͨ  ·��
    600377,//15.00  ��������  ·��
    600548,//16.00  ����� ·��
    601107,//17.00  �Ĵ�����  ·��
    601188,//18.00  ������ͨ  ·��
    601518//19.00  ���ָ���  ·��
    ));
    
    List<Integer> listCode_102_jc = new ArrayList<Integer>(Arrays.asList(
    9000089,//1.00  ���ڻ���  ����
    600004,//2.00 ���ƻ���  ����
    600009,//3.00 �Ϻ�����  ����
    600897//4.00 ���ſո�  ����
    ));
    List<Integer> listCode_103_gk = new ArrayList<Integer>(Arrays.asList(
    9000022,//1.00  ������  �ۿ�
    9000088,//2.00  �� �� �� �ۿ�
    9000507,//3.00  �麣�� �ۿ�
    9000582,//4.00  �������  �ۿ�
    9000905,//5.00  ���Ÿ���  �ۿ�
    9002040,//6.00  �� �� �� �ۿ�
    600017,//7.00 ���ո� �ۿ�
    600018,//8.00 �ϸۼ���  �ۿ�
    600190,//9.00 ���ݸ� �ۿ�
    600279,//10.00  ����۾�  �ۿ�
    600317,//11.00  Ӫ�ڸ� �ۿ�
    600717,//12.00  ���� �ۿ�
    601000,//13.00  ��ɽ�� �ۿ�
    601008,//14.00  ���Ƹ� �ۿ�
    601018,//15.00  ������ �ۿ�
    601880//16.00  ������ �ۿ�
    ));
    List<Integer> listCode_104_jzsg = new ArrayList<Integer>(Arrays.asList(
    9000010,//1.00  ������̬  ����ʩ��
    9000065,//2.00  ��������  ����ʩ��
    9000090,//3.00  �콡����  ����ʩ��
    9000498,//4.00  ɽ��·��  ����ʩ��
    9000928,//5.00  �иֹ���  ����ʩ��
    9000961,//6.00  ���Ͻ���  ����ʩ��
    9002051,//7.00  �й�����  ����ʩ��
    9002060,//8.00  �� ˮ �� ����ʩ��
    9002062,//9.00  ������  ����ʩ��
    9002116,//10.00 �й�����  ����ʩ��
    9002135,//11.00 ��������  ����ʩ��
    9002140,//12.00 �����Ƽ�  ����ʩ��
    9002178,//13.00 �ӻ�����  ����ʩ��
    9002307,//14.00 ����·��  ����ʩ��
    9002310,//15.00 ����԰��  ����ʩ��
    9002323,//16.00 �Ű��� ����ʩ��
    9002374,//17.00 �����ɷ�  ����ʩ��
    9002431,//18.00 ��鵹ɷ�  ����ʩ��
    9002469,//19.00 ��ά����  ����ʩ��
    9002542,//20.00 �л�����  ����ʩ��
    9002586,//21.00 Χ���ɷ�  ����ʩ��
    9002628,//22.00 �ɶ�·��  ����ʩ��
    9002659,//23.00 ��̩����  ����ʩ��
    9002663,//24.00 �հ�ɷ�  ����ʩ��
    9002717,//25.00 ����԰��  ����ʩ��
    9002738,//26.00 �п���Դ  ����ʩ��
    9002755,//27.00 ��������  ����ʩ��
    9002775,//28.00 �Ŀ�԰��  ����ʩ��
    300237,//29.00  �����Ƽ�  ����ʩ��
    300284,//30.00  �ս��� ����ʩ��
    300492,//31.00  ɽ�����  ����ʩ��
    300495,//32.00  ������̬  ����ʩ��
    300500,//33.00  �������  ����ʩ��
    300506,//34.00  ���һ� ����ʩ��
    300517,//35.00  �����ؿ�  ����ʩ��
    300536,//36.00  ũ�л���  ����ʩ��
    600039,//37.00  �Ĵ�·��  ����ʩ��
    600068,//38.00  ���ް� ����ʩ��
    600083,//39.00  ���Źɷ�  ����ʩ��
    600170,//40.00  �Ϻ�����  ����ʩ��
    600248,//41.00  �ӳ�����  ����ʩ��
    600284,//42.00  �ֶ�����  ����ʩ��
    600326,//43.00  ������·  ����ʩ��
    600491,//44.00  ��Ԫ����  ����ʩ��
    600502,//45.00  ����ˮ��  ����ʩ��
    600512,//46.00  �ڴｨ��  ����ʩ��
    600528,//47.00  ��������  ����ʩ��
    600545,//48.00  �½��ǽ�  ����ʩ��
    600610,//49.00  ����� ����ʩ��
    600629,//50.00  ��������  ����ʩ��
    600769,//51.00  ������ҵ  ����ʩ��
    600820,//52.00  ����ɷ�  ����ʩ��
    600853,//53.00  �����ɷ�  ����ʩ��
    600970,//54.00  �вĹ���  ����ʩ��
    601117,//55.00  �й���ѧ  ����ʩ��
    601186,//56.00  �й�����  ����ʩ��
    601390,//57.00  �й�����  ����ʩ��
    601611,//58.00  �й��˽�  ����ʩ��
    601618,//59.00  �й���ұ  ����ʩ��
    601668,//60.00  �й�����  ����ʩ��
    601669,//61.00  �й��罨  ����ʩ��
    601789,//62.00  ��������  ����ʩ��
    601800,//63.00  �й�����  ����ʩ��
    603007,//64.00  �����ɷ�  ����ʩ��
    603017,//65.00  �к����  ����ʩ��
    603018,//66.00  ���輯��  ����ʩ��
    603060,//67.00  ���켯��  ����ʩ��
    603778,//68.00  Ǭ��԰��  ����ʩ��
    603843,//69.00  XD��ƽ�� ����ʩ��
    603887,//70.00  �ǵعɷ�  ����ʩ��
    603909,//71.00  �ϳϹɷ�  ����ʩ��
    603959,//72.00  �����Ƽ�  ����ʩ��
    603979//73.00  ����� ����ʩ��
    ));
    
    List<Integer> listCode_105_jzzs = new ArrayList<Integer>(Arrays.asList(
    9000018,//1.00  ���ݳ���  װ��װ��
    9002047,//2.00  ��ӥ�ɷ�  װ��װ��
    9002081,//3.00  �� � �� װ��װ��
    9002163,//4.00  �к�����  װ��װ��
    9002247,//5.00  �����Ļ�  װ��װ��
    9002325,//6.00  ���ιɷ�  װ��װ��
    9002375,//7.00  ���ùɷ�  װ��װ��
    9002482,//8.00  ���Ｏ��  װ��װ��
    9002504,//9.00  ��ߴ���  װ��װ��
    9002620,//10.00 ��͹ɷ�  װ��װ��
    9002713,//11.00 ������ʢ  װ��װ��
    9002781,//12.00 ���Źɷ�  װ��װ��
    9002789,//13.00 ���ռ���  װ��װ��
    9002811,//14.00 ��̩����  װ��װ��
    9002822,//15.00 ��װ����  װ��װ��
    9002830,//16.00 ����ɷ�  װ��װ��
    300117,//17.00  ��Ԣ�ɷ�  װ��װ��
    600193,//18.00  ������Դ  װ��װ��
    601886,//19.00  ���Ӽ���  װ��װ��
    603030,//20.00  ȫ���ɷ�  װ��װ��
    603098,//21.00  ɭ�عɷ�  װ��װ��
    603828,//22.00  ������ װ��װ��
    603929//23.00  N���� װ��װ��
    ));
    List<Integer> listCode_106_qgdc = new ArrayList<Integer>(Arrays.asList(
    9000002,//1.00  �� �ƣ�  ȫ���ز�
    9000014,//2.00  ɳ�ӹɷ�  ȫ���ز�
    9000031,//3.00  �����ز�  ȫ���ز�
    9000036,//4.00  �����ع�  ȫ���ز�
    9000040,//5.00  ��������  ȫ���ز�
    9000042,//6.00  ���޿ع�  ȫ���ز�
    9000043,//7.00  �к��ز�  ȫ���ز�
    9000046,//8.00  �����ع�  ȫ���ز�
    9000402,//9.00  �� �� �� ȫ���ز�
    9000616,//10.00 ����Ͷ��  ȫ���ز�
    9000620,//11.00 �»��� ȫ���ز�
    9000667,//12.00 ������ҵ  ȫ���ز�
    9000736,//13.00 �з��ز�  ȫ���ز�
    9000797,//14.00 �й�����  ȫ���ز�
    9000918,//15.00 �ο��� ȫ���ز�
    9001979,//16.00 �����߿�  ȫ���ز�
    9002133,//17.00 �����  ȫ���ز�
    9002146,//18.00 ��ʢ��չ  ȫ���ز�
    600048,//19.00  �����ز�  ȫ���ز�
    600067,//20.00  �ڳǴ�ͨ  ȫ���ز�
    600077,//21.00  �ζ��ɷ�  ȫ���ز�
    600162,//22.00  �㽭�ع�  ȫ���ز�
    600173,//23.00  �����ز�  ȫ���ز�
    600208,//24.00  �º��б�  ȫ���ز�
    600240,//25.00  ��ҵ�ʱ�  ȫ���ز�
    600383,//26.00  ��ؼ���  ȫ���ز�
    600393,//27.00  ��̩�ɷ�  ȫ���ز�
    600510,//28.00  ��ĵ�� ȫ���ز�
    600565,//29.00  ����ɷ�  ȫ���ز�
    600606,//30.00  �̵ؿع�  ȫ���ز�
    600621,//31.00  ���ιɷ�  ȫ���ز�
    600657,//32.00  �Ŵ�ز�  ȫ���ز�
    600665,//33.00  ���Դ ȫ���ز�
    600684,//34.00  �齭ʵҵ  ȫ���ز�
    600708,//35.00  �����ز�  ȫ���ز�
    600748,//36.00  ��ʵ��չ  ȫ���ز�
    600791,//37.00  ������ҵ  ȫ���ز�
    600823//38.00  ��ï�ɷ�  ȫ���ز�
    ));
    List<Integer> listCode_107_qydc = new ArrayList<Integer>(Arrays.asList(
    9000006,//1.00  ����ҵ��  ����ز�
    9000011,//2.00  ����ҵA  ����ز�
    9000029,//3.00  �����  ����ز�
    9000506,//4.00  ������Դ  ����ز�
    9000514,//5.00  �� �� �� ����ز�
    9000517,//6.00  �ٰ��ز�  ����ز�
    9000534,//7.00  ����ɷ�  ����ز�
    9000537,//8.00  ���չ  ����ز�
    9000540,//9.00  �����Ͷ  ����ز�
    9000567,//10.00 ���¹ɷ�  ����ز�
    9000573,//11.00 ����Զ��  ����ز�
    9000608,//12.00 ����ɷ�  ����ز�
    9000609,//13.00 ��ʯͶ��  ����ز�
    9000631,//14.00 ˳����ҵ  ����ز�
    9000656,//15.00 ��ƹɷ�  ����ز�
    9000668,//16.00 �ٷ�ع�  ����ز�
    9000671,//17.00 �� �� �� ����ز�
    9000691,//18.00 ST��̫  ����ز�
    9000718,//19.00 ��������  ����ز�
    9000732,//20.00 ̩�̼���  ����ز�
    9000809,//21.00 �����³�  ����ز�
    9000838,//22.00 ���ŷ�չ  ����ز�
    9000863,//23.00 ����ӡ��  ����ز�
    9000897,//24.00 �����չ  ����ز�
    9000926,//25.00 ���ǹɷ�  ����ز�
    9000931,//26.00 �� �� �� ����ز�
    9000965,//27.00 �챣����  ����ز�
    9000979,//28.00 �к�ɷ�  ����ز�
    9000981,//29.00 ���ڹɷ�  ����ز�
    9002016,//30.00 ������ҵ  ����ز�
    9002077,//31.00 ��۹ɷ�  ����ز�
    9002208,//32.00 �Ϸʳǽ�  ����ز�
    9002244,//33.00 ��������  ����ز�
    9002305,//34.00 �Ϲ���ҵ  ����ز�
    9002314,//35.00 ��ɽ�ع�  ����ز�
    600052,//36.00  �㽭����  ����ز�
    600053,//37.00  �Ŷ�Ͷ��  ����ز�
    600094,//38.00  ������ ����ز�
    600095,//39.00  ���߿� ����ز�
    600113,//40.00  �㽭����  ����ز�
    600159,//41.00  �����ز�  ����ز�
    600185,//42.00  �����ز�  ����ز�
    600223,//43.00  ³����ҵ  ����ز�
    600225,//44.00  ����ɽ�  ����ز�
    600239,//45.00  ���ϳ�Ͷ  ����ز�
    600246,//46.00  ��ͨ�ز�  ����ز�
    600266,//47.00  �����ǽ�  ����ز�
    600322,//48.00  �췿��չ  ����ز�
    600325,//49.00  �����ɷ�  ����ز�
    600340,//50.00  �����Ҹ�  ����ز�
    600376,//51.00  �׿��ɷ�  ����ز�
    600466,//52.00  ���ⷢչ  ����ز�
    600503,//53.00  ��������  ����ز�
    600533,//54.00  ��ϼ����  ����ز�
    600568,//55.00  ����ҽ��  ����ز�
    600622,//56.00  �α�����  ����ز�
    600638,//57.00  �»��� ����ز�
    600641,//58.00  ��ҵ��ҵ  ����ز�
    600649,//59.00  ��Ͷ�ع�  ����ز�
    600675,//60.00  *ST���� ����ز�
    600683,//61.00  ��Ͷ��չ  ����ز�
    600696,//62.00  ƥ͹ƥ ����ز�
    600716,//63.00  ��˹ɷ�  ����ز�
    600724,//64.00  ��������  ����ز�
    600733,//65.00  Sǰ�� ����ز�
    600743,//66.00  ��Զ�ز�  ����ز�
    600773,//67.00  ���س�Ͷ  ����ز�
    600807,//68.00  ��ҵ�ɷ�  ����ز�
    600890,//69.00  �з��ɷ�  ����ز�
    601155,//70.00  �³ǿع�  ����ز�
    601588,//71.00  ����ʵҵ  ����ز�
    600732//72.00  *ST��÷ ����ز�

    ));
    List<Integer> listCode_108_yqkf = new ArrayList<Integer>(Arrays.asList(
    9000628,//1.00  ���·�չ  ԰������
    600007,//2.00 �й���ó  ԰������
    600064,//3.00 �Ͼ��߿�  ԰������
    600082,//4.00 ��̩��չ  ԰������
    600133,//5.00 ��������  ԰������
    600215,//6.00 ��������  ԰������
    600463,//7.00 �ո۹ɷ�  ԰������
    600604,//8.00 �б�����  ԰������
    600639,//9.00 �ֶ�����  ԰������
    600648,//10.00  ����� ԰������
    600658,//11.00  ���ӳ� ԰������
    600663,//12.00  ½���� ԰������
    600736,//13.00  ���ݸ���  ԰������
    600848,//14.00  �Ϻ��ٸ�  ԰������
    600895//1.00 �Ž��߿�  ԰������
    
    ));
    
    List<Integer> listCode_109_fcfw = new ArrayList<Integer>(Arrays.asList(
        9000005,//2.00  ������Դ  ��������
        9000056,//3.00  ��ͥ����  ��������
        9000505,//4.00  *ST�齭 ��������
        9000861,//5.00  ��ӡ�ɷ�  ��������
        9002285,//6.00  ������ ��������
        9002818//7.00  ��ɭ�� ��������
    ));
    
    
    List<Integer> listAll = new ArrayList<Integer>();

    
    

    //��ȫ��
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
      //�����ҵ
      listAll.addAll(listCode_109_fcfw);
      
      
    }else if(flag.equals("3")){
      //��ĳ����Ʊ 
      listAll.add(stockNum);  // 9000001   Ҫ��9��ǰ��
      
    }
    
    
    

    return listAll;
    
    
  }
  
  
  //����  �۷�����   http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code=60051901&num=9&code1=sh600519&spstr=&n=1&timetip=1487063111207
}
