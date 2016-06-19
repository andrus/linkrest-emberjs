package com.nhl.link.rest.emberjs;

import com.nhl.link.rest.runtime.adapter.LinkRestAdapter;
import com.nhl.link.rest.runtime.encoder.EncoderService;
import org.apache.cayenne.di.Binder;

import javax.ws.rs.core.Feature;
import java.util.Collection;

/**
 * @since 2.1
 */
public class EmberJSAdapter implements LinkRestAdapter {

    @Override
    public void contributeToRuntime(Binder binder) {
        binder.bind(EncoderService.class).to(EmberJSEncoderService.class);
    }

    @Override
    public void contributeToJaxRs(Collection<Feature> features) {
        // no JAX RS contributions
    }
}
