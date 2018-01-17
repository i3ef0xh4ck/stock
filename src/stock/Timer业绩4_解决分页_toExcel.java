package stock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class Timer业绩4_解决分页_toExcel {
  //新安股份
  
  public static void main(String [] args) throws Exception {
    

    String date0 = "201612";
    System.out.println("总入口:"+"http://data.eastmoney.com/bbsj/"+date0+"/yjbb.html");
    
    
    //总
    String date = "2016-12-31";
    int page = 1;
    int pageSize = 500;
    String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&fd="+date+"&st=13&sr=-1&p="+page+"&ps="+pageSize+"&js=var%20DUwfyPhF={pages:(pc),data:[(x)]}&stat=0&rt=49692322";
    
    
  //  
    Map<String,String> map = new HashMap<String,String>();
    Map<String,String> map2 = new HashMap<String,String>();
    
    String fileName = "yy";

    System.out.println(url);
    File downFile = stockDow.downFile(url.toString(),fileName);
    
    String readFile = stockDow.readFile(downFile,"UTF8");
    
    if(readFile.indexOf("[")>0){
      String readFile2 = readFile.substring(readFile.indexOf("["), readFile.indexOf("}"));
      JSONArray parseArray = JSON.parseArray(readFile2);
      //只要当前一天的  因为我们的定时器每天都看
      Date currentdate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String formatDate = sdf.format(currentdate);
      formatDate = "2017-04-27";
      for(int i = 0 ;i < parseArray.size();i++){
        Object object = parseArray.get(i);
        String str = object.toString();
        
        
        if(str.indexOf(formatDate) > 0){
          String[] split = str.split(",");
          
         // for(int j = 0 ; j < split.length;j++){
            String code = split[0];
            String name = split[1];

            
            String incode = split[4];
            String profite = split[7];
            
            String 发布日期 = split[split.length-3];
            String 公告日期 = split[split.length-2];
            
            
            //TODO 要数字 有业绩原因
            String url3 = "";
            if(code.startsWith("6")){
              url3 = "http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code="+code+"01&num=9&code1=sh"+code+"&spstr=&n=2&timetip=1487063111208";
            }else{
              //只有数字   这个有非和扣非
              url3 = "http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code="+code+"02&num=9&code1=sz"+code+"&spstr=&n=2&timetip=1487063111208";

            }
            
            File downFile2 = stockDow.downFile(url3.toString(),"业绩扣非与不扣");
            
            String readFile3 = stockDow.readFile(downFile2,"UTF8");
            if(readFile3.equals("")){
              continue;
            }
            
            
            
          //  int indexOf4 = readFile3.indexOf("成长能力指标");
            
            int indexOf = readFile3.indexOf("归属净利润");
            
            int indexOf2 = readFile3.indexOf("扣非净利润");
            
            int indexOf3 = readFile3.indexOf("营业总收入同比增长");
            
            
            String substring2 = readFile3.substring(indexOf, indexOf2);
            
            String substring3 = readFile3.substring(indexOf2, indexOf3);
            
            System.out.println(code+ "  "+ name + " "+ incode + " "+ profite + " "+ 公告日期 +"      "+ 发布日期);
            System.out.println();
            System.out.println(substring2);
            System.out.println(substring3);
            System.out.println();
            System.out.println(i);
            
            String [] split2 = substring2.split("span");
            String [] split3 = substring3.split("span");
            
            String string = split2[2].replace(">", "").replace("</", "");
            String string2 = split3[2].replace(">", "").replace("</", "");
            map.put(code, string);
            map2.put(code, string2);
            System.out.println();
            
             // String s1 = "归属净利润(元)</span></td><td class=\"tips-data-Right\"><span>";
             // String s2 = "扣非净利润(元)</span></td><td class=\"tips-data-Right\"><span>";
              
        //   System.out.println(code+ "  "+ name + " "+ incode + " "+ profite + " "+ 公告日期);
         //  System.out.println(url3);
           
           
        //  }
          
          
        }else{
          //System.out.println("没有当天数据");
        }
        

        
      }
      
      excel.updateExcelxlxs2("H:\\新建文件夹\\新建文件夹\\程序复制\\z业绩公告程序复制单季4.xlsx", "所有四季利润", map,map2);
      
      
     
      
    }else{
      System.out.println("规则有变化");
    }
    
 
    
    
  }
  

}
