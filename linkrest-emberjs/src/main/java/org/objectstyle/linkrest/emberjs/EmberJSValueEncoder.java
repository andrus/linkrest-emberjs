package org.objectstyle.linkrest.emberjs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.nhl.link.rest.EntityProperty;
import com.nhl.link.rest.encoder.Encoder;
import com.nhl.link.rest.encoder.EncoderVisitor;

import java.io.IOException;

public class EmberJSValueEncoder implements Encoder {

    private EntityProperty idEncoder;

    public EmberJSValueEncoder(EntityProperty idEncoder) {
        this.idEncoder = idEncoder;
    }

    @Override
    public boolean encode(String propertyName, Object object, JsonGenerator out) throws IOException {
        if (object == null) {
            out.writeNull();
        } else {
            idEncoder.encode(object, null, out);
        }

        return true;
    }

    @Override
    public boolean willEncode(String propertyName, Object object) {
        return true;
    }

    @Override
    public int visitEntities(Object object, EncoderVisitor visitor) {

        if (object == null || !willEncode(null, object)) {
            return VISIT_CONTINUE;
        }

        int bitmask = visitor.visit(object);

        if ((bitmask & VISIT_SKIP_ALL) != 0) {
            return VISIT_SKIP_ALL;
        }


        return VISIT_CONTINUE;
    }
}
