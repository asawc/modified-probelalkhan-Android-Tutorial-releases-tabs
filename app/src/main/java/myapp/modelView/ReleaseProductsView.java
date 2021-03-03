package myapp.modelView;

public class ReleaseProductsView {

    private int id;
    private String status;
    private String name;
    private int reqQuantity;

    public ReleaseProductsView(int id, String status, String name, int reqQuantity) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.reqQuantity = reqQuantity;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getReqQuantity() {
        return reqQuantity;
    }
}