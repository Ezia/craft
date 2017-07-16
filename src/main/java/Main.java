import util.math.Matrix;
import view.lwjglUi.ui.container.LwjglTable;
import view.lwjglUi.ui.drawable.LwjglUniformColorPolygon;
import view.lwjglUi.ui.element.LwjglColoredPolygon;
import view.lwjglUi.ui.element.LwjglDrawing;
import view.lwjglUi.ui.element.LwjglTexturedPolygon;
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



        LwjglColoredPolygon rec = new LwjglColoredPolygon(
                new Rectangle(new Vector(0., 0.), new Vector(200., 200.)),
                new Vector(1., 1., 1., 0.6)
        );

        LwjglColoredPolygon rec1 = new LwjglColoredPolygon(
                new Rectangle(new Vector(0., 0.), new Vector(100., 100.)),
                new Vector(0., 1., 1., 0.3)
        );

        LwjglColoredPolygon rec2 = new LwjglColoredPolygon(
                new Rectangle(new Vector(0., 0.), new Vector(50., 50.)),
                new Vector(1., 0., 1., 0.3)
        );

        LwjglDrawing draw = new LwjglDrawing(
                new LwjglUniformColorPolygon(
                    new Rectangle(new Vector(0., 0.), new Vector(50., 50.)),
                    new Vector(1., 0.5, 1., 1)
                )
        );

        LwjglTexturedPolygon texRec = new LwjglTexturedPolygon(
                new Rectangle(new Vector(0., 0.), new Vector(250., 250.)),
//                new AWTImage("src/main/resources/test_photo.jpg")
                new AWTImage("src/main/resources/test.png")
        );

        texRec.getUITexturedPolygon().getTransform().setMatrix(Matrix.translation(new Vector(20., 20.)));

        LwjglTable tab = new LwjglTable(2, 2);
        LwjglTable tab1 = new LwjglTable(2, 2);
        tab1.getUITable().set(0, 0, rec);
        tab1.getUITable().set(1, 1, rec1);
        tab.getUITable().set(1, 0, tab1);
        tab.getUITable().set(1, 1, rec2);
        tab.getUITable().set(0, 1, draw);

        LwjglWindow win = new LwjglWindow(windowBox , "Craft");

        LwjglLayer layer0 = new LwjglLayer();

        win.addOnTop(layer0);

        layer0.addOnTop(texRec);
        layer0.addOnTop(tab);

        try {
            win.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}