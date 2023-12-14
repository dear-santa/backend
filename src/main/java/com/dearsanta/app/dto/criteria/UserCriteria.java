package com.dearsanta.app.dto.criteria;

import com.dearsanta.app.dto.Criteria;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
public class UserCriteria extends Criteria {
    private String userId;
}
