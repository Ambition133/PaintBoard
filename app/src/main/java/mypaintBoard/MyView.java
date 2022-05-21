package mypaintBoard;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
public class MyView extends View {
    public String  mMode = "";
    private float startX, startY, endX, endY;//用来存手指移动后的坐标
    private float mX, mY;
    public static Bitmap map;//对图像的一些操作
    public Canvas canvas;
    public Paint paint;//画笔
    public Paint eraserPaint; //橡皮
    int screenWidth, screenHeight;
    Path mPath;
    private float radius;
    Context context;
    public String openPath;
    int autoSaveIndex,saveIndex;
    boolean isDrawPutDown = false;
    public MyView(Context context, int screenWidth, int screenHeight) {
        super(context);
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        paint = new Paint();
        //设置颜色
        paint.setStyle(Paint.Style.STROKE);//设置画笔结尾时的样式为圆润
        //设置笔触宽度
        paint.setStrokeWidth(10);
        //空心为Paint. style. STROKE，实心为Paint. Style.EIL
        paint.setColor(Color.BLACK);
        //橡皮擦
        eraserPaint = new Paint();
        eraserPaint.setAlpha(0);
        eraserPaint.setStyle(Paint.Style.STROKE);
        eraserPaint.setStrokeWidth(10);
        eraserPaint.setColor(Color.WHITE);
        mPath = new Path();
        map = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);//绘制一个与手机屏幕大小的BitMap对象
        canvas = new Canvas(map);
        canvas.drawColor(Color.WHITE);
        autoSave();
    }
    //设置绘制模式
    public void setMode(String mode) {
        this.mMode = mode;
    }
    @Override
    public void onDraw(Canvas canvas ) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (!isDrawPutDown)//放在这里最后一步可撤回并没有立马显示
            canvas.drawBitmap(map, 0, 0, paint);//从 0 0开始到map中的width，height的一块画布，也就是全屏幕的一块画布
        switch (mMode) {
            case MainActivity.DrawLine:
                canvas.drawLine(startX, startY, endX, endY, paint);
                break;
            case MainActivity.DrawRectPen:
                canvas.drawRect(startX, startY, endX, endY, paint);
                break;
            case MainActivity.DrawCirclePen:
                canvas.drawCircle(startX, startY, radius, paint);
                break;
            case MainActivity.HandWritePen:
                canvas.drawPath(mPath, paint);
                break;
        }
        if (isDrawPutDown) {//放在这里最后一步可撤回并立马显示
            canvas.drawBitmap(map, 0, 0, paint);//从 0 0开始到map中的width，height的一块画布，也就是全屏幕的一块画布
            isDrawPutDown =false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        paint.setColor(MainActivity.chooseColor);
        //获得手指放下的坐标
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (mMode){
            case MainActivity.DrawLine:
                drawLine(x,y,action);
                break;
            case MainActivity.DrawRectPen:
                drawRect(x, y, action);
                break;
            case MainActivity.DrawCirclePen:
                drawCircle(x, y, action);
                break;
            case MainActivity.HandWritePen:
                handWrite(x, y, action);
                break;
            case MainActivity.Eraser:
                eraser(x, y, action);
                break;
        }
        return true;
    }
    private void drawLine(float x, float y, int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                endX = x;
                endY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawLine(startX, startY, endX, endY, paint);
                autoSave();
                break;
        }
    }
    private void drawRect(float x, float y, int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                endX = x;
                endY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawRect(startX, startY, endX, endY, paint);
                autoSave();
                break;
        }
    }
    private void drawCircle(float x, float y, int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                endX = x;
                endY = y;
                radius = (float) Math.sqrt((endX - startX) * (endX - startX) + (endY - startY) * (endY - startY));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawCircle(startX, startY, radius, paint);
                autoSave();
                break;
        }
    }
    public void handWrite(float x, float y, int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(mX, mY);
                mX = x;
                mY = y;
                canvas.drawPath(mPath,paint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                autoSave();
                break;
        }
    }
    public void eraser(float x, float y, int action) {
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(mX, mY);
                mX = x;
                mY = y;
                canvas.drawPath(mPath, eraserPaint);
                invalidate();
                break;
        }
    }
    public void clearScreen(){
        openPath = Environment.getExternalStorageDirectory().getPath() + "/绘图程序APP/1.png";
        Bitmap tempMap= BitmapFactory.decodeFile(openPath);
        canvas.drawBitmap( tempMap,0,0,paint);
        invalidate();
    }
    public void autoSave(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    autoSaveIndex++;
                    Bitmap tempMap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(tempMap);//用新的位图来创建新的画布
                    canvas.drawBitmap(map, 0, 0, null);
                    // 保存图片到SD卡上
                    File file = new File(Environment.getExternalStorageDirectory().getPath()+ "/绘图程序APP");
                    if (!file.exists())
                        file.mkdirs();
                    FileOutputStream fos= new FileOutputStream(file.getPath()+"/"+ autoSaveIndex +".png");
                    tempMap.compress(Bitmap.CompressFormat.PNG, 100,fos);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void save() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                    try {
                        // 保存图片到SD卡上
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+ "/绘图程序APP/绘图保存");
                        if (!file.exists())
                            file.mkdirs();
                        String[] list = file.list();
                        saveIndex = list.length+1;
                        FileOutputStream fos = new FileOutputStream(file.getPath()+"/保存记录"+saveIndex+".png");
                        map.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.close();
                        Looper.prepare();
                        Toast.makeText(context, "保存绘图成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } catch (Exception e) {
                        Looper.prepare();
                        Toast.makeText(context, "保存绘图失败", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                        e.printStackTrace();
                    }
                }
        }.start();
    }
    public void open (){
        Bitmap tempMap =map;
        map=Bitmap.createBitmap(screenWidth, screenHeight,Bitmap.Config.ARGB_8888);//绘制一个与手机屏幕大小的BitMap对象
        canvas = new Canvas(map);//用新的位图来创建新的画布
        canvas.drawBitmap(tempMap,0,0,paint);
        invalidate();
        autoSave();
    }
    public void chooseBackground( ){
        Bitmap tempMap=map;
        map=Bitmap.createBitmap(screenWidth, screenHeight,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(map);//用新的位图来创建新的画布
        canvas.drawBitmap(tempMap,0,0,paint);
        invalidate();
        autoSave();
    }
    public void withdraw(){
        if (autoSaveIndex >1)
            autoSaveIndex--;
        else
            return;
        openPath = Environment.getExternalStorageDirectory().getPath() + "/绘图程序APP/"+ autoSaveIndex +".png";
        Bitmap tempMap= BitmapFactory.decodeFile(openPath);
        canvas.drawBitmap( tempMap,0,0,paint);
        invalidate();
    }
}
