import util.lwjglUI.ui.container.LwjglTable;
import util.lwjglUI.ui.element.LwjglRectangle;
import util.lwjglUI.ui.topObjects.LwjglLayer;
import util.lwjglUI.ui.topObjects.LwjglWindow;
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

        LwjglRectangle rec = new LwjglRectangle(new Vector(250., 250.), new Vector(1., 1., 1., 0.3));

        LwjglRectangle rec1 = new LwjglRectangle(new Vector(20., 100.), new Vector(0., 1., 1., 0.3));

        LwjglRectangle rec2 = new LwjglRectangle(new Vector(100., 50.), new Vector(1., 0., 1., 0.3));

        LwjglTable tab = new LwjglTable(3, 3);
        tab.set(0, 0, rec);
        tab.set(1, 1, rec1);
        tab.set(2, 2, rec2);

        LwjglWindow win = new LwjglWindow(windowBox , "Craft");

        LwjglLayer layer0 = new LwjglLayer();

        win.addOnTop(layer0);

//        layer0.addOnTop(rec);
//        layer0.addOnTop(rec1);
//        layer0.addOnTop(rec2);
        layer0.addOnTop(tab);

        try {
            win.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}