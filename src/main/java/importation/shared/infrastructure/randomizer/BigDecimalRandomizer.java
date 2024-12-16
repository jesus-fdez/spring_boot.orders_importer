package importation.shared.infrastructure.randomizer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.jeasy.random.api.Randomizer;

public class BigDecimalRandomizer implements Randomizer<BigDecimal>
{
    @Override
    public BigDecimal getRandomValue()
    {
	return BigDecimal.valueOf(new Random().nextDouble()).setScale(2, RoundingMode.HALF_UP);
    }
}