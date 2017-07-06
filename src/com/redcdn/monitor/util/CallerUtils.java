/**
 * 
 */
package com.redcdn.monitor.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.redcdn.monitor.action.BaseAction;

/**
 * @Author 刘艳伟
 * @date 2014-2-25下午8:00:52
 * @类功能　话务工具类
 */
public class CallerUtils extends BaseAction {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		String json = "{'result':0,'data':{'search':[{'sid':'53739662_589072861_77775933_77776424','reportorId':77775933,'startTime':'1970-01-01 08:00:00','endTime':'1970-01-01 08:00:00','duration':10,'eventValue':0,'result':'host','recvVideo':75680,'recvAudio':59708,'recvFec':15126,'recvVideoLost':82,'recvAudioLost':84,'recvFecLost':0,'sendtoFailure':1,'version':'sip_sdk=Router V2.3.0 Svn:7309:7828  01/15/14 12:50:16 voip_sdk=v2.1.8.2 svn:1487:7265 12/02/13 10:34:06'},{'sid':'53739662_589072862_77775934_77776425','reportorId':77775934,'startTime':'1970-01-01 08:00:00','endTime':'1970-01-01 08:00:00','duration':11,'eventValue':0,'result':'host','recvVideo':75680,'recvAudio':59708,'recvFec':15126,'recvVideoLost':82,'recvAudioLost':84,'recvFecLost':0,'sendtoFailure':0,'version':'sip_sdk=Router V2.3.1 Svn:7309:7828  02/15/14 12:50:16 voip_sdk=v2.1.8.2 svn:1487:7265 12/02/13 10:34:06'}],'totalPage':1,'currPage':1}}";
		JSONObject jsonObject = new JSONObject();
		// jsonToMap(jsonObject.fromObject(json));
	}

	/**
	 * @方法功能 将查询出的结果以sid为key放入Map中
	 * @param jsonObject
	 */
	public void jsonToMap(JSONObject jsonObject) {
		JSONArray jsonArray;
		jsonArray = jsonObject.getJSONObject("data").getJSONArray("search");

		for (int i = 0; i < jsonArray.size(); i++) {
			String sid = ((JSONObject) jsonArray.get(i)).getString("sid");
			map.put(sid, (Object)jsonArray.get(i));
		}
		setSession(map);
	}

	/**
	 * @方法功能 通过会话id取出相应的某条记录　
	 * @param sid
	 */
	public JSONObject mapToJsonFromKey(String sid) {
		return JSONObject.fromObject(map.get(sid));
	}

	public static void sop(Object object) {
		System.err.println(object);
	}

}
