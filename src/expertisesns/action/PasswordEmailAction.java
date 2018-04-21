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

import expertisesns.email.SMTPAuthenticatior;
import expertisesns.model.MemberDao;

public class PasswordEmailAction implements CommandAction
{
	MemberDao memberDao = null;
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		String transmitter = (String)request.getAttribute("sender");
		
		if(transmitter != null)
		{
			memberDao = MemberDao.getInstance();
			
			String newPassword = memberDao.RandomPassword(transmitter);
			
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
			
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject("Bacon Network 임시 비밀번호 발급 안내");
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("<h1>임시 비밀번호 발급</h1>");
			buffer.append("<br><br>");
			buffer.append("임시 비밀번호가 발급되었습니다.");
			buffer.append("<br>");
			buffer.append("임시 비밀번호로 로그인 하신 후 비밀번호 변경해주시길 바랍니다.");
			buffer.append("<br><br>");
			buffer.append("<h3>임시 비밀번호 : "+"<u>"+newPassword+"</u></h3>");
			
			System.out.println(buffer.toString());
			
			Address fromAddr = new InternetAddress("zubwark@naver.com");
			msg.setFrom(fromAddr);
			
			Address toAddr = new InternetAddress(transmitter);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(buffer.toString(), "text/html;charset=UTF-8");
			Transport.send(msg);
			
			request.setAttribute("check", "sendemail2");
			return "/sign/MainEntrance.jsp";
		}
		return "/sign/SearchPassword.jsp";
	}
}
