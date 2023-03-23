
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
    "certified",
    "control",
    "streaming"
})
@Generated("jsonschema2pojo")
public class Capabilities {

    @JsonProperty("certified")
    private boolean certified;
    @JsonProperty("control")
    private Control control;
    @JsonProperty("streaming")
    private Streaming streaming;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("certified")
    public boolean getCertified() {
        return certified;
    }

    @JsonProperty("certified")
    public void setCertified(boolean certified) {
        this.certified = certified;
    }

    @JsonProperty("control")
    public Control getControl() {
        return control;
    }

    @JsonProperty("control")
    public void setControl(Control control) {
        this.control = control;
    }

    @JsonProperty("streaming")
    public Streaming getStreaming() {
        return streaming;
    }

    @JsonProperty("streaming")
    public void setStreaming(Streaming streaming) {
        this.streaming = streaming;
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
