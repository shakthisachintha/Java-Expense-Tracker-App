package category;

public class CategoryFactory {
    public static Category createIncomeCategory(String name) {
        return new IncomeCategoryImpl(name);
    }

    public static Category createExpenseCategory(String name) {
        return new ExpenseCategoryImpl(name);
    }
}
