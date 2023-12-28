package gov.app3;

import org.json.simple.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import static gov.app3.Main.frame;
import static gov.app3.Main.shapesArray;

class Triangle implements RegularShapes {
    public void drawingShape (int x1, int y1, int z) {
        Graphics g = frame.getGraphics();
        int[] x = {x1, (x1 + z), (x1 + z / 2)};
        int[] y = {y1, y1, y1+ z};
        g.drawPolygon(x, y, 3); //triangle
    }
    public double area(double x) {
        System.out.printf("Triangle area: %.2f%n", x * x * Math.sqrt(3) / 4);
        return x * x * Math.sqrt(3) / 4;
    }

    public double perimeter(double x) {
        System.out.println("Triangle perimeter: " + (3 * x));
        return 3 * x;
    }

    public void objectJSON(int x, int y, int z) {
        JSONObject triangle = new JSONObject();
        triangle.put("shape", "triangle");
        triangle.put("x coordinate", x);
        triangle.put("y coordinate", y);
        triangle.put("width", z);
        shapesArray.add(triangle);
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
