package util.shape;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * defined in counter clockwise order
 */
public interface Polygon extends Shape {

	PolygonalChain getPolygonalChain();

	PolygonalChain getTriangleChain();

}
