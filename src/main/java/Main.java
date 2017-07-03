import lwjglUI.ui.element.LwjglRectangle;
import lwjglUI.ui.topObjects.LwjglLayer;
import lwjglUI.ui.topObjects.LwjglWindow;
import util.math.shape.shape2d.Rectangle;
import util.math.Vector;

public class Main {

    public static void main(String[] args) {
        Rectangle windowBox = new Rectangle(
                new Vector(0., 0.),
                new Vector(500., 500.)
        );

//        LwjglRectangle UIRect = new LwjglRectangle(null, RGBColor.PURPLE, new Vector2D(1, 1));
//
//        LwjglLayer layer = new LwjglLayer();
//        layer.setRootObject(UIRect);

        LwjglRectangle rec = new LwjglRectangle(new Vector(250., 250.), new Vector(1., 1., 1., 1.));

        LwjglWindow win = new LwjglWindow(windowBox , "Craft");

        LwjglLayer layer0 = new LwjglLayer();

        win.addOnTop(layer0);

        layer0.addOnTop(rec);

        win.start();
    }

}