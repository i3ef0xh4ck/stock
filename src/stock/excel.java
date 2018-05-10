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
   // modifyExcelJXl("H:"+File.separator+"新建文件夹"+File.separator+"新建文件夹"+File.separator+"图test.xls",map,53);
    
  //  modifyExcelJXl("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\tt.xls",map,53);
    
    
   // readExcel2("H:\\新建文件夹\\新建文件夹\\图test.xls",map,53);
    //readExcel2("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\te.xlsx",map,10);
    
  //  updateExcelXlxs("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\tab.xlsx","所有四季利润",map);
    
    
    //updateExcel("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\tea.xls","所有四季利润",map);
    
 //   updateExcelxlxs2("H:\\新建文件夹\\新建文件夹\\程序复制\\z预告程序复制.xlsx","所有四季利润",map,null);
  //  updateExcelxlxs2("H:\\新建文件夹\\新建文件夹\\程序复制\\z业绩公告程序复制.xlsx","所有四季利润",map);
 //   updateExcelxlxs2("H:\\新建文件夹\\新建文件夹\\程序复制\\z业绩公告程序年报复制.xlsx","所有四季利润",map);
    
    
    readExcel0415年("C:\\Users\\Administrator.WINDOWS-8RP82UF\\Desktop\\all.xls");
    
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
    //这里假设对应单元格原来的类型也是String类型
    cell2.setCellValue(content);
    map.remove(code);
//    System.out.println("单元格原来值为"+oldValue);
 //   System.out.println("单元格值被更新为"+content);
    
    
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
    System.out.println("匹配不到的map");
    System.out.println(map.toString());

    fis.close();//关闭文件输入流

    FileOutputStream fos=new FileOutputStream(exlFile);
    xssfWorkbook.write(fos);
    fos.close();//关闭文件输出流
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
		    //这里假设对应单元格原来的类型也是String类型
		    cell2.setCellValue(content.get(j));
	    	  
	      }

	    
	    
	    map.remove(code);
//	    System.out.println("单元格原来值为"+oldValue);
	 //   System.out.println("单元格值被更新为"+content);
	    
	    
	   
	    
	    
	    }
	    System.out.println("匹配不到的map");
	    System.out.println(map.toString());

	    fis.close();//关闭文件输入流

	    FileOutputStream fos=new FileOutputStream(exlFile);
	    xssfWorkbook.write(fos);
	    fos.close();//关闭文件输出流
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
      //这里假设对应单元格原来的类型也是String类型
      cell2.setCellValue(content);
      System.out.println("单元格原来值为"+oldValue);
      System.out.println("单元格值被更新为"+content);
      }
      
      
      workbook.write(fos);
      

        fis.close();
        fos.close();//关闭文件输出流



 
}
  
  
  /**
   
   * */
  public static void modifyExcelJXl(String excelpath,Map<String,String> map,int cellnum){
    try {
      jxl.Workbook wb =null; //创建一个workbook对象
  
      InputStream is = new FileInputStream(excelpath); //创建一个文件流，读入Excel文件
      wb = Workbook.getWorkbook(is); //将文件流写入到workbook对象

    // jxl.Workbook 对象是只读的，所以如果要修改Excel，需要创建一个可读的副本，副本指向原Excel文件（即下面的new File(excelpath)）
    jxl.write.WritableWorkbook wbe= Workbook.createWorkbook(new File(excelpath), wb);//创建workbook的副本
    WritableSheet sheet = wbe.getSheet("所有四季利润"); //获取第一个sheet
    
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
      
      WritableCell cell2 =sheet.getWritableCell(i, cellnum);//获取第一个单元格   //第一个参数是列  第二个参数 行
      jxl.format.CellFormat cf = cell2.getCellFormat();//获取第一个单元格的格式
      jxl.write.Label lbl = new jxl.write.Label(i, cellnum, content);//将第一个单元格的值改为“修改後的值”
      lbl.setCellFormat(cf);//将修改后的单元格的格式设定成跟原来一样

      sheet.addCell(lbl);//将改过的单元格保存到sheet
      
    //  map.remove(code);
      
    }
    
    wbe.write();//将修改保存到workbook --》一定要保存
    wbe.close();//关闭workbook，释放内存 ---》一定要释放内存

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
    //这里假设对应单元格原来的类型也是String类型
    cell2.setCellValue(content);
    System.out.println("单元格原来值为"+oldValue);
    System.out.println("单元格值被更新为"+content);
    }
    



    fis.close();//关闭文件输入流

    FileOutputStream fos=new FileOutputStream(exlFile);
    workbook.write(fos);
    fos.close();//关闭文件输出流
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
      XSSFSheet sheet = workbook.getSheet("所有四季利润");
      
     // Sheet sheet = workbook.getSheet("所有四季利润");
       
       
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
         
       //  HSSFCell cell2 = row.getCell(cellnum); //  53列     BB 第一季预告
         
        // cell2.setCellComment(comment);
        // cell2.setCellValue(new HSSFRichTextString("我是单元格！"));

         map.remove(code);

       }
       
       //System.out.println("剩下未匹配到的："+map.toString());
       

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
      // 操作结束，关闭文件  
   
      
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
    // 创建新的Excel 工作簿  
    HSSFWorkbook workbook = new HSSFWorkbook();  
    
    HSSFSheet  sheet = workbook.createSheet();
    
   // HSSFRow row = sheet.createRow((short) 0);
    
    int k = 0;
    boolean flag = false;
    for (Integer key : map.keySet()) {
      //map.keySet()返回的是所有key的值
      List<Object> list = map.get(key);//得到每个key多对用value的值

      
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

        
        //变颜色 
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
    // 新建一输出文件流  
    FileOutputStream fOut = new FileOutputStream(outputFile);  
    // 把相应的Excel 工作簿存盘  
    workbook.write(fOut);  
    fOut.flush();  
    // 操作结束，关闭文件  
    fOut.close();  
  }
  
  
  
  public static void writeFile0415年(Map<Integer,List<Object>> map) throws Exception{
    
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
    // 新建一输出文件流  
    FileOutputStream fOut = new FileOutputStream(outputFile);  
    // 把相应的Excel 工作簿存盘  
    workbook.write(fOut);  
    fOut.flush();  
    // 操作结束，关闭文件  
    fOut.close();  
    
  }
  
  public static void readExcel0415年(String fileName) throws Exception{
    
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
    
    writeFile0415年(map);
    
    
  }

}
