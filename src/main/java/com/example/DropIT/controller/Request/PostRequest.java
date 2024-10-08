package com.example.DropIT.controller.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String fromLocation;

    private String toLocation;

    private String itemDescription;

    private Double weight;

    private Double price;
}

