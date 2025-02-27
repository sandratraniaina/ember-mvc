package mg.emberframework.util.http;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlParser {
    private UrlParser() {
    }

    public static String getRoute(String url) throws URISyntaxException {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        URI uri = new URI(url);
        String path = uri.getPath();

        String[] segments = path.split("/");

        if (segments.length > 2) {
            StringBuilder route = new StringBuilder();
            for (int i = 2; i < segments.length; i++) {
                route.append("/").append(segments[i]);
            }

            return route.toString();
        }

        return "/";
    }
}
