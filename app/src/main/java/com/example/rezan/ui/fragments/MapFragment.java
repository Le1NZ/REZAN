package com.example.rezan.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.rezan.R;
import com.example.rezan.data.db.MapObject;
import com.example.rezan.databinding.FragmentMapBinding;
import com.example.rezan.ui.viewModels.MapFragmentViewModel;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.Image;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.Rect;
import com.yandex.mapkit.map.TextStyle;
import com.yandex.mapkit.map.internal.PlacemarkMapObjectBinding;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import org.w3c.dom.Text;

import java.util.Map;

public class MapFragment extends Fragment {

    private MapView mapView;
    private MapFragmentViewModel viewModel;

    private FragmentMapBinding binding;
    private Point centerRzn = new Point(54.629224, 39.736880);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        mapView = binding.mainMapView;
        MapKitFactory.initialize(getActivity());

        viewModel = new ViewModelProvider(getActivity()).get(MapFragmentViewModel.class);
        viewModel.download();

        viewModel.map.observe(getViewLifecycleOwner(), stringMapObjectMap -> {
            for (Map.Entry<String, MapObject> i : stringMapObjectMap.entrySet()) {
                if (i.getValue().getLatitude() == null || i.getValue().getLongitude() == null)
                    continue;

                IconStyle iconStyle = new IconStyle()
                        .setAnchor(new PointF(0.5f, 0.5f))
                        .setScale(1.5f).setTappableArea(
                                new Rect(new PointF(-3f, -3f), new PointF(3f, 3f))
                        );

                PlacemarkMapObject placemarkMapObject = mapView.getMap().getMapObjects().addPlacemark(
                        new Point(i.getValue().getLatitude(), i.getValue().getLongitude()));
                placemarkMapObject.setIconStyle(iconStyle);
                placemarkMapObject.addTapListener(setListener(i.getKey()));
                placemarkMapObject.useAnimation();
            }
        });

        moveToCenter();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
    }

    private void moveToCenter() {
        mapView.getMap().move(new CameraPosition(centerRzn,
                12.0f, 0.0f, 0.0f));
    }

    private MapObjectTapListener setListener(String key) {
        MapObjectTapListener listener = (mapObject1, point) -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", key);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_map_to_mapObjectFragment, bundle);
            return true;
        };
        return listener;
    }
}