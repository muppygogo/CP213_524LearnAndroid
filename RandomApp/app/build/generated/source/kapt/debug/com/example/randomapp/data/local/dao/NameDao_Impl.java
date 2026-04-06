package com.example.randomapp.data.local.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.randomapp.data.local.entity.NameEntity;
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
public final class NameDao_Impl implements NameDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NameEntity> __insertionAdapterOfNameEntity;

  private final EntityDeletionOrUpdateAdapter<NameEntity> __deletionAdapterOfNameEntity;

  private final EntityDeletionOrUpdateAdapter<NameEntity> __updateAdapterOfNameEntity;

  public NameDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNameEntity = new EntityInsertionAdapter<NameEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `names` (`id`,`firstName`,`lastName`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NameEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
      }
    };
    this.__deletionAdapterOfNameEntity = new EntityDeletionOrUpdateAdapter<NameEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `names` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NameEntity value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfNameEntity = new EntityDeletionOrUpdateAdapter<NameEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `names` SET `id` = ?,`firstName` = ?,`lastName` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NameEntity value) {
        stmt.bindLong(1, value.getId());
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getLastName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getLastName());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public Object insert(final NameEntity name, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object delete(final NameEntity name, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object update(final NameEntity name, final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getAllNames(final Continuation<? super List<NameEntity>> $completion) {
    final String _sql = "SELECT * FROM names";
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
  public Object getNameById(final int id, final Continuation<? super NameEntity> $completion) {
    final String _sql = "SELECT * FROM names WHERE id = ?";
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
