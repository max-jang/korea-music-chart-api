package com.hynixlabs.chart.vibe;

import com.hynixlabs.chart.common.ChartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chart/vibe")
public class VibeChartController {
    private final VibeCrawlerService vibeCrawlerService;

    @Autowired
    public VibeChartController(VibeCrawlerService vibeCrawlerService) {
        this.vibeCrawlerService = vibeCrawlerService;
    }

    @GetMapping
    public List<ChartVO> getVibeChartTop100() throws Exception {
        return vibeCrawlerService.getVibeChartTop100(false, null);
    }

    @GetMapping("/{artistName}")
    public List<ChartVO> getVibeChartTop100ByArtistName(@PathVariable String artistName) throws Exception {
        return vibeCrawlerService.getVibeChartTop100ByArtistName(artistName);
    }


}
