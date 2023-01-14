package category;

public class ExpenseCategoryImpl extends Category {
    public ExpenseCategoryImpl(String name) {
        super(name);
    }

    @Override
    public int getOperator() {
        return -1;
    }

    @Override
    public String getType() {
        return "expense";
    }
}
