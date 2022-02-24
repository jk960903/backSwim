package com.example.backswim.component;

import com.example.backswim.member.entity.EmailEntity;
import com.example.backswim.member.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MailComponents {
    private final JavaMailSender javaMailSender;

    private final EmailRepository emailRepository;


    public boolean sendMail(int id){

        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");

                Optional<EmailEntity> emailOption = emailRepository.findById(id);

                if(emailOption.isPresent()){
                    EmailEntity email = emailOption.get();
                    mimeMessageHelper.setTo(email.getUserEmail());
                    mimeMessageHelper.setSubject(email.getEmailTitle());
                    mimeMessageHelper.setText(email.getEmailContent(),true);
                }

            }
        };
        try {
            javaMailSender.send(msg);
            result = true;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

}

