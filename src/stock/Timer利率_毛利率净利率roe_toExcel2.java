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

public class Timer����_ë���ʾ�����roe_toExcel2 {
  //�°��ɷ�
  
  public static void main(String [] args) throws Exception {
    

    String date0 = "201803";
    System.out.println("�����:"+"http://data.eastmoney.com/bbsj/"+date0+"/yjbb.html");
    
    
    //��
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
      //ֻҪ��ǰһ���  ��Ϊ���ǵĶ�ʱ��ÿ�춼��
      Date currentdate = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String formatDate = sdf.format(currentdate);
      formatDate = "2018-04-27";
      
      
      for(int i = 0 ;i < parseArray.size();i++){
        Object object = parseArray.get(i);
        String str = object.toString();
        System.out.println("str:"+str);
        
        
        if(str.indexOf(formatDate) > 0|| str.indexOf(formatDate) < 0){ //��һ��ȫ����Ҫ
          String[] split = str.split(",");
          
         // for(int j = 0 ; j < split.length;j++){
            String code = split[0];
            String name = split[1];

            
            String incode = split[4];
            String profite = split[7];
            
            String �������� = split[split.length-3];
            String �������� = split[split.length-2];
            
            
            //TODO Ҫ���� ��ҵ��ԭ��
            String url3 = "";
            if(code.startsWith("6")){
            	
            //  url3 = "http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code="+code+"01&num=9&code1=sh"+code+"&spstr=&n=2&timetip=1487063111208";
            	  url3 = "http://emweb.securities.eastmoney.com/PC_HSF10/FinanceAnalysis/MainTargetAjax?code=sh"+code+"&type=0";//���type=1 2  ���ǵ��� 
            }else{
              //ֻ������   ����зǺͿ۷�
            //  url3 = "http://f10.eastmoney.com/f10_v2/BackOffice.aspx?command=RptF10MainTarget&code="+code+"02&num=9&code1=sz"+code+"&spstr=&n=2&timetip=1487063111208";
            	  url3 = "http://emweb.securities.eastmoney.com/PC_HSF10/FinanceAnalysis/MainTargetAjax?code=sz"+code+"&type=0";

            }
            
            
            
            File downFile2 = stockDow.downFile(url3.toString(),"ҵ���۷��벻��");
            
            String readFile3 = stockDow.readFile(downFile2,"UTF8");
            if(readFile3.equals("")){
              continue;
            }
            String substring = readFile3.substring(readFile3.indexOf("["), readFile3.indexOf("]")+1);
            System.out.println(substring);
            JSONArray parseArray2 = JSON.parseArray(substring);
            
            String dateNeed = "2018-03-31"; //ֻ��Ҫ���ļ��ı���
            for(int j = 0 ; j < parseArray2.size();j++){
            	List<String> list = new ArrayList<String>();
            	Object object2 = parseArray2.get(j);
            	Object date1 = JSON.parseObject(object2.toString()).get("date");
             	Object gsjlr = JSON.parseObject(object2.toString()).get("gsjlr");//����
             	Object kfjlr = JSON.parseObject(object2.toString()).get("kfjlr");//�۷�
             	
             	Object jqjzcsyl = JSON.parseObject(object2.toString()).get("jqjzcsyl");//���ʲ�������
             	Object mll = JSON.parseObject(object2.toString()).get("mll");//ë����
             	Object jll = JSON.parseObject(object2.toString()).get("jll");//ë����
             	
             	
             	if(date1.equals(dateNeed)){//ȡ�ڼ��ڵ�����
             		
             		
                    System.out.println(code+ "  "+ name + " "+ incode + " "+ profite + " "+ �������� +"      "+ ��������);
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
          //System.out.println("û�е�������");
        }
        

        
      }
      
      excel.updateExcelxlxs3("F:\\stock\\git\\new\\�½��ļ���\\������\\����.xlsx", "�����ļ�����", map);
      
      
     
      
    }else{
      System.out.println("�����б仯");
    }
    
 
    
    
  }
  

}
