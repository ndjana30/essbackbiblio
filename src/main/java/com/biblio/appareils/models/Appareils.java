package com.biblio.appareils.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@Table(name = "appareils")
@Entity 
@AllArgsConstructor
@NoArgsConstructor
public class Appareils {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "LONGBLOB")  // For MySQL
    // Use @Column(columnDefinition = "BYTEA") for PostgreSQL
    private byte[] image;
    private Long number;
    private Boolean available;
}
