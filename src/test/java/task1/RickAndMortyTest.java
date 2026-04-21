package task1;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task1.models.Character;
import task1.models.Episode;
import task1.rickandmorty.EnvConstants;
import task1.rickandmorty.Specifications;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RickAndMortyTest {

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = Specifications.baseRequestSpec(EnvConstants.RICK_AND_MORTY_API_URL);
        RestAssured.responseSpecification = Specifications.baseResponseSpecSuccess();
    }

    @Test
    public void mortySmithLastEpisodeLastCharacterTest() {

        Character morty = given()
                .when()
                .get("/character/2")
                .then()
                .extract()
                .as(Character.class);

        List<String> episodeUrls = morty.getEpisodeUrls();
        String lastEpisodeUrl = episodeUrls.get(episodeUrls.size() - 1);
        System.out.println("1. Последний эпизод Морти: " + lastEpisodeUrl);

        Episode episode = given()
                .when()
                .get(lastEpisodeUrl)
                .then()
                .extract()
                .as(Episode.class);

        List<String> characterUrls = episode.getCharacterUrls();
        String lastCharacterUrl = characterUrls.get(characterUrls.size() - 1);
        System.out.println("2. URL последнего персонажа в эпизоде: " + lastCharacterUrl);

        Character lastCharacter = given()
                .when()
                .get(lastCharacterUrl)
                .then()
                .extract()
                .as(Character.class);

        String lastCharacterSpecies = lastCharacter.getSpecies();
        String lastCharacterLocation = lastCharacter.getLocation().getName();

        String mortySpecies = morty.getSpecies();
        String mortyLocation = morty.getLocation().getName();

        System.out.println("3. Раса последнего персонажа: " + lastCharacterSpecies);
        System.out.println("   Местоположение последнего персонажа: " + lastCharacterLocation);
        System.out.println("   Раса Морти: " + mortySpecies);
        System.out.println("   Местоположение Морти: " + mortyLocation);

        if (mortySpecies.equals(lastCharacterSpecies) && mortyLocation.equals(lastCharacterLocation)) {
            System.out.println("Персонаж той же расы и того же места, что и Морти.");
        } else {
            System.out.println("Персонаж отличается от Морти:");
            if (!mortySpecies.equals(lastCharacterSpecies)) {
                System.out.println("   - Раса: у Морти '" + mortySpecies + "', у персонажа '" + lastCharacterSpecies + "'");
            }
            if (!mortyLocation.equals(lastCharacterLocation)) {
                System.out.println("   - Локация: у Морти '" + mortyLocation + "', у персонажа '" + lastCharacterLocation + "'");
            }
            System.out.println("   (Это нормально по условию задачи)");
        }
    }
}