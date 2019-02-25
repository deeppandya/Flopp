package mainpackage.flopp.db;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import mainpackage.flopp.model.MovieModel;
import mainpackage.flopp.model.MovieResultModel;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by deeppandya
 * On 2019-02-25.
 */

@RunWith(AndroidJUnit4.class)
public class MoviesDbDaoTest extends DbTest {

    @Test
    public void insertAndReadTest(){
        MovieResultModel movieResultModel = mockMovieResult();
        movieDbDatabase.movieDbDao().insert(movieResultModel);

        assertThat(movieDbDatabase.movieDbDao().getAllMovieResults(1),is(notNullValue()));
    }

    private MovieResultModel mockMovieResult() {

        List<MovieModel> movieModels = new ArrayList<>();
        movieModels.add(new MovieModel("1", "alita", "xyz.jpg", "xyz.jpg", "qwerty", "2019-02-25"));
        movieModels.add(new MovieModel("2", "alita2", "xyz2.jpg", "xyz2.jpg", "qwerty2", "2019-02-26"));

        return new MovieResultModel(1, 81, movieModels);
    }

}
