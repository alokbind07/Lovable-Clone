package com.alokbind.projects.lovable_clone.service;

import com.alokbind.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.alokbind.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.alokbind.projects.lovable_clone.dto.subscription.PortalResponse;

public interface PaymentProcessor {

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request);

    PortalResponse openCustomerPortal(Long userId);
}
