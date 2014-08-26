/*
 * Copyright (C) 2014 Saravan Pantham
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.psaravan.flexiimageview.lib.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.psaravan.flexiimageview.lib.Shapes.ShapeHelper;

/**
 * View class for FlexiImageView
 *
 * @author Saravan Pantham
 */
public class FlexiImageView extends ImageView {

    //Context.
    private Context mContext;

    //Feature flags.
    private int mShape = -1;
    private boolean mBlur = false;
    private boolean mBorder = false;
    private boolean mMultiImage = false;
    private boolean mDropShadow = false;

    //Shape constants.
    public static final int SHAPE_RECTANGLE = 0;
    public static final int SHAPE_EQUILATERAL_TRIANGLE = 1;
    public static final int SHAPE_CIRCLE = 2;
    public static final int SHAPE_OVAL = 3;

    //Shape parameters.
    private float mShapeCornerRadiusFactor = 0;
    private float mOvalVerticalRadiusFactor = 0;
    private float mOvalHorizontalRadiusFactor = 0;

    public FlexiImageView(Context context) {
        super(context);
        mContext = context;

    }

    public FlexiImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;

    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {

        //Check to make sure a valid bitmap was passed in.
        if (bitmap==null || bitmap.getWidth() <= 0 || bitmap.getHeight() <=0) {
            super.setImageBitmap(null);
            return;
        }

        applyAndSetBitmapEffects(bitmap);

    }

    /**
     * Determines which effects have been enabled by the user, applies those effects to the
     * specified bitmap, and then sets it to the View container.
     *
     * @param originalBitmap The original bitmap object that is passed in through the default
     *                       {@link #setImageBitmap(android.graphics.Bitmap)} method.
     *
     */
    private void applyAndSetBitmapEffects(Bitmap originalBitmap) {

        //Create a new Bitmap object to apply the transformations to.
        Bitmap transformedBitmap = Bitmap.createBitmap(originalBitmap);

        //Apply the correct shape transformation to the bitmap.
        if (mShape > -1) {
            ShapeHelper shapeHelper = new ShapeHelper(mContext);
            transformedBitmap = shapeHelper.applyShape(originalBitmap, mShape,
                                                       mShapeCornerRadiusFactor,
                                                       mOvalVerticalRadiusFactor,
                                                       mOvalHorizontalRadiusFactor);
        }

        //Apply the bitmap to the View.
        super.setImageBitmap(transformedBitmap);
    }

