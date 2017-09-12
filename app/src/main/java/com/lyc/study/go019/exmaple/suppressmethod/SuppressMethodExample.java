/*
 * Copyright 2009 the original author or authors.
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
package com.lyc.study.go019.exmaple.suppressmethod;

public class SuppressMethodExample extends SuppressMethodParent {

	public static final Object OBJECT = new Object();

	public Object getObject() {
		return OBJECT;
	}

	public Object getStringObject() {
		return "test";
	}

	public static Object getStringObjectStatic() {
		return "test";
	}
}