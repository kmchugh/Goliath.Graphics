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

import Goliath.Graphics.Dimension;
import Goliath.Graphics.Image;
import Goliath.Graphics.Point;
import Goliath.Graphics.Utilities;

/**
 * Class Description.
 * Compositing effect based on Simple alpha blending described in
 *
 * http://en.wikipedia.org/wiki/Alpha_compositing
 *
 * to composit an image A over image B, on transparency value "alpha"
 * we do,
 *
 *  outPixel = PixelA * alpha + pixelB * (1-alpha)
 *
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Jul 11, 2011
 * @author      vinodhini
**/
public class CompositeEffect extends Effect<CompositeEffectArguments>
{

    /**
     * Creates a new instance of ScaleEffect
     */
    public CompositeEffect()
    {
    }

    @Override
    protected int[] onProcess(int[] taSource, int tnWidth, int tnHeight, CompositeEffectArguments toArgs)
    {
        float lnAlpha = toArgs.getAlpha();
    	Image  loBlendImage = toArgs.getImage();
        Dimension loBlendSize = loBlendImage.getSize();
        Point loPoint = toArgs.getOffset();
        
        int lnX = Goliath.Graphics.Utilities.toDevice(loPoint.getX());
        int lnY = Goliath.Graphics.Utilities.toDevice(loPoint.getY());
        
        // If the x or y is outside the image, then there is nothing to do
        if (lnX > tnWidth || 
            lnY > tnHeight ||
            lnX + loBlendSize.getWidth() < 0 || 
            lnY + loBlendSize.getHeight() < 0)
        {
            return taSource;
        }
        
        int lnXOffset = lnX > 0 ? lnX : -lnX;
        int lnYOffset = lnY > 0 ? lnY : -lnY;
        int lnBlendWidth = Math.min(tnWidth - lnXOffset, (int)loBlendSize.getWidth());
        int lnBlendHeight = Math.min(tnHeight - lnYOffset, (int)loBlendSize.getHeight());
        int lnBlue, lnGreen, lnRed, lnAlphaValue;
        
        int[] laBlendPixels = Utilities.getPixelData(loBlendImage.getImage(), 0, 0, lnBlendWidth, lnBlendHeight);
        for (int i=0; i<laBlendPixels.length; i++)
        {
            int lnPixelSource = ((i % lnBlendWidth) + lnXOffset) + 
                    (((int)(i / (float)lnBlendWidth) + lnYOffset) * tnWidth);
            
            // The algorithm for the over operation is as follows:
            // C0 = Ca Aa + Cb Ab ( 1 - Aa);
            // Where Ca and Cb are colour a and colour b respectively, and 
            // Aa and Ab, are alpha values for a and b respectively.
            // See: http://en.wikipedia.org/wiki/Alpha_compositing for more details
            // TODO: Implement other compositing types as represented in the wikipedia article
            
            int lnBgA = (taSource[lnPixelSource] >> 24) & 0xFF;
            int lnBgR = (taSource[lnPixelSource] >> 16) & 0xFF;
            int lnBgG = (taSource[lnPixelSource] >> 8) & 0xFF;
            int lnBgB = (taSource[lnPixelSource]) & 0xFF;
            
            float lnBlendA = (((laBlendPixels[i] >> 24) & 0xFF) / 255f) * lnAlpha;
            
            taSource[lnPixelSource] = (lnBgA | (laBlendPixels[i] >> 24) & 0xFF) << 24 | // alpha
                    (int)(lnBgR + ((((laBlendPixels[i] >> 16) & 0xFF) - lnBgR) * lnBlendA)) << 16 | // red
                    (int)(lnBgG + ((((laBlendPixels[i] >> 8) & 0xFF) - lnBgG) * lnBlendA)) << 8 | // green
                    (int)(lnBgB + ((((laBlendPixels[i]) & 0xFF) - lnBgB) * lnBlendA));  // blue
            

        }
        
        return taSource;
    }
}
