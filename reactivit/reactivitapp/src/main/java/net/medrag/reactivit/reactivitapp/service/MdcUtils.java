package net.medrag.reactivit.reactivitapp.service;

import net.medrag.reactivit.dto.RequestDto;
import org.slf4j.MDC;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Utils to wrap code for MDC logging. Functions, presented here, fill MDC with values from {@link ContextView}, execute accepted functional
 * interface, and then clear MDC. To make this utils work, code must be wrapped with {@link Mono#deferContextual(Function)} function.
 * <p>
 * Template for Intellij IDEA: `return Mono.deferContextual(ctx -> { $SELECTION$ });`
 * (Ctrl+Alt+s -> Editor -> Live Templates to add; wrap expression -> Ctrl+Alt+j to use)
 *
 * @author Stanislav Tretyakov
 * 27.05.2022
 */
public final class MdcUtils {

    private MdcUtils() {}

    private static final String REACTOR_ON_DISCARD_LOCAL = "reactor.onDiscard.local";

    public static <T> Consumer<Signal<T>> logOnNext(Consumer<T> consumer) {
        return signal -> {
            if (signal.isOnNext()) {
                try {
                    fillMdcContext(signal.getContextView());
                    consumer.accept(signal.get());
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    public static <T> Consumer<Signal<T>> logOnError(Consumer<Throwable> consumer) {
        return signal -> {
            if (signal.isOnError()) {
                try {
                    fillMdcContext(signal.getContextView());
                    consumer.accept(signal.getThrowable());
                } finally {
                    MDC.clear();
                }
            }
        };
    }

    /**
     * Enrich Mdc context with specified key and value if both of them are not null, do nothing otherwise.
     *
     * @param context  mdc context to enrich.
     * @param mdcKey   key
     * @param mdcValue value
     * @return enriched mdc context.
     */
    public static Context enrichContext(Context context, String mdcKey, String mdcValue) {
        return mdcKey != null && mdcValue != null ? context.put(mdcKey, mdcValue) : context;
    }

    public static Context enrichContext(Context context, RequestDto dto) {
        if (dto != null) {
            context = enrichContext(context, "name", dto.getName());
            context = enrichContext(context, "surname", dto.getSurname());
        }
        return context;
    }

    /**
     * Function, that allows to log with MDC.
     * Template for Intellij IDEA: `logWithMdc(ctx, () -> $SELECTION$)`
     * (Ctrl+Alt+s -> Editor -> Live Templates to add; wrap expression -> Ctrl+Alt+j to use)
     *
     * <pre>{@code
     *     return Mono.deferContextual(ctx -> {
     *              logWithMdc(ctx, () -> log.info("logging with MDC here."));
     *              return Mono.just(new ResponseDto("Ivan", "Ivanov"));
     *          })
     *          .contextWrite(ctx -> ctx.put(NAME, req.getName()))
     *          .contextWrite(ctx -> ctx.put(SNAME, req.getSurname()));
     * }
     * </pre>
     */
    public static void logWithMdc(ContextView context, Runnable runnable) {
        try {
            fillMdcContext(context);
            runnable.run();
        } finally {
            MDC.clear();
        }
    }

    /**
     * Function, that allows to log with MDC considering logging level. That allows to slightly increase performance when logging level
     * is higher than message logging level.
     *
     * <pre>{@code
     *     return Mono.deferContextual(ctx -> {
     *              logWithMdc(ctx, log::isDebugEnabled, () -> log.info("logging with MDC here."));
     *              return Mono.just(new ResponseDto("Ivan", "Ivanov"));
     *          })
     *          .contextWrite(ctx -> ctx.put(NAME, req.getName()))
     *          .contextWrite(ctx -> ctx.put(SNAME, req.getSurname()));
     * }
     * </pre>
     */
    public static void logWithMdc(ContextView context, BooleanSupplier level, Runnable runnable) {
        if (level.getAsBoolean()) {
            try {
                fillMdcContext(context);
                runnable.run();
            } finally {
                MDC.clear();
            }
        }
    }

    /**
     * Function, that allows to enrich mdc context and log several times with different log levels.
     *
     * @param context   mdc context
     * @param level1    first logging level
     * @param runnable1 first logging statement
     * @param level2    second logging level
     * @param runnable2 second logging statement
     */
    public static void multilogWithMdc(
        ContextView context,
        BooleanSupplier level1,
        Runnable runnable1,
        BooleanSupplier level2,
        Runnable runnable2
    ) {
        if (level1.getAsBoolean() || level2.getAsBoolean()) {
            try {
                fillMdcContext(context);
                if (level1.getAsBoolean()) {
                    runnable1.run();
                }
                if (level2.getAsBoolean()) {
                    runnable2.run();
                }
            } finally {
                MDC.clear();
            }
        }
    }

    /**
     * Function, that allows to enrich mdc context and log several times with different log levels.
     *
     * @param context   mdc context
     * @param level1    first logging level
     * @param runnable1 first logging statement
     * @param level2    second logging level
     * @param runnable2 second logging statement
     * @param level3    third logging level
     * @param runnable3 third logging statement
     */
    public static void multilogWithMdc(
        ContextView context,
        BooleanSupplier level1,
        Runnable runnable1,
        BooleanSupplier level2,
        Runnable runnable2,
        BooleanSupplier level3,
        Runnable runnable3
    ) {
        if (level1.getAsBoolean() || level2.getAsBoolean() || level3.getAsBoolean()) {
            try {
                fillMdcContext(context);
                if (level1.getAsBoolean()) {
                    runnable1.run();
                }
                if (level2.getAsBoolean()) {
                    runnable2.run();
                }
                if (level3.getAsBoolean()) {
                    runnable3.run();
                }
            } finally {
                MDC.clear();
            }
        }
    }

    /**
     * Function, that allows to log with MDC and return some value.
     * Template for Intellij IDEA: `valueWithMdc(ctx, () -> $SELECTION$)`
     * (Ctrl+Alt+s -> Editor -> Live Templates to add; wrap expression -> Ctrl+Alt+j to use)
     *
     * <pre>{@code
     *     String result = valueWithMdc(ctx, () -> doRequest());
     *
     *     private String doRequest() {
     *         log.info("logging with MDC here");
     *         return "OK";
     *     }
     * }
     * </pre>
     */
    public static <R> R valueWithMdc(ContextView context, Supplier<R> supplier) {
        try {
            fillMdcContext(context);
            return supplier.get();
        } finally {
            MDC.clear();
        }
    }

    /**
     * Function, that allows to log with MDC and return Mono, that will be executed later. Honestly, there is no difference between this
     * function and {@link #valueWithMdc(ContextView, Supplier)}, but this function presence allows to additionally check yourself inferring
     * values: this one returns mono.
     * Template for Intellij IDEA: `monoWithMdc(ctx, () -> $SELECTION$)`
     * (Ctrl+Alt+s -> Editor -> Live Templates to add; wrap expression -> Ctrl+Alt+j to use)
     *
     * <pre>{@code
     *     Mono<String> mono = monoWithMdc(ctx, () -> doRequest());
     *
     *     private Mono<String> doRequest() {
     *         log.info("logging with MDC here");
     *         return Mono.just("OK");
     *     }
     * }
     * </pre>
     */
    public static <R> Mono<R> monoWithMdc(ContextView context, Supplier<Mono<R>> supplier) {
        try {
            fillMdcContext(context);
            return supplier.get();
        } finally {
            MDC.clear();
        }
    }

    /**
     * Allows to use MDC logging inside {@link Mono#map(Function)} function without {@link #logWithMdc} function usage.
     *
     * <pre>{@code
     *    .map(r -> mapWithMdc(ctx, r, resp -> {
     *        log.info("log with MDC here: [{}]", resp.getName());
     *        return new ResponseDto(resp.getName(), resp.getSurname());
     *    }))
     * }
     * </pre>
     */
    public static <T, R> R mapWithMdc(ContextView context, T element, Function<? super T, ? extends R> mapper) {
        return applyFunction(context, element, mapper);
    }

    /**
     * Allows to use MDC logging inside {@link Mono#flatMap(Function)} function without {@link #logWithMdc} function usage.
     *
     * <pre>{@code
     *     return Mono.deferContextual(ctx ->
     *             doRequest()
     *                 .flatMap(r -> flatMapWithMdc(ctx, r, resp -> {
     *                     log.info("sending request with MDC here: [{}]", req.getName());
     *                     var response = doRequest();
     *                     log.info("response received with MDC here: [{}]", req.getName());
     *                     return response;
     *                 }))
     *         );
     * }
     * </pre>
     */
    public static <T, R> Mono<R> flatMapWithMdc(ContextView context, T element, Function<? super T, ? extends Mono<R>> transformer) {
        return applyFunction(context, element, transformer);
    }

    public static void onErrorWithMdc(ContextView context, Throwable throwable, Consumer<? super Throwable> onError) {
        acceptConsumer(context, throwable, onError);
    }

    public static <T> void onNextWithMdc(ContextView context, T element, Consumer<? super T> onNext) {
        acceptConsumer(context, element, onNext);
    }

    private static <T, R> R applyFunction(ContextView context, T t, Function<? super T, ? extends R> function) {
        try {
            fillMdcContext(context);
            return function.apply(t);
        } finally {
            MDC.clear();
        }
    }

    private static <T> void acceptConsumer(ContextView context, T t, Consumer<? super T> consumer) {
        try {
            fillMdcContext(context);
            consumer.accept(t);
        } finally {
            MDC.clear();
        }
    }

    private static void fillMdcContext(ContextView context) {
        context.stream().forEach(entry -> {
            if (!REACTOR_ON_DISCARD_LOCAL.equals(entry.getKey())) {
                MDC.put(entry.getKey().toString(), entry.getValue().toString());
            }
        });
    }
}
