package com.jst.vesms.util.excel;

public interface Constant {
	// 成功导出EXCEL文件
	public static final int EXPORT_EXCEL_SUCCESS = 1;

	// 没有数据
	public static final int EXPORT_EXCEL_NO_DATA = -1;

	// 系统异常
	public static final int EXPORT_EXCEL_SYSTEM_EXCEPTION = -2;
	// 其他异常
	public static final int EXPORT_EXCEL_OTHER_EXCEPTION = -3;

	// 文件路径和文件名无效
	public static final int EXPORT_EXCEL_NOFILE_EXCEPTION = -4;

	// 集合对象中的数组存放的数据个数不一致
	public static final int EXPORT_EXCEL_ARRAYNOTHESAME_EXCEPTION = -5;

	// 传入的OUTPUTSTREAM为空
	public static final int EXPORT_EXCEL_NULL_OUTPUTSTREAM_EXCEPTION = -6;

	// 宽度设置参数有误，按默认值设定宽度
	public static final int EXPORT_EXCEL_COLSNUM_NOTRIGHT = 2;

}
