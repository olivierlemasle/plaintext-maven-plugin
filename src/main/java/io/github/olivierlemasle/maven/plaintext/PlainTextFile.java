package io.github.olivierlemasle.maven.plaintext;


import java.util.List;

public class PlainTextFile {
    public PlainTextFile() {
    }

    PlainTextFile(String name, boolean append, List<String> lines) {
        this.name = name;
        this.append = append;
        this.lines = lines;
    }

    private String name;

    private boolean append;

    private List<String> lines;

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    boolean isAppend() {
        return append;
    }

    public void setAppend(boolean append) {
        this.append = append;
    }

    List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
