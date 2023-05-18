package prada.teno.domain.crawling.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prada.teno.domain.crawling.converter.CourtConverter;
import prada.teno.domain.crawling.dto.CrawlingCourt;
import prada.teno.domain.crawling.dto.Time;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.crawling.domain.Court;
import prada.teno.domain.crawling.repository.CenterRepository;
import prada.teno.domain.crawling.domain.CenterType;
import prada.teno.domain.crawling.dto.ResponseCourt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CourtService {

    private final CenterRepository centerRepository;
    private final List<CourtConverter> courtConverter;

    public CourtService(CenterRepository centerRepository, List<CourtConverter> courtConverter) {
        this.centerRepository = centerRepository;
        this.courtConverter = courtConverter;
    }

    @Transactional
    public void save(List<CrawlingCourt> inputs, String centerName, CenterType centerType) {
        courtConverter.stream()
                .filter(converter -> converter.isSatisfied(centerType))
                .forEach(converter -> converter.convert(inputs, centerName));
    }

    @Transactional
    public List<ResponseCourt> findCourts(
            long centerId, String date
    ) {
        List<ResponseCourt> responseCourtList = new ArrayList<>();

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        LocalDateTime startTime = LocalDateTime.of(localDate, LocalTime.of(0, 0, 0));
        LocalDateTime endTime = startTime.plusDays(1);

        Center center = centerRepository.findById(centerId).orElseThrow(() -> new NoSuchElementException("no center"));

        for (Court court : center.getCourts()) {
            List<Time> timeList = new ArrayList<>();

            List<prada.teno.domain.crawling.domain.Time> times = court.getTimes()
                    .stream()
                    .filter(courtTime -> courtTime.getStartTime().isAfter(startTime) && courtTime.getStartTime().isBefore(endTime))
                    .collect(Collectors.toList());

            for (prada.teno.domain.crawling.domain.Time time : times) {      // getCorutTimes는 LazyLoading -> Transaction
                timeList.add(Time.builder()
                        .time(time.getStartTime().toString())
                        .reservable(time.isReservable())
                        .url(time.getUrl())
                        .build());
            }
            responseCourtList.add(new ResponseCourt(court.getName(), timeList));
        }

        return responseCourtList;
    }
}
