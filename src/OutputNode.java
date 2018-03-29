import java.util.ArrayList;

/**
 * Created by Simon on 10/6/16.
 */

/**
 * An output node is a node in the network which calculates activation levels and updates the weights to each input
 * it receives.
 */
public class OutputNode {

    private Double activationLevel;
    private Double learningRate;
    private Double error = 0.0;
    private ArrayList<Double> weights = new ArrayList();
    private int faceValue;

    /**
     * Adds weights and adds the last bias weight
     * @param learningRate Specify learning rate for the node
     * @param numOfInputs Amount of pixels
     * @param faceValue Specifies which face the node represents
     */
    public OutputNode(Double learningRate, int numOfInputs, int faceValue) {
        this.learningRate = learningRate;
        this.faceValue = faceValue;
        // Create a weights list for this node with random values between 0 and 1
        for(int i=0; i<numOfInputs; i++) {
            this.weights.add(Math.random());
        }
        // Add a bias weight
        this.weights.add(1.0);
    }

    /**
     * Train the node with the given input image
     * @param img An image that will be iterated
     */
    public void train(Image img, int expectedValue) {
        this.calculateActivationLevel(img);
        this.updateWeights(img, expectedValue);
    }

    /**
     * Test the node with given test image. Calculates the activation level that is previously trained.
     * @param img An image that will be iterated, usually from the test-file.
     * @return Activation level of this node.
     */
    public Double test(Image img){
        this.calculateActivationLevel(img);
        return this.getActivationLevel();
    }

    /**
     * Calculates this OutputNode's activation level by summing each pixel value in the incoming image with the
     * corresponding weight, and then running it through the sigmoid activation function.
     *
     * @return The activation level of this OutputNode
     */
    private void calculateActivationLevel(Image img) {
        Double sum = 0.0;
        ArrayList imagePixels = img.getPixels();
        // Sum all pixel values times the weight of the pixel value
        for (int i = 0; i < imagePixels.size(); i++) {
            sum += (Double)imagePixels.get(i) * weights.get(i);
        }
        // Put the bias weight (last index of the list) to the sum
        sum += weights.get(imagePixels.size());
        // Run activation function
        this.activationLevel = activationFunction(sum);
    }

    /**
     * The sigmoid activation function
     * @param x Input value as a double
     * @return An activation of the input
     */
    private Double activationFunction(Double x) {
        return (1 / (1 + Math.exp(-x)));
    }

    /**
     * This is the learning method for this OutputNode. It updates the weights of each input pixel by calculating the
     * error between the expected value and the activation value.
     */
    private void updateWeights(Image img, int expectedValue) {
        ArrayList imagePixels = img.getPixels();
        this.error = calculateError(expectedValue);

        // For all the pixels in the image
        for (int i = 0; i < imagePixels.size(); i++) {
            Double deltaWeight = learningRate * error * (Double)imagePixels.get(i);
            Double weight = weights.get(i);

            // Update the weight
            weights.set(i, deltaWeight + weight);
        }
    }

    /**
     * Calculates the error from this OutputNode in the form of ei = yi - ai
     * @return The error as a Double
     */
    private Double calculateError(int expectedValue) {
        return expectedValue - this.activationLevel;
    }

    /**
     * Returns this nodes activation level
     * @return The activation level as a Double
     */
    public Double getActivationLevel() {
        return this.activationLevel;
    }

    /**
     * Get the errorSum for this outputNode
     * @return the errorSum as a Double
     */
    public Double getError() {
        return this.error;
    }

    /**
     * Returns the weights of this node
     * @return An ArrayList with the weights of this node
     */
    public ArrayList<Double> getWeights() {
        return weights;
    }

    /**
     * Return the face representation that this node has
     * @return an integer representing a face (1=happy, 2=sad, 3=angry, 4=mischievous)
     */
    public int getFaceValue() {
        return faceValue;
    }
}
