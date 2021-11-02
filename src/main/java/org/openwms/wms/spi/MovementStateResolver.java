/*
 * Copyright 2005-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.wms.spi;

/**
 * A MovementStateResolver requires to resolve the basic states of a {@code Movement}
 *
 * @author Heiko Scherrer
 */
public interface MovementStateResolver {

    /**
     * Get the state when a {@code Movement} is created.
     *
     * @return The state
     */
    DefaultMovementState getNewState();

    /**
     * Get the state when a {@code Movement} is completely done.
     *
     * @return The state
     */
    DefaultMovementState getCompletedState();
}
