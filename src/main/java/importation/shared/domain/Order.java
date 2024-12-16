package importation.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order
{
    @Id
    private Long id;

    private String uuid;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private OrderType type;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private SalesChannel channel;

    private String priority;

    private Date date;

    private Date shipDate;

    private Integer unitsSold;

    private BigDecimal unitPrice;

    private BigDecimal unitCost;

    private BigDecimal totalRevenue;

    private BigDecimal totalCost;

    private BigDecimal totalProfit;
}
