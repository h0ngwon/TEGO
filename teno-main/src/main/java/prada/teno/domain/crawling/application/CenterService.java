package prada.teno.domain.crawling.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.crawling.repository.CenterRepository;
import prada.teno.domain.crawling.dto.ResponseCenter;
import prada.teno.domain.crawling.dto.ResponseCenterDate;
import prada.teno.domain.crawling.dto.CrawlingCenter;
import prada.teno.domain.crawling.domain.Court;
import prada.teno.domain.crawling.domain.Time;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CenterService {

    private final CenterRepository centerRepository;

    @Transactional
    public void centerSave(
            List<CrawlingCenter> crawlingCenterList
    ) {
        centerRepository.saveAll(dtoToEntity(crawlingCenterList));
    }

    @Transactional(readOnly = true)
    public List<ResponseCenter> findCenters(){
        return entityToDto(centerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ResponseCenter> searchCenters(
            String centerName
    ) {
        List<Center> centers;
        try {
            centers = centerRepository.findByNameContains(centerName);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no center");
        }

        return entityToDto(centers);
    }

    @Transactional(readOnly = true)
    public ResponseCenter findCenter(
            long centerId
    ) {
        return ResponseCenter.fromEntity(
                centerRepository.findById(centerId).orElseThrow(() -> new NoSuchElementException("no center"))
        );
    }

    @Transactional
    public List<ResponseCenterDate> findCalendar(
            long id, String date
    ) {  // month '2022-04'
        List<ResponseCenterDate> responseCenterCalendar = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> countMap = new HashMap<>();

        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));
        LocalDateTime start = LocalDateTime.of(yearMonth.getYear(), yearMonth.getMonth(), 1, 0, 0, 0);
        LocalDateTime end = start.plusMonths(1);

        Center center = centerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("no center"));

        for (Court court : center.getCourts()) {
            List<Time> times = court.getTimes()
                    .stream()
                    .filter(
                            courtTime -> courtTime.getStartTime().isAfter(start) && courtTime.getStartTime().isBefore(end)
                    ).collect(Collectors.toList());

            for (Time time : times) {
                int day = time.getStartTime().getDayOfMonth();
                if (time.isReservable()) {
                    if (countMap.containsKey(day)) {
                        countMap.replace(day, new ArrayList(Arrays.asList(countMap.get(day).get(0) + 1, countMap.get(day).get(1))));
                    } else {
                        countMap.put(day, new ArrayList(Arrays.asList(1, 0)));
                    }
                }
                if (countMap.containsKey(day)) {
                    countMap.replace(day, new ArrayList(Arrays.asList(countMap.get(day).get(0), countMap.get(day).get(1) + 1)));
                } else {
                    countMap.put(day, new ArrayList(Arrays.asList(0, 1)));
                }
            }
        }

        for (Integer day : countMap.keySet()) {
            responseCenterCalendar.add(new ResponseCenterDate(day.toString(), countMap.get(day).get(0), countMap.get(day).get(1)));
        }

        return responseCenterCalendar;
    }

    public List<Center> dtoToEntity(List<CrawlingCenter> crawlingCenterList) {
        return crawlingCenterList.stream().map(CrawlingCenter::toEntity).collect(Collectors.toList());        // 특정 객제(CrawlingCenterDto) 인스턴스(crawlingCenterDto) 메서드(toEntity) 참조 -> crawlingCenterDto::toEntity
    }

    public List<ResponseCenter> entityToDto(List<Center> centerList) {
        return centerList.stream().map(ResponseCenter::fromEntity).collect(Collectors.toList());
    }
}
