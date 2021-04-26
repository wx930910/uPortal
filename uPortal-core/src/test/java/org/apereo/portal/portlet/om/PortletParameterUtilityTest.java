package org.apereo.portal.portlet.om;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PortletParameterUtilityTest {

	public IPortletDefinitionParameter mockIPortletDefinitionParameter1(String name, String value) {
		String mockFieldVariableValue;
		String mockFieldVariableName;
		IPortletDefinitionParameter mockInstance = mock(IPortletDefinitionParameter.class);
		mockFieldVariableName = name;
		mockFieldVariableValue = value;
		when(mockInstance.getValue()).thenAnswer((stubInvo) -> {
			return mockFieldVariableValue;
		});
		when(mockInstance.getName()).thenAnswer((stubInvo) -> {
			return mockFieldVariableName;
		});
		return mockInstance;
	}

	@Test
	public void nullMapConvertsToNullMap() {
		assertNull(PortletParameterUtility.parameterMapToStringStringMap(null));
	}

	@Test
	public void convertsParameterMapToStringMap() {
		Map<String, IPortletDefinitionParameter> input = new HashMap<>(2);

		IPortletDefinitionParameter iconParam = mockIPortletDefinitionParameter1("icon", "dashboard");
		IPortletDefinitionParameter altMaxUrlParam = mockIPortletDefinitionParameter1("alternativeMaximizedLink",
				"https://public.my.wisc.edu");

		input.put("icon", iconParam);
		input.put("alternativeMaximizedLink", altMaxUrlParam);

		Map<String, String> expected = new HashMap<>(2);

		expected.put("icon", "dashboard");
		expected.put("alternativeMaximizedLink", "https://public.my.wisc.edu");

		assertEquals(expected, PortletParameterUtility.parameterMapToStringStringMap(input));
	}
}
