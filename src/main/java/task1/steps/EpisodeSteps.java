package task1.steps;

import io.restassured.response.ValidatableResponse;
import task1.api.CharacterApi;
import task1.dto.Episode;

public class EpisodeSteps {

    private final CharacterApi api;

    public EpisodeSteps(CharacterApi api) {
        this.api = api;
    }

    public Episode getEpisode(String episodeUrl) {
        ValidatableResponse response = api.getEpisode(episodeUrl);
        response.statusCode(200);
        return response.extract().as(Episode.class);
    }
}