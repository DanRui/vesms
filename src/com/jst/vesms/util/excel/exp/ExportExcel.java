package com.jst.vesms.util.excel.exp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jst.common.utils.DateUtil;
import com.jst.vesms.dao.IImportDetailDao;
import com.jst.vesms.dao.IPayImportDao;
import com.jst.vesms.model.PayResultImport;
import com.jst.vesms.model.PayResultImportDetail;
import com.jst.vesms.util.excel.Constant;
import com.jst.vesms.util.excel.ExcelProperties;

/**
 * 
 */
public class ExportExcel {

	/**
	 * 生成EXCEL通过浏览器导出到客户端,带比较全面的参数列表,在WEB项目中使用
	 * 
	 * @param title
	 *            标题
	 * @param hasInnerTitle
	 *            子标题，如果传入NULL，就不生成子标题。
	 * @param colsSize
	 *            每列的宽度，如果传入的列数和LIST中的数组长度不一致，将会按默认的宽度处理
	 * @param data
	 *            EXCEL列表数据源
	 * @param path
	 *            文件存放路径
	 * @return
	 */
	@Resource(name="importDetailDao")
	private static IImportDetailDao importDetailDao ;
	
	@Resource(name="payImportDao")
	private static IPayImportDao payImportDao ;
	//正常批次预览文件
	public static int exportExcelInWeb(ExcelProperties excelProperties, String innerTitle, int[] colsSize, List<String[]> data, OutputStream os) {
		int result = 1;
		int colsNum = 0;
		// 先判断是否传入原始数据
		if (data == null || data.size() == 0) {
			result = Constant.EXPORT_EXCEL_NO_DATA;
		} else {
			// 判断传入的流
			if (os != null) {
				// 生成EXCEL文件
				WritableWorkbook wwb = null;
				try {
					wwb = Workbook.createWorkbook(os);
					Label lable = new Label(0, 0, excelProperties.getHeader());
					// lable.setCellFormat(new CellFormat)
					// 设置字体
					WritableFont font = new WritableFont(WritableFont.ARIAL,20,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
					WritableCellFormat cellFormat = new WritableCellFormat(font);
					// 水平居中
					cellFormat.setAlignment(Alignment.CENTRE);
					// 垂直居中
					cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					// 背景颜色
					//cellFormat.setBackground(Colour.YELLOW);
					lable.setCellFormat(cellFormat);
					// 创建EXCEL工作表
					WritableSheet ws = wwb.createSheet("默认", 0);
					ws.addCell(lable);
					// 取得LIST中的数组大小
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 设置标题行高
					ws.setRowView(0, 800, false);
					
					
					
					// 取得LIST中的数组大小(对象的个数，excel中的列数)
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 行
					int flag = 2;
					WritableFont sideFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
					// 处理内标题
					WritableCellFormat tempFormat = new WritableCellFormat(sideFont);
					// 水平居中
					tempFormat.setAlignment(Alignment.CENTRE);
					// 垂直居中
					tempFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					// 自动适应
					tempFormat.setWrap(true);
					String tempClosHeader[] = excelProperties.getColsHeader();
					for (int i = 0; i < tempClosHeader.length; i++) {
						lable = new Label(i, flag, tempClosHeader[i]);
						lable.setCellFormat(tempFormat);
						ws.addCell(lable);
					}
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					Integer intnumbersum=0;
					int j=0;
					WritableCellFormat dataFormat = new WritableCellFormat();
					// 水平居中
					dataFormat.setAlignment(Alignment.CENTRE);
					// 垂直居中
					dataFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					// 自动适应
					dataFormat.setWrap(true);
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							for (int i = 0; i < temp.length; i++) {
								//金额，数字输出		
								if(i==13){
									jxl.write.Number number = new jxl.write.Number(i, flag, Double.parseDouble(temp[i]),dataFormat);									
									ws.addCell(number);
									Double doublst=Double.parseDouble(temp[i]);
									intnumbersum=intnumbersum+doublst.intValue();
								}else{
									lable = new Label(i, flag, temp[i]);
									// 加入自适应等样式
									lable.setCellFormat(dataFormat);
									ws.addCell(lable);
								}
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
						j++;
					}
					//合计
				//	jxl.write.Number number = new jxl.write.Number(6, j+2, Double.parseDouble(intnumbersum.toString()));
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					String endstr="批次文件到此结束      车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					WritableCellFormat endFormat = new WritableCellFormat();
					// 黄色
					endFormat.setBackground(Colour.YELLOW);
					// 居左
					endFormat.setAlignment(Alignment.LEFT);
					// 垂直居中
					endFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					//第二行展示数据
					lable = new Label(0, 1, numberstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 1, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(1, 350);
					ws.addCell(lable);
					
					//末尾展示数据
					lable = new Label(0, j+3, endstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, j+3, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(j+3, 350);
					ws.addCell(lable);
					
					if (result != Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION) {
						// 设置列宽
						if (colsSize.length == colsNum) {
							for (int i = 0; i < colsSize.length; i++) {
								//设置每列的宽度
								ws.setColumnView(i, colsSize[i]);
							}
						} else {
							// 设置默认的宽度
							for (int i = 0; i < colsNum; i++) {
								ws.setColumnView(i, 20);
							}
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}
					wwb.write();
					wwb.close();
					os.close();
				} catch (IOException e) {
					result = Constant.EXPORT_EXCEL_NOFILE_EXCEPTION;
					e.printStackTrace();
				} catch (RowsExceededException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				} catch (WriteException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				}
			} else {
				result = Constant.EXPORT_EXCEL_NULL_OUTPUTSTREAM_EXCEPTION;
			}
		}
		return result;
	}	
	
	
	
	
	//报财务导出   重报报财务导出   
	public static int exportExcelInWebs(ExcelProperties excelProperties, String innerTitle, int[] colsSize, List<String[]> data, OutputStream os,String password) {
		int result = 1;
		int colsNum = 0;
		// 先判断是否传入原始数据
		if (data == null || data.size() == 0) {
			result = Constant.EXPORT_EXCEL_NO_DATA;
		} else {
			// 判断传入的流
			if (os != null) {
				// 生成EXCEL文件
				WritableWorkbook wwb = null;
				try {
					wwb = Workbook.createWorkbook(os);
					Label lable = new Label(0, 0, excelProperties.getHeader());
					// lable.setCellFormat(new CellFormat)
					WritableFont font = new WritableFont(WritableFont.ARIAL,11,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
					WritableCellFormat cellFormat = new WritableCellFormat(font);
					// 居中
					cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
					cellFormat.setBackground(jxl.format.Colour.YELLOW);
					lable.setCellFormat(cellFormat);
					// 创建EXCEL工作表
					WritableSheet ws = wwb.createSheet("默认", 0);
					ws.addCell(lable);
					// 取得LIST中的数组大小
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 第2行上设置内标题
					int flag = 1;
					// 处理内标题
					String tempClosHeader[] = excelProperties.getColsHeader();
					for (int i = 0; i < tempClosHeader.length; i++) {
						lable = new Label(i, flag, tempClosHeader[i]);
						ws.addCell(lable);
					}
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					
					Integer intnumbersum=0;
					int j=0;
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							for (int i = 0; i < temp.length; i++) {
								//金额，数字输出		
								if(i==1){
									jxl.write.Number number = new jxl.write.Number(i, flag, Double.parseDouble(temp[i]));									
									ws.addCell(number);
									Double doublst=Double.parseDouble(temp[i]);
									intnumbersum=intnumbersum+doublst.intValue();
								}else{
									lable = new Label(i, flag, temp[i]);
									ws.addCell(lable);
								}
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
						j++;
					}
					
					//合计
					/*String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					String endstr="批次文件到此结束      车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					WritableCellFormat endFormat = new WritableCellFormat();
					// 黄色
					endFormat.setBackground(Colour.YELLOW);
					// 居左
					endFormat.setAlignment(Alignment.LEFT);
					// 垂直居中
					endFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					//第二行展示数据
					lable = new Label(0, 1, numberstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 1, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(1, 350);
					ws.addCell(lable);
					
					//末尾展示数据
					lable = new Label(0, j+3, endstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, j+3, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(j+3, 350);
					ws.addCell(lable);*/

					//
					if (result != Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION) {
						// 设置列宽
						if (colsSize.length == colsNum) {
							for (int i = 0; i < colsSize.length; i++) {
								ws.setColumnView(i, colsSize[i]);
							}
						} else {
							// 设置默认的宽度 每格20
							for (int i = 0; i < colsNum; i++) {
								ws.setColumnView(i, 20);
							}
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}
					//设置密码
					 SheetSettings ss = ws.getSettings(); 
					    ss.setPassword(password); 
					    ss.setProtected(true);
					wwb.write();
					wwb.close();
					os.close();
					result = Constant.EXPORT_EXCEL_SUCCESS;
				} catch (IOException e) {
					result = Constant.EXPORT_EXCEL_NOFILE_EXCEPTION;
					e.printStackTrace();
				} catch (RowsExceededException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				} catch (WriteException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				}
			} else {
				result = Constant.EXPORT_EXCEL_NULL_OUTPUTSTREAM_EXCEPTION;
			}
		}
		return result;
	}	
	
	
	
	
	
	
	
	
	
	
	//重报导出预览
/*	public static int repExportExcelPreview(ExcelProperties excelProperties, String innerTitle, int[] colsSize, List<String[]> data, OutputStream os) {
		int result = 1;
		int colsNum = 0;
		// 先判断是否传入原始数据
		if (data == null || data.size() == 0) {
			result = Constant.EXPORT_EXCEL_NO_DATA;
		} else {
			// 判断传入的流
			if (os != null) {
				// 生成EXCEL文件
				WritableWorkbook wwb = null;
				try {
					wwb = Workbook.createWorkbook(os);
					Label lable = new Label(0, 0, excelProperties.getHeader());
					// lable.setCellFormat(new CellFormat)
					// 设置字体
					WritableFont font = new WritableFont(WritableFont.ARIAL,20,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
					WritableCellFormat cellFormat = new WritableCellFormat(font);
					// 水平居中
					cellFormat.setAlignment(Alignment.CENTRE);
					// 垂直居中
					cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					// 背景颜色
					//cellFormat.setBackground(Colour.YELLOW);
					lable.setCellFormat(cellFormat);
					// 创建EXCEL工作表
					WritableSheet ws = wwb.createSheet("默认", 0);
					ws.addCell(lable);
					// 取得LIST中的数组大小
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 设置标题行高
					ws.setRowView(0, 800, false);

					// 处理内标题
					int flag = 2;
					WritableFont sideFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
					WritableCellFormat sideTitle = new WritableCellFormat(sideFont);
					// 水平居中
					sideTitle.setAlignment(Alignment.CENTRE);
					// 垂直居中
					sideTitle.setVerticalAlignment(VerticalAlignment.CENTRE); 
					String tempClosHeader[] = excelProperties.getColsHeader();
					for (int i = 0; i < tempClosHeader.length; i++) {
						lable = new Label(i, flag, tempClosHeader[i],sideTitle);
						ws.setRowView(2, 500);
						ws.addCell(lable);
						
					}
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					
					
					// 给第二行后末尾行加上标志(显示总行和总金额)
					Integer intnumbersum=0;
					int j=0;
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							//设置内容
							WritableCellFormat content = new WritableCellFormat();
							// 水平居中
							content.setAlignment(Alignment.CENTRE);
							// 垂直居中
							content.setVerticalAlignment(VerticalAlignment.CENTRE); 
							for (int i = 0; i < temp.length; i++) {
								//金额，数字输出		
								if(i==2){
									jxl.write.Number number = new jxl.write.Number(i, flag, Double.parseDouble(temp[i]),content);									
									ws.addCell(number);
									Double doublst=Double.parseDouble(temp[i]);
									intnumbersum=intnumbersum+doublst.intValue();
								}else{
									lable = new Label(i, flag, temp[i],content);// 列   行 
									ws.setRowView(flag, 500);
									ws.addCell(lable);
								}
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
						j++;
					}
					
					//合计
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					String endstr="批次文件到此结束      车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					WritableCellFormat endFormat = new WritableCellFormat();
					// 黄色
					endFormat.setBackground(Colour.YELLOW);
					// 居左
					endFormat.setAlignment(Alignment.LEFT);
					// 垂直居中
					endFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					//第二行展示数据
					lable = new Label(0, 1, numberstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 1, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(1, 500);
					ws.addCell(lable);
					
					//末尾展示数据
					lable = new Label(0, j+3, endstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, j+3, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(j+3, 500);
					ws.addCell(lable);
					
				
					if (result != Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION) {
						if (colsSize.length == colsNum) {
							for (int i = 0; i < colsSize.length; i++) {
								
								ws.setColumnView(i, colsSize[i]);
							}
						} else {
							// 设置默认的宽度
							for (int i = 0; i < colsNum; i++) {
								ws.setColumnView(i, 20);
							}
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}
					wwb.write();
					wwb.close();
					os.close();
				} catch (IOException e) {
					result = Constant.EXPORT_EXCEL_NOFILE_EXCEPTION;
					e.printStackTrace();
				} catch (RowsExceededException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				} catch (WriteException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				}
			} else {
				result = Constant.EXPORT_EXCEL_NULL_OUTPUTSTREAM_EXCEPTION;
			}
		}
		return result;
	}	*/
	
	
	
	
	
	
	
	
	//重报审核表预览   
	public static int repExportExcelPreview(ExcelProperties excelProperties, String innerTitle, int[] colsSize, List<String[]> data, OutputStream os) {
		int result = 1;
		int colsNum = 0;
		// 先判断是否传入原始数据
		if (data == null || data.size() == 0) {
			result = Constant.EXPORT_EXCEL_NO_DATA;
		} else {
			// 判断传入的流
			if (os != null) {
				// 生成EXCEL文件
				WritableWorkbook wwb = null;
				try {
					wwb = Workbook.createWorkbook(os);
					Label lable = new Label(0, 0, excelProperties.getHeader());
					// lable.setCellFormat(new CellFormat)
					// 设置字体
					WritableFont font = new WritableFont(WritableFont.ARIAL,20,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
					WritableCellFormat cellFormat = new WritableCellFormat(font);
					// 水平居中
					cellFormat.setAlignment(Alignment.CENTRE);
					// 垂直居中
					cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					// 背景颜色
					//cellFormat.setBackground(Colour.YELLOW);
					lable.setCellFormat(cellFormat);
					// 创建EXCEL工作表
					WritableSheet ws = wwb.createSheet("默认", 0);
					ws.addCell(lable);
					// 取得LIST中的数组大小
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 设置标题行高
					ws.setRowView(0, 800, false);

					// 处理内标题
					int flag = 2;
					WritableFont sideFont = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);
					WritableCellFormat sideTitle = new WritableCellFormat(sideFont);
					// 水平居中
					sideTitle.setAlignment(Alignment.CENTRE);
					// 垂直居中
					sideTitle.setVerticalAlignment(VerticalAlignment.CENTRE); 
					String tempClosHeader[] = excelProperties.getColsHeader();
					for (int i = 0; i < tempClosHeader.length; i++) {
						lable = new Label(i, flag, tempClosHeader[i],sideTitle);
						ws.setRowView(2, 500);
						ws.addCell(lable);
						
					}
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					
					
					// 给第二行后末尾行加上标志(显示总行和总金额)
					Integer intnumbersum=0;
					int j=0;
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							//设置内容
							WritableCellFormat content = new WritableCellFormat();
							// 水平居中
							content.setAlignment(Alignment.CENTRE);
							// 垂直居中
							content.setVerticalAlignment(VerticalAlignment.CENTRE); 
							for (int i = 0; i < temp.length; i++) {
								//金额，数字输出		
								if(i==2){
									jxl.write.Number number = new jxl.write.Number(i, flag, Double.parseDouble(temp[i]),content);									
									ws.addCell(number);
									Double doublst=Double.parseDouble(temp[i]);
									intnumbersum=intnumbersum+doublst.intValue();
								}else{
									lable = new Label(i, flag, temp[i],content);// 列   行 
									ws.setRowView(flag, 500);
									ws.addCell(lable);
								}
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
						j++;
					}
					
					//合计
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					String endstr="批次文件到此结束      车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
					WritableCellFormat endFormat = new WritableCellFormat();
					// 黄色
					endFormat.setBackground(Colour.YELLOW);
					// 居左
					endFormat.setAlignment(Alignment.LEFT);
					// 垂直居中
					endFormat.setVerticalAlignment(VerticalAlignment.CENTRE); 
					//第二行展示数据
					lable = new Label(0, 1, numberstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 1, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(1, 500);
					ws.addCell(lable);
					
					//末尾展示数据
					lable = new Label(0, j+3, endstr);
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, j+3, colsNum - 1, 0);
					lable.setCellFormat(endFormat);
					ws.setRowView(j+3, 500);
					ws.addCell(lable);
					
				
					if (result != Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION) {
						if (colsSize.length == colsNum) {
							for (int i = 0; i < colsSize.length; i++) {
								
								ws.setColumnView(i, colsSize[i]);
							}
						} else {
							// 设置默认的宽度
							for (int i = 0; i < colsNum; i++) {
								ws.setColumnView(i, 20);
							}
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}
					wwb.write();
					wwb.close();
					os.close();
				} catch (IOException e) {
					result = Constant.EXPORT_EXCEL_NOFILE_EXCEPTION;
					e.printStackTrace();
				} catch (RowsExceededException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				} catch (WriteException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				}
			} else {
				result = Constant.EXPORT_EXCEL_NULL_OUTPUTSTREAM_EXCEPTION;
			}
		}
		return result;
	}	
	
	
	
	

	
	public static int exportExcelInWeb_02(ExcelProperties excelProperties, String innerTitle, int[] colsSize, List<String[]> data, OutputStream os,String ly) {
		int result = 1;
		
		int colsNum = 0;
		// 先判断是否传入原始数据
		if (data == null || data.size() == 0) {
			result = Constant.EXPORT_EXCEL_NO_DATA;
		} else {
			// 判断传入的流
			if (os != null) {
				// 生成EXCEL文件
				WritableWorkbook wwb = null;
				try {
					wwb = Workbook.createWorkbook(os);
					Label lable = new Label(0, 0, excelProperties.getHeader());
					// lable.setCellFormat(new CellFormat)
					WritableCellFormat cellFormat = new WritableCellFormat();
					// 居中
					cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
					cellFormat.setBackground(jxl.format.Colour.YELLOW);
					lable.setCellFormat(cellFormat);
					// 创建EXCEL工作表
					WritableSheet ws = wwb.createSheet("默认", 0);
					ws.addCell(lable);
					// 取得LIST中的数组大小
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 行
					int flag = 1;
					// 处理内标题
					String tempClosHeader[] = excelProperties.getColsHeader();
					for (int i = 0; i < tempClosHeader.length; i++) {
						lable = new Label(i, flag, tempClosHeader[i]);
						ws.addCell(lable);
					}
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					
					Integer intnumbersum=0;
					int j=0;
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							for (int i = 0; i < temp.length; i++) {
								//金额，数字输出		
								if("1".equals(ly)){
									if(i==6){
									jxl.write.Number number = new jxl.write.Number(i, flag, Double.parseDouble(temp[i]));									
									ws.addCell(number);
									Double doublst=Double.parseDouble(temp[i]);
									intnumbersum=intnumbersum+doublst.intValue();
									}else{
										lable = new Label(i, flag, temp[i]);
										ws.addCell(lable);
									}
								}
								if ("2".equals(ly)){
									if(i==7){
									jxl.write.Number number = new jxl.write.Number(i, flag, Double.parseDouble(temp[i]));									
									ws.addCell(number);
									Double doublst=Double.parseDouble(temp[i]);
									intnumbersum=intnumbersum+doublst.intValue();
									}else{
										lable = new Label(i, flag, temp[i]);
										ws.addCell(lable);
									}
								}
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
						j++;
					}
					//合计
					jxl.write.Number number = new jxl.write.Number(6, j+2, Double.parseDouble(intnumbersum.toString()));
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString();
					lable = new Label(5, j+4, numberstr);
					ws.addCell(lable);
					//
					if (result != Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION) {
						// 设置列宽
						if (colsSize.length == colsNum) {
							for (int i = 0; i < colsSize.length; i++) {
								ws.setColumnView(i, colsSize[i]);
							}
						} else {
							// 设置默认的宽度
							for (int i = 0; i < colsNum; i++) {
								ws.setColumnView(i, 20);
							}
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}
					// 加密excel
				    SheetSettings ss = ws.getSettings(); 
				    ss.setPassword("12345678"); 
				    ss.setProtected(true);
					
					wwb.write();
					wwb.close();
					os.close();
				} catch (IOException e) {
					result = Constant.EXPORT_EXCEL_NOFILE_EXCEPTION;
					e.printStackTrace();
				} catch (RowsExceededException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				} catch (WriteException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				}
			} else {
				result = Constant.EXPORT_EXCEL_NULL_OUTPUTSTREAM_EXCEPTION;
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	
	//	财委已审导出数据
	public static int exportExcelInWeb_03(ExcelProperties excelProperties, String innerTitle, int[] colsSize, List<String[]> data, OutputStream os,String ly) {
		int result = 1;
		
		int colsNum = 0;
		// 先判断是否传入原始数据
		if (data == null || data.size() == 0) {
			result = Constant.EXPORT_EXCEL_NO_DATA;
		} else {
			// 判断传入的流
			if (os != null) {
				// 生成EXCEL文件
				WritableWorkbook wwb = null;
				try {
					wwb = Workbook.createWorkbook(os);
					Label lable = new Label(0, 0, excelProperties.getHeader());
					// lable.setCellFormat(new CellFormat)
					WritableCellFormat cellFormat = new WritableCellFormat();
					// 居中
					cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
					cellFormat.setBackground(jxl.format.Colour.YELLOW);
					lable.setCellFormat(cellFormat);
					// 创建EXCEL工作表
					WritableSheet ws = wwb.createSheet("默认", 0);
					ws.addCell(lable);
					// 取得LIST中的数组大小
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 行
					int flag = 1;
					// 处理内标题
					String tempClosHeader[] = excelProperties.getColsHeader();
					for (int i = 0; i < tempClosHeader.length; i++) {
						lable = new Label(i, flag, tempClosHeader[i]);
						ws.addCell(lable);
					}
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					
					Integer intnumbersum=0;
					int j=0;
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							for (int i = 0; i < temp.length; i++) {
								//金额，数字输出		
								if("1".equals(ly)){
									if(i==1){
									jxl.write.Number number = new jxl.write.Number(i, flag, Double.parseDouble(temp[i]));									
									ws.addCell(number);
									Double doublst=Double.parseDouble(temp[i]);
									intnumbersum=intnumbersum+doublst.intValue();
									}else{
										lable = new Label(i, flag, temp[i]);
										ws.addCell(lable);
									}
								}
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
						j++;
					}
					//
					if (result != Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION) {
						// 设置列宽 	
						if (colsSize.length == colsNum) {
							for (int i = 0; i < colsSize.length; i++) {
								ws.setColumnView(i, colsSize[i]);
							}
						} else {
							// 设置默认的宽度
							for (int i = 0; i < colsNum; i++) {
								ws.setColumnView(i, 20);
							}
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}

					wwb.write();
					wwb.close();
					os.close();
				} catch (IOException e) {
					result = Constant.EXPORT_EXCEL_NOFILE_EXCEPTION;
					e.printStackTrace();
				} catch (RowsExceededException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				} catch (WriteException e) {
					result = Constant.EXPORT_EXCEL_SYSTEM_EXCEPTION;
					e.printStackTrace();
				}
			} else {
				result = Constant.EXPORT_EXCEL_NULL_OUTPUTSTREAM_EXCEPTION;
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	
		      /**
		      * 读取xls文件内容
		      * @param file 想要读取的文件对象
		      * @return 返回文件内容
		     */
		     public static List<Map<String, Object>> xls2String(File file,int startRow){
		        String result = "";
		        List<Map<String,Object>> allData = new ArrayList<Map<String ,Object>>();
	        	Map<String, Object> dataMap = null;
		        try{
		           FileInputStream fis = new FileInputStream(file);   
		           //StringBuilder sb = new StringBuilder();   
		           jxl.Workbook rwb = Workbook.getWorkbook(fis);   
		           Sheet[] sheet = rwb.getSheets();   
		           for (int i = 0; i < sheet.length; i++) {
	               Sheet rs = rwb.getSheet(i);   //获取所有的行
	               int cols = rs.getColumns();  //列
	               // 读取每一行对应的列数目
	               // 循环读取每一行的全部列数目的内容
	               int rows = rs.getRows();//行
	               for (int j = startRow; j < rows; j++) {
	            	   dataMap = new HashMap<String, Object>();
	            	   // 行循环,Excel的行列式从(0,0)开始
	            	   for (int k = 0; k < cols; k++) {
						 Cell excCell = rs.getCell(k, j);// 得到第4列的所有数据 (列,行)
						 dataMap.put("bfcl", excCell.getContents());//获取内容
	            	   }
	            	   allData.add(dataMap);
	               }
	               
		          /*     for (int j = 0; j < rs.getRows(); j++) {   
	                     Cell[] cells = rs.getRow(j); 
	                     
	                     for(int k=4;k<cells.length;k++)   
	                     sb.append(cells[k].getContents());   
			           }*/
		           }
		            /*  fis.close();   
		              result += sb.toString();*/
		        }catch(Exception e){
		            e.printStackTrace();
		        }
		          return allData;
		      }
		     
		     
		     
		     
		     
		     
		     
		     public static void readExcel(String filePath) throws IOException, BiffException{  
		         List list = new ArrayList();
		    	 //创建输入流  
		         InputStream stream = new FileInputStream(filePath);  
		         //获取Excel文件对象  
		         Workbook  rwb = Workbook.getWorkbook(stream);  
		         //获取文件的指定工作表 默认的第一个  
		         Sheet sheet = rwb.getSheet(0);    
		         //行数(表头的目录不需要，从1开始)  
		         for(int i=3; i<sheet.getRows(); i++){  
		              //创建一个数组 用来存储每一列的值  
		             String[] str = new String[sheet.getColumns()];  
		             Cell cell = null;  
		             //列数  
		             for(int j=0; j<sheet.getColumns(); j++){  
		               //获取第i行，第j列的值  
		               cell = sheet.getCell(1,i);    
		               if(str[j]!=null){
		            	   str[j] = cell.getContents();  
		               }
		               System.out.println(str[i]);
		             }  
		           //把刚获取的列存入list  
		           list.add(str);  
		         }
		         int count=0;
		         for(int i=0;i<list.size();i++){
		              String[] str = (String[])list.get(i);
		              for(int j=0;j<str.length;j++){
		            	  count++;
		                  System.out.println(count+"------"+str[j]);
		              }
		          }
		     } 
		     
		     
		     
		     public static PayResultImport readPayResultImport(File file) throws Exception{
		    	 InputStream io = new FileInputStream(file.getAbsoluteFile());  
		    	  PayResultImport payResultImport = new PayResultImport();
		    	  Workbook readwb = Workbook.getWorkbook(io); 
		    	  Sheet sheet = readwb.getSheet(0);
		    	  // 第二行第一列的值
		    	  
		    	  Cell cell = sheet.getCell(2, 1);
		    	  String dateString = cell.getContents().replace("/", "-");
		    	  System.out.println(cell.getContents());
		    	  Date date = DateUtil.parse(dateString, "yyyy-MM-dd");
		    	  /*DateCell dc = (DateCell)cell;
		    	  date = dc.getDate();	//获取单元格的date类型
*/		    	  // 获取制表时间  
		    	  payResultImport.setMakeTime(date);
		    	  Date date2 = new Date();
		    	  // 获取导入时间
		    	  payResultImport.setImportTime(date2);// new Date()为获取当前系统时间
		    	  // 导入文件路径
		    	  payResultImport.setFilePath(file+"");
		    	  
		    //	  payImportDao.save(payResultImport);
		    	  return payResultImport;
		     }
		     
		     
		     
		     
		     public static void readSpecify(File file)throws Exception{  
		    	 List<PayResultImportDetail>  list = new ArrayList<PayResultImportDetail>();
		    //	 PayResultImport resultImport = new PayResultImport();  
		   // 	 ArrayList<String> columnList = new ArrayList<String>();  
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
		    	    	PayResultImportDetail payResultImportDetail = new PayResultImportDetail();
		    	    //	Cell[] cells = readsheet.getRow(i);
		    	    //	System.out.println("--"+cells[0]);
		    	    	// 获取国库受理单号
		    	    	Cell cell = readsheet.getCell(0, i);  
		    	        payResultImportDetail.setRequestNo(cell.getContents());
		    	        System.out.println("--"+cell.getContents());
		    	        // 获取付款日期
		    	        Cell cell1 = readsheet.getCell(1, i); 
		    	        if(cell1.getContents()!=null && cell1.getContents()!=""){
			    	        Date date = DateUtil.parse(cell1.getContents(), "yyyy-MM-dd HH:mm:ss");
			    	        payResultImportDetail.setPayTime(date);
			    	        System.out.println("--"+cell1.getContents());
		    	        }
		    	        // 获取清算日期
		    	        Cell cell2 = readsheet.getCell(2, i);
		    	        if(null!=cell2.getContents() && cell2.getContents()!=""){
			    	        Date date1 = DateUtil.parse(cell2.getContents(), "yyyy-MM-dd HH:mm:ss");
			    	        payResultImportDetail.setConfirmTime(date1);
			    	        System.out.println("--"+cell2.getContents());
		    	        }
		    	        //支付单号
		    	        Cell cell3 = readsheet.getCell(3, i);
		    	        System.out.println("--"+cell3.getContents());
		    	        payResultImportDetail.setPayNo(cell3.getContents());
		    	        //原支付单号
		    	        Cell cell4 = readsheet.getCell(4, i);
		    	        System.out.println("--"+cell4.getContents());
		    	        payResultImportDetail.setOriginalPayNo(cell4.getContents());
		    	        // 资金说明
		    	        Cell cell5 = readsheet.getCell(15, i);
		    	        System.out.println("--"+cell5.getContents());
		    	        payResultImportDetail.setPayComment(cell5.getContents());
		    	        // 支付金额
	    	        	Cell cell6 = readsheet.getCell(16, i);
	    	        // 	payResultImportDetail.setPayAmount(Double.parseDouble(cell6.getContents()));
	    	        	String numDouble = cell6.getContents().replace(",", "");
	    	        	System.out.println(numDouble);
	    	        	payResultImportDetail.setPayAmount(Double.parseDouble(numDouble));
		    	        // 支付状态
	    	        	Cell cell7 = readsheet.getCell(17, i);
	    	        	System.out.println("--"+cell7.getContents());
	    	        	payResultImportDetail.setPayResStatus(cell7.getContents());
		    	        // 收款人名称
	    	        	Cell cell8 = readsheet.getCell(18, i);
	    	        	System.out.println("--"+cell8.getContents());
	    	        	payResultImportDetail.setAccountName(cell8.getContents());
		    	        // 收款人账户
	    	        	Cell cell9 = readsheet.getCell(19, i);
	    	        	System.out.println("--"+cell9.getContents());
	    	        	payResultImportDetail.setAccountNo(cell9.getContents());
		    	        // 收款人开户行
	    	        	Cell cell10 = readsheet.getCell(20, i);
	    	        	System.out.println("--"+cell10.getContents());
	    	        	payResultImportDetail.setBankName(cell10.getContents());
	    	        //	Serializable id = importDetailDao.save(payResultImportDetail);
	    	        	System.out.println("------------------------------------");
		    	    }  
		    	   /* String[] ageString = new String[columnList.size()];  
		    	    for (int i = 0; i < columnList.size(); i++) {  
		    	    	if(columnList.get(i)!="" && null!=columnList.get(i)){
		    	    		ageString[i] = columnList.get(i);  
		    	    	}
		    	        System.out.println(ageString[i]);  
		    	        System.out.println();
		    	    }*/
		    	}  
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
	     public static void main(String[] args){
	    	 
	         File file = new File("D:/国库导出支付信息模板.xls");
	         try {
	        	 readPayResultImport(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      }
		  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	public static void main(String[] args) {
/*		ExcelProperties excelProperties = new ExcelProperties();
		// excelProperties.setBeginRows(2);
		excelProperties.setColsHeader(new String[] { "序号", "车牌号码", "补贴金额", "车主姓名", "原开户银行", "原开户账户", "变更后补贴对象", "变更后银行","变更后银行账号","变更内容","批次号" });
		excelProperties.setHeader("深圳市老旧车提前淘汰奖励补贴退款重新支付审核表(第X批)");
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File("d:\\tstddd.xls"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String[]> dataList = new ArrayList<String[]>();
		dataList.add(new String[] { "1", "粤B2000", "10000.0", "警世通企业有限公司", "农业银行", "543513434354", "警世通企业有限公司", "工商银行", "1234521312","银行账户有误重新变更","batch_1" });
		dataList.add(new String[] { "2", "粤B900xxx", "20000.0", "张三", "农业银行", "543513434354", "张绍刚", "工商银行", "1234521312","银行账户有误重新变更","batch_1" });*/
	//	repExportExcelPreview(excelProperties, "ss", new int[] {5,13,10,18,15,18,20,15,18,23,8 }, dataList, outputStream);
		
//	}
}
