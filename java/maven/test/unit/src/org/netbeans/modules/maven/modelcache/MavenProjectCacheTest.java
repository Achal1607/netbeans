/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.maven.modelcache;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import org.netbeans.junit.NbTestCase;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.test.TestFileUtils;

public class MavenProjectCacheTest extends NbTestCase {

    public MavenProjectCacheTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        clearWorkDir();
    }

    public void testFindSettingsFileRelative() throws Exception {
        File root = getWorkDir();
        File settings = new File(new File(root, "alt"), "settings.xml");
        TestFileUtils.writeFile(settings, "<settings/>");
        List<String> options = Arrays.asList("-s", "alt/settings.xml");
        FileObject rootFo = FileUtil.toFileObject(root);
        assertNotNull(rootFo);
        File resolved = MavenProjectCache.findSettingsFile(rootFo, options);
        assertEquals(FileUtil.normalizeFile(settings), resolved);
    }

    public void testFindSettingsFileAbsolute() throws Exception {
        File root = getWorkDir();
        File settings = new File(root, "settings.xml");
        TestFileUtils.writeFile(settings, "<settings/>");
        List<String> options = Arrays.asList("--settings=" + settings.getAbsolutePath());
        FileObject rootFo = FileUtil.toFileObject(root);
        assertNotNull(rootFo);
        File resolved = MavenProjectCache.findSettingsFile(rootFo, options);
        assertEquals(FileUtil.normalizeFile(settings), resolved);
    }

    public void testFindSettingsFileInlineShortOption() throws Exception {
        File root = getWorkDir();
        File settings = new File(root, "settings.xml");
        TestFileUtils.writeFile(settings, "<settings/>");
        List<String> options = Arrays.asList("-s=" + settings.getAbsolutePath());
        FileObject rootFo = FileUtil.toFileObject(root);
        assertNotNull(rootFo);
        File resolved = MavenProjectCache.findSettingsFile(rootFo, options);
        assertEquals(FileUtil.normalizeFile(settings), resolved);
    }
}
