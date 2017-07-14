package com.jst.vesms.util.excel.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.jst.common.utils.DateUtil;
import com.jst.vesms.model.PayResultImport;
import com.jst.vesms.model.PayResultImportDetail;

public class ImportExcel {

    
    public static PayResultImport readPayResultImport(File file,PayResultImport payResultImport) throws Exception{
   	 InputStream io = new FileInputStream(file.getAbsoluteFile());  
   	  Workbook readwb = Workbook.getWorkbook(io); 
   	  Sheet sheet = readwb.getSheet(0);
   	  // 第二行第一列的值
   	  Cell cell = sheet.getCell(2, 1);
	  String dateString = cell.getContents().replace("/", "-");
	  System.out.println(cell.getContents());
	  Date date = DateUtil.parse(dateString, "yyyy-MM-dd");
   /*	  Date date = null ;
   	  Cell cell = sheet.getCell(2, 1);
   	  System.out.println(cell.getContents());
   	  DateCell dc = (DateCell)cell;
   	  date = dc.getDate();	//获取单元格的date类型
   */
   	  // 获取制表时间  
   	  payResultImport.setMakeTime(date);
   	  Date date2 = new Date();
   	  // 获取导入时间
   	  payResultImport.setImportTime(date2);// new Date()为获取当前系统时间
   	  // 导入文件路径
   	  payResultImport.setFilePath(file+"");
   	  
   	  //payImportDao.save(payResultImport);
	   	readwb.close(); 
	   	io.close();
   	  return payResultImport;
    }
    
    
    
    
    public static List<PayResultImportDetail> readSpecify(File file,Integer payImportId)throws Exception{  
   //	 List<PayResultImport>  list = new ArrayList<PayResultImport>();
  // 	 PayResultImport resultImport = new PayResultImport();  
   //	 ArrayList<String> columnList = new ArrayList<String>();  
    	PayResultImportDetail payResultImportDetail;
    	List<PayResultImportDetail>  list = new ArrayList<PayResultImportDetail>();
   	    Workbook readwb = null;  
   	    InputStream io = new FileInputStream(file.getAbsoluteFile());  
   	    readwb = Workbook.getWorkbook(io);  
   	    Sheet readsheet = readwb.getSheet(0); 
   	    // 获取行数
   	    int rsRows = readsheet.getRows();  
   	    // 获取列数
   //	    int rsColumns = readsheet.getColumns();  
   	    // 从第三行读取
   	    for (int i = 4; i < rsRows; i++) { 
   	    	Cell[] cells = readsheet.getRow(i);
   	    	System.out.println("--"+cells[0]);
   	    	payResultImportDetail = new PayResultImportDetail();
   	    	//获取主表id
   	    	payResultImportDetail.setPayImportId(payImportId);
   	    	// 获取国库受理单号
   	    	Cell cell = readsheet.getCell(0, i);  
   	        payResultImportDetail.setRequestNo(cell.getContents());
   	        // 获取付款日期
   	        Cell cell1 = readsheet.getCell(1, i); 
   	        System.out.println("--"+cell1.getContents());
   	     if(null!=cell1.getContents() && cell1.getContents()!=""){
   	        Date date = DateUtil.parse(cell1.getContents(), "yyyy-MM-dd HH:mm:ss");
   	        payResultImportDetail.setPayTime(date);
   	     }
   	        // 获取清算日期
   	        Cell cell2 = readsheet.getCell(2, i);
   	     if(null!=cell2.getContents() && cell2.getContents()!=""){
   	        Date date1 = DateUtil.parse(cell2.getContents(), "yyyy-MM-dd HH:mm:ss");
   	        payResultImportDetail.setConfirmTime(date1);
   	     }
   	        //支付单号
   	        Cell cell3 = readsheet.getCell(3, i);
   	        payResultImportDetail.setPayNo(cell3.getContents());
   	        //原支付单号
   	        Cell cell4 = readsheet.getCell(4, i);
   	        payResultImportDetail.setOriginalPayNo(cell4.getContents());
   	        // 资金说明
   	        Cell cell5 = readsheet.getCell(15, i);
   	        payResultImportDetail.setPayComment(cell5.getContents());
   	        
   	        // 业务ID 
   	        String str[] = cell5.getContents().split("ID:");
   	        String str1 = str[1];
   	        String str2[] = str1.split(")");
   	        Integer applyId= Integer.parseInt(str2[0]);
   	        payResultImportDetail.setApplyId(applyId);
   	     
   	        // 支付金额
	        	Cell cell6 = readsheet.getCell(16, i);
	        	String numDouble = cell6.getContents().replace(",", "");
	        	payResultImportDetail.setPayAmount(Double.parseDouble(numDouble));
   	        // 支付状态
	        	Cell cell7 = readsheet.getCell(17, i);
	        	payResultImportDetail.setPayResStatus(cell7.getContents());
   	        // 收款人名称
	        	Cell cell8 = readsheet.getCell(18, i);
	        	payResultImportDetail.setAccountName(cell8.getContents());
   	        // 收款人账户
	        	Cell cell9 = readsheet.getCell(19, i);
	        	payResultImportDetail.setAccountNo(cell9.getContents());
   	        // 收款人开户行
	        	Cell cell10 = readsheet.getCell(20, i);
	        	payResultImportDetail.setBankName(cell10.getContents());
	        //	importDetailDao.save(payResultImportDetail);
	        	list.add(payResultImportDetail);
   	    }  
   	   /* String[] ageString = new String[columnList.size()];  
   	    for (int i = 0; i < columnList.size(); i++) {  
   	    	if(columnList.get(i)!="" && null!=columnList.get(i)){
   	    		ageString[i] = columnList.get(i);  
   	    	}
   	        System.out.println(ageString[i]);  
   	        System.out.println();
   	    }*/
   	    readwb.close();
   	    io.close();
   	    return list;
   	}  
    

}
