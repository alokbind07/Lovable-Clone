package com.alokbind.projects.lovable_clone.service.impl;

import com.alokbind.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.alokbind.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.alokbind.projects.lovable_clone.dto.subscription.PortalResponse;
import com.alokbind.projects.lovable_clone.service.PaymentProcessor;

public class StripePaymentProcessor implements PaymentProcessor {

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
