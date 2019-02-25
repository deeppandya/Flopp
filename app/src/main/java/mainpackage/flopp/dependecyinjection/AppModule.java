package mainpackage.flopp.dependecyinjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mainpackage.flopp.BuildConfig;
import mainpackage.flopp.database.MovieDbDatabase;
import mainpackage.flopp.database.MoviesDbDao;
import mainpackage.flopp.networkcalls.MovieDbService;
import mainpackage.flopp.utils.Connection;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {

    @Singleton
    @Provides
    MovieDbService providesMovieDbService() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.MOVIE_DB_END_POINT)
                .build()
                .create(MovieDbService.class);
    }

    @Singleton
    @Provides
    MovieDbDatabase providesMovieDbDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(),
                MovieDbDatabase.class, "movie_db_database")
                .allowMainThreadQueries()
                .build();
    }

    @Singleton
    @Provides
    MoviesDbDao providesMoviesDbDao(MovieDbDatabase movieDbDatabase) {
        return movieDbDatabase.movieDbDao();
    }

    @Singleton
    @Provides
    Connection provideConnection(Application application) {
        return new Connection(application.getApplicationContext());
    }
}
