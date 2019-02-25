package mainpackage.flopp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import mainpackage.flopp.model.MovieModel;
import mainpackage.flopp.model.MovieResultModel;

/**
 * Created by deeppandya
 * On 2019-02-24.
 */

@Dao
public interface MoviesDbDao {
    @Insert
    void insert(MovieModel movieModel);

    @Query("DELETE FROM MovieModel")
    void deleteAll();

    @Query("SELECT * FROM MovieModel ORDER BY release_date ASC")
    LiveData<List<MovieModel>> getAllMovies();

    @Query("SELECT COUNT(*) FROM MovieResultModel WHERE page == :page")
    int hasPage(int page);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieResultModel movieResultModel);

    @Query("SELECT * FROM MovieResultModel where page == :page")
    Single<MovieResultModel> getAllMovieResults(int page);
}
