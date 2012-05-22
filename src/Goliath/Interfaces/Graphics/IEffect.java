/* ========================================================
 * IEffect.java
 *
 * Author:      kmchugh
 * Created:     Nov 3, 2010, 2:19:04 PM
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

package Goliath.Interfaces.Graphics;

import Goliath.Graphics.Effects.EffectArguments;
import java.awt.image.BufferedImage;



/**
 * Interface Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Nov 3, 2010
 * @author      kmchugh
**/
public interface IEffect<T extends EffectArguments>
{
    /**
     * Processes the image passed in and returns the image after processing.
     * This method may make modifications to the original and return the original, or it could
     * create a completely new BufferedImage
     * @param taSource the image pixels
     * @param tnWidth the number of columns in the pixel array
     * @param tnHeight the number of rows in the pixel array
     * @param toArguments the arguments for the effect
     * @return the image after the effect has been added
     */
    int[] process(int[] taSource, int tnWidth, int tnHeight, T toArguments);
}
