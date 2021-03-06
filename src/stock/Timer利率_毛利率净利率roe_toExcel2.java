package stock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class Timer利率_毛利率净利率roe_toExcel2 {
  //新安股份
  
  public static void main(String [] args) throws Exception {
    

    String date0 = "201803";
    System.out.println("总入口:"+"http://data.eastmoney.com/bbsj/"+date0+"/yjbb.html");
    
    
    //总
    String date = "2018-03-31";
    int page = 1;
    int pageSize = 5000;
    String url = "http://datainterface.eastmoney.com/EM_DataCenter/JS.aspx?type=SR&sty=YJBB&fd="+date+"&st=13&sr=-1&p="+page+"&ps="+pageSize+"&js=var%20DUwfyPhF={pages:(pc),data:[(x)]}&stat=0&rt=49692323";
    
    //http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?type=YJBB20_YJBB&token=70f12f2f4f091e459a279469fe49eca5&st=latestnoticedate&sr=-1&p=2&ps=50&js=var%20dbYkhmfv={pages:(tp),data:%20(x)}&filter=(reportdate=^2017-12-31^)(securitytypecode%20in%20(%27058001001%27,%27058001002%27))&rt=50839478
  //  
    Map<String,List<String>> map = new HashMap<String,List<String>>();
    
    String fileName = "yy";

    System.out.println(url);
    File downFile = stockDow.downFile(url.toString(),fileName);
    
    String readFile = stockDow.readFile(downFile,"UTF8");
    
    if(readFile.indexOf("[")>0){
      String readFile2 = readFile.substring(readFile.indexOf("["), readFile.indexOf("}"));
      System.out.println("readFile2:"+readFile2);
      JSONArray parseArray = JSON.parseArray(readFile2);
      //只要当前一天的  因为我们的定时器每天都看
      Date currentdate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String formatDate = sdf.format(currentdate);
      formatDate = "2018-04-27";
      
      
      for(int i = 0 ;i < parseArray.size();i++){
        Object object = parseArray.get(i);
        String str = object.toString();
        System.out.println("str:"+str);
        
        
        if(str.indexOf(formatDate) > 0|| str.indexOf(formatDate) < 0){ //第一次全部都要
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
            	
            //  url3 = "http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code="+code+"01&num=9&code1=sh"+code+"&spstr=&n=2&timetip=1487063111208";
            	  url3 = "http://emweb.securities.eastmoney.com/PC_HSF10/FinanceAnalysis/MainTargetAjax?code=sh"+code+"&type=0";//如果type=1 2  则是单季 
            }else{
              //只有数字   这个有非和扣非
            //  url3 = "http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code="+code+"02&num=9&code1=sz"+code+"&spstr=&n=2&timetip=1487063111208";
            	  url3 = "http://emweb.securities.eastmoney.com/PC_HSF10/FinanceAnalysis/MainTargetAjax?code=sz"+code+"&type=0";

            }
            
            
            
            File downFile2 = stockDow.downFile(url3.toString(),"业绩扣非与不扣");
            
            String readFile3 = stockDow.readFile(downFile2,"UTF8");
            if(readFile3.equals("")){
              continue;
            }
            String substring = readFile3.substring(readFile3.indexOf("["), readFile3.indexOf("]")+1);
            System.out.println(substring);
            JSONArray parseArray2 = JSON.parseArray(substring);
            
            String dateNeed = "2018-03-31"; //只需要第四季的报告
            for(int j = 0 ; j < parseArray2.size();j++){
            	List<String> list = new ArrayList<String>();
            	Object object2 = parseArray2.get(j);
            	Object date1 = JSON.parseObject(object2.toString()).get("date");
             	Object gsjlr = JSON.parseObject(object2.toString()).get("gsjlr");//归属
             	Object kfjlr = JSON.parseObject(object2.toString()).get("kfjlr");//扣非
             	
             	Object jqjzcsyl = JSON.parseObject(object2.toString()).get("jqjzcsyl");//净资产收益率
             	Object mll = JSON.parseObject(object2.toString()).get("mll");//毛利率
             	Object jll = JSON.parseObject(object2.toString()).get("jll");//毛利率
             	
             	
             	if(date1.equals(dateNeed)){//取第几期的利润
             		
             		
                    System.out.println(code+ "  "+ name + " "+ incode + " "+ profite + " "+ 公告日期 +"      "+ 发布日期);
                    System.out.println();
                    System.out.println(gsjlr);
                    System.out.println(kfjlr);
                    System.out.println();
                    System.out.println(i);
                    
                    list.add(mll.toString());
                    list.add(jll.toString());
                    list.add(jqjzcsyl.toString());
                    
                    map.put(code, list);
                    System.out.println();
             	}
            }
        
          
          
        }else{
          //System.out.println("没有当天数据");
        }
        

        
      }
      
      excel.updateExcelxlxs3("F:\\stock\\git\\new\\新建文件夹\\程序复制\\利率.xlsx", "所有四季利润", map);
      
      
     
      
    }else{
      System.out.println("规则有变化");
    }
    
 
    
    
  }
  

}
