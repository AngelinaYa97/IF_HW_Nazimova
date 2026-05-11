package task1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Episode {
    private int id;
    private String name;
    @JsonProperty("air_date")
    private String airDate;
    private String episode;
    private List<String> characters;
    private String url;
    private String created;
}
