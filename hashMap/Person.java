package hashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Person {
    private int id;
    private String name;

    // Construtor
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }

    // hashCode e equals
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person other = (Person) obj;
        return id == other.id && Objects.equals(name, other.name);
    }

    // toString para facilitar a visualização
    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "'}";
    }
}
