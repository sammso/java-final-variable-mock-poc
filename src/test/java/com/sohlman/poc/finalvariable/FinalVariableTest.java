package com.sohlman.poc.finalvariable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

/**
 * 
 * @author Sampsa Sohlman
 * 
 * Thanks to http://stackoverflow.com/questions/2474017/using-reflection-to-change-static-final-file-separatorchar-for-unit-testing
 * 
 */
public class FinalVariableTest {

	@After
	public void tearDown() throws Exception {
		for (Entry<Field, Object> entry : _changedFieldMap.entrySet()) {
			Field field = entry.getKey();
			field.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField
					.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(null, entry.getValue());
			modifiersField.setAccessible(false);
		}
		_changedFieldMap.clear();
	}

	@Test
	public void test1WithNoModify() throws Exception {
		Assert.assertEquals(FinalVariables.ALPHABET, "ABC");
	}

	@Test
	public void test2WithModify() throws Exception {
		setFinalStatic(FinalVariables.class.getField("ALPHABET"), "BDC");
		Assert.assertEquals(FinalVariables.ALPHABET, "BDC");
	}

	@Test
	public void test3WithNoModifyAgain() throws Exception {
		Assert.assertEquals(FinalVariables.ALPHABET, "ABC");
	}
	
	@Test
	public void testBoolean1WithNoModify() throws Exception {
		Assert.assertEquals(FinalVariables.MYBOOLEAN, true);
	}

	@Test
	public void testBoolean2WithModify() throws Exception {
		setFinalStatic(FinalVariables.class.getField("MYBOOLEAN"), false);
		Assert.assertEquals(FinalVariables.MYBOOLEAN, false);
	}

	@Test
	public void testBoolean3WithNoModifyAgain() throws Exception {
		Assert.assertEquals(FinalVariables.MYBOOLEAN, true);
	}	

	protected void setFinalStatic(Field field, Object newValue)
			throws Exception {
		Object obj = field.get(null);
		field.setAccessible(true);

		// remove final modifier from field

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);

		_changedFieldMap.put(field, obj);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);

	}

	private Map<Field, Object> _changedFieldMap = new HashMap<Field, Object>();
}
