package ku.cs.model.product;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Lot {
    private int L_ID;
    private LocalDate L_Date;
    private int P_ID;
    private LocalDate L_Exp;
    private int L_Quantity;
    private int L_Status;

    public Lot(int l_ID, LocalDate l_Date, int p_ID, LocalDate l_Exp, int l_Quantity) {
        L_ID = l_ID;
        L_Date = l_Date;
        P_ID = p_ID;
        L_Exp = l_Exp;
        L_Quantity = l_Quantity;
    }

    public Lot(int l_ID, LocalDate l_Date, int p_ID, LocalDate l_Exp, int l_Quantity, int l_Status) {
        L_ID = l_ID;
        L_Date = l_Date;
        P_ID = p_ID;
        L_Exp = l_Exp;
        L_Quantity = l_Quantity;
        L_Status = l_Status;
    }

    public int getL_ID() {
        return L_ID;
    }

    public void setL_ID(int l_ID) {
        L_ID = l_ID;
    }

    public LocalDate getL_Date() {
        return L_Date;
    }

    public void setL_Date(LocalDate l_Date) {
        L_Date = l_Date;
    }

    public int getP_ID() {
        return P_ID;
    }

    public void setP_ID(int p_ID) {
        P_ID = p_ID;
    }

    public LocalDate getL_Exp() {
        return L_Exp;
    }

    public void setL_Exp(LocalDate l_Exp) {
        L_Exp = l_Exp;
    }

    public int getL_Quantity() {
        return L_Quantity;
    }

    public void setL_Quantity(int l_Quantity) {
        L_Quantity = l_Quantity;
    }

    public int getL_Status() {
        return L_Status;
    }

    public void setL_Status(int l_Status) {
        L_Status = l_Status;
    }
}
