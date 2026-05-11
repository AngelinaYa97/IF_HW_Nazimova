package task1.api;

import baseApi.BaseApiClient;
import io.restassured.response.ValidatableResponse;
import utils.ConfigLoader;

import java.util.Map;

public class CharacterApi extends BaseApiClient {

    private static final String RICK_MORTY_API_URL = ConfigLoader.getRickMortyApiUrl();

    public ValidatableResponse searchCharacter(String name) {
        Map<String, String> queryParams = Map.of("name", name);
        return getWithQueryParams(RICK_MORTY_API_URL, "/character", queryParams);
    }

    public ValidatableResponse getEpisode(String episodeUrl) {
        return get(episodeUrl);
    }

    public ValidatableResponse getCharacter(String characterUrl) {
        return get(characterUrl);
    }
}