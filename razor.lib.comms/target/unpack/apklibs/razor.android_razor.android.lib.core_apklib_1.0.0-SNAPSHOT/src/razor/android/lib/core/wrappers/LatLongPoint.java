package razor.android.lib.core.wrappers;

import com.google.android.maps.GeoPoint;

public final class LatLongPoint extends GeoPoint {
    public LatLongPoint(double latitude, double longitude) {
        super((int) (latitude * 1E6), (int) (longitude * 1E6));
    }
}
