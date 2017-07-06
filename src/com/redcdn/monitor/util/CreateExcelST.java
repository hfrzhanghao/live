package com.redcdn.monitor.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.hssf.util.HSSFColor;

import java.util.List;
import java.io.FileOutputStream;

public class CreateExcelST {
    @SuppressWarnings("unchecked")
    public void createExcelFile(ExcelBean excelBean) throws Exception {
        // 创建工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(excelBean.getSheetName());

        // 打印页面设置
        HSSFPrintSetup ps = sheet.getPrintSetup();
        sheet.setMargin(HSSFSheet.BottomMargin, 0.5);// 页边距
        sheet.setMargin(HSSFSheet.LeftMargin, 0.1);
        sheet.setMargin(HSSFSheet.RightMargin, 0.1);
        sheet.setMargin(HSSFSheet.TopMargin, 0.5);
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); // 纸张

        // 设置列宽
        this.setColumnWidth(sheet, excelBean.getColumnWidth());

        // 标题栏设置字体
        HSSFFont cellFontReport = wb.createFont();
        cellFontReport.setFontHeightInPoints((short) 16); // 字号
        cellFontReport.setBoldweight(HSSFFont.U_SINGLE); // 加粗
        cellFontReport.setFontName("Courier New");
        // 字段栏设置字体
        HSSFFont cellFontColumn = wb.createFont();
        cellFontColumn.setFontHeightInPoints((short) 12); // 字号
        cellFontColumn.setBoldweight(HSSFFont.U_SINGLE); // 加粗
        cellFontColumn.setFontName("Courier New");
        cellFontColumn.setColor(HSSFFont.SS_NONE);
        // 设置字体
        HSSFFont cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short) 10); // 字号
        cellFont.setBoldweight(HSSFFont.U_SINGLE); // 加粗
        cellFont.setFontName("Courier New");

        //自定义颜色
        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.AQUA.index, (byte) 252, (byte) 254, (byte) 236);

        // 设置标题栏单元格格式
        HSSFCellStyle cellStyleReport = wb.createCellStyle();
        cellStyleReport.setFont(cellFontReport);
        cellStyleReport.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 左右居中
        cellStyleReport.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
        cellStyleReport.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyleReport.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyleReport.setBorderRight(HSSFCellStyle.BORDER_THIN); //右边框
        cellStyleReport.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
        cellStyleReport.setWrapText(true);//自动换行
        cellStyleReport.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyleReport.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 设置标题栏单元格格式
        HSSFCellStyle cellStyleColumn = wb.createCellStyle();
        cellStyleColumn.setFont(cellFontColumn);
        cellStyleColumn.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 左右居中
        cellStyleColumn.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
        cellStyleColumn.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        cellStyleColumn.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyleColumn.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
        cellStyleColumn.setBorderTop(HSSFCellStyle.BORDER_THIN); // 上边框
        cellStyleColumn.setWrapText(true);// 自动换行
        cellStyleColumn.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        cellStyleColumn.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // 设置单元格格式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(cellFont);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //左右居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //上下居中
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边框
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边框
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); //上边框
        cellStyle.setWrapText(true);//自动换行

        List dataList = excelBean.getDataList();
        if (null != dataList) {
            // 报表的标题
            List titleList = excelBean.getTitleList();

            // 创建行 设置报表名称
            HSSFRow rowReportName = sheet.createRow(0);
            HSSFCell cellReportName = rowReportName.createCell((short) 0);
            sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (titleList.size() - 

1)));
            HSSFRichTextString reportString = new HSSFRichTextString

(excelBean.getReportName());
            cellReportName.setCellValue(reportString);
            cellReportName.setCellStyle(cellStyleReport);
            HSSFCell endReportName = rowReportName.createCell((short) (titleList.size() - 

1));
            endReportName.setCellStyle(cellStyleReport);
            // 创建行
            HSSFRow rowTitle = sheet.createRow(1);

            for (int i = 0; i < titleList.size(); i++) {
                String s_title = (String) titleList.get(i);
                HSSFCell cellx_y = rowTitle.createCell((short) i);
                HSSFRichTextString hssfRichTextString = new HSSFRichTextString(s_title);
                // 单元格内容
                cellx_y.setCellValue(hssfRichTextString);
                // 单元格格式
                cellx_y.setCellStyle(cellStyleColumn);
            }

            // 报表数据
            for (int i = 0; i < dataList.size(); i++) {
                HSSFRow row = sheet.createRow(i + 2);
                List list_row = (List) dataList.get(i);
                for (int j = 0; j < list_row.size(); j++) {
                    String strtmp = (String) list_row.get(j);
//                    String strtmp = list_row.get(j).toString();
                    if (" ".equals(strtmp)) {
                        strtmp = " ";
                    }
                    HSSFCell cell = row.createCell((short) j);
                    try {
						 cell.setCellValue(Integer.parseInt

(strtmp));
					} catch (Exception e) {
						HSSFRichTextString hssfRichTextString = new 

HSSFRichTextString(strtmp);
	                    cell.setCellValue(hssfRichTextString);
					}
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        // 另外一个标题
        List otherTitleList = excelBean.getOtherTitleList();
        if (otherTitleList != null) {
            // 创建行
            HSSFRow otherRowTitle = sheet.createRow(dataList.size() + 2);

            for (int i = 0; i < otherTitleList.size(); i++) {
                String s_title = (String) otherTitleList.get(i);
                HSSFCell cellx_y = otherRowTitle.createCell((short) i);
                HSSFRichTextString hssfRichTextString = new HSSFRichTextString(s_title);
                cellx_y.setCellValue(hssfRichTextString); //单元格内容
                cellx_y.setCellStyle(cellStyle); // 单元格格式
            }
        }
            // 另外的数据集
            List otherDataList = excelBean.getOtherDataList();
            if (otherDataList != null) {
            // 报表数据
            for (int i = 0; i < otherDataList.size(); i++) {
                HSSFRow row = sheet.createRow(dataList.size() + 2 + i);
                List list_row = (List) otherDataList.get(i);
                for (int j = 0; j < list_row.size(); j++) {
                    String strtmp = (String) list_row.get(j);
                    if (" ".equals(strtmp)) {
                        strtmp = " ";
                    }
                    HSSFCell cell = row.createCell((short) j);
                    
                    try {
						 cell.setCellValue(Integer.parseInt

(strtmp));
					} catch (Exception e) {
						HSSFRichTextString hssfRichTextString = new 

HSSFRichTextString(strtmp);
	                    cell.setCellValue(hssfRichTextString);
					}
                    cell.setCellStyle(cellStyle);
                }
            }
            }

        try {
            FileOutputStream fileOut = new FileOutputStream(excelBean.getPath());
           // log.debug("===FilePath>>>>>>>>>>>>>>>>>.===>>>>>>" + excelBean.getPath());
          //  System.out.println

            //log.fatal("关闭");
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            throw new Exception("文件已经打开，请关闭后再生成");
        }
    }

    public void setColumnWidth(HSSFSheet sheet, int[] width) {
        for (int i = 0; i < width.length; i++) {
            sheet.setColumnWidth((short) i, (short) (width[i] * 100));
        }
    }
}
