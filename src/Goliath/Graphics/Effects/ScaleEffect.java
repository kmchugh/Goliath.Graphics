/* ========================================================
 * ScaleEffect.java
 *
 * Author:      kmchugh
 * Created:     Nov 3, 2010, 2:20:26 PM
 *
 * Description
 * --------------------------------------------------------
 * General Class Description.
 *
 * Change Log
 * --------------------------------------------------------
 * Init.Date        Ref.            Description
 * --------------------------------------------------------
 *
 * ===================================================== */

package Goliath.Graphics.Effects;

import Goliath.Graphics.Utilities;
        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Nov 3, 2010
 * @author      kmchugh
**/
public class ScaleEffect extends Effect<ScaleEffectArguments>
{

    /**
     * Creates a new instance of ScaleEffect
     */
    public ScaleEffect()
    {
    }

    @Override
    protected int[] onProcess(int[] taSource, int tnWidth, int tnHeight , ScaleEffectArguments toArgs)
    {
        // If the scale 9 grid is all zeros then this is a simple scale transform
        int lnGridTop = toArgs.getGridTop();
        int lnGridRight = toArgs.getGridRight();
        int lnGridBottom = toArgs.getGridBottom();
        int lnGridLeft = toArgs.getGridLeft();
        int lnNewHeight = (int)Math.max(toArgs.getHeight(), 1);
        int lnNewWidth = (int)Math.max(toArgs.getWidth(), 1);


        // Quick check for invalid sizes or no processing needed
        if (tnWidth == lnNewWidth && tnHeight == lnNewHeight)
        {
            // If the same size, then no scaling required
            return taSource;
        }
        else if (lnNewWidth <= 0 || lnNewHeight <= 0)
        {
            // An invalid or zero size, so return an empty array
            return new int[0];
        }


        if (lnGridBottom == 0 && lnGridLeft == 0 
                && lnGridRight == 0 && lnGridTop == 0)
        {
            // This is just a simple transform
            return scaleImage(taSource, tnWidth, tnHeight, lnNewWidth, lnNewHeight, toArgs.getAllowMultipass());
        }
        else
        {
            // This is a scale9 transform
            return scale9Image(taSource, tnWidth, tnHeight, lnNewWidth, lnNewHeight, lnGridTop, lnGridRight, lnGridBottom, lnGridLeft, toArgs.getAllowMultipass());
        }
    }
    
    
    /**
     * Performs a scale 9 type scaling on the image
     * @param toSource the image to scale
     * @param tnNewWidth the new width
     * @param tnNewHeight the new height
     * @param tnTop the height of the top row in the scale 9 grid
     * @param tnRight the width of the right column in the scale 9 grid
     * @param tnBottom the height of the bootom row in the scale 9 grid
     * @param tnLeft the width of the left column in the scale 9 grid
     * @return the new scaled image
     */
    private int[] scale9Image(int[] taSource, int tnOriginalWidth, int tnOriginalHeight, int tnNewWidth, int tnNewHeight,
            int tnTop, int tnRight, int tnBottom, int tnLeft, boolean tlAllowMultipass)
    {
        // If the sizes are not the same, we need to create a new array
        int[] laReturn = new int[tnNewWidth * tnNewHeight];
        
        // First copy in the four corners

        // Top Left
        Utilities.copyPixelData(taSource, tnOriginalWidth, 0, 0, Math.min(tnLeft, tnNewWidth), Math.min(tnTop, tnNewHeight),
                laReturn, tnNewWidth, 0, 0);

        // Top Right
        Utilities.copyPixelData(taSource, tnOriginalWidth, tnOriginalWidth - tnRight, 0, Math.min(tnRight, tnNewWidth), Math.min(tnTop, tnNewHeight),
                laReturn, tnNewWidth, tnNewWidth - tnRight, 0);

        // Bottom Left
        Utilities.copyPixelData(taSource, tnOriginalWidth, 0, tnOriginalHeight - tnBottom, Math.min(tnLeft, tnNewWidth), Math.min(tnBottom, tnNewHeight),
                laReturn, tnNewWidth, 0, tnNewHeight - tnBottom);

        // Bottom Right
        Utilities.copyPixelData(taSource, tnOriginalWidth, tnOriginalWidth - tnRight, tnOriginalHeight - tnBottom, Math.min(tnRight, tnNewWidth), Math.min(tnBottom, tnNewHeight),
                laReturn, tnNewWidth, tnNewWidth - tnRight, tnNewHeight - tnBottom);

        // Left side
        int lnScaledWidth = tnLeft;
        int lnScaledHeight = tnNewHeight - tnTop - tnBottom;
        int[] laPixelData = scaleImage(
                Utilities.getPixelData(taSource, tnOriginalWidth, 0, tnTop, lnScaledWidth, tnOriginalHeight - tnTop - tnBottom),
                lnScaledWidth,
                tnOriginalHeight - tnTop - tnBottom,
                lnScaledWidth,
                lnScaledHeight,
                tlAllowMultipass);

        Utilities.copyPixelData(laPixelData, lnScaledWidth, 0, 0, Math.min(lnScaledWidth, tnNewWidth), Math.min(lnScaledHeight, tnNewHeight),
                laReturn, tnNewWidth, 0, tnTop);

        // Right side
        lnScaledWidth = tnRight;
        lnScaledHeight = tnNewHeight - tnTop - tnBottom;
        laPixelData = scaleImage(
                Utilities.getPixelData(taSource, tnOriginalWidth, tnOriginalWidth - tnRight, tnTop, lnScaledWidth, tnOriginalHeight - tnTop - tnBottom),
                lnScaledWidth,
                tnOriginalHeight - tnTop - tnBottom,
                lnScaledWidth,
                lnScaledHeight,
                tlAllowMultipass);

        Utilities.copyPixelData(laPixelData, lnScaledWidth, 0, 0, Math.min(lnScaledWidth, tnNewWidth), Math.min(lnScaledHeight, tnNewHeight),
                laReturn, tnNewWidth, tnNewWidth - tnRight, tnTop);

        // Top side
        lnScaledWidth = tnNewWidth - tnLeft - tnRight;
        lnScaledHeight = tnTop;
        laPixelData = scaleImage(
                Utilities.getPixelData(taSource, tnOriginalWidth, tnLeft, 0, tnOriginalWidth - tnLeft - tnRight, lnScaledHeight),
                tnOriginalWidth - tnLeft - tnRight,
                lnScaledHeight,
                lnScaledWidth,
                lnScaledHeight,
                tlAllowMultipass);

        Utilities.copyPixelData(laPixelData, lnScaledWidth, 0, 0, Math.min(lnScaledWidth, tnNewWidth), Math.min(lnScaledHeight, tnNewHeight),
                laReturn, tnNewWidth, tnLeft, 0);

        // Bottom side
        lnScaledWidth = tnNewWidth - tnLeft - tnRight;
        lnScaledHeight = tnBottom;
        laPixelData = scaleImage(
                Utilities.getPixelData(taSource, tnOriginalWidth, tnLeft, tnOriginalHeight - tnBottom, tnOriginalWidth - tnLeft - tnRight, lnScaledHeight),
                tnOriginalWidth - tnLeft - tnRight,
                lnScaledHeight,
                lnScaledWidth,
                lnScaledHeight,
                tlAllowMultipass);

        Utilities.copyPixelData(laPixelData, lnScaledWidth, 0, 0, Math.min(lnScaledWidth, tnNewWidth), Math.min(lnScaledHeight, tnNewHeight),
                laReturn, tnNewWidth, tnLeft, tnNewHeight - tnBottom);

        // Content area
        lnScaledWidth = tnNewWidth - tnLeft - tnRight;
        lnScaledHeight = tnNewHeight - tnTop - tnBottom;
        laPixelData = scaleImage(
                Utilities.getPixelData(taSource, tnOriginalWidth, tnLeft, tnTop, tnOriginalWidth - tnLeft - tnRight, tnOriginalHeight - tnTop - tnBottom),
                tnOriginalWidth - tnLeft - tnRight,
                tnOriginalHeight - tnTop - tnBottom,
                lnScaledWidth,
                lnScaledHeight,
                tlAllowMultipass);

        Utilities.copyPixelData(laPixelData, lnScaledWidth, 0, 0, Math.min(lnScaledWidth, tnNewWidth), Math.min(lnScaledHeight, tnNewHeight),
                laReturn, tnNewWidth, tnLeft, tnTop);

        return laReturn;
    }
    
