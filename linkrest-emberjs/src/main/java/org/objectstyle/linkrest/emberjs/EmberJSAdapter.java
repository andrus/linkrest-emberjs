package org.objectstyle.linkrest.emberjs;

import com.nhl.link.rest.SimpleResponse;
import com.nhl.link.rest.runtime.LinkRestRuntime;
import com.nhl.link.rest.runtime.adapter.LinkRestAdapter;
import com.nhl.link.rest.runtime.encoder.IEncoderService;
import org.apache.cayenne.di.Binder;

import javax.ws.rs.core.Feature;
import java.util.Collection;

/**
 * @since 2.1
 */
public class EmberJSAdapter implements LinkRestAdapter {

    @Override
    public void contributeToRuntime(Binder binder) {
        binder.bind(IEncoderService.class).to(EmberJSEncoderService.class);
        binder.<Class<?>>bindMap(LinkRestRuntime.BODY_WRITERS_MAP)
                .put(SimpleResponse.class.getName(), EmberJSSimpleResponseWriter.class);
    }

    @Override
    public void contributeToJaxRs(Collection<Feature> features) {
        // no JAX RS contributions
    }
}
