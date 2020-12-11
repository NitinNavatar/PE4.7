package com.navatar.generic;

import java.util.Map;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 
 * @author Ankur Rana
 * @description modified all the soft assert methods to take screeshot at every failure.
 */

public class SoftAssert extends Assertion {

	// LinkedHashMap to preserve the order
	private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();

	abstract private static class SimpleAssert<T> implements IAssert<T> {
		private final T actual;
		private final T expected;
		private final String m_message;

		public SimpleAssert(String message) {
			this(null, null, message);
		}

		public SimpleAssert(T actual, T expected) {
			this(actual, expected, null);
		}

		public SimpleAssert(T actual, T expected, String message) {
			this.actual = actual;
			this.expected = expected;
			m_message = message;
		}

		@Override
		public String getMessage() {
			return m_message;
		}

		@Override
		public T getActual() {
			return actual;
		}

		@Override
		public T getExpected() {
			return expected;
		}

		@Override
		abstract public void doAssert();
	}

	@Override
	protected void doAssert(IAssert<?> a) {
		onBeforeAssert(a);
		try {
			a.doAssert();
			onAssertSuccess(a);
		} catch (AssertionError ex) {
			onAssertFailure(a, ex);
			m_errors.put(ex, a);
		} finally {
			onAfterAssert(a);
		}
	}

	public void assertAll() {
		if (!m_errors.isEmpty()) {
			StringBuilder sb = new StringBuilder("The following asserts failed:");
			boolean first = true;
			for (Map.Entry<AssertionError, IAssert<?>> ae : m_errors.entrySet()) {
				if (first) {
					first = false;
					AppListeners.extentLog.log(LogStatus.INFO, sb.toString(), "");
				} else {
					sb.append(",");
				}
				sb.append("\n\t");
				sb.append(ae.getKey().getMessage());
				AppListeners.extentLog.log(LogStatus.ERROR, ae.getKey().getMessage(), AppListeners.extentLog.addScreenCapture(System.getProperty("user.dir")+"/screenshot/"+ae.getKey().getMessage().split("Name:")[1].split("expected")[0].trim()));
			}
			throw new AssertionError(sb.toString());
		}
	}

	public void assertTrue(final boolean condition, final String message) {

		doAssert(new SimpleAssert<Boolean>(condition, Boolean.TRUE,
				message + " " + CommonLib.logLineNumber(new Throwable()) + " Screenshot Name: " + compare(condition, true) + "\t") {
			@Override
			public void doAssert() {
				org.testng.Assert.assertTrue(condition,
						message + " " + CommonLib.logLineNumber(new Throwable()) + "\tScreenshot Name: " + compare(condition, true) + "\t");
			}
		});
	}

