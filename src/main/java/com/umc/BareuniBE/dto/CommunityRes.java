package com.umc.BareuniBE.dto;

import com.umc.BareuniBE.entities.Community;
import lombok.*;

public class CommunityRes {
//    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class CommunityCreateRes {
        private Community community;
    }
}
