package prada.teno.domain.crawling.converter;

import prada.teno.domain.crawling.dto.CrawlingCourt;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.crawling.domain.CenterType;

import java.util.List;

public interface CourtConverter {

    boolean isSatisfied(final CenterType centerType);
    Center convert(final List<CrawlingCourt> inputs, String centerName);
}
