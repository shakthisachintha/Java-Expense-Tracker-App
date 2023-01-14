import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import category.Category;
import category.CategoryFactory;
import expensetracker.ExpenseTracker;
import expensetracker.ExpenseTrackerImpl;
import transaction.Transaction;

public class main {

    public static void main(String[] args) {

        // create a new ExpenseTracker object
        ExpenseTracker tracker = ExpenseTrackerImpl.getExpenseTracker();

        // add default categories
        Category salary = CategoryFactory.createIncomeCategory("Salary");
        Category eatingOut = CategoryFactory.createExpenseCategory("Eating Out");
        Category creadit = CategoryFactory.createExpenseCategory("Credit");
        Category entertainment = CategoryFactory.createExpenseCategory("Entertainment");
        Category fuel = CategoryFactory.createExpenseCategory("Fuel");
        Category general = CategoryFactory.createExpenseCategory("General");
        Category holidays = CategoryFactory.createExpenseCategory("Holidays");
        Category kids = CategoryFactory.createExpenseCategory("Kids");
        Category shopping = CategoryFactory.createExpenseCategory("Shopping");
        Category sports = CategoryFactory.createExpenseCategory("Sports");
        Category travel = CategoryFactory.createExpenseCategory("Travel");

        // add default categories to the expense tracker
        tracker.addCategory(salary);
        tracker.addCategory(eatingOut);
        tracker.addCategory(creadit);
        tracker.addCategory(entertainment);
        tracker.addCategory(fuel);
        tracker.addCategory(general);
        tracker.addCategory(holidays);
        tracker.addCategory(kids);
        tracker.addCategory(shopping);
        tracker.addCategory(sports);
        tracker.addCategory(travel);

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
            date5 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-12-23");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // add default transactions
        Transaction t1 = new Transaction(1000, eatingOut, "My Birthday treat for family members", date1);
        Transaction t2 = new Transaction(5000, fuel, "Filled my car up with petrol", date2);
        Transaction t3 = new Transaction(12400, entertainment, "Had party with friends", date3);
        Transaction t4 = new Transaction(23000, shopping, "Shopping with the family", date4);
        Transaction t5 = new Transaction(9500, eatingOut, "Trip to yala with the friends", date5);
        Transaction t6 = new Transaction(115000, eatingOut, "December Salary received", date5);

        // add all the default transactions to expense tracker transaction list
        tracker.addTransaction(t1);
        tracker.addTransaction(t2);
        tracker.addTransaction(t3);
        tracker.addTransaction(t4);
        tracker.addTransaction(t5);
        tracker.addTransaction(t6);

        // Main View
        System.out.println("\033[33m==================================================================\033[0m");
        System.out.println("                       Expense Tracker                            ");
        System.out.println("\033[33m------------------------------------------------------------------\033[0m");
        System.out.println("Expense                                                            ");
        // for (String string : tracker.getTransactionsForMonth(string)) {
        //     System.out.println("    ");   
        // }
    }

}