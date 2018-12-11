package com.indream.casandra.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
public class CasandraConfig {

	@Bean
	public Cluster getCluster() {
		return Cluster.builder().withoutMetrics().addContactPoint("127.0.0.1").withPort(9042).build();
	}

	@Bean
	public Session getSession() {

		return getCluster().connect();

	}

	@PostConstruct
	public void keySpace() {
		StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append("analytics12")
				.append(" WITH replication = {").append("'class':'").append("SimpleStrategy")
				.append("','replication_factor':").append(2).append("};");
		String query = sb.toString();
		getSession().execute(query);
	}

}
