package prada.teno.domain.crawling.converter;

import prada.teno.domain.crawling.domain.Court;
import prada.teno.domain.crawling.domain.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TimeInitializer {

    private final String url;
    private final Queue<LocalDateTime> reservableTimes;
    private final int startTime;
    private final int endTime;
    private final int playTime;
    private Court court;

    public TimeInitializer(String url, List<LocalDateTime> reservableTimes, int startTime, int endTime, int playTime, Court court) {
        this.url = url;
        this.reservableTimes = new LinkedList<>(reservableTimes);
        this.startTime = startTime;
        this.endTime = endTime;
        this.playTime = playTime;
        this.court = court;
    }

    public List<Time> initialize() {
        LocalDate basicDate = LocalDate.now();

        List<Time> times = new ArrayList<>(generateTimes(basicDate.getYear(), basicDate.getMonthValue()));

        basicDate = basicDate.plusMonths(1);
        times.addAll(generateTimes(basicDate.getYear(), basicDate.getMonthValue()));

        return times;
    }

    private List<Time> generateTimes(int year, int month) {
        List<Time> times = new ArrayList<>();

        for (int day = 1; day <= YearMonth.of(year, month).lengthOfMonth(); day++) {
            for (int i = 0; i < (endTime - startTime) / playTime; i++) {
                LocalDateTime time = LocalDateTime.of(year, month, day, startTime + playTime * i, 0 , 0);

                if (time == reservableTimes.peek()) {
                    LocalDateTime reservableTime = reservableTimes.poll();

                    times.add(
                            Time.builder()
                                    .court(court)
                                    .url(url)
                                    .startTime(reservableTime)
                                    .endTime(reservableTime.plusHours(playTime))
                                    .reservable(true)
                                    .build());
                } else {
                    times.add(
                            Time.builder()
                                    .court(court)
                                    .url(url)
                                    .startTime(time)
                                    .endTime(time.plusHours(playTime))
                                    .reservable(false)
                                    .build());
                }
            }
        }

        return times;
    }
}
