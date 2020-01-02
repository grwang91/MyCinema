package techtown.org.mycinema;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.RequestQueue;

import java.io.ByteArrayOutputStream;

import techtown.org.mycinema.MovieListApi.Movie;

public class AppHelper {


    public static RequestQueue requestQueue;


    private static final String TAG = "AppHelper";

    private static SQLiteDatabase database;
    public static String createTableOutlineSql = "create table if not exists outline" +
            "(" +
            "    _id integer PRIMARY KEY autoincrement, " +
            "    id text, " +
            "    title text, " +
            "    reservation_rate float, " +
            "    grade integer, " +
            "    image BLOB " +
            ")";

    public static String selectTableOutlineSql = "select id, title, reservation_rate, grade from outline";

    public static SQLiteDatabase openDatabase(Context context, String databaseName) {
        println("openDatabase 호출됨.");
        try {
            database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
            if (database != null) {
                println("데이터베이스 " + databaseName + "오픈됨.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return database;
    }

    public static void createTable(SQLiteDatabase database, String SQLString, String tableName) {
        println("createTable 호출됨 : " + tableName);

        if (database != null) {
            if (tableName.equals(tableName)) {
                database.execSQL(SQLString);
                println(tableName+"테이블 생성 요청됨.");
            }
        } else {
            println("데이터베이스를 먼저 오픈하세요");
        }
    }

    public static Cursor selectTable(SQLiteDatabase database, String SQLString, String tableName) {
        println("selectTable 호출됨 : " + tableName);
        Cursor cursor;
        if (database != null) {
            cursor = database.rawQuery(SQLString, null);
            println("데이터베이스 이씀");
            return cursor;
        }
        return null;
    }

    public static void insertDataOutline(SQLiteDatabase database, Movie movie) {
        println("insertDataOutline 호출됨");

        if (database != null) {
            String delSql = "delete from outline where id="+movie.id;
            database.execSQL(delSql);
            String sql = "insert into outline(id, title, reservation_rate, grade) values(?, ?, ?, ?)";
            Object[] params = {movie.id, movie.title, movie.reservation_rate, movie.grade};

            database.execSQL(sql, params);
            println("데이터 추가함");
        }
    }

    public static void println(String data) {
        Log.d(TAG, data);
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray = null;

        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray();
            println("bitmap null아님");
            return byteArray;
        }
        return byteArray;
    }


}
