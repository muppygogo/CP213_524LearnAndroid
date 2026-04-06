package com.example.randomapp.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\'\u001a\u00020(J\u000e\u0010)\u001a\u00020(2\u0006\u0010*\u001a\u00020\bJ\u000e\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020\nJ\u000e\u0010-\u001a\u00020(2\u0006\u0010.\u001a\u00020/J\u000e\u00100\u001a\u00020(2\u0006\u0010.\u001a\u00020/J\u000e\u0010&\u001a\u00020(2\u0006\u00101\u001a\u00020/J\u000e\u00102\u001a\u00020(2\u0006\u0010*\u001a\u00020\bJ\u000e\u00103\u001a\u00020(2\u0006\u0010,\u001a\u00020\nJ\u0006\u00104\u001a\u00020(J\u0006\u00105\u001a\u00020(J\u000e\u00106\u001a\u00020(2\u0006\u00107\u001a\u00020\u000fJ\u000e\u00108\u001a\u00020(2\u0006\u00107\u001a\u00020\u000fJ\u000e\u00109\u001a\u00020(2\u0006\u0010*\u001a\u00020\bJ\u000e\u0010:\u001a\u00020(2\u0006\u0010,\u001a\u00020\nR\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0019\u0010\u001c\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0019\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00110\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u001d\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017R\u001d\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2 = {"Lcom/example/randomapp/viewmodel/FoodViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/example/randomapp/data/repository/FoodRepository;", "(Lcom/example/randomapp/data/repository/FoodRepository;)V", "_allCategories", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/example/randomapp/data/local/entity/FoodCategory;", "_allMeals", "Lcom/example/randomapp/data/local/entity/MealEntity;", "_categorySearchResults", "_currentCategory", "_currentMeal", "_errorMessage", "", "_isLoading", "", "_mealSearchResults", "_mealsByCategory", "allCategories", "Lkotlinx/coroutines/flow/StateFlow;", "getAllCategories", "()Lkotlinx/coroutines/flow/StateFlow;", "allMeals", "getAllMeals", "categorySearchResults", "getCategorySearchResults", "currentCategory", "getCurrentCategory", "currentMeal", "getCurrentMeal", "errorMessage", "getErrorMessage", "isLoading", "mealSearchResults", "getMealSearchResults", "mealsByCategory", "getMealsByCategory", "clearError", "", "deleteCategory", "foodCategory", "deleteMeal", "meal", "getCategoryById", "id", "", "getMealById", "categoryId", "insertCategory", "insertMeal", "loadAllCategories", "loadAllMeals", "searchCategories", "query", "searchMeals", "updateCategory", "updateMeal", "app_debug"})
public final class FoodViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.randomapp.data.repository.FoodRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.randomapp.data.local.entity.FoodCategory>> _allCategories = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.FoodCategory>> allCategories = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.randomapp.data.local.entity.FoodCategory>> _categorySearchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.FoodCategory>> categorySearchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.randomapp.data.local.entity.FoodCategory> _currentCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.randomapp.data.local.entity.FoodCategory> currentCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> _allMeals = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> allMeals = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> _mealSearchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> mealSearchResults = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> _mealsByCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> mealsByCategory = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.randomapp.data.local.entity.MealEntity> _currentMeal = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.randomapp.data.local.entity.MealEntity> currentMeal = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> errorMessage = null;
    
    public FoodViewModel(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.repository.FoodRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.FoodCategory>> getAllCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.FoodCategory>> getCategorySearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.randomapp.data.local.entity.FoodCategory> getCurrentCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> getAllMeals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> getMealSearchResults() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.randomapp.data.local.entity.MealEntity>> getMealsByCategory() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.randomapp.data.local.entity.MealEntity> getCurrentMeal() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getErrorMessage() {
        return null;
    }
    
    public final void loadAllCategories() {
    }
    
    public final void insertCategory(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.FoodCategory foodCategory) {
    }
    
    public final void updateCategory(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.FoodCategory foodCategory) {
    }
    
    public final void deleteCategory(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.FoodCategory foodCategory) {
    }
    
    public final void searchCategories(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void getCategoryById(int id) {
    }
    
    public final void loadAllMeals() {
    }
    
    public final void insertMeal(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.MealEntity meal) {
    }
    
    public final void updateMeal(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.MealEntity meal) {
    }
    
    public final void deleteMeal(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.MealEntity meal) {
    }
    
    public final void searchMeals(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void getMealsByCategory(int categoryId) {
    }
    
    public final void getMealById(int id) {
    }
    
    public final void clearError() {
    }
}