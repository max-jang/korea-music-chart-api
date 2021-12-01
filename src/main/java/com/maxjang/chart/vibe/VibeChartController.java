package com.maxjang.chart.vibe;

import com.maxjang.chart.common.DetailVO;
import com.maxjang.chart.common.ChartVO;
import com.maxjang.chart.common.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vibe")
public class VibeChartController {
    private final VibeChartService vibeChartService;

    @Autowired
    public VibeChartController(VibeChartService vibeChartService) {
        this.vibeChartService = vibeChartService;
    }

    @GetMapping("/chart")
    public ResponseFormat<ChartVO> getVibeChartTop100() throws Exception {
        return new ResponseFormat<>(vibeChartService.getVibeChartTop100(null));
    }

    @GetMapping("/chart/{artistName}")
    public ResponseFormat<ChartVO> getVibeChartTop100ByArtistName(@PathVariable String artistName) throws Exception {
        return new ResponseFormat<>(vibeChartService.getVibeChartTop100(artistName));
    }

    @GetMapping("/albums/{artistName}")
    public ResponseFormat<DetailVO> getAlbums(@PathVariable String artistName) throws Exception {
        return new ResponseFormat<>(vibeChartService.getAlbums(artistName));
    }

    @GetMapping("/songs/{albumNumber}")
    public ResponseFormat<DetailVO> getSongs(@PathVariable String albumNumber) throws Exception {
        return new ResponseFormat<>(vibeChartService.getSongLists(albumNumber));
    }
}
