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
package com.psaravan.flexiimageview.lib.Shapes;

import android.content.Context;
import android.graphics.Bitmap;

import com.psaravan.flexiimageview.lib.View.FlexiImageView;

/**
 * Helper class that contains methods to apply shape transformations.
 *
 * @author Saravan Pantham
 */
public class ShapeHelper {

    private Context mContext;

    public ShapeHelper(Context context) {
        mContext = context;
    }

    /**
     * Applies the specified shape transformation on the input Bitmap and returns it.
     *
     * @param bitmap The bitmap to apply the shape transformation on.
     * @param shape The shape to apply.
     */
    public Bitmap applyShape(Bitmap bitmap, int shape, float cornerRadius,
                             float verticalRadius, float horizontalRadius) {

        //Apply the transformation based on the specified shape.
        switch (shape) {
            case FlexiImageView.SHAPE_CIRCLE:
                Circle circle = new Circle(mContext);
                return circle.applyShape(bitmap);
            case FlexiImageView.SHAPE_OVAL:
                Oval oval = new Oval(mContext);
                return oval.applyShape(bitmap, verticalRadius, horizontalRadius);
            case FlexiImageView.SHAPE_EQUILATERAL_TRIANGLE:
                Triangle triangle = new Triangle(mContext);
                return null;
            default:
                return bitmap;
        }

    }

}
