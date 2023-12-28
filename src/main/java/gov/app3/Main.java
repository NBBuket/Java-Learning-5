package gov.app3;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    static ArrayList<String> myShapes = new ArrayList<>();
    static JSONArray shapesArray = new JSONArray();
    static JFrame frame = new JFrame("Shapes");
    public static void printer(String str) {
        System.out.println(str);
    }

        public static void main(String[] args) {

            frame.setSize(600, 600);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            Square mySquare = new Square();
            Triangle myTriangle = new Triangle();
            Circle myCircle = new Circle();
            Rectangle myRectangle = new Rectangle();

            AtomicReference<Double> areaSum = new AtomicReference<>((double) 0);
            ArrayList<Double> areas = new ArrayList<>();
            AtomicReference<Double> perimeterSum = new AtomicReference<>((double) 0);
            ArrayList<Double> perimeters = new ArrayList<>();

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();

            JButton reset = new JButton("Reset");
            reset.addActionListener(e -> {
                areaSum.set(0.0);
                perimeterSum.set(0.0);
                areas.clear();
                perimeters.clear();
                frame.getContentPane().repaint();
                myShapes.clear(); //clear the list
                printer("Areas, perimeters and the list is cleared.");

            });

            JButton rectangleButton = new JButton("Draw Rectangle");
            rectangleButton.addActionListener(e -> {
                printer("Please give the starting coordinates and width & height for the rectangle:");
                Scanner in = new Scanner(System.in);
                int x = in.nextInt();
                int y = in.nextInt();
                int rectangleWidth = in.nextInt();
                int rectangleHeight = in.nextInt();
                myRectangle.drawingShape(x, y, rectangleWidth, rectangleHeight);
                int rectangleArea = myRectangle.area(rectangleWidth, rectangleHeight);
                areas.add((double)rectangleArea);
                int rectanglePerimeter = myRectangle.perimeter(rectangleWidth, rectangleHeight);
                perimeters.add((double)rectanglePerimeter);
                myShapes.add("rectangle");
                myRectangle.rectangleJSON(x, y, rectangleWidth, rectangleHeight);
            });

            JButton triangleButton = new JButton("Draw Triangle");
            triangleButton.addActionListener(e -> {
                printer("Please give the starting coordinates and edge length for the triangle:");
                Scanner in = new Scanner(System.in);
                int x1 = in.nextInt();
                int y1 = in.nextInt();
                int triangleEdge = in.nextInt();
                myTriangle.drawingShape(x1, y1, triangleEdge);
                double triangleArea = myTriangle.area(triangleEdge);
                areas.add(triangleArea);
                double trianglePerimeter = myTriangle.perimeter(triangleEdge);
                perimeters.add(trianglePerimeter);
                myShapes.add("triangle");
                myTriangle.objectJSON(x1, y1, triangleEdge);
            });

            JButton circleButton = new JButton("Draw Circle");
            circleButton.addActionListener(e -> {
                printer("Please give the starting coordinates and radius values for the circle:");
                Scanner in = new Scanner(System.in);
                int x = in.nextInt();
                int y = in.nextInt();
                int circleRadius = in.nextInt();
                myCircle.drawingShape(x, y, circleRadius);
                double circleArea = myCircle.area(circleRadius);
                areas.add(circleArea);
                double circlePerimeter = myCircle.perimeter(circleRadius);
                perimeters.add(circlePerimeter);
                myShapes.add("circle");
                myCircle.objectJSON(x, y, circleRadius);
            });

            JButton squareButton = new JButton("Draw Square");
            squareButton.addActionListener(e -> {
                printer("Please give the starting coordinates and edge length for the square:");
                Scanner in = new Scanner(System.in);
                int x = in.nextInt();
                int y = in.nextInt();
                int squareEdge = in.nextInt();
                mySquare.drawingShape(x, y, squareEdge);
                double squareArea = mySquare.area(squareEdge);
                areas.add(squareArea);
                double squarePerimeter = mySquare.perimeter(squareEdge);
                perimeters.add(squarePerimeter);
                myShapes.add("square");
                mySquare.objectJSON(x, y, squareEdge);
            });

            JButton sumUp = new JButton("Sum Up");
            sumUp.addActionListener(e -> {
                areaSum.set(areas.stream().mapToDouble(i -> i).sum());
                perimeterSum.set(perimeters.stream().mapToDouble(i -> i).sum());
                System.out.printf("Total areas: %.2f%n", areaSum.get());
                System.out.printf("Total perimeters: %.2f%n", perimeterSum.get());
            });

            JButton writingButton = new JButton("Write"); //it writes the shapes list into the file
            writingButton.addActionListener(e -> printer("All infos are written in the file"));

            JButton redrawingButton = new JButton("Redraw"); //it redraws the shapes in shapes.json
            redrawingButton.addActionListener(e -> {
                JSONParser parser = new JSONParser();
                JSONArray arr = null;
                try {
                    arr = (JSONArray) parser.parse(new FileReader("shapes.json"));
                } catch (IOException | ParseException ex) {
                    throw new RuntimeException(ex);
                }

                for (Object obj : arr) {
                    JSONObject shape = (JSONObject) obj;
                    String shapeName = (String) shape.get("shape");

                    Object xCoordinate = shape.get("x coordinate");
                    int intXCoordinate = ((Number) xCoordinate).intValue();
                    Object yCoordinate = shape.get("y coordinate");
                    int intYCoordinate = ((Number) yCoordinate).intValue();
                    Object shapeWidth = shape.get("width");
                    int intShapeWidth = ((Number) shapeWidth).intValue();

                    switch(shapeName) {
                        case "circle":
                            myCircle.drawingShape(intXCoordinate, intYCoordinate, intShapeWidth);
                            break;

                        case "square":
                            mySquare.drawingShape(intXCoordinate, intYCoordinate, intShapeWidth);
                            break;

                        case "triangle":
                            myTriangle.drawingShape(intXCoordinate, intYCoordinate, intShapeWidth);
                            break;

                        case "rectangle":
                            Object shapeHeight = shape.get("height");
                            int intShapeHeight  = ((Number) shapeHeight).intValue();
                            myRectangle.drawingShape(intXCoordinate, intYCoordinate, intShapeWidth, intShapeHeight);
                            break;

                        default:
                            break;
                    }


                }
            });

            JButton addingButton = new JButton("Add to List"); //it reads the file and add the lines to the list
            addingButton.addActionListener(e -> {
                StringBuilder jsonData = new StringBuilder();
                BufferedReader br = null;
                try {
                    String line;
                    br = new BufferedReader(new FileReader("shapes.json"));
                    while ((line = br.readLine()) != null) {
                        jsonData.append(line).append("\n");
                    }
                } catch (IOException error) {
                    error.printStackTrace();
                } finally {
                    try {
                        if (br != null)
                            br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                FileWriter file;
                try {
                    file = new FileWriter("readShapes.json", true);
                    file.write(String.valueOf(jsonData));
                    file.write("\n");
                    file.flush();
                    file.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                printer("All infos are in the other file");
                printer(jsonData.toString());
            });

            panel1.add(sumUp);
            panel1.add(reset);
            panel1.add(redrawingButton);
            panel1.add(writingButton);
            panel1.add(addingButton);
            panel2.add(rectangleButton);
            panel2.add(triangleButton);
            panel2.add(circleButton);
            panel2.add(squareButton);

            frame.getContentPane().add(BorderLayout.SOUTH, panel1);
            frame.getContentPane().add(BorderLayout.NORTH, panel2);
            frame.setVisible(true);
        }
}
