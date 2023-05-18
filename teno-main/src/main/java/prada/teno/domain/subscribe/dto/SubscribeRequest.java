package prada.teno.domain.subscribe.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SubscribeRequest {
    private String userEmail;
    private boolean subscribe;
    private LocalDateTime startTime;
}
