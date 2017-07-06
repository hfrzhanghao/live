package com.redcdn.monitor.action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import sun.misc.BASE64Encoder;

import com.redcdn.monitor.common.AboutTime;
import com.redcdn.monitor.common.CommonConstants;
import com.redcdn.monitor.common.GZipUtils;
import com.redcdn.monitor.common.HttpPostMethod;
import com.redcdn.monitor.util.Mail;
import com.redcdn.monitor.util.PostRequest;


/**
 * @Author 刘艳伟
 * @date 2014-5-6下午3:22:50
 * @类功能　将“话务报告”和“通话详情”的数据都封装在这里，组合成一个json返回
 */
public class AllAction extends BaseAction {
	private static final long serialVersionUID = 2L;

	private String sid;// 会话id
	XMLSerializer xmlSerializer = new XMLSerializer();
	/**
	 * @方法功能　点击“报告”和“日志”链接后，进入“话务报告”和“通话详情”的数据都封装在这里，组合成一个json返回
	 * @return
	 */	
	public void report() {
		response.setContentType("text/html;charset=UTF-8");
		try {
			String urls = "/report/report";
			HttpClient http = new HttpClient();
			HttpPostMethod get = proxy.httpPostMothed(urls);
			get.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			// 添加头信息告诉服务端可以对Response进行GZip压缩
			get.setRequestHeader("Accept-Encoding", "gzip");
			get.setParameter("sid", sid);
			get.setParameter("isSimpl", "false");
			try {
				int statusCode = http.executeMethod(get);
				if (statusCode != HttpStatus.SC_OK) {
					logger.error("Method failed: " + get.getStatusLine());
				}
				ServletOutputStream sout = response.getOutputStream();
				byte[] dat = get.getResponseBodyover();
				response.setHeader("Content-Encoding", "gzip");
				response.setHeader("Content-Length", dat.length + "");
				sout.write(dat);
				sout.flush();
				sout.close();
			} catch (Exception e) {
				Mail.sendMail("日志查询异常－", "通话详情数据获取失败", get.toString());
				logger.error("页面无法访问");
				e.printStackTrace();
			} finally {
				get.releaseConnection();
			}
		} catch (Exception e) {
		}
		
		
		/*MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
		
		//只须传递一个sid参数即可
		form.add("sid", sid);
		//添加isSimpl参数，为true时只取出主和被的网优数据，为false时取出所有。
		form.add("isSimpl", "false");
		
		JSONObject jsonObject = null;
		int result = 10;
		try {
			jsonObject = proxy.postFormWithReturnJSONObject("/report/report", form);
			if(jsonObject != null && jsonObject.getInt("result")==0){
				if(!"null".equals(jsonObject.get("data").toString())){
					JSONObject data = jsonObject.getJSONObject("data");
					com.redcdn.monitor.common.GZipUtils gzs = new com.redcdn.monitor.common.GZipUtils();
					BASE64Decoder decod = new BASE64Decoder();
					JSONObject	called =data.getJSONObject("called");
					if(called != null){
						String calledGetpath = called.getString("getpath");
						if(calledGetpath != null && !"".equals(calledGetpath) && !"null".equals(calledGetpath)){
						byte [] bytedata = decod.decodeBuffer(calledGetpath);
						byte[] output = gzs.decompress(bytedata);  
						String outputStr = new String(output);  
						System.err.println("解压缩后长度:\t" + output.length);
						jsonObject.getJSONObject("data").getJSONObject("called").element("getpath",JSONObject.fromObject(outputStr));
						}
					}
					JSONObject	caller =data.getJSONObject("caller");
					if(caller != null){
						String callerGetpath = caller.getString("getpath");
						if(callerGetpath != null && !"".equals(callerGetpath) && !"null".equals(callerGetpath)){
						byte [] bytedata = decod.decodeBuffer(callerGetpath);
						byte[] output = gzs.decompress(bytedata);  
						String outputStr = new String(output);  
						System.err.println("解压缩后长度:\t" + output.length);
						jsonObject.getJSONObject("data").getJSONObject("caller").element("getpath",JSONObject.fromObject(outputStr));
						}
					}
				}
			}
			
			logger.info("根据sid取出json信息");
			
			//result = jsonObject.getInt("result");
			//System.out.println("AllAction.java中查询结果，“通话详情”/“话务报告”的整个json："+jsonObject);
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
		return null;*/
	}
	
	
	/**
	 * 获取所有Relay状态信息
	 * 
	 * @author 刘艳伟
	 * @date 2014-10-21上午11:14:15
	 */
	/*public String sopRelayStateURL() {
		
		
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
		JSONObject jsonObject = null;
		// 取得前台页面传来的值
		form.add("ignoreParam", "true");
		try {
			// 参数
			String data = "ignoreParam=true";
			String json = PostRequest.go(CommonConstants.RELAYSTATEURL, data);
			//logger.info("从地址" + relayStateURL + "获取所有relay状态成功！:" + json);
			logger.info("获取所有relay状态成功！:" + json);
			if (isAjax(request)) {
				return renderJsonString(xmlSerializer.read(json).toString());
			}
		} catch (Exception e) {
			logger.error("获取所有relay信息失败！");
			logger.error(e);
		}
		return null;
	}*/
	
	
	
