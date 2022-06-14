package dev.randolph.model;

/**
 * Holds various double inputs from json requests regarding balance changes to client accounts.
 * Assumes all inputs when given are positive.
 * @author Robert
 *
 */
public class BalanceTransfer {
    
    private Double deposit, withdraw, amount = null;

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Double withdraw) {
        this.withdraw = withdraw;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    /**
     * @return The first non null-double, 0 if all values are null
     */
    public Double getDouble() {
        if (deposit != null) {
            return deposit;
        }
        else if (withdraw != null) {
            return withdraw * -1;
        }
        else if (amount != null) {
            return amount;
        }
        
        return 0.0;
    }

    @Override
    public String toString() {
        return "BalanceTransfer [deposit=" + deposit + ", withdraw=" + withdraw + ", amount=" + amount + "]";
    }

}
