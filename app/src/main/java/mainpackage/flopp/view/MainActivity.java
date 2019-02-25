package mainpackage.flopp.view;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.HasActivityInjector;
import mainpackage.flopp.R;
import mainpackage.flopp.adapter.MovieDbAdapter;
import mainpackage.flopp.listener.InfiniteScrollListener;
import mainpackage.flopp.model.MovieModel;
import mainpackage.flopp.utils.Constants;
import mainpackage.flopp.viewmodel.MovieDbViewModel;

import static dagger.internal.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity implements MovieDbAdapter.OnItemClickListener {
    private static final int NUM_COLS = 2;
    private ArrayList<MovieModel> movieModels;
    private MovieDbAdapter movieDbAdapter;
    private int currentPage = 1;
    private MovieDbViewModel movieDbViewModel;
    private RecyclerView recyclerView;
    private ProgressBar itemProgressBar;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mFirebaseAnalytics.setCurrentScreen(this, MainActivity.class.getName(), null /* class override */);

        movieDbViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDbViewModel.class);
        movieDbViewModel.resultModelLiveData().observe(this, movieResultModel -> {
            if (movieResultModel != null) {
                movieModels.addAll(movieResultModel.getResults());
                movieDbAdapter.notifyDataSetChanged();
                itemProgressBar.setVisibility(View.GONE);
            }
        });
        movieDbViewModel.resultModelError().observe(this, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show());
        movieDbViewModel.resultModelLoader().observe(this, aBoolean -> {
            if (!aBoolean) {
                itemProgressBar.setVisibility(View.GONE);
            }
        });

        setViews();

        setRecyclerViewForMovies();

        addDataToList(currentPage);
    }

    private void setRecyclerViewForMovies() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, NUM_COLS);
        recyclerView.setLayoutManager(mLayoutManager);
        movieModels = new ArrayList<>();
        movieDbAdapter = new MovieDbAdapter(movieModels, this);
        recyclerView.setAdapter(movieDbAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(30);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerView.addOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList(currentPage);
            }
        });
    }

    private void setViews() {
        recyclerView = findViewById(R.id.recycler_view);
        itemProgressBar = findViewById(R.id.item_progress_bar);
    }

    private void addDataToList(int page) {
        itemProgressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> movieDbViewModel.getUpcomingMovies(String.valueOf(page)), 1000);
        currentPage++;
    }

    @Override
    public void onItemClick(View view, MovieModel movieModel, int position) {
        Bundle params = new Bundle();
        params.putString("movie_overview", movieModel.getOverview());
        params.putString("released_date", movieModel.getReleaseDate());
        params.putString("title", movieModel.getTitle());
        mFirebaseAnalytics.logEvent("clicked_movie", params);

        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.MOVIE, movieModel);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        movieDbViewModel.disposeElements();
        super.onDestroy();
    }

    private void inject() {
        Application application = getApplication();
        if (application == null) {
            application = (Application) getApplicationContext();
        }

        if (!(application instanceof HasActivityInjector)) {
            throw new RuntimeException(String.format("%s does not implement %s", application.getClass().getCanonicalName(), HasActivityInjector.class.getCanonicalName()));
        }

        AndroidInjector<Activity> activityInjector = ((HasActivityInjector) application).activityInjector();
        checkNotNull(activityInjector, "%s.activityInjector() returned null", application.getClass().getCanonicalName());

        activityInjector.inject(this);
    }
}