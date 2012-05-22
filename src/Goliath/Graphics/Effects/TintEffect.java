/* ========================================================
 * TintEffect.java
 *
 * Author:      kenmchugh
 * Created:     Dec 16, 2010, 1:10:54 PM
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

import Goliath.Collections.HashTable;
import java.awt.Color;
import java.awt.image.BufferedImage;


        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Dec 16, 2010
 * @author      kenmchugh
**/
public class TintEffect extends Effect<TintEffectArguments>
{
    /**
     * Creates a new instance of ScaleEffect
     */
    public TintEffect()
    {
    }

    @Override
    protected int[] onProcess(int[] taSource, int tnOriginalWidth, int tnOriginalHeight, TintEffectArguments toArgs)
    {
        // TODO: This can be optimised much further now that we are operating directly over the pixel data
        Color loTint = toArgs.getTint();
        if (loTint != null)
        {
            float lnStrength = toArgs.getStrength();
            int lnMixRed = (int)(loTint.getRed() * lnStrength);
            int lnMixGreen = (int)(loTint.getGreen() * lnStrength);
            int lnMixBlue = (int)(loTint.getBlue() * lnStrength);

            float lnAlphaFactor = 1.0f - lnStrength;

            int[] laCachedRed = new int[256];
            int[] laCachedGreen = new int[256];
            int[] laCachedBlue = new int[256];

            for (int i=0; i<256; i++)
            {
                int lnValue = (int) (i * lnAlphaFactor);
                laCachedRed[i] = lnValue + lnMixRed;
                laCachedGreen[i] = lnValue + lnMixGreen;
                laCachedBlue[i] = lnValue + lnMixBlue;
            }


            // Get an array of pixels
            for (int i=0; i < taSource.length; i++)
            {
                int lnPixel = taSource[i];
                taSource[i] = (lnPixel & 0xFF000000) |
                        laCachedRed[(lnPixel >> 16) & 0xFF] << 16 |
                        laCachedGreen[(lnPixel >> 8) & 0xFF] << 8 |
                        laCachedBlue[lnPixel & 0xFF];

            }
        }
        return taSource;
    }
}