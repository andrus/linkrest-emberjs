package org.objectstyle.linkrest.emberjs;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.nhl.link.rest.ResourceEntity;
import com.nhl.link.rest.encoder.Encoder;
import com.nhl.link.rest.encoder.PropertyMetadataEncoder;
import com.nhl.link.rest.it.fixture.cayenne.E1;
import com.nhl.link.rest.runtime.encoder.AttributeEncoderFactory;
import com.nhl.link.rest.runtime.encoder.IAttributeEncoderFactory;
import com.nhl.link.rest.runtime.encoder.IStringConverterFactory;
import com.nhl.link.rest.runtime.jackson.JacksonService;
import com.nhl.link.rest.runtime.semantics.RelationshipMapper;
import com.nhl.link.rest.unit.TestWithCayenneMapping;
import org.apache.cayenne.ObjectId;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EmberJSEncoderService_IT extends TestWithCayenneMapping {

    private EmberJSEncoderService encoderService;

    @Before
    public void before() {

        IAttributeEncoderFactory attributeEncoderFactory = new AttributeEncoderFactory();
        IStringConverterFactory stringConverterFactory = mock(IStringConverterFactory.class);

        encoderService = new EmberJSEncoderService(Collections.emptyList(), attributeEncoderFactory, stringConverterFactory,
                new RelationshipMapper(), Collections.<String, PropertyMetadataEncoder>emptyMap());
    }

    @Test
    public void testSingleObject() throws IOException {

        ResourceEntity<E1> descriptor = getResourceEntity(E1.class).includeId();

        E1 e1 = new E1();
        e1.setObjectId(new ObjectId("E1", E1.ID_PK_COLUMN, 777));
        e1.setName("XYZ");
        e1.setAge(30);
        e1.setDescription("test");

        assertEquals("{\"e1s\":[{\"id\":777}],\"meta\":{\"total\":1}}", toJson(e1, descriptor));
    }

    @Test
    public void testCollectionObject() throws IOException {

        ResourceEntity<E1> descriptor = getResourceEntity(E1.class).includeId();

        List<E1> e1s = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            E1 e1 = new E1();
            e1.setObjectId(new ObjectId("E1", E1.ID_PK_COLUMN, 100 + i));
            e1s.add(e1);
        }


        assertEquals("{\"e1s\":[{\"id\":100},{\"id\":101}],\"meta\":{\"total\":2}}", toJson(e1s, descriptor));
    }


    private String toJson(Object object, ResourceEntity<?> entity) throws IOException {

        Encoder encoder = encoderService.dataEncoder(entity);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Collection<?> objects = object instanceof Collection ? (Collection) object : Collections.singletonList(object);

        try (JsonGenerator generator = new JacksonService().getJsonFactory().createGenerator(out, JsonEncoding.UTF8)) {
            encoder.encode(null, objects, generator);
        }

        return new String(out.toByteArray(), "UTF-8");
    }
}
