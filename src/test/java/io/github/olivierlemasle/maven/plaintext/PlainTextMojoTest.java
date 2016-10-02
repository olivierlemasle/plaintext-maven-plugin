package io.github.olivierlemasle.maven.plaintext;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PlainTextMojoTest {

    @Test
    public void writeFile() throws Exception {
        final FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        final PlainTextFile f1 = new PlainTextFile("/file1", false, new ArrayList<String>());
        final PlainTextMojo mojo = new PlainTextMojo();
        mojo.setOutputDirectory("/path/");

        assertFalse(Files.isRegularFile(fs.getPath("/path/file1")));
        mojo.writeFile(f1, fs);
        assertTrue(Files.isRegularFile(fs.getPath("/path/file1")));
    }

    @Test
    public void appendNewFile() throws Exception {
        final FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        final List<String> lines = Arrays.asList("line1", "line2");
        final PlainTextFile f1 = new PlainTextFile("/file1", true, lines);
        final PlainTextMojo mojo = new PlainTextMojo();
        mojo.setOutputDirectory("/path/");


        final Path path = fs.getPath("/path/file1");
        assertFalse(Files.isRegularFile(path));
        mojo.writeFile(f1, fs);
        assertTrue(Files.isRegularFile(path));
        assertEquals(lines, Files.readAllLines(path, StandardCharsets.UTF_8));
    }

    @Test
    public void appendExistingFile() throws Exception {
        final FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        final List<String> lines = Arrays.asList("line1", "line2");
        final List<String> newLines = Arrays.asList("line3", "line4");
        final PlainTextFile f1 = new PlainTextFile("file1", true, newLines);
        final PlainTextMojo mojo = new PlainTextMojo();
        mojo.setOutputDirectory("/path/");


        final Path path = fs.getPath("/path/file1");
        Files.createDirectories(fs.getPath("/path"));
        Files.write(path, lines, StandardCharsets.UTF_8);
        mojo.writeFile(f1, fs);
        assertTrue(Files.isRegularFile(path));
        final List<String> allLines = new ArrayList<>(lines);
        allLines.addAll(newLines);
        assertEquals(allLines, Files.readAllLines(path, StandardCharsets.UTF_8));
    }

    @Test
    public void windows() throws Exception {
        final FileSystem fs = Jimfs.newFileSystem(Configuration.windows());
        final PlainTextFile f1 = new PlainTextFile("to\\file.txt", false, new ArrayList<String>());
        final PlainTextMojo mojo = new PlainTextMojo();
        mojo.setOutputDirectory("C:\\path");

        assertFalse(Files.isRegularFile(fs.getPath("C:\\path\\to\\file.txt")));
        mojo.writeFile(f1, fs);
        assertTrue(Files.isRegularFile(fs.getPath("C:\\path\\to\\file.txt")));
    }

    @Test
    public void testCheck() throws Exception {
        final FileSystem fs = Jimfs.newFileSystem();
        try {
            final PlainTextFile f = new PlainTextFile(null, false, new ArrayList<String>());
            new PlainTextMojo().writeFile(f, fs);
            fail("Should have thrown a MojoExecutionException.");
        } catch (MojoExecutionException ignored) {
        }
        try {
            final PlainTextFile f = new PlainTextFile("test", false, null);
            new PlainTextMojo().writeFile(f, fs);
            fail("Should have thrown a MojoExecutionException.");
        } catch (MojoExecutionException ignored) {
        }
    }

    @Test
    public void testIoException() throws Exception {
        // Append to a non-empty directory instead of a file
        final FileSystem fs = Jimfs.newFileSystem();
        Files.createDirectories(fs.getPath("/path/dir"));
        Files.createFile(fs.getPath("/path/dir/file"));

        try {
            final PlainTextFile f = new PlainTextFile("dir", true, Arrays.asList("Line"));
            final PlainTextMojo mojo = new PlainTextMojo();
            mojo.setOutputDirectory("/path");
            mojo.writeFile(f, fs);
            fail("Should have thrown a MojoExecutionException.");
        } catch (MojoExecutionException ignored) {
            assertTrue(ignored.getCause() instanceof IOException);
        }
    }
}