import util.math.Color;
import view.lwjglUi.ui.container.LwjglTable;
import view.lwjglUi.ui.drawable.LwjglUniformColorPolygon;
import view.lwjglUi.ui.drawable.LwjglUniformTexturePolygon;
import view.lwjglUi.ui.element.LwjglDrawing;
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
                new Vector(50., 50.),
                new Vector(500., 500.)
        );


        LwjglDrawing draw = new LwjglDrawing(
                false,
                new LwjglUniformColorPolygon(
                    new Rectangle(new Vector(0., 0.), new Vector(150., 150.)),
                    new Color(Color.Type.RGBA, new Vector(1., 0.5, 1., 0.5))
                )
        );

        LwjglDrawing draw1 = new LwjglDrawing(
                false,
                new LwjglUniformColorPolygon(
                        new Rectangle(new Vector(0., 0.), new Vector(300., 300.)),
                        new Color(Color.Type.RGBA, new Vector(1., 0.5, 0., 1.))
				)
        );

        LwjglDrawing draw2 = new LwjglDrawing(
                true,
                new LwjglUniformColorPolygon(
                        new Rectangle(new Vector(0., 0.), new Vector(400., 200.)),
                        new Color(Color.Type.RGBA, new Vector(1., 0., 0.5, 0.5))
                )
//                new LwjglUniformTexturePolygon(
//                        new Rectangle(new Vector(0., 0.), new Vector(300., 400.)),
//                        new AWTImage("src/main/resources/test_photo.jpg")
//                )
        );


        LwjglTable tab = new LwjglTable(2, 2);
        tab.getUITable().set(0, 0, draw);
        tab.getUITable().set(1, 1, draw2);
        tab.getUITable().setBackground(draw1);

        draw2.setDimension(new Vector(150., 150.));
        draw1.getUIDrawing().setDimension(new Vector(10., 10.));

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