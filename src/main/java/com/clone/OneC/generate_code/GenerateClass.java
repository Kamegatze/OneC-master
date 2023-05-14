package com.clone.OneC.generate_code;

import lombok.Getter;

import java.io.IOException;

public abstract class GenerateClass {
    @Getter
    protected final String nameClass;
    @Getter
    protected final String nameProject;
    @Getter
    protected final String packageName;

    protected final String pathForCreateDirectory;

    public GenerateClass(String nameClass, String nameProject, String pathBeforeProject, String packageName) {
        this.nameClass = nameClass;
        this.nameProject = nameProject;
        this.pathForCreateDirectory = pathBeforeProject + nameProject + "/src/main/java/" + packageName.replace(".", "/") + "/" + nameProject;
        this.packageName = packageName;
    }

    public void build() throws IOException {
        toCreate();
    }

    protected void toCreate() throws IOException {

    }
}