    /**
     * Scales the specified image, if multipass is true this will scale the image by factors of 2 until the required size is reached
     * @param taSource the source pixel data
     * @param tnOriginalWidth the original width
     * @param tnOriginalHeight the original height
     * @param tnNewWidth the desired width
     * @param tnNewHeight the desired height
     * @param tlMultiPass true to multipass process this image
     * @return the modified image data
     */
    private int[] scaleImage(int[] taSource, int tnOriginalWidth, int tnOriginalHeight,  int tnNewWidth, int tnNewHeight, boolean tlMultiPass)
    {
        // Check if we can do a multipass (slower)
        tlMultiPass = tlMultiPass && getMultipass(tnOriginalWidth, tnOriginalHeight, tnNewWidth, tnNewHeight);

        // If the sizes are the same, just return the original
        if (tnOriginalWidth == tnNewWidth && tnOriginalHeight == tnNewHeight)
        {
            return taSource;
        }

        // If the new width or height <= 0, just return an empty image
        if (tnNewWidth <= 0 || tnNewHeight <= 0)
        {
            return new int[0];
        }

        int lnWidth = tlMultiPass ? tnOriginalWidth : tnNewWidth;
        int lnHeight = tlMultiPass ? tnOriginalHeight : tnNewHeight;

        // Storage for looped values if doing multipass
        int lnPreviousWidth = tnOriginalWidth;
        int lnPreviousHeight = tnOriginalHeight;

        
        do
        {
            
            // to multipass, we double the size with each pass until reaching the desired size
            if (tlMultiPass)
            {
                lnWidth = (lnWidth > tnNewWidth) ?
                    Math.max(lnWidth/2, tnNewWidth) :
                    (lnWidth < tnNewWidth) ?
                        Math.min(lnWidth*2, tnNewWidth) :
                        lnWidth;

                lnHeight = (lnHeight > tnNewHeight) ?
                    Math.max(lnHeight/2, tnNewHeight) :
                    (lnHeight < tnNewHeight) ?
                        Math.min(lnHeight*2, tnNewHeight) :
                        lnHeight;
            }
            
            int[] laProcessing = new int[lnWidth * lnHeight];

            // If this is a scale on only one axis, or if this is too small to use bilinear, use nearest Neighbour
            if (tnOriginalHeight == tnNewHeight || tnOriginalWidth == tnNewWidth || taSource.length < 4)
            {
                nearestNeighbour(taSource, laProcessing, lnPreviousWidth, lnPreviousHeight, lnWidth, lnHeight);
            }
            else
            {
                bilinear(taSource, laProcessing, lnPreviousWidth, lnPreviousHeight, lnWidth, lnHeight);
            }

            // Store the pixels for a second pass or return
            taSource = laProcessing;

            // Update previous values for next pass
            lnPreviousWidth = lnWidth;
            lnPreviousHeight = lnHeight;

        } while (lnWidth != tnNewWidth || lnHeight != tnNewHeight);

        return taSource;
    }

