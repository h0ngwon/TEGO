package prada.teno.domain.crawling.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prada.teno.domain.crawling.dto.CrawlingCenter;
import prada.teno.domain.crawling.dto.CrawlingCourt;
import prada.teno.domain.crawling.domain.CenterType;
import prada.teno.domain.crawling.application.CenterService;
import prada.teno.domain.crawling.application.CourtService;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CrawlingController {

    private final CenterService centerService;
    private final CourtService courtService;

    @ApiIgnore
    @PostMapping("/center")
    public void saveCenter(
            @RequestBody List<CrawlingCenter> crawlingCenters
    ) {
        centerService.centerSave(crawlingCenters);
    }

    @PostMapping("/courts")
    @ApiIgnore
    public void saveCourts(
            @RequestBody List<CrawlingCourt> crawlingCourts
    ) {
        if (crawlingCourts.size() > 0) {
            courtService.save(crawlingCourts, crawlingCourts.get(0).getCenterName(), CenterType.NATIONAL);
        }
    }
}
