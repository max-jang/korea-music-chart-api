package com.maxjang.chart.vibe;

import com.maxjang.chart.common.DetailVO;
import com.maxjang.chart.common.ChartVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VibeChartService {
    // Get Top100 Chart
    public List<ChartVO> getVibeChartTop100(String artistName) throws Exception {
        String url = "https://apis.naver.com/vibeWeb/musicapiweb/vibe/v1/chart/track/total";
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<ChartVO> data = new ArrayList<>();
        for (Element element : doc.select("response > result > chart > items > tracks > track")) {
            String[] rank = getRankStatus(element).split(",");
            String searchedArtistName = element.select("album > artists > artist > artistName").text();
            if (artistName == null || searchedArtistName.contains(artistName)) {
                data.add(ChartVO.builder()
                        .rank(Integer.parseInt(element.select("rank > currentRank").text()))
                        .artistName(element.select("album > artists > artist > artistName").text())
                        .title(element.select("trackTitle").text())
                        .albumName(element.select("album > albumTitle").text())
                        .albumArt(element.select("album > imageUrl").text())
                        .songNumber(element.select("trackId").text())
                        .rankStatus(rank[0])
                        .changedRank(Integer.parseInt(rank[1]))
                        .build());
            }
        }
        return data;
    }

    // Get RankStatus
    private String getRankStatus(Element element) {
        String rankVariation = element.select("rank > rankVariation").text();
        if (rankVariation.equals("0")) {
            return "static,0";
        } else if (rankVariation.contains("-")) {
            return "down," + Math.abs(Integer.parseInt(rankVariation));
        } else {
            return "up," + rankVariation;
        }
    }

    // Find AlbumNames By ArtistName
    public List<DetailVO> getAlbums(String artistName) throws Exception {
        String url = "https://apis.naver.com/vibeWeb/musicapiweb/v3/search/album?query="
                + artistName
                + "&start=1&display=100&sort=RELEVANCE";
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<DetailVO> data = new ArrayList<>();
        for (Element element : doc.select("response > result > albums > album")) {
            data.add(DetailVO.builder()
                    .title(element.select("albumTitle").text())
                    .number(element.select("albumId").text())
                    .build());
        }
        return data;
    }

    // Find Songs By AlbumNumber
    public List<DetailVO> getSongLists(String albumNumber) throws Exception {
        String url = "https://apis.naver.com/vibeWeb/musicapiweb/album/" + albumNumber + "/tracks";
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<DetailVO> data = new ArrayList<>();
        for (Element element : doc.select("response > result > tracks > track")) {
            data.add(DetailVO.builder()
                    .title(element.select("trackTitle").text())
                    .number(element.select("trackId").text())
                    .build());
        }
        return data;
    }
}
