package mainpackage.flopp.dependecyinjection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import mainpackage.flopp.FloppApplication;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class
})
public interface AppComponent extends AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(FloppApplication floppApplication);
}
