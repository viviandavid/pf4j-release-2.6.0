/*
 * Copyright (C) 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pf4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.pf4j.plugin.PluginZip;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Decebal Suiu
 */
public class CompoundPluginDescriptorFinderTest {

    @Rule
    public TemporaryFolder pluginsFolder = new TemporaryFolder();

    @Test
    public void add() {
        CompoundPluginDescriptorFinder descriptorFinder = new CompoundPluginDescriptorFinder();
        assertEquals(0, descriptorFinder.size());

        descriptorFinder.add(new PropertiesPluginDescriptorFinder());
        assertEquals(1, descriptorFinder.size());
    }

    @Test
    public void find() throws Exception {
        Path pluginPath = pluginsFolder.newFolder("test-plugin-1").toPath();
        Files.write(pluginPath.resolve("plugin.properties"), getPlugin1Properties(), StandardCharsets.UTF_8);

        PluginDescriptorFinder descriptorFinder = new CompoundPluginDescriptorFinder()
            .add(new PropertiesPluginDescriptorFinder());

        PluginDescriptor pluginDescriptor = descriptorFinder.find(pluginPath);
        assertNotNull(pluginDescriptor);
        assertEquals("test-plugin-1", pluginDescriptor.getPluginId());
        assertEquals("0.0.1", pluginDescriptor.getVersion());
    }

    @Test
    public void findInJar() throws Exception {
        PluginDescriptorFinder descriptorFinder = new CompoundPluginDescriptorFinder()
            .add(new PropertiesPluginDescriptorFinder());

        PluginZip pluginJar = new PluginZip.Builder(pluginsFolder.newFile("my-plugin-1.2.3.jar"), "myPlugin")
            .pluginVersion("1.2.3")
            .build();

        PluginDescriptor pluginDescriptor = descriptorFinder.find(pluginJar.path());
        assertNotNull(pluginDescriptor);
        assertEquals("myPlugin", pluginJar.pluginId());
        assertEquals("1.2.3", pluginJar.pluginVersion());
    }

    @Test(expected = PluginException.class)
    public void testNotFound() throws Exception {
        PluginDescriptorFinder descriptorFinder = new CompoundPluginDescriptorFinder();
        Path pluginsPath = pluginsFolder.getRoot().toPath();
        descriptorFinder.find(pluginsPath.resolve("test-plugin-3"));
    }

    @Test
    public void testSpaceCharacterInFileName() throws Exception {
        PluginDescriptorFinder descriptorFinder = new PropertiesPluginDescriptorFinder();
        File jar = pluginsFolder.newFile("my plugin-1.2.3.jar");

        PluginZip pluginJar = new PluginZip.Builder(jar, "myPlugin")
            .pluginVersion("1.2.3")
            .build();

        PluginDescriptor pluginDescriptor = descriptorFinder.find(pluginJar.path());
        assertNotNull(pluginDescriptor);
    }

    private List<String> getPlugin1Properties() {
        String[] lines = new String[] {
            "plugin.id=test-plugin-1\n"
            + "plugin.version=0.0.1\n"
            + "plugin.description=Test Plugin 1\n"
            + "plugin.provider=Decebal Suiu\n"
            + "plugin.class=org.pf4j.plugin.TestPlugin\n"
            + "plugin.dependencies=test-plugin-2,test-plugin-3@~1.0\n"
            + "plugin.requires=>=1\n"
            + "plugin.license=Apache-2.0\n"
            + "\n"
            + ""
        };

        return Arrays.asList(lines);
    }

}
