package com.redcdn.monitor.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * 发送邮件
 * @author 刘艳伟
 * @path monitor2.0/com.redcdn.monitor.util/Mail.java
 * @date 2015-11-17上午10:48:35
 */
public class Mail {
	public static void main(String[] args) {
		Mail.sendMail("日志查询异常－", "标识查询", "3398848818_2145601857_90009387_50015666_505839413890009387_50015666");
	}
	
	public static void sendMail(String title,String subTitle,String args){
		HtmlEmail email = new HtmlEmail();
		email.setCharset("UTF-8");// 必须放在前面，否则乱码
		//email.setHostName("smtp.163.com");// 设置使用发电子邮件的邮件服务器
		email.setHostName("mail.butel.com");// 设置使用发电子邮件的邮件服务器
		try {
			email.setFrom("liuyw@butel.com");//发送者
			email.setAuthentication("liuyw@butel.com", "461400@163.com");//验证用户名、密码
			email.addTo("405539143@qq.com"); //接收者
			email.addTo("liuyw@butel.com"); //接收者
			email.setSubject(title+subTitle);
			email.setHtmlMsg("<html><meta http-equiv=Content-Type content=text/html; charset=gb2312>"
							+ "<div >尊敬的刘艳伟：</div>"
							+ "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好。话务查询出现问题："
							+ "参数为</div>"
							+ args
							+ "<div>如有问题请使用如下方式与我们取得联系：</div>"
							+ "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;E-mail：liuyw@butel.com</div>"
							+ "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客服专线：159 0140 3909</div>"
							+ "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地址：北京市海淀区阜成路73号世纪裕惠大厦B座九层(100142)</div></html>");
			email.send();

		} catch (EmailException ex) {
			ex.printStackTrace();
		}
	}
}
