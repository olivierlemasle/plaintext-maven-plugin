package io.github.olivierlemasle.maven.plaintext;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Create plain text files with the provided lines, or append lines to existing files.
 */
@Mojo(name = "write", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public final class PlainTextMojo extends AbstractMojo {
    private final FileSystem defaultFileSystem = FileSystems.getDefault();

    /**
     * Base directory in which the files must be created/modified.
     */
    @Parameter(defaultValue = "${project.build.directory}", required = true)
    private String outputDirectory;

    void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * Files
     */
    @Parameter(required = true)
    private PlainTextFile[] files;

    @Override
    public void execute() throws MojoExecutionException {
        for (PlainTextFile file : files) {
            writeFile(file, defaultFileSystem);
        }
    }

    void writeFile(PlainTextFile file, FileSystem fs) throws MojoExecutionException {
        check(file);

        final Path path = fs.getPath(outputDirectory, file.getName());
        getLog().debug(String.format("Write on path %s", path));
        try {
            Files.createDirectories(path.getParent());
            if (file.isAppend() && Files.isRegularFile(path)) {
                Files.write(path, file.getLines(), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                getLog().debug(String.format("%s lines appended to file %s", file.getLines().size(), path));
            } else {
                Files.deleteIfExists(path);
                Files.write(path, file.getLines(), StandardCharsets.UTF_8);
                getLog().debug(String.format("%s lines written in file %s", file.getLines().size(), path));
            }
        } catch (IOException e) {
            throw new MojoExecutionException(String.format("IOException with file %s", path), e);
        }
    }

    private void check(PlainTextFile file) throws MojoExecutionException {
        if (file.getName() == null)
            throw new MojoExecutionException("Parameter file.name is required.");
        if (file.getLines() == null)
            throw new MojoExecutionException("Parameter file.lines is required");
    }
}
