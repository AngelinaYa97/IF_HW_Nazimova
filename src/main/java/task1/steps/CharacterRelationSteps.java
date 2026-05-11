package task1.steps;

import io.restassured.response.ValidatableResponse;
import task1.api.CharacterApi;
import task1.dto.Character;

public class CharacterRelationSteps {

    private final CharacterApi api;

    public CharacterRelationSteps(CharacterApi api) {
        this.api = api;
    }

    public Character getCharacter(String characterUrl) {
        ValidatableResponse response = api.getCharacter(characterUrl);
        response.statusCode(200);
        return response.extract().as(Character.class);
    }
}