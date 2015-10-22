package com.instand.domain.repo.prod;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.instand.domain.repo.SubjectRepository;
import com.instand.domain.repo.UserRepository;

/**
 * Provides Guice configuration for repositories through production implementations.
 */
public class ProductionRepositoryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SubjectRepository.class).to(DynamoSubjectRepository.class);
        bind(UserRepository.class).to(DynamoUserRepository.class);
    }

    @Provides
    @Singleton
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new ProfileCredentialsProvider();
    }

    @Provides
    @Singleton
    public AmazonDynamoDBClient amazonDynamoDBClient(AWSCredentialsProvider provider) {
        return new AmazonDynamoDBClient(provider);
    }
}
