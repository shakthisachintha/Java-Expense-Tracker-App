package expensetracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import category.Category;
import category.CategoryFactory;
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
    private static boolean hasSetDefaultData = false;

    // Singleton pattern
    private static ExpenseTrackerImpl expenseTracker;

    private ExpenseTrackerImpl() {
    }

    public static ExpenseTrackerImpl getExpenseTracker(Boolean defaultData) {
        if (!defaultData) {
            if (expenseTracker == null) {
                return new ExpenseTrackerImpl();
            } else {
                return expenseTracker;
            }
        } else {
            if (!hasSetDefaultData) {
                if (expenseTracker == null) {
                    expenseTracker = new ExpenseTrackerImpl();
                }
                return setDefaultTrackerData();
            } else {
                return expenseTracker;
            }
        }

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

    private static ExpenseTrackerImpl setDefaultTrackerData() {

        hasSetDefaultData = true;

        // add default categories
        Category salary = CategoryFactory.createIncomeCategory("Salary");
        Category eatingOut = CategoryFactory.createExpenseCategory("Eating Out");
        Category credit = CategoryFactory.createExpenseCategory("Credit");
        Category entertainment = CategoryFactory.createExpenseCategory("Entertainment");
        Category fuel = CategoryFactory.createExpenseCategory("Fuel");
        Category general = CategoryFactory.createExpenseCategory("General");
        Category holidays = CategoryFactory.createExpenseCategory("Holidays");
        Category kids = CategoryFactory.createExpenseCategory("Kids");
        Category shopping = CategoryFactory.createExpenseCategory("Shopping");
        Category sports = CategoryFactory.createExpenseCategory("Sports");
        Category travel = CategoryFactory.createExpenseCategory("Travel");

        expenseTracker.newMonth("January", 35000);

        // add default categories to the expense tracker
        expenseTracker.addCategory(salary);
        expenseTracker.addCategory(eatingOut);
        expenseTracker.addCategory(credit);
        expenseTracker.addCategory(entertainment);
        expenseTracker.addCategory(fuel);
        expenseTracker.addCategory(general);
        expenseTracker.addCategory(holidays);
        expenseTracker.addCategory(kids);
        expenseTracker.addCategory(shopping);
        expenseTracker.addCategory(sports);
        expenseTracker.addCategory(travel);

        // add sample dates
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();
        Date date5 = new Date();
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-05");
            date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-07");
            date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-12");
            date4 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-18");
            date5 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-27");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // add default transactions
        Transaction t1 = new Transaction(1000, eatingOut, "My Birthday treat for family members", date1);
        Transaction t2 = new Transaction(5000, fuel, "Filled my car up with petrol", date2);
        Transaction t3 = new Transaction(12400, entertainment, "Had party with friends", date3);
        Transaction t4 = new Transaction(23000, shopping, "Shopping with the family", date4);
        Transaction t5 = new Transaction(9500, eatingOut, "Trip to yala with the friends", date5);
        Transaction t6 = new Transaction(115000, salary, "December Salary received", date5);

        // add all the default transactions to expense tracker transaction list
        expenseTracker.addTransaction(t1);
        expenseTracker.addTransaction(t2);
        expenseTracker.addTransaction(t3);
        expenseTracker.addTransaction(t4);
        expenseTracker.addTransaction(t5);
        expenseTracker.addTransaction(t6);

        return expenseTracker;
    }

}
