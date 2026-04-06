package com.example.randomapp.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\b2\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0012"}, d2 = {"Lcom/example/randomapp/data/local/dao/MealDao;", "", "delete", "", "meal", "Lcom/example/randomapp/data/local/entity/MealEntity;", "(Lcom/example/randomapp/data/local/entity/MealEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllMeals", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMealById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMealsByCategory", "categoryId", "insert", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface MealDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.MealEntity meal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.MealEntity meal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.MealEntity meal, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM meals")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllMeals(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.randomapp.data.local.entity.MealEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM meals WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMealById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.randomapp.data.local.entity.MealEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM meals WHERE category_id = :categoryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMealsByCategory(int categoryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.randomapp.data.local.entity.MealEntity>> $completion);
}