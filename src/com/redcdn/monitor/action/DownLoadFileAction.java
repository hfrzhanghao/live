/**
 * 
 */
package com.redcdn.monitor.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sun.management.counter.Variability;

import com.redcdn.monitor.common.AboutTime;
import com.redcdn.monitor.common.CommonConstants;
import com.redcdn.monitor.util.PostRequest;
import com.redcdn.monitor.ws.proxy.RestTemplateProxy;

/**
 * 下载文件
 * 
 * @author 刘艳伟
 * @path monitor2.0/com.redcdn.monitor.action/DownLoadFileAction.java
 */
public class DownLoadFileAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String startTime;
	private String endTime;
	private String fileType;// 日志类型
	private String ipAndPort;// ip:port
	private String clientId;// 客户端ID

	private String sidsArr;// 查询页面要导出的sid

	
	private String sdkmac;// 视讯号登录设备的MAC
	private String type;//类型，指明是SDK概要信息几部分中哪一部分
	/**
	 * 下载客户端日志
	 * 
	 * @author 刘艳伟
	 * @date 2015-7-25下午5:09:33
	 */
	public void downloadClientLogFile() {
		
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
		
		form.add("startTime", startTime);
		form.add("endTime", endTime);
		form.add("sdkmac", sdkmac + "");
		form.add("type", type + "");
		
		JSONObject jsonObject = null;
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String downLoadFileName = "clientLog" + sf.format(Calendar.getInstance().getTime()) + ".log";

		try {
			logger.info("下载客户端日志");
			jsonObject = proxy.postFormWithReturnJSONObject("/sdk/sdkInfoList", form);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();// 生成流对象
			String sdkEvent = "哎呀，不好啦，没有对应的客户端日志哟";
			if(jsonObject.getJSONObject("data")!=null){
				if(jsonObject.getJSONObject("data").getJSONArray("sdkEvent")!=null){
					sdkEvent = jsonObject.getJSONObject("data").getJSONArray("sdkEvent").toString();
				}
			}

			byteArrayOutputStream.write(sdkEvent.getBytes(), 0, sdkEvent.getBytes().length);
			// 工具类，封装弹出下载框：
			DownloadBaseAction down = new DownloadBaseAction();
			HttpServletResponse response = ServletActionContext.getResponse();
			try {
				down.download(byteArrayOutputStream, response, downLoadFileName);
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 获取服务器端日志，不下载
	 * 
	 * @author 刘艳伟
	 * @date 2015-7-21上午11:09:03
	 */
	/*public String downloadServerLogFile() {

		// 从接口中获取到的服务器端日志下载地址的格式为：
		// {"result":0,"object":[{"id":0,"fileType":"rc","fileName":"http://localhost:8090/ftpContent/aaaaa.zip","fileContent":"","fileTimeBegin":0,"fileTimeEnd":1439464268000,"ipAndPort":"10.130.36.193:2345","clientId":2}]}
		// 所以要将其文件读取出来转化为字段串
		JSONArray jsonArray = new JSONArray();
		if (fileType != null) {
			if (fileType.indexOf(",") != -1) {

				String[] startTimeArr = startTime.split(",");
				String[] endTimeArr = endTime.split(",");
				String[] fileTypeArr = fileType.split(",");
				String[] ipAndPortArr = ipAndPort.split(",");
				String[] clientIdArr = clientId.split(",");
				int lenth = fileTypeArr.length;
				for (int i = 0; i < lenth; i++) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.element("startTime", AboutTime.toLong(startTimeArr[i]) + "");
					jsonObject.element("endTime", AboutTime.toLong(endTimeArr[i]) + "");
					jsonObject.element("fileType", fileTypeArr[i]);
					jsonObject.element("ipAndPort", ipAndPortArr[i]);
					jsonObject.element("clientId", clientIdArr[i]);
					jsonArray.element(jsonObject);
				}
			} else {
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("startTime", AboutTime.toLong(startTime) + "");
				jsonObject.element("endTime", AboutTime.toLong(endTime) + "");
				jsonObject.element("fileType", fileType);
				jsonObject.element("ipAndPort", ipAndPort);
				jsonObject.element("clientId", clientId);
				jsonArray.element(jsonObject);
			}
		}

		JSONObject jsonObject = null;
		int result = 10;
		try {

			jsonObject = proxy.postJsonToCloudStore(CommonConstants.DOWNSERVERLOGURL, jsonArray.toString());
			// jsonObject =
			// proxy.postJsonToCloudStore("http://localhost:8090/serverLogServer/fileContent/findServerLogList",
			// jsonArray.toString());
			logger.info("下载服务器端日志");

			if (jsonObject != null) {
				String res = jsonObject.getString("result");
				if ("0".equals(res)) {
					JSONArray resArray = jsonObject.getJSONArray("object");
					if (resArray != null && resArray.size() > 0) {
						for (int i = 0; i < resArray.size(); i++) {

							JSONObject jsonList = resArray.getJSONObject(i);

							String url = jsonList.getString("fileName");
							// 压缩文件存放在tomcat的webapps\ftpContent\项目中
							// D:\Program Files\apache-tomcat-7.0.50\webapps\monitor2.0\http:\localhost:8090\ftpContent\aaaaa.rar
							String path = ServletActionContext.getServletContext().getRealPath("/");
							String dircPath = path.substring(0, path.indexOf("webapps") + 8) + url.substring(url.indexOf("ftpContent"), url.length());

							jsonList.element("fileName", unZip(dircPath));
						}
					}
					jsonObject.element("object", resArray);
				}

			}

			result = jsonObject.getInt("result");
		} catch (Exception e) {
			result = 10;
			String message = e.getMessage() == null ? "" : e.getMessage();
			if (message.indexOf("Read timed out;") != -1) {
				result = -1;
			}
			jsonObject = new JSONObject();
			jsonObject.element("result", result);
			logger.error(e.getMessage(), e);
		}

		if (isAjax(request)) {
			return renderJsonString(jsonObject.toString());
		}
		return null;
	}*/

	/**
	 * 从解压包中循环读取文件，并输出文件内容
	 * 
	 * @author 刘艳伟
	 * @date 2015-8-13下午2:54:07
	 * @param zipFileName
	 *            被解压的zip文件
	 * @param destPath
	 *            解压后文件的存放路径
	 */
	public static String unZip(String zipFileName) {
		InputStream in = null;
		FileOutputStream out = null;
		StringBuffer sb = new StringBuffer();
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;

			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();

				in = zipFile.getInputStream(zipEntry);
				int len = -1;
				while ((len = in.read()) != -1) {
					sb.append((char) len);
				}
				sop(sb);
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static void sop(Object obj) {
		System.out.println(obj);
	}

	/**
	 * 下载日志查询首页的sid
	 * 
	 * @author 刘艳伟
	 * @date 2015-8-5下午3:53:39
	 */
	public void downSid(JSONObject jsonObject) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(); // 新建工作簿
		HSSFSheet sheet = wb.createSheet(); // 新建工作表
		HSSFRow nRow = sheet.createRow(5); // 创建行对象，起始值为0
		HSSFCell nCell = nRow.createCell(4); // 创建单元格对象

		// 创建表头
		nRow = sheet.createRow(0);
		nCell = nRow.createCell(0);
		nCell.setCellValue("从日志查询系统中导出的sid");

		nRow = sheet.createRow(1);
		nCell = nRow.createCell(0);
		nCell.setCellValue("编号");
		nCell = nRow.createCell(1);
		nCell.setCellValue("sid");

		JSONArray items = new JSONArray();
		items = jsonObject.getJSONArray("items");
		for (int i = 0; i < items.size(); i++) {
			String sid = items.getJSONObject(i).getString("sid");
			sid = sid.substring(0, sid.lastIndexOf("_"));

			// 填充内容
			nRow = sheet.createRow(i + 2);
			nCell = nRow.createCell(0);
			nCell.setCellValue(i);
			nCell = nRow.createCell(1);
			nCell.setCellValue(sid);

		}

		// 生成excel文件
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 生成流对象
		wb.write(byteArrayOutputStream); // 将excel写入流

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		String fileName = sf.format(Calendar.getInstance().getTime());

		// 工具类，封装弹出下载框：
		String outFile = fileName + ".xls";
		DownloadBaseAction down = new DownloadBaseAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		down.download(byteArrayOutputStream, response, outFile);
	}
	/**
	 * 下载离线话务的sid
	 * 
	 * @author 刘艳伟
	 * @date 2015-8-5下午3:53:39
	 */
	public void downOffLineSid(JSONObject jsonObject) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook(); // 新建工作簿
		HSSFSheet sheet = wb.createSheet(); // 新建工作表
		HSSFRow nRow = sheet.createRow(5); // 创建行对象，起始值为0
		HSSFCell nCell = nRow.createCell(4); // 创建单元格对象
		
		// 创建表头
		nRow = sheet.createRow(0);
		nCell = nRow.createCell(0);
		nCell.setCellValue("从日志查询系统中导出的sid");
		
		nRow = sheet.createRow(1);
		nCell = nRow.createCell(0);
		nCell.setCellValue("编号");
		nCell = nRow.createCell(1);
		nCell.setCellValue("sid");
		
		JSONArray items = new JSONArray();
		items = jsonObject.getJSONArray("offLineSids");
		for (int i = 0; i < items.size(); i++) {
			String sid = items.getJSONObject(i).getString("sid");
			
			// 填充内容
			nRow = sheet.createRow(i + 2);
			nCell = nRow.createCell(0);
			nCell.setCellValue(i);
			nCell = nRow.createCell(1);
			nCell.setCellValue(sid);
			
		}
		
		// 生成excel文件
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 生成流对象
		wb.write(byteArrayOutputStream); // 将excel写入流
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		String fileName = sf.format(Calendar.getInstance().getTime());
		
		// 工具类，封装弹出下载框：
		String outFile = fileName + ".xls";
		DownloadBaseAction down = new DownloadBaseAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		down.download(byteArrayOutputStream, response, outFile);
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getIpAndPort() {
		return ipAndPort;
	}

	public void setIpAndPort(String ipAndPort) {
		this.ipAndPort = ipAndPort;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSidsArr() {
		return sidsArr;
	}

	public void setSidsArr(String sidsArr) {
		this.sidsArr = sidsArr;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSdkmac() {
		return sdkmac;
	}

	public void setSdkmac(String sdkmac) {
		this.sdkmac = sdkmac;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
