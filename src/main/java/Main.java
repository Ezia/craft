import lwjglUI.ui.element.LwjglRectangle;
import lwjglUI.ui.topObjects.LwjglLayer;
import lwjglUI.ui.topObjects.LwjglWindow;
import util.datastructure.AWTImage;
import util.math.shape.shape2d.Rectangle;
import util.math.Vector;

public class Main {

    public static void main(String[] args) {

        AWTImage img = new AWTImage("src/main/resources/test.png");
        try {
            img.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Rectangle windowBox = new Rectangle(
                new Vector(0., 0.),
                new Vector(500., 500.)
        );

        LwjglRectangle rec = new LwjglRectangle(new Vector(250., 250.), new Vector(1., 1., 1., 1.));

        LwjglWindow win = new LwjglWindow(windowBox , "Craft");

        LwjglLayer layer0 = new LwjglLayer();

        win.addOnTop(layer0);

        layer0.addOnTop(rec);

        win.start();
    }

}