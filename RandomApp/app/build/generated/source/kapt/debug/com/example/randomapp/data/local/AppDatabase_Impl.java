package com.example.randomapp.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.randomapp.data.local.dao.FoodCategoryDao;
import com.example.randomapp.data.local.dao.FoodCategoryDao_Impl;
import com.example.randomapp.data.local.dao.MealDao;
import com.example.randomapp.data.local.dao.MealDao_Impl;
import com.example.randomapp.data.local.dao.NameDao;
import com.example.randomapp.data.local.dao.NameDao_Impl;
import com.example.randomapp.data.local.dao.RestaurantSearchDao;
import com.example.randomapp.data.local.dao.RestaurantSearchDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile FoodCategoryDao _foodCategoryDao;

  private volatile MealDao _mealDao;

  private volatile NameDao _nameDao;

  private volatile RestaurantSearchDao _restaurantSearchDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `food_categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `meals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT, `category_id` INTEGER NOT NULL, `price` REAL, FOREIGN KEY(`category_id`) REFERENCES `food_categories`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `names` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `firstName` TEXT NOT NULL, `lastName` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `restaurant_searches` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `restaurantName` TEXT NOT NULL, `location` TEXT, `searchDate` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ba9a1153443148bd919f23ea1dfccbe8')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `food_categories`");
        _db.execSQL("DROP TABLE IF EXISTS `meals`");
        _db.execSQL("DROP TABLE IF EXISTS `names`");
        _db.execSQL("DROP TABLE IF EXISTS `restaurant_searches`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFoodCategories = new HashMap<String, TableInfo.Column>(3);
        _columnsFoodCategories.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodCategories.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFoodCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFoodCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFoodCategories = new TableInfo("food_categories", _columnsFoodCategories, _foreignKeysFoodCategories, _indicesFoodCategories);
        final TableInfo _existingFoodCategories = TableInfo.read(_db, "food_categories");
        if (! _infoFoodCategories.equals(_existingFoodCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "food_categories(com.example.randomapp.data.local.entity.FoodCategory).\n"
                  + " Expected:\n" + _infoFoodCategories + "\n"
                  + " Found:\n" + _existingFoodCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsMeals = new HashMap<String, TableInfo.Column>(5);
        _columnsMeals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeals.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeals.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeals.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMeals.put("price", new TableInfo.Column("price", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMeals = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMeals.add(new TableInfo.ForeignKey("food_categories", "CASCADE", "NO ACTION",Arrays.asList("category_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMeals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMeals = new TableInfo("meals", _columnsMeals, _foreignKeysMeals, _indicesMeals);
        final TableInfo _existingMeals = TableInfo.read(_db, "meals");
        if (! _infoMeals.equals(_existingMeals)) {
          return new RoomOpenHelper.ValidationResult(false, "meals(com.example.randomapp.data.local.entity.MealEntity).\n"
                  + " Expected:\n" + _infoMeals + "\n"
                  + " Found:\n" + _existingMeals);
        }
        final HashMap<String, TableInfo.Column> _columnsNames = new HashMap<String, TableInfo.Column>(3);
        _columnsNames.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNames.put("firstName", new TableInfo.Column("firstName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNames.put("lastName", new TableInfo.Column("lastName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNames = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNames = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNames = new TableInfo("names", _columnsNames, _foreignKeysNames, _indicesNames);
        final TableInfo _existingNames = TableInfo.read(_db, "names");
        if (! _infoNames.equals(_existingNames)) {
          return new RoomOpenHelper.ValidationResult(false, "names(com.example.randomapp.data.local.entity.NameEntity).\n"
                  + " Expected:\n" + _infoNames + "\n"
                  + " Found:\n" + _existingNames);
        }
        final HashMap<String, TableInfo.Column> _columnsRestaurantSearches = new HashMap<String, TableInfo.Column>(4);
        _columnsRestaurantSearches.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantSearches.put("restaurantName", new TableInfo.Column("restaurantName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantSearches.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRestaurantSearches.put("searchDate", new TableInfo.Column("searchDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRestaurantSearches = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRestaurantSearches = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRestaurantSearches = new TableInfo("restaurant_searches", _columnsRestaurantSearches, _foreignKeysRestaurantSearches, _indicesRestaurantSearches);
        final TableInfo _existingRestaurantSearches = TableInfo.read(_db, "restaurant_searches");
        if (! _infoRestaurantSearches.equals(_existingRestaurantSearches)) {
          return new RoomOpenHelper.ValidationResult(false, "restaurant_searches(com.example.randomapp.data.local.entity.RestaurantSearchEntity).\n"
                  + " Expected:\n" + _infoRestaurantSearches + "\n"
                  + " Found:\n" + _existingRestaurantSearches);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "ba9a1153443148bd919f23ea1dfccbe8", "53a03b56b61e8236a9d0d066530ed980");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "food_categories","meals","names","restaurant_searches");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `food_categories`");
      _db.execSQL("DELETE FROM `meals`");
      _db.execSQL("DELETE FROM `names`");
      _db.execSQL("DELETE FROM `restaurant_searches`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FoodCategoryDao.class, FoodCategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MealDao.class, MealDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NameDao.class, NameDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RestaurantSearchDao.class, RestaurantSearchDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public FoodCategoryDao foodCategoryDao() {
    if (_foodCategoryDao != null) {
      return _foodCategoryDao;
    } else {
      synchronized(this) {
        if(_foodCategoryDao == null) {
          _foodCategoryDao = new FoodCategoryDao_Impl(this);
        }
        return _foodCategoryDao;
      }
    }
  }

  @Override
  public MealDao mealDao() {
    if (_mealDao != null) {
      return _mealDao;
    } else {
      synchronized(this) {
        if(_mealDao == null) {
          _mealDao = new MealDao_Impl(this);
        }
        return _mealDao;
      }
    }
  }

  @Override
  public NameDao nameDao() {
    if (_nameDao != null) {
      return _nameDao;
    } else {
      synchronized(this) {
        if(_nameDao == null) {
          _nameDao = new NameDao_Impl(this);
        }
        return _nameDao;
      }
    }
  }

  @Override
  public RestaurantSearchDao restaurantSearchDao() {
    if (_restaurantSearchDao != null) {
      return _restaurantSearchDao;
    } else {
      synchronized(this) {
        if(_restaurantSearchDao == null) {
          _restaurantSearchDao = new RestaurantSearchDao_Impl(this);
        }
        return _restaurantSearchDao;
      }
    }
  }
}
