package com.instand.dataaccess.repo.prod;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.instand.common.env.EnvironmentContext;
import com.instand.common.jackson.JacksonMapper;
import com.instand.domain.User;
import com.instand.domain.repo.EntityAlreadyExistsException;
import com.instand.domain.repo.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DynamoUserRepository implements UserRepository {

    public static final String ID_ATTR_NAME = "id";
    public static final String USERNAME_ATTR_NAME = "username";
    public static final String EMAIL_ADDRESS_ATTR_NAME = "emailAddress";
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
    public User create(@NonNull User entity) {
        if (exists(entity.getId())) {
            throw new EntityAlreadyExistsException("Entity exists with id " + entity.getId());
        }
        String json = jm.serializer().serialize(entity);
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Item item = new Item()
                .withPrimaryKey(ID_ATTR_NAME, entity.getId())
                .withString(USERNAME_ATTR_NAME, entity.getUsername())
                .withString(EMAIL_ADDRESS_ATTR_NAME, entity.getEmailAddress().orElse(null))
                .withJSON(DOC_ATTR_NAME, json);
        table.putItem(item);
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> find(@NonNull String id) {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Item item = table.getItem(ID_ATTR_NAME, id);
        if (item == null) {
            return Optional.empty();
        }
        String json = item.getJSON(DOC_ATTR_NAME);
        return Optional.ofNullable(jm.deserializer(User.class).deserialize(json));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findByUsername(String username) {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Index index = table.getIndex("username-index");
        ItemCollection<QueryOutcome> results = index.query("username", username);
        List<Item> items = Lists.newArrayList(results.iterator());
        if (items.isEmpty()) {
            return Optional.empty();
        }
        if (items.size() > 1) {
            throw new IllegalStateException("Multiple users found for username: " + username);
        }
        String json = items.get(0).getJSON(DOC_ATTR_NAME);
        return Optional.of(jm.deserializer(User.class).deserialize(json));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsUsername(String username) {
        // TODO: use a more efficient implementation
        return findByUsername(username).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findByEmailAddress(String emailAddress) {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        Index index = table.getIndex("emailAddress-index");
        ItemCollection<QueryOutcome> results = index.query("emailAddress", emailAddress);
        List<Item> items = Lists.newArrayList(results.iterator());
        if (items.isEmpty()) {
            return Optional.empty();
        }
        if (items.size() > 1) {
            throw new IllegalStateException("Multiple user for emailAddress: " + emailAddress);
        }
        String json = items.get(0).getJSON(DOC_ATTR_NAME);
        return Optional.of(jm.deserializer(User.class).deserialize(json));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsEmailAddress(String emailAddress) {
        // TODO: use a more efficient implementation
        return findByEmailAddress(emailAddress).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        DynamoDB ddb = new DynamoDB(client);
        Table table = getTable(ddb);
        ItemCollection<ScanOutcome> items = table.scan();
        List<User> results = Lists.newArrayList();
        for (Item item : items) {
            String json = item.getJSON(DOC_ATTR_NAME);
            User s = jm.deserializer(User.class).deserialize(json);
            results.add(s);
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(@NonNull User entity) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(@NonNull User entity) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(@NonNull User entity) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(@NonNull String id) {
        // TODO: use more efficient implementation
        return find(id).isPresent();
    }

    private Table getTable(DynamoDB ddb) {
        return ddb.getTable(resolveTableName());
    }

    private String resolveTableName() {
        return String.format("%s-user", this.ec.getStage().getId());
    }

}