    /**
     * Implements the nearest neighbour algorithm when scaling.  This takes the x and y position and finds the pixel data
     * at the relative x and y position in the source image.
     * @param taSource the source pixel data
     * @param taDest the destination pixel data
     * @param tnWidth the width of the source image
     * @param tnHeight the height of the source image
     * @param tnNewWidth the new height
     * @param tnNewHeight the new width
     */
    private void nearestNeighbour(int[] taSource, int[] taDest, int tnWidth, int tnHeight, int tnNewWidth, int tnNewHeight)
    {
        int lnPrecision = 1000000;
        float lnWidthRatio = ((int)(((tnWidth)/(float)tnNewWidth) * lnPrecision) / (float)lnPrecision);
        float lnHeightRatio = ((int)(((tnHeight)/(float)tnNewHeight) * lnPrecision) / (float)lnPrecision);
        
        // Loop through the destination array
        for (int lnIndex = 0; lnIndex < taDest.length; lnIndex++)
        {
            // index = newX + newY;
            // The X coordinates are determined by : (int)Math.ceil(((lnIndex % tnNewWidth) + 1) * lnWidthRatio)
            // The Y coordinates are determined by : ((int)((int)(lnIndex / (float)tnNewWidth) * lnHeightRatio) * tnWidth)
            // The end result is simply the x and y added together, subtracting one because the array is zero based
            taDest[lnIndex] = taSource[((int)Math.ceil(((lnIndex % tnNewWidth) + 1) * lnWidthRatio) + ((int)((int)(lnIndex / (float)tnNewWidth) * lnHeightRatio) * tnWidth)) -1];
        }
    }