    /**
     * Sets the shape of the ImageView.
     *
     * @param shape Use one of the following shapes: {@link #SHAPE_RECTANGLE}, {@link #SHAPE_CIRCLE},
     *              {@link #SHAPE_EQUILATERAL_TRIANGLE}, {@link #SHAPE_OVAL}.
     *
     * @throws IllegalArgumentException Exception thrown if an invalid shape is passed in.
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setShape(int shape) throws IllegalArgumentException {
        if (shape > 3 || shape < 0)
            throw new IllegalArgumentException("Invalid shape passed in. Must use one of the " +
                                               "predefined, static shape constants.");

        mShape = shape;
        return this;
    }

    /**
     * Sets whether the ImageView's image should be blurred or not.
     *
     * @param blur Pass true to blur the ImageView's image. False otherwise.
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setBlur(boolean blur) {
        mBlur = blur;
        return this;
    }

    /**
     * Sets whether the ImageView should have a border drawn around it. Make sure you call
     * {@link #setBorderDrawable(Drawable drawable)} or this method will have no effect.
     *
     * @param border Pass true to draw a border around the ImageView. False otherwise.
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setBorder(boolean border) {
        mBorder = border;
        return this;
    }

    /**
     * Sets whether the ImageView will have multiple images drawn inside it. Make sure you call
     * {@link #setMultiImageBitmaps(android.graphics.Bitmap[])} or this method will have no effect.
     *
     * @param multiImage Pass true to enable a multi-image view (à la Facebook chat heads).
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setMultiImage(boolean multiImage) {
        mMultiImage = multiImage;
        return this;
    }

    /**
     * Sets whether the ImageView will have multiple images drawn inside it. Make sure you call
     * {@link #setMultiImageBitmaps(android.graphics.Bitmap[])} or this method will have no effect.
     *
     * @param dropShadow Pass true to show a drop shadow below the View.
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setDropShadow(boolean dropShadow) {
        mDropShadow = dropShadow;
        return this;
    }

    /**
     * Sets the drawable to use as the ImageView's border.
     *
     * @param drawable The drawable to use for the ImageView's border.
     * @throws java.lang.IllegalStateException Exception thrown if {@code setBorder(true)} is
     *                                         not called before calling this method.
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setBorderDrawable(Drawable drawable) {
        if (mBorder==false)
            throw new IllegalStateException("setBorder() was not called or was set to false! " +
                                            "Call setBorder(true) before calling this method.");


        return this;
    }

    /**
     * Sets the image bitmaps to display within the ImageView.
     *
     * @param bitmaps An array of bitmaps that will be displayed by this ImageView.
     * @throws java.lang.IllegalStateException Exception thrown if {@code setMultiImage(true)}
     *                                         is not called before calling this method.
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setMultiImageBitmaps(Bitmap[] bitmaps) {
        if (mMultiImage==false)
            throw new IllegalStateException("setMultiImage(true) was not called or was set to false! " +
                                            "Call setMultiImage(true) before calling this method.");


        return this;
    }

    /**
     * Sets the image bitmaps to display within the ImageView.
     *
     * @param radius The radius of the drop shadow to draw. Higher values will create a larger drop
     *               shadow, while lower values will result in a subtler look.
     * @throws java.lang.IllegalStateException Exception thrown if {@code setMultiImage(true)}
     *                                         is not called before calling this method.
     * @return Returns this instance to allow method chaining.
     */
    public FlexiImageView setDropShadowRadius(float radius) {
        if (mDropShadow==false)
            throw new IllegalStateException("setDropShadow(true) was not called or was set to false! " +
                    "Call setDropShadow(true) before calling this method.");


        return this;
    }

    /**
     * Sets the vertical radius of the oval shape. This method has no effect unless
     * {@link #setShape(int)} is called and has {@link #SHAPE_OVAL} passed in as a parameter.
     *
     * @param verticalRadiusFactor Used to calculate the curvature above and below the center.
     *                             Higher values will result in a larger curvature, while smaller
     *                             values will create a linear/sharp curve.
     *
     * @return This FlexiImageView instance to allow method chaining.
     */
    public FlexiImageView setOvalVerticalRadius(float verticalRadiusFactor) {
        if (getShape()==SHAPE_OVAL)
            mOvalVerticalRadiusFactor = verticalRadiusFactor;

        return this;
    }

    /**
     * Sets the horizontal radius of the oval shape. This method has no effect unless
     * {@link #setShape(int)} is called and has {@link #SHAPE_OVAL} passed in as a parameter.
     *
     * @param horizontalRadiusFactor Used to calculate the curvature to the sides of the center.
     *                               Higher values will result in a larger curvature, while smaller
     *                               values will create a linear/sharp curve.
     *
     * @return This FlexiImageView instance to allow method chaining.
     */
    public FlexiImageView setOvalHorizontalRadius(float horizontalRadiusFactor) {
        if (getShape()==SHAPE_OVAL)
            mOvalHorizontalRadiusFactor = horizontalRadiusFactor;

        return this;
    }


    /**
     * Public getter methods.
     */
    public float getOvalVerticalRadiusFactor() {
        return mOvalVerticalRadiusFactor;
    }

    public float getOvalHorizontalRadiusFactor() {
        return mOvalHorizontalRadiusFactor;
    }

    public int getShape() {
        return mShape;
    }

}
