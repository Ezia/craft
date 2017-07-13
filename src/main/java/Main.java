import view.lwjglUi.ui.container.LwjglTable;
import view.lwjglUi.ui.element.LwjglRectangle;
import view.lwjglUi.ui.topObjects.LwjglLayer;
import view.lwjglUi.ui.topObjects.LwjglWindow;
import view.awtUi.AWTImage;
import util.shape.Rectangle;
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

        LwjglRectangle rec1 = new LwjglRectangle(new Vector(100., 100.), new Vector(0., 1., 1., 0.3));

        LwjglRectangle rec2 = new LwjglRectangle(new Vector(50., 50.), new Vector(1., 0., 1., 0.3));

        LwjglTable tab = new LwjglTable(2, 2);
        LwjglTable tab1 = new LwjglTable(2, 2);
        tab1.getUITable().set(0, 0, rec);
        tab1.getUITable().set(1, 1, rec1);
        tab.getUITable().set(1, 0, tab1);
        tab.getUITable().set(1, 1, rec2);

        LwjglWindow win = new LwjglWindow(windowBox , "Craft");

        LwjglLayer layer0 = new LwjglLayer();

        win.addOnTop(layer0);

        layer0.addOnTop(tab);

        try {
            win.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}