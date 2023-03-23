package org.example.hue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.zeroone3010.yahueapi.Hue;
import io.github.zeroone3010.yahueapi.Light;
import io.github.zeroone3010.yahueapi.Room;
import org.example.hue.objects.LightBulb;
import org.example.hue.objects.LightObjects;
//import org.example.utils.BooleanDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HueController {
    private final Hue hue;

    public HueController() {
        hue = new Hue(HueConfig.BRIDGE_IP, HueConfig.API_KEY);
    }


//    public static void main(String[] args) throws IOException {
//        HueController hueController = new HueController();
//        hueController.test();
//    }
//        public String test() throws IOException {
//            RestTemplate restTemplate = new RestTemplate();
//
//            HttpEntity<String> request = new HttpEntity<>(new String());
//
//          ResponseEntity<String> output =   restTemplate.getForEntity(HueConfig.BRIDGE_IP+ HueConfig.LIGHTS_METHOD, String.class);
//            Map<String, Object> response = new ObjectMapper().readValue(output.getBody(), HashMap.class);
//
//
//            LightObjects response1 =             new ObjectMapper().readValue(output.getBody(), LightObjects.class);
//            System.out.println(response1.getClass());
//            System.out.println(output.getBody());
//        return response1.toString();
//    }
    public String  getAllLights(){
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<>(new String());

        ResponseEntity<String> output =   restTemplate.getForEntity(HueConfig.BRIDGE_IP+ HueConfig.LIGHTS_METHOD, String.class);


        Hue hue1 = new Hue(HueConfig.BRIDGE_IP, HueConfig.API_KEY);
        LightObjects lights = null;
        try {
            lights = new ObjectMapper().readValue(output.getBody(), LightObjects.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        String lightDescriptions = lightDescription(lights);

        return lightDescriptions;

    }



    private String lightDescription (LightObjects lightObjects){
        StringBuilder description = new StringBuilder();
        description.append("В вашем помещении найдено ").append(lightObjects.getAdditionalProperties().size()).append(" лампочек\n");
        for (String key : lightObjects.getAdditionalProperties().keySet())
        {
            LightBulb  lightBulb = lightObjects.getAdditionalProperties().get(key);
            description.append("Лампочка: " + lightBulb.getName() + ". Состояние: " + (lightBulb.getState().getOn() ?"Включена":"Выключена")).append("\n");

        }



        return description.toString();
    }

}
