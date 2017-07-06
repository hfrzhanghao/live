package com.redcdn.monitor.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

public final class CommonConstants {
	public static final String USER_LOGIN_COOKIE = "userlogincookie";
	public static final String LIVE_ANALYSIS_URL;
	public static final String LIVE_ANALYSIS_URL_FINAL;
	public static final String LIVE_ANALYSIS_URL_LOG;
	public static final String LIVE_ANALYSIS_URL_IP;

	static {
		LIVE_ANALYSIS_URL = ResourceBundle.getBundle("config").getString("liveAnalysisUrl");
		if (LIVE_ANALYSIS_URL.endsWith("/")) {
			LIVE_ANALYSIS_URL_FINAL = LIVE_ANALYSIS_URL.substring(0, LIVE_ANALYSIS_URL.length() - 1);
		} else {
			LIVE_ANALYSIS_URL_FINAL = LIVE_ANALYSIS_URL;
		}
		LIVE_ANALYSIS_URL_LOG = LIVE_ANALYSIS_URL_FINAL + "/all/logCheck";
		LIVE_ANALYSIS_URL_IP = LIVE_ANALYSIS_URL_FINAL + "/all/ipCheck";
	}

	public static String roomjson() {
		File file = new File(CommonConstants.class.getClassLoader().getResource("").getFile() + "json.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buf = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while ((fis.read(buf)) != -1) {
				sb.append(new String(buf));
				buf = new byte[1024];
			}

			return new String(sb.toString().getBytes("GBK"), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
