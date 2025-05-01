package in.irise.soft.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender sender;

	public boolean send(
			String to,
			String cc[],
			String bcc[],
			String subject,
			String text,
			MultipartFile file
			) 
	{
		boolean issent=false;
		try {
			//create one empty message
			MimeMessage message = sender.createMimeMessage();

			//fill details
			MimeMessageHelper helper = new MimeMessageHelper(
					message, file!=null?true:false);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			
			if(cc!=null)
				helper.setCc(cc);
			
			if(bcc!=null)
				helper.setBcc(bcc);
			
			if(file!=null)
				helper.addAttachment(file.getOriginalFilename(), file);

			//send message
			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return issent;
	}
	
	public boolean send(
			String to,
			String subject,
			String text) 
	{
		return send(to, null, null, subject, text, null);
	}
}
