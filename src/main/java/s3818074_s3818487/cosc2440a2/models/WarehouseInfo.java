package s3818074_s3818487.cosc2440a2.models;

public class WarehouseInfo {
    private Product product;
    private int received;
    private int delivery;
    private int balance;

    public WarehouseInfo(Product product, int received, int delivery) {
        this.product = product;
        this.received = received;
        this.delivery = delivery;
        this.balance = received - delivery;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Product getProduct() {
        return product;
    }

    public int getReceived() {
        return received;
    }

    public int getDelivery() {
        return delivery;
    }

    public int getBalance() {
        return balance;
    }
}
