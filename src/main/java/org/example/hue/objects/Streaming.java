
package org.example.hue.objects;

import java.util.LinkedHashMap;
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
    "renderer",
    "proxy"
})
@Generated("jsonschema2pojo")
public class Streaming {

    @JsonProperty("renderer")
    private boolean renderer;
    @JsonProperty("proxy")
    private boolean proxy;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("renderer")
    public boolean getRenderer() {
        return renderer;
    }

    @JsonProperty("renderer")
    public void setRenderer(boolean renderer) {
        this.renderer = renderer;
    }

    @JsonProperty("proxy")
    public boolean getProxy() {
        return proxy;
    }

    @JsonProperty("proxy")
    public void setProxy(boolean proxy) {
        this.proxy = proxy;
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
