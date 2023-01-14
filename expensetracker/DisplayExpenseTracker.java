package expensetracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import category.Category;
import category.CategoryFactory;
import constants.Constants;
import date.Date;
import transaction.Transaction;
import types.TransactionType;

public class DisplayExpenseTracker {

    // create a new ExpenseTracker object
    static ExpenseTracker tracker = ExpenseTrackerFactory.getExpenseTrackerWithDefaultData();
    static String outlineColor = Constants.COLOR_YELLOW;

    public static void viewTracker() {
        displayMainView();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            // this will clear the previously displayed console data
            clearConsole();

            switch (choice) {
                case 1:
                    displayMainView();
                    break;
                case 2:
                    transactionMenuExecution();
                    break;
                case 3:
                    categoryMenuExecution();
                    break;
                case 4:
                default:
                    displayMainView();
                    return;
            }
        }
    }

    public static void displayMainView() {
        viewHeader();
        showSpendings();
        viewFooter();
    }

    public static void transactionMenuExecution() {
        displayTransactionListView();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            clearConsole();
            switch (choice) {
                case 1:
                    displayTransactionCreateView();
                    break;
                case 2:
                    displayTransactionListView();
                    break;
                case 3:
                    displayTransactionUpdateView();
                    break;
                case 4:
                    displayTransactionDeleteView();
                    break;
                default:
                    displayMainView();
                    return;
            }
        }
    }

    public static void displayTransactionCreateView() {
        viewHeader();
        Scanner scanner = new Scanner(System.in);
        List<Category> categoryList = new ArrayList<Category>();
        System.out.print("\tEnter Transaction Type: ");
        System.out.println("\t   1. " + TransactionType.EXPENSE + "   2. " + TransactionType.INCOME);
        System.out.print("\t");
        int trType = scanner.nextInt();
        while (true) {
            if (trType == 1) {
                categoryList = tracker.getCategories(TransactionType.EXPENSE);
                break;
            } else if (trType == 2) {
                categoryList = tracker.getCategories(TransactionType.INCOME);
                break;
            } else {
                System.out.println("\tInvalid Transaction Type ! Enter Again .");
                trType = scanner.nextInt();
            }
        }
        System.out.println("\n\tEnter Category ID From Below List: \n");
        System.out.println("\t   ID\t\033[" + outlineColor + "m|\033[0m Name");
        System.out.println("\033[" + outlineColor + "m\t   ---------------------\033[0m");
        for (Category categories : categoryList) {
            System.out.println(
                    "\t   " + categories.getId() + "\t\033[" + outlineColor + "m|\033[0m " + categories.getName());
        }
        scanner.nextLine();
        System.out.print("\t   ");
        String catId = scanner.nextLine();
        Category category = tracker.getCategoryById(catId);
        System.out.print("\n\tEnter Transaction Note:  ");
        String trNote = scanner.nextLine();
        System.out.print("\n\tEnter Transaction Amount:  ");
        double trAmount = scanner.nextDouble();
        System.out.print("\n\tEnter Transaction Date (yyyy-MM-dd):  ");
        scanner.nextLine();
        String trDate = scanner.nextLine();
        LocalDate date = LocalDate.parse(trDate);
        Date customeDate = new Date(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        Transaction transaction = new Transaction(trAmount, category, trNote, customeDate);
        tracker.addTransaction(transaction);
        System.out.println("\n\tTransaction Successfully Added !");
        transactionMenuFooter();
    }

    public static void displayTransactionDeleteView() {
        viewTransactionsListTable();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n\tEnter Transaction ID To Delete : ");
        String id = scanner.nextLine();
        tracker.deleteTransaction(id);
        System.out.println("\n\tTransaction Successfully Deleted !");
        transactionMenuFooter();
    }

    public static void displayTransactionUpdateView() {
        viewTransactionsListTable();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n\tEnter Transaction ID To Update : ");
        String id = scanner.nextLine();
        System.out.print("\n\tEnter Transaction Note : ");
        String note = scanner.nextLine();
        System.out.print("\n\tEnter Transaction Amount : ");
        double amount = scanner.nextDouble();
        tracker.updateTransaction(id, note, amount);
        System.out.println("\n\tTransaction Successfully Updated !");
        transactionMenuFooter();
    }

    public static void categoryMenuExecution() {
        displayCategoryListView();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            clearConsole();
            switch (choice) {
                case 1:
                    displayCategoryCreateView(false);
                    break;
                case 2:
                    displayCategoryCreateView(true);
                    break;
                case 3:
                    displayCategoryListView();
                    break;
                case 4:
                default:
                    displayMainView();
                    return;
            }
        }
    }

    public static void displayCategoryListView() {
        viewHeader();
        System.out.println("\t\033[" + Constants.COLOR_BLUE + "mCategory List :\n\033[0m");
        for (Category categories : tracker.getCategories()) {
            System.out.println("\t   " + categories.getName());
        }
        categoryMenuFooter();
    }

    public static void displayCategoryCreateView(boolean isIncomeCategory) {
        viewHeader();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\tEnter Category Name : ");
        String name = scanner.nextLine();
        if (isIncomeCategory) {
            tracker.addCategory(CategoryFactory.createIncomeCategory(name));
        } else {
            tracker.addCategory(CategoryFactory.createExpenseCategory(name));
        }
        // TODO: view return value from createcategory method
        System.out.println("\n\tCategory Successfully Created !");
        categoryMenuFooter();
    }

    public static void showSpendings() {
        String monthName = LocalDate.now().getMonth().name();
        var result = tracker.getSummaryForMonth(monthName);
        if (result.size() > 0) {
            double totalIncomeAmount = 0;
            for (DtoMonthlySummaryData value : result) {
                if (value.category.getType() == TransactionType.INCOME)
                    totalIncomeAmount += value.totalAmount;
            }
            System.out.println(String.format("%-35s %s", "\t\033[" + Constants.COLOR_PURPLE + "mIncome\033[0m",
                    "\033[" + Constants.COLOR_GREEN + "m\t   Rs. " + totalIncomeAmount + "\033[0m"));
            for (DtoMonthlySummaryData data : result) {
                if (data.category.getType() == TransactionType.INCOME)
                    System.out.println(
                            String.format("%-35s %s", "\t   " + data.category.getName(), "  Rs. " + data.totalAmount));
            }

            double totalExpenseAmount = 0;
            for (DtoMonthlySummaryData value : result) {
                if (value.category.getType() == TransactionType.EXPENSE)
                    totalExpenseAmount += value.totalAmount;
            }
            System.out.println(String.format("%-35s %s", "\t\033[" + Constants.COLOR_PURPLE + "mExpense\033[0m",
                    "\033[" + Constants.COLOR_RED + "m\t   Rs. " + totalExpenseAmount + "\033[0m"));
            for (DtoMonthlySummaryData data : result) {
                if (data.category.getType() == TransactionType.EXPENSE)
                    System.out.println(
                            String.format("%-35s %s", "\t   " + data.category.getName(), "  Rs. " + data.totalAmount));
            }
        } else {
            System.out.println("                       No Transactions to Display                            ");
        }
    }

    public static void displayTransactionListView() {
        viewTransactionsListTable();
        transactionMenuFooter();
    }

    public static void viewTransactionsListTable() {
        String monthName = LocalDate.now().getMonth().name();
        List<Transaction> tList = tracker.getTransactionsForMonth(monthName);
        tList.sort((a, b) -> a.getType().compareTo(b.getType()));
        viewHeader();
        System.out.println("\033[" + Constants.COLOR_BLUE + "m\tTransaction List : \033[0m\n");
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

    public static void transactionMenuFooter() {
        printNewLine();
        printDottedLine();
        printTransactionsViewMenu();
        printDottedLine();
    }

    public static void categoryMenuFooter() {
        printNewLine();
        printDottedLine();
        printCategoriesViewMenu();
        printDottedLine();
    }

    public static void printDottedLine() {
        System.out.println(
                "\033[" + outlineColor
                        + "m---------------------------------------------------------------------------------\033[0m");
    }

    public static void printDoubleLine() {
        System.out.println(
                "\033[" + outlineColor
                        + "m=================================================================================\033[0m");
    }

    public static void printExpenseTrackerTitle() {
        System.out.println("                               Expense Tracker                            ");
    }

    public static void printMainMenu() {
        System.out.println("Menu    1. Spending   2. Transactions   3. Categories   4.Exit");
    }

    public static void printMenuOnlyWithDashBoard() {
        System.out.println("Menu    1. Dashboard");
    }

    public static void printCategoriesViewMenu() {
        System.out.println("Menu    1. Add Expense Category  2. Add Income Category  3. View  4. Main Menu");
    }

    public static void printTransactionsViewMenu() {
        System.out.println("Menu    1. Add Transaction  2. View  3. Update  4. Delete  5. Main Menu");
    }

    public static void printNewLine() {
        System.out.println("\n");
    }

    public static void clearConsole() {
        System.out.print("\033c");
    }
}
