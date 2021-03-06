/*
 * Copyright 2012-2015 the original author or authors.
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

package org.springframework.boot.devtools.restart.classloader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.springframework.boot.devtools.restart.classloader.ClassLoaderFile.Kind;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link ClassLoaderFile}.
 *
 * @author Phillip Webb
 */
public class ClassLoaderFileTests {

	public static final byte[] BYTES = "ABC".getBytes();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void kindMustNotBeNull() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Kind must not be null");
		new ClassLoaderFile(null, null);
	}

	@Test
	public void addedContentsMustNotBeNull() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Contents must not be null");
		new ClassLoaderFile(Kind.ADDED, null);
	}

	@Test
	public void modifiedContentsMustNotBeNull() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Contents must not be null");
		new ClassLoaderFile(Kind.MODIFIED, null);
	}

	@Test
	public void deletedContentsMustBeNull() throws Exception {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("Contents must be null");
		new ClassLoaderFile(Kind.DELETED, new byte[10]);
	}

	@Test
	public void added() throws Exception {
		ClassLoaderFile file = new ClassLoaderFile(Kind.ADDED, BYTES);
		assertThat(file.getKind(), equalTo(ClassLoaderFile.Kind.ADDED));
		assertThat(file.getContents(), equalTo(BYTES));
	}

	@Test
	public void modified() throws Exception {
		ClassLoaderFile file = new ClassLoaderFile(Kind.MODIFIED, BYTES);
		assertThat(file.getKind(), equalTo(ClassLoaderFile.Kind.MODIFIED));
		assertThat(file.getContents(), equalTo(BYTES));
	}

	@Test
	public void deleted() throws Exception {
		ClassLoaderFile file = new ClassLoaderFile(Kind.DELETED, null);
		assertThat(file.getKind(), equalTo(ClassLoaderFile.Kind.DELETED));
		assertThat(file.getContents(), nullValue());
	}

}
