package mainpackage.flopp;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import mainpackage.flopp.dependecyinjection.AppComponent;
import mainpackage.flopp.dependecyinjection.DaggerAppComponent;

/**
 * Created by deeppandya
 * On 2019-02-25.
 */
public class TestMovieDbApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this)
                .build().inject(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }
}
