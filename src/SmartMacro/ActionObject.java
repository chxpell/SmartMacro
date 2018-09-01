/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartMacro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author connorschwing
 */
public class ActionObject {
    private final SimpleIntegerProperty index; // Position of the action in the script (starts at 1)
    private final SimpleStringProperty action; // 
    private final SimpleIntegerProperty xCoord;
    private final SimpleIntegerProperty yCoord;
    private final SimpleIntegerProperty delay;
    private final SimpleIntegerProperty repeat;
    private final SimpleStringProperty note;


    ActionObject(int index, String action, int xCoord, int yCoord, int delay, int repeat, String note)
    {
        this.index = new SimpleIntegerProperty(index);
        this.action = new SimpleStringProperty(action);
        this.xCoord = new SimpleIntegerProperty(xCoord);
        this.yCoord = new SimpleIntegerProperty(yCoord);
        this.delay = new SimpleIntegerProperty(delay);
        this.repeat = new SimpleIntegerProperty(repeat);
        this.note = new SimpleStringProperty(note);
    }

 
    public int getIndex() {
        return index.get();
    }
    public void setIndex(int i) {
        index.set(i);
    }
    
    public String getAction() {
        return action.get();
    }
    public void setAction(String a) {
        action.set(a);
    }
    
    public int getXCoord() {
        return xCoord.get();
    }
    public void setXCoord(int x) {
        xCoord.set(x);
    }
    
    public int getYCoord() {
        return yCoord.get();
    }
    public void setYCoord(int y) {
        yCoord.set(y);
    }
    
    public int getDelay() {
        return delay.get();
    }
    public void setDelay(int d) {
        delay.set(d);
    }
    
    public int getRepeat() {
        return repeat.get();
    }
    public void setRepeat(int r) {
        index.set(r);
    }
    
    public String getNote() {
        return note.get();
    }
    public void setNote(String n) {
        action.set(n);
    }

    public void print()
    {
        //System.out.println(index);
        System.out.printf("%d %s %d %d %d %d %s\n", getIndex(), getAction(), getXCoord(), getYCoord(), getDelay(), getRepeat(), getNote());
    }

       
}
