import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

public class VkApi {
    private static final String AUTH_URL = "https://oauth.vk.com/authorize?client_id={APP_ID}&scope={PERMISSIONS}&redirect_uri={REDIRECT_URI}&display={DISPLAY}&v={API_VERSION}&response_type=token";

    private static final String API_REQUEST = "https://api.vk.com/method/{METHOD}?{PARAMS}&access_token={TOKEN}&v=5.21";

    public static VkApi with(String appId, String accessToken) throws IOException {
        return new VkApi(appId, accessToken);
    }

    private final String accessToken;

    private VkApi(String appId, String accessToken) throws IOException {
        this.accessToken = accessToken;
        if (accessToken == null || accessToken.isEmpty()) {
            auth(appId);
            throw new Error("Need access token");
        }
    }

    private void auth(String appId) throws IOException {
        String reqUrl = AUTH_URL
                .replace("{APP_ID}", appId)
                .replace("{PERMISSIONS}", "photos,messages")
                .replace("{REDIRECT_URI}", "https://oauth.vk.com/blank.html")
                .replace("{DISPLAY}", "page")
                .replace("{API_VERSION}", "5.21");
        try {
            Desktop.getDesktop().browse(new URL(reqUrl).toURI());
        } catch (URISyntaxException ex) {
            throw new IOException(ex);
        }
    }

    public String getDialogs() throws IOException {
        return invokeApi("messages.getDialogs", null);
    }

    public String getHistory(String userId, boolean rev) throws IOException {
        return invokeApi("messages.getHistory", Params.create()
                .add("user_id", userId)
                .add("count", "1")
                .add("rev", rev ? "1" : "0"));
    }

    public String send(String userId, String rev) throws IOException {
        return invokeApi("messages.send", Params.create()
                .add("user_id", userId)
                .add("message", rev));
    }

    public String getPeople(String userId) throws IOException {
        return invokeApi("users.get", Params.create()
                .add("user_ids", userId));
    }

    public String getAlbums(String userId) throws IOException {
        return invokeApi("photos.getAlbums", Params.create()
                .add("owner_id", userId)
                .add("photo_sizes", "1")
                .add("thumb_src", "1"));
    }

    private String invokeApi(String method, Params params) throws IOException {
        final String parameters = (params == null) ? "" : params.build();
        String reqUrl = API_REQUEST
                .replace("{METHOD}", method)
                .replace("{TOKEN}", accessToken)
                .replace("{PARAMS}&", parameters);
        final StringBuilder result = new StringBuilder();
        final URL url = new URL(reqUrl);
        try (InputStream is = url.openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines().forEach(result::append);
        }
        return result.toString();
    }

    private static class Params {

        public static Params create() {
            return new Params();
        }

        private final HashMap<String, String> params;

        private Params() {
            params = new HashMap<>();
        }

        public Params add(String key, String value) {
            params.put(key, value);
            return this;
        }

        public String build() {
            if (params.isEmpty()) return "";
            final StringBuilder out = new StringBuilder();
            params.keySet().stream().forEach(key -> {
                out.append(key).append('=').append(params.get(key)).append('&');
            });
            return out.toString();
        }
    }
}

