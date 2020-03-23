/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.cameraframework.api.context;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;

/**
 * Associates a given {@link CommandContext} with the current execution thread.
 * <p>
 * This class provides a series of static methods that delegate to an instance of
 * {@link CommandContextHolderStrategy}. The
 * purpose of the class is to provide a convenient way to specify the strategy that should
 * be used for a given JVM. This is a JVM-wide setting, since everything in this class is
 * <code>static</code> to facilitate ease of use in calling code.
 * <p>
 * To specify which strategy should be used, you must provide a mode setting. A mode
 * setting is one of the three valid <code>MODE_</code> settings defined as
 * <code>static final</code> fields, or a fully qualified classname to a concrete
 * implementation of
 * {@link CommandContextHolderStrategy} that
 * provides a public no-argument constructor.
 * <p>
 * There are two ways to specify the desired strategy mode <code>String</code>. The first
 * is to specify it via the system property keyed on {@link #SYSTEM_PROPERTY}. The second
 * is to call {@link #setStrategyName(String)} before using the class. If neither approach
 * is used, the class will default to using {@link #MODE_THREADLOCAL}.
 * <p>
 * NOTE: See SecurityContextHolder from Spring Framework to see similar code as this was taken from there
 * </p>
 * <p>Created on 2018/10/23.</p>
 *
 * @author Yoann CAPLAIN
 * @since 1.0.0
 * @deprecated not used and not sure if would be useful
 */
@Deprecated
public class CommandContextHolder {

    public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
    //	public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";
//	public static final String MODE_GLOBAL = "MODE_GLOBAL"; // might never create this one as it would work only for single command at once for any camera or single camera use
    public static final String SYSTEM_PROPERTY = "blackdread.canon.framework.command.strategy";
    private static String strategyName = System.getProperty(SYSTEM_PROPERTY);
    private static CommandContextHolderStrategy strategy;
    private static int initializeCount = 0;

    static {
        initialize();
    }

    /**
     * Explicitly clears the context value from the current thread.
     */
    public static void clearContext() {
        strategy.clearContext();
    }

    /**
     * Obtain the current <code>CommandContext</code>.
     *
     * @return the command context (never <code>null</code>)
     */
    public static CommandContext getContext() {
        return strategy.getContext();
    }

    /**
     * Primarily for troubleshooting purposes, this method shows how many times the class
     * has re-initialized its <code>CommandContextHolderStrategy</code>.
     *
     * @return the count (should be one unless you've called
     * {@link #setStrategyName(String)} to switch to an alternate strategy.
     */
    public static int getInitializeCount() {
        return initializeCount;
    }

    private static void initialize() {
        if (!StringUtils.isBlank(strategyName)) {
            // Set default
            strategyName = MODE_THREADLOCAL;
        }

        if (strategyName.equals(MODE_THREADLOCAL)) {
            strategy = new ThreadLocalCommandContextHolderStrategy();
        }
//		else if (strategyName.equals(MODE_INHERITABLETHREADLOCAL)) {
//			strategy = new InheritableThreadLocalCommandContextHolderStrategy();
//		}
//		else if (strategyName.equals(MODE_GLOBAL)) {
//			strategy = new GlobalCommandContextHolderStrategy();
//		}
        else {
            // Try to load a custom strategy
            try {
                Class<?> clazz = Class.forName(strategyName);
                Constructor<?> customStrategy = clazz.getConstructor();
                strategy = (CommandContextHolderStrategy) customStrategy.newInstance();
            } catch (Exception e) {
                throw new IllegalStateException("Failed to load custom strategy: " + strategyName);
            }
        }

        initializeCount++;
    }

    /**
     * Associates a new <code>CommandContext</code> with the current thread of execution.
     *
     * @param context the new <code>CommandContext</code> (may not be <code>null</code>)
     */
    public static void setContext(CommandContext context) {
        strategy.setContext(context);
    }

    /**
     * Changes the preferred strategy. Do <em>NOT</em> call this method more than once for
     * a given JVM, as it will re-initialize the strategy and adversely affect any
     * existing threads using the old strategy.
     *
     * @param strategyName the fully qualified class name of the strategy that should be
     *                     used.
     */
    public static void setStrategyName(String strategyName) {
        CommandContextHolder.strategyName = strategyName;
        initialize();
    }

    /**
     * Allows retrieval of the context strategy.
     *
     * @return the configured strategy for storing the command context.
     */
    public static CommandContextHolderStrategy getContextHolderStrategy() {
        return strategy;
    }

    /**
     * Delegates the creation of a new, empty context to the configured strategy.
     *
     * @return empty context (never null)
     */
    public static CommandContext createEmptyContext() {
        return strategy.createEmptyContext();
    }

    @Override
    public String toString() {
        return "CommandContextHolder[strategy='" + strategyName + "'; initializeCount="
            + initializeCount + "]";
    }
}
