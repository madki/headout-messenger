package models;

/**
 * Created by madki on 20/06/16.
 */
public class SelectTourPayload extends CustomPayload {
    private long tourId;

    SelectTourPayload(long tourId) {
        super(CustomPayloadType.SELECT_TOUR);
        this.tourId = tourId;
    }

    public static SelectTourPayload create(long tourId) {
        return new SelectTourPayload(tourId);
    }

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    public long getTourId() {
        return tourId;
    }

    @Override
    public String toString() {
        return "SelectTourPayload{" +
                "tourId=" + tourId +
                '}';
    }
}
