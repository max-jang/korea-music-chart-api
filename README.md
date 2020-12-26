# korea-music-chart-api
한국 음악 스트리밍 서비스 실시간 차트 TOP 100 API 

## 지원 플랫폼 
멜론, 지니, 바이브, 벅스...<br>
타 사이트는 추가예정

## 사용방법
/{melon, genie, vibe, bugs}/chart - 실시간 차트 TOP 100을 보여줍니다.
<br/>
/{melon, genie, vibe, bugs}/chart/{artistName} - 특정 아티스트만 필터링하여 보여줍니다.

## 예제로 보는 JSON 설명
```
"rank": 1, // 현재 순위
"rankStatus": "static", // 순위변동 내역(static, up, down)
"changedRank": 0, // 순위변동 
"artistName": "태민(TAEMIN)", // 아티스트명 
"title": "Criminal", // 노래명
"albumName": "Never Gonna Dance Again : Act 1 - The 3rd Album", // 앨범명
"albumArt": "https://cdnimg.melon.co.kr/cm2/album/images/104/86/847/10486847_20200907155042_500.jpg", //앨범아트 이미지 URL
"songNumber": "32908826" //노래 고유번호
```

## 특이사항
멜론은 순위변경을 보여주지 않습니다. (차트개편)

## 부가기능 
/{melon, genie, vibe, bugs}/albums/{artistName} - 해당 아티스트의 앨범들을 검색합니다.
<br/>
/{melon, genie, vibe, bugs}/songs/{albumNumber} - 해당 앨범의 노래들을 검색합니다.

## 예제로 보는 JSON 설명

###앨범검색
```
"title": "Never Gonna Dance Again : Act 1 - The 3rd Album", - 앨범명
"number": "10486847" - 앨범 고유번호
```

###노래검색
```
"title": "Criminal", - 노래명
"number": "32908826" - 노래 고유번호
```
