package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.INVOICE_INITIAL;
import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.dto.InvoiceDto;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.Guest;
import com.munan.hotelmgt.model.Invoice;
import com.munan.hotelmgt.model.Room;
import com.munan.hotelmgt.repository.GuestRepository;
import com.munan.hotelmgt.repository.InvoiceRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final GuestRepository guestRepository;
    private final GuestService guestService;

    //ADD NEW INVOICE
    public ResponseEntity<HttpResponse<?>> add(InvoiceDto invoiceDto) throws NotFoundException, AlreadyExistException, NoSuchAlgorithmException {
        Invoice newInvoice = addInvoiceFromDto(invoiceDto);
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        invoiceRepository.save(newInvoice)
                )
        );
    }

    //GET ALL INVOICE
    public ResponseEntity<HttpResponse<?>> getAll(Integer page, Integer size, String field) {
        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        invoiceRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.ASC, field))))
        );
    }

    //GET INVOICE BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        
        Invoice findInvoice = findById(id);
        

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findInvoice)
        );
    }
    
    //GET INVOICE BY GUEST ID
    public ResponseEntity<HttpResponse<?>> getByGuestId(Long id) throws NotFoundException {
        
        Invoice invoice = findInvoiceByGuestId(id);
        
        return ResponseEntity.ok(
          new HttpResponse<>(
                  HttpStatus.OK.value(),
                  HttpStatus.OK,
                  succesResponse,
                  invoice
          ));
    }

    //DELETE INVOICE BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
        Invoice findInvoice = findById(id);

        String code = findInvoice.getInvoiceCode();

        invoiceRepository.delete(findInvoice);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Invoice with code " + code +" has been deleted")
        );
    }

    //UPDATE INVOICE
    public ResponseEntity<HttpResponse<?>> update(Invoice invoice) throws NotFoundException {
        Invoice savedInvoice = addInvoiceFromInvoice(invoice);
        
        if(invoice.getId() != null){
            savedInvoice.setId(invoice.getId());
        }
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        invoiceRepository.save(savedInvoice))
        );
    }
    
    
    //PRIVATE METHOD TO FIND BY ID
    private Invoice findById(Long id) throws NotFoundException {

        return invoiceRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Invoice with id "+id+" Does not exist"));

    }
    
    //PRIVATE METHOD TO ADD NEW IVOICE FROM DTO
    private Invoice addInvoiceFromDto(InvoiceDto invoice) throws NotFoundException, NoSuchAlgorithmException{
        
        String invoiceCode = randomNum();
        
        Invoice newInvoice = new Invoice();
        
        Guest guest = guestService.findGuestByGuestCode(invoice.getGuestCode());
        
        Double invoiceTotal = guestRoomSum(guest);
        
        
        newInvoice.setInvoiceCode(invoiceCode);
        newInvoice.setInvoiceTotal(invoiceTotal);
        newInvoice.setGuest(guest);
        newInvoice.setLateCharges(invoice.getLateCharges());
        newInvoice.setPaymentTotal(invoice.getPaymentTotal());
        
        return newInvoice;
    }
    
    //PRIVATE METHOD TO ADD NEW INVOICE FROM INVOICE
    private Invoice addInvoiceFromInvoice(Invoice invoice) throws NotFoundException{
    
        Invoice newInvoice = findInvoiceById(invoice.getId());
        Double sum = newInvoice.getInvoiceTotal();
        
        if(invoice.getLateCharges() != null){
            newInvoice.setLateCharges(invoice.getLateCharges());
            sum += invoice.getLateCharges();
            newInvoice.setInvoiceTotal(sum);
        }
        
        return newInvoice;
    }
    
    
    //RANDOM NUMBER METHOD
    public String randomNum() throws NoSuchAlgorithmException{
        String code;
        
        Random rnd = SecureRandom.getInstanceStrong();
        int number = rnd.nextInt(9999999);
        code = String.format("%07d", number);

        return INVOICE_INITIAL+code;
    }
    
    //FIND INVOICE BY INVOICE NUMBER
    public Invoice findInvoiceByCode(String code) throws NotFoundException{
        return invoiceRepository.findByInvoiceCode(code).orElseThrow(()-> new NotFoundException("Invoice with code "+code+" not Found"));
    }
    
    //FIND INVOICE BY GUEST ID
    private Invoice findInvoiceByGuestId(Long id) throws NotFoundException{
        return invoiceRepository.findByGuest_Id(id).orElseThrow(()-> new NotFoundException("Invoice for Guest with id "+id+" not found"));
    }
    
    //FIND INVOICE BY ID
    public Invoice findInvoiceById(Long id) throws NotFoundException{
        return invoiceRepository.findById(id).orElseThrow(()-> new NotFoundException("Invoice with id "+id+" not found"));
    }
    
    //SUM PRICES OF GUEST ROOMS
    private Double guestRoomSum(Guest guest){
        Double sum = 0.0;
        
        Iterator<Room> roomIterate = guest.getRooms().iterator();
        while(roomIterate.hasNext()){
            sum += roomIterate.next().getRoomType().getPrice();
        }
        
        return sum;
    }

    
}
