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
package org.apereo.portal.groups.pags;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apereo.portal.groups.pags.testers.IntegerLTTester;
import org.apereo.portal.groups.pags.testers.MissingAttributeTester;
import org.apereo.portal.groups.pags.testers.NbValuesEQTester;
import org.apereo.portal.groups.pags.testers.NbValuesGETester;
import org.apereo.portal.groups.pags.testers.NbValuesGTTester;
import org.apereo.portal.groups.pags.testers.NbValuesLETester;
import org.apereo.portal.groups.pags.testers.NbValuesLTTester;
import org.apereo.portal.groups.pags.testers.RegexTester;
import org.apereo.portal.security.IPerson;
import org.apereo.portal.security.provider.PersonImpl;
import org.junit.Before;
import org.junit.Test;

/** Test PAGS IPersonTester implementations against multi-valued attributes. */
public class MultivaluedPAGSTest {

	IPerson person;
	String strAttributeName = "mail";
	int strNbValues = 0;
	String intAttributeName = "num";

	@Before
	public void setUp() {
		person = new PersonImpl();
		person.setUserName("testuser");

		List<Object> emailAddresses = new ArrayList<Object>();
		emailAddresses.add("testuser1@somewhere.com");
		emailAddresses.add("testuser1@elsewhere.com");
		strNbValues = emailAddresses.size();
		person.setAttribute(strAttributeName, emailAddresses);

		List<Object> nums = new ArrayList<Object>();
		nums.add("123");
		nums.add("246");
		person.setAttribute(intAttributeName, nums);
	}

	@Test
	public void testMultivaluedRegex() {
		IPersonTester tester = new RegexTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, ".*somewhere.*"));
		assertTrue(tester.test(person));

		tester = new RegexTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, ".*nowhere.*"));
		assertFalse(tester.test(person));
	}

	@Test
	public void testIntegerLT() {
		IPersonTester tester = new IntegerLTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(intAttributeName, "124"));
		assertTrue(tester.test(person));

		tester = new IntegerLTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(intAttributeName, "122"));
		assertFalse(tester.test(person));
	}

	@Test
	public void testNbValuesGT() {
		IPersonTester tester = new NbValuesGTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues - 1)));
		assertTrue(tester.test(person));

		tester = new NbValuesGTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues)));
		assertFalse(tester.test(person));

		tester = new NbValuesGTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues + 1)));
		assertFalse(tester.test(person));
	}

	@Test
	public void testNbValuesGE() {
		IPersonTester tester = new NbValuesGETester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues)));
		assertTrue(tester.test(person));

		tester = new NbValuesGETester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues - 1)));
		assertTrue(tester.test(person));

		tester = new NbValuesGETester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues + 1)));
		assertFalse(tester.test(person));
	}

	@Test
	public void testNbValuesLT() {
		IPersonTester tester = new NbValuesLTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues + 1)));
		assertTrue(tester.test(person));

		tester = new NbValuesLTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues)));
		assertFalse(tester.test(person));

		tester = new NbValuesLTTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues - 1)));
		assertFalse(tester.test(person));
	}

	@Test
	public void testNbValuesLE() {
		IPersonTester tester = new NbValuesLETester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues)));
		assertTrue(tester.test(person));

		tester = new NbValuesLETester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues + 1)));
		assertTrue(tester.test(person));

		tester = new NbValuesLETester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues - 1)));
		assertFalse(tester.test(person));
	}

	@Test
	public void testNbValuesEQ() {
		IPersonTester tester = new NbValuesEQTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues)));
		assertTrue(tester.test(person));

		tester = new NbValuesEQTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues + 1)));
		assertFalse(tester.test(person));

		tester = new NbValuesEQTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, Integer.toString(strNbValues - 1)));
		assertFalse(tester.test(person));
	}

	@Test
	public void testMissingAttributeFalse() {
		IPersonTester tester = new MissingAttributeTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName, "something"));

		assertFalse(tester.test(person));
	}

	@Test
	public void testMissingAttribute() {
		IPersonTester tester = new MissingAttributeTester(TestPersonAttributesGroupTestDefinition
				.mockIPersonAttributesGroupTestDefinition1(strAttributeName + "not", "something"));

		assertTrue(tester.test(person));
	}
}
