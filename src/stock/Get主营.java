package stock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Get主营 {
  
  public static void main(String [] args) throws Exception {
    //getService("保理");
    getAllService();
  }
  
  public static void getAllService()throws Exception{
    String fileName = "主营2";
    List<Integer> prepareData = appendHtml新浪预告.prepareData();
    
    
    Map<String,String> map = new HashMap<String,String>();
    
    
    for(int i = 0 ; i < prepareData.size();i++){
      String code = prepareData.get(i).toString();
      if(code.equals("11111111")){
        continue;
      }
      
      
      if(code.startsWith("9")){
        String substring = code.toString().substring(1, code.toString().length());
        code = substring;
        
      }
      
      
      String url = "http://stock.quote.stockstar.com/corp/business_"+code+".shtml ";
      //String url = "http://stock.quote.stockstar.com/corp/business_002797.shtml ";
      File downFile = stockDow.downFile(url.toString(),fileName);
      
      String readFile = stockDow.readFile(downFile,"GBK");
      
      int indexOf4 = readFile.indexOf("<title>");
      String name = readFile.substring(indexOf4+7, indexOf4+11);
      
      
      String key1 = "按行业分类";
      String key2 = "按产品分类";
      String key3 = "按地域分类";
      
      int indexOf = readFile.indexOf(key1);
      int indexOf2 = readFile.indexOf(key2);
      int indexOf3 = readFile.indexOf(key3);
      
      String substring1 = "";
      String substring2 = "";
      
      if(indexOf>0){//有  按行业分类  
        if(indexOf2<0){
          indexOf2 = indexOf + 2100;
          substring1 = readFile.substring(indexOf, indexOf2);

        }else{
          substring1 = readFile.substring(indexOf, indexOf2);
          
          if(indexOf3<0){
            indexOf3 = indexOf2 + 2100;
          }
          substring2 = readFile.substring(indexOf2, indexOf3);
        }
        
        
        
         
      }else{
        
        if(indexOf2>0){
          if(indexOf3<0){
            indexOf3 = indexOf2 + 2100;
          }
          substring2 = readFile.substring(indexOf2, indexOf3);
        }
        
        
      }
      

      System.out.println(code+"   "+name);
     // System.out.println(url);
      if(!substring1.equals("")){
        String [] split = substring1.split("tbody");
        //System.out.println("行业分类");
        //System.out.println(split[3].trim());
        String string = split[3];
        
        String [] split2 = string.split("align_left\">");
        List<String> list = new ArrayList<String>();
        for(int j = 1 ;j < split2.length;j++){

          int service = split2[j].indexOf("</td>");
          String serviceCon = split2[j].substring(0, service);
          list.add(serviceCon);
          //System.out.println(serviceCon);
          
          map.put(serviceCon, serviceCon);
        }
        
        System.out.println("行业分类:"+list.toString());
      }
      
      
      if(!substring2.equals("")){
        String [] split = substring2.split("tbody");
        
        String string = split[3];
        
        String [] split2 = string.split("align_left\">");
        
        List<String> list = new ArrayList<String>();
        
/*        if(split2.length==1){
          System.out.println("企业简单");
        }*/
        
        for(int j = 1 ;j < split2.length;j++){

          int service = split2[j].indexOf("</td>");
          String serviceCon = split2[j].substring(0, service);
          list.add(serviceCon);
          
          
          map.put(serviceCon, serviceCon);
        
        }
        System.out.println("产品分类"+list.toString());
        
      }
      
      //System.out.println(i);
      //System.out.println();
      
      
/*      if(i == 4000){
        
        break;
      }
      */
      
      
      
    }
    
    
    int j = 1;
    for (String key : map.keySet()) {
    	   System.out.println( key );
    	   j++;
    	   if(j==500){
    		   System.out.println(j);
    		   Thread.sleep(5000);
    	   }
    	   
    	   if(j==1000){
    		   System.out.println(j);
    		   Thread.sleep(5000);
    	   }
    	   
    	   
    	   if(j==1500){
    		   System.out.println(j);
    		   Thread.sleep(5000);
    	   }
    	   
    	   
    	   if(j==2000){
    		   System.out.println(j);
    		   Thread.sleep(5000);
    	   }
    	   
    	   
    	   if(j==2500){
    		   System.out.println(j);
    		   Thread.sleep(5000);
    	   }
    	   
    	   
    	   if(j==3000){
    		   System.out.println(j);
    		   Thread.sleep(5000);
    	   }
    	   
    	   
   }
    
  }
  
  public static void getService(String keyWord) throws Exception{
    String fileName = "主营";
    
    List<Integer> prepareData = appendHtml新浪预告.prepareData();
    for(int j = 0 ;j < prepareData.size();j++){
      String code = prepareData.get(j).toString();
      if(code.equals("11111111")){
        continue;
      }
      
      if(code.startsWith("9")){
        String substring = code.toString().substring(1, code.toString().length());
        code = substring;
        
      }
      
      String url = "http://stock.quote.stockstar.com/corp/business_"+code+".shtml ";
      File downFile = stockDow.downFile(url.toString(),fileName);
      
      String readFile = stockDow.readFile(downFile,"GBK");
      int i = 0;
      if(readFile.indexOf(keyWord)>-1){
        System.out.println(readFile);
        i++;
      }else{
        i++;
      }
      System.out.println(i);
      
      
      
      
    }
    

    

    
    
    
    
  }
  
  

}
