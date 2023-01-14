package expensetracker;

import java.util.*;

import category.Category;
import month.Month;
import transaction.Transaction;
import types.TransactionType;

public class ExpenseTrackerImpl implements ExpenseTracker {

    // Main data mapping structure
    // Map<MonthName, Map<CategoryId, List<TransactionID>>>
    private Map<String, Map<String, List<String>>> mainDataStructure = new HashMap<>();

    // Main data storage
    // Map<MonthName, Month>
    private Map<String, Month> months = new HashMap<>();
    private Map<String, Transaction> transactions = new HashMap<>();
    private Map<String, Transaction> recurringTransactions = new HashMap<>();
    private Map<String, Category> categories = new HashMap<>();

    // Singleton pattern
    private static ExpenseTrackerImpl expenseTracker;

    private ExpenseTrackerImpl() {
    }

    public static ExpenseTrackerImpl getExpenseTracker() {
        if (expenseTracker == null) {
            expenseTracker = new ExpenseTrackerImpl();
        }
        return expenseTracker;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.isRecurring()) {
            addRecurringTransaction(transaction);
        } else {
            addNonRecurringTransaction(transaction);
        }
        addTransactionToMainDataStructure(transaction);
    }

    private void addTransactionToMainDataStructure(Transaction transaction) {
        // get the month key
        String monthKey = transaction.getDate().getMonthName().toLowerCase();

        // get the category key
        String categoryKey = transaction.getCategory().getId().toLowerCase();

        // get the list of transactions for the month and category
        var transactions = mainDataStructure.get(monthKey).get(categoryKey);

        // add the transaction to the list
        transactions.add(transaction.getId());

        // add the transaction to the main data structure
        mainDataStructure.get(monthKey).put(categoryKey, transactions);
    }

    public void addCategory(Category category) {
        // get the category key
        var categoryKey = category.getId().toLowerCase();

        // get all month keys
        var monthKeys = months.keySet();
        
        // for each month
        for (var monthKey : monthKeys) {
            // add the category to the main data structure
            mainDataStructure.get(monthKey).put(categoryKey, new ArrayList<>());
        }

        // add the category to the categories map
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
        // month key
        String monthKey = month.toLowerCase();
        Month mon = new Month(month, budget);
        addMonthToMainDataStructure(mon);
        months.put(monthKey, mon);
    }

    @Override
    public void newMonth(String monthName) {
        // month key
        String monthKey = monthName.toLowerCase();

        // create new month object
        Month mon = new Month(monthName);

        // add the month to the main data structure
        addMonthToMainDataStructure(mon);

        // add to the months map
        months.put(monthKey, mon);
    }

    private void addMonthToMainDataStructure (Month month) {
        // month key
        String monthKey = month.getName().toLowerCase();
        
        // add to main data structure
        mainDataStructure.put(monthKey, new HashMap<String, List<String>>());
    }

    @Override
    public void deleteTransaction(String transactionId) {
        // transaction key
        String transactionKey = transactionId.toLowerCase();

        // get the transaction
        Transaction transaction = transactions.containsKey(transactionKey) ? transactions.get(transactionKey)
                : recurringTransactions.get(transactionKey);

        // if transaction is recurring
        if (transaction.isRecurring()) {
            // mark the transaction as inactive
            recurringTransactions.remove(transactionKey).setActive(false);
            ;

        } else {
            // get the month key
            String monthKey = transaction.getDate().getMonthName().toLowerCase();

            // get the category key
            String categoryKey = transaction.getCategory().getId().toLowerCase();

            // get the list of transactions for the month and category
            var transactions = mainDataStructure.get(monthKey).get(categoryKey);

            // remove the transaction from the list
            transactions.remove(transactionKey);

            // delete the transaction
            this.transactions.remove(transactionKey);
        }

        transactions.get(transactionId).setActive(false);
    }

    @Override
    public void updateTransaction(String transactionId, String note, double amount) {
        Transaction transaction = transactions.get(transactionId);
        transaction.setAmount(amount);
        transaction.setNote(note);
    }

    @Override
    public List<Transaction> getTransactionsForMonth(String month) {
        // month key
        String monthKey = month.toLowerCase();

        // check if the month exists
        if (months.containsKey(monthKey)) {
            // get category keys for the month
            var categoryKeys = mainDataStructure.get(monthKey).keySet();

            // get all the transactions for the month
            var transactions = new ArrayList<Transaction>();

            // loop through the categories
            for (String categoryKey : categoryKeys) {
                // get the transactions for the category
                var categoryTransactions = this.getMonthlyTransactionForCategory(monthKey, categoryKey);

                // add the transactions to the list
                transactions.addAll(categoryTransactions);
            }
            return transactions;
        } else {
            // if month not exist return an empty array
            return new ArrayList<Transaction>();
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
    public List<DtoMonthlySummaryData> getSummaryForMonth(String month) {
        // month key
        String monthKey = month.toLowerCase();

        // check if the month exists
        if (months.containsKey(monthKey)) {
            // get the categories for the month
            var categoryKeys = mainDataStructure.get(monthKey).keySet();

            // get total amount spent in each category
            var summary = new ArrayList<DtoMonthlySummaryData>();

            // iterate over the categoryKeys
            for (String categoryKey : categoryKeys) {
                // get the transactions for the category
                var categoryTransactions = this.getMonthlyTransactionForCategory(monthKey, categoryKey);

                // get the total amount spent in the category
                double total = 0;
                for (var transaction : categoryTransactions) {
                    total += transaction.getAmount();
                }

                // get the category name and type
                var category = categories.get(categoryKey);

                // add the total amount to the summary
                var summaryRow = new DtoMonthlySummaryData();
                summaryRow.category = category;
                summaryRow.totalAmount = total;

                // add the summary row to the summary
                summary.add(summaryRow);
            }
            // return the summary
            return summary;

        }
        // if month not exist return an empty list
        return new ArrayList<DtoMonthlySummaryData>();
    }

    @Override
    public DtoFullDetailsForMonth getFullDetailsForMonth(String month) {
        String monthKey = month.toLowerCase();
        // check if the month exists
        if (months.containsKey(monthKey)) {
            // get the month
            var monthCats = mainDataStructure.get(monthKey);

            // get total amount spent in each category
            var summary = new ArrayList<CategoryTransactions>();

            // get category key
            var categoryKeys = monthCats.keySet();

            // total income and expenses
            double totalIncome = 0;
            double totalExpenses = 0;
            double balance = 0;

            // get the transactions for each category
            for (var categoryKey : categoryKeys) {
                // get the category
                var category = categories.get(categoryKey);

                // create a new DtoCategoryTransactions object
                var categoryTransactions = new CategoryTransactions();
                categoryTransactions.category = category;
                categoryTransactions.transactions = getMonthlyTransactionForCategory(monthKey, categoryKey);

                // add total income and expenses
                for (var transaction : categoryTransactions.transactions) {
                    if (transaction.getType() == TransactionType.INCOME) {
                        totalIncome += transaction.getAmount();
                    } else {
                        totalExpenses += transaction.getAmount();
                    }
                    balance += transaction.getAmountForTotal();
                }

                // add the categoryTransactions to the summary
                summary.add(categoryTransactions);
            }

            // create a new DtoFullDetailsForMonth object
            var fullDetails = new DtoFullDetailsForMonth();
            fullDetails.month = months.get(monthKey);
            fullDetails.categoryTransactions = summary;
            fullDetails.totalIncome = totalIncome;
            fullDetails.totalExpense = totalExpenses;
            fullDetails.totalBalance = balance;

            // return the summary
            return fullDetails;
        }

        // if month not exist return an empty list
        return new DtoFullDetailsForMonth();
    }

    private List<Transaction> getMonthlyTransactionForCategory(String month, String categoryId) {
        String monthKey = month.toLowerCase();
        String categoryKey = categoryId.toLowerCase();

        if (mainDataStructure.containsKey(categoryKey) && mainDataStructure.get(categoryKey).containsKey(monthKey)) {
            var transactionKeys = mainDataStructure.get(categoryKey).get(monthKey);
            var activeTransactions = new ArrayList<Transaction>();

            for (var transactionKey : transactionKeys) {
                var transaction = transactions.containsKey(transactionKey) ? transactions.get(transactionKey)
                        : recurringTransactions.get(transactionKey);

                activeTransactions.add(transaction);
            }
            return activeTransactions;
        }
        return new ArrayList<Transaction>();
    }

}
