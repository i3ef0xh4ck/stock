package stock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


/**
 * ͬʱҪ�õ����ڱ������Ʋ�  
 * */
public class TimerԤ��_2�����ҳ����_toExcel {
  //�˵ؿƼ�
  private static Integer stockNum = 9000001;   //0��ͷ��Ҫ��9��
  private static String flag = "1";  //1 ȫ��   2 ��ҵ  3���� 
  
  
  @SuppressWarnings("unused")
  public static void main(String [] args) throws Exception {
        //֧�ֶ��Ҳ֧��һ��
    
    //���˸����ȵ�ҵ��Ԥ��
  String date2 = "201806";

  //��ֻ�ǵ�һҳ  ���еڶ�ҳ��url
  String url3 = "http://data.eastmoney.com/bbsj/"+date2+"/yjyg.html";
  System.out.println("����ʼǰ���Ƚ�Ԥ���и��Ƶ� H:\\�½��ļ���\\�½��ļ���\\������\\zԤ�������.xlsx");
  System.out.println("����ڣ�"+url3);
  
        
          //���˸����ȵ�ҵ��Ԥ��
        String date = "2018-06-30";
    
        int pageSize = 5000;
        String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJYG&fd="+date+"&st=4&sr=-1&p=1&ps="+pageSize+"&js=var%20BVEynmsU={pages:(pc),data:[(x)]}&stat=0&rt=49692448";
        System.out.println(url);
        
        String fileName = "yjyg";
        
        File downFile = stockDow.downFile(url.toString(),fileName);
        
        String readFile = stockDow.readFile(downFile,"UTF8");
        
        Map<String,String> map = new HashMap<String,String>();
        
        if(readFile.indexOf("[")>0){
          String readFile2 = readFile.substring(readFile.indexOf("["), readFile.indexOf("}"));
          
         // System.out.println(substring);
          JSONArray parseArray = JSON.parseArray(readFile2);
          

          
          //ֻҪ��ǰһ���  ��Ϊ���ǵĶ�ʱ��ÿ�춼��
          Date currentdate = new Date();
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          String formatDate = sdf.format(currentdate);
          
 
        //  formatDate = "2017-06-30";
          String formatDate2 = "2018-08-20";
          String formatDate3 = "2018-08-21";
          String formatDate4 = "2018-08-22";
          String formatDate5 = "2018-08-23";
          String formatDate6 = "2018-08-24";
          String formatDate7 = "2018-08-25";
          String formatDate8 = "2018-08-26";
          
          
          List<String> listDate = Arrays.asList(formatDate2,formatDate3,formatDate4,formatDate5,formatDate6,formatDate7,formatDate8);
          
          
          for(int i = 0 ;i < parseArray.size();i++){
            Object object = parseArray.get(i);
            String str = object.toString();
            
            
            
            //if(str.indexOf(formatDate)>0){//ƽʱû������ �����˼�ĩ���һ���������
             //System.out.println(i+"   "+str);
             String[] split = str.split(",");
             
             String ����date = split[split.length-2];
             
            // if(����date.equals(formatDate)){
             if(listDate.contains(����date) || !listDate.contains(����date)){
             
               
               for(int j = 0 ; j < split.length;j++){
                 
                 String code = split[0];
                 
                 String yg = "http://quotes.money.163.com/f10/yjyg_"+code+".html#01c03";  
                 
                 File downFile2 = stockDow.downFile(yg.toString(),fileName);
                 
                 String readFile3 = stockDow.readFile(downFile,"UTF-8");
                 //System.out.println(readFile3);
                 
                 if(readFile3.indexOf("title_01")>0){
                   String substring2 = readFile3.substring(readFile3.indexOf("title_01"),readFile3.length());
                   String all = substring2.substring(0,substring2.indexOf("</table>"));
                   
                   
                   String �������� = all.substring(all.indexOf("��������")+20, all.indexOf("��������")+20+10);
                   
                   String name  = all.substring(all.indexOf("span")+18, all.indexOf("span")+18+4);
                   
                   String �������� = all.substring(all.indexOf("��������")+20, all.indexOf("��������")+20+10);
                   
                   
                   

                   String Ԥ������ = all.substring(all.indexOf("Ԥ������")+32, all.indexOf("Ԥ������")+32+2);
                   
                   String Ԥ������ = all.substring(all.indexOf("Ԥ������")+32, all.lastIndexOf("</tr>")-15);
                   
                   System.out.println(yg);
                   System.out.println(Ԥ������+" "+name+" "+code +" "+��������+" "+��������+" "+ " "+Ԥ������);
                   
                   
                   
  /*                 System.out.println(Ԥ������);
                   System.out.println(name);
                   System.out.println(code);
                   System.out.println(��������);
                   System.out.println(��������);
                   System.out.println(Ԥ������);*/
                   System.out.println();
                   
                   map.put(code, Ԥ������+" "+name+" "+code +" "+��������+" "+��������+" "+ " "+Ԥ������);
                   
                 //  
                   
                  // all.substring(all.indexOf("Ԥ������"), endIndex);
                   
                 }
                 
             
                 
                 /**
                  * �Ѿ�Ԥ���˵ģ���һ�����������Ȼ��Ҫ���� ��ʣ�µĻ�����Щҵ��������ȴû��Ԥ���
                  * */
                 
                 break;
               }
               
               
             }else{
               
               System.out.println("���Ҳ�����������");
               continue;
               //break;
               
             }
             
             System.out.println(i);
              
  
            
          }
          
          excel.updateExcelxlxs2("F:\\stock\\git\\new\\�½��ļ���\\������\\zԤ�������2.xlsx", "�����ļ�����", map,null);
          
          System.out.println("��鿴:F:\\stock\\git\\new\\�½��ļ���\\������\\zԤ�������2.xlsx  �����ļ�����");
          
          
          
          
          
        }else{
          System.out.println("�����б仯");
         }
      
        
        
        String url2 = "http://quotes.money.163.com/f10/yjyg_002476.html#01c03";
       
        
     
  }
  
  
  

}
