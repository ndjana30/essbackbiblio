package com.biblio.appareils.dto;

import com.biblio.appareils.models.Appareils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

    private List<Appareils> appareils;
}
