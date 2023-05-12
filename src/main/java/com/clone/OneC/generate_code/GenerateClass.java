package com.clone.OneC.generate_code;

import java.io.IOException;

public abstract class GenerateClass {
    protected final String nameClass;

    protected final String nameProject;

    protected final String pathForCreateDirectory;

    public GenerateClass(String nameClass, String nameProject, String pathBeforeProject) {
        this.nameClass = nameClass;
        this.nameProject = nameProject;
        this.pathForCreateDirectory = pathBeforeProject + nameProject + "/src/main/java/com/example/" + nameProject;;
    }

    public void build() throws IOException {
        toCreate();
    }

    protected void toCreate() throws IOException {

    }
}
