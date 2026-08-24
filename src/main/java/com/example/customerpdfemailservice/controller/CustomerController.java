package com.example.customerpdfemailservice.controller;

import com.example.customerpdfemailservice.entity.Customer;
import com.example.customerpdfemailservice.service.CustomerService;
import com.example.customerpdfemailservice.service.EmailService;
import com.example.customerpdfemailservice.service.PdfService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final PdfService pdfService;
    private final EmailService emailService;

    public CustomerController(
            CustomerService customerService,
            PdfService pdfService,
            EmailService emailService
    ) {
        this.customerService = customerService;
        this.pdfService = pdfService;
        this.emailService = emailService;
    }

    @PostMapping("/pdf")
    public ResponseEntity<?> generateCustomerPdf(
            @RequestBody CustomerRequest request
    ) {

        // 1. Check CNIC format
        if (!customerService.isValidCnic(request.getCnic())) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid CNIC format. Use: 42101-1234567-1");
        }

        // 2. Find customer by CNIC
        Optional<Customer> customerOptional =
                customerService.findByCnic(request.getCnic());

        if (customerOptional.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Customer not found for CNIC: " + request.getCnic());
        }

        Customer customer = customerOptional.get();

        // 3. Check Gmail format
        if (!customerService.isValidGmail(customer.getEmail())) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            "Invalid Gmail address for customer: "
                                    + customer.getEmail()
                    );
        }

        // 4. Generate PDF
        byte[] pdfBytes =
                pdfService.generateCustomerPdf(customer);

        // 5. Send PDF by email
        emailService.sendCustomerPdf(
                customer,
                pdfBytes
        );

        // 6. Success response
        return ResponseEntity.ok(
                "Customer PDF generated and sent successfully to "
                        + customer.getEmail()
        );
    }

    // =========================================================
    // REQUEST DTO
    // =========================================================

    public static class CustomerRequest {

        private String cnic;

        public String getCnic() {
            return cnic;
        }

        public void setCnic(String cnic) {
            this.cnic = cnic;
        }
    }
}