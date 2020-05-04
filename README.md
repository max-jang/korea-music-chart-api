# korea-music-chart-api
한국 음악 스트리밍 서비스 실시간 차트 TOP 100 API 

## 지원 플랫폼 
멜론, 지니, 바이브 (추가 예정)

## 사용방법
/chart/{melon, genie, vibe} - 실시간 차트 TOP 100을 보여줍니다.

/chart/{melon, genie, vibe}/{artistName} - 특정 아티스트만 필터링하여 보여줍니다.

## 예제로 보는 JSON 설명
<code>

    "rank": 1, // 현재 순위
    "rankStatus": "static", // 순위변동 내역(static, up, down)
    "changedRank": 0, // 순위변동 
    "artistName": "조정석", // 아티스트명 
    "title": "아로하", // 노래명
    "albumName": "슬기로운 의사생활 OST Part 3", // 앨범명
    "albumArt": "https://cdnimg.melon.co.kr/cm2/album/images/104/09/054/10409054_20200326163459_500.jpg/melon/resize/120/quality/80/optimize", //앨범아트 이미지 URL
    "songNumber": "32491274" //노래 ID번호
</code>


## 특이사항
네이버 Vibe는 Javascript 로딩으로 기본적인 Jsoup 크롤링이 불가능하여 Selenium같은 Headless 가상 브라우저를 통해 크롤링해야함