/* ========================================================
 * ImageType.java
 *
 * Author:      kenmchugh
 * Created:     Feb 15, 2011, 7:17:07 PM
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
 * @version     1.0 Feb 15, 2011
 * @author      kenmchugh
**/
public class ImageType extends DynamicEnum
{
    /**
     * Creates a new instance of ImageType
     */
    public ImageType(String tcType)
    {
        super(tcType);
    }

    private static ImageType g_oARGB;
    /**
     * The ARGB Image type, this means the image includes an alpha channel
     * @return The ARGB string format type
     */
    public static ImageType ARGB()
    {
        if (g_oARGB == null)
        {
            try
            {
                g_oARGB = new ImageType("ARGB");
            }
            catch (Goliath.Exceptions.InvalidParameterException ex)
            {}
        }
        return g_oARGB;
    }

    private static ImageType g_oRGB;
    /**
     * The RGB Image type, this means the image does not include an alpha channel
     * @return The RGB string format type
     */
    public static ImageType RGB()
    {
        if (g_oRGB == null)
        {
            try
            {
                g_oRGB = new ImageType("RGB");
            }
            catch (Goliath.Exceptions.InvalidParameterException ex)
            {}
        }
        return g_oRGB;
    }
}
