package com.practice.OneYear.mapper.activityLogMapper;

import com.practice.OneYear.dto.activityLogDTOS.ActivityLogOutputDTO;
import com.practice.OneYear.entity.ActivityLog;
import org.springframework.stereotype.Component;

@Component
public class ActivityLogMapper {

    public ActivityLogOutputDTO toOutputDTOfromEntity(ActivityLog activityLog){

        if (activityLog == null){
            return null;
        }

        ActivityLogOutputDTO build = ActivityLogOutputDTO.builder()
                .id(activityLog.getActivity_log_id())
                .action(activityLog.getAction())
                .timestamp(activityLog.getTimestamp())
                .build();

        return build;
    }
}
