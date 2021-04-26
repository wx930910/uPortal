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
package org.apereo.portal.portlets.layout.dlm.remoting.registry.v43;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apereo.portal.layout.dlm.remoting.registry.v43.PortletDefinitionBean;
import org.apereo.portal.portlet.marketplace.IMarketplaceService;
import org.apereo.portal.portlet.marketplace.MarketplacePortletDefinition;
import org.apereo.portal.portlet.om.IPortletDefinition;
import org.apereo.portal.portlet.om.IPortletDefinitionId;
import org.apereo.portal.portlet.om.IPortletDefinitionParameter;
import org.apereo.portal.portlet.om.IPortletPreference;
import org.apereo.portal.portlet.om.IPortletType;
import org.apereo.portal.portlet.om.PortletCategory;
import org.apereo.portal.portlet.om.PortletLifecycleState;
import org.apereo.portal.portlet.registry.IPortletCategoryRegistry;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class PortletDefinitionBeanTest {

	public static IPortletCategoryRegistry mockIPortletCategoryRegistry1() {
		IPortletCategoryRegistry mockInstance = mock(IPortletCategoryRegistry.class);
		when(mockInstance.getChildPortlets(any(PortletCategory.class))).thenAnswer((stubInvo) -> {
			return Collections.EMPTY_SET;
		});
		when(mockInstance.getParentCategories(any(IPortletDefinition.class))).thenAnswer((stubInvo) -> {
			return Collections.EMPTY_SET;
		});
		when(mockInstance.getParentCategories(any(PortletCategory.class))).thenAnswer((stubInvo) -> {
			return Collections.EMPTY_SET;
		});
		return mockInstance;
	}

	public static IMarketplaceService mockIMarketplaceService1() {
		IMarketplaceService mockInstance = mock(IMarketplaceService.class);
		return mockInstance;
	}

	@Mock
	private IPortletPreference portletPref;

	@Mock
	private IPortletDefinitionParameter portletDefParam;

	// Static test data
	private String title = "testTitle";
	private String fName = "testFName";
	private Double averageRating = 4.7;
	private String description = "testDesc";
	private PortletLifecycleState state = PortletLifecycleState.CREATED;
	private int typeId = 456;
	private Long usersRated = 567L;

	@Before
	public void setup() throws Exception {
		portletPref = Mockito.mock(IPortletPreference.class);
		portletDefParam = Mockito.mock(IPortletDefinitionParameter.class);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFromMarketplacePortletDefinition() {
		Long id = 345L;
		String name = "testName";

		String[] keywords = new String[] { "val1", "val2" };
		List<IPortletPreference> prefs = new ArrayList<>();
		prefs.add(portletPref);
		Mockito.when(portletPref.getName()).thenReturn("keywords");
		Mockito.when(portletPref.getValues()).thenReturn(keywords);

		Map<String, IPortletDefinitionParameter> params = new HashMap<>();
		params.put("test1", portletDefParam);

		MarketplacePortletDefinition mpd = buildMarketplacePortletDefinition(id, name, prefs, params);

		PortletDefinitionBean pdb = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd, Locale.ENGLISH, false);
		assertEquals(averageRating, pdb.getAverageRating());
		assertEquals(id, (Long) pdb.getId());
		assertEquals(fName, pdb.getFname());
		assertEquals(title, pdb.getTitle());
		assertEquals(name, pdb.getName());
		assertEquals(description, pdb.getDescription());
		assertEquals(state.toString(), pdb.getState());
		assertEquals(typeId, pdb.getTypeId());
		assertEquals(usersRated, (Long) pdb.getRatingsCount());
		assertEquals(params, pdb.getParameters());
		assertEquals(Arrays.asList(keywords), pdb.getKeywords());
	}

	@Test
	public void testFromMarketplacePortletDefinitionNoKeywords() {
		Long id = 345L;
		String name = "testName";

		// Create a non-keyword list
		String[] nonKeywords = new String[] { "val1", "val2" };
		List<IPortletPreference> prefs = new ArrayList<>();
		prefs.add(portletPref);
		Mockito.when(portletPref.getName()).thenReturn("non-keywords");
		Mockito.when(portletPref.getValues()).thenReturn(nonKeywords);

		MarketplacePortletDefinition mpd = buildMarketplacePortletDefinition(id, name, prefs, null);

		PortletDefinitionBean pdb = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd, Locale.ENGLISH, false);
		assertEquals(Collections.EMPTY_LIST, pdb.getKeywords());
	}

	@Test
	public void testFromMarketplacePortletDefinitionHashCode() {
		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(678L, "testName1", null, null);
		MarketplacePortletDefinition mpd2 = buildMarketplacePortletDefinition(678L, "testName2", null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);
		PortletDefinitionBean pdb2 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd2, Locale.ENGLISH,
				false);

		assertEquals(pdb1.hashCode(), pdb2.hashCode());
	}

	@Test
	public void testCompareToDifferent() {
		String name1 = "testName1";
		String name2 = "testName2";
		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(678L, name1, null, null);
		MarketplacePortletDefinition mpd2 = buildMarketplacePortletDefinition(678L, name2, null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);
		PortletDefinitionBean pdb2 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd2, Locale.ENGLISH,
				false);

		assertEquals(name1.compareTo(name2), pdb1.compareTo(pdb2));
	}

	@Test
	public void testCompareToSimilar() {
		String name1 = "testName1";
		String name2 = "testName1";
		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(678L, name1, null, null);
		MarketplacePortletDefinition mpd2 = buildMarketplacePortletDefinition(678L, name2, null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);
		PortletDefinitionBean pdb2 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd2, Locale.ENGLISH,
				false);

		assertEquals(name1.compareTo(name2), pdb1.compareTo(pdb2));
	}

	@Test
	public void testEqualsSameID() {
		Long id1 = 678L;
		Long id2 = 678L;

		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(id1, "testName", null, null);
		MarketplacePortletDefinition mpd2 = buildMarketplacePortletDefinition(id2, "testName", null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);
		PortletDefinitionBean pdb2 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd2, Locale.ENGLISH,
				false);

		assertTrue(pdb1.equals(pdb2));
	}

	@Test
	public void testEqualsDifferentID() {
		Long id1 = 678L;
		Long id2 = 732L;

		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(id1, "testName", null, null);
		MarketplacePortletDefinition mpd2 = buildMarketplacePortletDefinition(id2, "testName", null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);
		PortletDefinitionBean pdb2 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd2, Locale.ENGLISH,
				false);

		assertFalse(pdb1.equals(pdb2));
	}

	@Test
	public void testEqualsSelf() {
		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(45L, "testName", null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);

		assertTrue(pdb1.equals(pdb1));
	}

	@Test
	public void testEqualsOtherObject() {
		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(45L, "testName", null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);

		assertFalse(pdb1.equals("id1"));
	}

	@Test
	public void testEqualsNull() {
		MarketplacePortletDefinition mpd1 = buildMarketplacePortletDefinition(45L, "testName", null, null);

		PortletDefinitionBean pdb1 = PortletDefinitionBean.fromMarketplacePortletDefinition(mpd1, Locale.ENGLISH,
				false);

		assertFalse(pdb1.equals(null));
	}

	/*
	 * Unable to mock the 3 interfaces needed to instantiate a
	 * MarkeyplacePortletDefinition. Instead, created near-empty shells that get
	 * some test parameters.
	 */
	private MarketplacePortletDefinition buildMarketplacePortletDefinition(Long id, String name,
			List<IPortletPreference> prefs, Map<String, IPortletDefinitionParameter> params) {

		List<IPortletPreference> prefsToUse = prefs;
		if (prefs == null) {
			prefsToUse = new ArrayList<>();
			prefsToUse.add(portletPref);
			Mockito.when(portletPref.getName()).thenReturn("keywords");
			Mockito.when(portletPref.getValues()).thenReturn(new String[] { "val1", "val2" });
		}

		Map<String, IPortletDefinitionParameter> paramsToUse = params;
		if (params == null) {
			paramsToUse = new HashMap<>();
			paramsToUse.put("test1", portletDefParam);
		}

		IPortletDefinition pdis = mock(IPortletDefinition.class);
		PortletLifecycleState[] pdisState = new PortletLifecycleState[1];
		String[] pdisName = new String[1];
		Long[] pdisUsersRated = new Long[1];
		String[] pdisFname = new String[1];
		List<IPortletPreference>[] pdisPrefs = new List[1];
		Long[] pdisId = new Long[1];
		int[] pdisTypeId = new int[1];
		String[] pdisDescr = new String[1];
		Double[] pdisRating = new Double[1];
		String[] pdisTitle = new String[1];
		Map<String, IPortletDefinitionParameter>[] pdisParams = new Map[1];
		doAnswer((stubInvo) -> {
			String nameMockVariable = stubInvo.getArgument(0);
			pdisName[0] = nameMockVariable;
			return null;
		}).when(pdis).setName(any());
		doAnswer((stubInvo) -> {
			String title = stubInvo.getArgument(0);
			pdisTitle[0] = title;
			return null;
		}).when(pdis).setTitle(any());
		when(pdis.getPortletPreferences()).thenAnswer((stubInvo) -> {
			return pdisPrefs[0];
		});
		when(pdis.getDescription()).thenAnswer((stubInvo) -> {
			return pdisDescr[0];
		});
		when(pdis.getParametersAsUnmodifiableMap()).thenAnswer((stubInvo) -> {
			return pdisParams[0];
		});
		when(pdis.getType()).thenAnswer((stubInvo) -> {
			return new IPortletType() {
				@Override
				public int getId() {
					return pdisTypeId[0];
				}

				@Override
				public String getName() {
					return null;
				}

				@Override
				public String getDescription() {
					return null;
				}

				@Override
				public String getCpdUri() {
					return null;
				}

				@Override
				public void setDescription(String descr) {
				}

				@Override
				public void setCpdUri(String cpdUri) {
				}

				@Override
				public String getDataId() {
					return null;
				}

				@Override
				public String getDataTitle() {
					return null;
				}

				@Override
				public String getDataDescription() {
					return null;
				}
			};
		});
		when(pdis.getRating()).thenAnswer((stubInvo) -> {
			return pdisRating[0];
		});
		when(pdis.getUsersRated()).thenAnswer((stubInvo) -> {
			return pdisUsersRated[0];
		});
		doAnswer((stubInvo) -> {
			String descr = stubInvo.getArgument(0);
			pdisDescr[0] = descr;
			return null;
		}).when(pdis).setDescription(any());
		when(pdis.getPortletDefinitionId()).thenAnswer((stubInvo) -> {
			return new IPortletDefinitionId() {
				@Override
				public long getLongId() {
					return pdisId[0];
				}

				@Override
				public String getStringId() {
					return "" + pdisId[0];
				}
			};
		});
		doAnswer((stubInvo) -> {
			Double rating = stubInvo.getArgument(0);
			pdisRating[0] = rating;
			return null;
		}).when(pdis).setRating(any());
		when(pdis.getLifecycleState()).thenAnswer((stubInvo) -> {
			return pdisState[0];
		});
		when(pdis.getFName()).thenAnswer((stubInvo) -> {
			return pdisFname[0];
		});
		when(pdis.getName()).thenAnswer((stubInvo) -> {
			return pdisName[0];
		});
		when(pdis.setPortletPreferences(any(List.class))).thenAnswer((stubInvo) -> {
			List<IPortletPreference> portletPreferences = stubInvo.getArgument(0);
			pdisPrefs[0] = portletPreferences;
			return true;
		});
		when(pdis.getTitle(any(String.class))).thenAnswer((stubInvo) -> {
			return pdisTitle[0];
		});
		doAnswer((stubInvo) -> {
			Long usersRated = stubInvo.getArgument(0);
			pdisUsersRated[0] = usersRated;
			return null;
		}).when(pdis).setUsersRated(any());
		doAnswer((stubInvo) -> {
			String fname = stubInvo.getArgument(0);
			pdisFname[0] = fname;
			return null;
		}).when(pdis).setFName(any());
		pdis.setRating(averageRating);
		pdisId[0] = id;
		pdis.setTitle(title);
		pdis.setFName(fName);
		pdis.setName(name);
		pdis.setDescription(description);
		pdisState[0] = state;
		pdisTypeId[0] = typeId;
		pdis.setUsersRated(usersRated);
		pdis.setPortletPreferences(prefsToUse);
		pdisParams[0] = paramsToUse;
		IMarketplaceService msis = PortletDefinitionBeanTest.mockIMarketplaceService1();
		IPortletCategoryRegistry pcris = PortletDefinitionBeanTest.mockIPortletCategoryRegistry1();
		return new MarketplacePortletDefinition(pdis, msis, pcris);
	}
}
