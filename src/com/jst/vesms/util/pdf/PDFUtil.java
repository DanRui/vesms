package com.jst.vesms.util.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * <p>
 * Title:PDF测试类
 * </p>
 * <p>
 * Description: 测试PDF文件的创建、生成、格式调整、加密保护等功能。
 * </p>
 * <p>
 * Copyright: DanRui
 * </p>
 * <p>
 * Company: jst
 * </p>
 * 
 * @author DanRui
 * @version 0.0.1 2017年6月9日-上午11:02:29
 */
public class PDFUtil {

	public static void generatePDF(String headerName, String filepath, List<String[]> dataList) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filepath));
			document.open();

			String font_cn = getChineseFont();
			BaseFont baseFont = BaseFont.createFont(font_cn, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

			//BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/msyh.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			Font font = new Font(baseFont);
			
			int columns = dataList.get(0).length;
			
			PdfPTable table = new PdfPTable(columns); // 3 columns.
			table.setWidthPercentage(100); // Width 100%
			table.setSpacingBefore(10f); // Space before table
			table.setSpacingAfter(10f); // Space after table

			// Set Column widths
			/*float[] columnWidths = { 1f, 1f, 1f };
			table.setWidths(columnWidths);*/
			
			PdfPCell headerCell = new PdfPCell(new Paragraph(headerName, font));
			headerCell.setColspan(columns);
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell columnCell1 = new PdfPCell(new Paragraph("序号", font));
			PdfPCell columnCell2 = new PdfPCell(new Paragraph("金额", font));
			PdfPCell columnCell3 = new PdfPCell(new Paragraph("经济分类编码", font));
			PdfPCell columnCell4 = new PdfPCell(new Paragraph("收款人行别编码", font));
			PdfPCell columnCell5 = new PdfPCell(new Paragraph("收款人名称", font));
			PdfPCell columnCell6 = new PdfPCell(new Paragraph("收款人账户", font));
			PdfPCell columnCell7 = new PdfPCell(new Paragraph("开户银行", font));
			PdfPCell columnCell8 = new PdfPCell(new Paragraph("摘要", font));
			
			table.addCell(headerCell);
			table.addCell(columnCell1);
			table.addCell(columnCell2);
			table.addCell(columnCell3);
			table.addCell(columnCell4);
			table.addCell(columnCell5);
			table.addCell(columnCell6);
			table.addCell(columnCell7);
			table.addCell(columnCell8);
			
			for (int i = 0 ; i < dataList.size() ; i ++) {
				String[] rowData = dataList.get(i);
				for (int j = 0 ; j < rowData.length ; j ++) {
					String data = rowData[j];
					PdfPCell dataCell = new PdfPCell(new Paragraph(data, font));
					table.addCell(dataCell);
				}
			}
			

			/*PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
			cell1.setBorderColor(BaseColor.BLUE);
			cell1.setPaddingLeft(10);
			cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
			cell2.setBorderColor(BaseColor.GREEN);
			cell2.setPaddingLeft(10);
			cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));
			cell3.setBorderColor(BaseColor.RED);
			cell3.setPaddingLeft(10);
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);*/

			// To avoid having the cell border and the content overlap, if you
			// are having thick cell borders
			// cell1.setUserBorderPadding(true);
			// cell2.setUserBorderPadding(true);
			// cell3.setUserBorderPadding(true);

			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
/*	public static void repGeneratePDF(String headerName, String filepath, List<String[]> dataList) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(filepath));
			document.open();

			//BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/msyh.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			String font_cn = getChineseFont();
			BaseFont baseFont = BaseFont.createFont(font_cn, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
			Font font = new Font(baseFont);
			
			int columns = dataList.get(0).length;
			
			PdfPTable table = new PdfPTable(columns); // 3 columns.
			table.setWidthPercentage(100); // Width 100%
			table.setSpacingBefore(10f); // Space before table
			table.setSpacingAfter(10f); // Space after table

			// Set Column widths
			float[] columnWidths = { 1f, 1f, 1f };
			table.setWidths(columnWidths);
			
			PdfPCell headerCell = new PdfPCell(new Paragraph(headerName, font));
			headerCell.setColspan(columns);
			headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			PdfPCell columnCell1 = new PdfPCell(new Paragraph("序号", font));
			PdfPCell columnCell2 = new PdfPCell(new Paragraph("车牌号码", font));
			PdfPCell columnCell3 = new PdfPCell(new Paragraph("补贴金额", font));
			PdfPCell columnCell4 = new PdfPCell(new Paragraph("车主姓名", font));
			PdfPCell columnCell5 = new PdfPCell(new Paragraph("原开户银行", font));
			PdfPCell columnCell6 = new PdfPCell(new Paragraph("原开户账户", font));
			PdfPCell columnCell7 = new PdfPCell(new Paragraph("变更后补贴对象", font));
			PdfPCell columnCell8 = new PdfPCell(new Paragraph("变更后银行", font));
			PdfPCell columnCell9 = new PdfPCell(new Paragraph("变更后银行变更后银行账户", font));
			PdfPCell columnCell10 = new PdfPCell(new Paragraph("变更内容", font));
			PdfPCell columnCell11 = new PdfPCell(new Paragraph("报送序号", font));
			
			table.addCell(headerCell);
			table.addCell(columnCell1);
			table.addCell(columnCell2);
			table.addCell(columnCell3);
			table.addCell(columnCell4);
			table.addCell(columnCell5);
			table.addCell(columnCell6);
			table.addCell(columnCell7);
			table.addCell(columnCell8);
			table.addCell(columnCell9);
			table.addCell(columnCell10);
			table.addCell(columnCell11);
			
			
			for (int i = 0 ; i < dataList.size() ; i ++) {
				String[] rowData = dataList.get(i);
				for (int j = 0 ; j < rowData.length ; j ++) {
					String data = rowData[j];
					PdfPCell dataCell = new PdfPCell(new Paragraph(data, font));
					table.addCell(dataCell);
				}
			}

			document.add(table);

			document.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	
	
	
	
	
	

	public static void generatePDFWithPage() {
		/*List<String> ponum = new ArrayList<String>();
		add(ponum, 26);
		List<String> line = new ArrayList<String>();
		add(line, 26);
		List<String> part = new ArrayList<String>();
		add(part, 26);
		List<String> description = new ArrayList<String>();
		add(description, 26);
		List<String> origin = new ArrayList<String>();
		add(origin, 26);

		// Create Document Instance
		Document document = new Document();

		// add Chinese font
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

		// Font headfont=new Font(bfChinese,10,Font.BOLD);
		Font keyfont = new Font(bfChinese, 8, Font.BOLD);
		Font textfont = new Font(bfChinese, 8, Font.NORMAL);

		// Create Writer associated with document
		PdfWriter.getInstance(document, new FileOutputStream(new File(
				"D:\\POReceiveReport.pdf")));

		document.open();

		// Seperate Page controller
		int recordPerPage = 10;
		int fullPageRequired = ponum.size() / recordPerPage;
		int remainPage = ponum.size() % recordPerPage > 1 ? 1 : 0;
		int totalPage = fullPageRequired + remainPage;

		for (int j = 0; j < totalPage; j++) {
			document.newPage();

			// create page number
			String pageNo = leftPad("页码: " + (j + 1) + " / " + totalPage, 615);
			Paragraph pageNumber = new Paragraph(pageNo, keyfont);
			document.add(pageNumber);

			// create title image
			Image jpeg = Image.getInstance("D:\\title.JPG");
			jpeg.setAlignment(Image.ALIGN_CENTER);
			jpeg.scaleAbsolute(530, 37);*/
			//document.add(jpeg);

		/*	// header information
			Table tHeader = new Table(2);
			float[] widthsHeader = { 2f, 3f };
			tHeader.setWidths(widthsHeader);
			tHeader.setWidth(100);
			tHeader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

			String compAdd = "河源市高新技术开发区兴业大道中66号";
			String company = "丰达音响（河源）有限公司";
			String vendor = "V006";
			String vendorName = "中山市卢氏五金有限公司";
			String ccn = "FHH";
			String mas_loc = "FHH";
			String delivery_note = "20130718001";
			String receive_date = "20130718";
			String dept = "H11";
			String asn = "0123456789";

			Cell c1Header = new Cell(new Paragraph("地址：" + compAdd, keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("供应商：" + vendor, keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("公司：" + company, keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("供应商工厂：" + vendorName, keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("CCN:   " + ccn
					+ "    Master Loc:   " + mas_loc, keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("送货编号: " + delivery_note
					+ "                             送货日期: " + receive_date,
					keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("Dept：" + dept, keyfont));
			tHeader.addCell(c1Header);
			c1Header = new Cell(new Paragraph("ASN#：" + asn, keyfont));
			tHeader.addCell(c1Header);
			document.add(tHeader);

			// record header field
			Table t = new Table(5);
			float[] widths = { 1.5f, 1f, 1f, 1.5f, 1f };
			t.setWidths(widths);
			t.setWidth(100);
			t.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			Cell c1 = new Cell(new Paragraph("PO#", keyfont));
			t.addCell(c1);
			c1 = new Cell(new Paragraph("Line", keyfont));
			t.addCell(c1);
			c1 = new Cell(new Paragraph("Part#", keyfont));
			t.addCell(c1);
			c1 = new Cell(new Paragraph("Description", keyfont));
			t.addCell(c1);
			c1 = new Cell(new Paragraph("Origin", keyfont));
			t.addCell(c1);

			// calculate the real records within a page ,to calculate the last
			// record number of every page
			int maxRecordInPage = j + 1 == totalPage ? (remainPage == 0 ? recordPerPage
					: (ponum.size() % recordPerPage))
					: recordPerPage;

			for (int i = j * recordPerPage; i < ((j * recordPerPage) + maxRecordInPage); i++) {
				Cell c2 = new Cell(new Paragraph(ponum.get(i), textfont));
				t.addCell(c2);
				c2 = new Cell(new Paragraph(line.get(i), textfont));
				t.addCell(c2);
				c2 = new Cell(new Paragraph(part.get(i), textfont));
				t.addCell(c2);
				c2 = new Cell(new Paragraph(description.get(i), textfont));
				t.addCell(c2);
				c2 = new Cell(new Paragraph(origin.get(i), textfont));
				t.addCell(c2);
			}
			document.add(t);

			if (j + 1 == totalPage) {

				Paragraph foot11 = new Paragraph("文件只作  Foster 收貨用"
						+ printBlank(150) + "__________________________",
						keyfont);
				document.add(foot11);
				Paragraph foot12 = new Paragraph(
						"Printed from Foster supplier portal" + printBlank(134)
								+ company + printBlank(40) + "版本: 1.0", keyfont);
				document.add(foot12);
				HeaderFooter footer11 = new HeaderFooter(foot11, true);
				footer11.setAlignment(HeaderFooter.ALIGN_BOTTOM);
				HeaderFooter footer12 = new HeaderFooter(foot12, true);
				footer12.setAlignment(HeaderFooter.ALIGN_BOTTOM);
			}
		}*/
		//document.close();
	}

	public static String leftPad(String str, int i) {
		int addSpaceNo = i - str.length();
		String space = "";
		for (int k = 0; k < addSpaceNo; k++) {
			space = " " + space;
		}
		;
		String result = space + str;
		return result;
	}

	public static void add(List<String> list, int num) {
		for (int i = 0; i < num; i++) {
			list.add("test" + i);
		}
	}

	public static String printBlank(int tmp) {
		String space = "";
		for (int m = 0; m < tmp; m++) {
			space = space + " ";
		}
		return space;
	}
	
	 private static String getChineseFont() {  
		  
        //宋体（对应css中的 属性 font-family: SimSun; /*宋体*/）  
        String font1 ="C:/Windows/Fonts/msyh.ttf";  
  
        //判断系统类型，加载字体文件  
        java.util.Properties prop = System.getProperties();  
        String osName = prop.getProperty("os.name").toLowerCase();  
        System.out.println(osName);  
        if (osName.indexOf("linux") > -1) {  
            font1 = "/usr/share/fonts/win/msyh.ttf";  
        }  
        if(!new File(font1).exists()) {  
            throw new RuntimeException("字体文件不存在,影响导出pdf中文显示！"+font1);  
        }
        return font1;
	 }
        

	public static void main(String[] args) {
		/*
		 * Document document = new Document(); try { PdfWriter writer =
		 * PdfWriter.getInstance(document, new
		 * FileOutputStream("HelloWorld.pdf")); document.open();
		 * document.add(new Paragraph("A Hello World PDF document."));
		 * document.close(); writer.close(); } catch (DocumentException e) {
		 * e.printStackTrace(); } catch (FileNotFoundException e) {
		 * e.printStackTrace(); }
		 */

		List<String[]> dataList = new ArrayList<String[]>();
		for (int i = 0 ; i < 100 ; i ++ ) {
			String[] dataStrings = new String[] {(i+1)+"", "23000", "39999", "003", "B12133", "qwewqqwewqewqwqewewewwqe", "adasdsa", "B11037(第16批老旧车淘汰补贴)已核非公务卡结算(786)"};
			dataList.add(dataStrings);
		}
		
		// 生成带表格的pdf文件
		generatePDF("批次号", "D:/batch_no.pdf", dataList);
	}
}
