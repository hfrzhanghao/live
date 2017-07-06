package com.redcdn.monitor.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.redcdn.monitor.common.AboutTime;
import com.redcdn.monitor.common.CommonConstants;
import com.redcdn.monitor.common.HttpPostMethod;
import com.redcdn.monitor.service.impl.ConfigServiceImpl;
import com.redcdn.monitor.util.Mail;

/**
 * 
 * @Author Cyril
 * @date 2016-4-25下午
 * @类功能　count.jsp的各个查询所调用的方法
 */
public class SearchAction extends BaseAction {
	private static final long serialVersionUID = 2L;

	private int pageSize = 25;//一页显示多少条记录

	private int currPage;// 当前页
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String playType;
	
	private String ip;
	private String url;
	
	private String lockCount;
	private String lockCountSelect;
	private String firstPic;
	private String picDurationSelect;
	private String result;
	
	/**
	 * 转化成毫秒的开始时间
	 */
	Long starTimestamp;
	/**
	 * 转化成毫秒的结束时间
	 */
	Long endTimestamp;

	/**
	 * 开始时间与结束时间的组合参数
	 */
	String data;

	/**
	 * @author Cyril
	 * @date 2016-4-25下午
	 */
	public String test() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", startTime + "");
		form.add("endTime", endTime + "");
		form.add("IP", ip + "");
		form.add("url", url + "");

		JSONObject jsonObject = null;
		try {
			// 将查询框表单值传送到monitorServer
			//if(playType.equals("1")){
			jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_LOG, form);
			/*}else{
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.DEMAND_ANALYSIS_URL_LOG, form);
			}*/
			//jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_LOG, form);
			System.out.println(jsonObject.toString());
			logger.info("查询ip的返回数据："+jsonObject.toString());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (isAjax(request)) {
			return renderJsonString(jsonObject.toString());
		}
		return null;
	}
	
	public String lock() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", startTime + "");
		form.add("endTime", endTime + "");
		form.add("lockCount", lockCount + "");
		form.add("lockCountSelect", lockCountSelect + "");
		form.add("url", url + "");
		form.add("picDuration", -1 + "");

		JSONObject jsonObject = null;
		try {

			// 将查询框表单值传送到monitorServer
			//if(playType.equals("1")){
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_IP, form);
			/*}else{
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.DEMAND_ANALYSIS_URL_IP, form);
			}*/
			//jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_IP, form);
			System.out.println(jsonObject.toString());
			logger.info("查询ip的返回数据："+jsonObject.toString());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (isAjax(request)) {
			return renderJsonString(jsonObject.toString());
		}
		return null;
	}
	
	public String firstPic() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", startTime + "");
		form.add("endTime", endTime + "");
		form.add("picDuration", firstPic + "");
		form.add("picDurationSelect", picDurationSelect + "");
		form.add("url", url + "");
		form.add("lockCount", -1 + "");

		JSONObject jsonObject = null;
		try {

			// 将查询框表单值传送到monitorServer
			//if(playType.equals("1")){
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_IP, form);
			/*}else{
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.DEMAND_ANALYSIS_URL_IP, form);
			}*/
			//jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_IP, form);
			System.out.println(jsonObject.toString());
			logger.info("查询ip的返回数据："+jsonObject.toString());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (isAjax(request)) {
			return renderJsonString(jsonObject.toString());
		}
		return null;
	}
	
	public String result() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", startTime + "");
		form.add("endTime", endTime + "");
		form.add("result", result + "");
		form.add("url", url + "");
		form.add("picDuration", -1 + "");
		form.add("lockCount", -1 + "");

		JSONObject jsonObject = null;
		try {

			// 将查询框表单值传送到monitorServer
			//if(playType.equals("1")){
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_IP, form);
			/*}else{
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.DEMAND_ANALYSIS_URL_IP, form);
			}*/
			System.out.println(jsonObject.toString());
			logger.info("查询ip的返回数据："+jsonObject.toString());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (isAjax(request)) {
			return renderJsonString(jsonObject.toString());
		}
		return null;
	}
	
	public String high() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("ishigh", "true");
		form.add("startTime", startTime + "");
		form.add("endTime", endTime + "");
		form.add("url", url + "");
		form.add("lockCount", lockCount + "");
		form.add("lockCountSelect", lockCountSelect + "");
		form.add("picDuration", firstPic + "");
		form.add("picDurationSelect", picDurationSelect + "");
		form.add("result", result + "");
		
		JSONObject jsonObject = null;
		try {

			// 将查询框表单值传送到monitorServer
			//if(playType.equals("1")){
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.LIVE_ANALYSIS_URL_IP, form);
			/*}else{
				jsonObject = proxy.postFormWithReturnJSONObject1(CommonConstants.DEMAND_ANALYSIS_URL_IP, form);
			}*/
			System.out.println(jsonObject.toString());
			logger.info("查询ip的返回数据："+jsonObject.toString());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (isAjax(request)) {
			return renderJsonString(jsonObject.toString());
		}
		return null;
	}
	
	/**
	 * 处理开始时间、结束时间，设置参数
	 * 
	 * @author
	 * @date 
	 */
	public void toSetConfig() {

		starTimestamp = StrTimeToLong(startTime);

		endTimestamp = StrTimeToLong(endTime);

		data = "startTime=" + starTimestamp + "&endTime=" + endTimestamp;

	}

	/**
	 * 将时间转化为毫秒值
	 * 
	 * @author 刘艳伟
	 */
	public long StrTimeToLong(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d = df.parse(time);
			return d.getTime();
		} catch (ParseException e) {
			logger.error("时间格式错误：yyyy-MM-dd HH:mm:ss：" + time);
		}
		return 0;
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
	
	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getStarTimestamp() {
		return starTimestamp;
	}

	public void setStarTimestamp(Long starTimestamp) {
		this.starTimestamp = starTimestamp;
	}

	public Long getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(Long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLockCount() {
		return lockCount;
	}

	public void setLockCount(String lockCount) {
		this.lockCount = lockCount;
	}

	public String getFirstPic() {
		return firstPic;
	}

	public void setFirstPic(String firstPic) {
		this.firstPic = firstPic;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLockCountSelect() {
		return lockCountSelect;
	}

	public void setLockCountSelect(String lockCountSelect) {
		this.lockCountSelect = lockCountSelect;
	}

	public String getPicDurationSelect() {
		return picDurationSelect;
	}

	public void setPicDurationSelect(String picDurationSelect) {
		this.picDurationSelect = picDurationSelect;
	}

}