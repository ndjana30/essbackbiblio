package com.biblio.appareils.logic;

import com.biblio.appareils.models.Appareils;
import com.biblio.appareils.models.Transaction;
import com.biblio.appareils.models.TransactionStatus;
import com.biblio.appareils.models.User;
import com.biblio.appareils.repos.AppareilRepository;
import com.biblio.appareils.repos.TransactionRepository;
import com.biblio.appareils.repos.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AppareilRepository appareilRepository;
    @Autowired
    UserRepository userRepository;
    String uname;

//    @Transactional
//    public Object startTransaction(List<Appareils> appareilsList)
//    {
//            List<Appareils> nw = new ArrayList<>();
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.isAuthenticated()) {
//
//                for(Appareils appareil: appareilsList)
//                {
//                        appareil.setAvailable(false);
//                        nw.add(appareil);
//                }
//                uname =  authentication.getName();
//                Optional<User> user = userRepository.findByEmail(uname);
//                try {
//                    user.ifPresent(value -> transactionRepository.save(Transaction.builder()
//                            .appareilsList(nw)
//                            .user(value)
//                            .build()
//                    ));
//                    return new ResponseEntity<>("Transaction begun", HttpStatus.CREATED);
//                } catch (Exception e) {
//                    return new ResponseEntity<>("error: "+ e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//
//            }
//            else {
//                return new ResponseEntity<>("USER NOT CONNECTED",HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//        }

    @Transactional
    public Object startTransaction(List<Appareils> appareilsList) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("USER NOT CONNECTED", HttpStatus.UNAUTHORIZED);
        }

        String uname = authentication.getName();
        Optional<User> userOpt = userRepository.findByEmail(uname);

        if (userOpt.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<Appareils> nw = new ArrayList<>();
        for (Appareils inputAppareil : appareilsList) {
            // Fetch the managed entity from the database to ensure it exists and is tracked
            Appareils appareil = appareilRepository.findById(inputAppareil.getId())
                    .orElseThrow(() -> new RuntimeException("Appareil not found with id: " + inputAppareil.getId()));

            appareil.setAvailable(false);
            appareilRepository.save(appareil); // Explicitly save updated state to DB
            nw.add(appareil);
        }

        // Save transaction (no try-catch needed; if this fails, Spring handles the rollback automatically)
        Transaction transaction = Transaction.builder()
                .appareilsList(nw)
                .user(userOpt.get())
                .transactionStatus(TransactionStatus.IN_USE)
                .build();

        transactionRepository.save(transaction);

        return new ResponseEntity<>("Transaction begun", HttpStatus.CREATED);
    }

    public Object getAllTransactions()
    {
        return new ResponseEntity<>(transactionRepository.findAll(),HttpStatus.OK);
    }

    @Transactional
    public Object endTransaction(Long id)
    {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent())
        {
            for (Appareils appareils: transaction.get().getAppareilsList())
            {
                appareils.setAvailable(true);
                appareilRepository.save(appareils);
            }
            transaction.get().setDepositTime(LocalDateTime.now());
            transaction.get().setTransactionStatus(TransactionStatus.RETURNED);
            transactionRepository.save(transaction.get());
        }
        return new ResponseEntity<>("Transaction Ended Successfully",HttpStatus.OK);
    }


}
