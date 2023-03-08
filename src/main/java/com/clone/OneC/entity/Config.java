package com.clone.OneC.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Config extends BaseEntity{
    private String type; //Const
    private String language; //Const
    private String bootVersion; //Const
    private String baseDir;
    private String groupId;//Const
    private String artifactId;
    private String name;
    private String description;
    private String packaging;
    private String javaVersion;
    private List<String> dependencies;
}
