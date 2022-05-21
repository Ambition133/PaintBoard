package mypaintBoard;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class ImageAndTextAdapter extends BaseAdapter {
    Context context;
    int[] image;
    String[] color;
    public ImageAndTextAdapter(Context context, int[] image, String[] color) {
        this.context = context;
        this.image = image;
        this.color = color;
    }
    @Override
    public int getCount() {
        return color.length;
    }
    @Override
    public Object getItem(int i) {
        return color[i];
    }
    @Override
    public long getItemId(int i) {
        return image[i];
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // TODO 自动生成的方法存根
        view = View.inflate(context, R.layout.image_text_adapter, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.color_imageView);
        imageView.setImageResource(image[i]);
        TextView textView = view.findViewById(R.id.color_name);
        textView.setText(color[i]);
        return view;
    }
}
