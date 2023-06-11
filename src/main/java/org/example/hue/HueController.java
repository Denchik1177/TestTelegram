package org.example.hue;

import io.github.zeroone3010.yahueapi.Color;
import io.github.zeroone3010.yahueapi.Hue;
import io.github.zeroone3010.yahueapi.Room;
import io.github.zeroone3010.yahueapi.State;
import org.example.hue.objects.LightBulb;
import org.example.hue.objects.LightObjects;
//import org.example.utils.BooleanDeserializer;
import org.example.mock.HueMock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class HueController {
    ///private final Hue hue;

    public HueController() {
//        hue = new Hue(HueConfig.BRIDGE_IP, HueConfig.API_KEY);
    }



    public String getEnvironmentDescription(){

        HueMock hueMock = new HueMock();
        Hue hue = hueMock.createHueAndInitializeMockServer();

        StringBuilder response = new StringBuilder();

        response.append("*Комнат: "+ hue.getRooms().size()).append("*\n");
        hue.getRooms().forEach(r -> {
            response.append("В комнате *'").append(r.getName()).append("'* размещены приборы освещения: \n");
                 r.getLights().forEach(light -> response.append("\t Лампа *'"+ light.getName()+ "'* состояние: *" + (light.getState().getOn()?"включён":"выключен")).append("*\n"));
        });


        response.append("*Датчики движения* : \n");
        hue.getPresenceSensors().forEach(s -> response.append("\t").append(s.getName()).append(": ").append(s.isPresence()).append("\n"));

        response.append("*Датчики температуры:* ");
        hue.getTemperatureSensors().forEach(s -> response.append("\t ").append(s.getName()).append(": показания температуры: ").append(s.getDegreesCelsius()).append("\n"));

        return response.toString();
    }

    public void turnLightInRoom(String name){
        HueMock hueMock = new HueMock();
        Hue hue = hueMock.createHueAndInitializeMockServer();
        hue.getRoomByName(name).get().setState(State.builder().color(Color.of(java.awt.Color.PINK)).on());

    }

    public void turnOffLightInRoom(String name){
        HueMock hueMock = new HueMock();
        Hue hue = hueMock.createHueAndInitializeMockServer();
        hue.getRoomByName(name).get().setState(State.builder().color(Color.of(java.awt.Color.PINK)).off());

    }
    public List<Room> getRooms(){
        HueMock hueMock = new HueMock();
        Hue hue = hueMock.createHueAndInitializeMockServer();
        return new ArrayList<>(hue.getRooms());
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
