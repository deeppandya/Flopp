package mainpackage.flopp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import mainpackage.flopp.model.MovieModel;

/**
 * Created by deeppandya
 * On 2019-02-24.
 */
public class ListDataConverter {

    @TypeConverter
    public String fromMovieModelList(List<MovieModel> movieModelList) {
        if (movieModelList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MovieModel>>() {
        }.getType();
        return gson.toJson(movieModelList, type);
    }

    @TypeConverter
    public List<MovieModel> toMovieModelList(String movieModelList) {
        if (movieModelList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MovieModel>>() {
        }.getType();
        return gson.fromJson(movieModelList, type);
    }
}
