package expertisesns.action;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expertisesns.data.DataInfo;
import expertisesns.email.SMTPAuthenticatior;

public class EmailAction implements CommandAction
{
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		//보낼 사람 이메일
		String transmitter = (String)request.getAttribute("sender");
		
		//정보를 담을 객체
		Properties p = new Properties();
		p.put("mail.smtp.host","smtp.naver.com");
		p.put("mail.smtp.port","465");
		p.put("mail.smtp.starttls.enable","true");
		p.put("mail.smtp.auth","true");
		p.put("mail.smtp.debug","true");
		p.put("mail.smtp.socketFactory.port","465");
		p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback","false");
		
		Authenticator auth = new SMTPAuthenticatior();
		Session ses = Session.getInstance(p, auth);
		ses.setDebug(true);
		
		//매일의 내용을 담을 객체 
		MimeMessage msg = new MimeMessage(ses);
		msg.setSubject("Bacon Network 회원 가입을 축하드립니다.");
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<h1>Bacon Network 가입을 축하드립니다.</h1>"+"<br>");
		buffer.append("아래 버튼을 눌러 회원가입을 마치세요."+"<br>");
		buffer.append("<button>");
		buffer.append("<a href=\""+DataInfo.localhost_path+"\"");
		buffer.append(request.getContextPath());
		buffer.append("/baconnetwork/emailAuthentication.do?email=");
		buffer.append(transmitter);
		buffer.append("\">");
		buffer.append("이메일 인증 확인");
		buffer.append("</a>");
		buffer.append("</button>");
		
		System.out.println(buffer.toString());
		
		Address fromAddr = new InternetAddress("zubwark@naver.com");
		msg.setFrom(fromAddr);
		
		Address toAddr = new InternetAddress(transmitter);
		msg.addRecipient(Message.RecipientType.TO, toAddr);
		msg.setContent(buffer.toString(), "text/html;charset=UTF-8");
		Transport.send(msg);
		
		request.setAttribute("check", "sendemail");
		return "/sign/MainEntrance.jsp";
	}
}
