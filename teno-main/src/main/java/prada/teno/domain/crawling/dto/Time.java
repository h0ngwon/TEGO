package prada.teno.domain.crawling.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class Time {

    @Schema(description = "시간", example = "14:00")
    private String time;

    @Schema(description = "예약가능여부", example = "True")
    private boolean reservable;

    @Schema(description = "예약링크")
    private String url;

    @Builder
    public Time(String time, boolean reservable, String url) {
        this.time = time;
        this.reservable = reservable;
        this.url = url;
    }

    public Time fromEntity(prada.teno.domain.crawling.domain.Time time) {
        return Time.builder()
                .time(time.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .reservable(time.isReservable())
                .url(time.getUrl())
                .build();
    }
}
