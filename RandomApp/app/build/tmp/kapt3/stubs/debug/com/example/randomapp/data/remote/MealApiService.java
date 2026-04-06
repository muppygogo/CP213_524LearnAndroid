package com.example.randomapp.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\n"}, d2 = {"Lcom/example/randomapp/data/remote/MealApiService;", "", "filterByCategory", "error/NonExistentClass", "category", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCategories", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRandomMeal", "app_debug"})
public abstract interface MealApiService {
    
    @retrofit2.http.GET(value = "api/json/v1/1/random.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRandomMeal(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super error.NonExistentClass> $completion);
    
    @retrofit2.http.GET(value = "api/json/v1/1/categories.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCategories(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super error.NonExistentClass> $completion);
    
    @retrofit2.http.GET(value = "api/json/v1/1/filter.php")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object filterByCategory(@retrofit2.http.Query(value = "c")
    @org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super error.NonExistentClass> $completion);
}