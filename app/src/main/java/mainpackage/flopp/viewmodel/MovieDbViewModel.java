package mainpackage.flopp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mainpackage.flopp.datarepository.MovieDbRepository;
import mainpackage.flopp.model.MovieResultModel;

/**
 * Created by deeppandya
 * On 2019-02-24.
 */
public class MovieDbViewModel extends AndroidViewModel {

    private MovieDbRepository movieDbRepository;
    private DisposableObserver<MovieResultModel> disposableObserver;
    private MutableLiveData<MovieResultModel> resultModelMutableLiveData = new MutableLiveData<MovieResultModel>();
    private MutableLiveData<String> resultModelError = new MutableLiveData<String>();
    private MutableLiveData<Boolean> resultModelLoader = new MutableLiveData<Boolean>();

    @Inject
    MovieDbViewModel(Application application, MovieDbRepository movieDbRepository) {
        super(application);
        this.movieDbRepository = movieDbRepository;
    }

    public LiveData<MovieResultModel> resultModelLiveData() {
        return resultModelMutableLiveData;
    }

    public LiveData<String> resultModelError() {
        return resultModelError;
    }

    public LiveData<Boolean> resultModelLoader() {
        return resultModelLoader;
    }

    public void getUpcomingMovies(String page) {

        disposableObserver = new DisposableObserver<MovieResultModel>() {
            @Override
            public void onNext(MovieResultModel movieResultModel) {
                resultModelMutableLiveData.postValue(movieResultModel);
                resultModelLoader.postValue(false);
            }

            @Override
            public void onError(Throwable e) {
                resultModelError.postValue(e.getMessage());
                resultModelLoader.postValue(false);
            }

            @Override
            public void onComplete() {

            }
        };

        movieDbRepository.getUpcomingMovies(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    public void disposeElements() {
        if (null != disposableObserver && !disposableObserver.isDisposed())
            disposableObserver.dispose();
    }
}
