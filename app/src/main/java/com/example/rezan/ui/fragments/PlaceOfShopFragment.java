package com.example.rezan.ui.fragments;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.rezan.databinding.FragmentPlaceOfShopBinding;
import com.example.rezan.ui.viewModels.CustomMapView;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.TextStyle;
import com.yandex.mapkit.mapview.MapView;

public class PlaceOfShopFragment extends Fragment {

    private FragmentPlaceOfShopBinding binding;
    private Point tirazhPoint = new Point(54.685885, 39.638126);
    private CustomMapView mapView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlaceOfShopBinding.inflate(inflater, container, false);

        mapView = binding.tirazhMapView;
        mapView.getMap().move(new CameraPosition(tirazhPoint,11f, 0f, 0f));
        TextStyle textStyle = new TextStyle().setSize(10f).setOutlineColor(0);
        IconStyle iconStyle = new IconStyle()
                .setAnchor(new PointF(0.5f, 0.5f))
                .setScale(1.2f).setZIndex(0.1f).setFlat(false);
        PlacemarkMapObject placemarkMapObject = mapView.getMap().getMapObjects().addPlacemark(tirazhPoint);
        placemarkMapObject.setText("Копицентр ТИРАЖ", textStyle);
        placemarkMapObject.setIconStyle(iconStyle);

        binding.backToShop.setOnClickListener(v -> Navigation.findNavController(binding.getRoot()).popBackStack());

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
    }
}
