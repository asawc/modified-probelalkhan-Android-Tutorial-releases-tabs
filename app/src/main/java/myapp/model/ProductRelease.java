package myapp.model;

import com.google.gson.annotations.SerializedName;

public class ProductRelease {

    //@SerializedName("id")
    private final Product product;
    @SerializedName("status")
    private final ProductStatus status;
    private final int quantity;

    public ProductRelease(Product product, ProductStatus status, int quantity) {
        this.product = product;
        this.status = status;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }
}
