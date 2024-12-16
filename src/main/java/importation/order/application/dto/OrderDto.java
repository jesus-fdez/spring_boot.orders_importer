package importation.order.application.dto;

import java.math.BigDecimal;

import org.jeasy.random.annotation.Randomizer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import importation.shared.infrastructure.randomizer.BigDecimalRandomizer;
import importation.shared.infrastructure.randomizer.StringDateRandomizer;
import importation.shared.infrastructure.randomizer.StringLongRandomizer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto
{
    @JsonProperty("uuid")
    private String uuid;

    @Randomizer(StringLongRandomizer.class)
    @JsonProperty("id")
    private String id;

    @JsonProperty("region")
    private String region;

    @JsonProperty("country")
    private String country;

    @JsonProperty("item_type")
    private String itemType;

    @JsonProperty("sales_channel")
    private String salesChannel;

    @JsonProperty("priority")
    private String priority;

    @Randomizer(StringDateRandomizer.class)
    @JsonProperty("date")
    private String date;

    @Randomizer(StringDateRandomizer.class)
    @JsonProperty("ship_date")
    private String shipDate;

    @JsonProperty("units_sold")
    private Integer unitsSold;

    @Randomizer(BigDecimalRandomizer.class)
    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    @Randomizer(BigDecimalRandomizer.class)
    @JsonProperty("unit_cost")
    private BigDecimal unitCost;

    @Randomizer(BigDecimalRandomizer.class)
    @JsonProperty("total_revenue")
    private BigDecimal totalRevenue;

    @Randomizer(BigDecimalRandomizer.class)
    @JsonProperty("total_cost")
    private BigDecimal totalCost;

    @Randomizer(BigDecimalRandomizer.class)
    @JsonProperty("total_profit")
    private BigDecimal totalProfit;
}
