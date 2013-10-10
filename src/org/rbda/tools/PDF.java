/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rbda.tools;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.FileDialog;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rbda.gui.MainFrame;

/**
 *
 * @author kewin
 */
public class PDF {
    public String filename;
    
    public static void WritePdf( String filename ){
        //先生成文档
    Document document = new Document();    
        try {
            try {
                // 第二步：    
            // 创建一个PdfWriter实例，    
            // 将文件输出流指向一个文件。             
            PdfWriter.getInstance(document,new FileOutputStream(filename));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 第三步：打开文档。    
        document.open();
        try {
            //增加对中文的支持
            //BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);  
            //Font font = new Font(baseFont);    
            // 第四步：在文档中增加一个段落。
            Paragraph context = new Paragraph();
            context.add("Title");
            context.setAlignment(1);
            document.add(context);
            Chunk chunk1 = new Chunk("This text is underlined", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE));  
            document.add(chunk1);           
        } catch (DocumentException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        document.close(); 
    }
}
