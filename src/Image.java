import java.util.ArrayList;


/**
 * Image
 * This class
 */
public class Image {
    private String imageName;
    private ArrayList<Double>pixels = new ArrayList();
    private int faceValue = 0;

    public Image(String name, ArrayList pixels, int faceValue) {
        this.imageName = name;
        this.pixels = pixels;
        this.faceValue = faceValue;
    }

    public ArrayList<Double> getPixels() {
        return pixels;
    }
    public String returnName() {
        return imageName;
    }
    public int getFaceValue(){
        return faceValue;
    }
    public void setFaceValue(int x){
        this.faceValue = x;
    }
}

