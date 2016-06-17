package utils;

/**
 * Created by madki on 17/06/16.
 */
public final class Urls {
    public static final String HTTPS = "https";
    public static final String HTTP = "http";
    public static final String BASE_URL = "https://www.headout.com";

    private Urls() {
        throw new AssertionError("No instances");
    }

    /**
     * @param url the url to be automatically prepended
     * @return {@link #absolute(String)} if url is relative, {@link #https(String)} if url doesn't
     * have a protocol
     */
    public static String auto(String url) {
        if (TextUtils.isEmpty(url)) return url;

        return https(absolute(url));
    }

    /**
     * @param url url that uses http or has no protocol
     * @return https version of a http url or a url that has no protocol specified
     */
    public static String https(String url) {
        if (TextUtils.isEmpty(url)) return url;

        if (url.startsWith(HTTP + ":")) {
            return url.replaceFirst(HTTP, HTTPS);
        }

        if (url.startsWith("//")) {
            return HTTPS + ":" + url;
        }

        return url;
    }

    /**
     * @param url relative url
     * @return absolute url if given url starts with a single "/"
     */
    public static String absolute(String url) {
        if (TextUtils.isEmpty(url)) return url;

        if (url.startsWith("/") && !url.startsWith("//")) {
            return url.replaceFirst("/", BASE_URL);
        }

        return url;
    }
}