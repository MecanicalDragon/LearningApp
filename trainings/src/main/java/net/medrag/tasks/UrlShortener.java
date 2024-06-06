package net.medrag.tasks;


import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * As simple as possible but ready for production.
 * Must hold upto 100 urls.
 * Urls must be random.
 */
public class UrlShortener {
    private static final String PREFIX = "https://shorturls.com/";
    private final ConcurrentHashMap<String, String> shortToUrls = new ConcurrentHashMap<>(128, 1.0f);

    public String add(String url) {
        if (shortToUrls.size() >= 100) {
            return null;
        }
        final Ref<String> added = new Ref<>();
        while (added.get() == null) {
            byte[] b = new byte[6];
            ThreadLocalRandom.current().nextBytes(b);
            final String shortUrl = PREFIX + (new String(Base64.getEncoder().encode(b)).replace("/", "-"));
            shortToUrls.compute(shortUrl, (k, v) -> {
                if (v != null) {
                    return v;
                } else {
                    added.set(shortUrl);
                    return url;
                }
            });
        }
        return added.get();
    }

    public String redirect(String shortUrl) {
        return shortToUrls.get(shortUrl);
    }

    public boolean remove(String shortUrl) {
        return shortUrl != null && shortToUrls.remove(shortUrl) != null;
    }

    private static final class Ref<T> {
        private T object = null;

        T get() {
            return object;
        }

        void set(T object) {
            this.object = object;
        }
    }
}
