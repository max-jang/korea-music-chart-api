package com.maxjang.chart.genie;

import com.maxjang.chart.common.DetailVO;
import com.maxjang.chart.common.ChartVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class GenieChartService {
    // Get Top100 Chart
    public List<ChartVO> getGenieChartTop100(String artistName) throws Exception {
        String url1 = "https://www.genie.co.kr/chart/top200?rtm=Y&pg=1";
        String url2 = "https://www.genie.co.kr/chart/top200?rtm=Y&pg=2";
        Document doc1 = Jsoup.connect(url1).userAgent("Chrome").get();
        Document doc2 = Jsoup.connect(url2).userAgent("Chrome").get();

        List<String> artistNames = getTextsOfElements(doc1, "table .artist");
        artistNames.addAll(getTextsOfElements(doc2, "table .artist"));

        List<String> titles = getTextsOfElements(doc1, "table .title");
        titles.addAll(getTextsOfElements(doc2, "table .title"));

        List<String> albumNames = getTextsOfElements(doc1, "table .albumtitle");
        albumNames.addAll(getTextsOfElements(doc2, "table .albumtitle"));

        List<String> albumArts = getAttrsOfElements(doc1, "table .cover img", "src");
        albumArts.addAll(getAttrsOfElements(doc2, "table .cover img", "src"));

        List<String> songNumbers = getAttrsOfElements(doc1, "table tr[songid]", "songid");
        songNumbers.addAll(getAttrsOfElements(doc2, "table tr[songid]", "songid"));

        List<String> rankStatuses = getRankStatus(doc1);
        rankStatuses.addAll(getRankStatus(doc2));

        List<ChartVO> data = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            String[] rank = rankStatuses.get(i).split(",");
            if (artistName == null || artistNames.get(i).contains(artistName)) {
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

    // Get tag value
    private List<String> getTextsOfElements(Document doc, String selector) {
        return doc.select(selector).stream()
                .map(Element::text)
                .collect(Collectors.toList());
    }

    // Get RankStatus
    private List<String> getRankStatus(Document doc) {
        List<String> hasChangedList = new ArrayList<>();
        for (Element element : doc.select("table span.rank>span.rank>span")) {
            String className = element.className();
            String text = element.ownText();
            switch (className) {
                case "rank-none":
                    hasChangedList.add("static,0");
                    break;
                case "rank-up":
                    hasChangedList.add("up," + text);
                    break;
                case "rank-down":
                    hasChangedList.add("down," + text);
                    break;
                case "rank-new":
                    hasChangedList.add("new,0"); // 진입
                    break;
            }
        }
        return hasChangedList;
    }

    // Get tag attribute values
    private List<String> getAttrsOfElements(Document doc, String selector, String attr) {
        return doc.select(selector).stream()
                .map(element -> element.attr(attr))
                .collect(Collectors.toList());
    }

    // Find AlbumNames By ArtistName
    public List<DetailVO> getAlbums(String artistName) throws Exception {
        String url = "https://www.genie.co.kr/search/searchAlbum?query=" +
                artistName;
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<DetailVO> data = new ArrayList<>();
        for (Element element : doc.select("dt > a")) {
            Matcher m = Pattern.compile("fnViewAlbumLayer\\('(.*?)'\\)").matcher(element.attr("onclick"));
            while (m.find()) {
                data.add(DetailVO.builder()
                        .title(element.text())
                        .number(m.group(1))
                        .build());
            }
        }
        return data;
    }

    // Find Songs By AlbumNumber
    public List<DetailVO> getSongLists(String albumNumber) throws Exception {
        String url = "https://www.genie.co.kr/detail/albumInfo?axnm=" + albumNumber;
        Document doc = Jsoup.connect(url).userAgent("Chrome").get();
        List<DetailVO> data = new ArrayList<>();
        for (Element element : doc.select("tbody > .list")) {
            data.add(DetailVO.builder()
                    .title(element.select(".title").text())
                    .number(element.attr("songid"))
                    .build());
        }
        return data;
    }
}
