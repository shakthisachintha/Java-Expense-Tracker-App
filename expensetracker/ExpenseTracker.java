package expensetracker;

import category.Category;
import month.Month;
import transaction.Transaction;

public interface ExpenseTracker {
    void addTransaction(Transaction transaction);

    void addCategory(Category category);

    void newMonth(String month, double budget);

    void newMonth(String month);

    void deleteTransaction(String transactionId);

    void updateTransaction(String transactionId, String note, double amount);

    Transaction[] getTransactionsByCategoryForMonth(String month, String categoryId);

    Transaction[] getTransactionsForMonth(String month);

    /**
     * Returns a 2D array of Strings where the first column is the category name
     * second column is whether the category is 'income' or 'expense'
     * third column total amount spent in that category for the month.
     * 
     * @param month month name to get summary for
     * @return ["Groceries", "expense", "USD 100.00"]
     *["Salary", "income", "USD 1200.00"]
     */
    String[][] getSummaryForMonth(String month);

    Category[] getCategories();

    Month[] getMonths();
}
