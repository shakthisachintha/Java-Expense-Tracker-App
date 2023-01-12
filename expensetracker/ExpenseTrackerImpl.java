package expensetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import category.Category;
import transaction.Transaction;

public class ExpenseTrackerImpl implements ExpenseTracker {

    // Map<Month, Map<CategoryId, List<Transaction>>>
    private Map<String, Map<String, List<Transaction>>> mainDataStructure = new HashMap<>();
    private List<Transaction> recurringTransactions = new ArrayList<>();
    private Map<String, Category> categories = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();


    // Singleton pattern
    private static ExpenseTrackerImpl expenseTracker = new ExpenseTrackerImpl();

    private ExpenseTrackerImpl() {}

    public static ExpenseTrackerImpl getExpenseTracker() {
        return expenseTracker;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.isRecurring()) {
            addRecurringTransaction(transaction);
        } else {
            addNonRecurringTransaction(transaction);
        }
    }

    public void addCategory(Category category) {
        categories.put(category.getId(), category);
    }

    private void addRecurringTransaction(Transaction transaction) {
        recurringTransactions.add(transaction);
    }

    private void addNonRecurringTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void setCurrentMonthBudget(double budget) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void newMonth(String month, double budget) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void newMonth(String month) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteTransaction(String transactionId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateTransaction(String transactionId, String note, double amount, Category category) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Transaction[] getTransactionsByCategoryForMonth(String month, String categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transaction[] getTransactionsForMonth(String month) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSummaryForMonth(String month) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
