package cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import task1.api.CharacterApi;
import task1.dto.Character;
import task1.dto.CharacterResponse;
import task1.dto.Episode;
import task1.steps.CharacterRelationSteps;
import task1.steps.CharacterSteps;
import task1.steps.EpisodeSteps;

import static org.junit.jupiter.api.Assertions.*;

public class RickMortyStepDefinitions {

    private final CharacterApi api = new CharacterApi();
    private final EpisodeSteps episodeSteps = new EpisodeSteps(api);
    private final CharacterRelationSteps relationSteps = new CharacterRelationSteps(api);
    private final CharacterSteps characterSteps = new CharacterSteps(api);
    private Character morty;
    private Episode lastEpisode;
    private Character lastCharacter;
    private String lastEpisodeUrl;
    private String lastCharacterUrl;
    private ValidatableResponse response;
    private int statusCode;

    @Дано("я ищу персонажа по имени {string}")
    @Step("Найти персонажа по имени")
    public void iSearchForCharacterByName(String name) {
        response = characterSteps.searchCharacterRaw(name);
        statusCode = response.extract().statusCode();
    }

    @Тогда("статус код рикморти ответа должен быть {int}")
    @Step("Проверить статус-код Rick & Morty")
    public void rickMortyResponseStatusCodeShouldBe(int expectedStatus) {
        assertEquals(expectedStatus, statusCode);
    }

    @Тогда("в ответе рикморти есть персонаж с именем {string}")
    @Step("Проверить наличие персонажа в ответе")
    public void responseContainsCharacterWithName(String expectedName) {
        CharacterResponse characterResponse = response.extract().as(CharacterResponse.class);
        assertNotNull(characterResponse.getResults());
        assertFalse(characterResponse.getResults().isEmpty());
        morty = characterResponse.getResults().get(0);
        assertEquals(expectedName, morty.getName());
    }

    @Тогда("я запоминаю последний эпизод из списка эпизодов Морти")
    @Step("Запомнить последний эпизод Морти")
    public void iRememberLastEpisodeFromMortyEpisodes() {
        assertNotNull(morty.getEpisode());
        assertFalse(morty.getEpisode().isEmpty());
        lastEpisodeUrl = morty.getEpisode().get(morty.getEpisode().size() - 1);
    }

    @Когда("я получаю информацию о последнем эпизоде")
    @Step("Получить данные последнего эпизода")
    public void iGetLastEpisodeInfo() {
        lastEpisode = episodeSteps.getEpisode(lastEpisodeUrl);
        assertNotNull(lastEpisode);
    }

    @Тогда("я запоминаю последнего персонажа из этого эпизода")
    @Step("Запомнить последнего персонажа эпизода")
    public void iRememberLastCharacterFromThisEpisode() {
        assertNotNull(lastEpisode.getCharacters());
        assertFalse(lastEpisode.getCharacters().isEmpty());
        lastCharacterUrl = lastEpisode.getCharacters().get(lastEpisode.getCharacters().size() - 1);
    }

    @Когда("я получаю информацию о последнем персонаже")
    @Step("Получить данные последнего персонажа")
    public void iGetLastCharacterInfo() {
        lastCharacter = relationSteps.getCharacter(lastCharacterUrl);
        assertNotNull(lastCharacter);
    }

    @Тогда("проверяю, что последний персонаж не совпадает с Морти по расе и локации")
    @Step("Проверить различие с Морти по расе и локации")
    public void verifyLastCharacterDiffersFromMortyInSpeciesAndLocation() {
        assertNotNull(lastCharacter.getSpecies(), "species must not be null");
        assertNotNull(lastCharacter.getLocation(), "location must not be null");
        assertNotNull(lastCharacter.getLocation().getName(), "location.name must not be null");
        assertNotNull(morty.getSpecies(), "Morty species must not be null");
        assertNotNull(morty.getLocation(), "Morty location must not be null");
        assertNotNull(morty.getLocation().getName(), "Morty location.name must not be null");

        boolean sameSpeciesAndLocation = lastCharacter.getSpecies().equals(morty.getSpecies())
                && lastCharacter.getLocation().getName().equals(morty.getLocation().getName());
        assertFalse(sameSpeciesAndLocation,
                "Персонаж " + lastCharacter.getName() + " не должен совпадать с Морти по расе и локации");
    }

    @Тогда("у персонажа рикморти заполнены все обязательные поля")
    @Step("Проверить обязательные поля персонажа")
    public void characterHasAllRequiredFields() {
        CharacterResponse characterResponse = response.extract().as(CharacterResponse.class);
        assertNotNull(characterResponse.getResults(), "results must not be null");
        assertFalse(characterResponse.getResults().isEmpty(), "results must not be empty");

        Character character = characterResponse.getResults().get(0);

        assertTrue(character.getId() > 0, "Character id must be positive");
        assertNotNull(character.getName());
        assertFalse(character.getName().isBlank());
        assertNotNull(character.getStatus());
        assertFalse(character.getStatus().isBlank());
        assertNotNull(character.getSpecies());
        assertFalse(character.getSpecies().isBlank());
        assertNotNull(character.getGender());
        assertFalse(character.getGender().isBlank());

        assertNotNull(character.getOrigin(), "origin must not be null");
        assertNotNull(character.getOrigin().getName());
        assertFalse(character.getOrigin().getName().isBlank());

        assertNotNull(character.getLocation(), "location must not be null");
        assertNotNull(character.getLocation().getName());
        assertFalse(character.getLocation().getName().isBlank());

        assertNotNull(character.getEpisode());
        assertFalse(character.getEpisode().isEmpty());

        attachCharacterJson(character);
    }

    private void attachCharacterJson(Character character) {
        try {
            String json = new ObjectMapper()
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(character);
            Allure.addAttachment("Проверенные поля персонажа", "application/json", json, ".json");
        } catch (Exception e) {
            Allure.addAttachment("Проверенные поля персонажа", "text/plain",
                    "Ошибка сериализации: " + e.getMessage(), ".txt");
        }
    }
}
