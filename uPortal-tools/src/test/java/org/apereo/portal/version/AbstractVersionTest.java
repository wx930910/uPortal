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
package org.apereo.portal.version;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSortedSet;

public class AbstractVersionTest {
	public static AbstractVersion mockAbstractVersion1(int major, int minor, int patch) {
		int mockFieldVariableMinor;
		long mockFieldVariableSerialVersionUID = 1L;
		int mockFieldVariableMajor;
		int mockFieldVariablePatch;
		AbstractVersion mockInstance = spy(AbstractVersion.class);
		mockFieldVariableMajor = major;
		mockFieldVariableMinor = minor;
		mockFieldVariablePatch = patch;
		doAnswer((stubInvo) -> {
			return mockFieldVariablePatch;
		}).when(mockInstance).getPatch();
		doAnswer((stubInvo) -> {
			return mockFieldVariableMinor;
		}).when(mockInstance).getMinor();
		doAnswer((stubInvo) -> {
			return mockFieldVariableMajor;
		}).when(mockInstance).getMajor();
		return mockInstance;
	}

	@Test
	public void testVersionComparison() {
		AbstractVersion v1 = AbstractVersionTest.mockAbstractVersion1(4, 3, 2);
		AbstractVersion v2 = AbstractVersionTest.mockAbstractVersion1(4, 3, 2);

		assertEquals(0, v1.compareTo(v2));
		assertEquals(0, v2.compareTo(v1));
		assertFalse(v1.isBefore(v2));
		assertFalse(v2.isBefore(v1));

		v2 = AbstractVersionTest.mockAbstractVersion1(4, 3, 3);
		assertEquals(-1, v1.compareTo(v2));
		assertEquals(1, v2.compareTo(v1));
		assertTrue(v1.isBefore(v2));
		assertFalse(v2.isBefore(v1));

		Set<AbstractVersion> set = ImmutableSortedSet.of(v1, v2);
		Assert.assertArrayEquals(new SimpleVersion[] { v1, v2 }, set.toArray(new SimpleVersion[0]));

		v1 = AbstractVersionTest.mockAbstractVersion1(5, 0, 0);
		assertEquals(1, v1.compareTo(v2));
		assertEquals(-1, v2.compareTo(v1));
		assertFalse(v1.isBefore(v2));
		assertTrue(v2.isBefore(v1));

		set = ImmutableSortedSet.of(v1, v2);
		Assert.assertArrayEquals(new SimpleVersion[] { v2, v1 }, set.toArray(new SimpleVersion[0]));
	}
}
