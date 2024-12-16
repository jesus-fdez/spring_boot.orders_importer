package importation.functional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import importation.BaseTest;
import importation.report.application.contract.IGenerateReport;

public class ReportTest extends BaseTest
{
    @Autowired
    private IGenerateReport generateReport;

    @Autowired
    private Environment env;

    @Test
    public void validReport()
    {
	try {
	    generateReport.handle();

	    File f = new File("target/" + env.getProperty("import.csv_file_name"));

	    assertTrue(f.exists() && !f.isDirectory());
	    assertTrue(Files.lines(f.toPath()).count() == 101);
	} catch (Exception e) {
	    fail(e);
	}
    }
}
