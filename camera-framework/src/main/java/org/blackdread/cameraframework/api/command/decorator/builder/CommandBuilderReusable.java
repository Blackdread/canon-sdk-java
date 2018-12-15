package org.blackdread.cameraframework.api.command.decorator.builder;

import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.blackdread.cameraframework.api.command.decorator.impl.DefaultValueOnErrorDecorator;
import org.blackdread.cameraframework.api.command.decorator.impl.TimeoutCommandDecorator;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * <p>Allow to decorate commands with builder. Easier and less verbose than chaining instance of decorators</p>
 * <p>This builder is reusable meaning that decorators are applied for each command passed {@link #setCanonCommand(CanonCommand)} and build only when {@link #build()} is called.
 * <br>
 * Useful when want to apply always same decorators and values.
 * </p>
 * <p>
 * NOTE: Not thread-safe !
 *
 * @param <T> type of builder which allow to extends without overloading everything
 * @param <R> type of command return value, can use Object to match any type but careful if later need to cast and type is wrong (This generic type could be removed)
 */
@NotThreadSafe
public class CommandBuilderReusable<T extends CommandBuilderReusable<T, R>, R> {

    private CanonCommand<R> canonCommand;

    private DecoratorCommand<R> decoratedCommand;

    // keep order in which decorator are called is important
    private final List<Supplier<DecoratorCommand<R>>> decorators = new ArrayList<>(5);

    public CommandBuilderReusable() {
    }

    /**
     * @param canonCommand initial command to decorate
     * @deprecated not sure to keep this constructor, might be confusing but allow to have a type-safe one even if can still pass other types
     */
    public CommandBuilderReusable(final CanonCommand<R> canonCommand) {
        this.canonCommand = canonCommand;
    }

    /**
     * @return command to decorate
     */
    protected final CanonCommand<R> getCommandToDecorate() {
        if (canonCommand == null)
            throw new IllegalStateException("Canon command must be set");
        return decoratedCommand == null ? canonCommand : decoratedCommand;
    }

    /**
     * @return mutable list of decorators that will be applied to commands at {@link #build()}
     */
    protected final List<Supplier<DecoratorCommand<R>>> getDecorators() {
        return decorators;
    }

    public T setCanonCommand(final CanonCommand<R> canonCommand) {
        this.canonCommand = canonCommand;
        return self();
    }

    public T timeout(final Duration timeout) {
        getDecorators().add(() -> new TimeoutCommandDecorator<>(getCommandToDecorate(), timeout));
        return self();
    }

    public T withDefaultOnException(final R defaultValue) {
        getDecorators().add(() -> new DefaultValueOnErrorDecorator<>(getCommandToDecorate(), defaultValue));
        return self();
    }

// Could give access to method below but not really safe and implementation must take care to not share values, etc
//    public T decorate(final Function<CanonCommand<R>, DecoratorCommand<R>> decorator) {
//        decorators.add(() -> decorator.apply(getCommandToDecorate()));
//        return self();
//    }

    /**
     * On return, canon command is set to null
     *
     * @return Canon command decorated if any was set before
     * @throws IllegalStateException if canon command was not set before call this method
     */
    public CanonCommand<R> build() {
        if (canonCommand == null)
            throw new IllegalStateException("Canon command must be set");
        try {
            final List<Supplier<DecoratorCommand<R>>> localDecorators = getDecorators();
            if (localDecorators.isEmpty())
                return canonCommand;
            for (final Supplier<DecoratorCommand<R>> decoratorSupplier : localDecorators) {
                decoratedCommand = decoratorSupplier.get();
            }
            return decoratedCommand;
        } finally {
            canonCommand = null;
            decoratedCommand = null;
        }
    }

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    public static class ReusableBuilder<R> extends CommandBuilderReusable<ReusableBuilder<R>, R> {
        public ReusableBuilder() {
        }

        public ReusableBuilder(final CanonCommand<R> canonCommand) {
            super(canonCommand);
        }
    }
}
