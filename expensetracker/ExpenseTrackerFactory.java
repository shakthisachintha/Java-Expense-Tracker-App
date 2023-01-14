package expensetracker;
import category.Category;
import category.CategoryFactory;
import date.Date;
import transaction.Transaction;

public class ExpenseTrackerFactory {
    public static ExpenseTracker getExpenseTrackerWithoutDefaultData() {
        return ExpenseTrackerImpl.getExpenseTracker();
    }

    public static ExpenseTracker getExpenseTrackerWithDefaultData() {

        ExpenseTracker expenseTracker = ExpenseTrackerImpl.getExpenseTracker();

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

        expenseTracker.newMonth("December", 35000);

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
        Date date1 = new Date(2022, 12, 05);
        Date date2 = new Date(2022, 12, 07);
        Date date3 = new Date(2022, 12, 12);
        Date date4 = new Date(2022, 12, 18);
        Date date5 = new Date(2022, 12, 27);

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
