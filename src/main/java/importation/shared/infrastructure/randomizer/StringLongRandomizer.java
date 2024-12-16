package importation.shared.infrastructure.randomizer;

import java.util.Random;

import org.jeasy.random.api.Randomizer;

public class StringLongRandomizer implements Randomizer<String>
{
    @Override
    public String getRandomValue()
    {
	return String.valueOf(new Random().nextLong());
    }
}