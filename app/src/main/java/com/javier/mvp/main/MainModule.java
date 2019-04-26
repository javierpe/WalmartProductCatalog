package com.javier.mvp.main;

import com.javier.catalogoproductos.MainModel;
import com.javier.catalogoproductos.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    @Provides
    public MainLayer.Presenter provideLoginActivityPresenter(MainLayer.Model model){
        return new MainPresenter(model);
    }

    @Provides
    public MainLayer.Model provideLoginActivityModel(MainMemory repository){
        return new MainModel(repository);
    }

    @Provides
    public MainMemory provideLoginRepository(){
        return new MainMemory();
    }
}