	public void assertTrue(final boolean condition) {
		compare(condition, true);
		doAssert(new SimpleAssert<Boolean>(condition, Boolean.TRUE) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertTrue(condition);
			}
		});
	}

	public void assertFalse(final boolean condition, final String message) {
		doAssert(new SimpleAssert<Boolean>(condition, Boolean.FALSE,
				message + " " + CommonLib.logLineNumber(new Throwable()) + CommonLib.logLineNumber(new Throwable()) + " Screenshot Name: " + compare(condition, false) + "\t") {
			@Override
			public void doAssert() {
				org.testng.Assert.assertFalse(condition,
						message + " " + CommonLib.logLineNumber(new Throwable()) + "\tScreenshot Name: " + compare(condition, false) + "\t");
			}
		});
	}

	public void assertFalse(final boolean condition) {
		compare(condition, false);
		doAssert(new SimpleAssert<Boolean>(condition, Boolean.FALSE) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertFalse(condition);
			}
		});
	}

	public <T> void assertEquals(final T actual, final T expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<T>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public <T> void assertEquals(final T actual, final T expected) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<T>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected);
			}
		});
	}

	public void assertEquals(final String actual, final String expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<String>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertEquals(final String actual, final String expected) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<String>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected);
			}
		});
	}

	public void assertEquals(final long actual, final long expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Long>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertEquals(final long actual, final long expected) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Long>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected);
			}
		});
	}

	public void assertEquals(final boolean actual, final boolean expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Boolean>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertEquals(final boolean actual, final boolean expected) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Boolean>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected);
			}
		});
	}

	public void assertEquals(final char actual, final char expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Character>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertEquals(final char actual, final char expected) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Character>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected);
			}
		});
	}

	public void assertEquals(final int actual, final int expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Integer>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertEquals(final int actual, final int expected) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Integer>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertEquals(actual, expected);
			}
		});
	}

	public void assertNotNull(final Object object) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(object, null) + "\t";
		doAssert(new SimpleAssert<Object>(object, null) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotNull(object);
			}
		});
	}

	public void assertNotNull(final Object object, final String message) {
		String screenShotName = "";
		final String msg;
		if (object == null) {
			screenShotName = "\tScreenshot Name: " + CommonLib.screenshot(AppListeners.currentlyExecutingTC) + "\t";
			msg = message + screenShotName;
		} else {
			msg = message;
		}
		doAssert(new SimpleAssert<Object>(object, null, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotNull(object, message + msg);
			}
		});
	}

	public void assertNull(final Object object) {
		String screenShotName = "\tScreenshot Name: " + compare(object, null) + "\t";
		doAssert(new SimpleAssert<Object>(object, null) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNull(object);
			}
		});
	}

	public void assertNull(final Object object, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(object, null) + "\t";
		doAssert(new SimpleAssert<Object>(object, null, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNull(object, message + screenShotName);
			}
		});
	}

	public void assertSame(final Object actual, final Object expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Object>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertSame(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertSame(final Object actual, final Object expected) {
		String screenShotName = "\tScreenshot Name: " + compare(actual, expected) + "\t";
		doAssert(new SimpleAssert<Object>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertSame(actual, expected);
			}
		});
	}

	public void assertNotSame(final Object actual, final Object expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Object>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotSame(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertNotSame(final Object actual, final Object expected) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Object>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotSame(actual, expected);
			}
		});
	}

	public void assertNotEquals(final Object actual, final Object expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Object>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertNotEquals(final Object actual, final Object expected) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Object>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected);
			}
		});
	}

	public void assertNotEquals(final String actual, final String expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<String>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertNotEquals(final String actual, final String expected) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<String>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected);
			}
		});
	}

	public void assertNotEquals(final boolean actual, final boolean expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Boolean>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertNotEquals(final boolean actual, final boolean expected) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Boolean>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected);
			}
		});
	}

	public void assertNotEquals(final char actual, final char expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Character>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertNotEquals(final char actual, final char expected) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Character>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected);
			}
		});
	}

	public void assertNotEquals(final int actual, final int expected, final String message) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Integer>(actual, expected, message + screenShotName) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected, message + screenShotName);
			}
		});
	}

	public void assertNotEquals(final int actual, final int expected) {
		String screenShotName = "\tScreenshot Name: " + compareNegative(actual, expected) + "\t";
		doAssert(new SimpleAssert<Integer>(actual, expected) {
			@Override
			public void doAssert() {
				org.testng.Assert.assertNotEquals(actual, expected);
			}
		});
	}

	public <T> String compare(T actual, T expected) {
		if (actual != expected) {
			String location = CommonLib.screenshot(AppListeners.currentlyExecutingTC);
			String[] ScreenShotName = location.split("//");
			return ScreenShotName[ScreenShotName.length - 1];
		}
		return "";
	}

	public <T> String compareNegative(T actual, T expected) {
		if (actual == expected) {
			String location = CommonLib.screenshot(AppListeners.currentlyExecutingTC);
			String[] ScreenShotName = location.split("//");
			return ScreenShotName[ScreenShotName.length - 1];
		}
		return "";
	}
	
	/**
	 * @author Ankur Rana
	 * @param sa: Object of SoftAssert
	 * @description: This method combines the errors of the two objects of SoftAssert Class
	 */
	public void combineAssertions(SoftAssert sa) {
		if (sa != null)
			if (!sa.m_errors.isEmpty()) 
				this.m_errors.putAll(sa.m_errors);
			else 
				AppListeners.appLog.info("There is nothing to combine in assertion");
		else
			AppListeners.appLog.info("Passed assertion object is empty.");
	}
}
