package com.app.vocabulary.room;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WordDao_Impl implements WordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WordEntity> __insertionAdapterOfWordEntity;

  public WordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWordEntity = new EntityInsertionAdapter<WordEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `vocabulary_tb` (`id`,`word_id`,`word`,`antonyms`,`date`,`description`,`synonyms`,`bookmarks`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WordEntity value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getWord_id());
        if (value.getWord() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getWord());
        }
        if (value.getAntonyms() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAntonyms());
        }
        if (value.getDate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDate());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDescription());
        }
        if (value.getSynonyms() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSynonyms());
        }
        stmt.bindLong(8, value.getBookmarks());
      }
    };
  }

  @Override
  public void insert(final WordEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWordEntity.insert(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<WordEntity> getAllEntities() {
    final String _sql = "SELECT * FROM vocabulary_tb";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfWordId = CursorUtil.getColumnIndexOrThrow(_cursor, "word_id");
      final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
      final int _cursorIndexOfAntonyms = CursorUtil.getColumnIndexOrThrow(_cursor, "antonyms");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfSynonyms = CursorUtil.getColumnIndexOrThrow(_cursor, "synonyms");
      final int _cursorIndexOfBookmarks = CursorUtil.getColumnIndexOrThrow(_cursor, "bookmarks");
      final List<WordEntity> _result = new ArrayList<WordEntity>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final WordEntity _item;
        _item = new WordEntity();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpWord_id;
        _tmpWord_id = _cursor.getInt(_cursorIndexOfWordId);
        _item.setWord_id(_tmpWord_id);
        final String _tmpWord;
        if (_cursor.isNull(_cursorIndexOfWord)) {
          _tmpWord = null;
        } else {
          _tmpWord = _cursor.getString(_cursorIndexOfWord);
        }
        _item.setWord(_tmpWord);
        final String _tmpAntonyms;
        if (_cursor.isNull(_cursorIndexOfAntonyms)) {
          _tmpAntonyms = null;
        } else {
          _tmpAntonyms = _cursor.getString(_cursorIndexOfAntonyms);
        }
        _item.setAntonyms(_tmpAntonyms);
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        _item.setDate(_tmpDate);
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        _item.setDescription(_tmpDescription);
        final String _tmpSynonyms;
        if (_cursor.isNull(_cursorIndexOfSynonyms)) {
          _tmpSynonyms = null;
        } else {
          _tmpSynonyms = _cursor.getString(_cursorIndexOfSynonyms);
        }
        _item.setSynonyms(_tmpSynonyms);
        final int _tmpBookmarks;
        _tmpBookmarks = _cursor.getInt(_cursorIndexOfBookmarks);
        _item.setBookmarks(_tmpBookmarks);
        _result.add(_item);
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
