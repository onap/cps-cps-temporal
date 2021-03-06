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

package org.onap.cps.temporal.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Identifier class for network data.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NetworkDataId implements Serializable {

    private static final long serialVersionUID = -1039604338648260766L;

    private OffsetDateTime observedTimestamp;
    private String dataspace;
    private String anchor;

}
