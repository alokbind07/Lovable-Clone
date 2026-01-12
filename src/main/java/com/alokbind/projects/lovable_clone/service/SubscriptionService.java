package com.alokbind.projects.lovable_clone.service;

import com.alokbind.projects.lovable_clone.dto.subscription.SubscriptionResponse;

public interface SubscriptionService {

    SubscriptionResponse getCurrentSubscription(Long userId);
}
