package ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public  void sendMail(String to, String subject, String html, List<MultipartFile> files){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage(); // tao 1 đối tượng đại diện cho mail
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.addCc("hung18061999hung@gmail.com");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(html,true);

            // up load file
            for (MultipartFile m: files
                 ) {
                ByteArrayResource bytes = new ByteArrayResource(m.getBytes());
                mimeMessageHelper.addAttachment(m.getOriginalFilename(),bytes);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }
}
