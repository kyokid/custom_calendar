package calendar.com.example.havh.mycustomcalendar;

import calendar.com.example.havh.mycustomcalendar.model.MyEvent;

/**
 * Created by HaVH on 2/9/18.
 */

public class MyEventView {
    private float x;
    private float y;
    private float width;
    private float height;

    private boolean isSelected;
    private MyEvent myEvent;
    public MyEventView(MyEvent myEvent){
        this.myEvent = myEvent;
    }

    public MyEvent getMyEvent() {
        return myEvent;
    }

    public void setMyEvent(MyEvent myEvent) {
        this.myEvent = myEvent;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
