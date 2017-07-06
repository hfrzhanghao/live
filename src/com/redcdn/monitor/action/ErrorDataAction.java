package com.redcdn.monitor.action;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.redcdn.monitor.common.AboutTime;
import com.redcdn.monitor.common.HttpPostMethod;

public class ErrorDataAction extends BaseAction {
	private static final long serialVersionUID = 2L;

	private String sid;// 会话id

	private int pageSize = 25;

	private int currPage;// 当前页
	private String startTime;// 开始时间
	private String endTime;// 结束时间

	/**
	 * 显示某一条话务的信息，点击“错误数据”查出数据后点击“详情”
	 * @author 刘艳伟 
	 * @date 2014-8-4上午10:59:18
	 */
	public void errordata() {
		response.setContentType("text/html;charset=UTF-8");
		try {
			String urls = "/errordata/callinfo";
			HttpClient http = new HttpClient();
			HttpPostMethod get = proxy.httpPostMothed(urls);
			get.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
			// 添加头信息告诉服务端可以对Response进行GZip压缩
			get.setRequestHeader("Accept-Encoding", "gzip");
			get.setParameter("sid", sid);
			
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
				logger.error("页面无法访问");
				e.printStackTrace();
			} finally {
				get.releaseConnection();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 在查询页面点击“错误数据”按钮，查询出所有错误数据
	 * @author 刘艳伟 
	 * @date 2014-8-4上午11:00:00
	 */
	public String errorID() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();
	
		form.add("startTime", AboutTime.toLong(startTime) + "");
		form.add("endTime", AboutTime.toLong(endTime) + "");
		form.add("pageSize", pageSize + "");
		form.add("currPage", currPage + "");

		JSONObject jsonObject = null;
		int result = 10;
		try {
			jsonObject = proxy.postFormWithReturnJSONObject("/errordata/errorID", form);
			logger.info("查询全部");

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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}