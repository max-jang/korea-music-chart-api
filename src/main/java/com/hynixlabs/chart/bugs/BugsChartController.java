package com.hynixlabs.chart.bugs;

import com.hynixlabs.chart.common.ChartVO;
import com.hynixlabs.chart.common.DetailVO;
import com.hynixlabs.chart.common.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bugs")
public class BugsChartController {

    private final BugsChartService bugsChartService;

    @Autowired
    public BugsChartController(BugsChartService bugsChartService) {
        this.bugsChartService = bugsChartService;
    }

    @GetMapping("/chart")
    public ResponseFormat<ChartVO> getBugsChartTop100() throws Exception {
        return new ResponseFormat<>(bugsChartService.getBugsChartTop100(false, null));
    }

    @GetMapping("/album/{artistName}")
    public ResponseFormat<DetailVO> getAlbums(@PathVariable String artistName) throws Exception {
        return new ResponseFormat<>(bugsChartService.getAlbums(artistName));
    }

    @GetMapping("/song/{albumNumber}")
    public ResponseFormat<DetailVO> getSongs(@PathVariable String albumNumber) throws Exception {
        return new ResponseFormat<>(bugsChartService.getSongLists(albumNumber));
    }

    @GetMapping("/{artistName}")
    public List<ChartVO> getBugsChartTop100ByArtistName(@PathVariable String artistName) throws Exception {
        return bugsChartService.getBugsChartTop100ByArtistName(artistName);
    }


}
