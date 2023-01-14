package expensetracker;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import category.Category;
import category.CategoryFactory;
import constants.Constants;
import transaction.Transaction;
import types.TransactionType;

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
                    showMainView();
                    break;
                case 2:
                    transactionMenuExecution();
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
        showSpendings();
        viewFooter();
    }

    public static void transactionMenuExecution() {
        showTransactions();
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
        String monthName = LocalDate.now().getMonth().name();
        var result = tracker.getSummaryForMonth(monthName);
        if (result.size() > 0) {
            double totalExpenseAmount = 0;
            for (DtoMonthlySummaryData value : result) {
                if (value.category.getType() == TransactionType.EXPENSE)
                    totalExpenseAmount += value.totalAmount;
            }
            System.out.println(String.format("%-35s %s", "\tExpense",
                    "\033[" + Constants.COLOR_RED + "mRs. " + totalExpenseAmount + "\033[0m"));
            for (DtoMonthlySummaryData data : result) {
                if (data.category.getType() == TransactionType.EXPENSE)
                    System.out.println(
                            String.format("%-35s %s", "\t   " + data.category.getName(), "  Rs. " + data.totalAmount));
            }
        } else {
            System.out.println("                       No Transactions to Display                            ");
        }
    }

    public static void showTransactions() {
        String monthName = LocalDate.now().getMonth().name();
        List<Transaction> tList = tracker.getTransactionsForMonth(monthName);
        tList.sort((a, b) -> a.getType().compareTo(b.getType()));
        viewHeader();
        System.out.println("\tTransaction List : \n");
        System.out.println(
                String.format("%-60s %s", "\t   " + "ID   " + "\033[" + outlineColor + "m|\033[0m Note",
                        "\033[" + outlineColor + "m |\033[0m Amount"));
        System.out.println("\033[" + outlineColor + "m"
                + "\t   ---------------------------------------------------------------\033[0m"); // TODO: character
                                                                                                  // length must be less
                                                                                                  // than 40
        tList.forEach(transaction -> {
            if (transaction.getType() == TransactionType.INCOME) {
                System.out.println(
                        String.format("%-60s %s",
                                "\t   " + transaction.getId() + " \033[" + outlineColor + "m|\033[0m "
                                        + transaction.getNote(),
                                " \033[" + outlineColor
                                        + "m|\033[0m \033[" + Constants.COLOR_GREEN
                                        + "mRs. " + transaction.getAmount() + "\033[0m"));
            } else {
                System.out.println(
                        String.format("%-60s %s",
                                "\t   " + transaction.getId() + " \033[" + outlineColor + "m|\033[0m "
                                        + transaction.getNote(),
                                " \033[" + outlineColor
                                        + "m|\033[0m \033[" + Constants.COLOR_RED
                                        + "mRs. " + transaction.getAmount() + "\033[0m"));
            }
        });
        printNewLine();
        printDottedLine();
        printTransactionsViewMenu();
        printDottedLine();
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
                        + "m-----------------------------------------------------------------------------\033[0m");
    }

    public static void printDoubleLine() {
        System.out.println(
                "\033[" + outlineColor
                        + "m=============================================================================\033[0m");
    }

    public static void printExpenseTrackerTitle() {
        System.out.println("                             Expense Tracker                            ");
    }

    public static void printMainMenu() {
        System.out.println("Menu    1. Spending   2. Transactions   3. Categories   4.Exit");
    }

    public static void printMenuOnlyWithDashBoard() {
        System.out.println("Menu    1. Dashboard");
    }

    public static void printCategoriesViewMenu() {
        System.out.println("Menu    1. Add Expense Category  2. Add Income Category  3. Back To Main Menu");
    }

    public static void printTransactionsViewMenu() {
        System.out.println("Menu    1. Add Expense  2. Add Income  3. Delete  4. Back To Main Menu");
    }

    public static void printNewLine() {
        System.out.println("\n");
    }

    public static void clearConsole() {
        System.out.print("\033c");
    }
}
