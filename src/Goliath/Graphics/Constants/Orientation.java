/* ========================================================
 * Orientation.java
 *
 * Author:      kmchugh
 * Created:     Feb 17, 2011, 1:01:27 PM
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


        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Feb 17, 2011
 * @author      kmchugh
**/
public class Orientation extends Goliath.DynamicEnum
{
    /**
     * Creates a new instance of a StringFormatType Object
     *
     * @param tcValue The value for the string format type
     * @throws Goliath.Exceptions.InvalidParameterException
     */
    protected Orientation(String tcValue)
    {
        super(tcValue);
    }

    private static Orientation g_oHorizontal;
    public static Orientation HORIZONTAL()
    {
        if (g_oHorizontal == null)
        {
            g_oHorizontal = createEnumeration(Orientation.class, "HORIZONTAL");
        }
        return g_oHorizontal;
    }

    private static Orientation g_oVertical;
    public static Orientation VERTICAL()
    {
        if (g_oVertical == null)
        {
            g_oVertical = createEnumeration(Orientation.class, "VERTICAL");
        }
        return g_oVertical;
    }
}