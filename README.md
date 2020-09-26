# korea-music-chart-api
한국 음악 스트리밍 서비스 실시간 차트 TOP 100 API 

## 지원 플랫폼 
멜론, 지니, 바이브...
타 사이트는 추가예정

## 사용방법
/chart/{melon, genie, vibe} - 실시간 차트 TOP 100을 보여줍니다.
<br/>
/chart/{melon, genie, vibe}/{artistName} - 특정 아티스트만 필터링하여 보여줍니다.

## 예제로 보는 JSON 설명
<code>
{
  "data": [
    "rank": 1, // 현재 순위
    "rankStatus": "static", // 순위변동 내역(static, up, down)
    "changedRank": 0, // 순위변동 
    "artistName": "조정석", // 아티스트명 
    "title": "아로하", // 노래명
    "albumName": "슬기로운 의사생활 OST Part 3", // 앨범명
    "albumArt": "https://cdnimg.melon.co.kr/cm2/album/images/104/09/054/10409054_20200326163459_500.jpg/melon/resize/120/quality/80/optimize", //앨범아트 이미지 URL
    "songNumber": "32491274" //노래 고유번호
  ]
}
</code>


## 특이사항
멜론은 순위변경을 보여주지 않습니다.

## 부가기능 
/chart/{melon, genie, vibe}/album/{artistName} - 해당 아티스트의 앨범들을 검색합니다.
<br/>
/chart/{melon, genie, vibe}/songs/{albumNumber} - 해당 앨범의 노래들을 검색합니다.

## 예제로 보는 JSON 설명
<h3>앨범검색</h3>

<code>
{
  "data": [
    {
      "title": "Never Gonna Dance Again : Act 1 - The 3rd Album", - 앨범명
      "number": "10486847" - 앨범 고유번호
    }
  ]
}
</code>

<h3>노래검색</h3>

<code>
{
  "data": [
    {
      "title": "Criminal", - 노래명
      "number": "32908826" - 노래 고유번호
    }
  ]
}
</code>
