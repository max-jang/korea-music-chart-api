package com.hynixlabs.chart.melon;

import com.hynixlabs.chart.common.DetailVO;
import com.hynixlabs.chart.common.ChartVO;
import com.hynixlabs.chart.common.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chart/melon")
public class MelonChartController {
    private final MelonChartService melonChartService;

    @Autowired
    public MelonChartController(MelonChartService melonChartService) {
        this.melonChartService = melonChartService;
    }

    @GetMapping
    public ResponseFormat<ChartVO> getMelonChartTop100() throws Exception {
        return new ResponseFormat<>(melonChartService.getMelonChartTop100());
    }

    @GetMapping("/album/{artistName}")
    public ResponseFormat<DetailVO> getAlbums(@PathVariable String artistName) throws Exception {
        return new ResponseFormat<>(melonChartService.getAlbums(artistName));
    }



    @GetMapping("/song/{albumNumber}")
    public ResponseFormat<DetailVO> getSongs(@PathVariable String albumNumber) throws Exception {
        return new ResponseFormat<>(melonChartService.getSongLists(albumNumber));
    }



//    @GetMapping("/{artistName}")
//    public List<Chart> getMelonChartTop100ByArtistName(@PathVariable String artistName) throws Exception {
//        return melonCrawlerService.getMelonChartTop100ByArtistName(artistName);
//    }


}
