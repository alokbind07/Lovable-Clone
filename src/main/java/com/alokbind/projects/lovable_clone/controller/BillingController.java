package com.alokbind.projects.lovable_clone.controller;

import com.alokbind.projects.lovable_clone.dto.subscription.*;
import com.alokbind.projects.lovable_clone.service.PaymentProcessor;
import com.alokbind.projects.lovable_clone.service.PlanService;
import com.alokbind.projects.lovable_clone.service.SubscriptionService;
import jdk.javadoc.doclet.Reporter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillingController {

    private final PlanService planService;
    private final SubscriptionService subscriptionService;
    private final PaymentProcessor paymentProcessor;

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans(){
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/api/me/subscription")
    public ResponseEntity<SubscriptionResponse> getMySubscription(){
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription(userId));
    }

    @PostMapping("/api/payments/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutResponse(
            @RequestBody CheckoutRequest request
    ){
        return ResponseEntity.ok(paymentProcessor.createCheckoutSessionUrl(request));
    }

    @PostMapping("api/payments/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal(){
        Long userId = 1L;
        return ResponseEntity.ok(paymentProcessor.openCustomerPortal(userId));
    }
}
