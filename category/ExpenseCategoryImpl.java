package category;

public class ExpenseCategoryImpl extends Category {
    public ExpenseCategoryImpl(String name) {
        super(name);
    }

    @Override
    public int getOperator() {
        return -1;
    }
}
