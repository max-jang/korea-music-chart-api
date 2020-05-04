package com.hynixlabs.chart.vibe;

import com.hynixlabs.chart.bot.ChartBotService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VibeCrawlerService {
    private final ChartBotService chartBotService;

    public VibeCrawlerService(ChartBotService chartBotService) {
        this.chartBotService = chartBotService;
    }

    public List<Vibe> getVibeChartTop100(boolean isSearch, String artistName) throws Exception {
        String url = "https://vibe.naver.com/chart/total";
        WebDriver driver = chartBotService.webDriver();
        driver.get(url);
        Document doc = Jsoup.parse(driver.getPageSource());

        List<String> artistNames = getAttrsOfElements(doc, "td.artist", "title");
        List<String> titles = getTextsOfElements(doc, ".inner_cell>.link_text");
        List<String> albumNames = getTextsOfElements(doc, ".album .link");
        List<String> albumArts = getAttrsOfElements(doc, ".img_thumb", "src");
        List<String> songNumbers = getAttrsOfElements(doc, ".inner .icon_play", "data-track-id");
        List<String> rankStatuses = getRankStatus(doc);

        List<Vibe> data = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            String rank = rankStatuses.get(i);
            if (isSearch) { // 아티스트 필터링 검색일 때
                if (artistNames.get(i).contains(artistName)) { // 해당 아티스트만 리스트에 추가
                    data.add(Vibe.builder()
                            .rank(i + 1)
                            .artistName(artistNames.get(i))
                            .title(titles.get(i))
                            .albumName(albumNames.get(i))
                            .albumArt(albumArts.get(i))
                            .songNumber(songNumbers.get(i))
                            .rankStatus(rank)
                            .build());
                }
            } else { // TOP 100 차트
                data.add(Vibe.builder()
                        .rank(i + 1)
                        .artistName(artistNames.get(i))
                        .title(titles.get(i))
                        .albumName(albumNames.get(i))
                        .albumArt(albumArts.get(i))
                        .songNumber(songNumbers.get(i))
                        .rankStatus(rank)
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
//        for (Element element : doc.select("tbody .rank .blind")) {
        for (Element element : doc.select("tbody .rank")) {
            Elements elements = element.children();
            if (elements.size() == 1) {
                hasChangedList.add("static");
            } else if (elements.select(".blind").text().equals("순위 상승")) {
                hasChangedList.add("상승");
            } else {
                hasChangedList.add("하락");
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
    public List<Vibe> getVibeChartTop100ByArtistName(String artistName) throws Exception {
        return getVibeChartTop100(true, artistName);
    }
}
