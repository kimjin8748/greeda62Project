package inhatc.cse.springboot.greeda62project.handler;

public interface MemberDataHandler {

    MemberDTO saveProduct(String productId, String productName, int productPrice, int productStock);
}
