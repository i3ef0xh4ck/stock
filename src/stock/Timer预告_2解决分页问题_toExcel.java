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
 * 同时要得到下期必增的推测  
 * */
public class Timer预告_2解决分页问题_toExcel {
  //顾地科技
  private static Integer stockNum = 9000001;   //0开头的要以9开
  private static String flag = "1";  //1 全部   2 行业  3个股 
  
  
  @SuppressWarnings("unused")
  public static void main(String [] args) throws Exception {
        //支持多个也支持一个
    
    //哪了个季度的业绩预告
  String date2 = "201806";

  //这只是第一页  还有第二页的url
  String url3 = "http://data.eastmoney.com/bbsj/"+date2+"/yjyg.html";
  System.out.println("程序开始前请先将预告列复制到 H:\\新建文件夹\\新建文件夹\\程序复制\\z预告程序复制.xlsx");
  System.out.println("总入口："+url3);
  
        
          //哪了个季度的业绩预告
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
          

          
          //只要当前一天的  因为我们的定时器每天都看
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
            
            
            
            //if(str.indexOf(formatDate)>0){//平时没有问题 但到了季末最后一天就有问题
             //System.out.println(i+"   "+str);
             String[] split = str.split(",");
             
             String 发布date = split[split.length-2];
             
            // if(发布date.equals(formatDate)){
             if(listDate.contains(发布date) || !listDate.contains(发布date)){
             
               
               for(int j = 0 ; j < split.length;j++){
                 
                 String code = split[0];
                 
                 String yg = "http://quotes.money.163.com/f10/yjyg_"+code+".html#01c03";  
                 
                 File downFile2 = stockDow.downFile(yg.toString(),fileName);
                 
                 String readFile3 = stockDow.readFile(downFile,"UTF-8");
                 //System.out.println(readFile3);
                 
                 if(readFile3.indexOf("title_01")>0){
                   String substring2 = readFile3.substring(readFile3.indexOf("title_01"),readFile3.length());
                   String all = substring2.substring(0,substring2.indexOf("</table>"));
                   
                   
                   String 发布日期 = all.substring(all.indexOf("公告日期")+20, all.indexOf("公告日期")+20+10);
                   
                   String name  = all.substring(all.indexOf("span")+18, all.indexOf("span")+18+4);
                   
                   String 报告日期 = all.substring(all.indexOf("报告日期")+20, all.indexOf("报告日期")+20+10);
                   
                   
                   

                   String 预测类型 = all.substring(all.indexOf("预测类型")+32, all.indexOf("预测类型")+32+2);
                   
                   String 预测内容 = all.substring(all.indexOf("预测内容")+32, all.lastIndexOf("</tr>")-15);
                   
                   System.out.println(yg);
                   System.out.println(预测类型+" "+name+" "+code +" "+报告日期+" "+发布日期+" "+ " "+预测内容);
                   
                   
                   
  /*                 System.out.println(预测类型);
                   System.out.println(name);
                   System.out.println(code);
                   System.out.println(报告日期);
                   System.out.println(发布日期);
                   System.out.println(预测内容);*/
                   System.out.println();
                   
                   map.put(code, 预测类型+" "+name+" "+code +" "+报告日期+" "+发布日期+" "+ " "+预测内容);
                   
                 //  
                   
                  // all.substring(all.indexOf("预测内容"), endIndex);
                   
                 }
                 
             
                 
                 /**
                  * 已经预告了的，在一个池里除掉。然后还要告诉 我剩下的还有哪些业绩增长了却没有预告的
                  * */
                 
                 break;
               }
               
               
             }else{
               
               System.out.println("已找不到当天数据");
               continue;
               //break;
               
             }
             
             System.out.println(i);
              
  
            
          }
          
          excel.updateExcelxlxs2("F:\\stock\\git\\new\\新建文件夹\\程序复制\\z预告程序复制2.xlsx", "所有四季利润", map,null);
          
          System.out.println("请查看:F:\\stock\\git\\new\\新建文件夹\\程序复制\\z预告程序复制2.xlsx  所有四季利润");
          
          
          
          
          
        }else{
          System.out.println("规则有变化");
         }
      
        
        
        String url2 = "http://quotes.money.163.com/f10/yjyg_002476.html#01c03";
       
        
     
  }
  
  
  

}
