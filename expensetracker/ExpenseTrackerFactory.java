package expensetracker;

import category.Category;
import category.CategoryFactory;
import date.Date;
import transaction.Transaction;

public class ExpenseTrackerFactory {

    static boolean hasAddedDefaultData = false;

    public static ExpenseTracker getExpenseTrackerWithoutDefaultData() {
        return new ExpenseTrackerImpl();
    }

    public static ExpenseTracker getExpenseTrackerWithDefaultData() {

        ExpenseTracker expenseTracker = new ExpenseTrackerImpl();
        if (!hasAddedDefaultData) {
            hasAddedDefaultData = true;

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

            // expenseTracker.newMonth("December", 35000);

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

            Date date6 = new Date(2023, 01, 01);
            Date date7 = new Date(2023, 01, 03);
            Date date8 = new Date(2023, 01, 7);
            Date date9 = new Date(2023, 01, 10);
            Date date10 = new Date(2023, 01, 13);
            Date date11 = new Date(2023, 02, 13);

            // add default transactions
            Transaction t1 = new Transaction(1000, entertainment, "My Birthday treat for family members", date1);
            Transaction t2 = new Transaction(5000, fuel, "Filled my car up with petrol", date2);
            Transaction t3 = new Transaction(12400, entertainment, "Had party with friends", date3);
            Transaction t4 = new Transaction(23000, shopping, "Shopping with the family", date4);
            Transaction t5 = new Transaction(9500, travel, "Trip to yala with the friends", date5);
            Transaction t6 = new Transaction(115000, salary, "Salary received", date5);
            t6.setRecurring(true);
            t5.setRecurring(true);

            Transaction t7 = new Transaction(4800, eatingOut, "Dinner from PizzaHut", date6);
            Transaction t8 = new Transaction(10000, fuel, "Filled my car up with petrol", date7);
            Transaction t9 = new Transaction(3000, kids, "Bought Toys for kids", date8);
            Transaction t10 = new Transaction(1400, sports, "Badminton Practices", date9);
            Transaction t11 = new Transaction(2500, sports, "Swimming Practices", date10);

            Transaction t12 = new Transaction(8000, credit, "Paid credit to Nuwan", date11);

            // add all the default transactions to expense tracker transaction list
            expenseTracker.addTransaction(t1);
            expenseTracker.addTransaction(t2);
            expenseTracker.addTransaction(t3);
            expenseTracker.addTransaction(t4);
            expenseTracker.addTransaction(t5);
            expenseTracker.addTransaction(t6);

            expenseTracker.addTransaction(t7);
            expenseTracker.addTransaction(t8);
            expenseTracker.addTransaction(t9);
            expenseTracker.addTransaction(t10);
            expenseTracker.addTransaction(t11);

            expenseTracker.addTransaction(t12);

            // expenseTracker.newMonth("January", 55000);
        }
        return expenseTracker;
    }
}
