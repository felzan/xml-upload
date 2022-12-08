package com.felzan.iapps.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class EPaper {

    @Id
    @Indexed
    private String id;
    @Indexed
    @CreatedDate
    @Builder.Default
    private Date createdAt = new Date();
    @Indexed
    private String filename;
    @Indexed
    private String newspaperName;
    @Indexed
    private int width;
    @Indexed
    private int height;
    @Indexed
    private int dpi;
}
