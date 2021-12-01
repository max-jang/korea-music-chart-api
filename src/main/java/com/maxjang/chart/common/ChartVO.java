package com.maxjang.chart.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartVO {
    private Integer rank;
    private String rankStatus; //up, static, down
    private Integer changedRank;
    private String artistName;
    private String title;
    private String albumName;
    private String albumArt;
    private String songNumber;
}
