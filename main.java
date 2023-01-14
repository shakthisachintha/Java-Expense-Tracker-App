import expensetracker.DisplayExpenseTracker;
import expensetracker.ExpenseTrackerFactory;

public class main {

    public static void main(String[] args) {

        // get expense trcker with default data
        ExpenseTrackerFactory.getExpenseTrackerWithDefaultData();
        // execute display funtions
        DisplayExpenseTracker.viewTracker();
    }
    
}