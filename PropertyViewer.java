import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed. 
 * 
 * 
 * @author Michael Kölling and Josh Murphy
 * @version 1.0
 */
public class PropertyViewer
{    
    private PropertyViewerGUI gui;              //the Graphical User Interface
    private Portfolio portfolio;
    private int currentProperty;                //variable to keep track of the property currently displayed
    private ArrayList<Property> viewedProperties;       //array that stores all the properties displayed
    /**
     * Create a PropertyViewer and display its GUI on screen, displaying first the first property of the CSV file.
     */
    public PropertyViewer()
    {
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        viewedProperties = new ArrayList<Property>();
        currentProperty = 0;
        gui.showProperty(portfolio.getProperty(currentProperty));
        gui.showID(portfolio.getProperty(currentProperty));
        viewedProperties.add(portfolio.getProperty(currentProperty));
        
    }

    /**
     *  Jumps to the next property in the CSV file.
     */
    public void nextProperty()
    {
       
        if(currentProperty == portfolio.numberOfProperties() - 1){      //Sanity check to avoid indexes greater than the number of properties
            currentProperty = portfolio.numberOfProperties() - 1;
        } else{
            currentProperty++;
            viewedProperties.add(portfolio.getProperty(currentProperty));
        }
        portfolio.getProperty(currentProperty);
        gui.showProperty(portfolio.getProperty(currentProperty));
        gui.showID(portfolio.getProperty(currentProperty));
        gui.showFavourite(portfolio.getProperty(currentProperty));
       
    }

    /**
     * Goes back to the previous property in the CSV file.
     */
    public void previousProperty()
    {
        if(currentProperty == 0){       //Sanity check to avoid negative property indexes by repeatedly pressing the 'Previous' button
            currentProperty = 0;
        } else{
            currentProperty--;
            viewedProperties.add(portfolio.getProperty(currentProperty));
        }
        portfolio.getProperty(currentProperty);
        gui.showProperty(portfolio.getProperty(currentProperty));
        gui.showID(portfolio.getProperty(currentProperty));
        gui.showFavourite(portfolio.getProperty(currentProperty));
    }

    /**
     * Sets the property to favourite or unnarmks it as favourite in case it already is.
     * After that it calls the gui to display wether it is favourite or not.
     */
    public void toggleFavourite() 
    {
        portfolio.getProperty(currentProperty).toggleFavourite();
        gui.showFavourite(portfolio.getProperty(currentProperty));
    }
    

    //----- methods for challenge tasks -----
    
    /**
     * This method opens the system's default internet browser
     * The Google maps page should show the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
       double latitude = portfolio.getProperty(currentProperty).getLatitude();
       double longitude = portfolio.getProperty(currentProperty).getLongitude();
       
       URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
       java.awt.Desktop.getDesktop().browse(uri); 
    }
    
    /**
     * Return the number of properties viewed since the application started
     * Viewing the same property twice counts as two views.
     */
    public int getNumberOfPropertiesViewed()
    {
        return viewedProperties.size();
    }
    
    /**
     * This method returns the average price of the properties viewed.
     */
    public int averagePropertyPrice()
    {
        double sum = 0;
        for(Property property : viewedProperties){
            sum += property.getPrice();
        } 
        return (int) Math.ceil(sum / viewedProperties.size()); //return average price rounded up to the closest integer
    }
    
}
