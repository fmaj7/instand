package com.instand.domain.repo.prod;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.instand.common.jackson.JacksonMapper;
import com.instand.domain.Interpretation;
import com.instand.domain.repo.InterpretationRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DynamoDB implementation of {@link InterpretationRepository}.
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DynamoInterpretationRepository implements InterpretationRepository {

    public static final String ID_ATTR_NAME = "id";
    public static final String CREATED_BY_USER_ID_ATTR_NAME = "createdByUserId";
    public static final String INTERPRETING_SUBJECT_ID_ATTR_NAME = "interpretingSubjectId";
    public static final String DOC_ATTR_NAME = "document";
    public static final String TABLE_NAME = "na-alpha-interpretation";

    @NonNull
    private final AmazonDynamoDBClient client;

    @NonNull
    private final JacksonMapper jm;

    /**
     * {@inheritDoc}
     */
    @Override
    public Interpretation create(@NonNull Interpretation entity) {
        String json = jm.serializer().serialize(entity);
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Item item = new Item()
                .withPrimaryKey(ID_ATTR_NAME, entity.getId())
                .withString(CREATED_BY_USER_ID_ATTR_NAME, entity.getCreatedByUserId())
                .withString(INTERPRETING_SUBJECT_ID_ATTR_NAME, entity.getInterpretingSubjectId())
                .withJSON(DOC_ATTR_NAME, json);
        table.putItem(item);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Interpretation> find(@NonNull String id) {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Item item = table.getItem(ID_ATTR_NAME, id);
        if (item == null) {
            return Optional.empty();
        }
        String json = item.getJSON(DOC_ATTR_NAME);
        return Optional.ofNullable(jm.deserializer(Interpretation.class).deserialize(json));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Interpretation> findAll() {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        ItemCollection<ScanOutcome> items = table.scan();
        List<Interpretation> results = Lists.newArrayList();
        for (Item item : items) {
            String json = item.getJSON(DOC_ATTR_NAME);
            Interpretation s = jm.deserializer(Interpretation.class).deserialize(json);
            results.add(s);
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Interpretation> findByInterpretingSubjectId(String subjectId) {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Index index = table.getIndex("interpretingSubjectId-index");
        ItemCollection<QueryOutcome> queryResult = index.query("interpretingSubjectId", subjectId);
        List<Item> items = Lists.newArrayList(queryResult.iterator());
        List<Interpretation> results = Lists.newArrayList();
        for (Item item : items) {
            String json = item.getJSON(DOC_ATTR_NAME);
            Interpretation s = jm.deserializer(Interpretation.class).deserialize(json);
            results.add(s);
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(@NonNull Interpretation entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(@NonNull Interpretation entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NonNull Interpretation entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(@NonNull String id) {
        // TODO: more efficient implementation
        return find(id).isPresent();
    }

    private Table getTable(DynamoDB ddb) {
        // TODO construct table name using runtime region and stage data
        return ddb.getTable(TABLE_NAME);
    }
}