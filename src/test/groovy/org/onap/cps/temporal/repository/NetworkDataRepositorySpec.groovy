/*
 * ============LICENSE_START=======================================================
 * Copyright (c) 2021 Bell Canada.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.onap.cps.temporal.repository


import org.onap.cps.temporal.domain.NetworkData
import org.onap.cps.temporal.repository.containers.TimescaleContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import java.time.OffsetDateTime

/**
 * Test specification for network data repository.
 */
@SpringBootTest
@Testcontainers
class NetworkDataRepositorySpec extends Specification {

    def observedTimestamp = OffsetDateTime.now()
    def dataspaceName = 'TEST_DATASPACE'
    def schemaSetName = 'TEST_SCHEMA_SET'
    def anchorName = 'TEST_ANCHOR'
    def payload = '{ \"message\": \"Hello World!\" }'

    @Autowired
    NetworkDataRepository networkDataRepository

    def networkData = NetworkData.builder().observedTimestamp(observedTimestamp).dataspace(dataspaceName)
            .schemaSet(schemaSetName).anchor(anchorName).payload(payload).build()

    @Shared
    def databaseTestContainer = TimescaleContainer.getInstance()

    def setupSpec() {
        databaseTestContainer.start()
    }

    def 'Store latest network data in timeseries database.'() {
        when: 'a new Network Data is stored'
            NetworkData savedData = networkDataRepository.save(networkData)
        then: ' the saved Network Data is returned'
            savedData.getDataspace() == networkData.getDataspace()
            savedData.getSchemaSet() == networkData.getSchemaSet()
            savedData.getAnchor() == networkData.getAnchor()
            savedData.getPayload() == networkData.getPayload()
            savedData.getObservedTimestamp() == networkData.getObservedTimestamp()
        and: ' createdTimestamp is auto populated by db '
            networkData.getCreatedTimestamp() == null
            savedData.getCreatedTimestamp() != null
        and: ' the CreationTimestamp is ahead of ObservedTimestamp'
            savedData.getCreatedTimestamp() > networkData.getObservedTimestamp()
    }
}
