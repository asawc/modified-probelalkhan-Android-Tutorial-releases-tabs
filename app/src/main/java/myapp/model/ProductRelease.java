package myapp.model;

import com.google.gson.annotations.SerializedName;

public class ProductRelease {

    //@SerializedName("id")
    @SerializedName("product")
    private final Product product;
    @SerializedName("status")
    private final ProductStatus status;
    @SerializedName("requested_quantity")
    private final int req_quantity;

    public ProductRelease(Product product, ProductStatus status, int quantity) {
        this.product = product;
        this.status = status;
        this.req_quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public int getQuantity() {
        return req_quantity;
    }
}
