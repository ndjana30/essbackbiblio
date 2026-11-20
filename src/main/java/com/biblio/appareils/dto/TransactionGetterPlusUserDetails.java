package com.biblio.appareils.dto;

import com.biblio.appareils.models.Appareils;
import com.biblio.appareils.models.TransactionStatus;
import com.biblio.appareils.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TransactionGetterPlusUserDetails")
@Entity
public class TransactionGetterPlusUserDetails { // JUST A COPY ENTITY OF THE REAL TRANSACTION CLASS IN ORDER TO HAVE ADDITIONAL INFO

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime collectTime;
    private LocalDateTime depositTime=null;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;
    private User user;
    @ElementCollection
    private List<Appareils> appareilsList= new ArrayList<>();
    private String userName;
    private String userClass;
    private String userPhoneNumber;
}
