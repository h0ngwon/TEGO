package prada.teno.domain.subscribe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SubscribeData {
    private String userEmail;
    private LocalDateTime startTime;
    private String CenterName;
    private String CourtName;
}
