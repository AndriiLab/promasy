package model.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class holds and compares program version
 * Modified from http://stackoverflow.com/questions/198431/how-do-you-compare-two-version-strings-in-java
 */
@Entity
@Table(name = "version")
public class Version implements Comparable<Version> {

    @Id
    @Column(name = "id")
    private int id = 1;

    @Column(name = "version_allowed")
    private String version;

    public Version() {
    }

    public Version(String version) {
        if (version == null) {
            throw new IllegalArgumentException("Version can not be null");
        }
        if (!version.matches("[0-9]+(\\.[0-9]+)+")) {
            throw new IllegalArgumentException("Invalid version format");
        }
        this.version = version;
    }

    public String get() {
        return this.version;
    }

    public void set(String version) {
        this.version = version;
    }

    public int compareTo(Version that) {
        if (that == null) {
            return 1;
        }
        String[] thisParts = this.get().split("\\.");
        String[] thatParts = that.get().split("\\.");
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart) {
                return -1;
            }
            if (thisPart > thatPart) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object that) {
        return this == that || that != null && this.getClass() == that.getClass() && this.compareTo((Version) that) == 0;
    }

}