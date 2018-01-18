package feature;

import feature.name.Name;

public class Attribute {
    private Name name;

    public void setName(Name name) {
        if (name == null) throw new IllegalStateException();
        this.name = name;
    }

    public Name getName() {
        return name;
    }
}