package com.nhl.link.rest.emberjs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.nhl.link.rest.encoder.AbstractEncoder;
import com.nhl.link.rest.encoder.Encoder;

import java.io.IOException;

/**
 * @since 2.1
 */
public class EmberJSTotalEncoder extends AbstractEncoder {

    private static final Encoder instance = new EmberJSTotalEncoder();

    private EmberJSTotalEncoder() {
    }

    public static Encoder encoder() {
        return instance;
    }

    @Override
    protected boolean encodeNonNullObject(Object object, JsonGenerator out) throws IOException {

        out.writeStartObject();
        out.writeFieldName("total");
        out.writeObject(object);
        out.writeEndObject();

        return true;
    }
}
