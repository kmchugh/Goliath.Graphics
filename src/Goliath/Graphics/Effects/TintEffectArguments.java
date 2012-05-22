/* ========================================================
 * TintEffectArguments.java
 *
 * Author:      kenmchugh
 * Created:     Dec 16, 2010, 1:12:15 PM
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

import java.awt.Color;


        
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
public class TintEffectArguments extends EffectArguments
{
    /**
     * Creates a new instance of ScaleEffectArguments
     */
    public TintEffectArguments(Color toTint)
    {
        this(toTint, 1);
    }

    /**
     * Creates a new instance of ScaleEffectArguments
     */
    public TintEffectArguments(Color toTint, float tnStrength)
    {
        setTint(toTint);
        setStrength(tnStrength);
    }

    public final void setTint(Color toTint)
    {
        setParameter("tintColour", toTint);
    }

    public Color getTint()
    {
        return (Color)getParameter("tintColour");
    }

    public final void setStrength(float tnStrength)
    {
        setParameter("tintStrength", Math.max(Math.min(tnStrength, 1), 0));
    }

    public float getStrength()
    {
        return ((Float)(Goliath.Utilities.isNull(getParameter("tintStrength"), 0f))).floatValue();
    }
}