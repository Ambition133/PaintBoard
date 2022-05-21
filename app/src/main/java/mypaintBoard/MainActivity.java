package mypaintBoard;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_drawLine, btn_drawRect, btn_drawCircle, btn_handWrite, btn_eraser,btn_clearScreen, btn_open, btn_save;
    private Button btn_handAdd, btn_handSub, btn_eraserAdd, btn_eraserSub, btn_colorButton, btn_chooseBackground, btn_withdraw,btn_author;
    private MyView myView;
    final static String DrawLine = "DrawLine";
    final static String DrawRectPen = "DrawRectPen";
    final static String DrawCirclePen = "DrawCirclePen";
    final static String HandWritePen = " HandWritePen";
    final static String Eraser = "Eraser";
    static int chooseColor = Color.BLACK;
    int StrokeWidth = 6;
    int eraserStrokeWidth = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到组件
        btn_drawLine = findViewById(R.id.btn_drawLine);
        btn_drawRect = findViewById(R.id.btn_drawRect);
        btn_drawCircle = findViewById(R.id.btn_drawCircle);
        btn_handWrite = findViewById(R.id.btn_handWrite);
        btn_eraser = findViewById(R.id.btn_eraser);
        btn_clearScreen= findViewById(R.id.btn_clearScreen);
        btn_open = findViewById(R.id.btn_open);
        btn_save = findViewById(R.id.btn_save);
        btn_handAdd = findViewById(R.id.btn_handAdd);
        btn_handSub = findViewById(R.id.btn_handSub);
        btn_eraserAdd = findViewById(R.id.btn_eraserAdd);
        btn_eraserSub = findViewById(R.id.btn_eraserSub);
        btn_colorButton = findViewById(R.id.btn_colorButton);
        btn_chooseBackground = findViewById(R.id.btn_chooseBackgroudn);
        btn_withdraw = findViewById(R.id.btn_withdraw);
        btn_author = findViewById(R.id.btn_author);
        //注册监听
        btn_drawLine.setOnClickListener(this);
        btn_colorButton.setOnClickListener(this);
        btn_eraserAdd.setOnClickListener(this);
        btn_eraserSub.setOnClickListener(this);
        btn_handAdd.setOnClickListener(this);
        btn_handSub.setOnClickListener(this);
        btn_drawRect.setOnClickListener(this);
        btn_drawCircle.setOnClickListener(this);
        btn_handWrite.setOnClickListener(this);
        btn_eraser.setOnClickListener(this);
        btn_clearScreen.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_chooseBackground.setOnClickListener(this);
        btn_withdraw.setOnClickListener(this);
        btn_author.setOnClickListener(this);
        // 获取创建的宽度和高度
        LinearLayout ll_canvas = findViewById(R.id.ll_canvas);
        myView = new MyView(this, getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());
        myView.setMode(HandWritePen);
        ll_canvas.addView(myView);
        myView.requestFocus();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_drawLine:
                myView.setMode(DrawLine);
                break;
            case R.id.btn_drawRect:
                myView.setMode(DrawRectPen);
                break;
            case R.id.btn_drawCircle:
                myView.setMode(DrawCirclePen);
                break;
            case R.id.btn_handWrite:
                myView.setMode(HandWritePen);
                break;
            case R.id.btn_eraser:
                myView.setMode(Eraser);
                break;
            case R.id.btn_clearScreen:
                myView.isDrawPutDown=true;
                myView.clearScreen();
                break;
            case R.id.btn_save:
                myView.save();
                break;
            case R.id.btn_colorButton:
                Intent colorChooseActivity = new Intent(this, ColorChooseActivity.class);
                startActivity(colorChooseActivity);
                break;
            case R.id.btn_handAdd:
                if (StrokeWidth < 3) {
                    StrokeWidth += 3;
                    break;
                }
                myView.paint.setStrokeWidth(StrokeWidth += 3);
                btn_handAdd.setText("线粗:" + StrokeWidth);
                btn_handSub.setText("线细:");
                break;

            case R.id.btn_handSub:
                if (StrokeWidth < 3) {
                    StrokeWidth += 3;
                    break;
                }
                myView.paint.setStrokeWidth(StrokeWidth -= 3);
                btn_handAdd.setText("线粗:");
                btn_handSub.setText("线细:" + StrokeWidth);
                break;
            case R.id.btn_eraserAdd:
                if (eraserStrokeWidth < 3) {
                    eraserStrokeWidth += 3;
                    break;
                }
                myView.eraserPaint.setStrokeWidth(eraserStrokeWidth += 3);
                btn_eraserAdd.setText("擦增:" + eraserStrokeWidth);
                btn_eraserSub.setText("擦减:");
                break;

            case R.id.btn_eraserSub:
                if (eraserStrokeWidth < 3) {
                    eraserStrokeWidth += 3;
                    break;
                }
                myView.eraserPaint.setStrokeWidth(eraserStrokeWidth -= 3);
                btn_eraserAdd.setText("擦增:");
                btn_eraserSub.setText("擦减:" + eraserStrokeWidth);
                break;
            case R.id.btn_chooseBackgroudn:
                myView.isDrawPutDown = true;
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 101);
                break;
            case R.id.btn_open:
                myView.isDrawPutDown = true;
                Intent openHistoriesActivity = new Intent(this, OpenHistoriesActivity.class);
                startActivityForResult(openHistoriesActivity, 102);
                break;
            case R.id.btn_withdraw:
                myView.isDrawPutDown = true;
                myView.withdraw();
                break;
            case  R.id.btn_author:
                Intent aboutAuthor = new Intent();
                aboutAuthor.setClass(this,AboutAuthorActivity.class);
                startActivity(aboutAuthor);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 101:
                try {
                    MyView.map = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    myView.chooseBackground();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 102:
                myView.open();
                break;
        }
    }
}


