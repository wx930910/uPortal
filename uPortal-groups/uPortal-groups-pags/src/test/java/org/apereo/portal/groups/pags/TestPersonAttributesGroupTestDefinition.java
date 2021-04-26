package org.apereo.portal.groups.pags;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apereo.portal.groups.pags.dao.IPersonAttributesGroupTestDefinition;
import org.apereo.portal.groups.pags.dao.IPersonAttributesGroupTestGroupDefinition;
import org.dom4j.Element;

/** Supports instantiating {@link IPersonTester} objects in unit tests. */
public final class TestPersonAttributesGroupTestDefinition {

	public static IPersonAttributesGroupTestDefinition mockIPersonAttributesGroupTestDefinition1(String attributeName,
			String testValue) {
		String mockFieldVariableTestValue;
		String mockFieldVariableAttributeName;
		IPersonAttributesGroupTestDefinition mockInstance = mock(IPersonAttributesGroupTestDefinition.class);
		mockFieldVariableAttributeName = attributeName;
		mockFieldVariableTestValue = testValue;
		when(mockInstance.getAttributeName()).thenAnswer((stubInvo) -> {
			return mockFieldVariableAttributeName;
		});
		when(mockInstance.getTesterClassName()).thenThrow(new UnsupportedOperationException());
		when(mockInstance.getId()).thenThrow(new UnsupportedOperationException());
		doThrow(new UnsupportedOperationException()).when(mockInstance)
				.setTestGroup(any(IPersonAttributesGroupTestGroupDefinition.class));
		doThrow(new UnsupportedOperationException()).when(mockInstance).toElement(any(Element.class));
		doThrow(new UnsupportedOperationException()).when(mockInstance).setTestValue(any(String.class));
		when(mockInstance.getTestValue()).thenAnswer((stubInvo) -> {
			return mockFieldVariableTestValue;
		});
		when(mockInstance.getTestGroup()).thenThrow(new UnsupportedOperationException());
		doThrow(new UnsupportedOperationException()).when(mockInstance).setAttributeName(any(String.class));
		doThrow(new UnsupportedOperationException()).when(mockInstance).setTesterClassName(any(String.class));
		return mockInstance;
	}
}
