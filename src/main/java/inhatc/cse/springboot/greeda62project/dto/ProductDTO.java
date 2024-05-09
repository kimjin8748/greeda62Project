package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDTO {


    private String serialNumber;
    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    private String PotSerialNumber;
    private String PotProductName;
    private String PotProductSize;
    private int PotProductPrice;
    private String PotProductDescription;

    private String SucculentSerialNumber;
    private String SucculentProductName;
    private String SucculentProductSize;
    private int SucculentProductPrice;
    private String SucculentProductDescription;

    private String SetSerialNumber;
    private String SetProductName;
    private String SetProductSize;
    private int SetProductPrice;
    private String SetProductDescription;

}
