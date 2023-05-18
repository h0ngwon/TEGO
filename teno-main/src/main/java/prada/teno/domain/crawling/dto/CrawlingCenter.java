package prada.teno.domain.crawling.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import prada.teno.domain.crawling.domain.Center;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class CrawlingCenter {

    private String name;

    private String address;

    private String district;

    private LocalTime centerStartTime;

    private LocalTime centerEndTime;

    private LocalTime playTime;

    public static Center toEntity(CrawlingCenter crawlingCenter) {
        return Center.builder()
                .name(crawlingCenter.getName())
                .address(crawlingCenter.getAddress())
                .district(crawlingCenter.getDistrict())
                .centerStartTime(crawlingCenter.getCenterStartTime())
                .centerEndTime(crawlingCenter.getCenterEndTime())
                .playTime(crawlingCenter.getPlayTime())
                .build();
    }
}
