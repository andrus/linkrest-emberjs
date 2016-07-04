package org.objectstyle.linkrest.emberjs;

import com.nhl.link.rest.EntityProperty;
import com.nhl.link.rest.ResourceEntity;
import com.nhl.link.rest.encoder.CollectionEncoder;
import com.nhl.link.rest.encoder.DataResponseEncoder;
import com.nhl.link.rest.encoder.Encoder;
import com.nhl.link.rest.encoder.EncoderFilter;
import com.nhl.link.rest.encoder.EntityToOneEncoder;
import com.nhl.link.rest.encoder.ListEncoder;
import com.nhl.link.rest.encoder.PropertyMetadataEncoder;
import com.nhl.link.rest.meta.LrRelationship;
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

    @Override
    protected Encoder nestedToManyEncoder(ResourceEntity<?> resourceEntity) {
        if (resourceEntity.getMapBy() != null) {
            throw new UnsupportedOperationException("'mapBy' is not supported by EmberJS adapter.");
        }

        // note that super calls 'collectionElementEncoder' that is not applicable here
        // (and it is also reused for the top level collection, so we can't override it here).
        Encoder elementEncoder = toManyElementEncoder(resourceEntity);

        ListEncoder listEncoder = new ListEncoder(elementEncoder, resourceEntity.getQualifier(),
                resourceEntity.getOrderings());

        listEncoder.withOffset(resourceEntity.getFetchOffset()).withLimit(resourceEntity.getFetchLimit());
        if (resourceEntity.isFiltered()) {
            listEncoder.shouldFilter();
        }

        return listEncoder;
    }

    protected Encoder toManyElementEncoder(ResourceEntity<?> resourceEntity) {
        EntityProperty idEncoder = attributeEncoderFactory.getIdProperty(resourceEntity);
        Encoder encoder = new EmberJSToManyValueEncoder(idEncoder);
        return filteredEncoder(encoder, resourceEntity);
    }

    @Override
    protected Encoder toOneEncoder(ResourceEntity<?> resourceEntity, LrRelationship relationship) {
        EntityProperty idEncoder = attributeEncoderFactory.getIdProperty(resourceEntity);

        Encoder valueEncoder = new EmberJSToOneValueEncoder(idEncoder);
        Encoder compositeValueEncoder = new EntityToOneEncoder(valueEncoder);
        return filteredEncoder(compositeValueEncoder, resourceEntity);
    }

}
