package onhands.com.deliverycompamostra.model;

/**
 * Created by rodrigocavalcante on 5/30/16.
 */
public class Company {

    int id;
    String name;
    String image;
    String[] phones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }
}