    /**
     * Bilinear scaling picks up the surrounding 4 pixels from the destination pixel, and averages the colour giving a much 
     * smoother scaling than nearest neighbour, but at the cost of cpu cycles
     * @param taSource the source pixel data
     * @param taDest the destination pixel data
     * @param tnWidth the width of the source image
     * @param tnHeight the height of the source image
     * @param tnNewWidth the new height
     * @param tnNewHeight the new width
     */
    private void bilinear(int[] taSource, int[] taDest, int tnWidth, int tnHeight, int tnNewWidth, int tnNewHeight)
    {
        
        int lnPrecision = 100000;
        float lnWidthRatio = ((int)(((tnWidth)/(float)tnNewWidth) * lnPrecision) / (float)lnPrecision);
        float lnHeightRatio = ((int)(((tnHeight)/(float)tnNewHeight) * lnPrecision) / (float)lnPrecision);
        
        float lnX = 0, lnY = 0;
        
        int[] laSourcePixels = new int[4];
        float lnRed, lnGreen, lnBlue, lnAlpha;
        float lnYDiff, lnXDiff;
        int lnHalfWidth = tnWidth /2;
        int lnHalfHeight = tnHeight /2;
        int lnSourceIndex = 0;
        int lnYOffset = 0;
        int lnLength = taDest.length;
        
        for (int lnIndex = 0; lnIndex < lnLength; lnIndex++)
        {
            try
            {
                lnX = ((int)((((lnIndex % tnNewWidth -1) + 1) * lnWidthRatio)   * lnPrecision) / (float)lnPrecision);
                lnY = ((int)(((int)(lnIndex / (float)tnNewWidth) * lnHeightRatio)   * lnPrecision) / (float)lnPrecision);
                lnYOffset =  (int)lnY * tnWidth;

                lnXDiff = ((int)((lnX - (int)lnX)   * lnPrecision) / (float)lnPrecision);
                //lnYDiff = ((int)((((lnIndex / tnNewWidth) * lnHeightRatio) - lnY) * lnPrecision) / (float)lnPrecision);
                lnYDiff = ((int)((lnY - (int)lnY)   * lnPrecision) / (float)lnPrecision);

                lnSourceIndex = (int)lnX + lnYOffset;


                // Get the four surrounding pixels
                // Using an if statement as otherwise we would need to compare for each of the 4 pixels, while it looks bulky
                // it ends up being faster than using ternary
                if (lnX < lnHalfWidth)
                {
                    if (lnY < lnHalfHeight)
                    {
                        // Top Left
                        laSourcePixels[0] = taSource[lnSourceIndex];
                        laSourcePixels[1] = taSource[lnSourceIndex + 1];
                        laSourcePixels[2] = taSource[lnSourceIndex + tnWidth];
                        laSourcePixels[3] = taSource[lnSourceIndex + tnWidth + 1];
                    }
                    else
                    {
                        // Bottom Left
                        laSourcePixels[0] = taSource[lnSourceIndex - tnWidth];
                        laSourcePixels[1] = taSource[lnSourceIndex - tnWidth + 1];
                        laSourcePixels[2] = taSource[lnSourceIndex];
                        laSourcePixels[3] = taSource[lnSourceIndex + 1];                      
                    }
                }
                else
                {
                    if (lnY < lnHalfHeight)
                    {
                        // Top Right
                        laSourcePixels[0] = taSource[lnSourceIndex - 1];
                        laSourcePixels[1] = taSource[lnSourceIndex];
                        laSourcePixels[2] = taSource[lnSourceIndex + tnWidth - 1];
                        laSourcePixels[3] = taSource[lnSourceIndex + tnWidth];
                    }
                    else
                    {
                        // Bottom right
                        laSourcePixels[0] = taSource[lnSourceIndex - tnWidth - 1];
                        laSourcePixels[1] = taSource[lnSourceIndex - tnWidth];
                        laSourcePixels[2] = taSource[lnSourceIndex - 1];
                        laSourcePixels[3] = taSource[lnSourceIndex];
                    }
                }



                // Calculate the colour difference, and place the pixels
                lnBlue = (int)((((laSourcePixels[0]) & 0xFF) * (1-lnXDiff)  * (1-lnYDiff)) +
                                (((laSourcePixels[1]) & 0xFF) * lnXDiff  * (1-lnYDiff)) +
                                (((laSourcePixels[2]) & 0xFF) * (1-lnXDiff)  * lnYDiff)+
                                (((laSourcePixels[3]) & 0xFF) * lnXDiff * lnYDiff));

                lnGreen = (int)((((laSourcePixels[0] >> 8) & 0xFF) * (1-lnXDiff)  * (1-lnYDiff)) +
                        (((laSourcePixels[1] >> 8) & 0xFF) * lnXDiff  * (1-lnYDiff)) +
                        (((laSourcePixels[2] >> 8) & 0xFF) * (1-lnXDiff)  * lnYDiff)+
                        (((laSourcePixels[3] >> 8) & 0xFF) * lnXDiff * lnYDiff));

                lnRed = (int)((((laSourcePixels[0] >> 16) & 0xFF) * (1-lnXDiff)  * (1-lnYDiff)) +
                        (((laSourcePixels[1] >> 16) & 0xFF) * lnXDiff  * (1-lnYDiff)) +
                        (((laSourcePixels[2] >> 16) & 0xFF) * (1-lnXDiff)  * lnYDiff)+
                        (((laSourcePixels[3] >> 16) & 0xFF) * lnXDiff * lnYDiff));

                lnAlpha = (int)((((laSourcePixels[0] >> 24) & 0xFF) * (1-lnXDiff)  * (1-lnYDiff)) +
                        (((laSourcePixels[1] >> 24) & 0xFF) * lnXDiff  * (1-lnYDiff)) +
                        (((laSourcePixels[2] >> 24) & 0xFF) * (1-lnXDiff)  * lnYDiff)+
                        (((laSourcePixels[3] >> 24) & 0xFF) * lnXDiff * lnYDiff));

                taDest[lnIndex] = ((int)lnAlpha <<24) |
                                        (int)lnRed << 16 |
                                        (int)lnGreen << 8 |
                                        (int)lnBlue;
            }
            catch (Throwable ex)
            {
                int i=0;
            }
        }
    }

    /**
     * Checks if this scaling will allow a multipass
     * @param tnOriginalWidth
     * @param tnOriginalHeight
     * @param tnNewWidth
     * @param tnNewHeight
     * @return
     */
    private boolean getMultipass(int tnOriginalWidth, int tnOriginalHeight, float tnNewWidth, float tnNewHeight)
    {
        return (tnNewWidth > 100 && tnNewHeight > 100 && Math.abs(tnNewWidth - tnOriginalWidth) > tnOriginalWidth && Math.abs(tnNewHeight - tnOriginalHeight) > tnOriginalHeight);
    }
}
