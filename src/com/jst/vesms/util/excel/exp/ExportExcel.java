package com.jst.vesms.util.excel.exp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.CellView;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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
	
	
	//导出预览(不加密)
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
					WritableFont font = new WritableFont(WritableFont.ARIAL,11,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
					WritableCellFormat cellFormat = new WritableCellFormat(font);
					// 居中
					cellFormat.setAlignment(Alignment.CENTRE);
					// 背景颜色
					cellFormat.setBackground(Colour.YELLOW);
					lable.setCellFormat(cellFormat);
					// 创建EXCEL工作表
					WritableSheet ws = wwb.createSheet("默认", 0);
					ws.addCell(lable);
					
					// 取得LIST中的数组大小(对象的个数，excel中的列数)
					colsNum = data.get(0).length;
					// 参数的含义为：左列，左行,右列，右行 合并成一个单元格
					ws.mergeCells(0, 0, colsNum - 1, 0);
					// 行
					int flag = 2;
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
					jxl.write.Number number = new jxl.write.Number(6, j+2, Double.parseDouble(intnumbersum.toString()));
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
				
					WritableCellFormat endFormat = new WritableCellFormat();
					endFormat.setBackground(Colour.YELLOW);
					lable = new Label(7, j+4, numberstr);
					lable.setCellFormat(endFormat);
					ws.addCell(lable);
					lable = new Label(7, 1, numberstr);
					lable.setCellFormat(endFormat);
					ws.addCell(lable);
					
					
					

					//
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
	
	
	
	
	//报财务导出(加密)
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
					// 第3行上设置内标题
					int flag = 2;
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
					jxl.write.Number number = new jxl.write.Number(6, j+2, Double.parseDouble(intnumbersum.toString()));
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
				
					WritableCellFormat endFormat = new WritableCellFormat();
					endFormat.setBackground(Colour.YELLOW);
					lable = new Label(7, j+4, numberstr);
					lable.setCellFormat(endFormat);
					ws.addCell(lable);
					lable = new Label(7, 1, numberstr);
					lable.setCellFormat(endFormat);
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
					ws.setRowView(0, 1200, false);
					
					
					
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
						ws.addCell(lable);
					}
					
					// 给第二行后末尾行加上标志(显示总行和总金额)
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
					jxl.write.Number number = new jxl.write.Number(6, j+2, Double.parseDouble(intnumbersum.toString()));
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
				
					WritableCellFormat endFormat = new WritableCellFormat();
					endFormat.setBackground(Colour.YELLOW);
					lable = new Label(10, j+4, numberstr);
					lable.setCellFormat(endFormat);
					ws.addCell(lable);
					lable = new Label(10, 1, numberstr);
					lable.setCellFormat(endFormat);
					ws.addCell(lable);
					
					
					//设置内容
					WritableCellFormat content = new WritableCellFormat();
					// 水平居中
					content.setAlignment(Alignment.CENTRE);
					// 垂直居中
					content.setVerticalAlignment(VerticalAlignment.CENTRE); 
					
					CellView cellView = new CellView(); 
				    cellView.setAutosize(true); //设置自动大小
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							for (int i = 0; i < temp.length; i++) {
								lable = new Label(i, flag, temp[i],content);
								ws.setRowView(i+1, 500);
								ws.addCell(lable);
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}

					//
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
	
	
	
	
	
	
	
	
	//重报批次报财务（加密文件）
	public static int repExportExcelToFinance(ExcelProperties excelProperties, String innerTitle, int[] colsSize, List<String[]> data, OutputStream os,String password) {
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
					ws.setRowView(0, 1200, false);
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
						ws.addCell(lable);
					}
					
					// 给第二行后末尾行加上标志(显示总行和总金额)
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
					jxl.write.Number number = new jxl.write.Number(6, j+2, Double.parseDouble(intnumbersum.toString()));
					String numberstr="车辆数："+data.size()+"  金额总数："+intnumbersum.toString()+"元";
				
					WritableCellFormat endFormat = new WritableCellFormat();
					endFormat.setBackground(Colour.YELLOW);
					lable = new Label(10, j+4, numberstr);
					lable.setCellFormat(endFormat);
					ws.addCell(lable);
					lable = new Label(10, 1, numberstr);
					lable.setCellFormat(endFormat);
					ws.addCell(lable);
					
					
					//设置内容
					WritableCellFormat content = new WritableCellFormat();
					// 水平居中
					content.setAlignment(Alignment.CENTRE);
					// 垂直居中
					content.setVerticalAlignment(VerticalAlignment.CENTRE); 
					
					CellView cellView = new CellView(); 
				    cellView.setAutosize(true); //设置自动大小
					if (tempClosHeader != null && tempClosHeader.length > 0) {
						flag++;
					}
					for (String temp[] : data) {
						if (colsNum == temp.length) {
							for (int i = 0; i < temp.length; i++) {
								lable = new Label(i, flag, temp[i],content);
								ws.setRowView(i+1, 500);
								ws.addCell(lable);
							}
							flag++;
						} else {
							result = Constant.EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION;
						}
					}

					//
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
					 SheetSettings ss = ws.getSettings(); 
					    ss.setPassword(password); 
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
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		ExcelProperties excelProperties = new ExcelProperties();
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
		dataList.add(new String[] { "2", "粤B900xxx", "20000.0", "张三", "农业银行", "543513434354", "张绍刚", "工商银行", "1234521312","银行账户有误重新变更","batch_1" });
		repExportExcelPreview(excelProperties, "ss", new int[] {5,13,10,18,15,18,20,15,18,23,8 }, dataList, outputStream);

	}
}
