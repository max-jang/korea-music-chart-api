package com.hynixlabs.chart.genie;

import com.hynixlabs.chart.bot.ChartBotService;
import com.hynixlabs.chart.common.ChartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chart/genie")
public class GenieChartController {
    final ChartBotService service;

    private final GenieCrawlerService genieCrawlerService;

    @Autowired
    public GenieChartController(GenieCrawlerService genieCrawlerService, ChartBotService service) {
        this.genieCrawlerService = genieCrawlerService;
        this.service = service;
    }

    @GetMapping
    public List<ChartVO> getGenieChartTop100() throws Exception {
        return genieCrawlerService.getGenieChartTop100(false, null);
    }

    @GetMapping("/{artistName}")
    public List<ChartVO> getGenieChartTop100ByArtistName(@PathVariable String artistName) throws Exception {
        return genieCrawlerService.getGenieChartTop100ByArtistName(artistName);
    }


}
