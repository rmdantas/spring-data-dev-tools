/*
 * Copyright 2014-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.release.model;

import static org.springframework.data.release.model.Iteration.*;
import static org.springframework.data.release.model.Projects.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Oliver Gierke
 * @author Mark Paluch
 */
public class ReleaseTrains {

	public static final List<Train> TRAINS;
	public static final Train CODD, DIJKSTRA, EVANS, FOWLER, GOSLING, HOPPER, INGALLS, KAY, LOVELACE, MOORE, NEUMANN,
			OCKHAM, PASCAL;

	static {

		CODD = codd();
		DIJKSTRA = dijkstra();
		EVANS = DIJKSTRA.next("Evans", Transition.MINOR);
		FOWLER = EVANS.next("Fowler", Transition.MINOR);
		GOSLING = FOWLER.next("Gosling", Transition.MINOR, new Module(KEY_VALUE, "1.0"));
		HOPPER = GOSLING.next("Hopper", Transition.MINOR, new Module(SOLR, "2.0"), new Module(ENVERS, "1.0"),
				new Module(NEO4J, "4.1"), new Module(COUCHBASE, "2.1"), new Module(ELASTICSEARCH, "2.0"));
		INGALLS = HOPPER.next("Ingalls", Transition.MINOR, new Module(LDAP, "1.0"));

		Iteration RC3 = new Iteration("RC3", GA);
		Iteration RC2 = new Iteration("RC2", RC3);
		Iteration RC1 = new Iteration("RC1", RC2);
		Iteration M4 = new Iteration("M4", RC1);
		Iteration M3 = new Iteration("M3", M4);
		Iteration M2 = new Iteration("M2", M3);
		Iteration M1 = new Iteration("M1", M2);

		KAY = INGALLS.next("Kay", Transition.MAJOR, new Module(GEODE, "2.0"));

		LOVELACE = KAY.next("Lovelace", Transition.MINOR, new Module(JDBC, "1.0"), new Module(SOLR, "4.0"));

		MOORE = LOVELACE.next("Moore", Transition.MINOR);

		NEUMANN = MOORE.next("Neumann", Transition.MINOR, //
				new Module(COUCHBASE, "4.0"), //
				new Module(CASSANDRA, "3.0"), //
				new Module(ELASTICSEARCH, "4.0"), //
				new Module(MONGO_DB, "3.0"), //
				new Module(JDBC, "2.0"), //
				new Module(R2DBC, "1.1")) //
				.filterModules(module -> !module.getProject().equals(GEMFIRE));

		OCKHAM = NEUMANN.next("Ockham", Transition.MINOR, //
				new Module(BOM, "2020.0.0"), //
				new Module(NEO4J, "6.0") //
		).withCalver("2020.0");

		PASCAL = OCKHAM.next("Pascal", Transition.MINOR) //
				.filterModules(module -> !module.getProject().equals(SOLR))
				.withCalver("2021.0") //
				.withIterations(new Train.Iterations(M1, M2, RC1, RC2, GA, SR1, SR2, SR3, SR4, SR5));

		// Trains

		TRAINS = Arrays.asList(CODD, DIJKSTRA, EVANS, FOWLER, GOSLING, HOPPER, INGALLS, KAY, LOVELACE, MOORE, NEUMANN,
				OCKHAM, PASCAL);
	}

	private static Train codd() {

		Module build = new Module(BUILD, "1.3");
		Module commons = new Module(COMMONS, "1.7");
		Module jpa = new Module(JPA, "1.5");
		Module mongoDb = new Module(MONGO_DB, "1.4");
		Module neo4j = new Module(NEO4J, "3.0");
		Module solr = new Module(SOLR, "1.1");

		Module rest = new Module(REST, "2.0");

		return new Train("Codd", build, commons, jpa, mongoDb, neo4j, solr, rest);
	}

	private static Train dijkstra() {

		Module build = new Module(BUILD, "1.4");
		Module commons = new Module(COMMONS, "1.8");
		Module jpa = new Module(JPA, "1.6");
		Module mongoDb = new Module(MONGO_DB, "1.5");
		Module neo4j = new Module(NEO4J, "3.1");
		Module solr = new Module(SOLR, "1.2");
		Module couchbase = new Module(COUCHBASE, "1.1");
		Module cassandra = new Module(CASSANDRA, "1.0");
		Module elasticsearch = new Module(ELASTICSEARCH, "1.0", "M2");
		Module gemfire = new Module(GEMFIRE, "1.4");
		Module redis = new Module(REDIS, "1.3");

		Module rest = new Module(REST, "2.1");

		return new Train("Dijkstra", build, commons, jpa, mongoDb, neo4j, solr, couchbase, cassandra, elasticsearch,
				gemfire, redis, rest);
	}

	public static Train getTrainByName(String name) {

		return TRAINS.stream() //
				.filter(it -> it.getName().equalsIgnoreCase(name)) //
				.findFirst() //
				.orElse(null);
	}

	public static Train getTrainByCalver(Version calver) {

		return TRAINS.stream() //
				.filter(Train::usesCalver)
				.filter(it -> it.getCalver().getMajor() == calver.getMajor() && it.getCalver().getMinor() == calver.getMinor()) //
				.findFirst() //
				.orElse(null);
	}

	public static Project getProjectByName(String name) {

		return PROJECTS.stream() //
				.filter(it -> it.getName().equalsIgnoreCase(name)) //
				.findFirst() //
				.orElse(null);
	}

	public static List<Train> trains() {
		return TRAINS;
	}
}
