package com.jst.vesms.util.excel;

public class ExcelProperties {
	private String header;
	private String[] colsHeader;
	private int beginRows;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String[] getColsHeader() {
		return colsHeader;
	}

	public void setColsHeader(String[] colsHeader) {
		this.colsHeader = colsHeader;
	}

	public int getBeginRows() {
		return beginRows;
	}

	public void setBeginRows(int beginRows) {
		this.beginRows = beginRows;
	}

}
