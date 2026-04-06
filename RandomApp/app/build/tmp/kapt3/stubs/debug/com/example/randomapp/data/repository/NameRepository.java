package com.example.randomapp.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0012\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\rJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\r2\u0006\u0010\u0014\u001a\u00020\u0011J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\u0017\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bR\u0010\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0010\u0010\u0006\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0005\u00a8\u0006\u0018"}, d2 = {"Lcom/example/randomapp/data/repository/NameRepository;", "", "database", "error/NonExistentClass", "(Lerror/NonExistentClass;)V", "Lerror/NonExistentClass;", "nameDao", "deleteName", "", "name", "Lcom/example/randomapp/data/local/entity/NameEntity;", "(Lcom/example/randomapp/data/local/entity/NameEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNames", "Lkotlinx/coroutines/flow/Flow;", "", "getNameById", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getNamesByUser", "userId", "insertName", "", "updateName", "app_debug"})
public final class NameRepository {
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass database = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass nameDao = null;
    
    public NameRepository(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass database) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertName(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.NameEntity name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateName(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.NameEntity name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteName(@org.jetbrains.annotations.NotNull()
    com.example.randomapp.data.local.entity.NameEntity name, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.randomapp.data.local.entity.NameEntity>> getAllNames() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getNameById(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.randomapp.data.local.entity.NameEntity> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.randomapp.data.local.entity.NameEntity>> getNamesByUser(int userId) {
        return null;
    }
}