package com.practice.OneYear.service.activityLogService;

import com.practice.OneYear.dto.activityLogDTOS.ActivityLogOutputDTO;
import com.practice.OneYear.dto.activityLogDTOS.IssueVelocityDTO;
import com.practice.OneYear.entity.ActivityLog;
import com.practice.OneYear.entity.Issue;
import com.practice.OneYear.exceptions.ResourceNotFoundException;
import com.practice.OneYear.mapper.activityLogMapper.ActivityLogMapper;
import com.practice.OneYear.repository.ActivityLogRepository;
import com.practice.OneYear.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final ActivityLogMapper activityLogMapper;
    private final IssueRepository issueRepository;

    public ActivityLogServiceImpl(ActivityLogRepository activityLogRepository, ActivityLogMapper activityLogMapper, IssueRepository issueRepository) {
        this.activityLogRepository = activityLogRepository;
        this.activityLogMapper = activityLogMapper;
        this.issueRepository = issueRepository;
    }

    @Override
    public void createActivityLog(Issue issue, String action) {

        ActivityLog build = ActivityLog.builder()
                .action(action)
                .issue(issue)
                .timestamp(LocalDateTime.now())
                .build();

        activityLogRepository.save(build);
    }

    @Override
    public List<ActivityLogOutputDTO> getActivityLog() {
        List<ActivityLog> all = activityLogRepository.findAll();
        return all.stream().map(activityLogMapper::toOutputDTOfromEntity).toList();
    }

    @Override
    public IssueVelocityDTO getIssueVelocity(Long issueId) {
        issueRepository.findById(issueId).orElseThrow(
                () -> new ResourceNotFoundException("Issue with id: " + issueId + " not found")
        );

//        return IssueVelocityDTO.builder()
//                .issueId()
//                .durationHours()
//                .stageDurations()
//                .slowestStage()
//                .build();

        return null;
    }
}
