package io.absa.bankxapp.controller;

import io.absa.bankxapp.model.Customer;
import io.absa.bankxapp.service.CustomerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/onboard")
    public Customer onboardCustomer(@RequestParam String name, @RequestParam String email) {
        return customerService.onboardCustomer(name, email);

    }
}