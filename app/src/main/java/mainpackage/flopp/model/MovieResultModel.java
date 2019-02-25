package mainpackage.flopp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mainpackage.flopp.database.ListDataConverter;

/**
 * Created by deeppandya
 * On 2019-02-24.
 */

@Entity
@TypeConverters(ListDataConverter.class)
public class MovieResultModel implements Parcelable {

    @PrimaryKey()
    private int page;
    @SerializedName("total_results")
    @ColumnInfo(name = "total_results")
    private int totalResults;
    private List<MovieModel> results;

    public MovieResultModel(int page, int totalResults, List<MovieModel> results) {
        this.page = page;
        this.totalResults = totalResults;
        this.results = results;
    }

    private MovieResultModel(Parcel in) {
        page = in.readInt();
        totalResults = in.readInt();
        in.readList(results, MovieModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(totalResults);
        dest.writeList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieResultModel> CREATOR = new Creator<MovieResultModel>() {
        @Override
        public MovieResultModel createFromParcel(Parcel in) {
            return new MovieResultModel(in);
        }

        @Override
        public MovieResultModel[] newArray(int size) {
            return new MovieResultModel[size];
        }
    };

    @NonNull
    public int getPage() {
        return page;
    }

    public void setPage(@NonNull int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<MovieModel> getResults() {
        return results;
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
    }
}
