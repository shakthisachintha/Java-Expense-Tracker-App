package expensetracker;

import java.util.Scanner;

import category.Category;
import category.CategoryFactory;
import constants.Constants;

public class DisplayExpenseTracker {

    // create a new ExpenseTracker object
    static ExpenseTracker tracker = ExpenseTrackerFactory.getExpenseTrackerWithDefaultData();
    static String outlineColor = Constants.COLOR_YELLOW;

    public static void viewTracker() {
        showMainView();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            // this will clear the previously displayed console data
            clearConsole();

            switch (choice) {
                case 1:
                    showSpendings();
                    break;
                case 2:
                    showTransactions();
                    break;
                case 3:
                    categoryMenuExecution();
                    break;
                case 4:
                    return;
                default:
                    showMainView();
                    break;
            }
        }
    }

    public static void viewMainMenuOnUserInput() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearConsole();
        switch (choice) {
            case 1:
            default:
                showMainView();
                break;
        }
    }

    public static void showMainView() {
        viewHeader();
        viewFooter();
    }

    public static void categoryMenuExecution() {
        showCategoriesView();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearConsole();
        switch (choice) {
            case 1:
                createCategoryView(false);
                viewMainMenuOnUserInput();
                break;
            case 2:
                createCategoryView(true);
                viewMainMenuOnUserInput();
                break;
            case 3:
            default:
                showMainView();
                break;
        }
    }

    public static void showCategoriesView() {
        viewHeader();
        System.out.println("\tCategories :");
        printNewLine();
        for (Category categories : tracker.getCategories()) {
            System.out.println("\t\t" + categories.getName());
        }
        printNewLine();
        printDottedLine();
        printCategoriesViewMenu();
        printDottedLine();
    }

    public static void createCategoryView(boolean isIncomeCategory) {
        viewHeader();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Category Name : ");
        String name = scanner.nextLine();
        if (isIncomeCategory) {
            tracker.addCategory(CategoryFactory.createExpenseCategory(name));
        } else {
            tracker.addCategory(CategoryFactory.createExpenseCategory(name));
        }
        // TODO: view return value from createcategory method
        System.out.println("Category Successfully Created !");
        printDottedLine();
        printMenuOnlyWithDashBoard();
        printDottedLine();
    }

    public static void showSpendings() {
        var result = tracker.getSummaryForMonth("december");
        System.out.println("count " + result.size());
        viewHeader();
        for (DtoMonthlySummaryData data : result) {
            System.out.println(data.category.getName() + "        " + data.totalAmount);
        }
        viewMainMenuOnUserInput();
    }

    public static void showTransactions() {
        viewHeader();
        tracker.getTransactionsForMonth("december").forEach(transaction -> {
            System.out.println(transaction.getNote()+"       "+transaction.getAmount());
        }); 
        viewFooter();
    }

    public static void viewHeader() {
        printDoubleLine();
        printExpenseTrackerTitle();
        printDottedLine();
        printNewLine();
    }

    public static void viewFooter() {
        printNewLine();
        printDottedLine();
        printMainMenu();
        printDottedLine();
    }

    public static void printDottedLine() {
        System.out.println(
                "\033[" + outlineColor
                        + "m----------------------------------------------------------------------\033[0m");
    }

    public static void printDoubleLine() {
        System.out.println(
                "\033[" + outlineColor
                        + "m======================================================================\033[0m");
    }

    public static void printExpenseTrackerTitle() {
        System.out.println("                       Expense Tracker                            ");
    }

    public static void printMainMenu() {
        System.out.println("Menu    1. Spending   2. Transactions   3. Categories   4.Exit");
    }

    public static void printMenuOnlyWithDashBoard() {
        System.out.println("Menu    1. Dashboard");
    }

    public static void printCategoriesViewMenu() {
        System.out.println("Menu    1. Create Expense Category  2. Create Income Category  3.Back");
    }

    public static void printTransactionsViewMenu() {
        System.out.println("Menu    1. Create Expense Transaction  2. Create Income Transaction  3.Back");
    }

    public static void printNewLine() {
        System.out.println("\n");
    }

    public static void clearConsole() {
        System.out.print("\033c");
    }
}
