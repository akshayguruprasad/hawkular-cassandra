package com.indream.casandra.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;

//@Configuration
public class CassandraKeySpaceConfig extends AbstractCassandraConfiguration {

	private static final String KEY_SPACE = "Animal_WellFare";

	@Override
	protected String getKeyspaceName() {
		return KEY_SPACE;
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		CreateKeyspaceSpecification createKeyspaceSpecification = CreateKeyspaceSpecification.createKeyspace(KEY_SPACE);

		return Arrays.asList(createKeyspaceSpecification);

	}

	@Override
	protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
		DropKeyspaceSpecification dropKeyspaceSpecification = DropKeyspaceSpecification.dropKeyspace(KEY_SPACE);
		return Arrays.asList(dropKeyspaceSpecification);
	}

	
	
	
	
	
}
