import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Simon Asp on 2016-10-06.
 */

/**
 * The network is responsible for holding the four output nodes, training them and also examining the performance.
 */
public class Network {

    private ArrayList<OutputNode> outputNodes = new ArrayList<>();
    private final Double learningRate = 0.005;
    private Double mse = 0.0;
    private final int MATRIX_SIZE = 400;
    private Double totalError = 0.0;
    private int counter = 0;

    /**
     * Creates a network with four output nodes.
     */
    public Network() {
        // Add four outputNodes to this network
        outputNodes.add(new OutputNode(learningRate, MATRIX_SIZE, 1));
        outputNodes.add(new OutputNode(learningRate, MATRIX_SIZE, 2));
        outputNodes.add(new OutputNode(learningRate, MATRIX_SIZE, 3));
        outputNodes.add(new OutputNode(learningRate, MATRIX_SIZE, 4));
    }

    /**
     * Trains this network with the training images provided.
     * Creates a counter which is the size of all the images times the number of nodes (around 800)
     */
    public void trainNetwork(ArrayList<Image> images) {
        runSamples(images);
        do {
            runSamples(images);
            Collections.shuffle(images);
        }
        while (mse > 0.01);
    }

    /**
     * Shows all the images for all nodes and executes the training method on each node.
     * Also takes the errors that each node make and squares it, then puts it in a sum.
     * The counter is to keep track of how many times this network has showed the images to the nodes.
     */
    private void runSamples(ArrayList<Image> images) {
        int expectedFaceValue;

        for (Image img : images) {
            //System.out.println(img.returnName());
            for (OutputNode node : outputNodes) {
                counter++;
                // Set the expected value as 1 if the faceValue of the node is the same as the images facit, else 0
                expectedFaceValue = (node.getFaceValue() == img.getFaceValue() ? 1 : 0);
                node.train(img, expectedFaceValue);
                totalError += Math.pow(node.getError(), 2);
            }
        }
        mse = totalError / counter;
    }

    /**
     * This is the examination function that tests the nodes and prints out the results
     * @param images - an image list
     */
    public void examine(ArrayList<Image> images) {

        for (Image img : images) {
            HashMap<Double, Integer> values = new HashMap<>();
            for (OutputNode node : outputNodes) {
                values.put(node.test(img), node.getFaceValue());
            }

            Double maxKey = Collections.max(values.keySet());
            int faceValue = values.get(maxKey);

            System.out.println(img.returnName() + " " + faceValue);
        }
    }
}