package task1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CharacterResponse {
    private Info info;
    private List<Character> results;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Info {
        private int count;
        private int pages;
        private String next;
        private String prev;
    }
}