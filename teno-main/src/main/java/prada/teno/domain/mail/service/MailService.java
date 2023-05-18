package prada.teno.domain.mail.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import prada.teno.domain.mail.dto.EmailSenderRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    //이메일 전송
    public void send(final String subject, final String content, final String receiver) {
        final EmailSenderRequest emailSenderRequest = EmailSenderRequest.builder()
                .to(receiver)
                .subject(subject)
                .content(content)
                .build();
        final SendEmailResult sendEmailResult = amazonSimpleEmailService
                .sendEmail(emailSenderRequest.toSendRequestDto());
        sendingResultMustSuccess(sendEmailResult);
    }

    private void sendingResultMustSuccess(final SendEmailResult sendEmailResult) {
        if (sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            log.error("{}", sendEmailResult.getSdkResponseMetadata().toString());
        }
    }
}
