package com.ravindra.commercex.admin.controller;


import com.ravindra.commercex.admin.dto.request.UpdateCustomerStatusRequest;
import com.ravindra.commercex.admin.dto.response.AdminCustomerResponse;
import com.ravindra.commercex.admin.service.AdminCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/customers")
@RequiredArgsConstructor
public class AdminCustomerController {


    private final AdminCustomerService adminCustomerService;



    @GetMapping
    public ResponseEntity<Page<AdminCustomerResponse>> getCustomers(
        Pageable pageable
    ){

        return ResponseEntity.ok(
            adminCustomerService.getCustomers(pageable)
        );

    }



    @GetMapping("/{customerId}")
    public ResponseEntity<AdminCustomerResponse> getCustomer(
        @PathVariable Long customerId
    ){

        return ResponseEntity.ok(
            adminCustomerService.getCustomer(customerId)
        );

    }



    @PatchMapping("/{customerId}/status")
    public ResponseEntity<AdminCustomerResponse> updateStatus(
        @PathVariable Long customerId,
        @RequestBody UpdateCustomerStatusRequest request
    ){

        return ResponseEntity.ok(
            adminCustomerService.updateCustomerStatus(
                customerId,
                request
            )
        );

    }


}
