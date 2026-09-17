package com.practice.OneYear.specification;

import com.practice.OneYear.entity.Issue;
import com.practice.OneYear.entity.enums.IssuePriority;
import com.practice.OneYear.entity.enums.IssueStatus;
import com.practice.OneYear.entity.enums.IssueType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class IssueSpecification {

    public static Specification<Issue> filterIssues(
            IssueStatus status,
            IssuePriority priority,
            IssueType type,
            Long assignedTo,
            Long createdBy
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // status
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // priority
            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }

            // type
            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            // assignedTo (JOIN with User)
            if (assignedTo != null) {
                predicates.add(cb.equal(root.get("assignee").get("id"), assignedTo));
            }

            // createdBy (JOIN with User)
            if (createdBy != null) {
                predicates.add(cb.equal(root.get("reporter").get("id"), createdBy));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}