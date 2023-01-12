package expensetracker;

import category.Category;
import transaction.Transaction;

public interface ExpenseTracker {
    void setCurrentMonthBudget(double budget);
    void addTransaction(Transaction transaction);
    void addCategory(Category category);
    void newMonth(String month, double budget);
    void newMonth(String month);
    void deleteTransaction(String transactionId);
    void updateTransaction(String transactionId, String note, double amount, Category category);
    Transaction[] getTransactionsByCategoryForMonth(String month, String categoryId);
    Transaction[] getTransactionsForMonth(String month);
    String getSummaryForMonth(String month);
}
