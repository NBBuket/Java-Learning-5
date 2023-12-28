package gov.app3;

import org.json.simple.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import static gov.app3.Main.frame;
import static gov.app3.Main.shapesArray;

class Circle implements RegularShapes {

    static final double PI = 3.14;
    public void drawingShape (int x, int y, int z) {
        Graphics g = frame.getGraphics();
        g.drawOval(x, y, 2 * z, 2 * z);
    }
    public double area(double x) {
        System.out.printf("Circle area: %.2f%n", 2 * x * PI);
        return 2 * x * PI;
    }
    public double perimeter(double x) {
        System.out.printf("Circle perimeter: %.2f%n", x * x * PI);
        return x * x * PI;
    }

    @Override
    public void objectJSON(int x, int y, int z) {
        JSONObject circle = new JSONObject();
        circle.put("shape", "circle");
        circle.put("x coordinate", x);
        circle.put("y coordinate", y);
        circle.put("width", z);
        shapesArray.add(circle);
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
