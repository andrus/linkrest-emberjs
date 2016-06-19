package com.nhl.link.rest.emberjs;

import com.nhl.link.rest.ResourceEntity;
import com.nhl.link.rest.encoder.*;
import com.nhl.link.rest.runtime.encoder.EncoderService;
import com.nhl.link.rest.runtime.encoder.IAttributeEncoderFactory;
import com.nhl.link.rest.runtime.encoder.IStringConverterFactory;
import com.nhl.link.rest.runtime.semantics.IRelationshipMapper;
import org.apache.cayenne.di.Inject;
import org.jvnet.inflector.Noun;
import org.jvnet.inflector.Pluralizer;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @since 2.1
 */
public class EmberJSEncoderService extends EncoderService {

    private Pluralizer pluralizer;

    public EmberJSEncoderService(@Inject(ENCODER_FILTER_LIST) List<EncoderFilter> filters,
                                 @Inject IAttributeEncoderFactory attributeEncoderFactory,
                                 @Inject IStringConverterFactory stringConverterFactory,
                                 @Inject IRelationshipMapper relationshipMapper,
                                 @Inject(PROPERTY_METADATA_ENCODER_MAP) Map<String, PropertyMetadataEncoder> propertyMetadataEncoders) {
        super(filters, attributeEncoderFactory, stringConverterFactory, relationshipMapper, propertyMetadataEncoders);

        this.pluralizer = Objects.requireNonNull(Noun.pluralizer(Locale.ENGLISH));
    }

    @Override
    public <T> Encoder dataEncoder(ResourceEntity<T> entity) {
        CollectionEncoder resultEncoder = resultEncoder(entity);
        String dataKey = toDataKeyName(entity.getLrEntity().getName());
        return new DataResponseEncoder(dataKey, resultEncoder, "meta", EmberJSTotalEncoder.encoder());
    }

    protected String toDataKeyName(String entityName) {

        // TODO: cache per entity?

        if (entityName.length() < 1) {
            throw new IllegalArgumentException("Entity name is empty");
        }

        String lcName = Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1);
        return pluralizer.pluralize(lcName);
    }
}
