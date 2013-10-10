/*
 * public static void CreatXLS(String filename)//创建xls工作簿
 * public static String[] Readsheet0(String filename)//读整个表格内容
 * public static String Readsheet0cell(String filename,int rowNumber, int colNumber)//读指定行列的内容
 */
package org.rbda.tools;
/**
 *
 * @author kewin
 */
import java.io.*; 
import jxl.*; 
import jxl.write.*; 

public class XLS {
    
public static void CreatXLS(String filename)//创建xls工作簿
   {
      try{
       WritableWorkbook book=Workbook.createWorkbook(new File(filename));//打开文件 　　
       WritableSheet sheet=book.createSheet("第一页", 0);//生成名为“第一页”的工作表，参数0表示这是第一页                 
       Label label=new Label(0,0,"第一项内容");      
       sheet.addCell(label);  //添加单元格               
       book.write(); 
       book.close();
       }catch(Exception e)
       { System.out.println(e);}
   }     

public static String[] Readsheet0(String filename)
    {               
        String[] context = null;
        try 
        { 
            Workbook book= Workbook.getWorkbook(new File(filename)); //获得文件名字 
            Sheet sheet=book.getSheet(0); //获得第一个工作表对象  
            int rowNumber=sheet.getRows()-1;
            context=new String[rowNumber];
           for(int i=0;i<rowNumber;i++)
           {
              Cell cell=sheet.getCell(0,i+1);
              context[i]=cell.getContents();           
           }
            book.close(); 
        }catch(Exception e) 
        { 
            System.out.println(e);
        }    
        return  context;
    } 

public static String Readsheet0cell(String filename,int rowNumber, int colNumber)
    {               
        String context = null;
        try 
        { 
            Workbook book= Workbook.getWorkbook(new File(filename)); //获得文件名字 
            Sheet sheet=book.getSheet(0); //获得第一个工作表对象  
              Cell cell=sheet.getCell(colNumber-1,rowNumber-1);
              context=cell.getContents();           
            book.close(); 
        }catch(Exception e) 
        { 
            System.out.println(e);
        }    
        return  context;
    } 

 public static void Addsheet0(String filename,String context,int index)
    {   //添加修改sheet0 中信息
     try 
    { //Excel获得文件 
       Workbook wb=Workbook.getWorkbook(new File(filename)); //打开一个文件的副本，并且指定数据写回到原文件 
       WritableWorkbook book=Workbook.createWorkbook(new File(filename),wb); //添加一个工作表 
       WritableSheet sheet0=book.getSheet(0);        
       sheet0.addCell(new Label(0,index,context));    
       WritableSheet sheet=book.createSheet(context,index); 
       sheet.addCell(new Label(0,0,"加入项内容"));
       book.write(); 
       book.close(); 
    }catch(Exception e)
    { 
        System.out.println(e); 
    }    
}

}
