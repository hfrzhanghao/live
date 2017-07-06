package com.redcdn.monitor.action;

import net.sf.json.JSONObject;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.redcdn.monitor.common.AboutTime;

/**
 * 非话务日志查询
 * 
 * @author 刘艳伟
 * @path monitor2.0/com.redcdn.monitor.action/NoCallAction.java
 */
public class NoCallAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String isCallCount;// 是否需要返回每两个时间点之间的通话个数，默认是false(不返回)
	private String sdkmac;// 视讯号登录设备的MAC
	private String uid;// 视讯号
	private String type;// 类型，指明是SDK概要信息几部分中哪一部分

	/**
	 * 查询出sdk设备ID和视讯号的列表
	 * 
	 * @author 刘艳伟
	 * @date 2015-7-21上午11:09:03
	 */
	public String sdkInfoSearch() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", AboutTime.toLong(startTime) + "");
		form.add("endTime", AboutTime.toLong(endTime) + "");
		form.add("sdkmac", sdkmac + "");
		form.add("uid", uid + "");

		JSONObject jsonObject = null;
		int result = 10;
		try {

			jsonObject = proxy.postFormWithReturnJSONObject("/sdk/sdkInfoSearch", form);
			logger.info("SDK非话务日志查询");

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

	/**
	 * 某个时间点之前，某个uid到对应mac从启动到现在的所有登录点集合，以及每两个登录点之间的通话个数
	 * 
	 * @param endTime
	 * @param uid
	 *            如果这个参数为空，则返回在如下sdkmac中所有的登录信息
	 * @param sdkmac
	 *            如果这个参数为空，会根据uid,在登录表中查找到最近一次登录的mac
	 * @param isCallCount
	 *            是否需要返回每两个时间点之间的通话个数，默认是false(不返回)
	 * @author 刘艳伟
	 * @date 2015-7-21上午11:09:03
	 */
	public String lastStartToNowLoginPoint() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("endTime", AboutTime.toLongSSS(endTime) + "");
		form.add("uid", uid + "");
		form.add("sdkmac", sdkmac + "");
		form.add("isCallCount", isCallCount + "");

		JSONObject jsonObject = null;
		int result = 10;
		try {

			jsonObject = proxy.postFormWithReturnJSONObject("/sdk/lastStartToNowLoginPoint", form);
			logger.info("查询某个时间点之前，某个uid到对应mac从启动到现在的所有登录点集合，以及每两个登录点之间的通话个数");
			logger.info("SDK非话务日志查询返回的数据：" + jsonObject.toString());

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

	/**
	 * 获取警告信息
	 * 
	 * @author 刘艳伟
	 * @date 2015-7-21下午3:07:57
	 */
	public String warningInfo() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", AboutTime.toLong(startTime) + "");
		form.add("endTime", AboutTime.toLong(endTime) + "");
		form.add("sdkmac", sdkmac + "");

		JSONObject jsonObject = null;
		int result = 10;
		try {

			jsonObject = proxy.postFormWithReturnJSONObject("/sdk/warningInfo", form);
			logger.info("获取警告信息");
			logger.info("SDK非话务日志查询返回的数据：" + jsonObject.toString());

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

	/**
	 * 获取SDK不同模块的概要信息
	 * 
	 * @author 刘艳伟
	 * @date 2015-7-21下午3:07:57
	 */
	public String sdkInfoStatistics() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", AboutTime.toLong(startTime) + "");
		form.add("endTime", AboutTime.toLong(endTime) + "");
		form.add("sdkmac", sdkmac + "");

		JSONObject jsonObject = null;
		int result = 10;
		try {

			jsonObject = proxy.postFormWithReturnJSONObject("/sdk/sdkInfoStatistics", form);
			logger.info("获取SDK四部分的概念信息");
			logger.info("SDK非话务日志查询返回的数据：" + jsonObject.toString());

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

	/**
	 * 获取SDK某模块的客户端的日志详情
	 * 
	 * @author 刘艳伟
	 * @date 2015-7-21下午3:07:57
	 */
	public String sdkInfoList() {
		MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

		form.add("startTime", AboutTime.toLong(startTime) + "");
		form.add("endTime", AboutTime.toLong(endTime) + "");
		form.add("sdkmac", sdkmac + "");
		form.add("type", type + "");

		JSONObject jsonObject = null;
		int result = 10;
		try {

			jsonObject = proxy.postFormWithReturnJSONObject("/sdk/sdkInfoList", form);
			logger.info("获取SDK四部分的概念信息");
			logger.info("SDK非话务日志查询返回的数据：" + jsonObject.toString());

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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getIsCallCount() {
		return isCallCount;
	}

	public void setIsCallCount(String isCallCount) {
		this.isCallCount = isCallCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
