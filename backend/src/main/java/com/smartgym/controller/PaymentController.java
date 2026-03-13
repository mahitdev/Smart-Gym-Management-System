package com.smartgym.controller;

import com.smartgym.dto.PaymentRequest;
import com.smartgym.model.Payment;
import com.smartgym.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/advance")
    public Payment makeAdvancePayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.makeAdvancePayment(request);
    }

    @GetMapping("/{paymentId}/invoice")
    public ResponseEntity<byte[]> getInvoice(@PathVariable Long paymentId) {
        byte[] pdf = paymentService.generateInvoicePdf(paymentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + paymentId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
