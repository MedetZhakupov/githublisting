package com.medetzhakupov.githublisting.di;


import com.medetzhakupov.githublisting.App;
import com.medetzhakupov.githublisting.data.DataModule;
import com.medetzhakupov.githublisting.network.ServiceModule;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        DataModule.class,
        ServiceModule.class,
        MainActivityBuilders.class,
        DetailsActivityBuilder.class,
        ViewModelModule.class,
})
public interface AppComponent extends AndroidInjector<App> {

    @Override
    void inject(App instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(App app);
        AppComponent build();

    }
}
