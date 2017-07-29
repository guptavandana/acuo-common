/**
 * Copyright (C) 2011 Fabio Strozzi (fabio.strozzi@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.acuo.common.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A JUnit class runner for Guice based applications.
 *
 */
public class GuiceJUnitRunner extends BlockJUnit4ClassRunner {

	private Injector injector;
	private InstanceTestClassListener setupListener;

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Inherited
	public @interface GuiceModules {
		Class<?>[] value();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.junit.runners.BlockJUnit4ClassRunner#createTest()
	 */
	@Override
	public Object createTest() throws Exception {
		Object obj = super.createTest();
		injector.injectMembers(obj);
		if (obj instanceof InstanceTestClassListener && setupListener == null) {
			setupListener = (InstanceTestClassListener) obj;
			setupListener.beforeClassSetup();
		}
		return obj;
	}

	@Override
	public void run(RunNotifier notifier) {
		super.run(notifier);
		if (setupListener != null)
			setupListener.afterClassSetup();
	}

	/**
	 * Instances a new JUnit runner.
	 * 
	 * @param klass
	 *            The test class
	 * @throws InitializationError
	 */
	public GuiceJUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
		Class<?>[] classes = getModulesFor(klass);
		injector = createInjectorFor(classes);
	}

	/**
	 * @param classes
	 * @return
	 * @throws InitializationError
	 */
	private Injector createInjectorFor(Class<?>[] classes) throws InitializationError {
		Module[] modules = new Module[classes.length];
		for (int i = 0; i < classes.length; i++) {
			try {
				modules[i] = (Module) (classes[i]).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new InitializationError(e);
			}
		}
		return Guice.createInjector(modules);
	}

	/**
	 * Gets the Guice modules for the given test class.
	 * 
	 * @param klass
	 *            The test class
	 * @return The array of Guice {@link Module} modules used to initialize the
	 *         injector for the given test.
	 * @throws InitializationError
	 */
	private Class<?>[] getModulesFor(Class<?> klass) throws InitializationError {
		GuiceModules annotation = klass.getAnnotation(GuiceModules.class);
		if (annotation == null)
			throw new InitializationError("Missing @GuiceModules annotation for unit test '" + klass.getName() + "'");
		return annotation.value();
	}

}