package mainpackage.flopp;

import java.util.ArrayList;
import java.util.List;

import mainpackage.flopp.model.MovieModel;
import mainpackage.flopp.model.MovieResultModel;

/**
 * Created by deeppandya
 * On 2019-02-25.
 */
public class MockTestUtil {

    public MovieResultModel mockMovieResult() {

        List<MovieModel> movieModels = new ArrayList<>();
        movieModels.add(new MovieModel("1", "alita", "xyz.jpg", "xyz.jpg", "qwerty", "2019-02-25"));
        movieModels.add(new MovieModel("2", "alita2", "xyz2.jpg", "xyz2.jpg", "qwerty2", "2019-02-26"));

        return new MovieResultModel(1, 81, movieModels);
    }

}
