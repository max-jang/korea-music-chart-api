package com.hynixlabs.chart.melon;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Melon {
    private Integer rank;
    private String rankStatus; //up, static, down
    private Integer changedRank;
    private String artistName;
    private String title;
    private String albumName;
    private String albumArt;
    private String songNumber;
}
