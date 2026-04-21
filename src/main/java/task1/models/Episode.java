package task1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Episode {
    private int id;
    private String name;
    @JsonProperty("characters")
    private List<String> characterUrls;


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

    public List<String> getCharacterUrls() {
        return characterUrls;
    }

    public void setCharacterUrls(List<String> characterUrls) {
        this.characterUrls = characterUrls;
    }
}