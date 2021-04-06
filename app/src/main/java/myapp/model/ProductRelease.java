package myapp.model;

import com.google.gson.annotations.SerializedName;
import myapp.model.ProductStatus;

public class ProductRelease {

    //@SerializedName("id")
    @SerializedName("product")
    private final Product product;
    @SerializedName("status")
    private final ProductStatus status;
    @SerializedName("requested_quantity")
    private final int req_quantity;
    //private String status_name;

   // private final int value;

    public ProductRelease(Product product, ProductStatus status, int req_quantity) {
        this.product = product;
        this.status = status;
        this.req_quantity = req_quantity;
        //status_name = null;
    }

    public Product getProduct() {
        return product;
    }

    public ProductStatus getStatus() {
        //status_name = status.toString();
        return status;
    }

    public int getQuantity() {
        return req_quantity;
    }

  /*  ProductRelease(int value) {
        this.value = value;
        status = null;
    }*/

  /*  public static ProductRelease enumOfValue(String value) {
        for (ProductRelease e : values()) {
            if (e.value == Integer.valueOf(value)) {
                return e;
            }
        }
        return null;
    }*/
}