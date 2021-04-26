/**
 * Licensed to Apereo under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. Apereo
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at the
 * following location:
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apereo.portal.groups.pags.testers;

import org.apereo.portal.groups.pags.TestPersonAttributesGroupTestDefinition;
import org.apereo.portal.security.IPerson;
import org.apereo.portal.security.PersonFactory;
import org.apereo.portal.security.provider.PersonImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GuestUserTesterTest {

	@Before
	public void setUp() {
		PersonFactory fac = new PersonFactory();
		fac.init();
	}

	@Test
	public void testGuestTrue() throws Exception {
		GuestUserTester tester = new GuestUserTester(
				TestPersonAttributesGroupTestDefinition.mockIPersonAttributesGroupTestDefinition1("", "true"));
		Assert.assertTrue(tester.test(createGuestPerson()));
		Assert.assertFalse(tester.test(createPerson()));
	}

	@Test
	public void testGuestFalse() throws Exception {
		GuestUserTester tester = new GuestUserTester(
				TestPersonAttributesGroupTestDefinition.mockIPersonAttributesGroupTestDefinition1("", "false"));
		Assert.assertTrue(tester.test(createPerson()));
		Assert.assertFalse(tester.test(createGuestPerson()));
	}

	protected static IPerson createGuestPerson() throws Exception {
		IPerson person = new PersonImpl();
		person.setAttribute(IPerson.USERNAME, "guest");

		return person;
	}

	protected static IPerson createPerson() throws Exception {
		IPerson person = new PersonImpl();
		person.setAttribute(IPerson.USERNAME, "non_guest");

		return person;
	}
}
