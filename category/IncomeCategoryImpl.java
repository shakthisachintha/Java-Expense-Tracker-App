package category;

public class IncomeCategoryImpl extends Category {
    
    public IncomeCategoryImpl(String name) {
        super(name);
    }

    @Override
    public int getOperator() {
        return 1;
    }

    @Override
    public String getType() {
        return "income";
    }
}
