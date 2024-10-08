package com.example.DropIT.controller.Request;
import com.example.DropIT.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest {
    private String fromLocation;

    private String toLocation;

    private User sender;

    private String itemDescription;

    private Double weight;

    private Double price;

}
