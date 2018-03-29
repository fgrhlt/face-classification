import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jakub on 10/5/16.
 */

public class Faces {
    /**
     * The main class that runs the face recognition.
     * @param args 0 = the training file, 1 = the training facit, 2 = the test file.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        ImageLoader loader = new ImageLoader(args[0], args[2]);
        ArrayList imgList = loader.getTrainingImages();
        ArrayList testList = loader.getTestImages();

        loader.loadFacit(args[1]);
        Network network = new Network();

        network.trainNetwork(imgList);
        network.examine(testList);
    }
}