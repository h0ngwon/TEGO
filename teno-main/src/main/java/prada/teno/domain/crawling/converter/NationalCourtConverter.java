package prada.teno.domain.crawling.converter;

import org.springframework.stereotype.Component;
import prada.teno.domain.crawling.dto.CrawlingCourt;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.crawling.repository.CenterRepository;
import prada.teno.domain.crawling.domain.CenterType;
import prada.teno.domain.crawling.domain.Court;
import prada.teno.domain.crawling.domain.Time;
import prada.teno.global.exception.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NationalCourtConverter implements CourtConverter {

    private final CenterRepository centerRepository;

    public NationalCourtConverter(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    @Override
    public boolean isSatisfied(final CenterType centerType) {
        return centerType == CenterType.NATIONAL;
    }

    @Override
    public Center convert(final List<CrawlingCourt> inputs, String centerName) {
        LocalDateTime basicDateTime = getBasicDate();
        Center center = centerRepository.findByNameAndStartTimeBetween(centerName, basicDateTime, basicDateTime.plusMonths(2))
                .orElseThrow(() -> new DataNotFoundException("해당 이름을 가진 센터를 찾을 수 없습니다. centerName : " + centerName));

        List<CrawlingCourt> creatable = inputs.stream().filter((input) -> !center.containCourt(input.getCourtName())).collect(Collectors.toList());
        List<CrawlingCourt> updatable = inputs.stream().filter((input) -> center.containCourt(input.getCourtName())).collect(Collectors.toList());

        center.addCourts(createCourt(creatable, center));
        updateCourt(updatable, center);

        return centerRepository.save(center);
    }

    private void updateCourt(final List<CrawlingCourt> inputs, Center center) {
        inputs.forEach(input -> {
            Court court = center.findCourt(input.getCourtName())
                    .orElseThrow(() -> new DataNotFoundException("해당 이름을 가진 코트를 찾을 수 없습니다. centerName : " + center.getName() + ", courtName : " + input.getCourtName()));
            new TimeUpdater(input.getPossibleTimeTable(), court).update();
        });
    }

    private List<Court> createCourt(final List<CrawlingCourt> inputs, Center center) {
        return inputs.stream().map((input) -> {
            Court court = Court.builder()
                    .center(center)
                    .name(input.getCourtName())
                    .build();
            court.setTimes(createTimes(input, center, court));

            return court;
        }).collect(Collectors.toList());
    }

    private List<Time> createTimes(CrawlingCourt input, Center center, Court court) {
        int startTime = center.getCenterStartTime().getHour();
        int endTime = center.getCenterEndTime().getHour();
        int playTime = center.getPlayTime().getHour();

        return new TimeInitializer(input.getUrl(), input.getPossibleTimeTable(), startTime, endTime, playTime, court).initialize();
    }

    private LocalDateTime getBasicDate() {
        LocalDateTime today = LocalDateTime.now();
        return LocalDateTime.of(today.getYear(), today.getDayOfMonth(), 1, 0, 0);
    }
}
