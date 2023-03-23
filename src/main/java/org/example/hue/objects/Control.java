
package org.example.hue.objects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "mindimlevel",
    "maxlumen",
    "colorgamuttype",
    "colorgamut",
    "ct"
})
@Generated("jsonschema2pojo")
public class Control {

    @JsonProperty("mindimlevel")
    private int mindimlevel;
    @JsonProperty("maxlumen")
    private int maxlumen;
    @JsonProperty("colorgamuttype")
    private String colorgamuttype;
    @JsonProperty("colorgamut")
    private List<List<Double>> colorgamut;
    @JsonProperty("ct")
    private Ct ct;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("mindimlevel")
    public int getMindimlevel() {
        return mindimlevel;
    }

    @JsonProperty("mindimlevel")
    public void setMindimlevel(int mindimlevel) {
        this.mindimlevel = mindimlevel;
    }

    @JsonProperty("maxlumen")
    public int getMaxlumen() {
        return maxlumen;
    }

    @JsonProperty("maxlumen")
    public void setMaxlumen(int maxlumen) {
        this.maxlumen = maxlumen;
    }

    @JsonProperty("colorgamuttype")
    public String getColorgamuttype() {
        return colorgamuttype;
    }

    @JsonProperty("colorgamuttype")
    public void setColorgamuttype(String colorgamuttype) {
        this.colorgamuttype = colorgamuttype;
    }

    @JsonProperty("colorgamut")
    public List<List<Double>> getColorgamut() {
        return colorgamut;
    }

    @JsonProperty("colorgamut")
    public void setColorgamut(List<List<Double>> colorgamut) {
        this.colorgamut = colorgamut;
    }

    @JsonProperty("ct")
    public Ct getCt() {
        return ct;
    }

    @JsonProperty("ct")
    public void setCt(Ct ct) {
        this.ct = ct;
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
