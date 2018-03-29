import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Jakub on 10/5/16.
 */

/**
 * Class that loads the files and makes them into appropriate data structures, image lists. Also loads the facit file.
 */
public class ImageLoader {

    private ArrayList<Image> trainingImages = new ArrayList<>();
    private ArrayList<Image> testImages = new ArrayList<>();

    /**
     * This class takes in the training file and the training facit.
     * @param trainingFile The file that includes the training images
     * @param testFile The file that includes the test images
     */
    public ImageLoader(String trainingFile, String testFile) {
        try {
            this.trainingImages = loadFile(trainingFile);
            this.testImages = loadFile(testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method to load a training, or test file with images. When it's loaded it gets put in an image list with pixel
     * values.
     * @param fileName the path to the image list file.
     * @return an array list of images.
     * @throws IOException
     */
    private ArrayList<Image> loadFile(String fileName) throws IOException {
        File textFile = new File(fileName);
        String str;

        ArrayList<Image> imageList = new ArrayList<Image>();

        // Loads the input file
        try (Scanner scanner = new Scanner(textFile)) {
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                // Skipping unnecessary input lines
                if ((!str.startsWith("#") && (str.startsWith("Image")))) {
                    String imageName = str;
                    ArrayList<Double> pixels = new ArrayList<>();

                    // Loads pixels as Doubles into pixel array and normalizes them, there's 400 pixels
                    for (int i = 0; i < 400; i++) {
                        str = scanner.next();
                        // Filter out the noise pixels
                        if(Double.parseDouble(str) <= 6.0) {
                            pixels.add(0.0);
                        }
                        else {
                            pixels.add(Double.parseDouble(str) / 32);
                        }
                    }

                    //Object image for storing the name, pixels and result
                    Image image = new Image(imageName, pixels, 0);
                    //List that has every image we have loaded
                    imageList.add(image);
                }
            }
            scanner.close();
        }
        return imageList;
    }

    /**
     * Puts the right category from the facit file in the training images-list.
     * @param fileName - the path to the facit file.
     * @throws FileNotFoundException
     */
    public void loadFacit(String fileName) throws FileNotFoundException {

        File textFile = new File(fileName);
        String str;
        int i = 0;
        try (Scanner scanner = new Scanner(textFile)) {
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();

                //Skipping unnecessary input lines
                if ((!str.startsWith("#") && (str.startsWith("Image") && (i <= 199)))) {
                    trainingImages.get(i).setFaceValue(Integer.parseInt(str.substring(str.length() - 1)));
                    i++;
                }
            }
        }
    }

    /**
     * Getter for the training images
     * @return an array list with the training images.
     */
    public ArrayList<Image> getTrainingImages() {
        return trainingImages;
    }

    /**
     * Getter for the test images
     * @return a list of test-images
     */
    public ArrayList<Image> getTestImages() {
        return testImages;
    }
}