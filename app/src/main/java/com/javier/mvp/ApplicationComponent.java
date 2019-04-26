package com.javier.mvp;

import com.javier.catalogoproductos.MainActivity;
import com.javier.mvp.main.MainModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MainModule.class})
public interface ApplicationComponent {
    void inject(MainActivity target);
}
