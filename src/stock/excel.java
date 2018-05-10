package stock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import jxl.Cell;
import jxl.Workbook;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;



public class excel {
  
  public static void main(String [] args) throws Exception {
    

    
    Map<String,String> map = new HashMap<String,String>();
    map.put("000420", "ddd");
   // modifyExcelJXl("H:"+File.separator+"�½��ļ���"+File.separator+"�½��ļ���"+File.separator+"ͼtest.xls",map,53);
    
  //  modifyExcelJXl("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\tt.xls",map,53);
    
    
   // readExcel2("H:\\�½��ļ���\\�½��ļ���\\ͼtest.xls",map,53);
    //readExcel2("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\te.xlsx",map,10);
    
  //  updateExcelXlxs("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\tab.xlsx","�����ļ�����",map);
    
    
    //updateExcel("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\tea.xls","�����ļ�����",map);
    
 //   updateExcelxlxs2("H:\\�½��ļ���\\�½��ļ���\\������\\zԤ�������.xlsx","�����ļ�����",map,null);
  //  updateExcelxlxs2("H:\\�½��ļ���\\�½��ļ���\\������\\zҵ�����������.xlsx","�����ļ�����",map);
 //   updateExcelxlxs2("H:\\�½��ļ���\\�½��ļ���\\������\\zҵ����������걨����.xlsx","�����ļ�����",map);
    
    
    readExcel0415��("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\all.xls");
    
  }
  
  
  
  public static void updateExcelxlxs2(String exlFile,String sheetName,Map<String,String> map,Map<String,String> map2)throws Exception{
    FileInputStream fis=new FileInputStream(exlFile);
    XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
    
    XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
    
    int lastRowNum = sheet.getLastRowNum();
    for(int i = 18 ; i < lastRowNum ; i++){
      //XSSFRow row = sheet.getRow(i);
       XSSFRow row = sheet.getRow(i);
      
      if(row == null){
        continue;
      }
      
      //XSSFCell cell = row.getCell(1);
      XSSFCell cell = row.getCell(0);
      if(cell == null ){
        continue;
      }
      String code = cell.getStringCellValue();// Cannot get a numeric value from a text cell
      System.out.println("code:"+code);
      String content = map.get(code);
      
      if(null == content || "".equals(content)){
        continue;
      }
//    int type=cell.getCellType();
      XSSFCell cell2 = row.getCell(5);
      if(cell2 == null ){
        cell2 = row.createCell(5);
        cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
        
      }
    String oldValue = cell2.getStringCellValue();
    //��������Ӧ��Ԫ��ԭ��������Ҳ��String����
    cell2.setCellValue(content);
    map.remove(code);
//    System.out.println("��Ԫ��ԭ��ֵΪ"+oldValue);
 //   System.out.println("��Ԫ��ֵ������Ϊ"+content);
    
    
    if(null!= map2){
      XSSFRow row2 = sheet.getRow(i+1);
      if(row2 == null){
        continue;
      }
      
      XSSFCell cell3 = row2.getCell(0);
      if(cell3 == null ){
        continue;
      }
      
      String code3 = cell3.getStringCellValue();// Cannot get a numeric value from a text cell
      
      String content2 = map2.get(code3);
      
      if(null == content2 || "".equals(content2)){
        continue;
      }
      
      XSSFCell cell5 = row2.getCell(5);
      if(cell5 == null ){
        cell5 = row2.createCell(5);
        cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
      }
      
      cell5.setCellValue(content2);
      map2.remove(code3);
      
      
    }
    
    
    }
    System.out.println("ƥ�䲻����map");
    System.out.println(map.toString());

    fis.close();//�ر��ļ�������

    FileOutputStream fos=new FileOutputStream(exlFile);
    xssfWorkbook.write(fos);
    fos.close();//�ر��ļ������
}
  
  
  
