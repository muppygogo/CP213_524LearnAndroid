package com.example.randomapp.data.local.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.randomapp.data.local.entity.RestaurantSearchEntity;
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
public final class RestaurantSearchDao_Impl implements RestaurantSearchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<RestaurantSearchEntity> __insertionAdapterOfRestaurantSearchEntity;

  private final EntityDeletionOrUpdateAdapter<RestaurantSearchEntity> __deletionAdapterOfRestaurantSearchEntity;

  private final EntityDeletionOrUpdateAdapter<RestaurantSearchEntity> __updateAdapterOfRestaurantSearchEntity;

  public RestaurantSearchDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRestaurantSearchEntity = new EntityInsertionAdapter<RestaurantSearchEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `restaurant_searches` (`id`,`restaurantName`,`location`,`searchDate`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantSearchEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getRestaurantName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getRestaurantName());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocation());
        }
        stmt.bindLong(4, value.getSearchDate());
      }
    };
    this.__deletionAdapterOfRestaurantSearchEntity = new EntityDeletionOrUpdateAdapter<RestaurantSearchEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `restaurant_searches` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantSearchEntity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfRestaurantSearchEntity = new EntityDeletionOrUpdateAdapter<RestaurantSearchEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `restaurant_searches` SET `id` = ?,`restaurantName` = ?,`location` = ?,`searchDate` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, RestaurantSearchEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getRestaurantName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getRestaurantName());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLocation());
        }
        stmt.bindLong(4, value.getSearchDate());
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public Object insert(final RestaurantSearchEntity restaurant,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object delete(final RestaurantSearchEntity restaurant,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object update(final RestaurantSearchEntity restaurant,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getAllRestaurants(
      final Continuation<? super List<RestaurantSearchEntity>> $completion) {
    final String _sql = "SELECT * FROM restaurant_searches";
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
  public Object getRestaurantById(final int id,
      final Continuation<? super RestaurantSearchEntity> $completion) {
    final String _sql = "SELECT * FROM restaurant_searches WHERE id = ?";
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
