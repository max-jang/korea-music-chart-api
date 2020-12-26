package com.hynixlabs.chart.bugs;

import com.hynixlabs.chart.common.ChartVO;
import com.hynixlabs.chart.common.DetailVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BugsChartService {
    public List<ChartVO> getBugsChartTop100(boolean isSearch, String artistName) throws Exception {
        String url1 = "https://music.bugs.co.kr/chart";
        Document doc1 = Jsoup.connect(url1).userAgent("Chrome").get();

        List<String> artistNames = getTextsOfElements(doc1, "p.artist");

        List<String> titles = getTextsOfElements(doc1, "p.title");

        List<String> albumNames = getTextsOfElements(doc1, ".left .album");

        List<String> albumArts = getAttrsOfElements(doc1, ".thumbnail img", "src");

        List<String> songNumbers = getAttrsOfElements(doc1, ".trackList tbody > tr", "trackid");

        List<String> rankStatuses = getRankStatus(doc1);

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
                            .albumArt("https://" + albumArts.get(i).split("//")[1])
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
                        .albumArt("https://" + albumArts.get(i).split("//")[1])
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
        for (Element element : doc.select(".byChart tbody .ranking p")) {
            String className = element.className().split(" ")[1];
            String text = element.select("em").text();
            switch (className) {
                case "none":
                    hasChangedList.add("static,0");
                    break;
                case "up":
                    hasChangedList.add("up," + text);
                    break;
                case "down":
                    hasChangedList.add("down," + text);
                    break;
                case "new":
                case "renew":
                    hasChangedList.add("new,0"); // 진입
                    break;
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
    public List<ChartVO> getBugsChartTop100ByArtistName(String artistName) throws Exception {
        return getBugsChartTop100(true, artistName);
    }

    public List<DetailVO> getAlbums(String artistName) throws Exception {
        String url = "https://music.bugs.co.kr/search/album?q="
                + artistName
                + "&target=ARTIST_ONLY&flac_only=false&sort=A";
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<DetailVO> data = new ArrayList<>();
        for (Element element : doc.select(".albumInfo")) {
                data.add(DetailVO.builder()
                        .title(element.select(".albumTitle").text())
                        .number(element.attr("albumid"))
                        .build());
        }
        return data;
    }

    public List<DetailVO> getSongLists(String albumNumber) throws Exception {
        String url = "https://music.bugs.co.kr/album/" + albumNumber;
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<DetailVO> data = new ArrayList<>();
        for (Element element : doc.select(".track tbody tr")) {
            data.add(DetailVO.builder()
                    .title(element.select(".title").text())
                    .number(element.attr("trackid"))
                    .build());
        }
        return data;
    }
}