  public static void updateExcelxlxs3(String exlFile,String sheetName,Map<String,List<String>> map)throws Exception{
	    FileInputStream fis=new FileInputStream(exlFile);
	    XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
	    
	    XSSFSheet sheet = xssfWorkbook.getSheet(sheetName);
	    
	    int lastRowNum = sheet.getLastRowNum();
	    for(int i = 18 ; i < lastRowNum ; i++){
	      //XSSFRow row = sheet.getRow(i);
	       XSSFRow row = sheet.getRow(i);
	      
	      if(row == null){
	        continue;
	      }
	      
	      //XSSFCell cell = row.getCell(1);
	      XSSFCell cell = row.getCell(0);
	      if(cell == null ){
	        continue;
	      }
	      String code = cell.getStringCellValue();// Cannot get a numeric value from a text cell
	      System.out.println("code:"+code);
	      List<String> content = map.get(code);
	      
	      if(null == content || "".equals(content)){
	        continue;
	      }
	      
	      for(int j = 0 ; j < 3;j++){
//	  	    int type=cell.getCellType();
		      XSSFCell cell2 = row.getCell(5+j);
		      if(cell2 == null ){
		        cell2 = row.createCell(5+j);
		        cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		        
		      }
		    String oldValue = cell2.getStringCellValue();
		    //��������Ӧ��Ԫ��ԭ��������Ҳ��String����
		    cell2.setCellValue(content.get(j));
	    	  
	      }

	    
	    
	    map.remove(code);
//	    System.out.println("��Ԫ��ԭ��ֵΪ"+oldValue);
	 //   System.out.println("��Ԫ��ֵ������Ϊ"+content);
	    
	    
	   
	    
	    
	    }
	    System.out.println("ƥ�䲻����map");
	    System.out.println(map.toString());

	    fis.close();//�ر��ļ�������

	    FileOutputStream fos=new FileOutputStream(exlFile);
	    xssfWorkbook.write(fos);
	    fos.close();//�ر��ļ������
	}
  
  
  
  
  public static void updateExcelXlxs(String exlFile,String sheetName,Map<String,String> map)throws Exception{
    
    
      
    FileInputStream  fis =new FileInputStream(exlFile);
      
    FileOutputStream  fos=new FileOutputStream(exlFile);
      XSSFWorkbook workbook = new XSSFWorkbook(fis);
      
      XSSFSheet sheet = workbook.getSheet(sheetName);
      
      
      int lastRowNum = sheet.getLastRowNum();
      for(int i = 18 ; i < lastRowNum ; i++){
        XSSFRow row = sheet.getRow(i);
        
        if(row == null){
          continue;
        }
        
        XSSFCell cell = row.getCell(1);
        
       // http://blog.csdn.net/lishengbo/article/details/40711769
          
          
        if(cell == null ){
          continue;
        }
        String code = cell.getStringCellValue();// Cannot get a numeric value from a text cell
        
        String content = map.get(code);
        
        if(null == content || "".equals(content)){
          continue;
        }
//      int type=cell.getCellType();
        XSSFCell cell2 = row.getCell(53);
        if(cell2 == null ){
          cell2 = row.createCell(53);
          cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
          
        }
      String oldValue = cell2.getStringCellValue();
      //��������Ӧ��Ԫ��ԭ��������Ҳ��String����
      cell2.setCellValue(content);
      System.out.println("��Ԫ��ԭ��ֵΪ"+oldValue);
      System.out.println("��Ԫ��ֵ������Ϊ"+content);
      }
      
      
      workbook.write(fos);
      

        fis.close();
        fos.close();//�ر��ļ������



 
}
  
  
  /**
   
   * */
  public static void modifyExcelJXl(String excelpath,Map<String,String> map,int cellnum){
    try {
      jxl.Workbook wb =null; //����һ��workbook����
  
      InputStream is = new FileInputStream(excelpath); //����һ���ļ���������Excel�ļ�
      wb = Workbook.getWorkbook(is); //���ļ���д�뵽workbook����

    // jxl.Workbook ������ֻ���ģ��������Ҫ�޸�Excel����Ҫ����һ���ɶ��ĸ���������ָ��ԭExcel�ļ����������new File(excelpath)��
    jxl.write.WritableWorkbook wbe= Workbook.createWorkbook(new File(excelpath), wb);//����workbook�ĸ���
    WritableSheet sheet = wbe.getSheet("�����ļ�����"); //��ȡ��һ��sheet
    
    int rows = sheet.getRows();
    for(int i = 18 ; i < rows;i++){
      Cell[] cells = sheet.getRow(i);
      if(cells.length==0){
        continue;
      }
      
      Cell cell = cells[1];
      
      if(cell == null){
        continue;
      }
      
      String code = cell.getContents();
      
      
      String content = map.get(code);
      
      if(null == content || "".equals(content)){
        continue;
      }
      
      WritableCell cell2 =sheet.getWritableCell(i, cellnum);//��ȡ��һ����Ԫ��   //��һ����������  �ڶ������� ��
      jxl.format.CellFormat cf = cell2.getCellFormat();//��ȡ��һ����Ԫ��ĸ�ʽ
      jxl.write.Label lbl = new jxl.write.Label(i, cellnum, content);//����һ����Ԫ���ֵ��Ϊ���޸����ֵ��
      lbl.setCellFormat(cf);//���޸ĺ�ĵ�Ԫ��ĸ�ʽ�趨�ɸ�ԭ��һ��

      sheet.addCell(lbl);//���Ĺ��ĵ�Ԫ�񱣴浽sheet
      
    //  map.remove(code);
      
    }
    
    wbe.write();//���޸ı��浽workbook --��һ��Ҫ����
    wbe.close();//�ر�workbook���ͷ��ڴ� ---��һ��Ҫ�ͷ��ڴ�

    } catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    } 

   } 
  
  public static void updateExcel(String exlFile,String sheetName,Map<String,String> map)throws Exception{
    FileInputStream fis=new FileInputStream(exlFile);
    HSSFWorkbook workbook=new HSSFWorkbook(fis);
//    workbook.
    //HSSFSheet sheet=workbook.getSheetAt(sheetIndex);
    
    HSSFSheet sheet = workbook.getSheet(sheetName);
    
    
    int lastRowNum = sheet.getLastRowNum();
    for(int i = 18 ; i < lastRowNum ; i++){
      //XSSFRow row = sheet.getRow(i);
       HSSFRow row = sheet.getRow(i);
      
      if(row == null){
        continue;
      }
      
      //XSSFCell cell = row.getCell(1);
      HSSFCell cell = row.getCell(1);
      if(cell == null ){
        continue;
      }
      String code = cell.getStringCellValue();// Cannot get a numeric value from a text cell
      
      String content = map.get(code);
      
      if(null == content || "".equals(content)){
        continue;
      }
//    int type=cell.getCellType();
      HSSFCell cell2 = row.getCell(5);
      if(cell2 == null ){
        cell2 = row.createCell(5);
        cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
        
      }
    String oldValue = cell2.getStringCellValue();
    //��������Ӧ��Ԫ��ԭ��������Ҳ��String����
    cell2.setCellValue(content);
    System.out.println("��Ԫ��ԭ��ֵΪ"+oldValue);
    System.out.println("��Ԫ��ֵ������Ϊ"+content);
    }
    



    fis.close();//�ر��ļ�������

    FileOutputStream fos=new FileOutputStream(exlFile);
    workbook.write(fos);
    fos.close();//�ر��ļ������
}
  
  
  
  
  public static void readExcel2(String fileName,Map<String,String> map,int cellnum) {
    
    
    FileOutputStream fOut = null;
    InputStream in = null;
    try{
      fOut = new FileOutputStream(fileName);
      in =  new FileInputStream(fileName);
      
      // HSSFWorkbook workbook = new HSSFWorkbook(in);
       XSSFWorkbook workbook = new XSSFWorkbook(in);
     //  org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(in);
      // XSSFSheet sheet = workbook.getSheetAt(0);
      XSSFSheet sheet = workbook.getSheet("�����ļ�����");
      
     // Sheet sheet = workbook.getSheet("�����ļ�����");
       
       
       int lastRowNum = sheet.getLastRowNum();
       
       
       //Map<Integer,List<Object>> map = new TreeMap<Integer,List<Object>>();
       for(int i = 18 ; i < lastRowNum ; i++){
         //XSSFRow row = sheet.getRow(i);
         Row row = sheet.getRow(i);
         
         
         if(row == null){
           continue;
         }
         
         //XSSFCell cell = row.getCell(1);
         org.apache.poi.ss.usermodel.Cell cell = row.getCell(1);
         if(cell == null ){
           continue;
         }
         String code = cell.getStringCellValue();// Cannot get a numeric value from a text cell
         
         String content = map.get(code);
         
         if(null == content || "".equals(content)){
           continue;
         }
         
          //XSSFCell createCell = row.createCell(cellnum);
         org.apache.poi.ss.usermodel.Cell createCell = row.createCell(cellnum);
         //createCell.setCellType(XSSFCell.CELL_TYPE_STRING);
          
          createCell.setCellType(XSSFCell.CELL_TYPE_STRING);
         createCell.setCellValue(content);
         
       //  HSSFCell cell2 = row.getCell(cellnum); //  53��     BB ��һ��Ԥ��
         
        // cell2.setCellComment(comment);
        // cell2.setCellValue(new HSSFRichTextString("���ǵ�Ԫ��"));

         map.remove(code);

       }
       
       //System.out.println("ʣ��δƥ�䵽�ģ�"+map.toString());
       

       workbook.write(fOut);  

      
    }catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.toString());
    }finally{
      try {
        fOut.flush();
        fOut.close(); 
      }
      catch(IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }  
      // �����������ر��ļ�  
   
      
    }
    
    

  }
  
  
  public static void readExcel(String fileName) throws Exception{
   
    InputStream in = new FileInputStream(fileName);
    HSSFWorkbook workbook = new HSSFWorkbook(in);
    HSSFSheet sheet = workbook.getSheet("a");
    
    
    int lastRowNum = sheet.getLastRowNum();
    
    
    Map<Integer,List<Object>> map = new TreeMap<Integer,List<Object>>();
    for(int i = 0 ; i < lastRowNum ; i++){
      HSSFRow row = sheet.getRow(i);
      System.out.println(i);

      
      if(row == null){
        continue;
      }
      short lastCellNum = row.getLastCellNum();
      
      List<Object> list = new ArrayList<Object>();
      for(int j = 0 ; j < lastCellNum ; j++){
        
        HSSFCell cell = row.getCell(j);
        if(cell == null){
          continue; 
        }
        System.out.println("j:"+j);
        
/*        if(j==1){
          Double numericCellValue = cell.getNumericCellValue();
          list.add(numericCellValue);
        }else{
          String stringCellValue = cell.getStringCellValue();
          list.add(stringCellValue);  
        }*/
        
        String stringCellValue = cell.getStringCellValue();
        list.add(stringCellValue);  
        
        
        
        
      }
      
      map.put(i, list);
      
      
    }
    
    writeFile(map);
    
    
  }
  
  
  public static void writeFile(Map<Integer,List<Object>> map) throws Exception{
    // �����µ�Excel ������  
    HSSFWorkbook workbook = new HSSFWorkbook();  
    
    HSSFSheet  sheet = workbook.createSheet();
    
   // HSSFRow row = sheet.createRow((short) 0);
    
    int k = 0;
    boolean flag = false;
    for (Integer key : map.keySet()) {
      //map.keySet()���ص�������key��ֵ
      List<Object> list = map.get(key);//�õ�ÿ��key�����value��ֵ

      
      if(list.get(0).equals("")){
        HSSFRow ro = sheet.createRow((short) 1+k);
        k++;
        flag = true;
        continue;
      }else{
        
        if(flag == false){
          HSSFRow ro = sheet.createRow((short) 1+k);
          k++;
        }else if(flag == true){
          flag = false;
        }


      }
     
     
      
      for(int j = 0;j<2;j++){
        
        HSSFRow ro = sheet.createRow((short) 1+k);

        
        //����ɫ 
 /*       if(j == 1){
          ro.set
        }*/
        
        k++;
        for(int i = 0 ; i < list.size();i++){
          
          HSSFCell ce = ro.createCell((short) 0+i);
          ce.setCellType(HSSFCell.CELL_TYPE_STRING);
          
          
          String value = list.get(i).toString();
          
          ce.setCellValue(value);
        }
      }
      
      


      
      

    //  System.out.print(key+"\t");
  }
    
    
    String outputFile = "C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\test5.xls";
    // �½�һ����ļ���  
    FileOutputStream fOut = new FileOutputStream(outputFile);  
    // ����Ӧ��Excel ����������  
    workbook.write(fOut);  
    fOut.flush();  
    // �����������ر��ļ�  
    fOut.close();  
  }
  
  
  
  public static void writeFile0415��(Map<Integer,List<Object>> map) throws Exception{
    
    HSSFWorkbook workbook = new HSSFWorkbook();  
    
    HSSFSheet  sheet = workbook.createSheet();
    
    int startRow = 0;
    boolean flag = false;
    for(Integer key : map.keySet()){
      List<Object> list = map.get(key);
      
      int k = 0;
      System.out.println(startRow);
      if(startRow== 72){
        System.out.println();
      }
      

      if(list.size()==0){
        //System.out.println("------------");
        if(flag == true){
          HSSFRow ro4 = sheet.createRow((short) startRow);
          System.out.println("555555");
          startRow += 1;
        }

        
        flag = true;
        continue;
        
      }

      
      
      
      HSSFRow ro = sheet.createRow((short) startRow+k);
      for(int i = 0 ; i < list.size();i++){
        HSSFCell createCell = ro.createCell(i);
        if(i >2){
          createCell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
          createCell.setCellValue(list.get(i).toString());
        }else{
          createCell.setCellType(HSSFCell.CELL_TYPE_STRING);
          createCell.setCellValue(list.get(i).toString());
        }

       
      }
      k++;
      HSSFRow ro2 = sheet.createRow((short) startRow+k);
      for(int i = 0 ; i < list.size();i++){
        HSSFCell createCell = ro2.createCell(i);
        createCell.setCellType(HSSFCell.CELL_TYPE_STRING);
        if(i <3){
          createCell.setCellValue(list.get(i).toString());
        }
        
        
        
      }
      k++;
      
      HSSFRow ro3 = sheet.createRow((short) startRow+k);
      flag = false;
      startRow = startRow+k+1;
      

      
      
    }
    
    
    String outputFile = "C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\test5.xls";
    // �½�һ����ļ���  
    FileOutputStream fOut = new FileOutputStream(outputFile);  
    // ����Ӧ��Excel ����������  
    workbook.write(fOut);  
    fOut.flush();  
    // �����������ر��ļ�  
    fOut.close();  
    
  }
  
  public static void readExcel0415��(String fileName) throws Exception{
    
    InputStream in = new FileInputStream(fileName);
    HSSFWorkbook workbook = new HSSFWorkbook(in);
    HSSFSheet sheet = workbook.getSheet("a");
    
    
    int lastRowNum = sheet.getLastRowNum();
    
    
    Map<Integer,List<Object>> map = new TreeMap<Integer,List<Object>>();
    for(int i = 0 ; i < lastRowNum ; i++){
      HSSFRow row = sheet.getRow(i);
      System.out.println(i);

      List<Object> list = new ArrayList<Object>();
      if(row == null){
        map.put(i, list);
        continue;
      }
      short lastCellNum = row.getLastCellNum();
      

      for(int j = 0 ; j < lastCellNum ; j++){
        
        HSSFCell cell = row.getCell(j);
/*        if(cell == null){
          continue; 
        }*/
        System.out.println("j:"+j);
        
/*        if(j==1){
          Double numericCellValue = cell.getNumericCellValue();
          list.add(numericCellValue);
        }else{
          String stringCellValue = cell.getStringCellValue();
          list.add(stringCellValue);  
        }*/
        

        if(cell == null){
          list.add(""); 
        }
        else if(j >2){
          Double numericCellValue = cell.getNumericCellValue();
          list.add(numericCellValue);
        }else{
          String stringCellValue = cell.getStringCellValue();
          list.add(stringCellValue);  
          
        }
      }
      
      map.put(i, list);
      
      
    }
    
    writeFile0415��(map);
    
    
  }

}
