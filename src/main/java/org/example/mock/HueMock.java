package org.example.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import io.github.zeroone3010.yahueapi.Hue;
import io.github.zeroone3010.yahueapi.HueBridgeProtocol;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class HueMock {
    private static final String API_KEY = "abcd1234";
    private static final String API_BASE_PATH = "/api/" + API_KEY + "/";
    private static final String GROUP_0_PATH = API_BASE_PATH + "groups/0";
    private static final String MOTION_SENSOR_NAME = "Hallway sensor";
    private static final String AMBIENT_LIGHT_SENSOR_NAME = "Hue ambient light sensor 1";
    private static final String TEMPERATURE_SENSOR_NAME = "Hue temperature sensor 1";
    private static final String DIMMER_SWITCH_NAME = "Living room door";
    private static final String TAP_SWITCH_NAME = "Hue tap switch 1";


    final static WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
            .notifier(new ConsoleNotifier(true))
            .port(8090));

    private static Hue hue;

    public void startServer() {
        wireMockServer.start();
    }

    public void stopServer() {
        wireMockServer.stop();
    }

    public Hue createHueAndInitializeMockServer() {
        if (hue == null) {
            final String hueRoot = readFile("hueRoot.json");
            final String hueRootWithLight900 = readFile("hueRootWithLight900.json");

            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode jsonNode;
            try {
                jsonNode = objectMapper.readTree(hueRoot);

                wireMockServer.stubFor(get(API_BASE_PATH).willReturn(okJson(hueRoot)));
                mockIndividualGetResponse(jsonNode, "lights", "100");
                mockIndividualGetResponse(jsonNode, "lights", "101");
                mockIndividualGetResponse(jsonNode, "lights", "200");
                mockIndividualGetResponse(jsonNode, "lights", "300");
                mockIndividualGetResponse(jsonNode, "lights", "400");
                mockIndividualGetResponse(jsonNode, "lights", "500");
                mockIndividualGetResponse(jsonNode, "sensors", "1");
                mockIndividualGetResponse(jsonNode, "sensors", "4");
                mockIndividualGetResponse(jsonNode, "sensors", "15");
                mockIndividualGetResponse(jsonNode, "sensors", "16");
                mockIndividualGetResponse(jsonNode, "sensors", "17");
                mockIndividualGetResponse(jsonNode, "sensors", "20");
                mockIndividualGetResponse(jsonNode, "sensors", "99");
                mockIndividualGetResponse(jsonNode, "groups", "1");
                mockIndividualGetResponse(jsonNode, "groups", "2");
                wireMockServer.stubFor(post(API_BASE_PATH + "lights").willReturn(okJson(
                        "[ { \"success\": { \"/lights\": \"Searching for new devices\" }}]"
                )));
                wireMockServer.stubFor(put(API_BASE_PATH + "lights/{id}/state").inScenario("change_light_state").whenScenarioStateIs(Scenario.STARTED)
                        .willReturn(okJson("{\n" +
                                "\"on\": true"+
                                "}")).willSetStateTo("changed_light_state_1"));
                //java.io.FileNotFoundException: http://localhost:8090/api/abcd1234/groups/3/action

                wireMockServer.stubFor(put(API_BASE_PATH + "groups/2/action").inScenario("change_room_state_2").whenScenarioStateIs(Scenario.STARTED)
                        .willReturn(okJson("[" +
                                "    {\"success\":{\"/groups/1/lights\":[\"1\"]}}," +
                                "    {\"success\":{\"/groups/1/name\":\"Bedroom\"}}" +
                                "]")));
                wireMockServer.stubFor(put(API_BASE_PATH + "groups/3/action").inScenario("change_room_state_3").whenScenarioStateIs(Scenario.STARTED)
                        .willReturn(okJson("[" +
                                "    {\"success\":{\"/groups/1/lights\":[\"1\"]}}," +
                                "    {\"success\":{\"/groups/1/name\":\"Bedroom\"}}" +
                                "]")));

                wireMockServer.stubFor(put(API_BASE_PATH + "groups/1/action").inScenario("change_room_state_1").whenScenarioStateIs(Scenario.STARTED)
                        .willReturn(okJson("[" +
                                "    {\"success\":{\"/groups/1/lights\":[\"1\"]}}," +
                                "    {\"success\":{\"/groups/1/name\":\"Bedroom\"}}" +
                                "]")));
                wireMockServer.stubFor(get(API_BASE_PATH + "lights/new").inScenario("scan").whenScenarioStateIs(Scenario.STARTED)
                        .willReturn(okJson("{\"lastscan\": \"active\"}")).willSetStateTo("scan1"));
                wireMockServer.stubFor(get(API_BASE_PATH + "lights/new").inScenario("scan").whenScenarioStateIs("scan1")
                        .willReturn(okJson("{\"lastscan\": \"active\"}")).willSetStateTo("scan2"));
                wireMockServer.stubFor(get(API_BASE_PATH + "lights/new").inScenario("scan").whenScenarioStateIs("scan2")
                        .willReturn(okJson("{\"lastscan\": \"active\"}")).willSetStateTo("scan3"));
                wireMockServer.stubFor(get(API_BASE_PATH + "lights/new").inScenario("scan").whenScenarioStateIs("scan3")
                        .willReturn(okJson("{\n" +
                                "    \"900\": {\"name\": \"Hue bulb\"},\n" +
                                "    \"lastscan\": \"2022-02-04T12:00:00\"\n" +
                                "}")));
                wireMockServer.stubFor(get(API_BASE_PATH).inScenario("scan").whenScenarioStateIs("scan3").willReturn(okJson(hueRootWithLight900)));
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
            hue = new Hue(HueBridgeProtocol.HTTP, "localhost:" + wireMockServer.port(), API_KEY);
        }
        return hue;
    }

    private void mockIndividualGetResponse(final JsonNode hueRoot, final String itemClass, final String id) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(hueRoot.get(itemClass).get(id));
        wireMockServer.stubFor(get(API_BASE_PATH + itemClass + "/" + id).willReturn(okJson(json)));
    }

    private String readFile(final String fileName) {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource(fileName).getFile());
        try {
            return Files.lines(file.toPath()).collect(Collectors.joining());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}
