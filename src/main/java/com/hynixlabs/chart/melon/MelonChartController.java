package com.hynixlabs.chart.melon;

import com.hynixlabs.chart.bot.ChartBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chart/melon")
public class MelonChartController {
    final ChartBotService service;

    private final MelonCrawlerService melonCrawlerService;

    @Autowired
    public MelonChartController(MelonCrawlerService melonCrawlerService, ChartBotService service) {
        this.melonCrawlerService = melonCrawlerService;
        this.service = service;
    }

    @GetMapping
    public List<Melon> getMelonChartTop100() throws Exception {
        return melonCrawlerService.getMelonChartTop100(false, null);
    }

    @GetMapping("/{artistName}")
    public List<Melon> getMelonChartTop100ByArtistName(@PathVariable String artistName) throws Exception {
        return melonCrawlerService.getMelonChartTop100ByArtistName(artistName);
    }


}
