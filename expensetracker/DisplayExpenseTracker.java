package expensetracker;

import java.util.Scanner;

import category.Category;
import category.CategoryFactory;
import constants.Constants;

public class DisplayExpenseTracker {

    // create a new ExpenseTracker object
    static ExpenseTracker tracker = ExpenseTrackerFactory.getExpenseTrackerWithDefaultData();

    static String outlineColor = Constants.COLOR_WHITE;

    public static void viewTracker() {
        showMainView();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = scanner.nextInt();
            scanner.nextLine();
            // this will clear the previously displayed console data
            System.out.print("\033c");

            switch (choice) {
                case 1:
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
        System.out.print("\033c");
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
        System.out.print("\033c");
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
        for (Category categories : tracker.getCategories()) {
            System.out.println("    " + categories.getName());
        }
        printDottedLine();
        showCategoriesViewMenu();
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
        createCategoryViewMenu();
        printDottedLine();
    }

    public static void showTransactions() {
        // for (Transaction transactionsForMonth :
        // tracker.getTransactionsForMonth("december")) {
        // System.out.println(
        // transactionsForMonth.getCategory().getName() + " " +
        // transactionsForMonth.getAmount());
        // }
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

    public static void createCategoryViewMenu() {
        System.out.println("Menu    1. Dashboard");
    }

    public static void showCategoriesViewMenu() {
        System.out.println("Menu    1. Create Expense Category  2. Create Income Category  3.Back");
    }

    public static void printNewLine() {
        System.out.println("\n");
    }
}
