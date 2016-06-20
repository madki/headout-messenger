package models;

/**
 * Created by madki on 20/06/16.
 */
public class PaginateToursPayload extends CustomPayload {
    private final String url;

    PaginateToursPayload(String url) {
        super(CustomPayloadType.PAGINATE_TOURS);
        this.url = url;
    }

    public static PaginateToursPayload create(String url) {
        return new PaginateToursPayload(url);
    }

    @Override
    public String toString() {
        return "PaginateToursPayload{" +
                "url='" + url + '\'' +
                '}';
    }
}
