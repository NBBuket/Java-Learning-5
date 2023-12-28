package gov.app3;

import org.json.simple.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

import static gov.app3.Main.frame;
import static gov.app3.Main.shapesArray;

class Square implements RegularShapes {
    public void drawingShape (int x, int y, int z) {
        Graphics g = frame.getGraphics();
        g.drawRect(x, y, z, z);
    }
    public double area(double x) {
        System.out.println("Square area: " + (x * x));
        return x * x;
    }
    public double perimeter(double x) {
        System.out.println("Square perimeter: " + (4 * x));
        return 4 * x;
    }

    @Override
    public void objectJSON(int x, int y, int z) {
        JSONObject square = new JSONObject();
        square.put("shape", "square");
        square.put("x coordinate", x);
        square.put("y coordinate", y);
        square.put("width", z);
        shapesArray.add(square);
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
