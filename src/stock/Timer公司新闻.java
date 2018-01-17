package stock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Timer公司新闻 {
  
  public static void main(String [] args) throws Exception {
  // a金融界();
 //   b证券之星();
//   c全景();
  // d东财();
    
    e腾讯();
	  
	//  f中国证券网();

    //http://company.cnstock.com/company/scp_gsxw
    
  //  String url3 = "http://www.p5w.net/stock/news/zonghe/";//要不要
  }
  
  private static void f中国证券网() throws Exception{
	  
	  // http://xinpi.cnstock.com/
	  
	  String url = "http://company.cnstock.com/company/scp_gsxw/";
	  String url2 = "http://company.cnstock.com/company/scp_gsxw/2";
	  String url3 = "http://company.cnstock.com/company/scp_gsxw/3";
	  String url4 = "http://company.cnstock.com/company/scp_gsxw/4";
	  String url5 = "http://company.cnstock.com/company/scp_gsxw/5";
	  String url6 = "http://company.cnstock.com/company/scp_gsxw/6";
	  String url7 = "http://company.cnstock.com/company/scp_gsxw/7";
	  String url8 = "http://company.cnstock.com/company/scp_gsxw/8";
	  
	  
	  String[] urls = new String[]{url,url2,url3,url4,url5,url6,url7,url8};
	  
	  
	    for(int i = 0 ;i < urls.length;i++){
	        System.out.println();
	        System.out.println("正在查询：" +urls[i]);
	        System.out.println();
	        String fileName = "xw";
	        File downFile = stockDow.downFile(urls[i].toString(),fileName);
	        
	        String readFile = stockDow.readFile(downFile,"UTF-8");
	        System.out.println(readFile);
	        
	        if(i==0){
	        	break;
	        }
	        
	        
	        
	        
	        
	        
	    
	        
	        
	    }
	  
	  
	  
	  
  }
  
  private static void e腾讯() throws Exception{
    System.out.println("e腾讯=====================================================start");
    String url = "http://stock.qq.com/l/stock/shsgs/list20150423134920.htm";
    String url2 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_2.htm";
    String url3 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_3.htm";
    String url4 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_4.htm";
    String url5 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_5.htm";
    String url6 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_6.htm";
    String url7 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_7.htm";
    String url8 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_8.htm";
    String url9 = "http://stock.qq.com/l/stock/shsgs/list20150423134920_9.htm";
    String url10 ="http://stock.qq.com/l/stock/shsgs/list20150423134920_10.htm";
    String url11 ="http://stock.qq.com/l/stock/shsgs/list20150423134920_11.htm";
    String url12 ="http://stock.qq.com/l/stock/shsgs/list20150423134920_12.htm";
    String url13 ="http://stock.qq.com/l/stock/shsgs/list20150423134920_13.htm";
    
    
    String[] urls = new String[]{url,url2,url3,url4,url5,url6,url7,url8,url9,url10,url11,url12,url13};
    
    for(int i = 0 ;i < urls.length;i++){
      System.out.println();
      System.out.println("正在查询：" +urls[i]);
      System.out.println();
      String fileName = "xw";
      File downFile = stockDow.downFile(urls[i].toString(),fileName);
      
      String readFile = stockDow.readFile(downFile,"GBK");
      
      
      int indexOf = readFile.indexOf("listInfo:");
      
      String substring = readFile.substring(indexOf+9, readFile.length());
      
      
      int indexOf5 = substring.indexOf("}]");
      
      String substring2 = substring.substring(0, indexOf5+2);
      
      JSONArray parseArray = JSON.parseArray(substring2);
      
      
      Date currentdate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String formatDate = sdf.format(currentdate);
      
      formatDate = "2017-07-03";
      
      
      for(int k = 0 ;k < parseArray.size();k++){
    	  
    	  JSONObject jsonObject = parseArray.getJSONObject(k);
    	  String date = jsonObject.get("pubtime").toString();
    	  String title = jsonObject.get("title").toString();
    	  String hrefUrl = jsonObject.get("url").toString();
    	  
    	  
          if(date.indexOf(formatDate)<0){
              break;
            }
            
            System.out.println( date + "   "+title+"   "+hrefUrl);
            
            
      }
      
      
              
      }
    System.out.println("e腾讯=====================================================end");
      
    }
    


    
  
  
  
  /**
   * 
   * @throws Exception 
   * */
  private static void d东财() throws Exception{
	  System.out.println("d东财=====================================================start");
	  
    String url = "http://finance.eastmoney.com/news/cgsxw.html";
    String url2 = "http://finance.eastmoney.com/news/cgsxw_2.html";
    String url3 = "http://finance.eastmoney.com/news/cgsxw_3.html";
    String url4 = "http://finance.eastmoney.com/news/cgsxw_4.html";
    String url5 = "http://finance.eastmoney.com/news/cgsxw_5.html";
    String url6 = "http://finance.eastmoney.com/news/cgsxw_6.html";
    String url7 = "http://finance.eastmoney.com/news/cgsxw_7.html";
    String url8 = "http://finance.eastmoney.com/news/cgsxw_8.html";
    String url9 = "http://finance.eastmoney.com/news/cgsxw_9.html";
    String url10 = "http://finance.eastmoney.com/news/cgsxw_10.html";
    String url11 = "http://finance.eastmoney.com/news/cgsxw_11.html";
    String url12 = "http://finance.eastmoney.com/news/cgsxw_12.html";
    String url13 = "http://finance.eastmoney.com/news/cgsxw_13.html";
    String url14 = "http://finance.eastmoney.com/news/cgsxw_14.html";
    String url15 = "http://finance.eastmoney.com/news/cgsxw_15.html";
    String url16 = "http://finance.eastmoney.com/news/cgsxw_16.html";
    String url17 = "http://finance.eastmoney.com/news/cgsxw_17.html";
    String url18 = "http://finance.eastmoney.com/news/cgsxw_18.html";
    String url19 = "http://finance.eastmoney.com/news/cgsxw_19.html";
    String url20 = "http://finance.eastmoney.com/news/cgsxw_20.html";
    String url21 = "http://finance.eastmoney.com/news/cgsxw_21.html";
    String url22 = "http://finance.eastmoney.com/news/cgsxw_22.html";
    
    String[] urls = new String[]{url,url2,url3,url4,url5,url6,url7,url8,url9,url10,url11,url12,url13,url14,url15,url16,url17,url18,url19,url20,url21};
    for(int k = 0 ; k < urls.length;k++){
      
    	
    	
    	if("http://finance.eastmoney.com/news/cgsxw_3.html".equals(urls[k])){
    		System.out.println();
    	}
    	 
      System.out.println();
      System.out.println("正在查询：" +urls[k]);
      System.out.println();
      String fileName = "xw";
      File downFile = stockDow.downFile(urls[k].toString(),fileName);
      
      String readFile = stockDow.readFile(downFile,"UTF8");
      
      int indexOf = readFile.indexOf("repeatList");
      
      
      String substring = readFile.substring(indexOf+13, readFile.length());
      
      
      int indexOf2 = substring.indexOf("</ul>");
      
      String substring2 = substring.substring(0, indexOf2);
      
      
      String[] split = substring2.split("</li>");
      
      
      
      Date currentdate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
      String formatDate = sdf.format(currentdate);
      
      formatDate = "7月03日";
      
      for(int i = 0 ; i < split.length-1;i++){
        
        String li = split[i];
        
        if(li.indexOf(formatDate)<0){
          //System.out.println("已没有当天数据  睡着");
         // Thread.sleep(50000);
         // break;
          continue;
        }
        
        String[] split2 = li.split("</div>");
        
        
        String con = split2[0];
        if(con.indexOf("<img src=")>-1){
        	con = split2[1];
        }
        
        
        
        /**
         *     <li id="newsTr1">
        <!--文-->
        <div class="text text-no-img">
            <p class="title">
                <a href="http://finance.eastmoney.com/news/1354,20170701752100652.html" target="_blank">
                    银星能源拟购银仪风电50%股权
                </a>
            </p>

                <p class="info">
                    银星能源今日公告称，公司6月30日与控股股东中铝宁夏能源签署了重大资产重组框架协议，拟收购后者持有的宁夏银仪风力发电有限责任公司(下称“银仪风电”)50%的股权。
                </p>
            <p class="time">
                07月01日 03:05
            </p>
         * */
        
        if(con.indexOf(".html") < 0){
          continue;
        }
        int indexOf3 = con.indexOf("http");
        int indexOf4 = con.indexOf(".html");
        
        String hrefUrl = con.substring(indexOf3, indexOf4) + ".html";

        
        int indexOf5 = con.indexOf("</a>");
        
        
        String title = con.substring(indexOf4+42, indexOf5);

        
        String[] split3 = con.split("</p>");
        
        String content = split3[1];
        String date = split3[2];
        
        
        date = date.substring(30, date.length());
        
        int indexOf6 = content.indexOf(">");
        content = content.substring(indexOf6+20, content.length());
        
        

        //String content = con.substring(indexOf6+23, indexOf7-95);
        
      //  String date = con.substring(indexOf7+34, indexOf7+58);
        
        System.out.println(date.trim()+" -  "+title.trim()+" - "+content.trim()+" - "+hrefUrl);
      
    }
    

      
    }
    
    
    System.out.println("d东财=====================================================end");
    
  }
  
  private static void c全景() throws Exception{
	  System.out.println("c全景=====================================================start");
    String url1 = "http://www.p5w.net/stock/news/gsxw/";
    String url2 = "http://www.p5w.net/stock/news/gsxw/index_1.htm";
    String url3 = "http://www.p5w.net/stock/news/gsxw/index_2.htm";
    String url4 = "http://www.p5w.net/stock/news/gsxw/index_3.htm";
    String url5 = "http://www.p5w.net/stock/news/gsxw/index_4.htm";
    String url6 = "http://www.p5w.net/stock/news/gsxw/index_5.htm";
    
    
    String[] urls = new String[]{url1,url2,url3,url4,url5,url6};
    
    for(int i = 0 ;i < urls.length;i++){
      
      System.out.println("正在查询：" +urls[i]);
      System.out.println();
      String fileName = "xw";
      File downFile = stockDow.downFile(urls[i].toString(),fileName);
      
      String readFile = stockDow.readFile(downFile,"GBK");
      
      int indexOf = readFile.indexOf("<ul>");
      int indexOf2 = readFile.indexOf("</ul>");
      
      String substring = readFile.substring(indexOf, indexOf2);
      String[] split = substring.split("</li>");
      
      Date currentdate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
      String formatDate = sdf.format(currentdate);
      
      formatDate = "06月30日";
      
      for(int j = 0 ; j < split.length-1;j++){
        String string = split[j];
        
        
        //03月28日
        if(string.indexOf(formatDate)<0){
          System.out.println("urls[i]  "+urls[i]);
          System.out.println("没有当前日期的数据");
          break;
        }
        
        int indexOf3 = string.indexOf("http");
        int indexOf4 = string.indexOf(".htm");
        String hrefUrl = string.substring(indexOf3, indexOf4)+".htm";
        
        int indexOf5 = string.indexOf("_blank");
        int indexOf6 = string.indexOf("</a>");
        String content1 = string.substring(indexOf5+8, indexOf6);
        
        int indexOf7 = string.indexOf("setinfo3");
        int indexOf8 = string.indexOf("</div>");
        String date = string.substring(indexOf7+10, indexOf8);
        
        
        int indexOf9 = string.indexOf("<p>");
        int indexOf10 = string.indexOf("</p>");
        String content2 = string.substring(indexOf9+3, indexOf10);
        
        System.out.println(date+ "  "+content1.trim()+"    "+content2.trim() + "   "+hrefUrl);
      }
      
      System.out.println();
      
    }
    
    System.out.println("c全景=====================================================end");
    
  }
  
//TODO
  /**
   * 
   * */
  private static void b证券之星() throws Exception{
	  
	  System.out.println("b证券之星=====================================================start");
	  
    String url = "http://stock.stockstar.com/list/10.shtml";
    String url2 = "http://stock.stockstar.com/list/10_2.shtml";
    String url3 = "http://stock.stockstar.com/list/10_3.shtml";
    String url4 = "http://stock.stockstar.com/list/10_4.shtml";
    
    String url5 = "http://stock.stockstar.com/list/10_5.shtml";
    String url6 = "http://stock.stockstar.com/list/10_6.shtml";
    String url7 = "http://stock.stockstar.com/list/10_7.shtml";
    
    String url8 = "http://stock.stockstar.com/list/10_8.shtml";
    String url9 = "http://stock.stockstar.com/list/10_9.shtml";
    String url10 = "http://stock.stockstar.com/list/10_10.shtml";
  //  String url11 = "http://stock.stockstar.com/list/10_11.shtml";
  //  String url12 = "http://stock.stockstar.com/list/10_12.shtml";
 //   String url13 = "http://stock.stockstar.com/list/10_13.shtml";
    
    
    String[] urls = new String[]{url,url2,url3,url4,url5,url6,url7,url8,url9,url10};
    for(int k = 0 ; k < urls.length;k++){
      
      System.out.println(urls[k]);
      System.out.println();
      String fileName = "xw";
      File downFile = stockDow.downFile(urls[k].toString(),fileName);
      
      String readFile = stockDow.readFile(downFile,"GBK");
      
      if(readFile.indexOf("listnews")> 0){
        
        int indexOf = readFile.indexOf("listnews");
        
        String substring = readFile.substring(indexOf, readFile.length());
        
        String substring2 = substring.substring(substring.indexOf("<ul>")+4, substring.indexOf("</ul>"));
        String[] split = substring2.split("</li>");
        
        //只要当前一天的  因为我们的定时器每天都看
        Date currentdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(currentdate);
        formatDate = "2017-06-30";
        
        for(int i = 0 ; i < split.length-1;i++){
          
          String string = split[i].trim();
          if(string.indexOf("space")>0){
            continue; 
           }
          if(string.indexOf(formatDate)>0){
            

             int indexOf2 = string.indexOf("<span>");
             int indexOf3 = string.indexOf("</span>");
             String date = string.substring(indexOf2+6, indexOf3);
             
             int indexOf4 = string.indexOf("http");
             int indexOf5 = string.indexOf(".shtml");
             String hrefUrl = string.substring(indexOf4, indexOf5)+".shtml";
             
             
             int indexOf6 = string.indexOf("\">");
             int indexOf7 = string.indexOf("</a>");
             String content = string.substring(indexOf6+2, indexOf7);
             System.out.println( " (" +date+")"+content+"         "+ hrefUrl);
             
          }else{
           // System.out.println("没有当天数据");
          }
        }
      }else{
      //  System.out.println("没找到数据");
      }
      
      
    }
    

    System.out.println("b证券之星=====================================================end");
    
    
  }
  
  
  
  private static void a金融界() throws Exception{
	  System.out.println("a金融界=====================================================start");
	  
    String url = "http://stock.jrj.com.cn/list/stockssgs.shtml";
    String url2 = "http://stock.jrj.com.cn/list/stockssgs-2.shtml";
    String url3 = "http://stock.jrj.com.cn/list/stockssgs-3.shtml";
    String url4 = "http://stock.jrj.com.cn/list/stockssgs-4.shtml";
    String url5 = "http://stock.jrj.com.cn/list/stockssgs-5.shtml";
    String url6 = "http://stock.jrj.com.cn/list/stockssgs-6.shtml";
    String url7 = "http://stock.jrj.com.cn/list/stockssgs-7.shtml";
    String url8 = "http://stock.jrj.com.cn/list/stockssgs-8.shtml";
    String url9 = "http://stock.jrj.com.cn/list/stockssgs-9.shtml";
    String url10 = "http://stock.jrj.com.cn/list/stockssgs-10.shtml";
    String url11 = "http://stock.jrj.com.cn/list/stockssgs-11.shtml";
    String url12 = "http://stock.jrj.com.cn/list/stockssgs-12.shtml";
    String url13 = "http://stock.jrj.com.cn/list/stockssgs-13.shtml";
    
    
    String[] urls = new String[]{url,url2,url3,url4,url5,url6,url7,url8,url9,url10,url11,url12,url13};
    for(int j = 0 ; j < urls.length;j++){
      
      System.out.println(urls[j]);
      String fileName = "xw";
      File downFile = stockDow.downFile(urls[j].toString(),fileName);
      
      String readFile = stockDow.readFile(downFile,"GBK");
      String substring2 = "";
      if(readFile.indexOf("<ul>")>0){
        int indexOf = readFile.indexOf("<ul>");
        String substring = readFile.substring(indexOf+26, readFile.length());
        
        int indexOf2 = substring.indexOf("</ul>");
        
        substring2 = substring.substring(0, indexOf2);
        
        String[] split = substring2.split("</li>");
        
        
        
        Date currentdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = sdf.format(currentdate);
        
        formatDate = "2017-06-30";
        
        for(int i = 0 ; i < split.length;i++){
          String string = split[i].trim();
          
          if("<li class=\"line\">".equals(string)){
            continue;
          }

          
          //当天
          if(string.indexOf(formatDate)>0){
            String date = string.substring(10, 27);
            //System.out.println();
            
            int indexOf3 = string.indexOf("http");
            int indexOf4 = string.indexOf(".shtml");
            String hrefUrl = string.substring(indexOf3, indexOf4) + ".shtml";
            //System.out.println(hrefUrl);
            
            int indexOf5 = string.indexOf("\">");
            int indexOf6 = string.indexOf("</a>");
            
            String content = string.substring(indexOf5+2, indexOf6);
            System.out.println("  (时间:"+date +")"+content+"                       "+hrefUrl);

          }else{
            
            System.out.println("非当天   读取到：j "+j);
            break;
          }

        }

      }
      
      
    }
    
    System.out.println("a金融界=====================================================end");
    

  }
  


}
