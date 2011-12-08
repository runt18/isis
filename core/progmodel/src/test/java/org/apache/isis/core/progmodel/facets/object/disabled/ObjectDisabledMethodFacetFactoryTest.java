/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.isis.core.progmodel.facets.object.disabled;

import java.lang.reflect.Method;

import org.apache.isis.core.metamodel.facetapi.Facet;
import org.apache.isis.core.metamodel.facets.FacetFactory.ProcessClassContext;
import org.apache.isis.core.progmodel.facets.AbstractFacetFactoryTest;
import org.apache.isis.core.progmodel.facets.object.disabled.method.DisabledObjectFacetViaDisabledMethod;
import org.apache.isis.core.progmodel.facets.object.disabled.method.DisabledObjectViaDisabledMethodFacetFactory;

public class ObjectDisabledMethodFacetFactoryTest extends AbstractFacetFactoryTest {

    private DisabledObjectViaDisabledMethodFacetFactory facetFactory;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        facetFactory = new DisabledObjectViaDisabledMethodFacetFactory();
    }

    @Override
    protected void tearDown() throws Exception {
        facetFactory = null;
        super.tearDown();
    }

    public void testDisabledMethodPickedUpAndMethodRemoved() {
        final Class<?>[] params = new Class<?>[1];
        params[0] = String.class;

        class Customer {
            @SuppressWarnings("unused")
            public String disabled(String type) {
                return null;
            }
        }
        final Method disabledMethod = findMethod(Customer.class, "disabled", params);
        assertNotNull(disabledMethod);

        final ProcessClassContext processClassContext =
            new ProcessClassContext(Customer.class, methodRemover, facetHolder);
        facetFactory.process(processClassContext);

        final Facet facet = facetHolder.getFacet(DisabledObjectFacet.class);
        assertNotNull(facet);
        assertTrue(facet instanceof DisabledObjectFacetViaDisabledMethod);

        assertTrue(methodRemover.getRemovedMethodMethodCalls().contains(disabledMethod));
    }

}
