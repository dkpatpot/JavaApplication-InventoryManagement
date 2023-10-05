package ku.cs.model.product;

import ku.cs.model.promotion.Promotion;

import java.time.LocalDateTime;

public class Order {
    private int OP_ID;
    private int OP_Quantity;
    private int OP_Price;
    private int P_ID;
    private LocalDateTime OP_Date;
    private int OP_Type;
    private int OP_Status;
    private String Username;

    public Order() {

    };

    public Order(int OP_ID, int OP_Quantity, int OP_Price, int p_ID, String username) {
        this.OP_ID = OP_ID;
        this.OP_Quantity = OP_Quantity;
        this.OP_Price = OP_Price;
        P_ID = p_ID;
        this.Username = username;
    }

    public Order(int OP_ID, int OP_Quantity, int OP_Price, int p_ID, int OP_Status, String username) {
        this.OP_ID = OP_ID;
        this.OP_Quantity = OP_Quantity;
        this.OP_Price = OP_Price;
        P_ID = p_ID;
        this.OP_Status = OP_Status;
        this.Username = username;
    }

    public Order(int OP_ID, int OP_Quantity, int OP_Price, LocalDateTime OP_Date, int OP_Type, int OP_Status) {
        this.OP_ID = OP_ID;
        this.OP_Quantity = OP_Quantity;
        this.OP_Price = OP_Price;
        this.OP_Date = OP_Date;
        this.OP_Type = OP_Type;
        this.OP_Status = OP_Status;
    }

    public Order(int OP_ID, int OP_Quantity, int OP_Price, int p_ID, int OP_Type, int OP_Status, String username) {
        this.OP_ID = OP_ID;
        this.OP_Quantity = OP_Quantity;
        this.OP_Price = OP_Price;
        P_ID = p_ID;
        this.OP_Type = OP_Type;
        this.OP_Status = OP_Status;
        Username = username;
    }

    public int getOP_ID() {
        return OP_ID;
    }

    public void setOP_ID(int OP_ID) {
        this.OP_ID = OP_ID;
    }

    public int getOP_Quantity() {
        return OP_Quantity;
    }

    public void setOP_Quantity(int OP_Quantity) {
        this.OP_Quantity = OP_Quantity;
    }

    public int getOP_Price() {
        return OP_Price;
    }

    public void setOP_Price(int OP_Price) {
        this.OP_Price = OP_Price;
    }

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    public LocalDateTime getOP_Date() {
        return OP_Date;
    }

    public void setOP_Date(LocalDateTime OP_Date) {
        this.OP_Date = OP_Date;
    }

    public int getOP_Type() {
        return OP_Type;
    }

    public void setOP_Type(int OP_Type) {
        this.OP_Type = OP_Type;
    }

    public int getOP_Status() {
        return OP_Status;
    }

    public void setOP_Status(int OP_Status) {
        this.OP_Status = OP_Status;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    //    public boolean checkPromotion(Promotion promotion) throws IllegalConditionException {
//        return promotion.usePromotion(this);
//    }
}
