package org.objectstyle.linkrest.emberjs.example;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.nhl.bootique.Bootique;
import com.nhl.bootique.jersey.JerseyModule;
import com.nhl.bootique.linkrest.LinkRestModule;
import org.objectstyle.linkrest.emberjs.EmberJSAdapter;
import org.objectstyle.linkrest.emberjs.example.api.DomainApi;

public class App implements Module {

    public static void main(String[] args) {
        Bootique.app(args).autoLoadModules().module(App.class).run();
    }

    @Override
    public void configure(Binder binder) {
        LinkRestModule.contributeAdapters(binder).addBinding().to(EmberJSAdapter.class);
        JerseyModule.contributePackages(binder).addBinding().toInstance(DomainApi.class.getPackage());
    }
}
