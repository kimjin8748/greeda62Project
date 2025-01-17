package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_image")
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;        // 이미지 URL
    private String photoFileName;  // 원본 파일 이름

    @Column(nullable = false)
    private boolean isMain = false; // 대표 이미지 여부

    @ManyToOne
    @JoinColumn(name = "product_id") // 외래키 설정
    private ProductEntity product;  // 상품과의 관계
}
