package pl.bykowski.springsecuritysimplefactorauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
// klasa pozwala mi na powłanie instancji, wywołać metodę sendMail
@Service
public class MailService {


    private JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
// Metoda sendMail
    public void sendMail(String to, // do kogo wysyłam
                         String subject, // temat maila
                        String text, // treść maila
                         boolean isHtmlContent) throws MessagingException { // czy content jest Html-owy
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);  // zostanie wysłana poprzez wykorzytanie wszystkich wartości w pliku configuracyjnym application properties
    }
}
