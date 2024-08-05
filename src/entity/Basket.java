package entity;

public class Basket {
    private int id;
    private int product_id;
    private Product product;

    public Basket(int product_id) {
        this.product_id = product_id;
    }

    public Basket() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", product=" + product +
                '}';
    }
}
