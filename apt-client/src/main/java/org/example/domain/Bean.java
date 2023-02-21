package org.example.domain;

public class Bean {

    private final String id;
    private final String name;
    public Bean(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
