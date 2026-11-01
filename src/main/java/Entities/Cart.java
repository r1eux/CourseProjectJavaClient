package Entities;



import java.io.Serializable;
import java.util.List;


public class Cart implements Serializable {

    private int cartId;


    private User user;



    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
