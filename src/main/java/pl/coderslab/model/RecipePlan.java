package pl.coderslab.model;

public class RecipePlan {
    private int id;
    private int recipeId;
    private int mealNameId;
    private int dayName;
    private int planId;

    public RecipePlan(int recipeId, int mealNameId, int dayName, int planId) {
        this.recipeId = recipeId;
        this.mealNameId = mealNameId;
        this.dayName = dayName;
        this.planId = planId;
    }

    public RecipePlan() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getMealNameId() {
        return mealNameId;
    }

    public void setMealNameId(int mealNameId) {
        this.mealNameId = mealNameId;
    }

    public int getDayName() {
        return dayName;
    }

    public void setDayName(int dayName) {
        this.dayName = dayName;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }
}
