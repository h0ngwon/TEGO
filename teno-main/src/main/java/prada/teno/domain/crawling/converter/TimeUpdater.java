package prada.teno.domain.crawling.converter;

import prada.teno.domain.crawling.domain.Court;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TimeUpdater {

    private final Queue<LocalDateTime> reservableTimes;
    private Court court;

    public TimeUpdater(List<LocalDateTime> reservableTimes, Court court) {
        this.reservableTimes = new LinkedList<>(reservableTimes);
        this.court = court;
    }

    public void update() {
        while (!reservableTimes.isEmpty()) {
            LocalDateTime reservableTime = reservableTimes.poll();

            court.getTimes().forEach(time -> {
                if (time.getStartTime().equals(reservableTime)) {
                    time.setReservable(true);
                }
            });
        }
    }
}
