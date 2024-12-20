package ma.emsi.client.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private float age;

    // Constructeur par défaut
    public Client() {
    }

    // Constructeur avec paramètres
    public Client(String name, float age) {
        this.name = name;
        this.age = age;
    }

    // Getters et Setters pour chaque champ
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    // Vous pouvez également ajouter toString(), equals() et hashCode() si nécessaire
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Float.compare(client.age, age) == 0 &&
                id.equals(client.id) &&
                name.equals(client.name);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode() + name.hashCode() + Float.hashCode(age);
    }
}
