package myapp.model;

import com.google.gson.annotations.SerializedName;

public enum ProductStatus {

    @SerializedName("0")
    AWAITED, // (0),

    @SerializedName("1")
    BRAK_W_MAGAZYNIE, // (1),

    @SerializedName("2")
    WYDANY; // (2);

    public static int value;

    public String status_name;


    public String ProductStatus(int value) {
        return status_name;
    }

    // private final String status_name;

    public static int getValue() {
        return value;
    }

   /* ProductStatus(int value) {
        this.value = value;
    //    status_name = toString(ProductStatus);
    //    status_name = valueOf(ProductStatus);
    }*/

    public static ProductStatus enumOfValue(String value) {
        for (ProductStatus e : values()) {
            if (e.value==Integer.valueOf(value)) {
                return e;
            }
        }
        return null;
    }
}
