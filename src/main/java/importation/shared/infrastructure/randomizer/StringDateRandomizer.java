package importation.shared.infrastructure.randomizer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.jeasy.random.api.Randomizer;

import importation.shared.domain.converter.StringToDateConverter;

public class StringDateRandomizer implements Randomizer<String>
{
    SimpleDateFormat sdf = new SimpleDateFormat(StringToDateConverter.dateFormat);

    @Override
    public String getRandomValue()
    {
	return sdf.format(new Date(ThreadLocalRandom.current().nextInt() * 1000L));
    }
}