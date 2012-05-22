/* ========================================================
 * Alignment.java
 *
 * Author:      kenmchugh
 * Created:     Mar 18, 2011, 12:20:44 PM
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

package Goliath.Graphics.Constants;

import Goliath.DynamicEnum;


        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Mar 18, 2011
 * @author      kenmchugh
**/
public class Alignment extends DynamicEnum
{
    private static Alignment g_oLeft;
    public static Alignment LEFT()
    {
        if (g_oLeft == null)
        {
            g_oLeft = createEnumeration(Alignment.class, "LEFT");
        }
        return g_oLeft;
    }

    private static Alignment g_oRight;
    public static Alignment RIGHT()
    {
        if (g_oRight == null)
        {
            g_oRight = createEnumeration(Alignment.class, "RIGHT");
        }
        return g_oRight;
    }

    private static Alignment g_oCenter;
    public static Alignment CENTER()
    {
        if (g_oCenter == null)
        {
            g_oCenter = createEnumeration(Alignment.class, "CENTER");
        }
        return g_oCenter;
    }


    protected Alignment(String tcValue)
    {
        super(tcValue);
    }
    
}
