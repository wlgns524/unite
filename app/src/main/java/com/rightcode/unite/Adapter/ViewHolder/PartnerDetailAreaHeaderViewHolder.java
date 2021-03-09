
package com.rightcode.unite.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rightcode.unite.Activity.Basic.BaseActivity;
import com.rightcode.unite.Activity.Partner.PartnerDetailActivity;
import com.rightcode.unite.Fragment.Product.ProductInformationFragment;
import com.rightcode.unite.R;
import com.rightcode.unite.Util.ImageWrapper;
import com.rightcode.unite.Util.MoneyHelper;
import com.rightcode.unite.network.model.response.partner.PartnerDetail;
import com.rightcode.unite.network.model.response.product.ProductDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnerDetailAreaHeaderViewHolder extends CommonRecyclerViewHolder implements OnMapReadyCallback {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_join)
    TextView tv_join;
    @BindView(R.id.tv_people)
    TextView tv_people;
    @BindView(R.id.tv_view)
    TextView tv_view;
    @BindView(R.id.tv_want_gender)
    TextView tv_want_gender;
    @BindView(R.id.tv_want_age)
    TextView tv_want_age;
    @BindView(R.id.tv_want_people)
    TextView tv_want_people;


    private Context mContext;
    private GoogleMap mMap;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public PartnerDetailAreaHeaderViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);


        // 기존에 사용하던 다음 2줄은 문제가 있습니다.

        // CameraUpdateFactory.zoomTo가 오동작하네요.
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void onBind(PartnerDetail data) {
        SupportMapFragment mapFragment = (SupportMapFragment) (((BaseActivity) mContext).getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        ImageWrapper.loadCircle(mContext, data.getUserImage(), iv_profile);
        tv_user_name.setText(data.getUserName());
        tv_date.setText(data.getCreatedAt());
        tv_title.setText(data.getTitle());
        tv_content.setText(data.getContent());
        tv_join.setText(String.format("%d", data.getJoin()));
        tv_people.setText(String.format("%d/%d명", data.getJoin(), data.getPeople()));
        tv_view.setText(String.format("%d", data.getViewCount()));
        tv_want_gender.setText(data.getGender());
        tv_want_age.setText(data.getAge());
        tv_want_people.setText(String.format("%d명",data.getPeople()));
    }


    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
