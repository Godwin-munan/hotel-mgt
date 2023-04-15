package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.dto.PaymentDto;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Invoice;
import com.munan.hotelmgt.model.Payment;
import com.munan.hotelmgt.model.PaymentMethod;
import com.munan.hotelmgt.repository.InvoiceRepository;
import com.munan.hotelmgt.repository.PaymentRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    
    private final InvoiceService invoiceService;
    private final PaymentMethodService methodService;

    public PaymentService(PaymentRepository paymentRepository, InvoiceRepository invoiceRepository, InvoiceService invoiceService, PaymentMethodService methodService) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceService = invoiceService;
        this.methodService = methodService;
    }
    
    

    //ADD NEW PAYMENT
    public ResponseEntity<HttpResponse<?>> add(PaymentDto paymentDto) throws NotFoundException {
        Payment payment = addPaymentFromDto(paymentDto);
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        paymentRepository.save(payment)
                )
        );
    }
    
    //ADD PAYMENT BY INVOICE ID
    public ResponseEntity<HttpResponse<?>> addByInvoiceId(Long id, PaymentDto paymentDto) throws NotFoundException {
        Invoice invoice = invoiceService.findInvoiceById(id);
        PaymentMethod method = methodService.findById(paymentDto.getPaymentMethodId());
        
        Payment payment = addPaymentToInvoice(paymentDto.getAmount(), invoice);
        
        payment.setPaymentDate(paymentDto.getDate());
        payment.setPaymentMethod(method);
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        paymentRepository.save(payment)
                )
        );
    }
    
    //GET ALL PAYMENT
    public ResponseEntity<HttpResponse<?>> getAll(Integer page, Integer size, String field) {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        paymentRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, field))))
        );
    }
    
    //GET ALL PAYMENT OF AN INVOICE BY INVOICE ID
    public ResponseEntity<HttpResponse<?>> getByInvoiceId(Long id) throws NotFoundException {
        List<Payment> payments = findPaymentByInvoiceId(id);
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        payments
                )
        );
    }
    
    //GET ALL PAYMENTS OF AN INVOICE BY INVOICE CODE
    public ResponseEntity<HttpResponse<?>> getByInvoiceCode(String code) throws NotFoundException {
        
        List<Payment> payments = findPaymentByInvoiceCode(code);
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        payments
                )
        );
    }
    
    //REMOVE PAYMENT BY INVOICE ID
    public ResponseEntity<HttpResponse<?>> removeById(Long id) throws NotFoundException {   
        removePayment(id);
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Payment with id " + id +" has been deleted")
        );
    }
    
    //PRIVATE METHOD TO ADD NEW PAYMENT FROM DTO
    private Payment addPaymentFromDto(PaymentDto payment) throws NotFoundException{
        
        Invoice invoice = invoiceService.findInvoiceById(payment.getInvoiceId());
        PaymentMethod method = methodService.findById(payment.getPaymentMethodId());
        
        Payment newPayment = addPaymentToInvoice( payment.getAmount(), invoice);
        
        newPayment.setPaymentDate(payment.getDate());
        newPayment.setPaymentMethod(method);
        
        
        return newPayment;
    }
    
    //ADD PAYMENT TO INVOICE
    private Payment addPaymentToInvoice(Double amount, Invoice invoice) {
        Double sum = invoice.getPaymentTotal();
        
        Payment payment = new Payment();
        
        sum += amount;
        invoice.setPaymentTotal(sum);
        payment.setInvoice(invoice);
        payment.setAmount(amount);
        
        return payment;
    }

    

     //FIND INVOICE BY GUEST ID
    private List<Payment> findPaymentByInvoiceId(Long id) throws NotFoundException{
        return paymentRepository.findByInvoice_id(id).orElseThrow(()-> {
            return new NotFoundException("No Payment available for this invoice");
        });
    }
    
    //FIND PAYMENTS BY INVOICE CODE
    private List<Payment> findPaymentByInvoiceCode(String invoiceCode) throws NotFoundException{
        Invoice invoice = invoiceService.findInvoiceByCode(invoiceCode);
        
        return findPaymentByInvoiceId(invoice.getId());
    }

    //FIND PAYMENT BY ID
    private Payment findPaymentById(Long id) throws NotFoundException{
        return paymentRepository.findById(id).orElseThrow(()->  new NotFoundException("Payment with id "+id+" not found"));
    }
    
    //REMOVE PAYMENT 
    private void removePayment(Long id) throws NotFoundException{
        
       
        Payment payment = findPaymentById(id);
        
        Double sum = payment.getInvoice().getPaymentTotal();
        Double amount = payment.getAmount();
        sum -= amount;
        payment.getInvoice().setPaymentTotal(sum);
        
        invoiceRepository.save(payment.getInvoice());
        paymentRepository.delete(payment);
    }


}
