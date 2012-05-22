/* ========================================================
 * Font.java
 *
 * Author:      kenmchugh
 * Created:     Dec 15, 2010, 8:37:21 PM
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

package Goliath.Graphics;

import Goliath.Collections.List;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;


        
/**
 * Class Description.
 * For example:
 * <pre>
 *      Example usage
 * </pre>
 *
 * @see         Related Class
 * @version     1.0 Dec 15, 2010
 * @author      kenmchugh
**/
public class Font extends Goliath.Object
{
    private static List<String> g_oFonts;
    public static List<String> getFonts()
    {
        if (g_oFonts == null)
        {
            String[] laFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            Arrays.sort(laFonts);
            g_oFonts = new List<String>(laFonts);
        }
        return g_oFonts;
    }
    /**
     * Creates a new instance of Font
     */
    public Font()
    {
    }
}
