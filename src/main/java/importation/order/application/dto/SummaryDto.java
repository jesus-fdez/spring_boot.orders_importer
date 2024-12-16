package importation.order.application.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryDto
{
    private Map<String, Integer> region;

    private Map<String, Integer> country;

    private Map<String, Integer> type;

    private Map<String, Integer> salesChannel;

    private Map<String, Integer> priority;

    public SummaryDto()
    {
	this.region = new HashMap<String, Integer>();
	this.country = new HashMap<String, Integer>();
	this.type = new HashMap<String, Integer>();
	this.salesChannel = new HashMap<String, Integer>();
	this.priority = new HashMap<String, Integer>();
    }

    public void addRegion(String regionName)
    {
	if (this.region.get(regionName) == null) {
	    this.region.put(regionName, 0);
	}

	this.region.put(regionName, this.region.get(regionName) + 1);
    }

    public void addCountry(String countryName)
    {
	if (this.country.get(countryName) == null) {
	    this.country.put(countryName, 0);
	}

	this.country.put(countryName, this.country.get(countryName) + 1);
    }

    public void addType(String typeName)
    {
	if (this.type.get(typeName) == null) {
	    this.type.put(typeName, 0);
	}

	this.type.put(typeName, this.type.get(typeName) + 1);
    }

    public void addSalesChannel(String salesChannelName)
    {
	if (this.salesChannel.get(salesChannelName) == null) {
	    this.salesChannel.put(salesChannelName, 0);
	}

	this.salesChannel.put(salesChannelName, this.salesChannel.get(salesChannelName) + 1);
    }

    public void addPriority(String priorityName)
    {
	if (this.priority.get(priorityName) == null) {
	    this.priority.put(priorityName, 0);
	}

	this.priority.put(priorityName, this.priority.get(priorityName) + 1);
    }
}
