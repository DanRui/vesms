
package com.jst.test.web;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFMaker {
	
	public static void main(String[] args) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("d:/AddTableExample.pdf"));
			document.open();
		
			PdfPTable table = new PdfPTable(3);    
			PdfPCell cell;    
			cell = new PdfPCell(new Phrase("Cell with colspan 3"));    
			cell.setColspan(3);    
			table.addCell(cell);    
			cell = new PdfPCell(new Phrase("Cell with rowspan 2"));    
			cell.setRowspan(2);    
			table.addCell(cell);    
			table.addCell("row 1; cell 1");    
			table.addCell("row 1; cell 2");    
			table.addCell("row 2; cell 1");    
			table.addCell("row 2; cell 2");    
		    
			document.add(table);  
			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

