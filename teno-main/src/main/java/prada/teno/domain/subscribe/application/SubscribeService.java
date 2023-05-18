package prada.teno.domain.subscribe.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prada.teno.domain.crawling.domain.Time;
import prada.teno.domain.crawling.repository.TimeRepository;
import prada.teno.domain.subscribe.dto.ResponseSubscribe;
import prada.teno.domain.subscribe.domain.Subscribe;
import prada.teno.domain.subscribe.dto.SubscribeData;
import prada.teno.domain.subscribe.repository.SubscribeRepository;
import prada.teno.domain.subscribe.dto.SubscribeRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final TimeRepository timeRepository;

    public SubscribeService(SubscribeRepository subscribeRepository, TimeRepository timeRepository) {
        this.subscribeRepository = subscribeRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public boolean subscribeCourt(
            SubscribeRequest subscribeRequest, Long timeId
    ) {

        Time courtTime = timeRepository.findById(timeId).orElseThrow(() -> new NoSuchElementException("no time"));
        Optional<Subscribe> restock = Optional.ofNullable(subscribeRepository.findByTimeIdAndUserEmail(timeId, subscribeRequest.getUserEmail()));

        List<Subscribe> subscribe = courtTime.getSubscribeList();

        SubscribeData subscribeData = new SubscribeData();

        subscribeData.setUserEmail(subscribeRequest.getUserEmail());
        subscribeData.setStartTime(subscribeRequest.getStartTime());
        subscribeData.setCourtName(courtTime.getCourt().getName());
        subscribeData.setCenterName(courtTime.getCourt().getCenter().getName());

        subscribe.add(
                new Subscribe(
                        courtTime,
                        subscribeRequest.getUserEmail(),
                        subscribeRequest.isSubscribe(),
                        subscribeRequest.getStartTime(),
                        subscribeData
                )
        );

        timeRepository.save(courtTime);
       /* Restock saveRestock = new Restock();
        saveRestock.setUserEmail(restockResponseDto.getUserEmail());
        saveRestock.setSubscribe(restockResponseDto.isSubscribe());
        if(courtTime.isPresent()) {
            saveRestock.setCourtTime(courtTime.get());
        }
        restockRepository.save(saveRestock);*/

        //구독 ID 반환
        return true;
    }

    @Transactional
    public void unsubscribeCourt(
            SubscribeRequest subscribeRequest, Long subscribeId
    ) {
        Optional<Subscribe> restock = Optional.ofNullable(
                subscribeRepository
                        .findByIdAndUserEmail(subscribeId, subscribeRequest.getUserEmail()));

        restock.ifPresent(r -> {
                    r.setDeleted(true);
                }
        );
          /* if(restock.isPresent()){
               restock.get().setDeleted(true);
            restockRepository.save(restock.get());
        }*/

        /*Restock deleteRestock = restockRepository.findByCourtTimeId(courtTimeId);
        deleteRestock.setDeleted(true);
        restockRepository.save(deleteRestock);*/
    }

    //Entity -> DTO
    public List<ResponseSubscribe> subscribeList(
            String userId
    ) {
        List<Subscribe> subscribes;
        try {
            subscribes = subscribeRepository.findAllByUserEmailAndSubscribe(userId, true);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no subscribe");
        }
        return subscribes.stream()
                .map(subscribe -> new ResponseSubscribe(subscribe.getId(), subscribe.getSubscribeData()))
                .collect(Collectors.toList());
    }
}
