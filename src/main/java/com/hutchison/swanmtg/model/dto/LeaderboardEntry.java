package com.hutchison.swanmtg.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = LeaderboardEntry.LeaderboardEntryBuilder.class)
@Builder
@Value
public class LeaderboardEntry {
    Long discordId;
    String name;
    int totalPoints;
    int totalTrophies;
}
