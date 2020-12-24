package co.ke.ikocare.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import co.ke.ikocare.R;
import co.ke.ikocare.models.cluster.CusMarker;

public class MyCustomMarkerRenderManager extends DefaultClusterRenderer<CusMarker> {

    private final IconGenerator iconGenerator;
    private final ImageView imageView;
    private final int markerWidth;
    private final int markerHeight;

    public MyCustomMarkerRenderManager(Context context, GoogleMap map,
                                       ClusterManager<CusMarker> clusterManager)
    {
        super(context, map, clusterManager);

        iconGenerator = new IconGenerator(context.getApplicationContext());
        imageView = new ImageView(context.getApplicationContext());
        markerWidth = (int) context.getResources().getDimension(R.dimen.dp30);
        markerHeight = (int) context.getResources().getDimension(R.dimen.dp30);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(markerWidth,markerHeight));
        int padding = (int) context.getResources().getDimension(R.dimen.dp1);
        iconGenerator.setContentView(imageView);

        imageView.setPadding(padding,padding,padding,padding);





    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull CusMarker item, @NonNull MarkerOptions markerOptions) {

        imageView.setImageResource(item.getIconPicture());
        Bitmap icon = iconGenerator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());

    }

    @Override
    protected boolean shouldRenderAsCluster(@NonNull Cluster<CusMarker> cluster) {
        return  false;
    }
}
