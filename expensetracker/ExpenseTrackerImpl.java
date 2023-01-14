package expensetracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import category.Category;
import month.Month;
import transaction.Transaction;

public class ExpenseTrackerImpl implements ExpenseTracker {

    // Map<MonthName, Map<CategoryId, List<Transaction>>>
    private Map<String, Map<String, List<Transaction>>> mainDataStructure = new HashMap<>();
    private Map<String, Transaction> recurringTransactions = new HashMap<>();
    private Map<String, Category> categories = new HashMap<>();
    // Map<MonthName, Month>
    private Map<String, Month> months = new HashMap<>();
    private Map<String, Transaction> transactions = new HashMap<>();

    // Singleton pattern
    private static ExpenseTrackerImpl expenseTracker = new ExpenseTrackerImpl();

    private ExpenseTrackerImpl() {
    }

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
        transaction.setRecurring(true);
        recurringTransactions.put(transaction.getId(), transaction);
    }

    private void addNonRecurringTransaction(Transaction transaction) {
        transaction.setRecurring(false);
        transactions.put(transaction.getId(), transaction);
    }

    @Override
    public void newMonth(String month, double budget) {
        Month mon = new Month(month, budget);
        months.put(month, mon);
    }

    @Override
    public void newMonth(String month) {
        Month mon = new Month(month);
        months.put(month, mon);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        transactions.get(transactionId).setActive(false);
    }

    @Override
    public void updateTransaction(String transactionId, String note, double amount) {
        Transaction transaction = transactions.get(transactionId);
        transaction.setAmount(amount);
        transaction.setNote(note);
    }

    @Override
    public Transaction[] getTransactionsForMonth(String month) {
        // check if the month exists
        if (months.containsKey(month.toLowerCase())) {
            // get the month
            var monthObject = mainDataStructure.get(month.toLowerCase(null));
            var transactionsList = new ArrayList<Transaction>();
            // get all the transactions from the monthObj
            for (var category : monthObject.values()) {
                transactionsList.addAll(category);
            }
            return transactionsList.toArray(new Transaction[transactionsList.size()]);
        } else {
            // if month not exist return an empty array
            return new Transaction[0];
        }
    }

    @Override
    public Category[] getCategories() {
        // get all the categories from the map
        Category[] categoriesArray = new Category[categories.size()];
        int i = 0;
        for (Category category : categories.values()) {
            categoriesArray[i] = category;
            i++;
        }
        return categoriesArray;
    }

    @Override
    public Month[] getMonths() {
        // get all the months from the map
        Month[] monthsArray = new Month[months.size()];
        int i = 0;
        for (Month month : months.values()) {
            monthsArray[i] = month;
            i++;
        }
        return null;
    }

    @Override
    public String[][] getSummaryForMonth(String month) {
        // check if the month exists
        if (months.containsKey(month.toLowerCase())) {
            // get the month
            var monthCats = mainDataStructure.get(month.toLowerCase(null));

            // get total amount spent in each category
            var summary = new String[monthCats.size()][3];

            int i = 0;
            for (List<Transaction> trans : monthCats.values()) {

                // get the category name and type
                summary[i][0] = trans.get(i).getCategory().getName();
                summary[i][1] = trans.get(i).getCategory().getType();

                // get the total amount spent in the category
                double total = 0;
                for (var transaction : trans) {
                    total += transaction.getAmount();
                }

                // add the total amount to the summary
                summary[i][2] = "LKR " + total;
                i++;
            }
            // return the summary
            return summary;

        }
        // if month not exist return an empty array
        return new String[0][0];

    }

    @Override
    public Transaction[] getTransactionsByCategoryForMonth(String month, String categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

}
