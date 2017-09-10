package com.powermock.jayway;

import com.lyc.study.go019.jayway.Example2;
import com.lyc.study.go019.jayway.Exmaple3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by lyc on 17/9/10.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Exmaple3.class)
public class Example3Test {
    @Test
    public void createDirectoryStructureWhenPathDoesntExist() throws Exception {
        final String directoryPath = "mocked path";

        File directoryMock = mock(File.class);

        // This is how you tell PowerMockito to mock construction of a new File.
        whenNew(File.class).withArguments(directoryPath).thenReturn(directoryMock);

        // Standard expectations
        when(directoryMock.exists()).thenReturn(false);
        when(directoryMock.mkdirs()).thenReturn(true);

        assertTrue(new Exmaple3().create(directoryPath));

        // Optionally verify that a new File was "created".
        verifyNew(File.class).withArguments(directoryPath);
    }
}