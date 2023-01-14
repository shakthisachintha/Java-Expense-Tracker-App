package expensetracker;

import java.util.List;

import category.Category;
import month.Month;
import transaction.Transaction;
import types.TransactionType;

class CategoryTransactions {
    public Category category;
    public List<Transaction> transactions;
    public double totalAmount;
}

class DtoMonthlySummaryData {
    public Category category;
    public double totalAmount;
}

class DtoFullDetailsForMonth {
    public Month month;
    public List<CategoryTransactions> categoryTransactions;
    public double totalIncome;
    public double totalExpense;
    public double totalBalance;
}

public interface ExpenseTracker {
    void addTransaction(Transaction transaction);

    void addCategory(Category category);

    void newMonth(String month, double budget);

    void newMonth(String month);

    void deleteTransaction(String transactionId);

    void updateTransaction(String transactionId, String note, double amount);

    List<Category> getCategories();

    List<Category> getCategories(TransactionType type);

    List<Month> getMonths();

    List<Transaction> getTransactionsForMonth(String month);

    Category getCategoryById(String categoryId);

    Transaction geTransactionById(String transactionId);

    /**
     * This method returns all the transactions for the month along with the
     * category name and total amount spent in each category.
     * 
     * @param month month name to get details for
     * @return an instance of DtoFullDetailsForMonth
     * @see DtoFullDetailsForMonth
     */
    public DtoFullDetailsForMonth getFullDetailsForMonth(String month);

    /**
     * This is the summary for the month. It returns total amount spent in each
     * category for the month.
     * 
     * @param month month name to get summary for
     * @return an instance of DtoMonthlySummaryData
     * @see DtoMonthlySummaryData
     */
    List<DtoMonthlySummaryData> getSummaryForMonth(String month);

}
