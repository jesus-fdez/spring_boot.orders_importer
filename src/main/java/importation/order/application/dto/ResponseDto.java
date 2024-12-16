package importation.order.application.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto
{
    @JsonProperty("page")
    private Integer page;

    @JsonProperty("content")
    private List<OrderDto> content;

    @JsonProperty("links")
    private Map<String, String> pagination;
}