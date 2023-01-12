package transaction;

import java.util.Date;
import java.util.UUID;
import java.util.Locale.Category;

public class Transaction {
    private String id;
    private double amount;
    private Category category;
    private String note;
    private Date date;
    private boolean isRecurring = false;
    private boolean isActive = true;
    
    public Transaction(double amount, Category category, String note, Date date) {
        this.id = UUID.randomUUID().toString().substring(0, 4);
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.date = date;
    }

    // getters and setters
    public boolean isRecurring() {
        return isRecurring;
    }

    public double getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }

    public String getNote() {
        return note;
    }

    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
