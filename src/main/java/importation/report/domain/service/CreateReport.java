package importation.report.domain.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import importation.report.domain.service.contract.ICreateReport;
import importation.report.domain.service.contract.IGetOrders;
import importation.shared.domain.Order;

@Service("createReport")
public class CreateReport implements ICreateReport
{
    private static final Logger LOG = LoggerFactory.getLogger(CreateReport.class);

    private static String CSV_SEPARATOR = ",";

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private IGetOrders getOrders;

    public void handle(String fileLocation)
    {
	BufferedWriter writer = null;

	try {
	    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileLocation), "UTF-8"));
	    List<Order> orders = getOrders.handle();
	    convertToCSV(orders, writer);
	} catch (Exception e) {
	    LOG.error(e.getLocalizedMessage(), e);
	} finally {
	    closeWriter(writer);
	}
    }

    private void convertToCSV(List<Order> orders, BufferedWriter writer) throws Exception
    {
	addHeader(writer);

	orders.stream().forEach(order -> {
	    try {
		StringBuffer line = new StringBuffer();
		line.append(order.getId());
		line.append(CSV_SEPARATOR);
		line.append(order.getPriority());
		line.append(CSV_SEPARATOR);
		line.append(sdf.format(order.getDate()));
		line.append(CSV_SEPARATOR);
		line.append(order.getRegion().getName());
		line.append(CSV_SEPARATOR);
		line.append(order.getCountry().getName());
		line.append(CSV_SEPARATOR);
		line.append(order.getType().getName());
		line.append(CSV_SEPARATOR);
		line.append(order.getChannel().getName());
		line.append(CSV_SEPARATOR);
		line.append(sdf.format(order.getShipDate()));
		line.append(CSV_SEPARATOR);
		line.append(order.getUnitsSold());
		line.append(CSV_SEPARATOR);
		line.append(order.getUnitPrice());
		line.append(CSV_SEPARATOR);
		line.append(order.getUnitCost());
		line.append(CSV_SEPARATOR);
		line.append(order.getTotalRevenue());
		line.append(CSV_SEPARATOR);
		line.append(order.getTotalCost());
		line.append(CSV_SEPARATOR);
		line.append(order.getTotalProfit());
		line.append(CSV_SEPARATOR);

		writer.write(line.toString());
		writer.newLine();
	    } catch (Exception e) {
		LOG.error(e.getLocalizedMessage(), e);
		return;
	    }
	});
    }

    private void addHeader(BufferedWriter writer) throws Exception
    {
	writer.write(
		"Order ID,Order Priority,Order Date,Region,Country,Item Type,Sales Channel,Ship Date,Units Sold,Unit Price,Unit Cost,Total Revenue,Total Cost,Total Profit");
	writer.newLine();
    }

    private void closeWriter(BufferedWriter writer)
    {
	if (writer != null) {
	    try {
		writer.flush();
		writer.close();
	    } catch (IOException e) {
		LOG.error(e.getLocalizedMessage(), e);
	    }
	}
    }
}
