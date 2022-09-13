package com.instand.dataaccess.repo.prod;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.instand.common.env.EnvironmentContext;
import com.instand.common.jackson.JacksonMapper;
import com.instand.domain.Subject;
import com.instand.domain.repo.SubjectRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * DynamoDB implementation of {@link SubjectRepository}.
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DynamoSubjectRepository implements SubjectRepository {

    public static final String ID_ATTR_NAME = "id";
    public static final String CREATED_BY_USER_ID_ATTR_NAME = "createdByUserId";
    public static final String DOC_ATTR_NAME = "document";

    @NonNull
    private final EnvironmentContext ec;

    @NonNull
    private final AmazonDynamoDBClient client;

    @NonNull
    private final JacksonMapper jm;

    /**
     * {@inheritDoc}
     */
    @Override
    public Subject create(@NonNull Subject entity) {
        String json = jm.serializer().serialize(entity);
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Item item = new Item()
                .withPrimaryKey(ID_ATTR_NAME, entity.getId())
                .withString(CREATED_BY_USER_ID_ATTR_NAME, entity.getCreatedByUserId())
                .withJSON(DOC_ATTR_NAME, json);
        table.putItem(item);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Subject> find(@NonNull String id) {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Item item = table.getItem(ID_ATTR_NAME, id);
        if (item == null) {
            return Optional.empty();
        }
        String json = item.getJSON(DOC_ATTR_NAME);
        return Optional.ofNullable(jm.deserializer(Subject.class).deserialize(json));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subject> findAll() {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        ItemCollection<ScanOutcome> items = table.scan();
        List<Subject> results = Lists.newArrayList();
        for (Item item : items) {
            String json = item.getJSON(DOC_ATTR_NAME);
            Subject s = jm.deserializer(Subject.class).deserialize(json);
            results.add(s);
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(@NonNull Subject entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(@NonNull Subject entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NonNull Subject entity) {
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
        return ddb.getTable(resolveTableName());
    }

    private String resolveTableName() {
        return String.format("%s-subject", this.ec.getStage().getId());
    }
}