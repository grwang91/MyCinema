package techtown.org.mycinema.MovieListApi;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieList implements Parcelable {

    public String message;
    public int code;
    public String resultType;
    public ArrayList<Movie> result = new ArrayList<>();

    public MovieList(String message, int code, String resultType, ArrayList<Movie> result) {
        this.message = message;
        this.code = code;
        this.resultType = resultType;
        this.result = result;
    }

    public MovieList (Parcel src) {
        message = src.readString();
        code = src.readInt();
        resultType = src.readString();
        result = src.readArrayList(null);
        //src.read
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public MovieList createFromParcel(Parcel src) {
            return new MovieList(src);
        }

        @Override
        public MovieList[] newArray(int size) {
            return new MovieList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeInt(code);
        dest.writeString(resultType);
        dest.writeList(result);
    }
}
