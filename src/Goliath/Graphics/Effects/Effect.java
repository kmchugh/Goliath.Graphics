/* ========================================================
 * Effect.java
 *
 * Author:      kmchugh
 * Created:     Nov 3, 2010, 2:18:16 PM
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

import Goliath.Interfaces.Graphics.IEffect;


        
/**
 * Defines an effect that is processed over a graphic
 *
 * @see         Related Class
 * @version     1.0 Nov 3, 2010
 * @author      kmchugh
**/
public abstract class Effect<T extends EffectArguments> extends Goliath.Object
        implements IEffect<T>
{
    /**
     * Creates a new instance of Effect
     */
    public Effect()
    {
    }

    @Override
    public final int[] process(int[] taSource, int tnWidth, int tnHeight, T toArgs)
    {
        return onProcess(taSource, tnWidth, tnHeight, toArgs);
    }

    /**
     * Hook method for allowing subclasses to participate in process.
     * @param taSource the image to process
     * @param toArgs the arguments for the processing
     * @return the new processesed image
     */
    protected abstract int[] onProcess(int[] taSource, int tnWidth, int tnHeight, T toArgs);
}
