package mainpackage.flopp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import mainpackage.flopp.model.MovieModel;
import mainpackage.flopp.model.MovieResultModel;

/**
 * Created by deeppandya
 * On 2019-02-24.
 */
@Database(entities = {MovieModel.class, MovieResultModel.class}, version = 1, exportSchema = false)
public abstract class MovieDbDatabase extends RoomDatabase {

    public abstract MoviesDbDao movieDbDao();
}
