package expensetracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import category.Category;
import category.CategoryFactory;
import month.Month;
import transaction.Transaction;
import transaction.types.TransactionType;

public class ExpenseTrackerImpl implements ExpenseTracker {

    // Map<MonthName, Map<CategoryId, List<Transaction>>>
    private Map<String, Map<String, List<Transaction>>> mainDataStructure = new HashMap<>();
    private Map<String, Transaction> recurringTransactions = new HashMap<>();
    private Map<String, Category> categories = new HashMap<>();
    // Map<MonthName, Month>
    private Map<String, Month> months = new HashMap<>();
    private Map<String, Transaction> transactions = new HashMap<>();
    private static boolean hasSetDefaultData = false;

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
    public List<Transaction> getTransactionsForMonth(String month) {
        // check if the month exists
        if (months.containsKey(month.toLowerCase())) {
            // get the month
            var monthObject = mainDataStructure.get(month.toLowerCase(null));
            var transactionsList = new ArrayList<Transaction>();
            // get all the transactions from the monthObj
            for (var category : monthObject.values()) {
                transactionsList.addAll(category);
            }
            return transactionsList;
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
        // check if the month exists
        if (months.containsKey(month.toLowerCase())) {
            // get the month
            var monthCats = mainDataStructure.get(month.toLowerCase(null));

            // get total amount spent in each category
            var summary = new ArrayList<DtoMonthlySummaryData>();

            int i = 0;
            for (List<Transaction> trans : monthCats.values()) {

                DtoMonthlySummaryData summaryRow = new DtoMonthlySummaryData();
                // get the category name and type
                summaryRow.category = trans.get(i).getCategory();

                // get the total amount spent in the category
                double total = 0;
                for (var transaction : trans) {
                    total += transaction.getAmount();
                }

                // add the total amount to the summary
                summaryRow.totalAmount = total;

                // add the summary row to the summary
                summary.add(summaryRow);
                i++;
            }
            // return the summary
            return summary;

        }
        // if month not exist return an empty list
        return new ArrayList<DtoMonthlySummaryData>();
    }

    @Override
    public DtoFullDetailsForMonth getFullDetailsForMonth(String month) {
        String monthKey  = month.toLowerCase();
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
                categoryTransactions.transactions = getActiveTransactionForCatForMonth(monthKey, categoryKey);

                // add total income and expenses
                for (var transaction : categoryTransactions.transactions){
                    if (transaction.getType() == TransactionType.INCOME){
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

    private List<Transaction> getActiveTransactionForCatForMonth (String month, String categoryId){
        String monthKey = month.toLowerCase();
        String categoryKey = categoryId.toLowerCase();

        if (mainDataStructure.containsKey(categoryKey) && mainDataStructure.get(categoryKey).containsKey(monthKey)){
            var transactions = mainDataStructure.get(categoryKey).get(monthKey);
            var activeTransactions = new ArrayList<Transaction>();
            for (var transaction : transactions){
                if (transaction.isActive()){
                    activeTransactions.add(transaction);
                }
            }
            return activeTransactions;
        }
        return new ArrayList<Transaction>();
    }

}
