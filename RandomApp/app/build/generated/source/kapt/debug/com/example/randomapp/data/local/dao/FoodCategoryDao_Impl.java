package com.example.randomapp.data.local.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.randomapp.data.local.entity.FoodCategory;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FoodCategoryDao_Impl implements FoodCategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FoodCategory> __insertionAdapterOfFoodCategory;

  private final EntityDeletionOrUpdateAdapter<FoodCategory> __deletionAdapterOfFoodCategory;

  private final EntityDeletionOrUpdateAdapter<FoodCategory> __updateAdapterOfFoodCategory;

  public FoodCategoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFoodCategory = new EntityInsertionAdapter<FoodCategory>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `food_categories` (`id`,`name`,`description`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FoodCategory value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
      }
    };
    this.__deletionAdapterOfFoodCategory = new EntityDeletionOrUpdateAdapter<FoodCategory>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `food_categories` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FoodCategory value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfFoodCategory = new EntityDeletionOrUpdateAdapter<FoodCategory>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `food_categories` SET `id` = ?,`name` = ?,`description` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FoodCategory value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public Object insert(final FoodCategory category, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object delete(final FoodCategory category, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object update(final FoodCategory category, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getAllCategories(final Continuation<? super List<FoodCategory>> $completion) {
    final String _sql = "SELECT * FROM food_categories";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    int _argIndex = 1;
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        _result = new Object();
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Object getCategoryById(final int id,
      final Continuation<? super FoodCategory> $completion) {
    final String _sql = "SELECT * FROM food_categories WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        _result = new Object();
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
