package com.example.razorpay454;

import com.razorpay.Invoice;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController()
public class InvoicesController {
    @PostMapping("/newinvoice")
    public String generate(@RequestParam("name") String customerName,
                           @RequestParam("contact") String customerContact,
                           @RequestParam("email") String customerEmail,
                           @RequestParam("line1") String billingLine1,
                           @RequestParam("line2") String billingLine2,
                           @RequestParam("zipcode") String billingZipcode,
                           @RequestParam("city") String billingCity,
                           @RequestParam("state") String billingState,
                           @RequestParam("country") String billingCountry,
                           @RequestParam("amount") int itemAmount,
                           @RequestParam("currency") String itemCurrency,
                           @RequestParam("quantity") int itemQuantity) {
        try {
            RazorpayClient razorpay = new RazorpayClient("rzp_test_sEetAssvzTuE4s", "ky37LKrxw8W9dVOz9EtG4Y0R");

            JSONObject invoiceRequest = new JSONObject();
            invoiceRequest.put("type", "invoice");
            invoiceRequest.put("description", "Invoice for the month of January 2020");
            invoiceRequest.put("partial_payment", true);

            JSONObject customer = new JSONObject();
            customer.put("name", customerName);
            customer.put("contact", customerContact);
            customer.put("email", customerEmail);

            JSONObject billingAddress = new JSONObject();
            billingAddress.put("line1", billingLine1);
            billingAddress.put("line2", billingLine2);
            billingAddress.put("zipcode", billingZipcode);
            billingAddress.put("city", billingCity);
            billingAddress.put("state", billingState);
            billingAddress.put("country", billingCountry);
            customer.put("billing_address", billingAddress);
            customer.put("shipping_address", billingAddress);

            invoiceRequest.put("customer", customer);

            List<Object> lines = new ArrayList<>();
            JSONObject lineItems = new JSONObject();
            lineItems.put("name", "Master Cloud Computing in 30 Days");
            lineItems.put("description", "Book by Ravena Ravenclaw");
            lineItems.put("amount", itemAmount);
            lineItems.put("currency", itemCurrency);
            lineItems.put("quantity", itemQuantity);
            lines.add(lineItems);
            invoiceRequest.put("line_items", lines);

            invoiceRequest.put("email_notify", 1);
            invoiceRequest.put("sms_notify", 1);
            invoiceRequest.put("currency", itemCurrency);
            invoiceRequest.put("expire_by", 2580479824L);
            Invoice invoice = razorpay.invoices.create(invoiceRequest);

            return "Payment Successful";
        } catch (RazorpayException e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}