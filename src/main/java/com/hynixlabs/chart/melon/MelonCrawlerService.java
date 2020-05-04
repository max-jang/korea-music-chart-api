package com.hynixlabs.chart.melon;

import com.hynixlabs.chart.common.ChartVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MelonCrawlerService {
    public List<ChartVO> getMelonChartTop100(boolean isSearch, String artistName) throws Exception {
        String url = "https://www.melon.com/chart/index.htm";
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();

        List<String> artistNames = getTextsOfElements(doc, ".wrap_song_info .rank02 span");
        List<String> titles = getTextsOfElements(doc, ".wrap_song_info .rank01 span a");
        List<String> albumNames = getTextsOfElements(doc, ".wrap_song_info .rank03 a");
        List<String> albumArts = getAttrsOfElements(doc, ".image_typeAll img", "src");
        List<String> songNumbers = getAttrsOfElements(doc, "tr[data-song-no]", "data-song-no");
        List<String> rankStatuses = getRankStatus(doc);

        List<ChartVO> data = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            String[] rank = rankStatuses.get(i).split(",");
            if (isSearch) { // 아티스트 필터링 검색일 때
                if (artistNames.get(i).contains(artistName)) { // 해당 아티스트만 리스트에 추가
                    data.add(ChartVO.builder()
                            .rank(i + 1)
                            .artistName(artistNames.get(i))
                            .title(titles.get(i))
                            .albumName(albumNames.get(i))
                            .albumArt(albumArts.get(i))
                            .songNumber(songNumbers.get(i))
                            .rankStatus(rank[0])
                            .changedRank(Integer.parseInt(rank[1]))
                            .build());
                }
            } else { // TOP 100 차트
                data.add(ChartVO.builder()
                        .rank(i + 1)
                        .artistName(artistNames.get(i))
                        .title(titles.get(i))
                        .albumName(albumNames.get(i))
                        .albumArt(albumArts.get(i))
                        .songNumber(songNumbers.get(i))
                        .rankStatus(rank[0])
                        .changedRank(Integer.parseInt(rank[1]))
                        .build());
            }
        }
        return data;
    }


    private List<String> getTextsOfElements(Document doc, String selector) {
        return doc.select(selector).stream()
                .map(Element::text)
                .collect(Collectors.toList());
    }

    // 변동 순위
    private List<String> getRankStatus(Document doc) {
        List<String> hasChangedList = new ArrayList<>();
        for (Element element : doc.select(".rank_wrap")) {
            String text = element.text();
            if (text.equals("순위 동일 0")) {
                hasChangedList.add("static,0");
            } else if (text.contains("상승")) {
                hasChangedList.add("up," + text.split("\\s")[2]);
            } else if (text.contains("하락")) {
                hasChangedList.add("down," + text.split("\\s")[2]);
            } else {
                hasChangedList.add("new,0"); // 진입
            }
        }
        return hasChangedList;
    }

    // 태그 속성값 가져오기
    private List<String> getAttrsOfElements(Document doc, String selector, String attr) {
        return doc.select(selector).stream()
                .map(element -> element.attr(attr))
                .collect(Collectors.toList());
    }

    // 아티스트 필터링 기능
    public List<ChartVO> getMelonChartTop100ByArtistName(String artistName) throws Exception {
        return getMelonChartTop100(true, artistName);
    }
}
