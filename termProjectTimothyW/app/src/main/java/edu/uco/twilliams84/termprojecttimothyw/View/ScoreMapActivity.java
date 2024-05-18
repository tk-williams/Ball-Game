package edu.uco.twilliams84.termprojecttimothyw.View;

import android.app.Activity;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Window;
import android.location.Address;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.uco.twilliams84.termprojecttimothyw.Database.DatabaseOperator;
import edu.uco.twilliams84.termprojecttimothyw.Model.Player;
import edu.uco.twilliams84.termprojecttimothyw.R;

public class ScoreMapActivity extends Activity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            DatabaseOperator databaseOperator = new DatabaseOperator(this);
            ScoreActivity.players = databaseOperator.gatherScores();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            for (Player player : ScoreActivity.players) {
                List<Address> addresses = geocoder.getFromLocationName(player.getCity() + ", " + player.getState() ,1);
                Address address = addresses.get(0);
                double longitude = address.getLongitude();
                double lantitude = address.getLatitude();
                LatLng latlng = new LatLng(lantitude, longitude);
                mMap.addMarker(new MarkerOptions()
                        .position(latlng)
                        .title(player.getName())
                        .snippet(player.getScores().get(0).toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            }
        } catch (IOException e) {
            System.exit(0);
        }
    }
}
