package com.hutchison.swanmtg.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@JsonDeserialize(builder = Leaderboard.LeaderboardBuilder.class)
@Builder
@Value
public class Leaderboard {
    List<LeaderboardEntry> records;
    Integer timeFrameInDays;
}
