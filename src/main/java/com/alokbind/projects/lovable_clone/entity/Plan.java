package com.alokbind.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Plan {

    Long id;

    String name;

    String stripePriceId;
    Integer maxProjects;
    Integer maxTokensPerDay;
    Integer maxPreviews;    //max number of preview allowed per plan
    Boolean unlimitedAi;    //Unlimited access to LLM, ignore maxTokensPerDay if true

    Boolean active;
}
