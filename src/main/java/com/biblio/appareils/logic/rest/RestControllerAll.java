package com.biblio.appareils.logic.rest;

import java.io.IOException;
import java.util.List;

import com.biblio.appareils.dto.AppareilDto;
import com.biblio.appareils.logic.TransactionService;
import com.biblio.appareils.models.Appareils;
import lombok.Locked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.biblio.appareils.logic.AppareilService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class RestControllerAll {
    
    
    @Autowired 
    public AppareilService appareilService;
    @Autowired
    public TransactionService transactionService;

    public RestControllerAll() {
    }
    

    @PostMapping(value = "/appareils/add")
    public Object postAppareil(@RequestParam ("name") String name,
                               @RequestParam ("number") Long number,
                               @RequestParam ("image") MultipartFile image
                               ) throws IOException {
        
        return appareilService.createAppareil(name, image, number);
    }

    @PostMapping("/transaction/add")
    public Object startTransaction(@RequestBody List<Appareils> appareilsList) throws IOException {

        return transactionService.startTransaction(appareilsList);
    }

    @GetMapping("/appareils/available")
    public Object getAvailableAppareils()
    {
        return appareilService.getAvailableAppareils();
    }

    @GetMapping("/transactions/all")
    public Object getAllTransactions()
    {
        return transactionService.getAllTransactions();
    }
    @PostMapping ("/transaction/end")
    public Object endTransactionById(@RequestParam ("id") Long id)
    {
        return transactionService.endTransaction(id);
    }
    
}
