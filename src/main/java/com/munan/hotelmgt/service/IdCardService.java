/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.hotelmgt.service;

import static com.munan.hotelmgt.constant.GenConstant.succesResponse;
import com.munan.hotelmgt.exception.AlreadyExistException;
import com.munan.hotelmgt.exception.NotFoundException;
import com.munan.hotelmgt.model.IdCard;
import com.munan.hotelmgt.repository.IdCardRepository;
import com.munan.hotelmgt.utils.HttpResponse;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author godwi
 */
@Service
@Transactional
@RequiredArgsConstructor
public class IdCardService {
    
    private final IdCardRepository cardRepository;

    //ADD NEW CARD
    public ResponseEntity<HttpResponse<?>> addCard(IdCard card) throws AlreadyExistException {
        Optional<IdCard> findCard = cardRepository.findByType(card.getType());

        if(findCard.isPresent()){
            throw new AlreadyExistException(card.getType()+" type already exist");
        }

        IdCard newCard = new IdCard();
        newCard.setType(card.getType());



        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        cardRepository.save(newCard)
                )
        );
    }
    
    //GET ALL CARDS
    public ResponseEntity<HttpResponse<?>> getAll() {
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        cardRepository.findAll()
                ));
    }

    //GET CARD BY ID
    public ResponseEntity<HttpResponse<?>> getById(Long id) throws NotFoundException {
        IdCard findCard = findById(id);

        return ResponseEntity.ok(
                new HttpResponse<>(HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        findCard)
        );
    }

    //DELETE CARD BY ID
    public ResponseEntity<HttpResponse<?>> deleteById(Long id) throws NotFoundException {
         IdCard findCard = findById(id);

        String type = findCard.getType();

        cardRepository.delete(findCard);

        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        "Card of type " + type +" "+ " has been deleted")
        ); 
    }

    //UPDATE CARD
    public ResponseEntity<HttpResponse<?>> update(IdCard card) {
        IdCard savedCard = new IdCard();
        
        if(card.getId() != null){
            savedCard.setId(card.getId());
        }
        
        savedCard.setType(card.getType());
        
        return ResponseEntity.ok(
                new HttpResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        succesResponse,
                        cardRepository.save(savedCard))
        ); 
    }
    
    //FIND CARD BY TYPE
    public IdCard findCardByType(String type)throws NotFoundException{
        return cardRepository.findByType(type).orElseThrow(()-> new NotFoundException("Card of Type "+type+" not Found"));
    }
    
    //PRIVATE METHOD TO FIND BY ID
    private IdCard findById(Long id) throws NotFoundException {

        return cardRepository.findById(id)
                .orElseThrow(()->new NotFoundException("Card with id "+id+", Does not exist"));

    }
    
    
}
