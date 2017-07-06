package com.redcdn.monitor.util;

import java.util.List;

public class ExcelBean {
	private String sheetName;
	private int[] columnWidth;
	private List dataList;
	private List titleList;
	private String reportName;

	
	private List otherTitleList;
	private List otherDataList;
	
	
	private String path;
	private boolean flag;
	

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List getOtherTitleList() {
		return otherTitleList;
	}
	public void setOtherTitleList(List otherTitleList) {
		this.otherTitleList = otherTitleList;
	}
	public List getOtherDataList() {
		return otherDataList;
	}
	public void setOtherDataList(List otherDataList) {
		this.otherDataList = otherDataList;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public List getTitleList() {
		return titleList;
	}
	public void setTitleList(List titleList) {
		this.titleList = titleList;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public int[] getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(int[] columnWidth) {
		this.columnWidth = columnWidth;
	}

}
