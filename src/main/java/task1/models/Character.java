package task1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Character {
    private int id;
    private String name;
    private String species;
    private Location location;
    @JsonProperty("episode")
    private List<String> episodeUrls;

    // Геттеры
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getEpisodeUrls() {
        return episodeUrls;
    }

    // Сеттеры (для полноты, чтобы Jackson мог заполнять поля)

    @JsonIgnoreProperties(ignoreUnknown = true)  // <-- ключевое добавление
    public static class Location {
        private String name;
        private String url;  // поле есть, но будет проигнорировано

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // геттер и сеттер для url не обязательны, но можно добавить

    }
}
