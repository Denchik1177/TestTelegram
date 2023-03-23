
package org.example.hue.objects;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "1"
})
@JsonIgnoreProperties(ignoreUnknown = true)

@Generated("jsonschema2pojo")
public class LightObjects {



    public void setAdditionalProperties(Map<String, LightBulb> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @JsonIgnore
    private Map<String, LightBulb> additionalProperties = new LinkedHashMap<String, LightBulb>();

    @JsonAnyGetter
    public Map<String, LightBulb> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, LightBulb value) {
        this.additionalProperties.put(name, value);
    }

}
