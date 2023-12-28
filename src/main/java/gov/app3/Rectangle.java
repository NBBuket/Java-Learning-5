package gov.app3;

import org.json.simple.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import static gov.app3.Main.frame;
import static gov.app3.Main.shapesArray;

class Rectangle {
    void drawingShape(int x, int y, int z, int w) {
        Graphics g = frame.getGraphics();
        g.drawRect(x, y, z, w);
    }
    public int area(int x, int y) {
        System.out.println("Rectangle area: " + (x * y));
        return x * y;
    }
    public int perimeter(int x, int y) {
        System.out.println("Rectangle perimeter: " + (2 * (x + y)));
        return 2 * (x + y);
    }

    public void rectangleJSON(int x, int y, int z, int w) {

        JSONObject rectangle = new JSONObject();
        rectangle.put("shape", "rectangle");
        rectangle.put("x coordinate", x);
        rectangle.put("y coordinate", y);
        rectangle.put("width", z);
        rectangle.put("height", w);
        shapesArray.add(rectangle);
        try {
            FileWriter file = new FileWriter("shapes.json");
            file.write(shapesArray.toJSONString());
            file.write("\n");
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
