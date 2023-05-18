package prada.teno.domain.crawling.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prada.teno.domain.crawling.application.CenterService;
import prada.teno.domain.crawling.dto.ResponseCenter;
import prada.teno.domain.crawling.dto.ResponseCenterDate;

import java.util.List;

@Tag(name = "Center", description = "센터")
@RestController
public class CenterController {

    CenterService centerService;

    @Autowired
    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    @Operation(summary = "센터정보조회", description = "모든 센터들의 정보를 조회", tags = "Center")
    @GetMapping("/centers")
    public ResponseEntity<List<ResponseCenter>> findCenters() {

        List<ResponseCenter> responseCenters;

        responseCenters = centerService.findCenters();

        if (responseCenters.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(responseCenters);
    }

    @Operation(summary = "검색센터조회", description = "검색어를 포함하는 센터들을 반환", tags = "Center")
    @GetMapping("/centers/search")
    public ResponseEntity<List<ResponseCenter>> searchCenter(
            @Parameter(description = "검색어", required = true) @RequestParam String keyword
    ) {
        List<ResponseCenter> responseCenters = centerService.searchCenters(keyword);

        if (responseCenters.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok().body(responseCenters);
        }
    }

    @Operation(summary = "센터상세조회", description = "센터 id로 특정 센터를 조회", tags = "Center")
    @GetMapping("/center/{id}")
    public ResponseEntity<ResponseCenter> findCenters(
            @Parameter(description = "센터 id", required = true) @PathVariable long id
    ) {
        return ResponseEntity.ok(centerService.findCenter(id));
    }

    @Operation(summary = "센터 calendar 조회", description = "센터 id와 연월('2022-04')로 특정 센터의 해당 연월 달력 조회", tags = "Center")
    @GetMapping("/calendar")
    public ResponseEntity<List<ResponseCenterDate>> findCalendar(
            @Parameter(description = "센터 id", required = true) @RequestParam long id,
            @Parameter(description = "연월", required = true) @RequestParam String date
    ) {
        return ResponseEntity.ok(centerService.findCalendar(id, date));
    }
}
