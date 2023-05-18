package prada.teno.domain.subscribe.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSubscribe {

    private long id;

    private SubscribeData subscribeData;
}
