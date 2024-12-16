package importation.report.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import importation.report.application.contract.IGenerateReport;
import importation.report.domain.service.contract.ICreateReport;

@Component("generateReport")
public class GenerateReport implements IGenerateReport
{
    private static final Logger LOG = LoggerFactory.getLogger(GenerateReport.class);

    @Autowired
    private Environment env;

    @Autowired
    private ICreateReport createReport;

    public void handle()
    {
	try {
	    String filename = env.getProperty("import.csv_file_name");
	    createReport.handle("target/" + filename);
	} catch (Exception e) {
	    LOG.error(e.getLocalizedMessage(), e);
	}
    }

}
