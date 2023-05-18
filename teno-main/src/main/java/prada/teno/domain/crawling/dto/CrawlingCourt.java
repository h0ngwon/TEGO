package prada.teno.domain.crawling.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CrawlingCourt {
    private String url;
    private String centerName;
    private String courtName;
    private List<LocalDateTime> possibleTimeTable;
}
