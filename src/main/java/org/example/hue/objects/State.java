
package org.example.hue.objects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.ser.std.BooleanSerializer;
//import org.example.utils.BooleanDeserializer;


@JsonIgnoreProperties(ignoreUnknown = true)

@Generated("jsonschema2pojo")
public class State {

    @JsonProperty("on")
//    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
//    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean on;
    @JsonProperty("bri")
    private int bri;
    @JsonProperty("hue")
    private int hue;
    @JsonProperty("sat")
    private int sat;
    @JsonProperty("effect")
    private String effect;
    @JsonProperty("xy")
    private List<Double> xy;
    @JsonProperty("ct")
    private int ct;
    @JsonProperty("alert")
    private String alert;
    @JsonProperty("colormode")
    private String colormode;
    @JsonProperty("mode")
    private String mode;
    @JsonProperty("reachable")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private Boolean reachable;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public State() {
    }

//    public State(Boolean on, int bri, int hue, int sat, String effect, List<Double> xy, int ct, String alert, String colormode, String mode, Boolean reachable, Map<String, Object> additionalProperties) {
////        this.on = on;
//        this.bri = bri;
//        this.hue = hue;
//        this.sat = sat;
//        this.effect = effect;
//        this.xy = xy;
//        this.ct = ct;
//        this.alert = alert;
//        this.colormode = colormode;
//        this.mode = mode;
//        this.reachable = reachable;
//        this.additionalProperties = additionalProperties;
//    }
//
    @JsonProperty("on")
    public Boolean getOn() {
        return on;
    }

    @JsonProperty("on")
    public void setOn(Boolean on) {
        this.on = on;
    }

    @JsonProperty("bri")
    public int getBri() {
        return bri;
    }

    @JsonProperty("bri")
    public void setBri(int bri) {
        this.bri = bri;
    }

    @JsonProperty("hue")
    public int getHue() {
        return hue;
    }

    @JsonProperty("hue")
    public void setHue(int hue) {
        this.hue = hue;
    }

    @JsonProperty("sat")
    public int getSat() {
        return sat;
    }

    @JsonProperty("sat")
    public void setSat(int sat) {
        this.sat = sat;
    }

    @JsonProperty("effect")
    public String getEffect() {
        return effect;
    }

    @JsonProperty("effect")
    public void setEffect(String effect) {
        this.effect = effect;
    }

    @JsonProperty("xy")
    public List<Double> getXy() {
        return xy;
    }

    @JsonProperty("xy")
    public void setXy(List<Double> xy) {
        this.xy = xy;
    }

    @JsonProperty("ct")
    public int getCt() {
        return ct;
    }

    @JsonProperty("ct")
    public void setCt(int ct) {
        this.ct = ct;
    }

    @JsonProperty("alert")
    public String getAlert() {
        return alert;
    }

    @JsonProperty("alert")
    public void setAlert(String alert) {
        this.alert = alert;
    }

    @JsonProperty("colormode")
    public String getColormode() {
        return colormode;
    }

    @JsonProperty("colormode")
    public void setColormode(String colormode) {
        this.colormode = colormode;
    }

    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @JsonProperty("reachable")
    public boolean getReachable() {
        return reachable;
    }

    @JsonProperty("reachable")
    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
