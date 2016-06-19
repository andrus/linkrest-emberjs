package com.nhl.link.rest.emberjs;

import com.nhl.link.rest.runtime.encoder.IAttributeEncoderFactory;
import com.nhl.link.rest.runtime.encoder.IStringConverterFactory;
import com.nhl.link.rest.runtime.semantics.IRelationshipMapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

public class EmberJSEncoderServiceTest {

    @Test
    public void testToDataKeyName() {

        EmberJSEncoderService encoderService = new EmberJSEncoderService(Collections.emptyList(),
                Mockito.mock(IAttributeEncoderFactory.class),
                Mockito.mock(IStringConverterFactory.class),
                Mockito.mock(IRelationshipMapper.class),
                Collections.emptyMap());

        Assert.assertEquals("e1s", encoderService.toDataKeyName("E1"));
        Assert.assertEquals("entities", encoderService.toDataKeyName("entity"));
        Assert.assertEquals("artists", encoderService.toDataKeyName("Artist"));
        Assert.assertEquals("as", encoderService.toDataKeyName("A"));

    }
}
