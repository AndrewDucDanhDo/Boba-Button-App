package andrewdddo.gmail.com.bobabutton;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;



public class ShopActivity extends AppCompatActivity {

    private int[] Images = {R.drawable.clicker_icon_x1, R.drawable.list_1_milk_tea_1x};
    private String[] Names = {"Clicker", "Milk Tea Boba"};
    private String[] Description = {"+10 bobas per second", "+100 bobas per second"};

    private SlidrInterface slidr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        slidr = Slidr.attach(this);
        showShopFragment();
    }

    private void showShopFragment() {
        ViewGroup container = findViewById(R.id.shopActivityContainer);
        ShopAdapter shopAdapter = new ShopAdapter();
        container.removeAllViews();
        container.addView(getLayoutInflater().inflate(R.layout.shop_activity, null));
        ((ListView)findViewById(R.id.listShop)).setAdapter(shopAdapter);
    }

    public class ShopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Images.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_listview, null);

            ((ImageView)convertView.findViewById(R.id.imgItem)).setImageResource(Images[position]);
            ((TextView)convertView.findViewById(R.id.tvName)).setText(Names[position]);
            ((TextView)convertView.findViewById(R.id.tvDescription)).setText(Description[position]);
            return convertView;
        }
    }
}
