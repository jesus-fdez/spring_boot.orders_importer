package importation.shared.application.exception;

import java.util.function.Consumer;

import org.springframework.util.function.ThrowingConsumer;

import jakarta.annotation.Nonnull;

public final class Throwing
{
    private Throwing()
    {
    }

    @Nonnull
    public static <T> Consumer<T> rethrow(@Nonnull final ThrowingConsumer<T> consumer)
    {
	return consumer;
    }
}