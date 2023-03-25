package net.medrag.hashtests;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import static org.apache.commons.codec.digest.DigestUtils.getDigest;

@State(Scope.Thread)
public class HashTest {

    public static void main(String[] args) throws Exception {
        final var options = new OptionsBuilder()
                .include(HashTest.class.getCanonicalName() + ".*")
                .shouldDoGC(true)
                .build();
        new Runner(options).run();
    }

    // Approved FIPS algorithms
    // https://en.wikipedia.org/wiki/Federal_Information_Processing_Standards
    // https://csrc.nist.gov/publications/detail/fips/180/4/final
    // SHA2 family: SHA-224, SHA-256, SHA-384, SHA-512, SHA-512/224, and SHA-512/256.
    // SHA3 family: SHA3-224, SHA3-256, SHA3-384, and SHA3-512

    @Param({"10", "100", "1000", "5000"})
    private int stringSize;
    private String stringToHash;

    @Setup(Level.Invocation)
    public void setUp() {
        stringToHash = RandomStringUtils.random(stringSize);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha224Test(Blackhole blackhole) {
        final String result = Hex.encodeHexString(getDigest(MessageDigestAlgorithms.SHA_224).digest(StringUtils.getBytesUtf8(stringToHash)));
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha256Test(Blackhole blackhole) {
        final String result = DigestUtils.sha256Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha384Test(Blackhole blackhole) {
        final String result = DigestUtils.sha384Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha512Test(Blackhole blackhole) {
        final String result = DigestUtils.sha512Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha512_224Test(Blackhole blackhole) {
        final String result = DigestUtils.sha512_224Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha512_256Test(Blackhole blackhole) {
        final String result = DigestUtils.sha512_256Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha3_224Test(Blackhole blackhole) {
        final String result = DigestUtils.sha3_224Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha3_256Test(Blackhole blackhole) {
        final String result = DigestUtils.sha3_256Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha3_384Test(Blackhole blackhole) {
        final String result = DigestUtils.sha3_384Hex(stringToHash);
        blackhole.consume(result);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Fork(value = 3, warmups = 1)
    public void sha3_512Test(Blackhole blackhole) {
        final String result = DigestUtils.sha3_512Hex(stringToHash);
        blackhole.consume(result);
    }
}
