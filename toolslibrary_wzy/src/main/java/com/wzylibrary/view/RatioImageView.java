package com.wzylibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wzylibrary.R;


/**
 * 比例缩放的图片
 * http://blog.csdn.net/Small_Lee/article/details/51026057
 */
public class RatioImageView extends ImageView {
    //宽高比，由我们自己设定
    private float ratio;
    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        //根据属性名称获取对应的值，属性名称的格式为类名_属性名
        ratio = typedArray.getFloat(R.styleable.RatioImageView_ratio, 1.8f); // 默认 宽/高=1.8
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取宽度的模式和尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        //修改为本项目配置的，宽固定为屏幕的1/3，32为图片的左右空隙
//        int widthSize = (ScreenUtils.getScreenWidth(this.getContext()) - DensityUtil.dp2px(this.getContext(), 32)) / 3;
//        int widthMode = MeasureSpec.EXACTLY;

        //获取高度的模式和尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //宽确定，高不确定
        if(widthMode == MeasureSpec.EXACTLY&&heightMode!= MeasureSpec.EXACTLY&&ratio!=0){
            heightSize = (int) (widthSize / ratio + 0.5f);//根据宽度和比例计算高度，四舍五入

        //宽不确定，高确定
        }else if(widthMode!= MeasureSpec.EXACTLY&&heightMode== MeasureSpec.EXACTLY&ratio!=0){
            widthSize = (int) (heightSize / ratio + 0.5f);

        //宽高都不确定
        }else{
            throw new RuntimeException("无法设定宽高比");
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        //必须调用下面的两个方法之一完成onMeasure方法的重写，否则会报错
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    /**
     * 设置宽高比
     * @param ratio
     */
    public void setRatio(float ratio){
        this.ratio = ratio;
    }
}