package prada.teno.domain.subscribe.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prada.teno.domain.subscribe.dto.ResponseSubscribe;
import prada.teno.domain.subscribe.dto.SubscribeRequest;
import prada.teno.domain.subscribe.application.SubscribeService;

import java.util.List;

@Tag(name = "Subscribe", description = "원하는 센터, 코트, 시간 구독")
@RestController
public class SubscribeController {
    private final SubscribeService subscribeService;

    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @Operation(summary = "구독", tags = "Subscribe")
    @PostMapping("/times/subscribe/{timeId}")
    public ResponseEntity subscribeCourt(
            @RequestBody SubscribeRequest subscribeRequest,
            @Parameter(description = "특정-센터-코트의 시간 id(pk)", required = true) @PathVariable Long timeId
    ) {
        subscribeService.subscribeCourt(subscribeRequest, timeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "구독 해제", tags = "Subscribe")
    @PostMapping("/unsubscribe/{subscribeId}")
    public ResponseEntity unsubscribeCourt(
            @RequestBody SubscribeRequest subscribeRequest,
            @Parameter(description = "구독한 코트의 id(pk)", required = true) @PathVariable Long subscribeId
    ) {
        subscribeService.unsubscribeCourt(subscribeRequest, subscribeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "구독 목록 조회", tags = "Subscribe")
    @GetMapping("/user/{userId}/subscribes")
    public ResponseEntity<List<ResponseSubscribe>> getSubscribeList(
            @Parameter(description = "사용자 id(pk)", required = true) @PathVariable String userId
    ) {
        return ResponseEntity.ok(subscribeService.subscribeList(userId));
    }
}
