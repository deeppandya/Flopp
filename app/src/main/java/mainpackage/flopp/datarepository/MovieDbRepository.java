package mainpackage.flopp.datarepository;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import mainpackage.flopp.BuildConfig;
import mainpackage.flopp.database.MoviesDbDao;
import mainpackage.flopp.model.MovieResultModel;
import mainpackage.flopp.networkcalls.MovieDbService;
import mainpackage.flopp.utils.Connection;


/**
 * Created by deeppandya
 * On 2019-02-24.
 */

@Singleton
public class MovieDbRepository {

    private MovieDbService movieDbService;
    private MoviesDbDao moviesDbDao;
    private Connection connection;

    @Inject
    MovieDbRepository(MovieDbService movieDbService, MoviesDbDao moviesDbDao, Connection connection) {
        this.movieDbService = movieDbService;
        this.moviesDbDao = moviesDbDao;
        this.connection = connection;
    }

    public Observable<MovieResultModel> getUpcomingMovies(String page) {

        boolean hasConnection = connection.isConnectedToInternet();
        Observable<MovieResultModel> observableFromApi = null;

        if (hasConnection)
            observableFromApi = getMoviesFromApi(page);
        Observable<MovieResultModel> observableFromLocal = getMoviesFromLocals(page);

        if (hasConnection)
            return Observable.concatArrayEager(observableFromApi, observableFromLocal);
        else return observableFromLocal;
    }

    private Observable<MovieResultModel> getMoviesFromApi(String page) {
        return movieDbService.getUpcomingMovies(BuildConfig.MOVIE_DB_API_KEY, page, "us", "en-US").doOnNext(movieResultModel -> moviesDbDao.insert(movieResultModel));
    }

    private Observable<MovieResultModel> getMoviesFromLocals(String page) {
        return moviesDbDao.getAllMovieResults(Integer.parseInt(page)).toObservable().doOnNext(movieResultModel -> {
            Log.d("Observable","Accepted");
        });
    }
}
