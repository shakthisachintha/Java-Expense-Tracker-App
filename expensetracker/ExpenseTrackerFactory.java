package expensetracker;

public class ExpenseTrackerFactory {
    public static ExpenseTrackerImpl getExpenseTrackerWithDefaultData() {
        return ExpenseTrackerImpl.getExpenseTracker(true);
    }

    public static ExpenseTrackerImpl getExpenseTrackerWithoutDefaultData() {
        return ExpenseTrackerImpl.getExpenseTracker(false);
    }
}
