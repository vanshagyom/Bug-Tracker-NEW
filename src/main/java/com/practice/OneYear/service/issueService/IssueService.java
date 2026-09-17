package com.practice.OneYear.service.issueService;

import com.practice.OneYear.dto.issueDTOS.IssueInputDTO;
import com.practice.OneYear.dto.issueDTOS.IssueOutputDTO;
import com.practice.OneYear.entity.enums.IssuePriority;
import com.practice.OneYear.entity.enums.IssueStatus;
import com.practice.OneYear.entity.enums.IssueType;

import java.util.List;

public interface IssueService {
    IssueOutputDTO addIssue(Long projectId, IssueInputDTO issueInputDTO, IssuePriority issuePriority, IssueType issueType);
    List<IssueOutputDTO> getIssues(Long projectId);
    IssueOutputDTO updateStatus(Long issueId);
    IssueOutputDTO assignUserToIssue(Long issueId, Long userId);
    List<IssueOutputDTO> filterIssues(
            IssueStatus status,
            IssuePriority priority,
            IssueType type,
            Long assignedTo,
            Long createdBy
    );
}
