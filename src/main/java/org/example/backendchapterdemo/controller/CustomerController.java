package org.example.backendchapterdemo.controller;

import org.example.backendchapterdemo.dto.request.CustomerRequest;
import org.example.backendchapterdemo.dto.response.CustomerResponse;
import org.example.backendchapterdemo.service.CustomerService;
import org.example.backendchapterdemo.util.CsvMapperUtil;
import org.example.backendchapterdemo.util.KafkaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/customer", produces = "application/json")
public class CustomerController {

    private final CustomerService customerService;
    private final CsvMapperUtil csvMapperUtil;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public CustomerController(CustomerService customerService, CsvMapperUtil csvMapperUtil,
                              KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.customerService = customerService;
        this.csvMapperUtil = csvMapperUtil;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<CustomerResponse>> getCustomerPage(
            @PageableDefault Pageable pageable, @RequestBody(required = false) CustomerRequest customerRequest) {
        kafkaTemplate.send(KafkaConstants.CUSTOMER_TOPIC,
                "Customer page retrieved with following request: " + customerRequest);
        return ResponseEntity.ok(customerService.getCustomerPage(customerRequest, pageable));
    }

    @GetMapping("/export/csv")
    public void exportCustomerCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");
        kafkaTemplate.send(KafkaConstants.CUSTOMER_TOPIC,"Customer data exported as CSV");
        csvMapperUtil.exportListAsCsv(customerService.getAllCustomers(), response.getWriter());
    }


    @PostMapping(value = "/import/csv")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<CustomerResponse>> importCustomerCsv(@RequestPart("file") MultipartFile file) {
        List<CustomerResponse> customers = customerService
                .saveBulk(csvMapperUtil.importCsvAsList(file, CustomerRequest.class));
        kafkaTemplate.send(KafkaConstants.CUSTOMER_TOPIC,"Customers CSV imported");
        return ResponseEntity.status(customers.isEmpty() ? HttpStatus.NOT_MODIFIED : HttpStatus.OK).body(customers);
    }


    @PostMapping("/")
    @Valid
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        kafkaTemplate.send(KafkaConstants.CUSTOMER_TOPIC, "Customer creation request received: " +customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable UUID userId) {
        Optional<CustomerResponse> customerResponse = customerService.deleteCustomer(userId);
        kafkaTemplate.send(KafkaConstants.CUSTOMER_TOPIC, "Customer deletion request received. User ID=" + userId);
        return customerResponse
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID userId,
                                                           @RequestBody CustomerRequest customerRequest) {
        Optional<CustomerResponse> customerResponse = customerService.updateCustomer(userId, customerRequest);
        kafkaTemplate.send(KafkaConstants.CUSTOMER_TOPIC, "Customer update request received. User ID=" + userId);
        return customerResponse
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
