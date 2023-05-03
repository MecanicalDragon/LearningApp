package vhmapper;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
public class MapperTest {

    public static void main(String[] args) throws Exception {
        final var options = new OptionsBuilder()
                .include(MapperTest.class.getCanonicalName() + ".*")
                .shouldDoGC(true)
                .build();
        new Runner(options).run();
    }

    private final static int N = 500;
    private final static TheSession[] SOURCE = new TheSession[N];

    @Setup
    public void init() {
        for (int i = 0; i < N; i++) {
            SOURCE[i] = createSession();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 2, warmups = 1)
    public void testMapper(Blackhole blackhole) {
        for (int i = 0; i < N; i++) {
            final var stringStringMap = ((MappedEntity) SOURCE[i % N]).toMap();
            blackhole.consume(Mapper.fromMap(stringStringMap, TheSession.class));
        }
    }

    //TODO: use randomizer
    private TheSession createSession() {
        return new TheSession();
    }
}