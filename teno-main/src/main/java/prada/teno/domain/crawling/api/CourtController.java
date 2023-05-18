package prada.teno.domain.crawling.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prada.teno.domain.crawling.application.CourtService;
import prada.teno.domain.crawling.dto.ResponseCourt;

import java.util.List;

    @Tag(name = "Court", description = "코트")
    @RestController
    public class CourtController {

        private final CourtService courtService;

        @Autowired
        public CourtController(CourtService courtService) {
            this.courtService = courtService;
        }

        @Operation(summary = "단일코트조회", description = "특정 코트(id)의 일자('2022-04-19')별로 조회", tags = "Court")
        @GetMapping("/court")
        public ResponseEntity<List<ResponseCourt>> findCourts(
            @Parameter(description = "센터 id", required = true) @RequestParam long centerId,
            @Parameter(description = "날짜", required = true) @RequestParam String date
    ) {
        return ResponseEntity.ok().body(courtService.findCourts(centerId, date));
    }
}
