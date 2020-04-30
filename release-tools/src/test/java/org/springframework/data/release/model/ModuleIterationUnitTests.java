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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Oliver Gierke
 */
class ModuleIterationUnitTests {

	@Test
	void abbreviatesTrailingZerosForNonServiceReleases() {

		TrainIteration iteration = new TrainIteration(ReleaseTrains.DIJKSTRA, Iteration.M1);
		ModuleIteration module = iteration.getModule(Projects.JPA);

		assertThat(module.getShortVersionString()).isEqualTo("1.6 M1");
		assertThat(module.getMediumVersionString()).isEqualTo("1.6 M1 (Dijkstra)");
		assertThat(module.getFullVersionString()).isEqualTo("1.6.0.M1 (Dijkstra M1)");
	}

	@Test
	void doesNotListIterationSuffixForServiceReleases() {

		TrainIteration iteration = new TrainIteration(ReleaseTrains.DIJKSTRA, Iteration.SR1);
		ModuleIteration module = iteration.getModule(Projects.JPA);

		assertThat(module.getShortVersionString()).isEqualTo("1.6.1");
		assertThat(module.getMediumVersionString()).isEqualTo("1.6.1 (Dijkstra SR1)");
		assertThat(module.getFullVersionString()).isEqualTo("1.6.1.RELEASE (Dijkstra SR1)");
	}
}
