package com.hynixlabs.chart.genie;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Genie {
    private Integer rank;
    private String rankStatus; //up, static, down
    private Integer changedRank;
    private String artistName;
    private String title;
    private String albumName;
    private String albumArt;
    private String songNumber;
}
