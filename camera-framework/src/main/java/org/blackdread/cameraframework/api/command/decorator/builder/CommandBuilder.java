package org.blackdread.cameraframework.api.command.decorator.builder;

import org.blackdread.cameraframework.api.command.AbstractCanonCommand;
import org.blackdread.cameraframework.api.command.CanonCommand;
import org.blackdread.cameraframework.api.command.decorator.DecoratorCommand;
import org.blackdread.cameraframework.api.command.decorator.impl.DefaultValueOnErrorDecorator;
import org.blackdread.cameraframework.api.command.decorator.impl.TimeoutCommandDecorator;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * <p>Allow to decorate commands with builder. Easier and less verbose than chaining instance of decorators.</p>
 * <p>This builder is not reusable, once {@link #build()} is called, it cannot be used again, instance of builder should be discarded.</p>
 * <p>If reusable builder is desired, can use {@link CommandBuilderReusable} but is less type-safe and can produce class cast exception at runtime for {@link DefaultValueOnErrorDecorator} if used not properly.</p>
 * NOTE: Not thread-safe !
 *
 * @param <T> type of builder which allow to extends without overloading everything
 * @param <R> type of command return value
 */
@NotThreadSafe
public class CommandBuilder<T extends CommandBuilder<T, R>, R> {

    private final CanonCommand<R> canonCommand;

    private DecoratorCommand<R> decoratedCommand;

    private boolean wasBuilt = false;

    // keep order in which decorator are called is important
    private final List<Supplier<DecoratorCommand<R>>> decorators = new ArrayList<>(5);

    /**
     * @param canonCommand command to decorate
     */
    public CommandBuilder(final CanonCommand<R> canonCommand) {
        this.canonCommand = Objects.requireNonNull(canonCommand);
    }

    protected final CanonCommand<R> getCommandToDecorate() {
        return decoratedCommand == null ? canonCommand : decoratedCommand;
    }

    /**
     * @return mutable list of decorators that will be applied to commands at {@link #build()}
     */
    protected final List<Supplier<DecoratorCommand<R>>> getDecorators() {
        return decorators;
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
     * @return Canon command decorated if any was set before
     * @throws IllegalStateException if build had already been called before
     */
    public CanonCommand<R> build() {
        if (wasBuilt)
            throw new IllegalStateException("Build has already been called");
        wasBuilt = true;
        try {
            final List<Supplier<DecoratorCommand<R>>> decorators = getDecorators();
            if (decorators.isEmpty())
                return canonCommand;
            for (final Supplier<DecoratorCommand<R>> decoratorSupplier : decorators) {
                decoratedCommand = decoratorSupplier.get();
            }
            return decoratedCommand;
        } finally {
            decoratedCommand = null;
            decorators.clear();
        }
    }

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }


    public static class SimpleBuilder<R> extends CommandBuilderReusable<CommandBuilder.SimpleBuilder<R>, R> {
        public SimpleBuilder(final AbstractCanonCommand<R> canonCommand) {
            super(canonCommand);
        }
    }
}
