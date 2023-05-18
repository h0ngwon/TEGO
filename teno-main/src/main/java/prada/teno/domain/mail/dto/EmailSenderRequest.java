package prada.teno.domain.mail.dto;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailSenderRequest {
    public static final String fromEmail = "tennisgo.info@gmail.com"; //보내는사람
    private final String to; //받는사람
    private final String subject; //제목
    private final String content; //본문

    @Builder
    public EmailSenderRequest(final String to, final String subject, final String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }


    public SendEmailRequest toSendRequestDto() { //메일 보낼때 필요한 객체가 담겨져 있음
        final Destination destination = new Destination().withToAddresses(this.to);
        final Message message = new Message().withSubject(createContent(this.subject))
                .withBody(new Body().withHtml(createContent(this.content)));

        return new SendEmailRequest() //메일 보내기
                .withSource(fromEmail)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(final String txt) { //봄누 만들기
        return new Content()
                .withCharset("UTF-8")
                .withData(txt);
    }
}
