package com.tplu.model;

public class JarInfo {
    private String artifact;
    private String currentVersion;
    private String newVersion;

    public JarInfo(String artifact, String currentVersion, String newVersion) {
        this.artifact = artifact;
        this.currentVersion = currentVersion;
        this.newVersion = newVersion;
    }

    public String getArtifact() {
        return artifact;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }
}
