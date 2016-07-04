package org.objectstyle.linkrest.emberjs;

import com.fasterxml.jackson.core.JsonGenerator;
import com.nhl.link.rest.SimpleResponse;
import com.nhl.link.rest.runtime.LinkRestRuntime;
import com.nhl.link.rest.runtime.jackson.IJacksonService;
import com.nhl.link.rest.runtime.jackson.JsonConvertable;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class EmberJSSimpleResponseWriter implements MessageBodyWriter<SimpleResponse> {

    private IJacksonService jacksonService;

    @Context
    private Configuration configuration;

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return SimpleResponse.class.isAssignableFrom(type);
    }

    @Override
    public long getSize(SimpleResponse simpleResponse, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(SimpleResponse simpleResponse, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        getJacksonService().outputJson(new JsonConvertable() {
            @Override
            public void generateJSON(JsonGenerator out) throws IOException {
                out.writeStartObject();
                if (!simpleResponse.isSuccess()) {

                    out.writeFieldName("errors");
                    out.writeStartObject();
                    if (simpleResponse.getMessage() != null) {
                        out.writeStringField("msg", simpleResponse.getMessage());
                    }
                    out.writeEndObject();
                }

                out.writeEndObject();
            }
        }, entityStream);
    }

    private IJacksonService getJacksonService() {
        if (jacksonService == null) {
            jacksonService = LinkRestRuntime.service(IJacksonService.class, configuration);
        }

        return jacksonService;
    }
}
