package task1.steps;

import io.restassured.response.ValidatableResponse;
import task1.api.CharacterApi;

public class CharacterSteps {

    private final CharacterApi api;

    public CharacterSteps(CharacterApi api) {
        this.api = api;
    }

    public ValidatableResponse searchCharacterRaw(String name) {
        return api.searchCharacter(name);
    }
}