	/**
	 * 通过一个5位的sid去查询是否生成了报告，供测试所有程序是否全部可以跑通使用
	 * @author 刘艳伟 
	 * @date 2014-10-29下午2:09:22
	 */
	public void sopReportBySid() {
		//获取数据的标志
		String flag = "0";
		
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		// 取得前台页面传来的值
		form.add("isSimpl", "true");
		form.add("sid", sid.trim());
		JSONObject jsonObject = null;
		try {
			jsonObject = proxy.postFormWithReturnJSONObject("/report/reportState", form);
			logger.info("通过一个5位的sid去查询是否生成了报告，供测试所有程序是否全部可以跑通使用"+jsonObject);
			//当原始日志成功走完所有流程时，此处调用接口可以查询到，将其置为1
			if("not null".equals(jsonObject.getString("state"))){
				flag = "1";
			}
		} catch (Exception e) {
			logger.error("调用接口失败");
		}
		try {
			response.getWriter().print(flag);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @方法功能　从文件中读取getPath的内容显示
	 */
	private String getPathFileName;
	@SuppressWarnings("null")
	public String report1() throws Exception {
		JSONObject jsonObject = new JSONObject();
		StringBuffer sb = new StringBuffer();
		// 创建一个读取流对象和文件相关联
		FileReader fr = new FileReader(getPathFileName);

		// 为了提高效率，加入了缓冲技术，将字符读取流对象作为参数传递给缓冲对象的构造函数。
		BufferedReader br = new BufferedReader(fr);
		String line = null;
		// readLine()读取一个文本行。包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		br.close();
		JSONArray jsonarray = new JSONArray();
		jsonarray.add(0,sb.toString());
		
		jsonObject.element("result", 0);
		jsonObject.element("object", jsonarray);
		
		if (isAjax(request)) {
			return renderJsonString(jsonObject.toString());
		}
		return null;
	}

	/**
	 * @方法功能 重新生成报告按钮调用的方法
	 * @return
	 */
	public String refresh() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		// 取得前台页面传来的值
		form.add("sid", sid.trim());

		JSONObject jsonObject = null;
		int result = 10;
		try {

			// 将查询框表单值传送到monitorServer
			// jsonObject =
			// proxy.postFormWithReturnJSONObject("/search/aboutID", form);
			jsonObject = proxy.postFormWithReturnJSONObject("/report/refresh", form);
			logger.info("点击按钮生成报告");

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
	}
	
	
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getGetPathFileName() {
		return getPathFileName;
	}

	public void setGetPathFileName(String getPathFileName) {
		this.getPathFileName = getPathFileName;
	}
}