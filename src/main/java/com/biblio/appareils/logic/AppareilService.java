package com.biblio.appareils.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biblio.appareils.models.Appareils;
import com.biblio.appareils.repos.AppareilRepository;

@Service 
public class AppareilService{

    
        @Autowired 
        public AppareilRepository appareilRepository;

    public Object createAppareil(String name, MultipartFile image, Long number) throws IOException {

        try {
            appareilRepository.save(Appareils.builder()
                .name(name)
                .image(image.getBytes())
                .number(number)
                    .available(true)
                .build());
        return new ResponseEntity<>("Appareil created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
                return new ResponseEntity<>("Failed to create appareil: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
}

    public Object getAvailableAppareils()
    {
        List<Appareils> appareils = appareilRepository.findAll();
        List<Appareils> nw = new ArrayList<>();
        for (Appareils appareil : appareils)
        {
            if (appareil.getAvailable() == true)
            {
                nw.add(appareil);
            }
        }
        return new ResponseEntity<>(nw,HttpStatus.FOUND);
    }


    public Object getAllAppareils()
    {
        List<Appareils> appareils = appareilRepository.findAll();

        return new ResponseEntity<>(appareils,HttpStatus.FOUND);
    }
}
