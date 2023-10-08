/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.chenyilei.aec.common.utils;

import cn.chenyilei.aec.common.exceptions.CommonException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 断言工具,丢出 CommonException，方便全局异常拦截
 */
public abstract class AssertUtil {

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw CommonException.createBizException(message);
		}
	}


	public static void notBlank(String object, String message) {
		if (org.apache.commons.lang3.StringUtils.isBlank(object)) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}

	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text)) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void hasLength(String text) {
		hasLength(text,
				"[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * <pre class="code">Assert.doesNotContain(name, "rod", "Name must not contain 'rod'");</pre>
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param message the exception message to use if the assertion fails
	 * @throws CommonException if the text contains the substring
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) &&
				textToSearch.contains(substring)) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
	}

	/**
	 * Assert that an array contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(array, "The array must contain elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws CommonException if the object array is {@code null} or contains no elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ObjectUtils.isEmpty(array)) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that an array contains no {@code null} elements.
	 * <p>Note: Does not complain if the array is empty!
	 * <pre class="code">Assert.noNullElements(array, "The array must contain non-null elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws CommonException if the object array contains a {@code null} element
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw CommonException.createBizException(message);
				}
			}
		}
	}

	@Deprecated
	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}

	/**
	 * Assert that a collection contains elements; that is, it must not be
	 * {@code null} and must contain at least one element.
	 * <pre class="code">Assert.notEmpty(collection, "Collection must contain elements");</pre>
	 * @param collection the collection to check
	 * @param message the exception message to use if the assertion fails
	 * @throws CommonException if the collection is {@code null} or
	 * contains no elements
	 */
	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw CommonException.createBizException(message);
		}
	}

	/**
	 * @deprecated as of 4.3.7, in favor of {@link #notEmpty(Collection, String)}
	 */
	@Deprecated
	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	/**
	 * Assert that a Map contains entries; that is, it must not be {@code null}
	 * and must contain at least one entry.
	 * <pre class="code">Assert.notEmpty(map, "Map must contain entries");</pre>
	 * @param map the map to check
	 * @param message the exception message to use if the assertion fails
	 * @throws CommonException if the map is {@code null} or contains no entries
	 */
	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map)) {
			throw CommonException.createBizException(message);
		}
	}

	@Deprecated
	public static void notEmpty(Map<?, ?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * <pre class="code">Assert.instanceOf(Foo.class, foo, "Foo expected");</pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @param message a message which will be prepended to provide further context.
	 * If it is empty or ends in ":" or ";" or "," or ".", a full exception message
	 * will be appended. If it ends in a space, the name of the offending object's
	 * type will be appended. In any other case, a ":" with a space and the name
	 * of the offending object's type will be appended.
	 * @throws CommonException if the object is not an instance of type
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			instanceCheckFailed(type, obj, message);
		}
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * <pre class="code">Assert.instanceOf(Foo.class, foo);</pre>
	 * @param type the type to check against
	 * @param obj the object to check
	 * @throws CommonException if the object is not an instance of type
	 */
	public static void isInstanceOf(Class<?> type, Object obj) {
		isInstanceOf(type, obj, "");
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass, "Number expected");</pre>
	 * @param superType the super type to check against
	 * @param subType the sub type to check
	 * @param message a message which will be prepended to provide further context.
	 * If it is empty or ends in ":" or ";" or "," or ".", a full exception message
	 * will be appended. If it ends in a space, the name of the offending sub type
	 * will be appended. In any other case, a ":" with a space and the name of the
	 * offending sub type will be appended.
	 * @throws CommonException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Super type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			assignableCheckFailed(superType, subType, message);
		}
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * @param superType the super type to check
	 * @param subType the sub type to check
	 * @throws CommonException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}


	private static void instanceCheckFailed(Class<?> type, Object obj, String msg) {
		String className = (obj != null ? obj.getClass().getName() : "null");
		String result = "";
		boolean defaultMessage = true;
		if (StringUtils.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, className);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + ("Object of class [" + className + "] must be an instance of " + type);
		}
		throw CommonException.createBizException(result);
	}

	private static void assignableCheckFailed(Class<?> superType, Class<?> subType, String msg) {
		String result = "";
		boolean defaultMessage = true;
		if (StringUtils.hasLength(msg)) {
			if (endsWithSeparator(msg)) {
				result = msg + " ";
			}
			else {
				result = messageWithTypeName(msg, subType);
				defaultMessage = false;
			}
		}
		if (defaultMessage) {
			result = result + (subType + " is not assignable to " + superType);
		}
		throw CommonException.createBizException(result);
	}

	private static boolean endsWithSeparator(String msg) {
		return (msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith("."));
	}

	private static String messageWithTypeName(String msg, Object typeName) {
		return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
	}

}
